package fr.esrf.TangoApi.Group;

import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.soleil.tango.clientapi.TangoGroupAttribute;

public class Test {

    public static void main(final String[] args) {
        try {
//            final DeviceProxy dev = new DeviceProxy("test/java/error.1");
//            final DeviceAttribute[] devattr = dev.read_attribute(new String[] { "timeoutRead",
//                    "throwExceptionReadDouble", "throwExceptionWriteDouble" });
//            for (final DeviceAttribute deviceAttribute : devattr) {
//                deviceAttribute.insert(0.2);
//            }
//            dev.write_attribute(devattr);
//            System.out.println("OK");

            final TangoGroupAttribute group = new TangoGroupAttribute(false, "test/java/error.1/timeoutRead",
                    "test/java/error.1/throwExceptionReadDouble", "test/java/error.1/throwExceptionWriteDouble");
            group.write(0.3);
        } catch (final DevFailed e) {
            // e.printStackTrace();
            DevFailedUtils.printDevFailed(e);
        }

    }

}
