package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "ErrSeverity"
 *	@author JacORB IDL compiler 
 */

public final class ErrSeverityHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.ErrSeverityHelper.id(),"ErrSeverity",new String[]{"WARN","ERR","PANIC"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.ErrSeverity s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.ErrSeverity extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/ErrSeverity:1.0";
	}
	public static ErrSeverity read (final org.omg.CORBA.portable.InputStream in)
	{
		return ErrSeverity.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final ErrSeverity s)
	{
		out.write_long(s.value());
	}
}
