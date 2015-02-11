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
package org.tango.server.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.attribute.AttributeImpl;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.EventProperties;

/**
 * Factory to build an event trigger in function of the event type
 * 
 * @author ABEILLE
 * 
 */
public class EventTriggerFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(EventTriggerFactory.class);

    /**
     * Create an {@link IEventTrigger}
     * 
     * @param eventType The event type
     * @param attribute The attribute that will send events
     * @return the created EventTrigger object
     * @throws DevFailed
     */
    public static IEventTrigger createEventTrigger(final EventType eventType, final AttributeImpl attribute)
            throws DevFailed {
        LOGGER.debug("create event trigger for attribute {} of type {}", attribute.getName(), eventType);
        final EventProperties props = attribute.getProperties().getEventProp();
        IEventTrigger eventTrigger;
        switch (eventType) {
            case PERIODIC_EVENT:
                final long period = Long.parseLong(props.per_event.period);
                eventTrigger = new PeriodicEventTrigger(period, attribute);
                break;
            case CHANGE_EVENT:
                eventTrigger = new ChangeEventTrigger(attribute, props.ch_event.abs_change, props.ch_event.rel_change);
                break;
            case ARCHIVE_EVENT:
                long periodA;
                try {
                    // Check if specified and a number
                    periodA = Long.parseLong(props.arch_event.period);
                } catch (final NumberFormatException e) {
                    periodA = -1;
                }
                eventTrigger = new ArchiveEventTrigger(periodA, props.arch_event.abs_change,
                        props.arch_event.rel_change, attribute);
                break;
            case ATT_CONF_EVENT:
            case DATA_READY_EVENT:
            case USER_EVENT:
            case INTERFACE_CHANGE_EVENT:
            case PIPE_EVENT:
            default:
                eventTrigger = new DefaultEventTrigger();
                break;

        }
        return eventTrigger;
    }

}
