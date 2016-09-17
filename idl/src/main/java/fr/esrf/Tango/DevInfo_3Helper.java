package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevInfo_3".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevInfo_3Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevInfo_3Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevInfo_3Helper.id(),"DevInfo_3",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dev_class", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_id", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_host", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("doc_url", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("dev_type", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevInfo_3 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevInfo_3 extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevInfo_3:1.0";
	}
	public static fr.esrf.Tango.DevInfo_3 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevInfo_3 result = new fr.esrf.Tango.DevInfo_3();
		result.dev_class=in.read_string();
		result.server_id=in.read_string();
		result.server_host=in.read_string();
		result.server_version=in.read_long();
		result.doc_url=in.read_string();
		result.dev_type=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevInfo_3 s)
	{
		java.lang.String tmpResult87 = s.dev_class;
out.write_string( tmpResult87 );
		java.lang.String tmpResult88 = s.server_id;
out.write_string( tmpResult88 );
		java.lang.String tmpResult89 = s.server_host;
out.write_string( tmpResult89 );
		out.write_long(s.server_version);
		java.lang.String tmpResult90 = s.doc_url;
out.write_string( tmpResult90 );
		java.lang.String tmpResult91 = s.dev_type;
out.write_string( tmpResult91 );
	}
}
