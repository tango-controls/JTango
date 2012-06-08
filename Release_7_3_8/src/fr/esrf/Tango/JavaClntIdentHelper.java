package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "JavaClntIdent"
 *	@author JacORB IDL compiler 
 */

public final class JavaClntIdentHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.JavaClntIdentHelper.id(),"JavaClntIdent",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("MainClass", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("uuid", fr.esrf.Tango.JavaUUIDHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.JavaClntIdent s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.JavaClntIdent extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/JavaClntIdent:1.0";
	}
	public static fr.esrf.Tango.JavaClntIdent read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.JavaClntIdent result = new fr.esrf.Tango.JavaClntIdent();
		result.MainClass=in.read_string();
		result.uuid = fr.esrf.Tango.JavaUUIDHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.JavaClntIdent s)
	{
		out.write_string(s.MainClass);
		fr.esrf.Tango.JavaUUIDHelper.write(out,s.uuid);
	}
}
