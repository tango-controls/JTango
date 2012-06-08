/*	Synchrotron Soleil 
 *  
 *   File          :  DummyArchivedAttributesReader.java
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
 *   Log: DummyArchivedAttributesReader.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db;

import java.sql.Timestamp;
import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DateHeure;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ModeData;


/**
 * Dummy implementation
 * @author CLAISSE 
 */
public class DummyDBReader implements IDBReader 
{   
    DummyDBReader() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.in.IArchivedAttributesReader#getArchivedAttributes()
     */
    public Hashtable getArchivedAttributes() 
    {
        return null;
    }

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#getRecordCount(java.lang.String)
     */
    public int getRecordCount(String completeName)  {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#getTimeOfLastInsert(java.lang.String)
     */
    public Timestamp getTimeOfLastInsert(String completeName) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#now()
     */
    public Timestamp now() 
    {
        DateHeure dh = new DateHeure ();
        long time = dh.toDate().getTime ();
        Timestamp ret = new Timestamp ( time );
        return ret;
    }

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#getRecordCount(java.lang.String, int)
     */
    public int getRecordCount(String completeName, int period)  {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see archwatch.datasource.in.IArchivedAttributesReader#isScalar(java.lang.String)
     */
    public boolean isScalar(String completeName)  {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#getArchiverLoad(java.lang.String, int)
     */
    public int getArchiverLoad(String archiverName, int loadType)  {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#isAlive(java.lang.String)
     */
    public boolean isAlive(String archiverName) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#getStatus(java.lang.String)
     */
    public String getDeviceStatus(String archiverName)  {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IArchivedAttributesReader#closeConnection()
     */
    public void closeConnection() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see archwatch.datasources.db.IHDBReader#openConnection()
     */
    public void openConnectionIfNecessary(boolean forceOpening)  {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IHDBReader#isScalarOrSpectrum(java.lang.String)
     */
    public boolean isScalarOrSpectrum(String completeName) throws DevFailed {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isDBConnectionAlive() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isLastValueNull(String completeName) throws DevFailed {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader#getModeDataForAttribute(java.lang.String)
     */
    public ModeData getModeDataForAttribute(String completeName) throws DevFailed {
        // TODO Auto-generated method stub
        return null;
    }

    public Mode getModeForAttribute(String completeName) throws DevFailed {
        // TODO Auto-generated method stub
        return null;
    }

}
