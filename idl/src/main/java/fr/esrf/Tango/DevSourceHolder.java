package fr.esrf.Tango;
/**
 * Generated from IDL enum "DevSource".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevSourceHolder
	implements org.omg.CORBA.portable.Streamable
{
	public DevSource value;

	public DevSourceHolder ()
	{
	}
	public DevSourceHolder (final DevSource initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevSourceHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevSourceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevSourceHelper.write (out,value);
	}
}
