/*	Synchrotron Soleil 
 *  
 *   File          :  HdbLifeCycleManager.java
 *  
 *   Project       :  javaapi_watcherTDB3
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  22 août 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: HdbLifeCycleManager.java,v 
 *
 */
 /*
 * Created on 22 août 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.lifecycle;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing.ChoosingStrategyFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global.ControllerFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes.ModeControllerFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.SaferPeriodCalculatorFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay.DelayManagerFactory;

public class HdbLifeCycleManager extends DefaultLifeCycleManager
{
    public HdbLifeCycleManager() 
    {
        super();
    }

     protected void startFactories ( boolean doRetry ) throws DevFailed
     {
         DBReaderFactory.getImpl ( DBReaderFactory.HDB );
         
         ChoosingStrategyFactory.getImpl ( ChoosingStrategyFactory.SCALAR_AND_SPECTRUM_ONLY );
         
         if ( doRetry )
         {
             //ControllerFactory.getImpl ( ControllerFactory.RETRY_EVERY_THIRD_STEP );
             ControllerFactory.getImpl ( ControllerFactory.RETRY_EVERY_LARGE_AMOUNT_OF_STEPS );
         }
         else
         {
             ControllerFactory.getImpl ( ControllerFactory.DO_ACTION_AT_FULL_CYCLE );    
         }
         
         ModeControllerFactory.getImpl ( ModeControllerFactory.HDB_BY_LAST_INSERT_COMPARISON );
         
         DelayManagerFactory.getImpl ( DelayManagerFactory.SIMPLE );
         
         SaferPeriodCalculatorFactory.getImpl ( SaferPeriodCalculatorFactory.ABSOLUTE );
     }

}
