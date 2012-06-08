//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  AttributeSupport.
//						(chinkumo) - 24 août 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.10  2006/06/28 12:43:58  ounsy
// image support
//
// Revision 1.9  2006/06/16 08:48:36  ounsy
// exceptions messages easier to understand
//
// Revision 1.8  2006/04/13 12:47:24  ounsy
// new spectrum types support
//
// Revision 1.7  2006/03/16 15:29:50  ounsy
// String and state scalar support
//
// Revision 1.6  2006/03/15 15:12:05  ounsy
// boolean scalar management
//
// Revision 1.5  2006/02/24 12:53:36  ounsy
// float ok
//
// Revision 1.4  2006/02/17 09:25:00  chinkumo
// Minor change : code reformated.
//
// Revision 1.3  2006/02/15 09:05:33  ounsy
// Spectrums Management
//
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.2.1  2005/11/15 13:36:38  chinkumo
// first commit
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
package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import fr.esrf.TangoDs.TangoConst;
import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.ErrSeverity;

public class AttributeSupport
{
	public static boolean checkAttributeSupport(String name , int data_type , int data_format , int writable) throws SnapshotingException
	{
		switch ( data_format )
		{
			case AttrDataFormat._SCALAR:
				switch ( writable )
				{
					case AttrWriteType._READ:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
								return true;
							case TangoConst.Tango_DEV_BOOLEAN:
								return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
								return true;
							case TangoConst.Tango_DEV_STRING:
								return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					case AttrWriteType._READ_WITH_WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
								return true;
							case TangoConst.Tango_DEV_BOOLEAN:
								return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STRING:
								return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					case AttrWriteType._WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
								return true;
							case TangoConst.Tango_DEV_BOOLEAN:
								return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STRING:
								return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					case AttrWriteType._READ_WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
								return true;
							case TangoConst.Tango_DEV_BOOLEAN:
								return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STRING:
								return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					default:
						throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
				}
			case AttrDataFormat._SPECTRUM:
				//throw generateException(GlobalConst.DATA_FORMAT_EXCEPTION , data_format , name);
				switch ( writable )
				{
					case AttrWriteType._READ:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
                                return true;
							case TangoConst.Tango_DEV_BOOLEAN:
                                return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
                                return true;
							case TangoConst.Tango_DEV_STRING:
                                return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					case AttrWriteType._WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
                                return true;
							case TangoConst.Tango_DEV_BOOLEAN:
                                return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STRING:
                                return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					case AttrWriteType._READ_WITH_WRITE:
					case AttrWriteType._READ_WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
								return true;
							case TangoConst.Tango_DEV_USHORT:
								return true;
							case TangoConst.Tango_DEV_LONG:
								return true;
							case TangoConst.Tango_DEV_ULONG:
								return true;
							case TangoConst.Tango_DEV_DOUBLE:
								return true;
							case TangoConst.Tango_DEV_FLOAT:
                                return true;
							case TangoConst.Tango_DEV_BOOLEAN:
                                return true;
							case TangoConst.Tango_DEV_CHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_UCHAR:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STATE:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
							case TangoConst.Tango_DEV_STRING:
                                return true;
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					default:
						throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
				}
			case AttrDataFormat._IMAGE:
                switch ( writable )
                {
                    case AttrWriteType._READ:
                        switch ( data_type )
                        {
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                            case TangoConst.Tango_DEV_DOUBLE:
                            case TangoConst.Tango_DEV_FLOAT:
                                return true;
                            case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_CHAR:
                            case TangoConst.Tango_DEV_UCHAR:
                            case TangoConst.Tango_DEV_STATE:
                            case TangoConst.Tango_DEV_STRING:
                            default:
                                throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                        }
                    case AttrWriteType._WRITE:
                    case AttrWriteType._READ_WITH_WRITE:
                    case AttrWriteType._READ_WRITE:
                    default:
                        throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
                }
			default:
				throw generateException(GlobalConst.DATA_FORMAT_EXCEPTION , data_format , name);
		}
	}

	private static SnapshotingException generateException(String cause , int cause_value , String name)
	{
		String message = GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + cause;
		String reason = "Failed while executing AttributeSupport.checkAttributeSupport()...";
        String desc = new String(cause + " (");
        if (GlobalConst.DATA_WRITABLE_EXCEPTION.equals(cause))
        {
            switch(cause_value)
            {
                case AttrWriteType._READ:
                    desc += "READ";
                    break;
                case AttrWriteType._WRITE:
                    desc += "WRITE";
                    break;
                case AttrWriteType._READ_WITH_WRITE:
                    desc += "READ WITH WRITE";
                    break;
                case AttrWriteType._READ_WRITE:
                    desc += "READ WRITE";
                    break;
                default:
                    desc += cause_value;
            }
        }
        else if (GlobalConst.DATA_FORMAT_EXCEPTION.equals(cause))
        {
            switch(cause_value)
            {
                case AttrDataFormat._SCALAR:
                    desc += "SCALAR";
                    break;
                case AttrDataFormat._SPECTRUM:
                    desc += "SPECTRUM";
                    break;
                case AttrDataFormat._IMAGE:
                    desc += "IMAGE";
                    break;
                default:
                    desc += cause_value;
            }
        }
        else if (GlobalConst.DATA_TYPE_EXCEPTION.equals(cause))
        {
            switch(cause_value)
            {
                case TangoConst.Tango_DEV_SHORT:
                    desc += "SHORT";
                    break;
                case TangoConst.Tango_DEV_USHORT:
                    desc += "UNSIGNED SHORT";
                    break;
                case TangoConst.Tango_DEV_LONG:
                    desc += "LONG";
                    break;
                case TangoConst.Tango_DEV_ULONG:
                    desc += "UNSIGNED LONG";
                    break;
                case TangoConst.Tango_DEV_DOUBLE:
                    desc += "DOUBLE";
                    break;
                case TangoConst.Tango_DEV_FLOAT:
                    desc += "FLOAT";
                    break;
                case TangoConst.Tango_DEV_BOOLEAN:
                    desc += "BOOLEAN";
                    break;
                case TangoConst.Tango_DEV_CHAR:
                    desc += "CHAR";
                    break;
                case TangoConst.Tango_DEV_UCHAR:
                    desc += "UNSIGNED CHAR";
                    break;
                case TangoConst.Tango_DEV_STATE:
                    desc += "STATE";
                    break;
                case TangoConst.Tango_DEV_STRING:
                    desc += "STRING";
                    break;
                default:
                    desc += cause_value;
            }
        }
        else
        {
            desc += cause_value;
        }
        desc += ") not supported !! [" + name + "]";
		//String desc = cause + " (" + cause_value + ") not supported !! [" + name + "]";
		return new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "");
	}
}
