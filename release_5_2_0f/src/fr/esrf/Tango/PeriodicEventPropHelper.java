package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "PeriodicEventProp"
 *	@author JacORB IDL compiler 
 */

public final class PeriodicEventPropHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.PeriodicEventPropHelper.id(),"PeriodicEventProp",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("period", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.PeriodicEventProp s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.PeriodicEventProp extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/PeriodicEventProp:1.0";
	}
	public static fr.esrf.Tango.PeriodicEventProp read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.PeriodicEventProp result = new fr.esrf.Tango.PeriodicEventProp();
		result.period=in.read_string();
		result.extensions = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.PeriodicEventProp s)
	{
		out.write_string(s.period);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
