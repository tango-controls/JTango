/**
 * Copyright (C) : 2012
 * 
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango. If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.servant;

public final class Constants {

    private Constants() {

    }

    /**
     * TANGO admin device domain name (admin)
     */
    public static final String ADMIN_DEVICE_DOMAIN = "dserver";

    public static final String MEMORIZED_VALUE = "__Value";
    public static final String MEMORIZED_VALUE_DIM = "memorizedValueDim";

    public static final String IS_POLLED = "isPolled";

    public static final String POLLING_PERIOD = "pollingPeriod";

    public static final int QUEUE_CAPACITY = 100;

    public static final String POLLED_OBJECT = "Polled object ";
    public static final String POLLED_ATTR = "polled_attr";
    public static final String MIN_POLLING_PERIOD_IS = "min polling period is ";
}
