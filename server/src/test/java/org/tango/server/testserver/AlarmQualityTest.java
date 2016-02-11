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

import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.client.database.DatabaseFactory;
import org.tango.client.database.ITangoDB;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;

public class AlarmQualityTest extends NoDBDeviceManager {

    @BeforeClass
    public static void before() throws DevFailed {
        final ITangoDB db = DatabaseFactory.getDatabase();
        final String propName = "StateCheckAttrAlarm";
        final Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(propName, new String[] { "true" });
        db.setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, map);
        new DeviceProxy(deviceName).command_inout("Init");
    }

    @AfterClass
    public static void after() throws DevFailed {
        final ITangoDB db = DatabaseFactory.getDatabase();
        final String propName = "StateCheckAttrAlarm";
        final Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(propName, new String[] { "false" });
        db.setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, map);
        new DeviceProxy(deviceName).command_inout("Init");
    }

    @Test
    public void testAlarmSpectrum() throws DevFailed {
        final String attrName = "longSpectrum";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        ta.write(new long[] { -10, 1 });
        ta.read();
        // System.out.println(ta.getQuality().value());
        // System.out.println(DeviceState.toString(dev.state()));
        // System.out.println(dev.status());
        assertThat(ta.getQuality().value(), equalTo(AttrQuality.ATTR_ALARM.value()));
        assertThat(dev.state(), equalTo(DevState.ALARM));
        assertThat(dev.status(), anything("Alarm : Value too low for longSpectrum"));
    }

    @Test
    public void testAlarmScalar() throws DevFailed {
        final String attrName = "doubleScalar";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        ta.write(-10.0);
        ta.read();
        // System.out.println(ta.getQuality().value());
        // System.out.println(DeviceState.toString(dev.state()));
        // System.out.println(dev.status());
        assertThat(ta.getQuality().value(), equalTo(AttrQuality.ATTR_ALARM.value()));
        assertThat(dev.state(), equalTo(DevState.ALARM));
        assertThat(dev.status(), anything("Alarm : Value too low for doubleScalar"));
    }

    @Test
    public void testDeltaScalar() throws DevFailed {
        System.out.println("test deltaAttribute");
        final String attrName = "deltaAttribute";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        ta.write(-10.0);
        try {
            Thread.sleep(20);
        } catch (final InterruptedException e) {
        }
        // System.out.println(ta.getQuality().value());
        // System.out.println(DeviceState.toString(dev.state()));
        // System.out.println(dev.status());
        ta.read();
        assertThat(ta.getQuality().value(), equalTo(AttrQuality.ATTR_ALARM.value()));
        assertThat(dev.state(), equalTo(DevState.ALARM));
        assertThat(dev.status(), anything("Alarm : RDS (R-W delta) for doubleScalar"));
    }

    @Test
    public void testWarning() throws DevFailed {
        final String attrName = "longSpectrum";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        ta.write(new long[] { 4, 3 });
        ta.read();
        // System.out.println(ta.getQuality().value());
        // System.out.println(DeviceState.toString(dev.state()));
        // System.out.println(dev.status());
        assertThat(ta.getQuality().value(), equalTo(AttrQuality.ATTR_WARNING.value()));
        assertThat(dev.state(), equalTo(DevState.ALARM));
        assertThat(dev.status(), anything("Alarm : Value too high for longSpectrum"));

    }

    @Test
    public void testInvalid() throws DevFailed {
        final String attrName = "invalidQuality";
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        try {
            ta.read();
        } catch (final DevFailed e) {
            // ignore exception with quality invalid

        }
        // System.out.println(ta.getQuality().value());
        // System.out.println(DeviceState.toString(dev.state()));
        // System.out.println(dev.status());
        assertThat(ta.getQuality().value(), equalTo(AttrQuality.ATTR_INVALID.value()));
        // assertThat(dev.state(), equalTo(DevState.ALARM));
        // assertThat(dev.status(), anything("Alarm : Value too high for longSpectrum"));

    }

    @Test
    public void testInvalid2() throws DevFailed {
        final String attrName = "invalidQuality2";
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        try {
            ta.read();
        } catch (final DevFailed e) {
            // ignore exception with quality invalid

        }

        assertThat(ta.getQuality().value(), equalTo(AttrQuality.ATTR_INVALID.value()));

    }

    @Ignore("min max on big array is not performant, fonctionnality removed")
    @Test(expected = DevFailed.class)
    public void testMaxValueSpectrum() throws DevFailed {
        final String attrName = "longSpectrum";
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        ta.write(new long[] { -200, 120 });
    }

    @Test(expected = DevFailed.class)
    public void testMaxValueScalar() throws DevFailed {
        final String attrName = "doubleScalar";
        final TangoAttribute ta = new TangoAttribute(deviceName + "/" + attrName);
        ta.write(-200.0);
    }

}
