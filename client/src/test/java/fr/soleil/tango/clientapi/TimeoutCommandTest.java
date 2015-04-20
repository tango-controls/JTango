package fr.soleil.tango.clientapi;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

@Ignore("a tango test must be running")
public class TimeoutCommandTest {

    @Test
    public void compareTimeout() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy("sys/tg_test/1");
        dev.set_timeout_millis(35000);
        final TangoCommand cmd = new TangoCommand("sys/tg_test/1", "DevVoid");
        assertThat(cmd.getDeviceProxy().get_timeout_millis(), equalTo(dev.get_timeout_millis()));
        System.out.println(cmd.getDeviceProxy().get_timeout_millis());
        System.out.println(dev.get_timeout_millis());
    }

}
