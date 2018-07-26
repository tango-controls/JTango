package org.tango.attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tango.DeviceState;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttributeDataType;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.TangoConst;

/**
 * Define all types supported as tango attributes
 * 
 * @author ABEILLE
 * 
 */
public enum AttributeTangoType {

    /**
     * 
     */
    DEVBOOLEAN(AttributeDataType.ATT_BOOL, TangoConst.Tango_DEV_BOOLEAN, boolean.class),

    /**
     * 
     */
    DEVLONG64(AttributeDataType.ATT_LONG64, TangoConst.Tango_DEV_LONG64, long.class),
    /**
     * 
     */
    DEVULONG64(AttributeDataType.ATT_ULONG64, TangoConst.Tango_DEV_ULONG64, long.class),
    /**
	     * 
	     */
    DEVSHORT(AttributeDataType.ATT_SHORT, TangoConst.Tango_DEV_SHORT, short.class),
    /**
     * 
     */
    DEVUSHORT(AttributeDataType.ATT_USHORT, TangoConst.Tango_DEV_USHORT, short.class),
    /**
	     * 
	     */
    DEVLONG(AttributeDataType.ATT_LONG, TangoConst.Tango_DEV_LONG, int.class),
    /**
	     * 
	     */
    DEVULONG(AttributeDataType.ATT_ULONG, TangoConst.Tango_DEV_ULONG, int.class),
    /**
	     * 
	     */
    DEVFLOAT(AttributeDataType.ATT_FLOAT, TangoConst.Tango_DEV_FLOAT, float.class),
    /**
	     * 
	     */
    DEVDOUBLE(AttributeDataType.ATT_DOUBLE, TangoConst.Tango_DEV_DOUBLE, double.class),
    /**
     * 
     */
    DEVSTRING(AttributeDataType.ATT_STRING, TangoConst.Tango_DEV_STRING, String.class),
    /**
	     * 
	     */
    DEVSTATE(AttributeDataType.ATT_STATE, TangoConst.Tango_DEV_STATE, DevState.class),
    /**
	     * 
	     */
    DEVUCHAR(AttributeDataType.ATT_UCHAR, TangoConst.Tango_DEV_UCHAR, byte.class),
    /**
	     * 
	     */
    DEVENCODED(AttributeDataType.ATT_ENCODED, TangoConst.Tango_DEV_ENCODED, DevEncoded.class),

    DEVENUM(AttributeDataType.ATT_SHORT, TangoConst.Tango_DEV_ENUM, short.class);

    private final AttributeDataType attributeDataType;
    private final Class<?> type;
    private final int tangoIDLType;

    private static final Map<Integer, AttributeTangoType> TANGO_TYPE_MAP = new HashMap<Integer, AttributeTangoType>();
    static {
        for (final AttributeTangoType s : EnumSet.allOf(AttributeTangoType.class)) {
            TANGO_TYPE_MAP.put(s.getTangoIDLType(), s);
        }
    }

    private static Class<?>[] OBJECTS = new Class[] { Void.class, Boolean.class, Byte.class, Short.class,
            Integer.class, Float.class, Double.class, Long.class, DeviceState.class };

    /**
     * The supported types for tango attributes
     */
    public static final List<Class<?>> ATTRIBUTE_CLASSES = new ArrayList<Class<?>>();
    static {
        for (final AttributeTangoType s : EnumSet.allOf(AttributeTangoType.class)) {
            ATTRIBUTE_CLASSES.add(s.getType());
        }
        ATTRIBUTE_CLASSES.addAll(Arrays.asList(OBJECTS));
    }

