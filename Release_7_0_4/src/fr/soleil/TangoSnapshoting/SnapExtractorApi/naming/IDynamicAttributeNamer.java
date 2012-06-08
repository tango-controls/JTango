/*	Synchrotron Soleil 
 *  
 *   File          :  IDynamicAttributeNamer.java
 *  
 *   Project       :  snapExtractorAPI
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  25 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: IDynamicAttributeNamer.java,v 
 *
 */
 /*
 * Created on 25 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.naming;

import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;

/**
 * Deals with the naming of dynamic attributes one needs to create for the extraction of snapshots.
 * Since the dynamic attribute used for extraction are read only, one must create two dynamic attributes for each R/W attribute.  
 * @author CLAISSE 
 */
public interface IDynamicAttributeNamer 
{
    /**
     * Makes a name for the new dynamic attribute
     * @param realAttribute The extracted attribute
     * @param id A counter maintained by the extraction device, incremented at each creation of a dynamic attribute
     * (or pair of dynamic attributes in the case of a R/W attribute) 
     * @param isReadValue True for the read value of an extracted attribute, false for the write value
     * @return The name of the new dynamic attribute
     */
    public String getName ( SnapAttributeExtract realAttribute , int id , boolean isReadValue );
}
