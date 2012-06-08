package fr.esrf.TangoApi;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

public class TestApiAsync {
    @BeforeClass
    public static void init() {
	System.setProperty("TANGO_HOST", "calypso:20001");
    }

    @Ignore
    @Test
    public void testDeviceImpl2() throws DevFailed {
	System.out.println("PROXY - test/java/error.1");
	final DeviceProxy dev = new DeviceProxy("test/java/error.1");
	System.out.println("PROXY");
	try {
	    final DeviceAttribute deviceAttribute = new DeviceAttribute("throwExceptionReadDouble");
	    deviceAttribute.insert(12.5);

	    System.out.println("test write_attribute_asynch - in");
	    final int rid = dev.write_attribute_asynch(deviceAttribute);
	    dev.write_attribute_reply(rid, 3000);
	    System.out.println("test write_attribute_asynch - out");
	} catch (final DevFailed e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    Except.print_exception(e);
	}
	try {
	    System.out.println("test read_attribute_asynch - in");
	    final int rid = dev.read_attribute_asynch("throwExceptionRea");
	    dev.read_attribute_reply(rid, 3000);
	    System.out.println("test read_attribute_asynch - out");
	} catch (final DevFailed e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    Except.print_exception(e);
	}
	// for (final DeviceAttribute element : deviceAttribute2) {
	// System.out.println("read " + element.extractBoolean());
	// }

	// System.out.println("id " + rid);
	try {
	    System.out.println("test command_inout_asynch - in");
	    final int rid = dev.command_inout_asynch("VoidVoidError");
	    dev.command_inout_reply(rid, 3000);
	    System.out.println("test command_inout_asynch - out");

	} catch (final DevFailed e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    Except.print_exception(e);
	}
    }

    @Test
    public void testDeviceImpl4() throws DevFailed {
	System.out.println("PROXY - tango/tangotest/1");
	final DeviceProxy dev = new DeviceProxy("tango/tangotest/1");
	System.out.println("PROXY");
	// try {
	// final DeviceAttribute deviceAttribute = new
	// DeviceAttribute("short_scalar_w");
	// deviceAttribute.insert(new Short("25"));
	//
	// System.out.println("test write_attribute_asynch - in");
	// final int rid = dev.write_attribute_asynch(deviceAttribute);
	// dev.write_attribute_reply(rid, 3000);
	// System.out.println("test write_attribute_asynch - out");
	//
	// } catch (final DevFailed e) {
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// Except.print_exception(e);
	// }
	try {
	    System.out.println("test read_attribute_asynch - in");
	    final int rid = dev.read_attribute_asynch("short_scalar_");
	    final DeviceAttribute[] deviceAttribute2 = dev.read_attribute_reply(rid, 3000);

	    for (final DeviceAttribute element : deviceAttribute2) {
		System.out.println("read " + element.extractBoolean());
	    }
	    System.out.println("test read_attribute_asynch - out");
	} catch (final DevFailed e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    Except.print_exception(e);
	}

	// try {
	// System.out.println("test command_inout_asynch - in");
	// final int rid = dev.command_inout_asynch("tptpt");
	// dev.command_inout_reply(rid, 3000);
	// System.out.println("test command_inout_asynch - out");
	// } catch (final DevFailed e) {
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// Except.print_exception(e);
	// }
    }
    // }
}
