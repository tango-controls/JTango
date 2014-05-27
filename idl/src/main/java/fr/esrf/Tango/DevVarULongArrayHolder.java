package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarULongArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarULongArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public int[] value;

	public DevVarULongArrayHolder ()
	{
	}
	public DevVarULongArrayHolder (final int[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarULongArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarULongArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarULongArrayHelper.write (out,value);
	}
}
