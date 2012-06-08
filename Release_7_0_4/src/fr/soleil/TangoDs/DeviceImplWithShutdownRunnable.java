/*	Synchrotron Soleil 
 *  
 *   File          :  DeviceImplWithUnexportHook.java
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
 *   Log: DeviceImplWithUnexportHook.java,v 
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

public abstract class DeviceImplWithShutdownRunnable extends DeviceImplWithShutdownHook 
{
    public DeviceImplWithShutdownRunnable(DeviceClass cl, String d_name) throws DevFailed 
    {
        super(cl, d_name );
        this.addShutdownRunnable ();
    }

    public DeviceImplWithShutdownRunnable(DeviceClass cl, String d_name,String desc) throws DevFailed 
    {
        super(cl, d_name, desc);
        this.addShutdownRunnable ();
    }

    public DeviceImplWithShutdownRunnable(DeviceClass cl, String d_name, String de, DevState st, String sta) throws DevFailed 
    {
        super(cl, d_name, de, st, sta );
        this.addShutdownRunnable ();
    }
    
    private void addShutdownRunnable ()
    {
        ShutdownRunnable runnable = new UnexportRunnable ();
        //ShutdownRunnable runnable = new DoNothingRunnable ();
        runnable.setDevice ( this );
        super.addShutdownHook ( runnable );
    }
    
    public abstract void init_device() throws DevFailed;
}
