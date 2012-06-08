/*	Synchrotron Soleil 
 *  
 *   File          :  TotalAttributesChoosingStrategy.java
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
 *   Log: TotalAttributesChoosingStrategy.java,v 
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

/**
 * A basic strategy that picks all attributes unconditionally. 
 * @author CLAISSE 
 */
public class TotalChoosingStrategy implements IChoosingStrategy 
{
    private int numberOfAttributesToControl;
    
    TotalChoosingStrategy() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.IAttributesChoosingStrategy#chooseAttributesToControl(java.util.Hashtable)
     */
    public Hashtable filterAttributesToControl(Hashtable allAttributes) 
    {
        this.numberOfAttributesToControl = allAttributes.size ();
        return allAttributes;
    }

    /**
     * @return Returns the numberOfAttributesToControl.
     */
    public int getNumberOfAttributesToControl() {
        return numberOfAttributesToControl;
    }
    
    public Hashtable getUndeterminedAttributes() 
    {
        return null;
    }
}
