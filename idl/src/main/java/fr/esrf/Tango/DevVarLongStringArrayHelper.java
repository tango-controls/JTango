package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevVarLongStringArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarLongStringArrayHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevVarLongStringArrayHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevVarLongStringArrayHelper.id(),"DevVarLongStringArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("lvalue", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarLongArrayHelper.id(), "DevVarLongArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)))), null),new org.omg.CORBA.StructMember("svalue", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)});
				}
			}
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
