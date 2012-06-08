package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevUShort"
 *	@author JacORB IDL compiler 
 */

public final class DevUShortHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, short s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static short extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevUShortHelper.id(), "DevUShort",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(4)));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevUShort:1.0";
	}
	public static short read (final org.omg.CORBA.portable.InputStream _in)
	{
		short _result;
		_result=_in.read_ushort();
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, short _s)
	{
		_out.write_ushort(_s);
	}
}
