package fr.esrf.Tango;
/**
 * Generated from IDL union "AttrValUnion".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttrValUnionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public AttrValUnion value;

	public AttrValUnionHolder ()
	{
	}
	public AttrValUnionHolder (final AttrValUnion initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttrValUnionHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttrValUnionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttrValUnionHelper.write (out, value);
	}
}
