//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2008/01/14 07:00:00  pascal_verdier
// EventSubscriptionChange call is now done before channel connection
//
// Revision 1.2  2007/11/07 07:30:37  pascal_verdier
// Add a catch on TIMOUT exception on consumerAdmin.obtain_notification_push_supplier() call.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 1.27  2007/08/22 08:49:27  pascal_verdier
// take off trace
//
// Revision 1.26  2007/08/07 06:54:13  pascal_verdier
// Management without database added.
//
// Revision 1.25  2006/11/13 08:25:24  pascal_verdier
// Warnings removed.
//
// Revision 1.24  2006/08/31 08:45:32  pascal_verdier
// Reconnection management changed to reconnect after network blank.
//
// Revision 1.23  2006/06/08 08:04:40  pascal_verdier
// Bug on events if DS change host fixed.
//
// Revision 1.22  2006/04/20 11:48:58  pascal_verdier
// Catch exception in case of host shutdown during eventChannel.default_consumer_admin().
//
// Revision 1.21  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.20  2005/11/29 05:38:12  pascal_verdier
// updateDatabaseObject() method added for multi nethost usage.
//
// Revision 1.19  2005/10/27 13:28:19  pascal_verdier
// Debug sleep removed.
//
// Revision 1.18  2005/10/18 15:31:46  jlpons
// Fixed synchronisation bug
// Reduced number of is_a() calls
//
// Revision 1.17  2005/08/10 08:27:27  pascal_verdier
// use invokeLater() in dispatch_event() method.
//
// Revision 1.16  2005/06/22 13:30:26  pascal_verdier
// Notifd reconnection improved.
// NullPointerException catched at subsribe event (very rare).
//
// Revision 1.15  2005/05/18 12:52:45  pascal_verdier
// Keep alive thread is now started even if in server.
//
// Revision 1.14  2004/12/07 09:32:28  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 1.13  2004/09/23 14:49:28  pascal_verdier
// Read attribute synchron amd callback call are now done in a thread.
// The goal is to do it after monitor release in case of long call.
//
// Revision 1.12  2004/09/16 14:00:07  pascal_verdier
// Filter has been set in lower case.
//
// Revision 1.11  2004/07/07 08:02:10  pascal_verdier
// Re-connection on ALL events bug fixed.
//
// Revision 1.10  2004/07/06 09:27:24  pascal_verdier
// Tace messages removed (again).
//
// Revision 1.9  2004/07/06 09:26:23  pascal_verdier
// Tace messages removed.
//
// Revision 1.8  2004/07/06 09:22:58  pascal_verdier
// subscribe event is now thread safe.
// notify daemon reconnection works.
//
// Revision 1.7  2004/07/05 13:54:22  ounsy
// corrected : resubscribe for heartbeat event after notifd reconnection
//
// Revision 1.6  2004/07/02 16:30:38  ounsy
// Corrected reconnection problem after notifd stop and restart
//
// Revision 1.5  2004/06/25 09:31:04  ounsy
// Corrected Missing push_event after reconnection
//
// Revision 1.4  2004/06/24 15:32:49  ounsy
// synchronization with tango 4.3 release event api features
//
// Revision 1.3  2004/06/04 08:48:09  ounsy
// Added KeepAliveThread stopping after Eventconsumerthread shutdown
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * EventConsumer.java
 *
 * Created on May 21, 2003, 3:06 PM
 */

package fr.esrf.TangoApi.events;


import fr.esrf.Tango.*;
import fr.esrf.TangoApi.*;
import fr.esrf.TangoDs.*;
import org.omg.CORBA.*;
import org.omg.CosNotification.*;
import org.omg.CosNotifyComm.*;
import org.omg.CosNotifyChannelAdmin.*;
import org.omg.CosNotifyFilter.*;

import java.util.*;



/**
 *
 * @author  ounsy
 * @noinspection ALL
 */
