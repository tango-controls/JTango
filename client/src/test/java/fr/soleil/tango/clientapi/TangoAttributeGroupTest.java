package fr.soleil.tango.clientapi;

import java.lang.reflect.Array;

import org.junit.Ignore;
import org.junit.Test;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;

@Ignore
public class TangoAttributeGroupTest {

    public static void main(final String[] args) {
	System.setProperty("TANGO_HOST", "calypso:20001");
	try {
	    final DeviceProxy dev = new DeviceProxy("katy/gs/enumeratedcommandd");
	    System.out.println(dev.read_attribute("Status").extractString());
	} catch (final DevFailed e) {
	    DevFailedUtils.printDevFailed(e);
	}
    }

    @Test
    public void test() throws DevFailed {
	System.setProperty("TANGO_HOST", "calypso:20001");
	try {
	    final TangoGroupAttribute group = new TangoGroupAttribute("tango/tangotest/1/short_spectrum",
		    "tango/tangotest/2/short_spectrum", "raphael/test/tangotestrg1/double_spectrum");
	    // write
	    group.write(new String[] { "30", "20" }, new String[] { "1", "20" }, new String[] { "10", "2" });

	    // read
	    final DeviceAttribute[] result = group.read();

	    for (final DeviceAttribute deviceAttribute : result) {
		System.out.println(deviceAttribute.getName());
		final Object tmpReadValue = InsertExtractUtils.extract(deviceAttribute);
		for (int i = 0; i < Array.getLength(tmpReadValue); i++) {
		    System.out.println(Array.get(tmpReadValue, i));
		}
	    }
	} catch (final DevFailed e) {
	    DevFailedUtils.printDevFailed(e);
	    throw e;
	}
    }

    // @Test
    public void testAsync() throws DevFailed {
	System.setProperty("TANGO_HOST", "calypso:20001");
	try {
	    final TangoGroupAttribute group = new TangoGroupAttribute("tango/tangotest/1/short_spectrum",
		    "tango/tangotest/2/short_spectrum", "tango/tangotest/1/double_spectrum");
	    // write
	    group.writeAysn(new String[] { "30", "2" }, new String[] { "1", "20" }, new String[] { "10", "2" });
	    group.getWriteAsyncReplies();
	    // read
	    group.readAync();
	    final DeviceAttribute[] result = group.getReadAsyncReplies();
	    for (final DeviceAttribute deviceAttribute : result) {
		System.out.println("test " + deviceAttribute.getName());
		final Object tmpReadValue = InsertExtractUtils.extract(deviceAttribute);
		for (int i = 0; i < Array.getLength(tmpReadValue); i++) {
		    System.out.println(Array.get(tmpReadValue, i));
		}
	    }
	} catch (final DevFailed e) {
	    DevFailedUtils.printDevFailed(e);
	    throw e;
	}
    }

}
