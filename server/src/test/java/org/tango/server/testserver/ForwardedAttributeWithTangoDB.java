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

import java.io.IOException;

import fr.esrf.Tango.DevState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceDataHistory;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.events.EventData;
import fr.esrf.TangoDs.TangoConst;

/**
 * TODO: test polling, locking, attribute props, several attributes on same device, error init
 * 
 * @author ABEILLE
 * 
 */
@Ignore("Tests need a tangdb")
public class ForwardedAttributeWithTangoDB {
    // XXX a device has to be declared in tangoddb
    private static String deviceNameRoot = "tango9/java/events.1";
    private static String deviceName = "tango9/java/forwarded.1";
    private static Process process;

    @BeforeClass
    public static void startDevice() throws DevFailed, IOException {
        System.setProperty("TANGO_HOST", "dev-el4-db1.ica.synchrotron-soleil.fr:20001");
        //System.out.println( System.getProperty("TANGO_HOST"));
        try {
            final String classpath = System.getProperty("java.class.path");
            final ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath,
                    EventServer.class.getCanonicalName(), "1");
            System.out.println("starting root device");
            process = pb.start();


            ForwardedAttributeTest.inheritIO(process.getInputStream(), System.out);
            ForwardedAttributeTest.inheritIO(process.getErrorStream(), System.err);

            try {
                Thread.sleep(5000);
            } catch (final InterruptedException e) {
            }

            final DeviceProxy devRoot = new DeviceProxy(deviceNameRoot);
            System.out.println(devRoot.state());
            System.out.println(devRoot.status());
            System.out.println(" root device started");
            ForwardedServer.setNoDbFwdAttributeName(deviceNameRoot +"/doubleAtt");
            ForwardedServer.setNoDbFwdAttributeName2(deviceNameRoot +"/doubleArrayAtt");
            ForwardedServer.setNoDbFwdAttributeName3(deviceNameRoot +"/userEvent");
            ServerManager.getInstance().addClass("ForwardedServer", ForwardedServer.class);
            ServerManager.getInstance().startError(new String[] { ForwardedServer.INSTANCE_NAME }, "ForwardedServer");
            try {
                Thread.sleep(5000);
            } catch (final InterruptedException e) {
            }
            final DeviceProxy dev = new DeviceProxy(deviceName);
            System.out.println(dev.state());
            System.out.println(dev.status());
            if(dev.state().equals(DevState.FAULT) ){
                throw  DevFailedUtils.newDevFailed(dev.status());
            }
            System.out.println("forwarded device started");
        } catch (final DevFailed e) {
            e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        process.destroy();
        ServerManager.getInstance().stop();
    }

    @Test
    public void attributeHistory() throws DevFailed {
        try {
            final DeviceProxy devRoot = new DeviceProxy(deviceNameRoot);
            final DeviceDataHistory[] histRoot = devRoot.attribute_history("doubleAtt");
            System.out.println("################root history " + histRoot.length);
            // get its history
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceDataHistory[] hist = dev.attribute_history("testfowarded");
            System.out.println("################history " + hist.length);
            for (final DeviceDataHistory element : hist) {
                // just extract to check that there is a value
                System.out.println(element.extractDouble());
            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            e.printStackTrace();
            throw e;
        }

    }

    @Test(timeout = 5000)
    public void userEvent() throws DevFailed{
        try {
        final DeviceProxy devRoot = new DeviceProxy(deviceNameRoot);
        final DeviceProxy dev = new DeviceProxy(deviceName);

        final int id = dev.subscribe_event("testfowarded3", TangoConst.USER_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        while (eventsNb < 3) {
            devRoot.command_inout("pushUserEvent");
            final EventData[] events = dev.get_events();
            for (final EventData eventData : events) {
                if (eventData.name.contains("testfowarded3")) {
                    eventsNb++;
                    final String value = eventData.attr_value.extractString();
                    System.out.println("######USER_EVENT value from event " + value);
                }
            }
        }
        dev.unsubscribe_event(id);
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            e.printStackTrace();
            throw e;
        }
    }

    @Test(timeout = 3000)
    public void changeEventTest() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);

        final int id = dev.subscribe_event("testfowarded", TangoConst.CHANGE_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        while (eventsNb < 3) {
            final EventData[] events = dev.get_events();
            for (final EventData eventData : events) {
                if (eventData.name.contains("testfowarded")) {
                    eventsNb++;
                    final double value = eventData.attr_value.extractDouble();
                    System.out.println("######CHANGE_EVENT value from event " + value);
                }
            }
        }
        dev.unsubscribe_event(id);
    }

    @Test(timeout = 30000)
    public void attConfEventTest() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);

        final int id = dev.subscribe_event("testfowarded", TangoConst.ATT_CONF_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        while (eventsNb < 3) {
            final EventData[] events = dev.get_events();
            for (final EventData eventData : events) {
                if (eventData.name.contains("testfowarded")) {
                    eventsNb++;
                    final String value = eventData.attr_config.events.arch_event.abs_change;
                    // be sure that there is a value
                    Double.valueOf(value);
                    System.out.println("######ATT_CONF_EVENT value from event " + value);
                }
            }
        }
        dev.unsubscribe_event(id);
    }

    @Test(timeout = 3000)
    public void periodicEventTest() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);

        final int id = dev.subscribe_event("testfowarded", TangoConst.PERIODIC_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventsNb = 0;
        while (eventsNb < 3) {
            final EventData[] events = dev.get_events();
           // System.out.println("has events " + events.length);
            for (final EventData eventData : events) {
                if (eventData.name.contains("testfowarded")) {
                    eventsNb++;
                    // previousValue = value;
                    final double value = eventData.attr_value.extractDouble();
                    System.out.println("######PERIODIC_EVENT value from event " + value);
                }
            }
        }
        dev.unsubscribe_event(id);
    }

    @Test(timeout = 3000)
    public void dataReady() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final DeviceProxy devRoot = new DeviceProxy(deviceNameRoot);
        final int id = dev.subscribe_event("testfowarded2", TangoConst.DATA_READY_EVENT, 100, new String[] {},
                TangoConst.NOT_STATELESS);
        int eventCounter = 0;
        try {
            while (eventCounter < 3) {
                devRoot.command_inout("pushDataReady");

                final EventData[] events = dev.get_events();
                for (final EventData eventData : events) {
                    if (eventData.name.contains("testfowarded2")) {
                        if (eventData.data_ready != null) {
                            final int value = eventData.data_ready.ctr;
                            System.out.println("######DATA_READY_EVENT " + value);
                            eventCounter++;
                        }
                    }
                }

            }
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        } finally {
            dev.unsubscribe_event(id);
        }
    }

}
