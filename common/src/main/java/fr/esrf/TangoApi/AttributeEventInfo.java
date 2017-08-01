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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.ArchiveEventProp;
import fr.esrf.Tango.ChangeEventProp;
import fr.esrf.Tango.EventProperties;
import fr.esrf.Tango.PeriodicEventProp;


/**
 *	Class Description: This class is the same class as EventProperties, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
 */
public class AttributeEventInfo implements java.io.Serializable
{
	public ChangeEventInfo		ch_event;
	public PeriodicEventInfo	per_event;
	public ArchiveEventInfo		arch_event;
	//-======================================================================
	//-======================================================================
	public AttributeEventInfo(ChangeEventProp ch_event, PeriodicEventProp per_event, ArchiveEventProp arch_event)
	{
		this.ch_event   = new ChangeEventInfo(ch_event);
		this.per_event  = new PeriodicEventInfo(per_event);
		this.arch_event = new ArchiveEventInfo(arch_event);
	}
	//-======================================================================
	//-======================================================================
	public AttributeEventInfo(EventProperties ev_prop)
	{
		this.ch_event   = new ChangeEventInfo(ev_prop.ch_event);
		this.per_event  = new PeriodicEventInfo(ev_prop.per_event);
		this.arch_event = new ArchiveEventInfo(ev_prop.arch_event);
	}
	//-======================================================================
	//-======================================================================
	public EventProperties getTangoObj()
	{
		return new EventProperties(ch_event.getTangoObj(),
									per_event.getTangoObj(),
									arch_event.getTangoObj());
	}
	//-======================================================================
	//-======================================================================
}
