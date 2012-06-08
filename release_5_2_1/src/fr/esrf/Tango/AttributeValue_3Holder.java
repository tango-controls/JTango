package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeValue_3"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValue_3Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeValue_3 value;

	public AttributeValue_3Holder ()
	{
	}
	public AttributeValue_3Holder(final fr.esrf.Tango.AttributeValue_3 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeValue_3Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeValue_3Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeValue_3Helper.write(_out, value);
	}
}
