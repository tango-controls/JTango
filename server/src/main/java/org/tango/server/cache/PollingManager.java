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
package org.tango.server.cache;

import fr.esrf.Tango.DevFailed;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.command.CommandTangoType;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.server.IPollable;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.command.CommandImpl;
import org.tango.server.servant.AttributeGetterSetter;
import org.tango.server.servant.CommandGetter;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Manage all polling stuff of a device
 *
 * @author ABEILLE
 *
 */
public final class PollingManager {

    private final Logger logger = LoggerFactory.getLogger(PollingManager.class);

    /**
     * Manage tango polling
     */
    private final TangoCacheManager cacheManager;
    private final Map<String, Integer> minCommandPolling;
    private final int minPolling;
    private final Map<String, Integer> minAttributePolling;
    private final Map<String, Integer> cmdPollRingDepth;
    private final Map<String, Integer> attrPollRingDepth;
    private final String deviceName;
    private final List<AttributeImpl> attributeList;
    private final List<CommandImpl> commandList;
    private Map<String, Integer> pollAttributes = new HashMap<String, Integer>();
    private int pollRingDepth = Constants.DEFAULT_POLL_DEPTH;

    public PollingManager(final String deviceName, final TangoCacheManager cacheManager,
            final List<AttributeImpl> attributeList, final List<CommandImpl> commandList, final int minPolling,
            final Map<String, Integer> minCommandPolling, final Map<String, Integer> minAttributePolling,
            final Map<String, Integer> cmdPollRingDepth, final Map<String, Integer> attrPollRingDepth) {
        this.deviceName = deviceName;
        this.cacheManager = cacheManager;
        this.attributeList = attributeList;
        this.commandList = commandList;
        this.minPolling = minPolling;
        this.minCommandPolling = minCommandPolling;
        this.minAttributePolling = minAttributePolling;
        this.cmdPollRingDepth = cmdPollRingDepth;
        this.attrPollRingDepth = attrPollRingDepth;
    }

    public void initPolling() throws DevFailed {
        for (final AttributeImpl attribute : attributeList) {
            attribute.applyMemorizedValue();
            attribute.configureAttributePropFromDb();
            configurePolling(attribute);
        }
        for (final CommandImpl command : commandList) {
            command.updatePollingConfigFromDB();
            configurePolling(command);
        }
    }

    public void configurePolling(final CommandImpl command) throws DevFailed {
        if (command.isPolled()) {
            if (command.getName().equals(DeviceImpl.STATE_NAME) || command.getName().equals(DeviceImpl.STATUS_NAME)) {
                // attribute is also set as polled
                final AttributeImpl attribute = AttributeGetterSetter.getAttribute(command.getName(), attributeList);
                // attribute.updatePollingConfigFromDB();
                cacheManager.startStateStatusPolling(command, attribute);
            } else {
                cacheManager.startCommandPolling(command);
            }
        }
        if (cmdPollRingDepth.containsKey(command.getName().toLowerCase(Locale.ENGLISH))) {
            command.setPollRingDepth(cmdPollRingDepth.get(command.getName().toLowerCase(Locale.ENGLISH)));
        } else {
            command.setPollRingDepth(pollRingDepth);
        }
    }

    public void configurePolling(final AttributeImpl attribute) throws DevFailed {
        if (pollAttributes.containsKey(attribute.getName().toLowerCase(Locale.ENGLISH))) {
            // configuration comes from tango db
            attribute.configurePolling(pollAttributes.get(attribute.getName().toLowerCase(Locale.ENGLISH)));
        }
        if (attribute.isPolled()) {
            logger.debug("configure polling of {}", attribute.getName());
            // start polling
            if (attribute.getName().equals(DeviceImpl.STATE_NAME) || attribute.getName().equals(DeviceImpl.STATUS_NAME)) {
                // command is also set as polled
                final CommandImpl cmd = CommandGetter.getCommand(attribute.getName(), commandList);
                cmd.updatePollingConfigFromDB();
                cacheManager.startStateStatusPolling(cmd, attribute);
            } else {
                cacheManager.startAttributePolling(attribute);
            }
        }
        if (attrPollRingDepth.containsKey(attribute.getName().toLowerCase(Locale.ENGLISH))) {
            attribute.setPollRingDepth(attrPollRingDepth.get(attribute.getName().toLowerCase(Locale.ENGLISH)));
        } else {
            attribute.setPollRingDepth(pollRingDepth);
        }
    }

