package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarLongArray"
 *	@author JacORB IDL compiler 
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
