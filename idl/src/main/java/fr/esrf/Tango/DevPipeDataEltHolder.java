package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevPipeDataElt".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevPipeDataEltHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevPipeDataElt value;

	public DevPipeDataEltHolder ()
	{
	}
	public DevPipeDataEltHolder(final fr.esrf.Tango.DevPipeDataElt initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevPipeDataEltHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevPipeDataEltHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevPipeDataEltHelper.write(_out, value);
	}
}
