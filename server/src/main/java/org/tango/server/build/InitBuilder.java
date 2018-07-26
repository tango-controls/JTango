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
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.annotation.Init;
import org.tango.server.device.InitImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link Init}
 * 
 * @author ABEILLE
 * 
 */
final class InitBuilder {

    private final Logger logger = LoggerFactory.getLogger(InitBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(InitBuilder.class);

    public void build(final Method method, final DeviceImpl device, final Object businessObject) throws DevFailed {
        xlogger.entry();
        logger.debug("Has an init method: {}", method.getName());

        if (method.getParameterTypes().length != 0 && method.getReturnType() != void.class) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, method + " must not be void void");
        }
        if (Modifier.isStatic(method.getModifiers())) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, method + " must not be static");
        }
        final Init annot = method.getAnnotation(Init.class);
        final InitImpl init = device.buildInit(method, annot.lazyLoading());
        BuilderUtils.setStateMachine(method, init);
        xlogger.exit();
    }

}
