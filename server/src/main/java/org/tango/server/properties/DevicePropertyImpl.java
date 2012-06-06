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
package org.tango.server.properties;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import fr.esrf.Tango.DevFailed;

public final class DevicePropertyImpl {
    private final XLogger xlogger = XLoggerFactory.getXLogger(DevicePropertyImpl.class);
    private final Method propertyMethod;
    private final String description;
    private final Object businessObject;
    private final String deviceName;
    private final String className;
    private final String name;
    private final String[] defaultValue;

    public DevicePropertyImpl(final String propertyName, final String description, final Method propertyMethod,
	    final Object businessObject, final String deviceName, final String className, final String... defaultValue)
	    throws DevFailed {
	this.description = description;
	name = propertyName;
	this.propertyMethod = propertyMethod;
	this.businessObject = businessObject;
	this.deviceName = deviceName;
	this.className = className;
	this.defaultValue = defaultValue.length == 0 ? new String[] { "" } : defaultValue;
    }

    public void update() throws DevFailed {
	xlogger.entry(name);
	final String[] property = PropertiesUtils.getDeviceProperty(deviceName, className, name);
	PropertiesUtils.injectProperty(name, propertyMethod, property, businessObject, defaultValue);
	xlogger.exit();
    }

    public String getName() {
	return name;
    }

    public String getDescription() {
	return description;
    }

    public String[] getDefaultValue() {
	return Arrays.copyOf(defaultValue, defaultValue.length);
    }

}
