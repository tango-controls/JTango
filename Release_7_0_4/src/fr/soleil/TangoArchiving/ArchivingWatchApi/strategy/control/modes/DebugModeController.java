/*	Synchrotron Soleil 
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
 *   Revision:  					Author:  
 *   Date: 							State:  
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

import fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;

/**
 * A random implementation, can be used for testing where one needs KO and OK attributes
 * @author CLAISSE 
 */
public class DebugModeController extends HdbModeControllerAdapter 
{
    DebugModeController() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.mode.controller.IModeController#controlPeriodicMode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode, archwatch.dto.Attribute)
     */
    public int controlPeriodicMode(ModePeriode modeP, ArchivingAttribute attr) 
    {
        double rndm = Math.random (); 
        
        if ( rndm > 0.5 )
        {
            return IModeController.CONTROL_OK;
        }
        else
        {
            return IModeController.CONTROL_KO;    
        }
    }

}
