package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarULong64Array".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
