package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevPipeBlob".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevPipeBlobHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevPipeBlobHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevPipeBlobHelper.id(),"DevPipeBlob",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("blob_data", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarPipeDataEltArrayHelper.id(), "DevVarPipeDataEltArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_recursive_tc("IDL:Tango/DevPipeDataElt:1.0"))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevPipeBlob s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevPipeBlob extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevPipeBlob:1.0";
	}
	public static fr.esrf.Tango.DevPipeBlob read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevPipeBlob result = new fr.esrf.Tango.DevPipeBlob();
		result.name=in.read_string();
		result.blob_data = fr.esrf.Tango.DevVarPipeDataEltArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevPipeBlob s)
	{
		java.lang.String tmpResult80 = s.name;
out.write_string( tmpResult80 );
		fr.esrf.Tango.DevVarPipeDataEltArrayHelper.write(out,s.blob_data);
	}
}
