package fr.esrf.Tango;


/**
 * Generated from IDL struct "DevPipeData".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DevPipeDataHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DevPipeDataHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevPipeDataHelper.id(),"DevPipeData",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("time", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.TimeValHelper.id(),"TimeVal",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("tv_sec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_usec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("tv_nsec", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null)}), null),new org.omg.CORBA.StructMember("data_blob", org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevPipeBlobHelper.id(),"DevPipeBlob",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("blob_data", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarPipeDataEltArrayHelper.id(), "DevVarPipeDataEltArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_recursive_tc("IDL:Tango/DevPipeDataElt:1.0"))), null)}), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevPipeData s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevPipeData extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/DevPipeData:1.0";
	}
	public static fr.esrf.Tango.DevPipeData read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevPipeData result = new fr.esrf.Tango.DevPipeData();
		result.name=in.read_string();
		result.time=fr.esrf.Tango.TimeValHelper.read(in);
		result.data_blob=fr.esrf.Tango.DevPipeBlobHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevPipeData s)
	{
		java.lang.String tmpResult81 = s.name;
out.write_string( tmpResult81 );
		fr.esrf.Tango.TimeValHelper.write(out,s.time);
		fr.esrf.Tango.DevPipeBlobHelper.write(out,s.data_blob);
	}
}
