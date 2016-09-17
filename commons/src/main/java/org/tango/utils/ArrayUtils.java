package org.tango.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import fr.esrf.Tango.DevFailed;

public final class ArrayUtils {
    private ArrayUtils() {

    }

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

    public final static Map<Class<?>, Class<?>> PRIMITIVE_TO_OBJ = new HashMap<Class<?>, Class<?>>();
    static {
        PRIMITIVE_TO_OBJ.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_OBJ.put(byte.class, Byte.class);
        PRIMITIVE_TO_OBJ.put(short.class, Short.class);
        PRIMITIVE_TO_OBJ.put(char.class, Character.class);
        PRIMITIVE_TO_OBJ.put(int.class, Integer.class);
        PRIMITIVE_TO_OBJ.put(long.class, Long.class);
        PRIMITIVE_TO_OBJ.put(float.class, Float.class);
        PRIMITIVE_TO_OBJ.put(double.class, Double.class);
    }

    /**
     * Convert an array of Objects to primitives if possible. Return input otherwise
     *
     * @param array
     *            the array to convert
     * @return The array of primitives
     */
    public final static Object toPrimitiveArray(final Object array) {
        if (!array.getClass().isArray()) {
            return array;
        }
        final Class<?> clazz = OBJ_TO_PRIMITIVE.get(array.getClass().getComponentType());
        return setArray(clazz, array);
    }

    /**
     * Convert an array of primitives to Objects if possible. Return input otherwise
     *
     * @param array
     *            the array to convert
     * @return The array of Objects
     */
    public final static Object toObjectArray(final Object array) {
        if (!array.getClass().isArray()) {
            return array;
        }
        final Class<?> clazz = PRIMITIVE_TO_OBJ.get(array.getClass().getComponentType());
        return setArray(clazz, array);
    }

    /**
     * Put values in new array
     *
     * @param clazz
     *            type of the new array
     * @param array
     *            origin array
     * @return
     */
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

