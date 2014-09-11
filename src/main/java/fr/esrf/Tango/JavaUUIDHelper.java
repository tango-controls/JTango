package fr.esrf.Tango;

/**
 * Generated from IDL alias "JavaUUID".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class JavaUUIDHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, long[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static long[] extract (final org.omg.CORBA.Any any)
	{
		if ( any.type().kind() == org.omg.CORBA.TCKind.tk_null)
		{
			throw new org.omg.CORBA.BAD_OPERATION ("Can't extract from Any with null type.");
		}
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(JavaUUIDHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.JavaUUIDHelper.id(), "JavaUUID",org.omg.CORBA.ORB.init().create_array_tc(2,org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(24))));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/JavaUUID:1.0";
	}
	public static long[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		long[] _result;
		_result = new long[2];
		_in.read_ulonglong_array(_result,0,2);
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, long[] _s)
	{
				if (_s.length<2)
			throw new org.omg.CORBA.MARSHAL("Incorrect array size "+_s.length+", expecting 2");
		_out.write_ulonglong_array(_s,0,2);
	}
}
