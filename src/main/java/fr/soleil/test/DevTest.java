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
package fr.soleil.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.DeviceState;
import org.tango.server.ServerManager;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.State;

import fr.esrf.Tango.DevFailed;

@Device
public class DevTest {
    Logger logger = LoggerFactory.getLogger(DevTest.class);

    @State
    DeviceState state;

    public static void main(final String[] args) throws DevFailed {
        // System.setProperty("OAPort", Integer.toString(54354));
        ServerManager.getInstance().addClass(DevTest.class.getSimpleName(), DevTest.class);
        ServerManager.getInstance().start(new String[] { "1", "-v3" }, DevTest.class.getSimpleName());
        // ServerManager.getInstance().startError(new String[] { "tot", "-nodb", "-dlist", "dev" }, "ferfr");
    }

    public DeviceState getState() {
        logger.error("error");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");
        return state;
    }

    public void setState(final DeviceState state) {
        this.state = state;
    }
}
