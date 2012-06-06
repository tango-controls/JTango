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

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.log.FileExecutionLogger2;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tango.server.ServerManager;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.soleil.tango.clientapi.TangoCommand;

@RunWith(Parameterized.class)
public class JTangoCommandPerf {
    @Rule
    public ContiPerfRule i = new ContiPerfRule(new FileExecutionLogger2("locale", 1000));

    /*
     * FileExecutionLogger2 type = local ou distant | nbrTest = nombre de test
     */
    private final String command;
    private final Object valueTest;

    @Parameters
    public static List<Object[]> getParametres() {
	final DevVarLongStringArray devVarLongStringArray = new DevVarLongStringArray();
	devVarLongStringArray.lvalue = new int[] { 1, 12 };
	devVarLongStringArray.svalue = new String[] { "toto", "tata" };
	final DevVarDoubleStringArray devVarDoubleStringArray = new DevVarDoubleStringArray();
	devVarDoubleStringArray.dvalue = new double[] { 1.0D, 12.568D };
	devVarDoubleStringArray.svalue = new String[] { "totot", "tatat" };
	return Arrays.asList(new Object[][] {
		// SHORT
		{ (short) 32767, "shortShortCommand" },
		{ new short[] { -32768, 254, 32767 }, "shortShortSpectrumCommand" },
		// INTEGER
		{ 10, "intIntCommand" },
		{ new int[] { -2147483648, 250, 2147483647 }, "intIntSpectrumCommand" },
		// LONG
		{ 2500L, "longLongCommand" },
		{ new long[] { -9223372036854775808L, 250L, 9223372036854775807L }, "longLongSpectrumCommand" },
		// FLOAT
		{ 10F, "floatFloatCommand" },

		{ new float[] { 100F, 250F, 2578F }, "floatFloatSpectrumCommand" },
		// DOUBLE
		{ 10.0D, "doubleDoubleCommand" },
		{ new double[] { 100.0D, 250.125D, 2578.987D }, "doubleDoubleSpectrumCommand" },
		// BOOLEAN

		{ true, "booleanBooleanCommand" },
		{ false, "booleanBooleanCommand" },
		// BooleanCommand not exist for Spectrum
		// STRING
		{ "testToto", "stringStringCommand" },
		{ new String[] { "toto11", "toto12", "toto13", "toto14" }, "stringStringSpectrumCommmand" },

		// DevVarLongStringArray
		{ devVarLongStringArray, "longStringCommand" },
		// DevVarDoubleStringArray
		{ devVarDoubleStringArray, "doubleStringCommand" }, });

    }

    @BeforeClass
    public static void startDevice() throws DevFailed {
	assertThat(System.getProperty("TANGO_HOST"), notNullValue());
	ServerManager.getInstance().startError(new String[] { "1" }, "JTangoTest");
    }

    public JTangoCommandPerf(final Object valueTest, final String command) {
	this.command = command;
	this.valueTest = valueTest;
    }

    @Test
    @Ignore
    @PerfTest(invocations = 1000)
    public void commandTest() throws DevFailed {
	try {
	    final TangoCommand tangoCommand = new TangoCommand("test/tango/jtangotest.1/" + command);
	    if (valueTest instanceof DevVarLongStringArray) {
		final DevVarLongStringArray mix = (DevVarLongStringArray) valueTest;
		tangoCommand.insertMixArgin(mix.lvalue, mix.svalue);
		tangoCommand.execute();
		final Object actualLongueArray = tangoCommand.getNumLongMixArrayArgout();
		final Object actualStringArray = tangoCommand.getStringMixArrayArgout();
	    } else if (valueTest instanceof DevVarDoubleStringArray) {
		final DevVarDoubleStringArray mix = (DevVarDoubleStringArray) valueTest;
		tangoCommand.insertMixArgin(mix.dvalue, mix.svalue);
		tangoCommand.execute();
		final Object actualDoubleArray = tangoCommand.getNumDoubleMixArrayArgout();
		final Object actualStringArray = tangoCommand.getStringMixArrayArgout();
	    } else {
		final Object actual = tangoCommand.executeExtract(valueTest);
	    }

	} catch (final DevFailed e) {
	    DevFailedUtils.printDevFailed(e);
	    throw e;
	}
    }
}
