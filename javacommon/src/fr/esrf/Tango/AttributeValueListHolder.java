package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "AttributeValueList"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValueListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeValue[] value;

	public AttributeValueListHolder ()
	{
	}
	public AttributeValueListHolder (final fr.esrf.Tango.AttributeValue[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeValueListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeValueListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeValueListHelper.write (out,value);
	}
}
