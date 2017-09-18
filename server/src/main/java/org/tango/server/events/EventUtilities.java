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

import fr.esrf.Tango.*;
import org.jacorb.orb.CDROutputStream;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.utils.TangoUtil;

import java.util.Locale;

/**
 * This class is a set of static utilities used for event management.
 *
 * @author verdier
 */

class EventUtilities {

    private static final String HEARTBEAT = ".heartbeat";
    private static final String TANGO = "tango://";
    private static final String IDL_VERSION = "idlversion_";
    private static final String DOT = ".";
    private static final String VERSION = "version";
    private static final XLogger xlogger = XLoggerFactory.getXLogger(EventImpl.class);
    private static String tangoHost = null;

    static String buildEventName(final String deviceName, final String attributeName, final EventType eventType,
            final int idlVersion) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, attributeName);
        if (idlVersion >= 5) {
            fullName += DOT + IDL_VERSION.replace(VERSION, Integer.toString(idlVersion)) + eventType.getString();
        } else {
            fullName += DOT + eventType.getString();
        }
        return fullName.toLowerCase(Locale.ENGLISH);
    }

    static String buildEventName(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, attributeName);
        fullName += DOT + EventManager.IDL_LATEST + eventType.getString();
        return fullName.toLowerCase(Locale.ENGLISH);
    }

    static String buildPipeEventName(final String deviceName, final String pipename) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, null);
        fullName += TangoUtil.DEVICE_SEPARATOR + pipename + "." + EventType.PIPE_EVENT.getString();
        return fullName.toLowerCase(Locale.ENGLISH);
    }

    static String buildHeartBeatEventName(final String deviceName) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, null);
        fullName += HEARTBEAT;
        return fullName.toLowerCase(Locale.ENGLISH);
    }

    static String buildDeviceEventName(final String deviceName, final EventType eventType) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, null);
        fullName += DOT + eventType.getString();
        return fullName.toLowerCase(Locale.ENGLISH);
    }

    private static String buildEventNameBeginning(final String deviceName, final String attributeName) throws DevFailed {
        if (tangoHost == null) {
            tangoHost = DatabaseFactory.getDatabase().getPossibleTangoHosts()[0];
        }
        // String deviceName2 = deviceName;
        // if (!DatabaseFactory.isUseDb()) {
        // deviceName2 = deviceName + "#dbase=no";
        // }
        String fullName = TANGO + tangoHost + TangoUtil.DEVICE_SEPARATOR + deviceName.toLowerCase(Locale.ENGLISH);
        if (attributeName != null) {
            fullName += TangoUtil.DEVICE_SEPARATOR + attributeName.toLowerCase(Locale.ENGLISH);
        }
        return fullName;
    }

    /**
     * Add 8 bytes at beginning for C++ alignment
     *
     * @param data buffer to be aligned
     * @return buffer after c++ alignment.
     */
    private static byte[] cppAlignmentAdd8(final byte[] data) {
        xlogger.entry();
        final byte[] buffer = new byte[data.length + 8];
        buffer[0] = (byte) 0xc0;
        buffer[1] = (byte) 0xde;
        buffer[2] = (byte) 0xc0;
        buffer[3] = (byte) 0xde;
        buffer[4] = (byte) 0xc0;
        buffer[5] = (byte) 0xde;
        buffer[6] = (byte) 0xc0;
        buffer[7] = (byte) 0xde;
        System.arraycopy(data, 0, buffer, 8, data.length);
        xlogger.exit();
        return buffer;
    }

    /**
     * Add 4 bytes at beginning for C++ alignment
     *
     * @param data buffer to be aligned
     * @return buffer after c++ alignment.
     */
    private static byte[] cppAlignment(final byte[] data) {
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
     * @param attributeValue attribute to marshall
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshallIDL5(final AttributeValue_5 attributeValue) throws DevFailed {
        xlogger.entry();
        try (CDROutputStream os = new CDROutputStream()) {
            AttributeValue_5Helper.write(os, attributeValue);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        }
    }

    /**
     * Marshall the attribute with attribute Value
     *
     * @param attributeValue attribute to marshall
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshallIDL4(final AttributeValue_4 attributeValue) throws DevFailed {
        xlogger.entry();
        try (CDROutputStream os = new CDROutputStream()) {
            AttributeValue_4Helper.write(os, attributeValue);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        }
    }

    /**
     * Marshall the attribute with attribute Value
     *
     * @param attribute attribute to marshall
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshallIDL5(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeValue_5 attributeValue = TangoIDLAttributeUtil.toAttributeValue5(attribute,
                attribute.getReadValue(), attribute.getWriteValue());
        return marshallIDL5(attributeValue);
    }

    /**
     * Marshall the attribute with attribute Value
     *
     * @param attribute attribute to marshall
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshallIDL4(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeValue_4 attributeValue = TangoIDLAttributeUtil.toAttributeValue4(attribute,
                attribute.getReadValue(), attribute.getWriteValue());
        return marshallIDL4(attributeValue);
    }

    /**
     * Marshall AttDataReady
     *
     * @param dataReady the DataReady object to marshall
     * @return result of the marshall action
     * @throws DevFailed
     */
    static byte[] marshall(final AttDataReady dataReady) throws DevFailed {
        xlogger.entry();
        try (CDROutputStream os = new CDROutputStream()) {
            AttDataReadyHelper.write(os, dataReady);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        }
    }

    /**
     * Marshall the attribute with attribute config
     *
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshallIDL4Config(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeConfig_3 config = TangoIDLAttributeUtil.toAttributeConfig3(attribute);
        try (CDROutputStream os = new CDROutputStream()) {
            AttributeConfig_3Helper.write(os, config);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        }
    }

    /**
     * Marshall the attribute with attribute config
     *
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshallIDL5Config(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeConfig_5 config = TangoIDLAttributeUtil.toAttributeConfig5(attribute);
        try (CDROutputStream os = new CDROutputStream()) {
            AttributeConfig_5Helper.write(os, config);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        }
    }

    /**
     * Marshall the attribute with a DevFailed object
     *
     * @param devFailed DevFailed object to marshall
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshall(final DevFailed devFailed) throws DevFailed {
        xlogger.entry();
        try (CDROutputStream os = new CDROutputStream()) {
            DevErrorListHelper.write(os, devFailed.errors);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
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
    static byte[] marshall(final int counter, final boolean isException) throws DevFailed {
        xlogger.entry();
        final ZmqCallInfo zmqCallInfo = new ZmqCallInfo(EventConstants.ZMQ_RELEASE, counter,
                EventConstants.EXECUTE_METHOD, EventConstants.OBJECT_IDENTIFIER, isException);
        try (CDROutputStream os = new CDROutputStream()) {
            ZmqCallInfoHelper.write(os, zmqCallInfo);
            xlogger.exit();
            // EventManager.dump(os.getBufferCopy());
            return os.getBufferCopy();
        }
    }

    static byte[] marshall(final DevIntrChange deviceInterface) {
        xlogger.entry();
        try (CDROutputStream os = new CDROutputStream()) {
            DevIntrChangeHelper.write(os, deviceInterface);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        }

    }

    static byte[] marshall(final DevPipeData pipeData) {
        xlogger.entry();
        try (CDROutputStream os = new CDROutputStream()) {
            DevPipeDataHelper.write(os, pipeData);
            xlogger.exit();
            return cppAlignmentAdd8(os.getBufferCopy());
        }
    }
}
