//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapAttribute.
//						(Chinkumo Jean) - Mar 24, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2006/02/15 09:06:05  ounsy
// minor changes
//
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

public class SnapAttribute
{
	private String attribute_complete_name = "";
	protected int data_format;
	protected int data_type;
	protected int writable;
	private int id_snap = -1;                          //  Identifier for this snapshot
	private int id_att = -1;                        //  Identifier for the associated attribute
	private java.sql.Timestamp snap_date = null;     //  Timestamp asociated to this snapshot
	private Object value;

	public SnapAttribute()
	{
	}


	public String getAttribute_complete_name()
	{
		return attribute_complete_name;
	}

	public void setAttribute_complete_name(String attribute_complete_name)
	{
		this.attribute_complete_name = attribute_complete_name;
	}

	public int getData_format()
	{
		return data_format;
	}

	public void setData_format(int data_format)
	{
		this.data_format = data_format;
	}

	public int getData_type()
	{
		return data_type;
	}

	public void setData_type(int data_type)
	{
		this.data_type = data_type;
	}

	public int getWritable()
	{
		return writable;
	}

	public void setWritable(int writable)
	{
		this.writable = writable;
	}

	public int getId_att()
	{
		return id_att;
	}

	public void setId_att(int id_att)
	{
		this.id_att = id_att;
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

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
}
