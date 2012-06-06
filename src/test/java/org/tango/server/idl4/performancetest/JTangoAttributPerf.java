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
package org.tango.server.idl4.performancetest;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

@RunWith(Parameterized.class)
public class JTangoAttributPerf {

    // @Rule
    // public ContiPerfRule i = new ContiPerfRule(new
    // FileExecutionLogger2("locale", 1000));

    private final String attributeName;

    @Parameterized.Parameters
    public static List<Object[]> getParametres() {
	return Arrays.asList(new Object[][] {
		// SHORT
		{ "shortScalar" },
		{ "shortSpectrum" },
		{ "shortSpectrum2" },
		{ "shortImage" },
		{ "shortImage2" },
		// INTEGER
		{ "intScalar" },
		{ "intSpectrum" },
		{ "intSpectrum2" },
		{ "intImage" },
		{ "intImage2" },
		// LONG
		{ "longScalar" },
		{ "longSpectrum" },
		{ "longSpectrum2" },
		{ "longImage" },
		{ "longImage2" },
		// FLOAT
		{ "floatScalar" }, { "floatSpectrum" },
		{ "floatSpectrum2" },
		{ "floatImage" },
		{ "floatImage2" },
		// DOUBLE
		{ "doubleScalar" }, { "doubleSpectrum" }, { "doubleSpectrum2" },
		{ "doubleImage" },
		{ "doubleImage2" },
		// BOOLEAN
		{ "booleanScalar" }, { "booleanSpectrum" }, { "booleanSpectrum2" },
		{ "booleanImage" }, { "booleanImage2" },
		// STRING
		{ "stringScalar" }, { "stringSpectrum" }, { "stringSpectrum2" }, { "stringImage" },
		{ "stringImage2" }
	// end Array.asList
		});
    }

    private final DeviceProxy proxy;

    public JTangoAttributPerf(final String attributeName) throws DevFailed {
//	System.setProperty("TANGO_HOST", "calypso:20001");
	this.attributeName = attributeName;
	proxy = new DeviceProxy("test/tango/jtangotest.1");
    }

    @Test
    @Ignore
    @PerfTest(invocations = 1000)
    public void attributTest() throws DevFailed {

	try {
	    // final TangoAttribute att = new
	    // TangoAttribute("test/tango/jtangotest.1/"
	    // + attributeName);
	    // final Object readValue = att.read();
	    final long time1 = System.nanoTime();
	    proxy.read_attribute(attributeName);
	    final long time2 = System.nanoTime();
	    System.out.println(attributeName + ": " + (time2 - time1) / 1000000.0 + " ms");
	} catch (final DevFailed e) {
	    DevFailedUtils.printDevFailed(e);

	    throw e;
	}
    }
}
