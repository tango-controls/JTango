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
import fr.soleil.tango.clientapi.TangoCommand;

public class DeviceManagerTest extends NoDBDeviceManager {

    @Test
    public void test() throws DevFailed {
	final TangoCommand cmd = new TangoCommand(NoDBDeviceManager.deviceName, "getName");
	final String name = (String) cmd.executeExtract(null);
	assertThat(name, equalTo(JTangoTest.NO_DB_DEVICE_NAME));
    }

}