    private void savePollingConfig() throws DevFailed {
        // save polling config
        final String[] pollingConfig = new String[pollAttributes.size() * 2];
        int i = 0;
        for (final Entry<String, Integer> entry : pollAttributes.entrySet()) {
            pollingConfig[i++] = entry.getKey();
            pollingConfig[i++] = Integer.toString(entry.getValue());
        }
        if (pollingConfig.length == 0) {
            DatabaseFactory.getDatabase().deleteDeviceProperty(deviceName, Constants.POLLED_ATTR);
        } else {
            final Map<String, String[]> props = new HashMap<String, String[]>();
            props.put(Constants.POLLED_ATTR, pollingConfig);
            DatabaseFactory.getDatabase().setDeviceProperties(deviceName, props);
        }
    }

    /**
     * Update polling cache
     *
     * @param objectName
     *            The command or attribute to update
     * @throws DevFailed
     */
    public void triggerPolling(final String objectName) throws DevFailed {
        boolean isACommand = false;
        CommandImpl cmd = null;
        try {
            cmd = CommandGetter.getCommand(objectName, commandList);
            isACommand = true;
        } catch (final DevFailed e) {
        }
        if (!isACommand) {
            // polled object is not a command. May be an attribute
            AttributeImpl att = null;
            try {
                att = AttributeGetterSetter.getAttribute(objectName, attributeList);
            } catch (final DevFailed e) {
                logger.error(Constants.POLLED_OBJECT + objectName + " not found");
                throw DevFailedUtils.newDevFailed(ExceptionMessages.POLL_OBJ_NOT_FOUND, Constants.POLLED_OBJECT
                        + objectName + " not found");
            }
            checkPolling(objectName, att);
            try {
                cacheManager.getAttributeCache(att).refresh(att.getName());
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    throw DevFailedUtils.newDevFailed(e.getCause());
                }
            } catch (final NoCacheFoundException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        } else {
            checkPolling(objectName, cmd);
            try {
                cacheManager.getCommandCache(cmd).refresh(cmd.getName());
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    throw DevFailedUtils.newDevFailed(e.getCause());
                }
            }
        }
    }

    private void checkPolling(final String objectName, final IPollable pollable) throws DevFailed {
        if (pollable.isPolled() && pollable.getPollingPeriod() > 0) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.NOT_SUPPORTED, Constants.POLLED_OBJECT + objectName
                    + " cannot be trigger externally");
        } else if (!pollable.isPolled()) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.POLL_OBJ_NOT_FOUND, Constants.POLLED_OBJECT + objectName
                    + " not polled");
        }
    }

    /**
     * Add command polling. Init command cannot be polled. Only command with
     * parameter void can be polled
     *
     * @param commandName
     *            the command to poll
     * @param pollingPeriod
     *            the polling period
     * @throws DevFailed
     */
    public void addCommandPolling(final String commandName, final int pollingPeriod) throws DevFailed {
        checkPollingLimits(commandName, pollingPeriod, minCommandPolling);
        final CommandImpl command = CommandGetter.getCommand(commandName, commandList);
        if (!command.getName().equals(DeviceImpl.INIT_CMD) && command.getInType().equals(CommandTangoType.VOID)) {
            command.configurePolling(pollingPeriod);
            if (command.getName().equals(DeviceImpl.STATE_NAME) || command.getName().equals(DeviceImpl.STATUS_NAME)) {
                // command is also set as polled
                final AttributeImpl attribute = AttributeGetterSetter.getAttribute(command.getName(), attributeList);
                attribute.configurePolling(pollingPeriod);
                pollAttributes.put(attribute.getName().toLowerCase(Locale.ENGLISH), pollingPeriod);
                cacheManager.startStateStatusPolling(command, attribute);
                pollAttributes.put(attribute.getName().toLowerCase(Locale.ENGLISH), pollingPeriod);
                savePollingConfig();
            } else {
                cacheManager.startCommandPolling(command);
            }
        }

    }

    private void checkPollingLimits(final String commandName, final int pollingPeriod,
            final Map<String, Integer> minPollingValues) throws DevFailed {
        if (pollingPeriod != 0) {
            if (pollingPeriod < minPolling) {
                throw DevFailedUtils.newDevFailed(Constants.MIN_POLLING_PERIOD_IS + minPolling);
            }
            if (minPollingValues.containsKey(commandName.toLowerCase(Locale.ENGLISH))
                    && pollingPeriod < minPollingValues.get(commandName.toLowerCase(Locale.ENGLISH))) {
                throw DevFailedUtils.newDevFailed(Constants.MIN_POLLING_PERIOD_IS + minPolling);
            }
        }
    }

    /**
     * Stop all polling
     */
    public void stopPolling() {
        cacheManager.stop();
    }

    /**
     * Start already configured polling
     */
    public void startPolling() {
        cacheManager.start();
    }

    /**
     * Add attribute polling
     *
     * @param attributeName
     *            the attribute to poll
     * @param pollingPeriod
     *            the polling period
     * @throws DevFailed
     */
    public void addAttributePolling(final String attributeName, final int pollingPeriod) throws DevFailed {

        logger.debug("add {} polling with period {}", attributeName, pollingPeriod);
        checkPollingLimits(attributeName, pollingPeriod, minAttributePolling);
        final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
        if (attribute.getBehavior() instanceof ForwardedAttribute) {
            throw DevFailedUtils.newDevFailed(attributeName + " not pollable because it is a forwarded attribute");
        }
        attribute.configurePolling(pollingPeriod);
        if (attribute.getName().equals(DeviceImpl.STATE_NAME) || attribute.getName().equals(DeviceImpl.STATUS_NAME)) {
            // command is also set as polled
            final CommandImpl cmd = CommandGetter.getCommand(attribute.getName(), commandList);
            cmd.configurePolling(pollingPeriod);
            cacheManager.startStateStatusPolling(cmd, attribute);
        } else {
            cacheManager.startAttributePolling(attribute);
        }
        pollAttributes.put(attributeName.toLowerCase(Locale.ENGLISH), pollingPeriod);
        savePollingConfig();
    }

    /**
     * Remove attribute polling
     *
     * @param attributeName
     *            the attribute
     * @throws DevFailed
     */
    public void removeAttributePolling(final String attributeName) throws DevFailed {
        // jive sends value with lower case, so manage it
        final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
        attribute.resetPolling();
        cacheManager.removeAttributePolling(attribute);
        pollAttributes.remove(attributeName.toLowerCase(Locale.ENGLISH));
        if (attribute.getName().equals(DeviceImpl.STATE_NAME) || attribute.getName().equals(DeviceImpl.STATUS_NAME)) {
            // command is also set as polled
            final CommandImpl cmd = CommandGetter.getCommand(attribute.getName(), commandList);
            cmd.resetPolling();
            cacheManager.removeCommandPolling(cmd);
        }
        savePollingConfig();
    }

    /**
     * Remove command polling
     *
     * @param commandName
     *            the command
     * @throws DevFailed
     */
    public void removeCommandPolling(final String commandName) throws DevFailed {
        final CommandImpl command = CommandGetter.getCommand(commandName, commandList);
        command.resetPolling();
        cacheManager.removeCommandPolling(command);
        if (command.getName().equals(DeviceImpl.STATE_NAME) || command.getName().equals(DeviceImpl.STATUS_NAME)) {
            // attribute is also set as polled
            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(command.getName(), attributeList);
            attribute.resetPolling();
            cacheManager.removeAttributePolling(attribute);
            pollAttributes.remove(command.getName().toLowerCase(Locale.ENGLISH));
            savePollingConfig();
        }
    }

    public void removeAll() {
        cacheManager.removeAll();
    }

    public Object getCommandCacheElement(final CommandImpl cmd) throws DevFailed {
        Object ret;
        try {
            final Element element = cacheManager.getCommandCache(cmd).get(cmd.getName().toLowerCase(Locale.ENGLISH));
            final Serializable cmdValue = element.getValue();
            if (cmdValue instanceof org.tango.server.attribute.AttributeValue) {
                // state or status are returned as attribute value
                ret = ((org.tango.server.attribute.AttributeValue) cmdValue).getValue();
            } else {
                ret = element.getValue();
            }
        } catch (final CacheException e) {
            if (e.getCause() instanceof DevFailed) {
                throw (DevFailed) e.getCause();
            } else {
                throw DevFailedUtils.newDevFailed(e.getCause());
            }
        }
        return ret;
    }

    /**
     * @param att the attribute
     * @return element or null
     * @throws IllegalStateException TODO description
     * @throws CacheException        TODO description
     */
    public AttributeValue getAttributeCacheElement(final AttributeImpl att) throws CacheException {
        final Element element;
        try {
            element = cacheManager.getAttributeCache(att).get(att.getName().toLowerCase(Locale.ENGLISH));
            return (AttributeValue) element.getValue();
        } catch (NoCacheFoundException e) {
            return null;
        }
    }

    public void setPollRingDepth(final int pollRingDepth) {
        this.pollRingDepth = pollRingDepth;
    }

    public void setPollAttributes(final Map<String, Integer> pollAttributes) {
        this.pollAttributes = pollAttributes;
    }
}
