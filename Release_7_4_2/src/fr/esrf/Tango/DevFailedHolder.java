package fr.esrf.Tango;

/**
 *	Generated from IDL definition of exception "DevFailed"
 *	@author JacORB IDL compiler 
 */

public final class DevFailedHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevFailed value;

	public DevFailedHolder ()
	{
	}
	public DevFailedHolder(final fr.esrf.Tango.DevFailed initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevFailedHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevFailedHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevFailedHelper.write(_out, value);
	}
}
