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
 *	Generated from IDL definition of union "ClntIdent"
 *	@author JacORB IDL compiler 
 */

public final class ClntIdent
	implements org.omg.CORBA.portable.IDLEntity
{
	private fr.esrf.Tango.LockerLanguage discriminator;
	private int cpp_clnt;
	private fr.esrf.Tango.JavaClntIdent java_clnt;

	public ClntIdent ()
	{
	}

	public fr.esrf.Tango.LockerLanguage discriminator ()
	{
		return discriminator;
	}

	public int cpp_clnt ()
	{
		if (discriminator != fr.esrf.Tango.LockerLanguage.CPP)
			throw new org.omg.CORBA.BAD_OPERATION();
		return cpp_clnt;
	}

	public void cpp_clnt (int _x)
	{
		discriminator = fr.esrf.Tango.LockerLanguage.CPP;
		cpp_clnt = _x;
	}

	public fr.esrf.Tango.JavaClntIdent java_clnt ()
	{
		if (discriminator != fr.esrf.Tango.LockerLanguage.JAVA)
			throw new org.omg.CORBA.BAD_OPERATION();
		return java_clnt;
	}

	public void java_clnt (fr.esrf.Tango.JavaClntIdent _x)
	{
		discriminator = fr.esrf.Tango.LockerLanguage.JAVA;
		java_clnt = _x;
	}

}
