package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevAttrHistory"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistoryHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevAttrHistory value;

	public DevAttrHistoryHolder ()
	{
	}
	public DevAttrHistoryHolder(final fr.esrf.Tango.DevAttrHistory initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevAttrHistoryHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevAttrHistoryHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevAttrHistoryHelper.write(_out, value);
	}
}
