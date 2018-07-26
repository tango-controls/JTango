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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.properties.DevicePropertyImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link DeviceProperty}
 * 
 * @author ABEILLE
 * 
 */
final class DevicePropertyBuilder {

    private final Logger logger = LoggerFactory.getLogger(DevicePropertyBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DevicePropertyBuilder.class);

    /**
     * Create device property {@link DeviceProperty}
     * 
     * @param clazz
     * @param field
     * @param device
     * @param businessObject
     * @throws DevFailed
     */
    public void build(final Class<?> clazz, final Field field, final DeviceImpl device, final Object businessObject)
            throws DevFailed {
        xlogger.entry();
        try {
            // Inject each device property
            final DeviceProperty annot = field.getAnnotation(DeviceProperty.class);
            final String fieldName = field.getName();
            String propName;
            if (annot.name().equals("")) {
                propName = fieldName;
            } else {
                propName = annot.name();
            }
            logger.debug("Has a DeviceProperty : {}", propName);
            BuilderUtils.checkStatic(field);
            String setterName = BuilderUtils.SET + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                    + fieldName.substring(1);
            Method setter = null;
            try {
                setter = businessObject.getClass().getMethod(setterName, field.getType());
            } catch (final NoSuchMethodException e) {
                if (fieldName.startsWith(BuilderUtils.IS)) {
                    setterName = BuilderUtils.SET + fieldName.substring(2);
                    try {
                        setter = businessObject.getClass().getMethod(setterName, field.getType());
                    } catch (final NoSuchMethodException e1) {
                        throw DevFailedUtils.newDevFailed(e);
                    }
                } else {
                    throw DevFailedUtils.newDevFailed(e);
                }
            }
            final DevicePropertyImpl property = new DevicePropertyImpl(propName, annot.description(), setter,
                    businessObject, device.getName(), device.getClassName(), annot.isMandatory(), annot.defaultValue());
            device.addDeviceProperty(property);
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

}
