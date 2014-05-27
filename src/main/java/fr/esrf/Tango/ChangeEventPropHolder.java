package fr.esrf.Tango;

/**
 * Generated from IDL struct "ChangeEventProp".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class ChangeEventPropHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.ChangeEventProp value;

	public ChangeEventPropHolder ()
	{
	}
	public ChangeEventPropHolder(final fr.esrf.Tango.ChangeEventProp initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.ChangeEventPropHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.ChangeEventPropHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.ChangeEventPropHelper.write(_out, value);
	}
}
