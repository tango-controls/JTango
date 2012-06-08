package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "AttributeAlarm"
 *	@author JacORB IDL compiler 
 */

public final class AttributeAlarmHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.AttributeAlarm value;

	public AttributeAlarmHolder ()
	{
	}
	public AttributeAlarmHolder(final fr.esrf.Tango.AttributeAlarm initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.AttributeAlarmHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.AttributeAlarmHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.AttributeAlarmHelper.write(_out, value);
	}
}
