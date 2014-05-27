package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevCmdHistory".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevCmdHistoryHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevCmdHistoryHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdHistoryHelper.id(),"DevCmdHistory",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("time", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.TimeValHelper.id(),"TimeVal",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tv_sec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_usec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_nsec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}), null),new org.omg.CORBA.StructMember("cmd_failed", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(11)), null),new org.omg.CORBA.StructMember("errors", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevErrorListHelper.id(), "DevErrorList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevErrorHelper.id(),"DevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("severity", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.ErrSeverityHelper.id(),"ErrSeverity",new String[]{"WARN","ERR","PANIC"}), null),new org.omg.CORBA.StructMember("desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("origin", org.omg.CORBA.ORB.init().create_string_tc(0), null)}))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevCmdHistory s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevCmdHistory extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevCmdHistory:1.0";
	}
	public static fr.esrf.Tango.DevCmdHistory read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevCmdHistory result = new fr.esrf.Tango.DevCmdHistory();
		result.time=fr.esrf.Tango.TimeValHelper.read(in);
		result.cmd_failed=in.read_boolean();
		result.value=in.read_any();
		result.errors = fr.esrf.Tango.DevErrorListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevCmdHistory s)
	{
		fr.esrf.Tango.TimeValHelper.write(out,s.time);
		out.write_boolean(s.cmd_failed);
		out.write_any(s.value);
		fr.esrf.Tango.DevErrorListHelper.write(out,s.errors);
	}
}
