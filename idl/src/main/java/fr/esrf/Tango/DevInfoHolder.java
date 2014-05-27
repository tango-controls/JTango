package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevInfo".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevInfoHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevInfo value;

	public DevInfoHolder ()
	{
	}
	public DevInfoHolder(final fr.esrf.Tango.DevInfo initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevInfoHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevInfoHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevInfoHelper.write(_out, value);
	}
}
