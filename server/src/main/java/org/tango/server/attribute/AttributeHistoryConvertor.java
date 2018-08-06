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

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

import org.omg.CORBA.Any;
import org.tango.orb.ORBManager;
import org.tango.server.idl.CleverAnyAttribute;
import org.tango.server.idl.TangoIDLUtil;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.DevAttrHistory_4;
import fr.esrf.Tango.DevAttrHistory_5;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.EltInArray;
import fr.esrf.Tango.TimeVal;
import fr.esrf.TangoApi.DeviceDataHistory;

public class AttributeHistoryConvertor {
    private final Deque<HistoryItem> valueHistory;
    private TimeVal[] times;
    private AttrQuality[] qualitiesArray;
    private EltInArray[] qualSizeArray;
    private AttributeDim[] readDimArray;
    private EltInArray[] readDimSizeArray;
    private AttributeDim[] writeDimArray;
    private EltInArray[] writeDimSizeArray;
    private EltInArray[] errorsArrayHist;
    private DevError[][] errorsHist;
    private Any values;
    private final String attributeName;
    private final boolean isReadWrite;
    private final int tangoType;
    private final AttrDataFormat format;

    AttributeHistoryConvertor(final String attributeName, final Deque<HistoryItem> valueHistory, final int maxSize,
            final int tangoType, final AttrDataFormat format, final boolean isReadWrite) throws DevFailed {
        this.attributeName = attributeName;
        this.valueHistory = valueHistory;
        this.isReadWrite = isReadWrite;
        this.tangoType = tangoType;
        this.format = format;
        if (!valueHistory.isEmpty()) {
            final HistoryItem[] historyArray = Collections.asLifoQueue(valueHistory).toArray(
                    new HistoryItem[valueHistory.size()]);
            final HistoryItem[] returnedhistoryArray = createHistoryArray(maxSize, historyArray);
            times = new TimeVal[returnedhistoryArray.length];

            final Object array = fillHistoryArray(returnedhistoryArray);

            values = CleverAnyAttribute.set(tangoType, array);
        } else {
            createEmptyValues();
        }
    }

    public AttributeHistoryConvertor(final DeviceDataHistory[] attributeHistory, final boolean isReadWrite)
            throws DevFailed {
        this.isReadWrite = isReadWrite;
        valueHistory = null;
        if (attributeHistory.length > 0) {
            this.attributeName = attributeHistory[0].name;
            this.tangoType = attributeHistory[0].dataType;
            this.format = attributeHistory[0].dataFormat;
            values = attributeHistory[0].extractAny();
            readDimArray = new AttributeDim[attributeHistory.length];
            readDimSizeArray = new EltInArray[attributeHistory.length];
            writeDimArray = new AttributeDim[attributeHistory.length];
            writeDimSizeArray = new EltInArray[attributeHistory.length];
            errorsArrayHist = new EltInArray[attributeHistory.length];
            errorsHist = new DevError[attributeHistory.length][];
            times = new TimeVal[attributeHistory.length];
            for (int i = 0; i < attributeHistory.length; i++) {
                times[i] = attributeHistory[i].getTimeVal();
            }

            final LinkedList<AttrQuality> qualities = new LinkedList<AttrQuality>();
            final LinkedList<EltInArray> qualSize = new LinkedList<EltInArray>();
            final LinkedList<AttributeDim> readDim = new LinkedList<AttributeDim>();
            final LinkedList<EltInArray> readDimSize = new LinkedList<EltInArray>();
            final LinkedList<AttributeDim> writeDim = new LinkedList<AttributeDim>();
            final LinkedList<EltInArray> writeDimSize = new LinkedList<EltInArray>();
            final LinkedList<DevError[]> errors = new LinkedList<DevError[]>();
            final LinkedList<EltInArray> errorsArray = new LinkedList<EltInArray>();
            for (int i = 0; i < attributeHistory.length; i++) {
                // errors
                if (attributeHistory[i].hasFailed() && attributeHistory[i].errors.length != 0) {
                    errors.add(attributeHistory[i].errors);
                    errorsArray.add(new EltInArray(i, 1));
                }
                // qualities
                if (qualities.isEmpty() || !qualities.isEmpty()
                        && attributeHistory[i].getAttrQuality() != qualities.getLast()) {
                    qualSize.add(new EltInArray(i - 1, 1));
                    qualities.add(attributeHistory[i].getAttrQuality());
                } else {
                    qualSize.getLast().nb_elt++;
                    qualSize.getLast().start++;
                }
                // readDim
                if (readDim.isEmpty()
                        || !readDim.isEmpty()
                        && (attributeHistory[i].getDimX() != readDim.getLast().dim_x || attributeHistory[i].getDimY() != readDim
                                .getLast().dim_y)) {
                    readDimSize.add(new EltInArray(i - 1, 1));
                    readDim.add(new AttributeDim(attributeHistory[i].getDimX(), attributeHistory[i].getDimY()));
                } else {
                    readDimSize.getLast().nb_elt++;
                    readDimSize.getLast().start++;
                }
                // writeDim
                if (isReadWrite) {
                    if (writeDim.isEmpty()
                            || !writeDim.isEmpty()
                            && (attributeHistory[i].getWrittenDimX() != writeDim.getLast().dim_x || attributeHistory[i]
                                    .getWrittenDimY() != writeDim.getLast().dim_y)) {
                        writeDimSize.add(new EltInArray(i - 1, 1));
                        writeDim.add(new AttributeDim(attributeHistory[i].getWrittenDimX(), attributeHistory[i]
                                .getWrittenDimY()));
                    } else {
                        writeDimSize.getLast().nb_elt++;
                        writeDimSize.getLast().start++;
                    }
                }
            }
            errorsArrayHist = errorsArray.toArray(new EltInArray[0]);
            errorsHist = errors.toArray(new DevError[0][0]);

            readDimArray = readDim.toArray(new AttributeDim[0]);
            readDimSizeArray = readDimSize.toArray(new EltInArray[0]);

            qualitiesArray = qualities.toArray(new AttrQuality[0]);
            qualSizeArray = qualSize.toArray(new EltInArray[0]);

            if (isReadWrite) {
                writeDimArray = writeDim.toArray(new AttributeDim[0]);
                writeDimSizeArray = writeDimSize.toArray(new EltInArray[0]);
            } else {
                writeDimArray = new AttributeDim[readDimArray.length];
                Arrays.fill(writeDimArray, new AttributeDim(0, 0));
                writeDimSizeArray = Arrays.copyOf(readDimSizeArray, readDimSizeArray.length);
            }
        } else {
            this.attributeName = "";
            this.tangoType = 0;
            this.format = AttrDataFormat.FMT_UNKNOWN;
            createEmptyValues();
        }
    }

