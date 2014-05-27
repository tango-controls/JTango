package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeConfig_2".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
