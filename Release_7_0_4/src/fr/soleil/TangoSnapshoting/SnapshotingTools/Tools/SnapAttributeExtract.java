//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapAttributeExtract.
//						(Chinkumo Jean) - Nov 14, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.13  2006/10/31 16:54:24  ounsy
// milliseconds and null values management
//
// Revision 1.12  2006/05/12 09:26:20  ounsy
// CLOB_SEPARATOR in GlobalConst
//
// Revision 1.11  2006/05/04 14:35:20  ounsy
// CLOB_SEPARATOR centralized in ConfigConst
//
// Revision 1.10  2006/04/13 12:47:24  ounsy
// new spectrum types support
//
// Revision 1.9  2006/03/29 15:05:33  ounsy
// added protections against null values
//
// Revision 1.8  2006/03/14 12:36:24  ounsy
// corrected the SNAP/spectrums/RW problem
// about the read and write values having the same length
//
// Revision 1.7  2006/02/28 17:05:58  chinkumo
// no message
//
// Revision 1.6  2006/02/24 12:06:29  ounsy
// replaced hard-coded "," value to CLOB_SEPARATOR
//
// Revision 1.5  2006/02/17 09:26:46  chinkumo
// Minor change : code reformated.
//
// Revision 1.4  2006/02/15 09:06:28  ounsy
// Spectrums Management
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.2.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2.2.1  2005/09/09 08:44:53  chinkumo
// Minor changes.
//
// Revision 1.2  2005/08/19 14:04:02  chinkumo
// no message
//
// Revision 1.1.14.1  2005/08/11 08:34:58  chinkumo
// Changes was made since the 'SetEquipement' functionnality was added.
// The scalar type management was also improved.
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

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst;


public class SnapAttributeExtract extends SnapAttribute
{
    //private int data_type = 0;
    //private int data_format = 0;
    //private int writable = 0;
    // Everything here is already available in SnapAttribute
    private int dimX; 
    
