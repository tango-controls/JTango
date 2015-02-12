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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.soleil.tango.clientapi.TangoAttribute;

@RunWith(Parameterized.class)
public class AttributeTest extends NoDBDeviceManager {

    private final String attributeName;
    private final Object writeValue;

    @Parameterized.Parameters(name = "{index}: {1}")
    public static List<Object[]> getParametres() {
        return Arrays
                .asList(new Object[][] {

                        // SHORT
                        { (short) 11, "shortScalar" }, // 1
                        { new short[] { 3400, 3500, 3600 }, "shortSpectrum" },// 2
                        { new short[][] { { -32768, 3500, 32767 }, { 1, 2, 3 } }, "shortImage" },// 3
                        // SHORT dynamic
                        { (short) 10, "shortDynamic" },// 4
                        { new short[] { 3400, 3500, 3600 }, "short[]Dynamic" },
                        { new short[][] { { -32768, 3500, 32767 }, { 1, 2, 3 } }, "short[][]Dynamic" },
                        // INTEGER
                        { 10, "intScalar" },
                        { new int[] { 254, 1567981215, 02, 2185 }, "intSpectrum" },
                        {
                                new int[][] { { 259874542, 498212434, 420, 48942120 },
                                        { -2147483648, 154, 2147483647, 4541 } }, "intImage" },
                        // INTEGER dynamic
                        { 10, "intDynamic" },
                        { new int[] { 254, 1567981215, 02, 2185 }, "int[]Dynamic" },
                        {
                                new int[][] { { 259874542, 498212434, 420, 48942120 },
                                        { -2147483648, 154, 2147483647, 4541 } }, "int[][]Dynamic" },
                        // LONG
                        { 9223372036854775807L, "longScalar" },
                        { new long[] { 37203685477580L, 1015054014654320L }, "longSpectrum" },
                        {
                                new long[][] { { 259874542L, 498212434L, 420L, 48942120L },
                                        { 37203685477580L, 1015054014654320L, 45648972468L, 89740140165421321L },
                                        { 37203685477580L, 1015054014654320L, 45648972468L, 89740140165421321L } },
                                "longImage" },

                        // LONG dynamic
                        { 9223372036854775807L, "longDynamic" },
                        { new long[] { 37203685477580L, 1015054014654320L }, "long[]Dynamic" },
                        {
                                new long[][] { { 259874542L, 498212434L, 420L, 48942120L },
                                        { 37203685477580L, 1015054014654320L, 45648972468L, 89740140165421321L },
                                        { 37203685477580L, 1015054014654320L, 45648972468L, 89740140165421321L } },
                                "long[][]Dynamic" },
                        // FLOAT
                        { 2500.05410F, "floatScalar" },
                        { new float[] { 3400F, 3500F, 3600F }, "floatSpectrum" },
                        {
                                new float[][] { { 100F, 250F, 2578F },
                                        { 1.40239846F, 124580.16510156F, 4781.0654015415412F } }, "floatImage" },
                        // FLOAT dynamic
                        { 2500.05410F, "floatDynamic" },
                        { new float[] { 3400F, 3500F, 3600F }, "float[]Dynamic" },
                        {
                                new float[][] { { 100F, 250F, 2578F },
                                        { 1.40239846F, 124580.16510156F, 4781.0654015415412F } }, "float[][]Dynamic" },
                        // DOUBLE
                        { 1002500.125D, "doubleScalar" },
                        { new double[] { 3400.258D, 3500.012D, 3600.234D }, "doubleSpectrum" },
                        {
                                new double[][] { { 100.0D, 250.125D, 2578.987D },
                                        { 10046546554.0065420205D, 2545600.18424325D, 25487098778.9165041000787D } },

                                "doubleImage" },
                        // DOUBLE dynamic
                        { 1002500.125D, "doubleDynamic" },
                        { new double[] { 3400.258D, 3500.012D, 3600.234D }, "double[]Dynamic" },
                        {
                                new double[][] { { 100.0D, 250.125D, 2578.987D },
                                        { 10046546554.0065420205D, 2545600.18424325D, 25487098778.9165041000787D } },

                                "double[][]Dynamic" },
                        // BYTE
                        { (short) 10, "byteScalar" },
                        { new short[] { 10, 15, 20 }, "byteSpectrum" },
                        { new short[][] { { 10, 15, 20 }, { 20, 35, 40 } }, "byteImage" },
                        // BYTE dynamic
                        { (short) 10, "byteDynamic" },
                        { new short[] { 10, 15, 20 }, "byte[]Dynamic" },
                        { new short[][] { { 10, 15, 20 }, { 20, 35, 40 } }, "byte[][]Dynamic" },
                        // BOOLEAN
                        { (boolean) true, "booleanScalar" },
                        { new boolean[] { true, false, false, true }, "booleanSpectrum" },
                        { new boolean[][] { { true, false, false, true }, { false, false, true, false } },
                                "booleanImage" },
                        // BOOLEAN dynamic
                        { (boolean) true, "booleanDynamic" },
                        { new boolean[] { true, false, false, true }, "boolean[]Dynamic" },
                        { new boolean[][] { { true, false, false, true }, { false, false, true, false } },
                                "boolean[][]Dynamic" },
                        // STRING
                        { "toto", "stringScalar" },
                        { new String[] { "toto11", "toto12", "toto13", "toto14" }, "stringSpectrum" },
                        {
                                new String[][] { { "toto21", "toto22", "toto23", "toto24" },
                                        { "toto25", "toto26", "toto27", "toto28" } }, "stringImage" },
                        // STRING dynamic
                        { "toto", "StringDynamic" },
                        { new String[] { "toto11", "toto12", "toto13", "toto14" }, "String[]Dynamic" },
                        {
                                new String[][] { { "toto21", "toto22", "toto23", "toto24" },
                                        { "toto25", "toto26", "toto27", "toto28" } }, "String[][]Dynamic" },
                        // DEVENCODED
                        { new DevEncoded("test", new byte[] { 1, 2, 3, 5, 6 }), "devEncodedScalar" },
                        // TODO: arrays of DevEncoded not supported by tangorb client
                        // api
                        // { new DevEncoded[] { new DevEncoded("test", new byte[] { 1,
                        // 2, 3, 5, 6 }) },
                        // "devEncodedSpectrum" },
                        // { new DevEncoded[][] { { new DevEncoded("test", new byte[] {
                        // 1, 2, 3, 5, 6 }) } },
                        // "devEncodedImage" },
                        // Fin Array.asList
                        // STATE
                        { DevState.ALARM, "stateScalar" },
//                         { (short) 2, "enumAttribute" },
//                         { (short) 2, "DevEnumDynamic" },
                        { new DevState[] { DevState.FAULT, DevState.OFF }, "stateSpectrum" } });
    }

