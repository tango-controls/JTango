//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapScalarEvent_RO.
//						(Chinkumo Jean) - 24, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
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

import java.sql.Timestamp;

public class SnapScalarEvent_RO extends SnapAttribute
{
	public SnapScalarEvent_RO()
	{
	}

	public SnapScalarEvent_RO(String[] snapScalarEvent_RO)
	{
		setAttribute_complete_name(snapScalarEvent_RO[ 0 ]);
		setId_att(Integer.parseInt(snapScalarEvent_RO[ 1 ]));
		setId_snap(Integer.parseInt(snapScalarEvent_RO[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapScalarEvent_RO[ 3 ]));

		setScalarValue(Double.valueOf(snapScalarEvent_RO[ 4 ]));
	}

	public void setScalarValue(Double d)
	{
		setValue(d);
	}

	public Double getScalarValueRO()
	{
		return ( Double ) getValue();
	}

	public String[] toArray()
	{
		Double d = ( Double ) getValue();
		String snapScalarEvent_RO[] = new String[ 5 ];

		snapScalarEvent_RO[ 0 ] = getAttribute_complete_name().trim();
		snapScalarEvent_RO[ 1 ] = Integer.toString(getId_att());
		snapScalarEvent_RO[ 2 ] = Integer.toString(getId_snap());

		snapScalarEvent_RO[ 3 ] = getSnap_date().toString().trim();
		snapScalarEvent_RO[ 4 ] = d + "";
		return snapScalarEvent_RO;
	}

	public String toString()
	{
		String snapSpectrumEvent_RO = "";
		snapSpectrumEvent_RO =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"Attribute ID : \t" + getId_att() + "\r\n" +
		"Snap ID : \t" + getId_snap() + "\r\n" +
		"Snap Time : \t" + getSnap_date() + "\r\n" +
		"Value : \t" + getScalarValueRO() + "\r\n";
		return snapSpectrumEvent_RO;
	}

}
