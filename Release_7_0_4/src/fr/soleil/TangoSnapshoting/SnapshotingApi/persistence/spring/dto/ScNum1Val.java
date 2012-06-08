/*	Synchrotron Soleil 
 *  
 *   File          :  ScNum1Val.java
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
 *   Log: ScNum1Val.java,v 
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

public class ScNum1Val extends Val
{
    private double value;
    
    public ScNum1Val ()
    {
       
    }

    public ScNum1Val(AnyAttribute attribute, SnapshotPersistenceContext context) 
    {
        super ( attribute , context ) ;
        double [] val = attribute.getConvertedNumericValuesTable ();
        this.value = val [0];
    }

    /**
     * @return the value
     */
    public double getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
}
