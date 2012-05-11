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
 *	Generated from IDL definition of struct "DevCmdHistory_4"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdHistory_4
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevCmdHistory_4(){}
	public fr.esrf.Tango.TimeVal[] dates;
	public org.omg.CORBA.Any value;
	public fr.esrf.Tango.AttributeDim[] dims;
	public fr.esrf.Tango.EltInArray[] dims_array;
	public fr.esrf.Tango.DevError[][] errors;
	public fr.esrf.Tango.EltInArray[] errors_array;
	public int cmd_type;
	public DevCmdHistory_4(fr.esrf.Tango.TimeVal[] dates, org.omg.CORBA.Any value, fr.esrf.Tango.AttributeDim[] dims, fr.esrf.Tango.EltInArray[] dims_array, fr.esrf.Tango.DevError[][] errors, fr.esrf.Tango.EltInArray[] errors_array, int cmd_type)
	{
		this.dates = dates;
		this.value = value;
		this.dims = dims;
		this.dims_array = dims_array;
		this.errors = errors;
		this.errors_array = errors_array;
		this.cmd_type = cmd_type;
	}
}
