package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarCharArray"
 *	@author JacORB IDL compiler 
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
