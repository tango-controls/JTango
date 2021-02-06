package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.tango.it.ITWithTangDB;
import org.tango.server.testserver.JTangoTest;
import org.tango.utils.DevFailedUtils;
import tango.it.runner.ITWithDBRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@Category(ITWithTangDB.class)
public class DBPropertiesTest extends ITWithDBRunner {

    @Test
    public void testProperty() throws DevFailed {
        try {
            final DbDatum[] dbDatum = new DbDatum[1];
            final String propValue = getRandomProp();
            final String propName = "myProp";
            dbDatum[0] = new DbDatum(propName, propValue);

            tangoDatabase.put_device_property(getDBDeviceName(), dbDatum);

            final DeviceProxy dev = getDeviceProxy();
            DeviceData devda = dev.command_inout("getMyProperty");
            String value = devda.extractString();

            Assert.assertEquals(propValue, value);
            tangoDatabase.delete_device_property(getDBDeviceName(), propName);
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
        final DbDatum[] dbDatum = new DbDatum[1];

        final String propClassValue = getRandomProp();
        final String propClassName = "myClassProp";

        dbDatum[0] = new DbDatum(propClassName, propClassValue);
        tangoDatabase.put_class_property(JTangoTest.class.getCanonicalName(), dbDatum);

        final DeviceProxy dev = getDeviceProxy();
        DeviceData devdaClass = dev.command_inout("getMyClassProperty");
        String[] valueClass = devdaClass.extractStringArray();

        Assert.assertArrayEquals(new String[]{propClassValue}, valueClass);

        tangoDatabase.delete_class_property(JTangoTest.class.getCanonicalName(), propClassName);

        dev.command_inout("Init");
        devdaClass = dev.command_inout("getMyClassProperty");
        valueClass = devdaClass.extractStringArray();
        Assert.assertArrayEquals(new String[]{"classDefault"}, valueClass);
    }

    @Test
    public void testAttributeProperty() throws DevFailed {
        final String value = getRandomProp();
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

        dev.put_attribute_property(new DbAttribute[]{dbAttr1, dbAttr2});
        DbAttribute[] dbAttributes = dev.get_attribute_property(new String[]{attributeName, attributeName2});

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

    private String getRandomProp() {
        return Double.toString(Math.random());
    }

}
