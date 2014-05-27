package fr.esrf.Tango;

/**
 * Generated from IDL struct "PeriodicEventProp".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class PeriodicEventPropHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.PeriodicEventProp value;

	public PeriodicEventPropHolder ()
	{
	}
	public PeriodicEventPropHolder(final fr.esrf.Tango.PeriodicEventProp initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.PeriodicEventPropHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.PeriodicEventPropHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.PeriodicEventPropHelper.write(_out, value);
	}
}
