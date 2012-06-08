/*	Synchrotron Soleil 
 *  
 *   File          :  Im2Val.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  7 mars 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: Im2Val.java,v 
 *
 */
 /*
 * Created on 7 mars 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.context.SnapshotPersistenceContext;
import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.persistance.AnyAttribute;

public class Im2Val extends ImVal
{
    private String readValue;
    private String writeValue;
    
    public Im2Val ()
    {
       
    }

    public Im2Val(AnyAttribute attribute, SnapshotPersistenceContext context) 
    {
        super ( attribute , context ) ;
    }
    
    /**
     * @return the readValue
     */
    public String getReadValue() {
        return this.readValue;
    }

    /**
     * @param readValue the readValue to set
     */
    public void setReadValue(String readValue) {
        this.readValue = readValue;
    }

    /**
     * @return the writeValue
     */
    public String getWriteValue() {
        return this.writeValue;
    }

    /**
     * @param writeValue the writeValue to set
     */
    public void setWriteValue(String writeValue) {
        this.writeValue = writeValue;
    }
}
