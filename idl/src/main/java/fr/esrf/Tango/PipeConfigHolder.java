package fr.esrf.Tango;

/**
 * Generated from IDL struct "PipeConfig".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class PipeConfigHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.PipeConfig value;

	public PipeConfigHolder ()
	{
	}
	public PipeConfigHolder(final fr.esrf.Tango.PipeConfig initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.PipeConfigHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.PipeConfigHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.PipeConfigHelper.write(_out, value);
	}
}
