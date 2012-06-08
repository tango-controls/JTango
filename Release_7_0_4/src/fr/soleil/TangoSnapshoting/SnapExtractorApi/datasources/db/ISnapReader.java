/*	Synchrotron Soleil 
 *  
 *   File          :  IArchivedAttributesReader.java
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
 *   Log: IArchivedAttributesReader.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.datasources.db;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;

/**
 * Contains data access methods used by for extracting snapshots
 * @author CLAISSE 
 */
public interface ISnapReader 
{
    /**
     * Opens a connection to the Snap database
     * @param string2 
     * @param string 
     * @throws DevFailed
     */
    public void openConnection (String string, String string2) throws DevFailed;
    /**
     * Closes the connection to the Snap database
     */
    public void closeConnection ();
    /**
     * Extracts a given snapshot's data
     * @param id The ID of the snapshot to extract
     * @return The snapshot's data
     * @throws DevFailed
     */
    public SnapAttributeExtract [] getSnap ( int id ) throws DevFailed;
    /**
     * Returns the list of snapshots associated to a given context
     * @param contextId The context's ID
     * @return A DevVarLongStringArray list where the Long elements are the snapshots' IDs, and the String elements are desciptions of each snapshot (typically, the concatenation of the snapshot's date and comment fields)  
     * @throws DevFailed
     */
    public DevVarLongStringArray getSnapshotsForContext ( int contextId ) throws DevFailed;
}
