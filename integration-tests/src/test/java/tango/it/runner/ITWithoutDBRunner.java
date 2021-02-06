package tango.it.runner;

import fr.esrf.Tango.DevFailed;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.tango.it.ITWithoutTangoDB;
import org.tango.it.manager.NoDBDeviceManager;

import java.io.IOException;
import java.util.List;

public class ITWithoutDBRunner {

    private static ITWithoutTangoDB manager;

    private static void init() {
        if (manager == null) {
            manager = new NoDBDeviceManager();
        } else {
            System.out.println("Manager was already init!");
        }
    }

    @BeforeClass
    public static void start() throws IOException, DevFailed {
        start(null);
    }

    protected static void start(String deviceList) throws IOException, DevFailed {
        init();
        manager.startDevices(deviceList);
    }

    @AfterClass
    public static void stop() throws DevFailed {
        manager.stopDevices();
    }

    public static String getDefaultDeviceFullName() {
        return manager.getDefaultDeviceFullName();
    }

    public static String getFullAdminName() {
        return manager.getFullAdminName();
    }

    public static List<String> getDeviceFullNameList() {
        return manager.getDeviceFullNameList();
    }
}
