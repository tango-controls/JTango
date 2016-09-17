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
import org.tango.server.annotation.Device;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.State;
import org.tango.server.annotation.Status;

/**
 * Device to test state
 * 
 * @author ABEILLE
 * 
 */
@Device
public final class TestStateServer {

    @State
    private DeviceState state = DeviceState.OFF;

    @Status
    private String status = "empty";

    @Init
    public void init() {
	state = DeviceState.ON;
	status = "OK";
    }

    public DeviceState getState() {
	return state;
    }

    public void setState(final DeviceState state) {
	this.state = state;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(final String status) {
	this.status = status;
    }

}
