package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevAttrHistory_4".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevAttrHistory_4Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevAttrHistory_4 value;

	public DevAttrHistory_4Holder ()
	{
	}
	public DevAttrHistory_4Holder(final fr.esrf.Tango.DevAttrHistory_4 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevAttrHistory_4Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevAttrHistory_4Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevAttrHistory_4Helper.write(_out, value);
	}
}
