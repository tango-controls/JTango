package fr.esrf.Tango;
/**
 * Generated from IDL enum "LockerLanguage".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class LockerLanguageHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(LockerLanguageHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.LockerLanguageHelper.id(),"LockerLanguage",new String[]{"CPP","JAVA"});
				}
			}
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
