package fr.esrf.Tango;


/**
 * Generated from IDL struct "PipeConfig".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class PipeConfigHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(PipeConfigHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.PipeConfigHelper.id(),"PipeConfig",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("label", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("level", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DispLevelHelper.id(),"DispLevel",new String[]{"OPERATOR","EXPERT","DL_UNKNOWN"}), null),new org.omg.CORBA.StructMember("writable", org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.PipeWriteTypeHelper.id(),"PipeWriteType",new String[]{"PIPE_READ","PIPE_READ_WRITE","PIPE_WT_UNKNOWN"}), null),new org.omg.CORBA.StructMember("extensions", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.PipeConfig s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.PipeConfig extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/PipeConfig:1.0";
	}
	public static fr.esrf.Tango.PipeConfig read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.PipeConfig result = new fr.esrf.Tango.PipeConfig();
		result.name=in.read_string();
		result.description=in.read_string();
		result.label=in.read_string();
		result.level=fr.esrf.Tango.DispLevelHelper.read(in);
		result.writable=fr.esrf.Tango.PipeWriteTypeHelper.read(in);
		result.extensions = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.PipeConfig s)
	{
		java.lang.String tmpResult75 = s.name;
out.write_string( tmpResult75 );
		java.lang.String tmpResult76 = s.description;
out.write_string( tmpResult76 );
		java.lang.String tmpResult77 = s.label;
out.write_string( tmpResult77 );
		fr.esrf.Tango.DispLevelHelper.write(out,s.level);
		fr.esrf.Tango.PipeWriteTypeHelper.write(out,s.writable);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
