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

import fr.esrf.Tango.DevFailed;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.DBReaderFactory;
import fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db.IDBReader;
import fr.soleil.TangoArchiving.ArchivingWatchApi.devicelink.Warnable;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ModeData;
import fr.soleil.TangoArchiving.ArchivingWatchApi.tools.Tools;

/**
 * A strategy that picks only scalar and spectrum attributes.
 * @author CLAISSE 
 */
public class ScalarsAndSpectrumsOnlyChoosingStrategy implements IChoosingStrategy 
{
    private int numberOfAttributesToControl;
    
    ScalarsAndSpectrumsOnlyChoosingStrategy() 
    {
        super();
    }

    /* (non-Javadoc)
     * @see archwatch.strategy.IAttributesChoosingStrategy#chooseAttributesToControl(java.util.Hashtable)
     */
    public Hashtable filterAttributesToControl(Hashtable allAttributes) throws DevFailed
    {
        if ( allAttributes == null )
        {
            Tools.trace ( "ScalarsAndSpectrumsOnlyChoosingStrategy/chooseAttributesToControl/allAttributes == null !!!!" , Warnable.LOG_LEVEL_WARN );
            return null;
        }
        
        IDBReader reader = DBReaderFactory.getCurrentImpl (); 
        
        Hashtable ret = new Hashtable ();
        Enumeration keys = allAttributes.keys ();
        while ( keys.hasMoreElements () )
        {
            String key = (String) keys.nextElement ();  
            ModeData val = (ModeData) allAttributes.get ( key );
            
            boolean isScalarOrSpectrum = false; 
            try
            {
                isScalarOrSpectrum = reader.isScalarOrSpectrum ( key );    
            }
            catch ( Exception e )
            {
                Tools.trace ( "ScalarsAndSpectrumsOnlyChoosingStrategy/chooseAttributesToControl/failed during call to isScalarOrSpectrum ( "+key+" )" , e , Warnable.LOG_LEVEL_WARN ); 
            }
            
            if ( isScalarOrSpectrum )
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
    public int getNumberOfAttributesToControl() 
    {
        return numberOfAttributesToControl;
    }
}
