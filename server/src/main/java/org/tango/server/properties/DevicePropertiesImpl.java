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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

public final class DevicePropertiesImpl {

    private final XLogger xlogger = XLoggerFactory.getXLogger(DevicePropertiesImpl.class);
    private final Method propertyMethod;
    private final Object businessObject;
    private final String deviceName;

    public DevicePropertiesImpl(final Method propertyField, final Object businessObject, final String deviceName) {
	propertyMethod = propertyField;
	this.businessObject = businessObject;
	this.deviceName = deviceName;
    }

    /**
     * update all properties and values of this device
     * 
     * @throws DevFailed
     */
    public void update() throws DevFailed {
	xlogger.entry();
	final Map<String, String[]> property = PropertiesUtils.getDeviceProperties(deviceName);

	if (property != null && property.size() != 0) {
	    try {
		propertyMethod.invoke(businessObject, property);
	    } catch (final IllegalArgumentException e) {
		throw DevFailedUtils.newDevFailed(e);
	    } catch (final IllegalAccessException e) {
		throw DevFailedUtils.newDevFailed(e);
	    } catch (final SecurityException e) {
		throw DevFailedUtils.newDevFailed(e);
	    } catch (final InvocationTargetException e) {
		throw DevFailedUtils.newDevFailed(e);
	    }
	}
	xlogger.exit();
    }

}
