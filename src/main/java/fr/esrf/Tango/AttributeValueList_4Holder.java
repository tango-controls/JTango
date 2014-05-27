package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeValueList_4".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeValueList_4Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeValue_4[] value;

	public AttributeValueList_4Holder ()
	{
	}
	public AttributeValueList_4Holder (final fr.esrf.Tango.AttributeValue_4[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeValueList_4Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeValueList_4Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeValueList_4Helper.write (out,value);
	}
}
