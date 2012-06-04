//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012
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
// $Revision$
//
//-======================================================================


package fr.esrf.TangoApi.events;


import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.DevError;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * @author pascal_verdier
 */
public class EventData implements java.io.Serializable {
    public DeviceProxy device;
    public String name;
    public String event;
    public int event_type;
    public int event_source;
    public DeviceAttribute attr_value;
    public AttributeInfoEx attr_config;
    public AttDataReady data_ready;
    public DevError[] errors;
    public boolean err;
    public long date;

    public static final int    ZMQ_EVENT    = 0;
    public static final int    NOTIFD_EVENT = 1;
    //-=============================================
    /*
     * Creates a new instance of EventData
     */
    //-=============================================
    public EventData(
                DeviceProxy device,
                String name,
                String event,
                int event_type,
                int event_source,
                DeviceAttribute attr_value,
                AttributeInfoEx attr_config,
                AttDataReady data_ready,
                DevError[] errors) {
        this.device = device;
        this.name = name;
        this.event = event;
        this.event_type = event_type;
        this.event_source = event_source;
        this.attr_value = attr_value;
        this.attr_config = attr_config;
        this.data_ready = data_ready;
        this.errors = errors;
        err = (errors != null);
        date = System.currentTimeMillis();
    }

    //-=============================================
    /**
     * @return true if event is NOT TangoConst.ATT_CONF_EVENT
     */
    //-=============================================
    @SuppressWarnings({"UnusedDeclaration"})
    public boolean isAttrValue() {
        return (attr_value != null);
    }
    //-=============================================
    /**
     * @return true if event is TangoConst.ATT_CONF_EVENT
     */
    //-=============================================
    @SuppressWarnings({"UnusedDeclaration"})
    public boolean isAttrConfig() {
        return (attr_config != null);
    }
    //-=============================================
    /**
     * @return true if event is TangoConst.DATA_READY_EVENT
     */
    //-=============================================
    @SuppressWarnings({"UnusedDeclaration"})
    public boolean isAttrDataReady() {
        return (data_ready != null);
    }
}
