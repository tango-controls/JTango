package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevFloat".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevFloatHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, float s)
	{
		any.insert_float(s);
	}

	public static float extract (final org.omg.CORBA.Any any)
	{
		return any.extract_float();
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevFloatHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevFloatHelper.id(), "DevFloat",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevFloat:1.0";
	}
	public static float read (final org.omg.CORBA.portable.InputStream _in)
	{
		float _result;
		_result=_in.read_float();
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, float _s)
	{
		_out.write_float(_s);
	}
}
