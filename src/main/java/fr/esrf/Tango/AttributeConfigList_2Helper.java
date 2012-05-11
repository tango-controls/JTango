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
 *	Generated from IDL definition of alias "AttributeConfigList_2"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfigList_2Helper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.AttributeConfig_2[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.AttributeConfig_2[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.AttributeConfigList_2Helper.id(), "AttributeConfigList_2",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.AttributeConfig_2Helper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/AttributeConfigList_2:1.0";
	}
	public static fr.esrf.Tango.AttributeConfig_2[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.AttributeConfig_2[] _result;
		int _l_result18 = _in.read_long();
		_result = new fr.esrf.Tango.AttributeConfig_2[_l_result18];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.AttributeConfig_2Helper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.AttributeConfig_2[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.AttributeConfig_2Helper.write(_out,_s[i]);
		}

	}
}
