package org.tango.client.database;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esrf.Tango.DevFailed;

public class FileTangoDbTest {

    private static ITangoDB db;

    @BeforeClass
    public static void loadFile() throws DevFailed {
        final String filePath = FileTangoDbTest.class.getResource("/noDbproperties.txt").getPath();
        final File file = new File(filePath);
        DatabaseFactory.setDbFile(file, new String[] { "" }, "");
        db = DatabaseFactory.getDatabase();
    }

    @Test
    public void testDeviceProperties() throws DevFailed {
        final Map<String, String[]> deviceProper = db.getDeviceProperties("archiving/hdb-oracle/hdbarchiver.01");
        for (final String[] deviceProps : deviceProper.values()) {
            System.out.println(Arrays.toString(deviceProps));
        }
        assertArrayEquals(deviceProper.get("DbHost"), new String[] { "ORION" });
        assertArrayEquals(deviceProper.get("diaryPath"), new String[] { "/tmp/archivage/tango/hdbarchiver/diary/01" });
        assertArrayEquals(deviceProper.get("hasDiary"), new String[] { "false" });
        assertArrayEquals(deviceProper.get("reservedAttributes"), new String[] { "tango/tangotest/1/float_scalar",
                "tango/tangotest/1/double_scalar", "tango/tangotest/2/double_scalar" });
    }

    @Test
    public void testClassProperties() throws DevFailed {
        final Map<String, String[]> prop1 = db.getClassProperties("HdbArchiver", "CommitPeriodInMinute");
        assertArrayEquals(prop1.get("CommitPeriodInMinute"), new String[] { "5" });

        final Map<String, String[]> prop2 = db.getClassProperties("HdbArchiver", "DbONSConf");
        assertArrayEquals(prop2.get("DbONSConf"), new String[] { "thalie:6200,euterpe:6200,calliope:6200" });

        final Map<String, String[]> prop3 = db.getClassProperties("HdbArchiver", "Description");
        assertArrayEquals(prop3.get("Description"), new String[] { "Device of Archiving system" });

        final Map<String, String[]> prop4 = db.getClassProperties("HdbArchiver", "RacConnection");
        assertArrayEquals(prop4.get("RacConnection"), new String[] { "false" });

        final Map<String, String[]> prop5 = db.getClassProperties("HdbArchiver", "shortPeriodAttributes");
        assertArrayEquals(prop5.get("shortPeriodAttributes"), new String[] { "tango/tangotest/1/float_scalar,1",
                "tango/tangotest/1/double_scalar,1", "tango/tangotest/2/double_scalar,1" });
    }

    @Test
    public void deleteDeviceProp() throws DevFailed {
        db.deleteDeviceProperty("archiving/hdb-oracle/hdbarchiver.01", "DbHost");
        final Map<String, String[]> prop = db.getDeviceProperties("archiving/hdb-oracle/hdbarchiver.01", "DbHost");
        // put it back for other tests
        final Map<String, String[]> back = new HashMap<String, String[]>();
        back.put("DbHost", new String[] { "ORION" });
        db.setDeviceProperties("archiving/hdb-oracle/hdbarchiver.01", back);
        assertArrayEquals(prop.get("DbHost"), new String[] {});
    }

    @After
    @Test
    public void testAttrProp() throws DevFailed {
        final Map<String, String[]> prop = new HashMap<String, String[]>();
        prop.put("prop", new String[] { "toto" });
        db.setAttributeProperties("archiving/hdb-oracle/hdbarchiver.01", "attr1", prop);
        final Map<String, String[]> prop2 = db.getAttributeProperties("archiving/hdb-oracle/hdbarchiver.01", "attr1");
        assertArrayEquals(prop2.get("prop"), new String[] { "toto" });
        db.deleteAttributeProperties("archiving/hdb-oracle/hdbarchiver.01", "attr1");
        final Map<String, String[]> prop3 = db.getAttributeProperties("archiving/hdb-oracle/hdbarchiver.01", "attr1");
        assertArrayEquals(prop3.get("prop"), null);
    }
}
