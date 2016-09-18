//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 25296 $
//
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.TangoConst;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class is an object returned by a read pipe or used to write a pipe.
 * It contains an object name, a read time and a PipeBlob containing data.
 */

//@NotThreadSafe
public class DevicePipe implements PipeScanner {
    private static final DevError[] DEV_ERRORS = new DevError[0];
    private final int size;
    private final AtomicInteger ndx = new AtomicInteger(0);
    private String pipeName = "";
    // ===================================================================
    private TimeVal timeVal;
    // ===================================================================
    private PipeBlob pipeBlob;
    /**
     * Create a DevicePipe object
     * @param pipeName the pipe name
     * @param pipeBlob the data to be transferred
     */
    // ===================================================================
    public DevicePipe(String pipeName, PipeBlob pipeBlob) {
        this.pipeName = pipeName;
        long t = System.currentTimeMillis();
        int sec = (int) (t / 1000);
        int usec = (int) (t - (1000 * sec)) * 1000;
        this.timeVal = new TimeVal(sec, usec, 0);
        this.pipeBlob = pipeBlob;
        size = pipeBlob.size();
    }
    // ===================================================================
    /**
     * Create a DevicePipe object
     * @param pipeData the IDL Object
     */
    // ===================================================================
    public DevicePipe(DevPipeData pipeData) {
        this.pipeName = pipeData.name;
        this.timeVal = pipeData.time;
        this.pipeBlob = new PipeBlob(pipeData.data_blob);
        size = this.pipeBlob.size();
    }
    // ===================================================================

    // ===================================================================
    // ===================================================================
    public DevPipeData getDevPipeDataObject() {
        DevPipeBlob devPipeBlob = pipeBlob.getDevPipeBlobObject();
        return new DevPipeData(pipeName, timeVal, devPipeBlob);
    }
    // ===================================================================

    /**
     * @return return pipe name
     */
    // ===================================================================
    public String getPipeName() {
        return pipeName;
    }
    // ===================================================================

    /**
     * Set pipe name
     * @param pipeName pipe name
     */
    // ===================================================================
    public void setPipeName(String pipeName) {
        this.pipeName = pipeName;
    }
    // ===================================================================

    /**
     * @return return pipe blob
     */
    // ===================================================================
    public PipeBlob getPipeBlob() {
        return pipeBlob;
    }
    // ===================================================================

    /**
     * Set pipe blob
     * @param pipeBlob pipe blob
     */
    // ===================================================================
    public void setPipeBlob(PipeBlob pipeBlob) {
        this.pipeBlob = pipeBlob;
    }
    // ===================================================================

    /**
     * @return pipe time stamp
     */
    // ===================================================================
    public TimeVal getTimeVal() {
        return timeVal;
    }
    // ===========================================

    /**
     * Set pipe time
     *
     * @param timeVal pipe time
     */
    // ===================================================================
    public void setTimeVal(TimeVal timeVal) {
        this.timeVal = timeVal;
    }
    // ===========================================

    /**
     * Set pipe time
     * @param t pipe time (number of milli seconds since EPOCH)
     */
    // ===================================================================
    public void setTimeVal(long t) {
        int seconds = (int) (t / 1000);
        int millis = (int) (t - 1000 * t);
        this.timeVal = new TimeVal(seconds, millis * 1000, 0);
    }

