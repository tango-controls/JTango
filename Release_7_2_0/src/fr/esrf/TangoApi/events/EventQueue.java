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
// Revision 1.3  2008/12/05 15:31:58  pascal_verdier
// Add methods on EventQueue management on sepecified events.
//
// Revision 1.2  2008/12/03 15:42:45  pascal_verdier
// Usage example added.
//
// Revision 1.1  2008/12/03 13:06:31  pascal_verdier
// EventQueue management added.
//
//
//-======================================================================

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.util.Vector;
//-============================================================================

/**
 *	This class manage a vector of EventData to implement
 *	an event queue mechanism..
 * <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 *
 *
 *	<i>
 *	<Br>
 *	<Font COLOR="#3b648b">
 *	/** 	<Br>
 *	 *	This class check the event queue mechanism.	<Br>
 *	 * @author  verdier	<Br>
 *	 * /	<Br>
 *	</Font>
 *	 <Br>
 *	import fr.esrf.Tango.DevFailed;			<Br>
 *	import fr.esrf.TangoDs.TangoConst;		<Br>
 *	import fr.esrf.TangoDs.Except;			<Br>
 *	import fr.esrf.TangoApi.DeviceProxy;	<Br>
 *	import fr.esrf.TangoApi.CallBack;		<Br>
 *	import fr.esrf.TangoApi.DeviceAttribute;	<Br>
 *	import fr.esrf.TangoApi.events.EventData;	<Br>
 *	import java.util.Date;	<Br>
 *	<Br>
 *	public class  QueueTest extends DeviceProxy	<Br>
 *	{	<Ul>
 *		<Font COLOR="#3b648b">
 *		//===============================================================<Br>
 *		/**	<Br>
 *		 *	Constructor	<Br>
 *		 *	@param devname	The device name.	<Br>
 *		 *	@param attname	The attribute name.	<Br>
 *		 * /	<Br>
 *		//===============================================================<Br>
 *		</Font>
 *		public QueueTest(String devname, String attname) throws DevFailed<Br>
 *		{	<Ul>
 *			super(devname);<Br>
 *			subscribe_event(attname, TangoConst.CHANGE_EVENT, 0, new String[] {}, true);
 *			<Br></Ul>
 *		}	<Br>
 *	
 *		<Font COLOR="#3b648b">
 *		//===============================================================<Br>
 *		/**	<Br>
 *		 *	Event management.	<Br>
 *		 *	Wait a bit to have enough events, get them and push them to callback	<Br>
 *		 * /	<Br>
 *		//===============================================================<Br>
 *		</Font>
 *		public void manageEvents()<Br>
 *		{<Ul>
 *			<Font COLOR="#3b648b">
 *			// nb events to wait.	<Br>
 *			</Font>
 *			int	nb = 10; 		<Br>
 *			while(get_event_queue_size() < nb)<Br>
 *			{<Ul>
 *				try { Thread.sleep(1000); }	<Br>
 *				catch(Exception e) {}		<Br></Ul>
 *			}	<Br>
 *			<Br>
 *			<Font COLOR="#3b648b">
 *			//	Get all change events and display them	</Font><Br>
 *			EventData[]	events = get_events(TangoConst.CHANGE_EVENT);<Br>
 *			for (EventData event : events)	<Br>
 *			{<Ul>
 *				System.out.print("Event at " + new Date(event.date) + ":	");	<Br>
 *				if (event.err)	<Ul>
 *					Except.print_exception(event.errors);	<Br></Ul>
 *				else	<Br>
 *				{	<Ul>
 *					DeviceAttribute	value = event.attr_value;	<Br>
 *					if (value.hasFailed())	<Ul>
 *						Except.print_exception(value.getErrStack());	<Br></Ul>
 *					else	<Br>
 *					try	<Br>
 *					{<Ul>
 *						if (value.getType()==TangoConst.Tango_DEV_DOUBLE)	<Ul>
 *							System.out.println(value.extractDouble());	<Br></Ul></Ul>
 *					}	<Br>
 *					catch(DevFailed e)	<Br>
 *					{	<Ul>
 *						Except.print_exception(e);<Br></Ul>
 *					}	<Br></Ul>
 *				}	<Br></Ul>
 *			}	<Br></Ul>
 *		}<Br>
 *	
 *		<Font COLOR="#3b648b">
 *		//===============================================================<Br>
 *		/**	<Br>
 *		 *	main method.	<Br>
 *		 * /	<Br>
 *		//===============================================================<Br>
 *		</Font>
 *		public static void main (String[] args)	<Br>
 *		{	<Ul>
 *			String	devname = "my/device/test";	<Br>
 *			String	attribute = "value";		<Br>
 *			try	<Br>
 *			{	<Ul>
 *				QueueTest	client = new QueueTest(devname, attribute);	<Br>
 *				
 *				client.manageEvents();	<Br></Ul>
 *			}	<Br>
 *			catch(DevFailed e)	<Br>
 *			{	<Ul>
 *				Except.print_exception(e);	<Br></Ul>
 *			}	<Br></Ul>
 *		}	<Br>
 *	
 *		<Font COLOR="#3b648b">
 *		//===============================================================</Font>
 *		<Br></Ul>
 *	}<Br>
 *	
 *
 *
 *	@author  pascal_verdier
 */
