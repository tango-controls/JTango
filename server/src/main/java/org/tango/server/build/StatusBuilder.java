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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.annotation.Status;
import org.tango.server.device.StatusImpl;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link Status}
 * 
 * @author ABEILLE
 * 
 */
final class StatusBuilder {
    private final Logger logger = LoggerFactory.getLogger(AttributeMethodBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributeMethodBuilder.class);

    /**
     * Create a status
     * 
     * @param clazz
     * @param field
     * @param device
     * @param businessObject
     * @throws DevFailed
     */
    public void build(final Class<?> clazz, final Field field, final DeviceImpl device, final Object businessObject)
            throws DevFailed {
        xlogger.entry();
        BuilderUtils.checkStatic(field);
        Method getter;
        final String stateName = field.getName();
        final String getterName = BuilderUtils.GET + stateName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + stateName.substring(1);
        try {
            getter = clazz.getMethod(getterName);
        } catch (final NoSuchMethodException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        if (getter.getParameterTypes().length != 0) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, getter + " must not have a parameter");
        }

        logger.debug("Has an status : {}", field.getName());
        if (getter.getReturnType() != String.class) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, getter + " must have a return type of  "
                    + String.class);
        }
        Method setter;
        final String setterName = BuilderUtils.SET + stateName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + stateName.substring(1);
        try {
            setter = clazz.getMethod(setterName, String.class);
        } catch (final NoSuchMethodException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        device.setStatusImpl(new StatusImpl(businessObject, getter, setter));
        final Status annot = field.getAnnotation(Status.class);
        if (annot.isPolled()) {
            device.addAttributePolling(DeviceImpl.STATUS_NAME, annot.pollingPeriod());
        }
        xlogger.exit();
    }

}
