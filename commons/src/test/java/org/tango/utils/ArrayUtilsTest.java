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

}
