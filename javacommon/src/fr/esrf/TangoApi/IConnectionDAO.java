//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	source code 
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
// Revision 1.6  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.5  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;

public interface IConnectionDAO {

	// ------------------------------------------------------------------
	/**
	 *	Connection constructor. It makes a connection on database server.
	 *
	 */
	//===================================================================
	public abstract void init(Connection connection) throws DevFailed;

	//===================================================================
	/**
	 *	Connection constructor. It makes a connection on database server.
	 *
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public abstract void init(Connection connection, String host, String port) throws DevFailed;
	//===================================================================
	/**
	 *	Connection constructor. It makes a connection on database server.
	 *
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 *	@param	auto_reconnect	do not reconnect if false.
	 */
	//===================================================================
	public abstract void init(Connection connection, String host, String port, boolean auto_reconnect)	throws DevFailed;

	//===================================================================
	/**
	 *	Connection constructor. It imports the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 */
	//===================================================================
	public abstract void init(Connection connection, String devname) throws DevFailed;
	//===================================================================
	/**
	 *	Connection constructor. It imports the device.
	 *
	 *	@param	info exported info of the device to be imported.
	 */
	//===================================================================
	public void init(Connection connection, DbDevImportInfo info) throws DevFailed ;
	//===================================================================
	/**
	 *	Connection constructor. It imports the device. And set check_access.
	 *
	 *	@param	devname			name of the device to be imported.
	 *	@param	check_access	set check_access value
	 */
	//===================================================================
	public abstract void init(Connection connection, String devname, boolean check_access) throws DevFailed;
	//===================================================================
	/**
	 *	Connection constructor. It imports the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	param	String parameter to import device.
	 *	@param	src		Source to import device (ior, dbase...)
	 */
	//===================================================================
	public abstract void init(Connection connection, String devname, String param, int src) throws DevFailed;
	//===================================================================
	/**
	 *	Connection constructor. It imports the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public abstract void init(Connection connection, String devname, String host, String port) throws DevFailed;
	
	
	//===================================================================
	//===================================================================
	public abstract Device get_device(Connection connection);

	//===================================================================
	//===================================================================	
	public abstract void build_connection(Connection connection) throws DevFailed;	
	
	//===================================================================
	//===================================================================
	public abstract String get_ior(Connection connection) throws DevFailed;

	//===================================================================
	/**
	 *	Really build the device connection.
	 */
	//===================================================================
	public abstract void dev_import(Connection connection) throws DevFailed;	
	
	//===================================================================
	/**
	 *	Change the timeout value for a device call.
	 *
	 *	@param	millis		New value of the timeout in milliseconds.
	 *	@throws	DevFailed	if orb.create_policy throws an
	 *						org.omg.CORBA.PolicyError exception.
	 */
	//===================================================================
	public abstract void set_timeout_millis(Connection connection, int millis) throws DevFailed;

	//===================================================================
	/**
	 *	return the timeout value for a device call.
	 *
	 *	@return the value of the timeout in milliseconds.
	 *	@deprecated use get_timeout_millis() instead
	 */
	//===================================================================
	public abstract int get_timeout(Connection connection);
	
	//===================================================================
	/**
	 *	return the timeout value for a device call.
	 *
	 *	@return the value of the timeout in milliseconds.
	 */
	//===================================================================
	public abstract int get_timeout_millis(Connection connection) throws DevFailed;

	//===========================================================
	/**
	 *	Send a command to a device server.
	 *
	 *	@param command	Command name to send to the device.
	 *	@param	argin	input command argument.
	 *	@return the output argument of the command.
	 *	@throws DevFailed
	 */
	//===========================================================
	public abstract DeviceData command_inout(Connection connection, String command, DeviceData argin)
			throws DevFailed;

	//===========================================================
	/**
	 *	Send a command to a device server.
	 *
	 *	@param command 	Command name.
	 *	@return the output argument of the command.
	 *	@throws DevFailed
	 */
	//===========================================================
	public abstract DeviceData command_inout(Connection connection, String command) throws DevFailed;

