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


import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CosEventComm.Disconnected;
import org.omg.CosNotification.StructuredEvent;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;


/**
 * @author pascal_verdier
 */
public class ZmqEventConsumer extends EventConsumer implements
        TangoConst, Runnable, IEventConsumer {

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
        addShutdownHook();
    }
   //===============================================================
   //===============================================================
    private Thread runner;
    private void addShutdownHook(){
        runner = new Thread(this);
        runner.setName("ZmqEventConsumer");
        //	Create a thread and start it
        Runtime.getRuntime().addShutdownHook(
            new Thread() {
                public void run() {
                    System.out.println("======== Shutting down ZMQ event system ==========");
                    KeepAliveThread.getInstance().stopThread();
                    try {
                        runner.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        runner.start();
    }
   //===============================================================
   //===============================================================
    public void run() {

    }

    //===============================================================
    /**
     * Subscribe event on device (Interface Change Event)
     * @param device device to subscribe
     * @param event event to subscribe
     * @param callback method to be called when receive an event
     * @param max_size  queue maximum size if use queue
     * @param stateless subscription stateless if true
     * @return the event ID
     * @throws DevFailed if subscription failed.
     */
    //===============================================================
    public int subscribe_event(DeviceProxy device,
                               int event,
                               CallBack callback,
                               int max_size,
                               boolean stateless)
            throws DevFailed {
        //	Set the event name;
        String event_name = eventNames[event];
        ApiUtil.printTrace("=============> subscribing for " + device.name() + "." + event_name);

        //	if no callback (null), create EventQueue
        if (callback == null && max_size >= 0) {
            //	Check if already created (in case of reconnection stateless mode)
            if (device.getEventQueue() == null)
                if (max_size > 0)
                    device.setEventQueue(new EventQueue(max_size));
                else
                    device.setEventQueue(new EventQueue());
        }

        String deviceName = device.fullName();
        String callback_key = deviceName.toLowerCase();

        //  Not added for Interface change event (Special case)
        /*
        if (device.get_idl_version()>=5)
            callback_key += ".idl" + device.get_idl_version()+ "_" + event_name;
        else
        */
            callback_key += "." + event_name;
        try {
            //	Inform server that we want to subscribe and try to connect
            ApiUtil.printTrace("calling callEventSubscriptionAndConnect() method");
            callEventSubscriptionAndConnect(device, event_name);
            ApiUtil.printTrace("call callEventSubscriptionAndConnect() method done");
        } catch (DevFailed e) {
            //  re throw if not stateless
            if (!stateless || e.errors[0].desc.equals(ZMQutils.SUBSCRIBE_COMMAND_NOT_FOUND)) {
                throw e;
            }
            else {
                //	Build Event CallBack Structure and add it to map
                subscribe_event_id++;
                EventCallBackStruct new_event_callback_struct =
                        new EventCallBackStruct(device,
                                event_name,
                                "",
                                callback,
                                max_size,
                                subscribe_event_id,
                                event,
                                false);
                failed_event_callback_map.put(callback_key, new_event_callback_struct);
                return subscribe_event_id;
            }
        }

        //	Prepare filters for heartbeat events on channelName
        String channelName = device_channel_map.get(deviceName);
        if (channelName==null) {
            //  If from notifd, tango host not used.
            int start = deviceName.indexOf('/', "tango:// ".length());
            deviceName = deviceName.substring(start+1);
            channelName = device_channel_map.get(deviceName);
        }
        EventChannelStruct event_channel_struct = channel_map.get(channelName);
        event_channel_struct.last_subscribed = System.currentTimeMillis();

        //	Check if a new event or a re-trying one
        int eventId;
        EventCallBackStruct failed_struct = failed_event_callback_map.get(callback_key);
        if (failed_struct == null) {
            //	It is a new one
            subscribe_event_id++;
            eventId = subscribe_event_id;
        } else
            eventId = failed_struct.id;

        //	Build Event CallBack Structure if any
        EventCallBackStruct new_event_callback_struct =
                new EventCallBackStruct(device,
                        event_name,
                        channelName,
                        callback,
                        max_size,
                        eventId,
                        event,
                        true);
        new_event_callback_struct.consumer  = this;
        event_callback_map.put(callback_key, new_event_callback_struct);


        //	Thread to read the attribute by a simple synchronous call and
        //	force callback execution after release monitor.
        //	This is necessary for the first point in "change" mode,
        //	but it is not necessary to be serialized in case of
        //	read attribute or callback execution a little bit long.
        if ((event == CHANGE_EVENT) ||
                (event == PERIODIC_EVENT) ||
                (event == PIPE_EVENT) ||
                (event == ARCHIVE_EVENT) ||
                (event == USER_EVENT) ||
                (event == INTERFACE_CHANGE) ||
                (event == ATT_CONF_EVENT)) {
            new PushAttrValueLater(new_event_callback_struct).start();
        }
        return eventId;
    }
    //===============================================================
    //===============================================================
    private void callEventSubscriptionAndConnect(DeviceProxy device, String eventType)
            throws DevFailed {
        //  Done for IDL>=5 and not for notifd event system (no attribute name)
        String device_name = device.name();
        String[] info = new String[] {
                device_name,
                "",
                "subscribe",
                eventType,
                Integer.toString(device.get_idl_version())
        };
        DeviceData argIn = new DeviceData();
        argIn.insert(info);
        String cmdName = getEventSubscriptionCommandName();
        ApiUtil.printTrace(device.get_adm_dev().name() + ".command_inout(\"" +
                cmdName + "\") for " + device_name + eventType);
        DeviceData argOut =
                device.get_adm_dev().command_inout(cmdName, argIn);
        ApiUtil.printTrace("    command_inout done.");

        //	And then connect to device
        checkDeviceConnection(device, null, argOut, eventType);
    }
   //===============================================================
   //===============================================================
    @Override
    protected String getEventSubscriptionCommandName() {
        return ZMQutils.SUBSCRIBE_COMMAND;
    }

    //===============================================================
    //===============================================================
    @Override
    protected void checkIfAlreadyConnected(DeviceProxy device, String attribute, String event_name, CallBack callback, int max_size, boolean stateless) throws DevFailed {
        //  Nothing to do (only override)
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
    private void connect(DeviceProxy deviceProxy, String attributeName,
                         String eventName, DeviceData deviceData) throws DevFailed {
        String deviceName = deviceProxy.fullName();
        int tangoVersion = deviceData.extractLongStringArray().lvalue[0];
        try {
            String adminName = deviceProxy.adm_name();  //.toLowerCase();
            //  Since Tango 8.1, heartbeat is sent in lower case.
            //tangoVersion = new DeviceProxy(adm_name).getTangoVersion();
            if (tangoVersion>=810)
                adminName = adminName.toLowerCase();

            // If no connection exists to this channel, create it
            Database database = null;
            if (!channel_map.containsKey(adminName)) {
                if (deviceProxy.use_db())
                    database = deviceProxy.get_db_obj();
                ConnectionStructure connectionStructure =
                        new ConnectionStructure(deviceProxy.get_tango_host(),
                                adminName, deviceName, attributeName,
                                eventName, database, deviceData, false);
                connect_event_channel(connectionStructure);
            } else if (deviceProxy.use_db()) {
                database = deviceProxy.get_db_obj();
                ZMQutils.connectEvent(deviceProxy.get_tango_host(), deviceName,
                        attributeName, deviceData.extractLongStringArray(), eventName,false);
            }
            EventChannelStruct eventChannelStruct = channel_map.get(adminName);
            eventChannelStruct.adm_device_proxy =  new DeviceProxy(adminName);
            eventChannelStruct.use_db = deviceProxy.use_db();
            eventChannelStruct.dbase = database;
            eventChannelStruct.setTangoRelease(tangoVersion);

            device_channel_map.put(deviceName, adminName);
        }
        catch (DevFailed e) {
            Except.throw_event_system_failed("API_BadConfigurationProperty",
                    "Can't subscribe to event for device " + deviceName
                            + "\n Check that device server is running...",
                    "ZmqEventConsumer.connect");
        }
    }
    //===============================================================
    /**
     *  Due to a problem when there is more than one network card,
     *  The address returned by the command ZmqEventSubscriptionChange
     *  is different than the getHostAddress() call !!!
     *  In this case the address from getHostAddress()
     *  replace the address in device data.
     */
   //===============================================================
    private DeviceData checkWithHostAddress(DeviceData deviceData, DeviceProxy deviceProxy) throws DevFailed {
// ToDo
        DevVarLongStringArray lsa = deviceData.extractLongStringArray();
        try {
            java.net.InetAddress iadd =
                    java.net.InetAddress.getByName(deviceProxy.get_host_name());
            String hostAddress = iadd.getHostAddress();
            System.err.println("Host address is " + hostAddress);
            System.err.println("Server returns  " + lsa.svalue[0]);
            if (! lsa.svalue[0].startsWith("tcp://"+hostAddress)) { //  Addresses are different
                 String  wrongAdd = lsa.svalue[0];
                 int idx = lsa.svalue[0].lastIndexOf(':');   //  get port
                 if (idx>0) {
                     lsa.svalue[0] = "tcp://" + hostAddress + lsa.svalue[0].substring(idx);
                     lsa.svalue[1] = "tcp://" + hostAddress + lsa.svalue[1].substring(idx);
                     System.out.println(wrongAdd + " ---> "+lsa.svalue[0]);
                     deviceData = new DeviceData();
                     deviceData.insert(lsa);
                     isEndpointAvailable(lsa.svalue[0]);
                 }
            }
        } catch (UnknownHostException e) {
            Except.throw_exception("UnknownHostException",
                    e.toString(), "ZmqEventConsumer.checkZmqAddress()");
        }
        //System.out.println("---> Connect on "+deviceData.extractLongStringArray().svalue[0]);
        return deviceData;
    }
    //===============================================================
    /**
     * In case of several endpoints, check which one is connected.
     * @param deviceData    data from ZmqEventSubscriptionChange command
     * @param deviceProxy   the admin device
     * @return the endpoints after checked
     * @throws DevFailed
     */
    //===============================================================
    private DeviceData checkZmqAddress(DeviceData deviceData, DeviceProxy deviceProxy) throws DevFailed{
        ZMQutils.zmqEventTrace("Inside checkZmqAddress()");
        DevVarLongStringArray lsa = deviceData.extractLongStringArray();
        for (int i=0 ; i<lsa.svalue.length ; i+=2) {
            String endpoint = lsa.svalue[i];
            if (isEndpointAvailable(endpoint)) {
                lsa.svalue[0] = lsa.svalue[i];
                lsa.svalue[1] = lsa.svalue[i+1];
                ZMQutils.zmqEventTrace("return "  + lsa.svalue[i] + " - " + lsa.svalue[i+1]);
                deviceData = new DeviceData();
                deviceData.insert(lsa);
                return deviceData;
            }
        }
        //  Not found check with host address
        return checkWithHostAddress(deviceData, deviceProxy);
    }
    //===============================================================
    //===============================================================
    private boolean isEndpointAvailable(String endpoint) {
        //System.out.println("Check endpoint: " + endpoint);
        try {
            //  Split address and port
            int start = endpoint.indexOf("//");
            if (start<0)  throw new Exception(endpoint + ": Bad syntax");
            int end = endpoint.indexOf(":", start);
            if (end<0)  throw new Exception(endpoint + ": Bad syntax");
            String address = endpoint.substring(start+2, end);
            int    port    = Integer.parseInt(endpoint.substring(end+1));

            //  Try to connect
            InetSocketAddress ip = new InetSocketAddress(address, port);
            Socket socket = new Socket();
            socket.connect(ip, 10);
            socket.close();
            return true;
        }
        catch (Exception e) {
            System.err.println(endpoint + " Failed:   " + e.getMessage());
            return false;
        }
    }
    //===============================================================
    //===============================================================
    @Override
    protected  void checkDeviceConnection(DeviceProxy deviceProxy,
                        String attribute, DeviceData deviceData, String event_name) throws DevFailed {

        //  Check if address is coherent (??)
        deviceData = checkZmqAddress(deviceData, deviceProxy);

        String deviceName = deviceProxy.fullName();
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
        //	Get a reference to an EventChannel for
        //  this device server from the tango database
        DeviceProxy adminDevice = new DeviceProxy(cs.channelName);
        cs.channelName = adminDevice.fullName().toLowerCase();    //  Update name with tango host

        DevVarLongStringArray   lsa = cs.deviceData.extractLongStringArray();
        ApiUtil.printTrace("connect_event_channel for " + cs.channelName);

        //  Build the buffer to connect heartbeat and send it
        ZMQutils.connectHeartbeat(adminDevice.get_tango_host(), adminDevice.name(), lsa, false);

        //  Build the buffer to connect event and send it
        ZMQutils.connectEvent(cs.tangoHost, cs.deviceName, cs.attributeName,
                lsa, cs.eventName, false);
        if (cs.reconnect) {
            EventChannelStruct eventChannelStruct = channel_map.get(cs.channelName);
           // eventChannelStruct.eventChannel = eventChannel;
            eventChannelStruct.last_heartbeat = System.currentTimeMillis();
            eventChannelStruct.heartbeat_skipped = false;
            eventChannelStruct.has_notifd_closed_the_connection = 0;
            eventChannelStruct.setTangoRelease(lsa.lvalue[0]);
            eventChannelStruct.setIdlVersion(lsa.lvalue[1]);
        } else {
            //  Crate new one
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

            //  Get possible TangoHosts and add it to list if not already in.
            String[]    tangoHosts = adminDevice.get_db_obj().getPossibleTangoHosts();
            for (String tangoHost : tangoHosts) {
                tangoHost = "tango://" + tangoHost;
                boolean found = false;
                for (String possibleTangoHost : possibleTangoHosts) {
                    if (possibleTangoHost.equals(tangoHost))
                        found = true;
                }
                if (!found) {
                    possibleTangoHosts.add(tangoHost);
                }
            }
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
                        eventCallBackStruct.device.name(),
                        eventCallBackStruct.attr_name, eventCallBackStruct.event_name);

            //  Update the heartbeat time
            String  admDeviceName = channelStruct.adm_device_proxy.name();  //.toLowerCase();
            //  Since Tango 8.1, heartbeat is sent in lower case.
            if (channelStruct.getTangoRelease()>=810)
                admDeviceName = admDeviceName.toLowerCase();
            push_structured_event_heartbeat(admDeviceName);
            channelStruct.heartbeat_skipped = false;
            channelStruct.last_subscribed = System.currentTimeMillis();
            channelStruct.setTangoRelease(lsa.lvalue[0]);
            channelStruct.setIdlVersion(lsa.lvalue[1]);
            eventCallBackStruct.last_subscribed = channelStruct.last_subscribed;
            done = true;
        }
        catch(DevFailed e) {
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
                String  admDeviceName = channelStruct.adm_device_proxy.fullName();  //.toLowerCase();
                //  Since Tango 8.1, heartbeat is sent in lower case.
                if (channelStruct.getTangoRelease()>=810)
                    admDeviceName = admDeviceName.toLowerCase();
                channelStruct.adm_device_proxy = new DeviceProxy(admDeviceName);
                channelStruct.adm_device_proxy.set_timeout_millis(300);
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
                    else {
                        //  ToDo Re connect without error means heartbeat missing
                        dev_error = new DevError("API_NoHeartbeat",
                                ErrSeverity.ERR, "No heartbeat from " +
                                    channelStruct.adm_device_proxy.get_name(),
                                "ZmqEventConsumer.checkIfHeartbeatSkipped()");
                        pushReceivedException(channelStruct, callbackStruct, dev_error);
                    }

                    //	If reconnection done, try to re subscribe
                    //		and read attribute in synchronous mode
                    if (reconnectToEvent(channelStruct, callbackStruct)) {
                        if (!callbackStruct.event_name.equals(eventNames[DATA_READY_EVENT])) {
                            readAttributeAndPush(channelStruct, callbackStruct);
                        }
                    }
                }
            }
        }
    }
    //===============================================================
    //===============================================================
    protected void unsubscribeTheEvent(EventCallBackStruct callbackStruct) throws DevFailed {
        ZMQutils.disConnectEvent(callbackStruct.device.get_tango_host(),
                callbackStruct.device.name(),
                callbackStruct.attr_name,
                callbackStruct.device.get_idl_version(),
                callbackStruct.event_name);
    }

    //===============================================================
    //===============================================================
    public void push_structured_event(StructuredEvent structuredEvent) throws Disconnected {
        //  Nothing to do for ZMQ system
    }


    //===============================================================
    /**
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
                        callBackStruct.device.name(),
                        callBackStruct.attr_name,
                        callBackStruct.event_name);

            //  Build the buffer to connect event and send it
            ZMQutils.connectEvent(callBackStruct.device.get_tango_host(),
                    callBackStruct.device.name(),
                    callBackStruct.attr_name, lsa,
                    callBackStruct.event_name, true);
            reConnected = true;
        }
        catch (DevFailed e) {
            //System.err.println(e.errors[0].desc);
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
