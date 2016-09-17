package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeConfigList_3".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttributeConfigList_3Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig_3[] value;

	public AttributeConfigList_3Holder ()
	{
	}
	public AttributeConfigList_3Holder (final fr.esrf.Tango.AttributeConfig_3[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeConfigList_3Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeConfigList_3Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeConfigList_3Helper.write (out,value);
	}
}
