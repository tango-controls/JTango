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
 *	Generated from IDL definition of enum "AttrWriteType"
 *	@author JacORB IDL compiler 
 */

public final class AttrWriteType
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _READ = 0;
	public static final AttrWriteType READ = new AttrWriteType(_READ);
	public static final int _READ_WITH_WRITE = 1;
	public static final AttrWriteType READ_WITH_WRITE = new AttrWriteType(_READ_WITH_WRITE);
	public static final int _WRITE = 2;
	public static final AttrWriteType WRITE = new AttrWriteType(_WRITE);
	public static final int _READ_WRITE = 3;
	public static final AttrWriteType READ_WRITE = new AttrWriteType(_READ_WRITE);
	public int value()
	{
		return value;
	}
	public static AttrWriteType from_int(int value)
	{
		switch (value) {
			case _READ: return READ;
			case _READ_WITH_WRITE: return READ_WITH_WRITE;
			case _WRITE: return WRITE;
			case _READ_WRITE: return READ_WRITE;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AttrWriteType(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
