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
package org.tango.server.device;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.servant.AttributeGetterSetter;
import org.tango.server.servant.DeviceImpl;

import fr.esrf.Tango.DevFailed;

/**
 * Global info and tool for a device. Injected with {@link DeviceManagement}
 * 
 * @author ABEILLE
 * 
 */
public final class DeviceManager {

    private final DeviceImpl device;
    private final String name;
    private final String className;
    private final String adminName;

    /**
     * Ctr
     * 
     * @param device
     */
    public DeviceManager(final DeviceImpl device) {
	this.device = device;
	name = device.getName();
	className = device.getClassName();
	adminName = device.adm_name();
    }

    /**
     * 
     * @return name of the device
     */
    public String getName() {
	return name;
    }

    /**
     * 
     * @return class of the device, as defined in tango db
     */
    public String getClassName() {
	return className;
    }

    /**
     * 
     * @return admin device name
     */
    public String getAdminName() {
	return adminName;
    }

    /**
     * Configure an attribute's properties
     * 
     * @param attributeName
     *            the attribute name
     * @param properties
     *            its properties
     * @throws DevFailed
     */
    public void setAttributeProperties(final String attributeName, final AttributePropertiesImpl properties)
	    throws DevFailed {
	final AttributeImpl attr = AttributeGetterSetter.getAttribute(attributeName, device.getAttributeList());
	attr.setProperties(properties);
    }

    /**
     * Remove an attribute's properties
     * 
     * @param attributeName
     *            the attribute name
     * @throws DevFailed
     */
    public void removeAttributeProperties(final String attributeName) throws DevFailed {
	final AttributeImpl attr = AttributeGetterSetter.getAttribute(attributeName, device.getAttributeList());
	attr.removeProperties();
    }

    /**
     * Check is an attribute or an command is polled
     * 
     * @param polledObject
     *            The name of the polled object (attribute or command)
     * @return true if polled
     * @throws DevFailed
     */
    public boolean isPolled(final String polledObject) throws DevFailed {
	try {
	    return AttributeGetterSetter.getAttribute(polledObject, device.getAttributeList()).isPolled();
	} catch (final DevFailed e) {
	    return device.getCommand(polledObject).isPolled();
	}

    }

    /**
     * Get polling period of an attribute or a command
     * 
     * @param polledObject
     *            The name of the polled object (attribute or command)
     * @return The polling period
     * @throws DevFailed
     */
    public int getPollingPeriod(final String polledObject) throws DevFailed {
	try {
	    return AttributeGetterSetter.getAttribute(polledObject, device.getAttributeList()).getPollingPeriod();
	} catch (final DevFailed e) {
	    return device.getCommand(polledObject).getPollingPeriod();
	}
    }

    /**
     * Update polling cache. Works only if polling period is zero.
     * 
     * @param polledObject
     *            The name of the polled object (attribute or command)
     * @throws DevFailed
     */
    public void triggerPolling(final String polledObject) throws DevFailed {
	device.triggerPolling(polledObject);
    }

    @Override
    public String toString() {
	final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this,
		ToStringStyle.SHORT_PREFIX_STYLE);
	reflectionToStringBuilder.setExcludeFieldNames(new String[] { "device" });
	return reflectionToStringBuilder.toString();
    }
}
