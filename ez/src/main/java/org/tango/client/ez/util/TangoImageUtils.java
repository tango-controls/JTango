package org.tango.client.ez.util;

import com.google.common.base.Preconditions;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.lang.reflect.Array;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 28.02.2015
 */
public class TangoImageUtils {
    //                                                    R           G          B         A
    private static final int[] sRGB = new int[]{0x00ff0000, 0x0000ff00, 0x000000ff};
    private static final int[] ARGB = new int[]{0x00ff0000, 0x0000ff00, 0x000000ff, 0xff000000};
    public static final int GRAY_PIXEL_OFFSET = 1;
    public static final int sRGB_PIXEL_OFFSET = 3;

    private TangoImageUtils(){}


    /**
     * Wraps this data array with {@link java.awt.image.BufferedImage}
     *
     * This method expects that underlying data represents 24 bits sRGB color data, i.e. an array of ints,
     * where each int packs 24 bits of data
     *
     * @return a RenderedImage that wraps this data array
     * @throws RuntimeException if this data array can not be wrapped
     * @throws IllegalArgumentException if this data array can not be wrapped
     * @param data
     * @param width
     * @param height
     */
    public static RenderedImage toRenderedImage_sRGB(int[] data, int width, int height){
        DataBuffer db = new DataBufferInt(data, width * height);

        WritableRaster raster = Raster.createPackedRaster(db, width, height, width, sRGB, null);

        DirectColorModel cm = new DirectColorModel(24, sRGB[0], sRGB[1], sRGB[2]);
        return toRenderedImage(cm, raster);
    }

    /**
     * Wraps this data array with {@link java.awt.image.BufferedImage}
     *
     * This method expects that underlying data represents 32 bits ARGB color data, i.e. an array of ints,
     * where each int packs 32 bits of data
     *
     * @return a RenderedImage that wraps this data array
     * @throws RuntimeException if this data array can not be wrapped
     * @throws IllegalArgumentException if this data array can not be wrapped
     * @param data
     * @param width
     * @param height
     */
    public static RenderedImage toRenderedImage_ARGB(int[] data, int width, int height){
        DataBuffer db = new DataBufferInt(data, width * height);

        WritableRaster raster = Raster.createPackedRaster(db, width, height, width, ARGB, null);

        DirectColorModel cm = new DirectColorModel(32, sRGB[0], sRGB[1], sRGB[2], ARGB[3]);
        return toRenderedImage(cm, raster);
    }

    /**
     * Wraps this data array with {@link java.awt.image.BufferedImage}
     *
     * This method expects that underlying data represents 24 bits sRGB color data, i.e. an array
     * where each element holds single component of the pixel (3 elements per pixel)
     *
     * @return a RenderedImage that wraps this data array
     * @throws RuntimeException if this data array can not be wrapped
     * @throws IllegalArgumentException if this data array can not be wrapped
     * @param data continuous data array
     * @param width px
     * @param height px
     */
    public static RenderedImage toRenderedImageDedicatedComponents_sRGB(Object data, int width, int height){
        Preconditions.checkArgument(data.getClass().isArray(), "An array is expected here!");
        Preconditions.checkArgument(Array.getLength(data) == width * sRGB_PIXEL_OFFSET * height, "data.length is not equal to image dimensions");
        DataBuffer db;
        Class<?> componentType = data.getClass().getComponentType();
        if(componentType == double.class)
            db = new DataBufferDouble((double[])data, width * sRGB_PIXEL_OFFSET * height);
        else if(componentType == float.class)
            db = new DataBufferFloat((float[])data, width * sRGB_PIXEL_OFFSET * height);
        else if(componentType == int.class)
            db = new DataBufferInt((int[])data, width * sRGB_PIXEL_OFFSET * height);
        else if(componentType == short.class)
            db = new DataBufferShort((short[])data, width * sRGB_PIXEL_OFFSET * height);
        else if(componentType == byte.class)
            db = new DataBufferByte((byte[])data, width * sRGB_PIXEL_OFFSET * height);
        else
            throw new IllegalArgumentException("Unsupported data type: " + componentType.getSimpleName());

        SampleModel sm =  new ComponentSampleModel(db.getDataType(),width,height, sRGB_PIXEL_OFFSET,width * sRGB_PIXEL_OFFSET,new int[]{0,1,2});

        WritableRaster raster = Raster.createWritableRaster(sm, db, null);

        ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), false, false, Transparency.OPAQUE, db.getDataType());
        return toRenderedImage(cm, raster);
    }

    /**
     * Wraps this data array with {@link java.awt.image.BufferedImage}
     *
     * This method expects that underlying data represents 24 bits sRGB color data, i.e. an array
     * where each element holds single pixel (gray scaled)
     *
     * @return a RenderedImage that wraps this data array
     * @throws RuntimeException if this data array can not be wrapped
     * @throws IllegalArgumentException if this data array can not be wrapped
     * @param data continuous data array
     * @param width px
     * @param height px
     */
    public static RenderedImage toRenderedImageDedicatedComponents_GRAY(Object data, int width, int height){
        Preconditions.checkArgument(data.getClass().isArray(), "An array is expected here!");
        Preconditions.checkArgument(Array.getLength(data) == width * GRAY_PIXEL_OFFSET * height, "data.length is not equal to image dimensions");
        DataBuffer db;
        Class<?> componentType = data.getClass().getComponentType();
        if(componentType == double.class)
            db = new DataBufferDouble((double[])data, width * GRAY_PIXEL_OFFSET * height);
        else if(componentType == float.class)
            db = new DataBufferFloat((float[])data, width * GRAY_PIXEL_OFFSET * height);
        else if(componentType == int.class)
            db = new DataBufferInt((int[])data, width * GRAY_PIXEL_OFFSET * height);
        else if(componentType == short.class)
            db = new DataBufferShort((short[])data, width * GRAY_PIXEL_OFFSET * height);
        else if(componentType == byte.class)
            db = new DataBufferByte((byte[])data, width * GRAY_PIXEL_OFFSET * height);
        else
            throw new IllegalArgumentException("Unsupported data type: " + componentType.getSimpleName());

        SampleModel sm =  new PixelInterleavedSampleModel(db.getDataType(),width,height, GRAY_PIXEL_OFFSET,width * GRAY_PIXEL_OFFSET,new int[]{0});

        WritableRaster raster = Raster.createWritableRaster(sm, db, null);

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);

        ColorModel cm = new ComponentColorModel(cs, false, true, Transparency.OPAQUE, db.getDataType());

        return toRenderedImage(cm, raster);
    }

    private static RenderedImage toRenderedImage(ColorModel cm, WritableRaster wr){
        return new BufferedImage(cm, wr, false, null);
    }
}
