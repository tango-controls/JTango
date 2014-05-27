package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeValue_4".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeValue_4Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeValue_4 value;

	public AttributeValue_4Holder ()
	{
	}
	public AttributeValue_4Holder(final fr.esrf.Tango.AttributeValue_4 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeValue_4Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeValue_4Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeValue_4Helper.write(_out, value);
	}
}
