package fr.esrf.Tango;


/**
 * Generated from IDL interface "Device_5".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class Device_5Helper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(Device_5Helper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:Tango/Device_5:1.0", "Device_5");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.Device_5 s)
	{
			any.insert_Object(s);
	}
	public static fr.esrf.Tango.Device_5 extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:Tango/Device_5:1.0";
	}
	public static Device_5 read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(fr.esrf.Tango._Device_5Stub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esrf.Tango.Device_5 s)
	{
		_out.write_Object(s);
	}
	public static fr.esrf.Tango.Device_5 narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esrf.Tango.Device_5)
		{
			return (fr.esrf.Tango.Device_5)obj;
		}
		else if (obj._is_a("IDL:Tango/Device_5:1.0"))
		{
			fr.esrf.Tango._Device_5Stub stub;
			stub = new fr.esrf.Tango._Device_5Stub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static fr.esrf.Tango.Device_5 unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof fr.esrf.Tango.Device_5)
		{
			return (fr.esrf.Tango.Device_5)obj;
		}
		else
		{
			fr.esrf.Tango._Device_5Stub stub;
			stub = new fr.esrf.Tango._Device_5Stub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
