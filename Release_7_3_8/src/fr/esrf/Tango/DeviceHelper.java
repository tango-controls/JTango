package fr.esrf.Tango;


/**
 *	Generated from IDL interface "Device"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */

public final class DeviceHelper
{
	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.Device s)
	{
			any.insert_Object(s);
	}
	public static fr.esrf.Tango.Device extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:Tango/Device:1.0", "Device");
	}
	public static String id()
	{
		return "IDL:Tango/Device:1.0";
	}
	public static Device read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esrf.Tango.Device s)
	{
		_out.write_Object(s);
	}
	public static fr.esrf.Tango.Device narrow(final java.lang.Object obj)
	{
		if (obj instanceof fr.esrf.Tango.Device)
		{
			return (fr.esrf.Tango.Device)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static fr.esrf.Tango.Device narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (fr.esrf.Tango.Device)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:Tango/Device:1.0"))
			{
				fr.esrf.Tango._DeviceStub stub;
				stub = new fr.esrf.Tango._DeviceStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static fr.esrf.Tango.Device unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (fr.esrf.Tango.Device)obj;
		}
		catch (ClassCastException c)
		{
				fr.esrf.Tango._DeviceStub stub;
				stub = new fr.esrf.Tango._DeviceStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
