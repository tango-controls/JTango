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

package org.tango.client.ez.data;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.TangoConst;
import org.junit.Assert;
import org.junit.Test;
import org.tango.client.ez.data.format.TangoDataFormat;
import org.tango.client.ez.data.type.TangoDataType;
import org.tango.client.ez.data.type.TangoDataTypes;

import java.lang.reflect.Array;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 05.06.12
 */
public class TangoDataFormatTest {
    @Test
    public void testExtract_String() throws Exception {
        TangoDataFormat<String> instance = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SCALAR);

        DeviceAttribute attribute = new DeviceAttribute("test", "some value");
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        String result = instance.extract(data);

        assertEquals("some value", result);
    }

    @Test
    public void testExtract_DoubleArr() throws Exception {
        TangoDataFormat<double[]> instance = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SPECTRUM);

        DeviceAttribute attribute = new DeviceAttribute("test", new double[]{0.1D, 0.9D, 0.8D, 0.4D}, 4, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        double[] result = instance.extract(data);

        assertArrayEquals(new double[]{0.1D, 0.9D, 0.8D, 0.4D}, result, 0.0);
    }

    @Test
    public void testExtract_BoolArr() throws Exception {
        TangoDataFormat<boolean[]> instance = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SPECTRUM);

        DeviceAttribute attribute = new DeviceAttribute("test", new boolean[]{true, false, false, true}, 4, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        boolean[] result = instance.extract(data);

        assertTrue(result[0]);
        assertFalse(result[1]);
        assertFalse(result[2]);
        assertTrue(result[3]);
    }

    @Test
    public void testGetDataTypeForBoolArr() throws Exception {
        TangoDataFormat<?> format = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SPECTRUM);
        TangoDataType<?> type = TangoDataTypes.forTangoDevDataType(TangoConst.Tango_DEV_BOOLEAN);
        assertSame(boolean[].class, format.getDataType(type.getAlias()).getDataType());
    }

    @Test
    public void testExtract_Unknown() throws Exception {
        TangoDataFormat<Object> instance = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.FMT_UNKNOWN);

        DeviceAttribute attribute = new DeviceAttribute("test", 1234);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);

        Object result = instance.extract(data);
        assertEquals(1234, Array.getInt(result, 0));
    }

    @Test
    public void testExtract_Unknown_Arr() throws Exception {
        TangoDataFormat<Object> instance = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.FMT_UNKNOWN);

        DeviceAttribute attribute = new DeviceAttribute("test", new int[]{1, 2, 3, 4}, 4, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);

        Object result = instance.extract(data);
        assertEquals(1, Array.getInt(result, 0));
        //TODO Tango does not set dimensions correctly: nbRead == 1.
//        assertEquals(2, Array.getInt(result, 1));
//        assertEquals(3, Array.getInt(result, 2));
//        assertEquals(4, Array.getInt(result, 3));
    }

    @Test
    public void testToString() {
        TangoDataFormat<?> instance = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SPECTRUM);

        Assert.assertEquals("Spectrum", instance.toString());
    }
}
