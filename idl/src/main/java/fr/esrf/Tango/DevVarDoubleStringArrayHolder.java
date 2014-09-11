package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevVarDoubleStringArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarDoubleStringArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevVarDoubleStringArray value;

	public DevVarDoubleStringArrayHolder ()
	{
	}
	public DevVarDoubleStringArrayHolder(final fr.esrf.Tango.DevVarDoubleStringArray initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevVarDoubleStringArrayHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevVarDoubleStringArrayHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevVarDoubleStringArrayHelper.write(_out, value);
	}
}
