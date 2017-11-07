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
// $Revision: 30280 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoApi.events.DbEventImportInfo;
import fr.esrf.TangoApi.events.EventData;
import fr.esrf.TangoApi.events.EventQueue;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 * <p/>
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
 * @version $Revision: 30280 $
 */

public class DeviceProxy extends Connection implements ApiDefs {

    private IDeviceProxyDAO deviceProxyDAO = null;

    static final private boolean check_idl = false;

    /**
     * DbDevice object to make an agregate..
     */
    private DbDevice db_dev;

    private String full_class_name;

    /**
     * Instance on administration device
     */
    private DeviceProxy adm_dev = null;

    private String[] attnames_array = null;

    /**
     * Lock device counter for this object instance
     */
    protected int proxy_lock_cnt = 0;
    /**
     * Event queue instance
     */
    protected EventQueue event_queue;

    private DbEventImportInfo evt_import_info = null;
    // ===================================================================
    /**
     * Default DeviceProxy constructor. It will do nothing
     */
    // ===================================================================
    public DeviceProxy() throws DevFailed {
        super();
        deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
        deviceProxyDAO.init(this);
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
        deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
        deviceProxyDAO.init(this, info.name);
        //System.out.println("=========== " + devname + " created ============");
        DeviceProxyFactory.add(this);
    }

    // ===================================================================
    /**
     * DeviceProxy constructor. It will import the device.
     *
     * @param devname name of the device to be imported.
     */
    // ===================================================================
    public DeviceProxy(String devname) throws DevFailed {
        super(devname);
        deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
        deviceProxyDAO.init(this, devname);
        //System.out.println("=========== " + devname + " created ============");
        DeviceProxyFactory.add(this);
    }

    // ===================================================================
    /**
     * DeviceProxy constructor. It will import the device.
     *
     * @param devname      name of the device to be imported.
     * @param check_access set check_access value
     */
    // ===================================================================
    DeviceProxy(String devname, boolean check_access) throws DevFailed {
        super(devname, check_access);
        deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
        deviceProxyDAO.init(this, devname, check_access);
        DeviceProxyFactory.add(this);
    }

    // ===================================================================
    /**
     * TangoDevice constructor. It will import the device.
     *
     * @param devname name of the device to be imported.
     * @param ior     ior string used to import device
     */
    // ===================================================================
    public DeviceProxy(String devname, String ior) throws DevFailed {
        super(devname, ior);
        deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
        deviceProxyDAO.init(this, devname, ior);
        DeviceProxyFactory.add(this);
    }

    // ===================================================================
    /**
     * TangoDevice constructor. It will import the device.
     *
     * @param devname name of the device to be imported.
     * @param host    host where database is running.
     * @param port    port for database connection.
     */
    // ===================================================================
    public DeviceProxy(String devname, String host, String port) throws DevFailed {
        super(devname, host, port);
        deviceProxyDAO = TangoFactory.getSingleton().getDeviceProxyDAO();
        deviceProxyDAO.init(this, devname, host, host);
        DeviceProxyFactory.add(this);
    }

    public boolean use_db() {
        return deviceProxyDAO.use_db(this);
    }

    //==========================================================================
    //==========================================================================
    public Database get_db_obj() throws DevFailed {
        return deviceProxyDAO.get_db_obj(this);
    }

    // ===================================================================
    /**
     * Get connection on administration device.
     */
    // ===================================================================
    protected void import_admin_device(DbDevImportInfo info) throws DevFailed {
        deviceProxyDAO.import_admin_device(this, info);
    }

    // ===================================================================
    /**
     * Get connection on administration device.
     */
    // ===================================================================
    protected void import_admin_device(String origin) throws DevFailed {
        deviceProxyDAO.import_admin_device(this, origin);
    }

    // ===========================================================
    /**
     * return the device name.
     */
    // ===========================================================
    public String name() {
        return deviceProxyDAO.name(this);
    }
    // ===========================================================
    /**
     * return the device name with url.
     */
    // ===========================================================
    public String fullName() {
        return this.getUrl().toString();
    }

    // ===========================================================
    /**
     * return the device status read from CORBA attribute.
     */
    // ===========================================================
    public String status() throws DevFailed {
        return deviceProxyDAO.status(this);
    }

    // ===========================================================
    /**
     * return the device status.
     *
     * @param src Source to read status. It could be ApiDefs.FROM_CMD to read it
     *            from a command_inout or ApiDefs.FROM_ATTR to read it from
     *            CORBA attribute.
     */
    // ===========================================================
    public String status(boolean src) throws DevFailed {
        return deviceProxyDAO.status(this, src);
    }

    // ===========================================================
    /**
     * return the device state read from CORBA attribute.
     */
    // ===========================================================
    public DevState state() throws DevFailed {
        return deviceProxyDAO.state(this);
    }

    // ===========================================================

    /**
     * return the device state.
     *
     * @param src Source to read state. It could be ApiDefs.FROM_CMD to read it
     *            from a command_inout or ApiDefs.FROM_ATTR to read it from
     *            CORBA attribute.
     */
    // ===========================================================
    public DevState state(boolean src) throws DevFailed {
        return deviceProxyDAO.state(this, src);
    }

    // ===========================================================
    /**
     * return the IDL device command_query for the specified command.
     *
     * @param cmdname command name to be used for the command_query
     * @return the command information..
     */
    // ===========================================================
    public CommandInfo command_query(String cmdname) throws DevFailed {
        return deviceProxyDAO.command_query(this, cmdname);
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
        return deviceProxyDAO.get_class(this);
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
        return deviceProxyDAO.get_class_inheritance(this);
    }

    // ==========================================================================

    /**
     * Set an alias for a device name
     *
     * @param aliasname alias name.
     */
    // ==========================================================================
    public void put_alias(String aliasname) throws DevFailed {
        deviceProxyDAO.put_alias(this, aliasname);
    }

    // ==========================================================================
    /**
     * Get alias for the device
     *
     * @return the alias name if found.
     */
    // ==========================================================================
    public String get_alias() throws DevFailed {
        return deviceProxyDAO.get_alias(this);
    }

    // ==========================================================================
    /**
     * Query the database for the info of this device.
     *
     * @return the information in a DeviceInfo.
     */
    // ==========================================================================
    public DeviceInfo get_info() throws DevFailed {
        return deviceProxyDAO.get_info(this);
    }

    // ==========================================================================
    /**
     * Query the database for the export info of this device.
     *
     * @return the information in a DbDevImportInfo.
     */
    // ==========================================================================
    public DbDevImportInfo import_device() throws DevFailed {
        return deviceProxyDAO.import_device(this);
    }

    // ==========================================================================
    /**
     * Update the export info for this device in the database.
     *
     * @param devinfo Device information to export.
     */
    // ==========================================================================
    public void export_device(DbDevExportInfo devinfo) throws DevFailed {
        deviceProxyDAO.export_device(this, devinfo);
    }

