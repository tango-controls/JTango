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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tango.DeviceState;

/**
 * An object of a device (attribute or command)
 *
 * @author ABEILLE
 *
 */
public abstract class DeviceBehaviorObject {
    /**
     * State machine
     */
    private StateMachineBehavior stateMachine = new StateMachineBehavior();

    /**
     * Check if a state is allowed
     *
     * @param state
     *            a state
     * @return true is state is allowed
     */
    public final boolean isAllowed(final DeviceState state) {
        return stateMachine.isAllowed(state);
    }

    /**
     * Get denied states
     *
     * @return the denied states
     */
    public final DeviceState[] getDeniedStates() {
        return stateMachine.getDeniedStates();
    }

    /**
     * Set denied states
     *
     * @param deniedStates
     */
    public final void setDeniedStates(final DeviceState... deniedStates) {
        stateMachine.setDeniedStates(deniedStates);
    }

    /**
     * Get end state
     *
     * @return the end state
     */
    public final DeviceState getEndState() {
        return stateMachine.getEndState();
    }

    /**
     * Set end state
     *
     * @param endState
     */
    public final void setEndState(final DeviceState endState) {
        stateMachine.setEndState(endState);
    }

    /**
     * Set a state machine
     *
     * @param stateMachine
     */
    public final void setStateMachine(final StateMachineBehavior stateMachine) {
        if (stateMachine != null) {
            this.stateMachine = stateMachine;
        }
    }

    /**
     * @return a string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
