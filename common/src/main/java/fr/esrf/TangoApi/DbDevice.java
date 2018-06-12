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
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class Description:
 * This class manage database connection for Tango device.
 * It is an api between user and IDL Device object.
 *
 * @author verdier
 * @version $Revision: 28928 $
 */


public class DbDevice implements java.io.Serializable {
    /**
     * Database object used for TANGO database access.
     */
    private Database database;

    /**
     * Device name used to access database if device not exported.
     */
    private String deviceName;

    //===================================================================

    /**
     * DbDevice constructor.
     * It will make a connection to the TANGO database.
     *
     * @param deviceName Name of the device to be imported.
     */
    //===================================================================
    public DbDevice(String deviceName) throws DevFailed {
        //	Check if database is  used.
        TangoUrl url = new TangoUrl(deviceName);
        if (!url.use_db) Except.throw_non_db_exception("Api_NonDatabaseDevice",         "Device " + deviceName + " do not use database",         "DbDevice.DbDevice()");

        //	Access the database
        database = ApiUtil.get_db_obj();
        this.deviceName = deviceName;
    }
    //===================================================================

    /**
     * DbDevice constructor.
     * It will make a connection to the TANGO database.
     *
     * @param deviceName Name of the device to be imported.
     * @param host       host where database is running.
     * @param port       port for database connection.
     */
    //===================================================================
    public DbDevice(String deviceName, String host, String port) throws DevFailed {
        //	Access the database
        //------------------------
        if (host==null || port==null) database = ApiUtil.get_db_obj();
        else database = ApiUtil.get_db_obj(host, port);
        this.deviceName = deviceName;
    }
    //==========================================================================

    /**
     * Query the database for the info of this device.
     *
     * @return the information in a DeviceInfo.
     */
    //==========================================================================
    public DeviceInfo get_info() throws DevFailed {
        return database.get_device_info(deviceName);
    }
    //==========================================================================

    /**
     * Query the database for the export info of this device.
     *
     * @return the information in a DbDevImportInfo.
     */
    //==========================================================================
    public DbDevImportInfo import_device() throws DevFailed {
        return database.import_device(deviceName);
    }
    //==========================================================================

    /**
     * Update the export info for this device in the database.
     *
     * @param devExportInfo Device information to export.
     */
    //==========================================================================
    public void export_device(DbDevExportInfo devExportInfo) throws DevFailed {
        database.export_device(devExportInfo);
    }
    //==========================================================================

    /**
     * Un export the device in database.
     */
    //==========================================================================
    public void unexport_device() throws DevFailed {
        database.unexport_device(deviceName);
    }
    //==========================================================================

    /**
     * Add/update this device to the database
     *
     * @param dbDevInfo The device name, class and server  specified in object.
     */
    //==========================================================================
    public void add_device(DbDevInfo dbDevInfo) throws DevFailed {
        database.add_device(dbDevInfo);
    }
    //==========================================================================

    /**
     * Delete this device from the database
     */
    //==========================================================================
    public void delete_device() throws DevFailed {
        database.delete_device(deviceName);
    }

    //==========================================================================
    //==========================================================================
    public String get_class() throws DevFailed {
        return database.get_class_for_device(deviceName);
    }

    //==========================================================================
    //==========================================================================
    public String[] get_class_inheritance() throws DevFailed {
        return database.get_class_inheritance_for_device(deviceName);
    }
    //==========================================================================

    /**
     * Set an alias for a device name
     *
     * @param aliasName alias name.
     */
    //==========================================================================
    public void put_alias(String aliasName) throws DevFailed {
        database.put_device_alias(deviceName, aliasName);
    }
    //==========================================================================

    /**
     * Get an alias for a device name
     */
    //==========================================================================
    public String get_alias() throws DevFailed {
        //	check if deviceName is already an alias.
        if (deviceName.contains("/")) { //	if alias -> replace alias by real device name deviceName = ApiUtil.get_db_obj().get_device_from_alias(deviceName);
        }

        //	Then query database for an alias for deviceName.
        return database.get_alias_from_device(deviceName);
    }
    //==========================================================================

