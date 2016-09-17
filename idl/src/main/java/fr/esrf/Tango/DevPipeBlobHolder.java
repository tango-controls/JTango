package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevPipeBlob".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevPipeBlobHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevPipeBlob value;

	public DevPipeBlobHolder ()
	{
	}
	public DevPipeBlobHolder(final fr.esrf.Tango.DevPipeBlob initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return fr.esrf.Tango.DevPipeBlobHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = fr.esrf.Tango.DevPipeBlobHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		fr.esrf.Tango.DevPipeBlobHelper.write(_out, value);
	}
}
