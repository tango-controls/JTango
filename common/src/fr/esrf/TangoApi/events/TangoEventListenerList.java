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
// $Revision:  $
//
//-======================================================================

package fr.esrf.TangoApi.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;


/**
 * This class manage a list of couple (Class, listener)
 */
public class TangoEventListenerList implements Serializable {

    private ArrayList<TangoListener> tangoListeners = new ArrayList<TangoListener>();
    //============================================================
    /**
     * A little class defining a Tango listener (Class and Listener method)
     */
    //============================================================
    class TangoListener {
        Class<?>       type;
        EventListener  listener;
        private <T extends EventListener> TangoListener(Class<T> type, T listener) {
            this.type = type;
            this.listener = listener;
        }
    }
    //============================================================
    /**
     * Registers a listener of a specific type.
     *
     * @param type     the listener type.
     * @param listener the listener to be added
     */
    //============================================================
    <T extends EventListener> void add(Class<T> type, T listener) {
        tangoListeners.add(new TangoListener(type, listener));
    }

    //============================================================
    /**
     * Removes a listener of a specific type.
     *
     * @param type     the listener type.
     * @param listener the listener to be removed
     */
    //============================================================
    <T extends EventListener> void remove(Class<T> type, T listener) {

        for (TangoListener tangoListener : tangoListeners) {
            if (tangoListener.type==type && tangoListener.listener==listener) {
                tangoListeners.remove(tangoListener);
                break;
            }
        }
    }
    //============================================================
    /**
     * Returns tangoListeners for specified type
     * @param type specified listener type
     * @return tangoListeners for specified type
     */
    //============================================================
    <T extends EventListener> ArrayList<EventListener> getListeners(Class<T> type) {
        ArrayList<EventListener>    listeners = new ArrayList<EventListener>();
        for (TangoListener tangoListener : tangoListeners) {
            if (tangoListener.type==type) {
                listeners.add(tangoListener.listener);
            }
        }
        return listeners;
    }
    //============================================================
    /**
     * @return the number of tangoListeners.
     */
    //============================================================
    int size() {
        return tangoListeners.size();
    }
}
