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
import org.tango.server.annotation.Attribute;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.ReflectAttributeBehavior;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build a {@link Attribute}
 *
 * @author ABEILLE
 *
 */
final class AttributeFieldBuilder {
    private final Logger logger = LoggerFactory.getLogger(AttributeFieldBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributeFieldBuilder.class);

    public void build(final DeviceImpl device, final Object businessObject, final Field field,
            final boolean isOnDeviceImpl) throws DevFailed {
        final String fieldName = field.getName();
        xlogger.entry(fieldName);

        Object target;
        if (isOnDeviceImpl) {
            target = device;
        } else {
            target = businessObject;
        }
        // final String fieldName = field.getName();
        final Class<?> type = field.getType();
        String getterName = BuilderUtils.GET + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + fieldName.substring(1);
        Method getter = null;
        try {
            getter = target.getClass().getMethod(getterName);
        } catch (final NoSuchMethodException e) {
            // try is for boolean getter
            if (fieldName.startsWith(BuilderUtils.IS)) {
                getterName = fieldName;
            } else {
                getterName = BuilderUtils.IS + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                        + fieldName.substring(1);
            }
            try {
                getter = target.getClass().getMethod(getterName);
            } catch (final NoSuchMethodException e1) {
                // attribute is write only
            }
        }

        String setterName = BuilderUtils.SET + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                + fieldName.substring(1);

        Method setter = null;
        try {
            setter = target.getClass().getMethod(setterName, type);
        } catch (final NoSuchMethodException e) {
            if (fieldName.startsWith(BuilderUtils.IS)) {
                // may be a boolean attribute
                setterName = BuilderUtils.SET + fieldName.substring(2);
                try {
                    setter = businessObject.getClass().getMethod(setterName, type);
                } catch (final NoSuchMethodException e1) {
                    throw DevFailedUtils.newDevFailed(e);
                }
            }
            // attribute is read only
        }

        if (setter == null && getter == null) {
            throw DevFailedUtils.newDevFailed(BuilderUtils.INIT_ERROR, getterName + " or " + setterName
                    + BuilderUtils.METHOD_NOT_FOUND);
        }

        final Attribute annot = field.getAnnotation(Attribute.class);
        final String attributeName = BuilderUtils.getAttributeName(fieldName, annot);
        final AttributeConfiguration config = BuilderUtils.getAttributeConfiguration(type, getter, setter, annot,
                attributeName);

        // add default attr properties
        AttributePropertiesImpl props = BuilderUtils.getAttributeProperties(field, attributeName,
                config.getScalarType());
        props = BuilderUtils.setEnumLabelProperty(type, props);
        config.setAttributeProperties(props);

        final AttributeImpl attr = new AttributeImpl(new ReflectAttributeBehavior(config, target, getter, setter),
                device.getName());
        logger.debug("Has an attribute: {} {}", attr.getName(), attr.getFormat().value());
        BuilderUtils.setStateMachine(field, attr);
        device.addAttribute(attr);
        xlogger.exit(field.getName());

    }
}
