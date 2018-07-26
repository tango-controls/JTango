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
package org.tango.server.servant;

import java.util.List;

import org.tango.server.ExceptionMessages;
import org.tango.server.command.CommandImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

public class CommandGetter {

    /**
     * Get a command
     * 
     * @param name
     * @return The command
     * @throws DevFailed
     */
    public static CommandImpl getCommand(final String name, final List<CommandImpl> commandList) throws DevFailed {
        CommandImpl result = null;
        for (final CommandImpl command : commandList) {
            if (command.getName().equalsIgnoreCase(name)) {
                result = command;
                break;
            }
        }
        if (result == null) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.COMMAND_NOT_FOUND, "Command " + name + " not found");
        }
        return result;
    }

}
