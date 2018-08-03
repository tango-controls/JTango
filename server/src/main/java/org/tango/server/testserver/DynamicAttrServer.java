package org.tango.server.testserver;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.Database;
import org.tango.server.ServerManager;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.device.DeviceManager;
import org.tango.server.dynamic.DynamicManager;

/**
 *
 */
@Device
public class DynamicAttrServer {

    @DynamicManagement
    public DynamicManager dynamicManager;
    @DeviceManagement
    public DeviceManager deviceManager;

    public static void main(String[] args) throws DevFailed {
        // declareServerInTangoDB();
        ServerManager.getInstance().start(new String[]{"1"}, DynamicAttrServer.class);
    }

    private static void declareServerInTangoDB() throws DevFailed {
        Database tangoDb = ApiUtil.get_db_obj();
        tangoDb.add_device("tango9/java/dynamic-attr.1", "DynamicAttrServer", "DynamicAttrServer/1");
    }

    public void setDynamicManager(DynamicManager dynamicManager) {
        this.dynamicManager = dynamicManager;
    }

    public void setDeviceManager(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    @Init
    public void init() throws DevFailed {
        dynamicManager.addAttribute(new DynamicMemorizedAttribute("atInit"));
    }

    @Delete
    public void delete() throws DevFailed {
        dynamicManager.clearAll();
    }

    @Command
    public void createDynamicAttribute(String name) throws DevFailed {
        dynamicManager.addAttribute(new DynamicMemorizedAttribute(name));
        dynamicManager.loadAttributeConfigFromDb(name);
        if (deviceManager.isPolled(name)) {
            deviceManager.startPolling(name);
        }
    }

    @Command
    public void removeDynamicAttribute(String name) throws DevFailed {
        // removed dynamic attribute but keeps its properties in TangoDB
        dynamicManager.removeAttribute(name);
    }

    @Command
    public void removedDynamicAttributeAndItsProperties(String name) throws DevFailed {
        // removed dynamic attribute and its properties in TangoDB
        deviceManager.removeAttributeProperties(name);
        dynamicManager.removeAttribute(name);
    }
}
