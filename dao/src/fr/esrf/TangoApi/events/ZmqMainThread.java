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



/** 
 *	This class is a thread class to manage ZMQ event receptions
 *
 * @author  verdier
 */

import fr.esrf.Tango.AttDataReady;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Hashtable;


//===============================================================
//===============================================================
public class ZmqMainThread extends Thread {
    private static final int    ControlSock  = 0;
    private static final int    HearBeatSock = 1;
    private static final int    EventSock    = 2;

    private ZMQ.Socket  controlSocket;
    private ZMQ.Socket  heartbeatSocket;
    private ZMQ.Socket  eventSocket;
    private ZMQ.Poller  items;
    private boolean     stop = false;
    private ArrayList<String> connected = new ArrayList<String>();
    //===============================================================
    /**
     * Default constructor
     * @param context ZMQ context instance
     */
    //===============================================================
    ZmqMainThread(ZMQ.Context context) {

        // Prepare our receivers
        controlSocket   = context.socket(ZMQ.REP);
        heartbeatSocket = context.socket(ZMQ.SUB);
        eventSocket     = context.socket(ZMQ.SUB);

        controlSocket.setLinger(0);
        controlSocket.bind("inproc://control");
        heartbeatSocket.setLinger(0);
        eventSocket.setLinger(0);

        // Initialize poll set
        items = context.poller(3);
        items.register(controlSocket,   ZMQ.Poller.POLLIN);
        items.register(heartbeatSocket, ZMQ.Poller.POLLIN);
        items.register(eventSocket,     ZMQ.Poller.POLLIN);

    }
    //===============================================================
    /**
     * The thread infinite loop.
     * Wait on sockets, and dispatch when message has been received
     */
    //===============================================================
    public void run() {

        while (!stop) {
            //  Poll the sockets inputs
            items.poll();

            //  read the speaking one
            for (int i=0 ; i<items.getSize() ; i++) {
                if (items.pollin(i)) {
                    manageInputBuffer(i);
                }
            }
        }
        ApiUtil.printTrace("------------ End of ZmqMainThread ---------------");
    }
    //===============================================================
    /**
     * Read messages on socket
     * @param socket    specified socket
     * @param nb        number of messages to be read
     * @return an array of buffers containing received messages
     */
    //===============================================================
    private byte[][] readSocket(ZMQ.Socket socket, int nb) {
        byte[][]    inputs = new byte[nb][];
        for (int i=0 ; i<nb ; i++) {
            inputs[i] = socket.recv(0);
        }
        return inputs;
    }
    //===============================================================
    /**
     * Manage received messages (depends on socket)
     * @param source specified socket
     */
    //===============================================================
    private void manageInputBuffer(int source){
        switch (source) {
            case ControlSock:
                try {
                    //  Read input from socket
                    byte[] inputBuffer = controlSocket.recv(0);
                    manageControl(inputBuffer);
                    controlSocket.send("".getBytes(), 0);
                }
                catch (DevFailed e) {
                    controlSocket.send(e.errors[0].desc.getBytes(), 0);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    controlSocket.send(e.toString().getBytes(), 0);
                }
                break;

            case HearBeatSock:
                try {
                    //  Read input from socket (in 3 parts)
                    byte[][]    inputs = readSocket(heartbeatSocket, 3);
                    manageHeartbeat(inputs);
                }
                catch (DevFailed e) {
                    Except.print_exception(e);
                }
                break;

            case EventSock:
                try {
                    byte[][]    inputs = readSocket(eventSocket, 4);
                    manageEvent(inputs);
                }
                catch (DevFailed e) {
                    Except.print_exception(e);
                }
                break;
        }
    }
    //===============================================================
    /**
     * Extract device name from event name
     * @param eventName Specified event
     * @return the device name
     */
    //===============================================================
    private String getDeviceName(String eventName) {
        int pos = eventName.lastIndexOf('.');
        return eventName.substring(0, pos);
    }
    //===============================================================
    /**
     * Extract event name from input byte buffer
     * @param inputs byte buffer
     * @return the event name
     */
    //===============================================================
    private String getEventName(byte[] inputs) {
        String  s = new String(inputs);
        //  Remove Tango host
        int pos = s.lastIndexOf('.');
        for (int i=0 ; i<4 ; i++)
            pos = s.lastIndexOf('/', pos-1);
        return s.substring(pos+1);
    }
    //===============================================================
    /**
     * Manage events. Extract data and push to callback
     * @param inputs received messages
     * @throws DevFailed if cannot extract event type or data from messages.
     */
    //===============================================================
    private void manageEvent(byte[][] inputs) throws DevFailed {

        boolean littleEndian = (inputs[1][0]!=0);
        String  eventName = getEventName(inputs[0]);
        String  deviceName = getDeviceName(eventName);
        int eventType = ZMQutils.getEventType(eventName);

        Hashtable<String, EventCallBackStruct> callbackMap = ZmqEventConsumer.getEventCallbackMap();
        if (callbackMap.containsKey(eventName)) {
            EventCallBackStruct callBackStruct = callbackMap.get(eventName);
            CallBack callback = callBackStruct.callback;
            DeviceAttribute attributeValue  = null;
            AttributeInfoEx attributeConfig = null;
            AttDataReady    dataReady       = null;
            DevError[]      dev_err_list    = null;
            switch (eventType) {
                case TangoConst.ATT_CONF_EVENT:
                    attributeConfig = ZMQutils.deMarshallAttributeConfig(inputs[3], littleEndian);
                    break;
                case TangoConst.DATA_READY_EVENT:
                    dataReady = ZMQutils.deMarshallAttDataReady(inputs[3], littleEndian);
                    break;
                default:
                    //  ToDo needs idl version
                    Hashtable<String, EventChannelStruct> channelMap = EventConsumer.getChannelMap();
                    EventChannelStruct eventChannelStruct = channelMap.get(callBackStruct.channel_name);
                    if (eventChannelStruct!=null) {
                        int idl = eventChannelStruct.getIdlVersion();
                        attributeValue =
                                ZMQutils.deMarshallAttribute(inputs[3], littleEndian, idl);
                    }
            }
            //	And build event data
            EventData event_data =
                    new EventData(callBackStruct.device,
                            deviceName, eventName,
                            callBackStruct.event_type, EventData.ZMQ_EVENT,
                            attributeValue, attributeConfig, dataReady, dev_err_list);


            if (callBackStruct.use_ev_queue) {
                EventQueue ev_queue = callBackStruct.device.getEventQueue();
                ev_queue.insert_event(event_data);
            } else if (callback != null) {
                callback.push_event(event_data);
            }
        }
        else
            System.err.println(eventName + " ?  NOT FOUND");
    }
    //===============================================================
    /**
     * Manage heartbeat
     * @param inputs received messages
     * @throws DevFailed if cannot get ZmqEventConsumer instance
     */
    //===============================================================
    private void manageHeartbeat(byte[][] inputs) throws DevFailed{

        //  First part is heartbeat name
        String  name = new String(inputs[0]);
        ApiUtil.printTrace("Receive Heartbeat");
        //  Get only device name
        int start = name.indexOf("dserver/");
        int end   = name.lastIndexOf('.');
        name = name.substring(start, end);
        ZmqEventConsumer.getInstance().push_structured_event_heartbeat(name);
        /*  NOT USED
        //  Second one is endian
        boolean littleEndian = (inputs[1][0]!=0);

        System.out.println(heartbeatName + ":   little="+littleEndian);
        //ZMQutils.dump(b2);
        */
    }
    //===============================================================
    /**
     * Remove the connection
     * @param endpoint connection endpoint
     */
    //===============================================================
    @SuppressWarnings({"UnusedDeclaration"})
    void removeConnection(String endpoint) {
        System.out.println("---------------> Removing " + endpoint);
        connected.remove(endpoint);
    }
    //===============================================================
    //===============================================================
    private boolean alreadyConnected(String endPoint) {
        for (String connectedEndPoint : connected) {
            if (connectedEndPoint.equals(endPoint))
                return true;
        }
        return false;
    }
    //===============================================================
    /**
     * Manage control socket messages
     * @param messageBytes received messages
     * @throws DevFailed if cannot get ZmqEventConsumer instance or cannot decode messages
     */
    //===============================================================
    private void manageControl(byte[] messageBytes) throws DevFailed{

        ZMQutils.ControlStructure
                controlStructure = ZMQutils.getInstance().decodeControlBuffer(messageBytes);
        ApiUtil.printTrace("From Control:\n" + controlStructure);
        switch (controlStructure.commandCode) {
            case ZMQutils.ZMQ_END:
                stop = true;
                break;

            case ZMQutils.ZMQ_CONNECT_HEARTBEAT:
                //  Check if not already connected
                if (alreadyConnected(controlStructure.endPoint)) {
                    ApiUtil.printTrace("heartbeatSocket already connected to " +
                            controlStructure.endPoint);
                }
                else {
                    ApiUtil.printTrace("Connect heartbeatSocket to "+ controlStructure.endPoint);
                    heartbeatSocket.connect(controlStructure.endPoint);
                    connected.add(controlStructure.endPoint);
                }
                heartbeatSocket.subscribe(controlStructure.eventName.getBytes());
                break;


            case ZMQutils.ZMQ_DISCONNECT_HEARTBEAT:
                heartbeatSocket.unsubscribe(controlStructure.eventName.getBytes());
                break;

            case ZMQutils.ZMQ_CONNECT_EVENT:
                //  Check if not already connected
                if (alreadyConnected(controlStructure.endPoint)) {
                    ApiUtil.printTrace(
                            "Event already connected to " + controlStructure.endPoint);
                }
                else {
                    ApiUtil.printTrace("Set socket buffer for HWM to " + controlStructure.hwm);
                    eventSocket.setHWM(controlStructure.hwm);
                    eventSocket.connect(controlStructure.endPoint);
                    connected.add(controlStructure.endPoint);
                    ApiUtil.printTrace("Connect   eventSocket to "+ controlStructure.endPoint);
                }
                eventSocket.subscribe(controlStructure.eventName.getBytes());
                ApiUtil.printTrace("subscribe eventSocket to "+ controlStructure.eventName);
                break;

            case ZMQutils.ZMQ_DISCONNECT_EVENT:
                eventSocket.unsubscribe(controlStructure.eventName.getBytes());
                break;

        }
    }
    //===============================================================
    //===============================================================
}
