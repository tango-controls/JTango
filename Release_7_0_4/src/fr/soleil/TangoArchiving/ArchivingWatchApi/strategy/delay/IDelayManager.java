/*	Synchrotron Soleil 
 *  
 *   File          :  IDelayManager.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  5 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: IDelayManager.java,v 
 *
 */
 /*
 * Created on 5 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.delay;

import java.util.Hashtable;
import java.util.Set;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warner;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;

/**
 * Links together the watcher's different time scales:
 * <UL>
 *  <LI> The cycle: lasts for macroPeriod (cf. device properties), and covers all controlled attributes
 *  <LI> The step: a subdivision of the cycle, it is the atomic control unit
 *  <LI> Timescales bigger than macroPeriod: references to older Control Results are stored, for example the most recent CR having KO attributes
 * </UL>
 * @author CLAISSE 
 */
public interface IDelayManager extends Warner
{
    /**
     * The code for reading the latest complete full cycle
     */
    public static final int READ_LATEST_COMPLETE_CYCLE = 10;
    /**
     * The code for reading the latest complete full cycle, or (if there is none) the latest control step 
     */
	public static final int READ_LATEST_COMPLETE_CYCLE_AUTO = 11;
    /**
     * The code for reading the latest control step
     */
    public static final int READ_LATEST_COMPLETE_STEP = 20;
    /**
     * The code for reading the latest control result added to the curren cycle (coincides with READ_LATEST_COMPLETE_STEP) 
     */
	public static final int READ_LATEST_ADDITION = 30;
	/**
	 * The code for reading the latest complete full cycle having KO attributes
	 */
	public static final int READ_LATEST_BAD_CYCLE = 40;
    /**
     * The code for reading the current rollover control result (rollover meaning that each time a step's attributes are added, they override the same attributes' older result )
     */
    public static final int READ_ROLLOVER = 50;
    
    /**
     * Add a step CR to the current CR
     * @param controlResult The CR to add
     * @param step 
     * @param cycle 
     * @return true if this addition completed a full control cycle
     */
    public boolean addControlResult ( ControlResult controlResult , Set namesOfAttributesToControl ); 
    /**
     * Returns the CR of the specified type.
     * @param readType The type of CR one wishes to read 
     * @return The required CR
     * @throws DevFailed
     */
    public ControlResult getControlResult ( int readType ) throws DevFailed;
    public ControlResult getLatestCompleteCycleResult ();
    public ControlResult getLatestCompleteStepResult ();
    public ControlResult getLatestBadCompleteResult ();
    public void setLatestBadCompleteResult(ControlResult latestBadCompleteResult);
    public ControlResult getLatestAddition ();
    public int getSizeOfLatestAddition();
    
    public Hashtable chooseAttributesSubsetForCurrentStep ( Hashtable filteredAttributes ) throws DevFailed;
    public Hashtable reloadArchivingAttributesIfNecessary () throws DevFailed;
    
    public boolean isNewCycle ();
    public void setNewCycle ( boolean isNewCycle );
    
    public int getSizeOfSteps ( int totalNumberOfAttributes , long macroPeriod ); 
    public long getTimeBetweenSteps ( int totalNumberOfAttributes , long macroPeriod );
}