    /**
     * Convert an array of any type to an array of strings
     *
     * @param array
     * @return
     */
    public static String[] toStringArray(final Object array) {
        final int length = Array.getLength(array);
        final String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = Array.get(array, i).toString();
        }
        return result;
    }

    /**
     * Add 2 arrays
     *
     * @param array1
     * @param array2
     * @return
     */
    public static Object addAll(final Object array1, final Object array2) {
        Object joinedArray;
        if (array1 == null) {
            if (array2.getClass().isArray()) {
                joinedArray = array2;
            } else {
                joinedArray = Array.newInstance(array2.getClass(), 1);
                Array.set(joinedArray, 0, array2);
            }
        } else if (array2 == null) {
            if (array1.getClass().isArray()) {
                joinedArray = array1;
            } else {
                joinedArray = Array.newInstance(array1.getClass(), 1);
                Array.set(joinedArray, 0, array1);
            }
        } else {
            int length1 = 1;
            if (array1.getClass().isArray()) {
                length1 = Array.getLength(array1);
            }
            int length2 = 1;
            if (array2.getClass().isArray()) {
                length2 = Array.getLength(array2);
            }
            if (array1.getClass().isArray()) {
                joinedArray = Array.newInstance(array1.getClass().getComponentType(), length1 + length2);
            } else {
                joinedArray = Array.newInstance(array1.getClass(), length1 + length2);
            }
            if (array1.getClass().isArray()) {
                System.arraycopy(array1, 0, joinedArray, 0, length1);
            } else {
                Array.set(joinedArray, 0, array1);
            }
            if (array2.getClass().isArray()) {
                System.arraycopy(array2, 0, joinedArray, length1, length2);
            } else {
                Array.set(joinedArray, length1, array2);
            }
        }
        return joinedArray;
    }

    /**
     * Convert a 2D array to a 1D array
     *
     * @param array2D
     * @return
     */
    public static Object from2DArrayToArray(final Object array2D) {
        // final Profiler profilerPeriod = new Profiler("from2DArrayToArray");
        // profilerPeriod.start("from2DArrayToArray");
        Object array = null;
        if (array2D.getClass().isArray()) {
            final int lengthY = Array.getLength(array2D);
            if (Array.getLength(array2D) > 0) {
                final Object firstLine = Array.get(array2D, 0);
                int lengthLineX;
                if (firstLine.getClass().isArray()) {
                    lengthLineX = Array.getLength(firstLine);
                    final Class<?> compType = firstLine.getClass().getComponentType();
                    array = Array.newInstance(compType, lengthY * lengthLineX);
                    if (lengthLineX > 0 && lengthY > 0) {
                        for (int y = 0; y < lengthY; y++) {
                            final Object line = Array.get(array2D, y);
                            System.arraycopy(line, 0, array, lengthLineX * y, lengthLineX);
                        }
                    }
                } else { // is not a 2D array
                    array = Array.newInstance(array2D.getClass().getComponentType(), Array.getLength(array2D));
                    System.arraycopy(array2D, 0, array, 0, Array.getLength(array2D));
                }
            } else { // empty array
                if (array2D.getClass().getComponentType().isArray()) {
                    array = Array.newInstance(array2D.getClass().getComponentType().getComponentType(),
                            Array.getLength(array2D));
                } else {
                    array = Array.newInstance(array2D.getClass().getComponentType(), Array.getLength(array2D));
                }
            }
        } else { // is not an array
            array = array2D;
        }
        // profilerPeriod.stop().print();
        return array;
    }

    /**
     * Get the x dimension where x is array[y][x]
     *
     * @param array2D
     * @return
     */
    public static int get2DArrayXDim(final Object array2D) {
        int dimX = 0;
        if (Array.getLength(array2D) > 0) {
            dimX = Array.getLength(Array.get(array2D, 0));
        }
        return dimX;
    }

    /**
     * Get the y dimension where y is array[y][x]
     *
     * @param array2D
     * @return
     */
    public static int get2DArrayYDim(final Object array2D) {
        return Array.getLength(array2D);
    }

    /**
     * Check of size corresponds to given dimensions
     *
     * @param object
     * @param dimX
     * @param dimY
     * @return
     */
    public static boolean checkDimensions(final Object object, final int dimX, final int dimY) {
        boolean hasGoodDimensions = false;
        if (object != null) {
            if (object.getClass().isArray()) {
                if (Array.getLength(object) == 0 && dimX == 0) {// is a 0D Array
                    hasGoodDimensions = true;
                } else if (object.getClass().getComponentType().isArray()) {// is a 2D Array
                    final Object line = Array.get(object, 0);
                    if (dimX * dimY == Array.getLength(object) * Array.getLength(line)) {
                        hasGoodDimensions = true;
                    }
                } else {// 1 D array
                    final int length = Array.getLength(object);
                    if (dimX == length && dimY == 0) {
                        hasGoodDimensions = true;
                    } else if (dimX * dimY == length) {
                        hasGoodDimensions = true;
                    }
                }
            } else {// not an array
                if (dimX == 1 && dimY == 0) {
                    hasGoodDimensions = true;
                }
            }
        }

        return hasGoodDimensions;
    }

    /**
     * Convert an array to a 2D array
     *
     * @param array
     * @param dimX
     * @param dimY
     * @return
     * @throws DevFailed
     */

    public static Object fromArrayTo2DArray(final Object array, final int dimX, final int dimY) throws DevFailed {
        Object array2D = null;
        // final Profiler profilerPeriod = new Profiler("fromArrayTo2DArray");
        // profilerPeriod.start("fromArrayTo2DArray");
        if (array.getClass().isArray()) {
            if (dimY > 0) {// to a 2D Array
                array2D = Array.newInstance(array.getClass().getComponentType(), dimY, dimX);
                for (int y = 0; y < dimY; y++) {
                    final Object line = Array.get(array2D, y);
                    System.arraycopy(array, y * dimX, line, 0, Array.getLength(line));
                }

            } else {
                array2D = Array.newInstance(array.getClass().getComponentType(), Array.getLength(array));
                System.arraycopy(array, 0, array2D, 0, Array.getLength(array));
            }
        } else {
            array2D = array;
        }
        // profilerPeriod.stop().print();
        return array2D;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static int[][] copyOf(final int[][] nums) {
        final int[][] copy = new int[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final int[] member = new int[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static double[][] copyOf(final double[][] nums) {
        final double[][] copy = new double[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final double[] member = new double[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static float[][] copyOf(final float[][] nums) {
        final float[][] copy = new float[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final float[] member = new float[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static long[][] copyOf(final long[][] nums) {
        final long[][] copy = new long[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final long[] member = new long[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static short[][] copyOf(final short[][] nums) {
        final short[][] copy = new short[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final short[] member = new short[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static byte[][] copyOf(final byte[][] nums) {
        final byte[][] copy = new byte[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final byte[] member = new byte[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static char[][] copyOf(final char[][] nums) {
        final char[][] copy = new char[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final char[] member = new char[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static boolean[][] copyOf(final boolean[][] nums) {
        final boolean[][] copy = new boolean[nums.length][];

        for (int i = 0; i < copy.length; i++) {
            final boolean[] member = new boolean[nums[i].length];
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }

        return copy;
    }

    /**
     * Make a copy for a 2D array
     *
     * @param nums
     * @return
     */
    public static <T> T[][] copyOf(final T[][] nums) {
        final int lengthY = Array.getLength(nums);
        int lengthX = 0;
        if (Array.getLength(nums) > 0) {
            lengthX = Array.getLength(nums[0]);
        }
        @SuppressWarnings("unchecked")
        final T[][] copy = (T[][]) Array.newInstance(nums.getClass().getComponentType().getComponentType(), lengthY,
                lengthX);
        for (int i = 0; i < copy.length; i++) {
            @SuppressWarnings("unchecked")
            final T[] member = (T[]) Array.newInstance(nums[i].getClass().getComponentType(), nums[i].length);
            System.arraycopy(nums[i], 0, member, 0, nums[i].length);
            copy[i] = member;
        }
        return copy;
    }

    public static Object deepCopyOf(final Object array) {
        Object result = array;
        int lengthX = 0;
        if (array != null && array.getClass().isArray()) {
            final int lengthY = Array.getLength(array);
            if (lengthY > 0) {
                final Object firtLine = Array.get(array, 0);
                if (firtLine != null && firtLine.getClass().isArray()) {
                    lengthX = Array.getLength(firtLine);
                    result = Array.newInstance(firtLine.getClass().getComponentType(), lengthY, lengthX);
                } else {
                    result = Array.newInstance(array.getClass().getComponentType(), lengthY);
                }
            }
            deepCopy(array, result);
        }
        return result;
    }

    private static void deepCopy(final Object source, final Object dest) {
        if (source != null) {
            final int length = Array.getLength(source);
            if (length > 0) {
                final Object firstline = Array.get(source, 0);
                if (firstline != null && firstline.getClass().isArray()) {
                    for (int i = 0; i < length; i++) {
                        deepCopy(Array.get(source, i), Array.get(dest, i));
                    }
                } else {
                    System.arraycopy(source, 0, dest, 0, length);
                }
            }
        }
    }

}
