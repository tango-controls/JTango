package fr.esrf.Tango;

/**
 * Generated from IDL struct "ZmqCallInfo".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class ZmqCallInfoHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.ZmqCallInfo value;

	public ZmqCallInfoHolder ()
	{
	}
	public ZmqCallInfoHolder(final fr.esrf.Tango.ZmqCallInfo initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.ZmqCallInfoHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.ZmqCallInfoHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.ZmqCallInfoHelper.write(_out, value);
	}
}
