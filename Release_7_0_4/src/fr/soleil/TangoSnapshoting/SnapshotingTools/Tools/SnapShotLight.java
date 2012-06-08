//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapShotLight.
//						(Chinkumo Jean) - Nov 11, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.16.1  2005/11/15 13:34:38  chinkumo
// no message
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


public class SnapShotLight
{
	private int id_snap = -1;                          //  Identifier for this snapshot
	private java.sql.Timestamp snap_date = null;     //  Timestamp asociated to this snapshot
	private String comment = "";

	public SnapShotLight()
	{
	}

	public SnapShotLight(int id_snap , java.sql.Timestamp snap_date , String comment)
	{
		this.id_snap = id_snap;
		this.snap_date = snap_date;
		this.comment = comment;
	}

	public SnapShotLight(String[] argin)
	{
		setId_snap(Integer.parseInt(argin[ 0 ]));
		setSnap_date(java.sql.Timestamp.valueOf(argin[ 1 ]));
		setComment(argin[ 2 ]);
	}

	public java.sql.Timestamp getSnap_date()
	{
		return snap_date;
	}

	public void setSnap_date(java.sql.Timestamp snap_date)
	{
		this.snap_date = snap_date;
	}

	public int getId_snap()
	{
		return id_snap;
	}

	public void setId_snap(int id_snap)
	{
		this.id_snap = id_snap;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String[] toArray()
	{
		String[] snapShot;
		snapShot = new String[ 2 ];
		snapShot[ 0 ] = Integer.toString(id_snap);
		snapShot[ 1 ] = snap_date.toString();
		snapShot[ 2 ] = comment;
		return snapShot;
	}

	public String toString()
	{
		String snapL =
		        "Identifier :  " + id_snap + "\r\n" +
		        "Record time : " + snap_date + "\r\n" +
		        "Comment : " + comment;
		return snapL;
	}
}
