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
package org.tango.server.events;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevIntrChange;
import fr.esrf.Tango.DevPipeData;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.HostInfo;
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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Set of ZMQ low level utilities
 *
 * @author verdier
 */
public final class EventManager {

    public static final int MINIMUM_IDL_VERSION = 4;
    public static final String IDL_REGEX = "idl[0-9]_[a-z]*";
    public static final String IDL_LATEST = "idl" + DeviceImpl.SERVER_VERSION + "_";
    private static final EventManager INSTANCE = new EventManager();
    private final static ScheduledExecutorService heartBeatExecutor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(r, "Event HeartBeat");
        }
    });
    private final Logger logger = LoggerFactory.getLogger(EventManager.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventManager.class);
    //TODO not thread safe
    private final Map<String, EventImpl> eventImplMap = new HashMap<>();
    //TODO concurrency
    private final Map<String, ZMQ.Socket> heartbeatEndpoints = new LinkedHashMap<>();
    private final Map<String, ZMQ.Socket> eventEndpoints = new LinkedHashMap<>();
    private ZContext context;
    private int serverHWM = EventConstants.HWM_DEFAULT;
    private int clientHWN = EventConstants.HWM_DEFAULT;
    private boolean isInitialized = false;

    private EventManager() {
        // Check the         serverHWM
        final String env = System.getenv("TANGO_DS_EVENT_BUFFER_HWM");
        try {
            if (env != null) {
                serverHWM = Integer.parseInt(env);
            }
        } catch (final NumberFormatException e) {
            logger.error("system env TANGO_DS_EVENT_BUFFER_HWM is not a number: {} ", env);
        }

        // Check the High Water Mark value from Control System property
        try {
            String value = DatabaseFactory.getDatabase().getFreeProperty("CtrlSystem", "EventBufferHwm");
            clientHWN = Integer.parseInt(value);
        } catch (final DevFailed e) {
            DevFailedUtils.logDevFailed(e, logger);
        } catch (final NumberFormatException e) {
            logger.error("CtrlSystem/EventBufferHwm property is not a number: {}", e.getMessage());
        }
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
    }

    private void initialize() throws DevFailed {

        xlogger.entry();
        logger.debug("client IP address is {}", ServerRequestInterceptor.getInstance().getClientIPAddress());

        try {
            context = new ZContext();
            logger.info("ZMQ ({}) SERVER event system started", ZMQ.getVersionString());
        } catch (final Throwable e) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.EVENT_NOT_AVAILABLE,
                    "ZMQ classes not found. Event system is not available: " + e.getMessage());
        }

        final String adminDeviceName = ServerManager.getInstance().getAdminDeviceName();

        Iterable<String> ipAddress = HostInfo.getIpAddresses();
        Iterable<String> ip4Address = Iterables.filter(ipAddress, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.split("\\.").length == 4;
            }
        });
        // Get the free ports and build endpoints
        bindEndpoints(createSocket(), ip4Address, heartbeatEndpoints, SocketType.HEARTBEAT);
        bindEndpoints(createSocket(), ip4Address, eventEndpoints, SocketType.EVENTS);

        // // TODO : without database?
        final String heartbeatName = EventUtilities.buildHeartBeatEventName(adminDeviceName);
        // Start the heartbeat thread
        heartBeatExecutor.scheduleAtFixedRate(new HeartbeatThread(heartbeatName), 0,
                EventConstants.EVENT_HEARTBEAT_PERIOD, TimeUnit.MILLISECONDS);
        isInitialized = true;
        xlogger.exit();
    }

    /**
     * Returns next port to connect the heartbeatSocket or eventSocket
     *
     * @throws DevFailed if no free port found
     */
    private int getNextAvailablePort() throws DevFailed {

        // find an available port
        ServerSocket ss1 = null;
        int port = 0;
        try {
            ss1 = new ServerSocket(0);
            ss1.setReuseAddress(true);
            port = ss1.getLocalPort();
        } catch (final SocketException e) {
            DevFailedUtils.throwDevFailed(e);
        } catch (final IOException e) {
            DevFailedUtils.throwDevFailed(e);
        } finally {
            if (ss1 != null) {
                try {
                    ss1.close();
                } catch (final IOException e) {
                    DevFailedUtils.throwDevFailed(e);
                }
            }
        }
        return port;
    }

    /**
     * Binds given socket types to the list of addresses
     *
     * @param socket
     * @param ipAddresses
     * @param heartbeatEndpoints
     * @param socketType
     */
    public void bindEndpoints(ZMQ.Socket socket, Iterable<String> ipAddresses, Map<String, ZMQ.Socket> heartbeatEndpoints, SocketType socketType) {
        xlogger.entry(ipAddresses, heartbeatEndpoints, socketType);


        for (String ipAddress : ipAddresses) {
            final StringBuilder endpoint = new StringBuilder("tcp://").append(ipAddress).append(":*");


            int port = socket.bind(endpoint.toString());

            //replace * with actual port
            endpoint.deleteCharAt(endpoint.length() - 1).append(port);
            heartbeatEndpoints.put(endpoint.toString(), socket);
            logger.debug("bind ZMQ socket {} for {}", endpoint.toString(), socketType);
        }

        xlogger.exit();
    }

    private ZMQ.Socket createSocket() {
        final ZMQ.Socket socket = context.createSocket(ZMQ.PUB);
        socket.setLinger(0);
        socket.setReconnectIVL(-1);
        return socket;
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
                logger.error("could not stop event hearbeat");
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
        longStringArray.lvalue = new int[] { EventConstants.TANGO_RELEASE, DeviceImpl.SERVER_VERSION, clientHWN, 0, 0,
                EventConstants.ZMQ_RELEASE };
        if (heartbeatEndpoints.isEmpty() || eventEndpoints.isEmpty()) {
            longStringArray.svalue = new String[] { "No ZMQ event yet !" };
        } else {
            longStringArray.svalue = endpointsAsStringArray();
        }
        return longStringArray;

    }

    private String[] endpointsAsStringArray() {
        List<String> svalue = new ArrayList<>(heartbeatEndpoints.size() + eventEndpoints.size());

        for (int i = 0, size = heartbeatEndpoints.size(); i < size; ++i) {
            svalue.add(Iterables.get(heartbeatEndpoints.keySet(), i));
            svalue.add(Iterables.get(eventEndpoints.keySet(), i));
        }

        return svalue.toArray(new String[svalue.size()]);
    }

    /**
     * Initialize ZMQ event system if not already done,
     * subscribe to the specified event end
     * returns the connection parameters for specified event.
     *
     * @param deviceName The specified event device name
     * @param pipe The specified event pipe
     * @return the connection parameters for specified event.
     */
    public DevVarLongStringArray subcribe(final String deviceName, final PipeImpl pipe) throws DevFailed {
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
            eventImpl = new EventImpl(pipe, DeviceImpl.SERVER_VERSION);
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
     * @param attribute The specified event attribute
     * @param eventType The specified event type
     * @return the connection parameters for specified event.
     */
    public DevVarLongStringArray subcribe(final String deviceName, final AttributeImpl attribute,
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
            eventImpl = new EventImpl(attribute, eventType, idlVersion);
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
    public DevVarLongStringArray subcribe(final String deviceName) throws DevFailed {
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
            eventImpl = new EventImpl(DeviceImpl.SERVER_VERSION);
            eventImplMap.put(fullName, eventImpl);
        } else {
            eventImpl.updateSubscribeTime();
        }

        return buildConnectionParameters(fullName);
    }

    private DevVarLongStringArray buildConnectionParameters(final String fullName) {
        // Build the connection parameters object
        final DevVarLongStringArray longStringArray = new DevVarLongStringArray();
        longStringArray.lvalue = new int[] { EventConstants.TANGO_RELEASE, DeviceImpl.SERVER_VERSION, clientHWN, 0, 0,
                EventConstants.ZMQ_RELEASE };
        longStringArray.svalue = endpointsAsStringArray();
        logger.debug("event registered for {}", fullName);
        return longStringArray;
    }

    /**
     * Check if the event must be sent and fire it if must be done
     *
     * @param attributeName specified event attribute
     * @param devFailed the attribute failed error to be sent as event
     * @throws fr.esrf.Tango.DevFailed
     * @throws DevFailed
     */
    public void pushAttributeEvent(final String deviceName, final String attributeName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.values()) {
            final String fullName5 = EventUtilities.buildEventName(deviceName, attributeName, eventType);
            final EventImpl eventImpl5 = getEventImpl(fullName5);
            if (eventImpl5 != null) {
                for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                    eventImpl5.pushEvent(eventSocket, fullName5, devFailed);
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
    public void pushAttributeEvent(final String deviceName, final String attributeName) throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.values()) {
            pushAttributeEvent(deviceName, attributeName, eventType);
        }
        xlogger.exit();
    }

    /**
     * fire event
     *
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param eventType specified event type.
     * @throws DevFailed
     */
    public void pushAttributeEvent(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        xlogger.entry();
        for (int idl = MINIMUM_IDL_VERSION; idl <= DeviceImpl.SERVER_VERSION; idl++) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType, idl);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                    eventImpl.pushAttributeEvent(eventSocket, fullName);
                }
            }
        }
        xlogger.exit();
    }

    /**
     * fire event without check
     *
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param eventType specified event type.
     * @throws DevFailed
     */
    public void forceAttributePushEvent(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                eventImpl.pushAttributeEvent(eventSocket, fullName);
            }
        }
        xlogger.exit();
    }

    /**
     * fire event with AttDataReady
     *
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param counter a counter value
     * @throws DevFailed
     */
    public void pushAttributeDataReadyEvent(final String deviceName, final String attributeName, final int counter)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, EventType.DATA_READY_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                eventImpl.pushAttributeDataReadyEvent(eventSocket, fullName, counter);
            }
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
                for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                    eventImpl.pushAttributeConfigEvent(eventSocket, fullName);
                }
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
            for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                eventImpl.pushInterfaceChangeEvent(eventSocket, fullName, deviceInterface);
            }
        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final PipeValue blob) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                eventImpl.pushPipeEvent(eventSocket, fullName,
                        new DevPipeData(pipeName, TangoIDLUtil.getTime(blob.getTime()), blob.getValue()
                                .getDevPipeBlobObject()));
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
            for (ZMQ.Socket eventSocket : eventEndpoints.values()) {
                eventImpl.pushEvent(eventSocket, fullName, devFailed);
            }
        }
        xlogger.exit();
    }

    private enum SocketType {
        HEARTBEAT, EVENTS
    }

    // ===================================================

    /**
     * This class is a thread to send a heartbeat
     */
    // ===================================================
    class HeartbeatThread implements Runnable {

        private final String heartbeatName;

        HeartbeatThread(final String heartbeatName) {
            this.heartbeatName = heartbeatName;
        }

        @Override
        public void run() {
            xlogger.entry();
            if (eventImplMap.isEmpty()) return;
            for (ZMQ.Socket heartbeatSocket : heartbeatEndpoints.values()) {
                // Fire heartbeat
                try {
                    heartbeatSocket.sendMore(heartbeatName);
                    heartbeatSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
                    heartbeatSocket.send(EventUtilities.marshall(0, false), 0);
                    // heartbeatSocket.send("0");
                } catch (final DevFailed e) {
                    DevFailedUtils.logDevFailed(e, logger);
                }
                logger.debug("Heartbeat sent for {}", heartbeatName);
            }
            xlogger.exit();
        }
    }

}
