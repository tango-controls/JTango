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
 *	Generated from IDL definition of struct "DevAttrHistory_3"
 *	@author JacORB IDL compiler 
 */

public final class DevAttrHistory_3Helper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevAttrHistory_3Helper.id(),"DevAttrHistory_3",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("attr_failed", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)), null),new org.omg.CORBA.StructMember("value", fr.esrf.Tango.AttributeValue_3Helper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.DevAttrHistory_3 s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.DevAttrHistory_3 extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/DevAttrHistory_3:1.0";
	}
	public static fr.esrf.Tango.DevAttrHistory_3 read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.DevAttrHistory_3 result = new fr.esrf.Tango.DevAttrHistory_3();
		result.attr_failed=in.read_boolean();
		result.value=fr.esrf.Tango.AttributeValue_3Helper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.DevAttrHistory_3 s)
	{
		out.write_boolean(s.attr_failed);
		fr.esrf.Tango.AttributeValue_3Helper.write(out,s.value);
	}
}
