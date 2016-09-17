package fr.esrf.Tango;

/**
 * Generated from IDL alias "NamedDevErrorList".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class NamedDevErrorListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.NamedDevError[] value;

	public NamedDevErrorListHolder ()
	{
	}
	public NamedDevErrorListHolder (final fr.esrf.Tango.NamedDevError[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return NamedDevErrorListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NamedDevErrorListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		NamedDevErrorListHelper.write (out,value);
	}
}
