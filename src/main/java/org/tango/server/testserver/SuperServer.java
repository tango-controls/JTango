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
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.State;
import org.tango.server.annotation.StateMachine;
import org.tango.server.device.DeviceManager;
import org.tango.server.dynamic.DynamicManager;

@Device
public class SuperServer {

    @Attribute
    private String superAttribute = "";

    @DeviceProperty(defaultValue = "defaultValue")
    private String superDeviceProperty;

    @DeviceManagement
    private DeviceManager dev;

    @DynamicManagement
    private DynamicManager dyn;

    @State
    private DeviceState state;

    @Init
//    @StateMachine(endState = DeviceState.ON)
    public void init() {
        System.out.println("Init " + dev.getName());
        System.out.println(dyn.getDynamicAttributes());
        this.state = DeviceState.ON;
    }

    @Command
    @StateMachine(endState = DeviceState.EXTRACT)
    public double superCommand(final double value) {
        return value;
    }

    public String getSuperAttribute() {
        return superAttribute;
    }

    public void setSuperAttribute(final String superAttribute) {
        this.superAttribute = superAttribute;
    }

    public String getSuperDeviceProperty() {
        return superDeviceProperty;
    }

    public void setSuperDeviceProperty(final String superDeviceProperty) {
        this.superDeviceProperty = superDeviceProperty;
    }

    public void setDev(final DeviceManager dev) {
        this.dev = dev;
    }

    public void setDyn(final DynamicManager dyn) {
        this.dyn = dyn;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(final DeviceState state) {
        this.state = state;
    }

}
