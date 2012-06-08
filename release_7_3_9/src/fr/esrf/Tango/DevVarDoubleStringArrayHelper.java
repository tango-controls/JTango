package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevVarDoubleStringArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarDoubleStringArrayHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevVarDoubleStringArrayHelper.id(),"DevVarDoubleStringArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dvalue", fr.esrf.Tango.DevVarDoubleArrayHelper.type(), null),new org.omg.CORBA.StructMember("svalue", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevVarDoubleStringArray s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevVarDoubleStringArray extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevVarDoubleStringArray:1.0";
	}
	public static fr.esrf.Tango.DevVarDoubleStringArray read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevVarDoubleStringArray result = new fr.esrf.Tango.DevVarDoubleStringArray();
		result.dvalue = fr.esrf.Tango.DevVarDoubleArrayHelper.read(in);
		result.svalue = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevVarDoubleStringArray s)
	{
		fr.esrf.Tango.DevVarDoubleArrayHelper.write(out,s.dvalue);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.svalue);
	}
}
