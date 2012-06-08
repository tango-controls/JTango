/*	Synchrotron Soleil 
 *  
 *   File          :  SnapshotPersistenceContext.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  6 mars 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: SnapshotPersistenceContext.java,v 
 *
 */
 /*
 * Created on 6 mars 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.context;

import java.util.Map;

import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.context.PersistenceContext;

public class SnapshotPersistenceContext extends PersistenceContext
{
    private int snapId;
    private Map<String, Integer> attributeIds;
    
    public SnapshotPersistenceContext ( int _snapId , Map<String, Integer> _attributeIds ) 
    {
        this.snapId = _snapId;
        this.attributeIds = _attributeIds;
    }

    /**
     * @return the attributeIds
     */
    public Map<String, Integer> getAttributeIds() {
        return this.attributeIds;
    }

    /**
     * @return the snapId
     */
    public int getSnapId() {
        return this.snapId;
    }
}
