package fr.esrf.Tango;


/**
 * Generated from IDL struct "JavaClntIdent".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class JavaClntIdentHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(JavaClntIdentHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.JavaClntIdentHelper.id(),"JavaClntIdent",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("MainClass", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("uuid", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.JavaUUIDHelper.id(), "JavaUUID",org.omg.CORBA.ORB.init().create_array_tc(2,org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(24)))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.JavaClntIdent s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.JavaClntIdent extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/JavaClntIdent:1.0";
	}
	public static fr.esrf.Tango.JavaClntIdent read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.JavaClntIdent result = new fr.esrf.Tango.JavaClntIdent();
		result.MainClass=in.read_string();
		result.uuid = fr.esrf.Tango.JavaUUIDHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.JavaClntIdent s)
	{
		java.lang.String tmpResult3 = s.MainClass;
out.write_string( tmpResult3 );
		fr.esrf.Tango.JavaUUIDHelper.write(out,s.uuid);
	}
}
