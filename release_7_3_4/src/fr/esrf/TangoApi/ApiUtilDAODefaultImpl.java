//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Revision$
//
// $Log$
// Revision 1.14  2010/08/06 13:54:45  abeilleg
// memory leak fix for jacorb objects(ReplyReceiver) when server is in timeout.
//
// Revision 1.13  2010/05/31 12:54:02  abeilleg
// make the projet compile with maven.
//
// Revision 1.12  2009/10/08 11:47:32  pascal_verdier
// remove synchronized on remove_async_request() method  (Gwenaelle Abeille).
//
// Revision 1.11  2009/09/11 12:10:30  pascal_verdier
// tangorc environment file is managed.
//
// Revision 1.10  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.9  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.8  2008/12/03 15:44:26  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.7  2008/11/12 10:09:36  pascal_verdier
// Add ORBgiopMaxMsgSize property to set max buffer tranfer size in Mbytes
//
// Revision 1.6  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2008/10/10 08:31:57  pascal_verdier
// Security check has been done.
//
// Revision 1.4  2008/09/12 11:32:16  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.3  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.2  2007/09/26 06:17:40  pascal_verdier
// Correction in qualityName() methods.
//
// Revision 1.1  2007/08/23 09:41:21  ounsy
// Add default impl for tangorb
//
// Revision 3.27  2007/04/18 05:45:53  pascal_verdier
// qualityName() methods added.
//
// Revision 3.26  2006/11/13 08:23:53  pascal_verdier
// Constant added.
//
// Revision 3.25  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.24  2006/09/07 10:44:54  pascal_verdier
// Memory leak on asynchronous calls fixed.
//
// Revision 3.23  2005/12/15 11:09:22  pascal_verdier
// ApiUtil.set_db_obj() methods added to set TANGO_HOST.
//
// Revision 3.22  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.21  2005/10/18 15:33:29  jlpons
// Fixed connection to default database
//
// Revision 3.20  2005/10/10 14:06:01  pascal_verdier
// Replace enum by _enum for java 1.5 comaptibility.
//
// Revision 3.19  2005/08/30 07:33:44  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.18  2005/08/10 08:08:12  pascal_verdier
// Some methods have been sychronized.
//
// Revision 3.17  2005/04/26 13:29:50  pascal_verdier
// Another Multi TANGO_HOST bug fixed.
//
// Revision 3.16  2005/01/14 10:32:33  pascal_verdier
// jacorb.connection.client.connect_timeout property added and set to 5000.
//
// Revision 3.15  2004/12/16 10:15:37  pascal_verdier
// Multi TANGO_HOST bug fixed.
//
// Revision 3.14  2004/12/08 15:34:12  pascal_verdier
// Bug on bad TANGO_HOST fixed.
//
// Revision 3.13  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.12  2004/11/30 13:07:03  pascal_verdier
// change_db_obj() method added..
//
// Revision 3.11  2004/11/05 12:05:34  pascal_verdier
// Use now JacORB_2_2_1.
//
// Revision 3.10  2004/11/05 11:59:19  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.9  2004/10/11 12:25:42  pascal_verdier
// Multi TANGO_HOST bug fixed.
//
// Revision 3.8  2004/09/23 14:00:15  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.7  2004/05/14 14:21:32  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.4  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.3  2003/09/08 11:02:34  pascal_verdier
// *** empty log message ***
//
// Revision 3.2  2003/08/18 11:05:38  pascal_verdier
// Client Timeout property name changed to jacorb.client.pending_reply_timeout
//
// Revision 3.1  2003/07/22 14:15:35  pascal_verdier
// DeviceData are now in-methods objects.
// Minor change for TACO-TANGO common database.
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
// Revision 2.0  2003/01/09 14:00:37  verdier
// jacORB is now the ORB used.
//
// Revision 1.8  2002/06/26 09:02:17  verdier
// tested with atkpanel on a TACO device
//
// Revision 1.7  2002/04/09 12:21:51  verdier
// IDL 2 implemented.
//
// Revision 1.6  2002/01/09 12:18:15  verdier
// TACO signals can be read as TANGO attribute.
//
// Revision 1.5  2001/12/10 14:19:42  verdier
// TACO JNI Interface added.
// URL syntax used for connection.
// Connection on device without database added.
//
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
//
//-======================================================================

package fr.esrf.TangoApi;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jacorb.orb.Delegate;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.events.EventConsumer;
import fr.esrf.TangoApi.events.IEventConsumer;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

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
 * @version $Revision$
 */

public class ApiUtilDAODefaultImpl implements IApiUtilDAO {
    static private Vector db_list = null;
    static private Database default_dbase = null;
    static private IEventConsumer event_consumer = null;

