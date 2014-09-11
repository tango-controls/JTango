package fr.esrf.Tango;
/**
 * Generated from IDL enum "AttrWriteType".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class AttrWriteTypeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public AttrWriteType value;

	public AttrWriteTypeHolder ()
	{
	}
	public AttrWriteTypeHolder (final AttrWriteType initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AttrWriteTypeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AttrWriteTypeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AttrWriteTypeHelper.write (out,value);
	}
}
