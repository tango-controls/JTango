/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.ExceptionMessages;
import org.tango.server.admin.AdminDevice;
import org.tango.server.build.DeviceClassBuilder;
import org.tango.server.cache.TangoCacheManager;
import org.tango.server.properties.PropertiesUtils;
import org.tango.server.servant.Constants;
import org.tango.server.servant.DeviceImpl;
import org.tango.server.servant.ORBUtils;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

public final class TangoExporter implements IExporter {

    private static final String ADMIN_SERVER_CLASS_NAME = "DServer";
    private final Logger logger = LoggerFactory.getLogger(TangoExporter.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(TangoExporter.class);

    private final String hostName;
    private final String serverName;
    private final String pid;
    private final Map<String, Class<?>> tangoClasses;
    private final List<DeviceClassBuilder> deviceClassList = new ArrayList<DeviceClassBuilder>();

    public TangoExporter(final String hostName, final String serverName, final String pid,
	    final Map<String, Class<?>> tangoClasses) {
	this.hostName = hostName;
	this.serverName = serverName;
	this.pid = pid;
	this.tangoClasses = new HashMap<String, Class<?>>(tangoClasses);
    }

    /**
     * Build all devices of all classes that are is this executable
     * 
     * @throws DevFailed
     */
    @Override
    public void exportAll() throws DevFailed {
	// load tango db cache
	DatabaseFactory.getDatabase().loadCache(serverName, hostName);
	// special case for admin device
	final DeviceClassBuilder clazz = new DeviceClassBuilder(AdminDevice.class, ADMIN_SERVER_CLASS_NAME);
	deviceClassList.add(clazz);
	final DeviceImpl dev = buildDevice(Constants.ADMIN_DEVICE_DOMAIN + "/" + serverName, clazz);
	((AdminDevice) dev.getBusinessObject()).setTangoExporter(this);
	((AdminDevice) dev.getBusinessObject()).setClassList(deviceClassList);

	// load server class
	exportDevices();

	// init polling pool config
	TangoCacheManager.initPoolConf();

	// clear tango db cache (used only for server start-up phase)
	DatabaseFactory.getDatabase().clearCache();
    }

    /**
     * Export all devices except admin device
     * 
     * @throws DevFailed
     */
    @Override
    public void exportDevices() throws DevFailed {
	// load server class
	for (final Entry<String, Class<?>> entry : tangoClasses.entrySet()) {
	    final String tangoClass = entry.getKey();
	    final Class<?> deviceClass = entry.getValue();
	    logger.debug("loading class {}", deviceClass.getCanonicalName());
	    final DeviceClassBuilder deviceClassBuilder = new DeviceClassBuilder(deviceClass, tangoClass);
	    deviceClassList.add(deviceClassBuilder);
	    // export all its devices
	    final String[] deviceList = DatabaseFactory.getDatabase().getDeviceList(serverName, tangoClass);
	    logger.debug("devices found  {}", Arrays.toString(deviceList));
	    if (deviceList.length == 0) {
		DevFailedUtils.throwDevFailed(ExceptionMessages.DB_ACCESS, "No device defined in database for class "
			+ tangoClass);
	    }
	    for (final String deviceName : deviceList) {
		buildDevice(deviceName, deviceClassBuilder);
	    }
	}
    }

    /**
     * Unexport all except admin device
     * 
     * @throws DevFailed
     */
    @Override
    public void unexportDevices() throws DevFailed {
	xlogger.entry();
	final List<DeviceClassBuilder> clazzToRemove = new ArrayList<DeviceClassBuilder>();
	for (final DeviceClassBuilder clazz : deviceClassList) {
	    if (!clazz.getDeviceClass().equals(AdminDevice.class)) {
		for (final DeviceImpl device : clazz.getDeviceImplList()) {
		    logger.debug("unexport device {}", device.getName());
		    ORBUtils.unexportDevice(device);
		}
		clazz.clearDevices();
		clazzToRemove.add(clazz);
	    }
	}
	for (final DeviceClassBuilder deviceClassBuilder : clazzToRemove) {
	    deviceClassList.remove(deviceClassBuilder);
	}
	// unregisterServer();
	// deviceClassList.clear();
	xlogger.exit();
    }

    @Override
    public void unexportAll() throws DevFailed {
	xlogger.entry();
	for (final DeviceClassBuilder clazz : deviceClassList) {
	    for (final DeviceImpl device : clazz.getDeviceImplList()) {
		logger.debug("unexport device {}", device.getName());
		device.unLock(true);
		try {
		    ORBUtils.unexportDevice(device);
		} catch (final DevFailed e) {
		}
	    }
	    clazz.clearDevices();
	}
	unregisterServer();
	deviceClassList.clear();
	// remove all cache of device and class properties to reload them
	PropertiesUtils.clearCache();

	xlogger.exit();
    }

    private void unregisterServer() {
	xlogger.entry();
	if (serverName != null) {
	    try {
		logger.info("unexporting server {}", serverName);
		DatabaseFactory.getDatabase().unexportServer(serverName);
	    } catch (final DevFailed e) {
		logger.debug(DevFailedUtils.toString(e));
	    }
	}
	xlogger.exit();
    }

    @Override
    public DeviceImpl buildDevice(final String name, final DeviceClassBuilder classBuilder) throws DevFailed {
	final DeviceImpl devToClean = classBuilder.getDeviceImpl(name);
	if (devToClean != null) {
	    logger.info("unexporting device {}", devToClean.getName());
	    ORBUtils.unexportDevice(devToClean);
	}
	final DeviceImpl dev = classBuilder.buildDevice(name);
	logger.info("exporting device {}", dev.getName());
	ORBUtils.exportDevice(dev, hostName, pid);
	return dev;
    }

    @Override
    public DeviceImpl getDevice(final String className, final String deviceName) throws DevFailed {
	if (!className.equalsIgnoreCase(ADMIN_SERVER_CLASS_NAME) && !tangoClasses.containsKey(className)) {
	    DevFailedUtils.throwDevFailed(ExceptionMessages.CLASS_NOT_FOUND, className
		    + " does not exists on this server");
	}
	DeviceImpl device = null;
	for (final DeviceClassBuilder classBuilder : deviceClassList) {
	    if (className.equalsIgnoreCase(classBuilder.getClassName())) {
		device = classBuilder.getDeviceImpl(deviceName);
		break;
	    }
	}
	if (device == null) {
	    DevFailedUtils.throwDevFailed(ExceptionMessages.DEVICE_NOT_FOUND, deviceName
		    + " does not exists on this server");
	}
	return device;
    }

    /**
     * Get the started devices of this server. WARNING: result is filled after
     * server has been started
     * 
     * @param tangoClass
     * @return The devices
     * @throws DevFailed
     */
    public String[] getDevicesOfClass(final String tangoClass) throws DevFailed {
	if (!tangoClasses.containsKey(tangoClass)) {
	    DevFailedUtils.throwDevFailed(ExceptionMessages.CLASS_NOT_FOUND, tangoClass
		    + " does not exists on this server");
	}
	String[] deviceNames = new String[] {};
	for (final DeviceClassBuilder classBuilder : deviceClassList) {
	    if (tangoClass.equalsIgnoreCase(classBuilder.getClassName())) {
		final List<String> list = classBuilder.getDeviceNameList();
		deviceNames = list.toArray(new String[list.size()]);
		break;
	    }
	}
	return deviceNames;
    }

    public List<DeviceClassBuilder> getDeviceClassList() {
	return new ArrayList<DeviceClassBuilder>(deviceClassList);
    }

    public void clearClass() {
	tangoClasses.clear();
    }

}
