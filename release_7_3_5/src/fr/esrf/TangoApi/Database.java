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
// Revision 1.12  2008/11/12 10:14:55  pascal_verdier
// Access control checked.
//
// Revision 1.11  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.10  2008/10/10 08:34:34  pascal_verdier
// Security test have been done.
//
// Revision 1.9  2008/01/10 15:40:22  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.8  2007/09/14 07:32:30  ounsy
// change protected modifier in constructor to public. It need by Web TangORB.
//
// Revision 1.7  2007/09/13 09:17:59  ounsy
// Correct two bug :
// - when the tango_host will be specified in the url (with device), it doesn't work
// - then tango_host change doesn't work
//
// Revision 1.6  2007/09/11 14:17:32  ounsy
// Correct a problem with Database when using parent method (Connection)
//
// Revision 1.5  2007/08/23 09:42:20  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
//
// Revision 3.26  2007/04/04 14:12:06  pascal_verdier
// Method get_device_attribute_list() added.
//
// Revision 3.25  2007/03/13 12:16:32  pascal_verdier
// Another bug in getServices fixed.
//
// Revision 3.24  2007/03/12 14:25:57  pascal_verdier
// Bug in getDevices fixed.
//
// Revision 3.23  2007/03/12 13:43:05  pascal_verdier
// Bug in getServices fixed.
//
// Revision 3.22  2006/11/27 16:12:14  pascal_verdier
// Methods register_service and unregister_service added.
//
// Revision 3.21  2006/11/13 08:21:47  pascal_verdier
// Constant added.
//
// Revision 3.20  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.19  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.18  2006/04/10 12:10:31  jlpons
// Added database history commands
//
// Revision 3.17  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.16  2005/08/30 07:33:44  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.15  2005/06/13 09:05:18  pascal_verdier
// Minor bugs fixed.
//
// Revision 3.14  2005/06/02 14:08:28  pascal_verdier
// *** empty log message ***
//
// Revision 3.13  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.12  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.11  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.10  2004/10/11 12:25:42  pascal_verdier
// Multi TANGO_HOST bug fixed.
//
// Revision 3.9  2004/09/23 14:00:15  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.8  2004/06/29 04:02:57  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.7  2004/06/21 12:23:35  pascal_verdier
// get_device_exported_for_class(String classname) method added
// and get_alias(String devname) bug fixed.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.2  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.1  2003/07/22 14:15:35  pascal_verdier
// DeviceData are now in-methods objects.
// Minor change for TACO-TANGO common database.
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
//
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoApi.events.DbEventImportInfo;

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
 * @version $Revision$
 */

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
	
	
	// ===================================================================
	/**
	 * Database access constructor.
	 * 
	 * @throws DevFailed
	 *             in case of environment not corectly set.
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
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
	 * @throws DevFailed
	 *             in case of host or port not available
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
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
	 * @param auto_reconnect
	 *            do not reconnect if false.
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
	 * @param devname
	 *            name of the device to be imported.
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
	 * @param devname
	 *            name of the device to be imported.
	 * @param check_access
	 *            set check_access value
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
	 * @param devname
	 *            name of the device to be imported.
	 * @param param
	 *            String parameter to import device.
	 * @param src
	 *            Source to import device (ior, dbase...)
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
	 * @param devname
	 *            name of the device to be imported.
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
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
	 */
	// ==========================================================================
	public String[] get_host_list() throws DevFailed {
		return databaseDAO.get_host_list(this);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of host registred.
	 * 
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the list of the hosts registred in TANGO database with the
	 *         specified wildcard.
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
	 * @param servname
	 *            server name and instance name (ie.: Serial/i1).
	 * @return the list of all classes registred in TANGO database for servname
	 *         except the DServer class (existing on all Tango device server).
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
	 */
	// ==========================================================================
	public String[] get_server_name_list() throws DevFailed {
		return databaseDAO.get_server_name_list(this);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of instance names registred for specified
	 * server name.
	 * 
	 * @param servname
	 *            server name.
	 * @return the list of all instance names for specified server name.
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
	 */
	// ==========================================================================
	public String[] get_server_list() throws DevFailed {
		return databaseDAO.get_server_list(this);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of servers registred in the database.
	 * 
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the list of all servers registred in TANGO database.
	 */
	// ==========================================================================
	public String[] get_server_list(String wildcard) throws DevFailed {
		return databaseDAO.get_server_list(this, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of servers registred on the specified host.
	 * 
	 * @param hostname
	 *            the specified host name.
	 * @return the list of the servers registred in TANGO database for the
	 *         specified host.
	 */
	// ==========================================================================
	public String[] get_host_server_list(String hostname) throws DevFailed {
		return databaseDAO.get_host_server_list(this, hostname);

	}

	// ==========================================================================
	/**
	 * Query the database for server information.
	 * 
	 * @param servname
	 *            The specified server name.
	 * @return The information found for the specified server in a DBServInfo
	 *         object.
	 */
	// ==========================================================================
	public DbServInfo get_server_info(String servname) throws DevFailed {
		return databaseDAO.get_server_info(this, servname);

	}

	// ==========================================================================
	/**
	 * Add/update server information in databse.
	 * 
	 * @param info
	 *            Server information for the specified server in a DbServinfo
	 *            object.
	 */
	// ==========================================================================
	public void put_server_info(DbServInfo info) throws DevFailed {
		databaseDAO.put_server_info(this, info);

	}

	// ==========================================================================
	/**
	 * Delete server information in databse.
	 * 
	 * @param servname
	 *            Server name.
	 */
	// ==========================================================================
	public void delete_server_info(String servname) throws DevFailed {
		databaseDAO.delete_server_info(this, servname);

	}

	// **************************************
	// DEVICES MANAGEMENT
	// **************************************

	// ==========================================================================
	/**
	 * Add/update a device to the database
	 * 
	 * @param devinfo
	 *            The device name, class and server specified in object.
	 */
	// ==========================================================================
	public void add_device(DbDevInfo devinfo) throws DevFailed {
		databaseDAO.add_device(this, devinfo);

	}

	// ==========================================================================
	/**
	 * Add/update a device to the database
	 * 
	 * @param devname
	 *            The device name
	 * @param classname
	 *            The class.
	 * @param servname
	 *            The server name.
	 */
	// ==========================================================================
	public void add_device(String devname, String classname, String servname) throws DevFailed {
		databaseDAO.add_device(this, devname, classname, servname);

	}

	// ==========================================================================
	/**
	 * Delete the device of the specified name from the database
	 * 
	 * @param devname
	 *            The device name.
	 */
	// ==========================================================================
	public void delete_device(String devname) throws DevFailed {
		databaseDAO.delete_device(this, devname);

	}

	// ==========================================================================
	/**
	 * Query the database for the export and more info of the specified device.
	 * 
	 * @param devname
	 *            The device name.
	 * @return the information in a DbGetDeviceInfo.
	 */
	// ==========================================================================
	public DeviceInfo get_device_info(String devname) throws DevFailed {
		return databaseDAO.get_device_info(this, devname);

	}

	// ==========================================================================
	/**
	 * Query database for list of  devices.
	 * 
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return The list of devices
	 */
	// ==========================================================================
	public String[] get_device_list(String wildcard) throws DevFailed {
		return databaseDAO.get_device_list(this, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for the export info of the specified device.
	 * 
	 * @param devname
	 *            The device name.
	 * @return the information in a DbDevImportInfo.
	 */
	// ==========================================================================
	public DbDevImportInfo import_device(String devname) throws DevFailed {
		return databaseDAO.import_device(this, devname);

	}

	// ==========================================================================
	/**
	 * Mark the specified server as unexported in the database.
	 * 
	 * @param devname
	 *            The device name.
	 */
	// ==========================================================================
	public void unexport_device(String devname) throws DevFailed {
		databaseDAO.unexport_device(this, devname);

	}

	// ==========================================================================
	/**
	 * Update the export info fort this device in the database.
	 * 
	 * @param devinfo
	 *            Device information to export.
	 */
	// ==========================================================================
	public void export_device(DbDevExportInfo devinfo) throws DevFailed {
		databaseDAO.export_device(this, devinfo);

	}

	// **************************************
	// Devices list MANAGEMENT
	// **************************************
	// ==========================================================================
	/**
	 * Query the database for server devices and classes.
	 * 
	 * @param servname
	 *            The specified server name.
	 * @return The devices and classes (e.g. "id11/motor/1", "StepperMotor",
	 *         "id11/motor/2", "StepperMotor",....)
	 */
	// ==========================================================================
	public String[] get_device_class_list(String servname) throws DevFailed {
		return databaseDAO.get_device_class_list(this, servname);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of devices served by the specified server
	 * and of the specified class.
	 * 
	 * @param servname
	 *            The server name.
	 * @param classname
	 *            The class name
	 * @return the device names are stored in an array of strings.
	 */
	// ==========================================================================
	public String[] get_device_name(String servname, String classname) throws DevFailed {
		return databaseDAO.get_device_name(this, servname, classname);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of device domain names witch match the
	 * wildcard provided.
	 * 
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the device domain are stored in an array of strings.
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
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the device family are stored in an array of strings.
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
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the device member are stored in an array of strings.
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
	 * @param servname
	 *            Server name for these devices.
	 * @param devinfo
	 *            Devices and server information.
	 */
	// ==========================================================================
	public void add_server(String servname, DbDevInfo[] devinfo) throws DevFailed {
		databaseDAO.add_server(this, servname, devinfo);

	}

	// ==========================================================================
	/**
	 * Delete the device server and its associated devices from the database.
	 * 
	 * @param devname
	 *            the device name.
	 */
	// ==========================================================================
	public void delete_server(String devname) throws DevFailed {
		databaseDAO.delete_server(this, devname);

	}

	// ==========================================================================
	/**
	 * Add a group of devices to the database.
	 * 
	 * @param devinfo
	 *            Devices and server information.
	 */
	// ==========================================================================
	public void export_server(DbDevExportInfo[] devinfo) throws DevFailed {
		databaseDAO.export_server(this, devinfo);

	}

	// ==========================================================================
	/**
	 * Mark all devices exported for this device server as unexported.
	 * 
	 * @param devname
	 *            the device name.
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
	 * properties are defiend.
	 * 
	 * @param wildcard
	 *            wildcard (* matches any charactere).
	 * @return objects for which properties are defiened list.
	 */
	// ==========================================================================
	public String[] get_object_list(String wildcard) throws DevFailed {
		return databaseDAO.get_object_list(this, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of object (ie non-device) for which
	 * properties are defiend.
	 * 
	 * @param objname
	 *            object name.
	 * @param wildcard
	 *            wildcard (* matches any charactere).
	 * @return Property names..
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
	 * @param name
	 *            Object name.
	 * @param propnames
	 *            list of property names.
	 * @return properties in DbDatum objects.
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
	 * @param name
	 *            Object name.
	 * @param propname
	 *            list of property names.
	 * @return property in DbDatum object.
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
	 * @param name
	 *            Object name.
	 * @param propname
	 *            list of property names.
	 * @return property in DbDatum object.
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
	 * @param name
	 *            Object name.
	 * @param properties
	 *            list of property DbDatum objects.
	 * @return properties in DbDatum objects.
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
	 * @param name
	 *            Object name.
	 * @param properties
	 *            Properties names and values array.
	 */
	// ==========================================================================
	public void put_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.put_property(this, name, properties);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_property(String name, String[] propnames) throws DevFailed {
		databaseDAO.delete_property(this, name, propnames);

	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param propname
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_property(String name, String propname) throws DevFailed {
		databaseDAO.delete_property(this, name, propname);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param properties
	 *            Property DbDatum objects.
	 */
	// ==========================================================================
	public void delete_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.delete_property(this, name, properties);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of class properties for the pecified
	 * object.
	 * 
	 * @param classname
	 *            device name.
	 * @param wildcard
	 *            propertie's wildcard (* matches any charactere).
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public String[] get_class_property_list(String classname, String wildcard) throws DevFailed {
		return databaseDAO.get_class_property_list(this, classname, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of device properties for the pecified
	 * object.
	 * 
	 * @param devname
	 *            device name.
	 * @param wildcard
	 *            propertie's wildcard (* matches any charactere).
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public String[] get_device_property_list(String devname, String wildcard) throws DevFailed {
		return databaseDAO.get_device_property_list(this, devname, wildcard);

	}

	// ==========================================================================
	// ==========================================================================
	public String get_class_for_device(String devname) throws DevFailed {
		return databaseDAO.get_class_for_device(this, devname);

	}

	// ==========================================================================
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
	 * @param name
	 *            device name.
	 * @param propnames
	 *            list of property names.
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public DbDatum[] get_device_property(String name, String[] propnames) throws DevFailed {
		return databaseDAO.get_device_property(this, name, propnames);

	}

	// ==========================================================================
	/**
	 * Query the database for a device property for the pecified object.
	 * 
	 * @param name
	 *            device name.
	 * @param propname
	 *            property name.
	 * @return property in DbDatum object.
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
	 * @param name
	 *            device name.
	 * @param properties
	 *            list of property DbDatum objects.
	 * @return properties in DbDatum objects.
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
	 * @param name
	 *            device name.
	 * @param properties
	 *            Properties names and values array.
	 */
	// ==========================================================================
	public void put_device_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.put_device_property(this, name, properties);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Device name.
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_device_property(String name, String[] propnames) throws DevFailed {
		databaseDAO.delete_device_property(this, name, propnames);

	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name
	 *            Device name.
	 * @param propname
	 *            Property name.
	 */
	// ==========================================================================
	public void delete_device_property(String name, String propname) throws DevFailed {
		databaseDAO.delete_device_property(this, name, propname);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Device name.
	 * @param properties
	 *            Property DbDatum objects.
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
	 * @param devname
	 *            device name.
	 * @return attribute names.
	 */
	// ==========================================================================
	public String[] get_device_attribute_list(String devname) throws DevFailed {
		return databaseDAO.get_device_attribute_list(this, devname);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of device attributes properties for the
	 * pecified object.
	 * 
	 * @param devname
	 *            device name.
	 * @param attnames
	 *            attribute names.
	 * @return properties in DbAttribute objects array.
	 */
	// ==========================================================================
	public DbAttribute[] get_device_attribute_property(String devname, String[] attnames) throws DevFailed {
		return databaseDAO.get_device_attribute_property(this, devname, attnames);

	}

	// ==========================================================================
	/**
	 * Query the database for device attribute property for the pecified object.
	 * 
	 * @param devname
	 *            device name.
	 * @param attname
	 *            attribute name.
	 * @return property in DbAttribute object.
	 */
	// ==========================================================================
	public DbAttribute get_device_attribute_property(String devname, String attname) throws DevFailed {
		return databaseDAO.get_device_attribute_property(this, devname, attname);

	}

	// ==========================================================================
	/**
	 * Insert or update a list of attribute properties for the specified device.
	 * The property names and their values are specified by the DbAttribute
	 * array.
	 * 
	 * @param devname
	 *            device name.
	 * @param attr
	 *            attribute names, and properties (names and values).
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
	 * @param devname
	 *            device name.
	 * @param attr
	 *            attribute name, and properties (names and values).
	 */
	// ==========================================================================
	public void put_device_attribute_property(String devname, DbAttribute attr) throws DevFailed {
		databaseDAO.put_device_attribute_property(this, devname, attr);

	}

	// ==========================================================================
	/**
	 * Delete an list of attributes properties for the specified object.
	 * 
	 * @param devname
	 *            Device name.
	 * @param attr
	 *            attribute name, and properties (names).
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, DbAttribute attr) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attr);

	}

	// ==========================================================================
	/**
	 * Delete a list of attributes properties for the specified object.
	 * 
	 * @param devname
	 *            Device name.
	 * @param attr
	 *            attribute names, and properties (names) in array.
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, DbAttribute[] attr) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attr);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param devname
	 *            Device name.
	 * @param attname
	 *            Attribute name.
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, String attname, String[] propnames) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attname, propnames);

	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param devname
	 *            Device name.
	 * @param attname
	 *            Attribute name.
	 * @param propname
	 *            Property name.
	 */
	// ==========================================================================
	public void delete_device_attribute_property(String devname, String attname, String propname) throws DevFailed {
		databaseDAO.delete_device_attribute_property(this, devname, attname, propname);

	}

	// ==========================================================================
	/**
	 * Delete an attribute for the specified object.
	 * 
	 * @param devname
	 *            Device name.
	 * @param attname
	 *            Attribute name.
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
	 * @param servname
	 *            server name
	 * @return the list of all servers registred in TANGO database.
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
	 * @param name
	 *            Class name.
	 * @param propnames
	 *            list of property names.
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public DbDatum[] get_class_property(String name, String[] propnames) throws DevFailed {
		return databaseDAO.get_class_property(this, name, propnames);

	}

	// ==========================================================================
	/**
	 * Query the database for a class property for the pecified object.
	 * 
	 * @param name
	 *            Class name.
	 * @param propname
	 *            list of property names.
	 * @return property in DbDatum object.
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
	 * @param name
	 *            Class name.
	 * @param properties
	 *            list of property DbDatum objects.
	 * @return properties in DbDatum objects.
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
	 * @param name
	 *            Class name.
	 * @param properties
	 *            Properties names and values array.
	 */
	// ==========================================================================
	public void put_class_property(String name, DbDatum[] properties) throws DevFailed {
		databaseDAO.put_class_property(this, name, properties);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Class name.
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_class_property(String name, String[] propnames) throws DevFailed {
		databaseDAO.delete_class_property(this, name, propnames);

	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name
	 *            Class name.
	 * @param propname
	 *            Property name.
	 */
	// ==========================================================================
	public void delete_class_property(String name, String propname) throws DevFailed {
		databaseDAO.delete_class_property(this, name, propname);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Class name.
	 * @param properties
	 *            Property DbDatum objects.
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
	 * @param classname
	 *            class name.
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return attributes list for specified class
	 */
	// ==========================================================================
	public String[] get_class_attribute_list(String classname, String wildcard) throws DevFailed {
		return databaseDAO.get_class_attribute_list(this, classname, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for a attribute properties for trhe specified class.
	 * 
	 * @param classname
	 *            class name.
	 * @param attname
	 *            attribute name
	 * @return attribute properties for specified class and attribute.
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
	 * @param classname
	 *            Class name.
	 * @param attnames
	 *            list of attribute names.
	 * @return attribute properties for specified class and attributes.
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
	 * @param classname
	 *            Class name.
	 * @param attr
	 *            DbAttribute objects containing attribute names, property names
	 *            and property values.
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
	 * @param classname
	 *            Class name.
	 * @param attr
	 *            DbAttribute object containing attribute name, property names
	 *            and property values.
	 */
	// ==========================================================================
	public void put_class_attribute_property(String classname, DbAttribute attr) throws DevFailed {
		databaseDAO.put_class_attribute_property(this, classname, attr);

	}

	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name
	 *            Class name.
	 * @param propname
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_class_attribute_property(String name, String attname, String propname) throws DevFailed {
		databaseDAO.delete_class_attribute_property(this, name, attname, propname);

	}

	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Class name.
	 * @param attname
	 *            attribute name.
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_class_attribute_property(String name, String attname, String[] propnames) throws DevFailed {
		databaseDAO.delete_class_attribute_property(this, name, attname, propnames);

	}

	// ==========================================================================
	/**
	 * Query database for list of exported devices.
	 * 
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return The list of exported devices
	 */
	// ==========================================================================
	public String[] get_device_exported(String wildcard) throws DevFailed {
		return databaseDAO.get_device_exported(this, wildcard);

	}

	// ==========================================================================
	/**
	 * Query database for list of exported devices for the specified class name.
	 * 
	 * @param classname
	 *            class name to query the exported devices.
	 * @return The list of exported devices
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
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the device aliases are stored in an array of strings.
	 */
	// ==========================================================================
	public String[] get_device_alias_list(String wildcard) throws DevFailed {
		return databaseDAO.get_device_alias_list(this, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for an alias for the specified device.
	 * 
	 * @param devname
	 *            device's name.
	 * @return the device alias found.
	 */
	// ==========================================================================
	public String get_device_alias(String devname) throws DevFailed {
		return databaseDAO.get_device_alias(this, devname);

	}

	// ==========================================================================
	/**
	 * Query the database a device for the specified alias.
	 * 
	 * @param alias
	 *            The device name.alias
	 * @return the device aliases are stored in an array of strings.
	 */
	// ==========================================================================
	public String get_alias_device(String alias) throws DevFailed {
		return databaseDAO.get_alias_device(this, alias);

	}

	// ==========================================================================
	/**
	 * Set an alias for a device name
	 * 
	 * @param devname
	 *            device name.
	 * @param aliasname
	 *            alias name.
	 */
	// ==========================================================================
	public void put_device_alias(String devname, String aliasname) throws DevFailed {
		databaseDAO.put_device_alias(this, devname, aliasname);

	}

	// ==========================================================================
	/**
	 * Query the database to delete alias for the specified device alias.
	 * 
	 * @param alias
	 *            device alias name.
	 */
	// ==========================================================================
	public void delete_device_alias(String alias) throws DevFailed {
		databaseDAO.delete_device_alias(this, alias);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of aliases for the specified wildcard.
	 * 
	 * @param wildcard
	 *            Wildcard char is '*' and matches wildvcard characters.
	 * @return the device aliases are stored in an array of strings.
	 */
	// ==========================================================================
	public String[] get_attribute_alias_list(String wildcard) throws DevFailed {
		return databaseDAO.get_attribute_alias_list(this, wildcard);

	}

	// ==========================================================================
	/**
	 * Query the database for a list of aliases for the specified attribute.
	 * 
	 * @param attname
	 *            The attribute name.
	 * @return the device aliases are stored in an array of strings.
	 */
	// ==========================================================================
	public String get_attribute_alias(String attname) throws DevFailed {
		return databaseDAO.get_attribute_alias(this, attname);

	}

	// ==========================================================================
	/**
	 * Set an alias for a attribute name
	 * 
	 * @param attname
	 *            attribute name.
	 * @param aliasname
	 *            alias name.
	 */
	// ==========================================================================
	public void put_attribute_alias(String attname, String aliasname) throws DevFailed {
		databaseDAO.put_attribute_alias(this, attname, aliasname);

	}

	// ==========================================================================
	/**
	 * Query the database to delete alias for the specified attribute alias.
	 * 
	 * @param alias
	 *            device alias name.
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
	 * @param channel_name
	 *            The event name.
	 * @return the information in a DbEventImportInfo.
	 */
	// ==========================================================================
	public DbEventImportInfo import_event(String channel_name) throws DevFailed {
		return databaseDAO.import_event(this, channel_name);

	}

	// ==========================================================================
	/**
	 * Returns the history of the specified device property.
	 * 
	 * @param devname
	 *            Device name
	 * @param propname
	 *            Property name (can be wildcarded)
	 * @throws DevFailed
	 *             in case of failure
	 */
	// ==========================================================================
	public DbHistory[] get_device_property_history(String devname, String propname) throws DevFailed {
		return databaseDAO.get_device_property_history(this, devname, propname);

	}

	// ==========================================================================
	/**
	 * Returns the history of the specified device attribute property.
	 * 
	 * @param devname
	 *            Device name
	 * @param attname
	 *            Attribute name (can be wildcarded)
	 * @param propname
	 *            Property name (can be wildcarded)
	 * @throws DevFailed
	 *             in case of failure
	 */
	// ==========================================================================
	public DbHistory[] get_device_attribute_property_history(String devname, String attname, String propname) throws DevFailed {
		return databaseDAO.get_device_attribute_property_history(this, devname, attname, propname);

	}

	// ==========================================================================
	/**
	 * Returns the history of the specified class property.
	 * 
	 * @param classname
	 *            Class name
	 * @param propname
	 *            Property name (can be wildcarded)
	 * @throws DevFailed
	 *             in case of failure
	 */
	// ==========================================================================
	public DbHistory[] get_class_property_history(String classname, String propname) throws DevFailed {
		return databaseDAO.get_class_property_history(this, classname, propname);

	}

	// ==========================================================================
	/**
	 * Returns the history of the specified class attribute property.
	 * 
	 * @param classname
	 *            Class name
	 * @param attname
	 *            Attribute name (can be wildcarded)
	 * @param propname
	 *            Property name (can be wildcarded)
	 * @throws DevFailed
	 *             in case of failure
	 */
	// ==========================================================================
	public DbHistory[] get_class_attribute_property_history(String classname, String attname, String propname) throws DevFailed {
		return databaseDAO.get_class_attribute_property_history(this, classname, attname, propname);

	}

	// ==========================================================================
	/**
	 * Returns the history of the specified object property.
	 * 
	 * @param objname
	 *            Object name
	 * @param propname
	 *            Property name (can be wildcarded)
	 * @throws DevFailed
	 *             in case of failure
	 */
	// ==========================================================================
	public DbHistory[] get_property_history(String objname, String propname) throws DevFailed {
		return databaseDAO.get_property_history(this, objname, propname);

	}

	// ===================================================================
	/**
	 * Query database for specified services.
	 * 
	 * @param servicename
	 *            The service name.
	 * @param instname
	 *            The instance name (could be * for all instances).
	 * @return The device names found for specified service and instance.
	 * @throws DevFailed
	 *             in case of failure
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
	 * @param serviceName
	 *            Service's name
	 * @param instanceName
	 *            Instance service's name
	 * @param devname
	 *            Device's name
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
	 * @param serviceName
	 *            Service's name
	 * @param instanceName
	 *            Instance service's name
	 * @param devname
	 *            Device's name
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
	 * @param devname
	 *            Specified device name.
	 * @return The Tango access controle found.
	 */
	// ===================================================================
	public int checkAccessControl(String devname) {
		return databaseDAO.checkAccessControl(this, devname);

	}

	// ===================================================================
	/**
	 * Check for specified class, the specified command is allowed.
	 * 
	 * @param classname
	 *            Specified class name.
	 * @param cmd
	 *            Specified command name.
	 */
	// ===================================================================
	public boolean isCommandAllowed(String classname, String cmd) throws DevFailed {
		return databaseDAO.isCommandAllowed(this, classname, cmd);

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
}

