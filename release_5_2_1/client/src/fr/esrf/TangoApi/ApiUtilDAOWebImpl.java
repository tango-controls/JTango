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
// Revision 1.1  2007/07/04 12:55:34  ounsy
// creation of 3 sub modules :
// 	- client for the webtangorb classes
// 	- common for the classes used by webtangorb and the tangowebserver
// 	- server for the generic classes of tangowebserver
//
// Revision 1.3  2007/07/02 12:03:46  ounsy
// Correction for tango web access
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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;	

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.TangoApi.events.EventConsumer;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

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
 * @version $Revision$
 */

public class ApiUtilDAOWebImpl implements IApiUtilDAO, IWebImpl{
private Object[] classParam = new Object[]{};

	// ===================================================================
	/**
	 * Return the database object created for specified host and port.
	 * 
	 * @param tango_host
	 *            host and port (hostname:portnumber) where database is running.
	 */
	// ===================================================================
	public Database get_db_obj(String tango_host) throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"get_db_obj", new Object[]{tango_host}, new Class[]{String.class});
	
}

	// ===================================================================
	/**
	 * Return the database object created with TANGO_HOST environment variable .
	 */
	// ===================================================================
	public Database get_default_db_obj() throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"get_default_db_obj", new Object[]{}, new Class[]{});
	
}

	// ===================================================================
	/**
	 * Return the database object created with TANGO_HOST environment variable .
	 */
	// ===================================================================
	public synchronized Database get_db_obj() throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"get_db_obj", new Object[]{}, new Class[]{});	
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
	public Database get_db_obj(String host, String port) throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"get_db_obj", new Object[]{host, port}, new Class[]{String.class, String.class});
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
	public Database change_db_obj(String host, String port) throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"change_db_obj", new Object[]{host, port}, new Class[]{String.class, String.class});
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
	public Database set_db_obj(String host, String port) throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"set_db_obj", new Object[]{host, port}, new Class[]{String.class, String.class});
}

	// ===================================================================
	/**
	 * Return the database object created for specified host and port.
	 * 
	 * @param tango_host
	 *            host and port (hostname:portnumber) where database is running.
	 */
	// ===================================================================
	public Database set_db_obj(String tango_host) throws DevFailed {
		return (Database) WebServerClientUtil.getResponse(this, classParam,"set_db_obj", new Object[]{tango_host}, new Class[]{String.class});
}

	// ===================================================================
	// ===================================================================

	// ===================================================================
	/**
	 * Create the orb object
	 */
	// ===================================================================
