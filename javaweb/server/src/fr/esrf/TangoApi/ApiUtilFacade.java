package fr.esrf.TangoApi;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.ApiUtil;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.Database;
import fr.esrf.TangoApi.DbAttribute;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.events.EventConsumer;

public class ApiUtilFacade {

	/**
	 * ORB object reference for connection.
	 */
	static protected ORB orb = null;

	// ===================================================================
	/**
	 * Return the database object created for specified host and port.
	 * 
	 * @param tango_host
	 *            host and port (hostname:portnumber) where database is running.
	 */
	// ===================================================================
	public static Database get_db_obj(String tango_host) throws DevFailed {
		throw new DevFailed("Operation not available for web access", new DevError[]{});
	}

	// ===================================================================
	/**
	 * Return the database object created with TANGO_HOST environment variable .
	 */
	// ===================================================================
	public static Database get_default_db_obj() throws DevFailed {
		return ApiUtil.get_default_db_obj();
	}

	// ===================================================================
	/**
	 * Return the database object created with TANGO_HOST environment variable .
	 */
	// ===================================================================
	public static synchronized Database get_db_obj() throws DevFailed {
		return ApiUtil.get_db_obj();
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
	public static Database get_db_obj(String host, String port) throws DevFailed {
		throw new DevFailed("Operation not available for web access", new DevError[]{});
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
	public static Database change_db_obj(String host, String port) throws DevFailed {
		throw new DevFailed("Operation not available for web access", new DevError[]{});
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
	public static Database set_db_obj(String host, String port) throws DevFailed {
		throw new DevFailed("Operation not available for web access", new DevError[]{});
	}

	// ===================================================================
	/**
	 * Return the database object created for specified host and port.
	 * 
	 * @param tango_host
	 *            host and port (hostname:portnumber) where database is running.
	 */
	// ===================================================================
	public static Database set_db_obj(String tango_host) throws DevFailed {
		throw new DevFailed("Operation not available for web access", new DevError[]{});
	}

	// ===================================================================
	// ===================================================================

	// ===================================================================
	/**
	 * Create the orb object
	 */
	// ===================================================================
	// TODO remove javadoc
	// ===================================================================
	/**
	 * Return the orb object
	 */
	// ===================================================================
	public static ORB get_orb() throws DevFailed {
		return ApiUtil.get_orb();
	}

	// ===================================================================
	/**
	 * Return the orb object
	 */
	// ===================================================================
	public static void set_in_server(boolean val) {
		ApiUtil.set_in_server(val);

	}

	// ===================================================================
	/**
	 * Return true if in server code or false if in client code.
	 */
	// ===================================================================
	public static boolean in_server() {
		return ApiUtil.in_server();
	}

	// ===================================================================
	/**
	 * Return reconnection delay for controle system.
	 */
	// ===================================================================
	private static int reconnection_delay = -1;

	public static int getReconnectionDelay() {
		return ApiUtil.getReconnectionDelay();

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
	public static int put_async_request(AsyncCallObject aco) {
		return ApiUtil.put_async_request(aco);

	}

	// ==========================================================================
	/**
	 * Return the request in hash table for the id
	 */
	// ==========================================================================
	public static Request get_async_request(int id) {
		return ApiUtil.get_async_request(id);

	}

	// ==========================================================================
	/**
	 * Return the Asynch Object in hash table for the id
	 */
	// ==========================================================================
	public static AsyncCallObject get_async_object(int id) {
		return ApiUtil.get_async_object(id);

	}

	// ==========================================================================
	/**
	 * Remove asynchronous call request and id from hashtable.
	 */
	// ==========================================================================
	public static void remove_async_request(int id) {
		ApiUtil.remove_async_request(id);

	}

	// ==========================================================================
	/**
	 * Set the reply_model in AsyncCallObject for the id key.
	 */
	// ==========================================================================
	public static void set_async_reply_model(int id, int reply_model) {
		ApiUtil.set_async_reply_model(id, reply_model);

	}

	// ==========================================================================
	/**
	 * Set the Callback object in AsyncCallObject for the id key.
	 */
	// ==========================================================================
	public static void set_async_reply_cb(int id, CallBack cb) {
		ApiUtil.set_async_reply_cb(id, cb);

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
	public static int pending_asynch_call(DeviceProxy dev, int reply_model) {
		return ApiUtil.pending_asynch_call(dev, reply_model);

	}

	// ==========================================================================
	/**
	 * return the still pending asynchronous call for a reply model.
	 * 
	 * @param reply_model
	 *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	// ==========================================================================
	public static int pending_asynch_call(int reply_model) {
		return ApiUtil.pending_asynch_call(reply_model);

	}

	// ==========================================================================
	/**
	 * Return the callback sub model used.
	 * 
	 * @param model
	 *            ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK.
	 */
	// ==========================================================================
	public static void set_asynch_cb_sub_model(int model) {
		ApiUtil.set_asynch_cb_sub_model(model);

	}

	// ==========================================================================
	/**
	 * Set the callback sub model used (ApiDefs.PUSH_CALLBACK or
	 * ApiDefs.PULL_CALLBACK).
	 */
	// ==========================================================================
	public static int get_asynch_cb_sub_model() {
		return ApiUtil.get_asynch_cb_sub_model();

	}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public static void get_asynch_replies() {
		ApiUtil.get_asynch_replies();

	}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public static void get_asynch_replies(int timeout) {
		ApiUtil.get_asynch_replies(timeout);

	}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public static void get_asynch_replies(DeviceProxy dev) {
		ApiUtil.get_asynch_replies(dev);

	}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public static void get_asynch_replies(DeviceProxy dev, int timeout) {
		ApiUtil.get_asynch_replies(dev, timeout);

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
	public static String[] toStringArray(String objname, String[] argin) {
		return ApiUtil.toStringArray(objname, argin);

	}

	// ==========================================================================
	/**
	 * Convert arguments to one String array
	 */
	// ==========================================================================
	public static String[] toStringArray(String objname, String argin) {
		return ApiUtil.toStringArray(objname, argin);

	}

	// ==========================================================================
	/**
	 * Convert arguments to one String array
	 */
	// ==========================================================================
	public static String[] toStringArray(String argin) {
		return ApiUtil.toStringArray(argin);

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
	public static String[] toStringArray(String objname, DbAttribute[] attr, int mode) {
		return ApiUtil.toStringArray(objname, attr, mode);

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
	public static DbAttribute[] toDbAttributeArray(String[] array, int mode) throws DevFailed {
		return ApiUtil.toDbAttributeArray(array, mode);

	}

	// ==========================================================================
	// ==========================================================================
	public static String stateName(DevState state) {
		return ApiUtil.stateName(state);

	}

	// ==========================================================================
	// ==========================================================================
	public static String stateName(short state_val) {
		return ApiUtil.stateName(state_val);

	}

	// ==========================================================================
	// ==========================================================================
	public static String qualityName(AttrQuality att_q) {
		return ApiUtil.qualityName(att_q);

	}

	// ==========================================================================
	// ==========================================================================
	public static String qualityName(short att_q_val) {
		return ApiUtil.qualityName(att_q_val);

	}

	// ===================================================================
	/**
	 * Parse Tango host (check multi Tango_host)
	 */
	// ===================================================================
	public static String[] parseTangoHost(String tgh) throws DevFailed {
		return ApiUtil.parseTangoHost(tgh);

	}

	// ===================================================================
	// ===================================================================

	// ===================================================================
	/**
	 * Create the event consumer. This will automatically start a new thread
	 * which is waiting indefinitely for events.
	 * 
	 * @return none
	 */
	// ===================================================================
	public static void create_event_consumer() throws DevFailed {
		ApiUtil.create_event_consumer();

	}

	// ===================================================================
	/**
	 * Get the event consumer singleton object.
	 * 
	 * @return EventConsumer
	 */
	// ===================================================================
	public static EventConsumer get_event_consumer() {
		return ApiUtil.get_event_consumer();

	}

}
