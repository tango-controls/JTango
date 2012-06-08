package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevEncoded"
 *	@author JacORB IDL compiler 
 */

public final class DevEncodedHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevEncoded value;

	public DevEncodedHolder ()
	{
	}
	public DevEncodedHolder(final fr.esrf.Tango.DevEncoded initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevEncodedHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevEncodedHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevEncodedHelper.write(_out, value);
	}
}
