package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevAttrHistory_4".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevAttrHistory_4Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevAttrHistory_4Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevAttrHistory_4Helper.id(),"DevAttrHistory_4",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("dates", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.TimeValListHelper.id(), "TimeValList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.TimeValHelper.id(),"TimeVal",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tv_sec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_usec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_nsec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(11)), null),new org.omg.CORBA.StructMember("quals", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttrQualityListHelper.id(), "AttrQualityList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrQualityHelper.id(),"AttrQuality",new String[]{"ATTR_VALID","ATTR_INVALID","ATTR_ALARM","ATTR_CHANGING","ATTR_WARNING"}))), null),new org.omg.CORBA.StructMember("quals_array", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.EltInArrayListHelper.id(), "EltInArrayList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EltInArrayHelper.id(),"EltInArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("start", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("nb_elt", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("r_dims", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeDimListHelper.id(), "AttributeDimList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeDimHelper.id(),"AttributeDim",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("r_dims_array", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.EltInArrayListHelper.id(), "EltInArrayList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EltInArrayHelper.id(),"EltInArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("start", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("nb_elt", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("w_dims", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeDimListHelper.id(), "AttributeDimList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeDimHelper.id(),"AttributeDim",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("w_dims_array", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.EltInArrayListHelper.id(), "EltInArrayList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EltInArrayHelper.id(),"EltInArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("start", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("nb_elt", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null),new org.omg.CORBA.StructMember("errors", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevErrorListListHelper.id(), "DevErrorListList",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.DevErrorListHelper.type())), null),new org.omg.CORBA.StructMember("errors_array", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.EltInArrayListHelper.id(), "EltInArrayList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EltInArrayHelper.id(),"EltInArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("start", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("nb_elt", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevAttrHistory_4 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevAttrHistory_4 extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevAttrHistory_4:1.0";
	}
	public static fr.esrf.Tango.DevAttrHistory_4 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevAttrHistory_4 result = new fr.esrf.Tango.DevAttrHistory_4();
		result.name=in.read_string();
		result.dates = fr.esrf.Tango.TimeValListHelper.read(in);
		result.value=in.read_any();
		result.quals = fr.esrf.Tango.AttrQualityListHelper.read(in);
		result.quals_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.r_dims = fr.esrf.Tango.AttributeDimListHelper.read(in);
		result.r_dims_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.w_dims = fr.esrf.Tango.AttributeDimListHelper.read(in);
		result.w_dims_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		result.errors = fr.esrf.Tango.DevErrorListListHelper.read(in);
		result.errors_array = fr.esrf.Tango.EltInArrayListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevAttrHistory_4 s)
	{
		java.lang.String tmpResult85 = s.name;
out.write_string( tmpResult85 );
		fr.esrf.Tango.TimeValListHelper.write(out,s.dates);
		out.write_any(s.value);
		fr.esrf.Tango.AttrQualityListHelper.write(out,s.quals);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.quals_array);
		fr.esrf.Tango.AttributeDimListHelper.write(out,s.r_dims);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.r_dims_array);
		fr.esrf.Tango.AttributeDimListHelper.write(out,s.w_dims);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.w_dims_array);
		fr.esrf.Tango.DevErrorListListHelper.write(out,s.errors);
		fr.esrf.Tango.EltInArrayListHelper.write(out,s.errors_array);
	}
}
