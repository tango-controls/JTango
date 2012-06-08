/*	Synchrotron Soleil 
 *  
 *   File          :  ModeData.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  28 nov. 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ModeData.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.dto;

import java.sql.Timestamp;

import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;

/**
 * Models a line from the HDB table AMT.
 * Less specifically, describes a given attribute's archiving informations:
 * <UL> 
 *  <LI> Its modes
 *  <LI> The archiving start and stop date
 *  <LI> The name of the archiver in charge of this attribute
 * </UL>
 * @author CLAISSE 
 */
public class ModeData 
{
    private Mode mode;
    private Timestamp startDate;
    private Timestamp stopDate;
    private String archiver;
    
    private boolean isIncomplete = false;
    
    /**
     * Default constructor
     */
    public ModeData() 
    {
        super();
        isIncomplete = false;
    }


    /**
     * @return Returns the archiver.
     */
    public String getArchiver() {
        return archiver;
    }
    /**
     * @param archiver The archiver to set.
     */
    public void setArchiver(String archiver) {
        this.archiver = archiver;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    /**
     * @return Returns the stopDate.
     */
    public Timestamp getStopDate() {
        return stopDate;
    }
    /**
     * @param stopDate The stopDate to set.
     */
    public void setStopDate(Timestamp stopDate) {
        this.stopDate = stopDate;
    }
    /**
     * @return Returns the mode.
     */
    public Mode getMode() {
        return mode;
    }
    /**
     * @param mode The mode to set.
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }


    public void setIncomplete(boolean _incomplete) 
    {
        this.isIncomplete = _incomplete;
    }

    /**
     * @return Returns the isIncomplete.
     */
    public boolean isIncomplete() 
    {
        return isIncomplete;
    }    
}
