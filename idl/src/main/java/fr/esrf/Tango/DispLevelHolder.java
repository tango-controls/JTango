package fr.esrf.Tango;
/**
 * Generated from IDL enum "DispLevel".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at Dec 11, 2012 4:18:48 PM
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
