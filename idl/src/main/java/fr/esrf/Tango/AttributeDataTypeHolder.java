package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttributeDataType".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at Dec 11, 2012 4:18:48 PM
 */

public final class AttributeDataTypeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public AttributeDataType value;

	public AttributeDataTypeHolder ()
	{
	}
	public AttributeDataTypeHolder (final AttributeDataType initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeDataTypeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeDataTypeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeDataTypeHelper.write (out,value);
	}
}