public class EventConsumer
             extends StructuredPushConsumerPOA
             implements TangoConst , Runnable
{
    
    /** Creates a new instance of EventConsumer */
    public static EventConsumer create() throws DevFailed
    {
        if (instance != null)
        {
            return instance;
        }
        return new EventConsumer();
    }

    public void updateDatabaseObject() throws DevFailed
	{
		//tango_db = ApiUtil.get_db_obj();
		System.out.println("updateDatabaseObject()  is deprecated !");
	}
    public void disconnect_structured_push_consumer() 
    {
    	System.out.println("calling EventConsumer.disconnect_structured_push_consumer()");
    }
    
    public void offer_change(org.omg.CosNotification.EventType[] added, org.omg.CosNotification.EventType[] removed) 
                throws org.omg.CosNotifyComm.InvalidEventType 
    {
		System.out.println("calling EventConsumer.offer_change()");
    }
    
	//===============================================================
	//===============================================================
    public void push_structured_event(org.omg.CosNotification.StructuredEvent notification) 
    {
        String domain_name = notification.header.fixed_header.event_type.domain_name;
        String event_name = notification.header.fixed_header.event_name;

		try
		{
        	if (event_name.equals("heartbeat"))
        	{
       	        if ( channel_map.containsKey(domain_name) )
            	{
               	    EventChannelStruct event_channel_struct = (EventChannelStruct) channel_map.get(domain_name);
                	event_channel_struct.last_heartbeat = System.currentTimeMillis();
            	}
				else
				{
					//	In case of (use_db==false) 
					//	doamin name is only device name
					//	but key is full name (//host:port/a/b/c....)
					Enumeration keys = channel_map.keys();
					boolean found = false;
					while ( keys.hasMoreElements() && !found)
					{
						String name = (String) keys.nextElement();
               	    	EventChannelStruct event_channel_struct = (EventChannelStruct) channel_map.get(name);
						if (event_channel_struct.adm_device_proxy.name().equals(domain_name) )
						{
                			event_channel_struct.last_heartbeat = System.currentTimeMillis();
							found = true;
						}
					}
				}
        	}
        	else
        	{
            	String attr_event_name = domain_name + "." + event_name;

            	if ( event_callback_map.containsKey( attr_event_name ) )
            	{
                	EventCallBackStruct event_callback_struct = (EventCallBackStruct) event_callback_map.get(attr_event_name);
                	CallBack callback = event_callback_struct.callback;
                	if ( callback != null )
                	{
                		DeviceAttribute attr_value = null;
                		DevError[] dev_err_list = null;
						TypeCode ty  = notification.remainder_of_body.type();
						if ( ty.kind().equals(TCKind.tk_struct) ) {	
                    		attr_value = new DeviceAttribute( AttributeValueHelper.extract(notification.remainder_of_body) );
                		} else {
                	    	dev_err_list = DevErrorListHelper.extract(notification.remainder_of_body);
                		}
                    	EventData event_data = new EventData(event_callback_struct.device,
                                                        	 domain_name,event_name,
                                                        	 attr_value, dev_err_list);
                    	callback.push_event(event_data);
                	}
            	}
        	}
		}
		catch (Exception e)
		{
    		e.printStackTrace();
		}
		/****
		long	t1 = System.currentTimeMillis();
		System.out.println(" - elapsed time : " + (t1-t0) + " ms");
		**********/
    }


	//===============================================================
	//===============================================================
    public int subscribe_event(DeviceProxy device, String attribute, int event, CallBack callback, String[] filters)
                throws DevFailed
    {

		if ( device == null ||  callback == null)
		{		
			Except.throw_wrong_syntax_exception("API_InvalidArgs",
							"Device or callback pointer NULL !!",
							"EventConsumer.subscribe_event()");
		}

        String event_name;
        
        switch (event) {
            case CHANGE_EVENT  : event_name = "change";
                                break;
            case QUALITY_EVENT : event_name = "quality_change";
                                break;
            case PERIODIC_EVENT: event_name = "periodic";
                                break;
			case ARCHIVE_EVENT : event_name = "archive";
								break;
			case USER_EVENT    : event_name = "user_event";
								break;
            default            : event_name = "unknown";
        }

		//	Check if already connected
        if (device==null || device.name()==null)
            Except.throw_event_system_failed("API_NotificationServiceFailed",
                            "Failed to connect to device",
                            "EventConsumer.subscribe_event()");

        //noinspection ConstantConditions
        String callback_key = device.name().toLowerCase()+ "/" + attribute + "." + event_name;
        if (event_callback_map.containsKey( callback_key ) )
        {
			Except.throw_event_system_failed("API_MethodArgument",
								"Already connected to event " + callback_key,
								"EventConsumer.subscribe_event()");
        }

		String device_name = device.name();

		//	EventSubscriptionChange call is now done before (PV 11/01/08)
		DeviceData	subscriber_in = new DeviceData();
		String[]	subscriber_info = new String[4];
		subscriber_info[0] = device_name;
		subscriber_info[1] = attribute.toLowerCase();
		subscriber_info[2] = "subscribe";
		subscriber_info[3] = event_name;
		subscriber_in.insert(subscriber_info);

		device.get_adm_dev().command_inout("EventSubscriptionChange", subscriber_in);

        if ( !device_channel_map.containsKey( device_name ) )
        {
            connect(device);
            if ( !device_channel_map.containsKey( device_name ) )
            {
				Except.throw_event_system_failed("API_NotificationServiceFailed",
								"Failed to connect to event channel for device",
								"EventConsumer.subscribe_event()");
            }
        }
        
        String channel_name = (String) device_channel_map.get(device_name);

// Inform server that we want to subscribe 

        EventChannelStruct event_channel_struct = (EventChannelStruct) channel_map.get(channel_name);
        //event_channel_struct.adm_device_proxy.command_inout("EventSubscriptionChange",subscriber_in);
        event_channel_struct.last_subscribed = System.currentTimeMillis();
        EventCallBackStruct new_event_callback_struct = new EventCallBackStruct();
        new_event_callback_struct.device = device;
        new_event_callback_struct.attr_name = attribute;
        new_event_callback_struct.event_name = event_name;
        new_event_callback_struct.channel_name = channel_name;
        new_event_callback_struct.callback = callback;
        new_event_callback_struct.id = ++subscribe_event_id;
// Add filter for heartbeat events on channel_name
        String constraint_expr = "$domain_name == \'" + device_name.toLowerCase() + "/"
                               + attribute.toLowerCase() + "\'" + " and $event_name == \'"
                               + event_name + "\'";
        if (filters != null && filters.length != 0) 
        {
        	constraint_expr += " and ((";
        	for (int i=0 ; i < filters.length ; i++)
        	{
				constraint_expr += filters[i];
        	}
			constraint_expr += " ) and $forced_event > 0.5 )";
        }
        new_event_callback_struct.filter_id = add_filter_for_channel(event_channel_struct,constraint_expr);
        new_event_callback_struct.filter_constraint = constraint_expr;
        new_event_callback_struct.filter_ok = true;
        event_callback_map.put(callback_key , new_event_callback_struct);

		//	Thread to read the attribute by a simple synchronous call and 
		//	force callback execution after release monitor.
		//	This is necessary for the first point in "change" mode,
		//	but it is not necessary to be serialized in case of
		//	read attribute or callback execution a little bit long.
  		 if (  (event == CHANGE_EVENT) ||
			   (event == PERIODIC_EVENT) || 
		       (event == QUALITY_EVENT) || 
	  		   (event == ARCHIVE_EVENT) ||
	   		   (event == USER_EVENT))
		{
			new PushAttrValueLater(device, attribute, event_name, callback).start();
		}
        return subscribe_event_id;
    }
 
 	//===============================================================
	//===============================================================
   public void unsubscribe_event(int event_id) throws DevFailed
    {
         Enumeration keys = event_callback_map.keys();
         boolean found = false;
         while ( keys.hasMoreElements() )
         {
               String name = (String) keys.nextElement();
               EventCallBackStruct callback_struct = (EventCallBackStruct) event_callback_map.get(name);
               if ( callback_struct.id == event_id )
               {
                   try {
                         EventChannelStruct ec_struct = (EventChannelStruct) channel_map.get(callback_struct.channel_name);
						 Filter filter = ec_struct.structuredProxyPushSupplier.get_filter(callback_struct.filter_id);
                         ec_struct.structuredProxyPushSupplier.remove_filter(callback_struct.filter_id);
                         filter.destroy();
                    } catch (FilterNotFound e) {
                         Except.throw_event_system_failed("API_EventNotFound",
                         "Failed to unsubscribe event, caught exception while calling remove_filter() (hint: check notification daemon is running)",
                         "EventConsumer.unsubscribe_event()");
                    }
                    event_callback_map.remove(name);
                    found = true;
                    break;
               }
         }

		if (!found)
		{
			Except.throw_event_system_failed("API_EventNotFound",
				"Failed to unsubscribe event, the event id (" + event_id +
				") specified does not correspond with any known one",
				"EventConsumer.unsubscribe_event()");
		}
	}

    public void unsubscribe_device(DeviceProxy device)
    {
    }
    
    public void connect(DeviceProxy device_proxy) throws DevFailed
    {
        String device_name = device_proxy.name();
		String adm_name = null;
		try {
			adm_name = device_proxy.adm_name();

		} catch (DevFailed e) {
			Except.throw_event_system_failed("API_BadConfigurationProperty",
			"Can't subscribe to event for device " + device_name
			+ "\n Check that device server is running..." ,
			 "EventConsumer.connect");
		}
		String channel_name = adm_name;
		// If no connection exists to this channel, create it
		Database	dbase = null;
        if ( !channel_map.containsKey(channel_name) )
        {
			if (device_proxy.use_db())
				dbase = device_proxy.get_db_obj();
            connect_event_channel(channel_name, dbase, false);
        }
        EventChannelStruct eventChannelStruct = (EventChannelStruct) channel_map.get(channel_name);
        eventChannelStruct.adm_device_proxy = device_proxy.get_adm_dev();
		eventChannelStruct.use_db = device_proxy.use_db();
		eventChannelStruct.dbase  = dbase;

        device_channel_map.put( device_name , channel_name);
   }
    
 // activate POA and go into endless lopp waiting for events to be pushed
 // 
    public void run() 
    {
        try
        {
        	if (!ApiUtil.in_server())
        	{
			   keepAliveTimer.schedule(new KeepAliveThread(),
									   2000L,//Delay 2s
									   EVENT_HEARTBEAT_PERIOD);
               // We need to serialize here as the event subscripotion
               // thread needs also to access the poa
               synchronized (this) {
			     org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
			     org.omg.PortableServer.POA poa = org.omg.PortableServer.POAHelper.narrow(obj);
			     org.omg.PortableServer.POAManager pman = poa.the_POAManager();
			     pman.activate();
               }
               orbRunning = true;
               orb.run();
               orb.destroy();
       		}
			else
				keepAliveTimer.schedule(new KeepAliveThread(),
									   2000L,//Delay 2s
									   EVENT_HEARTBEAT_PERIOD);
			
        }
        catch (org.omg.CORBA.UserException ex)
        {
            System.out.println("EventConsumer.run() : Unable to start orb");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private EventConsumer() throws DevFailed 
    {
        orb = ApiUtil.get_orb();
        instance = this;
        channel_map = new Hashtable();
        device_channel_map = new Hashtable();
        device_channel_map.clear();
        event_callback_map = new Hashtable();
        keepAliveTimer = new Timer();
        runner = new Thread(this);
        Runtime.getRuntime().addShutdownHook(
                  new Thread() {
                  	public void run()
                  	{
                  		keepAliveTimer.cancel();
                  		cleanup_heartbeat_filters();
                  		cleanup_event_filters();
                        if(orbRunning) {
                          // Shutdown the ORB and wait for
                          // Completion
                  		  orb.shutdown(true);
                  		  try {
							runner.join();
						  } catch (InterruptedException e) {
    					        e.printStackTrace();
						  }
                        }
                  	}
                  }                                       );
		runner.start();
	}
	/**
  * 
  */
 private void cleanup_heartbeat_filters() {
	 Enumeration channel_names = channel_map.keys();
	 while ( channel_names.hasMoreElements() )
	 {
		   String name = (String) channel_names.nextElement();
		   EventChannelStruct event_channel_struct = (EventChannelStruct)channel_map.get(name);
		   try {
			 int filter_id = event_channel_struct.heartbeat_filter_id;
			 Filter filter = event_channel_struct.structuredProxyPushSupplier.get_filter(filter_id);
			 event_channel_struct.structuredProxyPushSupplier.remove_filter(filter_id);
			 filter.destroy();
				
		 } catch (FilterNotFound e) {
         }
			  
	 }		
 }

    
   /**
 * 
 */
	private void cleanup_event_filters() 
	{
		Enumeration keys = event_callback_map.keys();
		while ( keys.hasMoreElements() )
		{
			  String name = (String) keys.nextElement();
			  EventCallBackStruct callback_struct = (EventCallBackStruct) event_callback_map.get(name);
            //noinspection EmptyCatchBlock
            try {
                  EventChannelStruct ec_struct = (EventChannelStruct) channel_map.get(callback_struct.channel_name);
                  Filter filter =ec_struct.structuredProxyPushSupplier.get_filter(callback_struct.filter_id);
                  ec_struct.structuredProxyPushSupplier.remove_filter(callback_struct.filter_id);
                  filter.destroy();
              } catch (FilterNotFound e) {
              }
				event_callback_map.remove(name);
		}
	}


    // We need to serialize as this method need access the POA
    private synchronized void connect_event_channel(String channel_name, Database dbase, boolean reconnect) throws DevFailed
    {
		DbEventImportInfo	received = null;
		DeviceProxy			adm_dev = null;

//		Get a reference to an EventChannel for this device server 
//		from the tango database
//System.out.println("In connect_event_channel() for " + channel_name);
		try
		{
			if (dbase!=null)
				received = dbase.import_event(channel_name);
			else
			{
				//	Get event chanel info object from admin device
				received = new DbEventImportInfo();
//System.out.println("Trying to connect " + channel_name);
				adm_dev = new DeviceProxy(channel_name);
				
				DeviceData data = adm_dev.command_inout("QueryEventChannelIOR");
				received.channel_ior = data.extractString();
				received.channel_exported = true;

				// get the hostname where the notifyd should be running
				IORdump	id = new IORdump(null, received.channel_ior);
				received.host   = id.get_hostname();		
			}
		}
		catch (DevFailed df)
		{
			//Except.print_exception(df);
			if (dbase!=null)
				Except.throw_event_system_failed("API_NotificationServiceFailed",
					channel_name + " has no event channel defined in the database\n"
					+ " May be the server is not running or is not linked with Tango release 4.x (or above)",
			 		"EventConsumer.connect_event_channel");
			else
				Except.throw_event_system_failed("API_NotificationServiceFailed",
					channel_name + " did not returned event channel IOR\n"
					+ " May be the server is not running or is not linked with Tango release 4.x (or above)",
					 "EventConsumer.connect_event_channel");
		}
        String channel_ior = received.channel_ior;
        boolean channel_exported = received.channel_exported;
		//	Keep host name without Fully Qualify Domain Name
		int	idx = received.host.indexOf('.');
		if (idx>0)
			received.host = received.host.substring(0, idx);

        if (channel_exported)
        {
             org.omg.CORBA.Object event_channel_obj = orb.string_to_object(channel_ior);
			try {
				 eventChannel = EventChannelHelper.narrow(event_channel_obj);
			} catch (RuntimeException e) {
				Except.throw_event_system_failed("API_NotificationServiceFailed",
				"Failed to connect notification daemon (hint : make sur the notifd daemon is running on this host",
				"EventConsumer.connect_event_channel");
			}
            if (eventChannel == null)
            {
                channel_exported = false;
            }
        }
        if (!channel_exported)
        {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
             "Failed to narrow EventChannel from notification daemon (hint : make sur the notifd daemon is running on this host",
             "EventConsumer.connect_event_channel");
        }
		try
		{
// Obtain a consumer admin : we'll use the channel's default consumer admin
        	consumerAdmin = eventChannel.default_consumer_admin();
		}
		catch(Exception e)
		{
           Except.throw_event_system_failed("API_NotificationServiceFailed",
             "Received " + e.toString() + 
			 "\nduring eventChannel.default_consumer_admin() call",
             "EventConsumer.connect_event_channel");
 			
		}
        if (consumerAdmin == null)
        {
            Except.throw_event_system_failed("API_NotificationServiceFailed",
             "Failed to get default consumer admin from notification daemon (hint : make sur the notifd daemon is running on this host",
             "EventConsumer.connect_event_channel");
        }
// Obtain a ProxtSupplier : we are using Push model and Structured data
        org.omg.CORBA.IntHolder pId = new org.omg.CORBA.IntHolder();
        try
        {
            proxySupplier = consumerAdmin.obtain_notification_push_supplier(ClientType.STRUCTURED_EVENT,pId);
            if (proxySupplier == null)
            {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                "Failed to get a push supplier from notification daemon (hint : make sur the notifd daemon is running on this host",
                "EventConsumer.connect_event_channel");
            }
        }
        catch (org.omg.CORBA.TIMEOUT ex)
        {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                "Failed to get a push supplier due to a Timeout",
                "EventConsumer.connect_event_channel");
        }
        catch (org.omg.CosNotifyChannelAdmin.AdminLimitExceeded ex)
        {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                "Failed to get a push supplier due to AdminLimitExceeded (hint : make sur the notifd daemon is running on this host",
                "EventConsumer.connect_event_channel");
        }
        structuredProxyPushSupplier = StructuredProxyPushSupplierHelper.narrow(proxySupplier);
        if (structuredProxyPushSupplier==null)
        {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                "Failed to narrow the push supplier due to AdminLimitExceeded (hint : make sur the notifd daemon is running on this host",
                "EventConsumer.connect_event_channel");
        }
