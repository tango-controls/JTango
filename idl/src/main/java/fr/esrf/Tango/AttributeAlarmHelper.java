package fr.esrf.Tango;


/**
 * Generated from IDL struct "AttributeAlarm".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeAlarmHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(AttributeAlarmHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.AttributeAlarmHelper.id(),"AttributeAlarm",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("min_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_alarm", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("min_warning", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("max_warning", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("delta_t", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("delta_val", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)});
				}
			}
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
		java.lang.String tmpResult48 = s.min_alarm;
out.write_string( tmpResult48 );
		java.lang.String tmpResult49 = s.max_alarm;
out.write_string( tmpResult49 );
		java.lang.String tmpResult50 = s.min_warning;
out.write_string( tmpResult50 );
		java.lang.String tmpResult51 = s.max_warning;
out.write_string( tmpResult51 );
		java.lang.String tmpResult52 = s.delta_t;
out.write_string( tmpResult52 );
		java.lang.String tmpResult53 = s.delta_val;
out.write_string( tmpResult53 );
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
