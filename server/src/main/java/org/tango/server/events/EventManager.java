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
package org.tango.server.events;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.esrf.Tango.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.ExceptionMessages;
import org.tango.server.ServerManager;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.ForwardedAttribute;
import org.tango.server.idl.TangoIDLUtil;
import org.tango.server.pipe.PipeImpl;
import org.tango.server.pipe.PipeValue;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.tango.orb.ORBManager.OAI_ADDR;

/**
 * Set of ZMQ low level utilities
 *
 * @author verdier
 */
//TODO split into server and client side
public final class EventManager {

    public static final int MINIMUM_IDL_VERSION = 4;
    public static final String IDL_REGEX = "idl[0-9]_[a-z]*";
    public static final String IDL_LATEST = "idl" + DeviceImpl.SERVER_VERSION + "_";
    //TODO singleton is anti-pattern
    private static final EventManager INSTANCE = new EventManager();
    private final Logger logger = LoggerFactory.getLogger(EventManager.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventManager.class);
    private final Map<String, EventImpl> eventImplMap = new HashMap<String, EventImpl>();
    private final int serverHWM = initializeServerHwm();
    private final int clientHWN = initializeClientHwm();
    private final List<String> heartbeatEndpoints = new LinkedList<>();
    private final List<String> eventEndpoints = new LinkedList<>();
    private ZMQ.Socket heartbeatSocket;
    private ZMQ.Socket eventSocket;
    private ZContext context;
    private ScheduledExecutorService heartBeatExecutor;
    private AtomicBoolean isInitialized = new AtomicBoolean(false);
    private String heartbeatName;

