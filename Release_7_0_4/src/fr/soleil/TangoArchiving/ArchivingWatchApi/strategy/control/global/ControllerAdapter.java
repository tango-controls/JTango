package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;

import java.util.Enumeration;
import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.Archiver;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResultLine;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ModeData;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes.IModeController;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes.ModeControllerFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * A basic implementation. Does real database controls but doesn't do anything with the result except printing it. 
 * @author CLAISSE 
 */
public abstract class ControllerAdapter implements IController 
{
    private Hashtable attributesToControl;   
    
    private static final String GLOBAL_RETRY_COMMAND_NAME = "RetryForAttributes";
    private static final int GLOBAL_RETRY_COMMAND_TIMEOUT = 10000;

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#control()
     */
    public ControlResult control ( boolean doArchiverDiagnosis ) throws DevFailed
    {
        //System.out.println ( "BasicController/control/START" );
        long t0 = System.currentTimeMillis (); 
        ControlResult ret = new ControlResult ();
        IModeController modeController = ModeControllerFactory.getCurrentImpl ();
        
        if ( this.attributesToControl == null || this.attributesToControl.size () ==0 )
        {
            ret.setCode ( ControlResult.NO_ATTRIBUTES_TO_CONTROL );
            return ret;    
        }
        
        Enumeration enumeration = this.attributesToControl.keys ();
        while ( enumeration.hasMoreElements () )
        {
            String nextCompleteName = (String) enumeration.nextElement ();
            ArchivingAttribute nextAttr = new ArchivingAttribute ();
            nextAttr.setCompleteName ( nextCompleteName );
            int [] result = null;
         
            ModeData nextModeData = (ModeData) this.attributesToControl.get ( nextCompleteName );
            if ( nextModeData.isIncomplete () )
            {
                nextAttr.setDetermined ( false );
            }
            else
            {
                Mode nextMode = nextModeData.getMode ();
                
                if ( nextMode.getModeP () == null )
                {
                    Tools.trace ( "ControllerAdapter/control/modeP == null!" , Warnable.LOG_LEVEL_ERROR );
                }
                
                result = modeController.controlMode ( nextMode , nextAttr );
            }
            
            ControlResultLine line = new ControlResultLine ( nextAttr , nextModeData , result );
            ret.addLine ( line );
        }
        
        return ret;
    }

    private static boolean resultContainsUndeterminedMode(int[] result) 
    {
        if ( result == null ) 
        {
            return false;
        }
        for ( int i = 0 ; i < result.length ; i++ )
        {
            //System.out.println ( "CLA/BasicController/resultContainsUndeterminedMode/i|"+i+"|result [ i ]|"+result [ i ] );
            if ( result [ i ] == IModeController.CONTROL_FAILED )
            {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#serAttributesToControl(java.util.Hashtable)
     */
    public void setAttributesToControl( Hashtable _attributesToControl ) 
    {
        this.attributesToControl = _attributesToControl;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#doSomethingAboutActionResult(int)
     */
    public void doSomethingAboutActionResult(int actionResult) 
    {
        switch ( actionResult )
        {
            case IController.NO_ACTION_NEEDED:
                Tools.trace( "doSomethingAboutActionResult/No action needed being undertaken" , Warnable.LOG_LEVEL_DEBUG );
            break;
            
            case IController.ACTION_SUCCESSED:
                Tools.trace( "doSomethingAboutActionResult/The action successed !" , Warnable.LOG_LEVEL_INFO );
            break;
            
            case IController.ACTION_FAILED:
                Tools.trace( "doSomethingAboutActionResult/The action failed !" , Warnable.LOG_LEVEL_WARN );
            break;
        }
    }

    public static int doRetry(ControlResult control) 
    {
        Hashtable errorArchivers = control.getErrorArchivers ();
        if ( errorArchivers == null )
        {
            return IController.NO_ACTION_NEEDED;
        }
         
        Enumeration koArchivers = errorArchivers.keys ();
        while ( koArchivers.hasMoreElements () )
        {
            String nextArchiver = (String) koArchivers.nextElement ();
            Archiver archiver = (Archiver) errorArchivers.get ( nextArchiver );
            
            Hashtable htKOAttributess = archiver.getKOAttributes ();
            Enumeration koAttributes = htKOAttributess.keys ();
            String [] parameters = new String [ htKOAttributess.size () ];
            int i = 0;
            while ( koAttributes.hasMoreElements() )
            {
                String nextAttributeName = (String) koAttributes.nextElement ();
                parameters [ i ] = nextAttributeName;
                i ++;
            }
            
            try 
            {
                DeviceProxy proxy = new DeviceProxy ( nextArchiver );
                proxy.set_timeout_millis ( GLOBAL_RETRY_COMMAND_TIMEOUT );
                DeviceData argin = new DeviceData ();
                argin.insert ( parameters );
                
                proxy.command_inout_asynch ( GLOBAL_RETRY_COMMAND_NAME , argin , true );
            } 
            catch (DevFailed e) 
            {
                Tools.trace  ( "ControllerAdapter/doRetry/DevFailed for archiver|"+nextArchiver+"| (" + htKOAttributess.size () +" attributes)" , e , Warnable.LOG_LEVEL_WARN );
                return IController.ACTION_FAILED;
            }
            catch (Throwable e) 
            {
                Tools.trace  ( "ControllerAdapter/doRetry/Exception for archiver|"+nextArchiver+"| (" + htKOAttributess.size () +" attributes)" , e , Warnable.LOG_LEVEL_ERROR );
                return IController.ACTION_FAILED;
            }
        }
        return IController.ACTION_SUCCESSED;
    }

}
