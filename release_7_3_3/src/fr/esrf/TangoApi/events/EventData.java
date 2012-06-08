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
// Revision 1.11  2009/03/12 09:07:55  pascal_verdier
// *** empty log message ***
//
// Revision 1.10  2008/12/05 15:31:58  pascal_verdier
// Add methods on EventQueue management on sepecified events.
//
// Revision 1.9  2008/12/03 13:06:31  pascal_verdier
// EventQueue management added.
//
// Revision 1.8  2008/10/10 11:34:15  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/04/11 08:08:45  pascal_verdier
// *** empty log message ***
//
// Revision 1.6  2008/04/11 07:14:09  pascal_verdier
// AttConfig event management added.
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.3  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.2  2004/03/19 10:24:35  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//-======================================================================
/*
 * EventData.java
 *
 * Created on May 21, 2003, 2:44 PM
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.AttDataReady;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceProxy;

/**
 *
 * @author  pascal_verdier
 */
public class EventData implements java.io.Serializable
{
    public DeviceProxy		device;
    public String			name;
    public String			event;
	public int				event_type;
	public DeviceAttribute	attr_value;
    public AttributeInfoEx	attr_config;
	public AttDataReady		data_ready;
    public DevError[]		errors;
    public boolean			err;
	public long				date;

	//-=============================================
    /**
	 *	Creates a new instance of EventData
	 */
	//-=============================================
    public EventData(
                     DeviceProxy device,
                     String name,
                     String event,
					 int    event_type,
					 DeviceAttribute attr_value,
                     AttributeInfoEx attr_config,
                     AttDataReady data_ready,
                     DevError[] errors
                 )
    {
        this.device      = device;
        this.name        = name;
        this.event       = event;
        this.event_type  = event_type;
        this.attr_value  = attr_value;
        this.attr_config = attr_config;
        this.data_ready  = data_ready;
        this.errors = errors;
        err = (errors!=null );
		date = System.currentTimeMillis();
    }

	//-=============================================
	/**
	 *	return true if event is NOT TangoConst.ATT_CONF_EVENT
	 */
	//-=============================================
	public boolean isAttrValue()
	{
		return (attr_value!=null);
	}
	//-=============================================
	/**
	 *	return true if event is TangoConst.ATT_CONF_EVENT
	 */
	//-=============================================
	public boolean isAttrConfig()
	{
		return (attr_config!=null);
	}
	//-=============================================
	/**
	 *	return true if event is TangoConst.DATA_READY_EVENT
	 */
	//-=============================================
	public boolean isAttrDataReady()
	{
		return (data_ready!=null);
	}
}
