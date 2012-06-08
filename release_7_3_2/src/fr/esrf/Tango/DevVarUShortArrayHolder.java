package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarUShortArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarUShortArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public short[] value;

	public DevVarUShortArrayHolder ()
	{
	}
	public DevVarUShortArrayHolder (final short[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarUShortArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarUShortArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarUShortArrayHelper.write (out,value);
	}
}
