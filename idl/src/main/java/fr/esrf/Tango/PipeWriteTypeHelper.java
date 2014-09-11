package fr.esrf.Tango;
/**
 * Generated from IDL enum "PipeWriteType".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class PipeWriteTypeHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(PipeWriteTypeHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.PipeWriteTypeHelper.id(),"PipeWriteType",new String[]{"PIPE_READ","PIPE_READ_WRITE","PIPE_WT_UNKNOWN"});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.PipeWriteType s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.PipeWriteType extract (final org.omg.CORBA.Any any)
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
		return "IDL:Tango/PipeWriteType:1.0";
	}
	public static PipeWriteType read (final org.omg.CORBA.portable.InputStream in)
	{
		return PipeWriteType.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final PipeWriteType s)
	{
		out.write_long(s.value());
	}
}
