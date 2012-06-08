//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ScalarEvent.
//						(Chinkumo Jean) - Aug 29, 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.11  2007/06/04 09:03:04  pierrejoseph
// New contructor + value = null treatement in various methods
//
// Revision 1.10  2007/03/20 12:50:09  ounsy
// 1.4 compatibility
//
// Revision 1.9  2007/03/14 09:19:14  ounsy
// added a method avoidUnderFlow ()
//
// Revision 1.8  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.7  2006/07/20 09:24:15  ounsy
// minor changes
//
// Revision 1.6  2006/05/12 09:23:10  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.5  2006/05/04 14:30:41  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.4  2006/03/13 14:46:45  ounsy
// State as an int management
// Long as an int management
//
// Revision 1.3  2006/03/10 11:31:00  ounsy
// state and string support
//
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.2.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.1.2.1  2005/09/09 08:21:24  chinkumo
// First commit !
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;

public class ScalarEvent extends ArchivingEvent
{
    private static final String MIN_VALUE = "1e-100";
    private static double minAbsoluteValue = Double.parseDouble ( MIN_VALUE ); 
    
	/**
	 * Creates a new instance of DhdbEvent
	 */
	public ScalarEvent()
	{
		super();
	}

	public ScalarEvent(String att_name,int data_type,int writable,long timestamp,Object value)
	{
		super();
		this.setAttribute_complete_name(att_name);
		this.setData_format(AttrDataFormat._SCALAR);
		this.setData_type(data_type);
		this.setWritable(writable);
		this.setTimeStamp(timestamp);
		setValue(value);		
	}
	
