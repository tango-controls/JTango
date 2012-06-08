/*	Synchrotron Soleil 
 *  
 *   File          :  ValDAO.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  13 mars 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: ValDAO.java,v 
 *
 */
 /*
 * Created on 13 mars 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Val;

public interface ValDAO <V extends Val> 
{
    //Defined as executed within a default hibernate transaction in beans.xml
    public V create ( V line );
    //Defined as executed within a read-only transaction in beans.xml
    public V findByKey ( CompositeId compositeId );
}
