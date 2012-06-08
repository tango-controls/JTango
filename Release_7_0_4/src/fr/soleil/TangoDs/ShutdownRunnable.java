/*	Synchrotron Soleil 
 *  
 *   File          :  ShutdownRunnable.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  14 nov. 06 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ShutdownRunnable.java,v 
 *
 */
 /*
 * Created on 14 nov. 06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoDs;

import fr.esrf.TangoDs.DeviceImpl;

public abstract class ShutdownRunnable implements Runnable
{
    protected DeviceImpl deviceToUnexport;
    
    public void setDevice( DeviceImpl  _deviceToUnexport) 
    {
        this.deviceToUnexport = _deviceToUnexport;
    }
}
