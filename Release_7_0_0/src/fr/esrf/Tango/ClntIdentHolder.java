package fr.esrf.Tango;
/**
 *	Generated from IDL definition of union "ClntIdent"
 *	@author JacORB IDL compiler 
 */

public final class ClntIdentHolder
	implements org.omg.CORBA.portable.Streamable
{
	public ClntIdent value;

	public ClntIdentHolder ()
	{
	}
	public ClntIdentHolder (final ClntIdent initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ClntIdentHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ClntIdentHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ClntIdentHelper.write (out, value);
	}
}
