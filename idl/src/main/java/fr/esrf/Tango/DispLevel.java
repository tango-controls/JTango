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
 *	Generated from IDL definition of enum "DispLevel"
 *	@author JacORB IDL compiler 
 */

public final class DispLevel
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _OPERATOR = 0;
	public static final DispLevel OPERATOR = new DispLevel(_OPERATOR);
	public static final int _EXPERT = 1;
	public static final DispLevel EXPERT = new DispLevel(_EXPERT);
	public int value()
	{
		return value;
	}
	public static DispLevel from_int(int value)
	{
		switch (value) {
			case _OPERATOR: return OPERATOR;
			case _EXPERT: return EXPERT;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected DispLevel(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
