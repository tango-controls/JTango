package fr.esrf.Tango;
/**
 * Generated from IDL union "AttrValUnion".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
