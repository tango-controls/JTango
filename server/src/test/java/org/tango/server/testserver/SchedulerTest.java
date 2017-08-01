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

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;
import fr.soleil.tango.clientapi.TangoAttribute;
import org.junit.Test;
import org.tango.client.database.DatabaseFactory;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Test schedule
 *
 * @author ABEILLE
 *
 */
public class SchedulerTest extends NoDBDeviceManager {

    @Test
    public void test() throws DevFailed {
        final Map<String, String[]> properties = new HashMap<String, String[]>();
        properties.put("isRunRefresh", new String[]{"true"});
        DatabaseFactory.getDatabase().setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, properties);
        new DeviceProxy(NoDBDeviceManager.deviceName).command_inout("Init");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        final TangoAttribute attr = new TangoAttribute(NoDBDeviceManager.deviceName + "/isScheduleRunning");
        assertThat(attr.read(boolean.class), equalTo(true));
        properties.put("isRunRefresh", new String[]{"false"});
        DatabaseFactory.getDatabase().setDeviceProperties(JTangoTest.NO_DB_DEVICE_NAME, properties);
        new DeviceProxy(NoDBDeviceManager.deviceName).command_inout("Init");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertThat(attr.read(boolean.class), equalTo(false));
    }

}
