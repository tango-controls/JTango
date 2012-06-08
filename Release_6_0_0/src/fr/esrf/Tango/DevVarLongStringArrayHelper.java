package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevVarLongStringArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarLongStringArrayHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevVarLongStringArrayHelper.id(),"DevVarLongStringArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("lvalue", fr.esrf.Tango.DevVarLongArrayHelper.type(), null),new org.omg.CORBA.StructMember("svalue", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevVarLongStringArray s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevVarLongStringArray extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevVarLongStringArray:1.0";
	}
	public static fr.esrf.Tango.DevVarLongStringArray read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevVarLongStringArray result = new fr.esrf.Tango.DevVarLongStringArray();
		result.lvalue = fr.esrf.Tango.DevVarLongArrayHelper.read(in);
		result.svalue = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevVarLongStringArray s)
	{
		fr.esrf.Tango.DevVarLongArrayHelper.write(out,s.lvalue);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.svalue);
	}
}
