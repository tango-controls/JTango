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

import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class is an object returned by a read pipe or used to write a pipe.
 * It contains an object name, a read time and a PipeBlob containing data.
 */

//@NotThreadSafe
public class DevicePipe implements PipeScanner {
    private String pipeName = "";
    private TimeVal  timeVal;
    private PipeBlob pipeBlob;
    // ===================================================================
    /**
     * Create a DevicePipe object
     * @param pipeName the pipe name
     * @param pipeBlob the data to be transferred
     */
    // ===================================================================
    public DevicePipe(String pipeName, PipeBlob pipeBlob) {
        this.pipeName = pipeName;
        long t = System.currentTimeMillis();
        int sec  = (int)(t/1000);
        int usec = (int)(t-(1000*sec))*1000;
        this.timeVal  = new TimeVal(sec, usec, 0);
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
        this.timeVal  = pipeData.time;
        this.pipeBlob = new PipeBlob(pipeData.data_blob);
        size = this.pipeBlob.size();
    }
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
    // ===================================================================
    /**
     * Set pipe time
     * @param timeVal pipe time
     */
    // ===================================================================
    public void setTimeVal(TimeVal timeVal) {
        this.timeVal = timeVal;
    }
    // ===================================================================
    /**
     * Set pipe time
     * @param t pipe time (number of milli seconds since EPOCH)
     */
    // ===================================================================
    public void setTimeVal(long t) {
        int seconds = (int) (t/1000);
        int millis  = (int) (t-1000*t);
        this.timeVal = new TimeVal(seconds, millis*1000, 0);
    }
    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     *
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValSec() throws DevFailed {
        return (long) timeVal.tv_sec;
    }
    // ===========================================
    /**
     * Return attribute time value in milli seconds since EPOCH.
     *
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValMillisSec() throws DevFailed {
        return (long)timeVal.tv_sec*1000 + timeVal.tv_usec/1000;
    }

    private final int size;
    private final AtomicInteger blobNdx = new AtomicInteger(0);
    private final AtomicInteger ndx = new AtomicInteger(0);

    @Override
    public boolean hasNext() {
        return ndx.get() < size;
    }

    @Override
    public boolean nextBoolean() throws DevFailed {
        boolean[] data = new boolean[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public byte nextByte() throws DevFailed {
        byte[] data = new byte[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public char nextChar() throws DevFailed {
        char[] data = new char[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public short nextShort() throws DevFailed {
        short[] data = new short[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public int nextInt() throws DevFailed {
        int[] data = new int[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public long nextLong() throws DevFailed {
        long[] data = new long[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public float nextFloat() throws DevFailed {
        float[] data = new float[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public double nextDouble() throws DevFailed {
        double[] data = new double[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public String nextString() throws DevFailed {
        String[] data = new String[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public DevState nextState() throws DevFailed {
        DevState[] data = new DevState[1];
        nextArray(data,1);
        return data[0];
    }

    @Override
    public DevEncoded nextEncoded() throws DevFailed {
        DevEncoded[] data = new DevEncoded[1];
        nextArray(data,1);
        return data[0];
    }

    private PipeBlob nextBlob() throws DevFailed{
        PipeBlob blob = getPipeBlob();
        if(blob.get(ndx.get()).getType() != TangoConst.Tango_DEV_PIPE_BLOB) return blob;
        //TODO
        return null;
    }

    @Override
    public <T> void nextArray(T[] target, int size) throws DevFailed {
        nextArray((Object) target, size);
    }

    private void nextArray(Object target, int size) throws DevFailed {
        if(!hasNext()) throw new IllegalStateException("EOF pipe has reached!");
        PipeDataElement el = nextBlob().get(ndx.getAndIncrement() - blobNdx.get());
        //TODO check type
        Object array = null;
        switch(el.getType()) {
            case TangoConst.Tango_DEV_PIPE_BLOB:
                throw new IllegalStateException("Unexpected state! Blobs are not welcome here...");
            case TangoConst.Tango_DEV_BOOLEAN:
                array = el.extractBooleanArray();
                break;
            case TangoConst.Tango_DEV_CHAR:
                array = el.extractCharArray();
                break;
            case TangoConst.Tango_DEV_UCHAR:
                array = el.extractUCharArray();
                break;
            case TangoConst.Tango_DEV_SHORT:
                array = el.extractShortArray();
                break;
            case TangoConst.Tango_DEV_USHORT:
                array = el.extractUShortArray();
                break;
            case TangoConst.Tango_DEV_LONG:
                array = el.extractLongArray();
                break;
            case TangoConst.Tango_DEV_ULONG:
                array = el.extractULongArray();
                break;
            case TangoConst.Tango_DEV_LONG64:
                array = el.extractLong64Array();
                break;
            case TangoConst.Tango_DEV_DOUBLE:
                array = el.extractDoubleArray();
                break;
            case TangoConst.Tango_DEV_FLOAT:
                array = el.extractFloatArray();
                break;
            case TangoConst.Tango_DEV_STRING:
                array = el.extractStringArray();
                break;
            case TangoConst.Tango_DEV_STATE:
                array = el.extractDevStateArray();
                break;
            case TangoConst.Tango_DEV_ENCODED:
                array = el.extractDevEncodedArray();
                break;
        }
        //TODO avoid copying?
        System.arraycopy(array,0,target,0,size);
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
