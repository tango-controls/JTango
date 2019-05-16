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
package org.tango.server.testserver;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.tango.server.ServerManager;
import org.tango.server.events.EventType;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.events.EventData;
import fr.esrf.TangoDs.TangoConst;

@Ignore("Tests need a tangdb")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventTest {
// XXX dependency to tangodb
    private static String deviceName = "tmp/test/event";

    // private static String adminName;

    @BeforeClass
    public static void startDevice() throws DevFailed, IOException {
        // TODO Event client API does not support no db device
//        ServerSocket ss1 = null;
//        try {
//            ss1 = new ServerSocket(0);
//            ss1.setReuseAddress(true);
//            ss1.close();
//            EventTester.startNoDb(ss1.getLocalPort());
//            deviceName = "tango://localhost:" + ss1.getLocalPort() + "/" + EventTester.NO_DB_DEVICE_NAME + "#dbase=no";
//            adminName = "tango://localhost:" + ss1.getLocalPort() + "/" + Constants.ADMIN_DEVICE_DOMAIN + "/"
//                    + ServerManager.getInstance().getServerName() + "#dbase=no";
//        } finally {
//            if (ss1 != null)
//                ss1.close();

        EventServer.start();
        System.out.println("event server started");
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        System.out.println("stop device");
        ServerManager.getInstance().stop();
        System.out.println("stop device out");
    }

    // @Ignore
    @Test(timeout = 3000)
    public void aInterfaceChange() throws DevFailed {
        System.out.println("\t####interfaceChange");
        try {
            final DeviceProxy dev = new DeviceProxy(deviceName);
            // dev.subscribe_event("booleanAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
            // TangoConst.NOT_STATELESS);
            // dev.subscribe_event("doubleAtt", TangoConst.PERIODIC_EVENT, 100, new String[] {},
            // TangoConst.NOT_STATELESS);
            final int id = dev.subscribe_event(TangoConst.INTERFACE_CHANGE, 100, TangoConst.NOT_STATELESS);
            int eventsNb = 0;

            while (eventsNb < 3) {
                dev.command_inout("Init");
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.event_type == EventType.INTERFACE_CHANGE_EVENT.getValue()) {
                        eventsNb++;
                        System.out.println("received INTERFACE_CHANGE for: "
                                + eventData.deviceInterface.getAttributeNumber());
                        dev.command_inout("Init");
                    }
                }
            }
            dev.unsubscribe_event(id);
        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test(timeout = 3000)
    public void changeNumberScalar() throws DevFailed {
        System.out.println("\t####changeNumberScalar");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("doubleAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        double value = 0;
        double previousValue = 0;
        while (eventsNb < 3) {
            final EventData[] events = dev.get_events();
            for (final EventData eventData : events) {
                if (eventData.name.contains("doubleatt")) {
                    eventsNb++;
                    previousValue = value;
                    value = eventData.attr_value.extractDouble();
                }
            }
        }
        assertThat(value, equalTo(previousValue + 1));
        dev.unsubscribe_event(id);
    }

    @Test(timeout = 3000)
    public void changeNumberScalarRelative() throws DevFailed {
        System.out.println("\t####changeNumberScalarRelative");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("changeRelative", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        try {
            int eventsNb = 0;
            double value = 0;
            double previousValue = 0;
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    System.out.println(eventData.event);
                    System.out.println(eventData.name);
                    if (eventData.name.contains("changerelative")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractDouble();
                        System.out.println("received changerelative " + value);
                    }
                }
            }
            assertThat(value, equalTo(previousValue + 1));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void archiveScalar() throws DevFailed {
        System.out.println("\t####archiveScalar");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("archive", TangoConst.ARCHIVE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        long value = 0;
        long previousValue = 0;
        try {
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("archive")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractLong64();
                    }
                }
            }
            assertThat(value, equalTo(previousValue + 1));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeBooleanScalar() throws DevFailed {
        System.out.println("\t####changeBooleanScalar");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("booleanAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        boolean value = false;
        boolean previousValue = false;
        try {
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("booleanatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractBoolean();
                        System.out.println("read value " + value);
                        System.out.println("read value time " + eventData.attr_value.getTime());
                        assertThat(eventData.attr_value.getTime(), equalTo(34567L));
                    }
                }
            }
            assertThat(value, equalTo(!previousValue));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void periodic() throws DevFailed {
        System.out.println("\t####periodic");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("doubleAtt", TangoConst.PERIODIC_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        double value = 0;
        double previousValue = 0;
        try {
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("doubleatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractDouble();
                    }
                }
            }
            assertThat(value, equalTo(previousValue + 1));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeNumberArray() throws DevFailed {
        System.out.println("\t####changeNumberArray");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("doubleArrayAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        double[] value = new double[] { 0 };
        double[] previousValue = new double[] { 0 };
        try {
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("doublearrayatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractDoubleArray();
                    }
                }
            }
            assertThat(value[0], equalTo(previousValue[0] + 1));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 10000)
    public void changeStringScalar() throws DevFailed {
        System.out.println("\t####changeStringScalar");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("stringAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        String value = "";
        String previousValue = "";
        try {
            while (eventsNb < 3) {

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("stringatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractString();
                    }
                }
            }
            assertThat(Double.parseDouble(value), equalTo(Double.parseDouble(previousValue) + 1));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeBooleanArray() throws DevFailed {
        System.out.println("\t####changeBooleanArray");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("booleanArrayAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        boolean[] value = new boolean[] { false };
        boolean[] previousValue = new boolean[] { false };
        try {
            while (eventsNb < 3) {

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("booleanarrayatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractBooleanArray();
                    }
                }
            }
            assertThat(value[0], equalTo(!previousValue[0]));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeStringArray() throws DevFailed {
        System.out.println("\t####changeStringArray");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("stringArrayAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        String[] value = new String[] { "" };
        String[] previousValue = new String[] { "" };
        try {
            while (eventsNb < 3) {

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("stringarrayatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractStringArray();
                    }
                }
            }
            assertThat(Double.parseDouble(value[0]), equalTo(Double.parseDouble(previousValue[0]) + 1));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 1000)
    public void pushDevStateEvent() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        System.out.println("state "+ dev.state());
        final int id = dev.subscribe_event("state", TangoConst.USER_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        dev.command_inout("pushDevStateEvents");
        int eventsNb = 0;
        DevState value = DevState.UNKNOWN;
        try {
            while (eventsNb < 2) {
                final EventData[] events = dev.get_events(TangoConst.USER_EVENT);
                System.out.println("events data " + events.length);
                for (final EventData eventData : events) {
                    System.out.println("received event name =" + eventData.name);
                    System.out.println("received event type =" + eventData.event_type);
                    if (eventData.name.contains("state")) {
                        eventsNb ++;
                        value = eventData.attr_value.extractDevState();
                        System.out.println("received state event "+ value);
                        break;
                    }
                }
            }
            assertThat(value, equalTo( DevState.ALARM));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 1000)
    public void pushStateEvent() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        System.out.println("state "+ dev.state());
        final int id = dev.subscribe_event("state", TangoConst.USER_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        dev.command_inout("pushDeviceStateEvents");
        int eventsNb = 0;
        DevState value = DevState.UNKNOWN;
        try {
            while (eventsNb < 2) {
                final EventData[] events = dev.get_events(TangoConst.USER_EVENT);
                System.out.println("events data " + events.length);
                for (final EventData eventData : events) {
                    System.out.println("received event name =" + eventData.name);
                    System.out.println("received event type =" + eventData.event_type);
                    if (eventData.name.contains("state")) {
                        eventsNb ++;
                        value = eventData.attr_value.extractDevState();
                        System.out.println("received state event "+ value);
                        break;
                    }
                }
            }
            assertThat(value, equalTo( DevState.FAULT));
        } finally {
            dev.unsubscribe_event(id);
        }
    }



    @Test(timeout = 3000)
    public void changeStateScalar() throws DevFailed {
        System.out.println("\t####changeStateScalar");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("state", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        DevState value = DevState.UNKNOWN;
        DevState previousValue = DevState.UNKNOWN;
        try {
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                // System.out.println("events " + events.length);
                for (final EventData eventData : events) {
                    System.out.println("event " + eventData.name);
                    if (eventData.name.contains("state")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractDevState();
                    }
                }
            }
            System.out.println("assert");
            assertThat(value, not(previousValue));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeDevEncoded() throws DevFailed {
        System.out.println("\t####changeDevEncoded");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("devEncodedAttr", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        byte[] value = new byte[0];
        byte[] previousValue = new byte[0];
        try {
            while (eventsNb < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    System.out.println("devEncodedAttr event " + eventData.name);
                    if (eventData.name.contains("devencodedattr")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractDevEncoded().encoded_data;
                    }
                }
            }
            assertThat(value, not(previousValue));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeStateArray() throws DevFailed {
        System.out.println("\t####changeStateArray");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("stateArray", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        DevState[] value = new DevState[] { DevState.UNKNOWN };
        DevState[] previousValue = new DevState[] { DevState.UNKNOWN };
        try {
            while (eventsNb < 3) {

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("statearray")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.extractDevStateArray();
                    }
                }
            }
            assertThat(value[0], not(previousValue[0]));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeQuality() throws DevFailed {
        System.out.println("\t####changeQuality");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("qualityAtt", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        AttrQuality value = AttrQuality.ATTR_VALID;
        AttrQuality previousValue = AttrQuality.ATTR_VALID;
        try {
            while (eventsNb < 3) {

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("qualityatt")) {
                        eventsNb++;
                        previousValue = value;
                        value = eventData.attr_value.getQuality();
                    }
                }
            }
            assertThat(value, not(previousValue));
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(expected = DevFailed.class, timeout = 3000)
    public void error() throws DevFailed {
        System.out.println("\t####error");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("errorAtt", TangoConst.USER_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);

        try {
            while (true) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("erroratt")) {
                        eventData.attr_value.extractLong();
                    }
                }
            }
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    /**
     * Check that a change is detected when an error occurs
     * 
     * @throws DevFailed
     */
    @Test(timeout = 3000)
    public void changeErrorAppears() throws DevFailed {
        System.out.println("\t####changeErrorAppears");
        final DeviceProxy dev = new DeviceProxy(deviceName);

        final int id = dev.subscribe_event("error", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        boolean error = false;
        try {
            dev.read_attribute("error");
            final DeviceData dd = new DeviceData();
            int errorCode = 0;

            while (!error) {
                dd.insert(errorCode);
                dev.command_inout("setError", dd);
                errorCode++;
                dev.command_inout("pushError");
                try {
                    Thread.sleep(100);
                } catch (final InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    System.out.println("error received: " + eventData.name);
                    if (eventData.name.contains("error")) {
                        // XXX I don't known why: an error can be received in 2 different ways
                        if (eventData.err) {
                            final DevFailed e = new DevFailed(eventData.errors);
                            System.out.println("contains an error: " + DevFailedUtils.toString(e));
                            if (DevFailedUtils.toString(e).contains("error0")) {
                                error = true;
                            }
                        } else {
                            try {
                                eventData.attr_value.extractLong();
                            } catch (final DevFailed e) {
                                if (DevFailedUtils.toString(e).contains("error0")) {
                                    error = true;
                                }
                            }
                        }
                    }
                }

            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            dev.unsubscribe_event(id);
            // put back no error
            final DeviceData dd = new DeviceData();
            dd.insert(5);
            dev.command_inout("setError", dd);
        }

    }

    /**
     * Check that a change is detected when 2 different errors occur
     * 
     * @throws DevFailed
     */
    @Test(timeout = 3000)
    public void changeErrorChange() throws DevFailed {
        System.out.println("\t####changeErrorChange");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("error", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        boolean error1 = false;
        try {
            final DeviceData dd = new DeviceData();
            dd.insert(0);
            dev.command_inout("setError", dd);
            dev.command_inout("pushError");
            try {
                Thread.sleep(300);
            } catch (final InterruptedException e1) {
            }
            dd.insert(1);
            dev.command_inout("setError", dd);
            dev.command_inout("pushError");
            while (!error1) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.endsWith("error")) {
                        if (eventData.err) {

                            final DevFailed e = new DevFailed(eventData.errors);
                            System.out.println("received error " + DevFailedUtils.toString(e));
                            if (DevFailedUtils.toString(e).contains("error0")) {
                            } else if (DevFailedUtils.toString(e).contains("error1")) {
                                error1 = true;
                            }
                        }
                    }
                }

            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
        } finally {
            dev.unsubscribe_event(id);
            // put back no error
            final DeviceData dd = new DeviceData();
            dd.insert(5);
            dev.command_inout("setError", dd);
        }

    }

    /**
     * Check that a change is detected when an error disappear
     * 
     * @throws DevFailed
     */
    @Test(timeout = 3000)
    public void changeErrorDisappear() throws DevFailed {
        System.out.println("\t####changeErrorDisappear");
        final DeviceProxy dev = new DeviceProxy(deviceName);

        final int id = dev.subscribe_event("error", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);

        boolean disappear = false;
        try {

            final DeviceData dd = new DeviceData();
            dd.insert(0);
            dev.command_inout("setError", dd);
            try {
                Thread.sleep(300);
            } catch (final InterruptedException e1) {
            }
            dd.insert(5);
            dev.command_inout("setError", dd);

            while (!disappear) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.endsWith("error")) {
                        if (!eventData.err) {
                            disappear = true;
                        }
                    }
                }

            }
        } finally {
            dev.unsubscribe_event(id);
        }

    }

    @Test(timeout = 3000)
    public void user() throws DevFailed {
        System.out.println("\t####user");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("userEvent", TangoConst.USER_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);

        // read will send user event
        try {
            dev.command_inout("pushUserEvent");

            final EventData[] events = dev.get_events();
            for (final EventData eventData : events) {
                if (eventData.name.contains("userevent")) {
                    try {
                        final String value = eventData.attr_value.extractString();
                        assertThat(value, equalTo("Hello"));
                    } catch (final DevFailed e) {
                        DevFailedUtils.printDevFailed(e);
                        throw e;
                    }
                }
            }

        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 30000)
    // @Ignore
    public void dataReady() throws DevFailed {
        System.out.println("\t####dataReady");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("doubleArrayAtt", TangoConst.DATA_READY_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventCounter = 0;
        int value = 0;
        int previousValue = 0;
        try {
            while (eventCounter < 3) {
                dev.command_inout("pushDataReady");

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    System.out.println("data ready " + eventData.name);
                    if (eventData.name.contains("doublearrayatt")) {
                        previousValue = value;
                        if (eventData.data_ready != null) {
                            value = eventData.data_ready.ctr;
                            System.out.println("data ready " + value);
                        }

                    }
                }
                eventCounter++;
            }
            assertThat(value, equalTo(previousValue + 1));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            dev.unsubscribe_event(id);
        }
    }

    @Test(timeout = 3000)
    public void changeAttrConfig() throws DevFailed {
        System.out.println("\t####changeAttrConfig");
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final int id = dev.subscribe_event("doubleAtt", TangoConst.ATT_CONF_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);

        int eventCount = 0;
        String value = "";

        String previousValue = "-100";
        try {
            while (eventCount < 3) {
                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if ( eventData.name.contains("doubleatt")) {
                        previousValue = value;
                       if(eventData.event_type == EventType.ATT_CONF_EVENT.getValue()) {
                           value = eventData.attr_config.events.arch_event.abs_change;
                           System.out.println(eventCount+ " read value " + value);
                           eventCount++;
                       }
                    }
                }
            }
            assertThat(Double.parseDouble(value), equalTo(Double.parseDouble(previousValue) + 1));

        } finally {
            dev.unsubscribe_event(id);
        }
    }
}
