/*	Synchrotron Soleil 
 *  
 *   File          :  ScNum2Val.java
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
 *   Log: ScNum2Val.java,v 
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

public class ScNum2Val extends Val
{
    private double readValue;
    private double writeValue;
    
    public ScNum2Val ()
    {
       
    }

    public ScNum2Val(AnyAttribute attribute, SnapshotPersistenceContext context) 
    {
        super ( attribute , context ) ;
        
        double [] val = attribute.getConvertedNumericValuesTable ();
        this.readValue = val [0];
        this.writeValue = val [1];
    }
    
    /**
     * @return the readValue
     */
    public double getReadValue() {
        return this.readValue;
    }

    /**
     * @param readValue the readValue to set
     */
    public void setReadValue(double readValue) {
        this.readValue = readValue;
    }

    /**
     * @return the writeValue
     */
    public double getWriteValue() {
        return this.writeValue;
    }

    /**
     * @param writeValue the writeValue to set
     */
    public void setWriteValue(double writeValue) {
        this.writeValue = writeValue;
    }

    public void trace() 
    {
        System.out.println();
        System.out.println("ScNum2Val/trace VVVVVVVVVVVVVVV");
        super.getCompositeId().trace ();    
        System.out.println("ScNum2Val/readValue/"+readValue);
        System.out.println("ScNum2Val/writeValue/"+writeValue);
        System.out.println("ScNum2Val/trace ^^^^^^^^^^^^^^");
        System.out.println();
    }
}
