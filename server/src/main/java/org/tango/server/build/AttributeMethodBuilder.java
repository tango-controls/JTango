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
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.annotation.Attribute;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeImpl;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.ReflectAttributeBehavior;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Build an {@link Attribute}
 *
 * @author ABEILLE
 *
 */
final class AttributeMethodBuilder {

    private final Logger logger = LoggerFactory.getLogger(AttributeMethodBuilder.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(AttributeMethodBuilder.class);

    /**
     * Create a Tango attribute {@link Attribute}
     *
     * @param device
     * @param businessObject
     * @param method
     * @param isOnDeviceImpl
     * @throws DevFailed
     */
    public void build(final DeviceImpl device, final Object businessObject, final Method method,
            final boolean isOnDeviceImpl) throws DevFailed {
        xlogger.entry();
        Object target;
        if (isOnDeviceImpl) {
            target = device;
        } else {
            target = businessObject;
        }
        checkSyntax(method);

        // retrieve field attribute
        final String getterName;
        final String setterName;
        final String removedGet;
        final String fieldName;
        final Class<?> type;
        Method setter = null;
        Method getter = null;
        if (method.getName().startsWith(BuilderUtils.GET) || method.getName().startsWith(BuilderUtils.IS)) {
            getter = method;
            getterName = method.getName();
            removedGet = getterName.startsWith(BuilderUtils.GET) ? getterName.substring(3) : getterName;
            setterName = BuilderUtils.SET + removedGet;
            fieldName = removedGet.substring(0, 1).toLowerCase(Locale.ENGLISH) + removedGet.substring(1);
            type = method.getReturnType();
            try {
                setter = target.getClass().getMethod(setterName, type);
            } catch (final NoSuchMethodException e) {
                // attribute is write only
            }
        } else {
            setter = method;
            setterName = method.getName();
            removedGet = setterName.substring(3);
            fieldName = removedGet.substring(0, 1).toLowerCase(Locale.ENGLISH) + removedGet.substring(1);
            if (method.getParameterTypes().length != 1) {
                throw DevFailedUtils
                .newDevFailed(BuilderUtils.INIT_ERROR, setterName + " must have only one parameter");
            }
            type = method.getParameterTypes()[0];
            final Class<?> attrType = AttributeTangoType.getTypeFromClass(type).getType();
            if (boolean.class.isAssignableFrom(attrType)) {
                getterName = BuilderUtils.IS + removedGet;
            } else {
                getterName = BuilderUtils.GET + removedGet;
            }
            try {
                getter = target.getClass().getMethod(getterName);
            } catch (final NoSuchMethodException e) {
                // attribute is write only
            }
        }

        checkNull(getterName, setterName, setter, getter);

        final Attribute annot = method.getAnnotation(Attribute.class);
        final String attributeName = BuilderUtils.getAttributeName(fieldName, annot);

        final AttributeConfiguration config = BuilderUtils.getAttributeConfiguration(type, getter, setter, annot,
                attributeName);
        AttributePropertiesImpl props = BuilderUtils.getAttributeProperties(method, attributeName,
                config.getScalarType());
        props = BuilderUtils.setEnumLabelProperty(type, props);
        config.setAttributeProperties(props);

        final AttributeImpl attr = new AttributeImpl(new ReflectAttributeBehavior(config, target, getter, setter),
                device.getName());
        configureStateMachine(setter, getter, attr);
        logger.debug("Has an attribute: {} {}", attr.getName(), attr.getFormat().value());

        device.addAttribute(attr);
        xlogger.exit();
    }

    private void configureStateMachine(final Method setter, final Method getter, final AttributeImpl attr) {
        if (getter != null) {
            BuilderUtils.setStateMachine(getter, attr);
        }
        if (setter != null) {
            BuilderUtils.setStateMachine(setter, attr);
        }
    }

    private void checkNull(final String getterName, final String setterName, final Method setter, final Method getter)
            throws DevFailed {
        if (setter == null && getter == null) {
            throw DevFailedUtils.newDevFailed(BuilderUtils.INIT_ERROR, getterName + " or " + setterName
                    + BuilderUtils.METHOD_NOT_FOUND);
        }
    }

    private void checkSyntax(final Method method) throws DevFailed {
        if (!method.getName().startsWith(BuilderUtils.GET) && !method.getName().startsWith(BuilderUtils.IS)
                && !method.getName().startsWith(BuilderUtils.SET)) {
            throw DevFailedUtils.newDevFailed(BuilderUtils.INIT_ERROR, method + " can only be a get/set method");
        }
    }

}
