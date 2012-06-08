//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapShot.
//						(Chinkumo Jean) - Mar 22, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.5  2006/02/17 09:26:46  chinkumo
// Minor change : code reformated.
//
// Revision 1.4  2006/02/15 09:07:47  ounsy
// minor changes : uncomment to debug
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.2.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/08/19 14:04:02  chinkumo
// no message
//
// Revision 1.1.14.1  2005/08/11 08:32:13  chinkumo
// Changes was made since the 'SetEquipement' functionnality was added.
//
// Revision 1.1  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import java.util.ArrayList;

/**
 * <p/>
 * <B>Description :</B><BR>
 * The <I>SnapShot</I> object describes a snapshot.
 * A snapshot ... *
 * <ul>
 * <li> ... is related to a context <I>(context ID)</I>,
 * <li> ... has an identifier  <I>(Snapshot ID)</I>
 * <li> ... is triggered at a given <I>timestamp</I>
 * <li> ... concerns a <I>list of attributes</I>
 * </ul>
 * <B>Date :<B> Mar 22, 2004
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version 1.0
 * @see SnapContext
 */
public class SnapShot
{
	private int id_context = -1;                        //  Identifier for the related context
	private int id_snap = -1;                          //  Identifier for this snapshot
	private java.sql.Timestamp snap_date = null;     //  Timestamp asociated to this snapshot
	private ArrayList attribute_List = null;        //  Attribute list associated to this snapshot

