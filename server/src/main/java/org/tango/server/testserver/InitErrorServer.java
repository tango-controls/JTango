/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.testserver;

import fr.esrf.Tango.DevFailed;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.Init;
import org.tango.utils.DevFailedUtils;

/**
 * a device to test device init
 *
 * @author ABEILLE
 *
 */
@Device
public final class InitErrorServer {

    private static boolean throwError = true;

    @Init(lazyLoading = true)
    public void init() throws DevFailed {
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (throwError) {
            throwError = false;
            throw DevFailedUtils.newDevFailed("fake error");
        }
    }
}
