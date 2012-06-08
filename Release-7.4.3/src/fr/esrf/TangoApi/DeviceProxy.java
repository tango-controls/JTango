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
// Revision 1.17  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.16  2008/12/05 15:30:53  pascal_verdier
// Add methods on EventQueue management for sepecific events.
//
// Revision 1.15  2008/12/03 13:05:38  pascal_verdier
// EventQueue management added.
//
// Revision 1.14  2008/11/12 10:14:55  pascal_verdier
// Access control checked.
//
// Revision 1.13  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.12  2008/09/12 11:20:52  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.11  2008/04/16 13:01:35  pascal_verdier
// Event subscribtion stateless added.
//
// Revision 1.10  2008/01/14 09:44:13  pascal_verdier
// 1.8 revision log message changed to an english one.
// (without french char !)
//
// Revision 1.9  2008/01/10 15:40:22  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.8  2007/11/15 09:46:47  ounsy
// DeviceProxy :
// - methods get_device and main are back
//
// Revision 1.7  2007/08/23 12:04:15  ounsy
// remove not used dependencies
//
// Revision 1.6  2007/08/23 09:56:33  ounsy
// add last modif : Methods use_db() and get_db_obj() added.
//
// Revision 1.5  2007/08/23 09:42:21  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
//
// Revision 3.30  2007/02/15 16:12:42  pascal_verdier
// Catch for runtime exception added in read_attribute_history
// and read_command_history  methods.
//
// Revision 3.29  2006/11/27 16:11:12  pascal_verdier
// Bug in reconnection fixed for org.omg.CORBA.OBJECT_NOT_EXIST exception.
// Bug in reconnection fixed for write_attribute() and status() call.
//
// Revision 3.28  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.27  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.26  2006/03/10 08:23:16  pascal_verdier
// Exception catched and converted to DevFailed in
// DeviceProxy.subscribe_event() andDeviceProxy.unsubscribe_event()
//
// Revision 3.25  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.24  2005/11/29 05:34:55  pascal_verdier
// TACO info added.
//
// Revision 3.23  2005/10/10 14:09:57  pascal_verdier
// set_source and get_source methods added for Taco device mapping.
// Compatibility with Taco-1.5.jar.
//
// Revision 3.22  2005/08/30 07:33:44  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.21  2005/08/10 08:13:30  pascal_verdier
// The object returned by get_adm_dev() is the same object
// for all devices of a server (do not re-create).
//
// Revision 3.20  2005/04/26 13:27:09  pascal_verdier
// unexport added for command line.
//
// Revision 3.19  2005/03/01 08:21:45  pascal_verdier
// Attribute name set to lower case in subscribe event.
//
// Revision 3.18  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.17  2005/02/10 14:07:33  pascal_verdier
// Build connection before get_attribute_list() if not already done.
//
// Revision 3.16  2005/01/19 10:17:00  pascal_verdier
// Convert NamedDevFailedList to DevFailed for single write_attribute().
//
// Revision 3.15  2005/01/06 10:16:39  pascal_verdier
// device_2 create a AttributeIfoExt on get_attribute_info_ex().
//
// Revision 3.14  2004/12/16 10:16:44  pascal_verdier
// Missing TANGO 5 features added.
//
// Revision 3.13  2004/12/07 14:29:30  pascal_verdier
// NonDbDevice exception management added.
//
// Revision 3.12  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.11  2004/11/30 13:06:19  pascal_verdier
// get_adm_dev() method added..
//
// Revision 3.10  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.9  2004/09/23 14:00:15  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.8  2004/09/17 07:57:03  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.7  2004/06/29 04:02:57  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.3  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.2  2003/09/08 11:02:34  pascal_verdier
// *** empty log message ***
//
// Revision 3.1  2003/05/19 13:35:14  pascal_verdier
// Bug on transparency reconnection fixed.
// input argument modified for add_logging_target method.
//
// Revision 3.0  2003/04/29 08:03:26  pascal_verdier
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

import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoApi.events.EventData;
import fr.esrf.TangoApi.events.EventQueue;
import fr.esrf.TangoApi.events.DbEventImportInfo;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.Request;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 *
 * <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> String status; <Br>
 * DeviceProxy dev = ApiUtil.getDeviceProxy("sys/steppermotor/1"); <Br>
 * try { <Br>
 * <ul>
 * DeviceData data = dev.command_inout("DevStatus"); <Br>
 * status = data.extractString(); <Br>
 * </ul> } <Br>
 * catch (DevFailed e) { <Br>
 * <ul>
 * status = "Unknown status"; <Br>
 * Except.print_exception(e); <Br>
 * </ul> } <Br>
 * </ul>
 * </i>
 *
 * @author verdier
 * @version $Revision$
 */

public class DeviceProxy extends Connection implements ApiDefs  {

	private IDeviceProxyDAO deviceProxy = null;

	static final private boolean	check_idl = false;

	/**
	 *	DbDevice object to make an agregat..
	 */
	private DbDevice	db_dev;

	private String		full_class_name;

	/**
	 *	Instance on administration device
	 */
	private	DeviceProxy	adm_dev = null;

	private String[]	attnames_array = null;

	/**
	 *	Lock device counter for this object instance
	 */
	protected int		proxy_lock_cnt = 0;
	/**
	 *	Event queue instance
	 */
	protected EventQueue	event_queue;
	
	private DbEventImportInfo	evt_import_info = null;
	// ===================================================================
	/**
	 * Default DeviceProxy constructor. It will do nothing
	 */
	// ===================================================================
	public DeviceProxy() throws DevFailed {
		super();
		deviceProxy = TangoFactory.getSingleton().getDeviceProxyDAO();
		deviceProxy.init(this);
		DeviceProxyFactory.add(this);
	}

	// ===================================================================
	/**
	 * DeviceProxy constructor. It will import the device.
	 *
	 * @param info exported info of the device to be imported.
	 */
	// ===================================================================
	public DeviceProxy(DbDevImportInfo info) throws DevFailed {
		super(info);
		deviceProxy = TangoFactory.getSingleton().getDeviceProxyDAO();
		deviceProxy.init(this, info.name);
		//System.out.println("=========== " + devname + " created ============");
		DeviceProxyFactory.add(this);
	}

