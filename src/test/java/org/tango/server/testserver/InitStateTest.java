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

import org.junit.Test;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;
import fr.soleil.tango.clientapi.TangoCommand;

public class InitStateTest extends NoDBDeviceManager {

    @Test
    public void testInit() throws DevFailed {
        final TangoAttribute attr = new TangoAttribute(deviceName + "/shortScalar");
        attr.write(20);
        final TangoCommand tangoCommand = new TangoCommand(deviceName, "Init");
        tangoCommand.execute();
        final short shortScalar = (Short) attr.read();
        assertThat(shortScalar, equalTo((short) 10));
    }

    @Test
    public void testInitState() throws DevFailed {
        final TangoCommand tangoCommand = new TangoCommand(deviceName, "Status");
        final Object status = tangoCommand.executeExtract(null);
        assertThat(status.toString(), equalTo("hello"));
    }

    @Test
    public void testState() throws DevFailed {
        final TangoCommand tangoCommand = new TangoCommand(deviceName, "testState");
        tangoCommand.execute();
        final TangoCommand tangoCommand2 = new TangoCommand(deviceName + "/State");
        final Object state = tangoCommand2.executeExtract(null);
        assertThat((DevState) state, equalTo(DevState.FAULT));
    }

    @Test
    public void testBlackBox() throws DevFailed {
        final DeviceProxy dev = new DeviceProxy(deviceName);
        dev.black_box(10);
    }

}
