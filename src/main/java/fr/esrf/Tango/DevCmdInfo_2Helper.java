package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevCmdInfo_2".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevCmdInfo_2Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevCmdInfo_2Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdInfo_2Helper.id(),"DevCmdInfo_2",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("cmd_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("level", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DispLevelHelper.id(),"DispLevel",new String[]{"OPERATOR","EXPERT","DL_UNKNOWN"}), null),new org.omg.CORBA.StructMember("cmd_tag", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("out_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("out_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevCmdInfo_2 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevCmdInfo_2 extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevCmdInfo_2:1.0";
	}
	public static fr.esrf.Tango.DevCmdInfo_2 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevCmdInfo_2 result = new fr.esrf.Tango.DevCmdInfo_2();
		result.cmd_name=in.read_string();
		result.level=fr.esrf.Tango.DispLevelHelper.read(in);
		result.cmd_tag=in.read_long();
		result.in_type=in.read_long();
		result.out_type=in.read_long();
		result.in_type_desc=in.read_string();
		result.out_type_desc=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevCmdInfo_2 s)
	{
		java.lang.String tmpResult7 = s.cmd_name;
out.write_string( tmpResult7 );
		fr.esrf.Tango.DispLevelHelper.write(out,s.level);
		out.write_long(s.cmd_tag);
		out.write_long(s.in_type);
		out.write_long(s.out_type);
		java.lang.String tmpResult8 = s.in_type_desc;
out.write_string( tmpResult8 );
		java.lang.String tmpResult9 = s.out_type_desc;
out.write_string( tmpResult9 );
	}
}
