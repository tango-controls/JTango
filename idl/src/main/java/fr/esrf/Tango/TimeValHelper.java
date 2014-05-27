package fr.esrf.Tango;


/**
 * Generated from IDL struct "TimeVal".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class TimeValHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(TimeValHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.TimeValHelper.id(),"TimeVal",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tv_sec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_usec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_nsec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.TimeVal s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.TimeVal extract (final org.omg.CORBA.Any any)
	{
		org.omg.CORBA.portable.InputStream in = any.create_input_stream();
		try
		{
			return read (in);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (java.io.IOException e)
			{
			throw new RuntimeException("Unexpected exception " + e.toString() );
			}
		}
	}

	public static String id()
	{
		return "IDL:Tango/TimeVal:1.0";
	}
	public static fr.esrf.Tango.TimeVal read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.TimeVal result = new fr.esrf.Tango.TimeVal();
		result.tv_sec=in.read_long();
		result.tv_usec=in.read_long();
		result.tv_nsec=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.TimeVal s)
	{
		out.write_long(s.tv_sec);
		out.write_long(s.tv_usec);
		out.write_long(s.tv_nsec);
	}
}
