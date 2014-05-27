package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevAttrHistoryList".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevAttrHistoryListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevAttrHistory[] value;

	public DevAttrHistoryListHolder ()
	{
	}
	public DevAttrHistoryListHolder (final fr.esrf.Tango.DevAttrHistory[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevAttrHistoryListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevAttrHistoryListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevAttrHistoryListHelper.write (out,value);
	}
}