	/**
	 * Default constructor
	 * Creates a new instance of SnapShot
	 *
	 * @see #SnapShot(int,  java.sql.Timestamp)
	 * @see #SnapShot(String[])
	 */
	public SnapShot()
	{
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param idContext the associated context identifier
	 * @param snapDate  the timestamp associated to this snapshot
	 * @see #SnapShot()
	 * @see #SnapShot(String[])
	 */
	public SnapShot(int idContext , java.sql.Timestamp snapDate)
	{
		id_context = idContext;
		snap_date = snapDate;
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param idContext     the associated context identifier
	 * @param snapDate      the timestamp associated to this snapshot
	 * @param attributeList the attribute list associated to this snapshot
	 * @see #SnapShot()
	 * @see #SnapShot(String[])
	 */
	public SnapShot(int idContext , java.sql.Timestamp snapDate , ArrayList attributeList)
	{
		id_context = idContext;
		snap_date = snapDate;
		attribute_List = attributeList;
	}

	/**
	 * This constructor builds an SnapContext from an array
	 *
	 * @param argin an array that contains the SnapContext's author's name, name, identifier,
	 *              creation date, reason, description and, the <I>list of attributes</I> that are included in the context.
	 */
	public SnapShot(String[] argin)
	{
		//System.out.println("Snapshot test 0");
		setId_context(Integer.parseInt(argin[ 0 ]));
		//System.out.println("Snapshot test 1");
		setId_snap(Integer.parseInt(argin[ 1 ]));
		//System.out.println("Snapshot test 2");
		setSnap_date(java.sql.Timestamp.valueOf(argin[ 2 ]));
		//System.out.println("Snapshot test 3");
		attribute_List = new ArrayList(( argin.length - 3 ) / 7);
		// System.out.println("Snapshot test 4");

		int k = 3;
		/*System.out.println("Snapshot test 5 : argin.length = " + argin.length);
		for (int i = 0; i < argin.length; i++) {
			System.out.println("argin["+i+"] = |"+argin[i]+"|");
		}*/

		while ( k < argin.length )
		{
			//System.out.println("Little test 0 : k="+k);
			String[] snapAttributeExtractArray = {argin[ k ], argin[ k + 1 ], argin[ k + 2 ], argin[ k + 3 ], argin[ k + 4 ], argin[ k + 5 ], argin[ k + 6 ]};
			//System.out.println("Little test 1");
			SnapAttributeExtract snapAttributeExtract = new SnapAttributeExtract(snapAttributeExtractArray);
			//System.out.println("Little test 2");
			attribute_List.add(snapAttributeExtract);
			//System.out.println("Little test 3");
			k = k + 7;
		}
	}

	/**
	 * Returns the identifier of the context associated to this Snapshot
	 *
	 * @return the identifier of the context associated to this Snapshot
	 */
	public int getId_context()
	{
		return id_context;
	}

	/**
	 * Sets the identifier of the context associated to this Snapshot
	 *
	 * @param id_context the identifier of the context associated to this Snapshot
	 */
	public void setId_context(int id_context)
	{
		this.id_context = id_context;
	}

	/**
	 * Returns the identifier associated to this Snapshot
	 *
	 * @return the identifier associated to this Snapshot
	 */
	public int getId_snap()
	{
		return id_snap;
	}

	/**
	 * Sets the identifier associated to this Snapshot
	 *
	 * @param id_snap the identifier associated to this Snapshot
	 */
	public void setId_snap(int id_snap)
	{
		this.id_snap = id_snap;
	}

	/**
	 * Returns the timestamp associated to this Snapshot
	 *
	 * @return the timestamp associated to this Snapshot
	 */
	public java.sql.Timestamp getSnap_date()
	{
		return snap_date;
	}

	/**
	 * Sets the timestamp associated to this Snapshot
	 *
	 * @param snap_date the timestamp associated to this Snapshot
	 */
	public void setSnap_date(java.sql.Timestamp snap_date)
	{
		this.snap_date = snap_date;
	}

	/**
	 * Returns the attribute list associated to this Snapshot
	 *
	 * @return the attribute list associated to this Snapshot
	 */
	public ArrayList getAttribute_List()
	{
		return attribute_List;
	}

	/**
	 * Sets the attribute list associated to this Snapshot
	 *
	 * @param attribute_List the attribute list associated to this Snapshot
	 */
	public void setAttribute_List(ArrayList attribute_List)
	{
		this.attribute_List = attribute_List;
	}

	/**
	 * Returns an array representation of the object <I>Snapshot</I>.
	 * In this order, array fields are :
	 * <ol>
	 * <li> the <I>identifier of the associated context</I>,
	 * <li> the <I>identifier of this snapshot</I>,
	 * <li> the <I>Timestamp</I> asociated to this snapshot
	 * <li> The <I>Attribute list associated to this snapshot</I> (with all the needed informations (data type, data format, writable))
	 * </ol>
	 *
	 * @return an array representation of the object <I>SnapContext</I>.
	 */
	public String[] toArray()
	{
		String[] snapShot;
		snapShot = new String[ 3 + ( 7 * attribute_List.size() ) ];     // Multiplied by 5 because the size of an attribute is 5.

		snapShot[ 0 ] = Integer.toString(id_context);
		snapShot[ 1 ] = Integer.toString(id_snap);
		snapShot[ 2 ] = snap_date.toString();
		int k = 3;

		for ( int i = 0 ; i < attribute_List.size() ; i++ )
		{
			SnapAttributeExtract snapAttributeExtract = ( SnapAttributeExtract ) attribute_List.get(i);
			//System.out.println("test0");
			String[] snapAttributeExtractArray = snapAttributeExtract.toArray();
			//System.out.println("test1");
			snapShot[ k ] = snapAttributeExtractArray[ 0 ];
			snapShot[ k + 1 ] = snapAttributeExtractArray[ 1 ];
			snapShot[ k + 2 ] = snapAttributeExtractArray[ 2 ];
			snapShot[ k + 3 ] = snapAttributeExtractArray[ 3 ];
			snapShot[ k + 4 ] = snapAttributeExtractArray[ 4 ];
			snapShot[ k + 5 ] = snapAttributeExtractArray[ 5 ];
			snapShot[ k + 6 ] = snapAttributeExtractArray[ 6 ];
			k = k + 7;
		}
		return snapShot;
	}

	public String toString()
	{
		String snapString = new String("");
		snapString = "SnapShot" + "\r\n" +
		             "Associated  Context Id : \t" + id_context + "\r\n" +
		             "Snapshot Id : \t" + id_snap + "\r\n" +
		             "SnapShot time : \t" + snap_date.toString() + "\r\n" +
		             "Attribute(s) : " + "\r\n";
		if ( attribute_List != null )
		{
			for ( int i = 0 ; i < attribute_List.size() ; i++ )
			{
				SnapAttributeExtract snapAttributeExtract = ( SnapAttributeExtract ) attribute_List.get(i);
				snapString = snapString + "\t\t" + "[" + i + "]" + "\t" + snapAttributeExtract.toString() + "\r\n";
			}
		}
		else
		{
			snapString = snapString + "\t\t" + "NULL" + "\r\n";
		}
		return snapString;
	}

}
