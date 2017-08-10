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
package org.tango.server.build;

import fr.esrf.Tango.DevFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.servant.DeviceImpl;

import java.util.*;

/**
 * Builder of a Tango class
 * 
 * @author ABEILLE
 * 
 */
public class DeviceClassBuilder {

    private final Logger logger = LoggerFactory.getLogger(DeviceClassBuilder.class);
    /**
     * The TANGO device class name
     */
    private final Class<?> clazz;

    /**
     * The device list
     */
    private final Map<String, DeviceImpl> deviceImplMap = new LinkedHashMap<String, DeviceImpl>();
    private final String className;

    /**
     * Construct a newly allocated DeviceClass object.
     * 
     * @param clazz
     *            The class of the device
     * @param className
     *            The Tango device class name
     */
    public DeviceClassBuilder(final Class<?> clazz, final String className) {
        this.clazz = clazz;
        this.className = className;
    }

    public DeviceImpl buildDevice(final String name) throws DevFailed {
        final String lowerName = name.toLowerCase(Locale.ENGLISH);
        logger.debug("create device {} of class {}", lowerName, clazz.getName());
        final DeviceImpl dev = new DeviceBuilder(clazz, className, name).createDevice();
        deviceImplMap.put(lowerName, dev);

        return dev;

    }

    public void removeDevice(final String name) {
        final String lowerName = name.toLowerCase(Locale.ENGLISH);
        logger.debug("remove device {}", lowerName);
        deviceImplMap.remove(lowerName);
    }

    public List<DeviceImpl> getDeviceImplList() {
        return new LinkedList<DeviceImpl>(deviceImplMap.values());
    }

    public DeviceImpl getDeviceImpl(final String deviceName) {
        return deviceImplMap.get(deviceName.toLowerCase(Locale.ENGLISH));
    }

    public boolean containsDevice(final String deviceName) {
        return deviceImplMap.containsKey(deviceName.toLowerCase(Locale.ENGLISH));
    }

    public void clearDevices() {
        deviceImplMap.clear();
    }

    public List<String> getDeviceNameList() {
        return new ArrayList<String>(deviceImplMap.keySet());
    }

    public Class<?> getDeviceClass() {
        return clazz;
    }

    public String getClassName() {
        return className;
    }

}
