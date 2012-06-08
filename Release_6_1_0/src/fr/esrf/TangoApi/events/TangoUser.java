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
// Revision 1.6  2008/04/11 08:08:44  pascal_verdier
// *** empty log message ***
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:53  ounsy
// updated change from api/java
//
// Revision 1.4  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.3  2005/08/10 08:24:33  pascal_verdier
// Synchronized done by a global object.
//
// Revision 1.2  2004/07/06 09:22:58  pascal_verdier
// subscribe event is now thread safe.
// notify daemon reconnection works.
//
// Revision 1.1  2004/03/19 10:24:35  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * TangoPeriodic.java
 *
 * Created on September 22, 2003, 11:22 AM
 */

package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;


/**
 *
 * @author  pascal_verdier
 */
public class TangoUser extends EventDispatcher implements java.io.Serializable {
    
    //=======================================================================
    /**
	 *	Creates a new instance of AttrPeriodicChange
	 */
    //=======================================================================
    public TangoUser(DeviceProxy device_proxy, String attr_name, String[] filters) {
        super(device_proxy);
        this.attr_name = attr_name;
        this.filters = filters;
        event_identifier = -1;
    }
    
    //=======================================================================
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, boolean stateless)
                throws DevFailed
    {
		event_listeners.add(ITangoUserListener.class, listener);
        event_identifier = subscribe_user_event(attr_name, filters, stateless);
    }
    

    //=======================================================================
    //=======================================================================
	public void removeTangoUserListener(ITangoUserListener listener) 
                throws DevFailed
    {
        event_listeners.remove(ITangoUserListener.class,listener);
        if ( event_listeners.getListenerCount() == 0 )
           unsubscribe_event(event_identifier);

    }
    
    //=======================================================================
    //=======================================================================
    public void dispatch_event(EventData event_data) {
            TangoUserEvent user_event = new TangoUserEvent(this,event_data);
            fireTangoUserEvent(user_event);
    }

    //=======================================================================
    //=======================================================================
    private void fireTangoUserEvent(TangoUserEvent user_event) {
        // Guaranteed to return a non null array
        Object [] listeners = event_listeners.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2 ; i>=0 ; i-=2 ) {
            if (listeners[i] == ITangoPeriodicListener.class) {
                ((ITangoUserListener)listeners[i+1]).user(user_event);
            }
        }
    }
    
	//==============================================================
	//==============================================================

    String attr_name;
    int event_identifier;
    String[] filters;
}
