package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "AttrDataFormat"
 *	@author JacORB IDL compiler 
 */

public final class AttrDataFormatHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrDataFormatHelper.id(),"AttrDataFormat",new String[]{"SCALAR","SPECTRUM","IMAGE","FMT_UNKNOWN"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttrDataFormat s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttrDataFormat extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttrDataFormat:1.0";
	}
	public static AttrDataFormat read (final org.omg.CORBA.portable.InputStream in)
	{
		return AttrDataFormat.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final AttrDataFormat s)
	{
		out.write_long(s.value());
	}
}
