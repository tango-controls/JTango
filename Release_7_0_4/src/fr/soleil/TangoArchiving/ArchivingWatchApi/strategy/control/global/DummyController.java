/*	Synchrotron Soleil 
 *  
 *   File          :  BasicController.java
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
 *   Log: BasicController.java,v 
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

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResult;

/**
 * Dummy implementation
 * @author CLAISSE 
 */
public class DummyController implements IController 
{
    DummyController() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#useControlResult(archwatch.dto.ControlResult)
     */
    public int useControlResult(ControlResult control) 
    {
        return IController.NO_ACTION_NEEDED;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#serAttributesToControl(java.util.Hashtable)
     */
    public void setAttributesToControl(Hashtable attributesToControl) 
    {
        
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#control(boolean)
     */
    public ControlResult control(boolean doArchiverDiagnosis)  {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.control.global.IController#doSomethingAboutActionResult(int)
     */
    public void doSomethingAboutActionResult(int actionResult) {
        // TODO Auto-generated method stub
        
    }

}
