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
 *	Generated from IDL definition of struct "DevInfo"
 *	@author JacORB IDL compiler 
 */

public final class DevInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevInfo(){}
	public java.lang.String dev_class = "";
	public java.lang.String server_id = "";
	public java.lang.String server_host = "";
	public int server_version;
	public java.lang.String doc_url = "";
	public DevInfo(java.lang.String dev_class, java.lang.String server_id, java.lang.String server_host, int server_version, java.lang.String doc_url)
	{
		this.dev_class = dev_class;
		this.server_id = server_id;
		this.server_host = server_host;
		this.server_version = server_version;
		this.doc_url = doc_url;
	}
}
