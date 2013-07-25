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
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.tango.server.attribute.AttributeImpl;

import fr.esrf.Tango.DevFailed;

/**
 * manage trigger for {@link EventType#ARCHIVE_EVENT}
 * 
 * @author ABEILLE
 * 
 */
public class ArchiveEventTrigger implements IEventTrigger {
    private final Logger logger = LoggerFactory.getLogger(ArchiveEventTrigger.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(ArchiveEventTrigger.class);

    private final ChangeEventTrigger change;
    private final PeriodicEventTrigger periodic;

    /**
     * Ctr
     * 
     * @param period The archive period
     * @param absolute The archive absolute change delta
     * @param relative The archive relative change delta
     * @param attribute The attribute that send events
     */
    public ArchiveEventTrigger(final long period, final String absolute, final String relative,
            final AttributeImpl attribute) {
        periodic = new PeriodicEventTrigger(period, attribute);
        change = new ChangeEventTrigger(attribute, absolute, relative);
    }

    @Override
    public boolean isSendEvent() throws DevFailed {
        xlogger.entry();
        boolean isSend = periodic.isSendEvent();
        if (!isSend) {
            isSend = change.isSendEvent();
        }
        logger.debug("ARCHIVE event must send: {}", isSend);
        xlogger.exit();
        return isSend;
    }

    @Override
    public void setError(final DevFailed error) {
        change.setError(error);

    }

}
