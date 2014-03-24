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
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.omg.CORBA.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.orb.ORBManager;
import org.tango.server.Constants;
import org.tango.server.idl.CleverAnyAttribute;
import org.tango.server.idl.TangoIDLUtil;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.DevAttrHistory_4;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.EltInArray;
import fr.esrf.Tango.TimeVal;

public final class AttributeHistory {

    private static class HistoryItem {
	private final AttributeValue readValue;
	private final AttributeValue writeValue;
	private final DevError[] error;

	public HistoryItem(final AttributeValue readValue, final AttributeValue writeValue, final DevError[] error) {
	    super();
	    this.readValue = readValue;
	    this.writeValue = writeValue;
	    this.error = Arrays.copyOf(error, error.length);
	}

	public AttributeValue getReadValue() {
	    return readValue;
	}

	public AttributeValue getWriteValue() {
	    return writeValue;
	}

	public DevError[] getError() {
	    return error;
	}

    }

    private final Logger logger = LoggerFactory.getLogger(AttributeHistory.class);
    private final String attributeName;
    private final Deque<HistoryItem> valueHistory = new ArrayDeque<HistoryItem>(Constants.QUEUE_CAPACITY);
    private final boolean isReadWrite;
    private final int tangoType;
    private int maxSize = 10;

    public AttributeHistory(final String attributeName, final boolean isReadWrite, final int tangoType) {
	this.attributeName = attributeName;
	this.isReadWrite = isReadWrite;
	this.tangoType = tangoType;
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
	final Any values;
	DevAttrHistory_4 result;
	if (!valueHistory.isEmpty()) {
	    final HistoryItem[] historyArray = Collections.asLifoQueue(valueHistory).toArray(
		    new HistoryItem[valueHistory.size()]);
	    final HistoryItem[] returnedhistoryArray = createHistoryArray(maxSize, historyArray);

	    final TimeVal[] times = new TimeVal[returnedhistoryArray.length];
	    final LinkedList<AttrQuality> qualities = new LinkedList<AttrQuality>();
	    final LinkedList<EltInArray> qualSize = new LinkedList<EltInArray>();
	    final LinkedList<AttributeDim> readDim = new LinkedList<AttributeDim>();
	    final LinkedList<EltInArray> readDimSize = new LinkedList<EltInArray>();
	    final LinkedList<AttributeDim> writeDim = new LinkedList<AttributeDim>();
	    final LinkedList<EltInArray> writeDimSize = new LinkedList<EltInArray>();
	    final LinkedList<DevError[]> errors = new LinkedList<DevError[]>();
	    final LinkedList<EltInArray> errorsArray = new LinkedList<EltInArray>();
	    final Object array = fillHistoryArray(returnedhistoryArray, times, qualities, qualSize, readDim,
		    readDimSize, writeDim, writeDimSize, errors, errorsArray);
	    // format data for DevAttrHistory_4
	    final AttrQuality[] qualitiesArray = qualities.toArray(new AttrQuality[qualities.size()]);
	    final EltInArray[] qualSizeArray = qualSize.toArray(new EltInArray[qualSize.size()]);
	    final AttributeDim[] readDimArray = readDim.toArray(new AttributeDim[readDim.size()]);
	    final EltInArray[] readDimSizeArray = readDimSize.toArray(new EltInArray[readDimSize.size()]);
	    AttributeDim[] writeDimArray;
	    EltInArray[] writeDimSizeArray;
	    if (isReadWrite) {
		writeDimArray = writeDim.toArray(new AttributeDim[writeDim.size()]);
		writeDimSizeArray = writeDimSize.toArray(new EltInArray[writeDimSize.size()]);
	    } else {
		writeDimArray = new AttributeDim[readDimArray.length];
		Arrays.fill(writeDimArray, new AttributeDim(0, 0));
		writeDimSizeArray = Arrays.copyOf(readDimSizeArray, readDimSizeArray.length);
	    }
	    final EltInArray[] errorsArrayHist = errorsArray.toArray(new EltInArray[errorsArray.size()]);
	    final DevError[][] errorsHist = errors.toArray(new DevError[0][0]);

	    values = CleverAnyAttribute.set(tangoType, array);

	    result = new DevAttrHistory_4(attributeName, times, values, qualitiesArray, qualSizeArray, readDimArray,
		    readDimSizeArray, writeDimArray, writeDimSizeArray, errorsHist, errorsArrayHist);
	} else {
	    result = new DevAttrHistory_4(attributeName, new TimeVal[0], ORBManager.createAny(), new AttrQuality[0],
		    new EltInArray[0], new AttributeDim[0], new EltInArray[0], new AttributeDim[0], new EltInArray[0],
		    new DevError[][] {}, new EltInArray[] {});
	}
	return result;
    }

