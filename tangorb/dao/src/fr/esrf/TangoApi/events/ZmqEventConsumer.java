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
        //  Nothing to do
    }

    //===============================================================
    //===============================================================
    @Override
    protected void setAdditionalInfoToEventCallBackStruct(EventCallBackStruct callback_struct,
                          String device_name, String attribute, String event_name, String[] filters, EventChannelStruct channel_struct) throws DevFailed {
        // Nothing
        ApiUtil.printTrace("-------------> Set as ZmqEventConsumer for "+device_name);
        callback_struct.consumer  = this;
    }

    //===============================================================
    //===============================================================
    private void connect(DeviceProxy deviceProxy, String attributeName, String eventName, DeviceData deviceData) throws DevFailed {
        String deviceName = deviceProxy.name();
        String adm_name = null;
        try {
            adm_name = deviceProxy.adm_name();
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
            if (deviceProxy.use_db())
                dbase = deviceProxy.get_db_obj();
            ConnectionStructure connectionStructure =
                    new ConnectionStructure(deviceProxy.get_tango_host(),
                            channelName, deviceName, attributeName,
                            eventName, dbase, deviceData, false);
            connect_event_channel(connectionStructure);
        } else if (deviceProxy.use_db()) {
            dbase = deviceProxy.get_db_obj();
            ZMQutils.connectEvent(deviceProxy.get_tango_host(), deviceName,
                        attributeName, deviceData.extractLongStringArray(), eventName,false);
        }
        EventChannelStruct eventChannelStruct = channel_map.get(channelName);
        eventChannelStruct.adm_device_proxy = deviceProxy.get_adm_dev();
        eventChannelStruct.use_db = deviceProxy.use_db();
        eventChannelStruct.dbase = dbase;

        device_channel_map.put(deviceName, channelName);
    }
    //===============================================================
    //===============================================================
    @Override
    protected  void checkDeviceConnection(DeviceProxy deviceProxy,
                        String attribute, DeviceData deviceData, String event_name) throws DevFailed {

        String deviceName = deviceProxy.name();
        ApiUtil.printTrace("checkDeviceConnection for " + deviceName);
        if (!device_channel_map.containsKey(deviceName)) {
            ApiUtil.printTrace("    Does NOT Exist");
            connect(deviceProxy, attribute, event_name, deviceData);
            if (!device_channel_map.containsKey(deviceName)) {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        "Failed to connect to event channel for device",
                        "EventConsumer.subscribe_event()");
            }
        }
        else {
            ApiUtil.printTrace(deviceName + " already connected.");
            ZMQutils.connectEvent(deviceProxy.get_tango_host(), deviceName,
                        attribute, deviceData.extractLongStringArray(), event_name,false);
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
        ZMQutils.connectHeartbeat(adminDevice.get_tango_host(), adminDevice.name(), lsa, false);

        //  Build the buffer to connect event and send it
        ZMQutils.connectEvent(cs.tangoHost,
                cs.deviceName, cs.attributeName, lsa, cs.eventName, false);
        if (cs.reconnect) {
            EventChannelStruct eventChannelStruct = channel_map.get(cs.channelName);
           // eventChannelStruct.eventChannel = eventChannel;
            eventChannelStruct.last_heartbeat = System.currentTimeMillis();
            eventChannelStruct.heartbeat_skipped = false;
            eventChannelStruct.has_notifd_closed_the_connection = 0;
            eventChannelStruct.setTangoRelease(lsa.lvalue[0]);
            eventChannelStruct.setIdlVersion(lsa.lvalue[1]);
        } else {
            EventChannelStruct newEventChannelStruct = new EventChannelStruct();
            //newEventChannelStruct.eventChannel = eventChannel;
            newEventChannelStruct.last_heartbeat = System.currentTimeMillis();
            newEventChannelStruct.heartbeat_skipped = false;
            newEventChannelStruct.adm_device_proxy = adminDevice;
            newEventChannelStruct.has_notifd_closed_the_connection = 0;
            newEventChannelStruct.consumer = this;
            newEventChannelStruct.zmqEndpoint = lsa.svalue[0];
            newEventChannelStruct.setTangoRelease(lsa.lvalue[0]);
            newEventChannelStruct.setIdlVersion(lsa.lvalue[1]);
            channel_map.put(cs.channelName, newEventChannelStruct);
            ApiUtil.printTrace("Adding " + cs.channelName + " to channel_map");
        }
    }

    //===============================================================
    //===============================================================
    @Override
    protected boolean reSubscribe(EventChannelStruct channelStruct, EventCallBackStruct eventCallBackStruct) {
        //  ToDo
        boolean done = false;
        try {
            ApiUtil.printTrace("====================================================\n" +
                                "   Try to resubscribe " + eventCallBackStruct.channel_name);
            DevVarLongStringArray   lsa =
                ZMQutils.getEventSubscriptionInfoFromAdmDevice(
                        channelStruct.adm_device_proxy,
                        eventCallBackStruct.device.name(), eventCallBackStruct.attr_name, eventCallBackStruct.event_name);

            //  Update the heartbeat time
            push_structured_event_heartbeat(channelStruct.adm_device_proxy.name());
            channelStruct.heartbeat_skipped = false;
            channelStruct.last_subscribed = System.currentTimeMillis();
            channelStruct.setTangoRelease(lsa.lvalue[0]);
            channelStruct.setIdlVersion(lsa.lvalue[1]);
            eventCallBackStruct.last_subscribed = channelStruct.last_subscribed;
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
    protected void checkIfHeartbeatSkipped(String name, EventChannelStruct channelStruct) {
            // Check if heartbeat have been skipped, can happen if
            // 1- the server is dead
            // 2- The network was down;
            // 3- The server has been restarted on another host.

        if (KeepAliveThread.heartbeatHasBeenSkipped(channelStruct)) {

            DevError    dev_error = null;
            try{
                channelStruct.adm_device_proxy =
                    new DeviceProxy(channelStruct.adm_device_proxy.name());
                channelStruct.adm_device_proxy.ping();
                reconnectToChannel(name);
            }
            catch (DevFailed e) {
                dev_error = e.errors[0];
            }

            Enumeration callbackStructs = EventConsumer.getEventCallbackMap().elements();
            while (callbackStructs.hasMoreElements()) {
                EventCallBackStruct callbackStruct = (EventCallBackStruct) callbackStructs.nextElement();
                if (callbackStruct.channel_name.equals(name)) {
                    //	Push exception
                    if (dev_error != null)
                        pushReceivedException(channelStruct, callbackStruct, dev_error);

                    //	If reconnection done, try to re subscribe
                    //		and read attribute in synchronous mode
                    if (!callbackStruct.event_name.equals(eventNames[DATA_READY_EVENT]))
                        if (reconnectToEvent(channelStruct, callbackStruct))
                            readAttributeAndPush(channelStruct, callbackStruct);
                }
            }
        }
    }
    //===============================================================
    //===============================================================
    protected void unsubscribeTheEvent(EventCallBackStruct callbackStruct) throws DevFailed {
        ZMQutils.disConnectEvent(callbackStruct.device.get_tango_host(),
                callbackStruct.device.name(),
                callbackStruct.attr_name, callbackStruct.event_name);
    }

    //===============================================================
    //===============================================================

    public void push_structured_event(StructuredEvent structuredEvent) throws Disconnected {
        //  Nothing to do for ZMQ system
    }


    //===============================================================
    /*
     * Reconnect to event
     *
     * @return true if reconnection done
     */
    //===============================================================
    private boolean reconnectToEvent(EventChannelStruct channelStruct, EventCallBackStruct callBackStruct) {
        boolean reConnected;
        try {
            DevVarLongStringArray   lsa =
                ZMQutils.getEventSubscriptionInfoFromAdmDevice(
                        channelStruct.adm_device_proxy,
                        callBackStruct.device.name(), callBackStruct.attr_name, callBackStruct.event_name);

            //  Build the buffer to connect event and send it
            ZMQutils.connectEvent(callBackStruct.device.get_tango_host(), callBackStruct.device.name(),
                    callBackStruct.attr_name, lsa, callBackStruct.event_name, true);
            reConnected = true;
        }
        catch (DevFailed e) {
            reConnected = false;
        }
        return reConnected;
    }
    //===============================================================
    /**
     * Reconnect to channel
     *
     * @param name channel name
     * @return true if reconnection done
     */
    //===============================================================
    private boolean reconnectToChannel(String name) {
        boolean reConnected = false;


        Enumeration callbackStructs = event_callback_map.elements();
        while (callbackStructs.hasMoreElements()) {
            EventCallBackStruct eventCallBackStruct = (EventCallBackStruct) callbackStructs.nextElement();
            if (eventCallBackStruct.channel_name.equals(name) && (eventCallBackStruct.callback != null)) {
                try {
                    EventChannelStruct channelStruct = channel_map.get(name);


                    DevVarLongStringArray   lsa =
                            ZMQutils.getEventSubscriptionInfoFromAdmDevice(
                                    channelStruct.adm_device_proxy,
                                    eventCallBackStruct.device.name(), eventCallBackStruct.attr_name, eventCallBackStruct.event_name);

                    //  Re Connect heartbeat
                    ZMQutils.connectHeartbeat(channelStruct.adm_device_proxy.get_tango_host(),
                                channelStruct.adm_device_proxy.name(), lsa, true);
                    reConnected = true;
                } catch (DevFailed e1) {
                    //Except.print_exception(e1);
                    reConnected = false;
                }
                break;
            }
        }
        return reConnected;
    }
}