    static private Hashtable async_request_table = null;
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
	if (default_dbase == null) {
	    return get_db_obj();
	} else {
	    return default_dbase;
	}
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
	    db_list = new Vector();
	}
	// If first time, create Database object
	// -----------------------------------------------------------
	if (default_dbase == null) {
	    default_dbase = new Database();
	    db_list.addElement(default_dbase);
	}
	return (Database) db_list.elementAt(0);
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     * 
     * @param host
     *            host where database is running.
     * @param port
     *            port for database connection.
     */
    // ===================================================================
    public Database get_db_obj(final String host, final String port) throws DevFailed {
	if (ApiUtil.getOrb() == null) {
	    create_orb();
	}

	// If first time, create vector
	// ---------------------------------------------------------------
	if (db_list == null) {
	    db_list = new Vector();
	}

	// Build tango_host string
	// ---------------------------
	final String tango_host = new String(host + ":" + port);

	// Search if database object already created for this host and port
	// -------------------------------------------------------------------
	if (default_dbase != null) {
	    if (default_dbase.get_tango_host().equals(tango_host)) {
		return default_dbase;
	    }
	}

	for (int i = 0; i < db_list.size(); i++) {
	    final Database dbase = (Database) db_list.elementAt(i);
	    if (dbase.get_tango_host().equals(tango_host)) {
		return dbase;
	    }
	}

	// Else, create a new database object
	// ---------------------------------------
	final Database dbase = new Database(host, port);
	db_list.add(dbase);
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
    public Database change_db_obj(final String host, final String port) throws DevFailed {
	// Get requested database object
	final Database dbase = get_db_obj(host, port);
	// And set it at first vector element as default Dbase
	db_list.remove(dbase);
	db_list.insertElementAt(dbase, 0);
	default_dbase = dbase;
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
	    props.put("jacorb.connection.client.connect_timeout", "5000");

	    // Set the Largest transfert.
	    final String str = checkORBgiopMaxMsgSize();
	    // System.out.println("Set jacorb.maxManagedBufSize  to  " + str);
	    props.put("jacorb.maxManagedBufSize", str);

	    // Set jacorb verbosity at minimum value
	    props.put("jacorb.config.log.verbosity", "0");

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
	int nb_mega = 0;
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
		if (data.is_empty() == false) {
		    reconnection_delay = data.extractLong();
		}
	    } catch (final DevFailed e) {/* do nothing */
	    }
	    if (reconnection_delay < 0) {
		reconnection_delay = 1000;
	    }
	}
	return reconnection_delay;
    }

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
	if (async_request_table == null) {
	    async_request_table = new Hashtable();
	}
	async_request_cnt++;
	aco.id = async_request_cnt;
	final Integer key = new Integer(async_request_cnt);
	async_request_table.put(key, aco);
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
	final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(id);
	return aco.request;
    }

    // ==========================================================================
    /**
     * Return the Asynch Object in hash table for the id
     */
    // ==========================================================================
    public AsyncCallObject get_async_object(final int id) {
	return (AsyncCallObject) async_request_table.get(id);
    }

    // ==========================================================================
    /**
     * Remove asynchronous call request and id from hashtable.
     */
    // ==========================================================================
    // GA: add synchronized
    public synchronized void remove_async_request(final int id) {

	// Try to destroye Request object (added by PV 7/9/06)
	final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(id);
	if (aco != null) {
	    removePendingRepliesOfRequest(aco.request);
	    ((org.jacorb.orb.ORB) ApiUtil.getOrb()).removeRequest(aco.request);
	    async_request_table.remove(id);
	}
    }

    private static void removePendingReplies(final Delegate delegate) {
	// try to solve a memory leak. pending_replies is still growing when
	// server is in timeout
	if (!delegate.get_pending_replies().isEmpty()) {
	    delegate.get_pending_replies().clear();
	}
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
	} else {
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
	final Integer key = new Integer(id);
	final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(key);
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
	final Integer key = new Integer(id);
	final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(key);
	if (aco != null) {
	    aco.cb = cb;
	}
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     * 
     * @param dev
     *            DeviceProxy object.
     * @param reply_model
     *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public int pending_asynch_call(final DeviceProxy dev, final int reply_model) {
	int cnt = 0;
	final Enumeration _enum = async_request_table.keys();
	while (_enum.hasMoreElements()) {
	    final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(_enum
		    .nextElement());
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
	    final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(_enum
		    .nextElement());
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
	    final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(_enum
		    .nextElement());
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
	    final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(_enum
		    .nextElement());
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
	    final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(_enum
		    .nextElement());
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
	    final AsyncCallObject aco = (AsyncCallObject) async_request_table.get(_enum
		    .nextElement());
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
    /**
     * Convert arguments to one String array
     */
    // ==========================================================================
    public String[] toStringArray(final String objname, final String[] argin) {
	final String[] array = new String[1 + argin.length];
	array[0] = objname;
	for (int i = 0; i < argin.length; i++) {
	    array[i + 1] = argin[i];
	}
	return array;
    }

    // ==========================================================================
    /**
     * Convert arguments to one String array
     */
    // ==========================================================================
    public String[] toStringArray(final String objname, final String argin) {
	final String[] array = new String[2];
	array[0] = objname;
	array[1] = argin;
	return array;
    }

    // ==========================================================================
    /**
     * Convert arguments to one String array
     */
    // ==========================================================================
    public String[] toStringArray(final String argin) {
	final String[] array = new String[1];
	array[0] = argin;
	return array;
    }

    // ==========================================================================
    /**
     * Convert a DbAttribute class array to a StringArray.
     * 
     * @param objname
     *            object name (used in first index of output array)..
     * @param attr
     *            DbAttribute array to be converted
     * @return the String array created from input argument.
     */
    // ==========================================================================
    public String[] toStringArray(final String objname, final DbAttribute[] attr, final int mode) {
	final int nb_attr = attr.length;

	// Copy object name and nb attrib to String array
	final Vector vector = new Vector();
	vector.add(objname);
	vector.add(new String("" + nb_attr));
	for (int i = 0; i < nb_attr; i++) {
	    // Copy Attrib name and nb prop to String array
	    vector.add(attr[i].name);
	    vector.add(new String("" + attr[i].size()));
	    for (int j = 0; j < attr[i].size(); j++) {
		// Copy data to String array
		vector.add(attr[i].get_property_name(j));
		final String[] values = attr[i].get_value(j);
		if (mode != 1) {
		    vector.add(new String("" + values.length));
		}
		for (final String value : values) {
		    vector.add(value);
		}
	    }
	}
	// alloacte a String array
	final String[] array = new String[vector.size()];
	for (int i = 0; i < vector.size(); i++) {
	    array[i] = (String) vector.elementAt(i);
	}
	return array;
    }

    // ==========================================================================
    /**
     * Convert a StringArray to a DbAttribute class array
     * 
     * @param array
     *            String array to be converted
     * @param mode
     *            decode argout params mode (mode=2 added 26/10/04)
     * @return the DbAtribute class array created from input argument.
     */
    // ==========================================================================
    public DbAttribute[] toDbAttributeArray(final String[] array, final int mode) throws DevFailed {
	if (mode < 1 && mode > 2) {
	    Except.throw_non_supported_exception("API_NotSupportedMode", "Mode " + mode
		    + " to decode attribute properties is not supported",
		    "ApiUtil.toDbAttributeArray()");
	}

	int idx = 1;
	final int nb_attr = Integer.parseInt(array[idx++]);
	final DbAttribute[] attr = new DbAttribute[nb_attr];
	for (int i = 0; i < nb_attr; i++) {
	    // Create DbAttribute with name and nb properties
	    // ------------------------------------------------------
	    attr[i] = new DbAttribute(array[idx++]);

	    // Get nb properties
	    // ------------------------------------------------------
	    final int nb_prop = Integer.parseInt(array[idx++]);

	    for (int j = 0; j < nb_prop; j++) {
		// And copy property name and value in
		// DbAttribute's DbDatum array
		// ------------------------------------------
		final String p_name = array[idx++];
		switch (mode) {
		case 1:
		    // Value is just one element
		    attr[i].add(p_name, array[idx++]);
		    break;
		case 2:
		    // value is an array
		    final int p_length = Integer.parseInt(array[idx++]);
		    final String[] val = new String[p_length];
		    for (int p = 0; p < p_length; p++) {
			val[p] = array[idx++];
		    }
		    attr[i].add(p_name, val);
		    break;
		}
	    }
	}
	return attr;
    }

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

	    final Vector vector = new Vector();
	    while (stk.hasMoreTokens()) {
		// Get each Tango_host
		final String th = stk.nextToken();
		final StringTokenizer stk2 = new StringTokenizer(th, ":");
		vector.add(stk2.nextToken()); // Host Name
		vector.add(stk2.nextToken()); // Port Number
	    }

	    // Get the default one (first)
	    int i = 0;
	    host = (String) vector.elementAt(i++);
	    strport = (String) vector.elementAt(i++);
	    Integer.parseInt(strport);

	    // Put second one if exists in a singleton map object
	    final String def_tango_host = host + ":" + strport;
	    final DbRedundancy dbr = DbRedundancy.get_instance();
	    if (vector.size() > 3) {
		final String redun = (String) vector.elementAt(i++) + ":"
			+ (String) vector.elementAt(i++);
		dbr.put(def_tango_host, redun);
	    }
	} catch (final Exception e) {
	    Except.throw_exception("TangoApi_TANGO_HOST_NOT_SET", e.toString()
		    + " occured when parsing " + "\"TANGO_HOST\" property " + tgh,
		    "TangoApi.ApiUtil.parseTangoHost()");
	}
	final String[] array = { host, strport };
	return array;
    }

    // ===================================================================
    // ===================================================================

    // ===================================================================
    /**
     * Create the event consumer. This will automatically start a new thread
     * which is waiting indefinitely for events.
     */
    // ===================================================================
    public void create_event_consumer() throws DevFailed {
	event_consumer = EventConsumer.create();
    }

    // ===================================================================
    /**
     * Get the event consumer singleton object.
     * 
     * @return EventConsumer
     */
    // ===================================================================
    public IEventConsumer get_event_consumer() {
	return event_consumer;
    }
}
