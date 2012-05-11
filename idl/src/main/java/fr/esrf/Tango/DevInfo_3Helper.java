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
 *	Generated from IDL definition of struct "DevInfo_3"
 *	@author JacORB IDL compiler 
 */

public final class DevInfo_3Helper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevInfo_3Helper.id(),"DevInfo_3",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("dev_class", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_id", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_host", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("server_version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("doc_url", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("dev_type", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevInfo_3 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevInfo_3 extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevInfo_3:1.0";
	}
	public static fr.esrf.Tango.DevInfo_3 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevInfo_3 result = new fr.esrf.Tango.DevInfo_3();
		result.dev_class=in.read_string();
		result.server_id=in.read_string();
		result.server_host=in.read_string();
		result.server_version=in.read_long();
		result.doc_url=in.read_string();
		result.dev_type=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevInfo_3 s)
	{
		out.write_string(s.dev_class);
		out.write_string(s.server_id);
		out.write_string(s.server_host);
		out.write_long(s.server_version);
		out.write_string(s.doc_url);
		out.write_string(s.dev_type);
	}
}
