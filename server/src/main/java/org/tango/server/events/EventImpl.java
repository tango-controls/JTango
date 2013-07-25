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

import org.jacorb.orb.CDROutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.utils.DevFailedUtils;
import org.zeromq.ZMQ;

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.AttDataReadyHelper;
import fr.esrf.Tango.AttributeConfig_3;
import fr.esrf.Tango.AttributeConfig_3Helper;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.AttributeValue_4Helper;
import fr.esrf.Tango.DevErrorListHelper;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.ZmqCallInfo;
import fr.esrf.Tango.ZmqCallInfoHelper;
import fr.esrf.TangoApi.ApiUtil;

/**
 * based on AttributeImpl object
 * with event information
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
        logger.debug("event trigger type is {}", eventTrigger.getClass());
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

    void pushEventCheck(final ZMQ.Socket eventSocket, final String fullName, final AttributeValue attributeValue)
            throws DevFailed {
        xlogger.entry();
        eventTrigger.setError(null);
        if (eventTrigger.isSendEvent()) {
            if (eventType.equals(EventType.ATT_CONF_EVENT)) {
                pushEventConfig(eventSocket, fullName);
            } else {
                pushEvent(eventSocket, fullName, attributeValue);
            }
        }
        xlogger.exit();
    }

    void pushEvent(final ZMQ.Socket eventSocket, final String fullName, final AttributeValue attributeValue)
            throws DevFailed {
        xlogger.entry();
        try {
            eventSocket.sendMore(fullName);
            eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
            eventSocket.send(marshall(counter++, false), ZMQ.SNDMORE);
            eventSocket.send(marshall(attribute, attributeValue), 0);
            logger.debug("sent event: {}", fullName);
            // System.out.println(" ----> Event sent for " + fullName);
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        xlogger.exit();
    }

    void pushEvent(final ZMQ.Socket eventSocket, final String fullName, final AttDataReady dataReady) throws DevFailed {
        xlogger.entry();
        try {
            eventSocket.sendMore(fullName);
            eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
            eventSocket.send(marshall(counter++, false), ZMQ.SNDMORE);
            eventSocket.send(marshall(dataReady), 0);
            logger.debug("sent event: {}", fullName);
            // System.out.println(" ----> Event sent for " + fullName);
        } catch (final org.zeromq.ZMQException e) {
            throw DevFailedUtils.newDevFailed(e);
        }

        xlogger.exit();
    }

    void pushEventConfig(final ZMQ.Socket eventSocket, final String fullName) throws DevFailed {
        xlogger.entry();
        try {
            eventSocket.sendMore(fullName);
            eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
            eventSocket.send(marshall(counter++, false), ZMQ.SNDMORE);
            eventSocket.send(marshallConfig(attribute), 0);
            logger.debug("sent event: {}", fullName);
            // System.out.println(" ----> Event sent for " + fullName);
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
        eventTrigger.setError(devFailed);
        if (eventTrigger.isSendEvent()) {
            try {
                eventSocket.sendMore(fullName);
                eventSocket.send(EventConstants.LITTLE_ENDIAN, ZMQ.SNDMORE);
                eventSocket.send(marshall(counter++, true), ZMQ.SNDMORE);
                eventSocket.send(marshall(devFailed), 0);
                logger.debug("sent ERROR event: {}", fullName);
                // System.out.println(" ----> Event sent for " + fullName);
            } catch (final org.zeromq.ZMQException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        xlogger.exit();
    }

    /**
     * Add 4 bytes at beginning for C++ alignment
     * 
     * @param data buffer to be aligned
     * @return buffer after c++ alignment.
     */
    private byte[] cppAlignment(final byte[] data) {
        xlogger.entry();
        final byte[] buffer = new byte[data.length + 4];
        buffer[0] = (byte) 0xc0;
        buffer[1] = (byte) 0xde;
        buffer[2] = (byte) 0xc0;
        buffer[3] = (byte) 0xde;
        System.arraycopy(data, 0, buffer, 4, data.length);
        xlogger.exit();
        return buffer;

    }

    /**
     * Marshall the attribute with attribute Value
     * 
     * @param attribute attribute to marshall
     * @param attributeValue attribute value to marshall
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    private byte[] marshall(final AttributeImpl attribute, final AttributeValue attributeValue) throws DevFailed {
        xlogger.entry();
        final AttributeValue_4 attributeValue4 = TangoIDLAttributeUtil.toAttributeValue4(attribute, attributeValue,
                null);
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeValue_4Helper.write(os, attributeValue4);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
    }

    /**
     * Marshall AttDataReady
     * 
     * @param dataReady
     * @return result of the marshall action
     * @throws DevFailed
     */
    private byte[] marshall(final AttDataReady dataReady) throws DevFailed {
        xlogger.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            AttDataReadyHelper.write(os, dataReady);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }

    }

    /**
     * Marshall the attribute with attribute config
     * 
     * @param attributeValue attribute value to marshall
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    private byte[] marshallConfig(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeConfig_3 config = TangoIDLAttributeUtil.toAttributeConfig3(attribute);
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeConfig_3Helper.write(os, config);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }

    }

    /**
     * Marshall the attribute with a DevFailed object
     * 
     * @param devFailed DevFailed object to marshall
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */

    private byte[] marshall(final DevFailed devFailed) throws DevFailed {
        xlogger.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            DevErrorListHelper.write(os, devFailed.errors);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
    }

    /**
     * Marshall the ZmqCallInfo object
     * 
     * @param counter event counter
     * @param isException true if the attribute has failed
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */

    private byte[] marshall(final int counter, final boolean isException) throws DevFailed {
        xlogger.entry();
        final ZmqCallInfo zmqCallInfo = new ZmqCallInfo((int) ApiUtil.getZmqVersion(), counter,
                EventConstants.EXECUTE_METHOD, EventConstants.OBJECT_IDENTIFIER, isException);
        final CDROutputStream os = new CDROutputStream();
        try {
            ZmqCallInfoHelper.write(os, zmqCallInfo);
            xlogger.exit();
            return os.getBufferCopy();
        } finally {
            os.close();
        }
    }

}
