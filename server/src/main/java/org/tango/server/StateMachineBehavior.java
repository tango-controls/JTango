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
package org.tango.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.DeviceState;
import org.tango.server.annotation.StateMachine;

/**
 * State machine
 *
 * @see StateMachine
 * @author ABEILLE
 *
 */
public final class StateMachineBehavior implements Cloneable {
    /**
     * The end state
     */
    private DeviceState endState;
    /**
     * The denied state
     */
    private List<DeviceState> deniedStates = new ArrayList<DeviceState>();

    /**
     * Check if a state is allowed
     *
     * @param state
     * @return true if allowed
     */
    public boolean isAllowed(final DeviceState state) {
        boolean isAllowed = true;
        if (deniedStates.contains(state)) {
            isAllowed = false;
        }
        return isAllowed;
    }

    /**
     *
     * @return denied states {@link DeviceState}
     */
    public DeviceState[] getDeniedStates() {
        return deniedStates.toArray(new DeviceState[0]);
    }

    /**
     * Set denied states
     *
     * @param deniedStates
     */
    public void setDeniedStates(final DeviceState... deniedStates) {
        Collections.addAll(this.deniedStates, deniedStates);
    }

    /**
     *
     * @return end state {@link DeviceState}
     */
    public DeviceState getEndState() {
        return endState;
    }

    /**
     * Set end state
     *
     * @param endState
     */
    public void setEndState(final DeviceState endState) {
        this.endState = endState;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        final StateMachineBehavior copy = (StateMachineBehavior) super.clone();
        copy.deniedStates = new ArrayList<DeviceState>(deniedStates);
        return copy;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
