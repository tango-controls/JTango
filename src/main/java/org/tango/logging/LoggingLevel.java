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
package org.tango.logging;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.classic.Level;

/**
 * Manage convertion between logback logging levels and tango levels
 *
 * @author ABEILLE
 *
 */
public enum LoggingLevel {
    OFF(Level.OFF), FATAL(Level.ERROR), ERROR(Level.ERROR), WARN(Level.WARN), INFO(Level.INFO), DEBUG(Level.DEBUG), TRACE(
            Level.TRACE);

    private static final Map<Level, LoggingLevel> LEVEL_MAP = new HashMap<Level, LoggingLevel>();
    static {
        for (final LoggingLevel s : EnumSet.allOf(LoggingLevel.class)) {
            LEVEL_MAP.put(s.level, s);
        }
    }

    private static final int OFF_VALUE = 0;
    private static final int FATAL_VALUE = 1;
    private static final int ERROR_VALUE = 2;
    private static final int WARN_VALUE = 3;
    private static final int INFO_VALUE = 4;
    private static final int DEBUG_VALUE = 5;
    private static final int TRACE_VALUE = 6;

    private static final String OFF_STR = "OFF";
    private static final String FATAL_STR = "FATAL";
    private static final String ERROR_STR = "ERROR";
    private static final String WARN_STR = "WARN";
    private static final String INFO_STR = "INFO";
    private static final String DEBUG_STR = "DEBUG";
    private static final String TRACE_STR = "TRACE";

    private static final Map<LoggingLevel, Integer> LEVEL_INT_MAP = new HashMap<LoggingLevel, Integer>();
    static {
        LEVEL_INT_MAP.put(OFF, OFF_VALUE);
        LEVEL_INT_MAP.put(FATAL, FATAL_VALUE);
        LEVEL_INT_MAP.put(ERROR, ERROR_VALUE);
        LEVEL_INT_MAP.put(WARN, WARN_VALUE);
        LEVEL_INT_MAP.put(INFO, INFO_VALUE);
        LEVEL_INT_MAP.put(DEBUG, DEBUG_VALUE);
        LEVEL_INT_MAP.put(TRACE, TRACE_VALUE);
    }

    private static final Map<Integer, LoggingLevel> INT_LEVEL_MAP = new HashMap<Integer, LoggingLevel>();
    static {
        INT_LEVEL_MAP.put(OFF_VALUE, OFF);
        INT_LEVEL_MAP.put(FATAL_VALUE, FATAL);
        INT_LEVEL_MAP.put(ERROR_VALUE, ERROR);
        INT_LEVEL_MAP.put(WARN_VALUE, WARN);
        INT_LEVEL_MAP.put(INFO_VALUE, INFO);
        INT_LEVEL_MAP.put(DEBUG_VALUE, DEBUG);
        INT_LEVEL_MAP.put(TRACE_VALUE, TRACE);
    }

    private static final Map<String, LoggingLevel> LEVEL_STRING_MAP = new HashMap<String, LoggingLevel>();
    static {
        LEVEL_STRING_MAP.put(OFF_STR, OFF);
        LEVEL_STRING_MAP.put(FATAL_STR, FATAL);
        LEVEL_STRING_MAP.put(ERROR_STR, ERROR);
        LEVEL_STRING_MAP.put(WARN_STR, WARN);
        LEVEL_STRING_MAP.put(INFO_STR, INFO);
        LEVEL_STRING_MAP.put(DEBUG_STR, DEBUG);
        LEVEL_STRING_MAP.put(TRACE_STR, TRACE);
    }

    private Level level;

    LoggingLevel(final Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public static LoggingLevel getLevel(final Level level) {
        return LEVEL_MAP.get(level);
    }

    public static Level getLevelFromInt(final int intLevel) {
        return INT_LEVEL_MAP.get(intLevel).getLevel();
    }

    public static LoggingLevel getLevelFromString(final String stringLevel) {
        return LEVEL_STRING_MAP.get(stringLevel);
    }

    public int toInt() {
        return LEVEL_INT_MAP.get(this);
    }

}
