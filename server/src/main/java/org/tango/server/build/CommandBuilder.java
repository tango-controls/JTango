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
package org.tango.server.build;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.annotation.Command;
import org.tango.server.command.CommandConfiguration;
import org.tango.server.command.CommandImpl;
import org.tango.server.command.ReflectCommandBehavior;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;

/**
 * Build a {@link Command}
 * 
 * @author ABEILLE
 * 
 */
final class CommandBuilder {

    private final Logger logger = LoggerFactory.getLogger(CommandBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(CommandBuilder.class);

    /**
     * Create a Tango command {@link Command}
     * 
     * @param device
     * @param businessObject
     * @param m
     * @param isOnDeviceImpl
     * @throws DevFailed
     */
    public void build(final DeviceImpl device, final Object businessObject, final Method m, final boolean isOnDeviceImpl)
            throws DevFailed {
        xlogger.entry();
        final Command annot = m.getAnnotation(Command.class);
        // retrieve parameter type
        final Class<?>[] paramTypeTable = m.getParameterTypes();
        if (paramTypeTable.length > 1) {
            throw DevFailedUtils.newDevFailed("INIT_FAILED", "Command can have only one parameter");
        }
        Class<?> paramType;
        if (paramTypeTable.length == 0) {
            paramType = Void.class;
        } else {
            paramType = paramTypeTable[0];
        }
        // retrieve returned type
        final Class<?> returnedType = m.getReturnType();
        // logger
        // .debug("get returned type of cmd {}", returnedType
        // .getCanonicalName());
        String cmdName;
        if ("".equals(annot.name())) {
            cmdName = m.getName();
        } else {
            cmdName = annot.name();
        }
        logger.debug("Has a command: {} type {}", cmdName, paramType.getCanonicalName());
        final CommandConfiguration config = new CommandConfiguration(cmdName, paramType, returnedType,
                annot.inTypeDesc(), annot.outTypeDesc(), DispLevel.from_int(annot.displayLevel()), annot.isPolled(),
                annot.pollingPeriod());

        ReflectCommandBehavior behavior = null;
        if (isOnDeviceImpl) {
            behavior = new ReflectCommandBehavior(m, device, config);

        } else {
            behavior = new ReflectCommandBehavior(m, businessObject, config);
        }
        final CommandImpl command = new CommandImpl(behavior, device.getName());
        BuilderUtils.setStateMachine(m, command);
        device.addCommand(command);
        xlogger.exit();
    }
}
