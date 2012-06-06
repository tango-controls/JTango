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
package org.tango.server.idl;

import fr.esrf.Tango.TimeVal;

public final class TangoIDLUtil {

    private static final int FACTOR_1000 = 1000;

    private TangoIDLUtil() {
    }

    public static TimeVal getTime(final long nbMillisec) {
	final TimeVal timeVal = new TimeVal();
	timeVal.tv_sec = (int) (nbMillisec / FACTOR_1000);
	timeVal.tv_usec = (int) ((nbMillisec - nbMillisec / FACTOR_1000 * FACTOR_1000) * FACTOR_1000);
	timeVal.tv_nsec = 0;
	return timeVal;
    }

    public static long getTime(final TimeVal timeVal) {
	return timeVal.tv_nsec * FACTOR_1000 + timeVal.tv_usec * FACTOR_1000 * FACTOR_1000;
    }
}
