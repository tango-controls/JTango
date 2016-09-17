package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevUChar".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevUCharHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, byte s)
	{
		any.insert_octet(s);
	}

	public static byte extract (final org.omg.CORBA.Any any)
	{
		return any.extract_octet();
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevUCharHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevUCharHelper.id(), "DevUChar",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10)));
				}
			}
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
