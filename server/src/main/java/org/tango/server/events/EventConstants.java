/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.events;

/**
 * defines constants to manage ZMQ events
 *
 * @author verdier
 */
interface EventConstants {

    // Communication constants
    int HWM_DEFAULT = 1000;
    int USER_PORT_START = 55555;
    int USER_PORT_END = 64000;

    // Miscellaneous releases
    int TANGO_RELEASE = 930;
    int ZMQ_RELEASE = (int) (100.0 * EventUtilities.getZmqVersion());

    // Time to manage events
    long EVENT_RESUBSCRIBE_PERIOD = 600000;
    long EVENT_HEARTBEAT_PERIOD = 9000;

    // Used by sent object but not yet by client API
    String EXECUTE_METHOD = "";
    byte[] OBJECT_IDENTIFIER = {};
}
