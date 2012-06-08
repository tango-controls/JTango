package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevError"
 *	@author JacORB IDL compiler 
 */

public final class DevErrorHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevErrorHelper.id(),"DevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("severity", fr.esrf.Tango.ErrSeverityHelper.type(), null),new org.omg.CORBA.StructMember("desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("origin", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevError s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevError extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevError:1.0";
	}
	public static fr.esrf.Tango.DevError read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevError result = new fr.esrf.Tango.DevError();
		result.reason=in.read_string();
		result.severity=fr.esrf.Tango.ErrSeverityHelper.read(in);
		result.desc=in.read_string();
		result.origin=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevError s)
	{
		out.write_string(s.reason);
		fr.esrf.Tango.ErrSeverityHelper.write(out,s.severity);
		out.write_string(s.desc);
		out.write_string(s.origin);
	}
}