    public SnapAttributeExtract(String[] argin)
    {
        setAttribute_complete_name(argin[ 0 ]);
        setId_att(Integer.parseInt(argin[ 1 ]));
        data_type = Integer.parseInt(argin[ 2 ]);
        data_format = Integer.parseInt(argin[ 3 ]);
        writable = Integer.parseInt(argin[ 4 ]);
        Object value = new Object();

        switch ( data_format )
        {
            case AttrDataFormat._SCALAR:
                switch ( writable )
                {
                    case AttrWriteType._READ:
                        value = new Object();
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                value = new String(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                value = new Integer(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                value = new Byte(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                value = new Long(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                value = new Long(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                value = new Boolean(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                value = new Short(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                value = new Float(( String ) argin[ 5 ]);
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                value = new Double(( String ) argin[ 5 ]);
                                break;
                            default:
                                value = new Double(( String ) argin[ 5 ]);
                                break;
                        }
                        break;

                    case AttrWriteType._READ_WITH_WRITE:
                        value = new Object[ 2 ];
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                String[] valueString = {new String(argin[ 5 ]), new String(argin[ 6 ])};
                                value = valueString;
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                Integer[] valueInteger = {new Integer(argin[ 5 ]), new Integer(argin[ 6 ])};
                                value = valueInteger;
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                Byte[] valueByte = {new Byte(argin[ 5 ]), new Byte(argin[ 6 ])};
                                value = valueByte;
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                Long[] valueLong = {new Long(argin[ 5 ]), new Long(argin[ 6 ])};
                                value = valueLong;
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                Long[] valueULong = {new Long(argin[ 5 ]), new Long(argin[ 6 ])};
                                value = valueULong;
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                Boolean[] valueBoolean = {new Boolean(argin[ 5 ]), new Boolean(argin[ 6 ])};
                                value = valueBoolean;
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                Short[] valueShort = {new Short(argin[ 5 ]), new Short(argin[ 6 ])};
                                value = valueShort;
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                Float[] valueFloat = {new Float(argin[ 5 ]), new Float(argin[ 6 ])};
                                value = valueFloat;
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                Double[] valueDouble = {new Double(argin[ 5 ]), new Double(argin[ 6 ])};
                                value = valueDouble;
                                break;
                            default:
                                Double[] valueDouble2 = {new Double(argin[ 5 ]), new Double(argin[ 6 ])};
                                value = valueDouble2;
                                break;
                        }
                        break;

                    case AttrWriteType._WRITE:
                        value = new Object();
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                value = new String(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                value = new Integer(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                value = new Byte(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                value = new Long(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                value = new Long(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                value = new Boolean(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                value = new Short(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                value = new Float(( String ) argin[ 6 ]);
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                value = new Double(( String ) argin[ 6 ]);
                                break;
                            default:
                                value = new Double(( String ) argin[ 6 ]);
                                break;
                        }
                        break;

                    case AttrWriteType._READ_WRITE:
                        value = new Object[ 2 ];
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                String[] valueString = {new String(argin[ 5 ]), new String(argin[ 6 ])};
                                value = valueString;
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                Integer[] valueInteger = {new Integer(argin[ 5 ]), new Integer(argin[ 6 ])};
                                value = valueInteger;
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                Byte[] valueByte = {new Byte(argin[ 5 ]), new Byte(argin[ 6 ])};
                                value = valueByte;
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                Long[] valueLong = {new Long(argin[ 5 ]), new Long(argin[ 6 ])};
                                value = valueLong;
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                Long[] valueULong = {new Long(argin[ 5 ]), new Long(argin[ 6 ])};
                                value = valueULong;
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                Boolean[] valueBoolean = {new Boolean(argin[ 5 ]), new Boolean(argin[ 6 ])};
                                value = valueBoolean;
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                Short[] valueShort = {new Short(argin[ 5 ]), new Short(argin[ 6 ])};
                                value = valueShort;
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                Float[] valueFloat = {new Float(argin[ 5 ]), new Float(argin[ 6 ])};
                                value = valueFloat;
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                Double[] valueDouble = {new Double(argin[ 5 ]), new Double(argin[ 6 ])};
                                value = valueDouble;
                                break;
                            default:
                                Double[] valueDouble2 = {new Double(argin[ 5 ]), new Double(argin[ 6 ])};
                                value = valueDouble2;
                                break;
                        }
                        break;
                }
                break;
            case AttrDataFormat._SPECTRUM:
                String toSplitRead = argin[ 5 ];
                String toSplitWrite = argin[ 6 ];
                String[] stringArrayRead;
                String[] stringArrayWrite;
                switch ( writable )
                {
                    case AttrWriteType._WRITE:
                        if ( toSplitWrite == null
                             || "NaN".equalsIgnoreCase(toSplitWrite.trim())
                             || "[]".equalsIgnoreCase(toSplitWrite.trim()) )
                        {
                            value = "NaN";
                            break;
                        }
                        stringArrayWrite = toSplitWrite.substring(1 , toSplitWrite.length() - 1).split(GlobalConst.CLOB_SEPARATOR);
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_BOOLEAN:
                                value = new Boolean[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Boolean[] ) value )[ i ] = new Boolean(Double.valueOf(stringArrayWrite[ i ]).byteValue()!=0);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Boolean[] ) value )[ i ] = new Boolean("true".equalsIgnoreCase(stringArrayWrite[ i ]));
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_STRING:
                                value = new String[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    ( ( String[] ) value )[ i ] = new String(stringArrayWrite[ i ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_CHAR:
                            case TangoConst.Tango_DEV_UCHAR:
                                value = new Byte[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Byte[] ) value )[ i ] = Byte.valueOf(stringArrayWrite[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Byte[] ) value )[ i ] = new Byte(Double.valueOf(stringArrayWrite[ i ]).byteValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                                value = new Integer[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Integer[] ) value )[ i ] = Integer.valueOf(stringArrayWrite[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Integer[] ) value )[ i ] = new Integer(Double.valueOf(stringArrayWrite[ i ]).intValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_SHORT:
                                value = new Short[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Short[] ) value )[ i ] = Short.valueOf(stringArrayWrite[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Short[] ) value )[ i ] = new Short(Double.valueOf(stringArrayWrite[ i ]).shortValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                value = new Float[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    ( ( Float[] ) value )[ i ] = Float.valueOf(stringArrayWrite[ i ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                value = new Double[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    ( ( Double[] ) value )[ i ] = Double.valueOf(stringArrayWrite[ i ]);
                                }
                                break;
                            default :
                                value = "NaN";
                        }
                        break;
                    case AttrWriteType._READ:
                        if ( toSplitRead == null
                             || "NaN".equalsIgnoreCase(toSplitRead.trim())
                             || "[]".equalsIgnoreCase(toSplitRead.trim()) )
                        {
                            value = "NaN";
                            break;
                        }
                        stringArrayRead = toSplitRead.substring(1 , toSplitRead.length() - 1).split(GlobalConst.CLOB_SEPARATOR);
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_BOOLEAN:
                                value = new Boolean[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Boolean[] ) value )[ i ] = new Boolean(Double.valueOf(stringArrayRead[ i ]).byteValue()!=0);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Boolean[] ) value )[ i ] = new Boolean("true".equalsIgnoreCase(stringArrayRead[ i ]));
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_STRING:
                                value = new String[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    ( ( String[] ) value )[ i ] = new String(stringArrayRead[ i ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_CHAR:
                            case TangoConst.Tango_DEV_UCHAR:
                                value = new Byte[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Byte[] ) value )[ i ] = Byte.valueOf(stringArrayRead[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Byte[] ) value )[ i ] = new Byte(Double.valueOf(stringArrayRead[ i ]).byteValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_STATE:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                                value = new Integer[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Integer[] ) value )[ i ] = Integer.valueOf(stringArrayRead[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Integer[] ) value )[ i ] = new Integer(Double.valueOf(stringArrayRead[ i ]).intValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_SHORT:
                                value = new Short[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Short[] ) value )[ i ] = Short.valueOf(stringArrayRead[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Short[] ) value )[ i ] = new Short(Double.valueOf(stringArrayRead[ i ]).shortValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                value = new Float[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    ( ( Float[] ) value )[ i ] = Float.valueOf(stringArrayRead[ i ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                value = new Double[ stringArrayRead.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    ( ( Double[] ) value )[ i ] = Double.valueOf(stringArrayRead[ i ]);
                                }
                                break;
                            default :
                                value = "NaN";
                        }
                        break;

                    case AttrWriteType._READ_WRITE:
                    case AttrWriteType._READ_WITH_WRITE:
                        if ( toSplitWrite == null
                             || "NaN".equalsIgnoreCase(toSplitWrite.trim())
                             || "[]".equalsIgnoreCase(toSplitWrite.trim()) )
                        {
                            value = "NaN";
                            break;
                        }
                        if ( toSplitRead == null
                             || "NaN".equalsIgnoreCase(toSplitRead.trim())
                             || "[]".equalsIgnoreCase(toSplitRead.trim()) )
                        {
                            value = "NaN";
                            break;
                        }
                        stringArrayRead = toSplitRead.substring(1 , toSplitRead.length() - 1).split(GlobalConst.CLOB_SEPARATOR);
                        stringArrayWrite = toSplitWrite.substring(1 , toSplitWrite.length() - 1).split(GlobalConst.CLOB_SEPARATOR);
                        value = new Object[ 2 ];
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_BOOLEAN:
                                ( ( Object[] ) value )[ 0 ] = new Boolean[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new Boolean[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Boolean[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = new Boolean(Double.valueOf(stringArrayRead[ i ]).byteValue()!=0);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Boolean[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = new Boolean("true".equalsIgnoreCase(stringArrayRead[ i ]));
                                    }
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Boolean[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = new Boolean(Double.valueOf(stringArrayWrite[ i ]).byteValue()!=0);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Boolean[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = new Boolean("true".equalsIgnoreCase(stringArrayWrite[ i ]));
                                    }
                                }
                                break;
                           case TangoConst.Tango_DEV_STRING:
                                ( ( Object[] ) value )[ 0 ] = new String[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new String[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    ( ( String[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = new String(stringArrayRead[ i ]);
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    ( ( String[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = new String(stringArrayWrite[ i ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_CHAR:
                            case TangoConst.Tango_DEV_UCHAR:
                                ( ( Object[] ) value )[ 0 ] = new Byte[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new Byte[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Byte[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = Byte.valueOf(stringArrayRead[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Byte[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = new Byte(Double.valueOf(stringArrayRead[ i ]).byteValue());
                                    }
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Byte[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = Byte.valueOf(stringArrayWrite[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Byte[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = new Byte(Double.valueOf(stringArrayWrite[ i ]).byteValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                                ( ( Object[] ) value )[ 0 ] = new Integer[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new Integer[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Integer[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = Integer.valueOf(stringArrayRead[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Integer[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = new Integer(Double.valueOf(stringArrayRead[ i ]).intValue());
                                    }
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Integer[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = Integer.valueOf(stringArrayWrite[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Integer[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = new Integer(Double.valueOf(stringArrayWrite[ i ]).intValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_SHORT:
                                ( ( Object[] ) value )[ 0 ] = new Short[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new Short[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Short[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = Short.valueOf(stringArrayRead[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Short[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = new Short(Double.valueOf(stringArrayRead[ i ]).shortValue());
                                    }
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    try
                                    {
                                        ( ( Short[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = Short.valueOf(stringArrayWrite[ i ]);
                                    }
                                    catch(NumberFormatException n)
                                    {
                                        ( ( Short[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = new Short(Double.valueOf(stringArrayWrite[ i ]).shortValue());
                                    }
                                }
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                ( ( Object[] ) value )[ 0 ] = new Float[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new Float[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    ( ( Float[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = Float.valueOf(stringArrayRead[ i ]);
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    ( ( Float[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = Float.valueOf(stringArrayWrite[ i ]);
                                }
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                ( ( Object[] ) value )[ 0 ] = new Double[ stringArrayRead.length ];
                                ( ( Object[] ) value )[ 1 ] = new Double[ stringArrayWrite.length ];
                                for ( int i = 0 ; i < stringArrayRead.length ; i++ )
                                {
                                    ( ( Double[] ) ( ( ( ( Object[] ) value ) )[ 0 ] ) )[ i ] = Double.valueOf(stringArrayRead[ i ]);
                                }
                                for ( int i = 0 ; i < stringArrayWrite.length ; i++ )
                                {
                                    ( ( Double[] ) ( ( ( ( Object[] ) value ) )[ 1 ] ) )[ i ] = Double.valueOf(stringArrayWrite[ i ]);
                                }
                                break;
                            default :
                                value = "NaN";
                        }
                        break;

                }
                break;
            default:
                value = "NaN";
        }

        setValue(value);

    }

    public SnapAttributeExtract(SnapAttributeLight snapAttributeLight)
    {
        super.setAttribute_complete_name(snapAttributeLight.getAttribute_complete_name());
        super.setId_att(snapAttributeLight.getAttribute_id());
        data_format = snapAttributeLight.getData_format();
        data_type = snapAttributeLight.getData_type();
        writable = snapAttributeLight.getWritable();
    }

    /*public int getData_type()
     {
         return data_type;
     }

     public void setData_type(int data_type)
     {
         this.data_type = data_type;
     }

     public int getData_format()
     {
         return data_format;
     }

     public void setData_format(int data_format)
     {
         this.data_format = data_format;
     }

     public int getWritable()
     {
         return writable;
     }

     public void setWritable(int writable)
     {
         this.writable = writable;
     }*/
    // Everything here is already available in SnapAttribute

    public String valueToString(int pos)
    {
        String nullvalue = "NULL";
        String value = nullvalue;
        if( getValue () == null )
        {
            return nullvalue;
        }
        if ( getValue () instanceof Object[] )
        {
	        Object[] valTab = ( Object[] ) getValue();
	        if ( valTab [ pos ] == null )
	        {
	            return nullvalue;
	        }
        }
        
        switch ( data_format )
        {
            case AttrDataFormat._SCALAR:
                switch ( writable )
                {
                    case AttrWriteType._READ:
                        if ( pos == 0 )
                        {
                            switch ( this.data_type )
                            {
                                case TangoConst.Tango_DEV_STRING:
                                    value = ( String ) getValue();
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                    value = ( ( Integer ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_UCHAR:
                                    value = ( ( Byte ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_LONG:
                                    value = ( ( Long ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_ULONG:
                                    value = ( ( Long ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    value = ( ( Boolean ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_SHORT:
                                    value = ( ( Short ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    value = ( ( Float ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    value = ( ( Double ) getValue() ).toString();
                                    break;
                                default:
                                    value = ( ( Double ) getValue() ).toString();
                                    break;
                            }
                        }
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                value = ( ( String ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                value = ( ( Integer ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                value = ( ( Byte ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                value = ( ( Long ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                value = ( ( Long ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                value = ( ( Boolean ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                value = ( ( Short ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                value = ( ( Float ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                value = ( ( Double ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            default:
                                value = ( ( Double ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                        }
                        break;
                    case AttrWriteType._WRITE:
                        if ( pos == 1 )
                        {
                            switch ( this.data_type )
                            {
                                case TangoConst.Tango_DEV_STRING:
                                    value = ( String ) getValue();
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                    value = ( ( Integer ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_UCHAR:
                                    value = ( ( Byte ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_LONG:
                                    value = ( ( Long ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_ULONG:
                                    value = ( ( Long ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    value = ( ( Boolean ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_SHORT:
                                    value = ( ( Short ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    value = ( ( Float ) getValue() ).toString();
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    value = ( ( Double ) getValue() ).toString();
                                    break;
                                default:
                                    value = ( ( Double ) getValue() ).toString();
                                    break;
                            }
                        }
                        break;
                    case AttrWriteType._READ_WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                value = ( ( String ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                value = ( ( Integer ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                value = ( ( Byte ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                value = ( ( Long ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                value = ( ( Long ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                value = ( ( Boolean ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                value = ( ( Short ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                value = ( ( Float ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                value = ( ( Double ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                            default:
                                value = ( ( Double ) ( ( Object[] ) getValue() )[ pos ] ).toString();
                                break;
                        }
                        break;
                }
                break;
            case AttrDataFormat._SPECTRUM:
                if ( getValue() == null ) return value;
                value = "[";
                if ( pos == 0 )
                {
                    switch ( writable )
                    {
                        case AttrWriteType._READ:
                            switch ( this.data_type )
                            {
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    Boolean[] valb = ( Boolean[] ) getValue();
                                    if ( valb != null )
                                    {
                                        for ( int i = 0 ; i < valb.length - 1 ; i++ )
                                        {
                                            value += valb[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valb[ valb.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] valstr = ( String[] ) getValue();
                                    if ( valstr != null )
                                    {
                                        for ( int i = 0 ; i < valstr.length - 1 ; i++ )
                                        {
                                            value += valstr[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valstr[ valstr.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_CHAR:
                                case TangoConst.Tango_DEV_UCHAR:
                                    Byte[] valc = ( Byte[] ) getValue();
                                    if ( valc != null )
                                    {
                                        for ( int i = 0 ; i < valc.length - 1 ; i++ )
                                        {
                                            value += valc[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valc[ valc.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    Integer[] vall = ( Integer[] ) getValue();
                                    if ( vall != null )
                                    {
                                        for ( int i = 0 ; i < vall.length - 1 ; i++ )
                                        {
                                            value += vall[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vall[ vall.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_USHORT:
                                case TangoConst.Tango_DEV_SHORT:
                                    Short[] vals = ( Short[] ) getValue();
                                    if ( vals != null )
                                    {
                                        for ( int i = 0 ; i < vals.length - 1 ; i++ )
                                        {
                                            value += vals[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vals[ vals.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    Float[] valf = ( Float[] ) getValue();
                                    if ( valf != null )
                                    {
                                        for ( int i = 0 ; i < valf.length - 1 ; i++ )
                                        {
                                            value += valf[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valf[ valf.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    Double[] vald = ( Double[] ) getValue();
                                    if ( vald != null )
                                    {
                                        for ( int i = 0 ; i < vald.length - 1 ; i++ )
                                        {
                                            value += vald[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vald[ vald.length - 1 ];
                                    }
                                    break;
                                default :
                                    value += "NaN";
                            }
                            break;
                        case AttrWriteType._READ_WITH_WRITE:
                        case AttrWriteType._READ_WRITE:
                            Object[] temp = ( Object[] ) getValue();
                            switch ( this.data_type )
                            {
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    Boolean[] valb = ( Boolean[] ) temp[pos];
                                    if ( valb != null )
                                    {
                                        for ( int i = 0 ; i < valb.length - 1 ; i++ )
                                        {
                                            value += valb[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valb[ valb.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] valstr = ( String[] ) temp[pos];
                                    if ( valstr != null )
                                    {
                                        for ( int i = 0 ; i < valstr.length - 1 ; i++ )
                                        {
                                            value += valstr[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valstr[ valstr.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_CHAR:
                                case TangoConst.Tango_DEV_UCHAR:
                                    Byte[] valc = ( Byte[] ) temp[pos];
                                    if ( valc != null )
                                    {
                                        for ( int i = 0 ; i < valc.length - 1 ; i++ )
                                        {
                                            value += valc[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valc[ valc.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    Integer[] vall = ( Integer[] ) temp[pos];
                                    if ( vall != null )
                                    {
                                        for ( int i = 0 ; i < vall.length - 1 ; i++ )
                                        {
                                            value += vall[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vall[ vall.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_USHORT:
                                case TangoConst.Tango_DEV_SHORT:
                                    Short[] vals = ( Short[] ) temp[pos];
                                    if ( vals != null )
                                    {
                                        for ( int i = 0 ; i < vals.length - 1 ; i++ )
                                        {
                                            value += vals[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vals[ vals.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    Float[] valf = ( Float[] ) temp[pos];
                                    if ( valf != null )
                                    {
                                        for ( int i = 0 ; i < valf.length - 1 ; i++ )
                                        {
                                            value += valf[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valf[ valf.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    Double[] vald = ( Double[] ) temp[pos];
                                    if ( vald != null )
                                    {
                                        for ( int i = 0 ; i < vald.length - 1 ; i++ )
                                        {
                                            value += vald[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vald[ vald.length - 1 ];
                                    }
                                    break;
                                default :
                                    value += "NaN";
                            }
                            break;
                    }
                }
                else if ( pos == 1 )
                {
                    switch ( writable )
                    {
                        case AttrWriteType._WRITE:
                            switch ( this.data_type )
                            {
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    Boolean[] valb = ( Boolean[] ) getValue();
                                    if ( valb != null )
                                    {
                                        for ( int i = 0 ; i < valb.length - 1 ; i++ )
                                        {
                                            value += valb[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valb[ valb.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] valstr = ( String[] ) getValue();
                                    if ( valstr != null )
                                    {
                                        for ( int i = 0 ; i < valstr.length - 1 ; i++ )
                                        {
                                            value += valstr[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valstr[ valstr.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_CHAR:
                                case TangoConst.Tango_DEV_UCHAR:
                                    Byte[] valc = ( Byte[] ) getValue();
                                    if ( valc != null )
                                    {
                                        for ( int i = 0 ; i < valc.length - 1 ; i++ )
                                        {
                                            value += valc[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valc[ valc.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    Integer[] vall = ( Integer[] ) getValue();
                                    if ( vall != null )
                                    {
                                        for ( int i = 0 ; i < vall.length - 1 ; i++ )
                                        {
                                            value += vall[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vall[ vall.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_USHORT:
                                case TangoConst.Tango_DEV_SHORT:
                                    Short[] vals = ( Short[] ) getValue();
                                    if ( vals != null )
                                    {
                                        for ( int i = 0 ; i < vals.length - 1 ; i++ )
                                        {
                                            value += vals[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vals[ vals.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    Float[] valf = ( Float[] ) getValue();
                                    if ( valf != null )
                                    {
                                        for ( int i = 0 ; i < valf.length - 1 ; i++ )
                                        {
                                            value += valf[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valf[ valf.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    Double[] vald = ( Double[] ) getValue();
                                    if ( vald != null )
                                    {
                                        for ( int i = 0 ; i < vald.length - 1 ; i++ )
                                        {
                                            value += vald[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vald[ vald.length - 1 ];
                                    }
                                    break;
                                default :
                                    value += "NaN";
                            }
                            break;
                        case AttrWriteType._READ_WITH_WRITE:
                        case AttrWriteType._READ_WRITE:
                            Object[] temp = ( Object[] ) getValue();
                            switch ( this.data_type )
                            {
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    Boolean[] valb = ( Boolean[] ) temp[pos];
                                    if ( valb != null )
                                    {
                                        for ( int i = 0 ; i < valb.length - 1 ; i++ )
                                        {
                                            value += valb[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valb[ valb.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] valstr = ( String[] ) temp[pos];
                                    if ( valstr != null )
                                    {
                                        for ( int i = 0 ; i < valstr.length - 1 ; i++ )
                                        {
                                            value += valstr[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valstr[ valstr.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_CHAR:
                                case TangoConst.Tango_DEV_UCHAR:
                                    Byte[] valc = ( Byte[] ) temp[pos];
                                    if ( valc != null )
                                    {
                                        for ( int i = 0 ; i < valc.length - 1 ; i++ )
                                        {
                                            value += valc[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valc[ valc.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    Integer[] vall = ( Integer[] ) temp[pos];
                                    if ( vall != null )
                                    {
                                        for ( int i = 0 ; i < vall.length - 1 ; i++ )
                                        {
                                            value += vall[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vall[ vall.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_USHORT:
                                case TangoConst.Tango_DEV_SHORT:
                                    Short[] vals = ( Short[] ) temp[pos];
                                    if ( vals != null )
                                    {
                                        for ( int i = 0 ; i < vals.length - 1 ; i++ )
                                        {
                                            value += vals[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vals[ vals.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    Float[] valf = ( Float[] ) temp[pos];
                                    if ( valf != null )
                                    {
                                        for ( int i = 0 ; i < valf.length - 1 ; i++ )
                                        {
                                            value += valf[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += valf[ valf.length - 1 ];
                                    }
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    Double[] vald = ( Double[] ) temp[pos];
                                    if ( vald != null )
                                    {
                                        for ( int i = 0 ; i < vald.length - 1 ; i++ )
                                        {
                                            value += vald[ i ] + GlobalConst.CLOB_SEPARATOR;
                                        }
                                        value += vald[ vald.length - 1 ];
                                    }
                                    break;
                                default :
                                    value += "NaN";
                            }
                            break;
                    }
                }
                value += "]";
                break;
            case AttrDataFormat._IMAGE:
                value = ( ( String ) getValue() ).toString();
                break;
        }
        return value;
    }

    public Object getWriteValue()
    {
        Object write_value = null;
        switch ( data_format )
        {
            case AttrDataFormat._SCALAR:
                switch ( writable )
                {
                    case AttrWriteType._READ:
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                write_value = ( String ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                write_value = ( Integer ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                write_value = ( Byte ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                write_value = ( Long ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                write_value = ( Long ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                write_value = ( Boolean ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                write_value = ( Short ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                write_value = ( Float ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                write_value = ( Double ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            default:
                                write_value = ( Double ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                        }
                        break;
                    case AttrWriteType._WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                write_value = ( String ) getValue();
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                write_value = ( Integer ) getValue();
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                write_value = ( Byte ) getValue();
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                write_value = ( Long ) getValue();
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                write_value = ( Long ) getValue();
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                write_value = ( Boolean ) getValue();
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                write_value = ( Short ) getValue();
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                write_value = ( Float ) getValue();
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                write_value = ( Double ) getValue();
                                break;
                            default:
                                write_value = ( Double ) getValue();
                                break;
                        }
                        break;
                    case AttrWriteType._READ_WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                write_value = ( String ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                write_value = ( Integer ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                write_value = ( Byte ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                write_value = ( Long ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                write_value = ( Long ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                write_value = ( Boolean ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                write_value = ( Short ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                write_value = ( Float ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                write_value = ( Double ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                            default:
                                write_value = ( Double ) ( ( Object[] ) getValue() )[ 1 ];
                                break;
                        }
                        break;
                }
                break;
            case AttrDataFormat._SPECTRUM:
                switch ( this.writable )
                {
                    case AttrWriteType._READ:
                        break;
                    case AttrWriteType._WRITE:
                        if ( getValue() == null || "NaN".equals(getValue()) )
                        {
                            return "NaN";
                        }
                        write_value = getValue();
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                    case AttrWriteType._READ_WRITE:
                        if ( getValue() == null || "NaN".equals(getValue()) )
                        {
                            return "NaN";
                        }
                        Object[] temp = ( Object[] ) getValue();
                        if ( temp[ 1 ] == null || "NaN".equals(temp[ 1 ]) )
                        {
                            return "NaN";
                        }
                        write_value = temp[ 1 ];
                        break;
                }
                break;
            case AttrDataFormat._IMAGE:
                write_value = ( ( String ) getValue() ).toString();
                break;
        }
        return write_value;
    }

    public Object getReadValue()
    {
        Object read_value = new Object();
        switch ( data_format )
        {
            case AttrDataFormat._SCALAR:
                switch ( writable )
                {
                    case AttrWriteType._READ:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                read_value = ( String ) getValue();
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                read_value = ( Integer ) getValue();
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                read_value = ( Byte ) getValue();
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                read_value = ( Long ) getValue();
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                read_value = ( Long ) getValue();
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                read_value = ( Boolean ) getValue();
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                read_value = ( Short ) getValue();
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                read_value = ( Float ) getValue();
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                read_value = ( Double ) getValue();
                                break;
                            default:
                                read_value = ( Double ) getValue();
                                break;
                        }
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                read_value = ( String ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                read_value = ( Integer ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                read_value = ( Byte ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                read_value = ( Long ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                read_value = ( Long ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                read_value = ( Boolean ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                read_value = ( Short ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                read_value = ( Float ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                read_value = ( Double ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            default:
                                read_value = ( Double ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                        }
                        break;
                    case AttrWriteType._WRITE:
                        break;
                    case AttrWriteType._READ_WRITE:
                        switch ( this.data_type )
                        {
                            case TangoConst.Tango_DEV_STRING:
                                read_value = ( String ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_STATE:
                                read_value = ( Integer ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_UCHAR:
                                read_value = ( Byte ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_LONG:
                                read_value = ( Long ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_ULONG:
                                read_value = ( Long ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                read_value = ( Boolean ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_SHORT:
                                read_value = ( Short ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                read_value = ( Float ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                read_value = ( Double ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                            default:
                                read_value = ( Double ) ( ( Object[] ) getValue() )[ 0 ];
                                break;
                        }
                        break;
                }
                break;
            case AttrDataFormat._SPECTRUM:
                switch ( this.writable )
                {
                    case AttrWriteType._READ:
                        if ( getValue() == null || "NaN".equals(getValue()) )
                        {
                            return "NaN";
                        }
                        read_value = getValue();
                        break;
                    case AttrWriteType._WRITE:
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                    case AttrWriteType._READ_WRITE:
                        if ( getValue() == null || "NaN".equals(getValue()) )
                        {
                            return "NaN";
                        }
                        Object[] temp = ( Object[] ) getValue();
                        if ( temp[ 0 ] == null || "NaN".equals(temp[ 0 ]) )
                        {
                            return "NaN";
                        }
                        read_value = temp[ 0 ];
                        break;
                }
                break;
            case AttrDataFormat._IMAGE:
                read_value = ( ( String ) getValue() ).toString();
                break;
        }
        return read_value;
    }

    public String toString()
    {
        String snapStr = "";
        String value =
                ( ( writable == AttrWriteType._READ || writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE ) ?
                  "read value :  " + valueToString(0) : "" ) +
                ( ( writable == AttrWriteType._WRITE || writable == AttrWriteType._READ_WITH_WRITE || writable == AttrWriteType._READ_WRITE ) ?
                  "\t " + "write value : " + valueToString(1) : "" );
        snapStr =
        "attribute ID   : \t" + getId_att() + "\r\n" +
        "attribute Name : \t" + getAttribute_complete_name() + "\r\n" +
        "attribute value : \t" + value + "\r\n";
        return snapStr;
    }

    public String[] toArray()
    {
        String[] snapAttExt;
        snapAttExt = new String[ 7 ];
        snapAttExt[ 0 ] = getAttribute_complete_name();
        snapAttExt[ 1 ] = Integer.toString(getId_att());
        snapAttExt[ 2 ] = Integer.toString(data_type);
        snapAttExt[ 3 ] = Integer.toString(data_format);
        snapAttExt[ 4 ] = Integer.toString(writable);
        snapAttExt[ 5 ] = valueToString(0);
        snapAttExt[ 6 ] = valueToString(1);
        return snapAttExt;
    }
    /**
     * @return Returns the dimX.
     */
    public int getDimX() {
        return dimX;
    }
    /**
     * @param dimX The dimX to set.
     */
    public void setDimX(int dimX) {
        this.dimX = dimX;
    }
}