    /**
     * Return attribute time value in seconds since EPOCH.
     *
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValSec() throws DevFailed {
        return (long) timeVal.tv_sec;
    }

    /**
     * Return attribute time value in milli seconds since EPOCH.
     *
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValMillisSec() throws DevFailed {
        return (long)timeVal.tv_sec*1000 + timeVal.tv_usec/1000;
    }

    @Override
    public boolean hasNext() {
        return ndx.get() < size;
    }

    @Override
    public PipeScanner move() {
        ndx.incrementAndGet();
        return this;
    }

    @Override
    public PipeScanner advance(int steps) {
        if(ndx.addAndGet(steps) >= size) throw new IllegalArgumentException("Can not advance by " + steps + ": exceeds size of " + size);
        return this;
    }

    @Override
    public PipeScanner reset() {
        ndx.set(0);
        return this;
    }

    @Override
    public boolean nextBoolean() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != boolean.class) throw new DevFailed("Wrong type! Expected boolean, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getBoolean(array, 0);
    }

    @Override
    public byte nextByte() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != byte.class) throw new DevFailed("Wrong type! Expected byte, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getByte(array, 0);
    }

    @Override
    public char nextChar() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != char.class) throw new DevFailed("Wrong type! Expected char, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getChar(array, 0);
    }

    @Override
    public short nextShort() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != short.class) throw new DevFailed("Wrong type! Expected short, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getShort(array, 0);
    }

    @Override
    public int nextInt() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != int.class) throw new DevFailed("Wrong type! Expected int, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getInt(array, 0);
    }

    @Override
    public long nextLong() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != long.class) throw new DevFailed("Wrong type! Expected long, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getLong(array,0);
    }

    @Override
    public float nextFloat() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != float.class) throw new DevFailed("Wrong type! Expected float, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getFloat(array,0);
    }

    @Override
    public double nextDouble() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != double.class) throw new DevFailed("Wrong type! Expected double, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return Array.getDouble(array,0);
    }

    @Override
    public String nextString() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != String.class) throw new DevFailed("Wrong type! Expected String, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return String.class.cast(Array.get(array, 0));
    }

    @Override
    public DevState nextState() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != DevState.class) throw new DevFailed("Wrong type! Expected DevState, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return DevState.class.cast(Array.get(array, 0));
    }

    @Override
    public DevEncoded nextEncoded() throws DevFailed {
        Object array = nextArray();
        if(array.getClass().getComponentType() != DevEncoded.class) throw new DevFailed("Wrong type! Expected DevEncoded, but was " +  array.getClass().getComponentType().getSimpleName(),DEV_ERRORS);
        return DevEncoded.class.cast(Array.get(array, 0));
    }

    @Override
    public PipeScanner nextScanner() throws DevFailed{
        PipeBlob blob = getPipeBlob();
        PipeDataElement el = blob.get(ndx.getAndIncrement());
        if(el.getType() != TangoConst.Tango_DEV_PIPE_BLOB) throw new DevFailed("Wrong type! Expected PipeBlob, but was " +  TangoConst.Tango_CmdArgTypeName[el.getType()], DEV_ERRORS);
        return new DevicePipe(pipeName,el.extractPipeBlob());
    }



    @Override
    public <T> void nextArray(T[] target, int size) throws DevFailed {
        Object array = nextArray();
        if(Array.getLength(array) != size) throw new DevFailed("size is not equal to array's length: " + size + "!=" +Array.getLength(array),DEV_ERRORS);
        if(target.getClass().getComponentType() != array.getClass().getComponentType()) throw new DevFailed("target array type " + target.getClass().getComponentType() + " does not match underlying array type " + array.getClass().getComponentType(), new DevError[0]);
        System.arraycopy(array,0,target,0,size);
    }

    @Override
    public void nextArray(Object target, int size) throws DevFailed {
        Object array = nextArray();
        if(Array.getLength(array) != size) throw new DevFailed("size is not equal to array's length: " + size + "!=" +Array.getLength(array),DEV_ERRORS);
        if(target.getClass().getComponentType() != array.getClass().getComponentType()) throw new DevFailed("target array type " + target.getClass().getComponentType() + " does not match underlying array type " + array.getClass().getComponentType(),DEV_ERRORS);
        System.arraycopy(array,0,target,0,size);
    }

    public Object nextArray() throws DevFailed {
        if(!hasNext()) throw new DevFailed("EOF pipe has reached!",DEV_ERRORS);
        PipeDataElement el = getPipeBlob().get(ndx.getAndIncrement());
        switch(el.getType()) {
            case TangoConst.Tango_DEV_PIPE_BLOB:
                throw new DevFailed("Unexpected state! Blobs are not welcome here...",DEV_ERRORS);
            case TangoConst.Tango_DEV_BOOLEAN:
                return el.extractBooleanArray();
            case TangoConst.Tango_DEV_CHAR:
                return el.extractCharArray();
            case TangoConst.Tango_DEV_UCHAR:
                return el.extractUCharArray();
            case TangoConst.Tango_DEV_SHORT:
                return el.extractShortArray();
            case TangoConst.Tango_DEV_USHORT:
                return el.extractUShortArray();
            case TangoConst.Tango_DEV_LONG:
                return el.extractLongArray();
            case TangoConst.Tango_DEV_ULONG:
                return el.extractULongArray();
            case TangoConst.Tango_DEV_LONG64:
                return el.extractLong64Array();
            case TangoConst.Tango_DEV_DOUBLE:
                return el.extractDoubleArray();
            case TangoConst.Tango_DEV_FLOAT:
                return el.extractFloatArray();
            case TangoConst.Tango_DEV_STRING:
                return el.extractStringArray();
            case TangoConst.Tango_DEV_STATE:
                return el.extractDevStateArray();
            case TangoConst.Tango_DEV_ENCODED:
                return el.extractDevEncodedArray();
        }
        throw new AssertionError("Unreachable statement");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T nextArray(Class<T> type) throws DevFailed {
        if (!type.isArray()) throw new DevFailed("Target type is not an array type!", DEV_ERRORS);
        Object result = nextArray();
        Class<?> componentType = result.getClass().getComponentType();
        Class<?> targetComponentType = type.getComponentType();
        if (componentType != targetComponentType)
            throw new DevFailed("Underlying array's component type[" + componentType.getSimpleName() + "] is not " + targetComponentType.getSimpleName(), DEV_ERRORS);
        return (T) result;//if
    }

    // ===================================================================
    // ===================================================================







    // ===================================================================
    /**
     * @return the number of data elements in root blob
     *
    // ===================================================================
    public int getDataElementNumber() {
        return pipeBlob.getDataElementNumber();
    }
    // ===================================================================
    /**
     * @param index specified data element index.
     * @return the name of data element at index
     * @throws DevFailed if index is negative or higher than data element number.
     *
    // ===================================================================
    public String getDataElementName(int index) throws DevFailed {
        return pipeBlob.getDataElementName(index);
    }
    // ===================================================================
    /**
     * @param index specified data element index.
     * @return the tye of DataElement at index
     * @throws DevFailed if index is negative or higher than data element number.
     *
    // ===================================================================
    public int getDataElementType(int index) throws DevFailed {
        return pipeBlob.getDataElementType(index);
    }
    // ===================================================================
    /**
     * @param name specified data element name.
     * @return the tye of DataElement for specified name
     * @throws DevFailed if name not found in data element list.
     *
    // ===================================================================
    public int getDataElementType(String name) throws DevFailed {
        return pipeBlob.getDataElementType(name);
    }
    // ===================================================================
    // ===================================================================
    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            long t = (long)timeVal.tv_sec*1000;
            sb.append(name).append(":  ").append(new Date(t)).append("\n");
            for (int i=0 ; i<getDataElementNumber() ; i++) {
                int type = pipeBlob.getDataElementType(i);
                sb.append("\t").append(pipeBlob.getDataElementName(i)).append(": ").
                        append(TangoConst.Tango_CmdArgTypeName[type]).append("\n");
            }
            return sb.toString();
        }
        catch (DevFailed e) {
            return e.errors[0].desc;
        }
    }
    // ===================================================================
    // ===================================================================
    */
}
