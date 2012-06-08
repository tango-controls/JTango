package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "NamedDevError"
 *	@author JacORB IDL compiler 
 */

public final class NamedDevErrorHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.NamedDevErrorHelper.id(),"NamedDevError",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("index_in_call", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("err_list", fr.esrf.Tango.DevErrorListHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.NamedDevError s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.NamedDevError extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/NamedDevError:1.0";
	}
	public static fr.esrf.Tango.NamedDevError read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.NamedDevError result = new fr.esrf.Tango.NamedDevError();
		result.name=in.read_string();
		result.index_in_call=in.read_long();
		result.err_list = fr.esrf.Tango.DevErrorListHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.NamedDevError s)
	{
		out.write_string(s.name);
		out.write_long(s.index_in_call);
		fr.esrf.Tango.DevErrorListHelper.write(out,s.err_list);
	}
}
