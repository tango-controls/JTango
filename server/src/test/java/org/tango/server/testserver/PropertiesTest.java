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
package org.tango.server.testserver;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.AttributeInfo;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DbDatum;
import fr.esrf.TangoApi.DeviceData;
import fr.esrf.TangoApi.DeviceProxy;
import org.junit.*;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration tests with tango db
 *
 * @author ABEILLE
 */
@Ignore
public class PropertiesTest {
    private static String deviceName = "test/tango/jtangotest.1";

    @BeforeClass
    public static void createDeviceInTangoDB() throws DevFailed {
        System.out.println("Tango host = " + System.getProperty("TANGO_HOST"));
        assertThat(System.getProperty("TANGO_HOST"), notNullValue());
        Database tangoDb = ApiUtil.get_db_obj();
        tangoDb.add_device(deviceName, JTangoTest.class.getCanonicalName(), JTangoTest.SERVER_NAME + "/" + JTangoTest.INSTANCE_NAME);
    }

    @Test
    public void testProperty() throws DevFailed {
        try {
            final Database db = ApiUtil.get_db_obj();
            final DbDatum[] dbDatum = new DbDatum[1];

            final Double d = new Double(Math.random());
            final String propValue = d.toString();

            final String propName = "myProp";

            dbDatum[0] = new DbDatum(propName, propValue);

            db.put_device_property(deviceName, dbDatum);

            connect();
            final DeviceProxy dev = new DeviceProxy(deviceName);
            DeviceData devda = dev.command_inout("getMyProperty");
            String value = devda.extractString();

            Assert.assertEquals(propValue, value);
            db.delete_device_property(deviceName, propName);
            dev.command_inout("Init");
            devda = dev.command_inout("getMyProperty");
            value = devda.extractString();
            Assert.assertEquals("default", value);
        } catch (DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

    @Test
    public void testClassProperty() throws DevFailed {
        final Database db = ApiUtil.get_db_obj();
        final DbDatum[] dbDatum = new DbDatum[1];

        final Double dd = new Double(Math.random());
        final String propClassValue = dd.toString();
        final String propClassName = "myClassProp";

        dbDatum[0] = new DbDatum(propClassName, propClassValue);

        db.put_class_property(JTangoTest.class.getCanonicalName(), dbDatum);

        connect();

        final DeviceProxy dev = new DeviceProxy(deviceName);
        DeviceData devdaClass = dev.command_inout("getMyClassProperty");

        String[] valueClass = devdaClass.extractStringArray();

        Assert.assertArrayEquals(new String[]{propClassValue}, valueClass);

        db.delete_class_property(JTangoTest.class.getCanonicalName(), propClassName);

        dev.command_inout("Init");
        devdaClass = dev.command_inout("getMyClassProperty");
        valueClass = devdaClass.extractStringArray();
        Assert.assertArrayEquals(new String[]{"classDefault"}, valueClass);
    }

    @Test
    public void testAttributeProperty() throws DevFailed {
        connect();
        final Double dd = new Double(Math.random());
        final String value = dd.toString();
        final String attrName = "shortScalar";
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final AttributeInfo info = dev.get_attribute_info(attrName);
        info.description = value;
        dev.set_attribute_info(new AttributeInfo[]{info});
        final String actualValue = dev.get_attribute_info(attrName).description;
        Assert.assertEquals(value, actualValue);
    }

    public void connect() throws DevFailed {
        JTangoTest.start();
    }

    @After
    public void disconnect() throws DevFailed {
        ServerManager.getInstance().stop();
    }
}
