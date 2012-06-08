package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarLong64Array"
 *	@author JacORB IDL compiler 
 */

public final class DevVarLong64ArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public long[] value;

	public DevVarLong64ArrayHolder ()
	{
	}
	public DevVarLong64ArrayHolder (final long[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarLong64ArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarLong64ArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarLong64ArrayHelper.write (out,value);
	}
}
