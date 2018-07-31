/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.properties;

import fr.esrf.Tango.DevFailed;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.utils.DevFailedUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class DevicePropertyImpl {
    private final XLogger xlogger = XLoggerFactory.getXLogger(DevicePropertyImpl.class);
    private final Method propertyMethod;
    private final String description;
    private final Object businessObject;
    private final String deviceName;
    private final String className;
    private final String name;
    private final String[] defaultValue;
    private final boolean isMandatory;

    public DevicePropertyImpl(final String propertyName, final String description, final Method propertyMethod,
                              final Object businessObject, final String deviceName, final String className, final boolean isMandatory,
                              final String... defaultValue) throws DevFailed {
        this.description = description;
        name = propertyName;
        this.propertyMethod = propertyMethod;
        this.businessObject = businessObject;
        this.deviceName = deviceName;
        this.className = className;
        this.isMandatory = isMandatory;
        if (defaultValue.length == 0 || (defaultValue.length == 1 && defaultValue[0].isEmpty())) {
            this.defaultValue = new String[0];
        }else{
            this.defaultValue = defaultValue;
        }
    }

    public void update() throws DevFailed {
        xlogger.entry(name);
        final String[] property = PropertiesUtils.getDeviceProperty(deviceName, className, name);
        if (isMandatory && (property.length == 0 || property[0].isEmpty())) {
            throw DevFailedUtils.newDevFailed(name + " device property is mandatory");
        }
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
