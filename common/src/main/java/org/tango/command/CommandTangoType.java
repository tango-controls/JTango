package org.tango.command;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.tango.DeviceState;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoDs.TangoConst;

public enum CommandTangoType {
    /**
     *
     */
    VOID(TangoConst.Tango_DEV_VOID, void.class),
    /**
     *
     */
    DEVBOOLEAN(TangoConst.Tango_DEV_BOOLEAN, boolean.class),
    /**
     *
     */
    DEVVARBOOLEANARRAY(TangoConst.Tango_DEVVAR_BOOLEANARRAY, boolean[].class),
    /**
     *
     */
    DEVLONG64(TangoConst.Tango_DEV_LONG64, long.class),
    /**
     *
     */
    DEVVARLONG64ARRAY(TangoConst.Tango_DEVVAR_LONG64ARRAY, long[].class),
    /**
     *
     */
    DEVULONG64(TangoConst.Tango_DEV_ULONG64, long.class),
    /**
     *
     */
    DEVVARULONG64ARRAY(TangoConst.Tango_DEVVAR_ULONG64ARRAY, long[].class),
    /**
     *
     */
    DEVSHORT(TangoConst.Tango_DEV_SHORT, short.class),
    /**
     *
     */
    DEVVARSHORTARRAY(TangoConst.Tango_DEVVAR_SHORTARRAY, short[].class),
    /**
     *
     */
    DEVUSHORT(TangoConst.Tango_DEV_USHORT, short.class),
    /**
     *
     */
    DEVVARUSHORTARRAY(TangoConst.Tango_DEVVAR_USHORTARRAY, short[].class),
    /**
     *
     */
    DEVLONG(TangoConst.Tango_DEV_LONG, int.class),
    /**
     *
     */
    DEVVARLONGARRAY(TangoConst.Tango_DEVVAR_LONGARRAY, int[].class),
    /**
     *
     */
    DEVULONG(TangoConst.Tango_DEV_ULONG, int.class),
    /**
     *
     */
    DEVVARULONGARRAY(TangoConst.Tango_DEVVAR_ULONGARRAY, int[].class),
    /**
     *
     */
    DEVFLOAT(TangoConst.Tango_DEV_FLOAT, float.class),
    /**
     *
     */
    DEVVARFLOATARRAY(TangoConst.Tango_DEVVAR_FLOATARRAY, float[].class),
    /**
     *
     */
    DEVDOUBLE(TangoConst.Tango_DEV_DOUBLE, double.class),
    /**
     *
     */
    DEVVARDOUBLEARRAY(TangoConst.Tango_DEVVAR_DOUBLEARRAY, double[].class),
    /**
     *
     */
    DEVSTRING(TangoConst.Tango_DEV_STRING, String.class),
    /**
     *
     */
    DEVVARSTRINGARRAY(TangoConst.Tango_DEVVAR_STRINGARRAY, String[].class),
    /**
     *
     */
    DEVSTATE(TangoConst.Tango_DEV_STATE, DevState.class),
    /**
     *
     */
    // DevChar(TangoConst.Tango_DEV_CHAR, byte.class), -> no DevCharHelper in
    // IDL
    DEVVARCHARARRAY(TangoConst.Tango_DEVVAR_CHARARRAY, byte[].class),
    /**
     *
     */
    DEVUCHAR(TangoConst.Tango_DEV_UCHAR, byte.class),
    /**
     *
     */
    DEVENCONDED(TangoConst.Tango_DEV_ENCODED, DevEncoded.class),
    /**
     *
     */
    DEVVARLONGSTRINGARRAY(TangoConst.Tango_DEVVAR_LONGSTRINGARRAY, DevVarLongStringArray.class),
    /**
     *
     */
    DEVVARDOUBLESTRINGARRAY(TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY, DevVarDoubleStringArray.class);

