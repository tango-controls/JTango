package tango.it.helpers;

import fr.esrf.Tango.DevFailed;
import org.tango.server.Constants;
import org.tango.server.ServerManager;
import org.tango.server.export.TangoExporter;
import org.tango.server.servant.DeviceImpl;

import java.lang.reflect.Field;

public class JTangoTestServerManager {

    private final ServerManager serverManager;

    public JTangoTestServerManager() {
        serverManager = ServerManager.getInstance();
    }

    public DeviceImpl getDefaultDeviceImp(String tangoClass, String tangoDeviceName) throws NoSuchFieldException, IllegalAccessException, DevFailed {
        TangoExporter tangoExporter = getTangoExporter();
        return tangoExporter.getDevice(tangoClass, tangoDeviceName);
    }

    public TangoExporter getTangoExporter() throws NoSuchFieldException, IllegalAccessException {
        return getFieldFromServiceManager("tangoExporter", TangoExporter.class);
    }

    private <T> T getFieldFromServiceManager(String field, Class<T> clazz)
            throws IllegalAccessException, NoSuchFieldException {
        System.out.println("Extracting " + clazz.getName() + " from ServiceManger");
        Field f = serverManager.getClass().getDeclaredField(field);
        f.setAccessible(true);
        return (T) f.get(serverManager);
    }

    public String getAdminDeviceName() {
        return Constants.ADMIN_DEVICE_DOMAIN + "/" + serverManager.getServerName();
    }

}
