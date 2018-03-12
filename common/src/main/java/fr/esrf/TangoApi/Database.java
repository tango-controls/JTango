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
// $Revision: 28928 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoApi.events.DbEventImportInfo;
import fr.esrf.TangoDs.Except;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class Description: This class is the main class for TANGO database API. The
 * TANGO database is implemented as a TANGO device server. To access it, the
 * user has the CORBA interface command_inout(). This expects and returns all
 * parameters as ascii strings thereby making the database laborious to use for
 * retreing device properties and information. In order to simplify this access,
 * a high-level API has been implemented which hides the low-level formatting
 * necessary to convert the command_inout() return values into binary values and
 * all CORBA aspects of the TANGO. All data types are native java types e.g.
 * simple types an arrays.
 * 
 * @author verdier
 * @version $Revision: 28928 $
 */

@SuppressWarnings("UnusedDeclaration")
public class Database extends Connection {
	private IDatabaseDAO databaseDAO = null;

	public IDatabaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

    public void setDatabaseDAO(IDatabaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	/**
	 *	Device proxy on access control device instance.
	 */
	private AccessProxy		access_proxy   = null;
	
	/**
	 * access rights already checked if true.
	 */
	protected boolean access_checked = false;

	DevFailed access_devfailed = null;
    
    protected String[] possibleTangoHosts = null;
	
	
	// ===================================================================
	/**
	 * Database access constructor.
	 * 
	 * @throws DevFailed in case of environment not correctly set.
	 */
	// ===================================================================
	public Database() throws DevFailed {
		super();
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this);
	}