	//===========================================================
	/**
	 *	Execute a ping command to a device server.
	 *
	 *	@return the elapsed time for ping command in microseconds.
	 */
	//===========================================================
	public abstract long ping(Connection connection) throws DevFailed;

	//===========================================================
	/**
	 *	Execute a info command to a device server.
	 */
	//===========================================================
	public abstract String[] black_box(Connection connection, int length) throws DevFailed;

	//===========================================================
	/**
	 *	Execute a info command to a device server.
	 */
	//===========================================================
	public abstract DevInfo_3 info_3(Connection connection) throws DevFailed;

	//===========================================================
	/**
	 *	Execute a info command to a device server.
	 */
	//===========================================================
	public abstract DevInfo info(Connection connection) throws DevFailed;

	//===========================================================
	/**
	 *	Execute a command_list_query command to a device server.
	 */
	//===========================================================
	public abstract CommandInfo[] command_list_query(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	Returns the IDL version supported
	 *
	 *	@return  the IDL version supported .
	 */
	//==========================================================================
	public abstract int get_idl_version(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	returns the device data source
	 *
	 *	@return  data source (CACHE_DEV, CACHE or DEV).
	 */
	//==========================================================================
	public abstract DevSource get_source(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	Set the device data source
	 *
	 *	@param new_src	new data source (CACHE_DEV, CACHE or DEV).
	 */
	//==========================================================================
	public abstract void set_source(Connection connection, DevSource new_src) throws DevFailed;

	//==========================================================================
	/**
	 *	return the device connected name.
	 */
	//==========================================================================
	public abstract String get_name(Connection connection);

	//==========================================================================
	/**
	 *	return the device connected host name.
	 */
	//==========================================================================
	public abstract String get_host_name(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	return the device connected class name.
	 */
	//==========================================================================
	public abstract String get_class_name(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	return the device connected server name.
	 */
	//==========================================================================
	public abstract String get_server_name(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	return the device connected dexcription.
	 */
	//==========================================================================
	public abstract String description(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	return the administartion device name.
	 */
	//==========================================================================
	public abstract String adm_name(Connection connection) throws DevFailed;
	
	//==========================================================================
	/**
	 *	return the name of connection (host:port)
	 */
	//==========================================================================
	public abstract String get_tango_host(Connection connection) throws DevFailed;

	//==========================================================================
	/**
	 *	return true if device is a taco device
	 */
	//==========================================================================
	public abstract boolean is_taco(Connection connection);
	
	//==========================================================================
	/**
	 *	if not a TACO command then throw a DevFailed Exception.
	 *
	 *	@param cmdname	command name to be put inside reason and origin fields.
	 */
	//==========================================================================
	public abstract void checkIfTaco(Connection connection, String cmdname) throws DevFailed;	
	
	//==========================================================================
	/**
	 *	if not a TACO command then throw a DevFailed Exception.
	 *
	 *	@param cmdname	command name to be put inside reason and origin fields.
	 */
	//==========================================================================
	public abstract void checkIfTango(Connection connection, String cmdname) throws DevFailed;	
	
	//==========================================================================
	/**
	 *	return the value of transparent_reconnection
	 */
	//==========================================================================
	public abstract boolean get_transparency_reconnection(Connection connection);

	//==========================================================================
	/**
	 *	set the value of transparent_reconnection
	 */
	//==========================================================================
	public abstract void set_transparency_reconnection(Connection connection, boolean val);

	//==========================================================================
	//==========================================================================
	public abstract int getAccessControl(Connection connection);

	//==========================================================================
	//==========================================================================
	public abstract void setAccessControl(Connection connection, int access);

	//==========================================================================
	//==========================================================================
	public abstract boolean isAllowedCommand(Connection connection, String cmd) throws DevFailed;
	
	//===========================================================
	/**
	 *	Build reason and origin of the exception
	 *	And throw it into a DevFailed exception
	 */
	//===========================================================
	public abstract void throw_dev_failed(Connection connection, Exception e, String command, boolean from_inout_cmd)
						throws DevFailed;	

}
