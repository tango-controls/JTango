package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevError".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevErrorHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevErrorHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevErrorHelper.id(),"DevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("severity", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.ErrSeverityHelper.id(),"ErrSeverity",new String[]{"WARN","ERR","PANIC"}), null),new org.omg.CORBA.StructMember("desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("origin", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
				}
			}
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
		java.lang.String tmpResult10 = s.reason;
out.write_string( tmpResult10 );
		fr.esrf.Tango.ErrSeverityHelper.write(out,s.severity);
		java.lang.String tmpResult11 = s.desc;
out.write_string( tmpResult11 );
		java.lang.String tmpResult12 = s.origin;
out.write_string( tmpResult12 );
	}
}
