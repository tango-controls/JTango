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
import org.tango.server.attribute.AttributePropertiesImpl;

import fr.esrf.Tango.DevFailed;

/**
 * manage trigger for {@link EventType#ATT_CONF_EVENT}
 * 
 * @author ABEILLE
 * 
 */
public class PropertiesEventTrigger implements IEventTrigger {

    private final Logger logger = LoggerFactory.getLogger(PropertiesEventTrigger.class);
    private final XLogger xlogger = XLoggerFactory.getXLogger(PropertiesEventTrigger.class);
    private final AttributeImpl attribute;
    private AttributePropertiesImpl previousProperties;
    private AttributePropertiesImpl properties;

    /**
     * Ctr
     * 
     * @param attribute The attribute that send events
     */
    PropertiesEventTrigger(final AttributeImpl attribute) {
        this.attribute = attribute;
        properties = new AttributePropertiesImpl(attribute.getProperties());
    }

    @Override
    public boolean isSendEvent() throws DevFailed {
        xlogger.entry();
        // get last properties
        previousProperties = new AttributePropertiesImpl(properties);
        properties = new AttributePropertiesImpl(attribute.getProperties());
        final boolean hasChanged = !previousProperties.equals(properties);
        logger.debug("ATTR_CONF event must send: {}", hasChanged);
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
