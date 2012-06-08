/*	Synchrotron Soleil 
 *  
 *   File          :  DeviceImplWithShutdownHook.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  13 nov. 06 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DeviceImplWithShutdownHook.java,v 
 *
 */
 /*
 * Created on 13 nov. 06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoDs;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.DeviceClass;
import fr.esrf.TangoDs.DeviceImpl;

public abstract class DeviceImplWithShutdownHook extends DeviceImpl 
{
    public DeviceImplWithShutdownHook(DeviceClass cl,String d_name) throws DevFailed 
    {
        super(cl, d_name);
    }
    public DeviceImplWithShutdownHook(DeviceClass cl,String d_name,String desc) throws DevFailed
    {
        super(cl, d_name, desc);
    }
    
    public DeviceImplWithShutdownHook ( DeviceClass cl,String d_name,String de,DevState st,String sta ) throws DevFailed
    {
        super(cl, d_name, de, st, sta);
    }
    
    private class ShutdownThread extends Thread 
    {
        public ShutdownThread ( Runnable runnable )
        {
            super ( runnable , DeviceImplWithShutdownHook.super.device_name + ".ShutdownThread" );
        }
    }
    
    public void addShutdownHook ( Runnable runnable )
    {
        Thread hook = new ShutdownThread ( runnable );
        Runtime.getRuntime ().addShutdownHook ( hook ); 
    }
    
    public abstract void init_device() throws DevFailed;
}
