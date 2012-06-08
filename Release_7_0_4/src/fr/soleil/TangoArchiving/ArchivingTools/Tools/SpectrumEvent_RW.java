//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SpectrumEvent_RW.
//						(Chinkumo Jean) -
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.13  2006/10/31 16:54:23  ounsy
// milliseconds and null values management
//
// Revision 1.12  2006/08/23 09:52:42  ounsy
// null value represented as an empty String in getValue_AsString() method
//
// Revision 1.11  2006/05/12 09:23:10  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.10  2006/05/04 14:30:41  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.9  2006/04/11 14:34:54  ounsy
// avoiding negative array size exception
//
// Revision 1.8  2006/03/27 15:19:31  ounsy
// new spectrum types support + better spectrum management
//
// Revision 1.7  2006/02/28 17:05:58  chinkumo
// no message
//
// Revision 1.6  2006/02/24 12:05:18  ounsy
// replaced hard-coded "," value to CLOB_SEPARATOR
//
// Revision 1.5  2006/02/15 11:07:34  chinkumo
// Minor changes made to optimize streams when sending data to DB.
//
// Revision 1.4  2006/02/08 15:42:10  chinkumo
// minor change
//
// Revision 1.3  2006/02/08 13:27:07  chinkumo
// spectrum management enhanced for the getSpectrumValueRWWrite and getSpectrumValueRWRead methods
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



public class SpectrumEvent_RW extends ArchivingEvent
{
	private int dim_x;
	private int dim_y = 0;

	/**
	 * Creates a new instance of Spectrum Event
	 */
	public SpectrumEvent_RW()
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
	public SpectrumEvent_RW(String[] hdbSpectrumEvent_ro)
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

    /**
     * @return
     */
    public Object getSpectrumValueRWRead() 
    {
    	Object value = getValue();
        if ( value == null )
        {
            return null;
        }
        int len = this.dim_x;
		if (value instanceof Double[])
		{
	        Double[] ret = new Double [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((Double[])value) [ i ];
	        }

	        return ret;
		}
		else if (value instanceof Byte[])
		{
			Byte[] ret = new Byte [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((Byte[])value) [ i ];
	        }

	        return ret;
		}
		else if (value instanceof Short[])
		{
			Short[] ret = new Short [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((Short[])value) [ i ];
	        }

	        return ret;
		}
		else if (value instanceof Integer[])
		{
            Integer[] ret = new Integer [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((Integer[])value) [ i ];
	        }

	        return ret;
		}
		else if (value instanceof Float[])
		{
			Float[] ret = new Float [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((Float[])value) [ i ];
	        }

	        return ret;
		}
		else if (value instanceof Boolean[])
		{
			Boolean[] ret = new Boolean [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((Boolean[])value) [ i ];
	        }

	        return ret;
		}
		else if (value instanceof String[])
		{
			String[] ret = new String [ len ];
	        for ( int i = 0 ; i < len ; i ++ )
	        {
	            ret [ i ] = ((String[])value) [ i ];
	        }

	        return ret;
		}
		else
		{
			return null;
		}
    }
    
