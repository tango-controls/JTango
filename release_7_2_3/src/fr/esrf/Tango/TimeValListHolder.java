package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "TimeValList"
 *	@author JacORB IDL compiler 
 */

public final class TimeValListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.TimeVal[] value;

	public TimeValListHolder ()
	{
	}
	public TimeValListHolder (final fr.esrf.Tango.TimeVal[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return TimeValListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TimeValListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		TimeValListHelper.write (out,value);
	}
}
