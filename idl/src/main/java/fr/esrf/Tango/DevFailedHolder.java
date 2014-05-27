package fr.esrf.Tango;

/**
 * Generated from IDL exception "DevFailed".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
