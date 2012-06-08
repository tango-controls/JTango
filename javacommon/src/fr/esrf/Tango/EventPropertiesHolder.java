package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "EventProperties"
 *	@author JacORB IDL compiler 
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