	// ===================================================================
	/**
	 * Database access constructor.
	 * 
	 * @param host host where database is running.
	 * @param port port for database connection.
	 * @throws DevFailed in case of host or port not available
	 */
	// ===================================================================
	public Database(String host, String port) throws DevFailed {
		super(host, port);
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this, host, port);
	}

	// ===================================================================
	/**
	 * Database constructor. It makes a Database on database server.
	 * 
	 * @param host host where database is running.
	 * @param port port for database connection.
	 * @param auto_reconnect do not reconnect if false.
     * @throws DevFailed in case of host or port not available
	 */
	// ===================================================================
	public Database(String host, String port, boolean auto_reconnect) throws DevFailed {
		super(host, port, auto_reconnect);
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this, host, port, auto_reconnect);		
	}

	// ===================================================================
	/**
	 * Database constructor. It imports the device.
	 * 
	 * @param devname name of the device to be imported.
     * @throws DevFailed in case of database access failed
	 */
	// ===================================================================
	public Database(String devname) throws DevFailed {
		super(devname);
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this, devname);		
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device. And set check_access.
	 * 
	 * @param devname name of the device to be imported.
	 * @param check_access set check_access value
     * @throws DevFailed in case of database access failed
	 */
	// ===================================================================
	public Database(String devname, boolean check_access) throws DevFailed {
		super(devname, check_access);
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this, devname, check_access);		
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device.
	 * 
	 * @param devname name of the device to be imported.
	 * @param param String parameter to import device.
	 * @param src Source to import device (ior, dbase...)
     * @throws DevFailed in case of database access failed
	 */
	// ===================================================================
	public Database(String devname, String param, int src) throws DevFailed {
		super(devname, param, src);
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this, devname, param, src);		
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device.
	 * 
	 * @param devname name of the device to be imported.
	 * @param host host where database is running.
	 * @param port port for database connection.
     * @throws DevFailed in case of host or port not available
	 */
	// ===================================================================
	public Database(String devname, String host, String port) throws DevFailed {
		super(devname, host, port);
		databaseDAO = TangoFactory.getSingleton().getDatabaseDAO();
		databaseDAO.init(this, devname, host, port);		
	}	
	
	
	// ==========================================================================
	// ==========================================================================
	public String toString() {
		return databaseDAO.toString();

	}

	// **************************************
	// MISCELANEOUS MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Query the database for general info about the table in the database.
	 * 
	 * @return the result of the query as String.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String get_info() throws DevFailed {
		return databaseDAO.get_info(this);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of host registred.
	 * 
	 * @return the list of all hosts registred in TANGO database.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_host_list() throws DevFailed {
		return databaseDAO.get_host_list(this);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of host registred.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildvcard characters.
	 * @return the list of the hosts registred in TANGO database with the
	 *         specified wildcard.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_host_list(String wildcard) throws DevFailed {
		return databaseDAO.get_host_list(this, wildcard);
	}

	// **************************************
	// SERVERS MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Query the database for a list of classes instancied for a server.
	 * 
	 * @param servname server name and instance name (ie.: Serial/i1).
	 * @return the list of all classes registred in TANGO database for servname
	 *         except the DServer class (existing on all Tango device server).
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_server_class_list(String servname) throws DevFailed {
		return databaseDAO.get_server_class_list(this, servname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of server names registred in the database.
	 * 
	 * @return the list of all server names registred in TANGO database.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_server_name_list() throws DevFailed {
		return databaseDAO.get_server_name_list(this);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of instance names registred for specified server name.
	 * 
	 * @param servname server name.
	 * @return the list of all instance names for specified server name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_instance_name_list(String servname) throws DevFailed {
		return databaseDAO.get_instance_name_list(this, servname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of servers registred in the database.
	 * 
	 * @return the list of all servers registred in TANGO database.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_server_list() throws DevFailed {
		return databaseDAO.get_server_list(this);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of servers registred in the database.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return the list of all servers registred in TANGO database.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_server_list(String wildcard) throws DevFailed {
		return databaseDAO.get_server_list(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of servers registred on the specified host.
	 * 
	 * @param hostname the specified host name.
	 * @return the list of the servers registred in TANGO database for the specified host.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_host_server_list(String hostname) throws DevFailed {
		return databaseDAO.get_host_server_list(this, hostname);
	}

	// ==========================================================================
	/**
	 * Query the database for server information.
	 * 
	 * @param servname The specified server name.
	 * @return The information found for the specified server in a DBServInfo object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbServInfo get_server_info(String servname) throws DevFailed {
		return databaseDAO.get_server_info(this, servname);
	}

	// ==========================================================================
	/**
	 * Add/update server information in database.
	 * 
	 * @param info Server information for the specified server in a DbServInfo object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_server_info(DbServInfo info) throws DevFailed {
		databaseDAO.put_server_info(this, info);
	}

	// ==========================================================================
	/**
	 * Delete server information in database.
	 * 
	 * @param servname Server name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_server_info(String servname) throws DevFailed {
		databaseDAO.delete_server_info(this, servname);
	}
	// ==========================================================================
	/**
	 * Rename server name/instance in database.
	 *
	 * @param srcServerName existing server name.
	 * @param newServerName new server name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void rename_server(String srcServerName, String newServerName) throws DevFailed {
		databaseDAO.rename_server(this, srcServerName, newServerName);
	}





	// **************************************
	// DEVICES MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Add/update a device to the database
	 * 
	 * @param devinfo The device name, class and server specified in object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void add_device(DbDevInfo devinfo) throws DevFailed {
		databaseDAO.add_device(this, devinfo);
	}

	// ==========================================================================
	/**
	 * Add/update a device to the database
	 * 
	 * @param devname The device name
	 * @param classname The class.
	 * @param servname The server name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void add_device(String devname, String classname, String servname) throws DevFailed {
		databaseDAO.add_device(this, devname, classname, servname);
    }

	// ==========================================================================
	/**
	 * Delete the device of the specified name from the database
	 * 
	 * @param devname The device name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device(String devname) throws DevFailed {
		databaseDAO.delete_device(this, devname);
	}

	// ==========================================================================
	/**
	 * Query the database for the export and more info of the specified device.
	 * 
	 * @param devname The device name.
	 * @return the information in a DbGetDeviceInfo.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DeviceInfo get_device_info(String devname) throws DevFailed {
		return databaseDAO.get_device_info(this, devname);
	}

	// ==========================================================================
	/**
	 * Query database for list of  devices.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return The list of devices
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_list(String wildcard) throws DevFailed {
		return databaseDAO.get_device_list(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for the export info of the specified device.
	 * 
	 * @param deviceName The device name.
	 * @return the information in a DbDevImportInfo.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDevImportInfo import_device(String deviceName) throws DevFailed {
		return databaseDAO.import_device(this, deviceName);
	}

	// ==========================================================================
	/**
	 * Mark the specified server as unexported in the database.
	 * 
	 * @param deviceName The device name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void unexport_device(String deviceName) throws DevFailed {
		databaseDAO.unexport_device(this, deviceName);
	}

	// ==========================================================================
	/**
	 * Update the export info fort this device in the database.
	 * 
	 * @param devExportInfo Device information to export.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void export_device(DbDevExportInfo devExportInfo) throws DevFailed {
		databaseDAO.export_device(this, devExportInfo);
	}

	// **************************************
	// Devices list MANAGEMENT
	// **************************************
	// ==========================================================================
	/**
	 * Query the database for server devices and classes.
	 * 
	 * @param serverName The specified server name.
	 * @return The devices and classes (e.g. "id11/motor/1", "StepperMotor",
	 *         "id11/motor/2", "StepperMotor",....)
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_class_list(String serverName) throws DevFailed {
		return databaseDAO.get_device_class_list(this, serverName);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of devices served by the specified server
	 * and of the specified class.
	 * 
	 * @param serverName The server name.
	 * @param className The class name
	 * @return the device names are stored in an array of strings.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_name(String serverName, String className) throws DevFailed {
		return databaseDAO.get_device_name(this, serverName, className);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device domain names witch match the
	 * wildcard provided.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return the device domain are stored in an array of strings.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_domain(String wildcard) throws DevFailed {
		return databaseDAO.get_device_domain(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device family names witch match the
	 * wildcard provided.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return the device family are stored in an array of strings.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_family(String wildcard) throws DevFailed {
		return databaseDAO.get_device_family(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device member names witch match the
	 * wildcard provided.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return the device member are stored in an array of strings.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_member(String wildcard) throws DevFailed {
		return databaseDAO.get_device_member(this, wildcard);

	}

	// **************************************
	// SERVERS MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Add a group of devices to the database.
	 * 
	 * @param servname Server name for these devices.
	 * @param devinfo Devices and server information.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void add_server(String servname, DbDevInfo[] devinfo) throws DevFailed {
		databaseDAO.add_server(this, servname, devinfo);
	}

	// ==========================================================================
	/**
	 * Delete the device server and its associated devices from the database.
	 * 
	 * @param devname the device name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_server(String devname) throws DevFailed {
		databaseDAO.delete_server(this, devname);

	}

	// ==========================================================================
	/**
	 * Add a group of devices to the database.
	 * 
	 * @param devinfo Devices and server information.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void export_server(DbDevExportInfo[] devinfo) throws DevFailed {
		databaseDAO.export_server(this, devinfo);
	}

	// ==========================================================================
	/**
	 * Mark all devices exported for this device server as unexported.
	 * 
	 * @param devname the device name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void unexport_server(String devname) throws DevFailed {
		databaseDAO.unexport_server(this, devname);
	}

	// **************************************
	// PROPERTIES MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Query the database for a list of object (ie non-device) for which
	 * properties are defined.
	 * 
	 * @param wildcard wildcard (* matches any charactere).
	 * @return objects for which properties are defiened list.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_object_list(String wildcard) throws DevFailed {
		return databaseDAO.get_object_list(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of object (ie non-device) for which
	 * properties are defined.
	 * 
	 * @param objname object name.
	 * @param wildcard wildcard (* matches any char).
	 * @return Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_object_property_list(String objname, String wildcard) throws DevFailed {
		return databaseDAO.get_object_property_list(this, objname, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of object (ie non-device) properties for
	 * the pecified object.
	 * 
	 * @param name Object name.
	 * @param propnames list of property names.
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum[] get_property(String name, String[] propnames) throws DevFailed {
		return databaseDAO.get_property(this, name, propnames);
	}

	// ==========================================================================
	/**
	 * Query the database for an object (ie non-device) property for the
	 * pecified object.
	 * 
	 * @param name Object name.
	 * @param propname list of property names.
	 * @return property in DbDatum object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum get_property(String name, String propname) throws DevFailed {
		return databaseDAO.get_property(this, name, propname);
	}

	// ==========================================================================
	/**
	 * Query the database for an object (ie non-device) property for the
	 * pecified object without access check (initilizing phase).
	 * 
	 * @param name Object name.
	 * @param propname list of property names.
	 * @param forced force TAC if true.
     * @return property in DbDatum object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum get_property(String name, String propname, boolean forced) throws DevFailed {
		return databaseDAO.get_property(this, name, propname, forced);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of object (ie non-device) properties for
	 * thr dpecified object. The property names are specified by the DbDatum
	 * array objects.
	 * 
	 * @param name Object name.
	 * @param properties list of property DbDatum objects.
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum[] get_property(String name, DbDatum[] properties) throws DevFailed {
		return databaseDAO.get_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of properties for the specified object The
	 * property names and their values are specified by the DbDatum array.
	 * 
	 * @param name Object name.
	 * @param properties Properties names and values array.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.put_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Object name.
	 * @param propnames Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_property(String name, String[] propnames) throws DevFailed {
		databaseDAO.delete_property(this, name, propnames);
	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name Object name.
	 * @param propname Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_property(String name, String propname) throws DevFailed {
		databaseDAO.delete_property(this, name, propname);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Object name.
	 * @param properties Property DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.delete_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of class properties for the specified object.
	 * 
	 * @param classname device name.
	 * @param wildcard propertie's wildcard (* matches any charactere).
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_class_property_list(String classname, String wildcard) throws DevFailed {
		return databaseDAO.get_class_property_list(this, classname, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device properties for the specified object.
	 * 
	 * @param devname device name.
	 * @param wildcard propertie's wildcard (* matches any charactere).
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_property_list(String devname, String wildcard) throws DevFailed {
		return databaseDAO.get_device_property_list(this, devname, wildcard);
	}

	// ==========================================================================
    /**
     * Returns the class name for specified device
     * @param devname specified device name
     * @return the class name for specified device
     * @throws DevFailed in case of database access failed
     */
	// ==========================================================================
	public String get_class_for_device(String devname) throws DevFailed {
		return databaseDAO.get_class_for_device(this, devname);
	}

	// ==========================================================================
    /**
     * Returns the inheritance for specified device
     * @param devname specified device name
     * @return the inheritance for specified device
     * @throws DevFailed in case of database access failed
     */
	// ==========================================================================
	public String[] get_class_inheritance_for_device(String devname) throws DevFailed {
		return databaseDAO.get_class_inheritance_for_device(this, devname);
	}

	// **************************************
	// DEVICE PROPERTIES MANAGEMENT
	// **************************************
	// ==========================================================================
	/**
	 * Query the database for a list of device properties for the pecified
	 * object.
	 * 
	 * @param name device name.
	 * @param propnames list of property names.
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum[] get_device_property(String name, String[] propnames) throws DevFailed {
		return databaseDAO.get_device_property(this, name, propnames);
	}

	// ==========================================================================
	/**
	 * Query the database for a device property for the pecified object.
	 * 
	 * @param name device name.
	 * @param propname property name.
	 * @return property in DbDatum object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum get_device_property(String name, String propname) throws DevFailed {
		return databaseDAO.get_device_property(this, name, propname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device properties for the pecified
	 * object. The property names are specified by the DbDatum array objects.
	 * 
	 * @param name device name.
	 * @param properties list of property DbDatum objects.
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum[] get_device_property(String name, DbDatum[] properties) throws DevFailed {
		return databaseDAO.get_device_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of properties for the specified device The
	 * property names and their values are specified by the DbDatum array.
	 * 
	 * @param name device name.
	 * @param properties Properties names and values array.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_device_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.put_device_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Device name.
	 * @param propnames Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_property(String name, String[] propnames) throws DevFailed {
		databaseDAO.delete_device_property(this, name, propnames);
	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name Device name.
	 * @param propname Property name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_property(String name, String propname) throws DevFailed {
		databaseDAO.delete_device_property(this, name, propname);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Device name.
	 * @param properties Property DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.delete_device_property(this, name, properties);
	}

	// **************************************
	// ATTRIBUTE PROPERTIES MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Query the database for a list of device attributes
	 * 
	 * @param devname device name.
	 * @return attribute names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_attribute_list(String devname) throws DevFailed {
		return databaseDAO.get_device_attribute_list(this, devname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device attributes properties for the
	 * specified object.
	 * 
	 * @param devname device name.
	 * @param attnames attribute names.
	 * @return properties in DbAttribute objects array.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbAttribute[] get_device_attribute_property(String devname, String[] attnames) throws DevFailed {
		return databaseDAO.get_device_attribute_property(this, devname, attnames);
	}

	// ==========================================================================
	/**
	 * Query the database for device attribute property for the pecified object.
	 * 
	 * @param devname device name.
	 * @param attname attribute name.
	 * @return property in DbAttribute object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbAttribute get_device_attribute_property(String devname, String attname) throws DevFailed {
		return databaseDAO.get_device_attribute_property(this, devname, attname);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of attribute properties for the specified device.
	 * The property names and their values are specified by the DbAttribute array.
	 * 
	 * @param devname device name.
	 * @param attr attribute names, and properties (names and values).
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_device_attribute_property(String devname, DbAttribute[] attr) throws DevFailed {
		databaseDAO.put_device_attribute_property(this, devname, attr);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of attribute properties for the specified device.
	 * The property names and their values are specified by the DbAttribute.
	 * 
	 * @param devname device name.
	 * @param attr attribute name, and properties (names and values).
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_device_attribute_property(String devname, DbAttribute attr) throws DevFailed {
		databaseDAO.put_device_attribute_property(this, devname, attr);
	}

	// ==========================================================================
	/**
	 * Delete an list of attributes properties for the specified object.
	 * 
	 * @param devname Device name.
	 * @param attr attribute name, and properties (names).
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, DbAttribute attr) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attr);
	}

	// ==========================================================================
	/**
	 * Delete a list of attributes properties for the specified object.
	 * 
	 * @param devname Device name.
	 * @param attr attribute names, and properties (names) in array.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, DbAttribute[] attr) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attr);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param devname Device name.
	 * @param attname Attribute name.
	 * @param propnames Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, String attname, String[] propnames) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attname, propnames);
	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param devname Device name.
	 * @param attname Attribute name.
	 * @param propname Property name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, String attname, String propname) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attname, propname);
	}

	// ==========================================================================
	/**
	 * Delete an attribute for the specified object.
	 * 
	 * @param devname Device name.
	 * @param attname Attribute name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_attribute(String devname, String attname) throws DevFailed {
		databaseDAO.delete_device_attribute(this, devname, attname);
	}

	// **************************************
	// CLASS PROPERTIES MANAGEMENT
	// **************************************
	// ==========================================================================
	/**
	 * Query the database for a list of classes registred in the database.
	 * 
	 * @param servname server name
	 * @return the list of all servers registred in TANGO database.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_class_list(String servname) throws DevFailed {
		return databaseDAO.get_class_list(this, servname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of class properties for the pecified
	 * object.
	 * 
	 * @param name Class name.
	 * @param propnames list of property names.
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum[] get_class_property(String name, String[] propnames) throws DevFailed {
		return databaseDAO.get_class_property(this, name, propnames);
	}

	// ==========================================================================
	/**
	 * Query the database for a class property for the pecified object.
	 * 
	 * @param name Class name.
	 * @param propname list of property names.
	 * @return property in DbDatum object.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum get_class_property(String name, String propname) throws DevFailed {
		return databaseDAO.get_class_property(this, name, propname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of class properties for the pecified
	 * object. The property names are specified by the DbDatum array objects.
	 * 
	 * @param name Class name.
	 * @param properties list of property DbDatum objects.
	 * @return properties in DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbDatum[] get_class_property(String name, DbDatum[] properties) throws DevFailed {
		return databaseDAO.get_class_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of properties for the specified class. The
	 * property names and their values are specified by the DbDatum array.
	 * 
	 * @param name Class name.
	 * @param properties Properties names and values array.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_class_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.put_class_property(this, name, properties);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Class name.
	 * @param propnames Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_class_property(String name, String[] propnames) throws DevFailed {
		databaseDAO.delete_class_property(this, name, propnames);
	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name Class name.
	 * @param propname Property name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_class_property(String name, String propname) throws DevFailed {
		databaseDAO.delete_class_property(this, name, propname);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Class name.
	 * @param properties Property DbDatum objects.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_class_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.delete_class_property(this, name, properties);
	}

	// **************************************
	// CLASS Attribute PROPERTIES MANAGEMENT
	// **************************************
	// ==========================================================================
	/**
	 * Query the database for a attributes defined for a class. All attributes
	 * for a class attribute are returned.
	 * 
	 * @param classname class name.
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return attributes list for specified class
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_class_attribute_list(String classname, String wildcard) throws DevFailed {
		return databaseDAO.get_class_attribute_list(this, classname, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a attribute properties for the specified class.
	 * 
	 * @param classname class name.
	 * @param attname attribute name
	 * @return attribute properties for specified class and attribute.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbAttribute get_class_attribute_property(String classname, String attname) throws DevFailed {
		return databaseDAO.get_class_attribute_property(this, classname, attname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of class attributes properties for the
	 * pecified object.
	 * 
	 * @param classname Class name.
	 * @param attnames list of attribute names.
	 * @return attribute properties for specified class and attributes.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbAttribute[] get_class_attribute_property(String classname, String[] attnames) throws DevFailed {
		return databaseDAO.get_class_attribute_property(this, classname, attnames);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of properties for the specified class attribute.
	 * The attribute name, the property names and their values are specified by
	 * the DbAttribute.
	 * 
	 * @param classname Class name.
	 * @param attr DbAttribute objects containing attribute names, property names
	 *            and property values.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_class_attribute_property(String classname, DbAttribute[] attr) throws DevFailed {
		databaseDAO.put_class_attribute_property(this, classname, attr);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of properties for the specified class attribute.
	 * The attribute name, the property names and their values are specified by
	 * the DbAttribute.
	 * 
	 * @param classname Class name.
	 * @param attr DbAttribute object containing attribute name, property names
	 *            and property values.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_class_attribute_property(String classname, DbAttribute attr) throws DevFailed {
		databaseDAO.put_class_attribute_property(this, classname, attr);
	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name Class name.
     * @param attname attribute name
	 * @param propname Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_class_attribute_property(String name, String attname, String propname) throws DevFailed {
		databaseDAO.delete_class_attribute_property(this, name, attname, propname);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name Class name.
	 * @param attname attribute name.
	 * @param propnames Property names.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_class_attribute_property(String name, String attname, String[] propnames) throws DevFailed {
		databaseDAO.delete_class_attribute_property(this, name, attname, propnames);
	}

	// ==========================================================================
	/**
	 * Query database for list of exported devices.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildvcard characters.
	 * @return The list of exported devices
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_exported(String wildcard) throws DevFailed {
		return databaseDAO.get_device_exported(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query database for list of exported devices for the specified class name.
	 * 
	 * @param classname class name to query the exported devices.
	 * @return The list of exported devices
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_exported_for_class(String classname) throws DevFailed {
		return databaseDAO.get_device_exported_for_class(this, classname);
	}

	// ==========================================================================
	// Aliases management
	// ==========================================================================

	// ==========================================================================
	/**
	 * Query the database for a list of aliases for the specified wildcard.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildcard characters.
	 * @return the device aliases are stored in an array of strings.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_device_alias_list(String wildcard) throws DevFailed {
		return databaseDAO.get_device_alias_list(this, wildcard);
	}
    // ==========================================================================
    /**
     * Query the database for an alias for the specified device.
     *
     * @param deviceName device's name.
     * @return the device alias found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String get_alias_from_device(String deviceName) throws DevFailed {
        return databaseDAO.getAliasFromDevice(this, deviceName);
    }
    // ==========================================================================
    /**
     * Query the database for a device name for the specified alias.
     *
     * @param alias alias name.
     * @return the device name found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String get_device_from_alias(String alias) throws DevFailed {
        return databaseDAO.getDeviceFromAlias(this, alias);
    }

	// ==========================================================================
	/**
	 * Query the database for an alias for the specified device.
	 * 
	 * @param devname device's name.
	 * @return the device alias found.
     * @throws DevFailed in case of database access failed
     * @deprecated use get_alias_from_device
	 */
	// ==========================================================================
	public String get_device_alias(String devname) throws DevFailed {
		return databaseDAO.get_device_alias(this, devname);
	}

	// ==========================================================================
	/**
	 * Query the database a device for the specified alias.
	 * 
	 * @param alias The device name.alias
	 * @return the device aliases are stored in an array of strings.
     * @throws DevFailed in case of database access failed
     * @deprecated use get_device_from_alias
	 */
	// ==========================================================================
	public String get_alias_device(String alias) throws DevFailed {
		return databaseDAO.get_alias_device(this, alias);
	}

	// ==========================================================================
	/**
	 * Set an alias for a device name
	 * 
	 * @param devname device name.
	 * @param aliasname alias name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_device_alias(String devname, String aliasname) throws DevFailed {
		databaseDAO.put_device_alias(this, devname, aliasname);
	}

	// ==========================================================================
	/**
	 * Query the database to delete alias for the specified device alias.
	 * 
	 * @param alias device alias name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_device_alias(String alias) throws DevFailed {
		databaseDAO.delete_device_alias(this, alias);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of aliases for the specified wildcard.
	 * 
	 * @param wildcard Wildcard char is '*' and matches wildvcard characters.
	 * @return the device aliases are stored in an array of strings.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public String[] get_attribute_alias_list(String wildcard) throws DevFailed {
		return databaseDAO.get_attribute_alias_list(this, wildcard);
	}

    // ==========================================================================
    /**
     * Query the database for an alias for the specified attribute.
     *
     * @param  attName attribute name.
     * @return the alias found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String get_alias_from_attribute(String attName) throws DevFailed {
        return databaseDAO.getAliasFromAttribute(this, attName);
    }
    // ==========================================================================
    /**
     * Query the database for an attribute name for the specified alias.
     *
     * @param  alias alias name.
     * @return the attribute found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String get_attribute_from_alias(String alias) throws DevFailed {
        return databaseDAO.getAttributeFromAlias(this, alias);
    }
	// ==========================================================================
	/**
	 * Query the database for a list of aliases for the specified attribute.
	 * 
	 * @param attname The attribute name.
	 * @return the device aliases are stored in an array of strings.
     * @throws DevFailed in case of database access failed
     * @deprecated use get_alias_from_attribute
	 */
	// ==========================================================================
	public String get_attribute_alias(String attname) throws DevFailed {
		return databaseDAO.get_attribute_alias(this, attname);
	}

	// ==========================================================================
	/**
	 * Set an alias for a attribute name
	 * 
	 * @param attname attribute name.
	 * @param aliasname alias name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void put_attribute_alias(String attname, String aliasname) throws DevFailed {
		databaseDAO.put_attribute_alias(this, attname, aliasname);
	}

	// ==========================================================================
	/**
	 * Query the database to delete alias for the specified attribute alias.
	 * 
	 * @param alias device alias name.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public void delete_attribute_alias(String alias) throws DevFailed {
		databaseDAO.delete_attribute_alias(this, alias);
	}

	// ==========================================================================
	// ==========================================================================
	public String[] getDevices(String wildcard) throws DevFailed {
		return databaseDAO.getDevices(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for the export info of the specified event.
	 * 
	 * @param channel_name The event name.
	 * @return the information in a DbEventImportInfo.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbEventImportInfo import_event(String channel_name) throws DevFailed {
		return databaseDAO.import_event(this, channel_name);
	}

	// ==========================================================================
	/**
	 * Returns the history of the specified device property.
	 *
	 * @param devname Device name
	 * @param propname Property name (can be wildcarded)
     * @return the history of the specified device property.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbHistory[] get_device_property_history(String devname, String propname) throws DevFailed {
		return databaseDAO.get_device_property_history(this, devname, propname);
	}

	// ==========================================================================
	/**
	 * Returns the history of the specified device attribute property.
	 *
	 * @param devname Device name
	 * @param attname Attribute name (can be wildcarded)
	 * @param propname Property name (can be wildcarded)
     * @return the history of the specified device attribute property.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbHistory[] get_device_attribute_property_history(String devname, String attname, String propname) throws DevFailed {
		return databaseDAO.get_device_attribute_property_history(this, devname, attname, propname);
	}

	// ==========================================================================
	/**
	 * Returns the history of the specified class property.
	 *
	 * @param classname Class name
	 * @param propname Property name (can be wildcarded)
     * @return the history of the specified class property.
	 * @throws DevFailed in case of failure
	 */
	// ==========================================================================
	public DbHistory[] get_class_property_history(String classname, String propname) throws DevFailed {
		return databaseDAO.get_class_property_history(this, classname, propname);
	}

	// ==========================================================================
	/**
	 * Returns the history of the specified class attribute property.
	 *
	 * @param classname Class name
	 * @param attname Attribute name (can be wildcarded)
	 * @param propname Property name (can be wildcarded)
     * @return the history of the specified class attribute property.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbHistory[] get_class_attribute_property_history(String classname, String attname, String propname) throws DevFailed {
		return databaseDAO.get_class_attribute_property_history(this, classname, attname, propname);
	}

	// ==========================================================================
	/**
	 * Returns the history of the specified object property.
	 *
	 * @param objname Object name
	 * @param propname Property name (can be wildcarded)
     * @return the history of the specified object property.
     * @throws DevFailed in case of database access failed
	 */
	// ==========================================================================
	public DbHistory[] get_property_history(String objname, String propname) throws DevFailed {
		return databaseDAO.get_property_history(this, objname, propname);
	}

	// ===================================================================
	/**
	 * Query database for specified services.
	 * 
	 * @param servicename The service name.
	 * @param instname The instance name (could be * for all instances).
	 * @return The device names found for specified service and instance.
     * @throws DevFailed in case of database access failed
	 */
	// ===================================================================
	public String[] getServices(String servicename, String instname) throws DevFailed {
		return databaseDAO.getServices(this, servicename, instname);
	}

	// ===============================================================
	/**
	 * Register a device as a Tango service :
	 * <b>ServiceName/InstanceName:DeviceName</b>
	 * 
	 * @param serviceName Service's name
	 * @param instanceName Instance service's name
	 * @param devname Device's name
     * @throws DevFailed in case of database access failed
	 */
	// ===============================================================
	public void registerService(String serviceName, String instanceName, String devname) throws DevFailed {
		databaseDAO.registerService(this, serviceName, instanceName, devname);
	}

	// ===============================================================
	/**
	 * Unregister a device as a Tango service :
	 * <b>ServiceName/InstanceName:DeviceName</b>
	 * 
	 * @param serviceName Service's name
	 * @param instanceName Instance service's name
	 * @param devname Device's name
     * @throws DevFailed in case of database access failed
	 */
	// ===============================================================
	public void unregisterService(String serviceName, String instanceName, String devname) throws DevFailed {
		databaseDAO.unregisterService(this, serviceName, instanceName, devname);
	}

	// ===================================================================
	// ===================================================================

	// ===================================================================
	/**
	 * Check Tango Access. - Check if control access is requested. - Check who
	 * is the user and the host. - Check access for this user, this host and the
	 * specified device.
	 * 
	 * @param devname Specified device name.
	 * @param devUrl  Specified device url
     * @return The Tango access controle found.
	 */
	// ===================================================================
	public int checkAccessControl(String devname, TangoUrl devUrl) {
		return databaseDAO.checkAccessControl(this, devname, devUrl);
	}

	// ===================================================================
	/**
	 * Check for specified class, the specified command is allowed.
	 * 
	 * @param classname Specified class name.
	 * @param cmd Specified command name.
     * @return true if specified command is allowed
     * @throws DevFailed in case of database access failed
	 */
	// ===================================================================
	public boolean isCommandAllowed(String classname, String cmd) throws DevFailed {
		return databaseDAO.isCommandAllowed(this, classname, cmd);
	}
	// ===================================================================
	// ===================================================================
    public String[] getPossibleTangoHosts() {
        if (possibleTangoHosts==null)
            possibleTangoHosts = databaseDAO.getPossibleTangoHosts(this);
        return possibleTangoHosts;
    }
	// ===================================================================






	// ===================================================================
    /*
     * Pipe related methods
     */
	// ===================================================================

    // ===================================================================
    /**
     * Query the database for a list of device pipe properties
     * for the specified pipe.
     * @param deviceName specified device.
     * @param pipeName specified pipe.
     * @return a list of device pipe properties.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbPipe getDevicePipeProperties(String deviceName, String pipeName) throws DevFailed {
        return databaseDAO.getDevicePipeProperties(this, deviceName, pipeName);
    }
    // ===================================================================
    /**
     * Query the database for a device pipe property
     * for the specified pipe.
     * @param deviceName specified device.
     * @param pipeName specified pipe.
     * @param propertyName specified property.
     * @return device pipe property.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbDatum getDevicePipeProperty(String deviceName, String pipeName, String propertyName) throws DevFailed {
        DbPipe dbPipe = databaseDAO.getDevicePipeProperties(this, deviceName, pipeName);
        DbDatum datum = dbPipe.getDatum(propertyName);
        if (datum==null)
            Except.throw_exception("TangoApi_PropertyNotFound",
                    "Property " + propertyName + " not found for pipe " + pipeName);
        return datum;
    }
	// ===================================================================
    /**
     * Query the database for a list of class pipe properties
     * for the specified pipe.
     * @param className specified class.
     * @param pipeName specified pipe.
     * @return a list of class pipe properties.
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public DbPipe getClassPipeProperties(String className, String pipeName) throws DevFailed {
        return databaseDAO.getClassPipeProperties(this, className, pipeName);
    }
    // ===================================================================
    /**
     * Query the database for a class pipe property
     * for the specified pipe.
     * @param className specified class.
     * @param pipeName specified pipe.
     * @param propertyName specified property.
     * @return class pipe property.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbDatum getClassPipeProperty(String className, String pipeName, String propertyName) throws DevFailed {
        DbPipe dbPipe = databaseDAO.getClassPipeProperties(this, className, pipeName);
        DbDatum datum = dbPipe.getDatum(propertyName);
        if (datum==null)
            Except.throw_exception("TangoApi_PropertyNotFound",
                    "Property " + propertyName + " not found for pipe " + pipeName);
        return datum;
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified device.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param deviceName device name.
     * @param dbPipe pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putDevicePipeProperty(String deviceName, DbPipe dbPipe) throws DevFailed {
        databaseDAO.putDevicePipeProperty(this, deviceName, dbPipe);
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified device.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param deviceName device name.
     * @param dbPipes    list of pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putDevicePipeProperty(String deviceName, ArrayList<DbPipe> dbPipes) throws DevFailed {
        for (DbPipe dbPipe : dbPipes)
            databaseDAO.putDevicePipeProperty(this, deviceName, dbPipe);
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified class.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param className class name.
     * @param dbPipe pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putClassPipeProperty(String className, DbPipe dbPipe) throws DevFailed {
        databaseDAO.putClassPipeProperty(this, className, dbPipe);
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified class.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param className class name.
     * @param dbPipes list of pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putClassPipeProperty(String className, ArrayList<DbPipe> dbPipes) throws DevFailed {
        for (DbPipe dbPipe : dbPipes)
            databaseDAO.putClassPipeProperty(this, className, dbPipe);
    }
	// ===================================================================
    /**
     * Query database for a list of pipes for specified device.
     * @param deviceName specified device name.
     * @return a list of pipes defined in database for specified device.
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public List<String> getDevicePipeList(String deviceName) throws DevFailed {
        return getDevicePipeList(deviceName, "*");
    }
	// ===================================================================
    /**
     * Query database for a list of pipes for specified device and specified wildcard.
     * @param deviceName specified device name.
     * @param wildcard specified wildcard.
     * @return a list of pipes defined in database for specified device and specified wildcard.
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public List<String> getDevicePipeList(String deviceName, String wildcard) throws DevFailed {
        return databaseDAO.getDevicePipeList(this, deviceName, wildcard);
    }
	// ===================================================================
    /**
     * Query database for a list of pipes for specified class.
     * @param className specified class name.
     * @return a list of pipes defined in database for specified class.
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public List<String> getClassPipeList(String className) throws DevFailed {
        return getClassPipeList(className, "*");
    }
	// ===================================================================
    /**
     * Query database for a list of pipes for specified class and specified wildcard.
     * @param className specified class name.
     * @param wildcard specified wildcard.
     * @return a list of pipes defined in database for specified class and specified wildcard.
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public List<String> getClassPipeList(String className, String wildcard) throws DevFailed {
        return databaseDAO.getClassPipeList(this, className, wildcard);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     * @param deviceName device name.
     * @param pipeName pipe name
     * @param propertyName property name
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteDevicePipeProperty(String deviceName,
                                         String pipeName, String propertyName) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(1);
        list.add(propertyName);
        databaseDAO.deleteDevicePipeProperties(this, deviceName, pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     * @param deviceName Device name.
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteDevicePipeProperties(String deviceName,
                                         String pipeName, String[] propertyNames) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(propertyNames.length);
        Collections.addAll(list, propertyNames);
        databaseDAO.deleteDevicePipeProperties(this, deviceName, pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     * @param deviceName Device name.
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteDevicePipeProperties(String deviceName,
                                         String pipeName, List<String> propertyNames) throws DevFailed {
        databaseDAO.deleteDevicePipeProperties(this, deviceName, pipeName, propertyNames);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     * @param className class name.
     * @param pipeName pipe name
     * @param propertyName property name
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteClassPipeProperty(String className,
                                         String pipeName, String propertyName) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(1);
        list.add(propertyName);
        databaseDAO.deleteClassPipeProperties(this, className, pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     * @param className class name.
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteClassPipeProperties(String className,
                                         String pipeName, String[] propertyNames) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(propertyNames.length);
        Collections.addAll(list, propertyNames);
        databaseDAO.deleteClassPipeProperties(this, className, pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     * @param className class name.
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteClassPipeProperties(String className,
                                         String pipeName, List<String> propertyNames) throws DevFailed {
        databaseDAO.deleteClassPipeProperties(this, className, pipeName, propertyNames);
    }
	// ===================================================================
    /**
     * Delete specified pipe for specified device.
     * @param deviceName    device name
     * @param pipeName      pipe name
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public void deleteDevicePipe(String deviceName, String pipeName) throws DevFailed {
        databaseDAO.deleteDevicePipe(this, deviceName, pipeName);
    }
	// ===================================================================
    /**
     * Delete specified pipe for specified class.
     * @param className    class name
     * @param pipeName      pipe name
     * @throws DevFailed in case of database access failed
     */
	// ===================================================================
    public void deleteClassPipe(String className, String pipeName) throws DevFailed {
        databaseDAO.deleteClassPipe(this, className, pipeName);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipe
     * @param deviceName    device name
     * @param pipeName      pipe name
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public void deleteAllDevicePipeProperty(String deviceName, String pipeName) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(1);
        list.add(pipeName);
        deleteAllDevicePipeProperty(deviceName, list);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipes
     * @param deviceName    device name
     * @param pipeNames     pipe names
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public void deleteAllDevicePipeProperty(String deviceName, String[] pipeNames) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(pipeNames.length);
        Collections.addAll(list, pipeNames);
        deleteAllDevicePipeProperty(deviceName, list);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipes
     * @param deviceName    device name
     * @param pipeNames     pipe names
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public void deleteAllDevicePipeProperty(String deviceName, List<String> pipeNames) throws DevFailed {
        databaseDAO.deleteAllDevicePipeProperty(this, deviceName, pipeNames);
    }
    // ===================================================================
    /**
     * Returns the property history for specified pipe.
     * @param deviceName    device name
     * @param pipeName      pipe name
     * @param propertyName  property Name
     * @return the property history for specified pipe.
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public List<DbHistory> getDevicePipePropertyHistory(String deviceName,String pipeName,
                                                             String propertyName) throws DevFailed {
        return databaseDAO.getDevicePipePropertyHistory(this, deviceName, pipeName, propertyName);
    }
    // ===================================================================
    /**
     * Returns the property history for specified pipe.
     * @param className    class name
     * @param pipeName      pipe name
     * @param propertyName  property Name
     * @return the property history for specified pipe.
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public List<DbHistory> getClassPipePropertyHistory(String className,String pipeName,
                                                             String propertyName) throws DevFailed {
        return databaseDAO.getClassPipePropertyHistory(this, className, pipeName, propertyName);
    }
    // ===================================================================
	/**
	 * Query database to get a list of device using the specified device as
	 * 		as root for forwarded attributes
	 * @param deviceName the specified device
	 * @return a list of device using the specified device as as root for forwarded attributes
	 * @throws DevFailed
	 */
    // ===================================================================
    public List<ForwardedAttributeDatum> getForwardedAttributeInfoForDevice(String deviceName) throws DevFailed {
		List<String[]> data = databaseDAO.getForwardedAttributeDataForDevice(this, deviceName);
		List<ForwardedAttributeDatum> info = new ArrayList<ForwardedAttributeDatum>();
		for (String[] datum : data) {
			info.add(new ForwardedAttributeDatum(datum[0],datum[1],datum[2]));
		}
		return info;
	}
    // ===================================================================
    // ===================================================================



	/*
	 * Getter and setter 
	 */
	public boolean isAccess_checked() {
		return access_checked;
	}

	public void setAccess_checked(boolean access_checked) {
		this.access_checked = access_checked;
	}

	public DevFailed getAccess_devfailed() {
		return access_devfailed;
	}

	public void setAccess_devfailed(DevFailed access_devfailed) {
		this.access_devfailed = access_devfailed;
	}

	public AccessProxy getAccess_proxy() {
		return access_proxy;
	}

	public void setAccess_proxy(AccessProxy access_proxy) {
		this.access_proxy = access_proxy;
	}
    // ===================================================================
    // ===================================================================
    public static void main(String[] args) {
        try {
            if (args.length>0) {
                if (args[0].equals("-hosts")) {
                    String[]    hosts = ApiUtil.get_db_obj().get_device_member("tango/admin/*");
                    for (String host : hosts) {
                        System.out.println(host);
                    }
                }
            }
            else {
                System.err.println("Parameters ?");
                System.err.println("\t-hosts: display controlled (by a Starter) host list");
            }
        }
        catch (DevFailed e) {
            System.err.println(e.errors[0].desc);
        }
    }

}

