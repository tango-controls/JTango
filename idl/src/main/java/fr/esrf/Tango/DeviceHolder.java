package fr.esrf.Tango;

/**
 * Generated from IDL interface "Device".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class DeviceHolder	implements org.omg.CORBA.portable.Streamable{
	 public Device value;
	public DeviceHolder()
	{
	}
	public DeviceHolder (final Device initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DeviceHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DeviceHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DeviceHelper.write (_out,value);
	}
}
