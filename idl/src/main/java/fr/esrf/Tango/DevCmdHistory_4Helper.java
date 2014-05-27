package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevCmdHistory_4".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevCmdHistory_4Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevCmdHistory_4Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdHistory_4Helper.id(),"DevCmdHistory_4",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dates", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.TimeValListHelper.id(), "TimeValList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.TimeValHelper.id(),"TimeVal",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tv_sec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_usec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_nsec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(11)), null),new org.omg.CORBA.StructMember("dims", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeDimListHelper.id(), "AttributeDimList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeDimHelper.id(),"AttributeDim",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("dims_array", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.EltInArrayListHelper.id(), "EltInArrayList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EltInArrayHelper.id(),"EltInArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("start", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("nb_elt", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("errors", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevErrorListListHelper.id(), "DevErrorListList",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.DevErrorListHelper.type())), null),new org.omg.CORBA.StructMember("errors_array", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.EltInArrayListHelper.id(), "EltInArrayList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EltInArrayHelper.id(),"EltInArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("start", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("nb_elt", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("cmd_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevCmdHistory_4 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevCmdHistory_4 extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevCmdHistory_4:1.0";
	}
	public static fr.esrf.Tango.DevCmdHistory_4 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevCmdHistory_4 result = new fr.esrf.Tango.DevCmdHistory_4();
		result.dates = fr.esrf.Tango.TimeValListHelper.read(in);
		result.value=in.read_any();
		result.dims = fr.esrf.Tango.AttributeDimListHelper.read(in);
		result.dims_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.errors = fr.esrf.Tango.DevErrorListListHelper.read(in);
		result.errors_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.cmd_type=in.read_long();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevCmdHistory_4 s)
	{
		fr.esrf.Tango.TimeValListHelper.write(out,s.dates);
		out.write_any(s.value);
		fr.esrf.Tango.AttributeDimListHelper.write(out,s.dims);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.dims_array);
		fr.esrf.Tango.DevErrorListListHelper.write(out,s.errors);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.errors_array);
		out.write_long(s.cmd_type);
	}
}