//TODO remove javadoc

	// ===================================================================
	/**
	 * Return the orb object
	 */
	// ===================================================================
	public ORB get_orb() throws DevFailed {
		return (ORB) WebServerClientUtil.getResponse(this, classParam,"get_orb", new Object[]{}, new Class[]{});
	
}

	// ===================================================================
	/**
	 * Return the orb object
	 */
	// ===================================================================
	public void set_in_server(boolean val) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "set_in_server", new Object[]{val}, new Class[]{boolean.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ===================================================================
	/**
	 * Return true if in server code or false if in client code.
	 */
	// ===================================================================
	public boolean in_server() {
		try
		{
		return (Boolean) WebServerClientUtil.getResponse(this, classParam,"in_server", new Object[]{}, new Class[]{});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return false;
		}	
}

	// ===================================================================
	/**
	 * Return reconnection delay for controle system.
	 */
	// ===================================================================
	private int reconnection_delay = -1;

	public int getReconnectionDelay() {
		try
		{
		return (Integer) WebServerClientUtil.getResponse(this, classParam,"getReconnectionDelay", new Object[]{}, new Class[]{});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return 0;
		}	
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
	public int put_async_request(AsyncCallObject aco) {
		try
		{
		return (Integer) WebServerClientUtil.getResponse(this, classParam,"put_async_request", new Object[]{aco}, new Class[]{AsyncCallObject.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return 0;
		}	
}

	// ==========================================================================
	/**
	 * Return the request in hash table for the id
	 */
	// ==========================================================================
	public Request get_async_request(int id) {
		try
		{
		return (Request) WebServerClientUtil.getResponse(this, classParam,"get_async_request", new Object[]{id}, new Class[]{int.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	/**
	 * Return the Asynch Object in hash table for the id
	 */
	// ==========================================================================
	public AsyncCallObject get_async_object(int id) {
		try
		{
		return (AsyncCallObject) WebServerClientUtil.getResponse(this, classParam,"get_async_object", new Object[]{id}, new Class[]{int.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	/**
	 * Remove asynchronous call request and id from hashtable.
	 */
	// ==========================================================================
	public void remove_async_request(int id) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "remove_async_request", new Object[]{id}, new Class[]{int.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ==========================================================================
	/**
	 * Set the reply_model in AsyncCallObject for the id key.
	 */
	// ==========================================================================
	public void set_async_reply_model(int id, int reply_model) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "set_async_reply_model", new Object[]{id, reply_model}, new Class[]{int.class, int.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ==========================================================================
	/**
	 * Set the Callback object in AsyncCallObject for the id key.
	 */
	// ==========================================================================
	public void set_async_reply_cb(int id, CallBack cb) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "set_async_reply_cb", new Object[]{id, cb}, new Class[]{int.class, CallBack.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
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
	public int pending_asynch_call(DeviceProxy dev, int reply_model) {
		try
		{
		return (Integer) WebServerClientUtil.getResponse(this, classParam,"pending_asynch_call", new Object[]{dev, reply_model}, new Class[]{DeviceProxy.class, int.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return 0;
		}	
}

	// ==========================================================================
	/**
	 * return the still pending asynchronous call for a reply model.
	 * 
	 * @param reply_model
	 *            ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	// ==========================================================================
	public int pending_asynch_call(int reply_model) {
		try
		{
		return (Integer) WebServerClientUtil.getResponse(this, classParam,"pending_asynch_call", new Object[]{reply_model}, new Class[]{int.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return 0;
		}	
}

	// ==========================================================================
	/**
	 * Return the callback sub model used.
	 * 
	 * @param model
	 *            ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK.
	 */
	// ==========================================================================
	public void set_asynch_cb_sub_model(int model) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "set_asynch_cb_sub_model", new Object[]{model}, new Class[]{int.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ==========================================================================
	/**
	 * Set the callback sub model used (ApiDefs.PUSH_CALLBACK or
	 * ApiDefs.PULL_CALLBACK).
	 */
	// ==========================================================================
	public int get_asynch_cb_sub_model() {
		try
		{
		return (Integer) WebServerClientUtil.getResponse(this, classParam,"get_asynch_cb_sub_model", new Object[]{}, new Class[]{});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return 0;
		}	
}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public void get_asynch_replies() {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "get_asynch_replies", new Object[]{}, new Class[]{});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public void get_asynch_replies(int timeout) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "get_asynch_replies", new Object[]{timeout}, new Class[]{int.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public void get_asynch_replies(DeviceProxy dev) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "get_asynch_replies", new Object[]{dev}, new Class[]{DeviceProxy.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
		}	
}

	// ==========================================================================
	/**
	 * Fire callback methods for all (any device) asynchronous requests(cmd and
	 * attr) with already arrived replies.
	 */
	// ==========================================================================
	public void get_asynch_replies(DeviceProxy dev, int timeout) {
		try
		{
		WebServerClientUtil.getResponse(this, classParam, "get_asynch_replies", new Object[]{dev, timeout}, new Class[]{DeviceProxy.class, int.class});

		}
		catch(DevFailed dfe)
		{
			dfe.printStackTrace();
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
	public String[] toStringArray(String objname, String[] argin) {
		try
		{
		return (String[]) WebServerClientUtil.getResponse(this, classParam,"toStringArray", new Object[]{objname, argin}, new Class[]{String.class, String[].class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	/**
	 * Convert arguments to one String array
	 */
	// ==========================================================================
	public String[] toStringArray(String objname, String argin) {
		try
		{
		return (String[]) WebServerClientUtil.getResponse(this, classParam,"toStringArray", new Object[]{objname, argin}, new Class[]{String.class, String.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	/**
	 * Convert arguments to one String array
	 */
	// ==========================================================================
	public String[] toStringArray(String argin) {
		try
		{
		return (String[]) WebServerClientUtil.getResponse(this, classParam,"toStringArray", new Object[]{argin}, new Class[]{String.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
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
	public String[] toStringArray(String objname, DbAttribute[] attr, int mode) {
		try
		{
		return (String[]) WebServerClientUtil.getResponse(this, classParam,"toStringArray", new Object[]{objname, attr, mode}, new Class[]{String.class, DbAttribute[].class, int.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
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
	public DbAttribute[] toDbAttributeArray(String[] array, int mode) throws DevFailed {
		return (DbAttribute[]) WebServerClientUtil.getResponse(this, classParam,"toDbAttributeArray", new Object[]{array, mode}, new Class[]{String[].class, int.class});
	
}

	// ==========================================================================
	// ==========================================================================
	public String stateName(DevState state) {
		try
		{
		return (String) WebServerClientUtil.getResponse(this, classParam,"stateName", new Object[]{state}, new Class[]{DevState.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	// ==========================================================================
	public String stateName(short state_val) {
		try
		{
		return (String) WebServerClientUtil.getResponse(this, classParam,"stateName", new Object[]{state_val}, new Class[]{short.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	// ==========================================================================
	public String qualityName(AttrQuality att_q) {
		try
		{
		return (String) WebServerClientUtil.getResponse(this, classParam,"qualityName", new Object[]{att_q}, new Class[]{AttrQuality.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ==========================================================================
	// ==========================================================================
	public String qualityName(short att_q_val) {
		try
		{
		return (String) WebServerClientUtil.getResponse(this, classParam,"qualityName", new Object[]{att_q_val}, new Class[]{short.class});

		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	// ===================================================================
	/**
	 * Parse Tango host (check multi Tango_host)
	 */
	// ===================================================================
	public String[] parseTangoHost(String tgh) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam,"parseTangoHost", new Object[]{tgh}, new Class[]{String.class});
	
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
	public void create_event_consumer() throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "create_event_consumer", new Object[]{}, new Class[]{});
	
}

	// ===================================================================
	/**
	 * Get the event consumer singleton object.
	 * 
	 * @return EventConsumer
	 */
	// ===================================================================
	public EventConsumer get_event_consumer() {
		try
		{
			return (EventConsumer) WebServerClientUtil.getResponse(this, classParam,"get_event_consumer", new Object[]{}, new Class[]{});
		}
		catch(DevFailed dfe) 
		{
			dfe.printStackTrace();
			return null;
		}	
}

	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}
}
