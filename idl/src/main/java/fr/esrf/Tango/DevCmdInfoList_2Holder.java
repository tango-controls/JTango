package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevCmdInfoList_2".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevCmdInfoList_2Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdInfo_2[] value;

	public DevCmdInfoList_2Holder ()
	{
	}
	public DevCmdInfoList_2Holder (final fr.esrf.Tango.DevCmdInfo_2[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevCmdInfoList_2Helper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevCmdInfoList_2Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevCmdInfoList_2Helper.write (out,value);
	}
}
