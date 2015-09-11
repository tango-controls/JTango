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
package org.tango.server.attribute;

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.Constants;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevAttrHistory_4;
import fr.esrf.Tango.DevAttrHistory_5;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;

public final class AttributeHistory {

    private final Logger logger = LoggerFactory.getLogger(AttributeHistory.class);
    private final String attributeName;
    private final Deque<HistoryItem> valueHistory = new ArrayDeque<HistoryItem>(Constants.QUEUE_CAPACITY);
    private final boolean isReadWrite;
    private final int tangoType;
    private int maxSize = Constants.DEFAULT_POLL_DEPTH;
    private final AttrDataFormat format;

    public AttributeHistory(final String attributeName, final boolean isReadWrite, final int tangoType,
            final AttrDataFormat format) {
        this.attributeName = attributeName;
        this.isReadWrite = isReadWrite;
        this.tangoType = tangoType;
        this.format = format;
    }

    public synchronized void addToHistory(final AttributeValue readValue, final AttributeValue writeValue,
            final DevError[] error) {
        while (valueHistory.size() >= maxSize - 1) {
            valueHistory.poll();
        }
        final boolean isInserted = valueHistory.offer(new HistoryItem(readValue, writeValue, error));
        if (!isInserted) {
            logger.debug("{} not inserted in history queue ", readValue);
        }
    }

    public synchronized int size() {
        return valueHistory.size();
    }

    public synchronized void clear() {
        valueHistory.clear();
    }

    public synchronized DevAttrHistory_4 getAttrHistory4(final int maxSize) throws DevFailed {
        return new AttributeHistoryConvertor(attributeName, valueHistory, maxSize, tangoType, format, isReadWrite)
        .getAttrHistory4();
    }

    public synchronized DevAttrHistory_5 getAttrHistory5(final int maxSize) throws DevFailed {
        return new AttributeHistoryConvertor(attributeName, valueHistory, maxSize, tangoType, format, isReadWrite)
        .getAttrHistory5();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("history size", valueHistory.size());
        return builder.toString();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
    }
}
