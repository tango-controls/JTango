package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarShortArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarShortArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public short[] value;

	public DevVarShortArrayHolder ()
	{
	}
	public DevVarShortArrayHolder (final short[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarShortArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarShortArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarShortArrayHelper.write (out,value);
	}
}
