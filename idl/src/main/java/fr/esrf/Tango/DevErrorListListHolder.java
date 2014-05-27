package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevErrorListList".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevErrorListListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevError[][] value;

	public DevErrorListListHolder ()
	{
	}
	public DevErrorListListHolder (final fr.esrf.Tango.DevError[][] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevErrorListListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevErrorListListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevErrorListListHelper.write (out,value);
	}
}
