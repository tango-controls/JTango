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

import org.omg.CORBA.Any;
import org.tango.attribute.AttributeTangoType;
import org.tango.orb.ORBManager;
import org.tango.server.ExceptionMessages;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarBooleanArrayHelper;
import fr.esrf.Tango.DevVarCharArrayHelper;
import fr.esrf.Tango.DevVarDoubleArrayHelper;
import fr.esrf.Tango.DevVarEncodedArrayHelper;
import fr.esrf.Tango.DevVarFloatArrayHelper;
import fr.esrf.Tango.DevVarLong64ArrayHelper;
import fr.esrf.Tango.DevVarLongArrayHelper;
import fr.esrf.Tango.DevVarShortArrayHelper;
import fr.esrf.Tango.DevVarStateArrayHelper;
import fr.esrf.Tango.DevVarStringArrayHelper;
import fr.esrf.Tango.DevVarULong64ArrayHelper;
import fr.esrf.Tango.DevVarULongArrayHelper;
import fr.esrf.Tango.DevVarUShortArrayHelper;
import fr.esrf.TangoDs.TangoConst;

/**
 * Convertion tool between java types and tango types for attributes
 * 
 * @author ABEILLE
 * 
 */
public final class CleverAnyAttribute {

    private static final Map<Integer, Class<?>> CLASS_MAP = new HashMap<Integer, Class<?>>();

    static {
        CLASS_MAP.put(TangoConst.Tango_DEV_BOOLEAN, DevVarBooleanArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_SHORT, DevVarShortArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_USHORT, DevVarUShortArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_LONG, DevVarLongArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_ULONG, DevVarULongArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_LONG64, DevVarLong64ArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_ULONG64, DevVarULong64ArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_FLOAT, DevVarFloatArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_DOUBLE, DevVarDoubleArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_STRING, DevVarStringArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_STATE, DevVarStateArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_UCHAR, DevVarCharArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_ENCODED, DevVarEncodedArrayHelper.class);
    }

    private static final Map<Integer, Class<?>> PARAM_MAP = new HashMap<Integer, Class<?>>();

    static {
        PARAM_MAP.put(TangoConst.Tango_DEV_BOOLEAN, boolean[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_SHORT, short[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_USHORT, short[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_LONG, int[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_ULONG, int[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_LONG64, long[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_ULONG64, long[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_FLOAT, float[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_DOUBLE, double[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_STRING, String[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_STATE, DevState[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_UCHAR, byte[].class);
        PARAM_MAP.put(TangoConst.Tango_DEV_ENCODED, DevEncoded[].class);
    }

    private CleverAnyAttribute() {

    }

    /**
     * Get value of an Any
     * 
     * @param any
     * @param tangoType
     * @return The value
     * @throws DevFailed
     */
    public static Object get(final Any any, final int tangoType, final AttrDataFormat format) throws DevFailed {
        Object result = null;
        if (any != null) {
            try {
                final Class<?> extractorClass = CLASS_MAP.get(tangoType);
                final Method method = extractorClass.getMethod("extract", Any.class);
                result = method.invoke(null, any);
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
            if (format.equals(AttrDataFormat.SCALAR)) {
                // for scalar except state, get only first value
                result = Array.get(result, 0);
            }
        }
        return result;
    }

    /**
     * Set a value in an any
     * 
     * @param tangoType
     * @param value
     * @return the Any
     * @throws DevFailed
     */
    public static Any set(final int tangoType, final Object value) throws DevFailed {
        final Any any = ORBManager.createAny();

        if (value != null) {
            Object array = null;
            if (value.getClass().isArray()) {
                // convert to array of primitives if necessary
                array = org.tango.utils.ArrayUtils.toPrimitiveArray(value);
            } else {
                // put in an array before inserting for scalars
                array = Array.newInstance(AttributeTangoType.getTypeFromTango(tangoType).getType(), 1);
                Array.set(array, 0, value);
            }
            Method method = null;
            try {
                final Class<?> inserterClass = CLASS_MAP.get(tangoType);
                method = inserterClass.getMethod("insert", Any.class, PARAM_MAP.get(tangoType));
                method.invoke(null, any, array);
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP, value.getClass().getCanonicalName()
                        + " is not of the good type, should be " + method);
            } catch (final IllegalAccessException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final InvocationTargetException e) {
                throw DevFailedUtils.newDevFailed(e.getCause());
            } catch (final SecurityException e) {
                throw DevFailedUtils.newDevFailed(e);
            } catch (final NoSuchMethodException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        return any;
    }
}
