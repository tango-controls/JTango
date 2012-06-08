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
 * A strategy that picks attributes randomly. Can be used for testing. 
 * @author CLAISSE 
 */
public class RandomChoosingStrategy implements IChoosingStrategy 
{
    private int numberOfAttributesToControl;
    
    RandomChoosingStrategy() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.IAttributesChoosingStrategy#chooseAttributesToControl(java.util.Hashtable)
     */
    public Hashtable filterAttributesToControl(Hashtable allAttributes) 
    {
        this.numberOfAttributesToControl = 0;
        
        return null;
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
