package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevVarDoubleStringArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevVarDoubleStringArrayHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevVarDoubleStringArrayHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevVarDoubleStringArrayHelper.id(),"DevVarDoubleStringArray",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dvalue", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarDoubleArrayHelper.id(), "DevVarDoubleArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(7)))), null),new org.omg.CORBA.StructMember("svalue", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)});
				}
			}
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
