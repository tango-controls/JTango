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
 *	Generated from IDL definition of struct "NamedDevError"
 *	@author JacORB IDL compiler 
 */

public final class NamedDevError
	implements org.omg.CORBA.portable.IDLEntity
{
	public NamedDevError(){}
	public java.lang.String name = "";
	public int index_in_call;
	public fr.esrf.Tango.DevError[] err_list;
	public NamedDevError(java.lang.String name, int index_in_call, fr.esrf.Tango.DevError[] err_list)
	{
		this.name = name;
		this.index_in_call = index_in_call;
		this.err_list = err_list;
	}
}
