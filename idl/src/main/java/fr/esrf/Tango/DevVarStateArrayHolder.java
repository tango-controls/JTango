package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarStateArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarStateArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevState[] value;

	public DevVarStateArrayHolder ()
	{
	}
	public DevVarStateArrayHolder (final fr.esrf.Tango.DevState[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarStateArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarStateArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarStateArrayHelper.write (out,value);
	}
}
