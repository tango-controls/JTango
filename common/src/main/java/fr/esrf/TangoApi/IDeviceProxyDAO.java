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
// $Revision: 29386 $
//
//-======================================================================


package fr.esrf.TangoApi;

import org.omg.CORBA.Request;

import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

import java.util.List;

public interface IDeviceProxyDAO extends IConnectionDAO{

	//===================================================================
	/**
	 *	Default DeviceProxy constructor. It will do nothing
	 */
	//===================================================================
	public abstract void init(DeviceProxy deviceProxy)	throws DevFailed;

	//===================================================================
	/**
	 *	DeviceProxy constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 */
	//===================================================================
	public abstract void init(DeviceProxy deviceProxy, String devname) throws DevFailed;

	//===================================================================
	/**
	 *	DeviceProxy constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	check_access	set check_access value
	 */
	//===================================================================
	public abstract void init(DeviceProxy deviceProxy, String devname, boolean check_access) throws DevFailed;

	//===================================================================
	/**
	 *	TangoDevice constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	ior		ior string used to import device
	 */
	//===================================================================
	public abstract void init(DeviceProxy deviceProxy, String devname, String ior) throws DevFailed;

	//===================================================================
	/**
	 *	TangoDevice constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public abstract void init(DeviceProxy deviceProxy, String devname, String host, String port) throws DevFailed;

	
	public abstract boolean use_db(DeviceProxy deviceProxy); 

	//========================================================================== 
	//========================================================================== 
	public abstract Database get_db_obj(DeviceProxy deviceProxy) throws DevFailed; 
	
	
	//===================================================================
	/**
	 *	Get connection on administration device.
	 */
	//===================================================================
	public abstract void import_admin_device(DeviceProxy deviceProxy, DbDevImportInfo info) throws DevFailed;

	//===================================================================
	/**
	 *	Get connection on administration device.
	 */
	//===================================================================
	public abstract void import_admin_device(DeviceProxy deviceProxy, String origin) throws DevFailed;

	//===========================================================
	/**
	 *	return the device name.
	 */
	//===========================================================
	public abstract String name(DeviceProxy deviceProxy);

	//===========================================================
	/**
	 *	return the device status read from CORBA attribute.
	 */
	//===========================================================
	public abstract String status(DeviceProxy deviceProxy) throws DevFailed;

	//===========================================================
	/**
	 *	return the device status.
	 *	@param	src	Source to read status.
	 *		It could be ApiDefs.FROM_CMD to read it from a command_inout or
	 *		ApiDefs.FROM_ATTR to read it from CORBA attribute.
	 */
	//===========================================================
	public abstract String status(DeviceProxy deviceProxy, boolean src) throws DevFailed;

	//===========================================================
	/**
	 *	return the device state read from CORBA attribute.
	 */
	//===========================================================
	public abstract DevState state(DeviceProxy deviceProxy) throws DevFailed;

	//===========================================================
	/**
	 *	return the device state.
	 *
	 *	@param	src	Source to read state.
	 *		It could be ApiDefs.FROM_CMD to read it from a command_inout or
	 *		ApiDefs.FROM_ATTR to read it from CORBA attribute.
	 */
	//===========================================================
	public abstract DevState state(DeviceProxy deviceProxy, boolean src) throws DevFailed;

	//===========================================================
	/**
	 *	return the IDL device command_query for the specified command.
	 *
	 *	@param cmdname command name to be used for the command_query
	 *	@return the command information..
	 */
	//===========================================================
	public abstract CommandInfo command_query(DeviceProxy deviceProxy, String cmdname) throws DevFailed;

