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
package org.tango.server;

public final class Constants {

    public static final String INIT_IN_PROGRESS = "Init in progress";
    public static final String INIT_FAILED = "Init failed";

    private Constants() {

    }

    /**
     * TANGO admin device domain name (admin)
     */
    public static final String ADMIN_DEVICE_DOMAIN = "dserver";
    public static final String ADMIN_SERVER_CLASS_NAME = "DServer";

    // default device properties
    public static final String STATE_CHECK_ATTR_ALARM = "StateCheckAttrAlarm";
    public static final String POLLED_ATTR = "polled_attr";
    public static final String POLL_RING_DEPTH = "poll_ring_depth";
    public static final String ATTR_POLL_RING_DEPTH = "attr_poll_ring_depth";
    public static final String CMD_POLL_RING_DEPTH = "cmd_poll_ring_depth";
    public static final String CMD_MIN_POLL_PERIOD = "cmd_min_poll_period";
    public static final String MIN_POLL_PERIOD = "min_poll_period";
    public static final String ATTR__MIN_POLL_PERIOD = "attr_min_poll_period";
    public static final String LOGGING_TARGET = "logging_target";
    public static final String LOGGING_LEVEL = "logging_level";

    /**
     * Retrieve the default value of STATE_CHECK_ATTR_ALARM from a system property. {@link stateCheckAttrAlarm}
     */
    public static final String STATE_CHECK_ALARMS_DEFAULT = System.getProperty("org.tango.server.checkalarms", "false");

    // attribute properties names in tango db
    public static final String MEMORIZED_VALUE = "__value";
    public static final String MEMORIZED_VALUE_DIM = "memorizedValueDim";
    public static final String LABEL = "label";
    public static final String FORMAT = "format";
    public static final String UNIT = "unit";
    public static final String DISPLAY_UNIT = "display_unit";
    public static final String STANDARD_UNIT = "standard_unit";
    public static final String MIN_VAL = "min_value";
    public static final String MAX_VAL = "max_value";
    public static final String MIN_ALARM = "min_alarm";
    public static final String MAX_ALARM = "max_alarm";
    public static final String MIN_WARNING = "min_warning";
    public static final String MAX_WARNING = "max_warning";
    public static final String DELTA_T = "delta_t";
    public static final String DELTA_VAL = "delta_val";
    public static final String DESC = "description";
    public static final String EVENT_CHANGE_ABS = "abs_change";
    public static final String EVENT_CHANGE_REL = "rel_change";
    public static final String EVENT_PERIOD = "event_period";
    public static final String EVENT_ARCHIVE_PERIOD = "archive_period";
    public static final String EVENT_ARCHIVE_REL = "archive_rel_change";
    public static final String EVENT_ARCHIVE_ABS = "archive_abs_change";
    public static final String ENUM_LABELS = "enum_labels";
    public static final String ROOT_ATTRIBUTE = "__root_att";
    public static final String IS_POLLED = "isPolled";
    public static final String POLLING_PERIOD = "pollingPeriod";

    // default values
    public static final String NOT_SPECIFIED = "Not specified";
    public static final String FORMAT_6_2F = "%6.2f";
    public static final String FORMAT_S = "%s";
    public static final String FORMAT_D = "%d";
    public static final String NO_UNIT = "No unit";
    public static final String NO_STD_UNIT = "No standard unit";
    public static final String NO_DIPLAY_UNIT = "No display unit";
    public static final String NO_DESCRIPTION = "No description";
    public static final String NONE = "None";
    public static final String NAN = "NAN";
    public static final String PERIOD_1000 = "1000";

    public static final int QUEUE_CAPACITY = 100;
    public static final int DEFAULT_POLL_DEPTH = 10;

    public static final String POLLED_OBJECT = "Polled object ";
    public static final String MIN_POLLING_PERIOD_IS = "min polling period is ";

    public static final String CLIENT_REQUESTS_LOGGER = "TangoClientRequests";
}
