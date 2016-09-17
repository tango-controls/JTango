package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarDoubleArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarDoubleArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public double[] value;

	public DevVarDoubleArrayHolder ()
	{
	}
	public DevVarDoubleArrayHolder (final double[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarDoubleArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarDoubleArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarDoubleArrayHelper.write (out,value);
	}
}
