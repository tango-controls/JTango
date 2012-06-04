//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012
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


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CosEventComm.Disconnected;
import org.omg.CosNotification.StructuredEvent;

import java.util.Enumeration;


/**
 * @author pascal_verdier
 */
public class ZmqEventConsumer extends EventConsumer
                    implements TangoConst, Runnable, IEventConsumer {

    private static ZmqEventConsumer instance = null;

    //===============================================================
    /**
     * Creates a new instance of EventConsumer
     *
     * @return an instance of EventConsumer object
     * @throws DevFailed in case of database connection failed.
     */
    //===============================================================
    public static ZmqEventConsumer getInstance() throws DevFailed {
        if (instance == null) {
            instance = new ZmqEventConsumer();
        }
        return instance;
    }
    //===============================================================
    //===============================================================
    private ZmqEventConsumer() throws DevFailed {

        super();
        //  Start ZMQ main thread
        ZmqMainThread zmqMainThread = new ZmqMainThread(ZMQutils.getContext());
        zmqMainThread.start();
    }
   //===============================================================
   //===============================================================
    public void run() {
        //  ToDo
        //keepAliveTimer.schedule(new KeepAliveThread(),
        //        2000L,//Delay 2s
//        /        KeepAliveThread.EVENT_HEARTBEAT_PERIOD);
    }
   //===============================================================
   //===============================================================


   //===============================================================
   //===============================================================
    @Override
    protected String getEventSubscriptionCommandName() {
        return "ZmqEventSubscriptionChange";
    }

    //===============================================================
    //===============================================================
    @Override
    protected void checkIfAlreadyConnected(DeviceProxy device, String attribute, String event_name, CallBack callback, int max_size, boolean stateless) throws DevFailed {
        //ToDo
    }

    //===============================================================
    //===============================================================
    @Override
    protected void setAdditionalInfoToEventCallBackStruct(EventCallBackStruct callback_struct,
                          String device_name, String attribute, String event_name, String[] filters, EventChannelStruct channel_struct) throws DevFailed {
        //ToDo
        ApiUtil.printTrace("-------------> Set as ZmqEventConsumer for "+device_name);
        callback_struct.consumer  = this;
    }

    //===============================================================
    //===============================================================
    private void connect(DeviceProxy device_proxy, String attributeName, String eventName, DeviceData deviceData) throws DevFailed {
        String deviceName = device_proxy.name();
        String adm_name = null;
        try {
            adm_name = device_proxy.adm_name();
        } catch (DevFailed e) {
            Except.throw_event_system_failed("API_BadConfigurationProperty",
                    "Can't subscribe to event for device " + deviceName
                            + "\n Check that device server is running...",
                    "ZmqEventConsumer.connect");
        }

        String channelName = adm_name;
        // If no connection exists to this channel, create it
        Database dbase = null;
        if (!channel_map.containsKey(channelName)) {
            if (device_proxy.use_db())
                dbase = device_proxy.get_db_obj();
            ConnectionStructure connectionStructure = new ConnectionStructure(
                    channelName, deviceName, attributeName, eventName, dbase, deviceData, false);
            connect_event_channel(connectionStructure);
        } else if (device_proxy.use_db()) {
            dbase = device_proxy.get_db_obj();
            ZMQutils.connectEvent(deviceName,
                        attributeName, deviceData.extractLongStringArray(), eventName);
        }
        EventChannelStruct eventChannelStruct = channel_map.get(channelName);
        eventChannelStruct.adm_device_proxy = device_proxy.get_adm_dev();
        eventChannelStruct.use_db = device_proxy.use_db();
        eventChannelStruct.dbase = dbase;

        device_channel_map.put(deviceName, channelName);
    }
    //===============================================================
    //===============================================================
    @Override
    protected  void checkDeviceConnection(DeviceProxy device,
                        String attribute, DeviceData deviceData, String event_name) throws DevFailed {

        String deviceName = device.name();
        ApiUtil.printTrace("checkDeviceConnection for " + deviceName);
        if (!device_channel_map.containsKey(deviceName)) {
            ApiUtil.printTrace("    Does NOT Exist");
            connect(device, attribute, event_name, deviceData);
            if (!device_channel_map.containsKey(deviceName)) {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        "Failed to connect to event channel for device",
                        "EventConsumer.subscribe_event()");
            }
        }
        else {
            ApiUtil.printTrace(deviceName + " already connected.");
            ZMQutils.connectEvent(deviceName,
                        attribute, deviceData.extractLongStringArray(), event_name);
        }
    }
    //===============================================================
    //===============================================================
    @Override
    protected synchronized void connect_event_channel(ConnectionStructure cs) throws DevFailed {
        //ToDo
        //	Get a reference to an EventChannel for
        //  this device server from the tango database
        DeviceProxy adminDevice = DeviceProxyFactory.get(cs.channelName);
        DevVarLongStringArray   lsa = cs.deviceData.extractLongStringArray();
        ApiUtil.printTrace("connect_event_channel for " + cs.channelName);

        //  Build the buffer to connect heartbeat and send it
        ZMQutils.connectHeartbeat(adminDevice.name(), lsa);

        //  Build the buffer to connect event and send it
        ZMQutils.connectEvent(cs.deviceName, cs.attributeName, lsa, cs.eventName);

        if (cs.reconnect) {
            EventChannelStruct eventChannelStruct = channel_map.get(cs.channelName);
           // eventChannelStruct.eventChannel = eventChannel;
            eventChannelStruct.last_heartbeat = System.currentTimeMillis();
            eventChannelStruct.heartbeat_skipped = false;
            eventChannelStruct.has_notifd_closed_the_connection = 0;
        } else {
            EventChannelStruct newEventChannelStruct = new EventChannelStruct();
            //newEventChannelStruct.eventChannel = eventChannel;
            newEventChannelStruct.last_heartbeat = System.currentTimeMillis();
            newEventChannelStruct.heartbeat_skipped = false;
            newEventChannelStruct.adm_device_proxy = adminDevice;
            newEventChannelStruct.has_notifd_closed_the_connection = 0;
            newEventChannelStruct.consumer = this;
            newEventChannelStruct.zmqEndpoint = lsa.svalue[0];
            channel_map.put(cs.channelName, newEventChannelStruct);
            ApiUtil.printTrace("Adding " + cs.channelName + " to channel_map");
        }
    }

    //===============================================================
    //===============================================================
    @Override
    protected boolean reSubscribe(EventChannelStruct channelStruct, EventCallBackStruct callbackStruct) {
        //  ToDo
        boolean done = false;
        try {
            ApiUtil.printTrace("====================================================\n" +
                                "   Try to resubscribe " + callbackStruct.channel_name);
            DevVarLongStringArray   lsa =
                ZMQutils.getEventSubscriptionInfoFromAdmDevice(
                        channelStruct.adm_device_proxy,
                        callbackStruct.device.name(), callbackStruct.attr_name, callbackStruct.event_name);

            //  Build the buffer to connect heartbeat and send it
            ZMQutils.connectHeartbeat(channelStruct.adm_device_proxy.name(), lsa);

            //  Build the buffer to connect event and send it
            ZMQutils.connectEvent(callbackStruct.device.name(),
                    callbackStruct.attr_name, lsa, callbackStruct.event_name);
            //  Update the heartbeat time
            push_structured_event_heartbeat(channelStruct.adm_device_proxy.name());
            channelStruct.heartbeat_skipped = false;
            channelStruct.last_subscribed = System.currentTimeMillis();
            callbackStruct.last_subscribed = channelStruct.last_subscribed;
            done = true;
        }catch(DevFailed e) {
            /* */
        }
        return done;
    }
    //===============================================================
    //===============================================================
    @Override
    protected void removeFilters(EventCallBackStruct cb_struct) throws DevFailed {
        //  Nothing to do for ZMQ
    }
    //===============================================================
    //===============================================================
    @Override
    protected void checkIfHeartbeatSkipped(String name, EventChannelStruct eventChannelStruct) {
            // Check if heartbeat have been skipped, can happen if
            // 1- the server is dead
            // 2- The network was down;
            // 3- The server has been restarted on another host.
        /********
            long now = System.currentTimeMillis();
            boolean heartbeat_skipped =
                ((now - eventChannelStruct.last_heartbeat) > KeepAliveThread.getEventHeartbeatPeriod());
            System.out.println((now - eventChannelStruct.last_heartbeat) +
                    " > " + KeepAliveThread.getEventHeartbeatPeriod()+ "=" + heartbeat_skipped);
        *************/
        if (KeepAliveThread.heartbeatHasBeenSkipped(eventChannelStruct)) {

            DevError    dev_error = null;
            try{
                //eventChannelStruct.adm_device_proxy.ping();
                eventChannelStruct.adm_device_proxy =
                    new DeviceProxy(eventChannelStruct.adm_device_proxy.name());
                eventChannelStruct.adm_device_proxy.ping();
            }
            catch (DevFailed e) {
                dev_error = e.errors[0];
            }
            //zmqMainThread.removeConnection(eventChannelStruct.zmqEndpoint);
            Enumeration callbackStructs = EventConsumer.getEventCallbackMap().elements();
            while (callbackStructs.hasMoreElements()) {
                EventCallBackStruct callbackStruct = (EventCallBackStruct) callbackStructs.nextElement();
                if (callbackStruct.channel_name.equals(name)) {
                    //	Push exception
                    if (dev_error != null)
                        pushReceivedException(eventChannelStruct, callbackStruct, dev_error);

                    //	If reconnection done, try to re subscribe
                    //		and read attribute in synchronous mode
                    if (!callbackStruct.event_name.equals(eventNames[DATA_READY_EVENT]))
                        if (eventChannelStruct.consumer.reSubscribe(eventChannelStruct, callbackStruct))
                            readAttributeAndPush(eventChannelStruct, callbackStruct);
                }
            }
        }
    }
    //===============================================================
    //===============================================================
    protected void unsubscribeTheEvent(EventCallBackStruct callbackStruct) throws DevFailed {
        ZMQutils.disConnectEvent(callbackStruct.device.name(),
                callbackStruct.attr_name, callbackStruct.event_name);
    }

    //===============================================================
    //===============================================================

    public void push_structured_event(StructuredEvent structuredEvent) throws Disconnected {
        //  Nothing to do for ZMQ system
    }
}
