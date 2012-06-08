/*	Synchrotron Soleil 
 *  
 *   File          :  Warner.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  17 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Warner.java,v 
 *
 */
 /*
 * Created on 17 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.devicelink;

/**
 * Models the API classes, seen from the device's point of view.
 * This is so that later, the API classes know what device to warn of events. 
 * @author CLAISSE 
 */
public interface Warner 
{
    /**
     * Sets the API's reference device
     * @param watcher The warnable device
     */
    public void setWatcherToWarn(Warnable watcher);
    /**
     * Returns the API's reference device.
     * @return The API's reference device
     */
    public Warnable getWatcherToWarn ();
}
