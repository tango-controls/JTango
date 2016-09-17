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
// $Revision: 19132 $
//
//-======================================================================


package fr.esrf.TangoApi.events;

import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * @author pascal_verdier
 */
public class EventCallBackStruct implements java.io.Serializable {

    public DeviceProxy device;
    public String attr_name;
    public String event_name;
    public String channel_name;
    public String filter_constraint;
    public CallBack callback;
    public int max_size;
    public long last_subscribed;
    public int event_type;
    public int id;
    public int filter_id;
    public String[] filters;
    public boolean filter_ok;
    public boolean use_ev_queue;
    public EventConsumer    consumer;
    private long zmqCounter = Long.MAX_VALUE;
    private boolean synchronousDone = false;

    //-======================================================================
    /*
     * Creates a new instance of EventCallBackStruct
     */
    //=======================================================================
    public EventCallBackStruct(DeviceProxy device,
                               String attr_name,
                               String event_name,
                               String channel_name,
                               CallBack callback,
                               int max_size,
                               int id,
                               int event_type,
                               String[] filters,
                               boolean filter_ok) {
        this.device = device;
        this.attr_name = attr_name;
        this.event_name = event_name;
        this.channel_name = channel_name;
        this.callback = callback;
        this.max_size = max_size;
        this.event_type = event_type;
        this.id = id;
        this.filters = filters;
        this.filter_ok = filter_ok;
        this.use_ev_queue = (callback == null);
    }
    //-======================================================================
    /*
     * Creates a new instance of EventCallBackStruct
     * For Interface change event no attribute and no filters
     */
    //=======================================================================
    public EventCallBackStruct(DeviceProxy device,
                               String event_name,
                               String channel_name,
                               CallBack callback,
                               int max_size,
                               int id,
                               int event_type,
                               boolean filter_ok) {
        this.device = device;
        this.event_name = event_name;
        this.channel_name = channel_name;
        this.callback = callback;
        this.max_size = max_size;
        this.event_type = event_type;
        this.id = id;
        this.filter_ok = filter_ok;
        this.use_ev_queue = (callback == null);
    }
    //=======================================================================
    //=======================================================================
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("device       = ").append(device.name()).append('\n');
        sb.append("attr_name    = ").append(attr_name).append('\n');
        sb.append("event_name   = ").append(event_name).append('\n');
        sb.append("channel_name = ").append(channel_name).append('\n');
        sb.append("callback     = ").append(callback).append('\n');
        sb.append("max_size     = ").append(max_size).append('\n');
        sb.append("event_type   = ").append(event_type).append('\n');
        sb.append("id           = ").append(id).append('\n');
        sb.append("filter_ok    = ").append(filter_ok).append('\n');
        sb.append("use_ev_queue = ").append(use_ev_queue).append('\n');
        return sb.toString();
    }
    //=======================================================================
    //=======================================================================
    public long getZmqCounter() {
        return zmqCounter;
    }
    //=======================================================================
    //=======================================================================
    public void setZmqCounter(long zmqCounter) {
        this.zmqCounter = zmqCounter;
    }
    //=======================================================================
    //=======================================================================
    public boolean isSynchronousDone() {
        return synchronousDone;
    }
    //=======================================================================
    //=======================================================================
    public void setSynchronousDone(boolean synchronousDone) {
        this.synchronousDone = synchronousDone;
        if (!synchronousDone)   //  When start synchronous, it is a reconnection.
            zmqCounter = Long.MAX_VALUE;    //  reset the event counter

    }
    //=======================================================================
    //=======================================================================
}
