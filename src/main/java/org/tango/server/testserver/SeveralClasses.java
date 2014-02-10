package org.tango.server.testserver;

import org.tango.server.ServerManager;
import org.tango.server.annotation.Device;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * A server with 2 classes
 * 
 * @author ABEILLE
 * 
 */
public class SeveralClasses {

    @Device
    public static class Class1 {

    }

    @Device
    public static class Class2 {

    }

    public static void start() throws DevFailed {
        System.setProperty("OAPort", Integer.toString(1245));
        ServerManager.getInstance().addClass(Class2.class.getSimpleName(), Class2.class);
        ServerManager.getInstance().addClass(Class1.class.getSimpleName(), Class1.class);

        ServerManager.getInstance().startError(new String[] { "instance", "-nodb", "-dlist", "deviceName", "device2" },
                "serverName");
//        ServerManager.getInstance().startError(new String[] { "1" }, SeveralClasses.class.getSimpleName());
    }

    public static void main(final String[] args) {
        try {
            SeveralClasses.start();
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