    // ==========================================================================
    /**
     * Unexport the device in database.
     */
    // ==========================================================================
    public void unexport_device() throws DevFailed {
        deviceProxyDAO.unexport_device(this);
    }

    // ==========================================================================
    /**
     * Add/update this device to the database
     *
     * @param devinfo The device name, class and server specified in object.
     */
    // ==========================================================================
    public void add_device(DbDevInfo devinfo) throws DevFailed {
        deviceProxyDAO.add_device(this, devinfo);
    }

    // ==========================================================================
    /**
     * Delete this device from the database
     */
    // ==========================================================================
    public void delete_device() throws DevFailed {
        deviceProxyDAO.delete_device(this);
    }

    // ==========================================================================
    /**
     * Query the database for a list of device properties for the pecified
     * object.
     *
     * @param wildcard propertie's wildcard (* matches any charactere).
     * @return properties in DbDatum objects.
     */
    // ==========================================================================
    public String[] get_property_list(String wildcard) throws DevFailed {
        return deviceProxyDAO.get_property_list(this, wildcard);
    }

    // ==========================================================================
    /**
     * Query the database for a list of device properties for this device.
     *
     * @param propnames list of property names.
     * @return properties in DbDatum objects.
     */
    // ==========================================================================
    public DbDatum[] get_property(String[] propnames) throws DevFailed {
        return deviceProxyDAO.get_property(this, propnames);
    }

    // ==========================================================================
    /**
     * Query the database for a device property for this device.
     *
     * @param propname property name.
     * @return property in DbDatum objects.
     */
    // ==========================================================================
    public DbDatum get_property(String propname) throws DevFailed {
        return deviceProxyDAO.get_property(this, propname);
    }

    // ==========================================================================
    /**
     * Query the database for a list of device properties for this device. The
     * property names are specified by the DbDatum array objects.
     *
     * @param properties list of property DbDatum objects.
     * @return properties in DbDatum objects.
     */
    // ==========================================================================
    public DbDatum[] get_property(DbDatum[] properties) throws DevFailed {
        return deviceProxyDAO.get_property(this, properties);
    }

    // ==========================================================================
    /**
     * Insert or update a property for this device The property name and its
     * value are specified by the DbDatum
     *
     * @param prop Property name and value
     */
    // ==========================================================================
    public void put_property(DbDatum prop) throws DevFailed {
        deviceProxyDAO.put_property(this, prop);
    }

