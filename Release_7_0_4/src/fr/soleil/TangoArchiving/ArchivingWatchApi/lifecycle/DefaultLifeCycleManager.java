//+======================================================================
//$Source$
//
//Project:      Tango Archiving Service
//
//Description:  Java source code for the class  DefaultLifeCycleManager.
//						(Claisse Laurent) - 5 juil. 2005
//
//$Author$
//
//$Revision$
//
//$Log$
//Revision 1.27  2007/05/09 16:42:06  chinkumo
//minor changes
//
//Revision 1.26  2007/02/08 10:12:54  ounsy
//minor changes
//
//Revision 1.25  2007/01/31 12:56:16  ounsy
//minor changes
//
//Revision 1.24  2007/01/22 10:46:23  ounsy
//modified the older than 1 cycle attributes removal process
//
//Revision 1.23  2006/12/14 14:54:57  ounsy
//addControlResult takes a new 'steps' parameter to allow to remove attributes older than one cycle from the ROLLOVER control result
//
//Revision 1.22  2006/11/20 09:30:28  ounsy
//minor changes
//
//Revision 1.21  2006/11/03 09:57:00  ounsy
//methods name changes in IDelayManager
//
//Revision 1.20  2006/10/20 13:03:55  ounsy
//minor changes
//
//Revision 1.19  2006/09/18 12:38:27  ounsy
//adjusted log levels
//
//Revision 1.18  2006/09/15 12:20:12  ounsy
//added cycles and steps computaion
//
//Revision 1.17  2006/09/07 14:41:10  ounsy
//minor changes
//
//Revision 1.16  2006/09/07 13:37:34  ounsy
//corrected the ArchivingWatcher problem pf the long first step
//
//Revision 1.15  2006/08/24 13:50:27  ounsy
//startFactories is now abstract and is implemented by the Hdb or Tdb specific managers
//
//Revision 1.14  2006/07/18 15:16:29  ounsy
//minor changes
//
//Revision 1.13  2006/07/18 08:07:46  ounsy
//modified the processStep method to only build the current CR if the step is the final step
//
//Revision 1.12  2006/06/02 14:26:13  ounsy
//added javadoc
//
//Revision 1.11  2006/05/16 16:15:22  ounsy
//commented two logs during applicationWiilStart beaus they were freezing the application launch..
//
//Revision 1.10  2006/05/16 13:17:09  ounsy
//added a isProcessing() method
//
//Revision 1.9  2006/05/05 08:59:20  ounsy
//Made the watcher more resilient
//
//Revision 1.8  2006/05/03 09:52:21  ounsy
//added a specific log message in case the cause of the FAULT state is certainly
//a dbConnection failure
//
//Revision 1.7  2006/03/01 15:43:03  ounsy
//spectrum attributes are now controlled
//
//Revision 1.6  2006/02/15 11:44:19  ounsy
//Added doRetry and doStartOnInitDevice flags
//
//Revision 1.5  2006/02/06 12:24:20  ounsy
//added the archiving auto-recovery process
//
//Revision 1.4  2006/01/27 13:02:27  ounsy
//added initialisation of the SaferPeriodCalculator factory
//
//Revision 1.3  2006/01/26 13:08:05  ounsy
//Added missing import declaration
//
//Revision 1.2  2006/01/26 12:57:16  ounsy
//The device logging system is now used instead of a System.out.println.
//
//Revision 1.1  2006/01/19 16:20:13  ounsy
//New API specialised in watching archiving.
//
//Revision 1.1.2.2  2005/09/14 15:41:32  chinkumo
//Second commit !
//
//
//copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoArchiving.ArchivingWatchApi.lifecycle;

import java.sql.Timestamp;
import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.ArchivingWatch;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.ChoosingStrategyFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.IChoosingStrategy;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global.ControllerFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global.IController;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay.DelayManagerFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay.IDelayManager;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * The default implementation.
 * Extends Thread so that it can run in a separate thread at the request of the device
 * @author CLAISSE 
 */
public abstract class DefaultLifeCycleManager extends Thread implements LifeCycleManager
{	 
    private boolean hasToStopProcessing = true;
    private boolean isProcessing = true;
    protected Warnable watcherToWarn;
    private static final String API_THREAD_NAME = "WatcherAPIThread";
    
    private int cycles = 0;
    private int steps = 0;
    
    DefaultLifeCycleManager ()
    {
        this.setName ( API_THREAD_NAME );
    }
    
    /* (non-Javadoc)
	  * @see mambo.lifecycle.LifeCycleManager#applicationWillStart(java.util.Hashtable)
	  */
	 public synchronized void applicationWillStart ( boolean doRetry )
	 {
	     Tools.trace ( ".....INITIALIZING APPLICATION" , Warnable.LOG_LEVEL_INFO );
	     
	     try
	     {
             startFactories ( doRetry );

		     IDBReader attributesReader = DBReaderFactory.getCurrentImpl ();
		     attributesReader.openConnectionIfNecessary ( true );
	     }
	     catch ( DevFailed t )
	     {
             Tools.trace ( "Error during applicationWillStart: " , t , Warnable.LOG_LEVEL_FATAL );
             t.printStackTrace ();
             this.warnWatcherFault ( t );
	     }
	     
	     //Tools.trace ( ".....APPLICATION INITIALIZED" , Warnable.LOG_LEVEL_INFO );
	     //Tools.trace ( "" , Warnable.LOG_LEVEL_INFO );
         //warning don't uncomment those logs, they can freeze the device.....?
	 }
	
