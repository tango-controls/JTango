package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarShortArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarShortArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public short[] value;

	public DevVarShortArrayHolder ()
	{
	}
	public DevVarShortArrayHolder (final short[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarShortArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarShortArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarShortArrayHelper.write (out,value);
	}
}
