package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevError"
 *	@author JacORB IDL compiler 
 */

public final class DevErrorHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevError value;

	public DevErrorHolder ()
	{
	}
	public DevErrorHolder(final fr.esrf.Tango.DevError initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevErrorHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevErrorHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevErrorHelper.write(_out, value);
	}
}
