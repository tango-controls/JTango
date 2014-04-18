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
package org.tango.server.attribute.log;

import org.slf4j.Logger;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;

public class LogAttribute implements IAttributeBehavior {

    private final AttributeAppender attributeAppender;

    public LogAttribute(final int depth, final Logger... loggers) {
	attributeAppender = new AttributeAppender(depth);
	for (final Logger logger : loggers) {
	    if (logger instanceof ch.qos.logback.classic.Logger) {
		final ch.qos.logback.classic.Logger loggerBack = (ch.qos.logback.classic.Logger) logger;
		loggerBack.addAppender(attributeAppender);
		attributeAppender.start();
	    }
	}

    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
	final AttributeConfiguration config = new AttributeConfiguration();
	config.setName("log");
	config.setTangoType(TangoConst.Tango_DEV_STRING, AttrDataFormat.IMAGE);
	config.setWritable(AttrWriteType.READ);
	return config;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
	return new AttributeValue(attributeAppender.getLog());
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {

    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
	// TODO Auto-generated method stub
	return null;
    }

}
