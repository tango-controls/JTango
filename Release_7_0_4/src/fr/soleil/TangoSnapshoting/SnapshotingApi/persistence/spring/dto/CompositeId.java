/*	Synchrotron Soleil 
 *  
 *   File          :  compositeId.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  9 mars 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: compositeId.java,v 
 *
 */
 /*
 * Created on 9 mars 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto;

import java.io.Serializable;

public class CompositeId implements Serializable
{
    private int idSnap;
    private int idAtt;
    
    public CompositeId ()
    {
        
    }

    @Override
    public int hashCode()
    {
        //using prime numbers to reduce odds of common product for different key fields (the odds don't have to be 0, but performance benefits from low odds)
        return 7*idAtt+11*idSnap;
    }
    
    @Override
    public boolean equals (Object otherObject)
    {
        if ( otherObject == null )
        {
            return false;
        }
        
        if ( this == otherObject )
        {
            return true;
        }
        
        if ( this.getClass() != otherObject.getClass() )
        {
            return false;
        }
        
        CompositeId other = (CompositeId) otherObject;
        if ( other.idAtt != this.idAtt )
        {
            return false;
        }
        if ( other.idSnap != this.idSnap )
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * @return the idAtt
     */
    public int getIdAtt() {
        return this.idAtt;
    }
    /**
     * @param idAtt the idAtt to set
     */
    public void setIdAtt(int idAtt) {
        this.idAtt = idAtt;
    }
    /**
     * @return the idSnap
     */
    public int getIdSnap() {
        return this.idSnap;
    }
    /**
     * @param idSnap the idSnap to set
     */
    public void setIdSnap(int idSnap) {
        this.idSnap = idSnap;
    }

    public void trace() 
    {
        System.out.println(this+"/idSnap/"+idSnap+"/idAtt/"+idAtt);
    }
}
