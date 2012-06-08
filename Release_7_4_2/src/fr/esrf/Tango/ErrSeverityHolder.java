package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "ErrSeverity"
 *	@author JacORB IDL compiler 
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
