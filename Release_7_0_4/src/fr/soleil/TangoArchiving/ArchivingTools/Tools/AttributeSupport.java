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
// Revision 1.10  2006/07/24 07:36:04  ounsy
// image support
//
// Revision 1.9  2006/06/16 08:48:36  ounsy
// exceptions messages easier to understand
//
// Revision 1.8  2006/04/05 13:50:50  ounsy
// new types full support
//
// Revision 1.7  2006/03/13 14:41:19  ounsy
// code factoring
//
// Revision 1.6  2006/03/10 11:31:00  ounsy
// state and string support
//
// Revision 1.5  2006/02/24 12:50:35  ounsy
// float ok
//
// Revision 1.4  2006/02/06 12:22:28  ounsy
// added spectrum support for basic types
//
// Revision 1.3  2006/01/23 10:31:56  ounsy
// spectrum management
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

import fr.esrf.TangoDs.TangoConst;
import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;

public class AttributeSupport
{
	public static boolean checkAttributeSupport(String name , int data_type , int data_format , int writable) throws ArchivingException
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
							case TangoConst.Tango_DEV_USHORT:
							case TangoConst.Tango_DEV_LONG:
							case TangoConst.Tango_DEV_ULONG:
							case TangoConst.Tango_DEV_DOUBLE:
							case TangoConst.Tango_DEV_FLOAT:
							case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_STRING:
                            case TangoConst.Tango_DEV_STATE:
                                return true;
							case TangoConst.Tango_DEV_CHAR:
							case TangoConst.Tango_DEV_UCHAR:
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
                    case AttrWriteType._WRITE:
					case AttrWriteType._READ_WITH_WRITE:
					case AttrWriteType._READ_WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
							case TangoConst.Tango_DEV_USHORT:
							case TangoConst.Tango_DEV_LONG:
							case TangoConst.Tango_DEV_ULONG:
							case TangoConst.Tango_DEV_DOUBLE:
							case TangoConst.Tango_DEV_FLOAT:
							case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_STRING:
                                return true;
                            case TangoConst.Tango_DEV_STATE:
							case TangoConst.Tango_DEV_CHAR:
							case TangoConst.Tango_DEV_UCHAR:
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					default:
						throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
				}
			case AttrDataFormat._SPECTRUM:
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
							case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_STRING:
                            case TangoConst.Tango_DEV_STATE:
                                return true;
							case TangoConst.Tango_DEV_CHAR:
							case TangoConst.Tango_DEV_UCHAR:
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
                    case AttrWriteType._WRITE:
					case AttrWriteType._READ_WITH_WRITE:
					case AttrWriteType._READ_WRITE:
						switch ( data_type )
						{
							case TangoConst.Tango_DEV_SHORT:
							case TangoConst.Tango_DEV_USHORT:
							case TangoConst.Tango_DEV_LONG:
							case TangoConst.Tango_DEV_ULONG:
							case TangoConst.Tango_DEV_DOUBLE:
							case TangoConst.Tango_DEV_FLOAT:
							case TangoConst.Tango_DEV_BOOLEAN:
                            case TangoConst.Tango_DEV_STRING:
                                return true;
                            case TangoConst.Tango_DEV_STATE:
							case TangoConst.Tango_DEV_CHAR:
							case TangoConst.Tango_DEV_UCHAR:
							default:
								throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
						}
					default:
						throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
				}
			case AttrDataFormat._IMAGE: // TODO IMAGES : NOT SUPPORTED
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

	private static ArchivingException generateException(String cause , int cause_value , String name)
	{
        String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + cause;
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
		//desc = cause + " (" + cause_value + ") not supported !! [" + name + "]";
		return new ArchivingException(message , reason , ErrSeverity.WARN , desc , "");
	}
}
