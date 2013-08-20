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

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.orb.ServerRequestInterceptor;
import org.tango.server.ExceptionMessages;
import org.tango.server.ServerManager;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
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

    private static ZMQ.Context context;
    private static final EventManager INSTANCE = new EventManager();
    private static ZMQ.Socket heartbeatSocket;
    private static ZMQ.Socket eventSocket;
    private static boolean isInitialized = false;
    private static String heartbeatEndpoint = null;
    private static String eventEndpoint = null;
    private final Logger logger = LoggerFactory.getLogger(EventManager.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventManager.class);

    private final Map<String, EventImpl> eventImplMap = new HashMap<String, EventImpl>();

    private static ScheduledExecutorService heartBeatExecutor;

    private enum SocketType {
        HEARTBEAT, EVENTS
    }


    public static EventManager getInstance() {
        return INSTANCE;
    }

    private EventManager() {
        isInitialized = false;
    }

    private void initialize() throws DevFailed {

        xlogger.entry();
        logger.debug("client IP address is {}", ServerRequestInterceptor.getInstance().getClientIPAddress());

        try {
            context = ZMQ.context(1);
            System.out.println("====================== ZMQ (" + EventUtilities.getZmqVersion() +
                    ") event system started =======================");
        }
        catch (Error e) {
            DevFailedUtils.throwDevFailed(ExceptionMessages.EVENT_NOT_AVAILABLE,
                    "ZMQ classes not found. Event system is not available.");
        }

        String adminDeviceName = ServerManager.getInstance().getAdminDeviceName();
        // TODO : without database?
        heartbeatSocket = context.socket(ZMQ.PUB);
        eventSocket = context.socket(ZMQ.PUB);

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
        String  heartbeatName = EventUtilities.buildEventName(adminDeviceName, null, "heartbeat");
        heartBeatExecutor.scheduleAtFixedRate(new HeartbeatThread(heartbeatName),
                0, EventConstants.EVENT_HEARTBEAT_PERIOD, TimeUnit.MILLISECONDS);
        isInitialized = true;
        xlogger.exit();
    }


    /**
     * Check the High Water Mark value for server.
     * @return the HWM value to be used.
     */
    private int getServerHWM() {

        int hwm = EventConstants.HWM_DEFAULT;
        //  Check the High Water Mark value from environment
        String  env = System.getenv("TANGO_DS_EVENT_BUFFER_HWM");
        try {
            if (env!=null) {
                hwm = Integer.parseInt(env);
            }
        }
        catch (NumberFormatException e) {
            System.err.println(e);
        }
        //  ToDo Check HWM from Util class and Property
        return hwm;
    }
    /**
     * Check the High Water Mark value for client.
     * @return the HWM value to be used.
     */
    private int getClientHWM() {

        int hwm = EventConstants.HWM_DEFAULT;
        //  Check the High Water Mark value from Control System property
        //  ToDo replace by value from Stored Procedure
        try {
            DbDatum datum = ApiUtil.get_db_obj().get_property("CtrlSystem", "EventBufferHwm");
            if (!datum.is_empty()) {
                hwm = Integer.parseInt(datum.extractString());
            }
        }
        catch (DevFailed e) {
            Except.print_exception(e);
        }
        catch (NumberFormatException e) {
            System.err.println("ControlSystem/EventBufferHwm property: " + e);
        }
        return hwm;
    }
    /**
     * Returns next port to connect the heartbeatSocket or eventSocket
     * @throws DevFailed if no free port found
     */
    private int getNextAvailablePort() throws DevFailed{

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
     * @throws DevFailed if no free port found
     */
    private void setEndpoints(final SocketType socketType) throws DevFailed {
        xlogger.entry();

        String ipAddress;
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (final UnknownHostException e1) {
            throw DevFailedUtils.newDevFailed(e1);
        }

        final String endpoint = "tcp://" + ipAddress + ":" + getNextAvailablePort();
        final ZMQ.Socket socket = context.socket(ZMQ.PUB);

        switch (socketType) {
            case HEARTBEAT:
                heartbeatSocket = socket;
                heartbeatEndpoint = endpoint;
                break;
            case EVENTS:
                eventSocket = socket;
                eventEndpoint = endpoint;
                socket.setSndHWM(getServerHWM());
                logger.debug("HWM has been set to " + socket.getSndHWM());
                break;
        }
        socket.setLinger(0);
        socket.setReconnectIVL(-1);

        logger.debug("bind ZMQ socket {} for {}", endpoint, socketType);
        socket.bind(endpoint);
        xlogger.exit();
    }

    /**
     * Search the specified EventImpl object
     * @param fullName specified EventImpl name.
     * @return the specified EventImpl object if found, otherwise returns null.
     */
    private EventImpl getEventImpl(final String fullName) {

        if (!isInitialized) {
            return null;
        }

        // Check if subscribed
        final EventImpl eventImpl = eventImplMap.get(fullName);
        if (eventImpl == null) {
            return null;
        }

        // Check if subscription is out of time
        if (!eventImpl.isStillSubscribed()) {
            logger.debug("{} not Subscribed any more", fullName);
            // System.out.println(fullName + "Not Subscribed any more");
            eventImplMap.remove(fullName);

            // if no subscribers, close sockets
            if (eventImplMap.isEmpty()) {
                close();
            }
            return null;
        }

        // Else
        return eventImpl;
    }

    /**
     * Close all zmq resources
     */
    public void close() {
        xlogger.entry();
        logger.debug("closing all event resources");

        if (heartBeatExecutor!=null)
            heartBeatExecutor.shutdownNow();

        if (heartbeatSocket!=null) {
            try {
                heartbeatSocket.close();
            } catch (final org.zeromq.ZMQException e) {
                logger.error("cannot close heartbeat socket ", e);
                System.err.println("cannot close heartbeat socket " + e);
            }
        }
        if (eventSocket!=null) {
            try {
                eventSocket.close();
            } catch (final org.zeromq.ZMQException e) {
                logger.error("cannot close event socket ", e);
                System.err.println("cannot close event socket "+ e);
            }
        }

        context.term();
        isInitialized = false;
        logger.debug("all event resources closed");
        xlogger.exit();
    }


    /**
     * Initialize ZMQ event system if not already done,
     * subscribe to the specified event end
     * returns the connection parameters for specified event.
     * 
     * @param deviceName The specified event device name
     * @param attribute The specified event attribute name
     * @param eventTypeStr The specified event type
     * @return the connection parameters for specified event.
     */
    public DevVarLongStringArray getConnectionParameters(final String deviceName, final AttributeImpl attribute,
            final String eventTypeStr) throws DevFailed {
        xlogger.entry();
        // If first time start the ZMQ management
        if (!isInitialized) {
            initialize();
        }

        // check if event is already subscribed
        final String fullName = EventUtilities.buildEventName(deviceName, attribute.getName(), eventTypeStr);
        EventImpl eventImpl = eventImplMap.get(fullName);
        if (eventImpl == null) {
            // If not already manage, create EventImpl object and add it to the map
            eventImpl = new EventImpl(attribute, EventType.getEvent(eventTypeStr));
            eventImplMap.put(fullName, eventImpl);
        } else {
            eventImpl.updateSubscribeTime();
        }

        // Build the connection parameters object
        final DevVarLongStringArray longStringArray = new DevVarLongStringArray();
        longStringArray.lvalue = new int[] { EventConstants.TANGO_RELEASE, DeviceImpl.SERVER_VERSION,
                getClientHWM(), 0, 0, EventConstants.ZMQ_RELEASE, };
        longStringArray.svalue = new String[] { heartbeatEndpoint, eventEndpoint };
        return longStringArray;
    }

    /**
     * Check if the event must be sent and fire it if must be done
     * 
     * @param attributeName specified event attribute
     * @param devFailed the attribute failed error to be sent as event
     * @throws DevFailed
     */
    public void pushEvent(final String attributeName, final String deviceName, final DevFailed devFailed)
            throws DevFailed {
        xlogger.entry();
        for (final String eventTypeName : TangoConst.eventNames) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventTypeName);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                eventImpl.pushEvent(eventSocket, fullName, devFailed);
            }
        }
        xlogger.exit();
    }

    /**
     * Check if the event must be sent and fire it if must be done
     * 
     * @param attributeName specified event attribute
     * @param attributeValue the attribute value to be sent as event
     * @throws DevFailed
     */
     public void pushEventCheck(final String attributeName, final String deviceName, final AttributeValue attributeValue)
            throws DevFailed {
        xlogger.entry();
        for (final String eventTypeName : TangoConst.eventNames) {
            final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventTypeName);
            final EventImpl eventImpl = getEventImpl(fullName);
            if (eventImpl != null) {
                eventImpl.pushEventCheck(eventSocket, fullName, attributeValue);
            }
        }
        xlogger.exit();
    }

    /**
     * fire event
     * 
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param attributeValue the attribute value to be sent as event
     * @param eventTypeName specified event type.
     * @throws DevFailed
     */
    public void pushEvent(final String deviceName, final String attributeName, final AttributeValue attributeValue,
            final EventType eventTypeName) throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName, eventTypeName.getString());
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            eventImpl.pushEvent(eventSocket, fullName, attributeValue);
        }
        xlogger.exit();
    }

    /**
     * fire event with AttDataReady
     * 
     * @param deviceName Specified event device
     * @param attributeName specified event attribute name
     * @param dataReady the DataReady value to be sent as event
     * @throws DevFailed
     */
    public void pushEvent(final String deviceName, final String attributeName, final AttDataReady dataReady)
            throws DevFailed {
        xlogger.entry();
        final String fullName = EventUtilities.buildEventName(deviceName, attributeName,
                EventType.DATA_READY_EVENT.getString());
        final EventImpl eventImpl = getEventImpl(fullName);
        if (eventImpl != null) {
            System.out.println("Fire DataReady event");
            eventImpl.pushEvent(eventSocket, fullName, dataReady);
        }
        xlogger.exit();
    }

    /**
     * Check if event criteria are set for change and archive events
     * @param attribute     the specified attribute
     * @param eventTypeStr  the specified event type
     * @throws DevFailed if event type is change or archive and no event criteria is set.
     */
    public static void checkEventCriteria(AttributeImpl attribute, String eventTypeStr) throws DevFailed{
        switch (EventType.getEvent(eventTypeStr)) {
            case CHANGE_EVENT:
                ChangeEventTrigger.checkEventCriteria(attribute);
                break;
            case ARCHIVE_EVENT:
                ArchiveEventTrigger.checkEventCriteria(attribute);
                break;
        }
    }





    //===================================================
    /**
     * This class is a thread to send a heartbeat
     */
    //===================================================
    class HeartbeatThread implements Runnable {
    
        private final String heartbeatName;

        HeartbeatThread(String heartbeatName) {
            this.heartbeatName = heartbeatName;
        }

        @Override
        public void run() {
            xlogger.entry();
            if (!eventImplMap.isEmpty()) {
                // Fire heartbeat
                heartbeatSocket.sendMore(heartbeatName);
                heartbeatSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
                heartbeatSocket.send("0");
                logger.debug("Heartbeat sent for {}", heartbeatName);
                //System.out.println("Heartbeat sent for " + heartbeatName);
            }
            xlogger.exit();
        }
    }
}
