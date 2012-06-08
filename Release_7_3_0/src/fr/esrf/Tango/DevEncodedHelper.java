package fr.esrf.Tango;


/**
 *	Generated from IDL definition of struct "DevEncoded"
 *	@author JacORB IDL compiler 
 */

public final class DevEncodedHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevEncodedHelper.id(),"DevEncoded",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("encoded_format", fr.esrf.Tango.DevStringHelper.type(), null),new org.omg.CORBA.StructMember("encoded_data", fr.esrf.Tango.DevVarCharArrayHelper.type(), null)});
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
		return read(any.create_input_stream());
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
		out.write_string(s.encoded_format);
		fr.esrf.Tango.DevVarCharArrayHelper.write(out,s.encoded_data);
	}
}
