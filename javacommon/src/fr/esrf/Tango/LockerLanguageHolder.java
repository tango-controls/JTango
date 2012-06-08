package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "LockerLanguage"
 *	@author JacORB IDL compiler 
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
