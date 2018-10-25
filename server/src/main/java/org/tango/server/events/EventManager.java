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
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.esrf.Tango.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.orb.ServerRequestInterceptor;
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
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.tango.orb.ORBManager.OAI_ADDR;

/**
 * Set of ZMQ low level utilities
 *
 * @author verdier
 */
public final class EventManager {
    private final Logger logger = LoggerFactory.getLogger(EventManager.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventManager.class);


    public static final int MINIMUM_IDL_VERSION = 4;
    public static final String IDL_REGEX = "idl[0-9]_[a-z]*";
    public static final String IDL_LATEST = "idl" + DeviceImpl.SERVER_VERSION + "_";
    private static final EventManager INSTANCE = new EventManager();

    private final Map<String, EventImpl> eventImplMap = new HashMap<String, EventImpl>();
    private ZContext context;
    private final ScheduledExecutorService heartBeatExecutor  = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(r, "Event HeartBeat");
        }
    });
    private final int serverHWM = getServerHwm();
    private final int clientHWN = getClientHwm();
    private final Map<String, ZMQ.Socket> heartbeatSockets = new HashMap<>();
    private final Map<String, ZMQ.Socket> eventSockets = new HashMap<>();

    private volatile boolean isInitialized;

    private final java.util.function.Function<EventImpl, Void> pushAttributeValueEvent = (eventImpl) -> {
        for (ZMQ.Socket eventSocket : eventSockets.values()) {
            try {
                eventImpl.pushAttributeValueEvent(eventSocket);
            } catch (DevFailed devFailed) {
                logger.error("Failed to pushAttributeValueEvent");
                DevFailedUtils.logDevFailed(devFailed, logger);
            }
        }
        return null;
    };

    private int getServerHwm(){
        // Check the High Water Mark value from environment
        final String env = System.getenv("TANGO_DS_EVENT_BUFFER_HWM");
        try {
            if (env != null) {
                return Integer.parseInt(env);
            }
        } catch (final NumberFormatException e) {
            logger.warn("System env.TANGO_DS_EVENT_BUFFER_HWM is not a number: {} ", env);

        }
        return EventConstants.HWM_DEFAULT;
    }

    private int getClientHwm(){
        // Check the High Water Mark value from Control System property
        String value = "";
        try {
            value = DatabaseFactory.getDatabase().getFreeProperty("CtrlSystem", "EventBufferHwm");
            return Integer.parseInt(value);
        } catch (final DevFailed e) {
            logger.warn("Failed to get free property CtrlSystem/EventBufferHwm from the database");
            DevFailedUtils.logDevFailed(e, logger);
        } catch (final NumberFormatException e) {
            logger.warn("CtrlSystem/EventBufferHwm property is not a number: {} ", value);
        }
        return EventConstants.HWM_DEFAULT;
    }

    protected EventManager() {
        isInitialized = false;
    }

    //TODO get rid off this non-sense
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
    }

    protected void initialize() throws DevFailed {

        xlogger.entry();
        logger.debug("client IP address is {}", ServerRequestInterceptor.getInstance().getClientIPAddress());

        try {
            context = new ZContext();
            logger.info("ZMQ ({}) SERVER event system started", EventUtilities.getZmqVersion());
        } catch (final Throwable e) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.EVENT_NOT_AVAILABLE,
                    "ZMQ classes not found. Event system is not available: " + e.getMessage());
        }

        final String adminDeviceName = ServerManager.getInstance().getAdminDeviceName();

        // Get the free ports and build endpoints
        Iterable<String> ipAddresses = getIp4Addresses();

        bindEndpoints(createSocket(), ipAddresses, heartbeatSockets, SocketType.HEARTBEAT);
        bindEndpoints(createEventSocket(), ipAddresses, eventSockets, SocketType.EVENTS);

        // Start the heartbeat thread
        // // TODO : without database?
        final String heartbeatName = EventUtilities.buildHeartBeatEventName(adminDeviceName);
        heartBeatExecutor.scheduleAtFixedRate(new HeartbeatThread(heartbeatName), 0,
                EventConstants.EVENT_HEARTBEAT_PERIOD, TimeUnit.MILLISECONDS);
        isInitialized = true;
        xlogger.exit();
    }

    private void bindEndpoints(ZMQ.Socket socket, Iterable<String> ipAddresses, Map<String, ZMQ.Socket> sockets, SocketType type) {
        xlogger.entry();

        for (String ipAddress : ipAddresses) {
            final StringBuilder endpoint = new StringBuilder("tcp://").append(ipAddress).append(":*");

            int port = socket.bind(endpoint.toString());

            //replace * with actual port
            endpoint.deleteCharAt(endpoint.length() - 1).append(port);
            sockets.put(endpoint.toString(), socket);

            logger.debug("bind ZMQ socket {} for {}", endpoint, type);
        }

        xlogger.exit();
    }


    private ZMQ.Socket createSocket() {
        final ZMQ.Socket socket = context.createSocket(ZMQ.PUB);
        socket.setLinger(0);
        socket.setReconnectIVL(-1);
        return socket;
    }

    private ZMQ.Socket createEventSocket() {
        final ZMQ.Socket socket = context.createSocket(ZMQ.PUB);
        socket.setLinger(0);
        socket.setReconnectIVL(-1);
        socket.setSndHWM(serverHWM);
        return socket;
    }

    protected Iterable<String> getIp4Addresses() throws DevFailed {
        if (OAI_ADDR != null && !OAI_ADDR.isEmpty()) {
            return Lists.newArrayList(OAI_ADDR);
        } else {
            try {
                Iterable<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

                java.util.function.Predicate<NetworkInterface> filter = networkInterface -> {
                    try {
                        return !networkInterface.isLoopback() && !networkInterface.isVirtual() && networkInterface.isUp();
                    } catch (SocketException e) {
                        logger.warn("Ignoring NetworkInterface({}) due to an exception: {}", networkInterface.getName(), e);
                        return false;
                    }
                };

                Function<InterfaceAddress, String> interfaceAddressToString = interfaceAddress -> interfaceAddress.getAddress().getHostAddress();

                Iterable<NetworkInterface> filteredNICs = Iterables.filter(networkInterfaces, filter::test);

                List<String> result = Lists.newArrayList();
                for (NetworkInterface nic : filteredNICs) {
                    result.addAll(
                            Collections2.filter(
                                    nic.getInterfaceAddresses().stream().map(interfaceAddressToString::apply).collect(Collectors.toList()),
                                    s -> s.split("\\.").length == 4)
                    );
                }
                return result;
            } catch (SocketException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
    }

    /**
     * Search the specified EventImpl object
     *
     * @param fullName specified EventImpl name.
     * @return the specified EventImpl object if found, otherwise returns null.
     */
    private EventImpl getEventImpl(final String fullName) {
        if (!isInitialized) {
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

        if (heartBeatExecutor != null) {
            heartBeatExecutor.shutdown();
            try {
                heartBeatExecutor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (context != null) {
            // close all open sockets
            context.destroy();
        }
        eventImplMap.clear();

        isInitialized = false;
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
        if (heartbeatSockets.isEmpty() || eventSockets.isEmpty()) {
            longStringArray.svalue = new String[]{"No ZMQ event yet !"};
        } else {
            longStringArray.svalue = getEndpoints();
        }
        return longStringArray;

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
        if (!isInitialized) {
            initialize();
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
        if (!isInitialized) {
            initialize();
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
        if (!isInitialized) {
            initialize();
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
        longStringArray.svalue = getEndpoints();;
        logger.debug("event registered for {}", fullName);
        return longStringArray;
    }

    private String[] getEndpoints() {
        return Iterables.toArray(
                Iterables.concat(heartbeatSockets.keySet(), eventSockets.keySet()), String.class);
    }

    /**
     * Check if the event must be sent and fire it if must be done
     *
     * @param attributeName specified event attribute
     * @param devFailed     the attribute failed error to be sent as event
     * @throws DevFailed
     */
    public void pushAttributeErrorEvent(final String deviceName, final String attributeName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.values()) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                for (ZMQ.Socket eventSocket : eventSockets.values()) {
                    eventImpl.pushDevFailedEvent(devFailed, eventSocket);
                }
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
    public void pushAttributeValueEvent(final String deviceName, final String attributeName) throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.getEventAttrValueTypeList()) {
            forEachIdlVersionDo(deviceName, attributeName, eventType, pushAttributeValueEvent);
        }
        xlogger.exit();
    }

    private void forEachIdlVersionDo(String deviceName, String attributeName, EventType eventType, java.util.function.Function<EventImpl, Void> action) throws DevFailed {
        for (int idl = MINIMUM_IDL_VERSION; idl <= DeviceImpl.SERVER_VERSION; idl++) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType, idl);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                action.apply(eventImpl);
            }
        }
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
        forEachIdlVersionDo(deviceName, attributeName, eventType, pushAttributeValueEvent);
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
            for (ZMQ.Socket eventSocket : eventSockets.values()) {
                eventImpl.pushAttributeDataReadyEvent(counter, eventSocket);
            }
        }
        xlogger.exit();
    }

    public void pushAttributeConfigEvent(final String deviceName, final String attributeName) throws DevFailed {
        xlogger.entry();
        forEachIdlVersionDo(deviceName, attributeName, EventType.ATT_CONF_EVENT, (eventImpl -> {
            for (ZMQ.Socket eventSocket : eventSockets.values()) {
                try {
                    eventImpl.pushAttributeConfigEvent(eventSocket);
                } catch (DevFailed devFailed) {
                    logger.error("Failed to pushAttributeConfigEvent");
                    DevFailedUtils.logDevFailed(devFailed, logger);
                }
            }
            return null;
        }));
        xlogger.exit();
    }

    public void pushInterfaceChangedEvent(final String deviceName, final DevIntrChange deviceInterface)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildDeviceEventName(deviceName, EventType.INTERFACE_CHANGE_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for(ZMQ.Socket eventSocket : eventSockets.values()) {
                eventImpl.pushInterfaceChangeEvent(deviceInterface, eventSocket);
            }
        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final PipeValue blob) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for(ZMQ.Socket eventSocket : eventSockets.values()) {
                eventImpl.pushPipeEvent(
                        new DevPipeData(pipeName, TangoIDLUtil.getTime(blob.getTime()), blob.getValue()
                                .getDevPipeBlobObject()), eventSocket);
            }
        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for (ZMQ.Socket eventSocket : eventSockets.values()) {
                eventImpl.pushDevFailedEvent(devFailed, eventSocket);
            }
        }
        xlogger.exit();
    }


    public void pushAttributeValueIDL5Event(final String deviceName, final String attributeName, AttributeValue_5 value, EventType evtType) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, evtType);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for(ZMQ.Socket eventSocket : eventSockets.values()) {
                eventImpl.pushAttributeIDL5Event(value, eventSocket);
            }
        }
        xlogger.exit();
    }

    public void pushAttributeConfigIDL5Event(final String deviceName, final String attributeName, AttributeConfig_5 config) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, EventType.ATT_CONF_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for (ZMQ.Socket eventSocket : eventSockets.values()) {
                eventImpl.pushAttributeConfigIDL5Event(config, eventSocket);
            }
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
            if (!eventImplMap.isEmpty()) {
                // Fire heartbeat
                try {
                    for (ZMQ.Socket heartbeatSocket : heartbeatSockets.values()) {
                        EventUtilities.sendHeartbeat(heartbeatSocket, heartbeatName);
                    }
                } catch (final DevFailed e) {
                    DevFailedUtils.logDevFailed(e, logger);
                }
                logger.debug("Heartbeat sent for {}", heartbeatName);
                // System.out.println("Heartbeat sent for " + heartbeatName);
            }
            xlogger.exit();
        }
    }

}
