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
// Revision 1.7  2008/04/11 08:08:44  pascal_verdier
// *** empty log message ***
//
// Revision 1.6  2008/04/11 07:14:08  pascal_verdier
// AttConfig event management added.
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.4  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.3  2004/06/24 15:32:49  ounsy
// synchronization with tango 4.3 release event api features
//
// Revision 1.2  2004/03/19 10:24:35  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * EventCallBackStruct.java
 *
 * Created on May 21, 2003, 3:19 PM
 */

package fr.esrf.TangoApi.events;

import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceProxy;
/**
 *
 * @author  pascal_verdier
 */
public class EventCallBackStruct implements java.io.Serializable {
    
    public DeviceProxy device;
    public String   attr_name;
    public String   event_name;
    public String   channel_name;
    public String   filter_constraint;
    public CallBack callback;
    public long     last_subscribed;
	public int      event_type;
    public int      id;
    public int      filter_id;
	public String[]	filters;
    public boolean  filter_ok;
    
    /** Creates a new instance of EventCallBackStruct */
	//-======================================================================
    //=======================================================================
    public EventCallBackStruct() 
    {
    }
    
	//-======================================================================
    //=======================================================================
    public EventCallBackStruct(
						 DeviceProxy device,
						 String   attr_name,
						 String   event_name,
						 String   channel_name,
						 CallBack callback,
						 int      id,
						 int      event_type,
						 String   filter_constraint,
						 int      filter_id,
						 String[] filters,
						 boolean  filter_ok
				)
    {
 		 this.device           = device;
		 this.attr_name        = attr_name;
		 this.event_name       = event_name;
		 this.channel_name     = channel_name;
		 this.filter_constraint= filter_constraint;
		 this.callback         = callback;
		 this.event_type       = event_type;
		 this.id               = id;
		 this.filter_id        = filter_id;
		 this.filters          = filters;
		 this.filter_ok        = filter_ok;
   }
    //=======================================================================
    //=======================================================================
}
