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
// Revision 1.17  2010/12/17 13:00:37  pascal_verdier
// Problem on host address for TangoAccess (127.0.1.1) fixed
// In case of READ_ONLY mode (Access Control used) user and host address
// 	are sent in the DevFailed exception.
//
// Revision 1.16  2010/08/09 12:37:31  abeilleg
// method signature changed. throw exception
//
// Revision 1.15  2010/05/31 12:32:45  abeilleg
// make the projet compile with maven: remove dependency to javadao
//
// Revision 1.14  2009/09/11 12:09:43  pascal_verdier
// tangorc environment file is managed.
//
// Revision 1.13  2009/03/25 13:27:53  pascal_verdier
// ...
//
// Revision 1.12  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.11  2008/12/03 15:39:50  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.10  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.9  2008/09/12 11:20:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.8  2008/07/11 08:36:36  pascal_verdier
// useDefaultFactory() added
//
// Revision 1.7  2008/01/10 15:40:22  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.6  2007/09/13 09:04:46  ounsy
// delete private field. These fields are now in the implementation class
//
// Revision 1.5  2007/08/23 09:42:21  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:37  ounsy
// Minor modification of common classes :
// - add getter and setter
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

import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoApi.events.IEventConsumer;

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
     * @param host
     *            host where database is running.
     * @param port
     *            port for database connection.
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
     * @param host
     *            host where database is running.
     * @param port
     *            port for database connection.
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
     * @param host
     *            host where database is running.
     * @param port
     *            port for database connection.
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
    /*
     * Create the orb object
     */
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
     * @param dev
     *            DeviceProxy object.
     * @param reply_model
     *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public static int pending_asynch_call(final DeviceProxy dev, final int reply_model) {
	return apiutilDAO.pending_asynch_call(dev, reply_model);

    }

    // ==========================================================================
    /**
     * return the still pending asynchronous call for a reply model.
     * 
     * @param reply_model
     *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
     */
    // ==========================================================================
    public static int pending_asynch_call(final int reply_model) {
	return apiutilDAO.pending_asynch_call(reply_model);

    }

    // ==========================================================================
    /**
     * Return the callback sub model used.
     * 
     * @param model
     *            ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK.
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
     * @param objname
     *            object name (used in first index of output array)..
     * @param attr
     *            DbAttribute array to be converted
     * @return the String array created from input argument.
     */
    // ==========================================================================
    public static String[] toStringArray(final String objname, final DbAttribute[] attr,
	    final int mode) {
	return apiutilDAO.toStringArray(objname, attr, mode);

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
    public static DbAttribute[] toDbAttributeArray(final String[] array, final int mode)
	    throws DevFailed {
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
    /**
     * Create the event consumer. This will automatically start a new thread
     * which is waiting indefinitely for events.
     */
    // ===================================================================
    public static void create_event_consumer() throws DevFailed {
	apiutilDAO.create_event_consumer();

    }

    // ===================================================================
    /**
     * Get the event consumer singleton object.
     * 
     * @return EventConsumer
     */
    // ===================================================================
    public static IEventConsumer get_event_consumer() {
	return apiutilDAO.get_event_consumer();

    }

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
    // ===================================================================
    // ===================================================================
}
