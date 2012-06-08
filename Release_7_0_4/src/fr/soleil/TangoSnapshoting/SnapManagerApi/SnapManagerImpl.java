//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapManagerImpl.
//						(Chinkumo Jean) - 30 juin 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.6  2007/03/14 15:44:34  ounsy
// user and password are no longer hard-coded into this class
//
// Revision 1.5  2006/01/27 13:04:44  ounsy
// organised imports
//
// Revision 1.4  2006/01/10 10:54:05  chinkumo
// A bug were corrected in the launchSnapshot method. (Thanks to R.Girardot)
// This bug concerns filters and dates.
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.2.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/08/19 14:48:37  chinkumo
// no message
//
// Revision 1.1.4.3.2.1  2005/08/11 08:19:29  chinkumo
// The 'SetEquipement' command and thus functionnality was added.
//
// Revision 1.1.4.3  2005/08/04 08:04:04  chinkumo
// The changes made into the ISnapManager class was reported here.
// (ISnapManager's constants were removed as they were already defined in the TangoSnapshoting.SnapshotingTools.Tools.GlobalConst class).
//
// Revision 1.1.4.2  2005/08/01 13:51:45  chinkumo
// Classes added for the support of the new graphical application (Bensikin).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapManagerApi;

import java.util.ArrayList;
import java.util.Hashtable;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoSnapshoting.SnapshotingApi.ConfigConst;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.Condition;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.Criterions;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.DateUtil;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeExtract;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeHeavy;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapAttributeLight;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapContext;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShot;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShotLight;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapshotingException;

