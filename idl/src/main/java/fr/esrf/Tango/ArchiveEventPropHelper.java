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
 *	Generated from IDL definition of struct "ArchiveEventProp"
 *	@author JacORB IDL compiler 
 */

public final class ArchiveEventPropHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.ArchiveEventPropHelper.id(),"ArchiveEventProp",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("rel_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("abs_change", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("period", org.omg.CORBA.ORB.init().create_string_tc(0), null),new org.omg.CORBA.StructMember("extensions", fr.esrf.Tango.DevVarStringArrayHelper.type(), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.ArchiveEventProp s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.ArchiveEventProp extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/ArchiveEventProp:1.0";
	}
	public static fr.esrf.Tango.ArchiveEventProp read (final org.omg.CORBA.portable.InputStream in)
	{
		fr.esrf.Tango.ArchiveEventProp result = new fr.esrf.Tango.ArchiveEventProp();
		result.rel_change=in.read_string();
		result.abs_change=in.read_string();
		result.period=in.read_string();
		result.extensions = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final fr.esrf.Tango.ArchiveEventProp s)
	{
		out.write_string(s.rel_change);
		out.write_string(s.abs_change);
		out.write_string(s.period);
		fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.extensions);
	}
}
