package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevVarLongStringArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarLongStringArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevVarLongStringArray value;

	public DevVarLongStringArrayHolder ()
	{
	}
	public DevVarLongStringArrayHolder(final fr.esrf.Tango.DevVarLongStringArray initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevVarLongStringArrayHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevVarLongStringArrayHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevVarLongStringArrayHelper.write(_out, value);
	}
}
