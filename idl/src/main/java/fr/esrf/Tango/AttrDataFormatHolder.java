package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttrDataFormat".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttrDataFormatHolder
	implements org.omg.CORBA.portable.Streamable
{
	public AttrDataFormat value;

	public AttrDataFormatHolder ()
	{
	}
	public AttrDataFormatHolder (final AttrDataFormat initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttrDataFormatHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttrDataFormatHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttrDataFormatHelper.write (out,value);
	}
}
