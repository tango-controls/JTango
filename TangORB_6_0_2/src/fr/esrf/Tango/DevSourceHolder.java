package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "DevSource"
 *	@author JacORB IDL compiler 
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
