package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevCmdHistoryList".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevCmdHistoryListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdHistory[] value;

	public DevCmdHistoryListHolder ()
	{
	}
	public DevCmdHistoryListHolder (final fr.esrf.Tango.DevCmdHistory[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevCmdHistoryListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevCmdHistoryListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevCmdHistoryListHelper.write (out,value);
	}
}
