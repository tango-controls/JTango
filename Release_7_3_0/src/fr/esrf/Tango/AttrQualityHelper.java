package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "AttrQuality"
 *	@author JacORB IDL compiler 
 */

public final class AttrQualityHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrQualityHelper.id(),"AttrQuality",new String[]{"ATTR_VALID","ATTR_INVALID","ATTR_ALARM","ATTR_CHANGING","ATTR_WARNING"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttrQuality s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttrQuality extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttrQuality:1.0";
	}
	public static AttrQuality read (final org.omg.CORBA.portable.InputStream in)
	{
		return AttrQuality.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final AttrQuality s)
	{
		out.write_long(s.value());
	}
}
