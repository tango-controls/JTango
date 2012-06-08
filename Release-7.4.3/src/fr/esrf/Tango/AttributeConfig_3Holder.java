package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeConfig_3"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfig_3Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig_3 value;

	public AttributeConfig_3Holder ()
	{
	}
	public AttributeConfig_3Holder(final fr.esrf.Tango.AttributeConfig_3 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeConfig_3Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeConfig_3Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeConfig_3Helper.write(_out, value);
	}
}
