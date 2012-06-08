//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SpectrumEvent_RO.
//						(Chinkumo Jean) - Mar 10, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.14  2007/02/27 15:36:34  ounsy
// corrected a severe bug in  getValue_AsString()  (the value wasn't filled)
//
// Revision 1.13  2007/02/06 09:03:47  ounsy
// corrected a bug in getValue_AsString for empty values
//
// Revision 1.12  2006/10/31 16:54:23  ounsy
// milliseconds and null values management
//
// Revision 1.11  2006/08/23 09:52:17  ounsy
// null value represented as an empty String in getValue_AsString() method
//
// Revision 1.10  2006/05/12 09:23:10  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.9  2006/05/04 14:30:41  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.8  2006/03/27 15:19:30  ounsy
// new spectrum types support + better spectrum management
//
// Revision 1.7  2006/02/28 17:05:58  chinkumo
// no message
//
// Revision 1.6  2006/02/24 12:05:06  ounsy
// replaced hard-coded "," value to CLOB_SEPARATOR
//
// Revision 1.5  2006/02/15 11:07:34  chinkumo
// Minor changes made to optimize streams when sending data to DB.
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
// Revision 1.2.4.1  2005/06/13 14:55:38  chinkumo
// Minor changes made to improve spectrum event management efficiency.
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

import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;


public class SpectrumEvent_RO extends ArchivingEvent
{
	private int dim_x;
	private int dim_y = 0;

	/**
	 * Creates a new instance of Spectrum Event
	 */
	public SpectrumEvent_RO()
	{
		super();
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

	/**
	 * Creates a new instance of Spectrum Event
	 */
	public SpectrumEvent_RO(String[] hdbSpectrumEvent_ro)
	{
		this.setAttribute_complete_name(hdbSpectrumEvent_ro[ 0 ]);
		this.setTimeStamp(Long.parseLong(hdbSpectrumEvent_ro[ 1 ]));
		this.setDim_x(Integer.parseInt(hdbSpectrumEvent_ro[ 2 ]));
		// dim_y = 0;
		Double[] value = new Double[ hdbSpectrumEvent_ro.length - 4 ];
		for ( int i = 0 ; i < value.length ; i++ )
		{
            if (hdbSpectrumEvent_ro[ i + 4 ] == null || "".equals(hdbSpectrumEvent_ro[ i + 4 ]) || "null".equals(hdbSpectrumEvent_ro[ i + 4 ]))
            {
                value[i] = null;
            }
            else
            {
                value[ i ] = Double.valueOf(hdbSpectrumEvent_ro[ i + 4 ]);
            }
		}
		this.setValue(value);
	}

	/**
	 * This method returns the value of this spectrum event. The returned value is then formated as a String.
	 *
	 * @return the value of this spectrum event.
	 */
	public String getValue_AsString()
	{
		Object value = getValue();
        if (value == null) return GlobalConst.ARCHIVER_NULL_VALUE;
		StringBuffer valueStr = new StringBuffer();
		
        if  ( ( (Object[])value ).length == 0 )
        {
            return "";
        }
        
        if (value instanceof Double[])
		{
			for ( int i = 0 ; i < ((Double[])value).length - 1 ; i++ )
			{
				valueStr.append(((Double[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((Double[])value)[ ((Double[])value).length - 1 ]);
		}
		else if (value instanceof Byte[])
		{
			for ( int i = 0 ; i < ((Byte[])value).length - 1 ; i++ )
			{
				valueStr.append(((Byte[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((Byte[])value)[ ((Byte[])value).length - 1 ]);
		}
		else if (value instanceof Short[])
		{
			for ( int i = 0 ; i < ((Short[])value).length - 1 ; i++ )
			{
				valueStr.append(((Short[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((Short[])value)[ ((Short[])value).length - 1 ]);
		}
		else if (value instanceof Integer[])
		{
			for ( int i = 0 ; i < ((Integer[])value).length - 1 ; i++ )
			{
				valueStr.append(((Integer[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((Integer[])value)[ ((Integer[])value).length - 1 ]);
		}
		else if (value instanceof Float[])
		{
			for ( int i = 0 ; i < ((Float[])value).length - 1 ; i++ )
			{
				valueStr.append(((Float[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((Float[])value)[ ((Float[])value).length - 1 ]);
		}
		else if (value instanceof Boolean[])
		{
            for ( int i = 0 ; i < ((Boolean[])value).length - 1 ; i++ )
			{
				valueStr.append(((Boolean[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((Boolean[])value)[ ((Boolean[])value).length - 1 ]);
		}
		else if (value instanceof String[])
		{
			for ( int i = 0 ; i < ((String[])value).length - 1 ; i++ )
			{
				valueStr.append(((String[])value)[ i ]).append(GlobalConst.CLOB_SEPARATOR);
			}
			valueStr.append(((String[])value)[ ((String[])value).length - 1 ]);
		}
		else
		{
			valueStr.append( value.toString() );
		}
        
		return valueStr.toString();
	}

	/**
	 * Returns an array representation of the object <I>SpectrumEvent_RO</I>.
	 *
	 * @return an array representation of the object <I>SpectrumEvent_RO</I>.
	 */
	public String[] toArray()
	{
		Double[] value = ( Double[] ) getValue();
		String[] hdbSpectrumEvent_ro = new String[ 4 + value.length ];
		hdbSpectrumEvent_ro[ 0 ] = getAttribute_complete_name(); //	name
		hdbSpectrumEvent_ro[ 1 ] = Long.toString(getTimeStamp()).trim(); //	time
		hdbSpectrumEvent_ro[ 2 ] = Integer.toString(value.length);
		hdbSpectrumEvent_ro[ 3 ] = "0";
		for ( int i = 0 ; i < value.length ; i++ )
		{
			hdbSpectrumEvent_ro[ i + 4 ] = "" + value[ i ];
		}
		return hdbSpectrumEvent_ro;

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
