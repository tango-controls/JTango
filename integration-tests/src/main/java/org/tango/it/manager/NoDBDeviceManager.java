package org.tango.it.manager;

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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.tango.it.ITWithoutTangoDB;
import org.tango.server.Constants;
import org.tango.server.ServerManager;

import fr.esrf.Tango.DevFailed;
import org.tango.server.testserver.JTangoTest;

/**
 * Starts, stop the server JTangoTest without tango db
 *
 * @author ABEILLE
 *
 */
public class NoDBDeviceManager implements ITWithoutTangoDB {

    private List<String> deviceFullNameList = new ArrayList<>();
    private String adminName;

    private boolean isDefault = false;

    @Override
    public void startDevices(String deviceList) throws DevFailed, IOException {
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

    @Override
    public String getDefaultDeviceFullName() {
        if (!this.isDefault) {
            System.out.println("NoDBDeviceManager::getDefaultDeviceFullName::Waring the default device isn't running. " +
                    "The devices was start with custom name!");
            System.out.println("NoDBDeviceManager::getDefaultDeviceFullName::Warning return first from running devices");
        }

        return deviceFullNameList.get(0);
    }

    @Override
    public List<String> getDeviceFullNameList() {
        return deviceFullNameList;
    }

    @Override
    public String getFullAdminName() {
        return adminName;
    }

    @Override
    public void stopDevices() throws DevFailed {
        ServerManager.getInstance().stop();
        clean();
    }

    protected void clean() {
        deviceFullNameList = new ArrayList<>();
        isDefault = false;
    }

}

