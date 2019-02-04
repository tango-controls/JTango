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
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.tango.client.database.DatabaseFactory;
import org.tango.client.database.ITangoDB;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * Test properties without tango db
 * 
 * @author ABEILLE
 * 
 */
public class NoDBPropertiesTest extends NoDBDeviceManager {

    @Test
    public void testFileProperty() throws DevFailed, IOException {
        stopDevice();
        ServerSocket ss1 = null;
        try {
            ss1 = new ServerSocket(0);
            ss1.setReuseAddress(true);
            ss1.close();
            JTangoTest.startNoDbFile(ss1.getLocalPort());
        } finally {
            if (ss1 != null) {
                ss1.close();
            }
        }
        deviceName = "tango://localhost:" + ss1.getLocalPort() + "/" + JTangoTest.NO_DB_DEVICE_NAME + "#dbase=no";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final DeviceData devda = dev.command_inout("getMyProperty");

        final String value = devda.extractString();
        Assert.assertEquals(value, "titi");
    }

    @Test
    public void testProperty() throws DevFailed {
        final ITangoDB db = DatabaseFactory.getDatabase();

        final Double d = new Double(Math.random());
        final String propValue = d.toString();

        final String propName = "myProp";

        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(propName, new String[] { propValue });
        db.setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, map);

        final DeviceProxy dev = new DeviceProxy(deviceName);
        dev.command_inout("Init");
        DeviceData devda = dev.command_inout("getMyProperty");

        String value = devda.extractString();

        Assert.assertEquals(propValue, value);

        map = new HashMap<String, String[]>();
        map.put(propName, new String[] { "" });
        db.setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, map);

        dev.command_inout("Init");
        devda = dev.command_inout("getMyProperty");
        value = devda.extractString();
        Assert.assertEquals("default", value);
    }

    @Test
    public void testbooleanProperty() throws DevFailed {
        final ITangoDB db = DatabaseFactory.getDatabase();

        final String propValue = "true";

        final String propName = "booleanProp";

        final Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(propName, new String[] { propValue });
        db.setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, map);

        final DeviceProxy dev = new DeviceProxy(deviceName);
        dev.command_inout("Init");
        final DeviceData devda = dev.command_inout("isBooleanProp");

        final boolean value = devda.extractBoolean();

        Assert.assertEquals(true, value);
    }

    @Test
    public void testbooleanNumProperty() throws DevFailed {
        final ITangoDB db = DatabaseFactory.getDatabase();

        final String propValue = "1";

        final String propName = "booleanProp";

        final Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(propName, new String[] { propValue });
        db.setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, map);

        final DeviceProxy dev = new DeviceProxy(deviceName);
        dev.command_inout("Init");
        final DeviceData devda = dev.command_inout("isBooleanProp");

        final boolean value = devda.extractBoolean();

        Assert.assertEquals(true, value);
    }

    @Test
    public void testClassProperty() throws DevFailed {
        final ITangoDB db = DatabaseFactory.getDatabase();

        final Double dd = new Double(Math.random());
        final String propClassValue = dd.toString();
        final String propClassName = "myClassProp";

        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(propClassName, new String[] { propClassValue });
        db.setClassProperties(JTangoTest.class.getCanonicalName(), map);

        final DeviceProxy dev = new DeviceProxy(deviceName);
        dev.command_inout("Init");
        DeviceData devdaClass = dev.command_inout("getMyClassProperty");

        String[] valueClass = devdaClass.extractStringArray();

        Assert.assertArrayEquals(new String[] { propClassValue }, valueClass);

        map = new HashMap<String, String[]>();
        map.put(propClassName, new String[] { "" });
        db.setClassProperties(JTangoTest.class.getCanonicalName(), map);

        dev.command_inout("Init");
        devdaClass = dev.command_inout("getMyClassProperty");
        valueClass = devdaClass.extractStringArray();
        Assert.assertArrayEquals(new String[] { "classDefault" }, valueClass);
    }

    @Test
    public void testEmptyArrayProperty() throws DevFailed{
        final DeviceProxy dev = new DeviceProxy(deviceName);
        DeviceData devdaClass = dev.command_inout("getEmptyArrayProperty");
        String[] valueClass = devdaClass.extractStringArray();
        System.out.println("test array "+ Arrays.toString(valueClass));
        Assert.assertArrayEquals(new String[0], valueClass);
    }

    @Test
    public void testAttributeProperty() throws DevFailed {
        try {
            final Double dd = new Double(Math.random());
            final String value = dd.toString();
            final String attrName = "shortScalar";
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final AttributeInfoEx info = dev.get_attribute_info_ex(attrName);
            info.description = value;
            dev.set_attribute_info(new AttributeInfoEx[] { info });
            final String actualValue = dev.get_attribute_info_ex(attrName).description;
            Assert.assertEquals(value, actualValue);
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testDefaultAttributeProperty() throws DevFailed {
        final String attrName = "longSpectrum";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final AttributeInfoEx info = dev.get_attribute_info_ex(attrName);
        assertThat(info.alarms.min_alarm, equalTo("0"));
        assertThat(info.alarms.max_alarm, equalTo("10"));
        assertThat(info.min_value, equalTo("-100"));
        assertThat(info.max_value, equalTo("1015054014654325L"));
        assertThat(info.alarms.min_warning, equalTo("3"));
        assertThat(info.alarms.max_warning, equalTo("4"));
        assertThat(info.alarms.delta_t, equalTo("10"));
        assertThat(info.alarms.delta_val, equalTo("20"));
        assertThat(info.description, equalTo("test"));
        Assert.assertArrayEquals(info.enum_label, new String[] { "Not specified" });
        // test IDL5 properties
        final AttributeInfoEx info2 = dev.get_attribute_info_ex("enumAttribute");
        Assert.assertArrayEquals(info2.enum_label, new String[] { "VALUE1", "VALUE2" });
    }
}
