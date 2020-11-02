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
import fr.esrf.TangoApi.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Integration tests with tango db
 *
 * @author ABEILLE
 */
public class PropertiesTest {
    private static final String deviceName = "test/tango/jtangotest.1";
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

            final DeviceProxy dev = getDeviceProxy();
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

        final DeviceProxy dev = getDeviceProxy();

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
        final Double dd = new Double(Math.random());
        final String value = dd.toString();
        final String attrName = "shortScalar";
        final DeviceProxy dev = getDeviceProxy();
        final AttributeInfo info = dev.get_attribute_info(attrName);
        info.description = value;
        dev.set_attribute_info(new AttributeInfo[]{info});
        final String actualValue = dev.get_attribute_info(attrName).description;
        Assert.assertEquals(value, actualValue);
    }

    @Test
    public void testDbDynamicAttributePropertyRead() throws DevFailed {
        int defaultDynamicAttrPropertiesSize = 2;
        List<String> dynamicAttribute = Arrays.asList("StringDynamic",
                "String[]Dynamic",
                "doubleDynamic",
                "double[]Dynamic",
                "intDynamic",
                "int[]Dynamic",
                "floatDynamic",
                "float[]Dynamic",
                "shortDynamic",
                "short[]Dynamic",
                "longDynamic",
                "long[]Dynamic",
                "byteDynamic",
                "byte[]Dynamic",
                "booleanDynamic");

        final DeviceProxy dev = getDeviceProxy();
        for (String attr : dev.get_attribute_list()) {
            if (dynamicAttribute.contains(attr)) {
                DbAttribute dbAttr = dev.get_attribute_property(attr);
                assertEquals(defaultDynamicAttrPropertiesSize, dbAttr.size());
            }
        }
    }

    @Test
    public void testDbDynamicAttributePropertyAddAndDelete() throws DevFailed {
        final DeviceProxy dev = getDeviceProxy();

        String attributeName = "doubleScalar";
        String testPropertyName = "testProperty";
        String testPropertyValue = "exampleValue";

        updateDbAttributeProperty(dev, attributeName, testPropertyName, testPropertyValue);
        Assert.assertEquals(testPropertyValue,
                dev.get_attribute_property(attributeName).get_string_value(testPropertyName));

        cleanupDbAttributeProperty(dev, attributeName, testPropertyName);

        String attributeName2 = "longScalar";
        String testPropertyName2 = "testPropertyForLongScalar";
        double testPropertyValue2 = 1.2;

        DbAttribute dbAttr1 = updateDbAttributeProperty(dev, attributeName, testPropertyName, testPropertyValue);
        DbAttribute dbAttr2 = updateDbAttributeProperty(dev, attributeName2, testPropertyName2, testPropertyValue2);

        dev.put_attribute_property(new DbAttribute[] {dbAttr1, dbAttr2});
        DbAttribute[] dbAttributes = dev.get_attribute_property(new String[] {attributeName, attributeName2});

        assertEquals(2, dbAttributes.length);
        assertEquals(testPropertyValue, dbAttributes[0].get_string_value(testPropertyName));
        assertEquals(testPropertyValue2, dbAttributes[1].datum(testPropertyName2).extractDouble(), 0.0);

        cleanupDbAttributeProperty(dev, attributeName, testPropertyName);
        cleanupDbAttributeProperty(dev, attributeName2, testPropertyName2);
    }

    private DbAttribute updateDbAttributeProperty(DeviceProxy dev, String attributeName,
                                                  String propName, Object propValue) throws DevFailed {
        DbAttribute dbAttr = dev.get_attribute_property(attributeName);
        dbAttr.add(propName, String.valueOf(propValue));
        dev.put_attribute_property(dbAttr);

        return dbAttr;
    }

    private void cleanupDbAttributeProperty(DeviceProxy dev, String attributeName, String propName) throws DevFailed {
        dev.delete_attribute_property(attributeName, propName);
        Assert.assertEquals(0, dev.get_attribute_property(propName).size());
    }

    public void connect() throws DevFailed {
        JTangoTest.start();
    }

    public DeviceProxy getDeviceProxy() throws DevFailed {
        connect();
        return new DeviceProxy(deviceName);
    }

    @After
    public void disconnect() throws DevFailed {
        ServerManager.getInstance().stop();
    }
}
