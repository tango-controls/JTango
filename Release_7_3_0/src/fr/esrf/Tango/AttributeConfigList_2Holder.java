package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "AttributeConfigList_2"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfigList_2Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig_2[] value;

	public AttributeConfigList_2Holder ()
	{
	}
	public AttributeConfigList_2Holder (final fr.esrf.Tango.AttributeConfig_2[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttributeConfigList_2Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttributeConfigList_2Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttributeConfigList_2Helper.write (out,value);
	}
}
