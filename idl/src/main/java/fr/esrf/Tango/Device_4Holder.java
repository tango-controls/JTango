package fr.esrf.Tango;

/**
 * Generated from IDL interface "Device_4".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class Device_4Holder	implements org.omg.CORBA.portable.Streamable{
	 public Device_4 value;
	public Device_4Holder()
	{
	}
	public Device_4Holder (final Device_4 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return Device_4Helper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = Device_4Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		Device_4Helper.write (_out,value);
	}
}
