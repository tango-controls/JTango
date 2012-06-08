package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "TimeVal"
 *	@author JacORB IDL compiler 
 */

public final class TimeValHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.TimeVal value;

	public TimeValHolder ()
	{
	}
	public TimeValHolder(final fr.esrf.Tango.TimeVal initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.TimeValHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.TimeValHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.TimeValHelper.write(_out, value);
	}
}
