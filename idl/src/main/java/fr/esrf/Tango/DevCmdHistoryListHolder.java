package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevCmdHistoryList".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
