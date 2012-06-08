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
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
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
 * @author  ounsy
 */
public class TangoChange extends EventDispatcher implements java.io.Serializable {
    
    /** Creates a new instance of AttrAbsoluteChange */
    public TangoChange(DeviceProxy device_proxy , String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }
    
    public void addTangoChangeListener(ITangoChangeListener listener)
                throws DevFailed
    {
		event_listeners.add(ITangoChangeListener.class,listener);
        event_identifier = subscribe_change_event(attr_name,filters);
    }
    
    public void removeTangoChangeListener(ITangoChangeListener listener) 
                throws DevFailed
    {
        event_listeners.remove(ITangoChangeListener.class,listener);
        if ( event_listeners.getListenerCount() == 0 )
           unsubscribe_event(event_identifier);
    }
    
/*
    public void dispatch_event(EventData event_data) {
            TangoChangeEvent change_event = new TangoChangeEvent(this,event_data);
            fireTangoChangeEvent(change_event);
    }
*/

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
