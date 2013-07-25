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
package org.tango.server.servant;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.omg.CORBA.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.DeviceState;
import org.tango.client.database.DatabaseFactory;
import org.tango.command.CommandTangoType;
import org.tango.server.ExceptionMessages;
import org.tango.server.IPollable;
import org.tango.server.InvocationContext;
import org.tango.server.InvocationContext.ContextType;
import org.tango.server.ServerManager;
import org.tango.server.annotation.AroundInvoke;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.ClassProperty;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceProperties;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.State;
import org.tango.server.annotation.Status;
import org.tango.server.annotation.TransactionType;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.cache.TangoCacheManager;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.device.DeviceLock;
import org.tango.server.device.InitImpl;
import org.tango.server.device.StateImpl;
import org.tango.server.device.StatusImpl;
import org.tango.server.history.DeviceBlackBox;
import org.tango.server.idl.CleverAnyCommand;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.server.lock.ClientLocking;
import org.tango.server.properties.AttributePropertiesManager;
import org.tango.server.properties.ClassPropertyImpl;
import org.tango.server.properties.DevicePropertiesImpl;
import org.tango.server.properties.DevicePropertyImpl;
import org.tango.server.properties.PropertiesUtils;
import org.tango.server.schedule.DeviceScheduler;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttributeConfig;
import fr.esrf.Tango.AttributeConfig_2;
import fr.esrf.Tango.AttributeConfig_3;
import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.ClntIdent;
import fr.esrf.Tango.DevAttrHistory;
import fr.esrf.Tango.DevAttrHistory_3;
import fr.esrf.Tango.DevAttrHistory_4;
import fr.esrf.Tango.DevCmdHistory;
import fr.esrf.Tango.DevCmdHistory_4;
import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevCmdInfo_2;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevInfo;
import fr.esrf.Tango.DevInfo_3;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.Device_4POA;
import fr.esrf.Tango.MultiDevFailed;

/**
 * The CORBA servant for a tango device server in IDL 4.
 * 
 * @author ABEILLE
 */
public final class DeviceImpl extends Device_4POA {

    private static final String INIT_CMD = "Init";
    private static final String MIN_POLLING_PERIOD_IS = "min polling period is ";
    private static final String POLLED_OBJECT = "Polled object ";
    private static final String POLLED_ATTR = "polled_attr";
    private final Logger logger = LoggerFactory.getLogger(DeviceImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DeviceImpl.class);

    private static final String NOT_IMPORTANT_ERROR = "not important error";
    private static final String READ_ASKED_FOR_0_ATTRIBUTES = "read asked for 0 attributes";
    private static final String READ_ERROR = "READ_ERROR";
    /**
     * TANGO system version
     */
    public static final int SERVER_VERSION = 4;
    /**
     * for logging
     */
    public static final String MDC_KEY = "deviceName";

    /**
     * Special attribute name meaning all attributes
     */
    public static final String ALL_ATTR = "All attributes";

    public static final String STATE_NAME = "State";
    public static final String STATUS_NAME = "Status";
    /**
     * The name of the tango device
     */
    private final String name;

    /**
     * to init the device
     */
    private InitImpl init;
    /**
     * The implementation of the device
     */
    private final Object businessObject;
    /**
     * The delete method. Called at each init
     */
    private Method deleteMethod;
    /**
     * Implementaion of Tango state
     */
    private StateImpl stateImpl;
    /**
     * Implementation of Tango status
     */
    private StatusImpl statusImpl;
    /**
     * Implementation of around invoke
     */
    private AroundInvokeImpl aroundInvokeImpl;

    // default attributes
    /**
     * The state attribute
     */
    @Attribute(name = STATE_NAME)
    private DevState state = DevState.UNKNOWN;

    /**
     * The status attribute
     */
    @Attribute(name = STATUS_NAME)
    private String status = "default status";

    /**
     * CORBA id
     */
    private byte[] objId;

    /**
     * Manage device locking
     */
    private final DeviceLock deviceLock;

    /**
     * the device's attributes
     */
    private final List<AttributeImpl> attributeList = new ArrayList<AttributeImpl>();
    /**
     * the device's commands
     */
    private final List<CommandImpl> commandList = new ArrayList<CommandImpl>();
    /**
     * the device's properties
     */
    private final List<DevicePropertyImpl> devicePropertyList = new ArrayList<DevicePropertyImpl>();
    /**
     * the device class's properties
     */
    private final List<ClassPropertyImpl> classPropertyList = new ArrayList<ClassPropertyImpl>();
    /**
     * all the device's properties. To allow dynamic properties
     */
    private DevicePropertiesImpl deviceProperties;
    /**
     * Manage attribute properties
     */
    private final AttributePropertiesManager attributeProperties;
    /**
     * Black box. History of all client requests
     */
    private final DeviceBlackBox blackBox = new DeviceBlackBox();
    /**
     * The class name as defined in tango db
     */
    private final String className;
    /**
     * Manage tango polling
     */
    private final TangoCacheManager cacheManager;

    /**
     * Manage lazy init
     */
    private volatile boolean isInitializing;
    /**
     * Manage lazy init
     */
    private volatile boolean isCorrectlyInit;
    private boolean stateCheckAttrAlarm = false;
    /**
     * client info of lastest command. Used for locking device from client
     */
    private ClntIdent clientIdentity;

    /**
     * Recreating a device does not delete locking object. So maintain a
     * reference
     */
    private static final Map<String, ClientLocking> CLIENT_LOCKING_MAP = new HashMap<String, ClientLocking>();
    /**
     * Manage locking from client
     */
    private final ClientLocking clientLocking;

    private final Map<String, Integer> pollAttributes = new HashMap<String, Integer>();
    private final Map<String, Integer> minCommandPolling = new HashMap<String, Integer>();
    private int minPolling = 0;
    private final Map<String, Integer> minAttributePolling = new HashMap<String, Integer>();
    private final Map<String, Integer> cmdPollRingDepth = new HashMap<String, Integer>();
    private final Map<String, Integer> attrPollRingDepth = new HashMap<String, Integer>();
    private final String deviceType;

    private DeviceScheduler deviceScheduler;

