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


package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import javax.swing.*;
import java.util.ArrayList;
import java.util.EventListener;


/**
 * @author pascal_verdier
 */
public class TangoInterfaceChange extends EventDispatcher implements java.io.Serializable {

    int eventIdentifier;

    //=======================================================================
    /**
     * Creates a new instance of TangoInterfaceChange
     *
     * @param device_proxy device proxy object.
     */
    //=======================================================================
    public TangoInterfaceChange(DeviceProxy device_proxy) {
        super(device_proxy);
        eventIdentifier = -1;
    }

    //=======================================================================
    //=======================================================================
    public void addTangoInterfaceChangeListener(ITangoInterfaceChangeListener listener, boolean stateless)
            throws DevFailed {
        event_listeners.add(ITangoInterfaceChangeListener.class, listener);
        eventIdentifier = subscribe_interface_change_event(stateless);
    }

    //=======================================================================
    //=======================================================================
    public void removeTangoInterfaceChangeListener(ITangoInterfaceChangeListener listener)
            throws DevFailed {
        event_listeners.remove(ITangoInterfaceChangeListener.class, listener);
        if (event_listeners.size() == 0)
            unsubscribe_event(eventIdentifier);
    }

    //=======================================================================
    //=======================================================================
    public void dispatch_event(final EventData eventData) {
        final TangoInterfaceChange interfaceChange = this;
        if (EventUtil.graphicAvailable()) {
            //   Causes doRun.run() to be executed asynchronously
            //      on the AWT event dispatching thread.
            Runnable do_work_later = new Runnable() {
                public void run() {
                    fireTangoInterfaceChangeEvent(interfaceChange, eventData);
                }
            };
            SwingUtilities.invokeLater(do_work_later);
        }
        else {
            fireTangoInterfaceChangeEvent(interfaceChange, eventData);
        }
    }

    //=======================================================================
    //=======================================================================
    private void fireTangoInterfaceChangeEvent(TangoInterfaceChange tangoChange, EventData eventData) {
        TangoInterfaceChangeEvent event = new TangoInterfaceChangeEvent(tangoChange, eventData);
        ArrayList<EventListener>    listeners = event_listeners.getListeners(ITangoInterfaceChangeListener.class);
        for (EventListener eventListener : listeners) {
            ((ITangoInterfaceChangeListener) eventListener).interface_change(event);
        }
    }
    //==============================================================
    //==============================================================
}