    /**
     * Query the database for a list of device
     * properties for the pecified object.
     *
     * @param wildcard propertie's wildcard (* matches any charactere).
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public String[] get_property_list(String wildcard) throws DevFailed {
        return database.get_device_property_list(deviceName, wildcard);
    }
    //==========================================================================

    /**
     * Query the database for a list of device properties for this device.
     *
     * @param propertyNames list of property names.
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public DbDatum[] get_property(String[] propertyNames) throws DevFailed {
        return database.get_device_property(deviceName, propertyNames);
    }
    //==========================================================================

    /**
     * Query the database for a device property for this device.
     *
     * @param propertyName property name.
     * @return property in DbDatum objects.
     */
    //==========================================================================
    public DbDatum get_property(String propertyName) throws DevFailed {
        return database.get_device_property(deviceName, propertyName);
    }
    //==========================================================================

    /**
     * Query the database for a list of device properties for this device.
     * The property names are specified by the DbDatum array objects.
     *
     * @param properties list of property DbDatum objects.
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public DbDatum[] get_property(DbDatum[] properties) throws DevFailed {
        return database.get_device_property(deviceName, properties);
    }

    //==========================================================================

    /**
     * Insert or update a list of properties for this device
     * The property names and their values are specified by the DbDatum array.
     *
     * @param properties Properties names and values array.
     */
    //==========================================================================
    public void put_property(DbDatum[] properties) throws DevFailed {
        database.put_device_property(deviceName, properties);
    }
    //==========================================================================

    /**
     * Delete a list of properties for this device.
     *
     * @param propnames Property names.
     */
    //==========================================================================
    public void delete_property(String[] propnames) throws DevFailed {
        database.delete_device_property(deviceName, propnames);
    }
    //==========================================================================

    /**
     * Delete a property for this device.
     *
     * @param propertyName Property name.
     */
    //==========================================================================
    public void delete_property(String propertyName) throws DevFailed {
        database.delete_device_property(deviceName, propertyName);
    }
    //==========================================================================

    /**
     * Delete a list of properties for this device.
     *
     * @param properties Property DbDatum objects.
     */
    //==========================================================================
    public void delete_property(DbDatum[] properties) throws DevFailed {
        database.delete_device_property(deviceName, properties);
    }

    //============================================
    //	ATTRIBUTE PROPERTY MANAGEMENT
    //============================================

    //==========================================================================

    /**
     * Insert or update a list of attribute properties for this device.
     * The property names and their values are specified by the DbAttribute array.
     *
     * @param attr attribute names and properties (names and values) array.
     */
    //==========================================================================
    public void put_attribute_property(DbAttribute[] attr) throws DevFailed {
        database.put_device_attribute_property(deviceName, attr);
    }
    //==========================================================================

    /**
     * Insert or update an attribute properties for this device.
     * The property names and their values are specified by the DbAttribute array.
     *
     * @param attr attribute name and properties (names and values).
     */
    //==========================================================================
    public void put_attribute_property(DbAttribute attr) throws DevFailed {
        database.put_device_attribute_property(deviceName, attr);
    }
    //==========================================================================

    /**
     * Delete a list of properties for this object.
     *
     * @param attname   attribute name.
     * @param propnames Property names.
     */
    //==========================================================================
    public void delete_attribute_property(String attname, String[] propnames) throws DevFailed {
        database.delete_device_attribute_property(deviceName, attname, propnames);
    }
    //==========================================================================

    /**
     * Delete a property for this object.
     *
     * @param attname  attribute name.
     * @param propname Property name.
     */
    //==========================================================================
    public void delete_attribute_property(String attname, String propname) throws DevFailed {
        database.delete_device_attribute_property(deviceName, attname, propname);
    }
    //==========================================================================

    /**
     * Delete a list of properties for this object.
     *
     * @param attr specified attribute
     */
    //==========================================================================
    public void delete_attribute_property(DbAttribute attr) throws DevFailed {
        database.delete_device_attribute_property(deviceName, attr);
    }
    //==========================================================================

