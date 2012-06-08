package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevVarFloatArray"
 *	@author JacORB IDL compiler 
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
