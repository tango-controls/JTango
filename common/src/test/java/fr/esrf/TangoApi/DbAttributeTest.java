package fr.esrf.TangoApi;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DbAttributeTest {

    public DbAttribute testObject;

    @Before
    public void setup() {
        testObject = new DbAttribute("testAttribute");
        testObject.add("emptyDatum");
        testObject.add("datumInt", 1);
        testObject.add("datumString", "test");
        testObject.add("datumShort", (short) 1);
        testObject.add("datumDouble", 1d);
        testObject.add("datumStringArray", new String[]{"test1", "test2", "test3"});
        testObject.add("datumShortArray", new short[]{(short)1, (short)2, (short)3});
        testObject.add("datumIntArray", new int[]{1, 2, 3});
        testObject.add("datumDoubleArray", new double[] {1d, 2d, 3d});
    }

    @Test
    public void testGetDbDatum() {
        int i = 0;
        for (String prop: testObject.get_property_list()) {
            DbDatum dbDatum = testObject.datum(i);
            DbDatum dbDatumToCompere = testObject.datum(prop);

            Assert.assertEquals(dbDatum, dbDatumToCompere);
            i++;
        }
    }

    @Test
    public void testGetDbDatumValue() {
        for (int i = 0; i < testObject.size(); i++) {
            DbDatum dbDatum = testObject.datum(i);
            String[] value = testObject.get_value(i);
            String[] value2 = testObject.get_value(dbDatum.name);

            Assert.assertArrayEquals(dbDatum.extractStringArray(), value);
            Assert.assertArrayEquals(dbDatum.extractStringArray(), value2);
        }
    }

    @Test
    public void testGetPropertyName() {
        for (int i = 0; i < testObject.size(); i++) {
            DbDatum dbDatum = testObject.datum(i);
            String propertyName = testObject.get_property_name(i);

            Assert.assertEquals(dbDatum.name, propertyName);
        }
    }

    @Test
    public void testGetStringValue() {
        for (int i = 0; i < testObject.size(); i++) {
            DbDatum dbDatum = testObject.datum(i);

            String stringValue = testObject.get_string_value(i);
            String stringValue2 = testObject.get_string_value(dbDatum.name);

            String originalString = getOriginalString(dbDatum.extractStringArray());

            Assert.assertEquals(originalString, stringValue);
            Assert.assertEquals(stringValue, stringValue2);
        }
    }

    @Test
    public void testIsEmpty() {
        for (String prop: testObject.get_property_list()) {
            Assert.assertFalse(testObject.is_empty(prop));
        }
    }

    @Test
    public void testRemoveDbAttribute() {
        for (String prop: testObject.get_property_list()) {
            DbDatum db = testObject.datum(prop);
            testObject.remove(db);

            Assert.assertNull(testObject.get_string_value(db.name));
            Assert.assertNull(testObject.datum(db.name));
            Assert.assertTrue(testObject.is_empty(db.name));
        }
    }

    private String getOriginalString(String[] array) {
        if (array.length == 1) {
            return array[0];
        } else {
            return String.join("\n", array);
        }
    }

}