	// ===================================================================
	/**
	 * DeviceProxy constructor. It will import the device.
	 *
	 * @param devname
	 *            name of the device to be imported.
	 */
	// ===================================================================
	public DeviceProxy(String devname) throws DevFailed {
		super(devname);
		deviceProxy = TangoFactory.getSingleton().getDeviceProxyDAO();
		deviceProxy.init(this, devname);
		//System.out.println("=========== " + devname + " created ============");
		DeviceProxyFactory.add(this);
	}

	// ===================================================================
	/**
	 * DeviceProxy constructor. It will import the device.
	 *
	 * @param devname
	 *            name of the device to be imported.
	 * @param check_access
	 *            set check_access value
	 */
	// ===================================================================
	DeviceProxy(String devname, boolean check_access) throws DevFailed {
		super(devname, check_access);
		deviceProxy = TangoFactory.getSingleton().getDeviceProxyDAO();
		deviceProxy.init(this, devname, check_access);
		DeviceProxyFactory.add(this);
	}

	// ===================================================================
	/**
	 * TangoDevice constructor. It will import the device.
	 *
	 * @param devname
	 *            name of the device to be imported.
	 * @param ior
	 *            ior string used to import device
	 */
	// ===================================================================
	public DeviceProxy(String devname, String ior) throws DevFailed {
		super(devname, ior);
		deviceProxy = TangoFactory.getSingleton().getDeviceProxyDAO();
		deviceProxy.init(this, devname, ior);
		DeviceProxyFactory.add(this);
	}

	// ===================================================================
	/**
	 * TangoDevice constructor. It will import the device.
	 *
	 * @param devname
	 *            name of the device to be imported.
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
	 */
	// ===================================================================
	public DeviceProxy(String devname, String host, String port) throws DevFailed {
		super(devname, host, port);
		deviceProxy = TangoFactory.getSingleton().getDeviceProxyDAO();
		deviceProxy.init(this, devname, host, host);
		DeviceProxyFactory.add(this);
	}

	public boolean use_db()
	{
		return deviceProxy.use_db(this);
	}

	//==========================================================================
	//==========================================================================
	public Database get_db_obj() throws DevFailed
	{
		return deviceProxy.get_db_obj(this);
	}

	// ===================================================================
	/**
	 * Get connection on administration device.
	 */
	// ===================================================================
	protected void import_admin_device(DbDevImportInfo info) throws DevFailed {
		deviceProxy.import_admin_device(this, info);
	}

	// ===================================================================
	/**
	 * Get connection on administration device.
	 */
	// ===================================================================
	protected void import_admin_device(String origin) throws DevFailed {
		deviceProxy.import_admin_device(this, origin);
	}

	// ===========================================================
	/**
	 * return the device name.
	 */
	// ===========================================================
	public String name() {
		return deviceProxy.name(this);
	}

	// ===========================================================
	/**
	 * return the device status read from CORBA attribute.
	 */
	// ===========================================================
	public String status() throws DevFailed {
		return deviceProxy.status(this);
	}

	// ===========================================================
	/**
	 * return the device status.
	 *
	 * @param src
	 *            Source to read status. It could be ApiDefs.FROM_CMD to read it
	 *            from a command_inout or ApiDefs.FROM_ATTR to read it from
	 *            CORBA attribute.
	 */
	// ===========================================================
	public String status(boolean src) throws DevFailed {
		return deviceProxy.status(this, src);
	}

	// ===========================================================
	/**
	 * return the device state read from CORBA attribute.
	 */
	// ===========================================================
	public DevState state() throws DevFailed {
		return deviceProxy.state(this);
	}

	// ===========================================================
	/**
	 * return the device state.
	 *
	 * @param src
	 *            Source to read state. It could be ApiDefs.FROM_CMD to read it
	 *            from a command_inout or ApiDefs.FROM_ATTR to read it from
	 *            CORBA attribute.
	 */
	// ===========================================================
	public DevState state(boolean src) throws DevFailed {
		return deviceProxy.state(this, src);
	}

	// ===========================================================
	/**
	 * return the IDL device command_query for the specified command.
	 *
	 * @param cmdname
	 *            command name to be used for the command_query
	 * @return the command information..
	 */
	// ===========================================================
	public CommandInfo command_query(String cmdname) throws DevFailed {
		return deviceProxy.command_query(this, cmdname);
	}

	// ===========================================================
	// The following methods are an agrega of DbDevice
	// ===========================================================
	// ==========================================================================
	/**
	 * Returns the class for the device
	 */
	// ==========================================================================
	public String get_class() throws DevFailed {
		return deviceProxy.get_class(this);
	}

	// ==========================================================================
	/**
	 * Returns the class inheritance for the device
	 * <ul>
	 * <li> [0] Device Class
	 * <li> [1] Class from the device class is inherited.
	 * <li> .....And so on
	 * </ul>
	 */
	// ==========================================================================
	public String[] get_class_inheritance() throws DevFailed {
		return deviceProxy.get_class_inheritance(this);
	}

	// ==========================================================================
	/**
	 * Set an alias for a device name
	 *
	 * @param aliasname
	 *            alias name.
	 */
	// ==========================================================================
	public void put_alias(String aliasname) throws DevFailed {
		deviceProxy.put_alias(this, aliasname);
	}

	// ==========================================================================
	/**
	 * Get alias for the device
	 *
	 * @return the alias name if found.
	 */
	// ==========================================================================
	public String get_alias() throws DevFailed {
		return deviceProxy.get_alias(this);
	}

	// ==========================================================================
	/**
	 * Query the database for the info of this device.
	 *
	 * @return the information in a DeviceInfo.
	 */
	// ==========================================================================
	public DeviceInfo get_info() throws DevFailed {
		return deviceProxy.get_info(this);
	}

	// ==========================================================================
	/**
	 * Query the database for the export info of this device.
	 *
	 * @return the information in a DbDevImportInfo.
	 */
	// ==========================================================================
	public DbDevImportInfo import_device() throws DevFailed {
		return deviceProxy.import_device(this);
	}

	// ==========================================================================
	/**
	 * Update the export info for this device in the database.
	 *
	 * @param devinfo
	 *            Device information to export.
	 */
	// ==========================================================================
	public void export_device(DbDevExportInfo devinfo) throws DevFailed {
		deviceProxy.export_device(this, devinfo);
	}

