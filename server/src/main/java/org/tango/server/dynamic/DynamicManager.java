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
package org.tango.server.dynamic;

import fr.esrf.Tango.DevFailed;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.command.CommandImpl;
import org.tango.server.command.ICommandBehavior;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Manage dynamic commands and attributes. This class is injected by {@link DynamicManagement}
 *
 * @author ABEILLE
 */
public final class DynamicManager {

    // private Logger logger =
    // LoggerFactory.getLogger(DynamicAttributeManager.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DynamicManager.class);
    /**
     * the associated device
     */
    private final DeviceImpl deviceImpl;
    /**
     * Map of dynamic attributes
     */
    private final Map<String, AttributeImpl> dynamicAttributes = new HashMap<String, AttributeImpl>();
    /**
     * Map of dynamic commands
     */
    private final Map<String, CommandImpl> dynamicCommands = new HashMap<String, CommandImpl>();

    private final List<String> forwardedAttributes = new ArrayList<String>();

    /**
     * Ctr
     *
     * @param deviceImpl the associated device
     */
    public DynamicManager(final DeviceImpl deviceImpl) {
        this.deviceImpl = deviceImpl;
    }

    /**
     * Add attribute. Only if not already exists on device.
     *
     * @param behavior
     * @throws DevFailed
     */
    public void addAttribute(final IAttributeBehavior behavior) throws DevFailed {
        final AttributeConfiguration configuration = behavior.getConfiguration();
        final String attributeName = configuration.getName();
        xlogger.entry("adding dynamic attribute {}", attributeName);
        if (behavior instanceof ForwardedAttribute) {
            // init attribute with either the attribute property value, or the value defined in its constructor
            final ForwardedAttribute att = (ForwardedAttribute) behavior;
            final String deviceName = deviceImpl.getName();
            final String rootAttributeName = behavior.getConfiguration().getAttributeProperties()
                    .loadAttributeRootName(deviceName, attributeName);
            if (rootAttributeName == null || rootAttributeName.isEmpty()
                    || rootAttributeName.equalsIgnoreCase(Constants.NOT_SPECIFIED)) {
                att.init(deviceName);
                // persist root attribute name in tango db
                behavior.getConfiguration().getAttributeProperties()
                        .persistAttributeRootName(deviceName, attributeName);
            } else {
                // use attribute property
                att.init(deviceName, rootAttributeName);
            }
            // check if this attribute is already created
            final String lower = att.getRootName().toLowerCase(Locale.ENGLISH);
            if (forwardedAttributes.contains(lower)) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.FWD_DOUBLE_USED,
                        "root attribute already used in this device");
            } else {
                forwardedAttributes.add(lower);
            }
        } else {
            // set default properties
            final AttributePropertiesImpl prop = configuration.getAttributeProperties();
            if (prop.getLabel().isEmpty()) {
                prop.setLabel(configuration.getName());
            }
            if (prop.getFormat().equals(Constants.NOT_SPECIFIED)) {
                prop.setDefaultFormat(configuration.getScalarType());
            }
        }
        final AttributeImpl attrImpl = new AttributeImpl(behavior, deviceImpl.getName());
        attrImpl.setStateMachine(behavior.getStateMachine());
        deviceImpl.addAttribute(attrImpl);
        dynamicAttributes.put(attributeName.toLowerCase(Locale.ENGLISH), attrImpl);
        deviceImpl.pushInterfaceChangeEvent(false);
        if (configuration.isPolled() && configuration.getPollingPeriod() > 0) {
            deviceImpl.addAttributePolling(attributeName, configuration.getPollingPeriod());
        }
        xlogger.exit();
    }

    /**
     * Load memorized attribute value and attribute properties from tangodb.
     * This loading is done by default at device init, should only be called when dynamic attribute is created outside {@link Init}
     *
     * @param attributeName
     * @throws DevFailed
     */
    public void loadAttributeConfigFromDb(final String attributeName) throws DevFailed {
        final AttributeImpl toConfigure = dynamicAttributes.get(attributeName.toLowerCase(Locale.ENGLISH));
        if (toConfigure != null) {
            toConfigure.lock();
            try {
                toConfigure.loadTangoDbConfig();
            }finally {
                toConfigure.unlock();
            }
        }
    }

    /**
     * remove a dynamic attribute
     *
     * @param attributeName attribute name
     * @throws DevFailed
     */
    public void removeAttribute(final String attributeName) throws DevFailed {
        final AttributeImpl toRemove = dynamicAttributes.get(attributeName.toLowerCase(Locale.ENGLISH));
        if (toRemove == null)
            throw DevFailedUtils.newDevFailed("API_AttributeNotFound", "Attribute \'" + attributeName + "\' not found");
        if (toRemove.getBehavior() instanceof ForwardedAttribute) {
            final ForwardedAttribute att = (ForwardedAttribute) toRemove.getBehavior();
            final String lower = att.getRootName().toLowerCase(Locale.ENGLISH);
            forwardedAttributes.remove(lower);
        }
        deviceImpl.removeAttribute(toRemove);
        dynamicAttributes.remove(attributeName);
        deviceImpl.pushInterfaceChangeEvent(false);
    }

    /**
     * remove a dynamic attribute
     *
     * @param attributeName attribute name
     * @throws DevFailed
     */
    public void removeAttribute(final String attributeName, final boolean clearAttributeProperties) throws DevFailed {
        removeAttribute(attributeName);
        if (clearAttributeProperties) {
            new AttributePropertiesManager(deviceImpl.getName()).removeAttributeProperties(attributeName);
        }
    }

    /**
     * Remove all dynamic attributes with exceptions
     *
     * @param exclude The attribute that will not be removed
     * @throws DevFailed
     */
    public void clearAttributesWithExclude(final String... exclude) throws DevFailed {
        final String[] toExclude = new String[exclude.length];
        for (int i = 0; i < toExclude.length; i++) {
            toExclude[i] = exclude[i].toLowerCase(Locale.ENGLISH);
        }
        for (final String attributeName : dynamicAttributes.keySet()) {
            if (!ArrayUtils.contains(toExclude, attributeName)) {
                removeAttribute(attributeName);
            }
        }
    }

    /**
     * Remove all dynamic attributes
     *
     * @throws DevFailed
     */
    public void clearAttributes() throws DevFailed {
        for (final AttributeImpl attributeImpl : dynamicAttributes.values()) {
            deviceImpl.removeAttribute(attributeImpl);
        }
        forwardedAttributes.clear();
        dynamicAttributes.clear();
    }

    /**
     * Remove all dynamic attributes
     *
     * @param clearAttributeProperties true to remove all attributes properties from tango db
     * @throws DevFailed
     */
    public void clearAttributes(final boolean clearAttributeProperties) throws DevFailed {
        clearAttributes();
        if (clearAttributeProperties) {
            new AttributePropertiesManager(deviceImpl.getName()).removeAttributeProperties();
        }
    }

    /**
     * Retrieve all dynamic attributes
     *
     * @return Dynamic attributes
     */
    public List<IAttributeBehavior> getDynamicAttributes() {
        final List<IAttributeBehavior> result = new ArrayList<IAttributeBehavior>();
        for (final AttributeImpl attributeImpl : dynamicAttributes.values()) {
            result.add(attributeImpl.getBehavior());
        }
        return result;
    }

    /**
     * Get a dynamic attribute
     *
     * @param attributeName the attribute name
     * @return The dynamic attribute
     */
    public IAttributeBehavior getAttribute(final String attributeName) {
        AttributeImpl attr = dynamicAttributes.get(attributeName.toLowerCase(Locale.ENGLISH));
        if (attr == null) return null;
        else return attr.getBehavior();
    }

    /**
     * Add command. Only if not already exists on device
     *
     * @param behavior
     * @throws DevFailed
     */
    public void addCommand(final ICommandBehavior behavior) throws DevFailed {
        final String cmdName = behavior.getConfiguration().getName();
        xlogger.entry("adding dynamic command {}", cmdName);
        final CommandImpl commandImpl = new CommandImpl(behavior, deviceImpl.getName());
        commandImpl.setStateMachine(behavior.getStateMachine());
        deviceImpl.addCommand(commandImpl);
        dynamicCommands.put(cmdName.toLowerCase(Locale.ENGLISH), commandImpl);
        deviceImpl.pushInterfaceChangeEvent(false);
        xlogger.exit();
    }

    /**
     * Remove a command
     *
     * @param commandName command name
     * @throws DevFailed
     */
    public void removeCommand(final String commandName) throws DevFailed {
        final CommandImpl toRemove = dynamicCommands.get(commandName.toLowerCase(Locale.ENGLISH));
        deviceImpl.removeCommand(toRemove);
        dynamicCommands.remove(commandName);
        deviceImpl.pushInterfaceChangeEvent(false);
    }

    /**
     * Get a dynamic command
     *
     * @param commandName command name
     * @return The dynamic command
     */
    public ICommandBehavior getCommand(final String commandName) {
        return dynamicCommands.get(commandName.toLowerCase(Locale.ENGLISH)).getBehavior();
    }

    /**
     * Remove all dynamic commands
     *
     * @throws DevFailed
     */
    public void clearCommands() throws DevFailed {
        for (final CommandImpl cmdImpl : dynamicCommands.values()) {
            deviceImpl.removeCommand(cmdImpl);
        }
        dynamicCommands.clear();
    }

    /**
     * Remove all dynamic command with exceptions
     *
     * @param exclude The commands that will not be removed
     * @throws DevFailed
     */
    public void clearCommandsWithExclude(final String... exclude) throws DevFailed {
        final String[] toExclude = new String[exclude.length];
        for (int i = 0; i < toExclude.length; i++) {
            toExclude[i] = exclude[i].toLowerCase(Locale.ENGLISH);
        }
        for (final String cmdName : dynamicCommands.keySet()) {
            if (!ArrayUtils.contains(toExclude, cmdName)) {
                removeCommand(cmdName);
            }
        }
    }

    /**
     * Retrieve all dynamic commands
     *
     * @return Dynamic commands
     */
    public List<ICommandBehavior> getDynamicCommands() {
        final List<ICommandBehavior> result = new ArrayList<ICommandBehavior>();
        for (final CommandImpl cmdImpl : dynamicCommands.values()) {
            result.add(cmdImpl.getBehavior());
        }
        return result;
    }

    /**
     * Remove all dynamic attributes and commands
     *
     * @throws DevFailed
     */
    public void clearAll() throws DevFailed {
        clearCommands();
        clearAttributes();
    }

    /**
     * Remove all dynamic attributes and commands
     *
     * @param clearAttributeProperties to remove attributes properties from tango db
     * @throws DevFailed
     */
    public void clearAll(final boolean clearAttributeProperties) throws DevFailed {
        clearCommands();
        clearAttributes(clearAttributeProperties);
    }
}
