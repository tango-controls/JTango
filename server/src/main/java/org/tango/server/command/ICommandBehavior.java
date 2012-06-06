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
package org.tango.server.command;

import org.tango.server.StateMachineBehavior;

import fr.esrf.Tango.DevFailed;

/**
 * Defines the behavior of a Tango command.
 * 
 * @author ABEILLE
 * 
 */
public interface ICommandBehavior {
    /**
     * 
     * @return the command configuration
     * @throws DevFailed
     */
    CommandConfiguration getConfiguration() throws DevFailed;

    /***
     * Execute the command
     * 
     * @param arg
     *            the input of the command
     * @return The ouput of the command
     * @throws DevFailed
     */
    Object execute(Object arg) throws DevFailed;

    /**
     * 
     * @return the command state machine
     * @throws DevFailed
     */
    StateMachineBehavior getStateMachine() throws DevFailed;

}
