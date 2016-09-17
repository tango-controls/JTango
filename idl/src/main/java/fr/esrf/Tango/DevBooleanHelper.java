package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevBoolean".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevBooleanHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, boolean s)
	{
		any.insert_boolean(s);
	}

	public static boolean extract (final org.omg.CORBA.Any any)
	{
		return any.extract_boolean();
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevBooleanHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevBooleanHelper.id(), "DevBoolean",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevBoolean:1.0";
	}
	public static boolean read (final org.omg.CORBA.portable.InputStream _in)
	{
		boolean _result;
		_result=_in.read_boolean();
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, boolean _s)
	{
		_out.write_boolean(_s);
	}
}
