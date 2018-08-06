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

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.AttDataReadyHelper;
import fr.esrf.Tango.AttributeConfig_3;
import fr.esrf.Tango.AttributeConfig_3Helper;
import fr.esrf.Tango.AttributeConfig_5;
import fr.esrf.Tango.AttributeConfig_5Helper;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.AttributeValue_4Helper;
import fr.esrf.Tango.AttributeValue_5;
import fr.esrf.Tango.AttributeValue_5Helper;
import fr.esrf.Tango.DevErrorListHelper;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevIntrChange;
import fr.esrf.Tango.DevIntrChangeHelper;
import fr.esrf.Tango.DevPipeData;
import fr.esrf.Tango.DevPipeDataHelper;
import fr.esrf.Tango.ZmqCallInfo;
import fr.esrf.Tango.ZmqCallInfoHelper;
import org.jacorb.orb.CDROutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.utils.TangoUtil;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * This class is a set of static utilities used for event management.
 *
 * @author verdier
 */

class EventUtilities {
    // Always in big endian (Jacorb ?)
    private static final byte[] LITTLE_ENDIAN = {0};
    private static final String HEARTBEAT = ".heartbeat";
    private static final String TANGO = "tango://";
    private static final String IDL_VERSION = "idlversion_";
    private static final String DOT = ".";
    private static final String VERSION = "version";
    private static final XLogger XLOGGER = XLoggerFactory.getXLogger(EventUtilities.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(EventUtilities.class);
    private static double zmqVersion = -1.0;
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
    static byte[] cppAlignmentAdd8(final byte[] data) {
        XLOGGER.entry();
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
        XLOGGER.exit();
        return buffer;
    }

    /**
     * Add 4 bytes at beginning for C++ alignment
     *
     * @param data buffer to be aligned
     * @return buffer after c++ alignment.
     */
    static byte[] cppAlignment(final byte[] data) {
        XLOGGER.entry();
        final byte[] buffer = new byte[data.length + 4];
        buffer[0] = (byte) 0xc0;
        buffer[1] = (byte) 0xde;
        buffer[2] = (byte) 0xc0;
        buffer[3] = (byte) 0xde;
        System.arraycopy(data, 0, buffer, 4, data.length);
        XLOGGER.exit();
        return buffer;
    }

    /**
     * Marshall the attribute with attribute Value
     *
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshallIDL5(final AttributeValue_5 attributeValue) throws DevFailed {
        XLOGGER.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeValue_5Helper.write(os, attributeValue);
            XLOGGER.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
    }

    /**
     * Marshall the attribute with attribute Value
     *
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshallIDL4(final AttributeValue_4 attributeValue) throws DevFailed {
        XLOGGER.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeValue_4Helper.write(os, attributeValue);
            XLOGGER.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
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
        XLOGGER.entry();
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
        XLOGGER.entry();
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
        XLOGGER.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            AttDataReadyHelper.write(os, dataReady);
            XLOGGER.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
    }

    /**
     * Marshall the attribute with attribute config
     *
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshallIDL4Config(final AttributeImpl attribute) throws DevFailed {
        XLOGGER.entry();
        final AttributeConfig_3 config = TangoIDLAttributeUtil.toAttributeConfig3(attribute);
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeConfig_3Helper.write(os, config);
            XLOGGER.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
    }

    /**
     * Marshall the attribute with attribute config
     *
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshallIDL5Config(final AttributeImpl attribute) throws DevFailed {
        return marshallIDL5Config(TangoIDLAttributeUtil.toAttributeConfig5(attribute));
    }

    /**
     * Marshall the attribute with attribute config
     *
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshallIDL5Config(AttributeConfig_5 config) {
        XLOGGER.entry();
        //  System.out.println("config "+ ToStringBuilder.reflectionToString(config, ToStringStyle.MULTI_LINE_STYLE));
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeConfig_5Helper.write(os, config);
            XLOGGER.exit();
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
    static byte[] marshall(final DevFailed devFailed) throws DevFailed {
        XLOGGER.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            DevErrorListHelper.write(os, devFailed.errors);
            XLOGGER.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
    }

    /**
     * Marshall the ZmqCallInfo object
     *
     * @param counter     event counter
     * @param isException true if the attribute has failed
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshall(final int counter, final boolean isException) throws DevFailed {
        XLOGGER.entry();
        final ZmqCallInfo zmqCallInfo = new ZmqCallInfo(EventConstants.ZMQ_RELEASE, counter,
                EventConstants.EXECUTE_METHOD, EventConstants.OBJECT_IDENTIFIER, isException);
        final CDROutputStream os = new CDROutputStream();
        try {
            ZmqCallInfoHelper.write(os, zmqCallInfo);
            XLOGGER.exit();
            // EventManager.dump(os.getBufferCopy());
            return os.getBufferCopy();
        } finally {
            os.close();
        }
    }

    static byte[] marshall(final DevIntrChange deviceInterface) {
        XLOGGER.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            DevIntrChangeHelper.write(os, deviceInterface);
            XLOGGER.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }

    }

    static byte[] marshall(final DevPipeData pipeData) {
        XLOGGER.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            DevPipeDataHelper.write(os, pipeData);
            XLOGGER.exit();
            return cppAlignmentAdd8(os.getBufferCopy());
        } finally {
            os.close();
        }

    }

    /**
     * Return the zmq version as a double like
     * 3.22 for "3.2.2" or 0.0 if zmq not available
     *
     * @return the TangORB version as a String
     */
    static double getZmqVersion() {
        XLOGGER.entry();
        if (zmqVersion < 0.0) { // Not already checked.
            zmqVersion = 0.0;
            try {
                String strVersion = org.zeromq.ZMQ.getVersionString();
                final StringTokenizer stk = new StringTokenizer(strVersion, ".");
                final ArrayList<String> list = new ArrayList<String>();
                while (stk.hasMoreTokens()) {
                    list.add(stk.nextToken());
                }

                strVersion = list.get(0) + "." + list.get(1);
                if (list.size() > 2) {
                    strVersion += list.get(2);
                }
                try {
                    zmqVersion = Double.parseDouble(strVersion);
                } catch (final NumberFormatException e) {
                }
            } catch (final Exception e) { /*System.err.println(e);*/
            } catch (final Error e) { /*System.err.println(e);*/
            }
        }
        XLOGGER.exit();
        return zmqVersion;
    }

    static void sendContextData(final ZMQ.Socket eventSocket, final String fullName, int counter) throws DevFailed {
        sendContextData(eventSocket, fullName, counter, false);
    }

    static void sendContextData(final ZMQ.Socket eventSocket, final String fullName, int counter, boolean isException) throws DevFailed {
        XLOGGER.entry();
        eventSocket.sendMore(fullName);
        eventSocket.send(LITTLE_ENDIAN, ZMQ.SNDMORE);
        eventSocket.send(EventUtilities.marshall(counter, isException), ZMQ.SNDMORE);
        XLOGGER.exit();
    }

    static void sendHeartbeat(final ZMQ.Socket heartbeatSocket, final String fullName) throws DevFailed {
        XLOGGER.entry();
        heartbeatSocket.sendMore(fullName);
        heartbeatSocket.send(LITTLE_ENDIAN, ZMQ.SNDMORE);
        heartbeatSocket.send(EventUtilities.marshall(0, false));
        XLOGGER.exit();
    }

    /**
     * Send data so ZMQ Socket. <br>
     * Warning. See http://zeromq.org/area:faq. "ZeroMQ sockets are not thread-safe.<br>
     * The short version is that sockets should not be shared between threads. We recommend creating a dedicated socket for each thread. <br>
     * For those situations where a dedicated socket per thread is infeasible, a socket may be shared if and only if each thread executes a full memory barrier before accessing the socket.
     * Most languages support a Mutex or Spinlock which will execute the full memory barrier on your behalf."
     *
     * @param eventSocket
     * @param fullName
     * @param counter
     * @param isException
     * @param data
     * @throws DevFailed
     */
    static void sendToSocket(final ZMQ.Socket eventSocket, final String fullName, int counter, boolean isException, byte[] data) throws DevFailed {
        XLOGGER.entry();
        sendContextData(eventSocket, fullName, counter, isException);
        eventSocket.send(data);
        LOGGER.debug("event {} sent", fullName);
        XLOGGER.exit();
    }

    static void sendToSocket(final ZMQ.Socket eventSocket, final String fullName, int counter, byte[] data) throws DevFailed {
        sendToSocket(eventSocket, fullName, counter, false, data);
    }
}
