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
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;

public class WriteReadTest extends NoDBDeviceManager {

    @Test
    public void writeRead() throws DevFailed {
        try {
            final DeviceProxy dev = new DeviceProxy(deviceName);
            final DeviceAttribute da = dev.read_attribute("doubleScalar");
            final double input = 10.4;
            da.insert(input);
            final DeviceAttribute[] result = dev.write_read_attribute(new DeviceAttribute[] { da },
                    new String[] { "doubleScalar" });
            assertThat(result.length, equalTo(1));
            final double val = result[0].extractDouble();
            assertThat(val, equalTo(input));
        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }

}
