package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "ChangeEventProp"
 *	@author JacORB IDL compiler 
 */

public final class ChangeEventPropHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.ChangeEventPropHelper.id(),"ChangeEventProp",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("rel_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("abs_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.ChangeEventProp s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.ChangeEventProp extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/ChangeEventProp:1.0";
	}
	public static fr.esrf.Tango.ChangeEventProp read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.ChangeEventProp result = new fr.esrf.Tango.ChangeEventProp();
		result.rel_change=in.read_string();
		result.abs_change=in.read_string();
		result.extensions = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.ChangeEventProp s)
	{
		out.write_string(s.rel_change);
		out.write_string(s.abs_change);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
