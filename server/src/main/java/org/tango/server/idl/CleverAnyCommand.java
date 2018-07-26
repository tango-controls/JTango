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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.Any;
import org.tango.orb.ORBManager;
import org.tango.server.ExceptionMessages;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevBooleanHelper;
import fr.esrf.Tango.DevDoubleHelper;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevEncodedHelper;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevFloatHelper;
import fr.esrf.Tango.DevLong64Helper;
import fr.esrf.Tango.DevLongHelper;
import fr.esrf.Tango.DevShortHelper;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevStateHelper;
import fr.esrf.Tango.DevStringHelper;
import fr.esrf.Tango.DevUCharHelper;
import fr.esrf.Tango.DevULong64Helper;
import fr.esrf.Tango.DevULongHelper;
import fr.esrf.Tango.DevUShortHelper;
import fr.esrf.Tango.DevVarCharArrayHelper;
import fr.esrf.Tango.DevVarDoubleArrayHelper;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarDoubleStringArrayHelper;
import fr.esrf.Tango.DevVarFloatArrayHelper;
import fr.esrf.Tango.DevVarLong64ArrayHelper;
import fr.esrf.Tango.DevVarLongArrayHelper;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.DevVarLongStringArrayHelper;
import fr.esrf.Tango.DevVarShortArrayHelper;
import fr.esrf.Tango.DevVarStateArrayHelper;
import fr.esrf.Tango.DevVarStringArrayHelper;
import fr.esrf.Tango.DevVarULong64ArrayHelper;
import fr.esrf.Tango.DevVarULongArrayHelper;
import fr.esrf.Tango.DevVarUShortArrayHelper;
import fr.esrf.TangoDs.TangoConst;

/**
 * Convertion tool between java types and tango types for commands
 * 
 * @author ABEILLE
 * 
 */
public final class CleverAnyCommand {

    private static final Map<Integer, Class<?>> CLASS_MAP = new HashMap<Integer, Class<?>>();

    static {
        CLASS_MAP.put(TangoConst.Tango_DEV_BOOLEAN, DevBooleanHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_SHORT, DevShortHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_USHORT, DevUShortHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_LONG, DevLongHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_ULONG, DevULongHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_LONG64, DevLong64Helper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_ULONG64, DevULong64Helper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_FLOAT, DevFloatHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_DOUBLE, DevDoubleHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_STRING, DevStringHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_STATE, DevStateHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_UCHAR, DevUCharHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEV_ENCODED, DevEncodedHelper.class);
        //
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_SHORTARRAY, DevVarShortArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_USHORTARRAY, DevVarUShortArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_LONGARRAY, DevVarLongArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_ULONGARRAY, DevVarULongArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_LONG64ARRAY, DevVarLong64ArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_ULONG64ARRAY, DevVarULong64ArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_FLOATARRAY, DevVarFloatArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_DOUBLEARRAY, DevVarDoubleArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_STRINGARRAY, DevVarStringArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_CHARARRAY, DevVarCharArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY, DevVarDoubleStringArrayHelper.class);
        CLASS_MAP.put(TangoConst.Tango_DEVVAR_LONGSTRINGARRAY, DevVarLongStringArrayHelper.class);
    }

    private static final Map<Integer, Class<?>> PARAM_MAP = new HashMap<Integer, Class<?>>();

    static {
        PARAM_MAP.put(TangoConst.Tango_DEV_BOOLEAN, boolean.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_SHORT, short.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_USHORT, short.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_LONG, int.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_ULONG, int.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_LONG64, long.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_ULONG64, long.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_FLOAT, float.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_DOUBLE, double.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_STRING, String.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_STATE, DevState.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_UCHAR, byte.class);
        PARAM_MAP.put(TangoConst.Tango_DEV_ENCODED, DevEncoded.class);

        PARAM_MAP.put(TangoConst.Tango_DEVVAR_SHORTARRAY, short[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_USHORTARRAY, short[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_LONGARRAY, int[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_ULONGARRAY, int[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_LONG64ARRAY, long[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_ULONG64ARRAY, long[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_FLOATARRAY, float[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_DOUBLEARRAY, double[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_STRINGARRAY, String[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_CHARARRAY, byte[].class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY, DevVarDoubleStringArray.class);
        PARAM_MAP.put(TangoConst.Tango_DEVVAR_LONGSTRINGARRAY, DevVarLongStringArray.class);
    }
    /**
     * Command types for which, insertion must be done on Any (otherwise C++ client crashes)
     */
    private static final Map<Integer, String> INSERT_ANY_MAP = new HashMap<Integer, String>();
    static {
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_BOOLEAN, "insert_boolean");
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_SHORT, "insert_short");
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_LONG, "insert_long");
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_LONG64, "insert_longlong");
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_FLOAT, "insert_float");
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_DOUBLE, "insert_double");
        INSERT_ANY_MAP.put(TangoConst.Tango_DEV_STRING, "insert_string");
    }

    private CleverAnyCommand() {

    }

    /**
     * Get value of an Any
     * 
     * @param any
     * @param tangoType
     * @return The value
     * @throws DevFailed
     */
    public static Object get(final Any any, final int tangoType, final boolean asPrimitive) throws DevFailed {
        Object result = null;
        if (any != null) {
            try {
                final Class<?> extractorClass = CLASS_MAP.get(tangoType);
                if (extractorClass != null) { // command void
                    final Method method = extractorClass.getMethod("extract", Any.class);
                    result = method.invoke(null, any);
                }
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
            // convert to array of Object if necessary
            if (result != null && !asPrimitive) {
                result = org.tango.utils.ArrayUtils.toObjectArray(result);
            }
        }
        return result;
    }

    public static Any set(final int tangoType, final Object value) throws DevFailed {
        final Any any = ORBManager.createAny();
        if (value != null) {
            Object array = value;
            if (value.getClass().isArray()) {
                // convert to array of primitives if necessary
                array = org.tango.utils.ArrayUtils.toPrimitiveArray(value);
            }
            Method method = null;
            try {
                final String methodName = INSERT_ANY_MAP.get(tangoType);
                if (methodName != null) {
                    // insert directly in Any
                    method = any.getClass().getMethod(methodName, PARAM_MAP.get(tangoType));
                    method.invoke(any, array);
                } else {
                    // insert in Helper
                    if (tangoType == TangoConst.Tango_DEV_STATE && array instanceof DevState[]) {
                        // special case for array of DevState (for command history)
                        DevVarStateArrayHelper.insert(any, (DevState[]) array);
                    } else {
                        final Class<?> inserterClass = CLASS_MAP.get(tangoType);
                        method = inserterClass.getMethod("insert", Any.class, PARAM_MAP.get(tangoType));
                        method.invoke(null, any, array);
                    }
                }
            } catch (final IllegalArgumentException e) {
                throw DevFailedUtils.newDevFailed(ExceptionMessages.ATTR_OPT_PROP, value.getClass().getCanonicalName()
                        + " is not the good type, should be " + method);
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
