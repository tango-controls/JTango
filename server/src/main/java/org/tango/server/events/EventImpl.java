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

import fr.esrf.Tango.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.pipe.PipeImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;
import org.zeromq.ZMQ;

/**
 * based on AttributeImpl object with event information
 *
 * @author verdier
 */
final class EventImpl {

    private final Logger logger = LoggerFactory.getLogger(EventImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventImpl.class);
    private final IEventTrigger eventTrigger;
    private final boolean isLatestIDLVersion;
    private final String fullName;
    private AttributeImpl attribute;
    private long subscribeTime;
    private int counter = 0;

    /**
     * Create a Event object based on an AttributeImpl with its event parameters.
     *
     * @param attribute  the attribute for specified event
     * @param eventType  the event type
     * @param idlVersion the event IDL version
     * @throws DevFailed
     */

    EventImpl(final AttributeImpl attribute, final EventType eventType, final int idlVersion, final String fullName) throws DevFailed {
        this.attribute = attribute;
        isLatestIDLVersion = idlVersion == DeviceImpl.SERVER_VERSION;
        this.fullName = fullName;
        eventTrigger = EventTriggerFactory.createEventTrigger(eventType, attribute);
        logger.debug("event trigger for {} type is {}", attribute.getName(), eventTrigger.getClass());
        updateSubscribeTime();
    }

    /**
     * Create a Event object based on an PipeImpl with its event parameters.
     *
     * @param pipe the pipe for specified event
     */
    EventImpl(final PipeImpl pipe, final int idlVersion, final String fullName) {
        isLatestIDLVersion = idlVersion == DeviceImpl.SERVER_VERSION;
        this.fullName = fullName;
        eventTrigger = new DefaultEventTrigger();
        logger.debug("event trigger for {} type is {}", pipe.getName(), eventTrigger.getClass());
        updateSubscribeTime();
    }

    /**
     * Create a Event object based without attribute for EventType.INTERFACE_CHANGE_EVENT.
     *
     */
    EventImpl(final int idlVersion, final String fullName) {
        isLatestIDLVersion = idlVersion == DeviceImpl.SERVER_VERSION;
        this.fullName = fullName;
        eventTrigger = new DefaultEventTrigger();
        logger.debug("event trigger for Device, type is {}", eventTrigger.getClass());
        updateSubscribeTime();
    }

    /**
     * Update the subscribe time to manage if subscribe is still active.
     */
    void updateSubscribeTime() {
        subscribeTime = System.currentTimeMillis();
    }

    /**
     * Returns false if the last subscribe is too old.
     *
     * @return false if the last subscribe is too old.
     */

    boolean isStillSubscribed() {
        final long dt = System.currentTimeMillis() - subscribeTime;
        return dt < EventConstants.EVENT_RESUBSCRIBE_PERIOD;
    }

    /**
     * Fire an event containing a value is condition is valid.
     *
     * @throws DevFailed
     * @param eventSocket
     */
    protected void pushAttributeValueEvent(ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        eventTrigger.setError(null);
        eventTrigger.updateProperties();
        if (isSendEvent()) {
            sendAttributeValueEvent(eventSocket);
        }
        xlogger.exit();
    }


    private void sendAttributeValueEvent(ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        try {
            if (isLatestIDLVersion) {
                synchronized (eventSocket) {
                    EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshallIDL5(attribute));
                }
            } else {
                synchronized (eventSocket) {
                    EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshallIDL4(attribute));
                }
            }
        } catch (final org.zeromq.ZMQException | ArrayIndexOutOfBoundsException e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                logger.error(fullName, e);
            }
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

    public void pushAttributeIDL5Event(AttributeValue_5 value, ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        try {
            synchronized (eventSocket) {
                EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshallIDL5(value));
            }
        } catch (final org.zeromq.ZMQException | ArrayIndexOutOfBoundsException e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                logger.error(fullName, e);
            }
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

    /**
     * Send a data ready event
     *
     * @param counter  a counter value
     * @param eventSocket
     * @throws DevFailed
     */
    protected void pushAttributeDataReadyEvent(final int counter, ZMQ.Socket eventSocket)
            throws DevFailed {
        xlogger.entry();
        try {
            final AttDataReady dataReady = new AttDataReady(attribute.getName(), attribute.getTangoType(), counter);
            synchronized (eventSocket) {
                EventUtilities.sendToSocket(eventSocket, fullName, counter, EventUtilities.marshall(dataReady));
            }
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        xlogger.exit();
    }

    protected void pushAttributeConfigIDL5Event(AttributeConfig_5 config, ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        try {
            synchronized (eventSocket) {
                EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshallIDL5Config(config));
            }
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

    protected void pushAttributeConfigEvent(ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        try {
            if (isLatestIDLVersion) {
                synchronized (eventSocket) {
                    EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshallIDL5Config(attribute));
                }
            } else {
                synchronized (eventSocket) {
                    EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshallIDL4Config(attribute));
                }
            }
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

    void pushInterfaceChangeEvent(
            final DevIntrChange deviceInterface, ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        try {
            synchronized (eventSocket) {
                EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshall(deviceInterface));
            }
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

    protected void pushPipeEvent(final DevPipeData pipeData, ZMQ.Socket eventSocket)
            throws DevFailed {
        xlogger.entry();
        try {
            synchronized (eventSocket) {
                EventUtilities.sendToSocket(eventSocket, fullName, counter++, EventUtilities.marshall(pipeData));
            }
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        xlogger.exit();
    }

    /**
     * Fire an event containing a DevFailed.
     *
     * @param devFailed the failed object to be sent.
     * @param eventSocket
     * @throws DevFailed
     */
    protected void pushDevFailedEvent(final DevFailed devFailed, ZMQ.Socket eventSocket) throws DevFailed {
        xlogger.entry();
        eventTrigger.updateProperties();
        eventTrigger.setError(devFailed);
        if (isSendEvent()) {
            try {
                synchronized (eventSocket) {
                    EventUtilities.sendToSocket(eventSocket, fullName, counter++, true, EventUtilities.marshall(devFailed));
                }
            } catch (final org.zeromq.ZMQException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
    }

    /**
     * check if send event
     *
     * @return
     */
    private boolean isSendEvent() throws DevFailed {
        return (eventTrigger.doCheck() && eventTrigger.isSendEvent()) || !eventTrigger.doCheck();
    }
}
