package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeValueList_3".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttributeValueList_3Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeValue_3[] value;

	public AttributeValueList_3Holder ()
	{
	}
	public AttributeValueList_3Holder (final fr.esrf.Tango.AttributeValue_3[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeValueList_3Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeValueList_3Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeValueList_3Helper.write (out,value);
	}
}
