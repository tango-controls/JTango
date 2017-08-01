package org.tango.utils;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import fr.esrf.Tango.DevFailed;

public class ArrayUtilsTest {

    @Test
    public void test1Dto2D() throws DevFailed {
        final float[] insert = new float[] { 10.0F, 15.2F, 10.0F, 15.2F };
        final float[][] result = (float[][]) ArrayUtils.fromArrayTo2DArray(insert, 2, 2);
        final float[][] expected = new float[][] { { 10.0F, 15.2F }, { 10.0F, 15.2F } };
        assertThat(result, equalTo(expected));
    }

    @Test
    public void test2Dto1D() throws DevFailed {
        final float[][] insert = new float[][] { { 10.0F, 15.2F }, { 10.0F, 15.2F } };
        final float[] result = (float[]) ArrayUtils.from2DArrayToArray(insert);
        final float[] expected = new float[] { 10.0F, 15.2F, 10.0F, 15.2F };
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testDeepCopy() throws DevFailed {
        final float[][] insert = new float[][] { { 10.0F, 15.2F }, { 10.0F, 15.2F } };
        final float[][] result = (float[][]) ArrayUtils.deepCopyOf(insert);
        insert[0][0] = 5.3F;
        final float[][] expected = new float[][] { { 10.0F, 15.2F }, { 10.0F, 15.2F } };
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testDeepCopyArray() throws DevFailed {
        final float[] insert = new float[] { 10.0F, 15.2F };
        final float[] result = (float[]) ArrayUtils.deepCopyOf(insert);
        insert[0] = 5.3F;
        final float[] expected = new float[] { 10.0F, 15.2F };
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testDeepCopyStringNullArray() throws DevFailed {
        final String[] insert = new String[] { null };
        final String[] result = (String[]) ArrayUtils.deepCopyOf(insert);
        insert[0] = "5.3F";
        final String[] expected = new String[] { null };
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testDeepCopyStringNull2Array() throws DevFailed {
        final String[] insert = new String[] { null, "a" };
        final String[] result = (String[]) ArrayUtils.deepCopyOf(insert);
        insert[0] = "5.3F";
        final String[] expected = new String[] { null, "a" };
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testDeepCopyEmpty() throws DevFailed {
        float[] insert = new float[] {};
        final float[] result = (float[]) ArrayUtils.deepCopyOf(insert);
        insert = new float[] { 5.3F };
        final float[] expected = new float[] {};
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testDeepCopyNull() throws DevFailed {
        float[] insert = null;
        final float[] result = (float[]) ArrayUtils.deepCopyOf(insert);
        insert = new float[] { 5.3F };
        final float[] expected = null;
        assertThat(result, equalTo(expected));
    }

}
