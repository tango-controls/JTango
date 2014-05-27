package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarFloatArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarFloatArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public float[] value;

	public DevVarFloatArrayHolder ()
	{
	}
	public DevVarFloatArrayHolder (final float[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarFloatArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarFloatArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarFloatArrayHelper.write (out,value);
	}
}
