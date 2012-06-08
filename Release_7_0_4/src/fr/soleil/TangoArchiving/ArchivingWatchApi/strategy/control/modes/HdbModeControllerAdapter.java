/*	Synchrotron Soleil 
 *  
 *   File          :  ModeControllerAdapter.java
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
 *   Log: ModeControllerAdapter.java,v 
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
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeSeuil;

/**
 * A minimal implementation that returns CONTROL_NOT_DONE for every mode control
 * @author CLAISSE 
 */
public class HdbModeControllerAdapter implements IModeController 
{
    public HdbModeControllerAdapter() 
    {
        super();
    }
    
    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode, archwatch.dto.Attribute)
     */
    protected int controlPeriodicMode(ModePeriode modeP, ArchivingAttribute attr) throws DevFailed 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu, archwatch.dto.Attribute)
     */
    protected int controlAbsoluteMode(ModeAbsolu modeA, ArchivingAttribute attr) 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif, archwatch.dto.Attribute)
     */
    protected int controlRelativeMode(ModeRelatif modeR, ArchivingAttribute attr) 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeSeuil, archwatch.dto.Attribute)
     */
    protected int controlThresholdMode(ModeSeuil modeT, ArchivingAttribute attr) 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference, archwatch.dto.Attribute)
     */
    protected int controlDifferenceMode(ModeDifference modeD, ArchivingAttribute attr) 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul, archwatch.dto.Attribute)
     */
    protected int controlCalculationMode(ModeCalcul modeC, ArchivingAttribute attr) 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne, archwatch.dto.Attribute)
     */
    protected int controlExternalMode(ModeExterne modeE, ArchivingAttribute attr) 
    {
        return IModeController.CONTROL_NOT_DONE;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.modes.IModeController#controlMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode, archwatch.dto.Attribute)
     */
    public int [] controlMode ( Mode mode, ArchivingAttribute attr ) throws DevFailed 
    {
        int [] ret = new int [ IModeController.NUMBER_OF_MODES_TO_CONTROL ];
        if ( mode == null )
        {
            return ret;
        }
        
        ret [ IModeController.MODE_P_POSITION ] = this.controlPeriodicMode ( mode.getModeP () , attr );
        ret [ IModeController.MODE_A_POSITION ] = this.controlAbsoluteMode ( mode.getModeA () , attr );
        ret [ IModeController.MODE_R_POSITION ] = this.controlRelativeMode ( mode.getModeR () , attr );
        ret [ IModeController.MODE_T_POSITION ] = this.controlThresholdMode ( mode.getModeT () , attr );
        ret [ IModeController.MODE_D_POSITION ] = this.controlDifferenceMode ( mode.getModeD () , attr );
        ret [ IModeController.MODE_C_POSITION ] = this.controlCalculationMode ( mode.getModeC () , attr );
        ret [ IModeController.MODE_E_POSITION ] = this.controlExternalMode ( mode.getModeE () , attr );
        
        return ret;
    }

}
