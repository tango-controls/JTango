package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "AttributeConfig"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfigHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeConfigHelper.id(),"AttributeConfig",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("writable", fr.esrf.Tango.AttrWriteTypeHelper.type(), null),new org.omg.CORBA.StructMember("data_format", fr.esrf.Tango.AttrDataFormatHelper.type(), null),new org.omg.CORBA.StructMember("data_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("max_dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("max_dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("label", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("standard_unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("display_unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("format", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_value", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_value", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("writable_attr_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttributeConfig s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttributeConfig extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttributeConfig:1.0";
	}
	public static fr.esrf.Tango.AttributeConfig read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.AttributeConfig result = new fr.esrf.Tango.AttributeConfig();
		result.name=in.read_string();
		result.writable=fr.esrf.Tango.AttrWriteTypeHelper.read(in);
		result.data_format=fr.esrf.Tango.AttrDataFormatHelper.read(in);
		result.data_type=in.read_long();
		result.max_dim_x=in.read_long();
		result.max_dim_y=in.read_long();
		result.description=in.read_string();
		result.label=in.read_string();
		result.unit=in.read_string();
		result.standard_unit=in.read_string();
		result.display_unit=in.read_string();
		result.format=in.read_string();
		result.min_value=in.read_string();
		result.max_value=in.read_string();
		result.min_alarm=in.read_string();
		result.max_alarm=in.read_string();
		result.writable_attr_name=in.read_string();
		result.extensions = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.AttributeConfig s)
	{
		out.write_string(s.name);
		fr.esrf.Tango.AttrWriteTypeHelper.write(out,s.writable);
		fr.esrf.Tango.AttrDataFormatHelper.write(out,s.data_format);
		out.write_long(s.data_type);
		out.write_long(s.max_dim_x);
		out.write_long(s.max_dim_y);
		out.write_string(s.description);
		out.write_string(s.label);
		out.write_string(s.unit);
		out.write_string(s.standard_unit);
		out.write_string(s.display_unit);
		out.write_string(s.format);
		out.write_string(s.min_value);
		out.write_string(s.max_value);
		out.write_string(s.min_alarm);
		out.write_string(s.max_alarm);
		out.write_string(s.writable_attr_name);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
