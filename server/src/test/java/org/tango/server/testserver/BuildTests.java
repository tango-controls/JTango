/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.testserver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;
import org.tango.DeviceState;
import org.tango.server.ServerManager;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;

public class BuildTests {

    private static final String noDbDeviceName = "1/1/1";
    private static final String noDbGiopPort = "12354";
    private static final String noDbInstanceName = "1";
    public static final String deviceName = "tango://localhost:" + noDbGiopPort + "/" + noDbDeviceName + "#dbase=no";

    @Test
    public void testNoInitTest() throws DevFailed {
        startDetachedNoDb(NoInitDevice.class);
        final DeviceProxy dev = new DeviceProxy(deviceName);
        final TangoAttribute ta = new TangoAttribute(deviceName + "/checkProp");
        assertThat(ta.read(String.class), equalTo("default"));
        ta.write("toto");
        assertThat(ta.read(String.class), equalTo("toto"));
        dev.command_inout("Init");
        assertThat(ta.read(String.class), equalTo("default"));

    }

    @Test(expected = DevFailed.class)
    public void testBad1() throws DevFailed {
        startDetachedNoDb(BadServer.class);
    }

    @Test(expected = DevFailed.class)
    public void testBad2() throws DevFailed {
        startDetachedNoDb(BadServer2.class);
    }

    @Test(expected = DevFailed.class)
    public void testBad3() throws DevFailed {
        startDetachedNoDb(BadServer3.class);
    }

    @Test(expected = DevFailed.class)
    public void testBad4() throws DevFailed {
        startDetachedNoDb(BadServer4.class);
    }

    @Test(expected = DevFailed.class)
    public void testBad5() throws DevFailed {
        startDetachedNoDb(BadServer5.class);
    }

    @Test(timeout = 3000)
    public void testInit() throws DevFailed {
        startDetachedNoDb(InitErrorServer.class);
        final DeviceProxy dev = new DeviceProxy(deviceName);
        assertThat(dev.state(), equalTo(DevState.INIT));
        assertThat(dev.status(), containsString("Init in progress"));
        while (dev.state().equals(DevState.INIT)) {
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
            }
        }
        assertThat(dev.state(), equalTo(DevState.FAULT));
        assertThat(dev.status(), containsString("fake error"));
        dev.command_inout("Init");
    }

    @Test
    public void testDefaultState() throws DevFailed {
        startDetachedNoDb(TestDefaultStateServer.class);
        final DeviceProxy dev = new DeviceProxy(deviceName);
        assertThat(dev.state(), equalTo(DevState.UNKNOWN));
        assertThat(dev.status(), equalTo("The device is in UNKNOWN state."));
    }

    @Test
    public void testState() throws DevFailed {
        startDetachedNoDb(TestStateServer.class);
        final DeviceProxy dev = new DeviceProxy(deviceName);
        System.out.println(DeviceState.toString(dev.state()));
        assertThat(dev.state(), equalTo(DevState.ON));
    }

    @After
    public void after() throws DevFailed {
        ServerManager.getInstance().stop();
    }

    private void startDetachedNoDb(final Class<?> deviceClass) throws DevFailed {
        System.setProperty("OAPort", noDbGiopPort);
        ServerManager.getInstance().addClass(deviceClass.getCanonicalName(), deviceClass);
        ServerManager.getInstance().startError(new String[] { noDbInstanceName, "-nodb", "-dlist", noDbDeviceName },
                deviceClass.getCanonicalName());
        // adminName = "tango://localhost:" + noDbGiopPort + "/" +
        // Constants.ADMIN_DEVICE_DOMAIN + "/"
        // + ServerManager.getInstance().getServerName() + "#dbase=no";
    }

}
