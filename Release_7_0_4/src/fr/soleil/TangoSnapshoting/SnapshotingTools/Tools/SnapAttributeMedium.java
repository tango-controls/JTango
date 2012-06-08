//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapAttributeMedium.
//						(Chinkumo Jean) - Mar 24, 2004
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

public class SnapAttributeMedium extends SnapAttributeLight
{
	private int id_snap = -1;                          //  Identifier for this snapshot
	private java.sql.Timestamp snap_date = null;     //  Timestamp asociated to this snapshot

	public SnapAttributeMedium(String attribute_complete_name , int data_type , int data_format , int writable , int id_context , int id_snap , java.sql.Timestamp snap_date)
	{
		setAttribute_complete_name(attribute_complete_name);
		setData_type(data_type);
		setData_format(data_format);
		setWritable(writable);

		this.id_snap = id_snap;
		this.snap_date = snap_date;
	}

	public SnapAttributeMedium(SnapAttributeLight snapAttributeLight , int id_context , int id_snap , java.sql.Timestamp snap_date)
	{
		setAttribute_complete_name(snapAttributeLight.getAttribute_complete_name());
		setAttribute_id(snapAttributeLight.getAttribute_id());
		setData_type(snapAttributeLight.getData_type());
		setData_format(snapAttributeLight.getData_format());
		setWritable(snapAttributeLight.getWritable());

		this.id_snap = id_snap;
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

	public java.sql.Timestamp getSnap_date()
	{
		return snap_date;
	}

	public void setSnap_date(java.sql.Timestamp snap_date)
	{
		this.snap_date = snap_date;
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof SnapAttributeMedium ) ) return false;

		final SnapAttributeMedium snapAttributeMedium = ( SnapAttributeMedium ) o;

		if ( getData_format() != snapAttributeMedium.getData_format() ) return false;
		if ( getData_type() != snapAttributeMedium.getData_type() ) return false;
		if ( getAttribute_id() != snapAttributeMedium.getAttribute_id() ) return false;
		if ( id_snap != snapAttributeMedium.id_snap ) return false;
		if ( getWritable() != snapAttributeMedium.getWritable() ) return false;
		if ( !getAttribute_complete_name().equals(snapAttributeMedium.getAttribute_complete_name()) ) return false;
		if ( !snap_date.equals(snapAttributeMedium.snap_date) ) return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = getAttribute_complete_name().hashCode();
		result = 29 * result + getAttribute_id();
		result = 29 * result + id_snap;
		result = 29 * result + snap_date.hashCode();
		return result;
	}

	public String toString()
	{
		String snapString = new String("");
		snapString = "Attribut : " + getAttribute_complete_name() + "\r\n" +
		             "\t" + "Attribute Id : \t" + getAttribute_id() + "\r\n" +
		             "\t" + "data_type : \t" + getData_type() + "\r\n" +
		             "\t" + "data_format : \t" + getData_format() + "\r\n" +
		             "\t" + "writable : \t" + getWritable() + "\r\n" +
		             "\t" + "Snapshot Id : \t" + id_snap + "\r\n" +
		             "\t" + "SnapShot time : \t" + snap_date.toString() + "\r\n";
		return snapString;
	}

}
