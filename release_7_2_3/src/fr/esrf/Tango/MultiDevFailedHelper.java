package fr.esrf.Tango;


/**
 *	Generated from IDL definition of exception "MultiDevFailed"
 *	@author JacORB IDL compiler 
 */

public final class MultiDevFailedHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_exception_tc(fr.esrf.Tango.MultiDevFailedHelper.id(),"MultiDevFailed",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("errors", fr.esrf.Tango.NamedDevErrorListHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.MultiDevFailed s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.MultiDevFailed extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/MultiDevFailed:1.0";
	}
	public static fr.esrf.Tango.MultiDevFailed read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.MultiDevFailed result = new fr.esrf.Tango.MultiDevFailed();
		if (!in.read_string().equals(id())) throw new org.omg.CORBA.MARSHAL("wrong id");
		result.errors = fr.esrf.Tango.NamedDevErrorListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.MultiDevFailed s)
	{
		out.write_string(id());
		fr.esrf.Tango.NamedDevErrorListHelper.write(out,s.errors);
	}
}
