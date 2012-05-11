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
 *	Generated from IDL definition of enum "LockerLanguage"
 *	@author JacORB IDL compiler 
 */

public final class LockerLanguage
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _CPP = 0;
	public static final LockerLanguage CPP = new LockerLanguage(_CPP);
	public static final int _JAVA = 1;
	public static final LockerLanguage JAVA = new LockerLanguage(_JAVA);
	public int value()
	{
		return value;
	}
	public static LockerLanguage from_int(int value)
	{
		switch (value) {
			case _CPP: return CPP;
			case _JAVA: return JAVA;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected LockerLanguage(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
