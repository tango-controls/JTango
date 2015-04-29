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
package org.tango.server.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.ExceptionMessages;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.command.CommandImpl;
import org.tango.server.command.ICommandBehavior;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Manage dynamic commands and attributes. The creation of commands and attributes should done in {@link Init}. Don't
 * forget to delete them in {@link Delete} This class is injected by {@link DynamicManagement}
 * 
 * @author ABEILLE
 * 
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
     * List of dynamic attributes
     */
    private final List<AttributeImpl> dynamicAttributes = new ArrayList<AttributeImpl>();
    /**
     * List of dynamic commands
     */
    private final List<CommandImpl> dynamicCommands = new ArrayList<CommandImpl>();

    private final List<String> forwardedAttributes = new ArrayList<String>();

    /**
     * Ctr
     * 
     * @param deviceImpl
     *            the associated device
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
        xlogger.entry("adding dynamic attribute {}", behavior.getConfiguration().getName());
        if (behavior instanceof ForwardedAttribute) {
            // init attribute with either the attribute property value, or the value defined in its constructor
            final ForwardedAttribute att = (ForwardedAttribute) behavior;
            final String attributeName = behavior.getConfiguration().getName();
            final String deviceName = deviceImpl.getName();
            final String rootAttributeName = behavior.getConfiguration().getAttributeProperties()
                    .loadAttributeRootName(deviceName, attributeName);
            if (rootAttributeName == null || rootAttributeName.isEmpty()
                    || rootAttributeName.equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)) {
                att.init();
                // persist root attribute name in tango db
                behavior.getConfiguration().getAttributeProperties()
                        .persistAttributeRootName(deviceName, attributeName);
            } else {
                // use attribute property
                att.init(rootAttributeName);
            }
            // check if this attribute is already created
            final String lower = att.getRootName().toLowerCase(Locale.ENGLISH);
            if (forwardedAttributes.contains(lower)) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.FWD_DOUBLE_USED,
                        "root attribute already used in this device");
            } else {
                forwardedAttributes.add(lower);
            }
        }
        final AttributeImpl attrImpl = new AttributeImpl(behavior, deviceImpl.getName());
        attrImpl.setStateMachine(behavior.getStateMachine());
        deviceImpl.addAttribute(attrImpl);
        dynamicAttributes.add(attrImpl);
        deviceImpl.pushInterfaceChangeEvent(false);
        xlogger.exit();
    }

    /**
     * remove a dynamic attribute
     * 
     * @param attributeName
     *            attribute name
     * @throws DevFailed
     */
    public void removeAttribute(final String attributeName) throws DevFailed {
        for (final AttributeImpl attributeImpl : dynamicAttributes) {
            if (attributeImpl.getBehavior() instanceof ForwardedAttribute) {
                // check if this attribute is already created
                final ForwardedAttribute att = (ForwardedAttribute) attributeImpl.getBehavior();
                final String lower = att.getRootName().toLowerCase(Locale.ENGLISH);
                forwardedAttributes.remove(lower);
            }

            if (attributeImpl.getName().equalsIgnoreCase(attributeName)) {
                deviceImpl.removeAttribute(attributeImpl);
                dynamicAttributes.remove(attributeImpl);
                deviceImpl.pushInterfaceChangeEvent(false);
                break;
            }
        }
    }

    /**
     * remove a dynamic attribute
     * 
     * @param attributeName
     *            attribute name
     * @throws DevFailed
     */
    public void removeAttribute(final String attributeName, final boolean clearAttributeProperties) throws DevFailed {
        removeAttribute(attributeName);
        if (clearAttributeProperties) {
            new AttributePropertiesManager(deviceImpl.getName()).removeAttributeProperties(attributeName);
        }
    }

    /**
     * Get a dynamic attribute
     * 
     * @param attributeName
     *            the attribute name
     * @return The dynamic attribute
     */
    public IAttributeBehavior getAttribute(final String attributeName) {
        IAttributeBehavior attribute = null;
        for (final AttributeImpl attributeImpl : dynamicAttributes) {
            if (attributeImpl.getName().equalsIgnoreCase(attributeName)) {
                attribute = attributeImpl.getBehavior();
                break;
            }
        }
        return attribute;
    }

    /**
     * Add command. Only if not already exists on device
     * 
     * @param behavior
     * @throws DevFailed
     */
    public void addCommand(final ICommandBehavior behavior) throws DevFailed {
        xlogger.entry("adding dynamic command {}", behavior.getConfiguration().getName());
        final CommandImpl commandImpl = new CommandImpl(behavior, deviceImpl.getName());
        commandImpl.setStateMachine(behavior.getStateMachine());
        deviceImpl.addCommand(commandImpl);
        dynamicCommands.add(commandImpl);
        deviceImpl.pushInterfaceChangeEvent(false);
        xlogger.exit();
    }

    /**
     * Remove a command
     * 
     * @param commandName
     *            command name
     * @throws DevFailed
     */
    public void removeCommand(final String commandName) throws DevFailed {
        for (final CommandImpl cmdImpl : dynamicCommands) {
            if (cmdImpl.getName().equalsIgnoreCase(commandName)) {
                deviceImpl.removeCommand(cmdImpl);
                dynamicCommands.remove(cmdImpl);
                deviceImpl.pushInterfaceChangeEvent(false);
                break;
            }
        }
    }

    /**
     * Get a dynamic command
     * 
     * @param commandName
     *            command name
     * @return The dynamic command
     */
    public ICommandBehavior getCommand(final String commandName) {
        ICommandBehavior command = null;
        for (final CommandImpl cmdImpl : dynamicCommands) {
            if (cmdImpl.getName().equalsIgnoreCase(commandName)) {
                command = cmdImpl.getBehavior();
                break;
            }
        }
        return command;
    }

    /**
     * Remove all dynamic attributes and commands
     * 
     * @throws DevFailed
     * 
     */
    public void clearAll() throws DevFailed {
        clearCommands();
        clearAttributes();
    }

    /**
     * Remove all dynamic attributes and commands
     * 
     * @param clearAttributeProperties
     *            to remove attributes properties from tango db
     * @throws DevFailed
     */
    public void clearAll(final boolean clearAttributeProperties) throws DevFailed {
        clearCommands();
        clearAttributes(clearAttributeProperties);
    }

    /**
     * Remove all dynamic commands
     * 
     * @throws DevFailed
     */
    public void clearCommands() throws DevFailed {
        for (final CommandImpl cmdImpl : dynamicCommands) {
            deviceImpl.removeCommand(cmdImpl);
        }
        dynamicCommands.clear();
    }

    /**
     * Remove all dynamic attributes
     * 
     * @throws DevFailed
     */
    public void clearAttributes() throws DevFailed {

        for (final AttributeImpl attributeImpl : dynamicAttributes) {
            deviceImpl.removeAttribute(attributeImpl);
        }
        forwardedAttributes.clear();
        dynamicAttributes.clear();
    }

    /**
     * Remove all dynamic attributes
     * 
     * @param clearAttributeProperties
     *            true to remove all attributes properties from tango db
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
        for (final AttributeImpl attributeImpl : dynamicAttributes) {
            result.add(attributeImpl.getBehavior());
        }
        return result;
    }

    /**
     * Retrieve all dynamic commands
     * 
     * @return Dynamic commands
     */
    public List<ICommandBehavior> getDynamicCommands() {
        final List<ICommandBehavior> result = new ArrayList<ICommandBehavior>();
        for (final CommandImpl cmdImpl : dynamicCommands) {
            result.add(cmdImpl.getBehavior());
        }
        return result;
    }
}
