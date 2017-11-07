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

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.zeromq.ZMQ;

import java.util.*;

/**
 *	This class is a thread class to manage ZMQ event receptions
 *
 * @author  verdier
 */
public class ZmqMainThread extends Thread {
    private static final int    HearBeatSock = 0;
    private static final int    EventSock    = 1;
    private static final int    ControlSock  = 2;

    private ZMQ.Socket  controlSocket;
    private ZMQ.Socket  heartbeatSocket;
    private ZMQ.Socket  eventSocket;
    private ZmqPollers pollers;
    private boolean     stop = false;
    /** Map<endPoint, eventList> */
    private Hashtable<String,EventList> connectedMap = new Hashtable<>();

    private int heartbeatDrift = 0;
    private int eventDrift     = 0;

    private static final int NameIdx    = 0;
    private static final int EndianIdx  = 1;
    private static final int ZmqInfoIdx = 2;
    private static final int ValueIdx   = 3;
    private static final int NbFields   = ValueIdx+1;

    private static final long SendHwmSocket = 10000;
    //===============================================================
    private class ZmqPollers extends ZMQ.Poller {
        private ZmqPollers(ZMQ.Context context, int size) {
            super(context, size);
        }
    }
    //===============================================================
    //===============================================================
    /**
     * Default constructor
     * @param context ZMQ context instance
     */
    //===============================================================
    ZmqMainThread(ZMQ.Context context) {

        this.setName("ZmqMainThread");
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
            //System.out.println("IVL set to " + longDelay);
            heartbeatSocket.setReconnectIVL(longDelay);
            eventSocket.setReconnectIVL(longDelay);
        }

