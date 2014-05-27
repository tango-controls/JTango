package fr.esrf.Tango;
/**
 * Generated from IDL union "ClntIdent".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
