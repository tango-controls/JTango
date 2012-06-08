/*	Synchrotron Soleil 
 *  
 *   File          :  IModeController.java
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
 *   Log: IModeController.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.modes;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;

/**
 * Regroups the archiving controls on all possible archiving modes. 
 * @author CLAISSE 
 */
public interface IModeController 
{
    /**
     * The attribute is archiving correctly
     */
    public static final int CONTROL_OK = 0;
    /**
     * The attribute isn't archiving correctly
     */
    public static final int CONTROL_KO = 1;
    /**
     * The control that was supposed to find out whether the attribute is archiving correctly or not failed
     */
    public static final int CONTROL_FAILED = 2;
    /**
     * The control hasn't been done yet
     */
    public static final int CONTROL_NOT_DONE = 3;
    /**
     * The attribute is archiving, but the most recent archived value is null
     */
    public static final int CONTROL_OK_BUT_VALUE_IS_NULL = 4;
    /**
     * The attribute is archiving, but the control that was supposed to find out whether the attribute's most recent archived value is null or not failed
     */
    public static final int CONTROL_OK_BUT_VALUE_MIGHT_BE_NULL = 5;
    
    /**
     * The number of diferent archiving modes
     */
    public static final int NUMBER_OF_MODES_TO_CONTROL = 7;
    
    /**
     * Index position of the periodic mode in the control's result 
     */
    public static final int MODE_P_POSITION = 0;
    /**
     * Index position of the absolute mode in the control's result 
     */
    public static final int MODE_A_POSITION = 1;
    /**
     * Index position of the relative mode in the control's result 
     */
    public static final int MODE_R_POSITION = 2;
    /**
     * Index position of the threshold mode in the control's result 
     */
    public static final int MODE_T_POSITION = 3;
    /**
     * Index position of the difference mode in the control's result 
     */
    public static final int MODE_D_POSITION = 4;
    /**
     * Index position of the calculation mode in the control's result 
     */
    public static final int MODE_C_POSITION = 5;
    /**
     * Index position of the external mode in the control's result 
     */
    public static final int MODE_E_POSITION = 6;
    
    /**
     * Controls the archiving of a given attribute with respect to a theoretical global mode.
     * @param mode The global archiving mode to control
     * @param attr The attribute to control
     * @return  A return code, one of IModeController's CONTROL_XXX constants 
     * @throws DevFailed
     */
    public int [] controlMode ( Mode mode , ArchivingAttribute attr ) throws DevFailed;
}
