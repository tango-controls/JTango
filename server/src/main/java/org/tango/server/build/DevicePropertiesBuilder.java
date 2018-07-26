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
import org.tango.server.annotation.DeviceProperties;
import org.tango.server.properties.DevicePropertiesImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link DeviceProperties}
 * 
 * @author ABEILLE
 * 
 */
final class DevicePropertiesBuilder {

    private final Logger logger = LoggerFactory.getLogger(DevicePropertiesBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DevicePropertiesBuilder.class);

    /**
     * Create class properties {@link DeviceProperties}
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
	// Inject each device property
	final String fieldName = field.getName();
	logger.debug("Has a DeviceProperties : {}", fieldName);
	BuilderUtils.checkStatic(field);
	final String setterName = BuilderUtils.SET + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
		+ fieldName.substring(1);
	Method setter = null;
	try {
	    setter = businessObject.getClass().getMethod(setterName, field.getType());
	} catch (final NoSuchMethodException e) {
	    throw DevFailedUtils.newDevFailed(e);
	}
	final DevicePropertiesImpl property = new DevicePropertiesImpl(setter, businessObject, device.getName());
	device.setDeviceProperties(property);

	xlogger.exit();
    }

}
