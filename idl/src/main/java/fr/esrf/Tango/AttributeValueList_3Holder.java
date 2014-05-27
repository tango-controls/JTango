package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeValueList_3".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
