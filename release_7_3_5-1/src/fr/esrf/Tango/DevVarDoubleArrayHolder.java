package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarDoubleArray"
 *	@author JacORB IDL compiler 
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
