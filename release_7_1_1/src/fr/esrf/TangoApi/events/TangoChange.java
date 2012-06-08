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
// Revision 1.8  2008/10/10 11:34:15  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/04/16 12:58:29  pascal_verdier
// Event subscribtion stateless added.
//
// Revision 1.6  2008/04/11 08:08:45  pascal_verdier
// *** empty log message ***
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.5  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.4  2005/08/10 08:24:33  pascal_verdier
// Synchronized done by a global object.
//
// Revision 1.3  2004/07/06 09:22:58  pascal_verdier
// subscribe event is now thread safe.
// notify daemon reconnection works.
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//-======================================================================
/*
 * TangoChange.java
 *
 * Created on September 22, 2003, 3:36 PM
 */

package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import javax.swing.*;


/**
 *
 * @author  pascal_verdier
 */
public class TangoChange extends EventDispatcher implements java.io.Serializable {
    
    //=======================================================================
    /**
	 *	Creates a new instance of AttrAbsoluteChange
	 */
    //=======================================================================
    public TangoChange(DeviceProxy device_proxy , String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }
    
    //=======================================================================
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, boolean stateless)
                throws DevFailed
    {
		event_listeners.add(ITangoChangeListener.class, listener);
        event_identifier = subscribe_change_event(attr_name, filters, stateless);
    }
    
    //=======================================================================
    //=======================================================================
    public void removeTangoChangeListener(ITangoChangeListener listener) 
                throws DevFailed
    {
        event_listeners.remove(ITangoChangeListener.class,listener);
        if ( event_listeners.getListenerCount() == 0 )
           unsubscribe_event(event_identifier);
    }
    
    //=======================================================================
    //=======================================================================
	public void dispatch_event(final EventData event_data) {
		final TangoChange tg = this;
		Runnable do_work_later = new Runnable() {
	        public void run() {
				TangoChangeEvent change_event = new TangoChangeEvent(tg, event_data);
				fireTangoChangeEvent(change_event);
			}
		};
	    SwingUtilities.invokeLater(do_work_later);
    }
    
    //=======================================================================
    //=======================================================================
    private void fireTangoChangeEvent(TangoChangeEvent change_event) {
        // Guaranteed to return a non null array
        Object [] listeners = event_listeners.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2 ; i>=0 ; i-=2 ) {
            if (listeners[i] == ITangoChangeListener.class) {
                ((ITangoChangeListener)listeners[i+1]).change(change_event);
            }
        }
    }
	//==============================================================
	//==============================================================

    String attr_name;
    int event_identifier;
    String[] filters;
}
