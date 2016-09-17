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
import org.tango.server.Chronometer;
import org.tango.server.attribute.AttributeImpl;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.EventProperties;

/**
 * manage trigger for {@link EventType#PERIODIC_EVENT}
 * 
 * @author ABEILLE
 * 
 */
public class PeriodicEventTrigger implements IEventTrigger {
    private final Chronometer periodicChrono = new Chronometer();

    private final Logger logger = LoggerFactory.getLogger(PeriodicEventTrigger.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(PeriodicEventTrigger.class);

    private long period;
    private boolean isSamePeriod;

    private final AttributeImpl attribute;

    /**
     * Ctr
     * 
     * @param period The period at which to send events
     * @param attribute the specified attribute
     */
    public PeriodicEventTrigger(final long period, final AttributeImpl attribute) {
        this.attribute = attribute;
        this.period = period;
        isSamePeriod = attribute.getPollingPeriod() == period;
        // first time, send event right now
        periodicChrono.stop();
    }

    public void setPeriod(final long period) {
        this.period = period;
        isSamePeriod = attribute.getPollingPeriod() == period;
    }

    @Override
    public boolean isSendEvent() {
        xlogger.entry();
        boolean hasChanged = false;
        if (period > 0) {
            if (isSamePeriod) {
                // the polling period is the same as the periodic event period. So always send.
                hasChanged = true;
            } else if (periodicChrono.isOver() && period > 0) {
                hasChanged = true;
                periodicChrono.start(period);
            }
        }
        logger.debug("PERIODIC event must send: {}", hasChanged);
        xlogger.exit();
        return hasChanged;
    }

    @Override
    public void setError(final DevFailed error) {
    }

    @Override
    public void updateProperties() throws DevFailed {
        final EventProperties props = attribute.getProperties().getEventProp();
        try {
            period = Long.parseLong(props.per_event.period);
        } catch (final NumberFormatException e) {
        }

    }

    @Override
    public boolean doCheck() {
        return true;
    }

}
