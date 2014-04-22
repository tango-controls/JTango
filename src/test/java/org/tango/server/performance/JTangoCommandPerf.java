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
package org.tango.server.performance;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.server.testserver.NoDBDeviceManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.soleil.tango.clientapi.TangoCommand;

@RunWith(Parameterized.class)
public class JTangoCommandPerf extends NoDBDeviceManager {
    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    // private final String command;
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

//    @BeforeClass
//    public static void startDevice() throws DevFailed {
//        ServerManager.getInstance().startError(new String[] { "1" }, "JTangoTest");
//    }

    private final TangoCommand tangoCommand;

    public JTangoCommandPerf(final Object valueTest, final String command) throws DevFailed {
        // this.command = command;
        this.valueTest = valueTest;
        tangoCommand = new TangoCommand(deviceName + "/" + command);
    }

    @Test
    // @Ignore
    @PerfTest(invocations = 1000)
    public void commandTest() throws DevFailed {
        try {
            if (valueTest instanceof DevVarLongStringArray) {
                final DevVarLongStringArray mix = (DevVarLongStringArray) valueTest;
                tangoCommand.insertMixArgin(mix.lvalue, mix.svalue);
                tangoCommand.execute();
                // final Object actualLongueArray = tangoCommand.getNumLongMixArrayArgout();
                // final Object actualStringArray = tangoCommand.getStringMixArrayArgout();
            } else if (valueTest instanceof DevVarDoubleStringArray) {
                final DevVarDoubleStringArray mix = (DevVarDoubleStringArray) valueTest;
                tangoCommand.insertMixArgin(mix.dvalue, mix.svalue);
                tangoCommand.execute();
                // final Object actualDoubleArray = tangoCommand.getNumDoubleMixArrayArgout();
                // final Object actualStringArray = tangoCommand.getStringMixArrayArgout();
            } else {
                // final Object actual = tangoCommand.executeExtract(valueTest);
            }

        } catch (final DevFailed e) {
            DevFailedUtils.printDevFailed(e);
            throw e;
        }
    }
}
