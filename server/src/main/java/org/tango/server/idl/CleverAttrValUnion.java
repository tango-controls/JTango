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
package org.tango.server.idl;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.tango.attribute.AttributeTangoType;
import org.tango.server.ExceptionMessages;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrValUnion;
import fr.esrf.Tango.AttributeDataType;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

/**
 * A helper class to insert / extract AttrValUnion with the method that matches the attribute type
 * 
 * @author ABEILLE
 * 
 */
public final class CleverAttrValUnion {

    private static final Map<AttributeDataType, String> METHOD_MAP = new HashMap<AttributeDataType, String>();

    static {
        METHOD_MAP.put(AttributeDataType.ATT_BOOL, "bool_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_SHORT, "short_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_USHORT, "ushort_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_LONG, "long_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_ULONG, "ulong_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_LONG64, "long64_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_ULONG64, "ulong64_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_FLOAT, "float_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_DOUBLE, "double_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_STRING, "string_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_STATE, "state_att_value");
        METHOD_MAP.put(AttributeDataType.DEVICE_STATE, "dev_state_att");
        METHOD_MAP.put(AttributeDataType.ATT_UCHAR, "uchar_att_value");
        METHOD_MAP.put(AttributeDataType.ATT_ENCODED, "encoded_att_value");
    }

    private static final Map<AttributeDataType, Class<?>> PARAM_MAP = new HashMap<AttributeDataType, Class<?>>();

    static {
        PARAM_MAP.put(AttributeDataType.ATT_BOOL, boolean[].class);
        PARAM_MAP.put(AttributeDataType.ATT_SHORT, short[].class);
        PARAM_MAP.put(AttributeDataType.ATT_USHORT, short[].class);
        PARAM_MAP.put(AttributeDataType.ATT_LONG, int[].class);
        PARAM_MAP.put(AttributeDataType.ATT_ULONG, int[].class);
        PARAM_MAP.put(AttributeDataType.ATT_LONG64, long[].class);
        PARAM_MAP.put(AttributeDataType.ATT_ULONG64, long[].class);
        PARAM_MAP.put(AttributeDataType.ATT_FLOAT, float[].class);
        PARAM_MAP.put(AttributeDataType.ATT_DOUBLE, double[].class);
        PARAM_MAP.put(AttributeDataType.ATT_STRING, String[].class);
        PARAM_MAP.put(AttributeDataType.ATT_STATE, DevState[].class);
        PARAM_MAP.put(AttributeDataType.DEVICE_STATE, DevState.class);
        PARAM_MAP.put(AttributeDataType.ATT_UCHAR, byte[].class);
        PARAM_MAP.put(AttributeDataType.ATT_ENCODED, DevEncoded[].class);
    }

    private CleverAttrValUnion() {

    }

    /**
     * Get value from an AttrValUnion
     * 
     * @param union
     * @param format
     * @return The value
     * @throws DevFailed
     */
    public static Object get(final AttrValUnion union, final AttrDataFormat format) throws DevFailed {
        Object result = null;
        if (union != null) {
            final AttributeDataType discriminator = union.discriminator();
            if (discriminator.value() == AttributeDataType._ATT_NO_DATA) {
                throw DevFailedUtils.newDevFailed("there is not data");
            }
            try {
                final Method method = union.getClass().getMethod(METHOD_MAP.get(discriminator));
                result = method.invoke(union);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throw DevFailedUtils.newDevFailed(e.getCause());
            } catch (final SecurityException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final NoSuchMethodException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
            if (format.equals(AttrDataFormat.SCALAR) && !discriminator.equals(AttributeDataType.DEVICE_STATE)) {
                // for scalar except state, get only first value
                result = Array.get(result, 0);
            }
        }
        return result;
    }

    /**
     * Set a value into an AttrValUnion
     * 
     * @param tangoType
     * @param value
     * @return The union
     * @throws DevFailed
     */
    public static AttrValUnion set(final int tangoType, final Object value) throws DevFailed {
        final AttributeDataType discriminator = AttributeTangoType.getTypeFromTango(tangoType).getAttributeDataType();
        final AttrValUnion union = new AttrValUnion();
        Object array = null;
        if (value.getClass().isArray()) {
            // convert to array of primitives if necessary
            array = org.tango.utils.ArrayUtils.toPrimitiveArray(value);
        } else {
            // put in an array before inserting for scalars
            array = Array.newInstance(AttributeTangoType.getTypeFromTango(tangoType).getType(), 1);
            try {
                Array.set(array, 0, value);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP, value.getClass().getCanonicalName()
                        + " is not of the good type");
            }
        }
        try {
            final Method method = union.getClass().getMethod(METHOD_MAP.get(discriminator),
                    PARAM_MAP.get(discriminator));
            method.invoke(union, array);
        } catch (final IllegalArgumentException e) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP, value.getClass().getCanonicalName()
                    + " is not of the good type");
        } catch (final IllegalAccessException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final InvocationTargetException e) {
            throw DevFailedUtils.newDevFailed(e.getCause());
        } catch (final SecurityException e) {
            throw DevFailedUtils.newDevFailed(e);
        } catch (final NoSuchMethodException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
        return union;
    }
}
