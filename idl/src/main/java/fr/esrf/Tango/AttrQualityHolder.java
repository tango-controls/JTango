package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttrQuality".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttrQualityHolder
	implements org.omg.CORBA.portable.Streamable
{
	public AttrQuality value;

	public AttrQualityHolder ()
	{
	}
	public AttrQualityHolder (final AttrQuality initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttrQualityHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttrQualityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttrQualityHelper.write (out,value);
	}
}
