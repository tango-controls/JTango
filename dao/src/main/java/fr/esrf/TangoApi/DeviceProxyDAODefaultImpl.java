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
// $Revision: 30265 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.events.EventConsumerUtil;
import org.omg.CORBA.*;

import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.NamedDevFailed;
import fr.esrf.TangoDs.NamedDevFailedList;
import fr.esrf.TangoDs.TangoConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 *
 * @author verdier
 * @version $Revision: 30265 $
 */

public class DeviceProxyDAODefaultImpl extends ConnectionDAODefaultImpl implements ApiDefs,
	IDeviceProxyDAO {

    public DeviceProxyDAODefaultImpl() {
	    // super();
    }

    // ===================================================================
    /**
     * Default DeviceProxy constructor. It will do nothing
     */
    // ===================================================================
    public void init(final DeviceProxy deviceProxy) throws DevFailed {
    	// super.init(deviceProxy);
    }

    // ===================================================================
    /**
     * DeviceProxy constructor. It will import the device.
     * 
     * @param devname name of the device to be imported.
     */
    // ===================================================================
    public void init(final DeviceProxy deviceProxy, final String devname) throws DevFailed {
        // super.init(deviceProxy, devname);
        deviceProxy.setFull_class_name("DeviceProxy(" + name(deviceProxy) + ")");
    }

    // ===================================================================
    /**
     * DeviceProxy constructor. It will import the device.
     * 
     * @param devname name of the device to be imported.
     * @param check_access set check_access value
     */
    // ===================================================================
    public void init(final DeviceProxy deviceProxy, final String devname, final boolean check_access)
	    throws DevFailed {
        // super.init(deviceProxy, devname, check_access);
        deviceProxy.setFull_class_name("DeviceProxy(" + name(deviceProxy) + ")");
    }

    // ===================================================================
    /**
     * TangoDevice constructor. It will import the device.
     * 
     * @param devname name of the device to be imported.
     * @param ior ior string used to import device
     */
    // ===================================================================
    public void init(final DeviceProxy deviceProxy, final String devname, final String ior)
	    throws DevFailed {
        // super.init(deviceProxy, devname, ior, FROM_IOR);
        deviceProxy.setFull_class_name("DeviceProxy(" + name(deviceProxy) + ")");
    }

    // ===================================================================
    /**
     * TangoDevice constructor. It will import the device.
     * 
     * @param devname name of the device to be imported.
     * @param host host where database is running.
     * @param port port for database connection.
     */
    // ===================================================================
    public void init(final DeviceProxy deviceProxy, final String devname, final String host,
            final String port) throws DevFailed {
        // super.init(deviceProxy, devname, host, port);
        deviceProxy.setFull_class_name("DeviceProxy(" + name(deviceProxy) + ")");
    }

    // ==========================================================================
    // ==========================================================================
    public boolean use_db(final DeviceProxy deviceProxy) {
    	return deviceProxy.url.use_db;
    }

    // ==========================================================================
    // ==========================================================================
    private void checkIfUseDb(final DeviceProxy deviceProxy, final String origin) throws DevFailed {
        if (!deviceProxy.url.use_db) {
            Except.throw_non_db_exception("Api_NonDatabaseDevice", "Device " + name(deviceProxy)
                + " do not use database", "DeviceProxy(" + name(deviceProxy) + ")." + origin);
        }
    }

    // ==========================================================================
    // ==========================================================================
    public Database get_db_obj(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_db_obj()");
        return ApiUtil.get_db_obj(deviceProxy.url.host, deviceProxy.url.strPort);
    }

    // ===================================================================
    /**
     * Get connection on administration device.
     */
    // ===================================================================
    public void import_admin_device(final DeviceProxy deviceProxy, final DbDevImportInfo info)
            throws DevFailed {
        checkIfTango(deviceProxy, "import_admin_device()");
        /*
         * if (deviceProxy.device==null && deviceProxy.devname!=null)
         * build_connection(deviceProxy);
         */
        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            if (DeviceProxyFactory.exists(info.name)) {
                deviceProxy.setAdm_dev(DeviceProxyFactory.get(info.name));
            } else {
            // If not exists, create it with info
                deviceProxy.setAdm_dev(new DeviceProxy(info));
            }
        }
    }

    // ===================================================================
    /**
     * Get connection on administration device.
     */
    // ===================================================================
    public void import_admin_device(final DeviceProxy deviceProxy, final String origin)
            	    throws DevFailed {
        checkIfTango(deviceProxy, origin);

        build_connection(deviceProxy);

        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            deviceProxy.setAdm_dev(DeviceProxyFactory.get(
                    adm_name(deviceProxy), deviceProxy.getUrl().getTangoHost()));
        }
    }

    // ===========================================================
    /**
     * return the device name.
     */
    // ===========================================================
    public String name(final DeviceProxy deviceProxy) {
	    return get_name(deviceProxy);
    }

    // ===========================================================
    /**
     * return the device status read from CORBA attribute.
     */
    // ===========================================================
    public String status(final DeviceProxy deviceProxy) throws DevFailed {
	    return status(deviceProxy, ApiDefs.FROM_ATTR);
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
    public String status(final DeviceProxy deviceProxy, final boolean src) throws DevFailed {

        build_connection(deviceProxy);

        if (deviceProxy.url.protocol == TANGO) {
            String status = "Unknown";
            final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
            boolean done = false;
            for (int i = 0; i < retries && !done; i++) {
            try {
                //noinspection PointlessBooleanExpression
                if (src == ApiDefs.FROM_ATTR) {
                    status = deviceProxy.device.status();
                } else {
                    final DeviceData argout = deviceProxy.command_inout("Status");
                    status = argout.extractString();
                }
                done = true;
            } catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, i, e, this.getClass()
                    + ".status");
            }
            }
            return status;
        } else {
            return command_inout(deviceProxy, "DevStatus").extractString();
        }
    }

    // ===========================================================
    /**
     * return the device state read from CORBA attribute.
     */
    // ===========================================================
    public DevState state(final DeviceProxy deviceProxy) throws DevFailed {
	    return state(deviceProxy, ApiDefs.FROM_ATTR);
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
    public DevState state(final DeviceProxy deviceProxy, final boolean src) throws DevFailed {

        build_connection(deviceProxy);

        if (deviceProxy.url.protocol == TANGO) {
            DevState state = DevState.UNKNOWN;
            final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
            boolean done = false;
            for (int i = 0; i < retries && !done; i++) {
                try {
                    //noinspection PointlessBooleanExpression
                    if (src == ApiDefs.FROM_ATTR) {
                        state = deviceProxy.device.state();
                    } else {
                    final DeviceData argout = deviceProxy.command_inout("State");
                    state = argout.extractDevState();
                    }
                    done = true;
                } catch (final Exception e) {
                    manageExceptionReconnection(deviceProxy,
                            retries, i, e, this.getClass() + ".state");
                }
            }
            return state;
        } else {
            final DeviceData argout = command_inout(deviceProxy, "DevState");
            final short state = argout.extractShort();
            return T2Ttypes.tangoState(state);
        }
    }

    // ===========================================================
    /**
     * return the IDL device command_query for the specified command.
     * 
     * @param commandName command name to be used for the command_query
     * @return the command information..
     */
    // ===========================================================
    public CommandInfo command_query(final DeviceProxy deviceProxy,
                                     final String commandName) throws DevFailed {

        build_connection(deviceProxy);

        CommandInfo info = null;
        if (deviceProxy.url.protocol == TANGO) {
            // try 2 times for reconnection if requested
            final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
            for (int i=0 ; i<retries ; i++) {
            try {
                // Check IDL version
                if (deviceProxy.device_2 != null) {
                    final DevCmdInfo_2 tmp = deviceProxy.device_2.command_query_2(commandName);
                    info = new CommandInfo(tmp);
                } else {
                    final DevCmdInfo tmp = deviceProxy.device.command_query(commandName);
                    info = new CommandInfo(tmp);
                }
            } catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, i, e, this.getClass()
                    + ".command_query");
            }
            }
        } else {
            // TACO device
            info = deviceProxy.taco_device.commandQuery(commandName);
        }
        return info;
    }

    // ===========================================================
    // The following methods are an agrega of DbDevice
    // ===========================================================
    // ==========================================================================
    /**
     * Returns the class for the device
     */
    // ==========================================================================
    public String get_class(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_class()");
        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_class");

        final String classname = super.get_class_name(deviceProxy);
        if (classname != null) {
            return classname;
        } else {
            return deviceProxy.getDb_dev().get_class();
        }
    }

    // ==========================================================================
    /**
     * Returns the class inheritance for the device
     * <ul>
     * <li>[0] Device Class
     * <li>[1] Class from the device class is inherited.
     * <li>.....And so on
     * </ul>
     */
    // ==========================================================================
    public String[] get_class_inheritance(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_class_inheritance()");
        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_class_inheritance");
        return deviceProxy.getDb_dev().get_class_inheritance();
    }

    // ==========================================================================
    /**
     * Set an alias for a device name
     * 
     * @param aliasName  alias name.
     */
    // ==========================================================================
    public void put_alias(final DeviceProxy deviceProxy, final String aliasName) throws DevFailed {
        checkIfUseDb(deviceProxy, "put_alias()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "put_alias");
        deviceProxy.getDb_dev().put_alias(aliasName);
    }

    // ==========================================================================
    /**
     * Get alias for the device
     * 
     * @return the alias name if found.
     */
    // ==========================================================================
    public String get_alias(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_alias()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_alias");
        return deviceProxy.getDb_dev().get_alias();
    }

    // ==========================================================================
    /**
     * Query the database for the info of this device.
     * 
     * @return the information in a DeviceInfo.
     */
    // ==========================================================================
    public DeviceInfo get_info(final DeviceProxy deviceProxy) throws DevFailed {
		checkIfUseDb(deviceProxy, "get_info()");

		if (deviceProxy.getDb_dev() == null) {
	    	deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
		    	deviceProxy.url.strPort));
		}
		// checkIfTango("import_device");
		if (deviceProxy.url.protocol == TANGO) {
	    	return deviceProxy.getDb_dev().get_info();
		} else {
	    	return null;
		}
    }

    // ==========================================================================
    /**
     * Query the database for the export info of this device.
     * 
     * @return the information in a DbDevImportInfo.
     */
    // ==========================================================================
    public DbDevImportInfo import_device(final DeviceProxy deviceProxy) throws DevFailed {

        checkIfUseDb(deviceProxy, "import_device()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        // checkIfTango("import_device");
        if (deviceProxy.url.protocol == TANGO) {
            return deviceProxy.getDb_dev().import_device();
        } else {
            return new DbDevImportInfo(dev_inform(deviceProxy));
        }
    }

    // ==========================================================================
    /**
     * Update the export info for this device in the database.
     * 
     * @param devinfo Device information to export.
     */
    // ==========================================================================
    public void export_device(final DeviceProxy deviceProxy,
                              final DbDevExportInfo devinfo) throws DevFailed {
        checkIfTango(deviceProxy, "export_device");
        checkIfUseDb(deviceProxy, "export_device()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        deviceProxy.getDb_dev().export_device(devinfo);
    }

    // ==========================================================================
    /**
     * Unexport the device in database.
     */
    // ==========================================================================
    public void unexport_device(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfTango(deviceProxy, "unexport_device");
        checkIfUseDb(deviceProxy, "unexport_device()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        deviceProxy.getDb_dev().unexport_device();
    }

    // ==========================================================================
    /**
     * Add/update this device to the database
     * 
     * @param devinfo
     *            The device name, class and server specified in object.
     */
    // ==========================================================================
    public void add_device(final DeviceProxy deviceProxy, final DbDevInfo devinfo) throws DevFailed {
        checkIfTango(deviceProxy, "add_device");
        checkIfUseDb(deviceProxy, "add_device()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        deviceProxy.getDb_dev().add_device(devinfo);
    }

    // ==========================================================================
    /**
     * Delete this device from the database
     */
    // ==========================================================================
    public void delete_device(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfTango(deviceProxy, "delete_device");
        checkIfUseDb(deviceProxy, "delete_device()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        deviceProxy.getDb_dev().delete_device();
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
    public String[] get_property_list(final DeviceProxy deviceProxy,
                                      final String wildcard) throws DevFailed {
        checkIfTango(deviceProxy, "get_property_list");
        checkIfUseDb(deviceProxy, "get_property_list()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        return deviceProxy.getDb_dev().get_property_list(wildcard);
    }

    // ==========================================================================
    /**
     * Query the database for a list of device properties for this device.
     * 
     * @param propertyNames list of property names.
     * @return properties in DbDatum objects.
     */
    // ==========================================================================
    public DbDatum[] get_property(final DeviceProxy deviceProxy,
                                  final String[] propertyNames) throws DevFailed {
		if (deviceProxy.url.protocol == TANGO) {
	    	checkIfUseDb(deviceProxy, "get_property()");

	    	if (deviceProxy.getDb_dev() == null) {
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
				deviceProxy.url.strPort));
	    	}
	    	return deviceProxy.getDb_dev().get_property(propertyNames);
		} else {
			if (deviceProxy.taco_device == null && deviceProxy.devname != null) {
	    		build_connection(deviceProxy);
			}
	    	return deviceProxy.taco_device.get_property(propertyNames);
		}
    }

    // ==========================================================================
    /**
     * Query the database for a device property for this device.
     * 
     * @param propertyName  property name.
     * @return property in DbDatum objects.
     */
    // ==========================================================================
    public DbDatum get_property(final DeviceProxy deviceProxy,
                                final String propertyName)  throws DevFailed {
        if (deviceProxy.url.protocol == TANGO) {
            checkIfUseDb(deviceProxy, "get_property()");

            if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
            }
            return deviceProxy.getDb_dev().get_property(propertyName);
        } else {
            final String[] propnames = { propertyName };
            return deviceProxy.taco_device.get_property(propnames)[0];
        }
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
    public DbDatum[] get_property(final DeviceProxy deviceProxy,
                                  final DbDatum[] properties) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_property");
        return deviceProxy.getDb_dev().get_property(properties);
    }

    // ==========================================================================
    /**
     * Insert or update a property for this device The property name and its
     * value are specified by the DbDatum
     * 
     * @param prop  Property name and value
     */
    // ==========================================================================
    public void put_property(final DeviceProxy deviceProxy, final DbDatum prop) throws DevFailed {
        checkIfUseDb(deviceProxy, "put_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "put_property");

        final DbDatum[] properties = new DbDatum[1];
        properties[0] = prop;
        put_property(deviceProxy, properties);
    }

    // ==========================================================================
    /**
     * Insert or update a list of properties for this device The property names
     * and their values are specified by the DbDatum array.
     * 
     * @param properties Properties names and values array.
     */
    // ==========================================================================
    public void put_property(final DeviceProxy deviceProxy,
                             final DbDatum[] properties) throws DevFailed {
        checkIfUseDb(deviceProxy, "put_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "put_property");
        deviceProxy.getDb_dev().put_property(properties);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this device.
     * 
     * @param propertyNames  Property names.
     */
    // ==========================================================================
    public void delete_property(final DeviceProxy deviceProxy,
                                final String[] propertyNames) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_property");
        deviceProxy.getDb_dev().delete_property(propertyNames);
    }

    // ==========================================================================
    /**
     * Delete a property for this device.
     * 
     * @param propertyName Property name.
     */
    // ==========================================================================
    public void delete_property(final DeviceProxy deviceProxy,
                                final String propertyName) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_property");
        deviceProxy.getDb_dev().delete_property(propertyName);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this device.
     * 
     * @param properties  Property DbDatum objects.
     */
    // ==========================================================================
    public void delete_property(final DeviceProxy deviceProxy,
                                final DbDatum[] properties) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_property");
        deviceProxy.getDb_dev().delete_property(properties);
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
    public String[] get_attribute_list(final DeviceProxy deviceProxy) throws DevFailed {
        build_connection(deviceProxy);

        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int oneTry=1 ; oneTry<=retries ; oneTry++) {
            try {
                if (deviceProxy.url.protocol == TANGO) {
                    // Read All attribute config
                    final String[] wildcard = new String[1];

                    if (deviceProxy.device_3 != null) {
                        wildcard[0] = TangoConst.Tango_AllAttr_3;
                    } else {
                       wildcard[0] = TangoConst.Tango_AllAttr;
                    }
                    final AttributeInfo[] ac = get_attribute_info(deviceProxy, wildcard);
                    final String[] result = new String[ac.length];
                    for (int i = 0; i < ac.length; i++) {
                        result[i] = ac[i].name;
                    }
                    return result;
                } else {
                    return deviceProxy.taco_device.get_attribute_list();
                }
            }
            catch (DevFailed e) {
                if (oneTry>=retries)
                    throw e;
                //  else retry
            }
        }
        return null;    //  cannot occur
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
    public void put_attribute_property(final DeviceProxy deviceProxy,
                                       final DbAttribute[] attr) throws DevFailed {
        checkIfUseDb(deviceProxy, "put_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "put_attribute_property");
        deviceProxy.getDb_dev().put_attribute_property(attr);
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
    public void put_attribute_property(final DeviceProxy deviceProxy,
                                       final DbAttribute attr) throws DevFailed {
        checkIfUseDb(deviceProxy, "put_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "put_attribute_property");
        deviceProxy.getDb_dev().put_attribute_property(attr);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this object.
     * 
     * @param attributeName  attribute name.
     * @param propertyNames Property names.
     */
    // ==========================================================================
    public void delete_attribute_property(final DeviceProxy deviceProxy, final String attributeName,
	    final String[] propertyNames) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_attribute_property");
        deviceProxy.getDb_dev().delete_attribute_property(attributeName, propertyNames);
    }

    // ==========================================================================
    /**
     * Delete a property for this object.
     * 
     * @param attributeName
     *            attribute name.
     * @param propertyName
     *            Property name.
     */
    // ==========================================================================
    public void delete_attribute_property(final DeviceProxy deviceProxy, final String attributeName,
	                                      final String propertyName) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_attribute_property");
        deviceProxy.getDb_dev().delete_attribute_property(attributeName, propertyName);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this object.
     * 
     * @param attr DbAttribute objects specifying attributes.
     */
    // ==========================================================================
    public void delete_attribute_property(final DeviceProxy deviceProxy,
                                          final DbAttribute attr) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_attribute_property");
        deviceProxy.getDb_dev().delete_attribute_property(attr);
    }

    // ==========================================================================
    /**
     * Delete a list of properties for this object.
     * 
     * @param attr  DbAttribute objects array specifying attributes.
     */
    // ==========================================================================
    public void delete_attribute_property(final DeviceProxy deviceProxy,
                                          final DbAttribute[] attr) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_attribute_property");
        deviceProxy.getDb_dev().delete_attribute_property(attr);
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
    public DbAttribute[] get_attribute_property(final DeviceProxy deviceProxy,
	                                            final String[] attnames) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_attribute_property");
        return deviceProxy.getDb_dev().get_attribute_property(attnames);
    }

    // ==========================================================================
    /**
     * Query the database for a device attribute property for this device.
     * 
     * @param attributeName  attribute name.
     * @return property in DbAttribute object.
     */
    // ==========================================================================
    public DbAttribute get_attribute_property(final DeviceProxy deviceProxy,
                                              final String attributeName) throws DevFailed {
        checkIfUseDb(deviceProxy, "get_attribute_property()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_attribute_property");
        return deviceProxy.getDb_dev().get_attribute_property(attributeName);
    }

    // ==========================================================================
    /**
     * Delete an attribute for this object.
     * 
     * @param attributeName  attribute name.
     */
    // ==========================================================================
    public void delete_attribute(final DeviceProxy deviceProxy,
                                 final String attributeName) throws DevFailed {
        checkIfUseDb(deviceProxy, "delete_attribute()");

        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "delete_attribute");
        deviceProxy.getDb_dev().delete_attribute(attributeName);
    }

    // ===========================================================
    // Attribute Methods
    // ===========================================================
    // ==========================================================================
    /**
     * Get the attributes configuration for the specified device.
     * 
     * @param attributeNames attribute names to request config.
     * @return the attributes configuration.
     */
    // ==========================================================================
    public AttributeInfo[] get_attribute_info(final DeviceProxy deviceProxy,
                                              final String[] attributeNames) throws DevFailed {

        build_connection(deviceProxy);

        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int oneTry=1 ; oneTry<=retries ; oneTry++) {
            try {
                AttributeInfo[] result;
                AttributeConfig[] ac = new AttributeConfig[0];
                AttributeConfig_2[] ac_2 = null;
                if (deviceProxy.url.protocol == TANGO) {
                // Check IDL version
                if (deviceProxy.device_2 != null) {
                    ac_2 = deviceProxy.device_2.get_attribute_config_2(attributeNames);
                } else {
                    ac = deviceProxy.device.get_attribute_config(attributeNames);
                }
                } else {
                ac = deviceProxy.taco_device.get_attribute_config(attributeNames);
                }

                // Convert AttributeConfig(_2) object to AttributeInfo object
                final int size = ac_2 != null ? ac_2.length : ac.length;
                result = new AttributeInfo[size];
                for (int i = 0; i < size; i++) {
                if (ac_2 != null) {
                    result[i] = new AttributeInfo(ac_2[i]);
                } else {
                    result[i] = new AttributeInfo(ac[i]);
                }
                }
                return result;
            }
            catch (final DevFailed e) {
                if (oneTry>=retries) {
                    throw e;
                }
            }
            catch (final Exception e) {
                if (oneTry>=retries) {
                    ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(deviceProxy);
                    throw_dev_failed(deviceProxy, e, "get_attribute_config", true);
                }
            }
        }
        return null;
    }

    // ==========================================================================
    /**
     * Get the extended attributes configuration for the specified device.
     *
     * @param attributeNames attribute names to request config.
     * @return the attributes configuration.
     */
    // ==========================================================================
    public AttributeInfoEx[] get_attribute_info_ex(final DeviceProxy deviceProxy,
                                                   final String[] attributeNames) throws DevFailed {

        build_connection(deviceProxy);
        // try 2 times for reconnection if requested
        AttributeInfoEx[] result = null;
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int i=0 ; i<retries && !done ; i++) {
            try {
                AttributeConfig_5[] attributeConfigList_5 = null;
                AttributeConfig_3[] attributeConfigList_3 = null;
                AttributeConfig_2[] attributeConfigList_2 = null;
                AttributeConfig[]   attributeConfigList = null;
                if (deviceProxy.url.protocol == TANGO) {
                    // Check IDL version
                    if (deviceProxy.device_5 != null) {
                        attributeConfigList_5 =
                                deviceProxy.device_5.get_attribute_config_5(attributeNames);
                    }
                    else if (deviceProxy.device_3 != null) {
                        attributeConfigList_3 =
                                deviceProxy.device_3.get_attribute_config_3(attributeNames);
                    }
                    else if (deviceProxy.device_2 != null) {
                        attributeConfigList_2 =
                                deviceProxy.device_2.get_attribute_config_2(attributeNames);
                    }
                    else {
                        Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
                            "Not supported by the IDL version used by device",
                                deviceProxy.getFull_class_name() + ".get_attribute_info_ex()");
                    }
                }
                else {
                    attributeConfigList = deviceProxy.taco_device.get_attribute_config(attributeNames);
                }

                // Convert AttributeConfig(_3) object to AttributeInfo object
                int size;
                if (attributeConfigList_5 != null) {
                    size = attributeConfigList_5.length;
                }
                else if (attributeConfigList_3 != null) {
                    size = attributeConfigList_3.length;
                }
                else if (attributeConfigList_2 != null) {
                    size = attributeConfigList_2.length;
                }
                else {
                    size = attributeConfigList.length;
                }
                result = new AttributeInfoEx[size];
                for (int n=0 ; n<size ; n++) {
                    if (attributeConfigList_5 != null) {
                        result[n] = new AttributeInfoEx(attributeConfigList_5[n]);
                    }
                    else if (attributeConfigList_3 != null) {
                        result[n] = new AttributeInfoEx(attributeConfigList_3[n]);
                    }
                    else if (attributeConfigList_2 != null) {
                        result[n] = new AttributeInfoEx(attributeConfigList_2[n]);
                    }
                    else if (attributeConfigList != null) {
                        result[n] = new AttributeInfoEx(attributeConfigList[n]);
                    }
                }
                done = true;
            }
            catch (final DevFailed e) {
                manageExceptionReconnection(deviceProxy,
                        retries, i, e, this.getClass() + ".get_attribute_config_ex");
            }
            catch (final Exception e) {
                manageExceptionReconnection(deviceProxy,
                        retries, i, e, this.getClass()+ ".get_attribute_config_ex");
	    }
	}
	return result;
    }

    // ==========================================================================
    /**
     * Get the attributes configuration for the specified device.
     * 
     * @param attnames  attribute names to request config.
     * @return the attributes configuration.
     * @deprecated use get_attribute_info(String[] attnames)
     */
    // ==========================================================================
    @Deprecated
        public AttributeInfo[] get_attribute_config(final DeviceProxy deviceProxy,
            final String[] attnames) throws DevFailed {
        return get_attribute_info(deviceProxy, attnames);
    }

    // ==========================================================================
    /**
     * Get the attribute info for the specified device.
     * 
     * @param attname attribute name to request config.
     * @return the attribute info.
     */
    // ==========================================================================
    public AttributeInfo get_attribute_info(final DeviceProxy deviceProxy, final String attname)
	    throws DevFailed {
        final String[] attnames = ApiUtil.toStringArray(attname);
        final AttributeInfo[] ac = get_attribute_info(deviceProxy, attnames);
        return ac[0];
    }

    // ==========================================================================
    /**
     * Get the attribute info for the specified device.
     * 
     * @param attname  attribute name to request config.
     * @return the attribute info.
     */
    // ==========================================================================
    public AttributeInfoEx get_attribute_info_ex(final DeviceProxy deviceProxy, final String attname)
	    throws DevFailed {
        final String[] attributeNames = ApiUtil.toStringArray(attname);
        return get_attribute_info_ex(deviceProxy, attributeNames)[0];
    }

    // ==========================================================================
    /**
     * Get the attribute configuration for the specified device.
     * 
     * @param attname  attribute name to request config.
     * @return the attribute configuration.
     * @deprecated use get_attribute_info(String attname)
     */
    // ==========================================================================
    @Deprecated
        public AttributeInfo get_attribute_config(final DeviceProxy deviceProxy, final String attname)
            throws DevFailed {
        return get_attribute_info(deviceProxy, attname);
    }

    // ==========================================================================
    /**
     * Get all attributes info for the specified device.
     * 
     * @return all attributes info.
     */
    // ==========================================================================
    public AttributeInfo[] get_attribute_info(final DeviceProxy deviceProxy) throws DevFailed {

        build_connection(deviceProxy);

        final String[] attributeNames = new String[1];
        if (deviceProxy.device_3 != null) {
            attributeNames[0] = TangoConst.Tango_AllAttr_3;
        } else {
            attributeNames[0] = TangoConst.Tango_AllAttr;
        }

        return get_attribute_info(deviceProxy, attributeNames);
    }

    // ==========================================================================
    /**
     * Get all attributes info for the specified device.
     * 
     * @return all attributes info.
     */
    // ==========================================================================
    public AttributeInfoEx[] get_attribute_info_ex(final DeviceProxy deviceProxy) throws DevFailed {

        build_connection(deviceProxy);

        final String[] attributeNames = new String[1];
        if (deviceProxy.device_3 != null) {
            attributeNames[0] = TangoConst.Tango_AllAttr_3;
        } else {
            attributeNames[0] = TangoConst.Tango_AllAttr;
        }

        return get_attribute_info_ex(deviceProxy, attributeNames);
    }

    // ==========================================================================
    /**
     * Get all attributes configuration for the specified device.
     * 
     * @return all attributes configuration.
     * @deprecated use get_attribute_info()
     */
    // ==========================================================================
    @Deprecated
    public AttributeInfo[] get_attribute_config(final DeviceProxy deviceProxy) throws DevFailed {
	    return get_attribute_info(deviceProxy);
    }

    // ==========================================================================
    /**
     * Update the attributes info for the specified device.
     * 
     * @param attr
     *            the attributes info.
     */
    // ==========================================================================
    public void set_attribute_info(final DeviceProxy deviceProxy, final AttributeInfo[] attr)
	    throws DevFailed {
        checkIfTango(deviceProxy, "set_attribute_config");

        build_connection(deviceProxy);

        try {
            final AttributeConfig[] config = new AttributeConfig[attr.length];
            for (int i = 0; i < attr.length; i++) {
            config[i] = attr[i].get_attribute_config_obj();
            }
            deviceProxy.device.set_attribute_config(config);
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(deviceProxy);
            throw_dev_failed(deviceProxy, e, "set_attribute_info", true);
        }
    }

    // ==========================================================================
    /**
     * Update the attributes extended info for the specified device.
     * 
     * @param attr
     *            the attributes info.
     */
    // ==========================================================================
    public void set_attribute_info(final DeviceProxy deviceProxy, final AttributeInfoEx[] attr)
	    throws DevFailed {
	checkIfTango(deviceProxy, "set_attribute_config");

	build_connection(deviceProxy);

        if (deviceProxy.access == TangoConst.ACCESS_READ) {
            throwNotAuthorizedException(deviceProxy.devname +
                    ".set_attribute_info()", "DeviceProxy.set_attribute_info()");
        }
        try {
            if (deviceProxy.device_5 != null) {
                final AttributeConfig_5[] config = new AttributeConfig_5[attr.length];
                for (int i = 0; i < attr.length; i++) {
                    config[i] = attr[i].get_attribute_config_obj_5();
                }
                deviceProxy.device_5.set_attribute_config_5(config,
                        DevLockManager.getInstance().getClntIdent());
            }
            else if (deviceProxy.device_4 != null) {
                final AttributeConfig_3[] config = new AttributeConfig_3[attr.length];
                for (int i = 0; i < attr.length; i++) {
                    config[i] = attr[i].get_attribute_config_obj_3();
                }
                deviceProxy.device_4.set_attribute_config_4(config,
                        DevLockManager.getInstance().getClntIdent());
            }
            else if (deviceProxy.device_3 != null) {
                final AttributeConfig_3[] config = new AttributeConfig_3[attr.length];
                for (int i = 0; i < attr.length; i++) {
                    config[i] = attr[i].get_attribute_config_obj_3();
                }
                deviceProxy.device_3.set_attribute_config_3(config);
            }
            else {
                final AttributeConfig[] config = new AttributeConfig[attr.length];
                for (int i = 0; i < attr.length; i++) {
                    config[i] = attr[i].get_attribute_config_obj();
                }
                deviceProxy.device.set_attribute_config(config);
            }
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(deviceProxy);
            throw_dev_failed(deviceProxy, e, "set_attribute_info", true);
        }
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
    @Deprecated
    public void set_attribute_config(final DeviceProxy deviceProxy, final AttributeInfo[] attr)
	    throws DevFailed {
	set_attribute_info(deviceProxy, attr);
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
    public DeviceAttribute read_attribute(final DeviceProxy deviceProxy, final String attname)
	    throws DevFailed {
	final String[] names = ApiUtil.toStringArray(attname);
	final DeviceAttribute[] attval = read_attribute(deviceProxy, names);
	return attval[0];
    }

    // ==========================================================================
    /**
     * return directly AttributeValue object without creation of DeviceAttribute
     * object
     */
    // ==========================================================================

    public AttributeValue read_attribute_value(final DeviceProxy deviceProxy, final String attname)
	    throws DevFailed {
        checkIfTango(deviceProxy, "read_attribute_value");

        build_connection(deviceProxy);

        AttributeValue[] attrval;
        if (deviceProxy.getAttnames_array() == null) {
            deviceProxy.setAttnames_array(new String[1]);
        }
        deviceProxy.getAttnames_array()[0] = attname;
        try {
            if (deviceProxy.device_2 != null) {
            attrval = deviceProxy.device_2.read_attributes_2(deviceProxy.getAttnames_array(),
                deviceProxy.dev_src);
            } else {
            attrval = deviceProxy.device.read_attributes(deviceProxy.getAttnames_array());
            }
            return attrval[0];
        } catch (final DevFailed e) {
            Except.throw_connection_failed(e, "TangoApi_CANNOT_READ_ATTRIBUTE",
                "Cannot read attribute:   " + attname, deviceProxy.getFull_class_name()
                    + ".read_attribute()");
            return null;
        } catch (final Exception e) {
            ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(deviceProxy);
            throw_dev_failed(deviceProxy, e, "device.read_attributes()", false);
            return null;
        }
    }

    // ==========================================================================
    /**
     * Read the attribute values for the specified device.
     * 
     * @param attributeNames
     *            attribute names to request values.
     * @return the attribute values.
     */
    // ==========================================================================
    public DeviceAttribute[] read_attribute(final DeviceProxy deviceProxy, final String[] attributeNames)
	    throws DevFailed {
	DeviceAttribute[] attr;

	build_connection(deviceProxy);

	// Read attributes on device server
	AttributeValue[] attributeValues = new AttributeValue[0];
	AttributeValue_3[] attributeValues_3 = new AttributeValue_3[0];
	AttributeValue_4[] attributeValues_4 = new AttributeValue_4[0];
	AttributeValue_5[] attributeValues_5 = new AttributeValue_5[0];
	if (deviceProxy.url.protocol == TANGO) {
	    // try 2 times for reconnection if requested
	    boolean done = false;
	    final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
	    for (int i = 0; i < retries && !done; i++) {
            try {
                if (deviceProxy.device_5 != null) {
                    attributeValues_5 = deviceProxy.device_5.read_attributes_5(
                            attributeNames, deviceProxy.dev_src,
                            DevLockManager.getInstance().getClntIdent());
                }
                else if (deviceProxy.device_4 != null) {
                    attributeValues_4 = deviceProxy.device_4.read_attributes_4(
                            attributeNames, deviceProxy.dev_src,
                            DevLockManager.getInstance().getClntIdent());
                }
                else if (deviceProxy.device_3 != null) {
                    attributeValues_3 = deviceProxy.device_3.read_attributes_3(
                            attributeNames, deviceProxy.dev_src);
                }
                else if (deviceProxy.device_2 != null) {
                    attributeValues = deviceProxy.device_2.read_attributes_2(
                            attributeNames, deviceProxy.dev_src);
                }
                else {
                    attributeValues = deviceProxy.device.read_attributes(attributeNames);
                }
                done = true;
            } catch (final DevFailed e) {
                // Except.print_exception(e);
                final StringBuilder sb = new StringBuilder(attributeNames[0]);
                for (int j = 1; j < attributeNames.length; j++) {
                    sb.append(", ").append(attributeNames[j]);
                }
                Except.throw_connection_failed(e, "TangoApi_CANNOT_READ_ATTRIBUTE",
                    "Cannot read attribute(s):   " + sb.toString(),
                        deviceProxy.getFull_class_name() + ".read_attribute()");
            } catch (final Exception e) {
                manageExceptionReconnection(deviceProxy,
                        retries, i, e, this.getClass() + ".read_attribute");
            }
	    }
	    // Build a Device Attribute Object
	    // Depends on Device_impl version
	    if (deviceProxy.device_5 != null) {
            attr = new DeviceAttribute[attributeValues_5.length];
            for (int i = 0; i < attributeValues_5.length; i++) {
                attr[i] = new DeviceAttribute(attributeValues_5[i]);
            }
	    } else if (deviceProxy.device_4 != null) {
            attr = new DeviceAttribute[attributeValues_4.length];
            for (int i = 0; i < attributeValues_4.length; i++) {
                attr[i] = new DeviceAttribute(attributeValues_4[i]);
            }
	    } else if (deviceProxy.device_3 != null) {
            attr = new DeviceAttribute[attributeValues_3.length];
            for (int i = 0; i < attributeValues_3.length; i++) {
                attr[i] = new DeviceAttribute(attributeValues_3[i]);
            }
	    } else {
            attr = new DeviceAttribute[attributeValues.length];
            for (int i = 0; i < attributeValues.length; i++) {
                attr[i] = new DeviceAttribute(attributeValues[i]);
            }
	    }
	} else {
	    attr = deviceProxy.taco_device.read_attribute(attributeNames);
	}
	return attr;
    }

    // ==========================================================================
    /**
     * Write the attribute value for the specified device.
     * 
     * @param deviceAttribute attribute name and value.
     */
    // ==========================================================================
    public void write_attribute(final DeviceProxy deviceProxy, final DeviceAttribute deviceAttribute)
	    	throws DevFailed {
		checkIfTango(deviceProxy, "write_attribute");
		try {
	    	final DeviceAttribute[] array = { deviceAttribute };
	    	write_attribute(deviceProxy, array);
		} catch (final NamedDevFailedList e) {
	    	final NamedDevFailed namedDF = e.elementAt(0);
	    	throw new DevFailed(namedDF.err_stack);
		} catch (final Exception e) {
	    	throw_dev_failed(deviceProxy, e, "device.write_attributes()", false);
		}
    }

    // ==========================================================================
    /**
     * Write the attribute values for the specified device.
     * 
     * @param deviceAttributes  attribute names and values.
     */
    // ==========================================================================
    public void write_attribute(final DeviceProxy deviceProxy,
                                final DeviceAttribute[] deviceAttributes) throws DevFailed {
		checkIfTango(deviceProxy, "write_attribute");
		build_connection(deviceProxy);

		//
		// Manage Access control
		//
		if (deviceProxy.access == TangoConst.ACCESS_READ) {
	    	// ping the device to throw execption
	    	// if failed (for reconnection)
	    	ping(deviceProxy);

        	throwNotAuthorizedException(deviceProxy.devname + ".write_attribute()",
                	"DeviceProxy.write_attribute()");
		}
		// Build an AttributeValue IDL object array
        AttributeValue_4[] attributeValues_4 = new AttributeValue_4[0];
		AttributeValue[] attributeValues = new AttributeValue[0];

		if (deviceProxy.device_5 != null || deviceProxy.device_4 != null) {
	    	attributeValues_4 = new AttributeValue_4[deviceAttributes.length];
	    	for (int i=0 ; i<deviceAttributes.length ; i++) {
                attributeValues_4[i] = deviceAttributes[i].getAttributeValueObject_4();
	    	}
		} else {
	    	attributeValues = new AttributeValue[deviceAttributes.length];
	    	for (int i = 0; i < deviceAttributes.length; i++) {
                attributeValues[i] = deviceAttributes[i].getAttributeValueObject_2();
	    	}
		}
		boolean done = false;
		final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
		for (int i = 0; i < retries && !done; i++) {
	    	// write attributes on device server
	    	try {
				if (deviceProxy.device_5 != null) {
		    		deviceProxy.device_5.write_attributes_4(
                            attributeValues_4, DevLockManager.getInstance().getClntIdent());
				} else
                if (deviceProxy.device_4 != null) {
		    		deviceProxy.device_4.write_attributes_4(
                            attributeValues_4, DevLockManager.getInstance().getClntIdent());
				} else
                if (deviceProxy.device_3 != null) {
		    		deviceProxy.device_3.write_attributes_3(attributeValues);
				} else {
		    		deviceProxy.device.write_attributes(attributeValues);
				}
				done = true;
	    	} catch (final DevFailed e) {
				// Except.print_exception(e);
				throw e;
	    	} catch (final MultiDevFailed e) {
				throw new NamedDevFailedList(e, name(deviceProxy),
						"DeviceProxy.write_attribute", "MultiDevFailed");
	    	} catch (final Exception e) {
				manageExceptionReconnection(deviceProxy, retries, i, e,
					this.getClass() + ".DeviceProxy.write_attribute");
	    	}
		} // End of for ( ; ; )
   	}

    // ==========================================================================
    /**
     * Write and then read the attribute values, for the specified device.
     * 
     * @param deviceAttributes attribute names and values.
     */
    // ==========================================================================
    public DeviceAttribute[] write_read_attribute(
                final DeviceProxy deviceProxy,
			    final DeviceAttribute[] deviceAttributes) throws DevFailed {
        return write_read_attribute(deviceProxy, deviceAttributes, new String[0]);
    }
    // ==========================================================================
    /**
     * Write and then read the attribute values, for the specified device.
     *
     * @param deviceAttributes attribute names and values to be written.
     * @param readNames attribute names to read.
     */
    // ==========================================================================
    public DeviceAttribute[] write_read_attribute(final DeviceProxy deviceProxy,
			    final DeviceAttribute[] deviceAttributes, final String[] readNames) throws DevFailed {

		checkIfTango(deviceProxy, "write_read_attribute");
		build_connection(deviceProxy);

		// Manage Access control
		if (deviceProxy.access == TangoConst.ACCESS_READ) {
	    	// ping the device to throw exception if failed (for reconnection)
	    	ping(deviceProxy);
        	throwNotAuthorizedException(deviceProxy.devname + ".write_read_attribute()",
		    	"DeviceProxy.write_read_attribute()");
		}
		// Build an AttributeValue IDL object array
		AttributeValue_4[] attributeValues_4 = new AttributeValue_4[0];
		AttributeValue_4[] outAttrValues_4 = new AttributeValue_4[0];
		AttributeValue_5[] outAttrValues_5 = new AttributeValue_5[0];

		if (deviceProxy.device_5 != null || deviceProxy.device_4 != null) {
	    	attributeValues_4 = new AttributeValue_4[deviceAttributes.length];
	    	for (int i=0 ; i<deviceAttributes.length ; i++) {
			    attributeValues_4[i] = deviceAttributes[i].getAttributeValueObject_4();
	    	}
		} else {
	    	Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
		    	"Cannot execute write_read_attribute(), " + deviceProxy.devname
			    	+ " is not a device_4Impl or above",
		    	"DeviceProxy.write_read_attribute()");
		}

		boolean done = false;
		final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
		for (int i = 0; i < retries && !done; i++) {
	    	// write attributes on device server
	    	try {
                if (deviceProxy.device_5 != null) {
                    outAttrValues_5 = deviceProxy.device_5.write_read_attributes_5(
                            attributeValues_4, readNames,
                            DevLockManager.getInstance().getClntIdent());
                } else
                if (deviceProxy.device_4 != null) {
                    outAttrValues_4 = deviceProxy.device_4.write_read_attributes_4(
                            attributeValues_4, DevLockManager.getInstance().getClntIdent());
			}
			done = true;
	    	} catch (final DevFailed e) {
                // Except.print_exception(e);
                throw e;
	    	} catch (final MultiDevFailed e) {
			throw new NamedDevFailedList(e, name(deviceProxy),
				"DeviceProxy.write_read_attribute", "MultiDevFailed");
	    	} catch (final Exception e) {
			    manageExceptionReconnection(deviceProxy, retries, i, e, this.getClass()
				+ ".write_read_attribute");
	    	}
		} // End of for ( ; ; )

		// Build a Device Attribute Object
		// Depends on Device_impl version
		if (deviceProxy.device_5 != null) {
            final DeviceAttribute[] attributes = new DeviceAttribute[outAttrValues_5.length];
	    	for (int i=0 ; i<outAttrValues_5.length; i++) {
                attributes[i] = new DeviceAttribute(outAttrValues_5[i]);
	    	}
            return attributes;
		}
        else
		if (deviceProxy.device_4 != null) {
            final DeviceAttribute[] attributes = new DeviceAttribute[outAttrValues_4.length];
	    	for (int i = 0; i < outAttrValues_4.length; i++) {
                attributes[i] = new DeviceAttribute(outAttrValues_4[i]);
	    	}
            return attributes;
		}
        else
            return null; // Cannot be possible (write_read did not exist before
    }

    // ==========================================================================
    // ==========================================================================
    public DeviceProxy get_adm_dev(final DeviceProxy deviceProxy) throws DevFailed {
		if (deviceProxy.getAdm_dev() == null) {
	    	import_admin_device(deviceProxy, "get_adm_dev");
		}
		return deviceProxy.getAdm_dev();
    }

    // ==========================================================================
    /**
     * Polling commands.
     */
    // ==========================================================================
    // ==========================================================================
    /**
     * Add a command to be polled for the device. If already polled, update its
     * polling period.
     * 
     * @param deviceProxy device proxy instance.
     * @param objectName command/attribute name to be polled.
     * @param objectType command or attribute.
     * @param period  polling period.
     * @throws fr.esrf.Tango.DevFailed if command failed
     */
    // ==========================================================================
    private void poll_object(final DeviceProxy deviceProxy, final String objectName,
	    	final String objectType, final int period) throws DevFailed {
		final DevVarLongStringArray lsa = new DevVarLongStringArray();
		lsa.lvalue = new int[1];
		lsa.svalue = new String[3];
		lsa.svalue[0] = deviceProxy.devname;
		lsa.svalue[1] = objectType;
		lsa.svalue[2] = objectName.toLowerCase();
		lsa.lvalue[0] = period;

		// Send command on administration device.
		if (deviceProxy.getAdm_dev() == null) {
	    	import_admin_device(deviceProxy, "poll_object");
		}
		if (DeviceProxy.isCheck_idl() && deviceProxy.getAdm_dev().get_idl_version() < 2) {
	    	Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
		    	"Not supported by the IDL version used by device", deviceProxy
			    	.getFull_class_name()
			    	+ ".poll_object()");
		}

		final DeviceData argin = new DeviceData();
		argin.insert(lsa);

		// Try to add polling period.
		try {
	    	deviceProxy.getAdm_dev().command_inout("AddObjPolling", argin);
		} catch (final DevFailed e) {
	    	// check : if already polled, just update period polling
	    	for (final DevError error : e.errors) {
				if (error.reason.equals("API_AlreadyPolled")) {
		    		deviceProxy.getAdm_dev().command_inout("UpdObjPollingPeriod", argin);
		    		return;
				}
	    	}
	    	// Not this exception then re-throw it
	    	Except.throw_communication_failed(e, "TangoApi_CANNOT_POLL_OBJECT",
		    	"Cannot poll object " + objectName, deviceProxy.getFull_class_name()
			    	+ ".poll_object()");
		}
    }

    // ==========================================================================
    /**
     * Add a command to be polled for the device. If already polled, update its
     * polling period.
     * 
     * @param cmdname command name to be polled.
     * @param period polling period.
     */
    // ==========================================================================
    public void poll_command(final DeviceProxy deviceProxy, final String cmdname, final int period)
		    throws DevFailed {
		poll_object(deviceProxy, cmdname, "command", period);
    }

    // ==========================================================================
    /**
     * Add a attribute to be polled for the device. If already polled, update
     * its polling period.
     * 
     * @param attname attribute name to be polled.
     * @param period polling period.
     */
    // ==========================================================================
    public void poll_attribute(final DeviceProxy deviceProxy, final String attname, final int period)
	    throws DevFailed {
    	poll_object(deviceProxy, attname, "attribute", period);
    }

    // ==========================================================================
    /**
     * Remove object of polled object list
     * 
     * @param deviceProxy   the specified DeviceProxy object
     * @param objname object name to be removed of polled object list.
     * @param objtype object type (command or attribute).
     * @throws fr.esrf.Tango.DevFailed if command failed
     */
    // ==========================================================================
    private void remove_poll_object(final DeviceProxy deviceProxy, final String objname,
            final String objtype) throws DevFailed {
        // Send command on administration device.
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "remove_poll_object");
        }
        if (DeviceProxy.isCheck_idl() && deviceProxy.getAdm_dev().get_idl_version() < 2) {
            Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
                "Not supported by the IDL version used by device", deviceProxy
                    .getFull_class_name()
                    + ".remove_poll_object()");
        }

        final DeviceData argin = new DeviceData();
        final String[] params = new String[3];
        params[0] = deviceProxy.devname;
        params[1] = objtype;
        params[2] = objname.toLowerCase();
        argin.insert(params);
        deviceProxy.getAdm_dev().command_inout("RemObjPolling", argin);
    }

    // ==========================================================================
    /**
     * Remove command of polled object list
     * 
     * @param cmdname command name to be removed of polled object list.
     */
    // ==========================================================================
    public void stop_poll_command(final DeviceProxy deviceProxy, final String cmdname)
            throws DevFailed {
        remove_poll_object(deviceProxy, cmdname, "command");
    }

    // ==========================================================================
    /**
     * Remove attribute of polled object list
     * 
     * @param attname attribute name to be removed of polled object list.
     */
    // ==========================================================================
    public void stop_poll_attribute(final DeviceProxy deviceProxy, final String attname)
            throws DevFailed {
        remove_poll_object(deviceProxy, attname, "attribute");
    }

    // ==========================================================================
    /**
     * Returns the polling status for the device.
     */
    // ==========================================================================
    public String[] polling_status(final DeviceProxy deviceProxy) throws DevFailed {
        // Send command on administration device.
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "polling_status");
        }
        if (DeviceProxy.isCheck_idl() && deviceProxy.getAdm_dev().get_idl_version() < 2) {
            Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
                "Not supported by the IDL version used by device", deviceProxy
                    .getFull_class_name()
                    + ".polling_status()");
        }
        final DeviceData argin = new DeviceData();
        argin.insert(deviceProxy.devname);
        final DeviceData argout = deviceProxy.getAdm_dev().command_inout("DevPollStatus", argin);
        return argout.extractStringArray();
    }

    // ==========================================================================
    /**
     * Return the history for command polled.
     * 
     * @param cmdname command name to read polled history.
     * @param nb nb data to read.
     */
    // ==========================================================================
    public DeviceDataHistory[] command_history(final DeviceProxy deviceProxy, final String cmdname,
            final int nb) throws DevFailed {
        checkIfTango(deviceProxy, "command_history");

        build_connection(deviceProxy);

        if (DeviceProxy.isCheck_idl() && get_idl_version(deviceProxy) < 2) {
            Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
                "Not supported by the IDL version used by device", deviceProxy
                    .getFull_class_name()
                    + ".command_history()");
	    }

        DeviceDataHistory[] histories = new DeviceDataHistory[0];
        try {
            // Check IDL revision to know kind of history.
            if (deviceProxy.device_5 != null) {
                final DevCmdHistory_4 cmdHistory =
                        deviceProxy.device_5.command_inout_history_4(cmdname, nb);
                histories = ConversionUtil.commandHistoryToDeviceDataHistoryArray(cmdname, cmdHistory);
            }
            else if (deviceProxy.device_4 != null) {
                final DevCmdHistory_4 cmdHistory =
                        deviceProxy.device_4.command_inout_history_4(cmdname, nb);
                histories = ConversionUtil.commandHistoryToDeviceDataHistoryArray(cmdname, cmdHistory);
            }
            else {
                final DevCmdHistory[] cmdHistory =
                        deviceProxy.device_2.command_inout_history_2(cmdname, nb);
                histories = new DeviceDataHistory[cmdHistory.length];
                for (int i = 0; i < cmdHistory.length; i++) {
                    histories[i] = new DeviceDataHistory(cmdname, cmdHistory[i]);
                }
            }
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            throw_dev_failed(deviceProxy, e, "command_inout_history()", false);
        }
        return histories;
    }

    // ==========================================================================
    /**
     * Return the history for attribute polled.
     * 
     * @param attributeName attribute name to read polled history.
     * @param nbData nb data to read.
     */
    // ==========================================================================
    public DeviceDataHistory[] attribute_history(final DeviceProxy deviceProxy,
            final String attributeName, final int nbData) throws DevFailed {
        checkIfTango(deviceProxy, "attribute_history");
        build_connection(deviceProxy);

        if (DeviceProxy.isCheck_idl() && get_idl_version(deviceProxy) < 2) {
            Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
                "Not supported by the IDL version used by device",
                    deviceProxy.getFull_class_name() + ".attribute_history()");
        }

        DeviceDataHistory[] deviceDataHistories = new DeviceDataHistory[0];
        try {
            // Check IDL revision to know kind of history.
            if (deviceProxy.device_5 != null) {
                final DevAttrHistory_5 attrHistory_5 =
                        deviceProxy.device_5.read_attribute_history_5(attributeName, nbData);
                deviceDataHistories =
                        ConversionUtil.attributeHistoryToDeviceDataHistoryArray(attrHistory_5);
            }
            else if (deviceProxy.device_4 != null) {
                final DevAttrHistory_4 attrHistory_4 =
                        deviceProxy.device_4.read_attribute_history_4(attributeName, nbData);
                deviceDataHistories =
                        ConversionUtil.attributeHistoryToDeviceDataHistoryArray(attrHistory_4);
            }
            else if (deviceProxy.device_3 != null) {
                final DevAttrHistory_3[] attrHistories_3 =
                        deviceProxy.device_3.read_attribute_history_3(attributeName, nbData);

                deviceDataHistories = new DeviceDataHistory[attrHistories_3.length];
                for (int i = 0; i < attrHistories_3.length; i++) {
                    deviceDataHistories[i] = new DeviceDataHistory(attrHistories_3[i]);
                }
            }
            else if (deviceProxy.device_2 != null) {
                final DevAttrHistory[] attrHistories = deviceProxy.device_2.read_attribute_history_2(
                    attributeName, nbData);
                deviceDataHistories = new DeviceDataHistory[attrHistories.length];
                for (int i=0 ; i<attrHistories.length ; i++) {
                    deviceDataHistories[i] = new DeviceDataHistory(attrHistories[i]);
                }
            }
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            throw_dev_failed(deviceProxy, e, "read_attribute_history()", false);
        }
        return deviceDataHistories;
    }

    // ==========================================================================
    /**
     * Return the full history for command polled.
     * 
     * @param cmdname  command name to read polled history.
     */
    // ==========================================================================
    public DeviceDataHistory[] command_history(final DeviceProxy deviceProxy, final String cmdname)
            throws DevFailed {
        int hist_depth = 10;
        final DbDatum data = get_property(deviceProxy, "poll_ring_depth");
        if (!data.is_empty()) {
            hist_depth = data.extractLong();
        }
        return command_history(deviceProxy, cmdname, hist_depth);
    }

    // ==========================================================================
    /**
     * Return the full history for attribute polled.
     * 
     * @param attname attribute name to read polled history.
     */
    // ==========================================================================
    public DeviceDataHistory[] attribute_history(final DeviceProxy deviceProxy, final String attname)
            throws DevFailed {
        int hist_depth = 10;
        final DbDatum data = get_property(deviceProxy, "poll_ring_depth");
        if (!data.is_empty()) {
            hist_depth = data.extractLong();
        }
        return attribute_history(deviceProxy, attname, hist_depth);
    }

    // ==========================================================================
    /**
     * Returns the polling period (in ms) for specified attribute.
     * 
     * @param attname specified attribute name.
     */
    // ==========================================================================
    public int get_attribute_polling_period(final DeviceProxy deviceProxy, final String attname)
            throws DevFailed {
        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_attribute_polling_period");
        return deviceProxy.getDb_dev().get_attribute_polling_period(attname);
    }

    // ==========================================================================
    /**
     * Returns the polling period (in ms) for specified command.
     * 
     * @param cmdname specified attribute name.
     */
    // ==========================================================================
    public int get_command_polling_period(final DeviceProxy deviceProxy, final String cmdname)
            throws DevFailed {
        if (deviceProxy.getDb_dev() == null) {
            deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host,
                deviceProxy.url.strPort));
        }
        checkIfTango(deviceProxy, "get_attribute_polling_period");
        return deviceProxy.getDb_dev().get_attribute_polling_period(cmdname);
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
    public int command_inout_asynch(final DeviceProxy deviceProxy, final String cmdname,
            final DeviceData data_in) throws DevFailed {
        return command_inout_asynch(deviceProxy, cmdname, data_in, false);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout.
     * 
     * @param cmdname command name.
     */
    // ==========================================================================
    public int command_inout_asynch(final DeviceProxy deviceProxy, final String cmdname)
            throws DevFailed {
        return command_inout_asynch(deviceProxy, cmdname, new DeviceData(), false);
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout.
     * 
     * @param cmdname  command name.
     * @param forget forget the response if true
     */
    // ==========================================================================
    public int command_inout_asynch(final DeviceProxy deviceProxy, final String cmdname,
            final boolean forget) throws DevFailed {
        return command_inout_asynch(deviceProxy, cmdname, new DeviceData(), forget);
    }

    // ==========================================================================
    /**
     * Add and set arguments to request for asynchronous command.
     * @param request request object
\    * @param data_in input value
     * @param name command name
     * @param src source CACHE/CACHE_DEV/DEV
     * @param ident client identifier object
     * @throws fr.esrf.Tango.DevFailed if command failed
     */
    // ==========================================================================
    private void setRequestArgsForCmd(final Request request, final String name,
            final DeviceData data_in, final DevSource src, final ClntIdent ident) throws DevFailed {
        final ORB orb = ApiUtil.get_orb();

        request.add_in_arg().insert_string(name);
        request.add_in_arg().insert_any(data_in.extractAny());
        // Add source if any
        if (src != null) {
            final Any any = request.add_in_arg();
            DevSourceHelper.insert(any, src);
        }
        // Add client ident if any
        if (ident != null) {
            final Any any = request.add_in_arg();
            ClntIdentHelper.insert(any, ident);
        }
        request.set_return_type(orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_any));
        request.exceptions().add(DevFailedHelper.type());
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout.
     * 
     * @param cmdname command name.
     * @param data_in input argument command.
     * @param forget forget the response if true
     */
    // ==========================================================================
    public int command_inout_asynch(final DeviceProxy deviceProxy, final String cmdname,
            final DeviceData data_in, final boolean forget) throws DevFailed {
        checkIfTango(deviceProxy, "command_inout_asynch");

        build_connection(deviceProxy);

        // Manage Access control
        // ----------------------------------
        if (deviceProxy.access == TangoConst.ACCESS_READ) {
            final Database db = ApiUtil.get_db_obj(deviceProxy.url.host, deviceProxy.url.strPort);
            if (!db.isCommandAllowed(deviceProxy.get_class_name(), cmdname)) {
            // Check if not allowed or PB with access device
            if (db.access_devfailed != null) {
                throw db.access_devfailed;
            }
            // pind the device to throw execption
            // if failed (for reconnection)
            ping(deviceProxy);

            System.out.println(deviceProxy.devname + "." + cmdname
                + "  -> TangoApi_READ_ONLY_MODE");
            throwNotAuthorizedException(deviceProxy.devname + ".command_inout_asynch(" + cmdname + ")",
                "Connection.command_inout_asynch()");
            }
        }
        // Create the request object
        // ----------------------------------
        Request request;
        if (deviceProxy.device_5 != null) {
            request = deviceProxy.device_5._request("command_inout_4");
            setRequestArgsForCmd(request, cmdname, data_in, get_source(deviceProxy), DevLockManager
                .getInstance().getClntIdent());
        }
        else if (deviceProxy.device_4 != null) {
            request = deviceProxy.device_4._request("command_inout_4");
            setRequestArgsForCmd(request, cmdname, data_in, get_source(deviceProxy), DevLockManager
                .getInstance().getClntIdent());
        }
        else if (deviceProxy.device_2 != null) {
            request = deviceProxy.device_2._request("command_inout_2");
            setRequestArgsForCmd(request, cmdname, data_in, get_source(deviceProxy), null);
        }
        else {
            request = deviceProxy.device._request("command_inout");
            setRequestArgsForCmd(request, cmdname, data_in, null, null);
        }

        // send it (deferred or just one way)
        int id = 0;
        // Else tango call
        boolean done = false;
        // try 2 times for reconnection if requested
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int i = 0; i < retries && !done; i++) {
            try {
                if (forget) {
                    request.send_oneway();
                } else {
                    request.send_deferred();
                    // store request reference to read reply later
                    final String[] names = new String[] { cmdname };
                    id = ApiUtil.put_async_request(
                            new AsyncCallObject(request, deviceProxy, CMD, names));
                }
                done = true;
            } catch (final Exception e) {
                manageExceptionReconnection(deviceProxy,
                        retries, i, e, this.getClass() + ".command_inout");
            }
        }
        return id;
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout using callback for reply.
     * 
     * @param cmdname Command name.
     * @param argin Input argument command.
     * @param cb a CallBack object instance.
     */
    // ==========================================================================
    public void command_inout_asynch(final DeviceProxy deviceProxy, final String cmdname,
            final DeviceData argin, final CallBack cb) throws DevFailed {
        final int id = command_inout_asynch(deviceProxy, cmdname, argin, false);
        ApiUtil.set_async_reply_model(id, CALLBACK);
        ApiUtil.set_async_reply_cb(id, cb);

        // if push callback, start a thread to do it
        if (ApiUtil.get_asynch_cb_sub_model() == PUSH_CALLBACK) {
            final AsyncCallObject aco = ApiUtil.get_async_object(id);
            new CallbackThread(aco).start();
        }
    }

    // ==========================================================================
    /**
     * Asynchronous command_inout using callback for reply.
     * 
     * @param cmdname Command name.
     * @param cb a CallBack object instance.
     */
    // ==========================================================================
    public void command_inout_asynch(final DeviceProxy deviceProxy, final String cmdname,
            final CallBack cb) throws DevFailed {
        command_inout_asynch(deviceProxy, cmdname, new DeviceData(), cb);
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     * 
     * @param id asynchronous call id (returned by command_inout_asynch).
     * @param timeout number of millisonds to wait reply before throw an excption.
     */
    // ==========================================================================
    public DeviceData command_inout_reply(final DeviceProxy deviceProxy,
                                          final int id, final int timeout) throws DevFailed {
        return command_inout_reply(deviceProxy, ApiUtil.get_async_object(id), timeout);
    }
    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     * 
     * @param aco asynchronous call Request instance
     * @param timeout number of milliseconds to wait reply before throw an exception.
     */
    // ==========================================================================
    public DeviceData command_inout_reply(final DeviceProxy deviceProxy,
                                          final AsyncCallObject aco, final int timeout) throws DevFailed {
        DeviceData argout = null;
        final int ms_to_sleep = 10;
        DevFailed except = null;
        final long t0 = System.currentTimeMillis();
        long t1 = t0;

        while ((t1 - t0 < timeout || timeout == 0) && argout == null) {
            try {
                argout = command_inout_reply(deviceProxy, aco);
            }
            catch (final AsynReplyNotArrived na) {
                except = na;
                // Wait a bit before retry
                try { Thread.sleep(ms_to_sleep); } catch (final InterruptedException e) { /* */ }
                t1 = System.currentTimeMillis();
            }
        }
        // If reply not arrived throw last exception
        if (argout == null) {
            ApiUtil.remove_async_request(aco.id);
            throw except;
        }

        return argout;
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     * 
     * @param id asynchronous call id (returned by command_inout_asynch).
     */
    // ==========================================================================
    public DeviceData command_inout_reply(final DeviceProxy deviceProxy, final int id)
            throws DevFailed {
        return command_inout_reply(deviceProxy, ApiUtil.get_async_object(id));
    }

    // ==========================================================================
    /**
     * Read Asynchronous command_inout reply.
     * 
     * @param aco asynchronous call Request instance
     */
    // ==========================================================================
    public DeviceData command_inout_reply(final DeviceProxy deviceProxy,
                                          final AsyncCallObject aco) throws DevFailed {
        DeviceData data;
        try {
            if (deviceProxy.device_5 != null) {
                check_asynch_reply(deviceProxy, aco.request, aco.id, "command_inout_4");
            }
            else if (deviceProxy.device_4 != null) {
                check_asynch_reply(deviceProxy, aco.request, aco.id, "command_inout_4");
            }
            else if (deviceProxy.device_2 != null) {
                check_asynch_reply(deviceProxy, aco.request, aco.id, "command_inout_2");
            }
            else {
                check_asynch_reply(deviceProxy, aco.request, aco.id, "command_inout");
            }
            // If no exception, extract the any from return value,
            final Any any = aco.request.return_value().extract_any();

            // And put it in a DeviceData object
            data = new DeviceData();
            data.insert(any);
        }
        catch (ConnectionFailed e) {
            try {
                //  If failed, retrieve data from request object
                final NVList args = aco.request.arguments();
                String command = args.item(0).value().extract_string(); // get command name
                DeviceData  argin = null;
                if (args.count()>1) {
                    Any any = args.item(1).value().extract_any();
                    argin = new DeviceData(any);
                }
                //System.err.println(e.errors[0].desc);

                //  And do the synchronous command
                if (argin==null)
                    data = deviceProxy.command_inout(command);
                else
                    data = deviceProxy.command_inout(command, argin);
            }
            catch (org.omg.CORBA.Bounds e1) {
                System.err.println(e1);
                throw e;
            }
        }
        ApiUtil.remove_async_request(aco.id);

        return data;
    }

    // ==========================================================================
    /**
     * add and set arguments to request for asynchronous attributes.
     */
    // ==========================================================================
    private void setRequestArgsForReadAttr(final Request request, final String[] names,
            final DevSource src, final ClntIdent ident, final TypeCode return_type) {
        Any any;
        any = request.add_in_arg();
        DevVarStringArrayHelper.insert(any, names);

        // Add source if any
        if (src != null) {
            any = request.add_in_arg();
            DevSourceHelper.insert(any, src);
        }
        if (ident != null) {
            any = request.add_in_arg();
            ClntIdentHelper.insert(any, ident);
        }
        request.set_return_type(return_type);
        request.exceptions().add(DevFailedHelper.type());
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute.
     * 
     * @param attname Attribute name.
     */
    // ==========================================================================
    public int read_attribute_asynch(final DeviceProxy deviceProxy, final String attname)
            throws DevFailed {
        final String[] attnames = new String[1];
        attnames[0] = attname;
        return read_attribute_asynch(deviceProxy, attnames);
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute.
     * 
     * @param attnames Attribute names.
     */
    // ==========================================================================
    public int read_attribute_asynch(final DeviceProxy deviceProxy, final String[] attnames)
            throws DevFailed {
        checkIfTango(deviceProxy, "read_attributes_asynch");

        build_connection(deviceProxy);

        // Create the request object
        // ----------------------------------
        Request request;
        if (deviceProxy.device_5 != null) {
            request = deviceProxy.device_5._request("read_attributes_5");
            setRequestArgsForReadAttr(request, attnames, get_source(deviceProxy),
                    DevLockManager.getInstance().getClntIdent(), AttributeValueList_5Helper.type());
            request.exceptions().add(MultiDevFailedHelper.type());
        }
        else if (deviceProxy.device_4 != null) {
            request = deviceProxy.device_4._request("read_attributes_4");
            setRequestArgsForReadAttr(request, attnames, get_source(deviceProxy),
                    DevLockManager.getInstance().getClntIdent(), AttributeValueList_4Helper.type());
            request.exceptions().add(MultiDevFailedHelper.type());
        }
        else if (deviceProxy.device_3 != null) {
            request = deviceProxy.device_3._request("read_attributes_3");
            setRequestArgsForReadAttr(request, attnames, get_source(deviceProxy), null,
                AttributeValueList_3Helper.type());
        }
        else if (deviceProxy.device_2 != null) {
            request = deviceProxy.device_2._request("read_attributes_2");
            setRequestArgsForReadAttr(request, attnames, get_source(deviceProxy), null,
                AttributeValueListHelper.type());
        }
        else {
            request = deviceProxy.device._request("read_attributes");
            setRequestArgsForReadAttr(request, attnames, null, null,
                    AttributeValueListHelper.type());
        }

        // send it (defered or just one way)
        request.send_deferred();

        // store request reference to read reply later
        return ApiUtil.put_async_request(
                new AsyncCallObject(request, deviceProxy, ATT_R, attnames));
    }

    // ==========================================================================
    /**
     * Retrieve the command/attribute arguments to build exception description.
     */
    // ==========================================================================
    public String get_asynch_idl_cmd(final DeviceProxy deviceProxy, final Request request,
	        final String idl_cmd) {
        final NVList args = request.arguments();
        final StringBuilder sb = new StringBuilder();
        try {
            if (idl_cmd.equals("command_inout")) {
                return args.item(0).value().extract_string(); // get command name
            } else {
                // read_attribute, get attribute names
                final String[] s_array = DevVarStringArrayHelper.extract(args.item(0).value());
                for (int i = 0; i < s_array.length; i++) {
                    sb.append(s_array[i]);
                    if (i < s_array.length - 1) {
                        sb.append(", ");
                    }
                }
            }
        } catch (final org.omg.CORBA.Bounds e) {
            return "";
        } catch (final Exception e) {
            return "";
        }
        return sb.toString();
    }

    // ==========================================================================
    /**
     * Check Asynchronous call reply.
     * 
     * @param id asynchronous call id (returned by read_attribute_asynch).
     */
    // ==========================================================================
    public void check_asynch_reply(final DeviceProxy deviceProxy, final Request request,
            final int id, final String idl_cmd) throws DevFailed {
        // Check if request object has been found
        if (request == null) {
            Except.throw_connection_failed("TangoApi_CommandFailed",
                "Asynchronous call id not found", deviceProxy.getFull_class_name() + "."
                    + idl_cmd + "_reply()");
        } else {
            if (!request.operation().equals(idl_cmd)) {
                Except.throw_connection_failed("TangoApi_CommandFailed",
                    "Asynchronous call id not for " + idl_cmd, deviceProxy.getFull_class_name()
                        + "." + idl_cmd + "_reply()");
            }

            // Reply arrived ? Throw exception if not yet arrived
            if (!request.poll_response()) {
                Except.throw_asyn_reply_not_arrived("API_AsynReplyNotArrived", "Device "
                    + deviceProxy.devname + ": reply for asynchronous call (id = " + id
                    + ") is not yet arrived", deviceProxy.getFull_class_name() + "." + idl_cmd
                    + "_reply()");
            } else {
                // Check if an exception has been thrown
                final Exception except = request.env().exception();

                if (except != null) {
                    ApiUtil.remove_async_request(id);
                    if (except instanceof org.omg.CORBA.TRANSIENT) {
                        throw_dev_failed(deviceProxy, except,
                                deviceProxy.getFull_class_name()
                                    + "." + idl_cmd + "_reply("
                                    + get_asynch_idl_cmd(deviceProxy, request, idl_cmd) + ")", false);
                    }
                    else
                    if (except instanceof org.omg.CORBA.TIMEOUT) {
                        throw_dev_failed(deviceProxy, except,
                                deviceProxy.getFull_class_name()
                                    + "." + idl_cmd + "_reply("
                                    + get_asynch_idl_cmd(deviceProxy, request, idl_cmd) + ")", false);
                    }
                    else
                    // Check if user exception (DevFailed).
                    if (except instanceof org.omg.CORBA.UnknownUserException) {
                        final Any any = ((org.omg.CORBA.UnknownUserException) except).except;
                        MultiDevFailed ex = null;
                        try {
                            //noinspection ThrowableResultOfMethodCallIgnored
                            ex = MultiDevFailedHelper.extract(any);
                        } catch (final Exception e) {
                            // not a MultiDevFailed, is a DevFailed
                            final DevFailed df = DevFailedHelper.extract(any);
                            Except.throw_connection_failed(df, "TangoApi_CommandFailed",
                                "Asynchronous command failed", deviceProxy.getFull_class_name()
                                    + "." + idl_cmd + "_reply("
                                    + get_asynch_idl_cmd(deviceProxy, request, idl_cmd)
                                    + ")");
                        }
                        if (ex!=null)
                            Except.throw_connection_failed(new DevFailed(
                                    ex.errors[0].err_list),
                                    "TangoApi_CommandFailed", "Asynchronous command failed",
                                    deviceProxy.getFull_class_name() + "." + idl_cmd + "_reply("
                                    + get_asynch_idl_cmd(deviceProxy, request, idl_cmd) + ")");
                    } else {
                        except.printStackTrace();
                        // Another exception -> re-throw it as a DevFailed
                        System.err.println(deviceProxy.getFull_class_name() + "." + idl_cmd
                            + "_reply(" + get_asynch_idl_cmd(deviceProxy, request, idl_cmd)
                            + ")");
                        throw_dev_failed(deviceProxy, except, deviceProxy.getFull_class_name()
                            + "." + idl_cmd + "_reply("
                            + get_asynch_idl_cmd(deviceProxy, request, idl_cmd) + ")", false);
                    }
                }
            }
        }
    }

    // ==========================================================================
    /**
     * Read Asynchronous read_attribute reply.
     * 
     * @param id asynchronous call id (returned by read_attribute_asynch).
     * @param timeout number of milliseconds to wait reply before throw an excption.
     */
    // ==========================================================================
    public DeviceAttribute[] read_attribute_reply(final DeviceProxy deviceProxy,
                                                  final int id, final int timeout) throws DevFailed {
        DeviceAttribute[] argout = null;
        final int ms_to_sleep = 10;
        AsynReplyNotArrived except = null;
        final long t0 = System.currentTimeMillis();
        long t1 = t0;

        while ((t1 - t0 < timeout || timeout == 0) && argout == null) {
            try {
                argout = read_attribute_reply(deviceProxy, id);
            }
            catch (final AsynReplyNotArrived na) {
                except = na;
                // Wait a bit before retry
                try { Thread.sleep(ms_to_sleep); } catch (final InterruptedException e) { /* */ }
                t1 = System.currentTimeMillis();
            }
        }
        // If reply not arrived throw last exception
        if (argout == null) {
            ApiUtil.remove_async_request(id);
            throw except;
        }

        return argout;
    }

    // ==========================================================================
    /**
     * Read Asynchronous read_attribute reply.
     * 
     * @param id asynchronous call id (returned by read_attribute_asynch).
     */
    // ==========================================================================
    public DeviceAttribute[] read_attribute_reply(final DeviceProxy deviceProxy,
                                                  final int id) throws DevFailed {
        DeviceAttribute[] data = new DeviceAttribute[0];
        AttributeValue[] attributeValues;
        AttributeValue_3[] attributeValues_3;
        AttributeValue_4[] attributeValues_4;
        AttributeValue_5[] attributeValues_5;
        final Request request = ApiUtil.get_async_request(id);

        // If no exception, extract the any from return value,
        final Any any = request.return_value();
        try {
            if (deviceProxy.device_5 != null) {
                check_asynch_reply(deviceProxy, request, id, "read_attributes_5");
                    attributeValues_5 = AttributeValueList_5Helper.extract(any);
                    data = new DeviceAttribute[attributeValues_5.length];
                    for (int i = 0; i < attributeValues_5.length; i++) {
                        data[i] = new DeviceAttribute(attributeValues_5[i]);
                }
            }
            else if (deviceProxy.device_4 != null) {
                check_asynch_reply(deviceProxy, request, id, "read_attributes_4");
                    attributeValues_4 = AttributeValueList_4Helper.extract(any);
                    data = new DeviceAttribute[attributeValues_4.length];
                    for (int i = 0; i < attributeValues_4.length; i++) {
                        data[i] = new DeviceAttribute(attributeValues_4[i]);
                }
            }
            else if (deviceProxy.device_3 != null) {
                check_asynch_reply(deviceProxy, request, id, "read_attributes_3");
                    attributeValues_3 = AttributeValueList_3Helper.extract(any);
                    data = new DeviceAttribute[attributeValues_3.length];
                    for (int i = 0; i < attributeValues_3.length; i++) {
                        data[i] = new DeviceAttribute(attributeValues_3[i]);
                }
            }
            else if (deviceProxy.device_2 != null) {
                check_asynch_reply(deviceProxy, request, id, "read_attributes_2");
                    attributeValues = AttributeValueListHelper.extract(any);
                    data = new DeviceAttribute[attributeValues.length];
                    for (int i = 0; i < attributeValues.length; i++) {
                        data[i] = new DeviceAttribute(attributeValues[i]);
                }
            }
            else {
                check_asynch_reply(deviceProxy, request, id, "read_attributes");
                    attributeValues = AttributeValueListHelper.extract(any);
                    data = new DeviceAttribute[attributeValues.length];
                    for (int i = 0; i < attributeValues.length; i++) {
                        data[i] = new DeviceAttribute(attributeValues[i]);
                }
            }
        }
        catch (Exception e) {
            if (e instanceof AsynReplyNotArrived)
                throw (AsynReplyNotArrived) e;
            try {
                //  If failed, retrieve data from request object
                final NVList args = request.arguments();
                final String[] attributeNames =
                        DevVarStringArrayHelper.extract(args.item(0).value());

                //  And do the synchronous read_attributes
                data = deviceProxy.read_attribute(attributeNames);
            }
            catch (org.omg.CORBA.Bounds e1) {
                System.err.println(e1);
				if (e instanceof DevFailed)
	                throw (DevFailed)e;
				else
					Except.throw_exception(e.toString(), e.toString());
            }
        }
        ApiUtil.remove_async_request(id);

        return data;
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute using callback for reply.
     * 
     * @param attname attribute name.
     * @param cb a CallBack object instance.
     */
    // ==========================================================================
    public void read_attribute_asynch(final DeviceProxy deviceProxy, final String attname,
            final CallBack cb) throws DevFailed {
        final String[] attnames = new String[1];
        attnames[0] = attname;
        read_attribute_asynch(deviceProxy, attnames, cb);
    }

    // ==========================================================================
    /**
     * Asynchronous read_attribute using callback for reply.
     * 
     * @param attnames attribute names.
     * @param cb a CallBack object instance.
     */
    // ==========================================================================
    public void read_attribute_asynch(final DeviceProxy deviceProxy, final String[] attnames,
            final CallBack cb) throws DevFailed {
        final int id = read_attribute_asynch(deviceProxy, attnames);
        ApiUtil.set_async_reply_model(id, CALLBACK);
        ApiUtil.set_async_reply_cb(id, cb);

        // if push callback, start a thread to do it
        if (ApiUtil.get_asynch_cb_sub_model() == PUSH_CALLBACK) {
            final AsyncCallObject aco = ApiUtil.get_async_object(id);
            new CallbackThread(aco).start();
        }
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     * 
     * @param attr Attribute value (name, writing value...)
     */
    // ==========================================================================
    public int write_attribute_asynch(final DeviceProxy deviceProxy, final DeviceAttribute attr)
            throws DevFailed {
        return write_attribute_asynch(deviceProxy, attr, false);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     * 
     * @param attr Attribute value (name, writing value...)
     * @param forget forget the response if true
     */
    // ==========================================================================
    public int write_attribute_asynch(final DeviceProxy deviceProxy, final DeviceAttribute attr,
            final boolean forget) throws DevFailed {
        final DeviceAttribute[] attribs = new DeviceAttribute[1];
        attribs[0] = attr;
        return write_attribute_asynch(deviceProxy, attribs);
        }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     * 
     * @param attribs  Attribute values (name, writing value...)
     */
    // ==========================================================================
    public int write_attribute_asynch(final DeviceProxy deviceProxy, final DeviceAttribute[] attribs)
            throws DevFailed {
        return write_attribute_asynch(deviceProxy, attribs, false);
        }

    // ==========================================================================
    /**
     * Asynchronous write_attribute.
     * 
     * @param attribs Attribute values (name, writing value...)
     * @param forget forget the response if true
     */
    // ==========================================================================
    public int write_attribute_asynch(final DeviceProxy deviceProxy,
	    final DeviceAttribute[] attribs, final boolean forget) throws DevFailed {

		build_connection(deviceProxy);

		//
		// Manage Access control
		//
		if (deviceProxy.access == TangoConst.ACCESS_READ) {
	    	// pind the device to throw execption
	    	// if failed (for reconnection)
	    	ping(deviceProxy);

        	throwNotAuthorizedException(deviceProxy.devname + ".write_attribute_asynch()",
		    	"DeviceProxy.write_attribute_asynch()");
		}

		// Build idl argin object
		Request request;
		final String[] attributeNames = new String[attribs.length];

		Any any;
		if (deviceProxy.device_5 != null || deviceProxy.device_4 != null) {
	    	final AttributeValue_4[] attributeValues_4 = new AttributeValue_4[attribs.length];
	    	for (int i=0; i<attribs.length ; i++) {
                attributeValues_4[i] = attribs[i].getAttributeValueObject_4();
                attributeValues_4[i].err_list = new DevError[0];
                attributeNames[i] = attributeValues_4[i].name;
            }
	    	request = deviceProxy.device_4._request("write_attributes_4");

	    	any = request.add_in_arg();
	    	AttributeValueList_4Helper.insert(any, attributeValues_4);

	    	any = request.add_in_arg();
	    	ClntIdentHelper.insert(any, DevLockManager.getInstance().getClntIdent());
	    	request.exceptions().add(MultiDevFailedHelper.type());
		}
        else {
	    	final AttributeValue[] attributeValues = new AttributeValue[attribs.length];
	    	for (int i = 0; i < attribs.length; i++) {
			    attributeValues[i] = attribs[i].getAttributeValueObject_2();
			    attributeNames[i] = attributeValues[i].name;
	    	}

	    	// Create the request object
	    	if (deviceProxy.device_3 != null) {
			    request = deviceProxy.device_3._request("write_attributes_3");
	    	}
            else if (deviceProxy.device_2 != null) {
	    		request = deviceProxy.device_2._request("write_attributes");
	    	}
            else {
    			request = deviceProxy.device._request("write_attributes");
	    	}

	    	any = request.add_in_arg();
	    	AttributeValueListHelper.insert(any, attributeValues);
		}

		request.exceptions().add(DevFailedHelper.type());

		// send it (defered or just one way)

		int id = 0;
		boolean done = false;
		// try 2 times for reconnection if requested
		final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
		for (int i=0 ; i<retries && !done ; i++) {
	    	try {
                if (forget) {
                    request.send_oneway();
                } else {
                    request.send_deferred();
                    // store request reference to read reply later
                    id = ApiUtil.put_async_request(new AsyncCallObject(request, deviceProxy, ATT_W,
                        attributeNames));
                }
                done = true;
	    	} catch (final Exception e) {
                manageExceptionReconnection(deviceProxy,
                        retries, i, e, this.getClass() + ".write_attribute_asynch");
	    	}
		}
		return id;
    }

    // ==========================================================================
    /**
     * check for Asynchronous write_attribute reply.
     * 
     * @param id  asynchronous call id (returned by read_attribute_asynch).
     */
    // ==========================================================================
    public void write_attribute_reply(final DeviceProxy deviceProxy, final int id)
		    throws DevFailed {
		final Request request = ApiUtil.get_async_request(id);
        try {
            if (deviceProxy.device_5 != null || deviceProxy.device_4 != null) {
                check_asynch_reply(deviceProxy, request, id, "write_attributes_4");
            } else if (deviceProxy.device_3 != null) {
                check_asynch_reply(deviceProxy, request, id, "write_attributes_3");
            } else if (deviceProxy.device_2 != null) {
                check_asynch_reply(deviceProxy, request, id, "write_attributes");
            } else {
                check_asynch_reply(deviceProxy, request, id, "write_attributes");
            }
        }
        catch (ConnectionFailed e) {
            DeviceAttribute[]   deviceAttributes;
            try {
                //  If failed, retrieve data from request object
                final NVList args = request.arguments();
                final Any any = args.item(0).value();

                if (deviceProxy.idl_version>=4) {
                    AttributeValue_4[]    attributeValues_4 =
                            AttributeValueList_4Helper.extract(any);

                    deviceAttributes = new DeviceAttribute[attributeValues_4.length];
                    for (int i=0 ;  i<attributeValues_4.length ; i++) {
                        deviceAttributes[i] = new DeviceAttribute(attributeValues_4[i]);
                    }
                }
                else if (deviceProxy.idl_version>=3) {
                    AttributeValue[]    attributeValues =
                            AttributeValueListHelper.extract(any);
                    deviceAttributes = new DeviceAttribute[attributeValues.length];
                    for (int i=0 ;  i<attributeValues.length ; i++) {
                        deviceAttributes[i] = new DeviceAttribute(attributeValues[i]);
                    }
                }
                else
                    throw e;
            }
            catch (org.omg.CORBA.Bounds e1) {
                System.err.println(e1);
                throw e;
            }

            //  And do the synchronous write_attributes
            deviceProxy.write_attribute(deviceAttributes);
        }
    }

    // ==========================================================================
    /**
     * check for Asynchronous write_attribute reply.
     * 
     * @param id  asynchronous call id (returned by write_attribute_asynch).
     * @param timeout number of millisonds to wait reply before throw an excption.
     */
    // ==========================================================================
    public void write_attribute_reply(final DeviceProxy deviceProxy, final int id, final int timeout)
            throws DevFailed {
        final int ms_to_sleep = 10;
        AsynReplyNotArrived except = null;
        final long t0 = System.currentTimeMillis();
        long t1 = t0;
        boolean done = false;
        while ((t1 - t0 < timeout || timeout == 0) && !done) {
            try {
                write_attribute_reply(deviceProxy, id);
                done = true;
            } catch (final AsynReplyNotArrived na) {
                except = na;
                // Wait a bit before retry
                try { Thread.sleep(ms_to_sleep); } catch (final InterruptedException e) { /* */ }
                t1 = System.currentTimeMillis();
            }
        }
        // If reply not arrived throw last exception
        if (!done) {
            throw except;
        }
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute using callback for reply.
     * 
     * @param attr Attribute values (name, writing value...)
     * @param cb a CallBack object instance.
     */
    // ==========================================================================
    public void write_attribute_asynch(final DeviceProxy deviceProxy, final DeviceAttribute attr,
	    final CallBack cb) throws DevFailed {
        final DeviceAttribute[] attribs = new DeviceAttribute[1];
        attribs[0] = attr;
        write_attribute_asynch(deviceProxy, attribs, cb);
    }

    // ==========================================================================
    /**
     * Asynchronous write_attribute using callback for reply.
     * 
     * @param attribs Attribute values (name, writing value...)
     * @param cb a CallBack object instance.
     */
    // ==========================================================================
    public void write_attribute_asynch(final DeviceProxy deviceProxy,
	    final DeviceAttribute[] attribs, final CallBack cb) throws DevFailed {
        final int id = write_attribute_asynch(deviceProxy, attribs);
        ApiUtil.set_async_reply_model(id, CALLBACK);
        ApiUtil.set_async_reply_cb(id, cb);

        // if push callback, start a thread to do it
        if (ApiUtil.get_asynch_cb_sub_model() == PUSH_CALLBACK) {
            final AsyncCallObject aco = ApiUtil.get_async_object(id);
            new CallbackThread(aco).start();
        }
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     * 
     * @param reply_model  ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public int pending_asynch_call(final DeviceProxy deviceProxy, final int reply_model) {
        return ApiUtil.pending_asynch_call(deviceProxy, reply_model);
    }

    // ==========================================================================
    /**
     * Fire callback methods for all asynchronous requests(cmd and attr) with
     * already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies(final DeviceProxy deviceProxy) {
        ApiUtil.get_asynch_replies(deviceProxy);

    }

    // ==========================================================================
    /**
     * Fire callback methods for all asynchronous requests(cmd and attr) with
     * already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies(final DeviceProxy deviceProxy, final int timeout) {
        ApiUtil.get_asynch_replies(deviceProxy, timeout);
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
     * @param target The target for logging (e.g. file::/tmp/logging_device).
     */
    // ==========================================================================
    public void add_logging_target(final DeviceProxy deviceProxy, final String target)
	    throws DevFailed {
        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "add_logging_target");
        }

        // Prepeare data

        final String[] str = new String[2];
        str[0] = get_name(deviceProxy);
        str[1] = target;
        final DeviceData argin = new DeviceData();
        argin.insert(str);
        // And send command
        deviceProxy.getAdm_dev().command_inout("AddLoggingTarget", argin);
    }

    // ==========================================================================
    /**
     * Removes a new logging target to the device.
     */
    // ==========================================================================
    public void remove_logging_target(final DeviceProxy deviceProxy, final String target_type,
	    final String target_name) throws DevFailed {
        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "remove_logging_target");
        }

        // Prepeare data
        final String[] target = new String[2];
        target[0] = get_name(deviceProxy);
        target[1] = target_type + "::" + target_name;
        final DeviceData argin = new DeviceData();
        argin.insert(target);
        // And send command
        deviceProxy.getAdm_dev().command_inout("RemoveLoggingTarget", argin);
    }

    // ==========================================================================
    /**
     * get logging target from the device.
     */
    // ==========================================================================
    public String[] get_logging_target(final DeviceProxy deviceProxy) throws DevFailed {
        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "get_logging_target");
        }

        // Prepeare data
        final DeviceData argin = new DeviceData();
        argin.insert(get_name(deviceProxy));
        // And send command
        final DeviceData argout = deviceProxy.getAdm_dev().command_inout("GetLoggingTarget", argin);
        return argout.extractStringArray();
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
    public int get_logging_level(final DeviceProxy deviceProxy) throws DevFailed {
        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "get_logging_level");
        }

        // Prepeare data
        final String[] target = new String[1];
        target[0] = get_name(deviceProxy);
        final DeviceData argin = new DeviceData();
        argin.insert(target);
        // And send command
        final DeviceData argout = deviceProxy.getAdm_dev().command_inout("GetLoggingLevel", argin);
        final DevVarLongStringArray lsa = argout.extractLongStringArray();
        return lsa.lvalue[0];
    }

    // ==========================================================================
    /**
     * Set logging level from the device.
     * 
     * @param level device's logging level: (ApiDefs.LOGGING_OFF,
     *            ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
     *            ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
     */
    // ==========================================================================
    public void set_logging_level(final DeviceProxy deviceProxy, final int level) throws DevFailed {
        // Get connection on administration device
        if (deviceProxy.getAdm_dev() == null) {
            import_admin_device(deviceProxy, "set_logging_level");
        }

        // Prepeare data
        final DevVarLongStringArray lsa = new DevVarLongStringArray();
        lsa.lvalue = new int[1];
        lsa.svalue = new String[1];
        lsa.lvalue[0] = level;
        lsa.svalue[0] = get_name(deviceProxy);
        final DeviceData argin = new DeviceData();
        argin.insert(lsa);
        // And send command
        deviceProxy.getAdm_dev().command_inout("SetLoggingLevel", argin);
    }

    // ==========================================================================
    // Locking Device 4 commands
    // ==========================================================================

    // ==========================================================================
    /**
     * Lock the device
     * 
     * @param validity Lock validity (in seconds)
     */
    // ==========================================================================
    public void lock(final DeviceProxy deviceProxy, final int validity) throws DevFailed {
	    DevLockManager.getInstance().lock(deviceProxy, validity);
    }

    // ==========================================================================
    /**
     * Unlock the device
     * 
     * @return the device lock counter
     */
    // ==========================================================================
    public int unlock(final DeviceProxy deviceProxy) throws DevFailed {
	    return DevLockManager.getInstance().unlock(deviceProxy);
    }

    // ==========================================================================
    /**
     * Returns true if the device is locked
     */
    // ==========================================================================
    public boolean isLocked(final DeviceProxy deviceProxy) throws DevFailed {
    	return DevLockManager.getInstance().isLocked(deviceProxy);
    }

    // ==========================================================================
    /**
     * Returns true if the device is locked by this process
     */
    // ==========================================================================
    public boolean isLockedByMe(final DeviceProxy deviceProxy) throws DevFailed {
        return DevLockManager.getInstance().isLockedByMe(deviceProxy);
    }

    // ==========================================================================
    /**
     * Returns the device lock status
     */
    // ==========================================================================
    public String getLockerStatus(final DeviceProxy deviceProxy) throws DevFailed {
    	return DevLockManager.getInstance().getLockerStatus(deviceProxy);
    }

    // ==========================================================================
    /**
     * Returns the device lock info
     */
    // ==========================================================================
    public LockerInfo getLockerInfo(final DeviceProxy deviceProxy) throws DevFailed {
        return DevLockManager.getInstance().getLockerInfo(deviceProxy);
    }

    // ==========================================================================
    // ==========================================================================

    // ==========================================================================
    /**
     * TACO commands
     */
    // ==========================================================================
    // ==========================================================================
    /**
     * Returns TACO device information.
     * 
     * @return TACO device information as String array. <li>Device name. <li>
     *         Class name <li>Device type <li>Device server name <li>Host name
     */
    // ==========================================================================
    public String[] dev_inform(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfTaco(deviceProxy, "dev_inform");
        if (deviceProxy.taco_device == null && deviceProxy.devname != null) {
            build_connection(deviceProxy);
        }
        return deviceProxy.taco_device.dev_inform();
    }

    // ==========================================================================
    /**
     * Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
     * 
     * @param mode RPC protocol mode to be seted
     *            (TangoApi.TacoDevice.<b>D_TCP</b> or
     *            TangoApi.TacoDevice.<b>D_UDP</b>).
     */
    // ==========================================================================
    public void set_rpc_protocol(final DeviceProxy deviceProxy, final int mode) throws DevFailed {
        checkIfTaco(deviceProxy, "dev_rpc_protocol");

        build_connection(deviceProxy);

        deviceProxy.taco_device.set_rpc_protocol(mode);
    }

    // ==========================================================================
    /**
     * @return mode RPC protocol mode used (TangoApi.TacoDevice.<b>D_TCP</b> or
     *         TangoApi.TacoDevice.<b>D_UDP</b>).
     */
    // ==========================================================================
    public int get_rpc_protocol(final DeviceProxy deviceProxy) throws DevFailed {
        checkIfTaco(deviceProxy, "get_rpc_protocol");

        build_connection(deviceProxy);

        return deviceProxy.taco_device.get_rpc_protocol();
    }


    // ===================================================================
    /*
     * ToDo Pipe related methods
     */
    // ===================================================================

    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig(DeviceProxy deviceProxy) throws DevFailed {
        build_connection(deviceProxy);
        if (deviceProxy.idl_version<5)
            Except.throw_exception("TangoApi_NOT_SUPPORTED",
                    "Pipe not supported in IDL " + deviceProxy.idl_version);
        ArrayList<PipeInfo> infoList = new ArrayList<PipeInfo>();
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int tr=0; tr<retries && !done ; tr++) {
            try {
                PipeConfig[]    configurations =
                        deviceProxy.device_5.get_pipe_config_5(new String[]{"All pipes"});
                for (PipeConfig configuration : configurations) {
                    infoList.add(new PipeInfo(configuration));
                }
                done = true;
            } catch (final DevFailed e) {
                // Except.print_exception(e);
                throw e;
            } catch (final Exception e) {
                if (e.toString().startsWith("org.omg.CORBA.UNKNOWN: Server-side")) {
                    //  Server does not implement pipe features
                    return infoList;
                }
                manageExceptionReconnection(deviceProxy, retries, tr, e,
                        this.getClass() + ".DeviceProxy.getPipeConfig");
            }
        }
        return infoList;
    }
    // ===================================================================
    /**
     * Query device for pipe configuration list
     * @param deviceProxy device proxy object
     * @param pipeNames pipe names.
     * @return  pipe configuration list
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public List<PipeInfo> getPipeConfig(DeviceProxy deviceProxy, List<String> pipeNames) throws DevFailed {
        build_connection(deviceProxy);
        if (deviceProxy.idl_version<5)
            Except.throw_exception("TangoApi_NOT_SUPPORTED",
                    "Pipe not supported in IDL " + deviceProxy.idl_version);
        ArrayList<PipeInfo> infoList = new ArrayList<PipeInfo>();
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int tr=0 ; tr<retries && !done ; tr++) {
            try {
                String[]    array = new String[pipeNames.size()];
                for (int i=0 ; i<pipeNames.size() ; i++)
                    array[i] = pipeNames.get(i);
                PipeConfig[]    configurations = deviceProxy.device_5.get_pipe_config_5(array);
                for (PipeConfig configuration : configurations) {
                    infoList.add(new PipeInfo(configuration));
                }
                done = true;
            }
            catch (final DevFailed e) {
                throw e;
            }
            catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, tr, e,
                        this.getClass() + ".DeviceProxy.getPipeConfig");
            }
        }
        return infoList;
    }
    // ===================================================================
    /**
     * Set device pipe configuration
     * @param deviceProxy device proxy object
     * @param pipeInfoList info list containing pipe name, description, label,....
     * @throws DevFailed if device connection failed
     */
    // ===================================================================
    public void setPipeConfig(DeviceProxy deviceProxy, List<PipeInfo> pipeInfoList) throws DevFailed {
        build_connection(deviceProxy);
        if (deviceProxy.idl_version<5)
            Except.throw_exception("TangoApi_NOT_SUPPORTED",
                    "Pipe not supported in IDL " + deviceProxy.idl_version);
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int tr=0 ; tr<retries && !done ; tr++) {
            try {
                PipeConfig[]  configList = new PipeConfig[pipeInfoList.size()];
                for (int i=0 ; i<pipeInfoList.size() ; i++) {
                    configList[i] = pipeInfoList.get(i).getPipeConfig();
                }
                deviceProxy.device_5.set_pipe_config_5(
                        configList, DevLockManager.getInstance().getClntIdent());
                done = true;
            }
            catch (final DevFailed e) {
                throw e;
            }
            catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, tr, e,
                        this.getClass() + ".DeviceProxy.setPipeConfig");
            }
        }
}
    // ===================================================================
    /**
     * Read specified pipe and returns read data
     * Read specified pipe and returns read data
     * @param deviceProxy device proxy object
     * @param pipeName pipe name
     * @return data read from specified pipe.
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public DevicePipe readPipe(DeviceProxy deviceProxy, String pipeName) throws DevFailed {
        build_connection(deviceProxy);
        if (deviceProxy.idl_version<5)
            Except.throw_exception("TangoApi_NOT_SUPPORTED",
                    "Pipe not supported in IDL " + deviceProxy.idl_version);
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int tr=0 ; tr<retries && !done ; tr++) {
            try {
                DevPipeData pipeData = deviceProxy.device_5.read_pipe_5(
                        pipeName, DevLockManager.getInstance().getClntIdent());
                done = true;
                return new DevicePipe(pipeData);
            }
            catch (final DevFailed e) {
                throw e;
            }
            catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, tr, e,
                        this.getClass() + ".DeviceProxy.readPipe");
            }
        }
        return null;    //  cannot occur
    }
    // ===================================================================
    /**
     * Write data in specified pipe
     * @param deviceProxy device proxy object
     * @param devicePipe data to be written
     * @throws DevFailed in case of device connection failed or pipe not found.
     */
    // ===================================================================
    public void writePipe(DeviceProxy deviceProxy, DevicePipe devicePipe) throws DevFailed {
        build_connection(deviceProxy);
        if (deviceProxy.idl_version<5)
            Except.throw_exception("TangoApi_NOT_SUPPORTED",
                    "Pipe not supported in IDL " + deviceProxy.idl_version);
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int tr=0 ; tr<retries && !done ; tr++) {
            try {
                DevPipeData devPipeData = devicePipe.getDevPipeDataObject();
                deviceProxy.device_5.write_pipe_5(
                        devPipeData, DevLockManager.getInstance().getClntIdent());
                done = true;
            }
            catch (final DevFailed e) {
                throw e;
            }
            catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, tr, e,
                        this.getClass() + ".DeviceProxy.writePipe");
            }
        }
    }
    // ===================================================================
    // ===================================================================
    public DevicePipe writeReadPipe(DeviceProxy deviceProxy, DevicePipe devicePipe) throws DevFailed {
        //  ToDo
        build_connection(deviceProxy);
        if (deviceProxy.idl_version<5)
            Except.throw_exception("TangoApi_NOT_SUPPORTED",
                    "Pipe not supported in IDL " + deviceProxy.idl_version);
        boolean done = false;
        final int retries = deviceProxy.transparent_reconnection ? 2 : 1;
        for (int tr = 0 ; tr<retries && !done ; tr++) {
            try {
                DevPipeData writePipeData = devicePipe.getDevPipeDataObject();
                DevPipeData readPipeData = deviceProxy.device_5.write_read_pipe_5(
                        writePipeData, DevLockManager.getInstance().getClntIdent());
                done = true;
                return new DevicePipe(readPipeData);
            } catch (final DevFailed e) {
                throw e;
            } catch (final Exception e) {
                manageExceptionReconnection(deviceProxy, retries, tr, e,
                        this.getClass() + ".DeviceProxy.writePipe");
            }
        }
        return null;    //  cannot occur
    }
    // ===================================================================
    // ===================================================================




    // ===================================================================
    /*
     * Event related methods
     */
    // ===================================================================

    // ==========================================================================
    /**
     * Subscribe to an event.
     * 
     * @param attributeName attribute name.
     * @param event event name.
     * @param callback  event callback.
     * @param stateless  If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(final DeviceProxy deviceProxy, final String attributeName,
	            final int event, final CallBack callback, final String[] filters,
	            final boolean stateless) throws DevFailed {
        int id = 0;
        try {
            id = EventConsumerUtil.getInstance().subscribe_event(deviceProxy,
                    attributeName.toLowerCase(), event, callback, filters, stateless);
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            e.printStackTrace();
            Except.throw_communication_failed(e.toString(), "Subscribe event on "
                + name(deviceProxy) + "/" + attributeName + " Failed !",
                "DeviceProxy.subscribe_event()");
        }
        return id;
    }

    // ==========================================================================
    /**
     * Subscribe to event to be stored in an event queue.
     * 
     * @param attributeName attribute name.
     * @param event event name.
     * @param max_size event queue maximum size.
     * @param stateless If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(final DeviceProxy deviceProxy, final String attributeName,
	            final int event, final int max_size, final String[] filters,
                final boolean stateless) throws DevFailed {
        int id = 0;
        try {
            id = EventConsumerUtil.getInstance().subscribe_event(deviceProxy,
                    attributeName.toLowerCase(), event, max_size, filters, stateless);
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            Except.throw_communication_failed(e.toString(), "Subscribe event on "
                + name(deviceProxy) + "/" + attributeName + " Failed !",
                "DeviceProxy.subscribe_event()");
        }
        return id;
    }
    // ==========================================================================
    /**
     * Subscribe to an event.
     *
     * @param event event name.
     * @param callback  event callback.
     * @param stateless  If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(final DeviceProxy deviceProxy,
	            final int event, final CallBack callback, final boolean stateless) throws DevFailed {
        int id = 0;
        try {
            id = EventConsumerUtil.getInstance().subscribe_event(
                    deviceProxy, event, callback, -1, stateless);
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            e.printStackTrace();
            Except.throw_communication_failed(e.toString(), "Subscribe event on "
                + name(deviceProxy) + "/" + deviceProxy.devname + " Failed !",
                "DeviceProxy.subscribe_event()");
        }
        return id;
    }

    // ==========================================================================
    /**
     * Subscribe to event to be stored in an event queue.
     *
     * @param event event name.
     * @param max_size event queue maximum size.
     * @param stateless If true, do not throw exception if connection failed.
     */
    // ==========================================================================
    public int subscribe_event(final DeviceProxy deviceProxy,
	            final int event, final int max_size, final boolean stateless) throws DevFailed {
        int id = 0;
        try {
            id = EventConsumerUtil.getInstance().subscribe_event(
                    deviceProxy, event, null, max_size, stateless);
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            Except.throw_communication_failed(e.toString(), "Subscribe event on "
                + name(deviceProxy) + "/" + deviceProxy.devname + " Failed !",
                "DeviceProxy.subscribe_event()");
        }
        return id;
    }

    // ==========================================================================
    /**
     * Unsubscribe to an event.
     * 
     * @param event_id event identifier.
     */
    // ==========================================================================
    public void unsubscribe_event(final DeviceProxy deviceProxy, final int event_id) throws DevFailed {
        try {
            EventConsumerUtil.getInstance().unsubscribe_event(event_id);
        } catch (final DevFailed e) {
            throw e;
        } catch (final Exception e) {
            Except.throw_communication_failed(e.toString(), "Unsubsrcibe event on event ID "
                + event_id + " Failed !", "DeviceProxy.unsubscribe_event()");
        }
    }

    // ==========================================================================
    // ==========================================================================
}
