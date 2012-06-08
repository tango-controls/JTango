package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "LockerLanguage"
 *	@author JacORB IDL compiler 
 */

public final class LockerLanguageHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.LockerLanguageHelper.id(),"LockerLanguage",new String[]{"CPP","JAVA"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.LockerLanguage s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.LockerLanguage extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/LockerLanguage:1.0";
	}
	public static LockerLanguage read (final org.omg.CORBA.portable.InputStream in)
	{
		return LockerLanguage.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final LockerLanguage s)
	{
		out.write_long(s.value());
	}
}
