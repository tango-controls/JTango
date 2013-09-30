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
import org.tango.server.attribute.AttributeValue;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;

/**
 * manage trigger for {@link EventType#QUALITY_EVENT}
 * 
 * @author ABEILLE
 * 
 */
public class QualityEventTrigger implements IEventTrigger {

    private final Logger logger = LoggerFactory.getLogger(PeriodicEventTrigger.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(PeriodicEventTrigger.class);

    private AttributeValue previousValue;
    private AttributeValue value;
    private final AttributeImpl attribute;

    /**
     * Ctr
     * 
     * @param attribute The attribute that will send events
     */
    public QualityEventTrigger(final AttributeImpl attribute) {
        this.attribute = attribute;
        previousValue = attribute.getReadValue();
    }

    @Override
    public boolean isSendEvent() {
        xlogger.entry();
        previousValue = value;
        value = attribute.getReadValue();
        final AttrQuality previousQuality = previousValue.getQuality();
        final AttrQuality quality = value.getQuality();
        final boolean hasChanged = !quality.equals(previousQuality);
        logger.debug("QUALITY event must send: {}", hasChanged);
        xlogger.exit();
        return hasChanged;
    }

    @Override
    public void setError(final DevFailed error) {
    }

    @Override
    public void updateProperties() {
    }

    @Override
    public boolean doCheck() {
        return true;
    }

}
