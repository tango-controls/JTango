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
//-======================================================================
/*
 * IEventConsumer.java
 *
 */

package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * 
 * @author Gwenaelle Abeille
 */
public interface IEventConsumer {

	// ===============================================================
	/**
	 * Creates a new instance of EventConsumer
	 */
	// ===============================================================
	// public static EventConsumer create() throws DevFailed;
	// ===============================================================
	// ===============================================================
	public void updateDatabaseObject() throws DevFailed;

	// ===============================================================
	// ===============================================================
	public void disconnect_structured_push_consumer();

	// ===============================================================
	// ===============================================================
	// public void offer_change(final org.omg.CosNotification.EventType[] added,
	// final org.omg.CosNotification.EventType[] removed);

	// ===============================================================
	// ===============================================================
	// public void push_structured_event(
	// final org.omg.CosNotification.StructuredEvent notification);

	// ===============================================================
	// ===============================================================
	public int subscribe_event(final DeviceProxy device,
			final String attribute, final int event, final CallBack callback,
			final String[] filters, final boolean stateless) throws DevFailed;

	// ===============================================================
	// ===============================================================
	public int subscribe_event(final DeviceProxy device,
			final String attribute, final int event, final int max_size,
			final String[] filters, final boolean stateless) throws DevFailed;

	// ===============================================================
	// ===============================================================
	public int subscribe_event(final DeviceProxy device,
			final String attribute, final int event, final CallBack callback,
			final int max_size, final String[] filters, final boolean stateless)
			throws DevFailed;

	// ===============================================================
	// ===============================================================
	public void unsubscribe_event(final int event_id) throws DevFailed;

	// ===============================================================
	/**
	 *
 	 */
	// ===============================================================
	public void connect(final DeviceProxy device_proxy) throws DevFailed;

	// ===============================================================
	/**
	 * activate POA and go into endless lopp waiting for events to be pushed
	 */
	// ===============================================================
	public void run();

}
