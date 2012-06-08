package fr.soleil.tango.client;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

public class Test1 {

    public static void main(final String[] args) {
	System.setProperty("TANGO_HOST", "calypso:20001");
	DeviceProxy dev = null;
	try {
	    dev = new DeviceProxy("test/java/error.1");
	} catch (final DevFailed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	while (true) {
	    try {
		System.out.println("#############################IN");
		// dev.read_attribute("throwExceptionRead");
		dev.read_attribute("timeoutRead");
		System.out.println("#############################OUT");

	    } catch (final DevFailed e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
}
