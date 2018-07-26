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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.tango.DeviceState;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.DeviceBehaviorObject;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.AttributeProperties;
import org.tango.server.annotation.Pipe;
import org.tango.server.annotation.StateMachine;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.pipe.PipeConfiguration;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import fr.esrf.Tango.PipeWriteType;

/**
 * Tools to build a tango device from a class with annotations {@link org.tango.server.annotation}
 *
 * @author ABEILLE
 *
 */
final class BuilderUtils {

    public static final String IS = "is";
    public static final String INIT_ERROR = "INIT_ERROR";
    static final String METHOD_NOT_FOUND = " method not found";
    public static final String SET = "set";
    public static final String GET = "get";
    private static final String MUST_NOT_BE_STATIC = " must not be static";

    private BuilderUtils() {

    }

    /**
     * Get attribute name from annotation {@link Attribute}
     *
     * @param fieldName
     * @param annot
     * @return
     */
    static String getAttributeName(final String fieldName, final Attribute annot) {
        String attributeName = null;
        if (annot.name().equals("")) {
            attributeName = fieldName;
        } else {
            attributeName = annot.name();
        }
        return attributeName;
    }

    /**
     * Get pipe name from annotation {@link Pipe}
     *
     * @param fieldName
     * @param annot
     * @return
     */
    static String getPipeName(final String fieldName, final Pipe annot) {
        String attributeName = null;
        if (annot.name().equals("")) {
            attributeName = fieldName;
        } else {
            attributeName = annot.name();
        }
        return attributeName;
    }

    /**
     * Check if a field is static
     *
     * @param field
     * @throws DevFailed
     *             if static
     */
    static void checkStatic(final Field field) throws DevFailed {
        if (Modifier.isStatic(field.getModifiers())) {
            throw DevFailedUtils.newDevFailed(DevFailedUtils.TANGO_BUILD_FAILED, field + MUST_NOT_BE_STATIC);
        }
    }

    static AttributeConfiguration getAttributeConfiguration(final Class<?> type, final Method getter,
            final Method setter, final Attribute annot, final String attributeName) throws DevFailed {
        final AttributeConfiguration config = new AttributeConfiguration();
        config.setName(attributeName);
        config.setDispLevel(DispLevel.from_int(annot.displayLevel()));
        config.setMaxX(annot.maxDimX());
        config.setMaxY(annot.maxDimY());
        config.setType(type);
        config.setMemorized(annot.isMemorized());
        config.setMemorizedAtInit(annot.isMemorizedAtInit());
        config.setPolled(annot.isPolled());
        config.setPollingPeriod(annot.pollingPeriod());
        config.setPushDataReady(annot.pushDataReady());
        config.setPushChangeEvent(annot.pushChangeEvent());
        config.setCheckChangeEvent(annot.checkChangeEvent());
        config.setPushArchiveEvent(annot.pushArchiveEvent());
        config.setCheckArchivingEvent(annot.checkArchivingEvent());
        if (setter == null) {
            config.setWritable(AttrWriteType.READ);
        } else if (getter == null) {
            config.setWritable(AttrWriteType.WRITE);
        } else {
            config.setWritable(AttrWriteType.READ_WRITE);
        }
        return config;
    }

    static PipeConfiguration getPipeConfiguration(final Class<?> type, final Method getter, final Method setter,
            final Pipe annot, final String pipeName) throws DevFailed {
        final PipeConfiguration config = new PipeConfiguration(pipeName);
        if (setter == null) {
            config.setWriteType(PipeWriteType.PIPE_READ);
        } else {
            config.setWriteType(PipeWriteType.PIPE_READ_WRITE);
        }
        config.setDisplayLevel(DispLevel.from_int(annot.displayLevel()));
        if (annot.label().equals("")) {
            config.setLabel(pipeName);
        } else {
            config.setLabel(annot.label());
        }
        config.setDescription(annot.description());
        // config.setPollingPeriod(annot.pollingPeriod());
        // TODO config.setPushDataReady(annot.pushDataReady());
        // TODO config.setPushChangeEvent(annot.pushChangeEvent());
        // TODO config.setCheckChangeEvent(annot.checkChangeEvent());
        // TODO config.setPushArchiveEvent(annot.pushArchiveEvent());
        // TODO config.setCheckArchivingEvent(annot.checkArchivingEvent());

        return config;
    }

    /**
     * Set enum label for Enum types
     *
     * @param type
     * @param props
     * @throws DevFailed
     */
    static AttributePropertiesImpl setEnumLabelProperty(final Class<?> type, final AttributePropertiesImpl props)
            throws DevFailed {
        // if is is an enum set enum values in properties
        if (AttributeTangoType.getTypeFromClass(type).equals(AttributeTangoType.DEVENUM)) {
            // final Class<Enum<?>> enumType = (Class<Enum<?>>) field.getType();
            final Object[] enumValues = type.getEnumConstants();
            final String[] enumLabels = new String[enumValues.length];
            for (int i = 0; i < enumLabels.length; i++) {
                enumLabels[i] = enumValues[i].toString();
            }
            props.setEnumLabels(enumLabels, false);
        }
        return props;
    }

    static AttributePropertiesImpl getAttributeProperties(final AccessibleObject method, final String attributeName,
            final Class<?> attributeScalarType) throws DevFailed {
        // add default attr properties
        final AttributePropertiesImpl props = new AttributePropertiesImpl();
        if (method.isAnnotationPresent(AttributeProperties.class)) {
            final AttributeProperties annotProp = method.getAnnotation(AttributeProperties.class);
            if (annotProp.label().equals("")) {
                props.setLabel(attributeName);
            } else {
                props.setLabel(annotProp.label());
            }
            props.setDescription(annotProp.description());
            props.setDisplayUnit(annotProp.displayUnit());
            props.setUnit(annotProp.unit());
            props.setStandardUnit(annotProp.standardUnit());
            // set default format by attribute type
            final String annotFormat = annotProp.format();
            if (annotFormat.isEmpty()) {
                props.setDefaultFormat(attributeScalarType);
            } else {
                props.setFormat(annotProp.format());
            }
            props.setMaxAlarm(annotProp.maxAlarm());
            props.setMinAlarm(annotProp.minAlarm());
            props.setMaxValue(annotProp.maxValue());
            props.setMinValue(annotProp.minValue());
            props.setMinWarning(annotProp.minWarning());
            props.setMaxWarning(annotProp.maxWarning());
            props.setDeltaT(annotProp.deltaTime());
            props.setDeltaVal(annotProp.deltaValue());
            // event
            props.setEventAbsChange(annotProp.changeEventAbsolute());
            props.setEventRelChange(annotProp.changeEventRelative());
            props.setEventPeriod(annotProp.periodicEvent());
            props.setArchivingEventRelChange(annotProp.archiveEventRelative());
            props.setArchivingEventAbsChange(annotProp.archiveEventAbsolute());
            props.setArchivingEventPeriod(annotProp.archiveEventPeriod());
            props.setRootAttribute(annotProp.rootAttribute());
        } else {
            props.setLabel(attributeName);
            props.setDefaultFormat(attributeScalarType);
        }
        return props;
    }

    static void setStateMachine(final AccessibleObject annotatedObject, final DeviceBehaviorObject behavior) {
        final StateMachine stateMach = annotatedObject.getAnnotation(StateMachine.class);
        if (stateMach != null) {
            if (stateMach.endState() != DeviceState.UNKNOWN) {
                behavior.setEndState(stateMach.endState());
            }
            if (stateMach.deniedStates().length != 0) {
                behavior.setDeniedStates(stateMach.deniedStates());
            }
        }
    }

}
