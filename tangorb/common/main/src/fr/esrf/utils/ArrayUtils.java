package fr.esrf.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Copy'n'Paste from JTangoCommons
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 23.07.2015
 */
public class ArrayUtils {
    public final static Map<Class<?>, Class<?>> OBJ_TO_PRIMITIVE = new HashMap<Class<?>, Class<?>>();

    static {
        OBJ_TO_PRIMITIVE.put(Boolean.class, boolean.class);
        OBJ_TO_PRIMITIVE.put(Byte.class, byte.class);
        OBJ_TO_PRIMITIVE.put(Short.class, short.class);
        OBJ_TO_PRIMITIVE.put(Character.class, char.class);
        OBJ_TO_PRIMITIVE.put(Integer.class, int.class);
        OBJ_TO_PRIMITIVE.put(Long.class, long.class);
        OBJ_TO_PRIMITIVE.put(Float.class, float.class);
        OBJ_TO_PRIMITIVE.put(Double.class, double.class);
    }

    private ArrayUtils() {
    }

    public static Object toPrimitiveArray(Object array, Class<?> source) {
        if (!array.getClass().isArray()) {
            return array;
        }
        final Class<?> clazz = OBJ_TO_PRIMITIVE.get(source);
        return setArray(clazz, array);
    }

    private final static Object setArray(final Class<?> clazz, final Object array) {
        Object result = array;
        if (clazz != null) {
            final int length = Array.getLength(array);
            result = Array.newInstance(clazz, length);
            for (int i = 0; i < length; i++) {
                Array.set(result, i, Array.get(array, i));
            }
        }
        return result;

    }
}
