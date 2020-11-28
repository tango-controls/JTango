package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import org.junit.Assert;
import org.junit.Test;
import org.tango.server.testserver.NoDBDeviceManager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DeviceProxyTest extends NoDBDeviceManager {

    DeviceProxy proxy;


    public DeviceProxyTest() throws DevFailed {
        proxy = new DeviceProxy(deviceName);
    }

    @Test
    public void lockDeviceTest() throws DevFailed {
        proxy.lock();
        Assert.assertTrue(proxy.isLocked());
        Assert.assertFalse(proxy.isLockedByMe());

        proxy.unlock();
        Assert.assertFalse(proxy.isLocked());
    }

    @Test
    public void lockCustomTimeDeviceTest() throws DevFailed, InterruptedException {
        // lock device for 1 sec
        proxy.lock(1);

        final CountDownLatch waiter = new CountDownLatch(1);
        waiter.await(2, TimeUnit.SECONDS);

        Assert.assertFalse(proxy.isLocked());
    }
}
