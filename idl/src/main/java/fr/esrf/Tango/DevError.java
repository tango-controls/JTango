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
 *	Generated from IDL definition of struct "DevError"
 *	@author JacORB IDL compiler 
 */

public final class DevError
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevError(){}
	public java.lang.String reason = "";
	public fr.esrf.Tango.ErrSeverity severity;
	public java.lang.String desc = "";
	public java.lang.String origin = "";
	public DevError(java.lang.String reason, fr.esrf.Tango.ErrSeverity severity, java.lang.String desc, java.lang.String origin)
	{
		this.reason = reason;
		this.severity = severity;
		this.desc = desc;
		this.origin = origin;
	}
}
