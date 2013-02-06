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

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;


//===============================================================
//===============================================================
public class ZmqMainThread extends Thread {
    private static final int    HearBeatSock = 0;
    private static final int    EventSock    = 1;
    private static final int    ControlSock  = 2;

    private ZMQ.Socket  controlSocket;
    private ZMQ.Socket  heartbeatSocket;
    private ZMQ.Socket  eventSocket;
    private ZMQ.Poller  items;
    private boolean     stop = false;
    /**
     * Map<endPoint, eventName>
     */
    private Hashtable<String,String> connectedMap = new Hashtable<String, String>();

    private static final int NameIdx    = 0;
    private static final int EndianIdx  = 1;
    private static final int ZmqInfoIdx = 2;
    private static final int ValueIdx   = 3;
    private static final int NbFields   = ValueIdx+1;

    private static final long SendHwmSocket = 10000;
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
        eventSocket.setSndHWM(SendHwmSocket);

        try {
            heartbeatSocket.setReconnectIVL(-1);
            eventSocket.setReconnectIVL(-1);
        }
        catch(Exception e) {
            //  Not supported in ZMQ-3.1
            long    longDelay = 1000*300;
            heartbeatSocket.setReconnectIVL(longDelay);
            eventSocket.setReconnectIVL(longDelay);
        }

        // Initialize poll set
        items = context.poller(3);
        items.register(heartbeatSocket, ZMQ.Poller.POLLIN);
        items.register(eventSocket,     ZMQ.Poller.POLLIN);
        items.register(controlSocket,   ZMQ.Poller.POLLIN);
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

        //  Check if input data are coherent
        if (inputs.length<NbFields) {   // || inputs[EndianIdx].length==0) {
            String name = "unknown";
            if (inputs.length>0)
                name = getEventName(inputs[0]);
            System.err.println("NbFields=" + NbFields);
            if (inputs.length>0) System.err.println("name=" + name);
            if (inputs.length>1) System.err.println("endian length=" + inputs[1].length);

            Except.throw_exception("Api_BadParameterException",
                    "Cannot decode event on " + name + " attribute",
                    "ZmqMainThread.manageEvent()");
        }

