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
 *	Generated from IDL definition of enum "DevSource"
 *	@author JacORB IDL compiler 
 */

public final class DevSource
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _DEV = 0;
	public static final DevSource DEV = new DevSource(_DEV);
	public static final int _CACHE = 1;
	public static final DevSource CACHE = new DevSource(_CACHE);
	public static final int _CACHE_DEV = 2;
	public static final DevSource CACHE_DEV = new DevSource(_CACHE_DEV);
	public int value()
	{
		return value;
	}
	public static DevSource from_int(int value)
	{
		switch (value) {
			case _DEV: return DEV;
			case _CACHE: return CACHE;
			case _CACHE_DEV: return CACHE_DEV;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected DevSource(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
