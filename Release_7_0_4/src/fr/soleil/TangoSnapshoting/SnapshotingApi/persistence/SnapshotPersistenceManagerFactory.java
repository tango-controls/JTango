/*  Synchrotron Soleil 
 *  
 *   File          :  SnapshotPersistenceManagerFactory.java
 *  
 *   Project       :  javaapi
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  9 mars 07 
 *  
 *   Revision:                      Author:  
 *   Date:                          State:  
 *  
 *   Log: SnapshotPersistenceManagerFactory.java,v 
 *
 */
 /*
 * Created on 9 mars 07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence;



public class SnapshotPersistenceManagerFactory 
{
    private static SnapshotPersistenceManagerFactory instance;
    
    private String beansFileName;
    private SnapshotPersistenceManager manager;
    
    private SnapshotPersistenceManagerFactory(String _beansFileName) 
    {
        this.beansFileName = _beansFileName;
    }

    public static SnapshotPersistenceManagerFactory getInstance ( String _beansFileName )
    {
        if ( instance == null )
        {
            instance = new SnapshotPersistenceManagerFactory ( _beansFileName );
        }
        return instance;
    }
    
    public static SnapshotPersistenceManagerFactory getInstance ()
    {
        return getInstance ( null );
    }
    
    public SnapshotPersistenceManager getManager ()
    {
        if ( manager == null )
        {
            manager = new SpringSnapshotPersistenceManagerImpl ( this.beansFileName );
        }
        return manager;
    }
}
