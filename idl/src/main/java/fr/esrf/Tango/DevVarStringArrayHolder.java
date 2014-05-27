package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarStringArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
