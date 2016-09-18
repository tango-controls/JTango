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


import fr.esrf.TangoDs.TangoConst;

import java.util.Enumeration;
import java.util.Hashtable;


/**
 * @author pascal_verdier
 */




//===============================================================
/**
 * A class inherited from TimerTask class
 */
//===============================================================
class KeepAliveThread extends Thread implements TangoConst {

    private static final long EVENT_RESUBSCRIBE_PERIOD = 600000;
    private static final long EVENT_HEARTBEAT_PERIOD   =  10000;
    private static boolean stop = false;
    private static KeepAliveThread  instance = null;
    //===============================================================
    /**
     * Creates a new instance of EventConsumer.KeepAliveThread
     */
    //===============================================================
    private KeepAliveThread() {
        super();
        this.setName("KeepAliveThread");
    }

    //===============================================================
    //===============================================================
    public void run() {

        while (!stop) {
            long t0 = System.currentTimeMillis();

            try {
                EventConsumer.subscribeIfNotDone();
                resubscribe_if_needed();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            catch (Error err) {
                err.printStackTrace();
            }

            long msToSleep = EVENT_HEARTBEAT_PERIOD - (System.currentTimeMillis() - t0);
            if (msToSleep<5)
                msToSleep = 5;
            waitNextLoop(msToSleep);
        }
    }
    //===============================================================
    //===============================================================
    private synchronized void waitNextLoop(long ms) {
        try {
            wait(ms);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
    //===============================================================
    //===============================================================
    synchronized void stopThread() {
        stop = true;
        if (instance!=null)
            notify();
        instance = null;
    }
    //===============================================================
    //===============================================================
    static KeepAliveThread getInstance() {
        if (instance==null) {
            instance = new KeepAliveThread();
            instance.start();
        }
        return instance;
    }
    //===============================================================
    //===============================================================
    static boolean heartbeatHasBeenSkipped(EventChannelStruct eventChannelStruct) {
        long now = System.currentTimeMillis();
        //System.out.println(now + "-" + eventChannelStruct.last_heartbeat + "=" +
        //        (now - eventChannelStruct.last_heartbeat));
        return ((now - eventChannelStruct.last_heartbeat) > EVENT_HEARTBEAT_PERIOD);
    }
    //===============================================================
    //===============================================================




    //===============================================================
    //===============================================================
    private void resubscribe_if_needed() {
        Enumeration channel_names = EventConsumer.getChannelMap().keys();
        long now = System.currentTimeMillis();

        // check the list of not yet connected events and try to subscribe
        while (channel_names.hasMoreElements()) {
            String name = (String) channel_names.nextElement();
            EventChannelStruct eventChannelStruct = EventConsumer.getChannelMap().get(name);
            if ((now - eventChannelStruct.last_subscribed) > EVENT_RESUBSCRIBE_PERIOD / 3) {
                reSubscribeByName(eventChannelStruct, name);
            }
            eventChannelStruct.consumer.checkIfHeartbeatSkipped(name, eventChannelStruct);

        }// end while  channel_names.hasMoreElements()
    }
    //===============================================================
    /*
     * Re subscribe event selected by name
     */
    //===============================================================
    private void reSubscribeByName(EventChannelStruct eventChannelStruct, String name) {

        //  Get the map and the callback structure for channel
        Hashtable<String, EventCallBackStruct>
                callBackMap = EventConsumer.getEventCallbackMap();
        EventCallBackStruct callbackStruct = null;
        Enumeration channelNames = callBackMap.keys();
        while (channelNames.hasMoreElements()) {
            String  key = (String) channelNames.nextElement();
            EventCallBackStruct eventStruct = callBackMap.get(key);
            if (eventStruct.channel_name.equals(name)) {
                callbackStruct = eventStruct;
            }
        }

        //  Get the callback structure
        if (callbackStruct!=null) {
            callbackStruct.consumer.reSubscribeByName(eventChannelStruct, name);
        }
    }
}
