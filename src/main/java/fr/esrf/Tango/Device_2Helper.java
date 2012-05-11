/**
 * Copyright (C) :     2004
 *
 *     European Synchrotron Radiation Facility
 *     BP 220, Grenoble 38043
 *     FRANCE
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.esrf.Tango;


/**
 *	Generated from IDL interface "Device_2"
 *	@author JacORB IDL compiler V 2.2, 7-May-2004
 */

public final class Device_2Helper
{
	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.Device_2 s)
	{
			any.insert_Object(s);
	}
	public static fr.esrf.Tango.Device_2 extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:Tango/Device_2:1.0", "Device_2");
	}
	public static String id()
	{
		return "IDL:Tango/Device_2:1.0";
	}
	public static Device_2 read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final fr.esrf.Tango.Device_2 s)
	{
		_out.write_Object(s);
	}
	public static fr.esrf.Tango.Device_2 narrow(final java.lang.Object obj)
	{
		if (obj instanceof fr.esrf.Tango.Device_2)
		{
			return (fr.esrf.Tango.Device_2)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static fr.esrf.Tango.Device_2 narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (fr.esrf.Tango.Device_2)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:Tango/Device_2:1.0"))
			{
				fr.esrf.Tango._Device_2Stub stub;
				stub = new fr.esrf.Tango._Device_2Stub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static fr.esrf.Tango.Device_2 unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (fr.esrf.Tango.Device_2)obj;
		}
		catch (ClassCastException c)
		{
				fr.esrf.Tango._Device_2Stub stub;
				stub = new fr.esrf.Tango._Device_2Stub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
