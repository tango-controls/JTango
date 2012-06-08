//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapScalarEvent_RW.
//						(Chinkumo Jean) - 24, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.3  2006/05/04 14:35:31  ounsy
// minor changes (commented useless methods and variables)
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

public class SnapScalarEvent_RW extends SnapAttribute
{
	public SnapScalarEvent_RW()
	{
	}

	public SnapScalarEvent_RW(String[] snapScalarEvent_RW)
	{
		setAttribute_complete_name(snapScalarEvent_RW[ 0 ]);
		setId_att(Integer.parseInt(snapScalarEvent_RW[ 1 ]));
		setId_snap(Integer.parseInt(snapScalarEvent_RW[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapScalarEvent_RW[ 3 ]));

		Double[] value = new Double[ 2 ];
		value[ 0 ] = Double.valueOf(snapScalarEvent_RW[ 4 ]);
		value[ 1 ] = Double.valueOf(snapScalarEvent_RW[ 5 ]);
		this.setScalarValueRW(value);
	}

	public void setScalarValueRW(Double[] value)
	{
		/*double[] tmp = new double[ 2 ];
		tmp = value;*/
		setValue(value);
	}

	public Double[] getScalarValueRW()
	{
		Double[] value = new Double[ 2 ];
		value = ( Double[] ) getValue();
		return value;
	}

	public String[] toArray()
	{
		Double[] value = ( Double[] ) getScalarValueRW();
		String snapScalarEvent_RW[] = new String[ 6 ];

		snapScalarEvent_RW[ 0 ] = getAttribute_complete_name().trim();
		snapScalarEvent_RW[ 1 ] = Integer.toString(getId_att());
		snapScalarEvent_RW[ 2 ] = Integer.toString(getId_snap());

		snapScalarEvent_RW[ 3 ] = getSnap_date().toString().trim();
		snapScalarEvent_RW[ 4 ] = value[ 0 ] + "";
		snapScalarEvent_RW[ 5 ] = value[ 1 ] + "";
		return snapScalarEvent_RW;
	}

	public String toString()
	{
		String snapScalarEvent_RW_String = "";

		snapScalarEvent_RW_String =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"Attribute Id : \t" + Integer.toString(getId_att()) + "\r\n" +
		"Snap Id : \t" + Integer.toString(getId_snap()) + "\r\n" +
		"Snap time : \t" + getSnap_date().toString().trim() + "\r\n" +
		"Value READ: \t" + getScalarValueRW()[ 0 ] + "\r\n" +
		"Value WRITE: \t" + getScalarValueRW()[ 1 ] + "\r\n";

		return snapScalarEvent_RW_String;
	}

}
