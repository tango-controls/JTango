package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarULong64Array"
 *	@author JacORB IDL compiler 
 */

public final class DevVarULong64ArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public long[] value;

	public DevVarULong64ArrayHolder ()
	{
	}
	public DevVarULong64ArrayHolder (final long[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarULong64ArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarULong64ArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarULong64ArrayHelper.write (out,value);
	}
}
