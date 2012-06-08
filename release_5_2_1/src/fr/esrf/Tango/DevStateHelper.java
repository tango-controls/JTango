package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "DevState"
 *	@author JacORB IDL compiler 
 */

public final class DevStateHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DevStateHelper.id(),"DevState",new String[]{"ON","OFF","CLOSE","OPEN","INSERT","EXTRACT","MOVING","STANDBY","FAULT","INIT","RUNNING","ALARM","DISABLE","UNKNOWN"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevState s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevState extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevState:1.0";
	}
	public static DevState read (final org.omg.CORBA.portable.InputStream in)
	{
		return DevState.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final DevState s)
	{
		out.write_long(s.value());
	}
}
