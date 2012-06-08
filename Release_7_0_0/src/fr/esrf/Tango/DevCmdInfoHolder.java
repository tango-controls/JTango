package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevCmdInfo"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdInfoHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdInfo value;

	public DevCmdInfoHolder ()
	{
	}
	public DevCmdInfoHolder(final fr.esrf.Tango.DevCmdInfo initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevCmdInfoHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevCmdInfoHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevCmdInfoHelper.write(_out, value);
	}
}
