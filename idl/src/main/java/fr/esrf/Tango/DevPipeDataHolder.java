package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevPipeData".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevPipeDataHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevPipeData value;

	public DevPipeDataHolder ()
	{
	}
	public DevPipeDataHolder(final fr.esrf.Tango.DevPipeData initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevPipeDataHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevPipeDataHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevPipeDataHelper.write(_out, value);
	}
}
