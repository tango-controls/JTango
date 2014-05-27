package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevIntrChange".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevIntrChangeHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevIntrChangeHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevIntrChangeHelper.id(),"DevIntrChange",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dev_started", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("cmds", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevCmdInfoList_2Helper.id(), "DevCmdInfoList_2",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdInfo_2Helper.id(),"DevCmdInfo_2",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("cmd_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("level", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DispLevelHelper.id(),"DispLevel",new String[]{"OPERATOR","EXPERT","DL_UNKNOWN"}), null),new org.omg.CORBA.StructMember("cmd_tag", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("out_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("out_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null)}))), null),new org.omg.CORBA.StructMember("atts", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeConfigList_5Helper.id(), "AttributeConfigList_5",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeConfig_5Helper.id(),"AttributeConfig_5",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("writable", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrWriteTypeHelper.id(),"AttrWriteType",new String[]{"READ","READ_WITH_WRITE","WRITE","READ_WRITE","WT_UNKNOWN"}), null),new org.omg.CORBA.StructMember("data_format", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrDataFormatHelper.id(),"AttrDataFormat",new String[]{"SCALAR","SPECTRUM","IMAGE","FMT_UNKNOWN"}), null),new org.omg.CORBA.StructMember("data_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("memorized", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("mem_init", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("max_dim_x", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("max_dim_y", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("label", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("standard_unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("display_unit", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("format", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_value", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_value", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("writable_attr_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("level", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DispLevelHelper.id(),"DispLevel",new String[]{"OPERATOR","EXPERT","DL_UNKNOWN"}), null),new org.omg.CORBA.StructMember("root_attr_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("enum_labels", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null),new org.omg.CORBA.StructMember("att_alarm", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeAlarmHelper.id(),"AttributeAlarm",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("min_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_warning", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_warning", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("delta_t", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("delta_val", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)}), null),new org.omg.CORBA.StructMember("event_prop", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.EventPropertiesHelper.id(),"EventProperties",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("ch_event", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.ChangeEventPropHelper.id(),"ChangeEventProp",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("rel_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("abs_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)}), null),new org.omg.CORBA.StructMember("per_event", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.PeriodicEventPropHelper.id(),"PeriodicEventProp",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("period", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)}), null),new org.omg.CORBA.StructMember("arch_event", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.ArchiveEventPropHelper.id(),"ArchiveEventProp",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("rel_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("abs_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("period", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)}), null)}), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null),new org.omg.CORBA.StructMember("sys_extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)}))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevIntrChange s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevIntrChange extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevIntrChange:1.0";
	}
	public static fr.esrf.Tango.DevIntrChange read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevIntrChange result = new fr.esrf.Tango.DevIntrChange();
		result.dev_started=in.read_boolean();
		result.cmds = fr.esrf.Tango.DevCmdInfoList_2Helper.read(in);
		result.atts = fr.esrf.Tango.AttributeConfigList_5Helper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevIntrChange s)
	{
		out.write_boolean(s.dev_started);
		fr.esrf.Tango.DevCmdInfoList_2Helper.write(out,s.cmds);
		fr.esrf.Tango.AttributeConfigList_5Helper.write(out,s.atts);
	}
}
