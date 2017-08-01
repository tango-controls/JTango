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
// $Revision: 25297 $
//
//-======================================================================


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
    public StructuredProxyPushSupplier
                            structuredProxyPushSupplier;
    public DeviceProxy		adm_device_proxy;
    public long		        last_subscribed;
    public long	            last_heartbeat;
    public boolean          heartbeat_skipped;
    public int	            heartbeat_filter_id;
	public String           host;
	public boolean	        notifd_failed = false;
	public boolean	        use_db = true;
	public Database	        dbase = null;
	public int		        has_notifd_closed_the_connection;
    public EventConsumer    consumer;
    public String           zmqEndpoint;
    private int idlVersion = 0;     //  used by zmq management
    private int tangoRelease = 0;   //  used by zmq management

    //=======================================================================
    /**
     *  Creates a new instance of EventChannelStruct
     */
    //=======================================================================
    public EventChannelStruct() {
    }
    //=======================================================================
    //=======================================================================
    int getIdlVersion() {
        return idlVersion;
    }
    //=======================================================================
    //=======================================================================
    void setIdlVersion(int idlVersion) {
        this.idlVersion = idlVersion;
    }
    //=======================================================================
    //=======================================================================
    int getTangoRelease() {
        return tangoRelease;
    }
    //=======================================================================
    //=======================================================================
    void setTangoRelease(int tangoRelease) {
        this.tangoRelease = tangoRelease;
    }

    //=======================================================================
    //=======================================================================
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("eventChannel         = ").append(eventChannel).append('\n');
        sb.append("adm_device_proxy     = ").append(adm_device_proxy.name()).append('\n');
        sb.append("last_subscribed      = ").append(last_subscribed).append('\n');
        sb.append("last_heartbeat       = ").append(last_heartbeat).append('\n');
        sb.append("heartbeat_skipped    = ").append(heartbeat_skipped).append('\n');
        sb.append("heartbeat_filter_id  = ").append(heartbeat_filter_id).append('\n');
        sb.append("host         = ").append(host).append('\n');
        sb.append("use_db       = ").append(use_db).append('\n');
        sb.append("dbase        = ").append(dbase).append('\n');
        sb.append("zmqEndpoint  = ").append(zmqEndpoint).append('\n');
        return sb.toString();
	}
}
