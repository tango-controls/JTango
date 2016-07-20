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
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

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
    private Queue<Triple<String, String, String>> log = new ArrayDeque<Triple<String, String, String>>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd '-' HH:mm:ss");

    public AttributeAppender() {
        this(1000);
    }

    public AttributeAppender(final int depth) {
        this.depth = depth;
        log = new ArrayDeque<Triple<String, String, String>>(depth);
    }

    @Override
    protected void append(final ILoggingEvent eventObject) {
        while (log.size() >= depth) {
            log.poll();
        }
        final Triple<String, String, String> event = new ImmutableTriple<String, String, String>(
                dateFormat.format(new Date(eventObject.getTimeStamp())), eventObject.getLevel().toString(),
                eventObject.getFormattedMessage());
        log.offer(event);
    }

    public String[][] getLog() {
        final String[][] result = new String[log.size()][3];
        int i = 0;
        for (final Triple<String, String, String> triple : log) {
            result[i][0] = triple.getLeft();
            result[i][1] = triple.getMiddle();
            result[i][2] = triple.getRight();
            i++;
        }
        return result;
    }
}
