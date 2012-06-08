/*	Synchrotron Soleil 
 *  
 *   File          :  BufferedDBReader.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  23 oct. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: BufferedDBReader.java,v 
 *
 */
 /*
 * Created on 23 oct. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db;

import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;

public abstract class BufferedDBReader extends DBReaderAdapter 
{
    private Hashtable nameToIsScalarOrSpectrum;
    
    public BufferedDBReader() 
    {
        super();
        this.nameToIsScalarOrSpectrum = new Hashtable ();
    }

    public boolean isScalarOrSpectrum ( String completeName ) throws DevFailed
    {
        Boolean bufferedResult = (Boolean) this.nameToIsScalarOrSpectrum.get ( completeName );   
        
        if ( bufferedResult == null )
        {
            bufferedResult = new Boolean ( super.isScalarOrSpectrum ( completeName ) );
            this.nameToIsScalarOrSpectrum.put ( completeName , bufferedResult );
        }
        
        return bufferedResult.booleanValue ();
    }
}
