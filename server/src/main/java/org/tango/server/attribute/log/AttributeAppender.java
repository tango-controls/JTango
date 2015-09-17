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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * Manage logging to an attribute
 *
 * @author ABEILLE
 *
 */
public final class AttributeAppender extends AppenderBase<ILoggingEvent> {

    private final int depth;
    private volatile String[][] log;
    private int currentPosition;
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy.MM.dd '-' HH:mm:ss ");

    public AttributeAppender() {
        this(1000);
    }

    public AttributeAppender(final int depth) {
        this.depth = depth;
        log = new String[depth][3];
        for (int i = 0; i < depth; i++) {
            Arrays.fill(log[i], "");
        }
        currentPosition = 0;
    }

    @Override
    protected void append(final ILoggingEvent eventObject) {
        log[currentPosition][0] = DATE_FORMAT.format(new Date(eventObject.getTimeStamp()));
        log[currentPosition][1] = eventObject.getLevel().toString();
        log[currentPosition][2] = eventObject.getFormattedMessage();
        currentPosition++;
        if (currentPosition >= depth) {
            currentPosition = 0;
        }
    }

    public String[][] getLog() {
        return log;
    }
}
