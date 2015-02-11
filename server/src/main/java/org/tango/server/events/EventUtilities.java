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
//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision:  $
//
//-======================================================================

package org.tango.server.events;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import org.jacorb.orb.CDROutputStream;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.client.database.DatabaseFactory;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.idl.TangoIDLAttributeUtil;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.AttDataReadyHelper;
import fr.esrf.Tango.AttributeConfig_5;
import fr.esrf.Tango.AttributeConfig_5Helper;
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

/**
 * This class is a set of static utilities used for event management.
 * 
 * @author verdier
 */

class EventUtilities {

    private static final String HEARTBEAT = ".heartbeat";
    private static final String TANGO = "tango://";
    private static final String IDL5 = ".idl5_";
    private static double zmqVersion = -1.0;
    private static String tangoHost = null;
    private static final XLogger xlogger = XLoggerFactory.getXLogger(EventImpl.class);

    static String buildEventName(final String deviceName, final String attributeName, final EventType eventType)
            throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, attributeName);
        fullName += IDL5 + eventType.getString();
        return fullName;
    }

    static String buildPipeEventName(final String deviceName, final String pipename) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, null);
        fullName += TangoUtil.DEVICE_SEPARATOR + pipename.toLowerCase(Locale.ENGLISH) + "."
                + EventType.PIPE_EVENT.getString();
        return fullName;
    }

    static String buildHeartBeatEventName(final String deviceName) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, null);
        fullName += HEARTBEAT;
        return fullName;
    }

    static String buildDeviceEventName(final String deviceName, final EventType eventType) throws DevFailed {
        String fullName = buildEventNameBeginning(deviceName, null);
        fullName += "." + eventType.getString();
        return fullName;
    }

    private static String buildEventNameBeginning(final String deviceName, final String attributeName) throws DevFailed {
        if (tangoHost == null) {
            tangoHost = DatabaseFactory.getDatabase().getPossibleTangoHosts()[0];
        }

//        String deviceName2 = deviceName;
//        if (!DatabaseFactory.isUseDb()) {
//            deviceName2 = deviceName + "#dbase=no";
//        }
        String fullName = TANGO + tangoHost + TangoUtil.DEVICE_SEPARATOR + deviceName.toLowerCase(Locale.ENGLISH);
        if (attributeName != null) {
            fullName += TangoUtil.DEVICE_SEPARATOR + attributeName.toLowerCase(Locale.ENGLISH);
        }
        return fullName;
    }

    /**
     * Add 4 bytes at beginning for C++ alignment
     * 
     * @param data buffer to be aligned
     * @return buffer after c++ alignment.
     */
    static byte[] cppAlignment(final byte[] data) {
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
     * @return result of the marshall action
     * @throws fr.esrf.Tango.DevFailed if marshall action failed
     */
    static byte[] marshall(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeValue_5 attributeValue = TangoIDLAttributeUtil.toAttributeValue5(attribute,
                attribute.getReadValue(), attribute.getWriteValue());
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeValue_5Helper.write(os, attributeValue);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }
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
     * @return result of the marshall action
     * @throws DevFailed if marshall action failed
     */
    static byte[] marshallConfig(final AttributeImpl attribute) throws DevFailed {
        xlogger.entry();
        final AttributeConfig_5 config = TangoIDLAttributeUtil.toAttributeConfig5(attribute);
        final CDROutputStream os = new CDROutputStream();
        try {
            AttributeConfig_5Helper.write(os, config);
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
    static byte[] marshall(final DevFailed devFailed) throws DevFailed {
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
    static byte[] marshall(final int counter, final boolean isException) throws DevFailed {
        xlogger.entry();
        final ZmqCallInfo zmqCallInfo = new ZmqCallInfo(EventConstants.ZMQ_RELEASE, counter,
                EventConstants.EXECUTE_METHOD, EventConstants.OBJECT_IDENTIFIER, isException);
        final CDROutputStream os = new CDROutputStream();
        try {
            ZmqCallInfoHelper.write(os, zmqCallInfo);
            xlogger.exit();
            // EventManager.dump(os.getBufferCopy());
            return os.getBufferCopy();
        } finally {
            os.close();
        }
    }

    static byte[] marshall(final DevIntrChange deviceInterface) {
        xlogger.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            DevIntrChangeHelper.write(os, deviceInterface);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }

    }

    static byte[] marshall(final DevPipeData pipeData) {
        xlogger.entry();
        final CDROutputStream os = new CDROutputStream();
        try {
            DevPipeDataHelper.write(os, pipeData);
            xlogger.exit();
            return cppAlignment(os.getBufferCopy());
        } finally {
            os.close();
        }

    }

    static void dump(final byte[] rec) {
        for (int i = 0; i < rec.length; i++) {

            final String s = String.format("%02x", 0xFF & rec[i]);
            // logger.debug("0x{} ",s);
            System.out.print("0x" + s + " ");
            if ((i + 1) % 16 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * Return the zmq version as a double like
     * 3.22 for "3.2.2" or 0.0 if zmq not available
     * 
     * @return the TangORB version as a String
     */
    static double getZmqVersion() {
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
                    System.err.println(e);
                }
            } catch (final Exception e) { /*System.err.println(e);*/
            } catch (final Error e) { /*System.err.println(e);*/
            }
        }
        return zmqVersion;
    }
}