//-============================================================================




public class EventQueue
{
	/**
	 *	Maximum size (not infinite but very big)
	 */
	private int		max_size = 1000000;
	/**
	 *	The queue itself
	 */
	private Vector<EventData>	events = new Vector();
    
	//==================================================================
	/**
	 *	Creates a new instance of EventQueue for infinite EventData number
	 */
	//==================================================================
	public EventQueue()
	{
	}
	//==================================================================
	/**
	 *	Creates a new instance of EventQueue for several EventData
	 *
	 *	@param max_size	maximum size of the event queue.
	 */
	//==================================================================
	public EventQueue(int max_size)
	{
		this.max_size = max_size;
	}
	//==================================================================
	/**
	 *	returns true if no EventData in queue.
	 */
	//==================================================================
	public synchronized  boolean is_empty()
	{
		return (events.size()==0);
	}
	//==================================================================
	/**
	 *	returns the number of EventData in queue.
	 *  @param	event_type	Specified event type.
	 */
	//==================================================================
	public synchronized int size(int event_type)
	{
		int	cnt = 0;
		for (EventData event : events)
			if (event.event_type == event_type)
				cnt++;
		return cnt;
	}
	//==================================================================
	/**
	 *	returns the number of EventData in queue for specified type.
	 */
	//==================================================================
	public synchronized int size()
	{
		return events.size();
	}
	//==================================================================
	/**
	 *	Insert an event in queue.
	 *
	 *	@param event Event to be inserted.
	 */
	//==================================================================
	public synchronized void insert_event(EventData event)
	{
		events.add(event);
		while (events.size()>max_size)
			events.remove(0);
	}
	//==================================================================
	/**
	 *	Returns the first event in queue.
	 */
	//==================================================================
	public synchronized EventData getNextEvent() throws DevFailed
	{
		if (events.size()==0)
			Except.throw_exception("BUFFER_EMPTY",
					"Event queue is empty.",
					"EventQueue.getNextEvent()");
		EventData	ev_data = events.get(0);
		events.remove(0);
		return ev_data;
	}
	//==================================================================
	/**
	 *	Returns the first event in queue for specified type.
	 *  @param	event_type	Specified event type.
	 */
	//==================================================================
	public synchronized EventData getNextEvent(int event_type) throws DevFailed
	{
		EventData	ev_data = null;
		for (EventData event : events)
			if (event.event_type == event_type)
				ev_data = event;
		if (ev_data==null)
			Except.throw_exception("BUFFER_EMPTY",
					"No "+TangoConst.eventNames[event_type]+" in event queue.",
					"EventQueue.getNextEvent()");
		events.remove(ev_data);
		return ev_data;
	}
	// ==========================================================================
	/**
	 *	returns all EventData in queue.
	 */
	// ==========================================================================
	public synchronized EventData[] getEvents()
	{
		EventData[]	ev_data = new EventData[events.size()];
		for (int i=0 ; i<events.size() ; i++)
			ev_data[i] = events.get(i);
		events.clear();
		return ev_data;
	}
	// ==========================================================================
	/**
	 *	returns all events in queue for specified type.
	 *  @param	event_type	Specified event type.
	 */
	// ==========================================================================
	public synchronized EventData[] getEvents(int event_type)
	{
		Vector<EventData>	v = new Vector<EventData>();
		for (EventData event : events)
			if (event.event_type == event_type)
				v.add(event);
		EventData[]	periodic = new EventData[v.size()];
		for (int i=0 ; i<v.size() ; i++)
		{
			periodic[i] = v.get(i);
			events.remove(v.get(i));
		}
		return periodic;
	}
	//==================================================================
	/**
	 *	Returns the date of the last inserted and not yet extracted event.
	 */
	//==================================================================
	public synchronized long getLastEventDate() throws DevFailed
	{
		if (events.size()==0)
			Except.throw_exception("BUFFER_EMPTY",
					"Event queue is empty.",
					"EventQueu.getNextEvent()");
		EventData	event = events.lastElement();
		return event.date;
	}
}
