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

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.DeviceProxy;

import java.util.Hashtable;

//===============================================================
/**
 * A class to manage NotifdEventConsumer and ZmqEventConsumer instances
 *
 * @author pascal_verdier
 */
//===============================================================
public class EventConsumerUtil {

    private static EventConsumerUtil    instance = null;
    private static boolean zmqTested = false;
    private static boolean zmqLoadable = true;

    //===============================================================
    /**
     * Create a singleton if not already done
     * @return an instance on this singleton
     */
    //===============================================================
    public static EventConsumerUtil getInstance() {
        if (instance==null) {
            instance = new EventConsumerUtil();
        }
        return instance;
    }
    //===============================================================
    /**
     * Default constructor
     */
    //===============================================================
    private EventConsumerUtil() {
        /*
        try {
            //  To start KeepAliveThread.
            NotifdEventConsumer.getInstance();
        }
        catch (DevFailed e) {
            //  Nothing
        }
        */
    }
    //===============================================================
    /**
     *
     * @param deviceProxy device to be connected
     * @return consumer if already connected, otherwise return null
     */
    //===============================================================
    private EventConsumer isChannelAlreadyConnected(DeviceProxy deviceProxy) {
        try {
            String adminName = deviceProxy.adm_name();
            EventChannelStruct eventChannelStruct = EventConsumer.getChannelMap().get(adminName);
            if (eventChannelStruct==null) {
                return null;
            }
            else {
                return eventChannelStruct.consumer;
            }
        }
        catch (DevFailed e) {
            return null;
        }
    }
    //===============================================================
    /**
     * Check if zmq is loadable:
     *  - JNI library must be in LD_LIBRARY_PATH
     *  - zmq jar file must be in CLASSPATH
     *
     * @return true if ZMQ could be loaded correctly
     */
    //===============================================================
    public static boolean isZmqLoadable(){
         if (!zmqTested) {
			String	zmqEnable = System.getenv("ZMQ_DISABLE");
			if (zmqEnable==null)
				zmqEnable = System.getProperty("ZMQ_DISABLE");

			if (zmqEnable==null || !zmqEnable.equals("true")) {
            	try {
                	ZMQutils.getInstance();
                    System.out.println("========== ZMQ (" + ZMQutils.getZmqVersion() +
                            ") event system is available ============");
            	}
            	catch(UnsatisfiedLinkError | NoClassDefFoundError error) {
                    System.err.println("======================================================================");
                	System.err.println("  "+error);
                	System.err.println("  Event system will be available only for notifd notification system ");
                	System.err.println("======================================================================");
                	zmqLoadable = false;
            	}
			}
			else {
                System.err.println("======================================================================");
                System.err.println("  ZMQ event system not enabled");
                System.err.println("  Event system will be available only for notifd notification system ");
                System.err.println("======================================================================");
                zmqLoadable = false;
			}
            zmqTested = true;
        }
        return zmqLoadable;
   }

    //===============================================================
    /**
     * Subscribe on specified event
     *
     * @param device    specified device
     * @param attribute specified attribute
     * @param event     specified event type
     * @param callback  callback class
     * @param filters   filters (not used with zmq)
     * @param stateless stateless subscription if true
     * @return  event ID.
     * @throws DevFailed if subscription failed
     */
    //===============================================================
    public int subscribe_event(DeviceProxy device,
                               String attribute,
                               int event,
                               CallBack callback,
                               String[] filters,
                               boolean stateless)
            throws DevFailed {
        return subscribe_event(device, attribute, event, callback, -1, filters, stateless);
    }

    //===============================================================
    /**
     * Subscribe on specified event
     *
     * @param device    specified device
     * @param attribute specified attribute
     * @param event     specified event type
     * @param max_size  maximum size for event queue
     * @param filters   filters (not used with zmq)
     * @param stateless stateless subscription if true
     * @return  event ID.
     * @throws DevFailed if subscription failed
     */
    //===============================================================
    public int subscribe_event(DeviceProxy device,
                               String attribute,
                               int event,
                               int max_size,
                               String[] filters,
                               boolean stateless)
            throws DevFailed {
        return subscribe_event(device, attribute, event, null, max_size, filters, stateless);
    }