	 protected abstract void startFactories ( boolean doRetry ) throws DevFailed;
	
	 /* (non-Javadoc)
	  * @see mambo.lifecycle.LifeCycleManager#applicationClosed(java.util.Hashtable)
	  */
	 public synchronized void applicationWillClose ( Hashtable endParameters )
	 {
	     try
	     {
	         Tools.trace ( ".....APPLICATION WILL CLOSE!" , Warnable.LOG_LEVEL_INFO );
	         
	         IDBReader attributesReader = DBReaderFactory.getCurrentImpl ();
	         attributesReader.closeConnection ();
	
	         Tools.trace ( ".....APPLICATION CLOSED" , Warnable.LOG_LEVEL_INFO );
	         
	         System.exit( 0 );
	     }
	     catch ( Throwable t )
	     {
	         t.printStackTrace();
	         System.exit( 1 );
	     }
	 }
	
	
	/* (non-Javadoc)
	 * @see archwatch.lifecycle.LifeCycleManager#process(java.util.Hashtable)
	 */
	public void process ( long macroPeriod , boolean doArchiverDiagnosis )
	{
	    IDelayManager delayManager = DelayManagerFactory.getCurrentImpl ();
        IChoosingStrategy choosingStrategy = ChoosingStrategyFactory.getCurrentImpl ();
	    long cycleComputationTime = 0;
	    
	    while ( true )
	    {
	        try
	        {
	        	// Wait for Start command (action) is requested
	            if ( this.hasToStopProcessing )
		        {
		            this.doSleep ( 100 );
                    this.setProcessing ( false );
                    
                    continue;
		        }
                this.setProcessing ( true );
                
		        Tools.trace ( "" , Warnable.LOG_LEVEL_DEBUG );
		        if ( delayManager.isNewCycle () )
		        {
		            Tools.trace ( ".....STARTING CONTROL CYCLE" , Warnable.LOG_LEVEL_INFO );
		            Tools.trace ( "" , Warnable.LOG_LEVEL_INFO );
		            cycleComputationTime = 0;
		        }
                Tools.trace ( ".....STARTING CONTROL STEP" , Warnable.LOG_LEVEL_INFO );
                		        
                long beforeStep = System.currentTimeMillis ();
                processStep ( macroPeriod , doArchiverDiagnosis );
                long afterStep = System.currentTimeMillis ();

                long stepDuration = afterStep - beforeStep;
                int finalSize = choosingStrategy.getNumberOfAttributesToControl();
                long stepPeriod  = delayManager.getTimeBetweenSteps ( finalSize , macroPeriod );
                long sleepDuration = Math.max ( stepPeriod - stepDuration , 0 );		        
		        cycleComputationTime += stepDuration;
		        int stepSize = delayManager.getSizeOfLatestAddition ();
		        int sleepDurationInSeconds  = (int) Math.round ( sleepDuration / 1000.0 );
                Tools.trace  ( "stepDuration/"+stepDuration+"/stepPeriod/"+stepPeriod+"/sleepDuration/"+sleepDuration , Warnable.LOG_LEVEL_INFO );
                
		        Timestamp nextStepTime = new Timestamp ( afterStep + sleepDuration );
                
                Tools.trace ( ".....ENDING CONTROL STEP. Controlled " + stepSize + " attributes in " + stepDuration + " milliseconds." , Warnable.LOG_LEVEL_INFO );
		        
                if ( delayManager.isNewCycle () )
		        {
		            Tools.trace ( ".....ENDING CONTROL CYCLE. Controlled " + finalSize + " attributes in " + cycleComputationTime + " milliseconds." , Warnable.LOG_LEVEL_INFO );
		            Tools.trace ( "" , Warnable.LOG_LEVEL_INFO );
		        }
		        Tools.trace ( ".....WAITING FOR " + sleepDuration + " milliseconds. Next control step: " + nextStepTime , Warnable.LOG_LEVEL_INFO );

		        this.doSleep ( sleepDuration );
	        }
	        catch ( DevFailed t )
	        {
	            t.printStackTrace ();
                Tools.trace ( "Error during process: " , t , Warnable.LOG_LEVEL_FATAL );
	            this.warnWatcherFault ( t );
	        }
	    }
	}

	private synchronized void setProcessing(boolean _isProcessing) 
    {
	    this.isProcessing = _isProcessing;
    }

    private void doSleep ( long sleepDuration )
	{
	    try 
        {
            Thread.sleep ( sleepDuration );
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
            this.applicationWillClose ( null );
        }
	}
	
