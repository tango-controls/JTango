package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeDimList".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttributeDimListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeDim[] value;

	public AttributeDimListHolder ()
	{
	}
	public AttributeDimListHolder (final fr.esrf.Tango.AttributeDim[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeDimListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeDimListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeDimListHelper.write (out,value);
	}
}