	//===========================================================
	//	The following methods are an agrega of DbDevice
	//===========================================================
	//==========================================================================
	/**
	 *	Returns the class for the device
	 */
	//==========================================================================
	public abstract String get_class(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Returns the class inheritance for the device
	 *	<ul>
	 *		<li> [0] Device Class
	 *		<li> [1] Class from the device class is inherited.
	 *		<li> .....And so on
	 *	</ul>
	 */
	//==========================================================================
	public abstract String[] get_class_inheritance(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Set an alias for a device name
	 *	@param aliasname alias name.
	 */
	//==========================================================================
	public abstract void put_alias(DeviceProxy deviceProxy, String aliasname) throws DevFailed;

	//==========================================================================
	/**
	 *	Get alias for the device
	 *	@return the alias name if found.
	 */
	//==========================================================================
	public abstract String get_alias(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for the info of this device.
	 *	@return the information in a DeviceInfo.
	 */
	//==========================================================================
	public abstract DeviceInfo get_info(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for the export info of this device.
	 *	@return the information in a DbDevImportInfo.
	 */
	//==========================================================================
	public abstract DbDevImportInfo import_device(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Update the export info for this device in the database.
	 *	@param devinfo	Device information to export.
	 */
	//==========================================================================
	public abstract void export_device(DeviceProxy deviceProxy, DbDevExportInfo devinfo) throws DevFailed;

	//==========================================================================
	/**
	 *	Unexport the device in database.
	 */
	//==========================================================================
	public abstract void unexport_device(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Add/update this device to the database
	 *	@param devinfo The device name, class and server  specified in object.
	 */
	//==========================================================================
	public abstract void add_device(DeviceProxy deviceProxy, DbDevInfo devinfo) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete this device from the database
	 */
	//==========================================================================
	public abstract void delete_device(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device
	 *	properties for the pecified object.
	 *	@param wildcard	propertie's wildcard (* matches any charactere).
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract String[] get_property_list(DeviceProxy deviceProxy, String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device properties for this device.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_property(DeviceProxy deviceProxy, String[] propnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a device property for this device.
	 *	@param propname property name.
	 *	@return property in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum get_property(DeviceProxy deviceProxy, String propname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device properties for this device.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_property(DeviceProxy deviceProxy, DbDatum[] properties) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a  property for this device
	 *	The property name and its value are specified by the DbDatum
	 *	
	 *	@param prop Property name and value
	 */
	//==========================================================================
	public abstract void put_property(DeviceProxy deviceProxy, DbDatum prop) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of properties for this device
	 *	The property names and their values are specified by the DbDatum array.
	 *	
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public abstract void put_property(DeviceProxy deviceProxy, DbDatum[] properties) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for this device.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_property(DeviceProxy deviceProxy, String[] propnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a property for this device.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public abstract void delete_property(DeviceProxy deviceProxy, String propname) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for this device.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public abstract void delete_property(DeviceProxy deviceProxy, DbDatum[] properties) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the device for attribute config and returns names only.
	 *
	 *	@return attributes list for specified device
	 */
	//==========================================================================
	public abstract String[] get_attribute_list(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param attr attribute names and properties (names and values) array.
	 */
	//==========================================================================
	public abstract void put_attribute_property(DeviceProxy deviceProxy, DbAttribute[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update an attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param attr attribute name and properties (names and values).
	 */
	//==========================================================================
	public abstract void put_attribute_property(DeviceProxy deviceProxy, DbAttribute attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param attname attribute name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_attribute_property(DeviceProxy deviceProxy, String attname, String[] propnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a property for this object.
	 *	@param attname attribute name.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public abstract void delete_attribute_property(DeviceProxy deviceProxy, String attname, String propname) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 */
	//==========================================================================
	public abstract void delete_attribute_property(DeviceProxy deviceProxy, DbAttribute attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 */
	//==========================================================================
	public abstract void delete_attribute_property(DeviceProxy deviceProxy, DbAttribute[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device attribute
	 *	properties for this device.
	 *	@param attnames list of attribute names.
	 *	@return properties in DbAttribute objects array.
	 */
	//==========================================================================
	public abstract DbAttribute[] get_attribute_property(DeviceProxy deviceProxy, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a device attribute
	 *	property for this device.
	 *	@param attname attribute name.
	 *	@return property in DbAttribute object.
	 */
	//==========================================================================
	public abstract DbAttribute get_attribute_property(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete an attribute for this object.
	 *	@param attname attribute name.
	 */
	//==========================================================================
	public abstract void delete_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//===========================================================
	//	Attribute Methods
	//===========================================================
	//==========================================================================
	/**
	 *	Get the attributes configuration for the specified device.
	 *
	 *	@param attnames	attribute names to request config.
	 *	@return the attributes configuration.
	 */
	//==========================================================================
	public abstract AttributeInfo[] get_attribute_info(DeviceProxy deviceProxy, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Get the extended attributes configuration for the specified device.
	 *
	 *	@param attnames	attribute names to request config.
	 *	@return the attributes configuration.
	 */
	//==========================================================================
	public abstract AttributeInfoEx[] get_attribute_info_ex(DeviceProxy deviceProxy, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Get the attributes configuration for the specified device.
	 *
	 *	@param attnames	attribute names to request config.
	 *	@return the attributes configuration.
	 *	@deprecated use get_attribute_info(String[] attnames)
	 */
	//==========================================================================
	public abstract AttributeInfo[] get_attribute_config(DeviceProxy deviceProxy, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Get the attribute info for the specified device.
	 *
	 *	@param attname	attribute name to request config.
	 *	@return the attribute info.
	 */
	//==========================================================================
	public abstract AttributeInfo get_attribute_info(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Get the attribute info for the specified device.
	 *
	 *	@param attname	attribute name to request config.
	 *	@return the attribute info.
	 */
	//==========================================================================
	public abstract AttributeInfoEx get_attribute_info_ex(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Get the attribute configuration for the specified device.
	 *
	 *	@param attname	attribute name to request config.
	 *	@return the attribute configuration.
	 *	@deprecated use get_attribute_info(String attname)
	 */
	//==========================================================================
	public abstract AttributeInfo get_attribute_config(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Get all attributes info for the specified device.
	 *
	 *	@return all attributes info.
	 */
	//==========================================================================
	public abstract AttributeInfo[] get_attribute_info(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Get all attributes info for the specified device.
	 *
	 *	@return all attributes info.
	 */
	//==========================================================================
	public abstract AttributeInfoEx[] get_attribute_info_ex(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Get all attributes configuration for the specified device.
	 *
	 *	@return all attributes configuration.
	 *	@deprecated use get_attribute_info()
	 */
	//==========================================================================
	public abstract AttributeInfo[] get_attribute_config(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Update the attributes info for the specified device.
	 *
	 *	@param attr the attributes info.
	 */
	//==========================================================================
	public abstract void set_attribute_info(DeviceProxy deviceProxy, AttributeInfo[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Update the attributes extended info for the specified device.
	 *
	 *	@param attr the attributes info.
	 */
	//==========================================================================
	public abstract void set_attribute_info(DeviceProxy deviceProxy, AttributeInfoEx[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Update the attributes configuration for the specified device.
	 *
	 *	@param attr the attributes configuration.
	 *	@deprecated use set_attribute_info(AttributeInfo[] attr)
	 */
	//==========================================================================
	public abstract void set_attribute_config(DeviceProxy deviceProxy, AttributeInfo[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Read the attribute value for the specified device.
	 *
	 *	@param attname	attribute name to request value.
	 *	@return the attribute value.
	 */
	//==========================================================================
	public abstract DeviceAttribute read_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed;

	public abstract AttributeValue read_attribute_value(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Read the attribute values for the specified device.
	 *
	 *	@param attnames	attribute names to request values.
	 *	@return the attribute values.
	 */
	//==========================================================================
	public abstract DeviceAttribute[] read_attribute(DeviceProxy deviceProxy, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Write the attribute value for the specified device.
	 *
	 *	@param	devattr	attribute name and value.
	 */
	//==========================================================================
	public abstract void write_attribute(DeviceProxy deviceProxy, DeviceAttribute devattr) throws DevFailed;

	//==========================================================================
	/**
	 *	Write the attribute values for the specified device.
	 *
	 *	@param	devattr	attribute names and values.
	 */
	//==========================================================================
	public abstract void write_attribute(DeviceProxy deviceProxy, DeviceAttribute[] devattr) throws DevFailed;

	// ==========================================================================
	/**
	 *	Write and then read the attribute values, for the specified device.
	 * 
	 * @param devattr attribute names and values.
	 */
	// ==========================================================================
	public DeviceAttribute[] write_read_attribute(DeviceProxy deviceProxy, DeviceAttribute[] devattr) throws DevFailed;
    // ==========================================================================
    /**
     *	Write and then read the attribute values, for the specified device.
     *
     * @param deviceAttributes attribute names and values to be written.
     * @param readNames attribute names to read.
     */
    // ==========================================================================
    public DeviceAttribute[] write_read_attribute(DeviceProxy deviceProxy,
                    DeviceAttribute[] deviceAttributes, String[] readNames) throws DevFailed;

	//==========================================================================
	//==========================================================================
	public abstract DeviceProxy get_adm_dev(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Polling commands.
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Add a command to be polled for the device.
	 *	If already polled, update its polling period.
	 *
	 *	@param	cmdname	command name to be polled.
	 *	@param	period	polling period.
	 */
	//==========================================================================
	public abstract void poll_command(DeviceProxy deviceProxy, String cmdname, int period) throws DevFailed;

	//==========================================================================
	/**
	 *	Add a attribute to be polled for the device.
	 *	If already polled, update its polling period.
	 *
	 *	@param	attname	attribute name to be polled.
	 *	@param	period	polling period.
	 */
	//==========================================================================
	public abstract void poll_attribute(DeviceProxy deviceProxy, String attname, int period) throws DevFailed;

	//==========================================================================
	/**
	 *	Remove command of polled object list
	 *
	 *	@param	cmdname	command name to be removed of polled object list.
	 */
	//==========================================================================
	public abstract void stop_poll_command(DeviceProxy deviceProxy, String cmdname) throws DevFailed;

	//==========================================================================
	/**
	 *	Remove attribute of polled object list
	 *
	 *	@param	attname	attribute name to be removed of polled object list.
	 */
	//==========================================================================
	public abstract void stop_poll_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Returns the polling status for the device.
	 */
	//==========================================================================
	public abstract String[] polling_status(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Return the history for command polled.
	 *
	 *	@param	cmdname	command name to read polled history.
	 *	@param	nb		nb data to read.
	 */
	//==========================================================================
	public abstract DeviceDataHistory[] command_history(DeviceProxy deviceProxy, String cmdname, int nb) throws DevFailed;

	//==========================================================================
	/**
	 *	Return the history for attribute polled.
	 *
	 *	@param	attname	attribute name to read polled history.
	 *	@param	nb		nb data to read.
	 */
	//==========================================================================
	public abstract DeviceDataHistory[] attribute_history(DeviceProxy deviceProxy, String attname, int nb) throws DevFailed;

	//==========================================================================
	/**
	 *	Return the full history for command polled.
	 *
	 *	@param	cmdname	command name to read polled history.
	 */
	//==========================================================================
	public abstract DeviceDataHistory[] command_history(DeviceProxy deviceProxy, String cmdname) throws DevFailed;

	//==========================================================================
	/**
	 *	Return the full history for attribute polled.
	 *
	 *	@param	attname	attribute name to read polled history.
	 */
	//==========================================================================
	public abstract DeviceDataHistory[] attribute_history(DeviceProxy deviceProxy, String attname) throws DevFailed;
	// ==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified attribute.
	 *
	 *	 @param attname	specified attribute name.
	 */
	// ==========================================================================
	public abstract int get_attribute_polling_period(DeviceProxy deviceProxy, String attname) throws DevFailed;
	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified command.
	 *
	 *	 @param cmdname	specified attribute name.
	 */
	//==========================================================================
	public int get_command_polling_period(DeviceProxy deviceProxy, String cmdname) throws DevFailed;

	//==========================================================================
	/**
	 * 	Asynchronous calls
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 *	@param	data_in	input argument command.
	 */
	//==========================================================================
	public abstract int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData data_in) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 */
	//==========================================================================
	public abstract int command_inout_asynch(DeviceProxy deviceProxy, String cmdname) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public abstract int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, boolean forget) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 *	@param	data_in	input argument command.
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public abstract int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData data_in, boolean forget) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous command_inout using callback for reply.
	 *
	 *	@param	cmdname	Command name.
	 *	@param	argin	Input argument command.
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public abstract void command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData argin, CallBack cb) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous command_inout using callback for reply.
	 *
	 *	@param	cmdname	Command name.
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public abstract void command_inout_asynch(DeviceProxy deviceProxy, String cmdname, CallBack cb) throws DevFailed;

	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	id	asynchronous call id (returned by command_inout_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public abstract DeviceData command_inout_reply(DeviceProxy deviceProxy, int id, int timeout) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	aco	asynchronous call Request instance
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public abstract DeviceData command_inout_reply(DeviceProxy deviceProxy, AsyncCallObject aco, int timeout) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	id	asynchronous call id (returned by command_inout_asynch).
	 */
	//==========================================================================
	public abstract DeviceData command_inout_reply(DeviceProxy deviceProxy, int id) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	aco	asynchronous call Request instance
	 */
	//==========================================================================
	public abstract DeviceData command_inout_reply(DeviceProxy deviceProxy, AsyncCallObject aco) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Asynchronous read_attribute.
	 *
	 *	@param	attname	Attribute name.
	 */
	//==========================================================================
	public abstract int read_attribute_asynch(DeviceProxy deviceProxy, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous read_attribute.
	 *
	 *	@param	attnames	Attribute names.
	 */
	//==========================================================================
	public abstract int read_attribute_asynch(DeviceProxy deviceProxy, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Retrieve the command/attribute arguments to build exception description.
	 */
	//==========================================================================
	public abstract String get_asynch_idl_cmd(DeviceProxy deviceProxy, Request request, String idl_cmd);

	//==========================================================================
	/**
	 *	Check Asynchronous call reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public abstract void check_asynch_reply(DeviceProxy deviceProxy, Request request, int id, String idl_cmd) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Read Asynchronous read_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public abstract DeviceAttribute[] read_attribute_reply(DeviceProxy deviceProxy, int id, int timeout) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Read Asynchronous read_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public abstract DeviceAttribute[] read_attribute_reply(DeviceProxy deviceProxy, int id) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Asynchronous read_attribute using callback for reply.
	 *
	 *	@param	attname	attribute name.
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public abstract void read_attribute_asynch(DeviceProxy deviceProxy, String attname, CallBack cb) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous read_attribute using callback for reply.
	 *
	 *	@param	attnames	attribute names.
	 *	@param	cb			a CallBack object instance.
	 */
	//==========================================================================
	public abstract void read_attribute_asynch(DeviceProxy deviceProxy, String[] attnames, CallBack cb) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attr	Attribute value (name, writing value...)
	 */
	//==========================================================================
	public abstract int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attr	Attribute value (name, writing value...)
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public abstract int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr, boolean forget) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attribs	Attribute values (name, writing value...)
	 */
	//==========================================================================
	public abstract int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attribs	Attribute values (name, writing value...)
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public abstract int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs, boolean forget) throws DevFailed;

	//==========================================================================
	/**
	 *	check for Asynchronous write_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public abstract void write_attribute_reply(DeviceProxy deviceProxy, int id) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	check for Asynchronous write_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by write_attribute_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public abstract void write_attribute_reply(DeviceProxy deviceProxy, int id, int timeout) throws DevFailed, AsynReplyNotArrived;

	//==========================================================================
	/**
	 *	Asynchronous write_attribute using callback for reply.
	 *
	 *	@param	attr	Attribute values (name, writing value...)
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public abstract void write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr, CallBack cb) throws DevFailed;

	//==========================================================================
	/**
	 *	Asynchronous write_attribute using callback for reply.
	 *
	 *	@param	attribs	Attribute values (name, writing value...)
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public abstract void write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs, CallBack cb) throws DevFailed;

	//==========================================================================
	/**
	 *	return the still pending asynchronous call for a reply model.
	 *
	 *	@param	reply_model	ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	//==========================================================================
	public abstract int pending_asynch_call(DeviceProxy deviceProxy, int reply_model);

	//==========================================================================
	/**
	 *	Fire callback methods for all 
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public abstract void get_asynch_replies(DeviceProxy deviceProxy);

	//==========================================================================
	/**
	 *	Fire callback methods for all 
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public abstract void get_asynch_replies(DeviceProxy deviceProxy, int timeout);

	//==========================================================================
	/**
	 * 	Logging related methods
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Adds a new logging target to the device.
	 *
	 *	@param target The target for logging (e.g. file::/tmp/logging_device).
	 */
	//==========================================================================
	public abstract void add_logging_target(DeviceProxy deviceProxy, String target) throws DevFailed;

	//==========================================================================
	/**
	 *	Removes a new logging target to the device.
	 */
	//==========================================================================
	public abstract void remove_logging_target(DeviceProxy deviceProxy, String target_type, String target_name) throws DevFailed;

	//==========================================================================
	/**
	 *	get logging target from the device.
	 */
	//==========================================================================
	public abstract String[] get_logging_target(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	get logging level from the device.
	 *	@return device's logging level:
	 *		(ApiDefs.LOGGING_OFF, ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
	 *		ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
	 */
	//==========================================================================
	public abstract int get_logging_level(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Set logging level from the device.
	 *	@param level device's logging level:
	 *		(ApiDefs.LOGGING_OFF, ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
	 *		ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
	 */
	//==========================================================================
	public abstract void set_logging_level(DeviceProxy deviceProxy, int level) throws DevFailed;

	// ==========================================================================
	//	Locking Device 4 commands
	// ==========================================================================
	
	// ==========================================================================
	/**
	 *	Lock the device
	 *
	 *	@param	validity	Lock validity (in seconds)
	 */
	// ==========================================================================
	public abstract void lock(DeviceProxy deviceProxy, int validity) throws DevFailed;
	// ==========================================================================
	/**
	 *	Unlock the device
	 *
	 *	@return the device lock counter
	 */
	// ==========================================================================
	public abstract int unlock(DeviceProxy deviceProxy) throws DevFailed;
	// ==========================================================================
	/**
	 *	Returns true if the device is locked
	 */
	// ==========================================================================
	public boolean isLocked(DeviceProxy deviceProxy) throws DevFailed;
	// ==========================================================================
	/**
	 *	Returns true if the device is locked by this process
	 */
	// ==========================================================================
	public boolean isLockedByMe(DeviceProxy deviceProxy) throws DevFailed;
	// ==========================================================================
	/**
	 *	Returns the device lock status
	 */
	// ==========================================================================
	public String getLockerStatus(DeviceProxy deviceProxy) throws DevFailed;
	// ==========================================================================
	/**
	 *	Returns the device lock info
	 */
	// ==========================================================================
	public LockerInfo getLockerInfo(DeviceProxy deviceProxy) throws DevFailed;




	//==========================================================================
	/**
	 * 	TACO commands
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Returns  TACO device information.
	 *
	 *	@return  TACO device information as String array.
	 *	<li> Device name.
	 *	<li> Class name
	 *	<li> Device type
	 *	<li> Device server name
	 *	<li> Host name
	 */
	//==========================================================================
	public abstract String[] dev_inform(DeviceProxy deviceProxy) throws DevFailed;

	//==========================================================================
	/**
	 *	Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
	 *	@param	mode RPC protocol mode to be seted 
	 *		(TangoApi.TacoDevice.<b>D_TCP</b> or TangoApi.TacoDevice.<b>D_UDP</b>).
	 */
	//==========================================================================
	public abstract void set_rpc_protocol(DeviceProxy deviceProxy, int mode) throws DevFailed;

	//==========================================================================
	/**
	 *	@return	mode RPC protocol mode  used
	 *		(TangoApi.TacoDevice.<b>D_TCP</b> or TangoApi.TacoDevice.<b>D_UDP</b>).
	 */
	//==========================================================================
	public abstract int get_rpc_protocol(DeviceProxy deviceProxy) throws DevFailed;


    // ===================================================================
    /*
     * Pipe related methods
     */
    // ===================================================================

    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @param deviceProxy device proxy object
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig(DeviceProxy deviceProxy) throws DevFailed;
    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @param deviceProxy device proxy object
     * @param pipeNames pipe names.
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig(DeviceProxy deviceProxy, List<String> pipeNames) throws DevFailed;
    // ===================================================================
    /**
     * Set device pipe configuration
     * @param deviceProxy device proxy object
     * @param pipeInfoList info list containing pipe name, description, label,....
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public void setPipeConfig(DeviceProxy deviceProxy, List<PipeInfo> pipeInfoList) throws DevFailed;
    // ===================================================================
    /**
     * Read specified pipe and returns read data
     * @param deviceProxy device proxy object
     * @param pipeName pipe name
     * @return data read from specified pipe.
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public DevicePipe readPipe(DeviceProxy deviceProxy, String pipeName) throws DevFailed;
    // ===================================================================
    /**
     * Write data in specified pipe
     * @param deviceProxy device proxy object
     * @param devicePipe data to be written
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public void writePipe(DeviceProxy deviceProxy, DevicePipe devicePipe) throws DevFailed;
    // ===================================================================
    /**
     * Write data in specified pipe
     * @param deviceProxy device proxy object
     * @param devicePipe data to be written
	 * @return data read from specified pipe.
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public DevicePipe writeReadPipe(DeviceProxy deviceProxy, DevicePipe devicePipe) throws DevFailed;



	//===============================================================
    /*
     *  Event related methods
     */
	//===============================================================
	//==========================================================================
	/**
	 *	Subscribe to an event.
	 *
	 *	@param	attr_name	attribute name.
	 *	@param	event		event name.
	 *	@param  callback	event callback.
	 *	@param  stateless	If true, do not throw exception if connection failed.
	 */
	//==========================================================================
	public abstract int subscribe_event(DeviceProxy deviceProxy, String attr_name, int event, CallBack callback, String[] filters, boolean stateless) throws DevFailed;
	//==========================================================================
	/**
	 *	Subscribe to event to be stored in an event queue.
	 *
	 *	@param	attr_name	attribute name.
	 *	@param	event		event name.
	 *	@param  max_size	event queue maximum size.
	 *	@param  stateless	If true, do not throw exception if connection failed.
	 */
	//==========================================================================
	public abstract int subscribe_event(DeviceProxy deviceProxy, String attr_name, int event, int max_size, String[] filters, boolean stateless) throws DevFailed;

	//==========================================================================
	/**
	 *	Unsubscribe to an event.
	 *
	 *	@param	event_id	event identifier.
	 */
	//==========================================================================
	public abstract void unsubscribe_event(DeviceProxy deviceProxy, int event_id) throws DevFailed;

    //==========================================================================
    /**
     *	Subscribe to an event.
     *
     *	@param	event		event name.
     *	@param  callback	event callback.
     *	@param  stateless	If true, do not throw exception if connection failed.
     */
    //==========================================================================
    public abstract int subscribe_event(DeviceProxy deviceProxy,
                                int event, CallBack callback, boolean stateless) throws DevFailed;
    //==========================================================================
    /**
     *	Subscribe to event to be stored in an event queue.
     *
     *	@param	event		event name.
     *	@param  max_size	event queue maximum size.
     *	@param  stateless	If true, do not throw exception if connection failed.
     */
    //==========================================================================
    public abstract int subscribe_event(DeviceProxy deviceProxy,
                                int event, int max_size, boolean stateless) throws DevFailed;

}
