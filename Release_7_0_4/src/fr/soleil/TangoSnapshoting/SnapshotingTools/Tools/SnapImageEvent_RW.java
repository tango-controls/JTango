//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapImageEvent_RW.
//						(GIRARDOT Raphaël) - Jun 21, 2006
//
// $Author$
//
// $Revision: 
//
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

public class SnapImageEvent_RW extends SnapAttribute
{
	private int dim_x = 0;
	private int dim_y = 0;
    private int dim_x_write = 0;
    private int dim_y_write = 0;
    public final static String NOT_CODING = "#";

	public SnapImageEvent_RW()
	{
		super();
	}

	public SnapImageEvent_RW(String[] snapImageEvent_RW)
	{
		setAttribute_complete_name(snapImageEvent_RW[ 0 ]);
		setId_att(Integer.parseInt(snapImageEvent_RW[ 1 ]));
		setId_snap(Integer.parseInt(snapImageEvent_RW[ 2 ]));
		setSnap_date(Timestamp.valueOf(snapImageEvent_RW[ 3 ]));
		setDim_x(Integer.parseInt(snapImageEvent_RW[ 4 ]));
		setDim_y(Integer.parseInt(snapImageEvent_RW[ 5 ]));
        setDim_x_write(Integer.parseInt(snapImageEvent_RW[ 6 ]));
        setDim_y_write(Integer.parseInt(snapImageEvent_RW[ 7 ]));

		Double[][] value = new Double[ dim_y + dim_y_write ][ max(dim_x, dim_x_write) ];
		int k = 8;
		for ( int i = 0 ; i < dim_y ; i++ )
		{
			for ( int j = 0 ; j < dim_x ; j++ )
			{
				value[ i ][ j ] = Double.valueOf(snapImageEvent_RW[ k++ ]);
			}
		}
        for ( int i = dim_y ; i < dim_y + dim_y_write ; i++ )
        {
            for ( int j = 0 ; j < dim_x_write ; j++ )
            {
                value[ i ][ j ] = Double.valueOf(snapImageEvent_RW[ k++ ]);
            }
        }
		this.setImageValueRW(value);
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

    public int getDim_x_write ()
    {
        return dim_x_write;
    }

    public void setDim_x_write (int dim_x_write)
    {
        this.dim_x_write = dim_x_write;
    }

    public int getDim_y_write ()
    {
        return dim_y_write;
    }

    public void setDim_y_write (int dim_y_write)
    {
        this.dim_y_write = dim_y_write;
    }

	public Double[][] getImageValueRW()
	{
		return ( Double[][] ) getValue();
	}

	public void setImageValueRW(Double[][] value)
	{
		setValue(value);
	}

    public Double[][] getImageValueRWRead()
    {
        Double[][] read = new Double[dim_y][dim_x];
        for (int i = 0; i < dim_y; i++)
        {
            for (int j = 0; j < dim_x; j++)
            {
                read[i][j] = getImageValueRW()[i][j];
            }
        }
        return read;
    }

    public Double[][] getImageValueRWWrite()
    {
        Double[][] write = new Double[dim_y_write][dim_x_write];
        for (int i = dim_y; i < dim_y + dim_y_write; i++)
        {
            for (int j = 0; j < dim_x_write; j++)
            {
                write[i][j] = getImageValueRW()[i][j];
            }
        }
        return write;
    }

	/**
	 * Returns an array representation of the object <I>SnapImageEvent_RO</I>.
	 *
	 * @return an array representation of the object <I>SnapImageEvent_RO</I>.
	 */
	public String[] toArray()
	{
		Double[][] value = ( Double[][] ) getValue();
		int dim_x = value[ 0 ].length;
		int dim_y = value.length;
		String[] snapImageEvent_RO = new String[ 8 + (dim_x + dim_x_write) * max(dim_y, dim_y_write) ];

		snapImageEvent_RO[ 0 ] = getAttribute_complete_name(); //	name
		snapImageEvent_RO[ 1 ] = Integer.toString(getId_att()); //	id_context
		snapImageEvent_RO[ 2 ] = Integer.toString(getId_snap()); //	id_snap
		snapImageEvent_RO[ 3 ] = getSnap_date().toString(); //	time
		snapImageEvent_RO[ 4 ] = Integer.toString(dim_x);  // dim_x
		snapImageEvent_RO[ 5 ] = Integer.toString(dim_y);  // dim_y
        snapImageEvent_RO[ 6 ] = Integer.toString(dim_x_write);  // dim_x_write
        snapImageEvent_RO[ 7 ] = Integer.toString(dim_y_write);  // dim_y_write

		int k = 8;
        for (int i = 0; i < snapImageEvent_RO.length - k; i++)
        {
            snapImageEvent_RO[i+k] = NOT_CODING;
        }

        for ( int i = 0 ; i < dim_y ; i++ )
		{
			for ( int j = 0 ; j < dim_x ; j++ )
			{
				snapImageEvent_RO[ k ] = value[ i ][ j ] + "";
				k++;
			}
		}
        for ( int i = dim_y ; i < dim_y + dim_y_write ; i++ )
        {
            for ( int j = 0 ; j < dim_x_write ; j++ )
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
        "Dim x write : \t" + getDim_x_write() + "\r\n" +
        "Dim y write : \t" + getDim_y_write() + "\r\n" +
		"Value : \t..." + "\r\n";
		return snapImageEvent_RO;
	}

    private int max(int x, int y)
    {
        if (x > y) return x;
        else return y;
    }

}
