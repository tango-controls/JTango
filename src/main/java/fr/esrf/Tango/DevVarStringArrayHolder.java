package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarStringArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarStringArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public DevVarStringArrayHolder ()
	{
	}
	public DevVarStringArrayHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarStringArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarStringArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarStringArrayHelper.write (out,value);
	}
}
