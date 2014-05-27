package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeValueList_5".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
