package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarEncodedArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevVarEncodedArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevEncoded[] value;

	public DevVarEncodedArrayHolder ()
	{
	}
	public DevVarEncodedArrayHolder (final fr.esrf.Tango.DevEncoded[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarEncodedArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarEncodedArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarEncodedArrayHelper.write (out,value);
	}
}
