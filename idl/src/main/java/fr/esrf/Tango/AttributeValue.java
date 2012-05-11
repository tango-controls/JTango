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
 *	Generated from IDL definition of struct "AttributeValue"
 *	@author JacORB IDL compiler 
 */

public final class AttributeValue
	implements org.omg.CORBA.portable.IDLEntity
{
	public AttributeValue(){}
	public org.omg.CORBA.Any value;
	public fr.esrf.Tango.AttrQuality quality;
	public fr.esrf.Tango.TimeVal time;
	public java.lang.String name = "";
	public int dim_x;
	public int dim_y;
	public AttributeValue(org.omg.CORBA.Any value, fr.esrf.Tango.AttrQuality quality, fr.esrf.Tango.TimeVal time, java.lang.String name, int dim_x, int dim_y)
	{
		this.value = value;
		this.quality = quality;
		this.time = time;
		this.name = name;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
	}
}
