package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttrQualityList".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at Dec 11, 2012 4:18:48 PM
 */

public final class AttrQualityListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttrQuality[] value;

	public AttrQualityListHolder ()
	{
	}
	public AttrQualityListHolder (final fr.esrf.Tango.AttrQuality[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttrQualityListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttrQualityListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttrQualityListHelper.write (out,value);
	}
}
