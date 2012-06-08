package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevCmdHistory"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdHistoryHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdHistory value;

	public DevCmdHistoryHolder ()
	{
	}
	public DevCmdHistoryHolder(final fr.esrf.Tango.DevCmdHistory initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevCmdHistoryHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevCmdHistoryHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevCmdHistoryHelper.write(_out, value);
	}
}
