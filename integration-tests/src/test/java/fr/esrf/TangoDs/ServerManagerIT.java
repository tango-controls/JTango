package fr.esrf.TangoDs;

import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.tango.it.ITWithoutTangoDB;
import org.tango.server.export.TangoExporter;
import org.tango.server.servant.DeviceImpl;
import org.tango.server.testserver.JTangoTest;
import tango.it.helpers.JTangoTestServerManager;
import tango.it.runner.ITWithoutDBRunner;

@Category(ITWithoutTangoDB.class)
public class ServerManagerIT extends ITWithoutDBRunner {

    private static final JTangoTestServerManager testServerManager = new JTangoTestServerManager();
    private static final String TANGO_CLASS = "org.tango.server.testserver.JTangoTest";

    @Test
    public void testGetDeviceImpl() throws DevFailed, NoSuchFieldException, IllegalAccessException {
        DeviceImpl deviceImp = testServerManager.getDefaultDeviceImp(TANGO_CLASS, JTangoTest.DEFAULT_NO_DB_DEVICE_NAME);
        Assert.assertNotNull(deviceImp);
        Assert.assertNotNull(deviceImp.description());
        Assert.assertNotNull(testServerManager.getAdminDeviceName(), deviceImp.adm_name());
        Assert.assertNotNull(deviceImp.info());
        Assert.assertNotNull(deviceImp.info_3());

        DevCmdInfo[] cmdList = deviceImp.command_list_query();
        Assert.assertTrue(cmdList.length > 0);

        for (DevCmdInfo cmd : cmdList) {
            DevCmdInfo newCmd = deviceImp.command_query(cmd.cmd_name);
            Assert.assertNotNull(newCmd);
        }
    }

    @Test
    public void testGetDeviceImplThatNotExist() {
        Assert.assertThrows(DevFailed.class, () -> testServerManager.getDefaultDeviceImp(TANGO_CLASS,
                "bad/device/name"));
    }

    @Test
    public void testTangoExporter() throws NoSuchFieldException, IllegalAccessException, DevFailed {
        System.out.println("RUN: ITWithoutTangoDB");

        TangoExporter tangoExporter = testServerManager.getTangoExporter();

        String[] tangoDeviceForClass = tangoExporter.getDevicesOfClass(TANGO_CLASS);
        Assert.assertEquals(JTangoTest.DEFAULT_NO_DB_DEVICE_NAME, tangoDeviceForClass[0]);
        System.out.println("test");

        // TODO Start/Stop new device
    }
}