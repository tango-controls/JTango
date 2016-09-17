package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevLong64".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevLong64Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, long s)
	{
		any.insert_longlong(s);
	}

	public static long extract (final org.omg.CORBA.Any any)
	{
		return any.extract_longlong();
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevLong64Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevLong64Helper.id(), "DevLong64",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(23)));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevLong64:1.0";
	}
	public static long read (final org.omg.CORBA.portable.InputStream _in)
	{
		long _result;
		_result=_in.read_longlong();
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, long _s)
	{
		_out.write_longlong(_s);
	}
}
