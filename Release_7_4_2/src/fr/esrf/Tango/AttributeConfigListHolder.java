package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "AttributeConfigList"
 *	@author JacORB IDL compiler 
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
