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
// $Revision: 30265 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.events.EventConsumerUtil;
import fr.esrf.TangoApi.events.ZMQutils;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.jacorb.orb.Delegate;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;

import java.util.*;

/**
 * Class Description: This class manage a static vector of Database object. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> Database dbase = ApiUtil.get_db_obj(); <Br>
 * </ul>
 * </i>
 *
 * @author verdier
 * @version $Revision: 30265 $
 */

public class ApiUtilDAODefaultImpl implements IApiUtilDAO {
    static private ArrayList<Database> db_list = null;
    static private Database defaultDatabase = null;

    static private Hashtable<Integer, AsyncCallObject> async_request_table =
            new Hashtable<Integer, AsyncCallObject>();
    static private int async_request_cnt = 0;
    static private int async_cb_sub_model = ApiDefs.PULL_CALLBACK;
    static private boolean in_server_code = false;

    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     *
     * @param tango_host
     *            host and port (hostname:portnumber) where database is running.
     */
    // ===================================================================
    public Database get_db_obj(final String tango_host) throws DevFailed {
        final int i = tango_host.indexOf(":");
        if (i <= 0) {
            Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
                "Cannot parse port number", "ApiUtil.get_db_obj()");
        }
        return get_db_obj(tango_host.substring(0, i), tango_host.substring(i + 1));
    }

    // ===================================================================
    /**
     * Return the database object created with TANGO_HOST environment variable .
     */
    // ===================================================================
    public Database get_default_db_obj() throws DevFailed {
        if (defaultDatabase == null) {
            return get_db_obj();
        } else {
            return defaultDatabase;
        }
    }
    // ===================================================================
    /**
     * Return tru if the database object has been created.
     */
    // ===================================================================
    public boolean default_db_obj_exists() throws DevFailed {
        return  (defaultDatabase != null);
    }

    // ===================================================================
    /**
     * Return the database object created with TANGO_HOST environment variable .
     */
    // ===================================================================
    public synchronized Database get_db_obj() throws DevFailed {
        if (ApiUtil.getOrb() == null) {
            create_orb();
        }

        // If first time, create vector
        // ---------------------------------------------------------------
        if (db_list == null) {
            db_list = new ArrayList<Database>();
        }
        // If first time, create Database object
        // -----------------------------------------------------------
        if (defaultDatabase == null) {
            defaultDatabase = new Database();
            db_list.add(defaultDatabase);
        }
        return db_list.get(0);
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     *
     * @param host host where database is running.
     * @param port port for database connection.
     */
    // ===================================================================
    public Database get_db_obj(final String host, final String port) throws DevFailed {
        if (ApiUtil.getOrb() == null) {
            create_orb();
        }

        // If first time, create vector
        if (db_list == null) {
            db_list = new ArrayList<Database>();
        }

        // Build tango_host string
        final String tango_host = host + ":" + port;

        // Search if database object already created for this host and port
        if (defaultDatabase != null) {
            if (defaultDatabase.get_tango_host().equals(tango_host)) {
                return defaultDatabase;
            }
        }

        for (final Database dbase : db_list) {
            if (dbase.get_tango_host().equals(tango_host)) {
                return dbase;
            }
        }

        // Else, create a new database object
        final Database dbase = new Database(host, port);
        db_list.add(dbase);
        return dbase;
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port, and set
     * this database object for all following uses..
     *
     * @param host host where database is running.
     * @param port port for database connection.
     */
    // ===================================================================
    public Database change_db_obj(final String host, final String port) throws DevFailed {
        // Get requested database object
        final Database dbase = get_db_obj(host, port);
        // And set it at first vector element as default Dbase
        db_list.remove(dbase);
        db_list.add(0, dbase);
        defaultDatabase = dbase;
        return dbase;
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port, and set
     * this database object for all following uses..
     *
     * @param host
     *            host where database is running.
     * @param port
     *            port for database connection.
     */
    // ===================================================================
    public Database set_db_obj(final String host, final String port) throws DevFailed {
	return change_db_obj(host, port);
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     *
     * @param tango_host
     *            host and port (hostname:portnumber) where database is running.
     */
    // ===================================================================
    public Database set_db_obj(final String tango_host) throws DevFailed {
	final int i = tango_host.indexOf(":");
	if (i <= 0) {
	    Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
		    "Cannot parse port number", "ApiUtil.set_db_obj()");
	}
	return change_db_obj(tango_host.substring(0, i), tango_host.substring(i + 1));
    }

    // ===================================================================
    // ===================================================================

    // ===================================================================
    /**
     * Create the orb object
     * @throws DevFailed if ORB creation failed
     */
    // ===================================================================
    private static synchronized void create_orb() throws DevFailed {
        try {
            // Modified properties fo ORB usage.
            // ---------------------------------------
            final Properties props = System.getProperties();
            props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
            props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");

            // Set retry properties
            props.put("jacorb.retries", "0");
            props.put("jacorb.retry_interval", "100");

            // Initial timeout for establishing a connection.
            props.put("jacorb.connection.client.connect_timeout", "300");

            // Set the Largest transfer.
            final String str = checkORBgiopMaxMsgSize();
            props.put("jacorb.maxManagedBufSize", str);
			
			//	Check for max threads
			final String nbThreads = System.getProperty("max_receptor_threads");
			if (nbThreads!=null)
				props.put("jacorb.connection.client.max_receptor_threads", nbThreads);

            // Set jacorb verbosity at minimum value
            props.put("jacorb.config.log.verbosity", "0");
            props.put("jacorb.disableClientOrbPolicies", "off");

            //  Add code set to jacorb.properties
            props.put("jacorb.codeset", "true");

            //  Add directory to get jacorb.properties
            props.put("jacorb.config.dir", "fr/esrf/TangoApi/etc");
			System.setProperties(props);

            // Initialize ORB
            // -----------------------------
            final String[] argv = null;
            ApiUtil.setOrb(ORB.init(argv, null));

            // Get an instance of DevLockManager to initialize.
            DevLockManager.getInstance();
        } catch (final SystemException ex) {
            // System.out.println("Excption catched in ApiUtil.create_orb");
            ApiUtil.setOrb(null);
            ex.printStackTrace();
            Except.throw_connection_failed(ex.toString(), "Initializing ORB failed !",
                "ApiUtil.create_orb()");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    // ===================================================================
    /**
     * Check if the checkORBgiopMaxMsgSize has been set. This environment
     * variable should be set in Mega bytes.
     * @return  the property string  to be set.
     */
    // ===================================================================
    private static String checkORBgiopMaxMsgSize() {
        /*
         * JacORB definition (see jacorb.properties file):
         *
         * This is NOT the maximum buffer size that can be used, but just the
         * largest size of buffers that will be kept and managed. This value
         * will be added to an internal constant of 5, so the real value in
         * bytes is 2**(5+maxManagedBufSize-1). You only need to increase this
         * value if you are dealing with LOTS of LARGE data structures. You may
         * decrease it to make the buffer manager release large buffers
         * immediately rather than keeping them for later reuse.
         */
        String str = "20"; // Set to 16 Mbytes

        // Check if environment ask for bigger size.
        String tmp = ApiUtil.getORBgiopMaxMsgSize();
        if (tmp != null) {
            if ((tmp = checkBufferSize(tmp)) != null) {
                str = tmp;
            }
        }
        return str;
    }

    // ===================================================================
    // ===================================================================
    private static String checkBufferSize(final String str) {
        // try to get value
        int nb_mega;
        try {
            nb_mega = Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return null;
        }

        // Compute the real size and the power of 2
        final long size = (long) nb_mega * 1024 * 1024;
        long l = size;
        int cnt;
        for (cnt = 0; l > 0; cnt++) {
            l >>= 1;
        }
        cnt--;

        // Check if number ob Mb is not power of 2
        if (Math.pow(2, cnt) < size) {
            cnt++;
        }
        System.out.println(nb_mega + " Mbytes  (2^" + cnt + ")");

        final int jacorb_size = cnt - 4;
        return Integer.toString(jacorb_size);
    }

    // ===================================================================
    /**
     * Return the orb object
     */
    // ===================================================================
    public ORB get_orb() throws DevFailed {
	if (ApiUtil.getOrb() == null) {
	    create_orb();
	}
	return ApiUtil.getOrb();
    }

    // ===================================================================
    /**
     * Return the orb object
     */
    // ===================================================================
    public void set_in_server(final boolean val) {
	in_server_code = val;
    }

    // ===================================================================
    /**
     * Return true if in server code or false if in client code.
     */
    // ===================================================================
    public boolean in_server() {
	return in_server_code;
    }

    // ===================================================================
    /**
     * Return reconnection delay for controle system.
     */
    // ===================================================================
    private static int reconnection_delay = -1;

    public int getReconnectionDelay() {
        if (reconnection_delay < 0) {
            try {
                final DbDatum data = get_db_obj().get_property(TangoConst.CONTROL_SYSTEM,
                    "ReconnectionDelay");
                if (!data.is_empty()) {
                    reconnection_delay = data.extractLong();
                }
            } catch (final DevFailed e) {
                /* do nothing */
            }
            if (reconnection_delay < 0) {
                reconnection_delay = 1000;
            }
        }
        return reconnection_delay;
    }

    // ==========================================================================
    // ==========================================================================
    public static String getUser()
    {
        return System.getProperty("user.name");
    }
    // ==========================================================================
    // ==========================================================================



    // ==========================================================================
    /*
     * Asynchronous request management
     */
    // ==========================================================================
    // ==========================================================================
    /**
     * Add request in hash table and return id
     */
    // ==========================================================================
    public synchronized int put_async_request(final AsyncCallObject aco) {

        async_request_cnt++;
        aco.id = async_request_cnt;
        async_request_table.put(async_request_cnt, aco);
        return async_request_cnt;
    }

    // ==========================================================================
    /**
     * Return the request in hash table for the id
     *
     * @throws DevFailed
     */
    // ==========================================================================
    public Request get_async_request(final int id) throws DevFailed {

	if (!async_request_table.containsKey(id)) {
	    Except.throw_exception("ASYNC_API_ERROR", "request for id " + id + " does not exist",
		    this.getClass().getCanonicalName() + ".get_async_request");
	}
	final AsyncCallObject aco = async_request_table.get(id);
	return aco.request;
    }

    // ==========================================================================
    /**
     * Return the Asynch Object in hash table for the id
     */
    // ==========================================================================
    public AsyncCallObject get_async_object(final int id) {
	return async_request_table.get(id);
    }

    // ==========================================================================
    /**
     * Remove asynchronous call request and id from hashtable.
     */
    // ==========================================================================
    // GA: add synchronized
    public synchronized void remove_async_request(final int id) {

        // Try to destroye Request object (added by PV 7/9/06)
        final AsyncCallObject aco =  async_request_table.get(id);
        if (aco != null) {
            removePendingRepliesOfRequest(aco.request);
            ((org.jacorb.orb.ORB) ApiUtil.getOrb()).removeRequest(aco.request);
            async_request_table.remove(id);
        }
    }


    @SuppressWarnings("UnusedParameters")
    private static void removePendingReplies(final Delegate delegate) {
        // try to solve a memory leak. pending_replies is still growing when
        // server is in timeout
        /*****
        Removed for JacORB-3
        if (!delegate.get_pending_replies().isEmpty()) {
            delegate.get_pending_replies().clear();
        }
        *****/
    }
    public static void removePendingRepliesOfRequest(final Request request) {
        final org.jacorb.orb.Delegate delegate = (org.jacorb.orb.Delegate) ((org.omg.CORBA.portable.ObjectImpl) request
            .target())._get_delegate();
        removePendingReplies(delegate);
    }

    public static void removePendingRepliesOfDevice(final Connection connection) {
        final org.jacorb.orb.Delegate delegate;
        if (connection.device_4 != null) {
            delegate = (org.jacorb.orb.Delegate) ((org.omg.CORBA.portable.ObjectImpl) connection.device_4)
                ._get_delegate();
        } else if (connection.device_3 != null) {
            delegate = (org.jacorb.orb.Delegate) ((org.omg.CORBA.portable.ObjectImpl) connection.device_3)
                ._get_delegate();
        } else if (connection.device_2 != null) {
            delegate = (org.jacorb.orb.Delegate) ((org.omg.CORBA.portable.ObjectImpl) connection.device_2)
                ._get_delegate();
        } else if (connection.device != null) {
            delegate = (org.jacorb.orb.Delegate) ((org.omg.CORBA.portable.ObjectImpl) connection.device)
                ._get_delegate();
        }
        else {
            return;
        }
        removePendingReplies(delegate);
    }

    // ==========================================================================
    /**
     * Set the reply_model in AsyncCallObject for the id key.
     */
    // ==========================================================================
    public void set_async_reply_model(final int id, final int reply_model) {
        final AsyncCallObject aco = async_request_table.get(id);
        if (aco != null) {
            aco.reply_model = reply_model;
        }
    }

    // ==========================================================================
    /**
     * Set the Callback object in AsyncCallObject for the id key.
     */
    // ==========================================================================
    public void set_async_reply_cb(final int id, final CallBack cb) {
        final AsyncCallObject aco = async_request_table.get(id);
        if (aco != null) {
            aco.cb = cb;
        }
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     *
     * @param dev   DeviceProxy object.
     * @param reply_model
     *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public int pending_asynch_call(final DeviceProxy dev, final int reply_model) {
        int cnt = 0;
        final Enumeration _enum = async_request_table.keys();
        while (_enum.hasMoreElements()) {
            int n = (Integer)_enum.nextElement();
            final AsyncCallObject aco = async_request_table.get(n);
            if (aco.dev == dev
                && (reply_model == ApiDefs.ALL_ASYNCH || aco.reply_model == reply_model)) {
            cnt++;
            }
        }
        return cnt;
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     *
     * @param reply_model
     *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public int pending_asynch_call(final int reply_model) {
        int cnt = 0;
        final Enumeration _enum = async_request_table.keys();
        while (_enum.hasMoreElements()) {
            int n = (Integer)_enum.nextElement();
            final AsyncCallObject aco = async_request_table.get(n);
            if (reply_model == ApiDefs.ALL_ASYNCH || aco.reply_model == reply_model) {
                cnt++;
            }
        }
        return cnt;
    }

    // ==========================================================================
    /**
     * Return the callback sub model used.
     *
     * @param model
     *            ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK.
     */
    // ==========================================================================
    public void set_asynch_cb_sub_model(final int model) {
    	async_cb_sub_model = model;
    }

    // ==========================================================================
    /**
     * Set the callback sub model used (ApiDefs.PUSH_CALLBACK or
     * ApiDefs.PULL_CALLBACK).
     */
    // ==========================================================================
    public int get_asynch_cb_sub_model() {
    	return async_cb_sub_model;
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies() {
        final Enumeration _enum = async_request_table.keys();
        while (_enum.hasMoreElements()) {
            int n = (Integer)_enum.nextElement();
            final AsyncCallObject aco = async_request_table.get(n);
            aco.manage_reply(ApiDefs.NO_TIMEOUT);
        }
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies(final int timeout) {
        final Enumeration _enum = async_request_table.keys();
        while (_enum.hasMoreElements()) {
            int n = (Integer)_enum.nextElement();
            final AsyncCallObject aco = async_request_table.get(n);
            aco.manage_reply(timeout);
        }
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies(final DeviceProxy dev) {
        final Enumeration _enum = async_request_table.keys();
        while (_enum.hasMoreElements()) {
            int n = (Integer)_enum.nextElement();
                final AsyncCallObject aco = async_request_table.get(n);
                if (aco.dev == dev) {
                    aco.manage_reply(ApiDefs.NO_TIMEOUT);
            }
        }
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public void get_asynch_replies(final DeviceProxy dev, final int timeout) {
        final Enumeration _enum = async_request_table.keys();
        while (_enum.hasMoreElements()) {
            int n = (Integer)_enum.nextElement();
                final AsyncCallObject aco = async_request_table.get(n);
                if (aco.dev == dev) {
                    aco.manage_reply(timeout);
            }
        }
    }

    // ==========================================================================
    /*
     * Methods to convert data.
     */
    // ==========================================================================


    // ==========================================================================
    // ==========================================================================
    public String stateName(final DevState state) {
	    return TangoConst.Tango_DevStateName[state.value()];
    }

    // ==========================================================================
    // ==========================================================================
    public String stateName(final short state_val) {
    	return TangoConst.Tango_DevStateName[state_val];
    }

    // ==========================================================================
    // ==========================================================================
    public String qualityName(final AttrQuality att_q) {
	    return TangoConst.Tango_QualityName[att_q.value()];
    }

    // ==========================================================================
    // ==========================================================================
    public String qualityName(final short att_q_val) {
	    return TangoConst.Tango_QualityName[att_q_val];
    }

    // ===================================================================
    /**
     * Parse Tango host (check multi Tango_host)
     */
    // ===================================================================
    public String[] parseTangoHost(final String tgh) throws DevFailed {
        String host = null;
        String strport = null;
        try {
            // Check if there is more than one Tango Host
            StringTokenizer stk;
            if (tgh.indexOf(",") > 0) {
            stk = new StringTokenizer(tgh, ",");
            } else {
            stk = new StringTokenizer(tgh);
            }

            final ArrayList<String> arrayList = new ArrayList<String>();
            while (stk.hasMoreTokens()) {
            // Get each Tango_host
            final String th = stk.nextToken();
            final StringTokenizer stk2 = new StringTokenizer(th, ":");
            arrayList.add(stk2.nextToken()); // Host Name
            arrayList.add(stk2.nextToken()); // Port Number
            }

            // Get the default one (first)
            host    = arrayList.get(0);
            strport = arrayList.get(1);
            Integer.parseInt(strport);

            // Put second one if exists in a singleton map object
            final String def_tango_host = host + ":" + strport;
            final DbRedundancy dbr = DbRedundancy.get_instance();
            if (arrayList.size() > 3) {
            final String redun = arrayList.get(2) + ":"
                        + arrayList.get(3);
            dbr.put(def_tango_host, redun);
            }
        } catch (final Exception e) {
            Except.throw_exception("TangoApi_TANGO_HOST_NOT_SET", e.toString()
                + " occurs when parsing " + "\"TANGO_HOST\" property " + tgh,
                "TangoApi.ApiUtil.parseTangoHost()");
        }
        return new String[] { host, strport };
    }
    // ===================================================================
    // ===================================================================
    public double getZmqVersion() {
        if (EventConsumerUtil.isZmqLoadable()) {
            return ZMQutils.getZmqVersion();
        }
        else
            return -1.0;
    }
    // ===================================================================
    // ===================================================================
}

