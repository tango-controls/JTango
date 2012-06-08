package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevCmdHistory_4"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdHistory_4Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevCmdHistory_4 value;

	public DevCmdHistory_4Holder ()
	{
	}
	public DevCmdHistory_4Holder(final fr.esrf.Tango.DevCmdHistory_4 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevCmdHistory_4Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevCmdHistory_4Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevCmdHistory_4Helper.write(_out, value);
	}
}
