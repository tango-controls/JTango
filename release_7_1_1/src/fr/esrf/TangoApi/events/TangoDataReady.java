//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Log$
// 
//-======================================================================

package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import javax.swing.*;

/**
 *
 * @author  pascal_verdier
 */
public class TangoDataReady extends EventDispatcher implements java.io.Serializable {
    
	//==============================================================
    /**
	 *	Creates a new instance of AttrDataReadyChange
	 */
	//==============================================================
    public TangoDataReady(DeviceProxy device_proxy, String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }
    
	//==============================================================
	//==============================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, boolean stateless)
                throws DevFailed
    {
		event_listeners.add(ITangoDataReadyListener.class, listener);
        event_identifier = subscribe_data_ready_event(attr_name, filters, stateless);
    }
    
	//==============================================================
	//==============================================================
    public void removeTangoDataReadyListener(ITangoDataReadyListener listener) 
                throws DevFailed
    {
        event_listeners.remove(ITangoDataReadyListener.class,listener);
        if ( event_listeners.getListenerCount() == 0 )
           unsubscribe_event(event_identifier);

    }
	//==============================================================
	//==============================================================
	public void dispatch_event(final EventData event_data) {
		final TangoDataReady tg = this;
		Runnable do_work_later = new Runnable() {
	        public void run() {
				TangoDataReadyEvent data_ready_event = new TangoDataReadyEvent(tg, event_data);
				fireTangoDataReadyEvent(data_ready_event);
			}
		};
	    SwingUtilities.invokeLater(do_work_later);
    }

	//==============================================================
	//==============================================================
    private void fireTangoDataReadyEvent(TangoDataReadyEvent data_ready_event) {
        // Guaranteed to return a non null array
        Object [] listeners = event_listeners.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2 ; i>=0 ; i-=2 ) {
            if (listeners[i] == ITangoDataReadyListener.class) {
                ((ITangoDataReadyListener)listeners[i+1]).data_ready(data_ready_event);
            }
        }
    }
    
	//==============================================================
	//==============================================================

    String		attr_name;
    int			event_identifier;
    String[]	filters;
}
