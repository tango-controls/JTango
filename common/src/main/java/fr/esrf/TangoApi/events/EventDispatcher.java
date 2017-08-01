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
// $Revision: 28159 $
//
//-======================================================================


package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoDs.TangoConst;

/**
 * @author pascal_verdier
 */
public abstract class EventDispatcher extends CallBack
        implements TangoConst, java.io.Serializable {

    /**
     * Creates a new instance of EventDispatcher
     * @param device_proxy device object
     */
    public EventDispatcher(DeviceProxy device_proxy) {
        event_supplier = device_proxy;
        event_listeners = new TangoEventListenerList();
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public DeviceProxy getEventSupplier() {
        return event_supplier;
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_pipe_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                PIPE_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_periodic_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                PERIODIC_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_change_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                CHANGE_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_quality_change_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                QUALITY_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_archive_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                ARCHIVE_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_user_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                USER_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_att_config_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                ATT_CONF_EVENT, this, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    protected int subscribe_data_ready_event(String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(attr_name,
                DATA_READY_EVENT, this, filters, stateless);
    }
    //=======================================================================
    //=======================================================================
    protected int subscribe_interface_change_event(boolean stateless)
            throws DevFailed {
        return event_supplier.subscribe_event(INTERFACE_CHANGE, this, stateless);
    }

    //=======================================================================
    //=======================================================================
    public void unsubscribe_event(int event_id)
            throws DevFailed {
        event_supplier.unsubscribe_event(event_id);
    }

    //=======================================================================
    //=======================================================================
    public void push_event(EventData event_data) {
        dispatch_event(event_data);
    }


    public abstract void dispatch_event(EventData event_data);

    //protected EventListenerList event_listeners = null;
    protected TangoEventListenerList event_listeners = null;
    protected DeviceProxy event_supplier = null;

}
