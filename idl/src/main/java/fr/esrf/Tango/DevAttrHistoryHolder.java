package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevAttrHistory".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
