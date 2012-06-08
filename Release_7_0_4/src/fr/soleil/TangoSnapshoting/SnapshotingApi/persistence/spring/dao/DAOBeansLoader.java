/*	Synchrotron Soleil 
 *  
 *   File          :  DAOBeansLoader.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  6 avr. 07 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: DAOBeansLoader.java,v 
 *
 */
 /*
 * Created on 6 avr. 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp2Val;

public interface DAOBeansLoader 
{
    public String getResourceName ();
    
    public ValDAO<ScNum1Val> getScNum1ValDAO ();
    public ValDAO<ScNum2Val> getScNum2ValDAO ();
    public ValDAO<ScStr1Val> getScStr1ValDAO ();
    public ValDAO<ScStr2Val> getScStr2ValDAO ();
    public ValDAO<Sp1Val> getSp1ValDAO ();
    public ValDAO<Sp2Val> getSp2ValDAO ();
    public ValDAO<Im1Val> getIm1ValDAO ();
    public ValDAO<Im2Val> getIm2ValDAO ();
}
