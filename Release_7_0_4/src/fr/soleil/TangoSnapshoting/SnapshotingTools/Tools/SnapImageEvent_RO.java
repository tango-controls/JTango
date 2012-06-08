//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapImageEvent_RO.
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
// Revision 1.3  2006/06/28 12:43:58  ounsy
// image support
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

public class SnapImageEvent_RO extends SnapAttribute
{
	private int dim_x;
	private int dim_y;

	public SnapImageEvent_RO()
	{
		super();
	}

	public SnapImageEvent_RO(String[] snapImageEvent_RO)
	{
		setAttribute_complete_name(snapImageEvent_RO[ 0 ]);
		setId_att(Integer.parseInt(snapImageEvent_RO[ 1 ]));
		setId_snap(Integer.parseInt(snapImageEvent_RO[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapImageEvent_RO[ 3 ]));
		setDim_x(Integer.parseInt(snapImageEvent_RO[ 4 ]));
		setDim_y(Integer.parseInt(snapImageEvent_RO[ 5 ]));

		Double[][] value = new Double[ dim_y ][ dim_x ];
		int k = 6;
		for ( int i = 0 ; i < dim_y ; i++ )
		{
			for ( int j = 0 ; j < dim_x ; j++ )
			{
				value[ i ][ j ] = Double.valueOf(snapImageEvent_RO[ k++ ]);
			}
		}
		this.setImageValueRO(value);
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
		this.dim_y = dim_y;
	}

	public Double[][] getImageValueRO()
	{
		return ( Double[][] ) getValue();
	}

	public void setImageValueRO(Double[][] value)
	{
		setValue(value);
	}


	/**
	 * Returns an array representation of the object <I>SnapImageEvent_RO</I>.
	 *
	 * @return an array representation of the object <I>SnapImageEvent_RO</I>.
	 */
	public String[] toArray()
	{
		Double[][] value = ( Double[][] ) getValue();
        if (value == null) return null;
		int dim_y = value.length;
        if (dim_y == 0) return new String[0];
		int dim_x = value[ 0 ].length;
		String[] snapImageEvent_RO = new String[ 6 + dim_x * dim_y ];

		snapImageEvent_RO[ 0 ] = getAttribute_complete_name(); //	name
		snapImageEvent_RO[ 1 ] = Integer.toString(getId_att()); //	id_context
		snapImageEvent_RO[ 2 ] = Integer.toString(getId_snap()); //	id_snap
		snapImageEvent_RO[ 3 ] = getSnap_date().toString(); //	time
		snapImageEvent_RO[ 4 ] = Integer.toString(dim_x);  // dim_x
		snapImageEvent_RO[ 5 ] = Integer.toString(dim_y);  // dim_y

		int k = 6;
		for ( int i = 0 ; i < dim_y ; i++ )
		{
			for ( int j = 0 ; j < dim_x ; j++ )
			{
				snapImageEvent_RO[ k ] = value[ i ][ j ] + "";
				k++;
			}
		}
		return snapImageEvent_RO;
	}

	public String toString()
	{
		String snapImageEvent_RO = "";
		snapImageEvent_RO =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"Attribute ID : \t" + getId_att() + "\r\n" +
		"Snap ID : \t" + getId_snap() + "\r\n" +
		"Snap Time : \t" + getSnap_date() + "\r\n" +
		"Dim x : \t" + getDim_x() + "\r\n" +
		"Dim y : \t" + getDim_y() + "\r\n" +
		"Value : \t..." + "\r\n";
		return snapImageEvent_RO;
	}
}
