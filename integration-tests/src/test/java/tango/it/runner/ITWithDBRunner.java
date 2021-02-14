package tango.it.runner;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DeviceProxy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.tango.it.ITWithTangDB;
import org.tango.it.manager.DBDeviceManager;
import org.tango.server.ServerManager;
import org.tango.server.testserver.JTangoTest;

public class ITWithDBRunner {

    private static ITWithTangDB manager;
    public static Database tangoDatabase;

    private static void init() throws DevFailed {
        if (manager == null) {
            manager = new DBDeviceManager();
            tangoDatabase = manager.getDatabase();
        }
    }

    @BeforeClass
    public static void setup() throws DevFailed {
        setup(null);
    }

    private static void setup(String deviceName) throws DevFailed {
        System.out.println("Tango host = " + System.getProperty("TANGO_HOST"));
        Assert.assertNotNull(System.getProperty("TANGO_HOST"));
        Assert.assertFalse(Boolean.parseBoolean(System.getProperty("org.tango.server.checkalarms")));

        init();

        manager.addDevice(deviceName);
    }

    protected static String getDBDeviceName() {
        return manager.getDBDeviceName();
    }

    public DeviceProxy getDeviceProxy() throws DevFailed {
        manager.startTestDevice();
        return new DeviceProxy(getDBDeviceName());
    }

    @AfterClass
    public static void cleanup() throws DevFailed {
        manager.removeDevice();
    }

    @After
    public void stopDevice() throws DevFailed {
        manager.stopTestDevice();
    }
}
