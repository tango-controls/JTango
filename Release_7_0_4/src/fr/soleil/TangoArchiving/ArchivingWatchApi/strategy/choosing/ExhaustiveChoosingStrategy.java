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

import java.util.Enumeration;
import java.util.Hashtable;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ModeData;

/**
 * A basic strategy that picks only hard-coded attributes. 
 * @author CLAISSE 
 */
public class ExhaustiveChoosingStrategy implements IChoosingStrategy 
{
    private int numberOfAttributesToControl;
    
    ExhaustiveChoosingStrategy() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.IAttributesChoosingStrategy#chooseAttributesToControl(java.util.Hashtable)
     */
    public Hashtable filterAttributesToControl(Hashtable allAttributes) 
    {
        Hashtable ret = new Hashtable ();
        Enumeration keys = allAttributes.keys ();
        while ( keys.hasMoreElements () )
        {
            String key = (String) keys.nextElement ();  
            ModeData val = (ModeData) allAttributes.get ( key );
            
            if 
            ( 
                    key.equals ( "tango/tangotest/2/double_scalar_rww" )  
                    ||  
                    key.equals ( "tango/tangotest/1/long_scalar" )
                    ||
                    key.equals ( "tango/tangotest/2/short_scalar_rww" )
            )
            {
                ret.put ( key , val );
            }
        }
        
        this.numberOfAttributesToControl = ret.size ();
        return ret;
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
