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
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CosNotification.EventType;
import org.omg.CosNotifyChannelAdmin.*;
import org.omg.CosNotifyFilter.ConstraintExp;
import org.omg.CosNotifyFilter.Filter;
import org.omg.CosNotifyFilter.FilterFactory;
import org.omg.CosNotifyFilter.FilterNotFound;

import java.util.Enumeration;


/**
 * @author pascal_verdier
 */
public class NotifdEventConsumer extends EventConsumer implements TangoConst, Runnable, IEventConsumer {

    private static NotifdEventConsumer instance = null;
    private EventChannel eventChannel;
    private ConsumerAdmin consumerAdmin;
    private ProxySupplier proxySupplier;

    private ORB orb;
    private Thread runner;
    private boolean orbRunning = false;

    //===============================================================
    /**
     * Creates a new instance of EventConsumer
     *
     * @return an instance of EventConsumer object
     * @throws DevFailed in case of database connection failed.
     */
    //===============================================================
    public static NotifdEventConsumer create() throws DevFailed {
        if (instance == null) {
            instance = new NotifdEventConsumer();
        }
        return instance;
    }
    //===============================================================
    public static NotifdEventConsumer getInstance() throws DevFailed {
        if (instance == null) {
            instance = new NotifdEventConsumer();
        }
        return instance;
    }
    //===============================================================
    //===============================================================
    private NotifdEventConsumer() throws DevFailed {

        super();
        orb = ApiUtil.get_orb();
        runner = new Thread(this);
        runner.setName("NotifdEventConsumer");

        //	Create a thread and start it
        Runtime.getRuntime().addShutdownHook(

                new Thread() {
                    public void run() {
                        System.out.println("======== Shutting down notifd event system =======");
                        cleanup_heartbeat_filters();
                        cleanup_event_filters();
                        cleanup_heartbeat_proxies();
                        KeepAliveThread.getInstance().stopThread();
                        if (orbRunning) {
                            // Shutdown the ORB and wait for Completion
                            orb.shutdown(true);
                            try {
                                runner.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //System.out.println("ORB Shutdown");
                        }
                    }
                }
        );
        runner.start();
    }
    //===============================================================
    /**
     * activate POA and go into endless loop waiting for events to be pushed
     */
    //===============================================================
    public void run() {

        try {
            if (!ApiUtil.in_server()) {
                // We need to serialize here as the event subscription
                // thread needs also to access the poa
                synchronized (this) {
                    org.omg.CORBA.Object obj =
                            orb.resolve_initial_references("RootPOA");
                    org.omg.PortableServer.POA poa =
                            org.omg.PortableServer.POAHelper.narrow(obj);
                    org.omg.PortableServer.POAManager pman =
                            poa.the_POAManager();
                    pman.activate();
                }
                orbRunning = true;
                orb.run();
                orb.destroy();
            }
        } catch (org.omg.CORBA.UserException ex) {
            System.err.println("EventConsumer.run() : Unable to start orb");
            ex.printStackTrace();
            System.exit(1);
        }

    }
    //===============================================================
    //===============================================================
    public int subscribe_event(DeviceProxy device,
                               int event,
                               CallBack callback,
                               int max_size,
                               boolean stateless)
            throws DevFailed {
        //  Cannot be used with notifd (IDL 5 only)
        return 0;
    }
    //===============================================================
    //===============================================================
    private java.lang.Object extractAttributeObject(org.omg.CosNotification.StructuredEvent notification)
            throws org.omg.CORBA.TypeCodePackage.BadKind {
        TypeCode ty = notification.remainder_of_body.type();
        if (ty.kind().equals(TCKind.tk_struct)) {
            String ty_name = ty.name();
            if (ty_name.equals("AttDataReady"))
                return AttDataReadyHelper.extract(notification.remainder_of_body);
            else if (ty_name.equals("AttributeConfig_3"))
                return new AttributeInfoEx(
                        AttributeConfig_3Helper.extract(notification.remainder_of_body));
            else if (ty_name.equals("AttributeConfig_2"))
                return new AttributeInfoEx(
                        AttributeConfigHelper.extract(notification.remainder_of_body));
            else if (ty_name.equals("AttributeValue_4"))
                return new DeviceAttribute(
                        AttributeValue_4Helper.extract(notification.remainder_of_body));
            else if (ty_name.equals("AttributeValue_3"))
                return new DeviceAttribute(
                        AttributeValue_3Helper.extract(notification.remainder_of_body));
            else if (ty_name.equals("AttributeValue"))
                return new DeviceAttribute(
                        AttributeValueHelper.extract(notification.remainder_of_body));
            else {
                DevError[] dev_err_list = new DevError[1];
                dev_err_list[0] = new DevError("API_IncompatibleAttrDataType",
                        ErrSeverity.ERR,
                        "Unknown structure used to pass attribute value (Need compilation ?)",
                        "EventConsumer::extractAttributeObject()");
                return dev_err_list;
            }
        } else
            return DevErrorListHelper.extract(notification.remainder_of_body);
    }

    //===============================================================
    //===============================================================
    private EventCallBackStruct getEventCallBackStruct(String eventName) {
        Enumeration keys = event_callback_map.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            //  Notifd do not use tango host
            int start = key.indexOf('/', "tango:// ".length());
            String shortName = key.substring(start+1);
            if (eventName.equalsIgnoreCase(shortName)) {
                return event_callback_map.get(key);
            }
        }
        return null;
    }
    //===============================================================
    //===============================================================
    public void push_structured_event(org.omg.CosNotification.StructuredEvent notification) {

        String domainName = notification.header.fixed_header.event_type.domain_name;
        String eventType = notification.header.fixed_header.event_name;
        try {
            //	Check if Heartbeat event
            if (eventType.equals("heartbeat")) {
                push_structured_event_heartbeat(domainName);
                return;
            }
            //	Else check if event is registered and get its CB struct
            String eventName = domainName + "." + eventType;
            EventCallBackStruct callBackStruct = getEventCallBackStruct(eventName);
            if (callBackStruct!=null) {
                CallBack callback = callBackStruct.callback;
                DeviceAttribute attr_value = null;
                AttributeInfoEx attr_config = null;
                AttDataReady data_ready = null;
                DevError[] dev_err_list = null;

                //	Extract CORBA object to check What kind (Value or config)
                java.lang.Object obj = extractAttributeObject(notification);
                if (obj instanceof AttributeInfoEx)
                    attr_config = (AttributeInfoEx) obj;
                else if (obj instanceof AttDataReady)
                    data_ready = (AttDataReady) obj;
                else if (obj instanceof DeviceAttribute)
                    attr_value = (DeviceAttribute) obj;
                else if (obj instanceof DevError[])
                    dev_err_list = (DevError[]) obj;

                //	And build event data
                EventData event_data =
                        new EventData(callBackStruct.device,
                                domainName, eventType,
                                callBackStruct.event_type, EventData.NOTIFD_EVENT,
                                attr_value, null, attr_config, data_ready, null, dev_err_list);


                if (callBackStruct.use_ev_queue) {
                    EventQueue ev_queue = callBackStruct.device.getEventQueue();
                    ev_queue.insert_event(event_data);
                } else if (callback != null)
                    callback.push_event(event_data);
            }
            else
                System.err.println(eventName + " event not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //===============================================================
    //===============================================================
    private void cleanup_event_filters() {
        Enumeration keys = event_callback_map.keys();
        while (keys.hasMoreElements()) {
            String name = (String) keys.nextElement();
            EventCallBackStruct callback_struct = event_callback_map.get(name);
            if (callback_struct.consumer instanceof NotifdEventConsumer) {
                try {
                    EventChannelStruct ec_struct = channel_map.get(callback_struct.channel_name);
                    Filter filter = ec_struct.structuredProxyPushSupplier.get_filter(callback_struct.filter_id);
                    ec_struct.structuredProxyPushSupplier.remove_filter(callback_struct.filter_id);
                    filter.destroy();
                } catch (FilterNotFound e) {
                    // Do nothing
                }
            }
        }
    }

    //===============================================================
    //===============================================================
    private void cleanup_heartbeat_proxies() {
        // disconnect the pushsupplier to stop receiving events
        Enumeration keys = event_callback_map.keys();
        while (keys.hasMoreElements()) {
            String name = (String) keys.nextElement();
            EventCallBackStruct callback_struct = event_callback_map.get(name);
            try {
                EventChannelStruct ec_struct = channel_map.get(callback_struct.channel_name);
                if (ec_struct.structuredProxyPushSupplier != null) {
                    //System.out.println("Disconnect " + callback_struct.channel_name);
                    ec_struct.structuredProxyPushSupplier.disconnect_structured_push_supplier();
                    ec_struct.structuredProxyPushSupplier = null;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            event_callback_map.remove(name);
        }
    }

    //===============================================================
    //===============================================================
    private DbEventImportInfo getEventImportInfo(String channelName,
                                                 Database dbase,
                                                 DeviceProxy adminDevice)
                                                 throws DevFailed {
        DbEventImportInfo received = null;
        try {

            if (dbase != null) {
                //	Check if info have been set to the device
                adminDevice = DeviceProxyFactory.get(channelName, dbase.getUrl().getTangoHost());
                received = adminDevice.get_evt_import_info();
                if (received == null || !received.channel_exported) {
                    received = dbase.import_event(channelName);
                }
            } else {
                //	Get event chanel info object from admin device
                received = new DbEventImportInfo();
                adminDevice = DeviceProxyFactory.get(channelName);

                DeviceData data = adminDevice.command_inout("QueryEventChannelIOR");
                received.channel_ior = data.extractString();
                received.channel_exported = true;

                // get the hostname where the notifyd should be running
                IORdump id = new IORdump(null, received.channel_ior);
                received.host = id.get_hostname();
            }
        } catch (DevFailed df) {
            //Except.print_exception(df);
            if (dbase != null)
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        channelName + " has no event channel defined in the database " +
                                dbase.getUrl().getTangoHost() +
                                "\nMay be the server is not running.",
                        "EventConsumer.connect_event_channel");
            else
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        channelName + " did not returned event channel IOR\n"
                                + " May be the server is not running.",
                        "EventConsumer.connect_event_channel");
        }
        return received;
    }
    //===============================================================
    //===============================================================
    @Override
    protected  void checkDeviceConnection(DeviceProxy device,
                        String attribute, DeviceData deviceData, String event_name) throws DevFailed {

        String deviceName = device.name();
        if (!device_channel_map.containsKey(deviceName)) {
            connect(device, attribute, event_name, deviceData);
            if (!device_channel_map.containsKey(deviceName)) {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        "Failed to connect to event channel for device",
                        "EventConsumer.subscribe_event()");
            }
        }
    }
    //===============================================================
    //===============================================================
    private void connectToNotificationDaemon(DbEventImportInfo received)
                throws DevFailed {

        boolean channel_exported = received.channel_exported;
        if (channel_exported) {
            org.omg.CORBA.Object event_channel_obj = orb.string_to_object(received.channel_ior);
            try {
                eventChannel = EventChannelHelper.narrow(event_channel_obj);
                //  Set timeout on eventChannel
                final org.omg.CORBA.Policy p =
                        new org.jacorb.orb.policies.RelativeRoundtripTimeoutPolicy(10000 * 3000);
                eventChannel._set_policy_override(
                        new Policy[] { p }, org.omg.CORBA.SetOverrideType.ADD_OVERRIDE);

            } catch (RuntimeException e) {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        "Failed to connect notification daemon (hint : make sure the notifd daemon is running on this host",
                        "EventConsumer.connect_event_channel");
            }
            if (eventChannel == null) {
                channel_exported = false;
            }
        }
        if (!channel_exported) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to narrow EventChannel from notification daemon (hint : make sure the notifd daemon is running on this host",
                    "EventConsumer.connect_event_channel");
        }
        // Obtain a consumer admin : we'll use the channel's default consumer admin
        try {
            consumerAdmin = eventChannel.default_consumer_admin();
        } catch (Exception e) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Received " + e.toString() +
                            "\nduring eventChannel.default_consumer_admin() call",
                    "EventConsumer.connect_event_channel");

        }
        if (consumerAdmin == null) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to get default consumer admin from notification daemon (hint : make sure the notifd daemon is running on this host",
                    "EventConsumer.connect_event_channel");
        }

        // Obtain a ProxySupplier : we are using Push model and Structured data
        org.omg.CORBA.IntHolder pId = new org.omg.CORBA.IntHolder();
        try {
            proxySupplier = consumerAdmin.obtain_notification_push_supplier(ClientType.STRUCTURED_EVENT, pId);
            if (proxySupplier == null) {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                        "Failed to get a push supplier from notification daemon (hint : make sure the notifd daemon is running on this host",
                        "EventConsumer.connect_event_channel");
            }
        } catch (org.omg.CORBA.TIMEOUT ex) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to get a push supplier due to a Timeout",
                    "EventConsumer.connect_event_channel");
        } catch (org.omg.CosNotifyChannelAdmin.AdminLimitExceeded ex) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to get a push supplier due to AdminLimitExceeded (hint : make sure the notifd daemon is running on this host",
                    "EventConsumer.connect_event_channel");
        }
    }
    //===============================================================
    //===============================================================
    private StructuredProxyPushSupplier getStructuredProxyPushSupplier(String channelName)
                    throws DevFailed {
        StructuredProxyPushSupplier structuredProxyPushSupplier =
                StructuredProxyPushSupplierHelper.narrow(proxySupplier);
        if (structuredProxyPushSupplier == null) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to narrow the push supplier due to AdminLimitExceeded (hint : make sure the notifd daemon is running on this host",
                    "EventConsumer.connect_event_channel");
            return null;    //  Just to remove warning
        }
        // Connect to the proxy consumer
        try {
            structuredProxyPushSupplier.connect_structured_push_consumer(_this(orb));
        } catch (NullPointerException e) {
            e.printStackTrace();
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    e + " detected when subscribing to " + channelName,
                    "EventConsumer.connect_event_channel");
        } catch (org.omg.CosEventChannelAdmin.AlreadyConnected ex) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to connect the push supplier due to CosEventChannelAdmin.AlreadyConnected.AlreadyConnected  exception",
                    "EventConsumer.connect_event_channel");
        } catch (org.omg.CosEventChannelAdmin.TypeError ex) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to connect the push supplier due to CosEventChannelAdmin.AlreadyConnected.TypeError  exception",
                    "EventConsumer.connect_event_channel");
        }
        return structuredProxyPushSupplier;
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
                    "NotifdEventConsumer.connect");
        }
        String channelName = adm_name;
        // If no connection exists to this channel, create it
        Database dbase = null;
        if (!channel_map.containsKey(channelName)) {
            if (device_proxy.use_db())
                dbase = device_proxy.get_db_obj();
            ConnectionStructure connectionStructure =
                    new ConnectionStructure(device_proxy.get_tango_host(), channelName,
                        deviceName, attributeName, eventName, dbase, deviceData, false);
            connect_event_channel(connectionStructure);
        } else if (device_proxy.use_db()) {
            dbase = device_proxy.get_db_obj();
        }
        EventChannelStruct eventChannelStruct = channel_map.get(channelName);
        eventChannelStruct.adm_device_proxy = device_proxy.get_adm_dev();
        eventChannelStruct.use_db = device_proxy.use_db();
        eventChannelStruct.dbase = dbase;

        device_channel_map.put(deviceName, channelName);
    }
    //===============================================================
    /**
     * We need to serialize as this method need access the POA
     *
     * @param cs  the connection information structure
     * @throws DevFailed if connection failed on notification service
     */
    //===============================================================
    protected synchronized void connect_event_channel(ConnectionStructure cs) throws DevFailed {
        //	Get a reference to an EventChannel for
        //  this device server from the tango database
        DeviceProxy adminDevice = DeviceProxyFactory.get(
                cs.channelName, cs.database.getUrl().getTangoHost());
        DbEventImportInfo received = getEventImportInfo(cs.channelName, cs.database, adminDevice);

        //	Keep host name without Fully Qualify Domain Name
        int idx = received.host.indexOf('.');
        if (idx > 0)
            received.host = received.host.substring(0, idx);

        //  Connect the notify daemon
        connectToNotificationDaemon(received);
        StructuredProxyPushSupplier
                structuredProxyPushSupplier = getStructuredProxyPushSupplier(cs.channelName);
        
        if (cs.reconnect) {
            EventChannelStruct eventChannelStruct = channel_map.get(cs.channelName);
            eventChannelStruct.eventChannel = eventChannel;
            eventChannelStruct.structuredProxyPushSupplier = structuredProxyPushSupplier;
            eventChannelStruct.last_heartbeat = System.currentTimeMillis();
            eventChannelStruct.heartbeat_skipped = false;
            eventChannelStruct.host = received.host;
            eventChannelStruct.has_notifd_closed_the_connection = 0;
            try {
                int filter_id = eventChannelStruct.heartbeat_filter_id;
                Filter filter = eventChannelStruct.structuredProxyPushSupplier.get_filter(filter_id);
                eventChannelStruct.structuredProxyPushSupplier.remove_filter(filter_id);
                filter.destroy();
            } catch (FilterNotFound e) {
                //  Do Nothing
            }
            //	Add filter for heartbeat events on channelName
            String constraint_expr = "$event_name == \'heartbeat\'";
            eventChannelStruct.heartbeat_filter_id = add_filter_for_channel(eventChannelStruct, constraint_expr);
            setEventChannelTimeoutMillis(eventChannelStruct.eventChannel, 3000);
        } else {
            EventChannelStruct newEventChannelStruct = new EventChannelStruct();
            newEventChannelStruct.eventChannel = eventChannel;
            newEventChannelStruct.structuredProxyPushSupplier = structuredProxyPushSupplier;
            newEventChannelStruct.last_heartbeat = System.currentTimeMillis();
            newEventChannelStruct.heartbeat_skipped = false;
            newEventChannelStruct.adm_device_proxy = adminDevice;
            newEventChannelStruct.host = received.host;
            newEventChannelStruct.has_notifd_closed_the_connection = 0;
            newEventChannelStruct.consumer = this;
            //	Add filter for heartbeat events on channelName
            String constraint_expr = "$event_name == \'heartbeat\'";
            newEventChannelStruct.heartbeat_filter_id = add_filter_for_channel(newEventChannelStruct, constraint_expr);
            channel_map.put(cs.channelName, newEventChannelStruct);

            setEventChannelTimeoutMillis(newEventChannelStruct.eventChannel, 3000);
        }
    }
    //===============================================================
    //===============================================================
    int add_filter_for_channel(EventChannelStruct event_channel_struct, String constraint_expr) throws DevFailed {
        Filter filter = null;
        int filter_id = -1;
        try {
            FilterFactory ffp = event_channel_struct.eventChannel.default_filter_factory();
            filter = ffp.create_filter("EXTENDED_TCL");
        } catch (org.omg.CosNotifyFilter.InvalidGrammar ex) {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Caught Invalid Grammar exception while creating heartbeat filter : check filter",
                    "EventConsumer.add_filter_for_channel");
        }

        ConstraintExp[] exp = new ConstraintExp[1];
        exp[0] = new ConstraintExp();
        exp[0].event_types = new EventType[0];
        exp[0].constraint_expr = constraint_expr;
        try {
            if (filter != null) {
                filter.add_constraints(exp);
                filter_id = event_channel_struct.structuredProxyPushSupplier.add_filter(filter);
            }
        } catch (org.omg.CosNotifyFilter.InvalidConstraint ex) {
            filter.destroy();
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Caught InvalidConstraint exception while adding constraint for heartbeat : check filter",
                    "EventConsumer.add_filter_for_channel");
        }
        return filter_id;
    }


    //===============================================================
    //===============================================================
    private String buildConstraintExpr(String device_name, String attribute, String event_name, String[] filters) {
        String str =
                "$domain_name == \'" + device_name.toLowerCase() + "/" +
                        attribute.toLowerCase() + "\'" + " and $event_name == \'" +
                        event_name + "\'";
        if (filters != null && filters.length != 0) {
            str += " and ((";
            for (String filter : filters)
                str += filter;
            str += " ) and $forced_event > 0.5 )";
        }
        return str;
    }

    //===============================================================
    /*
     * Check if DS is now running on another host
     * (another notifd too).
     *
     * @return true if host has changed
     */
    //===============================================================
    private boolean checkIfHostHasChanged(EventChannelStruct event_channel_struct) {
        boolean retVal = false;
        try {
            IORdump dump = new IORdump(event_channel_struct.adm_device_proxy);
            String server_host = dump.get_hostname();

            //	Keep server_host name without Fully Qualify Domain Name
            int idx = server_host.indexOf('.');
            if (idx > 0)
                server_host = server_host.substring(0, idx);

            if (!event_channel_struct.host.equals(server_host))
                retVal = true;
        } catch (DevFailed e) { /* */ }
        return retVal;
    }
    //===============================================================
    //===============================================================
    protected void checkIfAlreadyConnected(DeviceProxy device, String attribute, String event_name, CallBack callback, int max_size, boolean stateless)
            throws DevFailed {
        if (device == null || (callback == null && max_size < 0)) {
            Except.throw_wrong_syntax_exception("API_InvalidArgs",
                    "Device or callback pointer NULL and  event queue not used !!",
                    "EventConsumer.subscribe_event()");
        }

        if (device == null || device.name() == null)
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                    "Failed to connect to device",
                    "EventConsumer.subscribe_event()");
        else {
            //	Check if already connected (exists in map)
            String callback_key = device.name().toLowerCase() + "/" + attribute + "." + event_name;
            if (event_callback_map.containsKey(callback_key))
                Except.throw_event_system_failed("API_MethodArgument",
                        "Already connected to event " + callback_key,
                        "EventConsumer.subscribe_event()");

            //	Check if already trying to connects (exists in map)
            if (stateless && failed_event_callback_map.containsKey(callback_key))
                Except.throw_event_system_failed("API_MethodArgument",
                        "Already trying to connect to event " + callback_key,
                        "EventConsumer.subscribe_event()");
        }
    }
    //===============================================================
    //===============================================================
    protected String getEventSubscriptionCommandName() {
        return "EventSubscriptionChange";
    }
    //===============================================================
    //===============================================================
    protected void setAdditionalInfoToEventCallBackStruct(EventCallBackStruct callback_struct,
                            String device_name, String attribute, String event_name,
                            String[]filters, EventChannelStruct channel_struct) throws DevFailed{
        
        String constraint_expr = buildConstraintExpr(device_name, attribute, event_name, filters);
        int filter_id = add_filter_for_channel(channel_struct, constraint_expr);

        callback_struct.filter_constraint = constraint_expr;
        callback_struct.filter_id = filter_id;
        callback_struct.consumer  = this;
    }

    //===============================================================
    /*
     * Push event containing exception
     */
    //===============================================================
    private void pushServerNotRespondingException(EventChannelStruct eventChannelStruct, EventCallBackStruct callbackStruct) {
        try {
            if (eventChannelStruct != null) {
                if (eventChannelStruct.consumer instanceof NotifdEventConsumer) {
                    if (!callbackStruct.filter_ok) {
                        callbackStruct.filter_id = NotifdEventConsumer.getInstance().add_filter_for_channel(
                                eventChannelStruct, callbackStruct.filter_constraint);
                        callbackStruct.filter_ok = true;
                    }
                }
            } else
                return;

            CallBack callback = callbackStruct.callback;
            DevError[] errors = {new DevError()};
            errors[0].severity = ErrSeverity.ERR;
            errors[0].origin = "EventConsumer.KeepAliveThread";
            errors[0].reason = "API_EventTimeout";
            errors[0].desc = "Event channel is not responding any more, maybe the server or event system is down";
            String domain_name = callbackStruct.device.name() + "/" + callbackStruct.attr_name.toLowerCase();
            EventData event_data =
                    new EventData(eventChannelStruct.adm_device_proxy,
                            domain_name, callbackStruct.event_name, EventData.NOTIFD_EVENT,
                            callbackStruct.event_type, null, null, null, null, null, errors);

            event_data.device = callbackStruct.device;
            event_data.name = callbackStruct.device.name();
            event_data.event = callbackStruct.event_name;

            if (callbackStruct.use_ev_queue) {
                EventQueue ev_queue = callbackStruct.device.getEventQueue();
                ev_queue.insert_event(event_data);
            } else
                callback.push_event(event_data);
        } catch (DevFailed e) { /* */ }
    }
    //===============================================================
    //===============================================================
    @Override
    protected void checkIfHeartbeatSkipped(String name, EventChannelStruct eventChannelStruct) {
            // Check if heartbeat have been skipped, can happen if
            // 1- the notifd is dead (if not ZMQ)
            // 2- the server is dead
            // 3- The network was down;
            // 4- The server has been restarted on another host.
//        long now = System.currentTimeMillis();
//        boolean heartbeat_skipped =
//                ((now - eventChannelStruct.last_heartbeat) > KeepAliveThread.getHeartBeatPeriod());
        if (KeepAliveThread.heartbeatHasBeenSkipped(eventChannelStruct) ||
            eventChannelStruct.heartbeat_skipped || eventChannelStruct.notifd_failed) {

            eventChannelStruct.heartbeat_skipped = true;

            // Check notifd by trying to read an attribute of the event channel
            DevError dev_error = null;
            try {
                eventChannelStruct.eventChannel.MyFactory();

                //	Check if DS is now running on another host
                if (checkIfHostHasChanged(eventChannelStruct))
                    eventChannelStruct.notifd_failed = true;
            } catch (RuntimeException e1) {
                //	MyFactory has failed
                dev_error = new DevError();
                dev_error.severity = ErrSeverity.ERR;
                dev_error.origin = "NotifdEventConsumer.checkIfHeartbeatSkipped()";
                dev_error.reason = "API_EventException";
                dev_error.desc = "Connection failed with notify daemon";

                //	Try to add reason
                int pos = e1.toString().indexOf(":");
                if (pos > 0) dev_error.desc += "  (" + e1.toString().substring(0, pos) + ")";

                eventChannelStruct.notifd_failed = true;

                //	reset the event import info stored in DeviceProxy object
                //	Until today, this feature is used only by Astor (import with external info).
                try {
                    DeviceProxyFactory.get(name,
                            eventChannelStruct.dbase.getUrl().getTangoHost()).set_evt_import_info(null);
                } catch (DevFailed e) {
                    System.err.println("API received a DevFailed :	" + e.errors[0].desc);
                }
            }

            //	Force to reconnect if not using database
            if (!eventChannelStruct.use_db)
                eventChannelStruct.notifd_failed = true;

            //	Check if has_notifd_closed_the_connection many times (nework blank)
            if (!eventChannelStruct.notifd_failed &&
                    eventChannelStruct.has_notifd_closed_the_connection >= 3)
                eventChannelStruct.notifd_failed = true;

            //	If notifd_failed  --> try to reconnect
            if (eventChannelStruct.notifd_failed) {
                eventChannelStruct.notifd_failed = !reconnect_to_channel(name);
                if (!eventChannelStruct.notifd_failed)
                    reconnect_to_event(name);
            }

            Enumeration callback_structs = EventConsumer.getEventCallbackMap().elements();
            while (callback_structs.hasMoreElements()) {
                EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
                if (callback_struct.channel_name.equals(name)) {
                    //	Push exception
                    if (dev_error != null)
                        pushReceivedException(eventChannelStruct, callback_struct, dev_error);
                    else
                        pushServerNotRespondingException(eventChannelStruct, callback_struct);

                    //	If reconnection done, try to re subscribe
                    //		and read attribute in synchronous mode
                    if (!callback_struct.event_name.equals(eventNames[DATA_READY_EVENT]))
                        if (!eventChannelStruct.notifd_failed)
                            if (eventChannelStruct.consumer.reSubscribe(eventChannelStruct, callback_struct))
                                readAttributeAndPush(eventChannelStruct, callback_struct);
                }
            }
        }// end if heartbeat_skipped
        else
            eventChannelStruct.has_notifd_closed_the_connection = 0;
    }


    //===============================================================
    /**
     * Try to connect if it failed at subscribe
     */
    //===============================================================

    //===============================================================
    /*
     * Re subscribe event
     */
    //===============================================================
    @Override
    protected boolean reSubscribe(EventChannelStruct event_channel_struct,
                                  EventCallBackStruct callback_struct) {
        boolean retVal = true;

        try {
            DeviceData subscriber_in = new DeviceData();
            String[] subscriber_info = new String[4];
            subscriber_info[0] = callback_struct.device.name();
            subscriber_info[1] = callback_struct.attr_name;
            subscriber_info[2] = "subscribe";
            subscriber_info[3] = callback_struct.event_name;
            subscriber_in.insert(subscriber_info);
            event_channel_struct.adm_device_proxy.command_inout("EventSubscriptionChange", subscriber_in);
            event_channel_struct.heartbeat_skipped = false;
            event_channel_struct.last_subscribed = System.currentTimeMillis();
            callback_struct.last_subscribed = event_channel_struct.last_subscribed;
        } catch (Exception e) {
            retVal = false;
        }
        return retVal;
    }
    //===============================================================
    /**
     * Reconnect to event
     *
     * @param name admin device name
     */
    //===============================================================
    void reconnect_to_event(String name) {

        Enumeration callback_structs = event_callback_map.elements();
        while (callback_structs.hasMoreElements()) {
            EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
            if (callback_struct.channel_name.equals(name) && (callback_struct.callback != null)) {
                try {
                    EventChannelStruct event_channel_struct = channel_map.get(name);
                    callback_struct.filter_id = add_filter_for_channel(event_channel_struct, callback_struct.filter_constraint);
                    callback_struct.filter_ok = true;
                } catch (DevFailed e1) {
                    callback_struct.filter_ok = false;
                }
            }
        }

    }
    //===============================================================
    //===============================================================
    private void setEventChannelTimeoutMillis(EventChannel eventChannel, int millis) {
        //	Change Jacorb policy for timeout
        org.omg.CORBA.Policy p =
                new org.jacorb.orb.policies.RelativeRoundtripTimeoutPolicy(10000 * millis);
        eventChannel._set_policy_override(new Policy[]{p},
                org.omg.CORBA.SetOverrideType.ADD_OVERRIDE);
    }
    //===============================================================
    //===============================================================
    private void cleanup_heartbeat_filters() {
        Enumeration channel_names = channel_map.keys();
        while (channel_names.hasMoreElements()) {
            String name = (String) channel_names.nextElement();
            EventChannelStruct eventChannelStruct = channel_map.get(name);
            if (eventChannelStruct.consumer instanceof NotifdEventConsumer) {
                try {
                    int filter_id = eventChannelStruct.heartbeat_filter_id;
                    Filter filter =
                            eventChannelStruct.structuredProxyPushSupplier.get_filter(filter_id);
                    eventChannelStruct.structuredProxyPushSupplier.remove_filter(filter_id);
                    filter.destroy();
                } catch (FilterNotFound e) {
                    //  do nothing
                }
            }
        }
    }
    //===============================================================
    //===============================================================
    protected void removeFilters(EventCallBackStruct cb_struct) throws DevFailed {
        try {
            EventChannelStruct ec_struct = channel_map.get(cb_struct.channel_name);
            if (ec_struct != null) {
                StructuredProxyPushSupplier supplier =
                        ec_struct.structuredProxyPushSupplier;

                Filter filter = supplier.get_filter(cb_struct.filter_id);
                if (filter != null) {
                    supplier.remove_filter(cb_struct.filter_id);
                    filter.destroy();
                }
            }
        } catch (FilterNotFound e) {
            Except.throw_event_system_failed("API_EventNotFound",
                    "Failed to unsubscribe event, caught exception while calling remove_filter()" +
                            " (hint: check notification daemon is running)",
                    "EventConsumer.unsubscribe_event()");
        }
    }
    //===============================================================
    //===============================================================
    protected void unsubscribeTheEvent(EventCallBackStruct callbackStruct) {
        //  Nothing
    }
    //===============================================================
    //===============================================================




    //===============================================================
    /**
     * Reconnect to channel
     *
     * @param name channel name
     * @return true if reconnection done
     */
    //===============================================================
     boolean reconnect_to_channel(String name) {
        boolean ret = true;
        Enumeration callback_structs = event_callback_map.elements();
        while (callback_structs.hasMoreElements()) {
            EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
            if (callback_struct.channel_name.equals(name) && (callback_struct.callback != null)) {
                try {
                    EventChannelStruct event_channel_struct = channel_map.get(name);
                    event_channel_struct.adm_device_proxy.ping();
                    connect_event_channel(new ConnectionStructure(
                            callback_struct.device.get_tango_host(),
                            name, event_channel_struct.dbase, true));
                    ret = true;
                } catch (DevFailed e1) {
                    //Except.print_exception(e1);
                    ret = false;
                }
                break;
            }
        }
        return ret;
    }



}
