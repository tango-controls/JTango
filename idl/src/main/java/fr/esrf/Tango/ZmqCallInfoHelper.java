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
 *	Generated from IDL definition of struct "ZmqCallInfo"
 *	@author JacORB IDL compiler 
 */

public final class ZmqCallInfoHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.ZmqCallInfoHelper.id(),"ZmqCallInfo",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("method_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("oid", fr.esrf.Tango.DevVarCharArrayHelper.type(), null),new org.omg.CORBA.StructMember("call_is_except", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.ZmqCallInfo s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.ZmqCallInfo extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/ZmqCallInfo:1.0";
	}
	public static fr.esrf.Tango.ZmqCallInfo read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.ZmqCallInfo result = new fr.esrf.Tango.ZmqCallInfo();
		result.version=in.read_long();
		result.method_name=in.read_string();
		result.oid = fr.esrf.Tango.DevVarCharArrayHelper.read(in);
		result.call_is_except=in.read_boolean();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.ZmqCallInfo s)
	{
		out.write_long(s.version);
		out.write_string(s.method_name);
		fr.esrf.Tango.DevVarCharArrayHelper.write(out,s.oid);
		out.write_boolean(s.call_is_except);
	}
}
