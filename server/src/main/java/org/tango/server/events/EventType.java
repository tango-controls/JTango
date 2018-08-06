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

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import org.tango.utils.DevFailedUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EventType {
    /**
     * Change event. Use the attribute properties abs_change and/or rel_change
     */
    CHANGE_EVENT(TangoConst.CHANGE_EVENT, TangoConst.eventNames[0]),
    /**
     * DEPRECATED: Quality event. Send an event if the attribute quality changes.
     *
     * QUALITY_EVENT(TangoConst.QUALITY_EVENT, TangoConst.eventNames[1]),
     */
    /**
     * Periodic event. Send an event at the period specified by the attribute property event_period
     */
    PERIODIC_EVENT(TangoConst.PERIODIC_EVENT, TangoConst.eventNames[2]),
    /**
     * Archived event. Send a periodic event at period configured in property archive_period. Or/and change event with
     * values from archive_rel_change and/or archive_abs_change
     */
    ARCHIVE_EVENT(TangoConst.ARCHIVE_EVENT, TangoConst.eventNames[3]),
    /**
     * User event.
     */
    USER_EVENT(TangoConst.USER_EVENT, TangoConst.eventNames[4]),
    /**
     * Attribute configuration event. Send an event if an attribute's properties change.
     */
    ATT_CONF_EVENT(TangoConst.ATT_CONF_EVENT, TangoConst.eventNames[5]),
    /**
     * Data ready event.
     */
    DATA_READY_EVENT(TangoConst.DATA_READY_EVENT, TangoConst.eventNames[6]),
    /**
     * Interface change event.
     */
    INTERFACE_CHANGE_EVENT(TangoConst.INTERFACE_CHANGE, TangoConst.eventNames[7]),
    /**
     * Interface change event.
     */
    PIPE_EVENT(TangoConst.PIPE_EVENT, TangoConst.eventNames[8]);

    private static final Map<String, EventType> EVENT_TYPE_MAP = new HashMap<String, EventType>();
    private static final Map<Integer, EventType> EVENT_TYPE_INT_MAP = new HashMap<Integer, EventType>();
    private static final List<EventType> EVENT_ATTR_VALUE_TYPE_LIST = new ArrayList<>();

    static {
        for (final EventType s : EnumSet.allOf(EventType.class)) {
            EVENT_TYPE_MAP.put(s.getString(), s);
        }
    }

    static {
        for (final EventType s : EnumSet.allOf(EventType.class)) {
            EVENT_TYPE_INT_MAP.put(s.getValue(), s);
        }
    }

    static {
        EVENT_ATTR_VALUE_TYPE_LIST.add(CHANGE_EVENT);
        EVENT_ATTR_VALUE_TYPE_LIST.add(PERIODIC_EVENT);
        EVENT_ATTR_VALUE_TYPE_LIST.add(ARCHIVE_EVENT);
        EVENT_ATTR_VALUE_TYPE_LIST.add(USER_EVENT);
    }

    private int value;
    private String string;

    EventType(final int value, final String string) {
        this.value = value;
        this.string = string;
    }

    /**
     * Get an {@link EventType} from a String
     *
     * @param string the event type as a String
     * @return the EventType
     * @throws DevFailed if event type does not exist
     */
    public static EventType getEvent(final String string) throws DevFailed {
        final EventType result = EVENT_TYPE_MAP.get(string);
        if (result == null) {
            throw DevFailedUtils.newDevFailed(string + " is not an event type");
        }
        return result;

    }

    /**
     * Get an {@link EventType} from a String
     *
     * @param eventValue the event type as an int
     * @return the EventType
     * @throws DevFailed if event type does not exist
     */
    public static EventType getEvent(final int eventValue) throws DevFailed {
        final EventType result = EVENT_TYPE_INT_MAP.get(eventValue);
        if (result == null) {
            throw DevFailedUtils.newDevFailed(eventValue + " is not an event type");
        }
        return result;

    }

    /**
     * Get event types used only for attribute value events
     * @return
     */
    public static List<EventType> getEventAttrValueTypeList() {
        return EVENT_ATTR_VALUE_TYPE_LIST;
    }

    /**
     *
     * @return EventType as a String
     */
    public String getString() {
        return string;
    }

    /**
     *
     * @return EventType as an int
     */
    public int getValue() {
        return value;
    }

}