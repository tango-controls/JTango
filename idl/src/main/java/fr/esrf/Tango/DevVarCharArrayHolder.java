package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarCharArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarCharArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public byte[] value;

	public DevVarCharArrayHolder ()
	{
	}
	public DevVarCharArrayHolder (final byte[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarCharArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarCharArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarCharArrayHelper.write (out,value);
	}
}
