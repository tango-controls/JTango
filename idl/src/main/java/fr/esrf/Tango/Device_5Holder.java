package fr.esrf.Tango;

/**
 * Generated from IDL interface "Device_5".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class Device_5Holder	implements org.omg.CORBA.portable.Streamable{
	 public Device_5 value;
	public Device_5Holder()
	{
	}
	public Device_5Holder (final Device_5 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return Device_5Helper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = Device_5Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		Device_5Helper.write (_out,value);
	}
}
