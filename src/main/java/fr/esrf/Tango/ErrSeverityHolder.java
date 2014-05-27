package fr.esrf.Tango;
/**
 * Generated from IDL enum "ErrSeverity".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
