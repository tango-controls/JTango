package fr.esrf.Tango;

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
	public void _read (final org.omg.CORBA.portable.InputStream _in)
	{
		value = JavaUUIDHelper.read (_in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		JavaUUIDHelper.write (_out,value);
	}
}
