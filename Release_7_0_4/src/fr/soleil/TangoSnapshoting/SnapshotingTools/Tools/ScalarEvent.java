package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import java.sql.Timestamp;
import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.TangoConst;

//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ScalarEvent.
//						(chinkumo) - 7 nov. 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.7  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.6  2006/05/12 09:26:20  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.5  2006/05/04 14:35:20  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.4  2006/03/13 15:19:43  ounsy
// State as an int management
//
// Revision 1.3  2006/03/13 15:14:11  ounsy
// Long as an int management
//
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.2.1  2005/11/15 13:36:38  chinkumo
// first commit
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

public class ScalarEvent extends SnapAttribute
{

	public ScalarEvent()
	{
		super();
	}

	public ScalarEvent(String[] scalarEvent)
	{
		this.setAttribute_complete_name(scalarEvent[ 0 ]);
		this.setData_format(AttrDataFormat._SCALAR);
		this.setData_type(Integer.parseInt(scalarEvent[ 2 ]));
		this.setWritable(Integer.parseInt(scalarEvent[ 3 ]));
		setId_att(Integer.parseInt(scalarEvent[ 4 ]));
		setId_snap(Integer.parseInt(scalarEvent[ 5 ]));
		setSnap_date(Timestamp.valueOf(scalarEvent[ 6 ]));

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

			case AttrWriteType._READ_WITH_WRITE:
				value = new Object[ 2 ];
				switch ( getData_type() )
				{
					case TangoConst.Tango_DEV_STRING:
						String[] valueString = {new String(scalarEvent[ 7 ]), new String(scalarEvent[ 8 ])};
						value = valueString;
						break;
					case TangoConst.Tango_DEV_STATE:
						Integer[] valueInteger = {new Integer(scalarEvent[ 7 ]), new Integer(scalarEvent[ 8 ])};
						value = valueInteger;
						break;
					case TangoConst.Tango_DEV_UCHAR:
						Byte[] valueByte = {new Byte(scalarEvent[ 7 ]), new Byte(scalarEvent[ 8 ])};
						value = valueByte;
						break;
					case TangoConst.Tango_DEV_LONG:
                        Integer[] valueLong = {new Integer(scalarEvent[ 7 ]), new Integer(scalarEvent[ 8 ])};
						value = valueLong;
						break;
					case TangoConst.Tango_DEV_ULONG:
                        Integer[] valueULong = {new Integer(scalarEvent[ 7 ]), new Integer(scalarEvent[ 8 ])};
						value = valueULong;
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						Boolean[] valueBoolean = {new Boolean(scalarEvent[ 7 ]), new Boolean(scalarEvent[ 8 ])};
						value = valueBoolean;
						break;
					case TangoConst.Tango_DEV_SHORT:
						Short[] valueShort = {new Short(scalarEvent[ 7 ]), new Short(scalarEvent[ 8 ])};
						value = valueShort;
						break;
					case TangoConst.Tango_DEV_USHORT:
						Short[] valueUShort = {new Short(scalarEvent[ 7 ]), new Short(scalarEvent[ 8 ])};
						value = valueUShort;
						break;
					case TangoConst.Tango_DEV_FLOAT:
						Float[] valueFloat = {new Float(scalarEvent[ 7 ]), new Float(scalarEvent[ 8 ])};
						value = valueFloat;
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						Double[] valueDouble = {new Double(scalarEvent[ 7 ]), new Double(scalarEvent[ 8 ])};
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
						value = new String(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_STATE:
						value = new Integer(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_UCHAR:
						value = new Byte(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_LONG:
						value = new Integer(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_ULONG:
						value = new Integer(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						value = new Boolean(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_SHORT:
						value = new Short(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_USHORT:
						value = new Short(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_FLOAT:
						value = new Float(( String ) scalarEvent[ 8 ]);
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						value = new Double(( String ) scalarEvent[ 8 ]);
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
						String[] valueString = {new String(scalarEvent[ 7 ]), new String(scalarEvent[ 8 ])};
						value = valueString;
						break;
					case TangoConst.Tango_DEV_STATE:
						Integer[] valueInteger = {new Integer(scalarEvent[ 7 ]), new Integer(scalarEvent[ 8 ])};
						value = valueInteger;
						break;
					case TangoConst.Tango_DEV_UCHAR:
						Byte[] valueByte = {new Byte(scalarEvent[ 7 ]), new Byte(scalarEvent[ 8 ])};
						value = valueByte;
						break;
					case TangoConst.Tango_DEV_LONG:
                        Integer[] valueLong = {new Integer(scalarEvent[ 7 ]), new Integer(scalarEvent[ 8 ])};
						value = valueLong;
						break;
					case TangoConst.Tango_DEV_ULONG:
                        Integer[] valueULong = {new Integer(scalarEvent[ 7 ]), new Integer(scalarEvent[ 8 ])};
						value = valueULong;
						break;
					case TangoConst.Tango_DEV_BOOLEAN:
						Boolean[] valueBoolean = {new Boolean(scalarEvent[ 7 ]), new Boolean(scalarEvent[ 8 ])};
						value = valueBoolean;
						break;
					case TangoConst.Tango_DEV_SHORT:
						Short[] valueShort = {new Short(scalarEvent[ 7 ]), new Short(scalarEvent[ 8 ])};
						value = valueShort;
						break;
					case TangoConst.Tango_DEV_USHORT:
						Short[] valueUShort = {new Short(scalarEvent[ 7 ]), new Short(scalarEvent[ 8 ])};
						value = valueUShort;
						break;
					case TangoConst.Tango_DEV_FLOAT:
						Float[] valueFloat = {new Float(scalarEvent[ 7 ]), new Float(scalarEvent[ 8 ])};
						value = valueFloat;
						break;
					case TangoConst.Tango_DEV_DOUBLE:
						Double[] valueDouble = {new Double(scalarEvent[ 7 ]), new Double(scalarEvent[ 8 ])};
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

	public String[] toArray()
	{
		String[] scalarEvent;
		scalarEvent = new String[ 9 ];
		scalarEvent[ 0 ] = getAttribute_complete_name();
		scalarEvent[ 1 ] = Integer.toString(getData_format());
		scalarEvent[ 2 ] = Integer.toString(getData_type());
		scalarEvent[ 3 ] = Integer.toString(getWritable());
		scalarEvent[ 4 ] = Integer.toString(getId_att());
		scalarEvent[ 5 ] = Integer.toString(getId_snap());
		scalarEvent[ 6 ] = getSnap_date().toString();
		scalarEvent[ 7 ] = valueToString(0);
		scalarEvent[ 8 ] = valueToString(1);
		return scalarEvent;
	}

	public String valueToString(int pos)
	{
		String nullvalue = "NULL";
		String value = nullvalue;
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
									value = nullvalue;
									break;
							}
						}
						break;
                    case AttrWriteType._READ_WRITE:
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
								value = ( ( Byte[] ) getValue() )[ pos ] + "";
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
								value = nullvalue;
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
									value = nullvalue;
									break;
							}
						}
						break;
				}
				break;
			case AttrDataFormat._SPECTRUM:
				value = ( ( String ) getValue() ).toString();
				break;
			case AttrDataFormat._IMAGE:
				value = ( ( String ) getValue() ).toString();
				break;
		}
		return value;
	}
}
