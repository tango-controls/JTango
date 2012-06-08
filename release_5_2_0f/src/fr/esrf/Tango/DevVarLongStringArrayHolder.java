package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevVarLongStringArray"
 *	@author JacORB IDL compiler 
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
