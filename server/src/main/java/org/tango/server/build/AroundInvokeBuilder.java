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
import org.tango.server.InvocationContext;
import org.tango.server.annotation.AroundInvoke;
import org.tango.server.device.AroundInvokeImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build {@link AroundInvoke}
 * 
 * @author ABEILLE
 * 
 */
final class AroundInvokeBuilder {

    private final Logger logger = LoggerFactory.getLogger(AroundInvokeBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AroundInvokeBuilder.class);

    public void build(final Method method, final DeviceImpl device, final Object businessObject) throws DevFailed {
	xlogger.entry();
	if (method.getParameterTypes().length != 1 || !method.getParameterTypes()[0].equals(InvocationContext.class)
		|| !method.getReturnType().equals(void.class)) {
	    throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, method
		    + " be like: void invoke(InvocationContext ctx)");
	}
	if (Modifier.isStatic(method.getModifiers())) {
	    throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, method + " must not be static");
	}
	logger.debug("has an AroundInvoke method {} ", method.getName());
	device.setAroundInvokeImpl(new AroundInvokeImpl(businessObject, method));
	xlogger.exit();
    }

}
