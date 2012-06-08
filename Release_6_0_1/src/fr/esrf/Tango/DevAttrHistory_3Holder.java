package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevAttrHistory_3"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistory_3Holder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevAttrHistory_3 value;

	public DevAttrHistory_3Holder ()
	{
	}
	public DevAttrHistory_3Holder(final fr.esrf.Tango.DevAttrHistory_3 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevAttrHistory_3Helper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevAttrHistory_3Helper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevAttrHistory_3Helper.write(_out, value);
	}
}
