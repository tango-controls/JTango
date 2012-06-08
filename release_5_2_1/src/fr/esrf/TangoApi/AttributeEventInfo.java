//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AttributeEventInfo class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.1  2004/11/05 11:59:19  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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
 * @version  $Revision$
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
