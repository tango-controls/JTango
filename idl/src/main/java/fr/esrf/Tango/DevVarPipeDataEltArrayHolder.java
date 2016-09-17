package fr.esrf.Tango;

/**
 * Generated from IDL alias "DevVarPipeDataEltArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DevVarPipeDataEltArrayHolder
	implements org.omg.CORBA.portable.Streamable
{
	public fr.esrf.Tango.DevPipeDataElt[] value;

	public DevVarPipeDataEltArrayHolder ()
	{
	}
	public DevVarPipeDataEltArrayHolder (final fr.esrf.Tango.DevPipeDataElt[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DevVarPipeDataEltArrayHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DevVarPipeDataEltArrayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DevVarPipeDataEltArrayHelper.write (out,value);
	}
}
