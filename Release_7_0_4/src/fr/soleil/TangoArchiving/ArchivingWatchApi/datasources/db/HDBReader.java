/*  Synchrotron Soleil 
 *  
 *   File          :  IA.java
 *  
 *   Project       :  archiving_watcher
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  28 nov. 2005 
 *  
 *   Revision:                      Author:  
 *   Date:                          State:  
 *  
 *   Log: IA.java,v 
 *
 */
 /*
 * Created on 28 nov. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db;

/**
 * An implementation that really loads data from the HDB physical database
 * @author CLAISSE 
 */
public class HDBReader extends BufferedDBReader 
{
    HDBReader() 
    {
        super();
    }

    protected boolean isHistoric() 
    {
        return true;
    }
}
