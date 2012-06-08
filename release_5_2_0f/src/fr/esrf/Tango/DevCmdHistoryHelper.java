package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevCmdHistory"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdHistoryHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdHistoryHelper.id(),"DevCmdHistory",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("time", fr.esrf.Tango.TimeValHelper.type(), null),new org.omg.CORBA.StructMember("cmd_failed", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(11)), null),new org.omg.CORBA.StructMember("errors", fr.esrf.Tango.DevErrorListHelper.type(), null)});
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
		return read(any.create_input_stream());
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
