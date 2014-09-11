package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarBooleanArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarBooleanArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public boolean[] value;

	public DevVarBooleanArrayHolder ()
	{
	}
	public DevVarBooleanArrayHolder (final boolean[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarBooleanArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarBooleanArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarBooleanArrayHelper.write (out,value);
	}
}