    private Object fillHistoryArray(final HistoryItem[] returnedhistoryArray, final TimeVal[] times,
	    final Deque<AttrQuality> qualities, final Deque<EltInArray> qualSize, final Deque<AttributeDim> readDim,
	    final Deque<EltInArray> readDimSize, final Deque<AttributeDim> writeDim,
	    final Deque<EltInArray> writeDimSize, final Deque<DevError[]> errors, final Deque<EltInArray> errorsArray) {
	Object array = null;
	int i = 0;
	// stack all history information
	for (final HistoryItem item : returnedhistoryArray) {
	    times[i] = TangoIDLUtil.getTime(item.getReadValue().getTime());
	    if (item.getError() != null && item.getError().length != 0) {
		errors.add(item.getError());
		errorsArray.add(new EltInArray(i, 1));
	    } else {
		// write value
		if (item.getWriteValue() != null && item.getWriteValue().getValue() != null && isReadWrite) {
		    array = org.tango.utils.ArrayUtils.addAll(item.getWriteValue().getValue(), array);
		}
		// read value
		if (item.getReadValue().getValue() != null) {
		    array = org.tango.utils.ArrayUtils.addAll(item.getReadValue().getValue(), array);
		}
	    }
	    i++;

	    // qualities
	    if (qualities.isEmpty() || !qualities.isEmpty() && item.getReadValue().getQuality() != qualities.getLast()) {
		qualSize.add(new EltInArray(i - 1, 1));
		qualities.add(item.getReadValue().getQuality());
	    } else {
		qualSize.getLast().nb_elt++;
		qualSize.getLast().start++;
	    }
	    // readDim
	    if (readDim.isEmpty()
		    || !readDim.isEmpty()
		    && (item.getReadValue().getXDim() != readDim.getLast().dim_x || item.getReadValue().getYDim() != readDim
			    .getLast().dim_y)) {
		readDimSize.add(new EltInArray(i - 1, 1));
		readDim.add(new AttributeDim(item.getReadValue().getXDim(), item.getReadValue().getYDim()));
	    } else {
		readDimSize.getLast().nb_elt++;
		readDimSize.getLast().start++;
	    }
	    // writeDim
	    if (isReadWrite) {
		if (writeDim.isEmpty()
			|| !writeDim.isEmpty()
			&& (item.getWriteValue().getXDim() != writeDim.getLast().dim_x || item.getWriteValue()
				.getYDim() != writeDim.getLast().dim_y)) {
		    writeDimSize.add(new EltInArray(i - 1, 1));
		    writeDim.add(new AttributeDim(item.getWriteValue().getXDim(), item.getWriteValue().getYDim()));
		} else {
		    writeDimSize.getLast().nb_elt++;
		    writeDimSize.getLast().start++;
		}
	    }
	}
	return array;
    }

    private HistoryItem[] createHistoryArray(final int maxSize, final HistoryItem[] historyArray) {
	HistoryItem[] returnedhistoryArray;
	if (valueHistory.size() > maxSize) {
	    returnedhistoryArray = new HistoryItem[maxSize];
	    System.arraycopy(historyArray, historyArray.length - maxSize, returnedhistoryArray, 0,
		    returnedhistoryArray.length);
	} else {
	    returnedhistoryArray = historyArray;
	}
	return returnedhistoryArray;
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
