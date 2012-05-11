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
 *	Generated from IDL definition of struct "DevCmdInfo_2"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdInfo_2Helper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevCmdInfo_2Helper.id(),"DevCmdInfo_2",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("cmd_name", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("level", fr.esrf.Tango.DispLevelHelper.type(), null),new org.omg.CORBA.StructMember("cmd_tag", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("out_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("in_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("out_type_desc", org.omg.CORBA.ORB.init().create_string_tc(0), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevCmdInfo_2 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevCmdInfo_2 extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevCmdInfo_2:1.0";
	}
	public static fr.esrf.Tango.DevCmdInfo_2 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevCmdInfo_2 result = new fr.esrf.Tango.DevCmdInfo_2();
		result.cmd_name=in.read_string();
		result.level=fr.esrf.Tango.DispLevelHelper.read(in);
		result.cmd_tag=in.read_long();
		result.in_type=in.read_long();
		result.out_type=in.read_long();
		result.in_type_desc=in.read_string();
		result.out_type_desc=in.read_string();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevCmdInfo_2 s)
	{
		out.write_string(s.cmd_name);
		fr.esrf.Tango.DispLevelHelper.write(out,s.level);
		out.write_long(s.cmd_tag);
		out.write_long(s.in_type);
		out.write_long(s.out_type);
		out.write_string(s.in_type_desc);
		out.write_string(s.out_type_desc);
	}
}
