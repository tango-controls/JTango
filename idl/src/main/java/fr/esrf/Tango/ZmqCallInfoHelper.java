package fr.esrf.Tango;


/**
 * Generated from IDL struct "ZmqCallInfo".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class ZmqCallInfoHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(ZmqCallInfoHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.ZmqCallInfoHelper.id(),"ZmqCallInfo",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("ctr", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(5)), null),new org.omg.CORBA.StructMember("method_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("oid", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarCharArrayHelper.id(), "DevVarCharArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10)))), null),new org.omg.CORBA.StructMember("call_is_except", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.ZmqCallInfo s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.ZmqCallInfo extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/ZmqCallInfo:1.0";
	}
	public static fr.esrf.Tango.ZmqCallInfo read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.ZmqCallInfo result = new fr.esrf.Tango.ZmqCallInfo();
		result.version=in.read_long();
		result.ctr=in.read_ulong();
		result.method_name=in.read_string();
		result.oid = fr.esrf.Tango.DevVarCharArrayHelper.read(in);
		result.call_is_except=in.read_boolean();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.ZmqCallInfo s)
	{
		out.write_long(s.version);
		out.write_ulong(s.ctr);
		java.lang.String tmpResult94 = s.method_name;
out.write_string( tmpResult94 );
		fr.esrf.Tango.DevVarCharArrayHelper.write(out,s.oid);
		out.write_boolean(s.call_is_except);
	}
}
