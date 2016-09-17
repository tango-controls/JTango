package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevEncoded".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevEncodedHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevEncodedHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevEncodedHelper.id(),"DevEncoded",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("encoded_format", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevStringHelper.id(), "DevString",org.omg.CORBA.ORB.init().create_string_tc(0)), null),new org.omg.CORBA.StructMember("encoded_data", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarCharArrayHelper.id(), "DevVarCharArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10)))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevEncoded s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevEncoded extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevEncoded:1.0";
	}
	public static fr.esrf.Tango.DevEncoded read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevEncoded result = new fr.esrf.Tango.DevEncoded();
		result.encoded_format=in.read_string();
		result.encoded_data = fr.esrf.Tango.DevVarCharArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevEncoded s)
	{
		java.lang.String tmpResult2 = s.encoded_format;
out.write_string( tmpResult2 );
		fr.esrf.Tango.DevVarCharArrayHelper.write(out,s.encoded_data);
	}
}
