package fr.esrf.Tango;


/**
 *	Generated from IDL definition of exception "DevFailed"
 *	@author JacORB IDL compiler 
 */

public final class DevFailedHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_exception_tc(fr.esrf.Tango.DevFailedHelper.id(),"DevFailed",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("errors", fr.esrf.Tango.DevErrorListHelper.type(), null)});
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
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevFailed:1.0";
	}
	public static fr.esrf.Tango.DevFailed read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevFailed result = new fr.esrf.Tango.DevFailed();
		if (!in.read_string().equals(id())) throw new org.omg.CORBA.MARSHAL("wrong id");
		result.errors = fr.esrf.Tango.DevErrorListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevFailed s)
	{
		out.write_string(id());
		fr.esrf.Tango.DevErrorListHelper.write(out,s.errors);
	}
}
