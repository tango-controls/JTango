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

import fr.esrf.Tango.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.omg.CORBA.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.DeviceState;
import org.tango.logging.LoggingLevel;
import org.tango.logging.LoggingManager;
import org.tango.server.Constants;
import org.tango.server.ExceptionMessages;
import org.tango.server.InvocationContext;
import org.tango.server.InvocationContext.CallType;
import org.tango.server.InvocationContext.ContextType;
import org.tango.server.ServerManager;
import org.tango.server.annotation.*;
import org.tango.server.annotation.Device;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.cache.PollingManager;
import org.tango.server.cache.TangoCacheManager;
import org.tango.server.command.CommandImpl;
import org.tango.server.device.*;
import org.tango.server.events.DeviceInterfaceChangedSender;
import org.tango.server.idl.CleverAnyCommand;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.server.idl.TangoIDLUtil;
import org.tango.server.lock.ClientLocking;
import org.tango.server.monitoring.DeviceMonitoring;
import org.tango.server.pipe.PipeImpl;
import org.tango.server.properties.ClassPropertyImpl;
import org.tango.server.properties.DevicePropertiesImpl;
import org.tango.server.properties.DevicePropertyImpl;
import org.tango.server.properties.PropertiesUtils;
import org.tango.server.schedule.DeviceScheduler;
import org.tango.utils.DevFailedUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The CORBA servant for a tango device server in IDL 5.
 *
 * @author ABEILLE
 */
public class DeviceImpl extends Device_5POA {

    public static final String INIT_CMD = "Init";
    /**
     * TANGO system version
     */
    public static final int SERVER_VERSION = 5;
    /**
     * for logging
     */
    public static final String MDC_KEY = "deviceName";
    /**
     * Special attribute name meaning all attributes
     */
    public static final String ALL_ATTR = "All attributes";
    public static final String ALL_PIPES = "All pipes";
    public static final String STATE_NAME = "State";
    public static final String STATUS_NAME = "Status";
    private static final String NOT_IMPORTANT_ERROR = "not important error";
    private static final String READ_ASKED_FOR_0_ATTRIBUTES = "read asked for 0 attributes";
    private static final String READ_ERROR = "READ_ERROR";
    /**
     * Recreating a device does not delete locking object. So maintain a
     * reference
     */
    private static final Map<String, ClientLocking> CLIENT_LOCKING_MAP = new HashMap<String, ClientLocking>();
    private final Logger logger = LoggerFactory.getLogger(DeviceImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DeviceImpl.class);
    /**
     * The name of the tango device
     */
    private final String name;
    /**
     * The implementation of the device
     */
    private final Object businessObject;
    /**
     * Manage device locking
     */
    private final DeviceLocker deviceLock;
    /**
     * the device's attributes
     */
    private final List<PipeImpl> pipeList = new ArrayList<PipeImpl>();
    /**
     * the device's attributes
     */
    private final List<AttributeImpl> attributeList = new ArrayList<AttributeImpl>();
    /**
     * the device's commands
     */
    private final List<CommandImpl> commandList = new ArrayList<CommandImpl>();

    // default attributes
    /**
     * the device's properties
     */
    private final List<DevicePropertyImpl> devicePropertyList = new ArrayList<DevicePropertyImpl>();
    /**
     * the device class's properties
     */
    private final List<ClassPropertyImpl> classPropertyList = new ArrayList<ClassPropertyImpl>();
    /**
     * Black box. History of all client requests
     */
    // private final DeviceBlackBox blackBox = new DeviceBlackBox();
    private final DeviceMonitoring deviceMonitoring;
    /**
     * The class name as defined in tango db
     */
    private final String className;
    /**
     * Manage lazy init
     */
    private final AtomicBoolean isCorrectlyInit = new AtomicBoolean(false);
    /**
     * client info of lastest command. Used for locking device from client
     */
    // private volatile ClntIdent clientIdentity;
    private final ThreadLocal<ClntIdent> clientIdentity = new ThreadLocal<ClntIdent>();
    /**
     * Manage locking from client
     */
    private final ClientLocking clientLocking;
    private final Map<String, Integer> pollAttributes = new HashMap<String, Integer>();
    private final Map<String, Integer> minCommandPolling = new HashMap<String, Integer>();
    private final Map<String, Integer> minAttributePolling = new HashMap<String, Integer>();
    private final Map<String, Integer> cmdPollRingDepth = new HashMap<String, Integer>();
    private final Map<String, Integer> attrPollRingDepth = new HashMap<String, Integer>();
    private final String deviceType;
    /**
     * to init the device
     */
    private InitImpl initImpl;
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
     * all the device's properties. To allow dynamic properties
     */
    private DevicePropertiesImpl deviceProperties;
    /**
     * Manage lazy init
     */
    private volatile boolean isInitializing;
    /**
     * Check all attributes alarms while get state of the device
     */
    private boolean stateCheckAttrAlarm = false;
    private int minPolling = 0;
    private DeviceScheduler deviceScheduler;

    private PollingManager pollingManager;

    private DeviceInterfaceChangedSender interfaceChangeSender;
    /**
     * device property, default polling ring depth for attributes and commands
     */
    private int pollRingDepth = Constants.DEFAULT_POLL_DEPTH;

    private final Map<String, String> contextMap;

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
        MDC.put(MDC_KEY, deviceName);
        contextMap = MDC.getCopyOfContextMap();

        name = deviceName;
        this.className = className;
        this.deviceType = deviceType;
        deviceMonitoring = new DeviceMonitoring(deviceName);
        deviceLock = new DeviceLocker(txType, businessObject.getClass());

