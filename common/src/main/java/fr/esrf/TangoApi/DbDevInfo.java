//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;




/** 
 *	Class Description:
 *	Device information object.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
 */


public class DbDevInfo implements java.io.Serializable
{
	/**
	 *	The device name.
	 */
	public String	name;
	/**
	 *	The class name.
	 */
	public String	_class;
	/**
	 *	The server name.
	 */
	public String	server;
	/**
	 *	The device type.
	 */
	public int		type = 0;

	//===============================================
	/**
	 *	Default constructor.
	 */
	//===============================================
	public DbDevInfo()
	{
	}
	//===============================================
	/**
	 *	Complete information constructor.
	 */
	//===============================================
	public DbDevInfo(String name, String _class, String server)
	{
		this.name   = name;
		this._class = _class;
		this.server = server;
	}
	//===============================================
	/**
	 *	Complete information constructor.
	 */
	//===============================================
	public DbDevInfo(String name, String _class, String server, int type)
	{
		this.name   = name;
		this._class = _class;
		this.server = server;
		this.type   = type;
	}
	//===============================================
	/**
	 *	Serialize object filed as string array.
	 */
	//===============================================
	public String[] toStringArray()
	{
		String[]	argout;
		argout = new String[3];
		argout[0] = server;
		argout[1] = name;
		argout[2] = _class;
		//argout[3] = "Undefined";
		return argout;
	}
}
