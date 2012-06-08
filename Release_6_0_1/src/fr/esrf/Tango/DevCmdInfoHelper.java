package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevCmdInfo"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdInfoHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdInfoHelper.id(),"DevCmdInfo",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("cmd_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("cmd_tag", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("out_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("out_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevCmdInfo s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevCmdInfo extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevCmdInfo:1.0";
	}
	public static fr.esrf.Tango.DevCmdInfo read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevCmdInfo result = new fr.esrf.Tango.DevCmdInfo();
		result.cmd_name=in.read_string();
		result.cmd_tag=in.read_long();
		result.in_type=in.read_long();
		result.out_type=in.read_long();
		result.in_type_desc=in.read_string();
		result.out_type_desc=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevCmdInfo s)
	{
		out.write_string(s.cmd_name);
		out.write_long(s.cmd_tag);
		out.write_long(s.in_type);
		out.write_long(s.out_type);
		out.write_string(s.in_type_desc);
		out.write_string(s.out_type_desc);
	}
}