	// ==========================================================================
	/**
	 * Unexport the device in database.
	 */
	// ==========================================================================
	public void unexport_device() throws DevFailed {
		deviceProxy.unexport_device(this);
	}

	// ==========================================================================
	/**
	 * Add/update this device to the database
	 *
	 * @param devinfo
	 *            The device name, class and server specified in object.
	 */
	// ==========================================================================
	public void add_device(DbDevInfo devinfo) throws DevFailed {
		deviceProxy.add_device(this, devinfo);
	}

	// ==========================================================================
	/**
	 * Delete this device from the database
	 */
	// ==========================================================================
	public void delete_device() throws DevFailed {
		deviceProxy.delete_device(this);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device properties for the pecified
	 * object.
	 *
	 * @param wildcard
	 *            propertie's wildcard (* matches any charactere).
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public String[] get_property_list(String wildcard) throws DevFailed {
		return deviceProxy.get_property_list(this, wildcard);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device properties for this device.
	 *
	 * @param propnames
	 *            list of property names.
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public DbDatum[] get_property(String[] propnames) throws DevFailed {
		return deviceProxy.get_property(this, propnames);
	}

	// ==========================================================================
	/**
	 * Query the database for a device property for this device.
	 *
	 * @param propname
	 *            property name.
	 * @return property in DbDatum objects.
	 */
	// ==========================================================================
	public DbDatum get_property(String propname) throws DevFailed {
		return deviceProxy.get_property(this, propname);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device properties for this device. The
	 * property names are specified by the DbDatum array objects.
	 *
	 * @param properties
	 *            list of property DbDatum objects.
	 * @return properties in DbDatum objects.
	 */
	// ==========================================================================
	public DbDatum[] get_property(DbDatum[] properties) throws DevFailed {
		return deviceProxy.get_property(this, properties);
	}

	// ==========================================================================
	/**
	 * Insert or update a property for this device The property name and its
	 * value are specified by the DbDatum
	 *
	 * @param prop
	 *            Property name and value
	 */
	// ==========================================================================
	public void put_property(DbDatum prop) throws DevFailed {
		deviceProxy.put_property(this, prop);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of properties for this device The property names
	 * and their values are specified by the DbDatum array.
	 *
	 * @param properties
	 *            Properties names and values array.
	 */
	// ==========================================================================
	public void put_property(DbDatum[] properties) throws DevFailed {
		deviceProxy.put_property(this, properties);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for this device.
	 *
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_property(String[] propnames) throws DevFailed {
		deviceProxy.delete_property(this, propnames);
	}

	// ==========================================================================
	/**
	 * Delete a property for this device.
	 *
	 * @param propname
	 *            Property name.
	 */
	// ==========================================================================
	public void delete_property(String propname) throws DevFailed {
		deviceProxy.delete_property(this, propname);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for this device.
	 *
	 * @param properties
	 *            Property DbDatum objects.
	 */
	// ==========================================================================
	public void delete_property(DbDatum[] properties) throws DevFailed {
		deviceProxy.delete_property(this, properties);
	}

	// ============================================
	// ATTRIBUTE PROPERTY MANAGEMENT
	// ============================================

	// ==========================================================================
	/**
	 * Query the device for attribute config and returns names only.
	 *
	 * @return attributes list for specified device
	 */
	// ==========================================================================
	public String[] get_attribute_list() throws DevFailed {
		return deviceProxy.get_attribute_list(this);
	}

	// ==========================================================================
	/**
	 * Insert or update a list of attribute properties for this device. The
	 * property names and their values are specified by the DbAttribute array.
	 *
	 * @param attr
	 *            attribute names and properties (names and values) array.
	 */
	// ==========================================================================
	public void put_attribute_property(DbAttribute[] attr) throws DevFailed {
		deviceProxy.put_attribute_property(this, attr);
	}

	// ==========================================================================
	/**
	 * Insert or update an attribute properties for this device. The property
	 * names and their values are specified by the DbAttribute array.
	 *
	 * @param attr
	 *            attribute name and properties (names and values).
	 */
	// ==========================================================================
	public void put_attribute_property(DbAttribute attr) throws DevFailed {
		deviceProxy.put_attribute_property(this, attr);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for this object.
	 *
	 * @param attname
	 *            attribute name.
	 * @param propnames
	 *            Property names.
	 */
	// ==========================================================================
	public void delete_attribute_property(String attname, String[] propnames) throws DevFailed {
		deviceProxy.delete_attribute_property(this, attname, propnames);
	}

	// ==========================================================================
	/**
	 * Delete a property for this object.
	 *
	 * @param attname
	 *            attribute name.
	 * @param propname
	 *            Property name.
	 */
	// ==========================================================================
	public void delete_attribute_property(String attname, String propname) throws DevFailed {
		deviceProxy.delete_attribute_property(this, attname, propname);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for this object.
	 *
	 * @param	attr DbAttribute object for specified attribute.
	 */
	// ==========================================================================
	public void delete_attribute_property(DbAttribute attr) throws DevFailed {
		deviceProxy.delete_attribute_property(this, attr);
	}

	// ==========================================================================
	/**
	 * Delete a list of properties for this object.
	 *
	 * @param attr DbAttribute array object for specified attribute.
	 */
	// ==========================================================================
	public void delete_attribute_property(DbAttribute[] attr) throws DevFailed {
		deviceProxy.delete_attribute_property(this, attr);
	}

	// ==========================================================================
	/**
	 * Query the database for a list of device attribute properties for this
	 * device.
	 *
	 * @param attnames
	 *            list of attribute names.
	 * @return properties in DbAttribute objects array.
	 */
	// ==========================================================================
	public DbAttribute[] get_attribute_property(String[] attnames) throws DevFailed {
		return deviceProxy.get_attribute_property(this, attnames);
	}

	// ==========================================================================
	/**
	 * Query the database for a device attribute property for this device.
	 *
	 * @param attname
	 *            attribute name.
	 * @return property in DbAttribute object.
	 */
	// ==========================================================================
	public DbAttribute get_attribute_property(String attname) throws DevFailed {
		return deviceProxy.get_attribute_property(this, attname);
	}

	// ==========================================================================
	/**
	 * Delete an attribute for this object.
	 *
	 * @param attname
	 *            attribute name.
	 */
	// ==========================================================================
	public void delete_attribute(String attname) throws DevFailed {
		deviceProxy.delete_attribute(this, attname);
	}

	// ===========================================================
	// Attribute Methods
	// ===========================================================
	// ==========================================================================
	/**
	 * Get the attributes configuration for the specified device.
	 *
	 * @param attnames
	 *            attribute names to request config.
	 * @return the attributes configuration.
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_info(String[] attnames) throws DevFailed {
		return deviceProxy.get_attribute_info(this, attnames);
	}

	// ==========================================================================
	/**
	 * Get the extended attributes configuration for the specified device.
	 *
	 * @param attnames
	 *            attribute names to request config.
	 * @return the attributes configuration.
	 */
	// ==========================================================================
	public AttributeInfoEx[] get_attribute_info_ex(String[] attnames) throws DevFailed {
		return deviceProxy.get_attribute_info_ex(this, attnames);
	}

	// ==========================================================================
	/**
	 * Get the attributes configuration for the specified device.
	 *
	 * @param attnames
	 *            attribute names to request config.
	 * @return the attributes configuration.
	 * @deprecated use get_attribute_info(String[] attnames)
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_config(String[] attnames) throws DevFailed {
		return deviceProxy.get_attribute_info(this, attnames);
	}

	// ==========================================================================
	/**
	 * Get the attribute info for the specified device.
	 *
	 * @param attname
	 *            attribute name to request config.
	 * @return the attribute info.
	 */
	// ==========================================================================
	public AttributeInfo get_attribute_info(String attname) throws DevFailed {
		return deviceProxy.get_attribute_info(this, attname);
	}

	// ==========================================================================
	/**
	 * Get the attribute info for the specified device.
	 *
	 * @param attname
	 *            attribute name to request config.
	 * @return the attribute info.
	 */
	// ==========================================================================
	public AttributeInfoEx get_attribute_info_ex(String attname) throws DevFailed {
		return deviceProxy.get_attribute_info_ex(this, attname);
	}

	// ==========================================================================
	/**
	 * Get the attribute configuration for the specified device.
	 *
	 * @param attname
	 *            attribute name to request config.
	 * @return the attribute configuration.
	 * @deprecated use get_attribute_info(String attname)
	 */
	// ==========================================================================
	public AttributeInfo get_attribute_config(String attname) throws DevFailed {
		return deviceProxy.get_attribute_info(this, attname);
	}

	// ==========================================================================
	/**
	 * Get all attributes info for the specified device.
	 *
	 * @return all attributes info.
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_info() throws DevFailed {
		return deviceProxy.get_attribute_info(this);
	}

	// ==========================================================================
	/**
	 * Get all attributes info for the specified device.
	 *
	 * @return all attributes info.
	 */
	// ==========================================================================
	public AttributeInfoEx[] get_attribute_info_ex() throws DevFailed {
		return deviceProxy.get_attribute_info_ex(this);
	}

	// ==========================================================================
	/**
	 * Get all attributes configuration for the specified device.
	 *
	 * @return all attributes configuration.
	 * @deprecated use get_attribute_info()
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_config() throws DevFailed {
		return deviceProxy.get_attribute_info(this);
	}

	// ==========================================================================
	/**
	 * Update the attributes info for the specified device.
	 *
	 * @param attr
	 *            the attributes info.
	 */
	// ==========================================================================
	public void set_attribute_info(AttributeInfo[] attr) throws DevFailed {
		deviceProxy.set_attribute_info(this, attr);
	}

	// ==========================================================================
	/**
	 * Update the attributes extended info for the specified device.
	 *
	 * @param attr
	 *            the attributes info.
	 */
	// ==========================================================================
	public void set_attribute_info(AttributeInfoEx[] attr) throws DevFailed {
		deviceProxy.set_attribute_info(this, attr);

	}

	// ==========================================================================
	/**
	 * Update the attributes configuration for the specified device.
	 *
	 * @param attr
	 *            the attributes configuration.
	 * @deprecated use set_attribute_info(AttributeInfo[] attr)
	 */
	// ==========================================================================
	public void set_attribute_config(AttributeInfo[] attr) throws DevFailed {
		deviceProxy.set_attribute_info(this, attr);
	}

	// ==========================================================================
	/**
	 * Read the attribute value for the specified device.
	 *
	 * @param attname
	 *            attribute name to request value.
	 * @return the attribute value.
	 */
	// ==========================================================================
	public DeviceAttribute read_attribute(String attname) throws DevFailed {
		return deviceProxy.read_attribute(this, attname);
	}

	// ==========================================================================
	/**
	 * return directly AttributeValue object without creation of DeviceAttribute
	 * object
	 */
	// ==========================================================================
	// used only by read_attribute_value() to do not re-create it every time.
	public AttributeValue read_attribute_value(String attname) throws DevFailed {
		return deviceProxy.read_attribute_value(this, attname);
	}

	// ==========================================================================
	/**
	 * Read the attribute values for the specified device.
	 *
	 * @param attnames
	 *            attribute names to request values.
	 * @return the attribute values.
	 */
	// ==========================================================================
	public DeviceAttribute[] read_attribute(String[] attnames) throws DevFailed {
		return deviceProxy.read_attribute(this, attnames);
	}

	// ==========================================================================
	/**
	 * Write the attribute value for the specified device.
	 *
	 * @param devattr
	 *            attribute name and value.
	 */
	// ==========================================================================
	public void write_attribute(DeviceAttribute devattr) throws DevFailed {
		deviceProxy.write_attribute(this, devattr);
	}

	// ==========================================================================
	/**
	 * Write the attribute values for the specified device.
	 *
	 * @param devattr
	 *            attribute names and values.
	 */
	// ==========================================================================
	public void write_attribute(DeviceAttribute[] devattr) throws DevFailed {
		deviceProxy.write_attribute(this, devattr);
	}

	// ==========================================================================
	/**
	 *	Write and then read the attribute values, for the specified device.
	 *
	 * @param devattr
	 *            attribute names and values.
	 */
	// ==========================================================================
	public DeviceAttribute write_read_attribute(DeviceAttribute devattr) throws DevFailed {
		return deviceProxy.write_read_attribute(this,
					new DeviceAttribute[] {devattr})[0];
	}

	// ==========================================================================
	/**
	 *	Write and then read the attribute values, for the specified device.
	 *
	 * @param devattr
	 *            attribute names and values.
	 */
	// ==========================================================================
	public DeviceAttribute[] write_read_attribute(DeviceAttribute[] devattr) throws DevFailed {
		return deviceProxy.write_read_attribute(this, devattr);
	}

	// ==========================================================================
	// ==========================================================================
	public DeviceProxy get_adm_dev() throws DevFailed {
		return deviceProxy.get_adm_dev(this);
	}

	// ==========================================================================
	/**
	 * Polling commands.
	 */

	// ==========================================================================
	/**
	 * Add a command to be polled for the device. If already polled, update its
	 * polling period.
	 *
	 * @param cmdname
	 *            command name to be polled.
	 * @param period
	 *            polling period.
	 */
	// ==========================================================================
	public void poll_command(String cmdname, int period) throws DevFailed {
		deviceProxy.poll_command(this, cmdname, period);
	}

	// ==========================================================================
	/**
	 * Add a attribute to be polled for the device. If already polled, update
	 * its polling period.
	 *
	 * @param attname
	 *            attribute name to be polled.
	 * @param period
	 *            polling period.
	 */
	// ==========================================================================
	public void poll_attribute(String attname, int period) throws DevFailed {
		deviceProxy.poll_attribute(this, attname, period);
	}

	// ==========================================================================
	/**
	 * Remove command of polled object list
	 *
	 * @param cmdname
	 *            command name to be removed of polled object list.
	 */
	// ==========================================================================
	public void stop_poll_command(String cmdname) throws DevFailed {
		deviceProxy.stop_poll_command(this, cmdname);
	}

	// ==========================================================================
	/**
	 * Remove attribute of polled object list
	 *
	 * @param attname
	 *            attribute name to be removed of polled object list.
	 */
	// ==========================================================================
	public void stop_poll_attribute(String attname) throws DevFailed {
		deviceProxy.stop_poll_attribute(this, attname);
	}

	// ==========================================================================
	/**
	 * Returns the polling status for the device.
	 */
	// ==========================================================================
	public String[] polling_status() throws DevFailed {
		return deviceProxy.polling_status(this);
	}

	// ==========================================================================
	/**
	 * Return the history for command polled.
	 *
	 * @param cmdname
	 *            command name to read polled history.
	 * @param nb
	 *            nb data to read.
	 */
	// ==========================================================================
	public DeviceDataHistory[] command_history(String cmdname, int nb) throws DevFailed {
		return deviceProxy.command_history(this, cmdname, nb);
	}

	// ==========================================================================
	/**
	 * Return the history for attribute polled.
	 *
	 * @param attname
	 *            attribute name to read polled history.
	 * @param nb
	 *            nb data to read.
	 */
	// ==========================================================================
	public DeviceDataHistory[] attribute_history(String attname, int nb) throws DevFailed {
		return deviceProxy.attribute_history(this, attname, nb);
	}

	// ==========================================================================
	/**
	 * Return the full history for command polled.
	 *
	 * @param cmdname
	 *            command name to read polled history.
	 */
	// ==========================================================================
	public DeviceDataHistory[] command_history(String cmdname) throws DevFailed {
		return deviceProxy.command_history(this, cmdname);
	}

	// ==========================================================================
	/**
	 * Return the full history for attribute polled.
	 *
	 * @param attname
	 *            attribute name to read polled history.
	 */
	// ==========================================================================
	public DeviceDataHistory[] attribute_history(String attname) throws DevFailed {
		return deviceProxy.attribute_history(this, attname);
	}
	// ==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified attribute.
	 *
	 *	 @param attname	specified attribute name.
	 */
	// ==========================================================================
	public int get_attribute_polling_period(String attname) throws DevFailed {
		return deviceProxy.get_attribute_polling_period(this, attname);
	}
	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified command.
	 *
	 *	 @param cmdname	specified attribute name.
	 */
	//==========================================================================
	public int get_command_polling_period(DeviceProxy deviceProxy, String cmdname) throws DevFailed {
		return deviceProxy.get_command_polling_period(this, cmdname);
	}


	// ==========================================================================
	/**
	 * Asynchronous calls
	 */
	// ==========================================================================
	// ==========================================================================
	/**
	 * Asynchronous command_inout.
	 *
	 * @param cmdname
	 *            command name.
	 * @param data_in
	 *            input argument command.
	 */
	// ==========================================================================
	public int command_inout_asynch(String cmdname, DeviceData data_in) throws DevFailed {
		return deviceProxy.command_inout_asynch(this, cmdname, data_in);
	}

	// ==========================================================================
	/**
	 * Asynchronous command_inout.
	 *
	 * @param cmdname
	 *            command name.
	 */
	// ==========================================================================
	public int command_inout_asynch(String cmdname) throws DevFailed {
		return deviceProxy.command_inout_asynch(this, cmdname);
	}

	// ==========================================================================
	/**
	 * Asynchronous command_inout.
	 *
	 * @param cmdname
	 *            command name.
	 * @param forget
	 *            forget the response if true
	 */
	// ==========================================================================
	public int command_inout_asynch(String cmdname, boolean forget) throws DevFailed {
		return deviceProxy.command_inout_asynch(this, cmdname, forget);
	}

	// ==========================================================================
	/**
	 * Asynchronous command_inout.
	 *
	 * @param cmdname
	 *            command name.
	 * @param data_in
	 *            input argument command.
	 * @param forget
	 *            forget the response if true
	 */
	// ==========================================================================
	public int command_inout_asynch(String cmdname, DeviceData data_in, boolean forget) throws DevFailed {
		return deviceProxy.command_inout_asynch(this, cmdname, data_in, forget);
	}

	// ==========================================================================
	/**
	 * Asynchronous command_inout using callback for reply.
	 *
	 * @param cmdname
	 *            Command name.
	 * @param argin
	 *            Input argument command.
	 * @param cb
	 *            a CallBack object instance.
	 */
	// ==========================================================================
	public void command_inout_asynch(String cmdname, DeviceData argin, CallBack cb) throws DevFailed {
		deviceProxy.command_inout_asynch(this, cmdname, argin, cb);
	}

	// ==========================================================================
	/**
	 * Asynchronous command_inout using callback for reply.
	 *
	 * @param cmdname
	 *            Command name.
	 * @param cb
	 *            a CallBack object instance.
	 */
	// ==========================================================================
	public void command_inout_asynch(String cmdname, CallBack cb) throws DevFailed {
		deviceProxy.command_inout_asynch(this, cmdname, cb);
	}

	// ==========================================================================
	/**
	 * Read Asynchronous command_inout reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by command_inout_asynch).
	 * @param timeout
	 *            number of millisonds to wait reply before throw an excption.
	 */
	// ==========================================================================
	public DeviceData command_inout_reply(int id, int timeout) throws DevFailed, AsynReplyNotArrived {
		return deviceProxy.command_inout_reply(this, id, timeout);
	}

	// ==========================================================================
	/**
	 * Read Asynchronous command_inout reply.
	 *
	 * @param aco
	 *            asynchronous call Request instance
	 * @param timeout
	 *            number of millisonds to wait reply before throw an excption.
	 */
	// ==========================================================================
	DeviceData command_inout_reply(AsyncCallObject aco, int timeout) throws DevFailed, AsynReplyNotArrived {
		return deviceProxy.command_inout_reply(this, aco, timeout);
	}

	// ==========================================================================
	/**
	 * Read Asynchronous command_inout reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by command_inout_asynch).
	 */
	// ==========================================================================
	public DeviceData command_inout_reply(int id) throws DevFailed, AsynReplyNotArrived {
		return deviceProxy.command_inout_reply(this, id);
	}

	// ==========================================================================
	/**
	 * Read Asynchronous command_inout reply.
	 *
	 * @param aco
	 *            asynchronous call Request instance
	 */
	// ==========================================================================
	DeviceData command_inout_reply(AsyncCallObject aco) throws DevFailed, AsynReplyNotArrived {
		return deviceProxy.command_inout_reply(this, aco);
	}

	// ==========================================================================
	/**
	 * Asynchronous read_attribute.
	 *
	 * @param attname
	 *            Attribute name.
	 */
	// ==========================================================================
	public int read_attribute_asynch(String attname) throws DevFailed {
		return deviceProxy.read_attribute_asynch(this, attname);
	}

	// ==========================================================================
	/**
	 * Asynchronous read_attribute.
	 *
	 * @param attnames
	 *            Attribute names.
	 */
	// ==========================================================================
	public int read_attribute_asynch(String[] attnames) throws DevFailed {
		return deviceProxy.read_attribute_asynch(this, attnames);
	}

	// ==========================================================================
	/**
	 * Retrieve the command/attribute arguments to build exception description.
	 */
	// ==========================================================================
	protected String get_asynch_idl_cmd(Request request, String idl_cmd) {
		return deviceProxy.get_asynch_idl_cmd(this, request, idl_cmd);
	}

	// ==========================================================================
	/**
	 * Check Asynchronous call reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by read_attribute_asynch).
	 */
	// ==========================================================================
	protected void check_asynch_reply(Request request, int id, String idl_cmd) throws DevFailed, AsynReplyNotArrived {
		deviceProxy.check_asynch_reply(this, request, id, idl_cmd);
	}

	// ==========================================================================
	/**
	 * Read Asynchronous read_attribute reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by read_attribute_asynch).
	 * @param timeout
	 *            number of millisonds to wait reply before throw an excption.
	 */
	// ==========================================================================
	public DeviceAttribute[] read_attribute_reply(int id, int timeout) throws DevFailed, AsynReplyNotArrived {
		return deviceProxy.read_attribute_reply(this, id, timeout);
	}

	// ==========================================================================
	/**
	 * Read Asynchronous read_attribute reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by read_attribute_asynch).
	 */
	// ==========================================================================
	public DeviceAttribute[] read_attribute_reply(int id) throws DevFailed, AsynReplyNotArrived {
		return deviceProxy.read_attribute_reply(this, id);
	}

	// ==========================================================================
	/**
	 * Asynchronous read_attribute using callback for reply.
	 *
	 * @param attname
	 *            attribute name.
	 * @param cb
	 *            a CallBack object instance.
	 */
	// ==========================================================================
	public void read_attribute_asynch(String attname, CallBack cb) throws DevFailed {
		deviceProxy.read_attribute_asynch(this, attname, cb);
	}

	// ==========================================================================
	/**
	 * Asynchronous read_attribute using callback for reply.
	 *
	 * @param attnames
	 *            attribute names.
	 * @param cb
	 *            a CallBack object instance.
	 */
	// ==========================================================================
	public void read_attribute_asynch(String[] attnames, CallBack cb) throws DevFailed {
		deviceProxy.read_attribute_asynch(this, attnames, cb);
	}

	// ==========================================================================
	/**
	 * Asynchronous write_attribute.
	 *
	 * @param attr
	 *            Attribute value (name, writing value...)
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceAttribute attr) throws DevFailed {
		return deviceProxy.write_attribute_asynch(this, attr);
	}

	// ==========================================================================
	/**
	 * Asynchronous write_attribute.
	 *
	 * @param attr
	 *            Attribute value (name, writing value...)
	 * @param forget
	 *            forget the response if true
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceAttribute attr, boolean forget) throws DevFailed {
		return deviceProxy.write_attribute_asynch(this, attr, forget);
	}

	// ==========================================================================
	/**
	 * Asynchronous write_attribute.
	 *
	 * @param attribs
	 *            Attribute values (name, writing value...)
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceAttribute[] attribs) throws DevFailed {
		return deviceProxy.write_attribute_asynch(this, attribs);
	}

	// ==========================================================================
	/**
	 * Asynchronous write_attribute.
	 *
	 * @param attribs
	 *            Attribute values (name, writing value...)
	 * @param forget
	 *            forget the response if true
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceAttribute[] attribs, boolean forget) throws DevFailed {
		return deviceProxy.write_attribute_asynch(this, attribs, forget);
	}

	// ==========================================================================
	/**
	 * check for Asynchronous write_attribute reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by read_attribute_asynch).
	 */
	// ==========================================================================
	public void write_attribute_reply(int id) throws DevFailed, AsynReplyNotArrived {
		deviceProxy.write_attribute_reply(this, id);
	}

	// ==========================================================================
	/**
	 * check for Asynchronous write_attribute reply.
	 *
	 * @param id
	 *            asynchronous call id (returned by write_attribute_asynch).
	 * @param timeout
	 *            number of millisonds to wait reply before throw an excption.
	 */
	// ==========================================================================
	public void write_attribute_reply(int id, int timeout) throws DevFailed, AsynReplyNotArrived {
		deviceProxy.write_attribute_reply(this, id, timeout);
	}

	// ==========================================================================
	/**
	 * Asynchronous write_attribute using callback for reply.
	 *
	 * @param attr
	 *            Attribute values (name, writing value...)
	 * @param cb
	 *            a CallBack object instance.
	 */
	// ==========================================================================
	public void write_attribute_asynch(DeviceAttribute attr, CallBack cb) throws DevFailed {
		deviceProxy.write_attribute_asynch(this, attr, cb);
	}

	// ==========================================================================
	/**
	 * Asynchronous write_attribute using callback for reply.
	 *
	 * @param attribs
	 *            Attribute values (name, writing value...)
	 * @param cb
	 *            a CallBack object instance.
	 */
	// ==========================================================================
	public void write_attribute_asynch(DeviceAttribute[] attribs, CallBack cb) throws DevFailed {
		deviceProxy.write_attribute_asynch(this, attribs, cb);
	}

	// ==========================================================================
	/**
	 * return the still pending asynchronous call for a reply model.
	 *
	 * @param reply_model
	 *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	// ==========================================================================
	public int pending_asynch_call(int reply_model) {
		return deviceProxy.pending_asynch_call(this, reply_model);
	}

	// ==========================================================================
	/**
	 * Fire callback methods for all asynchronous requests(cmd and attr) with
	 * already arrived replies.
	 */
	// ==========================================================================
	public void get_asynch_replies() {
		deviceProxy.get_asynch_replies(this);
	}

	// ==========================================================================
	/**
	 * Fire callback methods for all asynchronous requests(cmd and attr) with
	 * already arrived replies.
	 */
	// ==========================================================================
	public void get_asynch_replies(int timeout) {
		deviceProxy.get_asynch_replies(this, timeout);
	}

	// ==========================================================================
	/**
	 * Logging related methods
	 */
	// ==========================================================================
	// ==========================================================================
	/**
	 * Adds a new logging target to the device.
	 *
	 * @deprecated use add_logging_target(String target).
	 */
	// ==========================================================================
	public void add_logging_target(String target_type, String target_name) throws DevFailed {
		deviceProxy.add_logging_target(this, target_type + "::" + target_name);
	}

	// ==========================================================================
	/**
	 * Adds a new logging target to the device.
	 *
	 * @param target
	 *            The target for logging (e.g. file::/tmp/logging_device).
	 */
	// ==========================================================================
	public void add_logging_target(String target) throws DevFailed {
		deviceProxy.add_logging_target(this, target);
	}

	// ==========================================================================
	/**
	 * Removes a new logging target to the device.
	 */
	// ==========================================================================
	public void remove_logging_target(String target_type, String target_name) throws DevFailed {
		deviceProxy.remove_logging_target(this, target_type, target_name);
	}

	// ==========================================================================
	/**
	 * get logging target from the device.
	 */
	// ==========================================================================
	public String[] get_logging_target() throws DevFailed {
		return deviceProxy.get_logging_target(this);
	}

	// ==========================================================================
	/**
	 * get logging level from the device.
	 *
	 * @return device's logging level: (ApiDefs.LOGGING_OFF,
	 *         ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
	 *         ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
	 */
	// ==========================================================================
	public int get_logging_level() throws DevFailed {
		return deviceProxy.get_logging_level(this);
	}

	// ==========================================================================
	/**
	 * Set logging level from the device.
	 *
	 * @param level
	 *            device's logging level: (ApiDefs.LOGGING_OFF,
	 *            ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
	 *            ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
	 */
	// ==========================================================================
	public void set_logging_level(int level) throws DevFailed {
		deviceProxy.set_logging_level(this, level);
	}


	// ==========================================================================
	//	Locking Device 4 commands
	// ==========================================================================

	// ==========================================================================
	/**
	 *	Lock the device
	 */
	// ==========================================================================
	public void lock() throws DevFailed {
		this.lock(TangoConst.DEFAULT_LOCK_VALIDITY);
	}
	// ==========================================================================
	/**
	 *	Lock the device
	 *
	 *	@param	validity	Lock validity (in seconds)
	 */
	// ==========================================================================
	public void lock(int validity) throws DevFailed {
		deviceProxy.lock(this, validity);
		proxy_lock_cnt++;
	}
	// ==========================================================================
	/**
	 *	Unlock the device
	 *
	 *	@return the device lock counter
	 */
	// ==========================================================================
	public int unlock() throws DevFailed {
		int	n = deviceProxy.unlock(this); // lock counter for the device itself (not the proxy)
		proxy_lock_cnt--;
		return n;
	}
	// ==========================================================================
	/**
	 *	Returns true if the device is locked
	 */
	// ==========================================================================
	public boolean isLocked() throws DevFailed {
		return deviceProxy.isLocked(this);
	}
	// ==========================================================================
	/**
	 *	Returns true if the device is locked by this process
	 */
	// ==========================================================================
	public boolean isLockedByMe() throws DevFailed {
		return deviceProxy.isLockedByMe(this);
	}

	// ==========================================================================
	/**
	 *	Returns the device lock status
	 */
	// ==========================================================================
	public String getLockerStatus() throws DevFailed {
		return deviceProxy.getLockerStatus(this);
	}
	// ==========================================================================
	/**
	 *	Returns the device lock info
	 */
	// ==========================================================================
	public LockerInfo getLockerInfo() throws DevFailed {
		return deviceProxy.getLockerInfo(this);
	}



	// ==========================================================================
	/**
	 * TACO commands
	 */
	// ==========================================================================
	// ==========================================================================
	/**
	 * Returns TACO device information.
	 *
	 * @return TACO device information as String array.
	 *         <li> Device name.
	 *         <li> Class name
	 *         <li> Device type
	 *         <li> Device server name
	 *         <li> Host name
	 */
	// ==========================================================================
	public String[] dev_inform() throws DevFailed {
		return deviceProxy.dev_inform(this);
	}

	// ==========================================================================
	/**
	 * Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
	 *
	 * @param mode
	 *            RPC protocol mode to be seted (TangoApi.TacoDevice.<b>D_TCP</b>
	 *            or TangoApi.TacoDevice.<b>D_UDP</b>).
	 */
	// ==========================================================================
	public void set_rpc_protocol(int mode) throws DevFailed {
		deviceProxy.set_rpc_protocol(this, mode);
	}

	// ==========================================================================
	/**
	 * @return mode RPC protocol mode used
	 * @return mode RPC protocol mode used (TangoApi.TacoDevice.<b>D_TCP</b>
	 *         or TangoApi.TacoDevice.<b>D_UDP</b>).
	 */
	// ==========================================================================
	public int get_rpc_protocol() throws DevFailed {
		return deviceProxy.get_rpc_protocol(this);
	}

	//==========================================================================
	/**
	 *	Just a main method to check API methods.
	 */
	//==========================================================================
	public static void main (String args[])
	{
		IDeviceProxyDAO deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
		// we call the main class of the implementation
		deviceProxyDAO.main(args);
	}

	// ==========================================================================
	/**
	 * Subscribe to an event.
	 *
	 * @param attr_name	attribute name.
	 * @param event 	event name.
	 * @param callback	event callback.
	 */
	// ==========================================================================
	public int subscribe_event(String attr_name, int event, CallBack callback, String[] filters)
				throws DevFailed
	{
		return deviceProxy.subscribe_event(this, attr_name, event, callback, filters, false);
	}

	// ==========================================================================
	/**
	 * Subscribe to an event.
	 *
	 *	@param attr_name	attribute name.
	 *	@param event 		event name.
	 *	@param callback		event callback.
	 *	@param  stateless	If true, do not throw exception if connection failed.
	 */
	// ==========================================================================
	public int subscribe_event(String attr_name, int event, CallBack callback, String[] filters, boolean stateless)
				throws DevFailed
	{
		return deviceProxy.subscribe_event(this, attr_name, event, callback, filters, stateless);
	}
	// ==========================================================================
	/**
	 * Subscribe to event to be stored in an event queue.
	 *
	 *	@param attr_name	attribute name.
	 *	@param event 		event name.
	 *	@param max_size		event queue maximum size.
	 *	@param  stateless	If true, do not throw exception if connection failed.
	 */
	// ==========================================================================
	public int subscribe_event(String attr_name, int event, int max_size, String[] filters, boolean stateless)
				throws DevFailed
	{
		return deviceProxy.subscribe_event(this, attr_name, event, max_size, filters, stateless);
	}
	// ==========================================================================
	// ==========================================================================
	public void setEventQueue(EventQueue eq)
	{
		event_queue = eq;
	}
	// ==========================================================================
	// ==========================================================================
	public EventQueue getEventQueue()
	{
		return event_queue;
	}
	// ==========================================================================
	/**
	 *	returns the number of EventData in queue.
	 */
	// ==========================================================================
	public int get_event_queue_size()
	{
		return event_queue.size();
	}
	// ==========================================================================
	/**
	 *	returns the number of EventData in queue for specifed type.
	 *  @param	event_type	Specified event type.
	 */
	// ==========================================================================
	public int get_event_queue_size(int event_type)
	{
		return event_queue.size(event_type);
	}
	// ==========================================================================
	/**
	 *	returns next EventData in queue.
	 */
	// ==========================================================================
	public EventData get_next_event() throws DevFailed
	{
		return event_queue.getNextEvent();
	}
	// ==========================================================================
	/**
	 *	returns next event in queue for specified type.
	 *  @param	event_type	Specified event type.
	 */
	// ==========================================================================
	public EventData get_next_event(int event_type) throws DevFailed
	{
		return event_queue.getNextEvent(event_type);
	}
	// ==========================================================================
	/**
	 *	returns number of milliseconds since EPOCH for the last EventData in queue.
	 */
	// ==========================================================================
	public synchronized long get_last_event_date() throws DevFailed
	{
		return event_queue.getLastEventDate();
	}
	// ==========================================================================
	/**
	 *	returns all EventData in queue.
	 */
	// ==========================================================================
	public EventData[] get_events()
	{
		return event_queue.getEvents();
	}
	// ==========================================================================
	/**
	 *	returns all event in queue for specified type.
	 *  @param	event_type	Specified event type.
	 */
	// ==========================================================================
	public EventData[] get_events(int event_type)
	{
		return event_queue.getEvents(event_type);
	}
	// ==========================================================================
	/**
	 * Unsubscribe to an event.
	 * 
	 * @param event_id	event identifier.
	 */
	// ==========================================================================
	public void unsubscribe_event(int event_id) throws DevFailed
	{
		deviceProxy.unsubscribe_event(this, event_id);
	}

	public IDeviceProxyDAO getDeviceProxy() {
		return deviceProxy;
	}


	public void setDeviceProxy(IDeviceProxyDAO deviceProxy) {
		this.deviceProxy = deviceProxy;
	}

	/*
	 * Getter and setter
	 */

	public DeviceProxy getAdm_dev() {
		 return this.adm_dev;
	}


	public void setAdm_dev(DeviceProxy adm_dev) {
		this.adm_dev = adm_dev;
	}


	public String[] getAttnames_array() {
		return attnames_array;
	}


	public void setAttnames_array(String[] attnames_array) {
		this.attnames_array = attnames_array;
	}


	public DbDevice getDb_dev() {
		return db_dev;
	}


	public void setDb_dev(DbDevice db_dev) {
		this.db_dev = db_dev;
	}


	public String getFull_class_name() {
		return full_class_name;
	}


	public void setFull_class_name(String full_class_name) {
		this.full_class_name = full_class_name;
	}


	public static boolean isCheck_idl() {
		return check_idl;
	}

	public DbEventImportInfo get_evt_import_info() {
		return evt_import_info;
	}
	
	public void  set_evt_import_info(DbEventImportInfo info) {
		evt_import_info = info;
	}

	//==========================================================================
	/**
	 *	called at the death of  the object.
	 *	(Not referenced and Garbage collector called)
	 */
	//==========================================================================

	int	factory_instance_counter = 1;
	protected void finalize()
	{
		if (proxy_lock_cnt>0)
		{
			try { unlock(); } catch(DevFailed e){}
			System.out.println("======== DeviceProxy " + get_name() + " object deleted.=======");
		}
		try { super.finalize(); } catch(Throwable e) {}
	}
}

