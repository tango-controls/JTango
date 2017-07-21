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

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.tango.server.ServerManager;

/**
 * properties tests
 * 
 * @author ABEILLE
 * 
 */
//TODO move to integration tests (start db)
@Ignore
public class PropertiesTest {
    // XXX: device must be declared in tango db before running this test
    private static String deviceName = "test/tango/jtangotest.1";

    @Test
    public void testProperty() throws DevFailed {
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
        db.delete_device_property("test/tango/jtangotest.1", propName);
        dev.command_inout("Init");
        devda = dev.command_inout("getMyProperty");
        value = devda.extractString();
        Assert.assertEquals("default", value);
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

        Assert.assertArrayEquals(new String[] { propClassValue }, valueClass);

        db.delete_class_property(JTangoTest.class.getCanonicalName(), propClassName);

        dev.command_inout("Init");
        devdaClass = dev.command_inout("getMyClassProperty");
        valueClass = devdaClass.extractStringArray();
        Assert.assertArrayEquals(new String[] { "classDefault" }, valueClass);
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
        dev.set_attribute_info(new AttributeInfo[] { info });
        final String actualValue = dev.get_attribute_info(attrName).description;
        Assert.assertEquals(value, actualValue);
    }

    public void connect() throws DevFailed {
        // assertThat(System.getProperty("TANGO_HOST"), notNullValue());
        JTangoTest.start();
    }

    @After
    public void disconnect() throws DevFailed {
        ServerManager.getInstance().stop();
    }
}
