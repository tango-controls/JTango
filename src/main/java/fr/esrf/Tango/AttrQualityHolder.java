package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttrQuality".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at Dec 11, 2012 4:18:48 PM
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
