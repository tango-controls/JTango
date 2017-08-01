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
// $Revision: 25898 $
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
public class TangoArchive extends EventDispatcher implements java.io.Serializable {

    //=======================================================================
    /**
     * Creates a new instance of TangoArchive
     *
     * @param device_proxy device proxy object.
     * @param attr_name    attribute name.
     * @param filters      filter array
     */
    //=======================================================================
    public TangoArchive(DeviceProxy device_proxy, String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }

    //=======================================================================
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, boolean stateless)
            throws DevFailed {
        event_listeners.add(ITangoArchiveListener.class, listener);
        event_identifier = subscribe_archive_event(attr_name, filters, stateless);
    }

    //=======================================================================
    //=======================================================================
    public void removeTangoArchiveListener(ITangoArchiveListener listener)
            throws DevFailed {
        event_listeners.remove(ITangoArchiveListener.class, listener);
        if (event_listeners.size() == 0)
            unsubscribe_event(event_identifier);
    }

    //=======================================================================
    //=======================================================================
    public void dispatch_event(final EventData eventData) {
        final TangoArchive tangoArchive = this;
        if (EventUtil.graphicAvailable()) {
            //   Causes doRun.run() to be executed asynchronously
            //      on the AWT event dispatching thread.
            Runnable do_work_later = new Runnable() {
                public void run() {
                    fireTangoArchiveEvent(tangoArchive, eventData);
                }
            };
            SwingUtilities.invokeLater(do_work_later);
        }
        else
            fireTangoArchiveEvent(tangoArchive, eventData);
    }


    //=======================================================================
    //=======================================================================
    private void fireTangoArchiveEvent(TangoArchive tangoArchive, EventData eventData) {
        TangoArchiveEvent archiveEvent = new TangoArchiveEvent(tangoArchive, eventData);
        // Notifying those that are interested in this event
        ArrayList<EventListener> listeners = event_listeners.getListeners(ITangoArchiveListener.class);
        for (EventListener eventListener : listeners) {
            ((ITangoArchiveListener) eventListener).archive(archiveEvent);
        }
    }
    //=======================================================================
    //=======================================================================

    String attr_name;
    int event_identifier;
    String[] filters;
}
