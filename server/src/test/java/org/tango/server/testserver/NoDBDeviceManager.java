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
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.tango.server.Constants;
import org.tango.server.ServerManager;

import fr.esrf.Tango.DevFailed;

/**
 * Starts, stop the server JTangoTest without tango db
 * 
 * @author ABEILLE
 * 
 */
public class NoDBDeviceManager {

    public static List<String> deviceFullNameList = new ArrayList<>();
    public static boolean isDefault = false;
    public static String adminName;

    @BeforeClass
    public static void startDevice() throws DevFailed, IOException {
        startDevice(null);
    }

    public static void startDevice(String deviceList) throws DevFailed, IOException {
        System.setProperty("org.tango.server.checkalarms", "false");

        try (ServerSocket ss1 = new ServerSocket(0)) {
            ss1.setReuseAddress(true);
            ss1.close();

            if (deviceList == null) {
                JTangoTest.startNoDb(ss1.getLocalPort());
                isDefault = true;
                deviceFullNameList.add("tango://localhost:" + ss1.getLocalPort() + "/"
                        + JTangoTest.DEFAULT_NO_DB_DEVICE_NAME + "#dbase=no");
            } else {
                JTangoTest.startNoDb(ss1.getLocalPort(), deviceList);
                for (String device : deviceList.split(",")) {
                    deviceFullNameList.add("tango://localhost:" + ss1.getLocalPort() + "/"
                            + device + "#dbase=no");
                }
            }
            adminName = "tango://localhost:" + ss1.getLocalPort() + "/" + Constants.ADMIN_DEVICE_DOMAIN + "/"
                    + ServerManager.getInstance().getServerName() + "#dbase=no";
            for (String device : deviceFullNameList) {
                System.out.println("START " + device);
            }
        }
    }

    public static String getDefaultDeviceFullName() {
        if (!isDefault) {
            System.out.println("NoDBDeviceManager::getDefaultDeviceFullName::Waring the default device isn't running. " +
                    "The devices was start with custom name!");
            System.out.println("NoDBDeviceManager::getDefaultDeviceFullName::Warning return first from running devices");
        }

        return deviceFullNameList.get(0);
    }

    @AfterClass
    public static void stopDevice() throws DevFailed {
        System.out.println("STOP");
        ServerManager.getInstance().stop();
        clean();
    }

    protected static void clean() {
        deviceFullNameList = new ArrayList<>();
        isDefault = false;
    }

}
