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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.soleil.tango.clientapi.TangoCommand;

@RunWith(Parameterized.class)
public class CommandTest extends NoDBDeviceManager {

    private final String command;
    private final Object valueTest;

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> getParametres() {
        final DevVarLongStringArray devVarLongStringArray = new DevVarLongStringArray();
        devVarLongStringArray.lvalue = new int[] { 1, 12 };
        devVarLongStringArray.svalue = new String[] { "toto", "tata" };
        final DevVarDoubleStringArray devVarDoubleStringArray = new DevVarDoubleStringArray();
        devVarDoubleStringArray.dvalue = new double[] { 1.0D, 12.568D };
        devVarDoubleStringArray.svalue = new String[] { "totot", "tatat" };
        return Arrays.asList(new Object[][] {
                // VOID
                { null, "voidCommand" },
                // SHORT
                { (short) 32767, "shortCommand" },
                { new short[] { -32768, 254, 32767 }, "shortSpectrumCommand" },
                // SHORT Dynamic
                { (short) 32767, "shortDynamic" },
                { new short[] { -32768, 254, 32767 }, "short[]Dynamic" },
                // INTEGER
                { 10, "intCommand" },
                { new int[] { -2147483648, 250, 2147483647 }, "intSpectrumCommand" },
                // INTEGER Dynamic
                { 10, "intDynamic" },
                { new int[] { -2147483648, 250, 2147483647 }, "int[]Dynamic" },
                // Byte
                { (byte) 10, "byteCommand" },
                { new byte[] { -2, 50, 21 }, "byteSpectrumCommand" },
                // Byte Dynamic
                { (byte) 10, "byteDynamic" },
                { new byte[] { -2, 50, 21 }, "byte[]Dynamic" },
                // LONG
                { 2500L, "longCommand" },
                { new long[] { -9223372036854775808L, 250L, 9223372036854775807L }, "longSpectrumCommand" },
                // LONG Dynamic
                { 2500L, "longDynamic" },
                { new long[] { -9223372036854775808L, 250L, 9223372036854775807L }, "long[]Dynamic" },
                // FLOAT
                { 10F, "floatCommand" },
                { new float[] { 100F, 250F, 2578F }, "floatSpectrumCommand" },
                // FLOAT Dynamic
                { 10F, "floatDynamic" },
                { new float[] { 100F, 250F, 2578F }, "float[]Dynamic" },
                // DOUBLE
                { 10.0D, "doubleCommand" },
                { new double[] { 100.0D, 250.125D, 2578.987D }, "doubleSpectrumCommand" },
                // DOUBLE Dynamic
                { 10.0D, "doubleDynamic" },
                { new double[] { 100.0D, 250.125D, 2578.987D }, "double[]Dynamic" },
                // BOOLEAN
                { true, "booleanCommand" },
                { false, "booleanCommand" },
                // BooleanCommand not exist for Spectrum
                // BOOLEAN Dynamic
                { true, "booleanDynamic" },
                // BooleanCommand not exist for Spectrum
                // STRING
                { "testToto", "stringCommand" },
                { new String[] { "toto11", "toto12", "toto13", "toto14" }, "stringSpectrumCommmand" },
                // STRING dynamic
                { "testToto", "StringDynamic" },
                { new String[] { "toto11", "toto12", "toto13", "toto14" }, "String[]Dynamic" },
                // DevVarLongStringArray
                { devVarLongStringArray, "longStringCommand" },
                // DevVarLongStringArray Dynamic
                { devVarLongStringArray, "DevVarLongStringArrayDynamic" },
                // DevVarDoubleStringArray
                { devVarDoubleStringArray, "doubleStringCommand" },
                // DevVarDoubleStringArray Dynamic
                { devVarDoubleStringArray, "DevVarDoubleStringArrayDynamic" },

        });

    }

    private static int testNb = 1;

    public CommandTest(final Object valueTest, final String command) {
        this.command = command;
        this.valueTest = valueTest;
    }

    @Test
    public void commandTest() throws DevFailed {
        try {
            System.out.println(testNb++ + " TEST " + command);
            // final TangoCommand tangoCommand = new
            // TangoCommand("test/tango/jtangotest.1/" + command);

            final TangoCommand tangoCommand = new TangoCommand(deviceName + "/" + command);
            if (valueTest instanceof DevVarLongStringArray) {
                final DevVarLongStringArray mix = (DevVarLongStringArray) valueTest;
                tangoCommand.insertMixArgin(mix.lvalue, mix.svalue);
                tangoCommand.execute();
                final Object actualLongueArray = tangoCommand.getNumLongMixArrayArgout();
                final Object actualStringArray = tangoCommand.getStringMixArrayArgout();

                assertThat(actualLongueArray, equalTo((Object) mix.lvalue));
                assertThat(actualStringArray, equalTo((Object) mix.svalue));
            } else if (valueTest instanceof DevVarDoubleStringArray) {
                final DevVarDoubleStringArray mix = (DevVarDoubleStringArray) valueTest;
                tangoCommand.insertMixArgin(mix.dvalue, mix.svalue);
                tangoCommand.execute();
                final Object actualDoubleArray = tangoCommand.getNumDoubleMixArrayArgout();
                final Object actualStringArray = tangoCommand.getStringMixArrayArgout();

                assertThat(actualDoubleArray, equalTo((Object) mix.dvalue));
                assertThat(actualStringArray, equalTo((Object) mix.svalue));
            } else {
                final Object actual = tangoCommand.executeExtract(valueTest);
                try {
                    assertThat(actual, equalTo(valueTest));
                } catch (final AssertionError e) {
                    System.out.println("ASSERT ERROR " + command);
                    throw e;
                }
            }

        } catch (final DevFailed e) {
            System.out.println("FAILED " + command);
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }
}
