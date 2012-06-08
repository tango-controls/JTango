/*	Synchrotron Soleil 
 *  
 *   File          :  ControlResultLine.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  30 nov. 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ControlResultLine.java,v 
 *
 */
 /*
 * Created on 30 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.dto;

import java.sql.Timestamp;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.ArchivingWatch;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.device.IDbProxy;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes.IModeController;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * An attribute-specific line of a ControlResult
 * @author CLAISSE 
 */
public class ControlResultLine 
{
    private ArchivingAttribute attribute;
    private ModeData modeData;
    private int [] result;
    
    private int step;
    private int cycle;
    
    /**
     * Builds an ControlResultLine
     * @param _attr The attribute this line is all about 
     * @param _modeData The attribute's archiving mode description
     * @param _result The result of the archiving controls on each mode
     */
    public ControlResultLine ( ArchivingAttribute _attr , ModeData _modeData , int [] _result )
    {
        super();
        
        this.attribute = _attr;
        this.modeData = _modeData;
        this.result = _result;
    }

    public ControlResultLine ( String attributeName ) 
    {
        super();
        
        this.attribute = new ArchivingAttribute ( attributeName );
    }

    /**
     * @return Returns the attribute.
     */
    public ArchivingAttribute getAttribute() {
        return attribute;
    }
    /**
     * @param attr The attribute to set.
     */
    public void setAttribute(ArchivingAttribute attr) {
        this.attribute = attr;
    }
    /**
     * @return Returns the modeData.
     */
    public ModeData getModeData() {
        return modeData;
    }
    /**
     * @param modeData The modeData to set.
     */
    public void setModeData(ModeData modeData) {
        this.modeData = modeData;
    }
    /**
     * @return Returns the result.
     */
    public int[] getResult() {
        return result;
    }
    /**
     * @param result The result to set.
     */
    public void setResult(int[] result) {
        this.result = result;
    }
    
    /**
     * Builds the line from the raw data.
     * <UL>
     *  <LI> Computes the archiving status of this line, using the result field.
     *  <LI> If necessary adds additionnal data such as the lastInsert and lastInsert dates 
     * </UL>
     * @throws DevFailed
     */
    public boolean build () throws DevFailed
    {
        int len = IModeController.NUMBER_OF_MODES_TO_CONTROL;
        boolean isOK = true;
        boolean isDetermined = true;
        
        boolean controlOkButValueIsNull = false;
        boolean controlOkButValueMightBeNull = false;
        
        if ( this.result != null )
        {
            for ( int i = 0 ; i < len ; i ++ )
            {
                if ( this.result [ i ] == IModeController.CONTROL_FAILED )
                {
                    isDetermined = false;
                    break;
                }
                
                if ( this.result [ i ] == IModeController.CONTROL_KO )
                {
                    isOK = false;
                    break;
                }
                
                if ( this.result [ i ] == IModeController.CONTROL_OK_BUT_VALUE_IS_NULL )
                {
                    controlOkButValueIsNull = true;
                }
                if ( this.result [ i ] == IModeController.CONTROL_OK_BUT_VALUE_MIGHT_BE_NULL )
                {
                    controlOkButValueMightBeNull = true;
                }
            }
        }
        else
        {
            isDetermined = false;
            isOK = false;
        }
        
        if ( isDetermined )
        {
            if ( controlOkButValueIsNull )
            {
                this.attribute.setArchivingStatus ( IModeController.CONTROL_OK_BUT_VALUE_IS_NULL );
            }
            else if ( controlOkButValueMightBeNull )
            {
                this.attribute.setArchivingStatus ( IModeController.CONTROL_OK_BUT_VALUE_MIGHT_BE_NULL );
            }
            else
            {
                this.attribute.setArchivingStatus ( isOK ? IModeController.CONTROL_OK : IModeController.CONTROL_KO );
            }
        }
        else
        {
            this.attribute.setArchivingStatus ( IModeController.CONTROL_FAILED );
        }
        
        String completeName = this.attribute.getCompleteName ();
        //Tools.trace ( "ControlResultLine/build/completeName|"+completeName+"|isDetermined|"+isDetermined+"|isOK|"+isOK , Warnable.LOG_LEVEL_DEBUG );
        
        String archiver = null;
        int period = 0;
        if ( modeData != null && ! modeData.isIncomplete () )
        {
            archiver = modeData.getArchiver ();
            period = modeData.getMode ().getModeP ().getPeriod ();
            
            //Tools.trace ( "ControlResultLine/build/modeData != null/archiver|"+archiver+"|period|"+period , Warnable.LOG_LEVEL_DEBUG );
        }
        else
        {
            //Tools.trace ( "ControlResultLine/build/modeData == null" , Warnable.LOG_LEVEL_DEBUG );
            archiver = "THIS";
        }
        
        boolean isArchivingOK = ( this.attribute.getArchivingStatus () == IModeController.CONTROL_OK );
        isArchivingOK = isArchivingOK || ( this.attribute.getArchivingStatus () == IModeController.CONTROL_OK_BUT_VALUE_IS_NULL );
        isArchivingOK = isArchivingOK || ( this.attribute.getArchivingStatus () == IModeController.CONTROL_OK_BUT_VALUE_MIGHT_BE_NULL );
        
        this.attribute.setArchiver ( archiver );
        this.attribute.setPeriod ( period );
        this.attribute.setDetermined ( isDetermined );
        
        if ( ! isDetermined )
        {
            return false;
        }
        this.attribute.setArchivingOK ( isArchivingOK );
        
        if ( ! isArchivingOK )
        {
            IDBReader hdbReader = DBReaderFactory.getCurrentImpl ();
            
            try
            {
	            if ( hdbReader == null )
	            {
                    IDbProxy dbProxy = ArchivingWatch.getDbProxy ();
	                
                    Timestamp lastInsert = dbProxy.getLastInsert ( completeName );
                    Timestamp lastInsertRequest = dbProxy.getLastInsertRequest ( completeName );
               
                    this.attribute.setLastInsert ( lastInsert );
                    this.attribute.setLastInsertRequest ( lastInsertRequest );
	            }
            }
            catch ( Exception e )
            {
                //Tools.throwDevFailed ( e );
                e.printStackTrace ();
                Tools.trace ( "ControlResultLine/build/failed to load the lastInsert date for attrtibute|"+this.getAttribute ().getCompleteName ()+"|" , e , Warnable.LOG_LEVEL_WARN );
            }  
        }
       
        return isOK;
    }
    