    /**
     * Ctr
     * 
     * @param deviceName
     *            The device name
     * @param className
     *            The class name as defined in tangodb
     * @param txType
     *            {@link TransactionType}
     * @param businessObject
     *            The real implementation of the device {@link Device}
     * @throws DevFailed
     */
    public DeviceImpl(final String deviceName, final String className, final TransactionType txType,
            final Object businessObject, final String deviceType) throws DevFailed {
        name = deviceName;
        this.className = className;
        this.deviceType = deviceType;
        deviceLock = new DeviceLock(txType, businessObject.getClass());
        cacheManager = new TangoCacheManager(name, deviceLock);
        this.businessObject = businessObject;
        attributeProperties = new AttributePropertiesManager(deviceName);
        stateCheckAttrAlarm = false;
        if (CLIENT_LOCKING_MAP.containsKey(deviceName)) {
            clientLocking = CLIENT_LOCKING_MAP.get(deviceName);
            clientLocking.init();
        } else {
            clientLocking = new ClientLocking(deviceName, className);
            CLIENT_LOCKING_MAP.put(deviceName, clientLocking);
        }
        // add default device properties
        try {
            final DevicePropertyImpl property = new DevicePropertyImpl(
                    "StateCheckAttrAlarm",
                    "boolean. If true, all attributes will be read at each state/status request to check if alarm is present",
                    this.getClass().getMethod("setStateCheckAttrAlarm", boolean.class), this, name, className, false,
                    "false");
            addDeviceProperty(property);
            final DevicePropertyImpl property2 = new DevicePropertyImpl("cmd_min_poll_period",
                    "min poll value for commands", this.getClass().getMethod("setMinCommandPolling", String[].class),
                    this, name, className, false);
            addDeviceProperty(property2);
            final DevicePropertyImpl property3 = new DevicePropertyImpl("min_poll_period", "min poll value", this
                    .getClass().getMethod("setMinPolling", int.class), this, name, className, false, "0");
            addDeviceProperty(property3);
            final DevicePropertyImpl property4 = new DevicePropertyImpl("attr_min_poll_period",
                    "min poll value for attributes", this.getClass()
                            .getMethod("setMinAttributePolling", String[].class), this, name, className, false);
            addDeviceProperty(property4);
            final DevicePropertyImpl property5 = new DevicePropertyImpl("cmd_poll_ring_depth",
                    "command poll ring depth", this.getClass().getMethod("setCmdPollRingDepth", String[].class), this,
                    name, className, false);
            addDeviceProperty(property5);
            final DevicePropertyImpl property6 = new DevicePropertyImpl("attr_poll_ring_depth",
                    "attribute poll ring depth", this.getClass().getMethod("setAttrPollRingDepth", String[].class),
                    this, name, className, false);
            addDeviceProperty(property6);

            final DevicePropertyImpl property7 = new DevicePropertyImpl(POLLED_ATTR, "poll attributes", this.getClass()
                    .getMethod("setPolledAttributes", String[].class), this, name, className, false);
            addDeviceProperty(property7);

        } catch (final SecurityException e) {
            DevFailedUtils.throwDevFailed(e);
        } catch (final NoSuchMethodException e) {
            DevFailedUtils.throwDevFailed(e);
        }

        MDC.put(MDC_KEY, name);
        logger.info("Device {} of of {} created with tx type: {}", new Object[] { deviceName,
                businessObject.getClass(), txType });

    }

    public void setStateCheckAttrAlarm(final boolean stateCheckAttrAlarm) {
        logger.debug("update all attributes when reading state of status {}", stateCheckAttrAlarm);
        this.stateCheckAttrAlarm = stateCheckAttrAlarm;
    }

    public void setPolledAttributes(final String[] pollAttributes) {
        for (int i = 0; i < pollAttributes.length; i = i + 2) {
            if (i + 1 < pollAttributes.length) {
                this.pollAttributes.put(pollAttributes[i].toLowerCase(Locale.ENGLISH),
                        Integer.parseInt(pollAttributes[i + 1]));
            }
        }
    }

    public void setMinCommandPolling(final String[] minCommandPolling) {
        for (int i = 0; i < minCommandPolling.length; i = i + 2) {
            if (i + 1 < minCommandPolling.length) {
                this.minCommandPolling.put(minCommandPolling[i].toLowerCase(Locale.ENGLISH),
                        Integer.parseInt(minCommandPolling[i + 1]));
            }
        }
    }

    public void setMinAttributePolling(final String[] minAttributePolling) {
        for (int i = 0; i < minAttributePolling.length; i = i + 2) {
            if (i + 1 < minAttributePolling.length) {
                this.minAttributePolling.put(minAttributePolling[i].toLowerCase(Locale.ENGLISH),
                        Integer.parseInt(minAttributePolling[i + 1]));
            }
        }
    }

    public void setMinPolling(final int minPolling) {
        this.minPolling = minPolling;
    }

    public void setCmdPollRingDepth(final String[] cmdPollRingDepth) {
        for (int i = 0; i < cmdPollRingDepth.length; i = i + 2) {
            if (i + 1 < cmdPollRingDepth.length) {
                this.cmdPollRingDepth.put(cmdPollRingDepth[i].toLowerCase(Locale.ENGLISH),
                        Integer.parseInt(cmdPollRingDepth[i + 1]));
            }
        }
    }

    public void setAttrPollRingDepth(final String[] attrPollRingDepth) {
        for (int i = 0; i < attrPollRingDepth.length; i = i + 2) {
            if (i + 1 < attrPollRingDepth.length) {
                this.attrPollRingDepth.put(attrPollRingDepth[i].toLowerCase(Locale.ENGLISH),
                        Integer.parseInt(attrPollRingDepth[i + 1]));
            }
        }
    }

