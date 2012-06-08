/*	Synchrotron Soleil 
 *  
 *   File          :  IController.java
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
 *   Log: IController.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;

import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;

/**
 * Regroups the main steps of a unitary archiving control:
 * <UL> 
 *  <LI> Deciding on a list of attributes to control
 *  <LI> Controlling them
 *  <LI> Using the control result to execute an adapted action
 *  <LI> Ending the control depending on the action's result   
 * </UL>
 * @author CLAISSE 
 */
public interface IController 
{
    /**
     * Code for the case where no action needed to be undertaken
     */
    static final int NO_ACTION_NEEDED = 0;
    /**
     * Code for the case where the undertaken action successed
     */
    static final int ACTION_SUCCESSED = 1;
    /**
     * Code for the case where the undertaken action failed
     */
    static final int ACTION_FAILED = 2;
    
    /**
     * Sets the list of attributes to control.
     * @param attributesToControl A hashtable which keys are the attributes complete names, and which values are ModeData objects describing their theoretical archiving.
     */
    public void setAttributesToControl ( Hashtable attributesToControl );
    /**
     * Executes the control step for the pre loaded attributes 
     * @param doArchiverDiagnosis True if investigation of KO archivers (archivers that have at least 1 KO attribute) is required
     * @return The control's result 
     * @throws DevFailed
     */
    public ControlResult control ( boolean doArchiverDiagnosis ) throws DevFailed;
    /**
     * Uses the control's result to undertake an adapted action.
     * @param control The control's result to use
     * @return A return code, has to be either NO_ACTION_NEEDED, ACTION_SUCCESSED, or ACTION_FAILED
     */
    public int useControlResult ( ControlResult control );
    /**
     * Finishes the control depending on the action's result. 
     * @param actionResult The return code of the action
     */
    public void doSomethingAboutActionResult ( int actionResult ); 
}