    public Object getSpectrumValueRWWrite() 
    {
    	Object value = getValue();
        if ( value == null )
        {
            return null;
        }
		if (value instanceof Double[])
		{
	        int len = ((Double[])value).length - this.dim_x;
            if (len < 0) len = 0;
	        Double[] ret = new Double [ len ];
	        for ( int i = this.dim_x ; i < ((Double[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((Double[])value) [ i ];
	        }

	        return ret;       
		}
		else if (value instanceof Byte[])
		{
	        int len = ((Byte[])value).length - this.dim_x;
            if (len < 0) len = 0;
	        Byte[] ret = new Byte [ len ];
	        for ( int i = this.dim_x ; i < ((Byte[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((Byte[])value) [ i ];
	        }

	        return ret;       
		}
		else if (value instanceof Short[])
		{
	        int len = ((Short[])value).length - this.dim_x;
            if (len < 0) len = 0;
	        Short[] ret = new Short [ len ];
	        for ( int i = this.dim_x ; i < ((Short[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((Short[])value) [ i ];
	        }

	        return ret;       
		}
		else if (value instanceof Integer[])
		{
	        int len = ((Integer[])value).length - this.dim_x;
            if (len < 0) len = 0;
            Integer[] ret = new Integer [ len ];
	        for ( int i = this.dim_x ; i < ((Integer[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((Integer[])value) [ i ];
	        }

	        return ret;       
		}
		else if (value instanceof Float[])
		{
	        int len = ((Float[])value).length - this.dim_x;
            if (len < 0) len = 0;
	        Float[] ret = new Float [ len ];
	        for ( int i = this.dim_x ; i < ((Float[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((Float[])value) [ i ];
	        }

	        return ret;       
		}
		else if (value instanceof Boolean[])
		{
	        int len = ((Boolean[])value).length - this.dim_x;
            if (len < 0) len = 0;
	        Boolean[] ret = new Boolean [ len ];
	        for ( int i = this.dim_x ; i < ((Boolean[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((Boolean[])value) [ i ];
	        }

	        return ret;       
		}
		else if (value instanceof String[])
		{
	        int len = ((String[])value).length - this.dim_x;
            if (len < 0) len = 0;
	        String[] ret = new String [ len ];
	        for ( int i = this.dim_x ; i < ((String[])value).length; i ++ )
	        {
	            ret [ i-this.dim_x ] = ((String[])value) [ i ];
	        }

	        return ret;       
		}
		else
		{
			return null;
		}
    }

    /**
     * @return
     */
    public String getSpectrumValueRW_AsString_Read() 
    {
    	Object value = getSpectrumValueRWRead();
        if ( value == null )
        {
            return GlobalConst.ARCHIVER_NULL_VALUE;
        }
		if (value instanceof Double[])
		{
	        return convertDoubleTabToString ( (Double[])value ); 
		}
		else if (value instanceof Byte[])
		{
	        return convertByteTabToString ( (Byte[])value ); 
		}
		else if (value instanceof Short[])
		{
	        return convertShortTabToString ( (Short[])value ); 
		}
		else if (value instanceof Integer[])
		{
	        return convertIntTabToString ( (Integer[])value ); 
		}
		else if (value instanceof Float[])
		{
	        return convertFloatTabToString ( (Float[])value ); 
		}
		else if (value instanceof Boolean[])
		{
	        return convertBooleanTabToString ( (Boolean[])value ); 
		}
		else if (value instanceof String[])
		{
	        return convertStringTabToString ( (String[])value ); 
		}
		else
		{
			return GlobalConst.ARCHIVER_NULL_VALUE;
		}
    }
    
    /**
     * @return
     */
    public String getSpectrumValueRW_AsString_Write() 
    {
    	Object value = getSpectrumValueRWWrite();
        if ( value == null )
        {
            return GlobalConst.ARCHIVER_NULL_VALUE;
        }
		if (value instanceof Double[])
		{
	        return convertDoubleTabToString ( (Double[])value ); 
		}
		else if (value instanceof Byte[])
		{
	        return convertByteTabToString ( (Byte[])value ); 
		}
		else if (value instanceof Short[])
		{
	        return convertShortTabToString ( (Short[])value ); 
		}
		else if (value instanceof Integer[])
		{
	        return convertIntTabToString ( (Integer[])value ); 
		}
		else if (value instanceof Float[])
		{
	        return convertFloatTabToString ( (Float[])value ); 
		}
		else if (value instanceof Boolean[])
		{
	        return convertBooleanTabToString ( (Boolean[])value ); 
		}
		else if (value instanceof String[])
		{
	        return convertStringTabToString ( (String[])value ); 
		}
		else
		{
			return GlobalConst.ARCHIVER_NULL_VALUE;
		}
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertDoubleTabToString(Double[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertByteTabToString(Byte[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertIntTabToString(Integer[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertShortTabToString(Short[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertFloatTabToString(Float[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertBooleanTabToString(Boolean[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

    /**
     * 
     * @param val
     * @return
     */
    private String convertStringTabToString(String[] val) 
    {
        if ( val == null )
        {
            return null;
        }
        StringBuffer valueStr = new StringBuffer();
        
        for ( int j = 0 ; j < val.length ; j ++ )
        {
            valueStr.append ( val [ j ] );
            if ( j < val.length - 1 )
            {
                valueStr.append(GlobalConst.CLOB_SEPARATOR);
            }
        }
        
        return valueStr.toString ();
    }

}
