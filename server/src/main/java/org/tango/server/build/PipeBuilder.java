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
import org.tango.server.annotation.Pipe;
import org.tango.server.pipe.PipeConfiguration;
import org.tango.server.pipe.PipeImpl;
import org.tango.server.pipe.PipeValue;
import org.tango.server.pipe.ReflectPipeBehavior;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

final class PipeBuilder {

    private final Logger logger = LoggerFactory.getLogger(PipeBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(PipeBuilder.class);

    public void build(final DeviceImpl device, final Object businessObject, final Field field) throws DevFailed {
        final String fieldName = field.getName();
        xlogger.entry(fieldName);

        final Class<?> type = field.getType();
        if (!type.equals(PipeValue.class)) {
            throw DevFailedUtils.newDevFailed(BuilderUtils.INIT_ERROR, fieldName + " must be a PipeValue");
        }
        final String getterName = BuilderUtils.GET + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + fieldName.substring(1);
        Method getter = null;
        try {
            getter = businessObject.getClass().getMethod(getterName);
        } catch (final NoSuchMethodException e) {
            // pipe is write only
        }

        final String setterName = BuilderUtils.SET + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + fieldName.substring(1);

        Method setter = null;
        try {
            setter = businessObject.getClass().getMethod(setterName, type);
        } catch (final NoSuchMethodException e) {
            // pipe is read only
        }

        if (setter == null && getter == null) {
            throw DevFailedUtils.newDevFailed(BuilderUtils.INIT_ERROR, getterName + " or " + setterName
                    + BuilderUtils.METHOD_NOT_FOUND);
        }

        final Pipe annot = field.getAnnotation(Pipe.class);
        final String pipeName = BuilderUtils.getPipeName(fieldName, annot);
        final PipeConfiguration config = BuilderUtils.getPipeConfiguration(type, getter, setter, annot, pipeName);

        final PipeImpl pipe = new PipeImpl(new ReflectPipeBehavior(config, businessObject, getter, setter),
                device.getName());

        logger.debug("Has a pipe: {} {}", pipe.getName());
        BuilderUtils.setStateMachine(field, pipe);
        device.addPipe(pipe);
        xlogger.exit(field.getName());

    }

}
