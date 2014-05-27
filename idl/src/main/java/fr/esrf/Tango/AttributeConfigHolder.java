package fr.esrf.Tango;

/**
 * Generated from IDL struct "AttributeConfig".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class AttributeConfigHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeConfig value;

	public AttributeConfigHolder ()
	{
	}
	public AttributeConfigHolder(final fr.esrf.Tango.AttributeConfig initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeConfigHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeConfigHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeConfigHelper.write(_out, value);
	}
}
