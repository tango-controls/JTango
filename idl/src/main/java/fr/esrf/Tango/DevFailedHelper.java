package fr.esrf.Tango;


/**
 * Generated from IDL exception "DevFailed".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevFailedHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevFailedHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_exception_tc(fr.esrf.Tango.DevFailedHelper.id(),"DevFailed",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("errors", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevErrorListHelper.id(), "DevErrorList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevErrorHelper.id(),"DevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("severity", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.ErrSeverityHelper.id(),"ErrSeverity",new String[]{"WARN","ERR","PANIC"}), null),new org.omg.CORBA.StructMember("desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("origin", org.omg.CORBA.ORB.init().create_string_tc(0), null)}))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevFailed s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevFailed extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevFailed:1.0";
	}
	public static fr.esrf.Tango.DevFailed read (final org.omg.CORBA.portable.InputStream in)
	{
		String id = in.read_string();
		if (!id.equals(id())) throw new org.omg.CORBA.MARSHAL("wrong id: " + id);
		fr.esrf.Tango.DevError[] x0;
		x0 = fr.esrf.Tango.DevErrorListHelper.read(in);
		final fr.esrf.Tango.DevFailed result = new fr.esrf.Tango.DevFailed(id, x0);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevFailed s)
	{
		out.write_string(id());
		fr.esrf.Tango.DevErrorListHelper.write(out,s.errors);
	}
}
