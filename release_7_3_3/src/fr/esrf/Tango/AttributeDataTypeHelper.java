package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "AttributeDataType"
 *	@author JacORB IDL compiler 
 */

public final class AttributeDataTypeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttributeDataTypeHelper.id(),"AttributeDataType",new String[]{"ATT_BOOL","ATT_SHORT","ATT_LONG","ATT_LONG64","ATT_FLOAT","ATT_DOUBLE","ATT_UCHAR","ATT_USHORT","ATT_ULONG","ATT_ULONG64","ATT_STRING","ATT_STATE","DEVICE_STATE","ATT_ENCODED","NO_DATA"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttributeDataType s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttributeDataType extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttributeDataType:1.0";
	}
	public static AttributeDataType read (final org.omg.CORBA.portable.InputStream in)
	{
		return AttributeDataType.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final AttributeDataType s)
	{
		out.write_long(s.value());
	}
}
