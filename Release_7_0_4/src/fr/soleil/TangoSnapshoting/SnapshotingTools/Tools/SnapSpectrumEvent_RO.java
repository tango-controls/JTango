//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapSpectrumEvent_RO.
//						(Chinkumo Jean) - Mar 26, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.3  2006/04/11 14:36:00  ounsy
// new spectrum types support
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

public class SnapSpectrumEvent_RO extends SnapAttribute
{
	private int dim_x;
	private int dim_y = 0;

	public SnapSpectrumEvent_RO()
	{
		super();
	}

	public SnapSpectrumEvent_RO(String[] snapSpectrumEvent_RO)
	{
		setAttribute_complete_name(snapSpectrumEvent_RO[ 0 ]);
		setId_att(Integer.parseInt(snapSpectrumEvent_RO[ 1 ]));
		setId_snap(Integer.parseInt(snapSpectrumEvent_RO[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapSpectrumEvent_RO[ 3 ]));
		setDim_x(Integer.parseInt(snapSpectrumEvent_RO[ 4 ]));
		//setDim_y(Integer.parseInt(snapSpectrumEvent_RO[5]));

		Double[] value = new Double[ snapSpectrumEvent_RO.length - 6 ];
		for ( int i = 0 ; i < value.length ; i++ )
		{
			value[ i ] = Double.valueOf(snapSpectrumEvent_RO[ i + 6 ]);
		}
		this.setValue(value);
	}

	public int getDim_x()
	{
		return dim_x;
	}

	public void setDim_x(int dim_x)
	{
		this.dim_x = dim_x;
	}

	public int getDim_y()
	{
		return dim_y;
	}

	public void setDim_y(int dim_y)
	{
		this.dim_y = 0;
	}

	/**
	 * Returns an array representation of the object <I>SnapSpectrumEvent_RO</I>.
	 *
	 * @return an array representation of the object <I>SnapSpectrumEvent_RO</I>.
	 */
	public String[] toArray()
	{
		Double[] value = ( Double[] ) getValue();
		String[] snapSpectrumEvent_RO = new String[ 6 + value.length ];
		snapSpectrumEvent_RO[ 0 ] = getAttribute_complete_name(); //	name
		snapSpectrumEvent_RO[ 1 ] = Integer.toString(getId_att()); //	id_context
		snapSpectrumEvent_RO[ 2 ] = Integer.toString(getId_snap()); //	id_snap
		snapSpectrumEvent_RO[ 3 ] = getSnap_date().toString(); //	time
		snapSpectrumEvent_RO[ 4 ] = Integer.toString(dim_x);  // dim_x
		snapSpectrumEvent_RO[ 5 ] = Integer.toString(dim_y);  // dim_y

		for ( int i = 0 ; i < value.length ; i++ )
		{
			snapSpectrumEvent_RO[ i + 6 ] = value[ i ] + "";
		}
		return snapSpectrumEvent_RO;
	}

	public String toString()
	{
		String snapSpectrumEvent_RO = "";
		snapSpectrumEvent_RO =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"Attribute ID : \t" + getId_att() + "\r\n" +
		"Snap ID : \t" + getId_snap() + "\r\n" +
		"Snap Time : \t" + getSnap_date() + "\r\n" +
		"Dim x : \t" + getDim_x() + "\r\n" +
		"Dim y : \t" + getDim_y() + "\r\n" +
		"Value : \t..." + "\r\n";
		return snapSpectrumEvent_RO;
	}
}
