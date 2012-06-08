package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "AttributeAlarm"
 *	@author JacORB IDL compiler 
 */

public final class AttributeAlarmHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeAlarmHelper.id(),"AttributeAlarm",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("min_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_warning", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_warning", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("delta_t", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("delta_val", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttributeAlarm s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttributeAlarm extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttributeAlarm:1.0";
	}
	public static fr.esrf.Tango.AttributeAlarm read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.AttributeAlarm result = new fr.esrf.Tango.AttributeAlarm();
		result.min_alarm=in.read_string();
		result.max_alarm=in.read_string();
		result.min_warning=in.read_string();
		result.max_warning=in.read_string();
		result.delta_t=in.read_string();
		result.delta_val=in.read_string();
		result.extensions = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.AttributeAlarm s)
	{
		out.write_string(s.min_alarm);
		out.write_string(s.max_alarm);
		out.write_string(s.min_warning);
		out.write_string(s.max_warning);
		out.write_string(s.delta_t);
		out.write_string(s.delta_val);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
