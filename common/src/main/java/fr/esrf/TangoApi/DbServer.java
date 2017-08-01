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

import fr.esrf.Tango.DevFailed;


/**
 *	Class Description:
 *	This class manage database connection for Tango server.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
 */


public class DbServer implements java.io.Serializable
{
	/**
	 *	Database object used for TANGO databse access.
	 */
	private Database	dbase;

	/**
	 *	Device name used to access database if device not exported.
	 */
	private String servname;

	//===================================================================
	/**
	 *	DbServer constructor.
	 *	It will make a connection to the TANGO database.
	 *
	 *	@param	servname		Name of the class oject.
	 */
	//===================================================================
	public DbServer(String servname) throws DevFailed
	{
		//	Access the database
		//----------------------------
		dbase = ApiUtil.get_db_obj();
		this.servname = servname;
	}
	//===================================================================
	/**
	 *	DbServer constructor.
	 *	It will make a connection to the TANGO database.
	 *
	 *	@param	servname		Name of the class oject.
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public DbServer(String servname, String host, String port) throws DevFailed
	{
		//	Access the database 
		//--------------------------
		dbase = ApiUtil.get_db_obj(host, port);
		this.servname = servname;
	}

	//==========================================================================
	/**
	 *	Query the database for server information.
	 *	@return	The information found for this server
	 *				in a DBServInfo object.
	 */
	//==========================================================================
	public DbServInfo get_info() throws DevFailed
	{
		return dbase.get_server_info(servname);
	}

	//==========================================================================
	/**
	 *	Add/update server information in databse.
	 *	@param info	Server information for this server
	 *					in a DbServinfo object.
	 */
	//==========================================================================
	public void put_info(DbServInfo info) throws DevFailed
	{
		dbase.put_server_info(info);
	}

	//==========================================================================
	/**
	 *	Delete for server information in the database.
	 */
	//==========================================================================
	public void delete_info() throws DevFailed
	{
		dbase.delete_server_info(servname);
	}

	//==========================================================================
	/**
	 *	Query the database for server classes.
	 *	@return	The  classes implemented for this server.
	 */
	//==========================================================================
	public String[] get_class_list() throws DevFailed
	{
		return dbase.get_server_class_list(servname);
	}

	//==========================================================================
	/**
	 *	Query the database for server devices and classes.
	 *	@return	The devices and classes (e.g. "id11/motor/1", "StepperMotor",
	 *			"id11/motor/2", "StepperMotor",....)
	 */
	//==========================================================================
	public String[] get_device_class_list() throws DevFailed
	{
		return dbase.get_device_class_list(servname);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of devices served by the specified server
	 *	and of the specified class.
	 *
	 *	@param classname	The class name
	 *	@return the device names are stored in an array of strings.
	 */
	//==========================================================================
	public String[] get_device_name(String classname)
				throws DevFailed
	{
		return dbase.get_device_name(servname, classname);
	}
	//===========================================================
	/**
	 *	return the server name.
	 */
	//===========================================================
	public String name()
	{
		return servname;
	}
}