    /**
     * Delete a list of properties for this object.
     *
     * @param attr specified attributes
     */
    //==========================================================================
    public void delete_attribute_property(DbAttribute[] attr) throws DevFailed {
        database.delete_device_attribute_property(deviceName, attr);
    }

    //==========================================================================

    /**
     * Query the database for a list of device attributes
     *
     * @return attribute names.
     */
    //==========================================================================
    public String[] get_attribute_list() throws DevFailed {
        return database.get_device_attribute_list(deviceName);
    }
    //==========================================================================

    /**
     * Query the database for a list of device attribute
     * properties for this device.
     *
     * @param attributeNames attribute names.
     * @return properties in DbAttribute objects array.
     */
    //==========================================================================
    public DbAttribute[] get_attribute_property(String[] attributeNames) throws DevFailed {
        return database.get_device_attribute_property(deviceName, attributeNames);
    }
    //==========================================================================

    /**
     * Query the database for a device attribute
     * property for this device.
     *
     * @param attributeNames attribute name.
     * @return properties in DbAttribute objects.
     */
    //==========================================================================
    public DbAttribute get_attribute_property(String attributeNames) throws DevFailed {
        return database.get_device_attribute_property(deviceName, attributeNames);
    }
    //==========================================================================

    /**
     * Delete an attribute for this object.
     *
     * @param attributeNames attribute name.
     */
    //==========================================================================
    public void delete_attribute(String attributeNames) throws DevFailed {
        database.delete_device_attribute(deviceName, attributeNames);
    }
    //==========================================================================

    /**
     * Returns the polling period (in ms) for specified attribute.
     *
     * @param attributeNames specified attribute name.
     */
    //==========================================================================
    public int get_attribute_polling_period(String attributeNames) throws DevFailed {
        return get_polling_period(attributeNames, TangoConst.ATTRIBUTE);
    }
    //==========================================================================

    /**
     * Returns the polling period (in ms) for specified command.
     *
     * @param commandName specified attribute name.
     */
    //==========================================================================
    public int get_command_polling_period(String commandName) throws DevFailed {
        return get_polling_period(commandName, TangoConst.COMMAND);
    }
    //==========================================================================

    /**
     * Returns the polling period (in ms) for specified attribute.
     *
     * @param name specified attribute or command name.
     * @param src  TangoConst.COMMAND or TangoConst.ATTRIBUTE
     */
    //==========================================================================
    private int get_polling_period(String name, int src) throws DevFailed {
        //	Get the polling property
        String propname = (src==TangoConst.ATTRIBUTE) ? "polled_attr" : "polled_cmd";
        DbDatum datum = get_property(propname);
        if (datum.is_empty())
            Except.throw_exception("NOT_POLLED",
                    ((src==TangoConst.ATTRIBUTE) ? "Attribute " : "Command ") +
                            name + " not polled",
                    "DbDevice.get_polling_period()");


        String[] str = datum.extractStringArray();
        String _name = name.toLowerCase();
        for (int i = 0 ; i<str.length ; i += 2) {
            //	Check for name
            if (str[i].toLowerCase().equals(_name)) {
                //	not last index.
                if (i<str.length - 1) {
                    try {
                        return Integer.parseInt(str[i + 1]);
                    } catch (NumberFormatException e) {
                        Except.throw_exception("BAD_PARAM",
                                "Period value is not coherent",
                                "DbDevice.get_polling_period()");
                    }
                } else
                    Except.throw_exception("BAD_PARAM",
                            "Period value is not coherent",
                            "DbDevice.get_polling_period()");
            }
        }
        Except.throw_exception("NOT_POLLED",
                ((src==TangoConst.ATTRIBUTE) ? "Attribute " : "Command ") +
                        name + " not polled",
                "DbDevice.get_polling_period()");

        return -1;
    }


    //===========================================================

    /**
     * return the device name.
     */
    //===========================================================
    public String getName() {
        return deviceName;
    }
    //===========================================================

    /**
     * return the device name.
     */
    //===========================================================
    public String name() {
        return deviceName;
    }
    // ===================================================================
    /*
     * Pipe related methods
     */
    // ===================================================================

