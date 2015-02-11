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
package org.tango.server.testserver;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.soleil.tango.clientapi.TangoAttribute;

public class EnumAttributeTest extends NoDBDeviceManager {

    @Test
    public void scalarSimpleTest() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/enumAttribute");
        // test default value
        att.read();
        // echo test
        final short value = 1;
        att.write(value);
        final short result = att.read(short.class);
        assertThat(result, equalTo(value));
    }

    @Test(expected = DevFailed.class)
    public void scalarOutOfRangeTest() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/enumAttribute");
        // test default value
        att.read();
        // echo test
        final short value = 2;
        att.write(value);
    }

    @Test(expected = DevFailed.class)
    public void scalarSetEnumLabelTest() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/enumAttribute");
        // modify enum list
        final AttributeInfoEx props = att.getAttributeProxy().get_info_ex();
        props.enum_label = new String[] { "ab", "cd", "ef" };
        att.getAttributeProxy().set_info(new AttributeInfoEx[] { props });
    }

    @Test
    public void dynamicScalarSimpleTest() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/DevEnumDynamic");
        // test default value
        att.read();
        // echo test
        final short value = 1;
        att.write(value);
        final short result = att.read(short.class);
        assertThat(result, equalTo(value));
    }

    @Test
    public void dynamicScalarChangeEnumTest() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/DevEnumDynamic");

        // test default value
        att.read();
        // modify enum list
        final AttributeInfoEx props = att.getAttributeProxy().get_info_ex();
        props.enum_label = new String[] { "a", "b", "c", "d" };
        att.getAttributeProxy().set_info(new AttributeInfoEx[] { props });
        // write
        final short value = 3;
        att.write(value);
        final short result = att.read(short.class);
        assertThat(result, equalTo(value));
    }

    @Test
    public void dynamicScalarChangeEnumTest2() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/DevEnumDynamic");
        // test default value
        att.read();
        // modify enum list
        final AttributeInfoEx props = att.getAttributeProxy().get_info_ex();
        props.enum_label = new String[] { "a" };
        att.getAttributeProxy().set_info(new AttributeInfoEx[] { props });
        // write
        final short value = 0;
        att.write(value);
        final short result = att.read(short.class);
        assertThat(result, equalTo(value));
    }

    @Test(expected = DevFailed.class)
    public void dynamicScalarErrorTest() throws DevFailed {
        final TangoAttribute att = new TangoAttribute(deviceName + "/DevEnumDynamic");
        // test default value
        att.read();
        // modify enum list
        final AttributeInfoEx props = att.getAttributeProxy().get_info_ex();
        props.enum_label = new String[] { "a" };
        att.getAttributeProxy().set_info(new AttributeInfoEx[] { props });
        // write
        final short value = 1;
        att.write(value);

    }

}
