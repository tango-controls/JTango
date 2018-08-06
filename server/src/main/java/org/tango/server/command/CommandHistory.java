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
package org.tango.server.command;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.orb.ORBManager;
import org.tango.server.Constants;
import org.tango.server.idl.CleverAnyCommand;
import org.tango.server.idl.TangoIDLUtil;

import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.DevCmdHistory_4;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.EltInArray;
import fr.esrf.Tango.TimeVal;
import fr.esrf.TangoDs.TangoConst;

public final class CommandHistory {

    private final Logger logger = LoggerFactory.getLogger(CommandHistory.class);
    /**
     * Utility to get array type of a scalar type
     */
    private static final Map<Integer, Integer> SCALAR_TO_ARRAY = new HashMap<Integer, Integer>();
    static {
	// no type of boolean array!
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_BOOLEAN, TangoConst.Tango_DEV_BOOLEAN);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_SHORT, TangoConst.Tango_DEVVAR_SHORTARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_LONG, TangoConst.Tango_DEVVAR_LONGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_FLOAT, TangoConst.Tango_DEVVAR_FLOATARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_DOUBLE, TangoConst.Tango_DEVVAR_DOUBLEARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_USHORT, TangoConst.Tango_DEVVAR_USHORTARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_ULONG, TangoConst.Tango_DEVVAR_ULONGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_STRING, TangoConst.Tango_DEVVAR_STRINGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_LONG64, TangoConst.Tango_DEVVAR_LONG64ARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_ULONG64, TangoConst.Tango_DEVVAR_ULONG64ARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_CHAR, TangoConst.Tango_DEVVAR_CHARARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_LONGSTRINGARRAY, TangoConst.Tango_DEVVAR_LONGSTRINGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY, TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_STATE, TangoConst.Tango_DEV_STATE);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_CONST_DEV_STRING, TangoConst.Tango_CONST_DEV_STRING);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_UCHAR, TangoConst.Tango_DEV_UCHAR);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_INT, TangoConst.Tango_DEV_INT);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEV_ENCODED, TangoConst.Tango_DEV_ENCODED);

	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_SHORTARRAY, TangoConst.Tango_DEVVAR_SHORTARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_LONGARRAY, TangoConst.Tango_DEVVAR_LONGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_FLOATARRAY, TangoConst.Tango_DEVVAR_FLOATARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_DOUBLEARRAY, TangoConst.Tango_DEVVAR_DOUBLEARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_USHORTARRAY, TangoConst.Tango_DEVVAR_USHORTARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_ULONGARRAY, TangoConst.Tango_DEVVAR_ULONGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_STRINGARRAY, TangoConst.Tango_DEVVAR_STRINGARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_LONG64ARRAY, TangoConst.Tango_DEVVAR_LONG64ARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_ULONG64ARRAY, TangoConst.Tango_DEVVAR_ULONG64ARRAY);
	SCALAR_TO_ARRAY.put(TangoConst.Tango_DEVVAR_CHARARRAY, TangoConst.Tango_DEVVAR_CHARARRAY);
    }

    private static class HistoryItem {
	private final TimeVal date;
	private final Object value;
	private final DevError[] error;

	public HistoryItem(final Object value, final long date, final DevError[] error) throws DevFailed {
	    this.value = value;
	    this.date = TangoIDLUtil.getTime(date);
	    this.error = Arrays.copyOf(error, error.length);
	}

	public TimeVal getDate() {
	    return date;
	}

	public Object getValue() {
	    return value;
	}

	public DevError[] getError() {
	    return error;
	}
    }

    private final Deque<HistoryItem> commandHistory = new ArrayDeque<HistoryItem>(Constants.QUEUE_CAPACITY);

    private final int type;
    private int maxSize = 10;

    public CommandHistory(final int type) {
	this.type = type;
    }

    public void setMaxSize(final int maxSize) {
	this.maxSize = maxSize;
    }

    public synchronized void addToHistory(final Object value, final DevError[] error) throws DevFailed {
	while (commandHistory.size() >= maxSize - 1) {
	    commandHistory.poll();
	}
	final boolean isInserted = commandHistory.offer(new HistoryItem(value, System.currentTimeMillis(), error));
	if (!isInserted) {
	    logger.debug("{} not inserted in command history queue ", value);
	}
    }

    public synchronized int size() {
	return commandHistory.size();
    }

    public synchronized void clear() {
	commandHistory.clear();
    }

    public synchronized DevCmdHistory_4 toDevCmdHistory4(final int maxSize) throws DevFailed {

	final DevCmdHistory_4 history = new DevCmdHistory_4();
	if (!commandHistory.isEmpty()) {
	    final HistoryItem[] returnedhistoryArray = createHistoryArray(maxSize);
	    final TimeVal[] times = new TimeVal[returnedhistoryArray.length];

	    final LinkedList<AttributeDim> dim = new LinkedList<AttributeDim>();
	    final LinkedList<EltInArray> dimSize = new LinkedList<EltInArray>();
	    final LinkedList<DevError[]> errors = new LinkedList<DevError[]>();
	    final LinkedList<EltInArray> errorsArray = new LinkedList<EltInArray>();
	    final Object array = fillHistoryArray(returnedhistoryArray, times, dim, dimSize, errors, errorsArray);
	    history.errors_array = errorsArray.toArray(new EltInArray[0]);
	    history.errors = errors.toArray(new DevError[0][0]);
	    history.cmd_type = type;
	    history.dates = times;
	    history.dims = dim.toArray(new AttributeDim[0]);
	    history.dims_array = dimSize.toArray(new EltInArray[0]);
	    if (type != TangoConst.Tango_DEV_VOID) {
		history.value = CleverAnyCommand.set(SCALAR_TO_ARRAY.get(type), array);
	    } else {
		history.value = ORBManager.createAny();
	    }
	} else {
	    history.errors_array = new EltInArray[0];
	    history.errors = new DevError[0][0];
	    history.cmd_type = type;
	    history.dates = new TimeVal[0];
	    history.dims = new AttributeDim[0];
	    history.dims_array = new EltInArray[0];
	    history.value = ORBManager.createAny();
	}
	return history;
    }

    private Object fillHistoryArray(final HistoryItem[] returnedhistoryArray, final TimeVal[] times,
	    final Deque<AttributeDim> dim, final Deque<EltInArray> dimSize, final Deque<DevError[]> errors,
	    final Deque<EltInArray> errorsArray) {
	int i = 0;
	Object array = null;
	for (final HistoryItem item : returnedhistoryArray) {
	    times[i++] = item.getDate();
	    final Object value = item.getValue();

	    int dimX = 1;
	    if (value == null) {
		dimX = 0;
	    } else if (value.getClass().isArray()) {
		dimX = Array.getLength(value);
		array = org.tango.utils.ArrayUtils.addAll(value, array);
	    } else {
		array = org.tango.utils.ArrayUtils.addAll(value, array);
	    }

	    if (item.getError() != null && item.getError().length != 0) {
		errors.add(item.getError());
		errorsArray.add(new EltInArray(i - 1, 1));
	    }
	    // dim - each dim contains the number of elements that have the same size
	    if (dim.isEmpty() || !dim.isEmpty() && dimX != dim.getLast().dim_x) {
		// the size has changed so create a new AttributeDim
		dimSize.add(new EltInArray(i - 1, 1));
		dim.add(new AttributeDim(dimX, 1));
	    } else {
		dimSize.getLast().nb_elt++;
		// start is the idx lastest
		dimSize.getLast().start++;
	    }
	}
	return array;
    }

    private HistoryItem[] createHistoryArray(final int maxSize) {
	final HistoryItem[] historyArray = Collections.asLifoQueue(commandHistory).toArray(
		new HistoryItem[commandHistory.size()]);
	HistoryItem[] returnedhistoryArray;
	if (commandHistory.size() > maxSize) {
	    returnedhistoryArray = new HistoryItem[maxSize];
	    System.arraycopy(historyArray, historyArray.length - maxSize, returnedhistoryArray, 0,
		    returnedhistoryArray.length);
	} else {
	    returnedhistoryArray = historyArray;
	}
	return returnedhistoryArray;
    }

    public int getMaxSize() {
	return maxSize;
    }
}
