package fr.esrf.Tango;


/**
 * Generated from IDL interface "Device_3".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class Device_3Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(Device_3Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:Tango/Device_3:1.0", "Device_3");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.Device_3 s)
	{
			any.insert_Object(s);
	}
	public static fr.esrf.Tango.Device_3 extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:Tango/Device_3:1.0";
	}
	public static Device_3 read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esrf.Tango._Device_3Stub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esrf.Tango.Device_3 s)
	{
		_out.write_Object(s);
	}
	public static fr.esrf.Tango.Device_3 narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esrf.Tango.Device_3)
		{
			return (fr.esrf.Tango.Device_3)obj;
		}
		else if (obj._is_a("IDL:Tango/Device_3:1.0"))
		{
			fr.esrf.Tango._Device_3Stub stub;
			stub = new fr.esrf.Tango._Device_3Stub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esrf.Tango.Device_3 unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esrf.Tango.Device_3)
		{
			return (fr.esrf.Tango.Device_3)obj;
		}
		else
		{
			fr.esrf.Tango._Device_3Stub stub;
			stub = new fr.esrf.Tango._Device_3Stub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