    /**
     * Command State
     * 
     * @return the state
     * @throws DevFailed
     */
    @Command(name = STATE_NAME, outTypeDesc = "Device state")
    public DevState executeStateCmd() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        xlogger.exit();
        return getState();
    }

    /**
     * Command status
     * 
     * @return status
     * @throws DevFailed
     */

    @Command(name = STATUS_NAME, outTypeDesc = "Device status")
    public String executeStatusCmd() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        xlogger.exit();
        return getStatus();
    }

    /**
     * Init the device. Calls {@link Delete} before {@link Init}
     * 
     * @throws DevFailed
     */
    @Command(name = INIT_CMD)
    public synchronized void initCmd() throws DevFailed {
        xlogger.entry();
        // if init is already in progress, do nothing
        if (!isInitializing) {
            isInitializing = true;
            deleteDevice();
            doInit();
            isInitializing = false;
        }
        xlogger.exit();
    }

    /**
     * Add an attribute to the device
     * 
     * @param attribute
     * @throws DevFailed
     */
    public void addAttribute(final AttributeImpl attribute) throws DevFailed {
        // add attribute only if it doesn't exists
        AttributeImpl result = null;
        for (final AttributeImpl attr : attributeList) {
            if (attr.getName().equalsIgnoreCase(attribute.getName())) {
                result = attribute;
                break;
            }
        }
        if (result == null) {
            attributeList.add(attribute);
            if (attrPollRingDepth.containsKey(attribute.getName().toLowerCase(Locale.ENGLISH))) {
                attribute.setPollRingDepth(attrPollRingDepth.get(attribute.getName().toLowerCase(Locale.ENGLISH)));
            }
        }
    }

    /**
     * Get attributes (copy)
     * 
     * @return the attributes
     */
    public List<AttributeImpl> getAttributeList() {
        return new ArrayList<AttributeImpl>(attributeList);
    }

    /**
     * Get a command
     * 
     * @param name
     * @return The command
     * @throws DevFailed
     */
    public CommandImpl getCommand(final String name) throws DevFailed {
        CommandImpl result = null;
        for (final CommandImpl command : commandList) {
            if (command.getName().equalsIgnoreCase(name)) {
                result = command;
                break;
            }
        }
        if (result == null) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.COMMAND_NOT_FOUND, "Command " + name + " not found");
        }
        return result;
    }

    /**
     * remove an attribute of the device. Not possible to remove State or Status
     * 
     * @param attribute
     * @throws DevFailed
     */
    public void removeAttribute(final AttributeImpl attribute) throws DevFailed {
        if (attribute.getName().equalsIgnoreCase(STATUS_NAME) || attribute.getName().equalsIgnoreCase(STATE_NAME)) {
            return;
        }
        attributeList.remove(attribute);
        statusImpl.removeAttributeAlarm(attribute.getName());
        stateImpl.removeAttributeAlarm(attribute.getName());
        cacheManager.removeAttributePolling(attribute);
    }

    /**
     * add a device property to this device {@link DeviceProperty}
     * 
     * @param property
     */
    public void addDeviceProperty(final DevicePropertyImpl property) {
        devicePropertyList.add(property);
    }

    /**
     * add a class property to this device {@link ClassProperty}
     * 
     * @param property
     */
    public void addClassProperty(final ClassPropertyImpl property) {
        classPropertyList.add(property);
    }

    /**
     * Set a dynamic properties manager {@link DeviceProperties}
     * 
     * @param properties
     */
    public synchronized void setDeviceProperties(final DevicePropertiesImpl properties) {
        deviceProperties = properties;
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
        }
    }

    public void configurePolling(final AttributeImpl attribute) throws DevFailed {
        if (pollAttributes.containsKey(attribute.getName().toLowerCase(Locale.ENGLISH))) {
            // configuration comes from tango db
            attribute.configurePolling(pollAttributes.get(attribute.getName().toLowerCase(Locale.ENGLISH)));
        }
        if (attribute.isPolled()) {
            // start polling
            if (attribute.getName().equals(DeviceImpl.STATE_NAME) || attribute.getName().equals(DeviceImpl.STATUS_NAME)) {
                // command is also set as polled
                final CommandImpl cmd = getCommand(attribute.getName());
                cmd.updatePollingConfigFromDB();
                cacheManager.startStateStatusPolling(cmd, attribute);
            } else {
                cacheManager.startAttributePolling(attribute);
            }
        }
        if (attrPollRingDepth.containsKey(attribute.getName().toLowerCase(Locale.ENGLISH))) {
            attribute.setPollRingDepth(attrPollRingDepth.get(attribute.getName().toLowerCase(Locale.ENGLISH)));
        }
    }

    private synchronized void doInit() {
        isCorrectlyInit = false;
        try {
            if (deviceScheduler != null) {
                deviceScheduler.triggerJob();
            }
            // update properties from tango db
            if (deviceProperties != null) {
                deviceProperties.update();
            }
            for (final DevicePropertyImpl prop : devicePropertyList) {
                prop.update();
            }
            for (final ClassPropertyImpl prop : classPropertyList) {
                prop.update();
            }

            if (init != null) {
                init.execute(stateImpl, statusImpl);
            }

        } catch (final DevFailed e) {
            isCorrectlyInit = false;
            // ignore error so that server always starts
            try {
                stateImpl.stateMachine(DeviceState.FAULT);
                statusImpl.statusMachine(DevFailedUtils.toString(e), DeviceState.FAULT);
            } catch (final DevFailed e1) {
                // ignore
                logger.debug("not important", e1);
            }
        } finally {
            try {
                for (final AttributeImpl attribute : attributeList) {
                    attribute.applyMemorizedValue();
                    attribute.configureAttributePropFromDb();
                    // attribute.updatePollingConfigFromDB();
                    configurePolling(attribute);
                }

                for (final CommandImpl command : commandList) {
                    command.updatePollingConfigFromDB();
                    configurePolling(command);
                }
                isCorrectlyInit = true;
            } catch (final DevFailed e) {
                isCorrectlyInit = false;
                try {
                    stateImpl.stateMachine(DeviceState.FAULT);
                    statusImpl.statusMachine(DevFailedUtils.toString(e), DeviceState.FAULT);
                } catch (final DevFailed e1) {
                    // ignore
                    logger.debug("not important", e1);
                }
            }

        }

        logger.error("init OK " + isCorrectlyInit);

    }

    /**
     * Initializes the device: <br>
     * <ul>
     * <li>reloads device and class properties</li>
     * <li>applies memorized value to attributes</li>
     * <li>restarts polling</li>
     * <li>calls delete method {@link Delete}
     * <li>
     * <li>calls init method {@link Init}
     * <li>
     * </ul>
     */
    public synchronized void initDevice() {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        if (stateImpl == null) {
            stateImpl = new StateImpl(businessObject, null, null);
        }
        if (statusImpl == null) {
            statusImpl = new StatusImpl(businessObject, null, null);
        }
        if (aroundInvokeImpl == null) {
            aroundInvokeImpl = new AroundInvokeImpl(businessObject, null);
        }
        doInit();
        logger.debug("device init done");
        xlogger.exit();
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
            DatabaseFactory.getDatabase().deleteDeviceProperty(name, POLLED_ATTR);
        } else {
            final Map<String, String[]> props = new HashMap<String, String[]>();
            props.put(POLLED_ATTR, pollingConfig);
            DatabaseFactory.getDatabase().setDeviceProperties(name, props);
        }
    }

    /**
     * Stops polling calls delete method {@link Delete}. Called before init and
     * at server shutdown
     * 
     * @throws DevFailed
     */
    public void deleteDevice() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        PropertiesUtils.clearDeviceCache(name);
        PropertiesUtils.clearClassCache(className);
        stopPolling();
        cacheManager.removeAll();
        if (deviceScheduler != null) {
            deviceScheduler.stop();
        }
        if (deleteMethod != null) {
            try {
                deleteMethod.invoke(businessObject);
            } catch (final IllegalArgumentException e) {
                DevFailedUtils.throwDevFailed(e);
            } catch (final IllegalAccessException e) {
                DevFailedUtils.throwDevFailed(e);
            } catch (final InvocationTargetException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    DevFailedUtils.throwDevFailed(e.getCause());
                }
            }
        }
        xlogger.exit();
    }

    /**
     * @throws DevFailed
     */
    private void checkInitialization() throws DevFailed {
        if (isInitializing) {
            DevFailedUtils.throwDevFailed("CONCURRENT_ERROR", name + " in Init command ");
        }
    }

    /**
     * Get info of this device in IDL1
     * 
     * @return info
     * @throws DevFailed
     */
    @Override
    public DevInfo info() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        final DevInfo info = new DevInfo();
        info.dev_class = className;
        info.doc_url = "Doc URL = http://www.tango-controls.org";
        info.server_host = ServerManager.getInstance().getHostName();
        info.server_id = ServerManager.getInstance().getServerName();
        info.server_version = SERVER_VERSION;
        xlogger.exit();
        return info;
    }

    /**
     * Get info of this device in IDL3
     * 
     * @return info
     * @throws DevFailed
     */
    @Override
    public DevInfo_3 info_3() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        final DevInfo_3 info3 = new DevInfo_3();
        final DevInfo info = info();
        info3.dev_class = info.dev_class;
        info3.doc_url = info.doc_url;
        info3.server_host = info.server_host;
        info3.server_id = info.server_id;
        info3.server_version = info.server_version;
        info3.dev_type = deviceType;
        xlogger.exit();
        return info3;
    }

    /**
     * Dummy method to check if this device is responding
     * 
     * @throws DevFailed
     */
    @Override
    public void ping() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        blackBox.insertInblackBox("Operation ping");
        xlogger.exit();
    }

    /**
     * @return the admin device of to this device
     */
    @Override
    public String adm_name() {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        blackBox.insertInblackBox("Attribute adm_name");
        final String adminDeviceName = Constants.ADMIN_DEVICE_DOMAIN + "/"
                + ServerManager.getInstance().getServerName();
        xlogger.exit(adminDeviceName);
        return adminDeviceName;
    }

    /**
     * Get the clients' requests history
     * 
     * @param maxSize
     *            The maximum depth of history
     * @return the request history
     */
    @Override
    public String[] black_box(final int maxSize) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        // blackBox.insertInblackBox("black_box");
        if (maxSize <= 0) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.BLACK_BOX_ARG, maxSize + " is not a good size");
        }
        xlogger.exit();
        return blackBox.toArray(maxSize);
    }

    /**
     * Get a description of this device
     * 
     * @return description
     */
    @Override
    public String description() {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        blackBox.insertInblackBox("Attribute description requested ");
        String desc = "A TANGO device";
        if (name.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName())) {
            desc = "A device server device !!";
        }
        return desc;
    }

    /**
     * Get the name of the device
     * 
     * @return name
     */
    @Override
    public String name() {
        MDC.put(MDC_KEY, name);
        blackBox.insertInblackBox("Attribute name");
        xlogger.entry();
        return name;
    }

    /**
     * read an attribute history. IDL 2 version. The history is filled only be
     * attribute polling
     * 
     * @param attributeName
     *            The attribute to retrieve
     * @param maxSize
     *            The history maximum size returned
     * @throws DevFailed
     */
    @Override
    public DevAttrHistory[] read_attribute_history_2(final String attributeName, final int maxSize) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("read_attribute_history_2");
        // TODO read_attribute_history_2
        return new DevAttrHistory[0];
    }

    /**
     * read an attribute history. IDL 3 version. The history is filled only be
     * attribute polling
     * 
     * @param attributeName
     *            The attribute to retrieve
     * @param maxSize
     *            The history maximum size returned
     * @throws DevFailed
     */
    @Override
    public DevAttrHistory_3[] read_attribute_history_3(final String attributeName, final int maxSize) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        // TODO read_attribute_history_3
        checkInitialization();
        blackBox.insertInblackBox("read_attribute_history_3");
        return new DevAttrHistory_3[0];
    }

    /**
     * read an attribute history. IDL 4 version. The history is filled only be
     * attribute polling
     * 
     * @param attributeName
     *            The attribute to retrieve
     * @param maxSize
     *            The history maximum size returned
     * @throws DevFailed
     */
    @Override
    public DevAttrHistory_4 read_attribute_history_4(final String attributeName, final int maxSize) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("read_attribute_history_4");
        DevAttrHistory_4 result = null;
        try {
            final AttributeImpl attr = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            result = attr.getHistory().getAttrHistory4(maxSize);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        }
        return result;
    }

    /**
     * Read some attributes. IDL 1 version.
     * 
     * @param attributeNames
     *            The attributes names
     * @return The read results.
     * @throws DevFailed
     */
    @Override
    public AttributeValue[] read_attributes(final String[] attributeNames) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("read_attributes");
        if (attributeNames.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }
        AttributeValue[] result = null;
        deviceLock.lockAttribute();
        try {
            result = AttributeGetterSetter.getAttributesValues(attributeNames, cacheManager, attributeList,
                    aroundInvokeImpl, true);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }
        return result;
    }

    /**
     * Read some attributes. IDL 2 version.
     * 
     * @param names
     *            The attributes names
     * @param source
     *            the device source (CACHE, DEV or CACHE_DEV)
     * @return The read results.
     * @throws DevFailed
     */
    @Override
    public AttributeValue[] read_attributes_2(final String[] names, final DevSource source) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("read_attributes_2", source);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }

        boolean fromCache = false;
        if (source.equals(DevSource.CACHE) || source.equals(DevSource.CACHE_DEV)) {
            fromCache = true;
        }
        deviceLock.lockAttribute();
        AttributeValue[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues(names, cacheManager, attributeList, aroundInvokeImpl,
                    fromCache);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }
        xlogger.exit();
        return result;
    }

    /**
     * Read some attributes. IDL 3 version.
     * 
     * @param names
     *            The attributes names
     * @param source
     *            the device source (CACHE, DEV or CACHE_DEV)
     * @return The read results.
     * @throws DevFailed
     */
    @Override
    public AttributeValue_3[] read_attributes_3(final String[] names, final DevSource source) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("read_attributes_3", source);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }

        boolean fromCache = false;
        if (source.equals(DevSource.CACHE) || source.equals(DevSource.CACHE_DEV)) {
            fromCache = true;
        }
        deviceLock.lockAttribute();
        AttributeValue_3[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues3(names, cacheManager, attributeList, aroundInvokeImpl,
                    fromCache);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }
        xlogger.exit();
        return result;
    }

    /**
     * Read some attributes. IDL 4 version.
     * 
     * @param names
     *            The attributes names
     * @param source
     *            the device source (CACHE, DEV or CACHE_DEV)
     * @param clIdent
     *            the client ID
     * @return The read results.
     * @throws DevFailed
     */
    @Override
    public AttributeValue_4[] read_attributes_4(final String[] names, final DevSource source, final ClntIdent clIdent)
            throws DevFailed {
        // final Profiler profilerPeriod = new Profiler("period");
        // profilerPeriod.start(Arrays.toString(names));
        MDC.put(MDC_KEY, name);
        xlogger.entry(Arrays.toString(names));
        checkInitialization();
        blackBox.insertInblackBox("read_attributes_4 " + Arrays.toString(names), source, clIdent);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }
        if (!name.equalsIgnoreCase(adm_name())) {
            clientLocking.checkClientLocking(clIdent, names);
        }
        deviceLock.lockAttribute();
        AttributeValue_4[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues4(name, names, cacheManager, attributeList,
                    aroundInvokeImpl, source);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }
        xlogger.exit();
        // profilerPeriod.stop().print();
        return result;
    }

    /**
     * Write some attributes. IDL 1 version
     * 
     * @param values
     *            a container for attribute values.
     * @throws DevFailed
     */
    @Override
    public void write_attributes(final AttributeValue[] values) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("write_attributes");
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
            logger.debug("writing {}", names[i]);
        }
        deviceLock.lockAttribute();
        try {
            AttributeGetterSetter.setAttributeValue(values, attributeList, stateImpl, aroundInvokeImpl);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }
        xlogger.exit();
    }

    /**
     * Write some attributes. IDL 2 version
     * 
     * @param values
     *            a container for attribute values.
     * @throws DevFailed
     */
    @Override
    public void write_attributes_3(final AttributeValue[] values) throws MultiDevFailed, DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("write_attributes_3");
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
            logger.debug("writing {}", names[i]);
        }
        deviceLock.lockAttribute();
        try {
            AttributeGetterSetter.setAttributeValue(values, attributeList, stateImpl, aroundInvokeImpl);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }
        xlogger.exit();

    }

    /**
     * Write some attributes. IDL 4 version
     * 
     * @param values
     *            a container for attribute values.
     * @param clIdent
     *            the client ID
     * @throws DevFailed
     */
    @Override
    public void write_attributes_4(final AttributeValue_4[] values, final ClntIdent clIdent) throws MultiDevFailed,
            DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
        }
        logger.debug("writing {}", Arrays.toString(names));
        blackBox.insertInblackBox("write_attributes_4 " + Arrays.toString(names), clIdent);
        if (!name.equalsIgnoreCase(adm_name())) {
            clientLocking.checkClientLocking(clIdent, names);
        }
        deviceLock.lockAttribute();

        try {
            AttributeGetterSetter.setAttributeValue4(values, attributeList, stateImpl, aroundInvokeImpl);
        } catch (final Exception e) {
            if (e instanceof MultiDevFailed) {
                throw (MultiDevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }

        xlogger.exit();
    }

    /**
     * Write and read attributes in a single request
     * 
     * @param values
     *            the values to write
     * @param clIdent
     *            the client ID
     * @return the read results
     */
    @Override
    public AttributeValue_4[] write_read_attributes_4(final AttributeValue_4[] values, final ClntIdent clIdent)
            throws MultiDevFailed, DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();

        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
        }
        blackBox.insertInblackBox("write_read_attributes_4 " + Arrays.toString(names), clIdent);

        AttributeValue_4[] val = null;
        if (!name.equalsIgnoreCase(adm_name())) {
            clientLocking.checkClientLocking(clIdent, names);
        }
        deviceLock.lockAttribute();
        try {
            val = writeRead(values);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockAttribute();
        }

        xlogger.exit();
        return val;
    }

    /**
     * @param values
     * @return The read values
     * @throws DevFailed
     */
    private AttributeValue_4[] writeRead(final AttributeValue_4[] values) throws DevFailed {
        try {
            AttributeGetterSetter.setAttributeValue4(values, attributeList, stateImpl, aroundInvokeImpl);
        } catch (final MultiDevFailed e) {
            throw new DevFailed(e.errors[0].err_list);
        }
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
        }
        return AttributeGetterSetter.getAttributesValues4(name, names, cacheManager, attributeList, aroundInvokeImpl,
                DevSource.DEV);
    }

    /**
     * Query all commands details. IDL 1 version
     * 
     * @return the commands details of this device
     * @throws DevFailed
     */
    @Override
    public DevCmdInfo[] command_list_query() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_list_query");
        // Retrieve number of command and allocate memory to send back info
        final List<CommandImpl> cmdList = getCommandList();
        Collections.sort(cmdList);
        final DevCmdInfo[] back = new DevCmdInfo[commandList.size()];
        int i = 0;
        for (final CommandImpl cmd : cmdList) {
            final DevCmdInfo tmp = new DevCmdInfo();
            tmp.cmd_name = cmd.getName();
            tmp.cmd_tag = cmd.getTag();
            tmp.in_type = cmd.getInType().getTangoIDLType();
            tmp.out_type = cmd.getOutType().getTangoIDLType();
            tmp.in_type_desc = cmd.getInTypeDesc();
            tmp.out_type_desc = cmd.getOutTypeDesc();
            back[i++] = tmp;
        }
        xlogger.exit();
        return back;
    }

    /**
     * Query all commands details. IDL 2 version
     * 
     * @return the commands details of this device
     * @throws DevFailed
     */
    @Override
    public DevCmdInfo_2[] command_list_query_2() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_list_query_2");
        final DevCmdInfo_2[] back = new DevCmdInfo_2[commandList.size()];
        int i = 0;
        final List<CommandImpl> cmdList = getCommandList();
        Collections.sort(cmdList);
        for (final CommandImpl cmd : cmdList) {
            final DevCmdInfo_2 tmp = new DevCmdInfo_2();
            tmp.cmd_name = cmd.getName();
            tmp.cmd_tag = cmd.getTag();
            tmp.level = cmd.getDisplayLevel();
            tmp.in_type = cmd.getInType().getTangoIDLType();
            tmp.out_type = cmd.getOutType().getTangoIDLType();
            tmp.in_type_desc = cmd.getInTypeDesc();
            tmp.out_type_desc = cmd.getOutTypeDesc();
            back[i++] = tmp;
        }
        logger.debug("found {} commands ", commandList.size());
        xlogger.exit();
        return back;
    }

    /**
     * Query a command details. IDL 1 version.
     * 
     * @param commandName
     *            the command name
     * @return the command details of this device
     * @throws DevFailed
     */
    @Override
    public DevCmdInfo command_query(final String commandName) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_query " + commandName);
        final CommandImpl foundCmd = getCommand(commandName);
        final DevCmdInfo tmp = new DevCmdInfo();
        tmp.cmd_name = foundCmd.getName();
        tmp.cmd_tag = foundCmd.getTag();
        tmp.in_type = foundCmd.getInType().getTangoIDLType();
        tmp.out_type = foundCmd.getOutType().getTangoIDLType();
        tmp.in_type_desc = foundCmd.getInTypeDesc();
        tmp.out_type_desc = foundCmd.getOutTypeDesc();
        return tmp;
    }

    /**
     * Query a command details. IDL 2 version.
     * 
     * @param commandName
     *            the command name
     * @return the command details of this device
     * @throws DevFailed
     */
    @Override
    public DevCmdInfo_2 command_query_2(final String commandName) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_query_2 " + commandName);
        final CommandImpl foundCmd = getCommand(commandName);
        final DevCmdInfo_2 tmp = new DevCmdInfo_2();
        tmp.cmd_name = foundCmd.getName();
        tmp.cmd_tag = foundCmd.getTag();
        tmp.in_type = foundCmd.getInType().getTangoIDLType();
        tmp.out_type = foundCmd.getOutType().getTangoIDLType();
        tmp.in_type_desc = foundCmd.getInTypeDesc();
        tmp.out_type_desc = foundCmd.getOutTypeDesc();
        tmp.level = foundCmd.getDisplayLevel();
        return tmp;
    }

    /**
     * Execute a command. IDL 1 version
     * 
     * @param command
     *            command name
     * @param argin
     *            command parameters
     * @return command result
     * @throws DevFailed
     */
    @Override
    public Any command_inout(final String command, final Any argin) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_inout " + command);
        Any argout = null;
        deviceLock.lockCommand();
        try {
            argout = commandHandler(command, argin, DevSource.CACHE_DEV);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockCommand();
        }
        xlogger.exit();
        return argout;
    }

    /**
     * Execute a command. IDL 2 version
     * 
     * @param command
     *            command name
     * @param argin
     *            command parameters
     * @param source
     *            the device source (CACHE, DEV or CACHE_DEV)
     * @return command result
     * @throws DevFailed
     */
    @Override
    public Any command_inout_2(final String command, final Any argin, final DevSource source) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_inout_2 " + command, source);
        Any argout = null;

        deviceLock.lockCommand();
        try {
            argout = commandHandler(command, argin, source);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockCommand();
        }
        xlogger.exit();
        return argout;
    }

    /**
     * Execute a command. IDL 4 version
     * 
     * @param commandName
     *            command name
     * @param argin
     *            command parameters
     * @param source
     *            the device source (CACHE, DEV or CACHE_DEV)
     * @param clIdent
     *            client id
     * @return command result
     * @throws DevFailed
     */
    @Override
    public Any command_inout_4(final String commandName, final Any argin, final DevSource source,
            final ClntIdent clIdent) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry(commandName);
        checkInitialization();
        blackBox.insertInblackBox("Operation command_inout_4 (cmd = " + commandName + ")", source, clIdent);
        clientIdentity = clIdent;
        Any argout = null;
        if (!name.equalsIgnoreCase(adm_name())) {
            clientLocking.checkClientLocking(clIdent, commandName);
        }
        deviceLock.lockCommand();
        try {
            argout = commandHandler(commandName, argin, source);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        } finally {
            deviceLock.unlockCommand();
        }
        xlogger.exit();
        return argout;
    }

    /**
     * Command history. IDL 2 version.
     * 
     * @param commandName
     *            the command name
     * @param maxSize
     *            the maximum depth of history
     * @return the history
     * @throws DevFailed
     */
    @Override
    public DevCmdHistory[] command_inout_history_2(final String commandName, final int maxSize) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_inout_history_2 " + commandName);
        // TODO command_inout_history_2
        // returncommandHistory.get(command).toArray(n)
        return new DevCmdHistory[] {};
    }

    /**
     * Command history. IDL 4 version.
     * 
     * @param commandName
     *            the command name
     * @param maxSize
     *            the maximum depth of history
     * @return the history
     * @throws DevFailed
     */
    @Override
    public DevCmdHistory_4 command_inout_history_4(final String commandName, final int maxSize) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("command_inout_history_4 " + commandName);
        final CommandImpl command = getCommand(commandName);

        DevCmdHistory_4 history = null;
        try {
            history = command.getHistory().toDevCmdHistory4(maxSize);
        } catch (final Exception e) {
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                DevFailedUtils.throwDevFailed(e);
            }
        }
        return history;
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
            cmd = getCommand(objectName);
            isACommand = true;
        } catch (final DevFailed e) {
        }
        if (!isACommand) {
            // polled object is not a command. May be an attribute
            AttributeImpl att = null;
            try {
                att = AttributeGetterSetter.getAttribute(objectName, attributeList);
            } catch (final DevFailed e) {
                logger.error(POLLED_OBJECT + objectName + " not found");
                DevFailedUtils.throwDevFailed(ExceptionMessages.POLL_OBJ_NOT_FOUND, POLLED_OBJECT + objectName
                        + " not found");
            }
            checkPolling(objectName, att);
            try {
                cacheManager.getAttributeCache(att).refresh(att.getName());
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    DevFailedUtils.throwDevFailed(e.getCause());
                }
            }
        } else {
            checkPolling(objectName, cmd);
            try {
                cacheManager.getCommandCache(cmd).refresh(cmd.getName());
            } catch (final CacheException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    DevFailedUtils.throwDevFailed(e.getCause());
                }
            }
        }
    }

    private void checkPolling(final String objectName, final IPollable pollable) throws DevFailed {
        if (pollable.isPolled() && pollable.getPollingPeriod() > 0) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.NOT_SUPPORTED, POLLED_OBJECT + objectName
                    + " cannot be trigger externally");
        } else if (!pollable.isPolled()) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.POLL_OBJ_NOT_FOUND, POLLED_OBJECT + objectName
                    + " not polled");
        }
    }

    /**
     * @param commandName
     * @param inAny
     * @param fromCache
     * @return The command result
     * @throws DevFailed
     */
    private Any commandHandler(final String commandName, final Any inAny, final DevSource source) throws DevFailed {
        xlogger.entry();
        boolean fromCache = false;
        if (source.equals(DevSource.CACHE) || source.equals(DevSource.CACHE_DEV)) {
            fromCache = true;
        }
        Object ret;
        final CommandImpl cmd = getCommand(commandName);
        if (!name.equalsIgnoreCase(ServerManager.getInstance().getAdminDeviceName()) && source.equals(DevSource.CACHE)
                && !cmd.isPolled()) {
            // command is not polled, so throw exception except for admin device
            DevFailedUtils.throwDevFailed(ExceptionMessages.CMD_NOT_POLLED, "Command " + commandName + " not polled");
        }

        // Check if the command is allowed
        if (!cmd.getName().equals(STATUS_NAME) && !cmd.getName().equals(STATE_NAME) && !cmd.getName().equals(INIT_CMD)) {
            final DeviceState currentState = DeviceState.getDeviceState(stateImpl.updateState());
            if (!cmd.isAllowed(currentState)) {
                DevFailedUtils.throwDevFailed("API_CommandNotAllowed", "Command " + commandName
                        + " not allowed when the device is in " + currentState + " state");
            }
        }
        // Call the always executed method
        aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_COMMAND, commandName));
        // Execute the command
        if (cmd.isPolled() && fromCache) {
            logger.debug("execute command {} from CACHE", cmd.getName());

            try {
                final Element element = cacheManager.getCommandCache(cmd).get(cmd.getName());
                final Serializable cmdValue = element.getValue();
                if (cmdValue instanceof org.tango.server.attribute.AttributeValue) {
                    // state or status are returned as attributevalue
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
        } else {
            logger.debug("execute command {} from DEVICE", cmd.getName());
            final Object input = CleverAnyCommand.get(inAny, cmd.getInTangoType(), !cmd.isArginPrimitive());
            ret = cmd.execute(input);
        }
        aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_COMMAND, commandName));
        stateImpl.stateMachine(cmd.getEndState());

        xlogger.exit();
        return CleverAnyCommand.set(cmd.getOutTangoType(), ret);
    }

    /**
     * Get attributes config. IDL3 version
     * 
     * @param attributeNames
     *            the attribute names or "All attributes"
     * @return the attribute configs
     * @throws DevFailed
     */
    @Override
    public AttributeConfig_3[] get_attribute_config_3(final String[] attributeNames) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry(Arrays.toString(attributeNames));
        checkInitialization();
        blackBox.insertInblackBox("get_attribute_config_3 " + Arrays.toString(attributeNames));
        // check if we must retrieve all attributes config
        final int length = attributeNames.length;
        boolean getAllConfig = false;
        if (length == 1 && attributeNames[0].contains(ALL_ATTR)) {
            getAllConfig = true;
        }

        AttributeConfig_3[] result;
        if (getAllConfig) {
            logger.debug("get All ");
            final List<AttributeImpl> attrList = getAttributeList();
            // Collections.sort(attrList);
            result = new AttributeConfig_3[attributeList.size()];
            int i = 0;
            for (final AttributeImpl attribute : attrList) {
                if (!attribute.getName().equals(STATE_NAME) && !attribute.getName().equals(STATUS_NAME)) {
                    result[i++] = TangoIDLAttributeUtil.toAttributeConfig3(attribute);
                }
            }
            result[i++] = TangoIDLAttributeUtil.toAttributeConfig3(AttributeGetterSetter.getAttribute(STATE_NAME,
                    attrList));
            result[i++] = TangoIDLAttributeUtil.toAttributeConfig3(AttributeGetterSetter.getAttribute(STATUS_NAME,
                    attrList));
        } else {
            result = new AttributeConfig_3[attributeNames.length];
            int i = 0;
            for (final String attributeName : attributeNames) {
                final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
                logger.debug("{}:{}", attributeName, attribute.getProperties());
                result[i++] = TangoIDLAttributeUtil.toAttributeConfig3(attribute);
            }
        }
        xlogger.exit();
        return result;
    }

    /**
     * Get attributes config. IDL2 version
     * 
     * @param attributeNames
     *            the attribute names or "All attributes"
     * @return the attribute configs
     * @throws DevFailed
     */
    @Override
    public AttributeConfig_2[] get_attribute_config_2(final String[] attributeNames) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry(Arrays.toString(attributeNames));
        checkInitialization();
        blackBox.insertInblackBox("get_attribute_config_2 " + Arrays.toString(attributeNames));
        // check if we must retrieve all attributes config
        final int length = attributeNames.length;
        boolean getAllConfig = false;
        if (length == 1 && attributeNames[0].contains(ALL_ATTR)) {
            getAllConfig = true;
        }

        AttributeConfig_2[] result;
        if (getAllConfig) {
            logger.debug("get all config");
            final List<AttributeImpl> attrList = getAttributeList();
            // Collections.sort(attrList);
            result = new AttributeConfig_2[attributeList.size()];
            int i = 0;
            for (final AttributeImpl attribute : attrList) {
                if (!attribute.getName().equals(STATE_NAME) && !attribute.getName().equals(STATUS_NAME)) {
                    result[i++] = TangoIDLAttributeUtil.toAttributeConfig2(attribute);
                }
            }
            result[i++] = TangoIDLAttributeUtil.toAttributeConfig2(AttributeGetterSetter.getAttribute(STATE_NAME,
                    attrList));
            result[i++] = TangoIDLAttributeUtil.toAttributeConfig2(AttributeGetterSetter.getAttribute(STATUS_NAME,
                    attrList));

        } else {
            result = new AttributeConfig_2[attributeNames.length];
            logger.debug("get config for " + Arrays.toString(attributeNames));
            int i = 0;
            for (final String attributeName : attributeNames) {
                final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
                result[i++] = TangoIDLAttributeUtil.toAttributeConfig2(attribute);

            }
        }
        xlogger.exit();
        return result;
    }

    /**
     * Get attributes config. IDL1 version
     * 
     * @param attributeNames
     *            the attribute names or "All attributes"
     * @return the attribute configs
     * @throws DevFailed
     */
    @Override
    public AttributeConfig[] get_attribute_config(final String[] attributeNames) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("get_attribute_config " + Arrays.toString(attributeNames));
        // check if we must retrieve all attributes config
        final int length = attributeNames.length;
        boolean getAllConfig = false;
        if (length == 1 && attributeNames[0].contains(ALL_ATTR)) {
            getAllConfig = true;
        }

        AttributeConfig[] result;
        if (getAllConfig) {
            final List<AttributeImpl> attrList = getAttributeList();
            Collections.sort(attrList);
            result = new AttributeConfig[attributeList.size()];
            int i = 0;
            for (final AttributeImpl attribute : attrList) {
                result[i++] = TangoIDLAttributeUtil.toAttributeConfig(attribute);
            }
        } else {
            result = new AttributeConfig[attributeNames.length];
            int i = 0;
            for (final String attributeName : attributeNames) {
                final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
                result[i++] = TangoIDLAttributeUtil.toAttributeConfig(attribute);

            }
        }
        xlogger.exit();
        return result;
    }

    /**
     * Set some attribute configs. IDL4 version
     * 
     * @param newConf
     *            the new configurations
     * @param clIdent
     *            client id
     * @throws DevFailed
     */
    @Override
    public void set_attribute_config_4(final AttributeConfig_3[] newConf, final ClntIdent clIdent) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        if (!name.equalsIgnoreCase(adm_name())) {
            clientLocking.checkClientLocking(clIdent);
        }
        blackBox.insertInblackBox("set_attribute_config_4", clIdent);
        set_attribute_config_3(newConf);
        xlogger.exit();

    }

    /**
     * Set some attribute configs. IDL3 version
     * 
     * @param newConf
     *            the new configurations
     * @throws DevFailed
     */
    @Override
    public void set_attribute_config_3(final AttributeConfig_3[] newConf) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("set_attribute_config_3");
        for (final AttributeConfig_3 attributeConfig : newConf) {
            final String attributeName = attributeConfig.name;

            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
                DevFailedUtils.throwDevFailed("set attribute is not possible for " + attribute.getName());
            }
            if (!attribute.getFormat().equals(attributeConfig.data_format)
                    || !attribute.getWritable().equals(attributeConfig.writable)
                    || !attribute.getDispLevel().equals(attributeConfig.level)
                    || attribute.getTangoType() != attributeConfig.data_type) {
                DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, "not a good config");
            }
            final AttributePropertiesImpl props = TangoIDLAttributeUtil.toAttributeProperties(attributeConfig);
            logger.debug("set_attribute_config_3: {}", props);
            attribute.setProperties(props);

        }
        xlogger.exit();
    }

    /**
     * Set some attribute configs. IDL2 version
     * 
     * @param newConf
     *            the new configurations
     * @throws DevFailed
     */
    @Override
    public void set_attribute_config(final AttributeConfig[] newConf) throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        blackBox.insertInblackBox("set_attribute_config");
        for (final AttributeConfig attributeConfig : newConf) {
            final String attributeName = attributeConfig.name;
            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
                DevFailedUtils.throwDevFailed("set attribute is not possible for " + attribute.getName());
            }
            if (!attribute.getFormat().equals(attributeConfig.data_format)
                    || !attribute.getWritable().equals(attributeConfig.writable)
                    || attribute.getTangoType() != attributeConfig.data_type) {
                DevFailedUtils.throwDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, "not a good config");
            }
            final AttributePropertiesImpl props = TangoIDLAttributeUtil.toAttributeProperties(attributeConfig);
            logger.debug("set_attribute_config: {}", props);
            attribute.setProperties(props);

        }
        xlogger.exit();
    }

    /**
     * Get a copy of the commands
     * 
     * @return the commands
     */
    public List<CommandImpl> getCommandList() {
        return new ArrayList<CommandImpl>(commandList);
    }

    /**
     * add a command
     * 
     * @param command
     *            the new command
     * @throws DevFailed
     */
    public void addCommand(final CommandImpl command) throws DevFailed {
        CommandImpl result = null;
        for (final CommandImpl cmd : commandList) {
            if (command.getName().equalsIgnoreCase(cmd.getName())) {
                result = command;
                break;
            }
        }
        if (result == null) {
            commandList.add(command);
            if (cmdPollRingDepth.containsKey(command.getName().toLowerCase(Locale.ENGLISH))) {
                command.setPollRingDepth(cmdPollRingDepth.get(command.getName().toLowerCase(Locale.ENGLISH)));
            }
        }
    }

    /**
     * Remove a command
     * 
     * @param command
     * @throws DevFailed
     */
    public void removeCommand(final CommandImpl command) throws DevFailed {
        if (!command.getName().equals(INIT_CMD)) {
            commandList.remove(command);
            cacheManager.removeCommandPolling(command);
        }
    }

    /**
     * Get device name
     * 
     * @return device name
     */
    public String getName() {
        return name;
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
        checkPollingLimits(attributeName, pollingPeriod, minAttributePolling);
        final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
        attribute.configurePolling(pollingPeriod);
        if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
            // command is also set as polled
            final CommandImpl cmd = getCommand(attribute.getName());
            cmd.configurePolling(pollingPeriod);
            cacheManager.startStateStatusPolling(cmd, attribute);
        } else {
            cacheManager.startAttributePolling(attribute);
        }
        pollAttributes.put(attributeName.toLowerCase(Locale.ENGLISH), pollingPeriod);
        savePollingConfig();
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
        final CommandImpl command = getCommand(commandName);
        if (!command.getName().equals(INIT_CMD) && command.getInType().equals(CommandTangoType.VOID)) {
            command.configurePolling(pollingPeriod);
            if (command.getName().equals(STATE_NAME) || command.getName().equals(STATUS_NAME)) {
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
                DevFailedUtils.throwDevFailed(MIN_POLLING_PERIOD_IS + minPolling);
            }
            if (minPollingValues.containsKey(commandName.toLowerCase(Locale.ENGLISH))
                    && pollingPeriod < minPollingValues.get(commandName.toLowerCase(Locale.ENGLISH))) {
                DevFailedUtils.throwDevFailed(MIN_POLLING_PERIOD_IS + minPolling);
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
        if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
            // command is also set as polled
            final CommandImpl cmd = getCommand(attribute.getName());
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
        final CommandImpl command = getCommand(commandName);
        command.resetPolling();
        cacheManager.removeCommandPolling(command);
        if (command.getName().equals(STATE_NAME) || command.getName().equals(STATUS_NAME)) {
            // attribute is also set as polled
            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(command.getName(), attributeList);
            attribute.resetPolling();
            cacheManager.removeAttributePolling(attribute);
            pollAttributes.remove(command.getName().toLowerCase(Locale.ENGLISH));
            savePollingConfig();
        }
    }

    public void lock(final int validity, final ClntIdent clientIdent, final String hostName) throws DevFailed {
        clientLocking.lock(validity, clientIdent, hostName);
    }

    public void relock() throws DevFailed {
        clientLocking.relock();
    }

    public void unLock(final boolean isForced) {
        clientLocking.unLock(isForced);
    }

    public DevVarLongStringArray getLockStatus() {
        return clientLocking.getLockStatus();
    }

    /**
     * get State
     * 
     * @return state
     */
    @Override
    public DevState state() {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        try {
            state = getState();
        } catch (final DevFailed e) {
            try {
                stateImpl.stateMachine(DeviceState.UNKNOWN);
                statusImpl.statusMachine(DevFailedUtils.toString(e), DeviceState.UNKNOWN);
                state = DevState.UNKNOWN;
            } catch (final DevFailed e1) {
                logger.debug(NOT_IMPORTANT_ERROR, e1);
            }
            logger.debug(NOT_IMPORTANT_ERROR, e);
        }
        return state;
    }

    /**
     * get Status
     * 
     * @return status
     */
    @Override
    public String status() {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        try {
            status = getStatus();
        } catch (final DevFailed e) {
            try {
                stateImpl.stateMachine(DeviceState.UNKNOWN);
                statusImpl.statusMachine(DevFailedUtils.toString(e), DeviceState.UNKNOWN);
                status = DevFailedUtils.toString(e);
            } catch (final DevFailed e1) {
                logger.debug(NOT_IMPORTANT_ERROR, e1);
            }
            logger.debug(NOT_IMPORTANT_ERROR, e);
        }
        return status;
    }

    /**
     * get CORBA id
     * 
     * @return CORBA id
     */
    public byte[] getObjId() {
        return Arrays.copyOf(objId, objId.length);
    }

    /**
     * set CORBA id
     * 
     * @param objId
     */
    public void setObjId(final byte[] objId) {
        this.objId = Arrays.copyOf(objId, objId.length);
    }

    /**
     * Set delete method {@link Delete}
     * 
     * @param deleteMethod
     */
    public void setDeleteMethod(final Method deleteMethod) {
        this.deleteMethod = deleteMethod;
    }

    /**
     * Set around invoke method {@link AroundInvoke}
     * 
     * @param aroundInvokeImpl
     */
    public void setAroundInvokeImpl(final AroundInvokeImpl aroundInvokeImpl) {
        this.aroundInvokeImpl = aroundInvokeImpl;
    }

    /**
     * Set init method {@link Init}
     * 
     * @param init
     */
    public synchronized void setInit(final InitImpl init) {
        this.init = init;
    }

    /**
     * Get State
     * 
     * @return state
     * @throws DevFailed
     */
    public DevState getState() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();

        if (isCorrectlyInit) {
            // read all attributes to check alarms
            if (stateCheckAttrAlarm) {
                checkAlarms();
            }
            state = stateImpl.updateState();
        } else {
            state = stateImpl.getState();
        }
        return state;
    }

    private void checkAlarms() throws DevFailed {
        logger.debug("State: Number of attribute(s) to read: {}", attributeList.size() - 2);
        for (final AttributeImpl attr : attributeList) {
            try {
                if (!attr.getName().equals(STATE_NAME) && !attr.getName().equals(STATUS_NAME)) {
                    synchronized (attr) {
                        if (!attr.isPolled()) {
                            // refresh value only if not polled
                            attr.updateValue();
                        }
                    }

                }
            } catch (final DevFailed e) {
            }
            if (attr.isOutOfLimits()) {
                // update device state and status with alarm
                stateImpl.addAttributeAlarm(attr.getName());
                statusImpl.addAttributeAlarm(attr.getName(), attr.isAlarmToHigh());
            } else if (attr.isDeltaAlarm()) {
                // update device state and status with alarm
                stateImpl.addAttributeAlarm(attr.getName());
                statusImpl.addDeltaAttributeAlarm(attr.getName());
            } else {
                statusImpl.removeAttributeAlarm(attr.getName());
                stateImpl.removeAttributeAlarm(attr.getName());
            }
        }
    }

    /**
     * Get status
     * 
     * @return status
     * @throws DevFailed
     */
    public String getStatus() throws DevFailed {
        MDC.put(MDC_KEY, name);
        xlogger.entry();
        checkInitialization();
        if (isCorrectlyInit) {
            status = statusImpl.updateStatus(DeviceState.getDeviceState(getState()));
        } else {
            status = statusImpl.getStatus();
        }

        return status;
    }

    public void checkLocking(final ClntIdent clIdent) throws DevFailed {
        if (!clientLocking.isHasBeenForced()) {
            clientLocking.checkClientLocking(clIdent, name);
        }
    }

    /**
     * Get class name as defined in tango db.
     * 
     * @return class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set state impl {@link State}
     * 
     * @param stateImpl
     */
    public void setStateImpl(final StateImpl stateImpl) {
        this.stateImpl = stateImpl;
    }

    /**
     * set status impl {@link Status}
     * 
     * @param statusImpl
     */
    public void setStatusImpl(final StatusImpl statusImpl) {
        this.statusImpl = statusImpl;
    }

    /**
     * String representation of device impl.
     * 
     * @return a string
     */
    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("name", name);
        builder.append("device class", businessObject.getClass());
        builder.append("device tango class", className);
        builder.append("Commands", commandList);
        builder.append("Attributes", attributeList);
        return builder.toString();
    }

    /**
     * get the implementation of the device
     * 
     * @return business object
     */
    public Object getBusinessObject() {
        return businessObject;
    }

    /**
     * get the device properties
     * 
     * @return device properties
     */
    public List<DevicePropertyImpl> getDevicePropertyList() {
        return new ArrayList<DevicePropertyImpl>(devicePropertyList);
    }

    /**
     * Get the class properties
     * 
     * @return class properties
     */
    public List<ClassPropertyImpl> getClassPropertyList() {
        return new ArrayList<ClassPropertyImpl>(classPropertyList);
    }

    /**
     * Get attribute properties manager
     * 
     * @return attribute properties manager
     */
    public AttributePropertiesManager getAttributeProperties() {
        return attributeProperties;
    }

    public int getAttributeHistorySize(final AttributeImpl attribute) {
        return attribute.getHistory().size();
    }

    public int getCommandHistorySize(final CommandImpl command) {
        return command.getHistory().size();
    }

    public ClntIdent getClientIdentity() {
        return clientIdentity;
    }

    public TangoCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setDeviceScheduler(final List<Method> methodList) {
        deviceScheduler = new DeviceScheduler(businessObject, methodList, name, className);
    }
}
