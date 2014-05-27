package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeConfigList_5".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeConfigList_5Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig_5[] value;

	public AttributeConfigList_5Holder ()
	{
	}
	public AttributeConfigList_5Holder (final fr.esrf.Tango.AttributeConfig_5[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeConfigList_5Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeConfigList_5Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeConfigList_5Helper.write (out,value);
	}
}