// Connect to the proxy consumer
        try
        {
           structuredProxyPushSupplier.connect_structured_push_consumer(_this(orb)); 
        }
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Except.throw_event_system_failed("API_NotificationServiceFailed",
				e + " detected when subscribing to " + channel_name,
				"EventConsumer.connect_event_channel");
		}
        catch (org.omg.CosEventChannelAdmin.AlreadyConnected ex)
        {
			Except.throw_event_system_failed("API_NotificationServiceFailed",
			"Failed to connect the push supplier due to CosEventChannelAdmin.AlreadyConnected.AlreadyConnected  exception",
			"EventConsumer.connect_event_channel");
        }
        catch (org.omg.CosEventChannelAdmin.TypeError ex)
        {
                Except.throw_event_system_failed("API_NotificationServiceFailed",
                "Failed to connect the push supplier due to CosEventChannelAdmin.AlreadyConnected.TypeError  exception",
                "EventConsumer.connect_event_channel");
        }
        if (reconnect) {
            EventChannelStruct eventChannelStruct = (EventChannelStruct) channel_map.get(channel_name);
            eventChannelStruct.eventChannel = eventChannel;
            eventChannelStruct.structuredProxyPushSupplier = structuredProxyPushSupplier;
            eventChannelStruct.last_heartbeat = System.currentTimeMillis();
            eventChannelStruct.heartbeat_skipped = false;
			eventChannelStruct.host = received.host;
 		   try {
			 int filter_id = eventChannelStruct.heartbeat_filter_id;
			 Filter filter = eventChannelStruct.structuredProxyPushSupplier.get_filter(filter_id);
			 eventChannelStruct.structuredProxyPushSupplier.remove_filter(filter_id);
			 filter.destroy();
				
 		   }
		   catch (FilterNotFound e) {
 		   }
//			 Add filter for heartbeat events on channel_name
	        String constraint_expr = "$event_name == \'heartbeat\'";
	        eventChannelStruct.heartbeat_filter_id = add_filter_for_channel(eventChannelStruct,constraint_expr);
		} 
		else
		{
	        EventChannelStruct new_event_channel_struct = new EventChannelStruct();
			new_event_channel_struct.eventChannel = eventChannel;
			new_event_channel_struct.structuredProxyPushSupplier = structuredProxyPushSupplier;
			new_event_channel_struct.last_heartbeat = System.currentTimeMillis();
			new_event_channel_struct.heartbeat_skipped = false;
			new_event_channel_struct.adm_device_proxy = adm_dev;
			new_event_channel_struct.host = received.host;
//			 Add filter for heartbeat events on channel_name
	        String constraint_expr = "$event_name == \'heartbeat\'";
	        new_event_channel_struct.heartbeat_filter_id = add_filter_for_channel(new_event_channel_struct,constraint_expr);
	        channel_map.put( channel_name , new_event_channel_struct );
		}
        
    }
    
