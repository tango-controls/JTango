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
package org.tango.server.dynamic.attribute;

import java.util.HashMap;
import java.util.Map;

import org.tango.DeviceState;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.attribute.ISetValueUpdater;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;

public final class PublisherAttribute implements IAttributeBehavior, ISetValueUpdater {
    private static final Map<String, Class<?>> JAVA_TYPES_MAP = new HashMap<String, Class<?>>();
    static {
        JAVA_TYPES_MAP.put("int", int.class);
        JAVA_TYPES_MAP.put("long", long.class);
        JAVA_TYPES_MAP.put("double", double.class);
        JAVA_TYPES_MAP.put("float", float.class);
        JAVA_TYPES_MAP.put("boolean", boolean.class);
        JAVA_TYPES_MAP.put("byte", byte.class);
        JAVA_TYPES_MAP.put("short", short.class);
        JAVA_TYPES_MAP.put("string", String.class);
        JAVA_TYPES_MAP.put("devstate", DeviceState.class);
        JAVA_TYPES_MAP.put("state", DeviceState.class);
        JAVA_TYPES_MAP.put("devencoded", DevEncoded.class);
        JAVA_TYPES_MAP.put("int[]", int[].class);
        JAVA_TYPES_MAP.put("long[]", long[].class);
        JAVA_TYPES_MAP.put("double[]", double[].class);
        JAVA_TYPES_MAP.put("float[]", float[].class);
        JAVA_TYPES_MAP.put("boolean[]", boolean[].class);
        JAVA_TYPES_MAP.put("byte[]", byte[].class);
        JAVA_TYPES_MAP.put("short[]", short[].class);
        JAVA_TYPES_MAP.put("string[]", String[].class);
        JAVA_TYPES_MAP.put("devstate[]", DeviceState[].class);
        JAVA_TYPES_MAP.put("state[]", DeviceState[].class);
        JAVA_TYPES_MAP.put("devencoded[]", DevEncoded[].class);
        JAVA_TYPES_MAP.put("int[][]", int[][].class);
        JAVA_TYPES_MAP.put("long[][]", long[][].class);
        JAVA_TYPES_MAP.put("double[][]", double[][].class);
        JAVA_TYPES_MAP.put("float[][]", float[][].class);
        JAVA_TYPES_MAP.put("boolean[][]", boolean[][].class);
        JAVA_TYPES_MAP.put("byte[][]", byte[][].class);
        JAVA_TYPES_MAP.put("short[][]", short[][].class);
        JAVA_TYPES_MAP.put("string[][]", String[][].class);
        JAVA_TYPES_MAP.put("state[][]", DeviceState[][].class);
        JAVA_TYPES_MAP.put("devstate[][]", DeviceState[][].class);
        JAVA_TYPES_MAP.put("devencoded[][]", DevEncoded[][].class);
    }
    private final AttributeConfiguration configAttr = new AttributeConfiguration();
    private AttributeValue value;

    /**
     * Create a dynamic attribute publisher
     *
     * @param config
     *            config[0] attribute name; config[1] type (ie. double, double[], double[][])
     * @throws ClassNotFoundException
     * @throws DevFailed
     */
    public PublisherAttribute(final String... config) throws DevFailed {

        if (config.length >= 1) {
            configAttr.setName(config[0]);
        } else {
            configAttr.setName("");
            throw DevFailedUtils.newDevFailed("DEVICE_PROP_ERROR", "unknown attribute config");
        }
        if (config.length == 2) {
            final Class<?> c = JAVA_TYPES_MAP.get(config[1].toLowerCase());
            configAttr.setType(c);
        } else if (config.length >= 3) {
            int typeAttr = 0;
            try {
                typeAttr = Integer.parseInt(config[1]);
            } catch (final NumberFormatException e) {
                typeAttr = org.tango.utils.TangoTypeUtils.getAttributeType(config[1]);
            }

            if (config[2].equalsIgnoreCase("SCALAR")) {
                configAttr.setTangoType(typeAttr, AttrDataFormat.SCALAR);
            } else if (config[2].equalsIgnoreCase("SPECTRUM")) {
                configAttr.setTangoType(typeAttr, AttrDataFormat.SPECTRUM);
            } else if (config[2].equalsIgnoreCase("IMAGE")) {
                configAttr.setTangoType(typeAttr, AttrDataFormat.IMAGE);
            } else {
                throw DevFailedUtils.newDevFailed("DEVICE_PROP_ERROR", "unknown attribute format: " + config[1]);
            }
        } else {
            throw DevFailedUtils.newDevFailed("DEVICE_PROP_ERROR", "unknown attribute config");
        }
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
        configAttr.setWritable(AttrWriteType.WRITE);
        configAttr.setMemorized(true);
        final AttributePropertiesImpl prop = new AttributePropertiesImpl();
        prop.setLabel(configAttr.getName());
        configAttr.setAttributeProperties(prop);
        return configAttr;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        return null;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        this.value = value;
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        return null;
    }

    @Override
    public AttributeValue getSetValue() throws DevFailed {
        if (value != null) {
            value.setTime(System.currentTimeMillis());
        }
        return value;
    }

}
