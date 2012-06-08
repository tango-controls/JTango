package fr.esrf.Tango;

/**
 *	Generated from IDL interface "Device_4"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
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