	/**
	 * Creates a new instance of DhdbEvent
	 */
	/*public ScalarEvent(String[] scalarEvent)
	{
		this.setAttribute_complete_name(scalarEvent[ 0 ]);
		this.setData_format(AttrDataFormat._SCALAR);
		this.setData_type(Integer.parseInt(scalarEvent[ 2 ]));
		this.setWritable(Integer.parseInt(scalarEvent[ 3 ]));
		this.setTimeStamp(Long.parseLong(scalarEvent[ 4 ]));
		this.setTable_name(scalarEvent[ 5 ]);
		Object value = getValue(scalarEvent);
		setValue(value);

	}

	public Object getValue(String[] scalarEvent)
	{
		Object value = new Object();
		switch ( getWritable() )
		{
			case AttrWriteType._READ:
				value = new Object();
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						value = new String(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_STATE:
						value = new Integer(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_UCHAR:
						value = new Byte(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_LONG:
						value = new Integer(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_ULONG:
						value = new Integer(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						value = new Boolean(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_SHORT:
						value = new Short(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_USHORT:
						value = new Short(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_FLOAT:
						value = new Float(( String ) scalarEvent[ 6 ]);
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						value = new Double(( String ) scalarEvent[ 6 ]);
						break;
					default:
						value = null;
						break;
				}
				break;

			case AttrWriteType._READ_WITH_WRITE:
				value = new Object[ 2 ];
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						String[] valueString = {new String(scalarEvent[ 6 ]), new String(scalarEvent[ 7 ])};
						value = valueString;
						break;
					case TangoConst.Tango_DEV_STATE:
						Integer[] valueInteger = {new Integer(scalarEvent[ 6 ]), new Integer(scalarEvent[ 7 ])};
						value = valueInteger;
						break;
					case TangoConst.Tango_DEV_UCHAR:
						Byte[] valueByte = {new Byte(scalarEvent[ 6 ]), new Byte(scalarEvent[ 7 ])};
						value = valueByte;
						break;
					case TangoConst.Tango_DEV_LONG:
                        Integer[] valueLong = {new Integer(scalarEvent[ 6 ]), new Integer(scalarEvent[ 7 ])};
						value = valueLong;
						break;
					case TangoConst.Tango_DEV_ULONG:
                        Integer[] valueULong = {new Integer(scalarEvent[ 6 ]), new Integer(scalarEvent[ 7 ])};
						value = valueULong;
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						Boolean[] valueBoolean = {new Boolean(scalarEvent[ 6 ]), new Boolean(scalarEvent[ 7 ])};
						value = valueBoolean;
						break;
					case TangoConst.Tango_DEV_SHORT:
						Short[] valueShort = {new Short(scalarEvent[ 6 ]), new Short(scalarEvent[ 7 ])};
						value = valueShort;
						break;
					case TangoConst.Tango_DEV_USHORT:
						Short[] valueUShort = {new Short(scalarEvent[ 6 ]), new Short(scalarEvent[ 7 ])};
						value = valueUShort;
						break;
					case TangoConst.Tango_DEV_FLOAT:
						Float[] valueFloat = {new Float(scalarEvent[ 6 ]), new Float(scalarEvent[ 7 ])};
						value = valueFloat;
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						Double[] valueDouble = {new Double(scalarEvent[ 6 ]), new Double(scalarEvent[ 7 ])};
						value = valueDouble;
						break;
					default:
						value = null;
						break;
				}
				break;

			case AttrWriteType._WRITE:
				value = new Object();
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						value = new String(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_STATE:
						value = new Integer(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_UCHAR:
						value = new Byte(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_LONG:
						value = new Integer(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_ULONG:
						value = new Integer(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						value = new Boolean(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_SHORT:
						value = new Short(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_USHORT:
						value = new Short(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_FLOAT:
						value = new Float(( String ) scalarEvent[ 7 ]);
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						value = new Double(( String ) scalarEvent[ 7 ]);
						break;
					default:
						value = null;
						break;
				}
				break;

			case AttrWriteType._READ_WRITE:
				value = new Object[ 2 ];
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						String[] valueString = {new String(scalarEvent[ 6 ]), new String(scalarEvent[ 7 ])};
						value = valueString;
						break;
					case TangoConst.Tango_DEV_STATE:
						Integer[] valueInteger = {new Integer(scalarEvent[ 6 ]), new Integer(scalarEvent[ 7 ])};
						value = valueInteger;
						break;
					case TangoConst.Tango_DEV_UCHAR:
						Byte[] valueByte = {new Byte(scalarEvent[ 6 ]), new Byte(scalarEvent[ 7 ])};
						value = valueByte;
						break;
					case TangoConst.Tango_DEV_LONG:
                        Integer[] valueLong = {new Integer(scalarEvent[ 6 ]), new Integer(scalarEvent[ 7 ])};
						value = valueLong;
						break;
					case TangoConst.Tango_DEV_ULONG:
                        Integer[] valueULong = {new Integer(scalarEvent[ 6 ]), new Integer(scalarEvent[ 7 ])};
						value = valueULong;
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						Boolean[] valueBoolean = {new Boolean(scalarEvent[ 6 ]), new Boolean(scalarEvent[ 7 ])};
						value = valueBoolean;
						break;
					case TangoConst.Tango_DEV_SHORT:
						Short[] valueShort = {new Short(scalarEvent[ 6 ]), new Short(scalarEvent[ 7 ])};
						value = valueShort;
						break;
					case TangoConst.Tango_DEV_USHORT:
						Short[] valueUShort = {new Short(scalarEvent[ 6 ]), new Short(scalarEvent[ 7 ])};
						value = valueUShort;
						break;
					case TangoConst.Tango_DEV_FLOAT:
						Float[] valueFloat = {new Float(scalarEvent[ 6 ]), new Float(scalarEvent[ 7 ])};
						value = valueFloat;
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						Double[] valueDouble = {new Double(scalarEvent[ 6 ]), new Double(scalarEvent[ 7 ])};
						value = valueDouble;
						break;
					default:
						value = null;
						break;
				}
				break;
		}
		return value;
	}
*/
	public Object getReadValue()
	{
		if(getValue() == null) return null;
		
		Object read_value = new Object();
		switch ( getWritable() )
		{
			case AttrWriteType._READ:
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						read_value = ( String ) getValue();
						break;
					case TangoConst.Tango_DEV_STATE:
                        if (getValue() instanceof DevState)
                        {
                            read_value = ( DevState ) getValue();
                        }
                        else if ((getValue() instanceof Integer[]))
                        {
                            read_value = ( Integer[] ) getValue();
                        }
                        else read_value = ( String ) getValue();
						break;
					case TangoConst.Tango_DEV_UCHAR:
						read_value = ( Byte ) getValue();
						break;
					case TangoConst.Tango_DEV_LONG:
						read_value = ( Integer ) getValue();
						break;
					case TangoConst.Tango_DEV_ULONG:
						read_value = ( Integer ) getValue();
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						read_value = ( Boolean ) getValue();
						break;
					case TangoConst.Tango_DEV_SHORT:
						read_value = ( Short ) getValue();
						break;
					case TangoConst.Tango_DEV_USHORT:
						read_value = ( Short ) getValue();
						break;
					case TangoConst.Tango_DEV_FLOAT:
						read_value = ( Float ) getValue();
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						read_value = ( Double ) getValue();
						break;
					default:
						read_value = null;
						break;
				}
				break;
			case AttrWriteType._WRITE:
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						read_value = ( String ) getValue();
						break;
					case TangoConst.Tango_DEV_STATE:
                        if (getValue() instanceof DevState)
                        {
                            read_value = ( DevState ) getValue();
                        }
                        else if ((getValue() instanceof Integer[]))
                        {
                            read_value = ( Integer[] ) getValue();
                        }
                        else read_value = ( String ) getValue();
                        break;
					case TangoConst.Tango_DEV_UCHAR:
						read_value = ( Byte ) getValue();
						break;
					case TangoConst.Tango_DEV_LONG:
						read_value = ( Integer ) getValue();
						break;
					case TangoConst.Tango_DEV_ULONG:
						read_value = ( Integer ) getValue();
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						read_value = ( Boolean ) getValue();
						break;
					case TangoConst.Tango_DEV_SHORT:
						read_value = ( Short ) getValue();
						break;
					case TangoConst.Tango_DEV_USHORT:
						read_value = ( Short ) getValue();
						break;
					case TangoConst.Tango_DEV_FLOAT:
						read_value = ( Float ) getValue();
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						read_value = ( Double ) getValue();
						break;
					default:
						read_value = null;
						break;
				}
				break;
            case AttrWriteType._READ_WITH_WRITE:
			case AttrWriteType._READ_WRITE:
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						read_value = ( ( String[] ) getValue() )[ 0 ];
						break;
					case TangoConst.Tango_DEV_STATE:
                        if (getValue() instanceof DevState)
                        {
                            read_value = ( DevState ) getValue();
                        }
                        else if ((getValue() instanceof Integer[]))
                        {
                            read_value = ( Integer[] ) getValue();
                        }
                        else read_value = ( String ) getValue();
                        break;
					case TangoConst.Tango_DEV_UCHAR:
						read_value = ( ( Byte[] ) getValue() )[ 0 ];
						break;
					case TangoConst.Tango_DEV_LONG:
					case TangoConst.Tango_DEV_ULONG:
						read_value =( ( Integer[] ) getValue() )[ 0 ];
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						read_value = ( ( Boolean[] ) getValue() )[ 0 ];
						break;
					case TangoConst.Tango_DEV_SHORT:
					case TangoConst.Tango_DEV_USHORT:
						read_value = ( ( Short[] ) getValue() )[ 0 ];
						break;
					case TangoConst.Tango_DEV_FLOAT:
						read_value = ( ( Float[] ) getValue() )[ 0 ];
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						read_value = ( ( Double[] ) getValue() )[ 0 ];
						break;
					default:
						read_value = null;
						break;
				}
				break;
		}

		return read_value;
	}

	/**
	 * Returns an array representation of the object <I>ArchivingEvent</I>.
	 *
	 * @return an array representation of the object <I>ArchivingEvent</I>.
	 */
	public String[] toArray()
	{
		String[] scalarEvent;
		scalarEvent = new String[ 8 ];
		scalarEvent[ 0 ] = getAttribute_complete_name();
		scalarEvent[ 1 ] = Integer.toString(getData_format());
		scalarEvent[ 2 ] = Integer.toString(getData_type());
		scalarEvent[ 3 ] = Integer.toString(getWritable());
		scalarEvent[ 4 ] = Long.toString(getTimeStamp());
		scalarEvent[ 5 ] = getTable_name();
		scalarEvent[ 6 ] = valueToString(0);
		scalarEvent[ 7 ] = valueToString(1);
		return scalarEvent;
	}

	public String valueToString(int pos)
	{
        if (getValue() == null) return GlobalConst.ARCHIVER_NULL_VALUE;
        if (getValue() instanceof Object[] && ((Object[])getValue())[pos] == null) return GlobalConst.ARCHIVER_NULL_VALUE;
		String value = GlobalConst.ARCHIVER_NULL_VALUE;
		switch ( getData_format() )
		{
			case AttrDataFormat._SCALAR:
				switch ( getWritable() )
				{
					case AttrWriteType._READ:
						if ( pos == 0 )
						{
							switch ( getData_type() )
							{
								case TangoConst.Tango_DEV_STRING:
									value = ( String ) getValue();
									break;
								case TangoConst.Tango_DEV_STATE:
                                    if (getValue() instanceof DevState)
                                    {
                                        value = "" + (( DevState ) getValue()).value();
                                    }
                                    else if ((getValue() instanceof Integer[]))
                                    {
                                        value = "";
                                        Integer[] tab = ( Integer[] ) getValue();
                                         for (int i = 0; i < tab.length; i++)
                                        {
                                            value += tab[i] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        if (tab.length > 0)
                                        {
                                            value = value.substring(0, value.length() - 1);
                                        }
                                    }
                                    else value = ( String ) getValue();
                                    break;
								case TangoConst.Tango_DEV_UCHAR:
									value = ( ( Byte ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_LONG:
									value = ( ( Integer ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_ULONG:
									value = ( ( Integer ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_BOOLEAN:
									value = ( ( Boolean ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_SHORT:
									value = ( ( Short ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_USHORT:
									value = ( ( Short ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_FLOAT:
									value = ( ( Float ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_DOUBLE:
									value = ( ( Double ) getValue() ).toString();
									break;
								default:
									value = GlobalConst.ARCHIVER_NULL_VALUE;
									break;
							}
						}
						break;
					case AttrWriteType._READ_WITH_WRITE:
						switch ( getData_type() )
						{
							case TangoConst.Tango_DEV_STRING:
								value = ( ( String[] ) getValue() )[ pos ];
								break;
							case TangoConst.Tango_DEV_STATE:
                                if (getValue() instanceof DevState)
                                {
                                    value = "" + (( DevState ) getValue()).value();
                                }
                                else if ((getValue() instanceof Integer[]))
                                {
                                    value = "";
                                    Integer[] tab = ( Integer[] ) getValue();
                                    value += tab[pos];
                                }
                                else value = ( ( String[] ) getValue() )[ pos ];
                                break;
							case TangoConst.Tango_DEV_UCHAR:
								value = (( Byte[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_LONG:
							case TangoConst.Tango_DEV_ULONG:
								value = ( ( Integer[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_BOOLEAN:
								value = ( ( Boolean[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_SHORT:
							case TangoConst.Tango_DEV_USHORT:
								value = ( ( Short[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_FLOAT:
								value = ( ( Float[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_DOUBLE:
								value = ( ( Double[] ) getValue() )[ pos ] + "";
								break;
							default:
								value = GlobalConst.ARCHIVER_NULL_VALUE;
								break;
						}
						break;
					case AttrWriteType._WRITE:
						if ( pos == 1 )
						{
							switch ( getData_type() )
							{
								case TangoConst.Tango_DEV_STRING:
									value = ( String ) getValue();
									break;
								case TangoConst.Tango_DEV_STATE:
                                    if (getValue() instanceof DevState)
                                    {
                                        value = "" + (( DevState ) getValue()).value();
                                    }
                                    else if ((getValue() instanceof Integer[]))
                                    {
                                        value = "";
                                        Integer[] tab = ( Integer[] ) getValue();
                                        for (int i = 0; i < tab.length; i++)
                                        {
                                            value += tab[i] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        if (tab.length > 0)
                                        {
                                            value = value.substring(0, value.length() - 1);
                                        }
                                    }
                                    else value = ( String ) getValue();
                                    break;
								case TangoConst.Tango_DEV_UCHAR:
									value = ( ( Byte ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_LONG:
									value = ( ( Integer ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_ULONG:
									value = ( ( Integer ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_BOOLEAN:
									value = ( ( Boolean ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_SHORT:
									value = ( ( Short ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_USHORT:
									value = ( ( Short ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_FLOAT:
									value = ( ( Float ) getValue() ).toString();
									break;
								case TangoConst.Tango_DEV_DOUBLE:
									value = ( ( Double ) getValue() ).toString();
									break;
								default:
									value = GlobalConst.ARCHIVER_NULL_VALUE;
									break;
							}
						}
						break;
					case AttrWriteType._READ_WRITE:
						switch ( getData_type() )
						{
							case TangoConst.Tango_DEV_STRING:
								value = ( ( String[] ) getValue() )[ pos ];
								break;
							case TangoConst.Tango_DEV_STATE:
                                if (getValue() instanceof DevState)
                                {
                                    value = "" + (( DevState ) getValue()).value();
                                }
                                else if ((getValue() instanceof Integer[]))
                                {
                                    value = "";
                                    Integer[] tab = ( Integer[] ) getValue();
                                    value += tab[pos];
                                }
                                else value = ( ( String[] ) getValue() )[ pos ];
                                break;
							case TangoConst.Tango_DEV_UCHAR:
								value = ( ( Byte[] ) getValue() )[ pos ] + "";
								break;
                            case TangoConst.Tango_DEV_ULONG:
							case TangoConst.Tango_DEV_LONG:
								value = ( ( Integer[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_BOOLEAN:
								value = ( ( Boolean[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
								value = ( ( Short[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_FLOAT:
								value = ( ( Float[] ) getValue() )[ pos ] + "";
								break;
							case TangoConst.Tango_DEV_DOUBLE:
								value = ( ( Double[] ) getValue() )[ pos ] + "";
								break;
							default:
								value = GlobalConst.ARCHIVER_NULL_VALUE;
								break;
						}
						break;
				}
				break;
			case AttrDataFormat._SPECTRUM:
			case AttrDataFormat._IMAGE:
				value = getValue().toString();
				break;
		}
		return value;
	}

	public String toString()
	{
		String scEvSt = "";
		String value =
		        ( ( getWritable() == AttrWriteType._READ || getWritable() == AttrWriteType._READ_WITH_WRITE || getWritable() == AttrWriteType._READ_WRITE ) ?
		          "read value :  " + valueToString(0) : "" ) +
		        ( ( getWritable() == AttrWriteType._WRITE || getWritable() == AttrWriteType._READ_WITH_WRITE || getWritable() == AttrWriteType._READ_WRITE ) ?
		          "\t " + "write value : " + valueToString(1) : "" );
		scEvSt =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"TimeSt : \t" + getTimeStamp() + "\r\n" +
		"Value  : \t" + value + "\r\n";
		return scEvSt;
	}

    public void avoidUnderFlow()
    {
        if ( super.getData_type () != TangoConst.Tango_DEV_DOUBLE || super.getValue() == null)
        {
            return;
        }
        
        switch ( super.getWritable() )
        {
            case AttrWriteType._READ:
            case AttrWriteType._WRITE:    
                Double valueEither = (Double) getValue ();
                valueEither = avoidUnderFlow ( valueEither );
                super.setValue ( valueEither );
            break;
                
            case AttrWriteType._READ_WRITE:
            case AttrWriteType._READ_WITH_WRITE:
                Double [] valueBoth = (Double[]) getValue ();
                valueBoth [ 0 ] = avoidUnderFlow ( valueBoth [ 0 ] );
                valueBoth [ 1 ] = avoidUnderFlow ( valueBoth [ 1 ] );
                super.setValue ( valueBoth );
            break;
        }
    }

    private Double avoidUnderFlow(Double value) 
    {
        if (value == null) {
            return value;
        }
        else {
            double absoluteValue = Math.abs ( value.doubleValue() );
            double ret;
            if ( absoluteValue < minAbsoluteValue )
            {
                ret = 0.0;
            }
            else
            {
                ret = value.doubleValue();
            }
            return new Double(ret);
        }
    }
}

