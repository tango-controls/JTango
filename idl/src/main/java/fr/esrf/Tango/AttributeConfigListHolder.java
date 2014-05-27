package fr.esrf.Tango;

/**
 * Generated from IDL alias "AttributeConfigList".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeConfigListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig[] value;

	public AttributeConfigListHolder ()
	{
	}
	public AttributeConfigListHolder (final fr.esrf.Tango.AttributeConfig[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeConfigListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeConfigListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeConfigListHelper.write (out,value);
	}
}
