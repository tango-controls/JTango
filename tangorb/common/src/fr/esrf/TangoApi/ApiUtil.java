//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
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
// $Revision$
//
//-======================================================================


package fr.esrf.TangoApi;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.factory.TangoFactory;

import java.util.Vector;

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

public class ApiUtil {
    private static IApiUtilDAO apiutilDAO = TangoFactory.getSingleton().getApiUtilDAO();

    public IApiUtilDAO getApiUtilDAO() {
	    return apiutilDAO;
    }

    public void setApiUtilDAO(final IApiUtilDAO databaseDAO) {
    }

    /**
     * ORB object reference for connection.
     */
    static protected ORB orb = null;

    // ===================================================================
    /**
     * @return true if use the default factory (new ObjectDAODefaultImpl or
     *         false if introspection factory (mainly used when from web)
     */
    // ===================================================================
    public static boolean useDefaultFactory() {
	    return TangoFactory.getSingleton().isDefaultFactory();
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     * 
     * @param tango_host
     *            host and port (hostname:portnumber) where database is running.
     */
    // ===================================================================
    public static Database get_db_obj(final String tango_host) throws DevFailed {
	    return apiutilDAO.get_db_obj(tango_host);
    }

    // ===================================================================
    /**
     * Return the database object created with TANGO_HOST environment variable .
     */
    // ===================================================================
    public static Database get_default_db_obj() throws DevFailed {
	    return apiutilDAO.get_default_db_obj();
    }

    // ===================================================================
    /**
     * Return the database object created with TANGO_HOST environment variable .
     */
    // ===================================================================
    public static synchronized Database get_db_obj() throws DevFailed {
	    return apiutilDAO.get_db_obj();
    }

    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     * 
     * @param host host where database is running.
     * @param port port for database connection.
     */
    // ===================================================================
    public static Database get_db_obj(final String host, final String port) throws DevFailed {
	    return apiutilDAO.get_db_obj(host, port);
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
    public static Database change_db_obj(final String host, final String port) throws DevFailed {
	    return apiutilDAO.change_db_obj(host, port);
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
    public static Database set_db_obj(final String host, final String port) throws DevFailed {
	    return apiutilDAO.set_db_obj(host, port);
    }
    // ===================================================================
    /**
     * Return the database object created for specified host and port.
     * 
     * @param tango_host
     *            host and port (hostname:portnumber) where database is running.
     */
    // ===================================================================
    public static Database set_db_obj(final String tango_host) throws DevFailed {
	    return apiutilDAO.set_db_obj(tango_host);
    }
    // ===================================================================
    // ===================================================================




    // ===================================================================
    /**
     * Return the orb object
     */
    // ===================================================================
    public static ORB get_orb() throws DevFailed {
	return apiutilDAO.get_orb();
    }
    // ===================================================================
    /**
     * Return the host address.
     */
    // ===================================================================
	public static String getHostAddress() throws DevFailed {
		return HostInfo.getAddress();
	}
    // ===================================================================
    /**
     * Return the host addresses.
     */
    // ===================================================================
	public static Vector<String> getHostAddresses() throws DevFailed {
		return HostInfo.getAddresses();
	}
     // ===================================================================
    /**
     * Return the host name.
     */
    // ===================================================================
	public static String getHostName() throws DevFailed {
		return HostInfo.getName();
	}
    // ===================================================================
    /**
     * Return the orb object
     */
    // ===================================================================
    public static void set_in_server(final boolean val) {
    	apiutilDAO.set_in_server(val);
    }

    // ===================================================================
    /**
     * Return true if in server code or false if in client code.
     */
    // ===================================================================
    public static boolean in_server() {
	    return apiutilDAO.in_server();
    }

    public static int getReconnectionDelay() {
	    return apiutilDAO.getReconnectionDelay();

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
    public static int put_async_request(final AsyncCallObject aco) {
	    return apiutilDAO.put_async_request(aco);
    }

    // ==========================================================================
    /**
     * Return the request in hash table for the id
     * 
     * @throws DevFailed
     */
    // ==========================================================================
    public static Request get_async_request(final int id) throws DevFailed {
    	return apiutilDAO.get_async_request(id);
    }

    // ==========================================================================
    /**
     * Return the Asynch Object in hash table for the id
     */
    // ==========================================================================
    public static AsyncCallObject get_async_object(final int id) {
    	return apiutilDAO.get_async_object(id);
    }

    // ==========================================================================
    /**
     * Remove asynchronous call request and id from hashtable.
     */
    // ==========================================================================
    public static void remove_async_request(final int id) {
    	apiutilDAO.remove_async_request(id);
    }

    // ==========================================================================
    /**
     * Set the reply_model in AsyncCallObject for the id key.
     */
    // ==========================================================================
    public static void set_async_reply_model(final int id, final int reply_model) {
    	apiutilDAO.set_async_reply_model(id, reply_model);
    }

    // ==========================================================================
    /**
     * Set the Callback object in AsyncCallObject for the id key.
     */
    // ==========================================================================
    public static void set_async_reply_cb(final int id, final CallBack cb) {
	    apiutilDAO.set_async_reply_cb(id, cb);
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     * 
     * @param dev DeviceProxy object.
     * @param reply_model ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public static int pending_asynch_call(final DeviceProxy dev, final int reply_model) {
	    return apiutilDAO.pending_asynch_call(dev, reply_model);
    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     * 
     * @param reply_model ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public static int pending_asynch_call(final int reply_model) {
    	return apiutilDAO.pending_asynch_call(reply_model);
    }

    // ==========================================================================
    /**
     * Return the callback sub model used.
     * 
     * @param model ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK.
     */
    // ==========================================================================
    public static void set_asynch_cb_sub_model(final int model) {
	    apiutilDAO.set_asynch_cb_sub_model(model);
    }

    // ==========================================================================
    /**
     * Set the callback sub model used (ApiDefs.PUSH_CALLBACK or
     * ApiDefs.PULL_CALLBACK).
     */
    // ==========================================================================
    public static int get_asynch_cb_sub_model() {
	    return apiutilDAO.get_asynch_cb_sub_model();
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public static void get_asynch_replies() {
	    apiutilDAO.get_asynch_replies();
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public static void get_asynch_replies(final int timeout) {
	    apiutilDAO.get_asynch_replies(timeout);
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public static void get_asynch_replies(final DeviceProxy dev) {
	    apiutilDAO.get_asynch_replies(dev);
    }

    // ==========================================================================
    /**
     * Fire callback methods for all (any device) asynchronous requests(cmd and
     * attr) with already arrived replies.
     */
    // ==========================================================================
    public static void get_asynch_replies(final DeviceProxy dev, final int timeout) {
	    apiutilDAO.get_asynch_replies(dev, timeout);
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
    public static String[] toStringArray(final String objname, final String[] argin) {
	    return apiutilDAO.toStringArray(objname, argin);
    }

    // ==========================================================================
    /**
     * Convert arguments to one String array
     */
    // ==========================================================================
    public static String[] toStringArray(final String objname, final String argin) {
	    return apiutilDAO.toStringArray(objname, argin);
    }

    // ==========================================================================
    /**
     * Convert arguments to one String array
     */
    // ==========================================================================
    public static String[] toStringArray(final String argin) {
	    return apiutilDAO.toStringArray(argin);
    }

    // ==========================================================================
    /**
     * Convert a DbAttribute class array to a StringArray.
     * 
     * @param objname object name (used in first index of output array)..
     * @param attr DbAttribute array to be converted
     * @return the String array created from input argument.
     */
    // ==========================================================================
    public static String[] toStringArray(final String objname,
                                         final DbAttribute[] attr, final int mode) {
	    return apiutilDAO.toStringArray(objname, attr, mode);
    }

    // ==========================================================================
    /**
     * Convert a StringArray to a DbAttribute class array
     * 
     * @param array String array to be converted
     * @param mode decode argout params mode (mode=2 added 26/10/04)
     * @return the DbAtribute class array created from input argument.
     */
    // ==========================================================================
    public static DbAttribute[] toDbAttributeArray(
            final String[] array, final int mode) throws DevFailed {
	    return apiutilDAO.toDbAttributeArray(array, mode);
    }

    // ==========================================================================
    // ==========================================================================
    public static String stateName(final DevState state) {
	    return apiutilDAO.stateName(state);
    }

    // ==========================================================================
    // ==========================================================================
    public static String stateName(final short state_val) {
	    return apiutilDAO.stateName(state_val);
    }

    // ==========================================================================
    // ==========================================================================
    public static String qualityName(final AttrQuality att_q) {
	    return apiutilDAO.qualityName(att_q);
    }

    // ==========================================================================
    // ==========================================================================
    public static String qualityName(final short att_q_val) {
	    return apiutilDAO.qualityName(att_q_val);
    }

    // ===================================================================
    /**
     * Parse Tango host (check multi Tango_host)
     */
    // ===================================================================
    public static String[] parseTangoHost(final String tgh) throws DevFailed {
	    return apiutilDAO.parseTangoHost(tgh);
    }

    // ===================================================================
    // ===================================================================

    // ===================================================================
    // ===================================================================
    public static ORB getOrb() {
    	return orb;
    }

    // ===================================================================
    // ===================================================================
    public static void setOrb(final ORB orb) {
	    ApiUtil.orb = orb;
    }

    // ===================================================================
    // ===================================================================

    // ===================================================================
    /**
     * Returns the TANGO_HOST found in JVM, environment, tangorc file,....
     */
    // ===================================================================
    public static String getTangoHost() throws DevFailed {
	    return TangoEnv.getTangoHost();
    }

    // ===================================================================
    /**
     * Returns the SUPER_TANGO found in JVM, environment, tangorc file,....
     */
    // ===================================================================
    public static boolean isSuperTango() {
	    return TangoEnv.isSuperTango();
    }

    // ===================================================================
    /**
     * Returns the ACCESS_DEVNAME found in JVM, environment, tangorc file,....
     */
    // ===================================================================
    public static String getAccessDevname() {
	    return TangoEnv.getAccessDevname();
    }

    // ===================================================================
    /**
     * Returns the TANGO_TIMEOUT found in JVM, environment, tangorc file,....
     */
    // ===================================================================
    public static String getStrDefaultTimeout() {
	    return TangoEnv.getStrDefaultTimeout();
    }

    // ===================================================================
    /**
     * Returns the ORBgiopMaxMsgSize found in JVM, environment, tangorc
     * file,....
     */
    // ===================================================================
    public static String getORBgiopMaxMsgSize() {
	    return TangoEnv.getORBgiopMaxMsgSize();
    }
	//===============================================================
    /**
     * Set the ZMQ high water mark buffer size.
     * @param bufferSize the expected buffer size.
     */
	//===============================================================
    private static int  hwmValue = 0;
    public static void setEventBufferHWM(int bufferSize) {
        hwmValue = bufferSize;
    }
	//===============================================================
    /**
     * Get the ZMQ high water mark buffer size.
     * @return the expected buffer size.
     */
	//===============================================================
    public static int getEventBufferHWM() {
        return hwmValue;
    }
	//===============================================================
	//===============================================================
    public static void printTrace(String str) {
        String  trace = System.getenv("ApiTrace");
        if (trace!=null) {
            if (trace.equals("true")) {
                System.out.println(str);
            }
        }

    }
    // ===================================================================
    // ===================================================================
}
