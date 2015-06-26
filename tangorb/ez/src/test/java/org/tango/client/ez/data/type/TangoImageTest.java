package org.tango.client.ez.data.type;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TangoImageTest {


    @Test
    public void testExtractHelper() throws Exception {
        double[][] result = (double[][]) TangoImage.extract(new double[]{0., 0., 15., 50.0, 0., 0., 13., 0., 0., 0., 25., 0.}, 3, 4);

        assertArrayEquals(new double[]{0., 0., 15.}, result[0], 0.0);
        assertArrayEquals(new double[]{50., 0., 0.}, result[1], 0.0);
        assertArrayEquals(new double[]{13., 0., 0.}, result[2], 0.0);
        assertArrayEquals(new double[]{0., 25., 0.}, result[3], 0.0);
    }

    @Test
    public void testExtractHelper_Multi() throws Exception {
        System.setProperty(ImageTangoDataTypes.TANGO_IMAGE_EXTRACTER_USES_MULTITHREADING, "true");
        double[][] result = (double[][]) TangoImage.extract(new double[]{0., 0., 15., 50.0, 0., 0., 13., 0., 0., 25.0, 0., 0.}, 3, 4);

        assertArrayEquals(new double[]{0., 0., 15.}, result[0], 0.0);
        assertArrayEquals(new double[]{50., 0., 0.}, result[1], 0.0);
        assertArrayEquals(new double[]{13., 0., 0.}, result[2], 0.0);
        assertArrayEquals(new double[]{25., 0., 0.}, result[3], 0.0);
    }


    @Test
    public void testInsertHelper() throws Exception {
        Object result = TangoImage.insert(new double[][]{{0., 11., 0.}, {50.0, 0., 33.}, {0., 22., 0.}}, 3, 3);

        assertArrayEquals(new double[]{0., 11., 0., 50.0, 0., 33., 0., 22., 0.}, (double[]) result, 0.0);
    }

    //    @Test
    public void testExtractHelper_BigData_Multithreading() throws Exception {

        System.setProperty(ImageTangoDataTypes.TANGO_IMAGE_EXTRACTER_USES_MULTITHREADING, "true");
        int size = 9000 * 9000;
        double[] hugeArray = new double[size];
        for (int i = 0; i < size; i++) {
            hugeArray[i] = Math.random();
        }

        long start = System.nanoTime();
        TangoImage.extract(hugeArray, 9000, 9000);
        long end = System.nanoTime();

        System.out.println("Total time:" + TimeUnit.NANOSECONDS.toMillis(end - start));


        //System.setProperty(ImageTangoDataTypes.TANGO_IMAGE_EXTRACTER_USES_MULTITHREADING,"false");

        start = System.nanoTime();
        TangoImage.extract(hugeArray, 9000, 9000);
        end = System.nanoTime();

        System.out.println("Total time:" + TimeUnit.NANOSECONDS.toMillis(end - start));

        start = System.nanoTime();
        TangoImage.extract(hugeArray, 9000, 9000);
        end = System.nanoTime();

        System.out.println("Total time:" + TimeUnit.NANOSECONDS.toMillis(end - start));

        start = System.nanoTime();
        TangoImage.extract(hugeArray, 9000, 9000);
        end = System.nanoTime();

        System.out.println("Total time:" + TimeUnit.NANOSECONDS.toMillis(end - start));

        start = System.nanoTime();
        TangoImage.extract(hugeArray, 9000, 9000);
        end = System.nanoTime();

        System.out.println("Total time:" + TimeUnit.NANOSECONDS.toMillis(end - start));
        //TODO
//        assertArrayEquals(new double[][]{{0.,0.,0.}{50.0,0.,0.}{0.,0.,0.}},result);
    }


    @Test
    public void testExtractHelper_ActualData() throws Exception {
        int size = 3056 * 3056;
        long averageDuration = 0;
        for (int j = 0; j < 50; j++) {
            double[] hugeArray = new double[size];
            for (int i = 0; i < size; i++) {
                hugeArray[i] = Math.random();
            }

            long start = System.nanoTime();
            TangoImage.extract(hugeArray, 3056, 3056);
            long end = System.nanoTime();

            long duration = end - start;
            System.out.println("Total time:" + TimeUnit.NANOSECONDS.toMillis(duration));
            averageDuration += duration;
        }

        averageDuration = averageDuration / 50;
        System.out.println("Average time:" + TimeUnit.NANOSECONDS.toMillis(averageDuration));
        //TODO
//        assertArrayEquals(new double[][]{{0.,0.,0.}{50.0,0.,0.}{0.,0.,0.}},result);
    }




}