    // ===================================================================
    /**
     * Query the database for a list of device pipe properties
     * for the specified pipe.
     *
     * @param pipeName specified pipe.
     * @return a list of device pipe properties.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbPipe getPipeProperties(String pipeName) throws DevFailed {
        return database.getDevicePipeProperties(deviceName, pipeName);
    }
    // ===================================================================
    /**
     * Query the database for a device pipe property
     * for the specified pipe.
     *
     * @param pipeName     specified pipe.
     * @param propertyName specified property.
     * @return device pipe property.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbDatum getPipeProperty(String pipeName, String propertyName) throws DevFailed {
        DbPipe dbPipe = database.getDevicePipeProperties(deviceName, pipeName);
        DbDatum datum = dbPipe.getDatum(propertyName);
        if (datum==null) Except.throw_exception("TangoApi_PropertyNotFound",         "Property " + propertyName + " not found for pipe " + pipeName);
        return datum;
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified device.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param dbPipe pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putPipeProperty(DbPipe dbPipe) throws DevFailed {
        database.putDevicePipeProperty(deviceName, dbPipe);
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified device.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param dbPipes list of pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putPipeProperty(ArrayList<DbPipe> dbPipes) throws DevFailed {
        for (DbPipe dbPipe : dbPipes) database.putDevicePipeProperty(deviceName, dbPipe);
    }
    // ===================================================================
    /**
     * Query database for a list of pipes for specified device.
     *
     * @return a list of pipes defined in database for specified device.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<String> getPipeList() throws DevFailed {
        return getPipeList("*");
    }
    // ===================================================================
    /**
     * Query database for a list of pipes for specified device and specified wildcard.
     *
     * @param wildcard specified wildcard.
     * @return a list of pipes defined in database for specified device and specified wildcard.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<String> getPipeList(String wildcard) throws DevFailed {
        return database.getDevicePipeList(deviceName, wildcard);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     * @param pipeName     pipe name
     * @param propertyName property name
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deletePipeProperty(String pipeName, String propertyName) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(1);
        list.add(propertyName);
        deletePipeProperties(pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     * @param pipeName      pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deletePipeProperties(String pipeName, String[] propertyNames) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(propertyNames.length);
        Collections.addAll(list, propertyNames);
        deletePipeProperties(pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     * @param pipeName      pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deletePipeProperties(String pipeName, List<String> propertyNames) throws DevFailed {
        database.deleteDevicePipeProperties(deviceName, pipeName, propertyNames);
    }
    // ===================================================================
    /**
     * Delete specified pipe for specified device.
     *
     * @param pipeName pipe name
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deletePipe(String pipeName) throws DevFailed {
        database.deleteDevicePipe(deviceName, pipeName);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipe
     *
     * @param pipeName pipe name
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deleteAllPipeProperty(String pipeName) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(1);
        list.add(pipeName);
        deleteAllPipeProperty(list);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipes
     *
     * @param pipeNames pipe names
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deleteAllPipeProperty(String[] pipeNames) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(pipeNames.length);
        Collections.addAll(list, pipeNames);
        deleteAllPipeProperty(list);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipes
     *
     * @param pipeNames pipe names
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deleteAllPipeProperty(List<String> pipeNames) throws DevFailed {
        database.deleteAllDevicePipeProperty(deviceName, pipeNames);
    }
    // ===================================================================
    /**
     * Returns the property history for specified pipe.
     *
     * @param pipeName     pipe name
     * @param propertyName property Name
     * @return the property history for specified pipe.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<DbHistory> getPipePropertyHistory(String pipeName, String propertyName) throws DevFailed {
        return database.getDevicePipePropertyHistory(deviceName, pipeName, propertyName);
    }
    // ===================================================================
    /**
     * Query database to get a list of device using the specified device as
     * as root for forwarded attributes
     *
     * @return a list of device using the specified device as as root for forwarded attributes
     * @throws DevFailed
     */
    // ===================================================================
    public List<ForwardedAttributeDatum> getForwardedAttributeInfoForDevice() throws DevFailed {
        return database.getForwardedAttributeInfoForDevice(deviceName);
    }
}
