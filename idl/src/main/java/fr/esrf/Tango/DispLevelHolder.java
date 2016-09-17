package fr.esrf.Tango;
/**
 * Generated from IDL enum "DispLevel".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DispLevelHolder
	implements org.omg.CORBA.portable.Streamable
{
	public DispLevel value;

	public DispLevelHolder ()
	{
	}
	public DispLevelHolder (final DispLevel initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DispLevelHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DispLevelHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DispLevelHelper.write (out,value);
	}
}
