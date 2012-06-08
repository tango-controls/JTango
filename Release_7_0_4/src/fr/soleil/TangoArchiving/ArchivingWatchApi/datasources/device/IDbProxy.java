/*	Synchrotron Soleil 
 *  
 *   File          :  IDbProxy.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  27 févr. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: IDbProxy.java,v 
 *
 */
 /*
 * Created on 27 févr. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.device;

import java.sql.Timestamp;

import fr.esrf.Tango.DevFailed;

/**
 * Models a DbProxy object, which is used to intercept two kinds of events:
 * <UL>
 *  <LI> The archiver will attempt to insert a new value 
 *  <LI> The attempt has been a success
 * </UL>
 * @author CLAISSE 
 */
public interface IDbProxy 
{
    /**
     * Returns the time of the most recent insert attempt.
     * @param completeName The attribute's complete name
     * @return The time of the most recent insert attempt
     * @throws DevFailed
     */
    public Timestamp getLastInsertRequest ( String completeName ) throws DevFailed;
    /**
     * Returns the time of the most recent successful insert.
     * @param completeName The attribute's complete name
     * @return The time of the most recent successful insert
     * @throws DevFailed
     */
    public Timestamp getLastInsert ( String completeName ) throws DevFailed;
}
