package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "DevState"
 *	@author JacORB IDL compiler 
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