        this.businessObject = businessObject;
        if (CLIENT_LOCKING_MAP.containsKey(deviceName)) {
            clientLocking = CLIENT_LOCKING_MAP.get(deviceName);
            clientLocking.init();
        } else {
            clientLocking = new ClientLocking(deviceName, className);
            CLIENT_LOCKING_MAP.put(deviceName, clientLocking);
        }
        // add default device properties
        try {
            // StateCheckAttrAlarm
            final DevicePropertyImpl property = new DevicePropertyImpl(
                    Constants.STATE_CHECK_ATTR_ALARM,
                    "boolean. If true, all attributes will be read at each state/status request to check if alarm is present",
                    this.getClass().getMethod("setStateCheckAttrAlarm", boolean.class), this, name, className, false,
                    Constants.STATE_CHECK_ALARMS_DEFAULT);
            addDeviceProperty(property);
            // cmd_min_poll_period
            final DevicePropertyImpl property2 = new DevicePropertyImpl(Constants.CMD_MIN_POLL_PERIOD,
                    "min poll value for commands", this.getClass().getMethod("setMinCommandPolling", String[].class),
                    this, name, className, false);
            addDeviceProperty(property2);
            // min_poll_period
            final DevicePropertyImpl property3 = new DevicePropertyImpl(Constants.MIN_POLL_PERIOD, "min poll value",
                    this.getClass().getMethod("setMinPolling", int.class), this, name, className, false, "0");
            addDeviceProperty(property3);
            // attr_min_poll_period
            final DevicePropertyImpl property4 = new DevicePropertyImpl(Constants.ATTR__MIN_POLL_PERIOD,
                    "min poll value for attributes", this.getClass()
                            .getMethod("setMinAttributePolling", String[].class), this, name, className, false);
            addDeviceProperty(property4);
            // poll_ring_depth
            final DevicePropertyImpl property10 = new DevicePropertyImpl(Constants.POLL_RING_DEPTH,
                    "default poll ring depth", this.getClass().getMethod("setPollRingDepth", int.class), this, name,
                    className, false, Constants.POLL_RING_DEPTH);
            addDeviceProperty(property10);
            // cmd_poll_ring_depth
            final DevicePropertyImpl property5 = new DevicePropertyImpl(Constants.CMD_POLL_RING_DEPTH,
                    "command poll ring depth", this.getClass().getMethod("setCmdPollRingDepth", String[].class), this,
                    name, className, false);
            addDeviceProperty(property5);
            // attr_poll_ring_depth
            final DevicePropertyImpl property6 = new DevicePropertyImpl(Constants.ATTR_POLL_RING_DEPTH,
                    "attribute poll ring depth", this.getClass().getMethod("setAttrPollRingDepth", String[].class),
                    this, name, className, false);
            addDeviceProperty(property6);
            // POLLED_ATTR
            final DevicePropertyImpl property7 = new DevicePropertyImpl(Constants.POLLED_ATTR, "poll attributes", this
                    .getClass().getMethod("setPolledAttributes", String[].class), this, name, className, false);
            addDeviceProperty(property7);
            // logging_target
            final DevicePropertyImpl property9 = new DevicePropertyImpl(Constants.LOGGING_TARGET, "logging target",
                    this.getClass().getMethod("setLoggingTarget", String.class), this, name, className, false);
            addDeviceProperty(property9);
            // logging_level
            final DevicePropertyImpl property8 = new DevicePropertyImpl(Constants.LOGGING_LEVEL, "logging level", this
                    .getClass().getMethod("setLoggingLevel", String.class), this, name, className, false);
            addDeviceProperty(property8);

            // TODO "logging_rft

        } catch (final SecurityException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final NoSuchMethodException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        logger.debug("Device {} of of {} created with tx type: {}",
                new Object[] { deviceName, businessObject.getClass(), txType });

    }

