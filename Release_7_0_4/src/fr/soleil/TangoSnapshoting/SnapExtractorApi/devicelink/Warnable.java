/*	Synchrotron Soleil 
 *  
 *   File          :  Warnable.java
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
 *   Log: Warnable.java,v 
 *
 */
 /*
 * Created on 17 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.devicelink;

import fr.esrf.Tango.DevFailed;

/**
 * Models a device that can be warned by the API classes.
 * Typically, one wants to use the ArchivingWatcher device's Tango methods of logging, 
 * state setting and warning, but from the API classes.
 * @author CLAISSE 
 */
public interface Warnable 
{
    /**
     * For debug messages
     */
    public static final int LOG_LEVEL_DEBUG = 9;
    /**
     * For info messages
     */
    public static final int LOG_LEVEL_INFO = 7;
    /**
     * For warning messages
     */
    public static final int LOG_LEVEL_WARN = 5;
    /**
     * For error messages
     */
    public static final int LOG_LEVEL_ERROR = 3;
    /**
     * For fatal error messages
     */
    public static final int LOG_LEVEL_FATAL = 1;
    
    /**
     * Warns the device an Init event has been detected 
     */
    public void warnInit();
    /**
     * Warns the device an On event has been detected 
     */
    public void warnOn ();
    /**
     * Warns the device an Off event has been detected 
     */
    public void warnOff();
    /**
     * Warns the device an Fault event has been detected 
     */
    public void warnFault ();
    /**
     * Warns the device an Alarm event has been detected 
     */
    public void warnAlarm ();
    
    /**
     * Directly puts the device in the specified status
     * @param status The new status
     */
    public void setStatus ( String status );
    
    /**
     * Logs a message using the device's specific methods
     * @param msg The message to log
     * @param level The criticity level
     * @throws DevFailed
     */
    public void trace ( String msg , int level ) throws DevFailed;
    /**
     * Logs a message using the device's specific methods
     * @param msg The message to log
     * @param level The criticity level
     * @param t The Throwable to og
     * @throws DevFailed
     */
    public void trace ( String msg , Throwable t , int level ) throws DevFailed;
}
