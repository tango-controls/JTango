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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.orb.ORBManager;
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

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevIntrChange;
import fr.esrf.Tango.DevPipeData;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.HostInfo;

/**
 * Set of ZMQ low level utilities
 *
 * @author verdier
 */
public final class EventManager {

    private static ZContext context;
    private static final EventManager INSTANCE = new EventManager();
    public static final int MINIMUM_IDL_VERSION = 4;
    public static final String IDL_REGEX = "idl[0-9]_[a-z]*";
    public static final String IDL_LATEST = "idl" + DeviceImpl.SERVER_VERSION + "_";
    private ZMQ.Socket heartbeatSocket;
    private ZMQ.Socket eventSocket;
    private boolean isInitialized = false;
    private String heartbeatEndpoint = null;
    private String eventEndpoint = null;
    private final Logger logger = LoggerFactory.getLogger(EventManager.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventManager.class);

    private final Map<String, EventImpl> eventImplMap = new HashMap<String, EventImpl>();
    private static ScheduledExecutorService heartBeatExecutor;

    private static int serverHWM;

    private static int clientHWN;

    private enum SocketType {
        HEARTBEAT, EVENTS
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    private EventManager() {
        serverHWM = EventConstants.HWM_DEFAULT;
        // Check the High Water Mark value from environment
        final String env = System.getenv("TANGO_DS_EVENT_BUFFER_HWM");
        try {
            if (env != null) {
                serverHWM = Integer.parseInt(env);
            }
        } catch (final NumberFormatException e) {
            logger.error("system env TANGO_DS_EVENT_BUFFER_HWM is not a number: {} ", env);
        }

        clientHWN = EventConstants.HWM_DEFAULT;
        // Check the High Water Mark value from Control System property
        String value = "";
        try {
            value = DatabaseFactory.getDatabase().getFreeProperty("CtrlSystem", "EventBufferHwm");
            clientHWN = Integer.parseInt(value);
        } catch (final DevFailed e) {
            DevFailedUtils.logDevFailed(e, logger);
        } catch (final NumberFormatException e) {
            logger.error("ControlSystem/EventBufferHwm property is not a number: {} ", value);
        }

        isInitialized = false;
    }

    private void initialize() throws DevFailed {

        xlogger.entry();
        logger.debug("client IP address is {}", ServerRequestInterceptor.getInstance().getClientIPAddress());

        try {
            context = new ZContext();
            logger.info("ZMQ ({}) SERVER event system started", EventUtilities.getZmqVersion());
        } catch (final Throwable e) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.EVENT_NOT_AVAILABLE,
                    "ZMQ classes not found. Event system is not available: " + e.getMessage());
        }

        final String adminDeviceName = ServerManager.getInstance().getAdminDeviceName();

        // Get the free ports and build endpoints
        setEndpoints(SocketType.HEARTBEAT);
        setEndpoints(SocketType.EVENTS);