    private EventManager() {
        //logger.debug("client IP address is {}", ServerRequestInterceptor.getInstance().getClientIPAddress());
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    /**
     * Check if event criteria are set for change and archive events
     *
     * @param attribute the specified attribute
     * @param eventType the specified event type
     * @throws DevFailed if event type is change or archive and no event criteria is set.
     */
    public static void checkEventCriteria(final AttributeImpl attribute, final EventType eventType) throws DevFailed {
        switch (eventType) {
            case CHANGE_EVENT:
                ChangeEventTrigger.checkEventCriteria(attribute);
                break;
            case ARCHIVE_EVENT:
                ArchiveEventTrigger.checkEventCriteria(attribute);
                break;
            default:
                break;
        }
        if (!attribute.isPolled()) {
            // no polled, so check if event is pushed from device
            boolean throwError = false;
            switch (eventType) {
                case ARCHIVE_EVENT:
                    if (!attribute.isPushArchiveEvent()) {
                        throwError = true;
                    }
                    break;
                case CHANGE_EVENT:
                    if (!attribute.isPushChangeEvent()) {
                        throwError = true;
                    }
                    break;
                case DATA_READY_EVENT:
                    if (!attribute.isPushDataReady()) {
                        throwError = true;
                    }
                    break;
                case USER_EVENT:
                case ATT_CONF_EVENT:
                case INTERFACE_CHANGE_EVENT:
                    break;
                case PERIODIC_EVENT:
                default:
                    throwError = true;
                    break;

            }
            if (throwError) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_NOT_POLLED,
                        "The polling (necessary to send events) for the attribute " + attribute.getName()
                                + " is not started");
            }
        }
    }

    private int initializeServerHwm() {
        final String env = System.getenv("TANGO_DS_EVENT_BUFFER_HWM");
        try {
            if (env != null) {
                return Integer.parseInt(env);
            } else {
                return EventConstants.HWM_DEFAULT;
            }
        } catch (final NumberFormatException e) {
            logger.error("system.env TANGO_DS_EVENT_BUFFER_HWM is not a number: {} ", env);
            return EventConstants.HWM_DEFAULT;
        }
    }

    private int initializeClientHwm() {
        // Check the High Water Mark value from Control System property
        String value = "";
        try {
            value = DatabaseFactory.getDatabase().getFreeProperty("CtrlSystem", "EventBufferHwm");
            return Integer.parseInt(value);
        } catch (final DevFailed e) {
            DevFailedUtils.logDevFailed(e, logger);
            return EventConstants.HWM_DEFAULT;
        } catch (final NumberFormatException e) {
            logger.error("CtrlSystem/EventBufferHwm property is not a number: {} ", value);
            return EventConstants.HWM_DEFAULT;
        }
    }

    private void initialize() throws DevFailed {
        xlogger.entry();
        Iterable<String> ipAddress = getIpAddresses();
        Iterable<String> ip4Address = Iterables.filter(ipAddress, s -> s.split("\\.").length == 4);
        context = new ZContext();
        // Get the free ports and build endpoints
        heartbeatSocket = createSocket();
        bindEndpoints(heartbeatSocket, ip4Address, heartbeatEndpoints, SocketType.HEARTBEAT);
        eventSocket = createEventSocket();
        bindEndpoints(eventSocket, ip4Address, eventEndpoints, SocketType.EVENTS);

        // Start the heartbeat thread
        // TODO : without database?
        final String adminDeviceName = ServerManager.getInstance().getAdminDeviceName();
        heartbeatName = EventUtilities.buildHeartBeatEventName(adminDeviceName);
        // start heartbeats
        heartBeatExecutor = Executors.newScheduledThreadPool(1, r -> new Thread(r, "Event HeartBeat"));
        heartBeatExecutor.scheduleAtFixedRate(new HeartbeatThread(heartbeatName), 0,
                EventConstants.EVENT_HEARTBEAT_PERIOD, TimeUnit.MILLISECONDS);
        isInitialized.set(true);
        logger.info("ZMQ ({}) SERVER event system started", ZMQ.getVersionString());
        xlogger.exit();
    }

    /**
     * @return all Ip (4&6) available on this host
     * @throws DevFailed
     */
    private Iterable<String> getIpAddresses() throws DevFailed {
        List<String> result;
        if (OAI_ADDR != null && !OAI_ADDR.isEmpty()) {
            result = new ArrayList<>(1);
            result.add(OAI_ADDR);
        } else {
            try {
                Iterable<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

                result = new ArrayList<>();

                Predicate<NetworkInterface> isLoopback = new Predicate<NetworkInterface>() {
                    @Override
                    public boolean apply(NetworkInterface networkInterface) {
                        try {
                            return !networkInterface.isLoopback();
                        } catch (SocketException e) {
                            logger.warn("Ignoring NetworkInterface({}) due to an exception: {}", networkInterface.getName(), e);
                            return false;
                        }
                    }
                };

                Function<InterfaceAddress, String> interfaceAddressToString = new Function<InterfaceAddress, String>() {
                    @Override
                    public String apply(InterfaceAddress interfaceAddress) {
                        return interfaceAddress.getAddress().getHostAddress();
                    }
                };

                Iterable<NetworkInterface> filteredNICs = Iterables.filter(networkInterfaces, isLoopback);
                for (NetworkInterface nic : filteredNICs) {
                    result.addAll(
                            Lists.transform(nic.getInterfaceAddresses(), interfaceAddressToString)
                    );
                }
            } catch (SocketException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        return result;
    }


    /**
     * Binds given socket types to the list of addresses
     *
     * @param socket
     * @param ipAddresses
     * @param endpoints
     * @param socketType
     */
    private void bindEndpoints(ZMQ.Socket socket, Iterable<String> ipAddresses, List<String> endpoints, SocketType socketType) {
        xlogger.entry(ipAddresses, endpoints, socketType);
        for (String ipAddress : ipAddresses) {
            final StringBuilder endpoint = new StringBuilder("tcp://").append(ipAddress);
            int port = socket.bindToRandomPort(endpoint.toString());
            endpoint.append(":").append(port);
            endpoints.add(endpoint.toString());
            logger.debug("bind ZMQ socket {} for {}", endpoint, socketType);
        }
        xlogger.exit();
    }

    private ZMQ.Socket createSocket() {
        final ZMQ.Socket socket = context.createSocket(org.zeromq.SocketType.PUB);
        socket.setLinger(0);
        socket.setReconnectIVL(-1);
        return socket;
    }

    private ZMQ.Socket createEventSocket() {
        final ZMQ.Socket socket = context.createSocket(org.zeromq.SocketType.PUB);
        socket.setLinger(0);
        socket.setReconnectIVL(-1);
        socket.setSndHWM(serverHWM);
        logger.debug("HWM has been set to {}", socket.getSndHWM());
        return socket;
    }


    /**
     * Search the specified EventImpl object
     *
     * @param fullName specified EventImpl name.
     * @return the specified EventImpl object if found, otherwise returns null.
     */
    private EventImpl getEventImpl(final String fullName) {
        if (!isInitialized.get()) {
            return null;
        }

        // Check if subscribed
        EventImpl eventImpl = eventImplMap.get(fullName);

        // Check if subscription is out of time
        if (eventImpl != null && !eventImpl.isStillSubscribed()) {
            logger.debug("{} not subscribed any more", fullName);
            // System.out.println(fullName + "Not Subscribed any more");
            eventImplMap.remove(fullName);

            // if no subscribers, close sockets
            if (eventImplMap.isEmpty()) {
                logger.debug("no subscribers on server, closing resources");
                close();
            }
            eventImpl = null;
        }

        return eventImpl;
    }

    public boolean hasSubscriber(final String deviceName) {
        boolean hasSubscriber = false;
        for (final String eventName : eventImplMap.keySet()) {
            if (eventName.toLowerCase(Locale.ENGLISH).contains(deviceName.toLowerCase(Locale.ENGLISH))) {
                hasSubscriber = true;
                break;
            }
        }
        return hasSubscriber;
    }

    /**
     * Close all zmq resources
     */
    public void close() {
        xlogger.entry();
        logger.debug("closing all event resources");
        synchronized (isInitialized) {
            if (heartBeatExecutor != null) {
                heartBeatExecutor.shutdown();
                try {
                    heartBeatExecutor.awaitTermination(1, TimeUnit.SECONDS);
                } catch (final InterruptedException e) {
                    logger.error("could not stop event hearbeat");
                    Thread.currentThread().interrupt();
                }
                heartBeatExecutor = null;
            }

            if (context != null) {
                // close all open sockets
                context.destroy();
            }
            eventImplMap.clear();

            isInitialized.set(false);
        }
        logger.debug("all event resources closed");
        xlogger.exit();
    }

    /**
     * returns the connection parameters for specified event.
     */
    public DevVarLongStringArray getInfo() {
        // Build the connection parameters object
        final DevVarLongStringArray longStringArray = new DevVarLongStringArray();
        // longStringArray.lvalue = new int[0];
        longStringArray.lvalue = new int[]{EventConstants.TANGO_RELEASE, DeviceImpl.SERVER_VERSION, clientHWN, 0, 0,
                EventConstants.ZMQ_RELEASE};
        if (heartbeatEndpoints.isEmpty() || eventEndpoints.isEmpty()) {
            longStringArray.svalue = new String[]{"No ZMQ event yet !"};
        } else {
            longStringArray.svalue = endpointsAndEventsAsStringArray("");
        }
        return longStringArray;

    }

    private String[] endpointsAndEventsAsStringArray(String eventName) {
        List<String> svalue = new ArrayList<>(heartbeatEndpoints.size() + eventEndpoints.size());
        for (int i = 0, size = heartbeatEndpoints.size(); i < size; ++i) {
            svalue.add(Iterables.get(heartbeatEndpoints, i));
            svalue.add(Iterables.get(eventEndpoints, i));
        }
        svalue.add(eventName);
        // XXX: The Tango client API is inconsistent and asks for a truncated hearbeat name ( remove ".heartbeat")
        svalue.add(StringUtils.removeEnd(heartbeatName, EventUtilities.HEARTBEAT));
        return svalue.toArray(new String[svalue.size()]);
    }

    /**
     * Initialize ZMQ event system if not already done,
     * subscribe to the specified event end
     * returns the connection parameters for specified event.
     *
     * @param deviceName The specified event device name
     * @param pipe       The specified event pipe
     * @return the connection parameters for specified event.
     */
    public DevVarLongStringArray subscribe(final String deviceName, final PipeImpl pipe) throws DevFailed {
        xlogger.entry();
        // If first time start the ZMQ management
        synchronized (isInitialized) {
            if (!isInitialized.get()) {
                initialize();
            }
        }

        // check if event is already subscribed
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipe.getName());
        EventImpl eventImpl = eventImplMap.get(fullName);
        if (eventImpl == null) {
            // If not already manage, create EventImpl object and add it to the map
            eventImpl = new EventImpl(pipe, DeviceImpl.SERVER_VERSION, fullName);
            eventImplMap.put(fullName, eventImpl);
        } else {
            eventImpl.updateSubscribeTime();
        }

        return buildConnectionParameters(fullName);
    }

    /**
     * Initialize ZMQ event system if not already done,
     * subscribe to the specified event end
     * returns the connection parameters for specified event.
     *
     * @param deviceName The specified event device name
     * @param attribute  The specified event attribute
     * @param eventType  The specified event type
     * @return the connection parameters for specified event.
     */
    public DevVarLongStringArray subscribe(final String deviceName, final AttributeImpl attribute,
                                           final EventType eventType, final int idlVersion) throws DevFailed {
        xlogger.entry();
        // If first time start the ZMQ management
        synchronized (isInitialized) {
            if (!isInitialized.get()) {
                initialize();
            }
        }

        // check if event is already subscribed
        final String fullName = EventUtilities.buildEventName(deviceName, attribute.getName(), eventType, idlVersion);
        EventImpl eventImpl = eventImplMap.get(fullName);
        if (eventImpl == null) {
            // special case for forwarded attribute, subscribe to root attribute
            if (attribute.getBehavior() instanceof ForwardedAttribute) {
                final ForwardedAttribute fwdAttr = (ForwardedAttribute) attribute.getBehavior();
                fwdAttr.subscribe(eventType);
            }
            // If not already manage, create EventImpl object and add it to the map
            eventImpl = new EventImpl(attribute, eventType, idlVersion, fullName);
            eventImplMap.put(fullName, eventImpl);
        } else {
            eventImpl.updateSubscribeTime();
        }
        logger.debug("starting event {}", fullName);
        return buildConnectionParameters(fullName);
    }

    /**
     * Initialize ZMQ event system if not already done,
     * subscribe to the interface change event end
     * returns the connection parameters.
     *
     * @param deviceName The specified event device name
     * @return the connection parameters.
     */
    public DevVarLongStringArray subscribe(final String deviceName) throws DevFailed {
        xlogger.entry();
        // If first time start the ZMQ management
        synchronized (isInitialized) {
            if (!isInitialized.get()) {
                initialize();
            }
        }

        // check if event is already subscribed
        final String fullName = EventUtilities.buildDeviceEventName(deviceName, EventType.INTERFACE_CHANGE_EVENT);
        EventImpl eventImpl = eventImplMap.get(fullName);
        if (eventImpl == null) {
            // If not already manage, create EventImpl object and add it to the map
            eventImpl = new EventImpl(DeviceImpl.SERVER_VERSION, fullName);
            eventImplMap.put(fullName, eventImpl);
        } else {
            eventImpl.updateSubscribeTime();
        }

        return buildConnectionParameters(fullName);
    }

    private DevVarLongStringArray buildConnectionParameters(final String fullName) {
        // Build the connection parameters object
        final DevVarLongStringArray longStringArray = new DevVarLongStringArray();
        longStringArray.lvalue = new int[]{EventConstants.TANGO_RELEASE, DeviceImpl.SERVER_VERSION, clientHWN, 0, 0,
                EventConstants.ZMQ_RELEASE};
        longStringArray.svalue = endpointsAndEventsAsStringArray(fullName);
        logger.debug("event registered for {}", fullName);
        return longStringArray;
    }

    /**
     * Check if the event must be sent and fire it if must be done
     *
     * @param attributeName specified event attribute
     * @param devFailed     the attribute failed error to be sent as event
     * @throws fr.esrf.Tango.DevFailed
     * @throws DevFailed
     */
    public void pushAttributeErrorEvent(final String deviceName, final String attributeName, final DevFailed devFailed, boolean isPushedFromPolling)
            throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.values()) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                eventImpl.pushDevFailedEvent(devFailed, eventSocket, isPushedFromPolling);
            }
        }
        xlogger.exit();
    }

    /**
     * Check if the event must be sent and fire it if must be done
     *
     * @param attributeName specified event attribute
     * @throws DevFailed
     */
    public void pushAttributeValueEvent(final String deviceName, final String attributeName, boolean isPushedFromPolling) throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.getEventAttrValueTypeList()) {
            pushAttributeValueEventIdlLoop(deviceName, attributeName, eventType, isPushedFromPolling);
        }
        xlogger.exit();
    }

    private void pushAttributeValueEventIdlLoop(String deviceName, String attributeName, EventType eventType, boolean isPushedFromPolling) throws DevFailed {
        for (int idl = MINIMUM_IDL_VERSION; idl <= DeviceImpl.SERVER_VERSION; idl++) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType, idl);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                eventImpl.pushAttributeValueEvent(eventSocket, isPushedFromPolling);
            }
        }
        xlogger.exit();
    }

    /**
     * fire event
     *
     * @param deviceName    Specified event device
     * @param attributeName specified event attribute name
     * @param eventType     specified event type.
     * @throws DevFailed
     */
    public void pushAttributeValueEvent(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        xlogger.entry();
        pushAttributeValueEventIdlLoop(deviceName, attributeName, eventType, false);
        xlogger.exit();
    }

    /**
     * fire event with AttDataReady
     *
     * @param deviceName    Specified event device
     * @param attributeName specified event attribute name
     * @param counter       a counter value
     * @throws DevFailed
     */
    public void pushAttributeDataReadyEvent(final String deviceName, final String attributeName, final int counter)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, EventType.DATA_READY_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushAttributeDataReadyEvent(counter, eventSocket);
        }
        xlogger.exit();
    }

    public void pushAttributeConfigEvent(final String deviceName, final String attributeName) throws DevFailed {
        xlogger.entry();
        for (int idl = 4; idl <= DeviceImpl.SERVER_VERSION; idl++) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, EventType.ATT_CONF_EVENT,
                    idl);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                eventImpl.pushAttributeConfigEvent(eventSocket);
            }
        }
        xlogger.exit();
    }

    public void pushInterfaceChangedEvent(final String deviceName, final DevIntrChange deviceInterface)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildDeviceEventName(deviceName, EventType.INTERFACE_CHANGE_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushInterfaceChangeEvent(deviceInterface, eventSocket);

        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final PipeValue blob) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushPipeEvent(
                    new DevPipeData(pipeName, TangoIDLUtil.getTime(blob.getTime()), blob.getValue()
                            .getDevPipeBlobObject()), eventSocket);
        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushDevFailedEvent(devFailed, eventSocket, false);
        }
        xlogger.exit();
    }


    public void pushAttributeValueIDL5Event(final String deviceName, final String attributeName, AttributeValue_5 value, EventType evtType) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, evtType);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushAttributeIDL5Event(value, eventSocket);
        }
        xlogger.exit();
    }

    public void pushAttributeConfigIDL5Event(final String deviceName, final String attributeName, AttributeConfig_5 config) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, EventType.ATT_CONF_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushAttributeConfigIDL5Event(config, eventSocket);
        }
        xlogger.exit();
    }

    private enum SocketType {
        HEARTBEAT, EVENTS
    }


    /**
     * This class is a thread to send a heartbeat
     */
    class HeartbeatThread implements Runnable {

        private final String heartbeatName;

        HeartbeatThread(final String heartbeatName) {
            this.heartbeatName = heartbeatName;
        }

        @Override
        public void run() {
            xlogger.entry();
            if (eventImplMap.isEmpty()) return;
            // Fire heartbeat
            try {
                EventUtilities.sendHeartbeat(heartbeatSocket, heartbeatName);
            } catch (final DevFailed e) {
                DevFailedUtils.logDevFailed(e, logger);
            }
            logger.debug("Heartbeat sent for {}", heartbeatName);
            xlogger.exit();
        }
    }

}
