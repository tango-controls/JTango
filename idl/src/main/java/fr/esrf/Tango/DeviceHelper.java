package fr.esrf.Tango;


/**
 * Generated from IDL interface "Device".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class DeviceHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DeviceHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:Tango/Device:1.0", "Device");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.Device s)
	{
			any.insert_Object(s);
	}
	public static fr.esrf.Tango.Device extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:Tango/Device:1.0";
	}
	public static Device read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esrf.Tango._DeviceStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esrf.Tango.Device s)
	{
		_out.write_Object(s);
	}
	public static fr.esrf.Tango.Device narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esrf.Tango.Device)
		{
			return (fr.esrf.Tango.Device)obj;
		}
		else if (obj._is_a("IDL:Tango/Device:1.0"))
		{
			fr.esrf.Tango._DeviceStub stub;
			stub = new fr.esrf.Tango._DeviceStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esrf.Tango.Device unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esrf.Tango.Device)
		{
			return (fr.esrf.Tango.Device)obj;
		}
		else
		{
			fr.esrf.Tango._DeviceStub stub;
			stub = new fr.esrf.Tango._DeviceStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
