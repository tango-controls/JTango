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
 *	This class is an object containing the exported device information.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
 */


public class DbDevExportInfo implements java.io.Serializable
{
	/**
	 *	The devivce name.
	 */
	public String	name;
	/**
	 *	ior connection as String.
	 */
	public String	ior;
	/**
	 *	Host name where device will be exported.
	 */
	public String	host;
	/**
	 *	TANGO protocol version number.
	 */
	public String	version;

	//===============================================
	/**
	 *	Default constructor.
	 */
	//===============================================
	@SuppressWarnings({"UnusedDeclaration"})
    public DbDevExportInfo()
	{
	}
	//===============================================
	/**
	 *	Complete constructor (pid does not exit in java).
     *
     * @param name device name
     * @param ior   IOR found in database
     * @param host  host wher running (or have been running)
     * @param version IDL revision
     */
	//===============================================
	public DbDevExportInfo(String name, String ior,
										String host, String version)
	{
		this.name     = name;
		this.ior      = ior;
		this.host     = host;
		this.version  = version;
	}
	//===============================================
	/**
	 *	Serialise object data to a string array data.
     * @return  a description as a String array.
     */
	//===============================================
	public String[] toStringArray()
	{
		String[]	argout;
		argout = new String[5];
		int	i=0;
		argout[i++] = name;
		argout[i++] = ior;
		argout[i++] = host;
		argout[i++] = version;
		argout[i] = "0";
		return argout;
	}
}
