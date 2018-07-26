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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

final class DeviceSchedulerBuilder {
    private final Logger logger = LoggerFactory.getLogger(DeviceSchedulerBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(DeviceSchedulerBuilder.class);

    public void build(final Set<Method> methods, final DeviceImpl device) throws DevFailed {
        xlogger.entry();
        logger.debug("Has scheduled methods: {}", methods);

        for (final Method method : methods) {
            if (method.getParameterTypes().length != 0 && !method.getReturnType().equals(void.class)) {
                throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, method + " must not be void void");
            }
        }

        device.setDeviceScheduler(methods);
        xlogger.exit();
    }
}