    // ==========================================================================
    /**
     * Insert or update a list of properties for this device The property names
     * and their values are specified by the DbDatum array.
     *
     * @param properties Properties names and values array.
     */
    // ==========================================================================
    public void put_property(DbDatum[] properties) throws DevFailed {
        deviceProxyDAO.put_property(this, properties);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this device.
     *
     * @param propnames Property names.
     */
    // ==========================================================================
    public void delete_property(String[] propnames) throws DevFailed {
        deviceProxyDAO.delete_property(this, propnames);
    }

    // ==========================================================================
    /**
     * Delete a property for this device.
     *
     * @param propname Property name.
     */
    // ==========================================================================
    public void delete_property(String propname) throws DevFailed {
        deviceProxyDAO.delete_property(this, propname);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this device.
     *
     * @param properties Property DbDatum objects.
     */
    // ==========================================================================
    public void delete_property(DbDatum[] properties) throws DevFailed {
        deviceProxyDAO.delete_property(this, properties);
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
        return deviceProxyDAO.get_attribute_list(this);
    }

    // ==========================================================================
    /**
     * Insert or update a list of attribute properties for this device. The
     * property names and their values are specified by the DbAttribute array.
     *
     * @param attr attribute names and properties (names and values) array.
     */
    // ==========================================================================
    public void put_attribute_property(DbAttribute[] attr) throws DevFailed {
        deviceProxyDAO.put_attribute_property(this, attr);
    }

    // ==========================================================================
    /**
     * Insert or update an attribute properties for this device. The property
     * names and their values are specified by the DbAttribute array.
     *
     * @param attr attribute name and properties (names and values).
     */
    // ==========================================================================
    public void put_attribute_property(DbAttribute attr) throws DevFailed {
        deviceProxyDAO.put_attribute_property(this, attr);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this object.
     *
     * @param attname   attribute name.
     * @param propnames Property names.
     */
    // ==========================================================================
    public void delete_attribute_property(String attname, String[] propnames) throws DevFailed {
        deviceProxyDAO.delete_attribute_property(this, attname, propnames);
    }

    // ==========================================================================
    /**
     * Delete a property for this object.
     *
     * @param attname  attribute name.
     * @param propname Property name.
     */
    // ==========================================================================
    public void delete_attribute_property(String attname, String propname) throws DevFailed {
        deviceProxyDAO.delete_attribute_property(this, attname, propname);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this object.
     *
     * @param    attr DbAttribute object for specified attribute.
     */
    // ==========================================================================
    public void delete_attribute_property(DbAttribute attr) throws DevFailed {
        deviceProxyDAO.delete_attribute_property(this, attr);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this object.
     *
     * @param attr DbAttribute array object for specified attribute.
     */
    // ==========================================================================
    public void delete_attribute_property(DbAttribute[] attr) throws DevFailed {
        deviceProxyDAO.delete_attribute_property(this, attr);
    }

    // ==========================================================================
    /**
     * Query the database for a list of device attribute properties for this
     * device.
     *
     * @param attnames list of attribute names.
     * @return properties in DbAttribute objects array.
     */
    // ==========================================================================
    public DbAttribute[] get_attribute_property(String[] attnames) throws DevFailed {
        return deviceProxyDAO.get_attribute_property(this, attnames);
    }

    // ==========================================================================
    /**
     * Query the database for a device attribute property for this device.
     *
     * @param attname attribute name.
     * @return property in DbAttribute object.
     */
    // ==========================================================================
    public DbAttribute get_attribute_property(String attname) throws DevFailed {
        return deviceProxyDAO.get_attribute_property(this, attname);
    }

    // ==========================================================================
    /**
     * Delete an attribute for this object.
     *
     * @param attname attribute name.
     */
    // ==========================================================================
    public void delete_attribute(String attname) throws DevFailed {
        deviceProxyDAO.delete_attribute(this, attname);
    }

    // ===========================================================
    // Attribute Methods
    // ===========================================================
    // ==========================================================================
    /**
     * Get the attributes configuration for the specified device.
     *
     * @param attnames attribute names to request config.
     * @return the attributes configuration.
     */
    // ==========================================================================
    public AttributeInfo[] get_attribute_info(String[] attnames) throws DevFailed {
        return deviceProxyDAO.get_attribute_info(this, attnames);
    }

    // ==========================================================================
    /**
     * Get the extended attributes configuration for the specified device.
     *
     * @param attnames attribute names to request config.
     * @return the attributes configuration.
     */
    // ==========================================================================
    public AttributeInfoEx[] get_attribute_info_ex(String[] attnames) throws DevFailed {
        return deviceProxyDAO.get_attribute_info_ex(this, attnames);
    }

    // ==========================================================================
    /**
     * Get the attributes configuration for the specified device.
     *
     * @param attnames attribute names to request config.
     * @return the attributes configuration.
     * @deprecated use get_attribute_info(String[] attnames)
     */
    // ==========================================================================
    public AttributeInfo[] get_attribute_config(String[] attnames) throws DevFailed {
        return deviceProxyDAO.get_attribute_info(this, attnames);
    }

    // ==========================================================================
    /**
     * Get the attribute info for the specified device.
     *
     * @param attname attribute name to request config.
     * @return the attribute info.
     */
    // ==========================================================================
    public AttributeInfo get_attribute_info(String attname) throws DevFailed {
        return deviceProxyDAO.get_attribute_info(this, attname);
    }

    // ==========================================================================
    /**
     * Get the attribute info for the specified device.
     *
     * @param attname attribute name to request config.
     * @return the attribute info.
     */
    // ==========================================================================
    public AttributeInfoEx get_attribute_info_ex(String attname) throws DevFailed {
        return deviceProxyDAO.get_attribute_info_ex(this, attname);
    }

    // ==========================================================================
    /**
     * Get the attribute configuration for the specified device.
     *
     * @param attname attribute name to request config.
     * @return the attribute configuration.
     * @deprecated use get_attribute_info(String attname)
     */
    // ==========================================================================
    public AttributeInfo get_attribute_config(String attname) throws DevFailed {
        return deviceProxyDAO.get_attribute_info(this, attname);
    }

    // ==========================================================================
    /**
     * Get all attributes info for the specified device.
     *
     * @return all attributes info.
     */
    // ==========================================================================
    public AttributeInfo[] get_attribute_info() throws DevFailed {
        return deviceProxyDAO.get_attribute_info(this);
    }

    // ==========================================================================
    /**
     * Get all attributes info for the specified device.
     *
     * @return all attributes info.
     */
    // ==========================================================================
    public AttributeInfoEx[] get_attribute_info_ex() throws DevFailed {
        return deviceProxyDAO.get_attribute_info_ex(this);
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
        return deviceProxyDAO.get_attribute_info(this);
    }

    // ==========================================================================
    /**
     * Update the attributes info for the specified device.
     *
     * @param attr the attributes info.
     */
    // ==========================================================================
    public void set_attribute_info(AttributeInfo[] attr) throws DevFailed {
        deviceProxyDAO.set_attribute_info(this, attr);
    }

    // ==========================================================================
    /**
     * Update the attributes extended info for the specified device.
     *
     * @param attr the attributes info.
     */
    // ==========================================================================
    public void set_attribute_info(AttributeInfoEx[] attr) throws DevFailed {
        deviceProxyDAO.set_attribute_info(this, attr);

    }

    // ==========================================================================
    /**
     * Update the attributes configuration for the specified device.
     *
     * @param attr the attributes configuration.
     * @deprecated use set_attribute_info(AttributeInfo[] attr)
     */
    // ==========================================================================
    public void set_attribute_config(AttributeInfo[] attr) throws DevFailed {
        deviceProxyDAO.set_attribute_info(this, attr);
    }

    // ==========================================================================
    /**
     * Read the attribute value for the specified device.
     *
     * @param attname attribute name to request value.
     * @return the attribute value.
     */
    // ==========================================================================
    public DeviceAttribute read_attribute(String attname) throws DevFailed {
        return deviceProxyDAO.read_attribute(this, attname);
    }

    // ==========================================================================
    /**
     * return directly AttributeValue object without creation of DeviceAttribute
     * object
     */
    // ==========================================================================
    // used only by read_attribute_value() to do not re-create it every time.
    public AttributeValue read_attribute_value(String attname) throws DevFailed {
        return deviceProxyDAO.read_attribute_value(this, attname);
    }

    // ==========================================================================
    /**
     * Read the attribute values for the specified device.
     *
     * @param attnames attribute names to request values.
     * @return the attribute values.
     */
    // ==========================================================================
    public DeviceAttribute[] read_attribute(String[] attnames) throws DevFailed {
        checkDuplication(attnames, "DeviceProxy.read_attribute()");
        return deviceProxyDAO.read_attribute(this, attnames);
    }

    // ==========================================================================
    /**
     * Write the attribute value for the specified device.
     *
     * @param deviceAttribute attribute name and value.
     */
    // ==========================================================================
    public void write_attribute(DeviceAttribute deviceAttribute) throws DevFailed {
        deviceProxyDAO.write_attribute(this, deviceAttribute);
    }

    // ==========================================================================
    /**
     * Write the attribute values for the specified device.
     *
     * @param deviceAttributes attribute names and values.
     */
    // ==========================================================================
    public void write_attribute(DeviceAttribute[] deviceAttributes) throws DevFailed {
        deviceProxyDAO.write_attribute(this, deviceAttributes);
    }

    // ==========================================================================
    /**
     * Write and then read the attribute values, for the specified device.
     *
     * @param deviceAttribute attribute name and values.
     */
    // ==========================================================================
    public DeviceAttribute write_read_attribute(DeviceAttribute deviceAttribute) throws DevFailed {
        return deviceProxyDAO.write_read_attribute(this,
                new DeviceAttribute[]{deviceAttribute})[0];
    }
    // ==========================================================================
    /**
     * Write and then read the attribute values, for the specified device.
     *
     * @param deviceAttributes attribute names and values to be written.
     * @param readNames        attribute names to read.
     */
    // ==========================================================================
    public DeviceAttribute[] write_read_attribute(DeviceAttribute[] deviceAttributes,
                                                  String[] readNames) throws DevFailed {
        return deviceProxyDAO.write_read_attribute(this, deviceAttributes, readNames);
    }

    // ==========================================================================
    /**
     * Write and then read the attribute values, for the specified device.
     *
     * @param deviceAttributes attribute names and values.
     */
    // ==========================================================================
    public DeviceAttribute[] write_read_attribute(DeviceAttribute[] deviceAttributes) throws DevFailed {
        return deviceProxyDAO.write_read_attribute(this, deviceAttributes);
    }

    // ==========================================================================
    // ==========================================================================
    public DeviceProxy get_adm_dev() throws DevFailed {
        return deviceProxyDAO.get_adm_dev(this);
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
     * @param cmdname command name to be polled.
     * @param period  polling period.
     */
    // ==========================================================================
    public void poll_command(String cmdname, int period) throws DevFailed {
        deviceProxyDAO.poll_command(this, cmdname, period);
    }

    // ==========================================================================
    /**
     * Add a attribute to be polled for the device. If already polled, update
     * its polling period.
     *
     * @param attname attribute name to be polled.
     * @param period  polling period.
     */
    // ==========================================================================
    public void poll_attribute(String attname, int period) throws DevFailed {
        deviceProxyDAO.poll_attribute(this, attname, period);
    }

    // ==========================================================================

    /**
     * Remove command of polled object list
     *
     * @param cmdname command name to be removed of polled object list.
     */
    // ==========================================================================
    public void stop_poll_command(String cmdname) throws DevFailed {
        deviceProxyDAO.stop_poll_command(this, cmdname);
    }

    // ==========================================================================
    /**
     * Remove attribute of polled object list
     *
     * @param attname attribute name to be removed of polled object list.
     */
    // ==========================================================================
    public void stop_poll_attribute(String attname) throws DevFailed {
        deviceProxyDAO.stop_poll_attribute(this, attname);
    }

    // ==========================================================================
    /**
     * Returns the polling status for the device.
     */
    // ==========================================================================
    public String[] polling_status() throws DevFailed {
        return deviceProxyDAO.polling_status(this);
    }

    // ==========================================================================
    /**
     * Return the history for command polled.
     *
     * @param cmdname command name to read polled history.
     * @param nb      nb data to read.
     */
    // ==========================================================================
    public DeviceDataHistory[] command_history(String cmdname, int nb) throws DevFailed {
        return deviceProxyDAO.command_history(this, cmdname, nb);
    }

    // ==========================================================================
    /**
     * Return the history for attribute polled.
     *
     * @param attname attribute name to read polled history.
     * @param nb      nb data to read.
     */
    // ==========================================================================
    public DeviceDataHistory[] attribute_history(String attname, int nb) throws DevFailed {
        return deviceProxyDAO.attribute_history(this, attname, nb);
    }

    // ==========================================================================
    /**
     * Return the full history for command polled.
     *
     * @param cmdname command name to read polled history.
     */
    // ==========================================================================
    public DeviceDataHistory[] command_history(String cmdname) throws DevFailed {
        return deviceProxyDAO.command_history(this, cmdname);
    }

    // ==========================================================================
    /**
     * Return the full history for attribute polled.
     *
     * @param attname attribute name to read polled history.
     */
    // ==========================================================================
    public DeviceDataHistory[] attribute_history(String attname) throws DevFailed {
        return deviceProxyDAO.attribute_history(this, attname);
    }
    // ==========================================================================
    /**
     * Returns the polling period (in ms) for specified attribute.
     *
     * @param attname specified attribute name.
     */
    // ==========================================================================
    public int get_attribute_polling_period(String attname) throws DevFailed {
        return deviceProxyDAO.get_attribute_polling_period(this, attname);
    }
    //==========================================================================
    /**
     * Returns the polling period (in ms) for specified command.
     *
     * @param cmdname specified attribute name.
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
     * @param cmdname command name.
     * @param data_in input argument command.
     */
    // ==========================================================================
    public int command_inout_asynch(String cmdname, DeviceData data_in) throws DevFailed {
        return deviceProxyDAO.command_inout_asynch(this, cmdname, data_in);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout.
     *
     * @param cmdname command name.
     */
    // ==========================================================================
    public int command_inout_asynch(String cmdname) throws DevFailed {
        return deviceProxyDAO.command_inout_asynch(this, cmdname);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout.
     *
     * @param cmdname command name.
     * @param forget  forget the response if true
     */
    // ==========================================================================
    public int command_inout_asynch(String cmdname, boolean forget) throws DevFailed {
        return deviceProxyDAO.command_inout_asynch(this, cmdname, forget);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout.
     *
     * @param cmdname command name.
     * @param data_in input argument command.
     * @param forget  forget the response if true
     */
    // ==========================================================================
    public int command_inout_asynch(String cmdname, DeviceData data_in, boolean forget) throws DevFailed {
        return deviceProxyDAO.command_inout_asynch(this, cmdname, data_in, forget);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout using callback for reply.
     *
     * @param cmdname Command name.
     * @param argin   Input argument command.
     * @param cb      a CallBack object instance.
     */
    // ==========================================================================
    public void command_inout_asynch(String cmdname, DeviceData argin, CallBack cb) throws DevFailed {
        deviceProxyDAO.command_inout_asynch(this, cmdname, argin, cb);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout using callback for reply.
     *
     * @param cmdname Command name.
     * @param cb      a CallBack object instance.
     */
    // ==========================================================================
    public void command_inout_asynch(String cmdname, CallBack cb) throws DevFailed {
        deviceProxyDAO.command_inout_asynch(this, cmdname, cb);
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     *
     * @param id      asynchronous call id (returned by command_inout_asynch).
     * @param timeout number of millisonds to wait reply before throw an excption.
     */
    // ==========================================================================
    public DeviceData command_inout_reply(int id, int timeout) throws DevFailed, AsynReplyNotArrived {
        return deviceProxyDAO.command_inout_reply(this, id, timeout);
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     *
     * @param aco     asynchronous call Request instance
     * @param timeout number of millisonds to wait reply before throw an excption.
     */
    // ==========================================================================
    DeviceData command_inout_reply(AsyncCallObject aco, int timeout) throws DevFailed, AsynReplyNotArrived {
        return deviceProxyDAO.command_inout_reply(this, aco, timeout);
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     *
     * @param id asynchronous call id (returned by command_inout_asynch).
     */
    // ==========================================================================
    public DeviceData command_inout_reply(int id) throws DevFailed, AsynReplyNotArrived {
        return deviceProxyDAO.command_inout_reply(this, id);
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     *
     * @param aco asynchronous call Request instance
     */
    // ==========================================================================
    DeviceData command_inout_reply(AsyncCallObject aco) throws DevFailed, AsynReplyNotArrived {
        return deviceProxyDAO.command_inout_reply(this, aco);
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute.
     *
     * @param attname Attribute name.
     */
    // ==========================================================================
    public int read_attribute_asynch(String attname) throws DevFailed {
        return deviceProxyDAO.read_attribute_asynch(this, attname);
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute.
     *
     * @param attnames Attribute names.
     */
    // ==========================================================================
    public int read_attribute_asynch(String[] attnames) throws DevFailed {
        checkDuplication(attnames, "DeviceProxy.read_attribute_asynch()");
        return deviceProxyDAO.read_attribute_asynch(this, attnames);
    }

    // ==========================================================================
    /**
     * Retrieve the command/attribute arguments to build exception description.
     */
    // ==========================================================================
    protected String get_asynch_idl_cmd(Request request, String idl_cmd) {
        return deviceProxyDAO.get_asynch_idl_cmd(this, request, idl_cmd);
    }

    // ==========================================================================
    /**
     * Check Asynchronous call reply.
     *
     * @param id asynchronous call id (returned by read_attribute_asynch).
     */
    // ==========================================================================
    protected void check_asynch_reply(Request request, int id, String idl_cmd) throws DevFailed, AsynReplyNotArrived {
        deviceProxyDAO.check_asynch_reply(this, request, id, idl_cmd);
    }

    // ==========================================================================
    /**
     * Read Asynchronous read_attribute reply.
     *
     * @param id      asynchronous call id (returned by read_attribute_asynch).
     * @param timeout number of millisonds to wait reply before throw an excption.
     */
    // ==========================================================================
    public DeviceAttribute[] read_attribute_reply(int id, int timeout) throws DevFailed, AsynReplyNotArrived {
        return deviceProxyDAO.read_attribute_reply(this, id, timeout);
    }

    // ==========================================================================
    /**
     * Read Asynchronous read_attribute reply.
     *
     * @param id asynchronous call id (returned by read_attribute_asynch).
     */
    // ==========================================================================
    public DeviceAttribute[] read_attribute_reply(int id) throws DevFailed, AsynReplyNotArrived {
        return deviceProxyDAO.read_attribute_reply(this, id);
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute using callback for reply.
     *
     * @param attname attribute name.
     * @param cb      a CallBack object instance.
     */
    // ==========================================================================
    public void read_attribute_asynch(String attname, CallBack cb) throws DevFailed {
        deviceProxyDAO.read_attribute_asynch(this, attname, cb);
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute using callback for reply.
     *
     * @param attnames attribute names.
     * @param cb       a CallBack object instance.
     */
    // ==========================================================================
    public void read_attribute_asynch(String[] attnames, CallBack cb) throws DevFailed {
        deviceProxyDAO.read_attribute_asynch(this, attnames, cb);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     *
     * @param attr Attribute value (name, writing value...)
     */
    // ==========================================================================
    public int write_attribute_asynch(DeviceAttribute attr) throws DevFailed {
        return deviceProxyDAO.write_attribute_asynch(this, attr);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     *
     * @param attr   Attribute value (name, writing value...)
     * @param forget forget the response if true
     */
    // ==========================================================================
    public int write_attribute_asynch(DeviceAttribute attr, boolean forget) throws DevFailed {
        return deviceProxyDAO.write_attribute_asynch(this, attr, forget);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     *
     * @param attribs Attribute values (name, writing value...)
     */
    // ==========================================================================
    public int write_attribute_asynch(DeviceAttribute[] attribs) throws DevFailed {
        return deviceProxyDAO.write_attribute_asynch(this, attribs);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     *
     * @param attribs Attribute values (name, writing value...)
     * @param forget  forget the response if true
     */
    // ==========================================================================
    public int write_attribute_asynch(DeviceAttribute[] attribs, boolean forget) throws DevFailed {
        return deviceProxyDAO.write_attribute_asynch(this, attribs, forget);
    }

    // ==========================================================================
    /**
     * check for Asynchronous write_attribute reply.
     *
     * @param id asynchronous call id (returned by read_attribute_asynch).
     */
    // ==========================================================================
    public void write_attribute_reply(int id) throws DevFailed, AsynReplyNotArrived {
        deviceProxyDAO.write_attribute_reply(this, id);
    }

    // ==========================================================================
    /**
     * check for Asynchronous write_attribute reply.
     *
     * @param id      asynchronous call id (returned by write_attribute_asynch).
     * @param timeout number of millisonds to wait reply before throw an excption.
     */
    // ==========================================================================
    public void write_attribute_reply(int id, int timeout) throws DevFailed, AsynReplyNotArrived {
        deviceProxyDAO.write_attribute_reply(this, id, timeout);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute using callback for reply.
     *
     * @param attr Attribute values (name, writing value...)
     * @param cb   a CallBack object instance.
     */
    // ==========================================================================
    public void write_attribute_asynch(DeviceAttribute attr, CallBack cb) throws DevFailed {
        deviceProxyDAO.write_attribute_asynch(this, attr, cb);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute using callback for reply.
     *
     * @param attribs Attribute values (name, writing value...)
     * @param cb      a CallBack object instance.
     */
    // ==========================================================================
    public void write_attribute_asynch(DeviceAttribute[] attribs, CallBack cb) throws DevFailed {
        deviceProxyDAO.write_attribute_asynch(this, attribs, cb);
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     *
     * @param reply_model ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public int pending_asynch_call(int reply_model) {
        return deviceProxyDAO.pending_asynch_call(this, reply_model);
    }

    // ==========================================================================
    /**
     * Fire callback methods for all asynchronous requests(cmd and attr) with
     * already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies() {
        deviceProxyDAO.get_asynch_replies(this);
    }

    // ==========================================================================
    /**
     * Fire callback methods for all asynchronous requests(cmd and attr) with
     * already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies(int timeout) {
        deviceProxyDAO.get_asynch_replies(this, timeout);
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
        deviceProxyDAO.add_logging_target(this, target_type + "::" + target_name);
    }

    // ==========================================================================
    /**
     * Adds a new logging target to the device.
     *
     * @param target The target for logging (e.g. file::/tmp/logging_device).
     */
    // ==========================================================================
    public void add_logging_target(String target) throws DevFailed {
        deviceProxyDAO.add_logging_target(this, target);
    }

    // ==========================================================================
    /**
     * Removes a new logging target to the device.
     */
    // ==========================================================================
    public void remove_logging_target(String target_type, String target_name) throws DevFailed {
        deviceProxyDAO.remove_logging_target(this, target_type, target_name);
    }

    // ==========================================================================
    /**
     * get logging target from the device.
     */
    // ==========================================================================
    public String[] get_logging_target() throws DevFailed {
        return deviceProxyDAO.get_logging_target(this);
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
        return deviceProxyDAO.get_logging_level(this);
    }

    // ==========================================================================
    /**
     * Set logging level from the device.
     *
     * @param level device's logging level: (ApiDefs.LOGGING_OFF,
     *              ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
     *              ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
     */
    // ==========================================================================
    public void set_logging_level(int level) throws DevFailed {
        deviceProxyDAO.set_logging_level(this, level);
    }


    // ==========================================================================
    //	Locking Device 4 commands
    // ==========================================================================

    // ==========================================================================
    /**
     * Lock the device
     */
    // ==========================================================================
    public void lock() throws DevFailed {
        this.lock(TangoConst.DEFAULT_LOCK_VALIDITY);
    }
    // ==========================================================================
    /**
     * Lock the device
     *
     * @param    validity    Lock validity (in seconds)
     */
    // ==========================================================================
    public void lock(int validity) throws DevFailed {
        deviceProxyDAO.lock(this, validity);
        proxy_lock_cnt++;
    }
    // ==========================================================================
    /**
     * Unlock the device
     *
     * @return the device lock counter
     */
    // ==========================================================================
    public int unlock() throws DevFailed {
        int n = deviceProxyDAO.unlock(this); // lock counter for the device itself (not the proxy)
        proxy_lock_cnt--;
        return n;
    }
    // ==========================================================================
    /**
     * Returns true if the device is locked
     */
    // ==========================================================================
    public boolean isLocked() throws DevFailed {
        return deviceProxyDAO.isLocked(this);
    }
    // ==========================================================================
    /**
     * Returns true if the device is locked by this process
     */
    // ==========================================================================
    public boolean isLockedByMe() throws DevFailed {
        return deviceProxyDAO.isLockedByMe(this);
    }

    // ==========================================================================
    /**
     * Returns the device lock status
     */
    // ==========================================================================
    public String getLockerStatus() throws DevFailed {
        return deviceProxyDAO.getLockerStatus(this);
    }
    // ==========================================================================
    /**
     * Returns the device lock info
     */
    // ==========================================================================
    public LockerInfo getLockerInfo() throws DevFailed {
        return deviceProxyDAO.getLockerInfo(this);
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
        return deviceProxyDAO.dev_inform(this);
    }

    // ==========================================================================
    /**
     * Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
     *
     * @param mode RPC protocol mode to be seted (TangoApi.TacoDevice.<b>D_TCP</b>
     *             or TangoApi.TacoDevice.<b>D_UDP</b>).
     */
    // ==========================================================================
    public void set_rpc_protocol(int mode) throws DevFailed {
        deviceProxyDAO.set_rpc_protocol(this, mode);
    }

    // ==========================================================================
    /**
     * @return mode RPC protocol mode used (TangoApi.TacoDevice.<b>D_TCP</b>
     *         or TangoApi.TacoDevice.<b>D_UDP</b>).
     */
    // ==========================================================================
    public int get_rpc_protocol() throws DevFailed {
        return deviceProxyDAO.get_rpc_protocol(this);
    }
    // ===================================================================
    /**
     * Query database to get a list of device using the specified device as
     * 		as root for forwarded attributes
     * @return a list of device using the specified device as as root for forwarded attributes
     * @throws DevFailed
     */
    // ===================================================================
    public List<ForwardedAttributeDatum> getForwardedAttributeInfoForDevice() throws DevFailed {
        if (db_dev==null)
            db_dev = new DbDevice(name());
        return db_dev.getForwardedAttributeInfoForDevice();
    }




    // ===================================================================
    /*
     * Pipe related methods
     */
    // ===================================================================

    // ===================================================================
    /**
     * Query device for pipe name list
     * @return  pipe name list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<String> getPipeNames() throws DevFailed {
        List<PipeInfo> infoList = deviceProxyDAO.getPipeConfig(this);
        ArrayList<String>   pipeNames = new ArrayList<String>();
        for (PipeInfo info : infoList)
            pipeNames.add(info.getName());
        return pipeNames;
    }
    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig() throws DevFailed {
        return deviceProxyDAO.getPipeConfig(this);
    }
    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @param pipeName pipe name.
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public PipeInfo getPipeConfig(String pipeName) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(1);
        list.add(pipeName);
        List<PipeInfo>  infoList = getPipeConfig(list);
        if (infoList.isEmpty())
            return null;
        else
            return infoList.get(0);
    }
    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @param pipeNames pipe names.
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig(String[] pipeNames) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(pipeNames.length);
        Collections.addAll(list, pipeNames);
        return getPipeConfig(list);
    }
    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @param pipeNames pipe names.
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig(List<String> pipeNames) throws DevFailed {
        return deviceProxyDAO.getPipeConfig(this, pipeNames);
    }
    // ===================================================================
    /**
     * Set device pipe configuration
     * @param pipeInfo info containing pipe name, description, label,....
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public void setPipeConfig(PipeInfo pipeInfo) throws DevFailed {
        ArrayList<PipeInfo> infoList = new ArrayList<PipeInfo>(1);
        infoList.add(pipeInfo);
        setPipeConfig(infoList);
    }
    // ===================================================================
    /**
     * Set device pipe configuration
     * @param pipeInfoList info list containing pipe name, description, label,....
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public void setPipeConfig(PipeInfo[] pipeInfoList) throws DevFailed {
        ArrayList<PipeInfo> infoList = new ArrayList<PipeInfo>(pipeInfoList.length);
        Collections.addAll(infoList, pipeInfoList);
        setPipeConfig(infoList);
    }
    // ===================================================================
    /**
     * Set device pipe configuration
     * @param pipeInfoList info list containing pipe name, description, label,....
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public void setPipeConfig(List<PipeInfo> pipeInfoList) throws DevFailed {
        deviceProxyDAO.setPipeConfig(this, pipeInfoList);
    }
    // ===================================================================
    /**
     * Read specified pipe and returns read data
     * @param pipeName pipe name
     * @return data read from specified pipe.
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public DevicePipe readPipe(String pipeName) throws DevFailed {
        return deviceProxyDAO.readPipe(this, pipeName);
    }
    // ===================================================================
    /**
     * Write data in specified pipe
     * @param pipeName pipe name
     * @param pipeBlob data to be written
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public void writePipe(String pipeName, PipeBlob pipeBlob) throws DevFailed {
        deviceProxyDAO.writePipe(this, new DevicePipe(pipeName, pipeBlob));
    }
    // ===================================================================
    /**
     * Write data in specified pipe
     * @param devicePipe data to be written (contains the pipe name)
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public void writePipe(DevicePipe devicePipe) throws DevFailed {
        deviceProxyDAO.writePipe(this, devicePipe);
    }
    // ===================================================================
    /**
     * Write and Read data in specified pipe
     * @param pipeName pipe name
     * @param pipeBlob data to be written
     * @return data read from specified pipe.
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public DevicePipe writeReadPipe(String pipeName, PipeBlob pipeBlob) throws DevFailed {
        return writeReadPipe(new DevicePipe(pipeName, pipeBlob));
    }
    // ===================================================================
    /**
     * Write and Read data in specified pipe
     * @param devicePipe data to be written (contains the pipe name)
     * @return data read from specified pipe.
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public DevicePipe writeReadPipe(DevicePipe devicePipe) throws DevFailed {
        return deviceProxyDAO.writeReadPipe(this, devicePipe);
    }
    // ===================================================================
    // ===================================================================




    // ==========================================================================
    /*
     * Event related methods
     */
    // ==========================================================================
    private static boolean useEvents = true;
    private static boolean useEventsChecked = false;

    private boolean useEvents() {
        if (!useEventsChecked) {
            String s = System.getenv("UseEvents");
            if (s!=null && s.equals("false"))
                useEvents = false;
            useEventsChecked = true;
        }
        return useEvents;
    }
    // ==========================================================================
    /**
     * Subscribe to an event.
     *
     * @param attr_name attribute name.
     * @param event     event name.
     * @param callback  event callback.
     */
    // ==========================================================================
    public int subscribe_event(String attr_name, int event, CallBack callback, String[] filters)
            throws DevFailed {
        if (!useEvents())
            Except.throw_exception("NO_EVENT", "Event system not available", "DeviceProxy.subscribe_event()");
        if (event==TangoConst.INTERFACE_CHANGE)
            Except.throw_exception("BAD_EVENT",
                    TangoConst.eventNames[event] + " cannot be applied to an attribute");
        return deviceProxyDAO.subscribe_event(this, attr_name, event, callback, filters, false);
    }

    // ==========================================================================
    /**
     * Subscribe to an event on a device(different to Interface Change Event).
     *
     * @param attr_name attribute name.
     * @param event     event name.
     * @param callback  event callback.
     * @param stateless If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(String attr_name, int event, CallBack callback, String[] filters, boolean stateless)
            throws DevFailed {
        if (!useEvents())
            Except.throw_exception("NO_EVENT", "Event system not available");
        if (event==TangoConst.INTERFACE_CHANGE)
            Except.throw_exception("BAD_EVENT",
                    TangoConst.eventNames[event] + " cannot be applied to an attribute");
        return deviceProxyDAO.subscribe_event(this, attr_name, event, callback, filters, stateless);
    }
    // ==========================================================================
    /**
     * Subscribe to event  on a device(different to Interface Change Event) to be stored in an event queue.
     *
     * @param attr_name attribute name.
     * @param event     event name.
     * @param max_size  event queue maximum size.
     * @param stateless If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(String attr_name, int event, int max_size, String[] filters, boolean stateless)
            throws DevFailed {
        if (!useEvents())
            Except.throw_exception("NO_EVENT", "Event system not available");
        if (event==TangoConst.INTERFACE_CHANGE)
            Except.throw_exception("BAD_EVENT",
                    TangoConst.eventNames[event] + " cannot be applied to a device");
        return deviceProxyDAO.subscribe_event(this, attr_name, event, max_size, filters, stateless);
    }
    // ==========================================================================
    // ==========================================================================
    /**
     * Subscribe to an event (Interface Change Event).
     *
     * @param event     event name.
     * @param callback  event callback.
     * @param stateless If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(int event, CallBack callback, boolean stateless) throws DevFailed {
        if (!useEvents())
            Except.throw_exception("NO_EVENT", "Event system not available");
        if (event!=TangoConst.INTERFACE_CHANGE)
            Except.throw_exception("BAD_EVENT",
                    TangoConst.eventNames[event] + " cannot be applied to a device");

        return deviceProxyDAO.subscribe_event(this, event, callback, stateless);
    }
    // ==========================================================================
    /**
     * Subscribe to an event.
     *
     * @param event     event name.
     * @param max_size  event queue maximum size.
     * @param stateless If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(int event, int max_size, boolean stateless) throws DevFailed {
        if (!useEvents())
            Except.throw_exception("NO_EVENT", "Event system not available");
        return deviceProxyDAO.subscribe_event(this, event, max_size, stateless);
    }

    // ==========================================================================
    // ==========================================================================
    public void setEventQueue(EventQueue eq) {
        event_queue = eq;
    }

    // ==========================================================================
    // ==========================================================================
    public EventQueue getEventQueue() {
        return event_queue;
    }
    // ==========================================================================

    /**
     * returns the number of EventData in queue.
     */
    // ==========================================================================
    public int get_event_queue_size() {
        return event_queue.size();
    }
    // ==========================================================================
    /**
     * returns the number of EventData in queue for specifed type.
     *
     * @param    event_type    Specified event type.
     */
    // ==========================================================================
    public int get_event_queue_size(int event_type) {
        return event_queue.size(event_type);
    }
    // ==========================================================================
    /**
     * returns next EventData in queue.
     */
    // ==========================================================================
    public EventData get_next_event() throws DevFailed {
        return event_queue.getNextEvent();
    }
    // ==========================================================================
    /**
     * returns next event in queue for specified type.
     *
     * @param    event_type    Specified event type.
     */
    // ==========================================================================
    public EventData get_next_event(int event_type) throws DevFailed {
        return event_queue.getNextEvent(event_type);
    }
    // ==========================================================================
    /**
     * returns number of milliseconds since EPOCH for the last EventData in queue.
     */
    // ==========================================================================
    public synchronized long get_last_event_date() throws DevFailed {
        return event_queue.getLastEventDate();
    }
    // ==========================================================================
    /**
     * returns all EventData in queue.
     */
    // ==========================================================================
    public EventData[] get_events() {
        return event_queue.getEvents();
    }
    // ==========================================================================
    /**
     * returns all event in queue for specified type.
     *
     * @param    event_type    Specified event type.
     */
    // ==========================================================================
    public EventData[] get_events(int event_type) {
        return event_queue.getEvents(event_type);
    }
    // ==========================================================================
    /**
     * Unsubscribe to an event.
     *
     * @param event_id event identifier.
     */
    // ==========================================================================
    public void unsubscribe_event(int event_id) throws DevFailed {
        deviceProxyDAO.unsubscribe_event(this, event_id);
    }

    public IDeviceProxyDAO getDeviceProxy() {
        return deviceProxyDAO;
    }


    public void setDeviceProxy(IDeviceProxyDAO deviceProxy) {
        this.deviceProxyDAO = deviceProxy;
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

    public void set_evt_import_info(DbEventImportInfo info) {
        evt_import_info = info;
    }

    //===============================================================
    /**
     * Check if an item is duplicated in specified array.
     * Then if duplication found, it throws an exception.
     * Used to do not have several same attribute names in a list
     *
     * @param list specified list to be checked.
     * @param orig method calling this method (used for DevFailed)
     * @throws DevFailed in case of duplication.
     */
    //===============================================================
    private static void checkDuplication(String[] list, String orig) throws DevFailed {
        Vector<String> dupli = new Vector<String>();
        //	For each string
        for (int i = 0 ; i<list.length ; i++) {
            String str = list[i];
            //	Check if it is a duplication
            for (int j = i + 1 ; j<list.length ; j++) {
                if (list[j].equalsIgnoreCase(str))
                    //	Check if not already found
                    if (dupli.indexOf(str)<0)
                        dupli.add(str);
            }
        }

        if (dupli.size()>0) {
            String message =
                    "Several times the same attribute in required attribute list: ";
            for (int i = 0 ; i<dupli.size() ; i++) {
                message += dupli.get(i);
                if (i<dupli.size() - 1) {
                    message += ", ";
                }
            }
            Except.throw_exception("",
                    message, orig);
        }
    }

    //==========================================================================
    /**
     * called at the death of  the object.
     * (Not referenced and Garbage collector called)
     */
    //==========================================================================
    protected void finalize() {
        if (proxy_lock_cnt>0) {
            try {
                unlock();
            } catch (DevFailed e) { /* */  }
        }
        try {
            super.finalize();
        } catch (Throwable e) { /* */  }
    }


    //===================================================================
    //===================================================================
    private int getTangoVersionFromZmqEventSubscriptionChange() throws DevFailed {
        try {
            DeviceData  argIn = new DeviceData();
            argIn.insert(new String[] { "info" });
            DeviceData  argOut = get_adm_dev().command_inout("ZmqEventSubscriptionChange", argIn);
            DevVarLongStringArray lsa = argOut.extractLongStringArray();
            if (lsa.lvalue.length==0)
                return -1;
            else
                return lsa.lvalue[0];
        }
        catch (DevFailed e) {
            if (e.errors[0].reason.equals("API_CommandNotFound"))
                return -1;
            else
                throw e;
        }
    }
    //===================================================================
    //===================================================================
    public int getTangoVersion() throws DevFailed {

        //  Get idl for administrative device
        int adminIdl = get_adm_dev().get_idl_version();

        //  Check with ZmqEventSubscriptionChange command
        //  since 9.1.0 this command returns Tango release in info mode
        int version = getTangoVersionFromZmqEventSubscriptionChange();
        if (version>900)    //  Found
            return version;

        //  Get the command list
        CommandInfo[] commandInfoList = get_adm_dev().command_list_query();

        //  Check between idl and commands
        switch (adminIdl) {
            case 1:
                return 100;
            case 2:
                return 200;
            case 3:
                //  IDL 3 is for Tango 5 and 6. Unfortunately,
                //  there is no way from the client side to determine if it is Tango 5 or 6.
                //  The best we can do is to get the info that it is Tango 5.2 (or above)
                for (CommandInfo commandInfo : commandInfoList) {
                    if (commandInfo.cmd_name.equals("QueryWizardClassProperty"))
                        return 520;
                }
                //  Not found
                return 500;
            case 4:
                //  try if tango 8.1 (Confirm for several events)
                for (CommandInfo commandInfo : commandInfoList) {
                    if (commandInfo.cmd_name.equals("EventConfirmSubscription"))
                        return 810;
                }
                //  try if tango 8.0 (ZMQ system)
                for (CommandInfo commandInfo : commandInfoList) {
                    if (commandInfo.cmd_name.equals("ZmqEventSubscriptionChange"))
                        return 800;
                }
                return 700;
             case 5:
			 	return 900;
       }

        //  Not found
        return 0;
    }

    //===================================================================
    //===================================================================
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            DeviceInfo deviceInfo = get_info();
            sb.append(deviceInfo).append('\n');
            DevInfo_3 devInfo3 = info_3();
            sb.append("Class:       ").append(devInfo3.dev_class).append('\n');
            sb.append("Server:      ").append(devInfo3.server_id).append('\n');
            sb.append("Host:        ").append(devInfo3.server_host).append('\n');
            sb.append(devInfo3.doc_url).append('\n');
            sb.append('\n');
            sb.append("IDL:   ").append(get_idl_version()).append('\n');

            int release = getTangoVersion();
            sb.append("Tango: ").append(String.format("%1.1f", (0.01 * release)));
        } catch (DevFailed e) {
            sb.append("\n").append(e.errors[0].desc);
        }
        return sb.toString();
    }

    //==========================================================================
    /**
     * Just a main method to check API methods.
     */
    //==========================================================================
    public static void main(String args[]) {
        String deviceName = null;
        String commandName = null;
        try {
            commandName = args[0];
            deviceName = args[1];
        } catch (final Exception e) {
            if (commandName==null) {
                System.out.println("Usage :");
                System.out.println("fr.esrf.TangoApi.DeviceProxy  commandName deviceName");
                System.out.println("	- info : Display device info.");
                System.out.println("	- commandName : command name (ping, state, status, unexport...)");
                System.out.println("	- deviceName : device name to send command.");
            } else {
                System.out.println("Device name ?");
            }
            System.exit(0);
        }
        try {
            // Check if wildcard
            String[] devnames;
            DeviceProxy[] deviceProxies;
            if (!deviceName.contains("*")) {
                devnames = new String[1];
                devnames[0] = deviceName;
            } else {
                devnames = ApiUtil.get_db_obj().getDevices(deviceName);
            }

            // Create DeviceProxy Objects
            deviceProxies = new DeviceProxy[devnames.length];
            for (int i = 0 ; i<devnames.length ; i++) {
                deviceProxies[i] = new DeviceProxy(devnames[i]);
            }

//            switch (commandName) {
//                case "info":
                if (commandName.equals("info")) {
                    for (DeviceProxy deviceProxy : deviceProxies) {
                        System.out.println(deviceProxy + "\n");
                    }
//                    break;
//                case "ping":
                }
                else
                if (commandName.equals("ping")) {
                    // noinspection InfiniteLoopStatement
                    while (true) {
                        for (int i = 0; i < deviceProxies.length; i++) {
                            try {
                                final long t = deviceProxies[i].ping();
                                System.out.println(devnames[i] + " is alive  (" + t / 1000 + " ms)");
                            } catch (final DevFailed e) {
                                System.out.println(devnames[i] + "  " + e.errors[0].desc);
                            }
                        }
                        if (deviceProxies.length > 1) {
                            System.out.println();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (final InterruptedException e) { /* */ }
                    }
//                    break;
//                case "status":
                }
                else
                if (commandName.equals("status")) {
                    for (int i = 0; i < deviceProxies.length; i++) {
                        try {
                            System.out.println(devnames[i] + " - " + deviceProxies[i].status());
                        } catch (final DevFailed e) {
                            System.out.println(devnames[i] + "  " + e.errors[0].desc);
                        }
                    }
//                    break;
//                case "state":
                }
                else
                if (commandName.equals("state")) {
                    for (int i = 0; i < deviceProxies.length; i++) {
                        try {
                            System.out
                                    .println(devnames[i] + " is " + ApiUtil.stateName(deviceProxies[i].state()));
                        /*
                        * DeviceAttribute da = dev[i].read_attribute("State");
                        * DevState st = da.extractDevStateArray()[0];
                        * System.out.println(devnames[i] + " is " +
                        * ApiUtil.stateName(st));
                        */
                        } catch (final DevFailed e) {
                            System.out.println(devnames[i] + "  " + e.errors[0].desc);
                        }
                    }
//                    break;
//                case "":
                }
                else
                if (commandName.equals("unexport")) {
                    for (int i = 0; i < deviceProxies.length; i++) {
                        try {
                            deviceProxies[i].unexport_device();
                            System.out.println(devnames[i] + " unexported !");
                        } catch (final DevFailed e) {
                            System.out.println(devnames[i] + "  " + e.errors[0].desc);
                        }
                    }
//                    break;
//                default:
                }
                else
                    System.out.println(commandName + " ?   Unknow command !");
//                    break;
//            }
        } catch (final DevFailed e) {
            Except.print_exception(e);
            // e.printStackTrace();
        }
    }
}

