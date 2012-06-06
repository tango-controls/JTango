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
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.log.FileExecutionLogger2;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import fr.esrf.Tango.DevFailed;
import fr.soleil.tango.clientapi.TangoAttribute;

@RunWith(Parameterized.class)
public class cppTestTangoPerf {

    @Rule
    public ContiPerfRule i = new ContiPerfRule(new FileExecutionLogger2("locale", 1000));
    private final String attributeName;

    /*
     * FileExecutionLogger2 type = local ou distant | nbrTest = nombre de test
     */

    @Parameterized.Parameters
    public static List<Object[]> getParametres() {
	return Arrays.asList(new Object[][] {
	// // SHORT
	// { "read_short_test" },
	// { "read_short_spec_test" },
	// { "read_short_spec_test2" },
	// { "read_short_image_test" },
	// { "read_short_image_test2" },
	// // INTEGER
	// { "read_int_test" },
	// { "read_int_spec_test" },
	// { "read_int_spec_test2" },
	// { "read_int_image_test" },
	// { "read_int_image_test2" },
	// // FLOAT
	// { "read_float_test" },
	// { "read_float_spec_test" },
	// { "read_float_spec_test2" },
	// { "read_float_image_test" },
	// { "read_float_image_test2" },
	// // DOUBLE
	// { "read_double_test" },
	// { "read_double_spec_test" },
	// { "read_double_spec_test2" },
	// { "read_double_image_test" },
	// { "read_double_image_test2" },
	// // BOOLEAN
	// { "read_boolean_test" },
	// { "read_boolean_spec_test" },
	// { "read_boolean_spec_test2" },
	// { "read_boolean_image_test" },
	// { "read_boolean_image_test2" },
	// // STRING
	// { "read_string_test" },
	// { "read_string_spec_test" },
	// { "read_string_spec_test2" },
	// { "read_string_image_test" }
	// { "read_string_image_test2" }

		});
    }

    public cppTestTangoPerf(final String attributeName) {
	this.attributeName = attributeName;
    }

    @Ignore
    @Test
    @PerfTest(invocations = 1000)
    public void test_tango_att() {
	try {
	    // TODO server name
	    final TangoAttribute tangoAttribut = new TangoAttribute("test_tango/Test_Tango/2/" + attributeName);
	    tangoAttribut.read();
	} catch (final DevFailed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
