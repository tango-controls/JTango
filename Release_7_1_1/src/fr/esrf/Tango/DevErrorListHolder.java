package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevErrorList"
 *	@author JacORB IDL compiler 
 */

public final class DevErrorListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevError[] value;

	public DevErrorListHolder ()
	{
	}
	public DevErrorListHolder (final fr.esrf.Tango.DevError[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevErrorListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevErrorListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevErrorListHelper.write (out,value);
	}
}