    private void createEmptyValues() throws DevFailed {
        qualitiesArray = new AttrQuality[0];
        qualSizeArray = new EltInArray[0];
        readDimArray = new AttributeDim[0];
        readDimSizeArray = new EltInArray[0];
        writeDimArray = new AttributeDim[0];
        writeDimSizeArray = new EltInArray[0];
        errorsArrayHist = new EltInArray[0];
        errorsHist = new DevError[][] {};
        times = new TimeVal[0];
        values = ORBManager.createAny();
    }

    public DevAttrHistory_4 getAttrHistory4() throws DevFailed {
        return new DevAttrHistory_4(attributeName, times, values, qualitiesArray, qualSizeArray, readDimArray,
                readDimSizeArray, writeDimArray, writeDimSizeArray, errorsHist, errorsArrayHist);
    }

    public DevAttrHistory_5 getAttrHistory5() throws DevFailed {
        return new DevAttrHistory_5(attributeName, format, tangoType, times, values, qualitiesArray, qualSizeArray,
                readDimArray, readDimSizeArray, writeDimArray, writeDimSizeArray, errorsHist, errorsArrayHist);
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

    private Object fillHistoryArray(final HistoryItem[] returnedhistoryArray) {
        Object array = null;
        int i = 0;
        final LinkedList<AttrQuality> qualities = new LinkedList<AttrQuality>();
        final LinkedList<EltInArray> qualSize = new LinkedList<EltInArray>();
        final LinkedList<AttributeDim> readDim = new LinkedList<AttributeDim>();
        final LinkedList<EltInArray> readDimSize = new LinkedList<EltInArray>();
        final LinkedList<AttributeDim> writeDim = new LinkedList<AttributeDim>();
        final LinkedList<EltInArray> writeDimSize = new LinkedList<EltInArray>();
        final LinkedList<DevError[]> errors = new LinkedList<DevError[]>();
        final LinkedList<EltInArray> errorsArray = new LinkedList<EltInArray>();
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
        qualitiesArray = qualities.toArray(new AttrQuality[0]);
        qualSizeArray = qualSize.toArray(new EltInArray[0]);
        readDimArray = readDim.toArray(new AttributeDim[0]);
        readDimSizeArray = readDimSize.toArray(new EltInArray[0]);

        if (isReadWrite) {
            writeDimArray = writeDim.toArray(new AttributeDim[0]);
            writeDimSizeArray = writeDimSize.toArray(new EltInArray[0]);
        } else {
            writeDimArray = new AttributeDim[readDimArray.length];
            Arrays.fill(writeDimArray, new AttributeDim(0, 0));
            writeDimSizeArray = Arrays.copyOf(readDimSizeArray, readDimSizeArray.length);
        }
        errorsArrayHist = errorsArray.toArray(new EltInArray[0]);
        errorsHist = errors.toArray(new DevError[0][0]);
        return array;
    }

}