    /**
     * Default values for attributes
     */
    private static final Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<Class<?>, Object>();
    static {
        DEFAULT_VALUES.put(boolean.class, false);
        DEFAULT_VALUES.put(Boolean.class, false);
        DEFAULT_VALUES.put(boolean[].class, new boolean[] { false });
        DEFAULT_VALUES.put(Boolean[].class, new Boolean[] { false });
        DEFAULT_VALUES.put(boolean[][].class, new boolean[] {});
        DEFAULT_VALUES.put(Boolean[][].class, new Boolean[] {});

        DEFAULT_VALUES.put(short.class, (short) 0);
        DEFAULT_VALUES.put(Short.class, Short.valueOf((short) 0));
        DEFAULT_VALUES.put(short[].class, new short[] { (short) 0 });
        DEFAULT_VALUES.put(Short[].class, new Short[] { (short) 0 });
        DEFAULT_VALUES.put(short[][].class, new short[] {});
        DEFAULT_VALUES.put(Short[][].class, new Short[] {});

        DEFAULT_VALUES.put(long.class, 0L);
        DEFAULT_VALUES.put(Long.class, Long.valueOf(0L));
        DEFAULT_VALUES.put(long[].class, new long[] { 0 });
        DEFAULT_VALUES.put(Long[].class, new Long[] { 0L });
        DEFAULT_VALUES.put(long[][].class, new long[] {});
        DEFAULT_VALUES.put(Long[][].class, new Long[] {});

        DEFAULT_VALUES.put(float.class, 0.0F);
        DEFAULT_VALUES.put(Float.class, Float.valueOf(0.0F));
        DEFAULT_VALUES.put(float[].class, new float[] { 0.0F });
        DEFAULT_VALUES.put(Float[].class, new Float[] { 0.0F });
        DEFAULT_VALUES.put(float[][].class, new float[] {});
        DEFAULT_VALUES.put(Float[][].class, new Float[] {});

        DEFAULT_VALUES.put(double.class, 0.0);
        DEFAULT_VALUES.put(Double.class, Double.valueOf(0.0));
        DEFAULT_VALUES.put(double[].class, new double[] { 0.0 });
        DEFAULT_VALUES.put(Double[].class, new Double[] { 0.0 });
        DEFAULT_VALUES.put(double[][].class, new double[] {});
        DEFAULT_VALUES.put(Double[][].class, new Double[] {});

        DEFAULT_VALUES.put(String.class, "Not initialised");
        DEFAULT_VALUES.put(String[].class, new String[] { "Not initialised" });
        DEFAULT_VALUES.put(String[][].class, new String[] {});

        DEFAULT_VALUES.put(int.class, 0);
        DEFAULT_VALUES.put(Integer.class, 0);
        DEFAULT_VALUES.put(int[].class, new int[] { 0 });
        DEFAULT_VALUES.put(Integer[].class, new Integer[] { 0 });
        DEFAULT_VALUES.put(int[][].class, new int[] {});
        DEFAULT_VALUES.put(Integer[][].class, new Integer[] {});

        DEFAULT_VALUES.put(DeviceState.class, DeviceState.UNKNOWN);
        DEFAULT_VALUES.put(DeviceState[].class, new DeviceState[] { DeviceState.UNKNOWN });
        DEFAULT_VALUES.put(DeviceState[][].class, new DeviceState[] {});

        DEFAULT_VALUES.put(DevState.class, DevState.UNKNOWN);
        DEFAULT_VALUES.put(DevState[].class, new DevState[] { DevState.UNKNOWN });
        DEFAULT_VALUES.put(DevState[][].class, new DevState[] {});

        DEFAULT_VALUES.put(byte.class, (byte) 0);
        DEFAULT_VALUES.put(Byte.class, (byte) 0);
        DEFAULT_VALUES.put(byte[].class, new byte[] { (byte) 0 });
        DEFAULT_VALUES.put(Byte[].class, new Byte[] { (byte) 0 });
        DEFAULT_VALUES.put(byte[][].class, new byte[] {});
        DEFAULT_VALUES.put(Byte[][].class, new Byte[] {});

        DEFAULT_VALUES.put(DevEncoded.class, new DevEncoded("", new byte[] {}));
        DEFAULT_VALUES.put(DevEncoded[].class, new DevEncoded[] { new DevEncoded("", new byte[] {}) });
        DEFAULT_VALUES.put(DevEncoded[][].class, new DevEncoded[] { new DevEncoded("", new byte[] {}) });
    }
    private static final Map<Class<?>, AttributeTangoType> CLASS_TYPE_MAP = new HashMap<Class<?>, AttributeTangoType>();
    static {
        CLASS_TYPE_MAP.put(Boolean.class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(boolean.class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(Boolean[].class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(boolean[].class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(Boolean[][].class, DEVBOOLEAN);
        CLASS_TYPE_MAP.put(boolean[][].class, DEVBOOLEAN);

        CLASS_TYPE_MAP.put(Short.class, DEVSHORT);
        CLASS_TYPE_MAP.put(short.class, DEVSHORT);
        CLASS_TYPE_MAP.put(Short[].class, DEVSHORT);
        CLASS_TYPE_MAP.put(short[].class, DEVSHORT);
        CLASS_TYPE_MAP.put(Short[][].class, DEVSHORT);
        CLASS_TYPE_MAP.put(short[][].class, DEVSHORT);

        CLASS_TYPE_MAP.put(Long.class, DEVLONG64);
        CLASS_TYPE_MAP.put(long.class, DEVLONG64);
        CLASS_TYPE_MAP.put(Long[].class, DEVLONG64);
        CLASS_TYPE_MAP.put(long[].class, DEVLONG64);
        CLASS_TYPE_MAP.put(Long[][].class, DEVLONG64);
        CLASS_TYPE_MAP.put(long[][].class, DEVLONG64);

        CLASS_TYPE_MAP.put(Float.class, DEVFLOAT);
        CLASS_TYPE_MAP.put(float.class, DEVFLOAT);
        CLASS_TYPE_MAP.put(Float[].class, DEVFLOAT);
        CLASS_TYPE_MAP.put(float[].class, DEVFLOAT);
        CLASS_TYPE_MAP.put(Float[][].class, DEVFLOAT);
        CLASS_TYPE_MAP.put(float[][].class, DEVFLOAT);

        CLASS_TYPE_MAP.put(Double.class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(double.class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(Double[].class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(double[].class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(Double[][].class, DEVDOUBLE);
        CLASS_TYPE_MAP.put(double[][].class, DEVDOUBLE);

        CLASS_TYPE_MAP.put(String.class, DEVSTRING);
        CLASS_TYPE_MAP.put(String[].class, DEVSTRING);
        CLASS_TYPE_MAP.put(String[][].class, DEVSTRING);

        CLASS_TYPE_MAP.put(Integer.class, DEVLONG);
        CLASS_TYPE_MAP.put(int.class, DEVLONG);
        CLASS_TYPE_MAP.put(Integer[].class, DEVLONG);
        CLASS_TYPE_MAP.put(int[].class, DEVLONG);
        CLASS_TYPE_MAP.put(Integer[][].class, DEVLONG);
        CLASS_TYPE_MAP.put(int[][].class, DEVLONG);

        CLASS_TYPE_MAP.put(DevState.class, DEVSTATE);
        CLASS_TYPE_MAP.put(DevState[].class, DEVSTATE);
        CLASS_TYPE_MAP.put(DevState[][].class, DEVSTATE);
        CLASS_TYPE_MAP.put(DeviceState.class, DEVSTATE);
        CLASS_TYPE_MAP.put(DeviceState[].class, DEVSTATE);
        CLASS_TYPE_MAP.put(DeviceState[][].class, DEVSTATE);

        CLASS_TYPE_MAP.put(Byte.class, DEVUCHAR);
        CLASS_TYPE_MAP.put(byte.class, DEVUCHAR);
        CLASS_TYPE_MAP.put(Byte[].class, DEVUCHAR);
        CLASS_TYPE_MAP.put(byte[].class, DEVUCHAR);
        CLASS_TYPE_MAP.put(Byte[][].class, DEVUCHAR);
        CLASS_TYPE_MAP.put(byte[][].class, DEVUCHAR);

        CLASS_TYPE_MAP.put(DevEncoded.class, DEVENCODED);
        CLASS_TYPE_MAP.put(DevEncoded[].class, DEVENCODED);
        CLASS_TYPE_MAP.put(DevEncoded[][].class, DEVENCODED);

    }

    AttributeTangoType(final AttributeDataType attributeDataType, final int typeCode, final Class<?> clazz) {
        this.attributeDataType = attributeDataType;
        tangoIDLType = typeCode;
        type = clazz;
    }

    public int getTangoIDLType() {
        return tangoIDLType;
    }

    public static AttributeTangoType getTypeFromClass(final Class<?> clazz) throws DevFailed {
        AttributeTangoType result = CLASS_TYPE_MAP.get(clazz);
        if (Enum.class.isAssignableFrom(clazz) && !DeviceState.class.isAssignableFrom(clazz)) {
            // specific case for enum attributes that are short in API
            result = AttributeTangoType.DEVENUM;
        } else if (result == null) {
            throw DevFailedUtils.newDevFailed("TYPE_ERROR", clazz + " is not an attribute type");
        }
        return result;
    }

    public static AttributeTangoType getTypeFromTango(final int tangoType) throws DevFailed {
        final AttributeTangoType result = TANGO_TYPE_MAP.get(tangoType);
        if (result == null) {
            throw DevFailedUtils.newDevFailed("TYPE_ERROR", tangoType + " is not an attribute type");
        }
        return result;
    }

    public static Object getDefaultValue(final Class<?> clazz) throws DevFailed {
        return DEFAULT_VALUES.get(clazz);
    }

    public Class<?> getType() {
        return type;
    }

    public AttributeDataType getAttributeDataType() {
        return attributeDataType;
    }

}
