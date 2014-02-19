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
import org.tango.server.annotation.AroundInvoke;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceManagement;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.State;
import org.tango.server.annotation.StateMachine;
import org.tango.server.device.DeviceManager;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;

@Device
public class Test {

    @DeviceManagement
    private DeviceManager deviceManager;

    public void setDeviceManager(final DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void test() throws DevFailed {
        final String attName = "TheAttr";
        if (deviceManager.isPolled(attName)) {
            System.out.println(attName + " is polled with period " + deviceManager.getPollingPeriod(attName) + " mS");
        } else {
            System.out.println(attName + " is not polled");
        }
        deviceManager.startPolling("TheCmd", 250);
        deviceManager.stopPolling("TheCmd");
    }

    @DeviceProperty(defaultValue = { "0", "1" })
    double[] tesStProp;

    public void setTesStProp(final double[] tesStProp) {
        this.tesStProp = tesStProp;
    }

    private double attribute;

    @State
    private DeviceState state = DeviceState.OFF;

    public DeviceState getState() {
        // System.out.println("State " + new Date());
        return state;
    }

    public void setState(final DeviceState state) {
        this.state = state;
    }

    @Attribute
    @StateMachine(deniedStates = { DeviceState.MOVING, DeviceState.FAULT })
    public double getAttribute() {
        return attribute;
    }

    public void setAttribute(final double attribute) {
        this.attribute = attribute;
    }

    @Command
    public void setFault() {
        state = DeviceState.FAULT;
    }

    @Command
    public void setOn() {
        state = DeviceState.ON;
    }

    @Command
    public void setMoving() {
        state = DeviceState.MOVING;
    }

    @AroundInvoke
    public void alwaysHook(final org.tango.server.InvocationContext ctx) {
        // System.out.println(ctx.toString());
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            final DeviceProxy dp = new DeviceProxy("tango/tangotest/1");
            final DeviceAttribute att = dp.read_attribute("string_scalar");
            System.out.println(dp.read_attribute("string_scalar").extractString());
            att.insert("µ");
            dp.write_attribute(att);

            System.out.println(dp.read_attribute("string_scalar").extractString());
        } catch (final DevFailed e) {
            e.printStackTrace();
        }
//        ServerManager.getInstance().start(new String[] { "2" }, Test.class);
    }

    @Init
    public void init() {
//        System.out.println("toto");
//        System.out.println(Arrays.toString(tesStProp));
    }
}
