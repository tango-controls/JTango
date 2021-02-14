package org.tango.server;

import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.tango.it.ITWithoutTangoDB;
import org.tango.server.build.DeviceClassBuilder;
import org.tango.server.export.TangoExporter;
import org.tango.server.servant.DeviceImpl;
import org.tango.server.testserver.JTangoTest;
import tango.it.helpers.JTangoTestServerManager;
import tango.it.runner.ITWithoutDBRunner;

import java.util.Arrays;
import java.util.List;

@Category(ITWithoutTangoDB.class)
public class ServerManagerIT extends ITWithoutDBRunner {

    private static final JTangoTestServerManager testServerManagerHelper = new JTangoTestServerManager();
    private static final String TANGO_CLASS = "org.tango.server.testserver.JTangoTest";

    @Test
    public void testServerManager() throws DevFailed, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        // when
        ServerManager serverManager = testServerManagerHelper.getServerManager();

        // then
        Assert.assertTrue(serverManager.isStarted());

        Assert.assertNotNull(serverManager.getPid());
        Assert.assertNotNull(serverManager.getExecName());
        Assert.assertNotNull(serverManager.getInstanceName());

        String[] devicesOfClass = serverManager.getDevicesOfClass(TANGO_CLASS);
        Arrays.stream(devicesOfClass).forEach(Assert::assertNotNull);

        // Check run/stop device
        // when
        serverManager.stopDevice(JTangoTest.DEFAULT_NO_DB_DEVICE_NAME);

        // then
        TangoExporter tangoExporter = testServerManagerHelper.getTangoExporter();
        DeviceClassBuilder devClassBuilder = getClassBuilderFromList(TANGO_CLASS,
                tangoExporter.getDeviceClassList());

        Assert.assertEquals(0, devClassBuilder.getDeviceImplList().size());

        // cleanup
        serverManager.startDevice(JTangoTest.DEFAULT_NO_DB_DEVICE_NAME, Class.forName(TANGO_CLASS));

        // then
        Assert.assertEquals(1, devClassBuilder.getDeviceImplList().size());

    }

    @Test
    public void testGetDeviceImpl() throws DevFailed, NoSuchFieldException, IllegalAccessException {
        // Check exported DeviceImpl
        // when
        DeviceImpl deviceImp = testServerManagerHelper.getDefaultDeviceImp(TANGO_CLASS, JTangoTest.DEFAULT_NO_DB_DEVICE_NAME);

        // then
        Assert.assertNotNull(deviceImp);
        Assert.assertNotNull(deviceImp.description());
        Assert.assertNotNull(testServerManagerHelper.getAdminDeviceName(), deviceImp.adm_name());
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
        Assert.assertThrows(DevFailed.class, () -> testServerManagerHelper.getDefaultDeviceImp(TANGO_CLASS,
                "bad/device/name"));
    }

    @Test
    public void testTangoExporter() throws NoSuchFieldException, IllegalAccessException, DevFailed, ClassNotFoundException {
        // Check exported device
        // when
        TangoExporter tangoExporter = testServerManagerHelper.getTangoExporter();

        // then
        List<DeviceClassBuilder> devClassBuilders = tangoExporter.getDeviceClassList();
        Assert.assertEquals(2, devClassBuilders.size());

        for (DeviceClassBuilder devClass : devClassBuilders) {
            Assert.assertEquals(1, devClass.getDeviceImplList().size());
        }

        String[] tangoDevicesForClass = tangoExporter.getDevicesOfClass(TANGO_CLASS);
        Assert.assertEquals(JTangoTest.DEFAULT_NO_DB_DEVICE_NAME, tangoDevicesForClass[0]);

        // Unexproting one device
        // when
        tangoExporter.unexportDevice(JTangoTest.DEFAULT_NO_DB_DEVICE_NAME);
        DeviceClassBuilder devClassBuilder = getClassBuilderFromList(TANGO_CLASS, devClassBuilders);

        // then
        Assert.assertEquals(0, devClassBuilder.getDeviceImplList().size());

        // cleanup
        tangoExporter.buildDevice(JTangoTest.DEFAULT_NO_DB_DEVICE_NAME, Class.forName(TANGO_CLASS));

        // then
        Assert.assertEquals(1, devClassBuilder.getDeviceImplList().size());
    }

    @Test
    public void testTangoExporterGetNotExistDevice() {
        Assert.assertThrows(DevFailed.class, () -> {
            testServerManagerHelper.getTangoExporter().getDevicesOfClass("NOT_EXIST_CLASS");
        });
    }

    public DeviceClassBuilder getClassBuilderFromList(String name, List<DeviceClassBuilder> list) {
        return list.stream()
                .filter(dev -> dev.getClassName().equals(name))
                .findFirst().orElse(null);
    }
}