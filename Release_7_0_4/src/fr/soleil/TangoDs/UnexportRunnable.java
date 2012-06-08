/*	Synchrotron Soleil 
 *  
 *   File          :  UnexportRunnable.java
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
 *   Log: UnexportRunnable.java,v 
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
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;

class UnexportRunnable extends ShutdownRunnable
{
    private boolean hasRun = false; 
    
    public void run() 
    {
        if ( this.deviceToUnexport == null )
        {
            return;
        }
        if ( this.hasRun )
        {
            return;
        }
        
        try 
        {
            Database db = ApiUtil.get_db_obj ();
            String devname = this.deviceToUnexport.get_name ();
            System.out.println( "UnexportRunnable/unexporting device: " + devname );
            db.unexport_device ( devname );
            System.out.println( "UnexportRunnable/unexporting successfull" );
        } 
        catch (DevFailed e) 
        {
            e.printStackTrace();
        }
        finally
        {
            this.hasRun = true;
        }
    }
}