/**
 * @author GARDA
 *         <p/>
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SnapManagerImpl implements ISnapManager
{

	private static boolean isConnected = false;
    private String user;
    private String pwd;
	//private static SnapManagerApi m_snapManager = null;

    public SnapManagerImpl ( String _user , String _pwd )
    {
        this.user = _user;
        this.pwd = _pwd;
        
        System.out.println("SnapManagerImpl/new/user/"+user+"/pwd/"+pwd);
    }

	/* (non-Javadoc)
	 * Description : Return an array of the attributes (SnapAttributeExtract) associated to the given snapshot
	 *
	 * @param snapshot the description (SnapShotLight) of the given snapshot
	 * @return an array of the attributes (SnapAttributeExtract) associated to the given snapshot
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#findSnapshotAttributes(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShotLight)
	 */
	public SnapAttributeExtract[] findSnapshotAttributes(SnapShotLight snapshot) throws SnapshotingException
	{
		if ( !isConnected )
			SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		// Gets the list of attributes associated to the given snapshot
		ArrayList arrayList = SnapManagerApi.getSnapshotAssociatedAttributes(snapshot);

		// Cast the attributes in SnapAttributesExtract
		SnapAttributeExtract[] snapAttributeExtract = new SnapAttributeExtract[ arrayList.size() ];
		for ( int i = 0 ; i < arrayList.size() ; i++ )
		{
			snapAttributeExtract[ i ] = ( SnapAttributeExtract ) arrayList.get(i);
		}

		return snapAttributeExtract;
	}

	/* (non-Javadoc)
	 * Description : Return the registered context (SnapContext) with its new context identifier
	 *
	 * @param the context (SnapContext) to register into the Snap database
	 * @param the options to register this context into the Snap database
	 * @return the registered context (SnapContext) with its new context identifier
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#saveContext(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapContext, java.util.Hashtable)
	 */
	public SnapContext saveContext(SnapContext context , Hashtable saveOptions) throws SnapshotingException
	{
		if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		ArrayList goodAttList = new ArrayList(32);
		// ArrayList badAttList = new ArrayList(32);

		// Gets the attributes associated to the context
		ArrayList theoricAttList = context.getAttributeList();

		// Registers the attributes into the Snap database
		for ( int i = 0 ; i < theoricAttList.size() ; i++ )
		{
			SnapAttributeLight snapAtt = ( SnapAttributeLight ) theoricAttList.get(i);
			String attributeName = snapAtt.getAttribute_complete_name().toString();
			try
			{
				// Verify that the choosen attribute is registered into the Snap database
				SnapManagerApi.insureRegitration(attributeName);
			}
			catch ( SnapshotingException e )
			{
				// Failed of the attribute's registration
				throw e;
			}
			// Gets the attribute identifier
			try
			{
				int att_id = SnapManagerApi.getAttId(attributeName.trim());
				snapAtt.setAttribute_id(att_id);
				goodAttList.add(snapAtt.getAttribute_complete_name());
			}
			catch ( SnapshotingException e )
			{
				throw e;
			}
		}
		/*
		if (!badAttList.isEmpty()) {
		    String message = "The following attributes have not been registered : \r\n";
		    for (int i = 0; i < badAttList.size(); i++) {
		        message = message + "\t" + badAttList.get(i) + "\r\n";
		    }
		    MyMessages.Error(this, message);
		}
		*/
		context.setAttributeList(goodAttList);
		//System.out.println("SnapContext avec liste d'attributs enregistes : " + context);
		// The context is registered into the database
		try
		{
			int context_id = SnapManagerApi.createContext2Manager(context);
			context.setId(context_id);
			// PB : Pas d'enregistrement de la relation id_context <-> id_att dans la table 'list'.
			//SnapManagerApi.createContext2Archiver(context);
		}
		catch ( SnapshotingException e )
		{
			String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_SNAPPATTERN_CREATION;
			String reason = "Failed while executing SnapManagerApi.createContext2Manager() method...";
			String desc = "";
			throw new SnapshotingException(message , reason , ErrSeverity.ERR , desc , "");
		}
		/*
		short res = SnapManagerApi.createContext2Manager(context);
		if (res == SnapManagerResult.ERROR_CONTEXTCREATION)
		{
		    //Util.out2.println("AddContext.jB_OK_actionPerformed");
		    MainPanel.jL_Error.setText("Error creating the new context");
		}
		else
		{
		    MainPanel.jL_Error.setText("New context registered...");
		}
		*/

		return context;
	}

	/* (non-Javadoc)
	 * Description : Return the registered snapshot (SnapShot) of the given context (SnapContext)
	 *
	 * @param the context (SnapContext) the context description
	 * @return the registered snapshot (SnapShot) of the given context (SnapContext)
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#launchSnapshot(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapContext)
	 */
    public SnapShot launchSnapshot(SnapContext context) throws SnapshotingException
    {
        if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

        // Registers the snapshot into the database
        long milliseconds = System.currentTimeMillis();
        int type = SnapManagerApi.getSnapDbType();
        String date = "";
        switch ( type )
        {
            case ConfigConst.BD_ORACLE:
                date = DateUtil.milliToString(milliseconds ,
                                              DateUtil.FR_DATE_PATTERN);
                break;
            case ConfigConst.BD_MYSQL:
            default :
                date = DateUtil.milliToString(milliseconds ,
                                              DateUtil.US_DATE_PATTERN);
        }
        
        int snapId = 0;
        try 
        {
            snapId = SnapManagerApi.launchSnap2Archiver(context.getId());
        } 
        catch (DevFailed e)// Failure of 'launchSnap2Archiver'. 
        {
            String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_LAUNCHINGSNAP;
            String reason = "Failed while executing SnapManagerApi.launchSnap2Archiver() method...";
            String desc = "";
            throw new SnapshotingException(message , reason , ErrSeverity.ERR , desc , "");
        }
        
        // Gets the snapshot identifier
        Condition[] condition = new Condition[ 1 ];
        condition[ 0 ] = new Condition(GlobalConst.TAB_SNAP[ 0 ] , GlobalConst.OP_EQUALS , snapId+"");
        Criterions criterions = new Criterions(condition);
        SnapShotLight[] snapShotLight = findSnapshots(criterions);

        if ( snapShotLight.length > 0 )
        {
            // Gets the attributes and theirs values
            SnapShotLight snap = snapShotLight[ 0 ];
            ArrayList arrayList = SnapManagerApi.getSnapshotAssociatedAttributes(snap);
            String[] tableau = {"" + context.getId(), "" + snap.getId_snap(), "" + snap.getSnap_date()};
            SnapShot snapShot = new SnapShot(tableau);
            snapShot.setAttribute_List(arrayList);
            return snapShot;
        }
        else
        {
            // Pas de snapshot correspondant.
            String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_RET_SNAP;
            String reason = "Failed while executing SnapManagerApi.getSnapshotAssociatedAttributes() method...";
            String desc = "";
            throw new SnapshotingException(message , reason , ErrSeverity.ERR , desc , "");
        }
    }
	/*public SnapShot launchSnapshot(SnapContext context) throws SnapshotingException
	{
		if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		// Registers the snapshot into the database
		long milliseconds = System.currentTimeMillis();
		int type = SnapManagerApi.getSnapDbType();
		String date = "";
		switch ( type )
		{
			case ConfigConst.BD_ORACLE:
				date = DateUtil.milliToString(milliseconds ,
				                              DateUtil.FR_DATE_PATTERN);
				break;
			case ConfigConst.BD_MYSQL:
			default :
				date = DateUtil.milliToString(milliseconds ,
				                              DateUtil.US_DATE_PATTERN);
		}
		short res = SnapManagerApi.launchSnap2Archiver(context.getId()); // Retourner l'id_snap?
		if ( res == SnapManagerResult.OK_SNAPLAUNCH )
		{
			// Gets the snapshot identifier
			Condition[] condition = new Condition[ 2 ];
			String context_id = "" + context.getId();
			condition[ 0 ] = new Condition(GlobalConst.TAB_SNAP[ 1 ] , GlobalConst.OP_EQUALS , context_id);
			condition[ 1 ] = new Condition(GlobalConst.TAB_SNAP[ 2 ] , GlobalConst.OP_GREATER_THAN , date);
			Criterions criterions = new Criterions(condition);
			SnapShotLight[] snapShotLight = findSnapshots(criterions);

			if ( snapShotLight.length > 0 )
			{
				// Gets the attributes and theirs values
				SnapShotLight snap = snapShotLight[ 0 ];
				ArrayList arrayList = SnapManagerApi.getSnapshotAssociatedAttributes(snap);
				String[] tableau = {"" + context.getId(), "" + snap.getId_snap(), "" + snap.getSnap_date()};
				SnapShot snapShot = new SnapShot(tableau);
				snapShot.setAttribute_List(arrayList);
				return snapShot;
			}
			else
			{
				// Pas de snapshot correspondant.
				String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_RET_SNAP;
				String reason = "Failed while executing SnapManagerApi.getSnapshotAssociatedAttributes() method...";
				String desc = "";
				throw new SnapshotingException(message , reason , ErrSeverity.ERR , desc , "");
			}
		}
		// Failure of 'launchSnap2Archiver'.
		String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.ERROR_LAUNCHINGSNAP;
		String reason = "Failed while executing SnapManagerApi.launchSnap2Archiver() method...";
		String desc = "";
		throw new SnapshotingException(message , reason , ErrSeverity.ERR , desc , "");
	}*/

	/* (non-Javadoc)
	 * Description : Return the updated snapshot (SnapShotLight)
	 *
	 * @param the snapshot to be updated
	 * @param the new comment
	 * @return the updated snapshot (SnapShotLight)
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#updateCommentOfSnapshot(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShotLight, java.lang.String)
	 */
	public SnapShotLight updateCommentOfSnapshot(SnapShotLight context , String comment) throws SnapshotingException
	{
		if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		// Updates the comment of the given snapshot
		int id_snap = context.getId_snap();
		SnapManagerApi.updateSnapComment(id_snap , comment);

		// Gets the updated snapshot
		Condition[] condition = new Condition[ 1 ];
		String id_snap_str = "" + id_snap;
		condition[ 0 ] = new Condition(GlobalConst.TAB_SNAP[ 0 ] , GlobalConst.OP_EQUALS , id_snap_str);
		Criterions criterions = new Criterions(condition);

		SnapShotLight[] updatedContexts = findSnapshots(criterions);
		return updatedContexts[ 0 ];
	}

	/* (non-Javadoc)
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#setEquipmentsWithSnapshot(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapShot)
	 */
	public void setEquipmentsWithSnapshot(SnapShot snapshot) throws SnapshotingException
	{
		SnapManagerApi.setEquipmentsWithSnapshot(snapshot);
	}

	/* (non-Javadoc)
	 * Description : Return an array of contexts (SnapContext) which subscribe to the given conditions (Criterions)
	 *
	 * @param the conditions relating to the contexts
	 * @return an array of contexts (SnapContext) which subscribe to the given conditions (Criterions)
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#findContexts(manager.Criterions)
	 */
	public SnapContext[] findContexts(Criterions criterions) throws SnapshotingException
	{
		if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		// Gets the contexts which subscribe to the given conditions
		ArrayList arrayList = SnapManagerApi.getContext(criterions);

		// Cast of the found contexts
		SnapContext[] snapContext = new SnapContext[ arrayList.size() ];
		for ( int i = 0 ; i < arrayList.size() ; i++ )
		{
			snapContext[ i ] = ( SnapContext ) arrayList.get(i);
		}

		return snapContext;
	}

	/* (non-Javadoc)
	 * Description : Return an array of snapshot (SnapShotLight) which subscribe to the given conditions (Criterions)
	 *
	 * @param the conditions relating to the snapshots
	 * @return an array of snapshot (SnapShotLight) which subscribe to the given conditions (Criterions)
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#findSnapshots(manager.Criterions)
	 */
	public SnapShotLight[] findSnapshots(Criterions criterions) throws SnapshotingException
	{
		if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		// Gets the snapshots which subscribe to the given conditions
		ArrayList arrayList = SnapManagerApi.getContextAssociatedSnapshots(criterions);

		// Cast of the found snapshots
		SnapShotLight[] snapShots = new SnapShotLight[ arrayList.size() ];
		for ( int i = 0 ; i < arrayList.size() ; i++ )
		{
			snapShots[ i ] = ( SnapShotLight ) arrayList.get(i);
		}
		return snapShots;
	}

	/* (non-Javadoc)
	 * Description : Return an array of attributes (SnapAttributeHeavy) which are associated to the given context (SnapContext) and subscribe to the given conditions (Criterions)
	 *
	 * @param the context (SnapContext)
	 * @param the conditions relating to the snapshots
	 * @return an array of attributes (SnapAttributeHeavy) which are associated to the given context (SnapContext) and subscribe to the given conditions (Criterions)
	 * @throws SnapshotingException
	 * @see fr.soleil.TangoSnapshoting.SnapManagerApi.ISnapManager#findContextAttributes(fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapContext, manager.Criterions)
	 */
	public SnapAttributeHeavy[] findContextAttributes(SnapContext context , Criterions criterions) throws SnapshotingException
	{
		if ( !isConnected )
            SnapManagerApi.SnapshotingConfigure(this.user , this.pwd);

		// Gets the attributes which are associated to the given context and subscribe to the given conditions
		ArrayList arrayList = SnapManagerApi.getContextAssociatedAttributes(context.getId() , criterions);

		// Cast of the found attributes
		SnapAttributeHeavy[] snapshotAttributes = new SnapAttributeHeavy[ arrayList.size() ];
		for ( int i = 0 ; i < arrayList.size() ; i++ )
		{
			snapshotAttributes[ i ] = ( SnapAttributeHeavy ) arrayList.get(i);
		}

		return snapshotAttributes;
	}

}
