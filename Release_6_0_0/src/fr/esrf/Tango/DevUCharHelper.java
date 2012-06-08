package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevUChar"
 *	@author JacORB IDL compiler 
 */

public final class DevUCharHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, byte s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static byte extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevUCharHelper.id(), "DevUChar",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10)));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevUChar:1.0";
	}
	public static byte read (final org.omg.CORBA.portable.InputStream _in)
	{
		byte _result;
		_result=_in.read_octet();
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, byte _s)
	{
		_out.write_octet(_s);
	}
}
