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

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevPipeBlob;
import fr.esrf.Tango.DevPipeData;
import fr.esrf.Tango.TimeVal;


/**
 * This class is an object returned by a read pipe or used to write a pipe.
 * It contains an object name, a read time and a PipeBlob containing data.
 */


public class DevicePipe {
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