    /**
     * Returns a String representation of this line 
     * @return This line's report
     */
    public String getReport() 
    {
        ArchivingAttribute attr = this.getAttribute ();
        
        boolean isArchivingOK = attr.isArchivingOK ();
        boolean isDetermined = attr.isDetermined ();
        int period = attr.getPeriod ();
        String archiver = attr.getArchiver ();
        Timestamp lastInsert = attr.getLastInsert ();
        Timestamp lastInsertRequest = attr.getLastInsertRequest ();
        String completeName = attr.getCompleteName ();
        String deviceStatus = attr.getDeviceStatus ();
        
        return formatReportLine ( completeName , archiver , isArchivingOK , isDetermined , period , lastInsert , lastInsertRequest , deviceStatus );
    }
    
    /**
     * Returns whether this line describes an OK attribute 
     * @return 
     */
    public boolean isArchivingOK ()
    {
        ArchivingAttribute attr = this.getAttribute ();
        
        boolean isArchivingOK = attr.isArchivingOK ();
        boolean isDetermined = attr.isDetermined ();
        
        return isArchivingOK && isDetermined;
    }
    
    /**
     * Returns whether this attribute's line has a null most recent value 
     * @return 
     */
    public boolean isValueCertainlyNull ()
    {
        int status = this.getAttribute ().getArchivingStatus ();
        return status == IModeController.CONTROL_OK_BUT_VALUE_IS_NULL;
    }
    
    /**
     * Returns true if it couldn't be determined (due to eg. an SQL error) whether this attribute's line has a null most recent value or not 
     * @return 
     */
    public boolean isValuePossiblyNull ()
    {
        int status = this.getAttribute ().getArchivingStatus ();
        return status == IModeController.CONTROL_OK_BUT_VALUE_MIGHT_BE_NULL;
    }
    
    private String formatReportLine ( String completeName , String archiver , boolean isArchivingOK , boolean isDetermined , int period , Timestamp lastInsert , Timestamp lastInsertRequest , String deviceStatus ) 
    {
        StringBuffer buff = new StringBuffer ();
        String status = isArchivingOK ? "OK" : "KO";
        if ( ! isDetermined )
        {
            status = "??";
        }
        
        buff.append ( status );
        buff.append ( "    " );
        buff.append ( completeName );
        buff.append ( "          period: " + period ); 
        buff.append ( "     archiver: " + archiver );
        buff.append ( "     lastInsert: " + lastInsert );
        
        IDbProxy dbProxy = ArchivingWatch.getDbProxy ();
        if ( dbProxy != null && isDetermined ) //HDBarchiver internal controls mode  
        {
            buff.append ( "     lastInsertRequest: " + lastInsertRequest );
        }
        //buff.append ( "     deviceStatus: " + deviceStatus );
        
        return buff.toString ();
    }

    public void setStep(int _step) 
    {
        this.step = _step;  
    }

    /**
     * @return the step
     */
    public int getStep() {
        return this.step;
    }

    /**
     * @return the cycle
     */
    public int getCycle() {
        return this.cycle;
    }

    /**
     * @param cycle the cycle to set
     */
    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public boolean isOlderThan(int step2, int cycle2) 
    {
        if ( this.getCycle() < cycle2 - 1 )
        {
            return true;
        }
        else if ( this.getCycle() > cycle2 - 1 )
        {
            return false;
        }
        else //this.getCycle() == cycle2 - 1
        {
            return false;    
        }
    }
}
