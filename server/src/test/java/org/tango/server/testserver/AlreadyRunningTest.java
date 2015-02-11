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

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Test;
import org.tango.orb.ORBManager;
import org.tango.server.ServerManager;

import fr.esrf.Tango.DevFailed;

public class AlreadyRunningTest {

    // XXX: server must be declared in tango db before running this test

    @Test(expected = DevFailed.class)
    public void testDB() throws DevFailed {
        // assertThat(System.getProperty("TANGO_HOST"), notNullValue());
        JTangoTest.start();
        ORBManager.init(true, "dserver/" + JTangoTest.SERVER_NAME + "/" + JTangoTest.INSTANCE_NAME);
    }

    @Test(expected = DevFailed.class)
    public void test() throws DevFailed, IOException {
        ServerSocket ss1 = null;
        try {
            ss1 = new ServerSocket(0);
            ss1.setReuseAddress(true);
            ss1.close();
            JTangoTest.startNoDb(ss1.getLocalPort());
        } finally {
            if (ss1 != null) {
                ss1.close();
            }
        }
        ServerSocket ss2 = null;
        try {
            ss2 = new ServerSocket(0);
            ss2.setReuseAddress(true);
            ss2.close();
            JTangoTest.startNoDb(ss2.getLocalPort());
        } finally {
            if (ss2 != null) {
                ss2.close();
            }
        }

    }

    @After
    public void stopDevice() throws DevFailed {
        ServerManager.getInstance().stop();
    }

}
