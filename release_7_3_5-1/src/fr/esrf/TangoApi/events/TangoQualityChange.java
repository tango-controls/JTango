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
// Revision 1.8  2008/10/10 11:34:14  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/04/16 12:58:29  pascal_verdier
// Event subscribtion stateless added.
//
// Revision 1.6  2008/04/11 08:08:44  pascal_verdier
// *** empty log message ***
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.6  2005/12/02 10:17:05  pascal_verdier
// invokeLater() used in dispatch_event() method.
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
// Revision 1.2  2004/03/19 10:24:35  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//-======================================================================
/*
 * TangoQualityChange.java
 *
 * Created on September 26, 2003, 11:50 AM
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import javax.swing.*;

/**
 *
 * @author  pascal_verdier
 */
public class TangoQualityChange extends EventDispatcher implements java.io.Serializable {
    
	//==============================================================
    /**
	 *	Creates a new instance of TangoOnQualityChange
	 */
	//==============================================================
    public TangoQualityChange(DeviceProxy device_proxy , String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }
    
	//==============================================================
	//==============================================================
    public void addTangoQualityChangeListener(ITangoQualityChangeListener listener, boolean stateless)
                throws DevFailed
    {
		event_listeners.add(ITangoQualityChangeListener.class, listener);
        event_identifier = subscribe_quality_change_event(attr_name, filters, stateless);
    }
    
	//==============================================================
	//==============================================================
     public void removeTangoQualityChangeListener(ITangoQualityChangeListener listener) 
                throws DevFailed
    {
        event_listeners.remove(ITangoQualityChangeListener.class,listener);
        if ( event_listeners.getListenerCount() == 0 )
           unsubscribe_event(event_identifier);
    }
	//==============================================================
	//==============================================================
	public void dispatch_event(final EventData event_data) {
		final TangoQualityChange tg = this;
		Runnable do_work_later = new Runnable() {
	        public void run() {
				TangoQualityChangeEvent change_event = new TangoQualityChangeEvent(tg, event_data);
				fireTangoQualityChangeEvent(change_event);
			}
		};
	    SwingUtilities.invokeLater(do_work_later);
    }

 	//==============================================================
	//==============================================================
    private void fireTangoQualityChangeEvent(TangoQualityChangeEvent quality_change_event) {
        // Guaranteed to return a non null array
        Object [] listeners = event_listeners.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2 ; i>=0 ; i-=2 ) {
            if (listeners[i] == ITangoQualityChangeListener.class) {
                ((ITangoQualityChangeListener)listeners[i+1]).qualityChange(quality_change_event);
            }
        }
    }
    
	//==============================================================
	//==============================================================

    String attr_name;
    int event_identifier;
    String[] filters;
}
