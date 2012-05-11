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
 *	Generated from IDL definition of alias "DevVarEncodedArray"
 *	@author JacORB IDL compiler 
 */

public final class DevVarEncodedArrayHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, fr.esrf.Tango.DevEncoded[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static fr.esrf.Tango.DevEncoded[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarEncodedArrayHelper.id(), "DevVarEncodedArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, fr.esrf.Tango.DevEncodedHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:Tango/DevVarEncodedArray:1.0";
	}
	public static fr.esrf.Tango.DevEncoded[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		fr.esrf.Tango.DevEncoded[] _result;
		int _l_result11 = _in.read_long();
		_result = new fr.esrf.Tango.DevEncoded[_l_result11];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=fr.esrf.Tango.DevEncodedHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, fr.esrf.Tango.DevEncoded[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			fr.esrf.Tango.DevEncodedHelper.write(_out,_s[i]);
		}

	}
}
