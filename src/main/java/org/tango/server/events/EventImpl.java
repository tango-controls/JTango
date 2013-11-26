/**
 * Copyright (C) : 2012
 * 
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango. If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.utils.DevFailedUtils;
import org.zeromq.ZMQ;

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.DevFailed;

/**
 * based on AttributeImpl object with event information
 * 
 * @author verdier
 */
final class EventImpl {

    private final Logger logger = LoggerFactory.getLogger(EventImpl.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(EventImpl.class);

    private final AttributeImpl attribute;
    private final IEventTrigger eventTrigger;
    private long subscribeTime;
    private int counter = 0;
    private final EventType eventType;

    /**
     * Create a Event object based on an AttributeImpl with its event parameters.
     * 
     * @param attribute the attribute for specified event
     * @param eventType the event type
     * @throws DevFailed
     */

    EventImpl(final AttributeImpl attribute, final EventType eventType) throws DevFailed {
        this.attribute = attribute;
        this.eventType = eventType;
        eventTrigger = EventTriggerFactory.createEventTrigger(eventType, attribute);
        logger.debug("event trigger for {} type is {}", attribute.getName(), eventTrigger.getClass());
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
     * Fire an event containing a value.
     * 
     * @param eventSocket the socket to send event
     * @param fullName event full name
     * @param attributeValue the value object to be sent.
     * @throws DevFailed
     */

    void pushEvent(final ZMQ.Socket eventSocket, final String fullName) throws DevFailed {
        xlogger.entry();
        eventTrigger.setError(null);
        eventTrigger.updateProperties();
        if (eventTrigger.doCheck() && eventTrigger.isSendEvent()) {
            if (eventType.equals(EventType.ATT_CONF_EVENT)) {
                pushConfigEvent(eventSocket, fullName);
            } else {
                sendEvent(eventSocket, fullName);
            }
        }
        xlogger.exit();
    }

    private void sendEvent(final ZMQ.Socket eventSocket, final String fullName) throws DevFailed {
        xlogger.entry();
        try {
            eventSocket.sendMore(fullName);
            eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
            eventSocket.send(EventUtilities.marshall(counter++, false), ZMQ.SNDMORE);
            eventSocket.send(EventUtilities.marshall(attribute), 0);
            logger.debug("sent event: {}", fullName);
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        xlogger.exit();
    }

    /**
     * Send a data ready event
     * 
     * @param eventSocket the socket to send event
     * @param fullName event full name
     * @param counter a counter value
     * @throws DevFailed
     */
    void pushDataReadyEvent(final ZMQ.Socket eventSocket, final String fullName, int counter) throws DevFailed {
        xlogger.entry();
        try {
            final AttDataReady dataReady = new AttDataReady(attribute.getName(), attribute.getTangoType(), counter);
            eventSocket.sendMore(fullName);
            eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
            eventSocket.send(EventUtilities.marshall(counter++, false), ZMQ.SNDMORE);
            eventSocket.send(EventUtilities.marshall(dataReady), 0);
            logger.debug("sent event: {}", fullName);
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        xlogger.exit();
    }

    void pushConfigEvent(final ZMQ.Socket eventSocket, final String fullName) throws DevFailed {
        xlogger.entry();
        try {
            eventSocket.sendMore(fullName);
            eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
            eventSocket.send(EventUtilities.marshall(counter++, false), ZMQ.SNDMORE);
            eventSocket.send(EventUtilities.marshallConfig(attribute), 0);
            logger.debug("sent event: {}", fullName);
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        xlogger.exit();
    }

    /**
     * Fire an event containing a DevFailed.
     * 
     * @param eventSocket the socket to send event
     * @param fullName event full name
     * @param devFailed the failed object to be sent.
     * @throws DevFailed
     */
    void pushEvent(final ZMQ.Socket eventSocket, final String fullName, final DevFailed devFailed) throws DevFailed {
        xlogger.entry();
        eventTrigger.updateProperties();
        eventTrigger.setError(devFailed);
        if (eventTrigger.doCheck() && eventTrigger.isSendEvent()) {
            try {
                eventSocket.sendMore(fullName);
                eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
                eventSocket.send(EventUtilities.marshall(counter++, true), ZMQ.SNDMORE);
                eventSocket.send(EventUtilities.marshall(devFailed), 0);
                logger.debug("sent ERROR event: {}", fullName);
            } catch (final org.zeromq.ZMQException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
    }

}
