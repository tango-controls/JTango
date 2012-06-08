/*  Synchrotron Soleil 
 *  
 *   File          :  BasicModeController.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  28 nov. 2005 
 *  
 *   Revision:                      Author:  
 *   Date:                          State:  
 *  
 *   Log: BasicModeController.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes;

import java.sql.Timestamp;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.ISaferPeriodCalculator;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.safetyperiod.SaferPeriodCalculatorFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode;

/**
 * An implementation that looks how many records (if any) have been inserted since <i>f(period)</i> ago.
 * Where <i>f(period)</i> is the "safety period", meaning a time span longer than period to allow for network (or any other reason) delays.   
 * @author CLAISSE 
 */
public class HdbModeControllerByLastInsertComparison extends HdbModeControllerAdapter 
{
    private int cnt;

    public HdbModeControllerByLastInsertComparison() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode, archwatch.dto.Attribute)
     */
    protected int controlPeriodicMode(ModePeriode modeP, ArchivingAttribute attr) throws DevFailed 
    {
        //modeP = nullAfterAFewTries ( modeP );
        if ( modeP == null )
        {
            Tools.trace ( "HdbModeControllerByLastInsertComparison/controlPeriodicMode/modeP == null!/for attribute|"+attr.getCompleteName()+"|" , Warnable.LOG_LEVEL_ERROR );
            return IModeController.CONTROL_FAILED;
        }
        int period = modeP.getPeriod ();
        ISaferPeriodCalculator saferPeriodCalculator = SaferPeriodCalculatorFactory.getCurrentImpl();
        int saferPeriod = saferPeriodCalculator.getSaferPeriod ( period );
        
        String completeName = attr.getCompleteName ();
        IDBReader attributesReader = DBReaderFactory.getCurrentImpl ();
        
        Timestamp now;
        Timestamp timeOfLastInsert;
        boolean isOk = false;
        
        try
        {
            now = attributesReader.now ();
            
            //System.out.println ( "-------------/now/"+now );
            timeOfLastInsert = attributesReader.getTimeOfLastInsert ( completeName );
            //System.out.println ( "-------------/timeOfLastInsert/"+timeOfLastInsert );
            attr.setLastInsert ( timeOfLastInsert );
            if ( timeOfLastInsert == null )
            {
                return IModeController.CONTROL_KO;    
            }   
            
            long delta = now.getTime () - timeOfLastInsert.getTime ();
            isOk = ( delta < saferPeriod );
            //System.out.println ( "-------------/delta/"+delta+"/saferPeriod/"+saferPeriod+"/isOk/"+isOk );
        }
        catch ( DevFailed e )
        {
            Tools.trace ( "HdbModeControllerByRecordCount/controlPeriodicMode/failed calling getRecordCount for attribute|"+completeName+"|" , e , Warnable.LOG_LEVEL_WARN );
            return IModeController.CONTROL_FAILED;
        }
        
        if ( isOk )
        {
            return IModeController.CONTROL_OK;
        }
        else
        {
            return IModeController.CONTROL_KO;    
        }
    }

    private ModePeriode nullAfterAFewTries(ModePeriode modeP) 
    {
        cnt ++;
        if ( cnt > 5 )
        {
            return null;
        }
        else
        {
            return modeP;
        }
        
    }

}