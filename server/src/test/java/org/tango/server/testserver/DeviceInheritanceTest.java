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

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.tango.server.ServerManager;

import fr.esrf.Tango.DevFailed;
import fr.soleil.tango.clientapi.TangoAttribute;
import fr.soleil.tango.clientapi.TangoCommand;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceInheritanceTest {

    public static String deviceName;

    @BeforeClass
    public static void startDevice() throws DevFailed, IOException {
        ServerSocket ss1 = null;
        try {
            ss1 = new ServerSocket(0);
            ss1.setReuseAddress(true);
            ss1.close();
            SubServer.startNoDb(ss1.getLocalPort());
            deviceName = "tango://localhost:" + ss1.getLocalPort() + "/" + SubServer.NO_DB_DEVICE_NAME + "#dbase=no";
        } finally {
            if (ss1 != null) {
                ss1.close();
            }
        }
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        ServerManager.getInstance().stop();
    }

    @Test()
    public void test1InitOK() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/State");
        assertThat(attr.read(String.class), equalTo("ON"));
    }

    @Test()
    public void testSetState() throws DevFailed {
        final TangoCommand cmd = new TangoCommand(deviceName, "setDisable");
        cmd.execute();
        final TangoAttribute attr = new TangoAttribute(deviceName + "/State");
        assertThat(attr.read(String.class), equalTo("DISABLE"));
    }

    @Test
    public void testAttribute() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/superAttribute");
        final String writeValue = "hello";
        attr.write(writeValue);
        final String readValue = attr.read(String.class);
        assertThat(readValue, equalTo(writeValue));
    }

    @Test
    public void testProperty() throws DevFailed {
        final TangoCommand cmd = new TangoCommand(deviceName, "getSuperDeviceProperty");
        final String prop = cmd.execute(String.class);
        assertThat(prop, equalTo("defaultValue"));
    }

    @Test
    public void testCommand() throws DevFailed {
        final TangoCommand cmd = new TangoCommand(deviceName, "superCommand");
        final double setPoint = 3.4;
        final double value = cmd.execute(double.class, setPoint);
        assertThat(value, equalTo(setPoint));
        final TangoAttribute attr = new TangoAttribute(deviceName + "/State");
        assertThat(attr.read(String.class), equalTo("EXTRACT"));
    }

}
