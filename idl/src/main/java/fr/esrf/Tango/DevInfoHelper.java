package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevInfo".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevInfoHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevInfoHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevInfoHelper.id(),"DevInfo",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dev_class", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_id", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_host", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("doc_url", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevInfo s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevInfo extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevInfo:1.0";
	}
	public static fr.esrf.Tango.DevInfo read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevInfo result = new fr.esrf.Tango.DevInfo();
		result.dev_class=in.read_string();
		result.server_id=in.read_string();
		result.server_host=in.read_string();
		result.server_version=in.read_long();
		result.doc_url=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevInfo s)
	{
		java.lang.String tmpResult76 = s.dev_class;
out.write_string( tmpResult76 );
		java.lang.String tmpResult77 = s.server_id;
out.write_string( tmpResult77 );
		java.lang.String tmpResult78 = s.server_host;
out.write_string( tmpResult78 );
		out.write_long(s.server_version);
		java.lang.String tmpResult79 = s.doc_url;
out.write_string( tmpResult79 );
	}
}