	public void processStep ( long macroPeriod , boolean doArchiverDiagnosis ) throws DevFailed
    {
	    IDelayManager delayManager = DelayManagerFactory.getCurrentImpl ();
	    Hashtable archivedAttributes = delayManager.reloadArchivingAttributesIfNecessary ();
	    int numberOfArchivingAttributes = archivedAttributes == null ? 0 : archivedAttributes.size ();
	    
	    // Filters out unwanted attributes and positions the cursor to the next position in the attributes list 
	    Hashtable stepArchivedAttributes = delayManager.chooseAttributesSubsetForCurrentStep ( archivedAttributes );

        IController controller = ControllerFactory.getCurrentImpl ();
	    controller.setAttributesToControl ( stepArchivedAttributes );
	    
	    ControlResult stepControlResult = null;
        stepControlResult = controller.control ( doArchiverDiagnosis );
        boolean isFullCycle = delayManager.addControlResult ( stepControlResult , archivedAttributes.keySet() );
        
        ControlResult currentResult = delayManager.getControlResult ( IDelayManager.READ_ROLLOVER );
        
        if ( currentResult != null )
        {
            //CLA 14/12/06
            currentResult.setNumberOfArchivingAttributes ( numberOfArchivingAttributes );
            
            currentResult.build ( doArchiverDiagnosis , isFullCycle );
            currentResult.setCompletedCycles ( cycles );
            currentResult.setCompletedSteps ( steps );
            //System.out.println ( "cycles/"+cycles+"/steps/"+steps );
            if ( currentResult.hasErrors () )
            {
                if ( currentResult.isComplete () )//otherwise it isn't a "complete" result
                {
                    delayManager.setLatestBadCompleteResult ( currentResult.cloneControlResult () );   
                }
                this.warnWatcherAlarm ();
            }
            //---CLA 12/05/06
            else if ( ! currentResult.isEmpty () )
            {
                this.warnWatcherOn ();
            }
            //---CLA 12/05/06
            
            int actionResult = controller.useControlResult ( currentResult );
            controller.doSomethingAboutActionResult ( actionResult );
            
            if ( isFullCycle )
            {
                cycles ++;
                steps = 0;
            }
            else
            {
                steps ++;
            }
        }
    }


    /* (non-Javadoc)
     * @see archwatch.lifecycle.LifeCycleManager#stopProcessing()
     */
    public synchronized void stopProcessing() 
    {
        this.hasToStopProcessing = true;  
        this.warnWatcherOff ();
    }
    
    public synchronized void startProcessing() 
    {
        this.hasToStopProcessing = false;        
        this.warnWatcherInit ();
    }


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        try 
        {
            long macroPeriod = ArchivingWatch.getMacroPeriod ();   
            boolean doArchiverDiagnosis = ArchivingWatch.isDoArchiverDiagnosis ();
            boolean doRetry = ArchivingWatch.isDoRetry ();
            boolean doStartOnInitDevice = ArchivingWatch.isDoStartOnInitDevice ();

            this.applicationWillStart ( doRetry );

            if ( doStartOnInitDevice )
            {
                this.startProcessing ();
            }

            this.process ( macroPeriod , doArchiverDiagnosis );
        } 
        catch (Throwable t) 
        {        
            t.printStackTrace();
        }
    }


    /* (non-Javadoc)
     * @see archwatch.lifecycle.LifeCycleManager#getAsThread()
     */
    public Thread getAsThread () 
    {
        return this;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.delay.IDelayManager#setWatcherToWarn(ArchivingWatcher.ArchivingWatcher)
     */
    public void setWatcherToWarn(Warnable _watcher) 
    {
        this.watcherToWarn = _watcher;
    }
    
    public Warnable getWatcherToWarn () 
    {
        return this.watcherToWarn;
    }
    
    protected void warnWatcherFault ( DevFailed t ) 
    {
        //this.stopProcessing ();
        if ( this.watcherToWarn == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        this.watcherToWarn.warnFault ( t );
    }
    
    protected void warnWatcherAlarm () 
    {
        if ( this.watcherToWarn == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        this.watcherToWarn.warnAlarm ();
    }
    
    protected void warnWatcherInit () 
    {
        if ( this.watcherToWarn == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        this.watcherToWarn.warnInit ();
    }
    
    protected void warnWatcherOff () 
    {
        if ( this.watcherToWarn == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        this.watcherToWarn.warnOff ();
    }
    
    protected void warnWatcherOn () 
    {
        if ( this.watcherToWarn == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        this.watcherToWarn.warnOn ();
    }

    protected void logIntoDiary ( int level , Object o )
    {
        if ( this.watcherToWarn == null )
        {
            //standalone mode, do nothing
            return;
        }
        
        this.watcherToWarn.logIntoDiary ( level , o );
    }
    
    
    /**
     * @return Returns the hasToStopProcessing.
     */
    public boolean isProcessing() 
    {
        //return this.isProcessing;
        return !this.hasToStopProcessing;
    }
}
