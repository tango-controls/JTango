//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Revision$
//
// $Log$
// Revision 1.7  2009/01/16 12:50:02  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:31  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
// Revision 2.0  2003/01/09 14:00:37  verdier
// jacORB is now the ORB used.
//
// Revision 1.8  2002/06/26 09:02:17  verdier
// tested with atkpanel on a TACO device
//
// Revision 1.7  2002/04/09 12:21:51  verdier
// IDL 2 implemented.
//
// Revision 1.6  2002/01/09 12:18:15  verdier
// TACO signals can be read as TANGO attribute.
//
// Revision 1.5  2001/12/10 14:19:42  verdier
// TACO JNI Interface added.
// URL syntax used for connection.
// Connection on device without database added.
//
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;


/**
 *	Class Description:
 *	This class manage database connection for Tango server.
 *
 * @author  verdier
 * @version  $Revision$
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
