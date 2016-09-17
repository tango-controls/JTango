package fr.esrf.Tango;
/**
 * Generated from IDL enum "DevSource".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevSourceHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevSourceHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DevSourceHelper.id(),"DevSource",new String[]{"DEV","CACHE","CACHE_DEV"});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevSource s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevSource extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevSource:1.0";
	}
	public static DevSource read (final org.omg.CORBA.portable.InputStream in)
	{
		return DevSource.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final DevSource s)
	{
		out.write_long(s.value());
	}
}
