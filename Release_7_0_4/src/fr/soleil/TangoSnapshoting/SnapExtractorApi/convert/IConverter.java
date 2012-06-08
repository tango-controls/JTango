/*	Synchrotron Soleil 
 *  
 *   File          :  IConverter.java
 *  
 *   Project       :  snapExtractorAPI
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  24 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: IConverter.java,v 
 *
 */
 /*
 * Created on 24 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.convert;

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.DbData;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;

/**
 * Converts data from the format returned by the Database API to a format usable for creating dynamic attributes
 * @author CLAISSE 
 */
public interface IConverter 
{
    /**
     * @param currentExtract The data in its Database API format
     * @return The data in its usable format
     * @throws DevFailed
     */
    public DbData convert ( SnapAttributeExtract currentExtract ) throws DevFailed;
    /**
     * Converts raw Object[] input to a more palatable String[] output
     * @param objects The raw input
     * @param returnNullIfOneElementIsNull If true, if even one element of the input is null, the output will automatically be null
     * @return The converted output
     */
    public String[] castObjectTableToString ( Object[] objects , boolean returnNullIfOneElementIsNull );
}