    private static final Map<Class<?>, CommandTangoType> CLASS_TYPE_MAP = new HashMap<Class<?>, CommandTangoType>();
    static {

        CLASS_TYPE_MAP.put(Void.class, VOID);
        CLASS_TYPE_MAP.put(void.class, VOID);
        CLASS_TYPE_MAP.put(Boolean.class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(boolean.class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(Boolean[].class, DEVVARBOOLEANARRAY);
        CLASS_TYPE_MAP.put(boolean[].class, DEVVARBOOLEANARRAY);

        CLASS_TYPE_MAP.put(Short.class, DEVSHORT);
        CLASS_TYPE_MAP.put(short.class, DEVSHORT);
        CLASS_TYPE_MAP.put(Short[].class, DEVVARSHORTARRAY);
        CLASS_TYPE_MAP.put(short[].class, DEVVARSHORTARRAY);

        CLASS_TYPE_MAP.put(Long.class, DEVLONG64);
        CLASS_TYPE_MAP.put(long.class, DEVLONG64);
        CLASS_TYPE_MAP.put(Long[].class, DEVVARLONG64ARRAY);
        CLASS_TYPE_MAP.put(long[].class, DEVVARLONG64ARRAY);

        CLASS_TYPE_MAP.put(Float.class, DEVFLOAT);
        CLASS_TYPE_MAP.put(float.class, DEVFLOAT);
        CLASS_TYPE_MAP.put(Float[].class, DEVVARFLOATARRAY);
        CLASS_TYPE_MAP.put(float[].class, DEVVARFLOATARRAY);

        CLASS_TYPE_MAP.put(Double.class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(double.class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(Double[].class, DEVVARDOUBLEARRAY);
        CLASS_TYPE_MAP.put(double[].class, DEVVARDOUBLEARRAY);

        CLASS_TYPE_MAP.put(String.class, DEVSTRING);
        CLASS_TYPE_MAP.put(String[].class, DEVVARSTRINGARRAY);

        CLASS_TYPE_MAP.put(Integer.class, DEVLONG);
        CLASS_TYPE_MAP.put(int.class, DEVLONG);
        CLASS_TYPE_MAP.put(Integer[].class, DEVVARLONGARRAY);
        CLASS_TYPE_MAP.put(int[].class, DEVVARLONGARRAY);

        CLASS_TYPE_MAP.put(DevState.class, DEVSTATE);
        CLASS_TYPE_MAP.put(DevState[].class, DEVSTATE);
        CLASS_TYPE_MAP.put(DeviceState.class, DEVSTATE);
        CLASS_TYPE_MAP.put(DeviceState[].class, DEVSTATE);

        CLASS_TYPE_MAP.put(Byte.class, DEVUCHAR);
        CLASS_TYPE_MAP.put(byte.class, DEVUCHAR);
        CLASS_TYPE_MAP.put(Byte[].class, DEVVARCHARARRAY);
        CLASS_TYPE_MAP.put(byte[].class, DEVVARCHARARRAY);

        CLASS_TYPE_MAP.put(DevEncoded.class, DEVENCONDED);
        CLASS_TYPE_MAP.put(DevEncoded[].class, DEVENCONDED);

        CLASS_TYPE_MAP.put(DevVarLongStringArray.class, DEVVARLONGSTRINGARRAY);
        CLASS_TYPE_MAP.put(DevVarDoubleStringArray.class, DEVVARDOUBLESTRINGARRAY);
    }

    private int tangoIDLType;
    private final Class<?> clazz;

    private static final Map<Integer, CommandTangoType> TANGO_TYPE_MAP = new HashMap<Integer, CommandTangoType>();
    static {
        for (final CommandTangoType s : EnumSet.allOf(CommandTangoType.class)) {
            TANGO_TYPE_MAP.put(s.getTangoIDLType(), s);
        }
    }

    CommandTangoType(final int tangoIDLType, final Class<?> clazz) {
        this.tangoIDLType = tangoIDLType;
        this.clazz = clazz;
    }

    public int getTangoIDLType() {
        return tangoIDLType;
    }

    public static CommandTangoType getTypeFromClass(final Class<?> clazz) throws DevFailed {
        final CommandTangoType type = CLASS_TYPE_MAP.get(clazz);
        if (type == null) {
            throw DevFailedUtils.newDevFailed("TYPE_ERROR", clazz.getCanonicalName() + " is not a command type");
        }
        return type;
    }

    public static CommandTangoType getTypeFromTango(final int tangoType) throws DevFailed {
        final CommandTangoType result = TANGO_TYPE_MAP.get(tangoType);
        if (result == null) {
            throw DevFailedUtils.newDevFailed("TYPE_ERROR", tangoType + " is not an command type");
        }
        return result;
    }

    public Class<?> getCmdClass() {
        return clazz;
    }
}
