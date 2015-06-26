package org.tango.client.ez.data.type;

import com.google.common.base.Preconditions;
import org.tango.client.ez.util.TangoImageUtils;

import java.awt.image.RenderedImage;
import java.lang.reflect.Array;

/**
 *
 * T one of the primitive array(int,float)
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 27.02.2015
 */
public class TangoImage<T> {
    private final T data;
    private final int width;
    private final int height;

    public TangoImage(T data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public RenderedImage toRenderedImage_sRGB(){
        return TangoImageUtils.toRenderedImage_sRGB((int[])data,width,height);
    }

    public RenderedImage toRenderedImage_ARGB(){
        return TangoImageUtils.toRenderedImage_ARGB((int[]) data, width, height);
    }

    public RenderedImage toRenderedImage_GRAY(){
        return TangoImageUtils.toRenderedImageDedicatedComponents_GRAY(data, width, height);
    }

    public T[] to2DArray(){
        return (T[]) extract(data, width, height);//resulting array is of type T see first code line of extract
    }

    /**
     * Creates this instance from 2D array, i.e. int[32][32] -> TangoImage(data:32*32,w:32,h:32)
     *
     * @param src 2D array of T
     * @return a new TangoImage instance
     * @throws java.lang.IllegalArgumentException if src is not a 2D array; if second level arrays do not have same length
     */
    public static TangoImage<byte[]> from2DArray(byte[][] src){
        return TangoImage.<byte[]>from2DArray((Object)src);
    }

    /**
     * Creates this instance from 2D array, i.e. int[32][32] -> TangoImage(data:32*32,w:32,h:32)
     *
     * @param src 2D array of T
     * @return a new TangoImage instance
     * @throws java.lang.IllegalArgumentException if src is not a 2D array; if second level arrays do not have same length
     */
    public static TangoImage<short[]> from2DArray(short[][] src){
        return TangoImage.<short[]>from2DArray((Object)src);
    }

    /**
     * Creates this instance from 2D array, i.e. int[32][32] -> TangoImage(data:32*32,w:32,h:32)
     *
     * @param src 2D array of T
     * @return a new TangoImage instance
     * @throws java.lang.IllegalArgumentException if src is not a 2D array; if second level arrays do not have same length
     */
    public static TangoImage<int[]> from2DArray(int[][] src){
        return TangoImage.<int[]>from2DArray((Object)src);
    }

    /**
     * Creates this instance from 2D array, i.e. int[32][32] -> TangoImage(data:32*32,w:32,h:32)
     *
     * @param src 2D array of T
     * @return a new TangoImage instance
     * @throws java.lang.IllegalArgumentException if src is not a 2D array; if second level arrays do not have same length
     */
    public static TangoImage<float[]> from2DArray(float[][] src){
        return TangoImage.<float[]>from2DArray((Object)src);
    }

    /**
     * Creates this instance from 2D array, i.e. int[32][32] -> TangoImage(data:32*32,w:32,h:32)
     *
     * @param src 2D array of T
     * @return a new TangoImage instance
     * @throws java.lang.IllegalArgumentException if src is not a 2D array; if second level arrays do not have same length
     */
    public static TangoImage<double[]> from2DArray(double[][] src){
        return TangoImage.<double[]>from2DArray((Object)src);
    }

    /**
     * Creates this instance from 2D array, i.e. int[32][32] -> TangoImage(data:32*32,w:32,h:32)
     *
     * @param src 2D array of T
     * @param <T> primitive array
     * @return a new TangoImage instance
     * @throws java.lang.IllegalArgumentException if src is not a 2D array; if second level arrays do not have same length
     */
    static <T> TangoImage<T> from2DArray(Object src){
        Preconditions.checkArgument(src.getClass().isArray(), "Array type is expected here!");
        int dimX = Array.getLength(Array.get(src, 0));
        int dimY = Array.getLength(src);

        for (int i = 0; i < dimY; i++) {
            Preconditions.checkArgument(Array.getLength(Array.get(src, i)) == dimX, "All sub arrays must be of the same length");
        }

        return new TangoImage<T>((T) insert(src, dimX, dimY),dimX,dimY);
    }

    /**
     * Starts several threads to speedup extraction process if dimY/CPUS > THRESHOLD
     * AND System.getProperty(TANGO_IMAGE_EXTRACTER_USE_MULTITHREADING) is set to true.
     *
     * @param value 1-dimensional array
     * @param dimX  x
     * @param dimY  y
     * @return 2-dimensional array:x,y
     */
    static Object extract(final Object value, final int dimX, final int dimY) {
        final Object result = Array.newInstance(value.getClass().getComponentType(), dimY, dimX);

        for (int i = 0, k = 0; i < dimY; i++, k += dimX)
            System.arraycopy(value, k, Array.get(result, i), 0, dimX);

        return result;
    }

    /**
     * @param value 2-dimensional array:x,y
     * @param dimX  x
     * @param dimY  y
     * @param <V>   1-dimensional array
     * @return 1-dimensional array
     */
    static <V> V insert(Object value, int dimX, int dimY) {
        Object result = Array.newInstance(value.getClass().getComponentType().getComponentType(), dimX * dimY);
        for (int i = 0, k = 0; i < dimY; i++, k += dimX)
            System.arraycopy(Array.get(value, i), 0, result, k, dimX);
        return (V) result;
    }

    public T getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