//    private int add_filter_for_channel(String channel_name, String constraint_expr) throws DevFailed
    private int add_filter_for_channel(EventChannelStruct event_channel_struct, String constraint_expr) throws DevFailed
    {
        Filter filter = null;
        int filter_id = -1;
        try
        {
            FilterFactory ffp = event_channel_struct.eventChannel.default_filter_factory();
            filter = ffp.create_filter("EXTENDED_TCL");
        }
        catch (org.omg.CosNotifyFilter.InvalidGrammar ex)
        {
           Except.throw_event_system_failed("API_NotificationServiceFailed",
           "Caught Invalid Grammar exception while creating heartbeat filter : check filter",
           "EventConsumer.add_filter_for_channel");
        }

        ConstraintExp[] exp = new ConstraintExp[1];
        exp[0] = new ConstraintExp();
        exp[0].event_types = new EventType[0];
        exp[0].constraint_expr = constraint_expr;
        try
        {
            filter.add_constraints(exp);
            filter_id = event_channel_struct.structuredProxyPushSupplier.add_filter(filter);
        }
        catch (org.omg.CosNotifyFilter.InvalidConstraint ex)
        {
           filter.destroy();
           Except.throw_event_system_failed("API_NotificationServiceFailed",
           "Caught InvalidConstraint exception while adding constraint for heartbeat : check filter",
           "EventConsumer.add_filter_for_channel");
        }
        return filter_id;
    }
    

    private static EventConsumer instance = null;
    private static int subscribe_event_id = 0;
    private static final long EVENT_HEARTBEAT_PERIOD = 10000;
    private static final long EVENT_RESUBSCRIBE_PERIOD = 600000;
    private EventChannel eventChannel;
    private ConsumerAdmin consumerAdmin;
    private ProxySupplier proxySupplier;
    private StructuredProxyPushSupplier structuredProxyPushSupplier;
    private ORB orb;
    private Hashtable channel_map;
    private Hashtable device_channel_map = null;
    private Hashtable event_callback_map;
    private Thread runner;
    private Timer keepAliveTimer;
    private boolean orbRunning = false;

	//===============================================================
    /**
	 *	An innner class inherited from TimerTask class
	 */
	//===============================================================
    private class KeepAliveThread
             extends TimerTask
    {
    
		//===============================================================
    	/**
		 *	Creates a new instance of EventConsumer.KeepAliveThread
		 */
		//===============================================================
    	public KeepAliveThread() 
    	{
        	super();
    	}

		//===============================================================
		//===============================================================
    	public void run()
    	{
        	long MAX_TARDINESS = EVENT_HEARTBEAT_PERIOD*3/2;
        	if (System.currentTimeMillis() - scheduledExecutionTime() >= MAX_TARDINESS)
        	{
            	return; // Too late, skip this execution
        	}
        	resubscribe_if_needed();                
    	}

		//===============================================================
		//===============================================================
		private void resubscribe_if_needed()
		{
			Enumeration channel_names = channel_map.keys();
			long now = System.currentTimeMillis();
			while ( channel_names.hasMoreElements() )
			{
				String name = (String) channel_names.nextElement();
				EventChannelStruct event_channel_struct = (EventChannelStruct)channel_map.get(name);
				if ( (now - event_channel_struct.last_subscribed) > EVENT_RESUBSCRIBE_PERIOD/3 )
			  		reSubscribeByName(event_channel_struct, name);

				// Check if heaheartbeat have been skipped, can happen if
				// 1- the notifd is dead
				// 2- the server is dead
				// 3- The network was down;
				// 4- The server has been restarted on another host.
				boolean heartbeat_skipped = ( (now - event_channel_struct.last_heartbeat) > EVENT_HEARTBEAT_PERIOD );

				if ( heartbeat_skipped || event_channel_struct.heartbeat_skipped ||
					event_channel_struct.notifd_failed)
				{
					event_channel_struct.heartbeat_skipped = true;

					// Check notifd by trying to read an attribute of the event channel
					try
					{
						event_channel_struct.eventChannel.MyFactory();

						//	Check if DS is now running on another host
						if (checkIfHostHasChanged(event_channel_struct))
							event_channel_struct.notifd_failed = true;
					}
					catch (RuntimeException e1) {
						event_channel_struct.notifd_failed = true;
					}

					//	Force to reconnect if not using database
					if (event_channel_struct.use_db==false)
						event_channel_struct.notifd_failed = true;


					//	If notid_failed --> try to reconnect
					if (event_channel_struct.notifd_failed)
					{
						event_channel_struct.notifd_failed = !reconnect_to_channel(name);
						if (event_channel_struct.notifd_failed==false)
	 						reconnect_to_event(name);
					}
					Enumeration callback_structs = event_callback_map.elements();
					while ( callback_structs.hasMoreElements() )
					{
						EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
						if ( callback_struct.channel_name.equals(name) && (callback_struct.callback != null) )
						{
							//	Push exception
							pushServerNotRespondingException(event_channel_struct, callback_struct);

							//	If reconnection done, try to re subscribe
							//		and read attribute in synchrone.
							if (event_channel_struct.notifd_failed==false)
								if (reSubscribe(event_channel_struct, callback_struct))
									readAttributeAndPush(event_channel_struct, callback_struct);
						}
					}
				} // end if heartbeat_skipped

			}// end while  channel_names.hasMoreElements()
    	}
		//===============================================================
		/**
		 *	Re subscribe event
		 */
		//===============================================================
		private boolean reSubscribe(EventChannelStruct event_channel_struct, EventCallBackStruct callback_struct)
		{
			boolean retVal = true;

        	try
			{
            	DeviceData subscriber_in = new DeviceData();
            	String[] subscriber_info = new String[4];
            	subscriber_info[0] = callback_struct.device.name();
            	subscriber_info[1] = callback_struct.attr_name;
            	subscriber_info[2] = "subscribe";
            	subscriber_info[3] = callback_struct.event_name;
            	subscriber_in.insert(subscriber_info);
            	event_channel_struct.adm_device_proxy.command_inout("EventSubscriptionChange",subscriber_in);
            	event_channel_struct.heartbeat_skipped = false;
				event_channel_struct.last_subscribed = System.currentTimeMillis();
				callback_struct.last_subscribed      = event_channel_struct.last_subscribed;
			}
			catch (Exception e) {
					retVal = false;
			}
			return retVal;
		}
		//===============================================================
		/**
		 *	Re subscribe event selected by name
		 */
		//===============================================================
		private void reSubscribeByName(EventChannelStruct event_channel_struct, String name)
		{
			Enumeration callback_structs = event_callback_map.elements();
			while ( callback_structs.hasMoreElements() )
			{
				EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
				if ( callback_struct.channel_name.equals(name) )
				{
					reSubscribe(event_channel_struct, callback_struct);
				}
			}
		}
		//===============================================================
		/**
		 *	Read attribute and push result as event.
		 */
		//===============================================================
		private void readAttributeAndPush(EventChannelStruct event_channel_struct, EventCallBackStruct callback_struct)
		{
			if (   callback_struct.event_name.equals("change")
				|| callback_struct.event_name.equals("quality")
				|| callback_struct.event_name.equals("archive")
				|| callback_struct.event_name.equals("user_event"))
			{
				DeviceAttribute da = null;
				DevError[] err = null;
				String domain_name = callback_struct.device.name() + "/" + callback_struct.attr_name;
				boolean old_transp = callback_struct.device.get_transparency_reconnection();
				callback_struct.device.set_transparency_reconnection(true);
				try
				{
					da = callback_struct.device.read_attribute(callback_struct.attr_name);
				}
				catch (DevFailed e)
				{
					err = e.errors;
				}
				callback_struct.device.set_transparency_reconnection(old_transp);
				EventData event_data = new EventData(callback_struct.device,
								domain_name, callback_struct.event_name, da, err);
				callback_struct.callback.push_event(event_data);
			}
		}
		//===============================================================
		/**
		 *	Push event containing exception
		 */
		//===============================================================
		private void pushServerNotRespondingException(EventChannelStruct event_channel_struct, EventCallBackStruct callback_struct)
		{
        	try
			{
            	if (callback_struct.filter_ok == false)
            	{
                	callback_struct.filter_id = add_filter_for_channel(
						event_channel_struct,callback_struct.filter_constraint);
            		callback_struct.filter_ok = true;
            	}

            	DevError[] errors  = { new DevError() };
            	errors[0].severity = ErrSeverity.ERR;
            	errors[0].origin  = "EventConsumer.KeepAliveThread";
            	errors[0].reason  = "API_EventTimeout";
            	errors[0].desc    = "Event channel is not responding any more, maybe the server or event system is down";
            	EventData event_data = new EventData( event_channel_struct.adm_device_proxy,
                                                	  "domain_name" , "event_name" , null , errors );

            	CallBack callback = callback_struct.callback;
            	event_data.device = callback_struct.device;
            	event_data.name   = callback_struct.device.name();
            	event_data.event  = callback_struct.event_name;
            	callback.push_event(event_data);

        	}
			catch (DevFailed e) {
        	}
		}
		//===============================================================
		/**
		 *	Check if DS is now running on another host
		 *			(another notifd too).
		 * @return true if host has changed
		 */
		//===============================================================
		private boolean checkIfHostHasChanged(EventChannelStruct event_channel_struct)
		{
			boolean	retVal = false;
			try
			{
				IORdump	dump = new IORdump(event_channel_struct.adm_device_proxy);
				String	server_host = dump.get_hostname();

				//	Keep server_host name without Fully Qualify Domain Name
				int	idx = server_host.indexOf('.');
				if (idx>0)
					server_host = server_host.substring(0, idx);

				if (event_channel_struct.host.equals(server_host)==false)
					retVal = true;
			}
			catch(DevFailed e) {}
			return retVal;
		}

		//===============================================================
		/**
		 *	Reconnect to event
		 *	@param name admin device name
		 */
		//===============================================================
		private void reconnect_to_event(String name) {

        	Enumeration callback_structs = event_callback_map.elements();
        	while ( callback_structs.hasMoreElements() )
        	{
            	EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
				if ( callback_struct.channel_name.equals(name) && (callback_struct.callback != null) )
            	{
	           		try {
		            	EventChannelStruct event_channel_struct = (EventChannelStruct)channel_map.get(name);
		            	callback_struct.filter_id = add_filter_for_channel(event_channel_struct,callback_struct.filter_constraint);
		            	callback_struct.filter_ok = true;
					} catch (DevFailed e1) {
		            	callback_struct.filter_ok = false;
					}
            	}
        	 }

		}

		//===============================================================
		/**
		 * Reconnect to channel
		 * @param name
		 * @return true if reconnection done
		 */
		//===============================================================
		private boolean reconnect_to_channel(String name) {
			boolean ret = true;
        	Enumeration callback_structs = event_callback_map.elements();
        	while ( callback_structs.hasMoreElements() )
        	{
            	EventCallBackStruct callback_struct = (EventCallBackStruct) callback_structs.nextElement();
            	if ( callback_struct.channel_name.equals(name) && (callback_struct.callback != null) )
            	{
            		try {
		            	EventChannelStruct event_channel_struct = (EventChannelStruct)channel_map.get(name);
						connect_event_channel(name, event_channel_struct.dbase, true);
		            	event_channel_struct.adm_device_proxy.ping();
						ret = true;
					} catch (DevFailed e1) {
						Except.print_exception(e1);
						ret = false;
					}
	            	break;
            	}
        	 }
			return ret;
		}
    
    }

	//===============================================================
	/**
	 *	Thread to read the attribute by a simple synchronous call and 
	 *	force callback execution after release monitor.
	 *	This is necessary for the first point in "change" mode,
	 *	but it is not necessary to be serialized in case of
	 *	read attribute or callback execution a little bit long.
	 */
	//===============================================================
	class PushAttrValueLater extends Thread
	{
		private DeviceProxy	device;
		private String		attribute;
		private String		event_name;
		private CallBack	callback;
		PushAttrValueLater(DeviceProxy device, String attribute, String event_name, CallBack callback)
		{
			this.device     = device;
			this.attribute  = attribute;
			this.event_name = event_name;
			this.callback   = callback;
		}
		public void run()
		{
			//	Sleep to do it a bit later
			try {
				sleep(10);
			} catch(Exception e){}

			//	Then read attribute
			DeviceAttribute da = null;
			DevError[] err = null;
			String domain_name = device.name() + "/" + attribute.toLowerCase();
			try
			{
				da = device.read_attribute(attribute);
			}
			catch (DevFailed e)
			{
				err = e.errors;
			}

			//	And push value
			EventData event_data = new EventData(device,domain_name,event_name,da,err);
			callback.push_event(event_data);
		}
	}

}
