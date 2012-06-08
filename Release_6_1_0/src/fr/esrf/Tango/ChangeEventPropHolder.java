package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "ChangeEventProp"
 *	@author JacORB IDL compiler 
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
