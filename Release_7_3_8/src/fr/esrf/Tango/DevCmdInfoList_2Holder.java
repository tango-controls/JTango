package fr.esrf.Tango;

/**
 *	Generated from IDL definition of alias "DevCmdInfoList_2"
 *	@author JacORB IDL compiler 
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