    public static PipeImpl getPipe(final String name, final List<PipeImpl> pipeList) throws DevFailed {
        PipeImpl result = null;
        for (final PipeImpl pipe : pipeList) {
            if (pipe.getName().equalsIgnoreCase(name)) {
                result = pipe;
                break;
            }
        }
        if (result == null) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_FOUND, name + " does not exists");
        }
        return result;
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
        if (pollingManager != null) {
            pollingManager.setPollAttributes(this.pollAttributes);
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

    public void setPollRingDepth(final int pollRingDepth) {
        if (pollRingDepth > 0) {
            this.pollRingDepth = pollRingDepth;
            if (pollingManager != null) {
                pollingManager.setPollRingDepth(pollRingDepth);
            }
        }
    }

    public void setLoggingLevel(final String level) {
        final LoggingLevel l = LoggingLevel.getLevelFromString(level);
        if (l != null) {
            LoggingManager.getInstance().setLoggingLevel(name, l.toInt());
        }
    }

    public void setLoggingTarget(final String target) throws DevFailed, ClassNotFoundException {
        final String[] config = target.split(LoggingManager.LOGGING_TARGET_SEPARATOR);
        if (config.length == 2) {
            if (config[0].equalsIgnoreCase(LoggingManager.LOGGING_TARGET_DEVICE)) {
                LoggingManager.getInstance().addDeviceAppender(config[1], Class.forName(className), name);
            } else {
                LoggingManager.getInstance().addFileAppender(config[1], name);
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
       MDC.setContextMap(contextMap);
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
       MDC.setContextMap(contextMap);
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
            isCorrectlyInit.set(false);
            deleteDevice();
            doInit();
            try {
                pushInterfaceChangeEvent(false);
            } catch (final DevFailed e) {
                // ignore
                logger.error("error pushing event", e);
            }
        }
        xlogger.exit();
    }

    /**
     * Add an attribute to the device
     *
     * @param attribute
     * @throws DevFailed
     */
    public synchronized void addAttribute(final AttributeImpl attribute) throws DevFailed {
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
            // set default polling configuration
            if (attrPollRingDepth.containsKey(attribute.getName().toLowerCase(Locale.ENGLISH))) {
                attribute.setPollRingDepth(attrPollRingDepth.get(attribute.getName().toLowerCase(Locale.ENGLISH)));
            } else {
                attribute.setPollRingDepth(pollRingDepth);
            }
        }
    }

    /**
     * remove an attribute of the device. Not possible to remove State or Status
     *
     * @param attribute
     * @throws DevFailed
     */
    public synchronized void removeAttribute(final AttributeImpl attribute) throws DevFailed {
        if (attribute.getName().equalsIgnoreCase(STATUS_NAME) || attribute.getName().equalsIgnoreCase(STATE_NAME)) {
            return;
        }
        pollingManager.removeAttributePolling(attribute.getName());
        statusImpl.removeAttributeAlarm(attribute.getName());
        stateImpl.removeAttributeAlarm(attribute.getName());
        attributeList.remove(attribute);
    }

    /**
     *
     * @param pipe
     * @throws DevFailed
     */
    public void addPipe(final PipeImpl pipe) throws DevFailed {
        PipeImpl result = null;
        for (final PipeImpl pipeElt : pipeList) {
            if (pipeElt.getName().equalsIgnoreCase(pipe.getName())) {
                result = pipe;
                break;
            }
        }
        if (result == null) {
            pipeList.add(pipe);
        }
    }

    /**
     * remove pipe of the device. Not possible to remove State or Status
     *
     * @param pipe
     * @throws DevFailed
     */
    public synchronized void removePipe(final PipeImpl pipe) throws DevFailed {
        pipeList.remove(pipe);
    }

    /**
     * Get a copy of the commands
     *
     * @return the commands
     */
    public List<PipeImpl> getPipeList() {
        return new ArrayList<PipeImpl>(pipeList);
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
        return CommandGetter.getCommand(name, commandList);
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
        pollingManager.configurePolling(command);
    }

    public void configurePolling(final AttributeImpl attribute) throws DevFailed {
        pollingManager.configurePolling(attribute);
    }

    private synchronized void doInit() {
        isCorrectlyInit.set(false);

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
            for (final PipeImpl pipe : pipeList) {
                pipe.loadConfiguration();
            }
            initImpl.execute(stateImpl, statusImpl);
            isCorrectlyInit.set(true);
        } catch (final DevFailed e) {
            deviceMonitoring.addError();
            isCorrectlyInit.set(false);
            // ignore error so that server always starts
            try {
                stateImpl.stateMachine(DeviceState.FAULT);
                statusImpl.statusMachine(DevFailedUtils.toString(e), DeviceState.FAULT);
            } catch (final DevFailed e1) {
                // ignore
                logger.debug("not important", e1);
            }
        }

        logger.debug("end with isCorrectlyInit={}", isCorrectlyInit);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (stateImpl == null) {
            stateImpl = new StateImpl(businessObject, null, null);
        }
        if (statusImpl == null) {
            statusImpl = new StatusImpl(businessObject, null, null);
        }
        if (initImpl == null) {
            buildInit(null, false);
        }
        doInit();
        try {
            pushInterfaceChangeEvent(true);
        } catch (final DevFailed e) {
            // ignore
            logger.error("error pushing event", e);
        }
        logger.debug("device init done");
        xlogger.exit();
    }

    public synchronized void pushInterfaceChangeEvent(final boolean isStarted) throws DevFailed {
        final DevIntrChange devInterface = new DevIntrChange(isStarted, command_list_query_2(),
                get_attribute_config_5(new String[] { ALL_ATTR }));
        if (interfaceChangeSender == null) {
            interfaceChangeSender = new DeviceInterfaceChangedSender(name);
        }
        interfaceChangeSender.pushEvent(devInterface, isStarted);
    }

    /**
     * Stops polling calls delete method {@link Delete}. Called before init and
     * at server shutdown
     *
     * @throws DevFailed
     */
    public void deleteDevice() throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        PropertiesUtils.clearCache();
        PropertiesUtils.clearDeviceCache(name);
        PropertiesUtils.clearClassCache(className);
        stopPolling();
        pollingManager.removeAll();
        if (deviceScheduler != null) {
            deviceScheduler.stop();
        }
        if (interfaceChangeSender != null) {
            interfaceChangeSender.stop();
        }
        if (deleteMethod != null) {
            try {
                deleteMethod.invoke(businessObject);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                if (e.getCause() instanceof DevFailed) {
                    throw (DevFailed) e.getCause();
                } else {
                    throw DevFailedUtils.newDevFailed(e.getCause());
                }
            }
        }
        xlogger.exit();
    }

    /**
     * Check if an init is in progress
     *
     * @throws DevFailed if init is init progress
     */
    private synchronized void checkInitialization() throws DevFailed {
        if (initImpl != null) {
            isInitializing = initImpl.isInitInProgress();
        } else {
            isInitializing = false;
        }
        if (isInitializing) {
            throw DevFailedUtils.newDevFailed("CONCURRENT_ERROR", name + " in Init command ");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        deviceMonitoring.startRequest("Operation info");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        deviceMonitoring.startRequest("Operation info_3");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        deviceMonitoring.startRequest("Operation ping");
        xlogger.exit();
    }

    /**
     * @return the admin device of to this device
     */
    @Override
    public String adm_name() {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        deviceMonitoring.startRequest("Attribute adm_name");
        xlogger.exit();
        return getAdminDeviceName();
    }

    public String getAdminDeviceName() {
        return Constants.ADMIN_DEVICE_DOMAIN + "/" + ServerManager.getInstance().getServerName();
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // deviceMonitoring.addRequest("black_box");
        if (maxSize <= 0) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.BLACK_BOX_ARG, maxSize + " is not a good size");
        }
        xlogger.exit();
        return deviceMonitoring.getBlackBox(maxSize);
    }

    /**
     * Get a description of this device
     *
     * @return description
     */
    @Override
    public String description() {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        deviceMonitoring.startRequest("Attribute description requested ");
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
       MDC.setContextMap(contextMap);
        deviceMonitoring.startRequest("Device name");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("read_attribute_history_2");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // TODO read_attribute_history_3
        checkInitialization();
        deviceMonitoring.startRequest("read_attribute_history_3");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("read_attribute_history_4");
        DevAttrHistory_4 result = null;
        try {
            final AttributeImpl attr = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (!attr.isPolled()) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, attr.getName() + " is not polled");
            }
            result = attr.getHistory().getAttrHistory4(maxSize);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (attributeNames.length != 1 || !attributeNames[0].equalsIgnoreCase(DeviceImpl.STATE_NAME)
                && !attributeNames[0].equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        deviceMonitoring.startRequest("read_attributes");
        clientIdentity.set(null);
        if (attributeNames.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }
        AttributeValue[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues(name, attributeNames, pollingManager, attributeList,
                    aroundInvokeImpl, DevSource.CACHE_DEV, deviceLock, null);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (names.length != 1 || !names[0].equalsIgnoreCase(DeviceImpl.STATE_NAME)
                && !names[0].equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        deviceMonitoring.startRequest("read_attributes_2", source);
        clientIdentity.set(null);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }

        AttributeValue[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues(name, names, pollingManager, attributeList,
                    aroundInvokeImpl, source, deviceLock, null);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (names.length != 1 || !names[0].equalsIgnoreCase(DeviceImpl.STATE_NAME)
                && !names[0].equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        deviceMonitoring.startRequest("read_attributes_3", source);
        clientIdentity.set(null);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }
        AttributeValue_3[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues3(name, names, pollingManager, attributeList,
                    aroundInvokeImpl, source, deviceLock, null);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }

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
       MDC.setContextMap(contextMap);
        xlogger.entry(Arrays.toString(names));
        if (names.length != 1 || !names[0].equalsIgnoreCase(DeviceImpl.STATE_NAME)
                && !names[0].equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }

        deviceMonitoring.startRequest("read_attributes_4 " + Arrays.toString(names), source, clIdent);
        clientIdentity.set(clIdent);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent, names);
        }

        AttributeValue_4[] result = null;
        try {
            result = AttributeGetterSetter.getAttributesValues4(name, names, pollingManager, attributeList,
                    aroundInvokeImpl, source, deviceLock, clIdent);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
        // profilerPeriod.stop().print();
        return result;
    }

    /**
     * Read some attributes. IDL 5 version.
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
    public AttributeValue_5[] read_attributes_5(final String[] names, final DevSource source, final ClntIdent clIdent)
            throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry(Arrays.toString(names));
        // final Profiler profiler = new Profiler("read time");
        // profiler.start(Arrays.toString(names));

        if (names.length != 1 || !names[0].equalsIgnoreCase(DeviceImpl.STATE_NAME)
                && !names[0].equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        // profiler.start("blackbox");
        final long request = deviceMonitoring.startRequest("read_attributes_5 " + Arrays.toString(names), source,
                clIdent);
        // profiler.start("locking");
        clientIdentity.set(clIdent);
        if (names.length == 0) {
            throw DevFailedUtils.newDevFailed(READ_ERROR, READ_ASKED_FOR_0_ATTRIBUTES);
        }
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent, names);
        }

        AttributeValue_5[] result = null;

        try {
            // profiler.start("get value");
            result = AttributeGetterSetter.getAttributesValues5(name, names, pollingManager, attributeList,
                    aroundInvokeImpl, source, deviceLock, clIdent);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        } finally {
            deviceMonitoring.endRequest(request);
        }

        // profiler.stop().print();
        xlogger.exit();
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("write_attributes");
        clientIdentity.set(null);
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
            logger.debug("writing {}", names[i]);
        }
        final Object lock = deviceLock.getAttributeLock();
        try {
            synchronized (lock != null ? lock : new Object()) {
                AttributeGetterSetter.setAttributeValue(values, attributeList, stateImpl, aroundInvokeImpl, null);
            }
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
    }

    /**
     * Write some attributes. IDL 3 version
     *
     * @param values
     *            a container for attribute values.
     * @throws DevFailed
     */
    @Override
    public void write_attributes_3(final AttributeValue[] values) throws MultiDevFailed, DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("write_attributes_3");
        clientIdentity.set(null);
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
            logger.debug("writing {}", names[i]);
        }
        final Object lock = deviceLock.getAttributeLock();
        try {
            synchronized (lock != null ? lock : new Object()) {
                AttributeGetterSetter.setAttributeValue(values, attributeList, stateImpl, aroundInvokeImpl, null);
            }
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
        }
        logger.debug("writing {}", Arrays.toString(names));
        deviceMonitoring.startRequest("write_attributes_4 " + Arrays.toString(names), clIdent);
        clientIdentity.set(clIdent);
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent, names);
        }
        final Object lock = deviceLock.getAttributeLock();
        try {
            synchronized (lock != null ? lock : new Object()) {
                AttributeGetterSetter.setAttributeValue4(values, attributeList, stateImpl, aroundInvokeImpl, clIdent);
            }
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof MultiDevFailed) {
                throw (MultiDevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();

        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
        }
        deviceMonitoring.startRequest("write_read_attributes_4 " + Arrays.toString(names), clIdent);
        clientIdentity.set(clIdent);
        AttributeValue_4[] val = null;
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent, names);
        }
        final Object lock = deviceLock.getAttributeLock();
        try {
            synchronized (lock != null ? lock : new Object()) {
                val = writeRead(values);
            }
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
        return val;
    }

    /**
     * Write and read attributes in a single request
     *
     * @param writeValues
     *            the values to write
     * @param readNames
     *            the attributes to read
     * @param clIdent
     *            the client ID
     * @return the read results
     */
    @Override
    public AttributeValue_5[] write_read_attributes_5(final AttributeValue_4[] writeValues, final String[] readNames,
            final ClntIdent clIdent) throws MultiDevFailed, DevFailed {
        deviceMonitoring.startRequest("write_read_attributes_5 ", clIdent);
        clientIdentity.set(clIdent);
        final String[] names = new String[writeValues.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = writeValues[i].name;
        }

        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent, names);
        }

        AttributeValue_5[] resultValues = null;
        final Object lock = deviceLock.getAttributeLock();
        try {
            synchronized (lock != null ? lock : new Object()) {
                aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_WRITE_READ_ATTRIBUTES,
                        CallType.CACHE_DEV, clIdent, name));
                // write attributes
                try {
                    AttributeGetterSetter.setAttributeValue4(writeValues, attributeList, stateImpl, aroundInvokeImpl,
                            clIdent);
                } catch (final MultiDevFailed e) {
                    throw new DevFailed(e.errors[0].err_list);
                }
                // read attributes
                resultValues = AttributeGetterSetter.getAttributesValues5(name, readNames, pollingManager,
                        attributeList, aroundInvokeImpl, DevSource.DEV, deviceLock, clIdent);
                aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_WRITE_READ_ATTRIBUTES,
                        CallType.CACHE_DEV, clIdent, name));
            }
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        return resultValues;
    }

    /**
     * @param values
     * @return The read values
     * @throws DevFailed
     */
    private AttributeValue_4[] writeRead(final AttributeValue_4[] values) throws DevFailed {
        aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_WRITE_READ_ATTRIBUTES, CallType.CACHE_DEV,
                null, name));
        try {
            AttributeGetterSetter.setAttributeValue4(values, attributeList, stateImpl, aroundInvokeImpl, null);
        } catch (final MultiDevFailed e) {
            throw new DevFailed(e.errors[0].err_list);
        }
        final String[] names = new String[values.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = values[i].name;
        }
        final AttributeValue_4[] resultValues = AttributeGetterSetter.getAttributesValues4(name, names, pollingManager,
                attributeList, aroundInvokeImpl, DevSource.DEV, deviceLock, null);
        aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_WRITE_READ_ATTRIBUTES, CallType.CACHE_DEV,
                null, name));
        return resultValues;
    }

    /**
     * Query all commands details. IDL 1 version
     *
     * @return the commands details of this device
     * @throws DevFailed
     */
    @Override
    public DevCmdInfo[] command_list_query() throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // checkInitialization();
        deviceMonitoring.startRequest("command_list_query");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // checkInitialization();
        deviceMonitoring.startRequest("command_list_query_2");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // checkInitialization();
        deviceMonitoring.startRequest("command_query " + commandName);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // checkInitialization();
        deviceMonitoring.startRequest("command_query_2 " + commandName);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (!command.equalsIgnoreCase(DeviceImpl.STATE_NAME) && !command.equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        final long request = deviceMonitoring.startRequest("command_inout " + command);
        clientIdentity.set(null);
        Any argout = null;
        try {
            argout = commandHandler(command, argin, DevSource.CACHE_DEV, null);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        } finally {
            deviceMonitoring.endRequest(request);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (!command.equalsIgnoreCase(DeviceImpl.STATE_NAME) && !command.equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        deviceMonitoring.startRequest("command_inout_2 " + command, source);
        clientIdentity.set(null);
        Any argout = null;
        try {
            argout = commandHandler(command, argin, source, null);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
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
       MDC.setContextMap(contextMap);
        xlogger.entry(commandName);
        if (!commandName.equalsIgnoreCase(DeviceImpl.STATE_NAME)
                && !commandName.equalsIgnoreCase(DeviceImpl.STATUS_NAME)) {
            checkInitialization();
        }
        final long request = deviceMonitoring.startRequest("Operation command_inout_4 (cmd = " + commandName + ")",
                source, clIdent);
        clientIdentity.set(clIdent);
        Any argout = null;
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent, commandName);
        }
        try {
            argout = commandHandler(commandName, argin, source, clIdent);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        } finally {
            deviceMonitoring.endRequest(request);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("command_inout_history_2 " + commandName);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        final long request = deviceMonitoring.startRequest("command_inout_history_4 " + commandName);
        final CommandImpl command = getCommand(commandName);

        DevCmdHistory_4 history = null;
        try {
            history = command.getHistory().toDevCmdHistory4(maxSize);
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        } finally {
            deviceMonitoring.endRequest(request);
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
        pollingManager.triggerPolling(objectName);
    }

    /**
     * @param commandName
     * @param inAny
     * @param clntIdent
     * @return The command result
     * @throws DevFailed
     */
    private Any commandHandler(final String commandName, final Any inAny, final DevSource source,
            final ClntIdent clntIdent) throws DevFailed {
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
            throw DevFailedUtils.newDevFailed(ExceptionMessages.CMD_NOT_POLLED, "Command " + commandName + " not polled");
        }

        // Check if the command is allowed
        if (!cmd.getName().equals(STATUS_NAME) && !cmd.getName().equals(STATE_NAME) && !cmd.getName().equals(INIT_CMD)) {
            final DeviceState currentState = DeviceState.getDeviceState(stateImpl.updateState());
            if (!cmd.isAllowed(currentState)) {
                throw DevFailedUtils.newDevFailed("API_CommandNotAllowed", "Command " + commandName
                        + " not allowed when the device is in " + currentState + " state");
            }
        }
        // Call the always executed method
        final CallType callType = CallType.getFromDevSource(source);
        // Execute the command
        if (cmd.isPolled() && fromCache) {
            logger.debug("execute command {} from CACHE", cmd.getName());
            ret = pollingManager.getCommandCacheElement(cmd);
        } else {
            logger.debug("execute command {} from DEVICE", cmd.getName());
            final Object lock = deviceLock.getCommandLock();
            synchronized (lock != null ? lock : new Object()) {
                aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_COMMAND, callType, clntIdent,
                        commandName));
                final Object input = CleverAnyCommand.get(inAny, cmd.getInTangoType(), !cmd.isArginPrimitive());
                ret = cmd.execute(input);
                aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_COMMAND, callType, clntIdent,
                        commandName));
            }
        }
        stateImpl.stateMachine(cmd.getEndState());

        xlogger.exit();
        return CleverAnyCommand.set(cmd.getOutTangoType(), ret);
    }

    /**
     * Get attributes config. IDL5 version
     *
     * @param attributeNames
     *            the attribute names or "All attributes"
     * @return the attribute configs
     * @throws DevFailed
     */
    @Override
    public AttributeConfig_5[] get_attribute_config_5(final String[] attributeNames) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry(Arrays.toString(attributeNames));
        // checkInitialization();
        deviceMonitoring.startRequest("get_attribute_config_5 " + Arrays.toString(attributeNames));
        // check if we must retrieve all attributes config
        final int length = attributeNames.length;
        boolean getAllConfig = false;

        if (length == 1 && attributeNames[0].contains(ALL_ATTR)) {
            getAllConfig = true;
        }
        AttributeConfig_5[] result;
        if (getAllConfig) {
            logger.debug("get All");
            final List<AttributeImpl> attrList = getAttributeList();
            // Collections.sort(attrList);
            result = new AttributeConfig_5[attributeList.size()];
            int i = 0;
            for (final AttributeImpl attribute : attrList) {
                if (!attribute.getName().equals(STATE_NAME) && !attribute.getName().equals(STATUS_NAME)) {
                    result[i++] = TangoIDLAttributeUtil.toAttributeConfig5(attribute);
                }
            }

            result[i++] = TangoIDLAttributeUtil.toAttributeConfig5(AttributeGetterSetter.getAttribute(STATE_NAME,
                    attrList));

            result[i++] = TangoIDLAttributeUtil.toAttributeConfig5(AttributeGetterSetter.getAttribute(STATUS_NAME,
                    attrList));

        } else {
            result = new AttributeConfig_5[attributeNames.length];
            int i = 0;
            for (final String attributeName : attributeNames) {
                final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
                logger.debug("{}:{}", attributeName, attribute.getProperties());
                result[i++] = TangoIDLAttributeUtil.toAttributeConfig5(attribute);
            }
        }
        xlogger.exit();
        return result;
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
       MDC.setContextMap(contextMap);
        xlogger.entry(Arrays.toString(attributeNames));
        // checkInitialization();
        deviceMonitoring.startRequest("get_attribute_config_3 " + Arrays.toString(attributeNames));
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
       MDC.setContextMap(contextMap);
        xlogger.entry(Arrays.toString(attributeNames));
        // checkInitialization();
        deviceMonitoring.startRequest("get_attribute_config_2 " + Arrays.toString(attributeNames));
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        // checkInitialization();
        deviceMonitoring.startRequest("get_attribute_config " + Arrays.toString(attributeNames));
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
     * Set some attribute configs. IDL5 version
     *
     * @param newConf
     *            the new configurations
     * @param clIdent
     *            client id
     * @throws DevFailed
     */

    @Override
    public void set_attribute_config_5(final AttributeConfig_5[] newConf, final ClntIdent clIdent) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        clientIdentity.set(clIdent);
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent);
        }

        deviceMonitoring.startRequest("set_attribute_config_5", clIdent);
        for (final AttributeConfig_5 attributeConfig : newConf) {
            final String attributeName = attributeConfig.name;

            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
                throw DevFailedUtils.newDevFailed("set attribute is not possible for " + attribute.getName());
            }
            if (!attribute.getFormat().equals(attributeConfig.data_format)
                    || !attribute.getWritable().equals(attributeConfig.writable)
                    || !attribute.getDispLevel().equals(attributeConfig.level)
                    || attribute.getTangoType() != attributeConfig.data_type) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, "not a good config");
            }

            final AttributePropertiesImpl props = TangoIDLAttributeUtil.toAttributeProperties(attributeConfig);
            logger.debug("set_attribute_config_5: {}", props);
            if (!attribute.getProperties().isEnumMutable()
                    && !Arrays.equals(attribute.getProperties().getEnumLabels(), props.getEnumLabels())) {
                throw DevFailedUtils
                        .newDevFailed(ExceptionMessages.NOT_SUPPORTED_FEATURE,
                                "It's not supported to change enumeration labels number from outside the Tango device class code");
            }
            attribute.setProperties(props);
        }
        xlogger.exit();
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        clientIdentity.set(clIdent);
        if (!name.equalsIgnoreCase(getAdminDeviceName())) {
            clientLocking.checkClientLocking(clIdent);
        }
        deviceMonitoring.startRequest("set_attribute_config_4", clIdent);
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("set_attribute_config_3");
        for (final AttributeConfig_3 attributeConfig : newConf) {
            final String attributeName = attributeConfig.name;

            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
                throw DevFailedUtils.newDevFailed("set attribute is not possible for " + attribute.getName());
            }
            if (!attribute.getFormat().equals(attributeConfig.data_format)
                    || !attribute.getWritable().equals(attributeConfig.writable)
                    || !attribute.getDispLevel().equals(attributeConfig.level)
                    || attribute.getTangoType() != attributeConfig.data_type) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, "not a good config");
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
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("set_attribute_config");
        for (final AttributeConfig attributeConfig : newConf) {
            final String attributeName = attributeConfig.name;
            final AttributeImpl attribute = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (attribute.getName().equals(STATE_NAME) || attribute.getName().equals(STATUS_NAME)) {
                throw DevFailedUtils.newDevFailed("set attribute is not possible for " + attribute.getName());
            }
            if (!attribute.getFormat().equals(attributeConfig.data_format)
                    || !attribute.getWritable().equals(attributeConfig.writable)
                    || attribute.getTangoType() != attributeConfig.data_type) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_ALLOWED, "not a good config");
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
    public synchronized void addCommand(final CommandImpl command) throws DevFailed {
        CommandImpl result = null;
        for (final CommandImpl cmd : commandList) {
            if (command.getName().equalsIgnoreCase(cmd.getName())) {
                result = command;
                break;
            }
        }
        if (result == null) {
            commandList.add(command);
            // set default polling configuration
            if (cmdPollRingDepth.containsKey(command.getName().toLowerCase(Locale.ENGLISH))) {
                command.setPollRingDepth(cmdPollRingDepth.get(command.getName().toLowerCase(Locale.ENGLISH)));
            } else {
                command.setPollRingDepth(pollRingDepth);
            }
        }
    }

    /**
     * Remove a command
     *
     * @param command
     * @throws DevFailed
     */
    public synchronized void removeCommand(final CommandImpl command) throws DevFailed {
        if (!command.getName().equalsIgnoreCase(INIT_CMD)) {
            pollingManager.removeCommandPolling(command.getName());
            commandList.remove(command);
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
        pollingManager.addAttributePolling(attributeName, pollingPeriod);
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
        pollingManager.addCommandPolling(commandName, pollingPeriod);

    }

    /**
     * Stop all polling
     */
    public void stopPolling() {
        pollingManager.stopPolling();
    }

    /**
     * Start already configured polling
     */
    public void startPolling() {
        pollingManager.startPolling();
    }

    /**
     * Remove attribute polling
     *
     * @param attributeName
     *            the attribute
     * @throws DevFailed
     */
    public void removeAttributePolling(final String attributeName) throws DevFailed {
        pollingManager.removeAttributePolling(attributeName);
    }

    /**
     * Remove command polling
     *
     * @param commandName
     *            the command
     * @throws DevFailed
     */
    public void removeCommandPolling(final String commandName) throws DevFailed {
        pollingManager.removeCommandPolling(commandName);
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
       MDC.setContextMap(contextMap);
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
       MDC.setContextMap(contextMap);
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
        final TangoCacheManager cacheManager = new TangoCacheManager(name, deviceLock, aroundInvokeImpl);
        pollingManager = new PollingManager(name, cacheManager, attributeList, commandList, minPolling,
                minCommandPolling, minAttributePolling, cmdPollRingDepth, attrPollRingDepth);
        if (initImpl != null) {
            initImpl.setPollingManager(pollingManager);
        }
    }

    /**
     * build init method {@link Init}
     *
     * @return init
     */
    public synchronized InitImpl buildInit(final Method initMethod, final boolean isLazy) {
        if (aroundInvokeImpl == null) {
            aroundInvokeImpl = new AroundInvokeImpl(businessObject, null);
        }
        if (pollingManager == null) {
            final TangoCacheManager cacheManager = new TangoCacheManager(name, deviceLock, aroundInvokeImpl);
            pollingManager = new PollingManager(name, cacheManager, attributeList, commandList, minPolling,
                    minCommandPolling, minAttributePolling, cmdPollRingDepth, attrPollRingDepth);
        }
        initImpl = new InitImpl(name, initMethod, isLazy, businessObject, pollingManager);
        return initImpl;
    }

    /**
     * Get State
     *
     * @return state
     * @throws DevFailed
     */
    public DevState getState() throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        if (isCorrectlyInit.get() && initImpl.isInitDoneCorrectly()) {
            state = stateImpl.updateState();
            // read all attributes to check alarms only if on
            if (state == DevState.ON && stateCheckAttrAlarm) {
                checkAlarms();
            }
        } else {
            state = stateImpl.getState();
        }
        return state;
    }

    private void checkAlarms() throws DevFailed {
        final List<AttributeImpl> attrs = getAttributeList();
        logger.debug("State: Number of attribute(s) to read: {}", attrs.size() - 2);

        for (final AttributeImpl attr : attrs) {
            try {
                if (!attr.getName().equals(STATE_NAME) && !attr.getName().equals(STATUS_NAME)) {
                    synchronized (attr) {
                        if (!attr.isPolled() && !attr.isFwdAttribute()) {
                            // refresh value only if not polled
                            attr.updateValue();
                        }
                    }
                }
            } catch (final DevFailed e) {
            }

            if (attr.isOutOfLimits()) {
                // update device state and status with alarm
                logger.debug("{} is out of limits", attr.getName());
                stateImpl.addAttributeAlarm(attr.getName());
                statusImpl.addAttributeAlarm(attr.getName(), attr.isAlarmToHigh());
            } else if (attr.isDeltaAlarm()) {
                logger.debug("{} has a delta alarm", attr.getName());
                // update device state and status with alarm
                stateImpl.addAttributeAlarm(attr.getName());
                statusImpl.addDeltaAttributeAlarm(attr.getName());
            } else {
                statusImpl.removeAttributeAlarm(attr.getName());
                stateImpl.removeAttributeAlarm(attr.getName());
            }
        }
        state = stateImpl.getState();
    }

    /**
     * Get status
     *
     * @return status
     * @throws DevFailed
     */
    public String getStatus() throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();

        if (initImpl.isInitInProgress()) {
            // set status while init is in progress
            final String tmp = statusImpl.updateStatus(DeviceState.getDeviceState(getState()));
            if (tmp.equals(Constants.INIT_IN_PROGRESS)) {
                status = tmp;
            } else {
                status = Constants.INIT_IN_PROGRESS + System.getProperty("line.separator") + tmp;
            }
        } else if (isCorrectlyInit.get() && initImpl.isInitDoneCorrectly()) {
            // init finished
            status = statusImpl.updateStatus(DeviceState.getDeviceState(getState()));
        } else {
            // init failed
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

    public int getAttributeHistorySize(final AttributeImpl attribute) {
        return attribute.getHistory().size();
    }

    public int getCommandHistorySize(final CommandImpl command) {
        return command.getHistory().size();
    }

    public ClntIdent getClientIdentity() {
        return clientIdentity.get();
    }

    public void setDeviceScheduler(final Set<Method> methodList) {
        deviceScheduler = new DeviceScheduler(businessObject, methodList, name, className);
    }

    /**
     * read an attribute history. IDL 5 version. The history is filled only be
     * attribute polling
     *
     * @param attributeName
     *            The attribute to retrieve
     * @param maxSize
     *            The history maximum size returned
     * @throws DevFailed
     */
    @Override
    public DevAttrHistory_5 read_attribute_history_5(final String attributeName, final int maxSize) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        deviceMonitoring.startRequest("read_attribute_history_5");
        DevAttrHistory_5 result = null;
        try {
            final AttributeImpl attr = AttributeGetterSetter.getAttribute(attributeName, attributeList);
            if (attr.getBehavior() instanceof ForwardedAttribute) {
                final ForwardedAttribute fwdAttr = (ForwardedAttribute) attr.getBehavior();
                result = fwdAttr.getAttributeHistory(maxSize);
            } else {
                if (!attr.isPolled()) {
                    throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED, attr.getName()
                            + " is not polled");
                }
                result = attr.getHistory().getAttrHistory5(maxSize);
            }
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        return result;

    }

    @Override
    public PipeConfig[] get_pipe_config_5(final String[] names) throws DevFailed {
        xlogger.entry(Arrays.toString(names));
        // checkInitialization();
        deviceMonitoring.startRequest("get_pipe_config_5 " + Arrays.toString(names));
        // check if we must retrieve all attributes config
        final int length = names.length;
        boolean getAllConfig = false;
        if (length == 1 && names[0].contains(ALL_PIPES)) {
            getAllConfig = true;
        }

        PipeConfig[] result;
        if (getAllConfig) {
            logger.debug("get All");
            result = new PipeConfig[pipeList.size()];
            int i = 0;
            for (final PipeImpl pipe : pipeList) {
                result[i++] = TangoIDLUtil.toPipeConfig(pipe);
            }
        } else {
            result = new PipeConfig[names.length];
            int i = 0;
            for (final String pipeName : names) {
                final PipeImpl pipe = getPipe(pipeName, pipeList);
                logger.debug("{}:{}", pipeName, pipe.getConfiguration());
                result[i++] = TangoIDLUtil.toPipeConfig(pipe);
            }
        }
        xlogger.exit();
        return result;
    }

    @Override
    public void set_pipe_config_5(final PipeConfig[] newConf, final ClntIdent clIdent) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry();
        checkInitialization();
        clientIdentity.set(clIdent);

        deviceMonitoring.startRequest("set_pipe_config_5", clIdent);
        for (final PipeConfig config : newConf) {
            final String name = config.name;
            final PipeImpl pipe = getPipe(name, pipeList);
            pipe.setConfiguration(config.label, config.description);
        }
        xlogger.exit();
    }

    @Override
    public DevPipeData read_pipe_5(final String name, final ClntIdent clIdent) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry(name);
        final PipeImpl pipe = getPipe(name, pipeList);
        deviceMonitoring.startRequest("read_pipe_5 " + name, clIdent);
        clientIdentity.set(clIdent);
        DevPipeData result = null;
        try {
            aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_PIPE_READ, CallType.UNKNOWN, clIdent,
                    pipe.getName()));
            pipe.updateValue();
            result = TangoIDLUtil.toDevPipeData(pipe.getName(), pipe.getReadValue());
            aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_PIPE_READ, CallType.UNKNOWN, clIdent,
                    pipe.getName()));
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
        return result;
    }

    @Override
    public void write_pipe_5(final DevPipeData value, final ClntIdent clIdent) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry(value.name);
        final PipeImpl pipe = getPipe(value.name, pipeList);
        deviceMonitoring.startRequest("write_pipe_5 " + value.name, clIdent);
        clientIdentity.set(clIdent);
        try {
            aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_PIPE_WRITE, CallType.UNKNOWN, clIdent,
                    pipe.getName()));
            pipe.setValue(TangoIDLUtil.toPipeValue(value));
            aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_PIPE_WRITE, CallType.UNKNOWN, clIdent,
                    pipe.getName()));
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
    }

    @Override
    public DevPipeData write_read_pipe_5(final DevPipeData value, final ClntIdent clIdent) throws DevFailed {
       MDC.setContextMap(contextMap);
        xlogger.entry(name);
        final PipeImpl pipe = getPipe(name, pipeList);
        deviceMonitoring.startRequest("write_read_pipe_5 " + name, clIdent);
        clientIdentity.set(clIdent);
        DevPipeData result = null;
        try {
            aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.PRE_PIPE_WRITE_READ, CallType.UNKNOWN,
                    clIdent, pipe.getName()));
            pipe.setValue(TangoIDLUtil.toPipeValue(value));
            pipe.updateValue();
            result = TangoIDLUtil.toDevPipeData(pipe.getName(), pipe.getReadValue());
            aroundInvokeImpl.aroundInvoke(new InvocationContext(ContextType.POST_PIPE_WRITE_READ, CallType.UNKNOWN,
                    clIdent, pipe.getName()));
        } catch (final Exception e) {
            deviceMonitoring.addError();
            if (e instanceof DevFailed) {
                throw (DevFailed) e;
            } else {
                // with CORBA, the stack trace is not visible by the client if
                // not inserted in DevFailed.
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
        return result;
    }
}
