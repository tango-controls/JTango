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
package org.tango.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tango.DeviceState;

/**
 * Manage the state machine of the device. Apply for command execution and attribute writing. See {@link Init},
 * {@link Attribute} and {@link Command}. WARNING: The StateMachine annotation for an attribute must be located at the
 * as the Attribute annotation
 * 
 * <pre>
 * &#064;Init
 * &#064;StateMachine(endState = DeviceState.ON)
 * private void init(){..}
 * 
 * &#064;Attribute
 * &#064;StateMachine(deniedStates = { DeviceState.FAULT, DeviceState.UNKNOWN }, endState = DeviceState.DISABLE)
 * private String attr;
 * 
 * &#064;Command
 * &#064;StateMachine(deniedStates = { DeviceState.FAULT, DeviceState.UNKNOWN }, endState = DeviceState.DISABLE)
 * public double myCommand(int value){..}
 * </pre>
 * 
 * @author ABEILLE
 * 
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StateMachine {
    /**
     * The device state after the method execution
     * 
     * @return the device state
     */
    DeviceState endState() default DeviceState.UNKNOWN;

    /**
     * The states that are not allowed.
     * 
     * @return the denied states
     */
    DeviceState[] deniedStates() default {};
}
