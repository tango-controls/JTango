package fr.esrf.Tango;

/**
 * Generated from IDL alias "JavaUUID".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
