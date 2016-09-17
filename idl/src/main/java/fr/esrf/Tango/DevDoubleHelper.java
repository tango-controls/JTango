package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevDouble".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevDoubleHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, double s)
	{
		any.insert_double(s);
	}

	public static double extract (final org.omg.CORBA.Any any)
	{
		return any.extract_double();
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevDoubleHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevDoubleHelper.id(), "DevDouble",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(7)));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevDouble:1.0";
	}
	public static double read (final org.omg.CORBA.portable.InputStream _in)
	{
		double _result;
		_result=_in.read_double();
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, double _s)
	{
		_out.write_double(_s);
	}
}
