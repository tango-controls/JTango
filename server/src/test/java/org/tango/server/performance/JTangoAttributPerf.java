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
import org.tango.server.testserver.NoDBDeviceManager;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

@RunWith(Parameterized.class)
public class JTangoAttributPerf extends NoDBDeviceManager {

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    // @Rule
    // public ContiPerfRule i = new ContiPerfRule(new
    // FileExecutionLogger2("locale", 1000));

    private final String attributeName;

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> getParametres() {
        return Arrays.asList(new Object[][] {

                // SHORT
                { "shortScalar" }, // 1
                { "shortSpectrum" },// 2
                { "shortImage" },// 3
                // SHORT dynamic
                { "shortDynamic" },// 4
                { "short[]Dynamic" }, { "short[][]Dynamic" },
                // INTEGER
                { "intScalar" }, { "intSpectrum" }, { "intImage" },
                // INTEGER dynamic
                { "intDynamic" }, { "int[]Dynamic" }, { "int[][]Dynamic" },
                // LONG
                { "longScalar" }, { "longSpectrum" }, { "longImage" },

                // LONG dynamic
                { "longDynamic" }, { "long[]Dynamic" }, { "long[][]Dynamic" },
                // FLOAT
                { "floatScalar" }, { "floatSpectrum" }, { "floatImage" },
                // FLOAT dynamic
                { "floatDynamic" }, { "float[]Dynamic" }, { "float[][]Dynamic" },
                // DOUBLE
                { "doubleScalar" }, { "doubleSpectrum" }, { "doubleImage" },
                // DOUBLE dynamic
                { "doubleDynamic" }, { "double[]Dynamic" }, { "double[][]Dynamic" },
                // BYTE
                { "byteScalar" }, { "byteSpectrum" }, { "byteImage" },
                // BYTE dynamic
                { "byteDynamic" }, { "byte[]Dynamic" }, { "byte[][]Dynamic" },
                // BOOLEAN
                { "booleanScalar" }, { "booleanSpectrum" }, { "booleanImage" },
                // BOOLEAN dynamic
                { "booleanDynamic" }, { "boolean[]Dynamic" }, { "boolean[][]Dynamic" },
                // STRING
                { "stringScalar" }, { "stringSpectrum" }, { "stringImage" },
                // STRING dynamic
                { "StringDynamic" }, { "String[]Dynamic" }, { "String[][]Dynamic" },
                // DEVENCODED
                { "devEncodedScalar" },
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
                { "stateScalar" }, { "stateSpectrum" } });
    }

    private final DeviceProxy proxy;

    public JTangoAttributPerf(final String attributeName) throws DevFailed {
        this.attributeName = attributeName;
        proxy = new DeviceProxy(deviceName);
    }

    @Test
    // @Ignore
    @PerfTest(invocations = 10)
    public void attributTest() throws DevFailed {
        proxy.read_attribute(attributeName);
    }
}
