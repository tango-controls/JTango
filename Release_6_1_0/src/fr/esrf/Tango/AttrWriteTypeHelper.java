package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "AttrWriteType"
 *	@author JacORB IDL compiler 
 */

public final class AttrWriteTypeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttrWriteTypeHelper.id(),"AttrWriteType",new String[]{"READ","READ_WITH_WRITE","WRITE","READ_WRITE"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttrWriteType s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttrWriteType extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/AttrWriteType:1.0";
	}
	public static AttrWriteType read (final org.omg.CORBA.portable.InputStream in)
	{
		return AttrWriteType.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final AttrWriteType s)
	{
		out.write_long(s.value());
	}
}
