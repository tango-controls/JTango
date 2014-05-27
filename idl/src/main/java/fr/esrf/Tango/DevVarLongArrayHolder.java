package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarLongArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarLongArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public int[] value;

	public DevVarLongArrayHolder ()
	{
	}
	public DevVarLongArrayHolder (final int[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarLongArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarLongArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarLongArrayHelper.write (out,value);
	}
}
