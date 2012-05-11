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
 *	Generated from IDL definition of struct "AttributeConfig"
 *	@author JacORB IDL compiler 
 */

public final class AttributeConfig
	implements org.omg.CORBA.portable.IDLEntity
{
	public AttributeConfig(){}
	public java.lang.String name = "";
	public fr.esrf.Tango.AttrWriteType writable;
	public fr.esrf.Tango.AttrDataFormat data_format;
	public int data_type;
	public int max_dim_x;
	public int max_dim_y;
	public java.lang.String description = "";
	public java.lang.String label = "";
	public java.lang.String unit = "";
	public java.lang.String standard_unit = "";
	public java.lang.String display_unit = "";
	public java.lang.String format = "";
	public java.lang.String min_value = "";
	public java.lang.String max_value = "";
	public java.lang.String min_alarm = "";
	public java.lang.String max_alarm = "";
	public java.lang.String writable_attr_name = "";
	public java.lang.String[] extensions;
	public AttributeConfig(java.lang.String name, fr.esrf.Tango.AttrWriteType writable, fr.esrf.Tango.AttrDataFormat data_format, int data_type, int max_dim_x, int max_dim_y, java.lang.String description, java.lang.String label, java.lang.String unit, java.lang.String standard_unit, java.lang.String display_unit, java.lang.String format, java.lang.String min_value, java.lang.String max_value, java.lang.String min_alarm, java.lang.String max_alarm, java.lang.String writable_attr_name, java.lang.String[] extensions)
	{
		this.name = name;
		this.writable = writable;
		this.data_format = data_format;
		this.data_type = data_type;
		this.max_dim_x = max_dim_x;
		this.max_dim_y = max_dim_y;
		this.description = description;
		this.label = label;
		this.unit = unit;
		this.standard_unit = standard_unit;
		this.display_unit = display_unit;
		this.format = format;
		this.min_value = min_value;
		this.max_value = max_value;
		this.min_alarm = min_alarm;
		this.max_alarm = max_alarm;
		this.writable_attr_name = writable_attr_name;
		this.extensions = extensions;
	}
}
