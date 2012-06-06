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

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.command.CommandImpl;
import org.tango.server.command.ICommandBehavior;
import org.tango.server.servant.DeviceImpl;

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
	final AttributeImpl attrImpl = new AttributeImpl(behavior, deviceImpl.getAttributeProperties());
	attrImpl.setStateMachine(behavior.getStateMachine());
	deviceImpl.addAttribute(attrImpl);
	dynamicAttributes.add(attrImpl);
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
	    if (attributeImpl.getName().equalsIgnoreCase(attributeName)) {
		deviceImpl.removeAttribute(attributeImpl);
		dynamicAttributes.remove(attributeImpl);
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
	    deviceImpl.getAttributeProperties().removeAttributeProperties(attributeName);
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
	final CommandImpl commandImpl = new CommandImpl(behavior, deviceImpl.getAttributeProperties());
	commandImpl.setStateMachine(behavior.getStateMachine());
	deviceImpl.addCommand(commandImpl);
	dynamicCommands.add(commandImpl);
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
	    deviceImpl.getAttributeProperties().removeAttributeProperties();
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
