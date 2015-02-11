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

import org.tango.DeviceState;
import org.tango.server.ServerManager;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.device.DeviceManager;
import org.tango.server.dynamic.DynamicManager;

import fr.esrf.Tango.DevFailed;

@Device
public class SubServer extends SuperServer {

    public static final String INSTANCE_NAME = "1";
    public static final String NO_DB_DEVICE_NAME = "1/2/3";
    public static final String SERVER_NAME = SubServer.class.getSimpleName();

    public static void main(final String[] args) {
        try {
            startNoDb(53656);
        } catch (final DevFailed e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void startNoDb(final int portNr) throws DevFailed {
        System.setProperty("OAPort", Integer.toString(portNr));
        ServerManager.getInstance().addClass(SubServer.class.getCanonicalName(), SubServer.class);
        ServerManager.getInstance().startError(new String[] { INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME },
                SERVER_NAME);
        // ServerManager.getInstance().startDevice("1/2/4", SubServer.class);
    }

    @DeviceManagement
    private DeviceManager dev2;

    @Override
    @Command
    public String getSuperDeviceProperty() {
        return super.getSuperDeviceProperty();
    }

    @Command
    public void setDisable() {
        setState(DeviceState.DISABLE);
    }

    @Init
    @Override
    public void init() {
        super.init();
    }

    public void setDev2(final DeviceManager dev2) {
        this.dev2 = dev2;
    }

    @DynamicManagement
    private DynamicManager dyn;

    @Override
    public void setDyn(final DynamicManager dyn) {
        this.dyn = dyn;
        super.setDyn(dyn);
    }

    @Override
    public DeviceState getState() {
        System.out.println("sub getState");
        return super.getState();
    }

    @Override
    public void setState(final DeviceState state) {
        System.out.println("sub setState");
        super.setState(state);
    }

}
