package fr.esrf.Tango;
/**
 * Generated from IDL enum "DevState".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public DevState value;

	public DevStateHolder ()
	{
	}
	public DevStateHolder (final DevState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevStateHelper.write (out,value);
	}
}
