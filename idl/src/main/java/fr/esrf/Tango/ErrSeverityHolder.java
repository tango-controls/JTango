package fr.esrf.Tango;
/**
 * Generated from IDL enum "ErrSeverity".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class ErrSeverityHolder
	implements org.omg.CORBA.portable.Streamable
{
	public ErrSeverity value;

	public ErrSeverityHolder ()
	{
	}
	public ErrSeverityHolder (final ErrSeverity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ErrSeverityHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ErrSeverityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ErrSeverityHelper.write (out,value);
	}
}
