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
// Revision 1.11  2008/12/05 15:31:58  pascal_verdier
// Add methods on EventQueue management on sepecified events.
//
// Revision 1.10  2008/12/03 13:06:31  pascal_verdier
// EventQueue management added.
//
// Revision 1.9  2008/10/10 11:34:15  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.8  2008/04/16 12:58:29  pascal_verdier
// Event subscribtion stateless added.
//
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
    public String      attr_name;
    public String      event_name;
    public String      channel_name; 
    public String      filter_constraint;
    public CallBack    callback;
	public int         max_size;
    public long        last_subscribed;
	public int         event_type;
    public int         id;		  
    public int         filter_id;		  
	public String[]    filters;
    public boolean     filter_ok;
	public boolean     use_ev_queue;
    
	//-======================================================================
    /**
	 *	Creates a new instance of EventCallBackStruct
	 */
    //=======================================================================
    public EventCallBackStruct() 
    {
    }
    
	//-======================================================================
    /**
	 *	Creates a new instance of EventCallBackStruct
	 */
    //=======================================================================
    public EventCallBackStruct(	DeviceProxy device,
								String      attr_name,
								String      event_name,
								String      channel_name,
								CallBack    callback,
								int         max_size,
								int         id,
								int         event_type,
								String      filter_constraint,
								int         filter_id,
								String[]    filters,
								boolean     filter_ok
							)
    {
 		 this.device           = device;
		 this.attr_name        = attr_name;
		 this.event_name       = event_name;
		 this.channel_name     = channel_name;
		 this.filter_constraint= filter_constraint;
		 this.callback         = callback;
		 this.max_size         = max_size;
		 this.event_type       = event_type;
		 this.id               = id;
		 this.filter_id        = filter_id;
		 this.filters          = filters;
		 this.filter_ok        = filter_ok;
		 this.use_ev_queue     = (callback==null);
   }
    //=======================================================================
    //=======================================================================
}