        // Initialize poll set
        //pollers = context.poller(3);
        pollers = new ZmqPollers(context, 3);
        pollers.register(heartbeatSocket, ZMQ.Poller.POLLIN);
        pollers.register(eventSocket, ZMQ.Poller.POLLIN);
        pollers.register(controlSocket, ZMQ.Poller.POLLIN);
    }
    //===============================================================
    /**
     * The thread infinite loop.
     * Wait on sockets, and dispatch when message has been received
     */
    //===============================================================
    public void run() {

        while (!stop) {
            try {
                //  Poll the sockets inputs
                pollers.poll();

                //  read the speaking one
                for (int i=0 ; i<pollers.getSize() ; i++) {
                    if (pollers.pollin(i)) {
                        manageInputBuffer(i);
                    }
                }
            }
            catch (Error | Exception e) {
                e.printStackTrace();
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

        //  Try to resynchronize if drifts
        if (socket==heartbeatSocket) {
            if (heartbeatDrift>0) {
                System.err.println("------> try to resynchronize heartbeat (" + heartbeatDrift + ")");
                for (int i=0 ; i<heartbeatDrift ; i++)
                    heartbeatSocket.recv(0);
                heartbeatDrift = 0;
            }
        }
        else
        if (socket==eventSocket) {
            if (eventDrift>0) {
                System.err.println("------> try to resynchronize event (" + eventDrift + ")");
                for (int i=0 ; i<eventDrift ; i++)
                    eventSocket.recv(0);
                eventDrift = 0;
            }
        }


        //  Read the socket for nb blocks
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
        //System.out.println(System.currentTimeMillis() + " manageInputBuffer -> "+source);
        switch (source) {
            case ControlSock:
                //System.out.println(System.currentTimeMillis() + " - receive Control");
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
                //System.out.println(System.currentTimeMillis() + " - receive heartbeat");
                try {
                    //  Read input from socket (in 3 parts)
                    byte[][] inputs = readSocket(heartbeatSocket, 3);
                    manageHeartbeat(inputs);
                }
                catch (DevFailed e) {
                    Except.print_exception(e);
                }
                break;

            case EventSock:
                //System.out.println(System.currentTimeMillis() + " - receive event  -----------------");
                try {
                    byte[][] inputs = readSocket(eventSocket, 4);
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
    @SuppressWarnings("unused")
    private String getEventName(byte[] inputs) {
        String  s = new String(inputs);
        //  Remove Tango host
        int pos = s.lastIndexOf('.');
        for (int i=0 ; i<4 ; i++)
            pos = s.lastIndexOf('/', pos-1);
        return s.substring(pos+1);
    }
    //===============================================================
    //===============================================================
    private void checkEventMessage(byte[][] inputs) throws Exception {

        if (inputs.length<NbFields) {   // || inputs[EndianIdx].length==0) {
            System.err.println("NbFields=" + NbFields);
            eventDrift = 4 - inputs.length;
            Except.throw_exception("Api_BadParameterException",
                    "Cannot decode event  (message size !)",
                    "ZmqMainThread.checkEventMessage()");
        }
        if (new String(inputs[NameIdx]).startsWith("tango://")) {
            //if (new String(inputs[NameIdx]).toLowerCase().contains("position"))
            //    System.out.println("checkEventMessage(): Receive " +new String(inputs[NameIdx]));
            return; //  OK
        }

        //  Check what is first input ?
        byte[]  bytes = inputs[NameIdx];
        if (bytes.length==1) {
            //  Endianess
            eventDrift = 3;
            Except.throw_exception("Api_BadParameterException",
                    "Cannot decode event  (start with endianess)",
                    "ZmqMainThread.checkEventMessage()");
        }
        else
        if (bytes[0]==0xc && bytes[1]==0x0 && bytes[2]==0xd && bytes[3]==0xe) {
            //  DATA
            eventDrift = 1;
            Except.throw_exception("Api_BadParameterException",
                    "Cannot decode event  (start with data)",
                    "ZmqMainThread.checkEventMessage()");
        }
        else {
            //  Specifications
            eventDrift = 2;
            Except.throw_exception("Api_BadParameterException",
                    "Cannot decode event  (start with specifications)",
                    "ZmqMainThread.checkEventMessage()");
        }
    }
    //===============================================================
    /**
     * Manage events. Extract data and push to callback
     * @param inputs received messages
     * @throws DevFailed if cannot extract event type or data from messages.
     */
    //===============================================================
    private void manageEvent(byte[][] inputs) throws DevFailed {

        try {
            //  Check if inputs are coherent
            checkEventMessage(inputs);

            //  Decode receive data parts
            //String  eventName = getEventName(inputs[NameIdx]);
            String  eventName = new String(inputs[NameIdx]);
            boolean littleEndian = true;
            if (inputs[EndianIdx].length>0) {
                //  Sometimes inputs[EndianIdx] could be empty (fixed in c++ 8.1)
                littleEndian = (inputs[EndianIdx][0]!=0);
            }
            ZmqCallInfo zmqCallInfo =
                    ZMQutils.deMarshallZmqCallInfo(inputs[ZmqInfoIdx], littleEndian);
            if (zmqCallInfo!=null) {
                manageEventValue(eventName, ApiUtil.toLongUnsigned(zmqCallInfo.ctr),
                        inputs[ValueIdx], littleEndian, zmqCallInfo.call_is_except);
            }
            else
                throw new Exception("DeMarshalling returns null");
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
    private EventCallBackStruct getEventCallBackStruct(String eventName) {
        List<String> possibleTangoHosts = EventConsumer.possibleTangoHosts;
        Hashtable<String, EventCallBackStruct> callbackMap = ZmqEventConsumer.getEventCallbackMap();
        if (callbackMap.containsKey(eventName)) {
            return callbackMap.get(eventName);
        }
        //  Check with other TangoHosts using possibleTangoHosts as header
        int index = eventName.indexOf("//");
        if (index>0) {
            index = eventName.indexOf('/', index+2); //  "//".length()
            for (String possibleTangoHost : possibleTangoHosts) {
                String key = possibleTangoHost + eventName.substring(index);
                if (callbackMap.containsKey(key)) {
                    return callbackMap.get(key);
                }
            }
        }

        //  Not found
        /*  Display table content
		if (eventName.contains("maxtempchange")) {
			System.err.println("===============================================================");
			System.err.println(eventName + " NOT FOUND");
			System.err.println("keys are:");
        	Enumeration<String> keys = callbackMap.keys();
        	while (keys.hasMoreElements()) {
            	System.err.println(keys.nextElement());
        	}
		}
        */
        return null;
    }
    //===============================================================
    //===============================================================
    private void manageEventValue(String eventName,
                                  long eventCounter,
                                  byte[] recData,
                                  boolean littleEndian,
                                  boolean isExcept) throws  DevFailed {
        //System.out.println("Event name  = " + eventName);
        EventCallBackStruct callBackStruct = getEventCallBackStruct(eventName);
        if (callBackStruct!=null) {
            DeviceAttribute attributeValue  = null;
            DevicePipe      devicePipe      = null;
            AttributeInfoEx attributeConfig = null;
            AttDataReady    dataReady       = null;
            DeviceInterface deviceInterface = null;
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
                Hashtable<String, EventChannelStruct> channelMap = EventConsumer.getChannelMap();
                EventChannelStruct eventChannelStruct = channelMap.get(callBackStruct.channel_name);
                if (eventChannelStruct!=null) {
                    try {
                        //  Needs idl version to de marshall
                        int idl = callBackStruct.device.get_idl_version();

                        //  Else check event type
                        switch (ZMQutils.getEventType(eventName)) {
                            case TangoConst.ATT_CONF_EVENT:
                                attributeConfig =
                                        ZMQutils.deMarshallAttributeConfig(recData, littleEndian, idl);
                                break;
                            case TangoConst.PIPE_EVENT:
                                devicePipe = ZMQutils.deMarshallPipe(recData, littleEndian, idl);
                                break;
                            case TangoConst.DATA_READY_EVENT:
                                dataReady = ZMQutils.deMarshallAttDataReady(recData, littleEndian);
                                break;
                            case TangoConst.INTERFACE_CHANGE:
                                deviceInterface = ZMQutils.deMarshallAttInterfaceChange(recData, littleEndian);
                                break;
                            default:
                                attributeValue = ZMQutils.deMarshallAttribute(recData, littleEndian, idl);
                         }
                    }
                    catch(DevFailed e) {
                        //  convert de marshall
                        devErrorList = e.errors;
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
                                attributeValue, devicePipe,
                                attributeConfig, dataReady,
                                deviceInterface, devErrorList));
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
            //  There is NO synchronous call for DataReady event !!!!
            if (callBackStruct.event_name.equals(
                    TangoConst.eventNames[TangoConst.DATA_READY_EVENT])) {
                callBackStruct.setSynchronousDone(true);
            }
            //  To be sure to have first event after synchronous call,
            //      wait the event pushed in dedicated thread.
            int timeout = 5000;
            for (int i=0 ; !callBackStruct.isSynchronousDone() && i<timeout ; i++) {
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
        //  eventCounter<=0  -> reconnection
        if (eventCounter<=0) {
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
        long nb = eventCounter - (previousCounter+1);
        DevError[]      devErrorList    = new DevError[] {
                    new DevError("Api_MissedEvents", ErrSeverity.ERR,
                        "Missed " + nb + " events ("  +
                                eventCounter + "-" + (previousCounter+1) +
                                ") ! ZMQ queue has reached HWM or resynchronize ?",
                        "ZmqMainThread.manageEventCounter()") };

        //	Build and push event data
        String  deviceName = getDeviceName(eventName);
        pushEventData(callBackStruct,
                new EventData(callBackStruct.device,
                        deviceName, eventName,
                        callBackStruct.event_type, EventData.ZMQ_EVENT,
                        null, null, null, null, null, devErrorList));
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
        //System.out.println("heartbeat : " + name);

        //  Check if name is coherent
        int start = name.indexOf("dserver/");
        if (start<0) {
            //  ToDo
            long    t = System.currentTimeMillis();
            System.err.println(formatTime(t) + ":\n heartbeat: " + name +
                    " cannot be parsed ! length=" + inputs[NameIdx].length);
            ZMQutils.dump(inputs[NameIdx]);

            //  Check if endianess or specif
            if (inputs[NameIdx].length==1)  //  Endianess
                heartbeatDrift = 2;
            else
                heartbeatDrift = 1;
            return;
        }

        //  Get only device name (without event type.
        int end   = name.lastIndexOf('.');
        name = name.substring(0, end);
        //System.out.println("-----------> Receive Heartbeat for " + name);
        ZmqEventConsumer.getInstance().push_structured_event_heartbeat(name);

        //  Second one is endianess
        if (inputs[EndianIdx].length==0) {
            System.err.println("heartbeat "+ name + ":   endianess is missing !!!");
        }
        /*
        else {
             NOT USED
        }
        */
    }
    //===============================================================
    //===============================================================
    private String getConnectedEndPoint(String eventName) {
        //  Returns endpoint for specified eventName
        Enumeration<String> keys = connectedMap.keys();
        while (keys.hasMoreElements()) {
            String  key = keys.nextElement();
            EventList  events = connectedMap.get(key);
            for (String event : events) {
                if (event.equals(eventName)) {
                    return key;
                }
            }
        }
        return null;
    }
    //===============================================================
    //===============================================================
    @SuppressWarnings("unused")
    private boolean isForcedJustified(ZMQutils.ControlStructure controlStructure) {
        EventList events = connectedMap.get(controlStructure.endPoint);
        if (events==null)
            return true;
        //noinspection SimplifiableIfStatement
        if (events.size()==0)
            return true;// force to be added.

        //  To reconnect just one time
        //  return true only for first one (false for other)
        return events.get(0).equals(controlStructure.eventName);
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
				//System.out.println("-------> ZMQ_CONNECT_HEARTBEAT: " + controlStructure.eventName);
                heartbeatSocket.subscribe(controlStructure.eventName.getBytes());
                break;


            case ZMQutils.ZMQ_DISCONNECT_HEARTBEAT:
                disconnect(heartbeatSocket, controlStructure.eventName);
                break;

            case ZMQutils.ZMQ_CONNECT_EVENT:
                connectIfNotDone(eventSocket, controlStructure);
                eventSocket.subscribe(controlStructure.eventName.getBytes());
                break;

            case ZMQutils.ZMQ_DISCONNECT_EVENT:
                disconnect(eventSocket, controlStructure.eventName);
                break;

        }
    }
    //===============================================================
    //===============================================================
    private void connectIfNotDone(ZMQ.Socket socket, ZMQutils.ControlStructure controlStructure){

        traceZmqSubscription(controlStructure.eventName, true);
        //  Check if not already connected or forced (re connection)
        if (controlStructure.forceReconnection || !alreadyConnected(controlStructure.endPoint)) {
            ApiUtil.printTrace("Set socket buffer for HWM to " + controlStructure.hwm);

            //  Check if it ia a reconnection -> disconnect before connection
            if (controlStructure.forceReconnection && alreadyConnected(controlStructure.endPoint)) {
                try {
                    //  needs an un subscribe before disconnection
                    //socket.unsubscribe(controlStructure.eventName.getBytes());
                    socket.disconnect(controlStructure.endPoint);
                }
                catch (org.zeromq.ZMQException e) {
                    System.err.println(e.getMessage());
                }
            }

            //  Do the connection
            //System.out.println("Connect on " + controlStructure.endPoint);
            //System.out.println("        for " + controlStructure.eventName);
            socket.setHWM(controlStructure.hwm);
            socket.connect(controlStructure.endPoint);
            if (!alreadyConnected(controlStructure.endPoint)) {
                EventList eventList = new EventList();
                eventList.add(controlStructure.eventName);
                connectedMap.put(controlStructure.endPoint, eventList);
            }
            else {
                //  Add to event list if not done
                EventList eventList = connectedMap.get(controlStructure.endPoint);
                String  s = eventList.getEvent(controlStructure.eventName);
                if (s==null)
                    eventList.add(controlStructure.eventName);
            }
        }
        else {
            //  Add to event list if not done
            EventList eventList = connectedMap.get(controlStructure.endPoint);
            String  s = eventList.getEvent(controlStructure.eventName);
            if (s==null)
                eventList.add(controlStructure.eventName);
            ApiUtil.printTrace(
                    ((controlStructure.commandCode==ZMQutils.ZMQ_CONNECT_EVENT)? "Event" : "Heartbeat") +
                    " already connected to " + controlStructure.endPoint);
        }
    }
    //===============================================================
    //===============================================================
    private void disconnect(ZMQ.Socket socket, String eventName){
        String   endpoint = getConnectedEndPoint(eventName);
        if (endpoint!=null) {
            EventList eventList = connectedMap.get(endpoint);
            if (eventList!=null) {
                socket.unsubscribe(eventName.getBytes());
                traceZmqSubscription(eventName, false);
                eventList.remove(eventName);
                if (eventList.size()==0) {
                    socket.disconnect(endpoint);
                    connectedMap.remove(endpoint);
                }
            }
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
            } else {
                zmqSubscribeCounter--;
                action = "unsubscribe";
            }
            System.out.println(new Date() + ":  #### " +
                    zmqSubscribeCounter + " -> " + action + " eventSocket to " + eventName);
        }
    }
    //===============================================================
    //===============================================================
    private static String formatTime(long ms)
    {
        StringTokenizer st = new StringTokenizer(new Date(ms).toString());
        ArrayList<String>	arrayList = new ArrayList<>();
        while (st.hasMoreTokens())
            arrayList.add(st.nextToken());

        String  time  = arrayList.get(3);
        double d = (double)ms/1000;
        long   l = ms/1000;
        d = (d - l) * 1000;
        ms = (long) d;

        return time + "." + ms;
    }


    //===============================================================
    //===============================================================
    private class EventList extends ArrayList<String> {
        private String getEvent(String eventName) {
            for (String event : this) {
                if (event.equals(eventName)) {
                    return event;
                }
            }
            return null;
        }
    }
    //===============================================================
    //===============================================================
}
