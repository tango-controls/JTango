package fr.esrf.Tango;

/**
 * Generated from IDL struct "EventProperties".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class EventPropertiesHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.EventProperties value;

	public EventPropertiesHolder ()
	{
	}
	public EventPropertiesHolder(final fr.esrf.Tango.EventProperties initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.EventPropertiesHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.EventPropertiesHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.EventPropertiesHelper.write(_out, value);
	}
}
