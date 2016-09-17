package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeValueList_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttributeValueList_5Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeValue_5[] value;

	public AttributeValueList_5Holder ()
	{
	}
	public AttributeValueList_5Holder (final fr.esrf.Tango.AttributeValue_5[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeValueList_5Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeValueList_5Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeValueList_5Helper.write (out,value);
	}
}