    //===============================================================
    /**
     * Subscribe on specified event
     *
     * @param device    specified device
     * @param attribute specified attribute
     * @param event     specified event type
     * @param callback  callback class
     * @param max_size  maximum size for event queue
     * @param filters   filters (not used with zmq)
     * @param stateless stateless subscription if true
     * @return  event ID.
     * @throws DevFailed if subscription failed
     */
    //===============================================================
    public int subscribe_event(DeviceProxy device,
                               String attribute,
                               int event,
                               CallBack callback,
                               int max_size,
                               String[] filters,
                               boolean stateless) throws DevFailed {
        ApiUtil.printTrace("trying to subscribe_event to " + device.name() + "/" + attribute);
        int id;
        //  If already connected, subscribe directly on same channel
        EventConsumer   consumer = isChannelAlreadyConnected(device);
        if (consumer!=null) {
            id = consumer.subscribe_event(device,
                    attribute, event, callback, max_size, filters, stateless);
        }
        else
        if (isZmqLoadable()) {
            try {
                //  If ZMQ jni library can be loaded, try to connect on ZMQ event system
                id = ZmqEventConsumer.getInstance().subscribe_event(device,
                        attribute, event, callback, max_size, filters, stateless);
            }
            catch (DevFailed e) {
                ApiUtil.printTrace(e.errors[0].desc);
                if (e.errors[0].desc.equals(ZMQutils.SUBSCRIBE_COMMAND_NOT_FOUND)) {
                    //  If not a ZMQ server, try on notifd system.
                    id = subscribeEventWithNotifd(device, attribute,
                            event, callback, max_size, filters,stateless);
                }
                else
                    throw e;
            }
        }
        else {
            //  If there is no ZMQ jni library loadable, try on notifd system.
            id = subscribeEventWithNotifd(device, attribute,
                    event, callback, max_size, filters,stateless);
        }

        return id;
    }
    //===============================================================
    /**
     * Subscribe on specified event
     *
     * @param device    specified device
     * @param event     specified event type
     * @param callback  callback class
     * @param max_size  maximum size for event queue
     * @param stateless stateless subscription if true
     * @return  event ID.
     * @throws DevFailed if subscription failed
     */
    //===============================================================
    public int subscribe_event(DeviceProxy device,
                               int event,
                               CallBack callback,
                               int max_size,
                               boolean stateless) throws DevFailed {
        ApiUtil.printTrace("INTERFACE_CHANGE: trying to subscribe_event to " + device.name());
        int id;
        //  If already connected, subscribe directly on same channel
        EventConsumer   consumer = isChannelAlreadyConnected(device);
        if (consumer!=null) {
            id = consumer.subscribe_event(device, event, callback, max_size, stateless);
        }

        //  If ZMQ jni library can be loaded, try to connect on ZMQ event system
        id = ZmqEventConsumer.getInstance().subscribe_event(
                device, event, callback, max_size, stateless);

        return id;
    }
    //===============================================================
    /**
     * Subscribe on specified event using notifd
     *
     * @param device    specified device
     * @param attribute specified attribute
     * @param event     specified event type
     * @param callback  callback class
     * @param max_size  maximum size for event queue
     * @param filters   filters (not used with zmq)
     * @param stateless stateless subscription if true
     * @return  event ID.
     * @throws DevFailed if subscription failed
     */
    //===============================================================
    private int subscribeEventWithNotifd(DeviceProxy device,
                                   String attribute,
                                   int event,
                                   CallBack callback,
                                   int max_size,
                                   String[] filters,
                                   boolean stateless) throws DevFailed {

        int id;
        id = NotifdEventConsumer.getInstance().subscribe_event(device,
                attribute, event, callback, max_size, filters, stateless);
        ApiUtil.printTrace(device.name() + "/" + attribute + "  connected to Notifd event system");
        return id;
    }
    //===============================================================
    /**
     * Un subscribe event
     * @param event_id  specified event ID
     * @throws DevFailed if un subscribe failed or event not found
     */
    //===============================================================
    public void unsubscribe_event(int event_id) throws DevFailed {

        //  Get the map
        Hashtable<String, EventCallBackStruct>
                callBackMap = EventConsumer.getEventCallbackMap();

        //  Get the callback structure
        EventCallBackStruct callbackStruct =
                EventConsumer.getCallBackStruct(callBackMap, event_id);

        //  Unsubscribe on EventConsumer object
        if (callbackStruct!=null)
            callbackStruct.consumer.unsubscribe_event(event_id);
    }
    //===============================================================
    //===============================================================
}
