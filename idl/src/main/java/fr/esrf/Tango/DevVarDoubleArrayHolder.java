package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarDoubleArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
