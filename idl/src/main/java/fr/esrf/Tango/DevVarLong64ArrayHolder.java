package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarLong64Array".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
