package fr.esrf.Tango;
/**
 * Generated from IDL enum "LockerLanguage".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class LockerLanguageHolder
	implements org.omg.CORBA.portable.Streamable
{
	public LockerLanguage value;

	public LockerLanguageHolder ()
	{
	}
	public LockerLanguageHolder (final LockerLanguage initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return LockerLanguageHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = LockerLanguageHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		LockerLanguageHelper.write (out,value);
	}
}
