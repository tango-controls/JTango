package fr.esrf.TangoApi;


import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class DatabaseDAOTest {

    private static Database database;
    private static IDatabaseDAO dao;
    private static final String SERVICE_TEST_NAME = "TestService";
    private static final String SERVICE_ONE_INSTANCE_TEST_NAME = "TestServiceInstance1";
    private static final String SERVICE_TWO_INSTANCE_TEST_NAME = "TestServiceInstance2";
    private static final String DEV_ONE_TEST_NAME = "test/tango/1";
    private static final String DEV_TWO_TEST_NAME = "test/tango/2";

    @BeforeClass
    public static void setup() throws DevFailed {
        database = ApiUtil.get_db_obj();
        dao = database.getDatabaseDAO();
    }

    @Test
    public void registerOneServiceTest() throws DevFailed {
        dao.registerService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_ONE_TEST_NAME);
        String[] test = dao.getServices(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME);
        Assert.assertEquals(1, test.length);
        Assert.assertEquals(DEV_ONE_TEST_NAME, test[0]);

        dao.unregisterService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_ONE_TEST_NAME);
        test = dao.getServices(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME);
        Assert.assertEquals(0, test.length);
    }

    @Test
    public void registerMultiDeviceTest() throws DevFailed {
        dao.registerService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_ONE_TEST_NAME);
        String[] test = dao.getServices(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME);
        Assert.assertEquals(1, test.length);
        Assert.assertEquals(DEV_ONE_TEST_NAME, test[0]);

        dao.registerService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_TWO_TEST_NAME);
        test = dao.getServices(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME);
        Assert.assertEquals(1, test.length);
        Assert.assertEquals(DEV_TWO_TEST_NAME, test[0]);

        dao.unregisterService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_ONE_TEST_NAME);
        dao.unregisterService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_TWO_TEST_NAME);
    }

    @Test
    public void registerMultiServiceTest() throws DevFailed {
        dao.registerService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_ONE_TEST_NAME);
        dao.registerService(database, SERVICE_TEST_NAME, SERVICE_TWO_INSTANCE_TEST_NAME, DEV_TWO_TEST_NAME);


        String[] test = dao.getServices(database, SERVICE_TEST_NAME, "*");
        Assert.assertEquals(2, test.length);
        Assert.assertTrue(existInArray(test, DEV_ONE_TEST_NAME));
        Assert.assertTrue(existInArray(test, DEV_TWO_TEST_NAME));

        dao.unregisterService(database, SERVICE_TEST_NAME, SERVICE_ONE_INSTANCE_TEST_NAME, DEV_ONE_TEST_NAME);
        dao.unregisterService(database, SERVICE_TEST_NAME, SERVICE_TWO_INSTANCE_TEST_NAME, DEV_TWO_TEST_NAME);
    }

    @Test
    public void getDeviceTest() throws DevFailed {
        String[] devices = dao.getDevices(database, "sys/*/*");

        Assert.assertTrue(devices.length >= 2);
        Assert.assertTrue(existInArray(devices, "sys/database/2"));
        Assert.assertTrue(existInArray(devices, "sys/access_control/1"));
    }

    @Test
    public void getDatabaseInfo() throws DevFailed {
        Assert.assertNotNull(dao.get_info(database));
    }

    private boolean existInArray(String[] array, String val) {
        return Arrays.asList(array).contains(val);
    }
}
