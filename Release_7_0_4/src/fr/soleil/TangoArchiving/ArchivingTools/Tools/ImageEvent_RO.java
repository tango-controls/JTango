//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ImageEvent_RO.
//						(Chinkumo Jean) - Mar 10, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.7  2006/10/31 16:54:23  ounsy
// milliseconds and null values management
//
// Revision 1.6  2006/07/24 07:36:33  ounsy
// getValue_AsString() method added
//
// Revision 1.5  2006/07/18 10:42:56  ounsy
// better image support
//
// Revision 1.4  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.3.12.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.3  2005/06/14 10:12:11  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.2.4.1  2005/06/13 15:08:16  chinkumo
// Minor changes made to improve image event management efficiency
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
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

package fr.soleil.TangoArchiving.ArchivingTools.Tools;

public class ImageEvent_RO extends ArchivingEvent
{
	private int dim_x;
	private int dim_y;

	/**
	 * Creates a new instance of DhdbEvent
	 */
	public ImageEvent_RO()
	{
		super();
	}

	/**
	 * Creates a new instance of DhdbEvent
	 */
	public ImageEvent_RO(String[] hdbImageEvent_RO)
	{
		setAttribute_complete_name(hdbImageEvent_RO[ 0 ]);
		setTimeStamp(Long.parseLong(hdbImageEvent_RO[ 1 ]));
		setDim_x(Integer.parseInt(hdbImageEvent_RO[ 2 ]));
		setDim_y(Integer.parseInt(hdbImageEvent_RO[ 3 ]));

		Double[][] value = new Double[ dim_y ][ dim_x ];
		int k = 4;
		for ( int i = 0 ; i < dim_y ; i++ )
		{
			for ( int j = 0 ; j < dim_x ; j++ )
			{
                if (hdbImageEvent_RO[k] == null || "".equals(hdbImageEvent_RO[k]) || "null".equals(hdbImageEvent_RO[k]))
                {
                    value[i][j] = null;
                }
                else
                {
                    value[ i ][ j ] = Double.valueOf(hdbImageEvent_RO[ k ]);
                }
                k++;
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
	 * Returns an array representation of the object <I>ImageEvent_RO</I>.
	 *
	 * @return an array representation of the object <I>ImageEvent_RO</I>.
	 */
	public String[] toArray()
	{
		Double[][] value = ( Double[][] ) getValue();
		int dim_x = value[ 0 ].length;
		int dim_y = value.length;
		String[] hdbImageEvent_RO = new String[ 4 + dim_x * dim_y ];

		hdbImageEvent_RO[ 0 ] = getAttribute_complete_name(); //	name
		hdbImageEvent_RO[ 1 ] = Long.toString(getTimeStamp()).trim(); //	time
		hdbImageEvent_RO[ 2 ] = Integer.toString(dim_x);  // dim_x
		hdbImageEvent_RO[ 3 ] = Integer.toString(dim_y);  // dim_y

		int k = 4;
		for ( int i = 0 ; i < dim_y ; i++ )
		{
			for ( int j = 0 ; j < dim_x ; j++ )
			{
				hdbImageEvent_RO[ k ] = "" + value[ i ][ j ];
				k++;
			}
		}
		return hdbImageEvent_RO;
	}


	/**
	 * This method returns the value of this spectrum event. The returned value is then formated as a String.
	 *
	 * @return the value of this spectrum event.
	 */
	public String getImageValueRO_AsString()
	{
		Double[][] value = getImageValueRO();
		StringBuffer valueStr = new StringBuffer();
		for ( int i = 0 ; i < value.length ; i++ )
		{    // lignes
			for ( int j = 0 ; j < ( value[ i ].length - 1 ) ; j++ )
			{ // colonnes
				valueStr.append(value[ i ][ j ]).append("\t");
			}
			valueStr.append(value[ i ][ value[ i ].length - 1 ]).append("\r\n");

		}
		return valueStr.toString();
	}

    /**
     * This method returns the value of this image event. The returned value is then formated as a String.
     *
     * @return the value of this image event.
     */
    public String getValue_AsString()
    {
        Object value = getValue();
        if (value == null) return "";
        StringBuffer valueStr = new StringBuffer();
        if (value instanceof Double[][] && ((Double[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((Double[][])value).length ; i++ )
            {
                for (int j = 0; j < ((Double[][])value)[0].length; j++ )
                {
                    valueStr.append( ((Double[][])value)[i][j] );
                    if (j < ((Double[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((Double[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else if (value instanceof Float[][] && ((Float[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((Float[][])value).length ; i++ )
            {
                for (int j = 0; j < ((Float[][])value)[0].length; j++ )
                {
                    valueStr.append( ((Float[][])value)[i][j] );
                    if (j < ((Float[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((Float[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else if (value instanceof Integer[][] && ((Integer[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((Integer[][])value).length ; i++ )
            {
                for (int j = 0; j < ((Integer[][])value)[0].length; j++ )
                {
                    valueStr.append( ((Integer[][])value)[i][j] );
                    if (j < ((Integer[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((Integer[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else if (value instanceof Short[][] && ((Short[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((Short[][])value).length ; i++ )
            {
                for (int j = 0; j < ((Short[][])value)[0].length; j++ )
                {
                    valueStr.append( ((Short[][])value)[i][j] );
                    if (j < ((Short[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((Short[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else if (value instanceof Byte[][] && ((Byte[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((Byte[][])value).length ; i++ )
            {
                for (int j = 0; j < ((Byte[][])value)[0].length; j++ )
                {
                    valueStr.append( ((Byte[][])value)[i][j] );
                    if (j < ((Byte[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((Byte[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else if (value instanceof Boolean[][] && ((Boolean[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((Boolean[][])value).length ; i++ )
            {
                for (int j = 0; j < ((Boolean[][])value)[0].length; j++ )
                {
                    valueStr.append( ((Boolean[][])value)[i][j] );
                    if (j < ((Boolean[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((Boolean[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else if (value instanceof String[][] && ((String[][])value).length != 0)
        {
            for ( int i = 0 ; i < ((String[][])value).length ; i++ )
            {
                for (int j = 0; j < ((String[][])value)[0].length; j++ )
                {
                    valueStr.append( ((String[][])value)[i][j] );
                    if (j < ((String[][])value)[0].length - 1)
                    {
                        valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_COLS );
                    }
                }
                if (i < ((String[][])value).length - 1)
                {
                    valueStr.append( GlobalConst.CLOB_SEPARATOR_IMAGE_ROWS );
                }
            }
        }
        else
        {
            valueStr.append( value.toString() );
        }
        return valueStr.toString();
    }

	public String toString()
	{
		StringBuffer event_String = new StringBuffer();
		event_String.append("Source : \t").append(getAttribute_complete_name()).append("\r\n");
		event_String.append("TimeSt : \t").append(getTimeStamp()).append("\r\n");
		event_String.append("Value :  \t...").append("\r\n");
		return event_String.toString();
	}
}
