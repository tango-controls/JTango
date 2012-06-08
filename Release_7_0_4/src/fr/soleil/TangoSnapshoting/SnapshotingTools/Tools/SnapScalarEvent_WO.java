//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapScalarEvent_WO.
//						(Chinkumo Jean) - 24, 2004
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

import java.sql.Timestamp;

public class SnapScalarEvent_WO extends SnapAttribute
{
	public SnapScalarEvent_WO()
	{
	}

	public SnapScalarEvent_WO(String[] snapScalarEvent_WO)
	{
		setAttribute_complete_name(snapScalarEvent_WO[ 0 ]);
		setId_att(Integer.parseInt(snapScalarEvent_WO[ 1 ]));
		setId_snap(Integer.parseInt(snapScalarEvent_WO[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapScalarEvent_WO[ 3 ]));

		setScalarValue(Double.parseDouble(snapScalarEvent_WO[ 4 ]));
	}

	public void setScalarValue(double d)
	{
		setValue(new Double(d));
	}

	public double getScalarValueWO()
	{
		return ( ( Double ) getValue() ).doubleValue();
	}

	public String[] toArray()
	{
		double d = ( ( Double ) getValue() ).doubleValue();
		String snapScalarEvent_WO[] = new String[ 5 ];

		snapScalarEvent_WO[ 0 ] = getAttribute_complete_name().trim();
		snapScalarEvent_WO[ 1 ] = Integer.toString(getId_att());
		snapScalarEvent_WO[ 2 ] = Integer.toString(getId_snap());

		snapScalarEvent_WO[ 3 ] = getSnap_date().toString().trim();
		snapScalarEvent_WO[ 4 ] = Double.toString(d).trim();
		return snapScalarEvent_WO;
	}

	public String toString()
	{
		String snapSpectrumEvent_WO = "";
		snapSpectrumEvent_WO =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"Attribute ID : \t" + getId_att() + "\r\n" +
		"Snap ID : \t" + getId_snap() + "\r\n" +
		"Snap Time : \t" + getSnap_date() + "\r\n" +
		"Value : \t" + getScalarValueWO() + "\r\n";
		return snapSpectrumEvent_WO;
	}

}
