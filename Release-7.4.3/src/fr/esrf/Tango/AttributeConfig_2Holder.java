package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeConfig_2"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfig_2Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig_2 value;

	public AttributeConfig_2Holder ()
	{
	}
	public AttributeConfig_2Holder(final fr.esrf.Tango.AttributeConfig_2 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeConfig_2Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeConfig_2Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeConfig_2Helper.write(_out, value);
	}
}
