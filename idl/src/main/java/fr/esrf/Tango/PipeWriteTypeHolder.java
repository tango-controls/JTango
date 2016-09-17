package fr.esrf.Tango;
/**
 * Generated from IDL enum "PipeWriteType".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class PipeWriteTypeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public PipeWriteType value;

	public PipeWriteTypeHolder ()
	{
	}
	public PipeWriteTypeHolder (final PipeWriteType initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PipeWriteTypeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PipeWriteTypeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PipeWriteTypeHelper.write (out,value);
	}
}
