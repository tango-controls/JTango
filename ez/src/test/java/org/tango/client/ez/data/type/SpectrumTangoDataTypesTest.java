// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package org.tango.client.ez.data.type;

import fr.esrf.TangoApi.DeviceAttribute;
import org.junit.Test;
import org.tango.client.ez.data.TangoDataWrapper;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
public class SpectrumTangoDataTypesTest {
    @Test
    public void testStringArr() throws Exception {
        TangoDataType<String[]> instance = SpectrumTangoDataTypes.STRING_ARR;

        String[] values = {"Hello", "World", "!!!"};
        DeviceAttribute attribute = new DeviceAttribute("test", values, values.length, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        String[] result = instance.extract(data);

        assertArrayEquals(new String[]{"Hello", "World", "!!!"}, result);
    }

    @Test
    public void testDoubleArr() throws Exception {
        TangoDataType<double[]> instance = SpectrumTangoDataTypes.DOUBLE_ARR;

        double[] values = {1., 2., 3.};
        DeviceAttribute attribute = new DeviceAttribute("test", values, values.length, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        double[] result = instance.extract(data);

        assertArrayEquals(new double[]{1., 2., 3.}, result, 0.00001);
    }


    @Test
    public void testShortArr() throws Exception {
        TangoDataType<short[]> instance = SpectrumTangoDataTypes.SHORT_ARR;

        short[] values = {1, 2, 3, 4};
        DeviceAttribute attribute = new DeviceAttribute("test", values, values.length, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        short[] result = instance.extract(data);

        assertArrayEquals(new short[]{1, 2, 3, 4}, result);
    }

    @Test
    public void testCharArr() throws Exception {
        TangoDataType<char[]> instance = SpectrumTangoDataTypes.CHAR_ARR;

        char[] values = {'a', 'b', 'c', 'd'};
        DeviceAttribute attribute = new DeviceAttribute("test", new String(values).getBytes("UTF-8"), values.length, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        char[] result = instance.extract(data);

        assertArrayEquals(new char[]{'a', 'b', 'c', 'd'}, result);
    }

    @Test
    public void testBooleanArr() throws Exception {
        TangoDataType<boolean[]> instance = SpectrumTangoDataTypes.BOOL_ARR;

        boolean[] values = {true, false, false, true, true};
        DeviceAttribute attribute = new DeviceAttribute("test", values, values.length, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        boolean[] result = instance.extract(data);

        assertTrue(result[0]);
        assertFalse(result[1]);
        assertFalse(result[2]);
        assertTrue(result[3]);
        assertTrue(result[4]);
    }
}
