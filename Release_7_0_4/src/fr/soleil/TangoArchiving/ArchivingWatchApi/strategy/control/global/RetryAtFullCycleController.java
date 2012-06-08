/*	Synchrotron Soleil 
 *  
 *   File          :  SecondChanceController.java
 *  
 *   Project       :  javaapi_project
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  3 févr. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: SecondChanceController.java,v 
 *
 */
 /*
 * Created on 3 févr. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.control.global;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;

/**
 * An implementation that calls a "RetryForAttributes" command on the detected KO attributes. 
 * @author CLAISSE 
 */
public class RetryAtFullCycleController extends DoActionAtFullCycleController 
{
    RetryAtFullCycleController() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#useControlResult(archwatch.dto.ControlResult)
     */
    public synchronized int useControlResult ( ControlResult control ) 
    {
        int basicActionResult = super.useControlResult ( control );
        if ( basicActionResult == IController.NO_ACTION_NEEDED )
        {
            return IController.NO_ACTION_NEEDED;
        }
        
        return ControllerAdapter.doRetry ( control );
    }
}
