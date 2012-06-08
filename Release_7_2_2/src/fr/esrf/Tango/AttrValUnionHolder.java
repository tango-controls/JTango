package fr.esrf.Tango;
/**
 *	Generated from IDL definition of union "AttrValUnion"
 *	@author JacORB IDL compiler 
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
