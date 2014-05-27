package fr.esrf.Tango;

/**
 * Generated from IDL interface "Device_2".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class Device_2Holder	implements org.omg.CORBA.portable.Streamable{
	 public Device_2 value;
	public Device_2Holder()
	{
	}
	public Device_2Holder (final Device_2 initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return Device_2Helper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = Device_2Helper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		Device_2Helper.write (_out,value);
	}
}
