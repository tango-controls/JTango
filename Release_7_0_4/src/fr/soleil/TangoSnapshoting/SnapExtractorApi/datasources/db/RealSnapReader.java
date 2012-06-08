/*	Synchrotron Soleil 
 *  
 *   File          :  RealSnapReader.java
 *  
 *   Project       :  snapExtractorAPI
 *  
 *   Description   :  
 *  
 *   Author        :  CLAISSE
 *  
 *   Original      :  23 janv. 2006 
 *  
 *   Revision:  					Author:  
 *   Date: 							State:  
 *  
 *   Log: RealSnapReader.java,v 
 *
 */
 /*
 * Created on 23 janv. 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fr.soleil.TangoSnapshoting.SnapExtractorApi.datasources.db;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.soleil.TangoSnapshoting.SnapExtractorApi.tools.Tools;
import fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager;
import fr.soleil.TangoSnapshoting.SnapManagerApi.SnapManagerImpl;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.Condition;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.Criterions;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShotLight;


/**
 * An implementation that loads data from the Snap Database
 * @author CLAISSE 
 */
public class RealSnapReader implements ISnapReader 
{
    private ISnapManager manager;
    private boolean isReady = false;

    RealSnapReader() 
    {
        super();
    }

    public void openConnection (String snapUser,String snapPassword) throws DevFailed
    {
        try
        {
	        if ( !this.isReady )
	        {
	            this.manager = new SnapManagerImpl ( snapUser,snapPassword );
	            this.isReady = true;
	        }
        }
	    catch ( Exception e )
	    {
	        Tools.throwDevFailed ( e );
	    }
    }
    
    public void closeConnection ()
    {
        this.isReady = false;
    }
    
    /* (non-Javadoc)
     * @see snapextractor.api.datasources.db.ISnapReader#getSnap(java.lang.String[])
     */
    public SnapAttributeExtract[] getSnap ( int id ) throws DevFailed  
    {
        Condition condition = new Condition( GlobalConst.TAB_SNAP[ 0 ] , "=" , String.valueOf( id ) );
        Criterions searchCriterions = new Criterions();
        searchCriterions.addCondition( condition );
     
        try
        {
            SnapShotLight[] snapshots = this.manager.findSnapshots( searchCriterions );
            if ( snapshots == null || snapshots.length == 0 )
            {
                return null;
            }
            SnapShotLight snapshotLight = snapshots[ 0 ];
         
            SnapAttributeExtract[] sae = this.manager.findSnapshotAttributes( snapshotLight );
            if ( sae == null || sae.length == 0 )
            {
                return null;
            }
            return sae;
        }
	    catch ( Exception e )
	    {
	        Tools.throwDevFailed ( e );
	    }
        return null;
    }



    /* (non-Javadoc)
     * @see snapextractor.api.datasources.db.ISnapReader#getSnapshotsForContext(int)
     */
    public DevVarLongStringArray getSnapshotsForContext(int contextId) throws DevFailed 
    {
        Criterions searchCriterions = new Criterions ();
        searchCriterions.addCondition( new Condition( GlobalConst.TAB_SNAP[ 1 ] , GlobalConst.OP_EQUALS , "" + contextId ) );

        SnapShotLight [] snapshots = null;
        try
        {
            snapshots = this.manager.findSnapshots( searchCriterions );
        }
	    catch ( Exception e )
	    {
	        Tools.throwDevFailed ( e );
	    }
	    if ( snapshots == null || snapshots.length == 0 )
	    {
	        return null;
	    }
	    int numberOfSnapshots = snapshots.length;
	    
	    DevVarLongStringArray ret = new DevVarLongStringArray ();
	    int [] lvalue = new int [ numberOfSnapshots ];
	    java.lang.String [] svalue = new java.lang.String [ numberOfSnapshots ]; 
	    for ( int i = 0; i < numberOfSnapshots ; i ++ )
	    {
	        SnapShotLight currentSnapshot = snapshots [ i ];
	        lvalue [ i ] = currentSnapshot.getId_snap ();
	        svalue [ i ] = currentSnapshot.getSnap_date () + " , " + currentSnapshot.getComment ();
	    }
	    ret.lvalue = lvalue;
	    ret.svalue = svalue;
	    
        return ret;
    }

    
}
