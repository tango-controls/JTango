package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevCmdInfo_2"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdInfo_2Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdInfo_2 value;

	public DevCmdInfo_2Holder ()
	{
	}
	public DevCmdInfo_2Holder(final fr.esrf.Tango.DevCmdInfo_2 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevCmdInfo_2Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevCmdInfo_2Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevCmdInfo_2Helper.write(_out, value);
	}
}
