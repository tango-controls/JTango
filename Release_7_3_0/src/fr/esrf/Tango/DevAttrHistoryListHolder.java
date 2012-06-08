package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevAttrHistoryList"
 *	@author JacORB IDL compiler 
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
