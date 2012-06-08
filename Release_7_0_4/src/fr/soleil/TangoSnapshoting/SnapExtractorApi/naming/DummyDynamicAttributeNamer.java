/*	Synchrotron Soleil 
 *  
 *   File          :  DummyDynamicAttributeNamer.java
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
 *   Log: DummyDynamicAttributeNamer.java,v 
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
 * A dummy implementation for testing purposes
 * @author CLAISSE 
 */
public class DummyDynamicAttributeNamer implements IDynamicAttributeNamer {

    DummyDynamicAttributeNamer() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see snapextractor.api.naming.IDynamicAttributeNamer#getName(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract, int, boolean)
     */
    public String getName(SnapAttributeExtract realAttribute, int id,boolean isReadValue) 
    {
        //return  "att_000" + id;            //OK
        //return  "att_CLAxxxda_000" + id;   //KO
        //return  "att1_000" + id;            //OK
        //return  "att1CLAxxxxxx_000" + id;  //KO
        //return  "attd_000" + id;            //OK
        //return  "attD_000" + id;            //KO
        //return  "attsdsdsdsdsqzaaz_000" + id;            //OK
        /*String s = new DefaultDynamicAttributeNamer ().getName (realAttribute,id,isReadValue);
        return s.toLowerCase ();*/  //OK
        return  "att_000" + id;            //OK
    }

}
