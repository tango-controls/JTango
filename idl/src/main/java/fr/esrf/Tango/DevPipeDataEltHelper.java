package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevPipeDataElt".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevPipeDataEltHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevPipeDataEltHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevPipeDataEltHelper.id(),"DevPipeDataElt",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("value", fr.esrf.Tango.AttrValUnionHelper.type(), null),new org.omg.CORBA.StructMember("inner_blob", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarPipeDataEltArrayHelper.id(), "DevVarPipeDataEltArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_recursive_tc("IDL:Tango/DevPipeDataElt:1.0"))), null),new org.omg.CORBA.StructMember("inner_blob_name", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevPipeDataElt s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevPipeDataElt extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevPipeDataElt:1.0";
	}
	public static fr.esrf.Tango.DevPipeDataElt read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevPipeDataElt result = new fr.esrf.Tango.DevPipeDataElt();
		result.name=in.read_string();
		result.value=fr.esrf.Tango.AttrValUnionHelper.read(in);
		result.inner_blob = fr.esrf.Tango.DevVarPipeDataEltArrayHelper.read(in);
		result.inner_blob_name=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevPipeDataElt s)
	{
		java.lang.String tmpResult78 = s.name;
out.write_string( tmpResult78 );
		fr.esrf.Tango.AttrValUnionHelper.write(out,s.value);
		fr.esrf.Tango.DevVarPipeDataEltArrayHelper.write(out,s.inner_blob);
		java.lang.String tmpResult79 = s.inner_blob_name;
out.write_string( tmpResult79 );
	}
}
