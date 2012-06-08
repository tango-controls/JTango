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
// Revision 1.4  2008/10/10 11:28:58  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.3  2008/06/27 12:23:31  pascal_verdier
// Bug in event reconnection after a network blank fixed
// (Timeout on EventChannel object for MyFactory() and notifd_close_connection counter).
//
// Revision 1.2  2008/04/11 08:09:26  pascal_verdier
// *** empty log message ***
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 1.6  2007/08/07 06:54:13  pascal_verdier
// Management without database added.
//
// Revision 1.5  2006/08/31 08:45:32  pascal_verdier
// Reconnection management changed to reconnect after network blank.
//
// Revision 1.4  2006/06/08 08:04:40  pascal_verdier
// Bug on events if DS change host fixed.
//
// Revision 1.3  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//-======================================================================
/*
 * EventChannelStruct.java
 *
 * Created on May 21, 2003, 3:22 PM
 */

package fr.esrf.TangoApi.events;

import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.Database;
import org.omg.CosNotifyChannelAdmin.EventChannel;
import org.omg.CosNotifyChannelAdmin.StructuredProxyPushSupplier;

/**
 *
 * @author  pascal_verdier
 */
public class EventChannelStruct {
    
    public EventChannel		eventChannel;
    public StructuredProxyPushSupplier	structuredProxyPushSupplier;
    public DeviceProxy		adm_device_proxy;
    public long		last_subscribed;
    public long		last_heartbeat;
    public boolean	heartbeat_skipped;
    public int		heartbeat_filter_id;
	public String	host;
	public boolean	notifd_failed = false;
	public boolean	use_db = true;
	public Database	dbase = null;
	public int		has_notifd_closed_the_connection;
    
    /** Creates a new instance of EventChannelStruct */
    public EventChannelStruct() 
    {
    }
    
}
