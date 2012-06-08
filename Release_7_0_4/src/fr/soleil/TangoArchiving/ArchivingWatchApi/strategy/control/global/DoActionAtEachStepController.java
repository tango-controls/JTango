package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;

import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * A basic implementation. Does real database controls but doesn't do anything with the result except printing it. 
 * @author CLAISSE 
 */
public class DoActionAtEachStepController extends ControllerAdapter implements IController 
{
    DoActionAtEachStepController() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#useControlResult(archwatch.dto.ControlResult)
     */
    public int useControlResult ( ControlResult control ) 
    {
        if ( control == null )
        {
            return IController.NO_ACTION_NEEDED;
        }

        //System.out.println ( "------------CLA/DoActionAtEachStepController/useControlResult/control.isFullCycle ()/"+control.isFullCycle () );
        
        try
        {
            int level = Warnable.LOG_LEVEL_INFO;
            int ret;
            
            switch ( control.getCode () )
            {
                case ControlResult.ALL_OK :
                    ret = IController.NO_ACTION_NEEDED;
                break;
                
                case ControlResult.ALL_KO :
                    ret = IController.ACTION_SUCCESSED;
                break;
                
                case ControlResult.PARTIAL_KO :
                    ret = IController.ACTION_SUCCESSED;
                break;
                
                case ControlResult.ALL_UNDETERMINED :
                    ret = IController.ACTION_SUCCESSED;
                break;
                
                case ControlResult.NO_ATTRIBUTES_TO_CONTROL :
                    ret = IController.ACTION_SUCCESSED;
                break;
                
                default :
                    ret = IController.NO_ACTION_NEEDED;
                break;
            }
            
            Tools.trace  ( "DoActionAtEachStepController/action taken." , level );
            
            Tools.trace  ( "------------------REPORT START-----------------------" , level );
            Tools.trace  ( control.getReportHeader() , level );
            Tools.trace  ( "------------------REPORT END-------------------------" , level );
            
            /*Timestamp startDate = control.getStartDate ();
            Timestamp endDate = control.getEndDate ();*/
            
            return ret;
        }
        catch ( Throwable t )
        {
            t.printStackTrace ();
            return IController.ACTION_FAILED;
        }
    }
}
