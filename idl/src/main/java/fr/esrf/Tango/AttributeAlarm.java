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
 *	Generated from IDL definition of struct "AttributeAlarm"
 *	@author JacORB IDL compiler 
 */

public final class AttributeAlarm
	implements org.omg.CORBA.portable.IDLEntity
{
	public AttributeAlarm(){}
	public java.lang.String min_alarm = "";
	public java.lang.String max_alarm = "";
	public java.lang.String min_warning = "";
	public java.lang.String max_warning = "";
	public java.lang.String delta_t = "";
	public java.lang.String delta_val = "";
	public java.lang.String[] extensions;
	public AttributeAlarm(java.lang.String min_alarm, java.lang.String max_alarm, java.lang.String min_warning, java.lang.String max_warning, java.lang.String delta_t, java.lang.String delta_val, java.lang.String[] extensions)
	{
		this.min_alarm = min_alarm;
		this.max_alarm = max_alarm;
		this.min_warning = min_warning;
		this.max_warning = max_warning;
		this.delta_t = delta_t;
		this.delta_val = delta_val;
		this.extensions = extensions;
	}
}