        // Start the heartbeat thread
        heartBeatExecutor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(final Runnable r) {
                return new Thread(r, "Event HeartBeat");
            }
        });
        // // TODO : without database?
        final String heartbeatName = EventUtilities.buildHeartBeatEventName(adminDeviceName);
        heartBeatExecutor.scheduleAtFixedRate(new HeartbeatThread(heartbeatName), 0,
                EventConstants.EVENT_HEARTBEAT_PERIOD, TimeUnit.MILLISECONDS);
        isInitialized = true;
        xlogger.exit();
    }

    /**
     * Returns next port to connect the heartbeatSocket or eventSocket
     *
     * @throws fr.esrf.Tango.DevFailed if no free port found
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
     * Check next port to connect the heartbeatSocket or eventSocket
     *
     * @param socketType HEARTBEAT or EVENT
     * @throws fr.esrf.Tango.DevFailed if no free port found
     */
    private void setEndpoints(final SocketType socketType) throws DevFailed {
        xlogger.entry();
        String ipAddress;
        if (ORBManager.OAI_ADDR != null && !ORBManager.OAI_ADDR.isEmpty()) {
            ipAddress = ORBManager.OAI_ADDR;
        } else {
            ipAddress = HostInfo.getAddress();
        }

        final String endpoint = "tcp://" + ipAddress + ":" + getNextAvailablePort();
        final ZMQ.Socket socket = context.createSocket(ZMQ.PUB);
        socket.setLinger(0);
        socket.setReconnectIVL(-1);
        logger.debug("bind ZMQ socket {} for {}", endpoint, socketType);
        socket.bind(endpoint);

        switch (socketType) {
            case HEARTBEAT:
                heartbeatSocket = socket;
                heartbeatEndpoint = endpoint;
                break;
            case EVENTS:
                socket.setSndHWM(serverHWM);
                eventSocket = socket;
                eventEndpoint = endpoint;
                logger.debug("HWM has been set to {}", socket.getSndHWM());
                break;
        }

        xlogger.exit();
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
        if (heartbeatEndpoint == null || eventEndpoint == null) {
            longStringArray.svalue = new String[] { "No ZMQ event yet !" };
        } else {
            longStringArray.svalue = new String[] { heartbeatEndpoint, eventEndpoint };
        }
        return longStringArray;

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
        longStringArray.svalue = new String[] { heartbeatEndpoint, eventEndpoint };
        logger.debug("event registered for {}", fullName);
        return longStringArray;
    }

    /**
     * Check if the event must be sent and fire it if must be done
     *
     * @param attributeName specified event attribute
     * @param devFailed the attribute failed error to be sent as event
     * @throws fr.esrf.Tango.DevFailed
     */
    public void pushAttributeEvent(final String deviceName, final String attributeName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.values()) {
            final String fullName5 = EventUtilities.buildEventName(deviceName, attributeName, eventType);
            final EventImpl eventImpl5 = getEventImpl(fullName5);
            if (eventImpl5 != null) {
                eventImpl5.pushEvent(eventSocket, fullName5, devFailed);
            }
        }
        xlogger.exit();
    }

    /**
     * Check if the event must be sent and fire it if must be done
     *
     * @param attributeName specified event attribute
     * @throws fr.esrf.Tango.DevFailed
     */
    public void pushAttributeEvent(final String deviceName, final String attributeName) throws DevFailed {
        xlogger.entry();
        for (final EventType eventType : EventType.values()) {
            for (int idl = MINIMUM_IDL_VERSION; idl <= DeviceImpl.SERVER_VERSION; idl++) {
                final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType, idl);
                final EventImpl eventImpl = getEventImpl(fullName);
                if (eventImpl != null) {
                    eventImpl.pushAttributeEvent(eventSocket, fullName);
                }
            }
        }
        xlogger.exit();
    }

    /**
     * fire event
     *
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param eventType specified event type.
     * @throws fr.esrf.Tango.DevFailed
     */
    public void pushAttributeEvent(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        xlogger.entry();
        for (int idl = MINIMUM_IDL_VERSION; idl <= DeviceImpl.SERVER_VERSION; idl++) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType, idl);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                eventImpl.pushAttributeEvent(eventSocket, fullName);
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
     * @throws fr.esrf.Tango.DevFailed
     */
    public void forceAttributePushEvent(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventType);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.forcePushEvent(eventSocket, fullName);
        }
        xlogger.exit();
    }

    /**
     * fire event with AttDataReady
     *
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param counter a counter value
     * @throws fr.esrf.Tango.DevFailed
     */
    public void pushAttributeDataReadyEvent(final String deviceName, final String attributeName, final int counter)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, EventType.DATA_READY_EVENT);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushAttributeDataReadyEvent(eventSocket, fullName, counter);
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
                eventImpl.pushAttributeConfigEvent(eventSocket, fullName);
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
            eventImpl.pushInterfaceChangeEvent(eventSocket, fullName, deviceInterface);
        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final PipeValue blob) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushPipeEvent(eventSocket, fullName,
                    new DevPipeData(pipeName, TangoIDLUtil.getTime(blob.getTime()), blob.getValue()
                            .getDevPipeBlobObject()));
        }
        xlogger.exit();
    }

    public void pushPipeEvent(final String deviceName, final String pipeName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildPipeEventName(deviceName, pipeName);
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushEvent(eventSocket, fullName, devFailed);
        }
        xlogger.exit();
    }

    /**
     * Check if event criteria are set for change and archive events
     *
     * @param attribute the specified attribute
     * @param eventType the specified event type
     * @throws fr.esrf.Tango.DevFailed if event type is change or archive and no event criteria is set.
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
            if (!eventImplMap.isEmpty()) {
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
                // System.out.println("Heartbeat sent for " + heartbeatName);
            }
            xlogger.exit();
        }
    }

}
