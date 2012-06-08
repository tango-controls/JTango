/*	Synchrotron Soleil 
 *  
 *   File          :  IAttributesChoosingStrategy.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  28 nov. 2005 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: IAttributesChoosingStrategy.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.strategy.choosing;

import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;

/**
 * Filters which attributes recorded in HDB have to be controlled.
 * @author CLAISSE 
 */
public interface IChoosingStrategy 
{
    /**
     * @param allAttributes The whole set of archiving HDB attributes:
     * a Hashtable which keys are the attributes complete names, and which values are the associated ModeData. 
     * @return The filtered set, with the same structure
     * @throws DevFailed
     */
    public Hashtable filterAttributesToControl ( Hashtable allAttributes ) throws DevFailed;
    /**
     * 
     * @return The number of attributes that have to be controlled according to this filter 
     */
    public int getNumberOfAttributesToControl ();
}
