package fr.esrf.Tango;

/**
 * Generated from IDL interface "Device_3".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class Device_3Holder	implements org.omg.CORBA.portable.Streamable{
	 public Device_3 value;
	public Device_3Holder()
	{
	}
	public Device_3Holder (final Device_3 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return Device_3Helper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = Device_3Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		Device_3Helper.write (_out,value);
	}
}