    private static int testNb = 1;

    public AttributeTest(final Object writeValue, final String attributeName) {
        this.attributeName = attributeName;
        this.writeValue = writeValue;
    }

    @Test
    public void attributTest() throws DevFailed {
        try {
            System.out.println(testNb++ + " TEST " + attributeName);
            final TangoAttribute att = new TangoAttribute(deviceName + "/" + attributeName);
            // test default value
            att.read();
            // echo test
            att.write(writeValue);
            final Object readValue = att.read();
            if (att.isImage()) {
                for (int i = 0; i < Array.getLength(writeValue); i++) {
                    final Object lineW = Array.get(writeValue, i);
                    final Object lineR = Array.get(readValue, i);
                    for (int j = 0; j < Array.getLength(lineW); j++) {
                        try {
                            assertThat(Array.get(lineR, j), equalTo(Array.get(lineW, j)));
                        } catch (final AssertionError e) {
                            System.out.println("ASSERT ERROR " + "test/tango/jtangotest.1/" + attributeName);
                            throw e;
                        }
                    }
                }
            } else {
                try {
                    if (readValue instanceof DevEncoded && writeValue instanceof DevEncoded) {
                        final DevEncoded encodedRead = (DevEncoded) readValue;
                        final DevEncoded encodedWrite = (DevEncoded) writeValue;
                        assertThat(encodedRead.encoded_data, equalTo(encodedWrite.encoded_data));
                        assertThat(encodedRead.encoded_format, equalTo(encodedWrite.encoded_format));
                    } else {
                        assertThat(readValue, equalTo(writeValue));
                    }
                } catch (final AssertionError e) {
                    System.out.println("ASSERT ERROR " + "test/tango/jtangotest.1/" + attributeName);
                    throw e;
                }
            }
        } catch (final DevFailed e) {
            e.printStackTrace();
            System.out.println("FAILED " + deviceName + "/" + attributeName);
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
        // catch (final Exception e) {
        // e.printStackTrace();
        // }
    }
}
