//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
//
// Copyleftt 2008 by Synchrotron Soleil, France
//-======================================================================
/*
 * TangoAttConfig.java
 *
 * Created on April 10, 2008 By Pascal Verdier
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import javax.swing.*;

/**
 *
 * @author  ounsy
 */
public class TangoAttConfig extends EventDispatcher implements java.io.Serializable {
    
    /** Creates a new instance of TangoOnAttConfig */
    public TangoAttConfig(DeviceProxy device_proxy , String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }
    
    public void addTangoAttConfigListener(ITangoAttConfigListener listener)
                throws DevFailed
    {
		event_listeners.add(ITangoAttConfigListener.class,listener);
        event_identifier = subscribe_att_config_event(attr_name,filters);
    }
    
    public void removeTangoAttConfigListener(ITangoAttConfigListener listener) 
                throws DevFailed
    {
        event_listeners.remove(ITangoAttConfigListener.class,listener);
        if ( event_listeners.getListenerCount() == 0 )
           unsubscribe_event(event_identifier);
    }
    /*
    public void dispatch_event(EventData event_data) {
            TangoAttConfigEvent onchange_event = new TangoAttConfigEvent(this,event_data);
            fireTangoAttConfigEvent(onchange_event);
    }
	*/
	public void dispatch_event(final EventData event_data) {
		final TangoAttConfig tg = this;
		Runnable do_work_later = new Runnable() {
	        public void run() {
				TangoAttConfigEvent change_event = new TangoAttConfigEvent(tg, event_data);
				fireTangoAttConfigEvent(change_event);
			}
		};
	    SwingUtilities.invokeLater(do_work_later);
    }

    private void fireTangoAttConfigEvent(TangoAttConfigEvent att_config_event) {
        // Guaranteed to return a non null array
        Object [] listeners = event_listeners.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2 ; i>=0 ; i-=2 ) {
            if (listeners[i] == ITangoAttConfigListener.class) {
                ((ITangoAttConfigListener)listeners[i+1]).attConfig(att_config_event);
            }
        }
    }
    
	//==============================================================
	//==============================================================

    String attr_name;
    int event_identifier;
    String[] filters;
}
