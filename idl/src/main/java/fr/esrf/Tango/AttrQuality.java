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
 *	Generated from IDL definition of enum "AttrQuality"
 *	@author JacORB IDL compiler 
 */

public final class AttrQuality
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _ATTR_VALID = 0;
	public static final AttrQuality ATTR_VALID = new AttrQuality(_ATTR_VALID);
	public static final int _ATTR_INVALID = 1;
	public static final AttrQuality ATTR_INVALID = new AttrQuality(_ATTR_INVALID);
	public static final int _ATTR_ALARM = 2;
	public static final AttrQuality ATTR_ALARM = new AttrQuality(_ATTR_ALARM);
	public static final int _ATTR_CHANGING = 3;
	public static final AttrQuality ATTR_CHANGING = new AttrQuality(_ATTR_CHANGING);
	public static final int _ATTR_WARNING = 4;
	public static final AttrQuality ATTR_WARNING = new AttrQuality(_ATTR_WARNING);
	public int value()
	{
		return value;
	}
	public static AttrQuality from_int(int value)
	{
		switch (value) {
			case _ATTR_VALID: return ATTR_VALID;
			case _ATTR_INVALID: return ATTR_INVALID;
			case _ATTR_ALARM: return ATTR_ALARM;
			case _ATTR_CHANGING: return ATTR_CHANGING;
			case _ATTR_WARNING: return ATTR_WARNING;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AttrQuality(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
