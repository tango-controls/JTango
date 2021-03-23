package org.tango.it.manager;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import org.tango.it.ITWithTangDB;
import org.tango.server.ServerManager;
import org.tango.server.testserver.JTangoTest;

public class DBDeviceManager implements ITWithTangDB {

    private static final String DEFAULT_DEVICE_NAME = "test/tango/jtangotest.1";
    private boolean deviceIsRunning = false;
    private String deviceName;

    @Override
    public void addDevice(String deviceName) throws DevFailed {
        this.deviceName = deviceName != null ? deviceName : DEFAULT_DEVICE_NAME;
        getDatabase().add_device(this.deviceName,
                JTangoTest.class.getCanonicalName(),
                JTangoTest.SERVER_NAME + "/" + JTangoTest.INSTANCE_NAME);
    }

    @Override
    public String getDBDeviceName() {
        return deviceName;
    }

    @Override
    public Database getDatabase() throws DevFailed {
        return ApiUtil.get_db_obj();
    }

    @Override
    public void removeDevice() throws DevFailed {
        Database tangoDb = ApiUtil.get_db_obj();
        ServerManager.getInstance().stop();
        tangoDb.delete_device(this.deviceName);
        tangoDb.delete_server(JTangoTest.SERVER_NAME + "/" + JTangoTest.INSTANCE_NAME);
    }

    @Override
    public void startTestDevice() throws DevFailed {
        if (!deviceIsRunning) {
            JTangoTest.start();
            deviceIsRunning = true;
        }
    }

    @Override
    public void stopTestDevice() throws DevFailed {
        if (deviceIsRunning) {
            ServerManager.getInstance().stop();
            deviceIsRunning = false;
        }
    }
}
