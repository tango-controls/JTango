/*	Synchrotron Soleil 
 *  
 *   File          :  DoNothingRunnable.java
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
 *   Log: DoNothingRunnable.java,v 
 *
 */
 /*
 * Created on 14 nov. 06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoDs;

class DoNothingRunnable extends ShutdownRunnable
{
    public void run() 
    {
        System.out.println( "DoNothingRunnable/doing nothing" );
    }
}
