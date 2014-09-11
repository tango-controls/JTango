package fr.esrf.Tango;


/**
 * Generated from IDL struct "AttributeConfig".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class AttributeConfigHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(AttributeConfigHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeConfigHelper.id(),"AttributeConfig",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("writable", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrWriteTypeHelper.id(),"AttrWriteType",new String[]{"READ","READ_WITH_WRITE","WRITE","READ_WRITE","WT_UNKNOWN"}), null),new org.omg.CORBA.StructMember("data_format", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrDataFormatHelper.id(),"AttrDataFormat",new String[]{"SCALAR","SPECTRUM","IMAGE","FMT_UNKNOWN"}), null),new org.omg.CORBA.StructMember("data_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("max_dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("max_dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("label", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("standard_unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("display_unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("format", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_value", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_value", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("writable_attr_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)});
				}
			}
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
		java.lang.String tmpResult14 = s.name;
out.write_string( tmpResult14 );
		fr.esrf.Tango.AttrWriteTypeHelper.write(out,s.writable);
		fr.esrf.Tango.AttrDataFormatHelper.write(out,s.data_format);
		out.write_long(s.data_type);
		out.write_long(s.max_dim_x);
		out.write_long(s.max_dim_y);
		java.lang.String tmpResult15 = s.description;
out.write_string( tmpResult15 );
		java.lang.String tmpResult16 = s.label;
out.write_string( tmpResult16 );
		java.lang.String tmpResult17 = s.unit;
out.write_string( tmpResult17 );
		java.lang.String tmpResult18 = s.standard_unit;
out.write_string( tmpResult18 );
		java.lang.String tmpResult19 = s.display_unit;
out.write_string( tmpResult19 );
		java.lang.String tmpResult20 = s.format;
out.write_string( tmpResult20 );
		java.lang.String tmpResult21 = s.min_value;
out.write_string( tmpResult21 );
		java.lang.String tmpResult22 = s.max_value;
out.write_string( tmpResult22 );
		java.lang.String tmpResult23 = s.min_alarm;
out.write_string( tmpResult23 );
		java.lang.String tmpResult24 = s.max_alarm;
out.write_string( tmpResult24 );
		java.lang.String tmpResult25 = s.writable_attr_name;
out.write_string( tmpResult25 );
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
