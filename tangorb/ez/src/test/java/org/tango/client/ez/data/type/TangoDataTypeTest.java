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

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.TangoConst;
import org.junit.Test;
import org.tango.client.ez.data.TangoDataWrapper;
import org.tango.client.ez.data.format.TangoDataFormat;

import java.util.Arrays;

import static junit.framework.Assert.*;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 04.06.12
 */
public class TangoDataTypeTest {
    @Test
    public void testConvert_Void() throws Exception {
        TangoDataType<Void> type = TangoDataTypes.forTangoDevDataType(TangoConst.Tango_DEV_VOID);

        DeviceAttribute attribute = new DeviceAttribute("test", "some value");
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        Void result = type.extract(data);

        assertNull(result);
    }

    @Test
    public void testConvert() throws Exception {
        TangoDataType<String> type = TangoDataTypes.forTangoDevDataType(TangoConst.Tango_DEV_STRING);

        DeviceAttribute attribute = new DeviceAttribute("test", "some value");
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        String result = type.extract(data);

        assertEquals("some value", result);
    }

    @Test
    public void testConvert_Array() throws Exception {
        TangoDataType<String[]> type = TangoDataTypes.forTangoDevDataType(TangoConst.Tango_DEVVAR_STRINGARRAY);

        DeviceAttribute attribute = new DeviceAttribute("test", new String[]{"some value"}, 0, 0);
        TangoDataWrapper data = TangoDataWrapper.create(attribute);
        String[] result = type.extract(data);

        assertEquals("[some value]", Arrays.toString(result));
    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        TangoDataType<?> type = TangoDataTypes.forClass(String.class);

        assertEquals("DevString", type.toString());
    }

    @Test
    public void testGetEncoded() throws Exception {
        TangoDataFormat<byte[]> format = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SCALAR);
        TangoDataType<byte[]> type = format.getDataType(TangoConst.Tango_DEV_ENCODED);

        assertSame(ScalarTangoDataTypes.ENCODED, type);
    }

    @Test(expected = UnknownTangoDataType.class)
    public void testGetEncoded_Image() throws Exception {
        TangoDataFormat<byte[]> format = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.IMAGE);
        TangoDataType<byte[]> type = format.getDataType(TangoConst.Tango_DEV_ENCODED);
    }


    @Test(expected = NullPointerException.class)//there is no spectrum data type for DevEncoded
    public void testGetEncoded_Spectrum() throws Exception {
        TangoDataFormat<byte[]> format = TangoDataFormat.createForAttrDataFormat(AttrDataFormat.SPECTRUM);
        TangoDataType<byte[]> type = format.getDataType(TangoConst.Tango_DEV_ENCODED);

        assertNull(type);
    }
}
