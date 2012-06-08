/*	Synchrotron Soleil 
 *  
 *   File          :  DummySnapReader.java
 *  
 *   Project       :  snapExtractorAPI
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  23 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DummySnapReader.java,v 
 *
 */
 /*
 * Created on 23 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.datasources.db;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;

/**
 * A dummy implementation
 * @author CLAISSE 
 */
public class DummySnapReader implements ISnapReader 
{
    DummySnapReader() {
        super();
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see snapextractor.api.datasources.db.ISnapReader#openConnection()
     */
    public void openConnection() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see snapextractor.api.datasources.db.ISnapReader#closeConnection()
     */
    public void closeConnection() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see snapextractor.api.datasources.db.ISnapReader#getSnap(java.lang.String[])
     */
    public SnapAttributeExtract[] getSnap(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see snapextractor.api.datasources.db.ISnapReader#getSnapshotsForContext(int)
     */
    public DevVarLongStringArray getSnapshotsForContext(int contextId) {
        // TODO Auto-generated method stub
        return null;
    }

    public void openConnection(String string, String string2) throws DevFailed {
        // TODO Auto-generated method stub
        
    }

}
