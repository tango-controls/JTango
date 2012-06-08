package fr.esrf.Tango;

/**
 *	Generated from IDL definition of exception "MultiDevFailed"
 *	@author JacORB IDL compiler 
 */

public final class MultiDevFailedHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.MultiDevFailed value;

	public MultiDevFailedHolder ()
	{
	}
	public MultiDevFailedHolder(final fr.esrf.Tango.MultiDevFailed initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.MultiDevFailedHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.MultiDevFailedHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.MultiDevFailedHelper.write(_out, value);
	}
}
