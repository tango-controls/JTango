package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevCmdInfoList".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevCmdInfoListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdInfo[] value;

	public DevCmdInfoListHolder ()
	{
	}
	public DevCmdInfoListHolder (final fr.esrf.Tango.DevCmdInfo[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevCmdInfoListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevCmdInfoListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevCmdInfoListHelper.write (out,value);
	}
}