        try {
            //  Decode receive data parts
            String  eventName = getEventName(inputs[NameIdx]);
            boolean littleEndian = true;
            if (inputs[EndianIdx].length>0) {
                //  Sometimes inputs[EndianIdx] could be empty (fixed in c++ 8.1)
                littleEndian = (inputs[EndianIdx][0]!=0);
            }
            ZmqCallInfo zmqCallInfo =
                    ZMQutils.deMarshallZmqCallInfo(inputs[ZmqInfoIdx], littleEndian);
            manageEventValue(eventName, ApiUtil.toLongUnsigned(zmqCallInfo.ctr),
                    inputs[ValueIdx], littleEndian, zmqCallInfo.call_is_except);
        }
        catch (Exception e) {
            if (e instanceof DevFailed)
                throw (DevFailed) e;
            e.printStackTrace();
            Except.throw_exception("Api_CatchException",
                    "API catch a " + e.toString() + " exception",
                    "ZmqMainThread.manageEvent()");
        }
    }
    //===============================================================
    //===============================================================
    private void manageEventValue(String eventName,
                                  long eventCounter,
                                  byte[] recData,
                                  boolean littleEndian,
                                  boolean isExcept) throws  DevFailed {

        Hashtable<String, EventCallBackStruct> callbackMap = ZmqEventConsumer.getEventCallbackMap();
        if (callbackMap.containsKey(eventName)) {
            EventCallBackStruct callBackStruct = callbackMap.get(eventName);
            DeviceAttribute attributeValue  = null;
            AttributeInfoEx attributeConfig = null;
            AttDataReady    dataReady       = null;
            DevError[]      devErrorList    = null;

            //  Manage ZMQ counter (queue has reached HWM ?)
            boolean pushTheEvent =
                    manageEventCounter(callBackStruct, eventName, eventCounter);
            ZMQutils.zmqEventTrace("ZMQ event from " + eventName);

            //  Check if Value part is a DevFailed
            if (isExcept) {
                devErrorList = ZMQutils.deMarshallErrorList(recData, littleEndian);
            }
            else {
                //  Else check event type
                switch (ZMQutils.getEventType(eventName)) {
                    case TangoConst.ATT_CONF_EVENT:
                        attributeConfig = ZMQutils.deMarshallAttributeConfig(recData, littleEndian);
                        break;
                    case TangoConst.DATA_READY_EVENT:
                        dataReady = ZMQutils.deMarshallAttDataReady(recData, littleEndian);
                        break;
                    default:
                        //  ToDo needs idl version
                        Hashtable<String, EventChannelStruct> channelMap = EventConsumer.getChannelMap();
                        EventChannelStruct eventChannelStruct = channelMap.get(callBackStruct.channel_name);
                        if (eventChannelStruct!=null) {
                            int idl = eventChannelStruct.getIdlVersion();
                            attributeValue =
                                    ZMQutils.deMarshallAttribute(recData, littleEndian, idl);
                        }
                }
            }
            if (pushTheEvent) {
                //	Build and push event data
                String  deviceName = getDeviceName(eventName);
                pushEventData(callBackStruct,
                        new EventData(callBackStruct.device,
                                deviceName, eventName,
                                callBackStruct.event_type, EventData.ZMQ_EVENT,
                                attributeValue, attributeConfig, dataReady, devErrorList));
            }
        }
        else
            System.err.println(eventName + " ?  NOT FOUND");
    }
    //===============================================================
    /**
     * Manage the event counter
     * @param callBackStruct    the event callback structure
     * @param eventName         the event name
     * @param eventCounter    the event counter to manage
     * @return true if the event must be pushed.
     * @throws DevFailed if at least one event has been lost
     */
    //===============================================================
    private boolean manageEventCounter(EventCallBackStruct callBackStruct,
                                    String eventName,
                                    long eventCounter) throws DevFailed {
        long    previousCounter = callBackStruct.getZmqCounter();
        //  Is it the first call ?
        if (previousCounter==Long.MAX_VALUE) {
            callBackStruct.setZmqCounter(eventCounter);
            //  To be sure to have first event after synchronous call,
            //      wait the event pushed in dedicated thread.
            while (!callBackStruct.isSynchronousDone()) {
                try { Thread.sleep(1); } catch (InterruptedException e) { /* */ }
            }
            return true;
        }

        long    delta = eventCounter - previousCounter;
        //  If delta==0 --> already receive (ZMQ bug)
        if (delta==0) {
            callBackStruct.setZmqCounter(eventCounter);
            return false;
        }
        //  eventCounter==0  -> reconnection
        if (eventCounter==0) {
            callBackStruct.setZmqCounter(eventCounter);
            return false;
        }

        //  If delta==1 -> It is OK nothing lost
        if (delta==1) {
            callBackStruct.setZmqCounter(eventCounter);
            return true;
        }

        //  if delta<0  --> integer overflow
        if (delta<0) {
            long    maxCounter = ApiUtil.toLongUnsigned(-1);
            long    delta2 = maxCounter - delta;
            //  If delta2==1 -> It is OK nothing lost
            if (delta2==1) {
                callBackStruct.setZmqCounter(eventCounter);
                return true;
            }
        }
        //  Else
        //  At least one event has been lost, push a DevError event
        DevError[]      devErrorList    = new DevError[] {
                    new DevError("Api_MissedEvents", ErrSeverity.ERR,
                        "Missed some events ("  + eventCounter + "-" + previousCounter + ") ! ZMQ queue has reached " +
                                "HWM ?",
                        "ZmqMainThread.manageEventCounter()") };

        //	Build and push event data
        String  deviceName = getDeviceName(eventName);
        pushEventData(callBackStruct,
                new EventData(callBackStruct.device,
                        deviceName, eventName,
                        callBackStruct.event_type, EventData.ZMQ_EVENT,
                        null, null, null, devErrorList));
        callBackStruct.setZmqCounter(eventCounter);
        return true;
    }
    //===============================================================
    //===============================================================
    private void pushEventData(EventCallBackStruct callBackStruct, EventData eventData) {

        if (callBackStruct.use_ev_queue) {
            EventQueue ev_queue = callBackStruct.device.getEventQueue();
            ev_queue.insert_event(eventData);
        } else if (callBackStruct.callback != null) {
            callBackStruct.callback.push_event(eventData);
        }
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
        String  name = new String(inputs[NameIdx]);

        //  Check if name is coherent
        int start = name.indexOf("dserver/");
        if (start<0) {
            //  ToDo
            long    t = System.currentTimeMillis();
            double d = (double)t/1000;
            System.err.println(d + ":\nheartbeat: " + name + " cannot be parsed !");
            ZMQutils.dump(inputs[NameIdx]);
            return;
        }

        //  Get only device name
        int end   = name.lastIndexOf('.');
        if (end>start)
            name = name.substring(start, end);
        else
            name = name.substring(start);
        ApiUtil.printTrace("Receive Heartbeat for " + name);
        ZmqEventConsumer.getInstance().push_structured_event_heartbeat(name);

        /*  NOT USED */
        //  Second one is endianess
        if (inputs[EndianIdx].length>0) {
            //boolean littleEndian = (inputs[EndianIdx][0]!=0);
            //System.out.println("heartbeat "+ name + ":   little="+littleEndian);
        }
        else {
            System.out.println("heartbeat "+ name + ":   endianess is missing !!!");
        }
        /* */
    }
    //===============================================================
    //===============================================================
    private ArrayList<String> getConnectedEndPoints(String eventName) {
        //  Returns endpoints for specified eventName
        ArrayList<String> list = new ArrayList<String>();
        Enumeration<String> keys = connectedMap.keys();
        while (keys.hasMoreElements()) {
            String  key = keys.nextElement();
            String  event = connectedMap.get(key);
            if (event.equals(eventName)) {
                list.add(key);
            }
        }
        return list;
    }
    //===============================================================
    //===============================================================
    private boolean alreadyConnected(String endPoint) {
        return connectedMap.containsKey(endPoint);
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
                connectIfNotDone(heartbeatSocket, controlStructure);
                heartbeatSocket.subscribe(controlStructure.eventName.getBytes());
                break;


            case ZMQutils.ZMQ_DISCONNECT_HEARTBEAT:
                disconnect(heartbeatSocket, controlStructure.eventName);
                break;

            case ZMQutils.ZMQ_CONNECT_EVENT:
                connectIfNotDone(eventSocket, controlStructure);
                eventSocket.subscribe(controlStructure.eventName.getBytes());
                if (!controlStructure.forceReconnection)
                    traceZmqSubscription(controlStructure.eventName, true);
                break;

            case ZMQutils.ZMQ_DISCONNECT_EVENT:
                disconnect(eventSocket, controlStructure.eventName);
                traceZmqSubscription(controlStructure.eventName, false);
                break;

        }
    }
    //===============================================================
    //===============================================================
    private void connectIfNotDone(ZMQ.Socket socket, ZMQutils.ControlStructure controlStructure){
        //  Check if not already connected or forced (re connection)
        if (controlStructure.forceReconnection || !alreadyConnected(controlStructure.endPoint)) {
            ApiUtil.printTrace("Set socket buffer for HWM to " + controlStructure.hwm);
            //  Check if it ia a reconnection -> disconnect before connection
            //  Not available in ZMQ 3.1
            //if (controlStructure.forceReconnection && alreadyConnected(controlStructure.endPoint))
            //    socket.disconnect(controlStructure.endPoint);

            //  Do the connection
            socket.setHWM(controlStructure.hwm);
            socket.connect(controlStructure.endPoint);
            if (!alreadyConnected(controlStructure.endPoint))
                connectedMap.put(controlStructure.endPoint, controlStructure.eventName);
        }
        else {
            ApiUtil.printTrace(
                    ((controlStructure.commandCode==ZMQutils.ZMQ_CONNECT_EVENT)? "Event" : "Heartbeat") +
                    " already connected to " + controlStructure.endPoint);
        }
    }
    //===============================================================
    //===============================================================
    private void disconnect(ZMQ.Socket socket, String eventName){
        ArrayList<String>   endpoints = getConnectedEndPoints(eventName);
        socket.unsubscribe(eventName.getBytes());
        for (String endpoint : endpoints) {
            //  Not available in ZMQ 3.1
            //socket.disconnect(endpoint);
            connectedMap.remove(endpoint);
        }
    }
    //===============================================================
    //===============================================================
    private static int zmqSubscribeCounter = 0;
    private boolean traceZmqSub = false;
    private boolean traceZmqSubRead = false;
    private void traceZmqSubscription(String eventName, boolean increase) {
        if (!traceZmqSubRead) {
            String s = System.getenv("TraceSubscribe");
            traceZmqSub = (s!=null && s.equals("true"));
            traceZmqSubRead = true;
        }
        if (traceZmqSub) {
            String action;
            if (increase) {
                zmqSubscribeCounter++;
                action = "subscribe";
            }  else {
                zmqSubscribeCounter--;
                action = "unsubscribe";
            }
            System.out.println(new Date() + ":  #### " +
                    zmqSubscribeCounter + " -> " + action + " eventSocket to " + eventName);
        }
    }
}
