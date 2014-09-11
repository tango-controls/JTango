package fr.esrf.Tango;

/**
 * Generated from IDL struct "NamedDevError".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class NamedDevErrorHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.NamedDevError value;

	public NamedDevErrorHolder ()
	{
	}
	public NamedDevErrorHolder(final fr.esrf.Tango.NamedDevError initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.NamedDevErrorHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.NamedDevErrorHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.NamedDevErrorHelper.write(_out, value);
	}
}
