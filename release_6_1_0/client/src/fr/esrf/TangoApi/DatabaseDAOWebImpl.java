//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.1  2007/07/04 12:55:33  ounsy
// creation of 3 sub modules :
// 	- client for the webtangorb classes
// 	- common for the classes used by webtangorb and the tangowebserver
// 	- server for the generic classes of tangowebserver
//
// Revision 1.3  2007/07/02 12:03:46  ounsy
// Correction for tango web access
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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;


import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.events.DbEventImportInfo;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.TangoDs.Except;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

import java.util.StringTokenizer;
import java.util.Vector;

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

public class DatabaseDAOWebImpl extends ConnectionDAOWebImpl implements IDatabaseDAO, IWebImpl {
	private Object[] classParam = null;

	/**
	 * Device proxy on access control device instance.
	 */
	private AccessProxy access_proxy = null;

	/**
	 * access rights already checked if true.
	 */
	protected boolean access_checked = false;

	public DatabaseDAOWebImpl() throws DevFailed {

	}

	// ===================================================================
	/**
	 * Database access constructor.
	 * 
	 * @throws DevFailed
	 *             in case of environment not corectly set.
	 */
	// ===================================================================
	public void init(Database database) {
		classParam = new Object[] {};
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
	public void init(Database database, String host, String port) {
		classParam = new Object[] { host, port };
	}

	// ==========================================================================
	// ==========================================================================
	public String toString(Database database) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "toString", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// ==========================================================================
	/**
	 * Convert a String array to a sting.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	// ==========================================================================
	// TODO remove javadoc
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
	public String get_info(Database database) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_info", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/**
	 * Query the database for a list of host registred.
	 * 
	 * @return the list of all hosts registred in TANGO database.
	 */
	// ==========================================================================
	public String[] get_host_list(Database database) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_host_list", new Object[] {}, new Class[] {});

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
	public String[] get_host_list(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_host_list", new Object[] { wildcard }, new Class[] { String.class });

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
	public String[] get_server_class_list(Database database, String servname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_server_class_list", new Object[] { servname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Query the database for a list of server names registred in the database.
	 * 
	 * @return the list of all server names registred in TANGO database.
	 */
	// ==========================================================================
	public String[] get_server_name_list(Database database) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_server_name_list", new Object[] {}, new Class[] {});

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
	public String[] get_instance_name_list(Database database, String servname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_instance_name_list", new Object[] { servname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Query the database for a list of servers registred in the database.
	 * 
	 * @return the list of all servers registred in TANGO database.
	 */
	// ==========================================================================
	public String[] get_server_list(Database database) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_server_list", new Object[] {}, new Class[] {});

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
	public String[] get_server_list(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_server_list", new Object[] { wildcard }, new Class[] { String.class });

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
	public String[] get_host_server_list(Database database, String hostname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_host_server_list", new Object[] { hostname }, new Class[] { String.class });

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
	public DbServInfo get_server_info(Database database, String servname) throws DevFailed {
		return (DbServInfo) WebServerClientUtil.getResponse(this, classParam, "get_server_info", new Object[] { servname }, new Class[] { String.class });

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
	public void put_server_info(Database database, DbServInfo info) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_server_info", new Object[] { info }, new Class[] { DbServInfo.class });

	}

	// ==========================================================================
	/**
	 * Delete server information in databse.
	 * 
	 * @param servname
	 *            Server name.
	 */
	// ==========================================================================
	public void delete_server_info(Database database, String servname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_server_info", new Object[] { servname }, new Class[] { String.class });

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
	public void add_device(Database database, DbDevInfo devinfo) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "add_device", new Object[] { devinfo }, new Class[] { DbDevInfo.class });

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
	public void add_device(Database database, String devname, String classname, String servname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "add_device", new Object[] { devname, classname, servname }, new Class[] { String.class, String.class, String.class });

	}

	// ==========================================================================
	/**
	 * Delete the device of the specified name from the database
	 * 
	 * @param devname
	 *            The device name.
	 */
	// ==========================================================================
	public void delete_device(Database database, String devname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device", new Object[] { devname }, new Class[] { String.class });

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
	public DeviceInfo get_device_info(Database database, String devname) throws DevFailed {
		return (DeviceInfo) WebServerClientUtil.getResponse(this, classParam, "get_device_info", new Object[] { devname }, new Class[] { String.class });

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
	public DbDevImportInfo import_device(Database database, String devname) throws DevFailed {
		return (DbDevImportInfo) WebServerClientUtil.getResponse(this, classParam, "import_device", new Object[] { devname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Mark the specified server as unexported in the database.
	 * 
	 * @param devname
	 *            The device name.
	 */
	// ==========================================================================
	public void unexport_device(Database database, String devname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "unexport_device", new Object[] { devname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Update the export info fort this device in the database.
	 * 
	 * @param devinfo
	 *            Device information to export.
	 */
	// ==========================================================================
	public void export_device(Database database, DbDevExportInfo devinfo) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "export_device", new Object[] { devinfo }, new Class[] { DbDevExportInfo.class });

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
	public String[] get_device_class_list(Database database, String servname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_class_list", new Object[] { servname }, new Class[] { String.class });

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
	public String[] get_device_name(Database database, String servname, String classname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_name", new Object[] { servname, classname }, new Class[] { String.class, String.class });

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
	public String[] get_device_domain(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_domain", new Object[] { wildcard }, new Class[] { String.class });

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
	public String[] get_device_family(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_family", new Object[] { wildcard }, new Class[] { String.class });

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
	public String[] get_device_member(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_member", new Object[] { wildcard }, new Class[] { String.class });

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
	public void add_server(Database database, String servname, DbDevInfo[] devinfo) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "add_server", new Object[] { servname, devinfo }, new Class[] { String.class, DbDevInfo[].class });

	}

	// ==========================================================================
	/**
	 * Delete the device server and its associated devices from the database.
	 * 
	 * @param devname
	 *            the device name.
	 */
	// ==========================================================================
	public void delete_server(Database database, String devname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_server", new Object[] { devname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Add a group of devices to the database.
	 * 
	 * @param devinfo
	 *            Devices and server information.
	 */
	// ==========================================================================
	public void export_server(Database database, DbDevExportInfo[] devinfo) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "export_server", new Object[] { devinfo }, new Class[] { DbDevExportInfo[].class });

	}

	// ==========================================================================
	/**
	 * Mark all devices exported for this device server as unexported.
	 * 
	 * @param devname
	 *            the device name.
	 */
	// ==========================================================================
	public void unexport_server(Database database, String devname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "unexport_server", new Object[] { devname }, new Class[] { String.class });

	}

	// **************************************
	// PROPERTIES MANAGEMENT
	// **************************************
	// ==========================================================================
	/**
	 * Convert Properties in DbDatnum array to a String array.
	 * 
	 * @param name
	 *            Object name.
	 * @param properties
	 *            Properties names and values array.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Convert a String array to a DbDatnum array for properties.
	 * 
	 * @param strprop
	 *            Properties names and values array.
	 * @return a DbDatum array specifying the properties fonud in string array.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Query the database for a list of object (ie. non-device, class or device)
	 * properties for the pecified object. The property names are specified by
	 * the DbDatum array objects.
	 * 
	 * @param name
	 *            Object name.
	 * @param type
	 *            Object type (nothing, class, device..)
	 * @param properties
	 *            list of property DbDatum objects.
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Query the database for an object (ie. non-device, class or device)
	 * property for the pecified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param type
	 *            Object type (nothing, class, device..)
	 * @param propname
	 *            lproperty name.
	 * @return property in DbDatum objects.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Query the database for a list of object (ie. non-device, class or device)
	 * properties for thr dpecified object. The property names are specified by
	 * the DbDatum array objects.
	 * 
	 * @param name
	 *            Object name.
	 * @param type
	 *            Object type (nothing, class, device..)
	 * @param propnames
	 *            list of property names.
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param type
	 *            Object type (nothing, class, device..)
	 * @param properties
	 *            list of property DbDatum objects.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Delete a property for the specified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param type
	 *            Object type (nothing, class, device..)
	 * @param propname
	 *            Property name.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/**
	 * Delete a list of properties for the specified object.
	 * 
	 * @param name
	 *            Object name.
	 * @param type
	 *            Object type (nothing, class, device..)
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	// TODO remove javadoc
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
	public String[] get_object_list(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_object_list", new Object[] { wildcard }, new Class[] { String.class });

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
	public String[] get_object_property_list(Database database, String objname, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_object_property_list", new Object[] { objname, wildcard }, new Class[] { String.class, String.class });

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
	public DbDatum[] get_property(Database database, String name, String[] propnames) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { name, propnames }, new Class[] { String.class, String[].class });

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
	public DbDatum get_property(Database database, String name, String propname) throws DevFailed {
		return (DbDatum) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { name, propname }, new Class[] { String.class, String.class });

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
	public DbDatum get_property(Database database, String name, String propname, boolean forced) throws DevFailed {
		return (DbDatum) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { name, propname, forced }, new Class[] { String.class, String.class, boolean.class });

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
	public DbDatum[] get_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public void put_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public void delete_property(Database database, String name, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_property", new Object[] { name, propnames }, new Class[] { String.class, String[].class });

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
	public void delete_property(Database database, String name, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_property", new Object[] { name, propname }, new Class[] { String.class, String.class });

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
	public void delete_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public String[] get_class_property_list(Database database, String classname, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_class_property_list", new Object[] { classname, wildcard }, new Class[] { String.class, String.class });

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
	public String[] get_device_property_list(Database database, String devname, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_property_list", new Object[] { devname, wildcard }, new Class[] { String.class, String.class });

	}

	// ==========================================================================
	// ==========================================================================
	public String get_class_for_device(Database database, String devname) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_class_for_device", new Object[] { devname }, new Class[] { String.class });

	}

	// ==========================================================================
	// ==========================================================================
	public String[] get_class_inheritance_for_device(Database database, String devname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_class_inheritance_for_device", new Object[] { devname }, new Class[] { String.class });

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
	public DbDatum[] get_device_property(Database database, String name, String[] propnames) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_device_property", new Object[] { name, propnames }, new Class[] { String.class, String[].class });

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
	public DbDatum get_device_property(Database database, String name, String propname) throws DevFailed {
		return (DbDatum) WebServerClientUtil.getResponse(this, classParam, "get_device_property", new Object[] { name, propname }, new Class[] { String.class, String.class });

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
	public DbDatum[] get_device_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_device_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public void put_device_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_device_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public void delete_device_property(Database database, String name, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_property", new Object[] { name, propnames }, new Class[] { String.class, String[].class });

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
	public void delete_device_property(Database database, String name, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_property", new Object[] { name, propname }, new Class[] { String.class, String.class });

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
	public void delete_device_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public String[] get_device_attribute_list(Database database, String devname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_attribute_list", new Object[] { devname }, new Class[] { String.class });

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
	public DbAttribute[] get_device_attribute_property(Database database, String devname, String[] attnames) throws DevFailed {
		return (DbAttribute[]) WebServerClientUtil.getResponse(this, classParam, "get_device_attribute_property", new Object[] { devname, attnames }, new Class[] { String.class, String[].class });

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
	public DbAttribute get_device_attribute_property(Database database, String devname, String attname) throws DevFailed {
		return (DbAttribute) WebServerClientUtil.getResponse(this, classParam, "get_device_attribute_property", new Object[] { devname, attname }, new Class[] { String.class, String.class });

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
	public void put_device_attribute_property(Database database, String devname, DbAttribute[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_device_attribute_property", new Object[] { devname, attr }, new Class[] { String.class, DbAttribute[].class });

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
	public void put_device_attribute_property(Database database, String devname, DbAttribute attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_device_attribute_property", new Object[] { devname, attr }, new Class[] { String.class, DbAttribute.class });

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
	public void delete_device_attribute_property(Database database, String devname, DbAttribute attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_attribute_property", new Object[] { devname, attr }, new Class[] { String.class, DbAttribute.class });

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
	public void delete_device_attribute_property(Database database, String devname, DbAttribute[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_attribute_property", new Object[] { devname, attr }, new Class[] { String.class, DbAttribute[].class });

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
	public void delete_device_attribute_property(Database database, String devname, String attname, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_attribute_property", new Object[] { devname, attname, propnames }, new Class[] { String.class, String.class, String[].class });

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
	public void delete_device_attribute_property(Database database, String devname, String attname, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_attribute_property", new Object[] { devname, attname, propname }, new Class[] { String.class, String.class, String.class });

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
	public void delete_device_attribute(Database database, String devname, String attname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_attribute", new Object[] { devname, attname }, new Class[] { String.class, String.class });

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
	public String[] get_class_list(Database database, String servname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_class_list", new Object[] { servname }, new Class[] { String.class });

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
	public DbDatum[] get_class_property(Database database, String name, String[] propnames) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_class_property", new Object[] { name, propnames }, new Class[] { String.class, String[].class });

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
	public DbDatum get_class_property(Database database, String name, String propname) throws DevFailed {
		return (DbDatum) WebServerClientUtil.getResponse(this, classParam, "get_class_property", new Object[] { name, propname }, new Class[] { String.class, String.class });

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
	public DbDatum[] get_class_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_class_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public void put_class_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_class_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public void delete_class_property(Database database, String name, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_class_property", new Object[] { name, propnames }, new Class[] { String.class, String[].class });

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
	public void delete_class_property(Database database, String name, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_class_property", new Object[] { name, propname }, new Class[] { String.class, String.class });

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
	public void delete_class_property(Database database, String name, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_class_property", new Object[] { name, properties }, new Class[] { String.class, DbDatum[].class });

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
	public String[] get_class_attribute_list(Database database, String classname, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_class_attribute_list", new Object[] { classname, wildcard }, new Class[] { String.class, String.class });

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
	public DbAttribute get_class_attribute_property(Database database, String classname, String attname) throws DevFailed {
		return (DbAttribute) WebServerClientUtil.getResponse(this, classParam, "get_class_attribute_property", new Object[] { classname, attname }, new Class[] { String.class, String.class });

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
	public DbAttribute[] get_class_attribute_property(Database database, String classname, String[] attnames) throws DevFailed {
		return (DbAttribute[]) WebServerClientUtil.getResponse(this, classParam, "get_class_attribute_property", new Object[] { classname, attnames }, new Class[] { String.class, String[].class });

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
	public void put_class_attribute_property(Database database, String classname, DbAttribute[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_class_attribute_property", new Object[] { classname, attr }, new Class[] { String.class, DbAttribute[].class });

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
	public void put_class_attribute_property(Database database, String classname, DbAttribute attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_class_attribute_property", new Object[] { classname, attr }, new Class[] { String.class, DbAttribute.class });

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
	public void delete_class_attribute_property(Database database, String name, String attname, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_class_attribute_property", new Object[] { name, attname, propname }, new Class[] { String.class, String.class, String.class });

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
	public void delete_class_attribute_property(Database database, String name, String attname, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_class_attribute_property", new Object[] { name, attname, propnames }, new Class[] { String.class, String.class, String[].class });

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
	public String[] get_device_exported(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_exported", new Object[] { wildcard }, new Class[] { String.class });

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
	public String[] get_device_exported_for_class(Database database, String classname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_exported_for_class", new Object[] { classname }, new Class[] { String.class });

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
	public String[] get_device_alias_list(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_device_alias_list", new Object[] { wildcard }, new Class[] { String.class });

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
	public String get_device_alias(Database database, String devname) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_device_alias", new Object[] { devname }, new Class[] { String.class });

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
	public String get_alias_device(Database database, String alias) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_alias_device", new Object[] { alias }, new Class[] { String.class });

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
	public void put_device_alias(Database database, String devname, String aliasname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_device_alias", new Object[] { devname, aliasname }, new Class[] { String.class, String.class });

	}

	// ==========================================================================
	/**
	 * Query the database to delete alias for the specified device alias.
	 * 
	 * @param alias
	 *            device alias name.
	 */
	// ==========================================================================
	public void delete_device_alias(Database database, String alias) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device_alias", new Object[] { alias }, new Class[] { String.class });

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
	public String[] get_attribute_alias_list(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_alias_list", new Object[] { wildcard }, new Class[] { String.class });

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
	public String get_attribute_alias(Database database, String attname) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_attribute_alias", new Object[] { attname }, new Class[] { String.class });

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
	public void put_attribute_alias(Database database, String attname, String aliasname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_attribute_alias", new Object[] { attname, aliasname }, new Class[] { String.class, String.class });

	}

	// ==========================================================================
	/**
	 * Query the database to delete alias for the specified attribute alias.
	 * 
	 * @param alias
	 *            device alias name.
	 */
	// ==========================================================================
	public void delete_attribute_alias(Database database, String alias) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_attribute_alias", new Object[] { alias }, new Class[] { String.class });

	}

	// ==========================================================================
	// ==========================================================================
	public String[] getDevices(Database database, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "getDevices", new Object[] { wildcard }, new Class[] { String.class });

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
	public DbEventImportInfo import_event(Database database, String channel_name) throws DevFailed {
		return (DbEventImportInfo) WebServerClientUtil.getResponse(this, classParam, "import_event", new Object[] { channel_name }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Convert the result of a DbGet...PropertyHist command.
	 */
	// ==========================================================================
	// TODO remove javadoc
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
	public DbHistory[] get_device_property_history(Database database, String devname, String propname) throws DevFailed {
		return (DbHistory[]) WebServerClientUtil.getResponse(this, classParam, "get_device_property_history", new Object[] { devname, propname }, new Class[] { String.class, String.class });

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
	public DbHistory[] get_device_attribute_property_history(Database database, String devname, String attname, String propname) throws DevFailed {
		return (DbHistory[]) WebServerClientUtil.getResponse(this, classParam, "get_device_attribute_property_history", new Object[] { devname, attname, propname }, new Class[] { String.class,
				String.class, String.class });

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
	public DbHistory[] get_class_property_history(Database database, String classname, String propname) throws DevFailed {
		return (DbHistory[]) WebServerClientUtil.getResponse(this, classParam, "get_class_property_history", new Object[] { classname, propname }, new Class[] { String.class, String.class });

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
	public DbHistory[] get_class_attribute_property_history(Database database, String classname, String attname, String propname) throws DevFailed {
		return (DbHistory[]) WebServerClientUtil.getResponse(this, classParam, "get_class_attribute_property_history", new Object[] { classname, attname, propname }, new Class[] { String.class,
				String.class, String.class });

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
	public DbHistory[] get_property_history(Database database, String objname, String propname) throws DevFailed {
		return (DbHistory[]) WebServerClientUtil.getResponse(this, classParam, "get_property_history", new Object[] { objname, propname }, new Class[] { String.class, String.class });

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
	public String[] getServices(Database database, String servicename, String instname) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "getServices", new Object[] { servicename, instname }, new Class[] { String.class, String.class });

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
	public void registerService(Database database, String serviceName, String instanceName, String devname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "registerService", new Object[] { serviceName, instanceName, devname }, new Class[] { String.class, String.class, String.class });

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
	public void unregisterService(Database database, String serviceName, String instanceName, String devname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "unregisterService", new Object[] { serviceName, instanceName, devname }, new Class[] { String.class, String.class, String.class });

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
	 * @returns The Tango access controle found.
	 */
	// ===================================================================
	public int checkAccessControl(Database database, String devname) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "checkAccessControl", new Object[] { devname }, new Class[] { String.class });
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	DevFailed access_devfailed = null;

	// ===================================================================
	/**
	 * Check for specified device, the specified command is allowed.
	 * 
	 * @param devname
	 *            Specified device name.
	 * @param cmd
	 *            Specified command name.
	 */
	// ===================================================================
	public boolean isCommandAllowed(Database database, String devname, String cmd) throws DevFailed {
		return (Boolean) WebServerClientUtil.getResponse(this, classParam, "isCommandAllowed", new Object[] { devname, cmd }, new Class[] { String.class, String.class });

	}
	// ===================================================================
	// ===================================================================

	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}
}
