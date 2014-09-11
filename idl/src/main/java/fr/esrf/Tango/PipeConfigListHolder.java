package fr.esrf.Tango;

/**
 * Generated from IDL alias "PipeConfigList".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class PipeConfigListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.PipeConfig[] value;

	public PipeConfigListHolder ()
	{
	}
	public PipeConfigListHolder (final fr.esrf.Tango.PipeConfig[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PipeConfigListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PipeConfigListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PipeConfigListHelper.write (out,value);
	}
}
