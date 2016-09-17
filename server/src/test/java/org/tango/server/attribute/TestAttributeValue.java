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
package org.tango.server.attribute;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.tango.DeviceState;
import org.tango.utils.ArrayUtils;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

public class TestAttributeValue {

    @Test
    public void testObject() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        final Double[] array = new Double[] { 1.0, 2.0 };
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array[0] = 10.0;
        final Double[] newArray = (Double[]) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test
    public void testObject2D() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        final double[][] array = new double[][] { { 1.0, 2.0 }, { 1.0, 2.0 } };
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array[0][0] = 10.0;
        final double[][] newArray = (double[][]) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test
    public void testPrimitive() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        final int[] array = new int[] { 1, 2 };
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array[0] = 10;
        final int[] newArray = (int[]) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test
    public void testDevState() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        final DevState[] array = new DevState[] { DevState.ON, DevState.OFF };
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array[0] = DevState.FAULT;
        final DevState[] newArray = (DevState[]) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test
    public void testDevStateScalar() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        DevState array = DevState.ON;
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array = DevState.FAULT;
        final DevState newArray = (DevState) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test
    public void testDeviceState() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        final DeviceState[] array = new DeviceState[] { DeviceState.ON, DeviceState.OFF };
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array[0] = DeviceState.FAULT;
        final DeviceState[] newArray = (DeviceState[]) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test
    public void testDevEncoded() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        final DevEncoded[] array = new DevEncoded[] { new DevEncoded("1", new byte[] { 1, 2 }),
                new DevEncoded("2", new byte[] { 3, 4 }) };
        value.setValue(array);
        final AttributeValue newValue = (AttributeValue) value.clone();
        array[0] = new DevEncoded("3", new byte[] { 1, 2 });
        final DevEncoded[] newArray = (DevEncoded[]) newValue.getValue();
        assertThat(newArray, not(array));
    }

    @Test(expected = DevFailed.class)
    public void testNotAllowedType() throws DevFailed {
        final AttributeValue value = new AttributeValue();
        value.setValue(new ArrayList<String>());
    }

    @Test
    public void testClone() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        value.setValue(10);
        value.setTime(1404);
        final AttributeValue copy = (AttributeValue) value.clone();
        assertThat(copy.getValue(), equalTo(copy.getValue()));
        assertThat(copy.getTime(), equalTo(copy.getTime()));
        assertThat(copy.getQuality(), equalTo(copy.getQuality()));
        assertThat(copy.getXDim(), equalTo(copy.getXDim()));
        assertThat(copy.getYDim(), equalTo(copy.getYDim()));

    }

    @Test
    public void testCloneSpectrum() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        value.setValue(new double[] { 10, 30 });
        value.setTime(1404);
        final AttributeValue copy = (AttributeValue) value.clone();
        assertThat(copy.getValue(), equalTo(copy.getValue()));
        assertThat(copy.getTime(), equalTo(copy.getTime()));
        assertThat(copy.getQuality(), equalTo(copy.getQuality()));
        assertThat(copy.getXDim(), equalTo(copy.getXDim()));
        assertThat(copy.getYDim(), equalTo(copy.getYDim()));
    }

    @Test
    public void testCloneImage() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        value.setValue(new double[][] { { 10, 30 }, { 40, 50 } });
        value.setTime(System.currentTimeMillis());
        final AttributeValue copy = (AttributeValue) value.clone();
        assertThat(copy.getValue(), equalTo(copy.getValue()));
        assertThat(copy.getTime(), equalTo(copy.getTime()));
        assertThat(copy.getQuality(), equalTo(copy.getQuality()));
        assertThat(copy.getXDim(), equalTo(copy.getXDim()));
        assertThat(copy.getYDim(), equalTo(copy.getYDim()));
    }

    @Test
    public void testCloneConvertImage() throws DevFailed, CloneNotSupportedException {
        final AttributeValue value = new AttributeValue();
        value.setValue(new double[][] { { 10, 30 }, { 40, 50 } });
        value.setTime(System.currentTimeMillis());
        final AttributeValue copy = (AttributeValue) value.clone();
        copy.setValueWithoutDim(ArrayUtils.from2DArrayToArray(value.getValue()));
        assertThat((double[]) copy.getValue(), equalTo(new double[] { 10.0, 30.0, 40.0, 50.0 }));
        assertThat(copy.getTime(), equalTo(copy.getTime()));
        assertThat(copy.getQuality(), equalTo(copy.getQuality()));
        assertThat(copy.getXDim(), equalTo(copy.getXDim()));
        assertThat(copy.getYDim(), equalTo(copy.getYDim()));
    }
}
