package fr.esrf.Tango;

/**
 * Generated from IDL alias "JavaUUID".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class JavaUUIDHolder
	implements org.omg.CORBA.portable.Streamable
{
	public long[] value;

	public JavaUUIDHolder ()
	{
	}
	public JavaUUIDHolder (final long[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return JavaUUIDHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = JavaUUIDHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		JavaUUIDHelper.write (out,value);
	}
}
