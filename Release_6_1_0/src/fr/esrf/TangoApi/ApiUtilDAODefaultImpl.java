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
import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;

import java.util.*;

/**
 *	Class Description:
 *	This class manage a static vector of Database object.
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *		Database	dbase = ApiUtil.get_db_obj(); <Br>
 *	</ul></i>
 *
 * @author  verdier
 * @version  $Revision$
 */

public class ApiUtilDAODefaultImpl implements IApiUtilDAO
{
	static private Vector	db_list = null;
	static private Database	default_dbase = null;
	static private EventConsumer event_consumer = null;

	static private Hashtable 	async_request_table = null;
	static private int			async_request_cnt   = 0;
	static private int			async_cb_sub_model  = ApiDefs.PULL_CALLBACK;
	static private boolean		in_server_code		= false;

	//===================================================================
	/**
	 *	Return the database object created for specified host and port.
	 *
	 *	@param	tango_host	host and port (hostname:portnumber)
	 *							where database is running.
	 */
	//===================================================================
	public Database get_db_obj(String tango_host) throws DevFailed
	{
		int	i = tango_host.indexOf(":");
		if (i<=0)
			Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
									"Cannot parse port number",
									"ApiUtil.get_db_obj()");
		return
			get_db_obj(tango_host.substring(0, i), tango_host.substring(i+1));

	}
	//===================================================================
	/**
	 *	Return the database object created
	 *	with TANGO_HOST environment variable .
	 */
	//===================================================================
	public Database get_default_db_obj() throws DevFailed
	{
		if (default_dbase==null)
			return get_db_obj();
		else
			return default_dbase;
	}
	//===================================================================
	/**
	 *	Return the database object created
	 *	with TANGO_HOST environment variable .
	 */
	//===================================================================
	public synchronized Database get_db_obj() throws DevFailed
	{
		if (ApiUtil.getOrb()==null)
			create_orb();

		//	If first time, create vector
		//---------------------------------------------------------------
		if (db_list==null)
		{
			db_list = new Vector();
		}
		//	If first time, create Database object
		//-----------------------------------------------------------
		if (default_dbase==null)
		{
			default_dbase = new Database();
			db_list.addElement(default_dbase);
		}
		return (Database) db_list.elementAt(0);
	}
	//===================================================================
	/**
	 *	Return the database object created for specified host and port.
	 *
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public Database get_db_obj(String host, String port) throws DevFailed
	{
		if (ApiUtil.getOrb()==null)
			create_orb();

		//	If first time, create vector
		//---------------------------------------------------------------
		if (db_list==null)
			db_list = new Vector();

		//	Build tango_host string
		//---------------------------
		String tango_host = new String(host + ":" + port);

		//	Search if database object already created for this host and port
		//-------------------------------------------------------------------
		if (default_dbase!=null)
			if (default_dbase.get_tango_host().equals(tango_host))
				return default_dbase;

		for (int i=0 ; i<db_list.size() ; i++)
		{
			Database dbase = (Database) db_list.elementAt(i);
			if (dbase.get_tango_host().equals(tango_host))
				return dbase;
		}

		//	Else, create a new database object
		//---------------------------------------
		Database dbase = new Database(host, port);
		db_list.add(dbase);
		return dbase;
	}
    //===================================================================
    /**
     *	Return the database object created for specified host and port,
     *		and set this database object for all following uses..
     *
     *	@param	host	host where database is running.
     *	@param	port	port for database connection.
     */
    //===================================================================
    public Database change_db_obj(String host, String port) throws DevFailed
    {
		//	Get requested database object
		Database	dbase = get_db_obj(host, port);
		//	And set it at first vector element as default Dbase
		db_list.remove(dbase);
		db_list.insertElementAt(dbase, 0);
		default_dbase = dbase;
		return dbase;
	}
    //===================================================================
    /**
     *	Return the database object created for specified host and port,
     *		and set this database object for all following uses..
     *
     *	@param	host	host where database is running.
     *	@param	port	port for database connection.
     */
    //===================================================================
    public Database set_db_obj(String host, String port) throws DevFailed
    {
		return change_db_obj(host, port);
	}
	//===================================================================
	/**
	 *	Return the database object created for specified host and port.
	 *
	 *	@param	tango_host	host and port (hostname:portnumber)
	 *							where database is running.
	 */
	//===================================================================
	public Database set_db_obj(String tango_host) throws DevFailed
	{
		int	i = tango_host.indexOf(":");
		if (i<=0)
			Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
									"Cannot parse port number",
									"ApiUtil.set_db_obj()");
		return
			change_db_obj(tango_host.substring(0, i), tango_host.substring(i+1));
	}
	//===================================================================
	//===================================================================


	//===================================================================
	/**
	 *	Create the orb object
	 */
	//===================================================================
	private static synchronized void create_orb() throws DevFailed
	{
		try
		{
			//	Modified properties fo ORB usage.
			//---------------------------------------
			Properties props = System.getProperties();
			props.put("org.omg.CORBA.ORBClass","org.jacorb.orb.ORB");
			props.put("org.omg.CORBA.ORBSingletonClass","org.jacorb.orb.ORBSingleton");


			//	Set retry properties
			props.put("jacorb.retries", "1");
			props.put("jacorb.retry_interval", "100");

			//	Initial timeout for establishing a connection.
			props.put("jacorb.connection.client.connect_timeout", "5000");

			//	Set the Largest transfert to 16 Mbytes
			//	2**(5+maxManagedBufSize-1)
			props.put("jacorb.maxManagedBufSize", "20");

			//	Set jacorb verbosity at minimum value
			props.put("jacorb.config.log.verbosity", "0");

			System.setProperties(props);

			//	Initialize ORB
			//-----------------------------
			String[]	argv = null;
			ApiUtil.setOrb(ORB.init(argv, null));

			//	Create the default database
			//get_db_obj();
		}
		catch(SystemException ex)
		{
			//System.out.println("Excption catched in ApiUtil.create_orb");
			ApiUtil.setOrb(null);
			ex.printStackTrace();
			Except.throw_connection_failed( ex.toString(),
					"Initializing ORB failed !",
					"ApiUtil.create_orb()");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	//===================================================================
	/**
	 *	Return the orb object
	 */
	//===================================================================
	public ORB get_orb() throws DevFailed
	{
		if (ApiUtil.getOrb()==null)
			create_orb();
		return ApiUtil.getOrb();
	}
	//===================================================================
	/**
	 *	Return the orb object
	 */
	//===================================================================
	public void set_in_server(boolean val)
	{
		in_server_code = val;
	}
	//===================================================================
	/**
	 *	Return true if in server code or false if in client code.
	 */
	//===================================================================
	public boolean in_server()
	{
		return in_server_code;
	}
	//===================================================================
	/**
	 *	Return reconnection delay for controle system.
	 */
	//===================================================================
	private static int	reconnection_delay = -1;
	public int getReconnectionDelay()
	{
		if (reconnection_delay<0)
		{
			try
			{
				DbDatum	data = get_db_obj().get_property(
					TangoConst.CONTROL_SYSTEM, "ReconnectionDelay");
				if (data.is_empty()==false)
					reconnection_delay = data.extractLong();
			}
			catch (DevFailed e){}
			if (reconnection_delay<0)
				reconnection_delay = 1000;
		}
		return reconnection_delay;
	}

	//==========================================================================
	/*
	 *	Asynchronous request management
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Add request in hash table and return id
	 */
	//==========================================================================
	public int put_async_request(AsyncCallObject aco)
	{
		if (async_request_table==null)
			async_request_table = new Hashtable();
		async_request_cnt++;
		aco.id = async_request_cnt;
		Integer	key = new Integer(async_request_cnt);
		async_request_table.put(key, aco);
		return async_request_cnt;
	}

	//==========================================================================
	/**
	 *	Return the request in hash table for the id
	 */
	//==========================================================================
	public Request get_async_request(int id)
	{
		Integer	key = new Integer(id);
		AsyncCallObject	aco = (AsyncCallObject)async_request_table.get(key);
		return aco.request;
	}
	//==========================================================================
	/**
	 *	Return the Asynch Object in hash table for the id
	 */
	//==========================================================================
	public AsyncCallObject get_async_object(int id)
	{
		Integer	key = new Integer(id);
		return (AsyncCallObject)async_request_table.get(key);
	}
	//==========================================================================
	/**
	 *	Remove asynchronous call request and id from hashtable.
	 */
	//==========================================================================
	public void remove_async_request(int id)
	{
		Integer	key = new Integer(id);

		//	Try to destroye Request object (added by PV 7/9/06)
		AsyncCallObject	aco = (AsyncCallObject)async_request_table.get(key);
		((org.jacorb.orb.ORB)ApiUtil.getOrb()).removeRequest(aco.request);

		async_request_table.remove(key);
	}
	//==========================================================================
	/**
	 *	Set the reply_model in  AsyncCallObject for the id key.
	 */
	//==========================================================================
	public void set_async_reply_model(int id, int reply_model)
	{
		Integer	key = new Integer(id);
		AsyncCallObject	aco = (AsyncCallObject)async_request_table.get(key);
		aco.reply_model = reply_model;
	}
	//==========================================================================
	/**
	 *	Set the Callback object in AsyncCallObject for the id key.
	 */
	//==========================================================================
	public void set_async_reply_cb(int id, CallBack cb)
	{
		Integer	key = new Integer(id);
		AsyncCallObject	aco = (AsyncCallObject)async_request_table.get(key);
		aco.cb = cb;
	}
	//==========================================================================
	/**
	 *	return the still pending asynchronous call for a reply model.
	 *	@param	dev			DeviceProxy object.
	 *	@param	reply_model	ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	//==========================================================================
	public int pending_asynch_call(DeviceProxy dev, int reply_model)
	{
		int	cnt = 0;
		Enumeration	_enum = async_request_table.keys();
		while (_enum.hasMoreElements())
		{
			AsyncCallObject	aco =
				(AsyncCallObject)async_request_table.get(_enum.nextElement());
			if (aco.dev == dev &&
				(reply_model==ApiDefs.ALL_ASYNCH || aco.reply_model==reply_model))
				cnt++;
		}
		return cnt;
	}
	//==========================================================================
	/**
	 *	return the still pending asynchronous call for a reply model.
	 *	@param	reply_model	ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	//==========================================================================
	public int pending_asynch_call(int reply_model)
	{
		int	cnt = 0;
		Enumeration	_enum = async_request_table.keys();
		while (_enum.hasMoreElements())
		{
			AsyncCallObject	aco =
				(AsyncCallObject)async_request_table.get(_enum.nextElement());
			if (reply_model==ApiDefs.ALL_ASYNCH || aco.reply_model==reply_model)
				cnt++;
		}
		return cnt;
	}
	//==========================================================================
	/**
	 *	Return the callback sub model used.
	 *	@param model ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK.
	 */
	//==========================================================================
	public void set_asynch_cb_sub_model(int model)
	{
		async_cb_sub_model = model;
	}

	//==========================================================================
	/**
	 *	Set the callback sub model used
	 *	(ApiDefs.PUSH_CALLBACK or ApiDefs.PULL_CALLBACK).
	 */
	//==========================================================================
	public int get_asynch_cb_sub_model()
	{
		return async_cb_sub_model;
	}
	//==========================================================================
	/**
	 *	Fire callback methods for all (any device)
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public void get_asynch_replies()
	{
		Enumeration	_enum = async_request_table.keys();
		while (_enum.hasMoreElements())
		{
			AsyncCallObject	aco =
				(AsyncCallObject)async_request_table.get(_enum.nextElement());
			aco.manage_reply(ApiDefs.NO_TIMEOUT);
		}
	}
	//==========================================================================
	/**
	 *	Fire callback methods for all (any device)
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public void get_asynch_replies(int timeout)
	{
		Enumeration	_enum = async_request_table.keys();
		while (_enum.hasMoreElements())
		{
			AsyncCallObject	aco =
				(AsyncCallObject)async_request_table.get(_enum.nextElement());
			aco.manage_reply(timeout);
		}
	}

	//==========================================================================
	/**
	 *	Fire callback methods for all (any device)
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public void get_asynch_replies(DeviceProxy dev)
	{
		Enumeration	_enum = async_request_table.keys();
		while (_enum.hasMoreElements())
		{
			AsyncCallObject	aco =
				(AsyncCallObject)async_request_table.get(_enum.nextElement());
			if (aco.dev==dev)
				aco.manage_reply(ApiDefs.NO_TIMEOUT);
		}
	}

	//==========================================================================
	/**
	 *	Fire callback methods for all (any device)
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public void get_asynch_replies(DeviceProxy dev, int timeout)
	{
		Enumeration	_enum = async_request_table.keys();
		while (_enum.hasMoreElements())
		{
			AsyncCallObject	aco =
				(AsyncCallObject)async_request_table.get(_enum.nextElement());
			if (aco.dev==dev)
				aco.manage_reply(timeout);
		}
	}









	//==========================================================================
	/*
	 *	Methods to convert data.
	 */
	//==========================================================================

	//==========================================================================
	/**
	 *	Convert arguments to one String array
	 */
	//==========================================================================
	public String[] toStringArray(String objname, String[] argin)
	{
		String[] array = new String[1 + argin.length];
		array[0] = objname;
		for (int i=0 ; i<argin.length ; i++)
			array[i+1] = argin[i];
		return array;
	}
	//==========================================================================
	/**
	 *	Convert arguments to one String array
	 */
	//==========================================================================
	public String[] toStringArray(String objname, String argin)
	{
		String[] array = new String[2];
		array[0] = objname;
		array[1] = argin;
		return array;
	}
	//==========================================================================
	/**
	 *	Convert arguments to one String array
	 */
	//==========================================================================
	public String[] toStringArray(String argin)
	{
		String[] array = new String[1];
		array[0] = argin;
		return array;
	}
	//==========================================================================
	/**
	 *	Convert a DbAttribute class array to a StringArray.
	 *	@param	objname	object name (used in first index of output array)..
	 *	@param	attr	DbAttribute array to be converted
	 *	@return	the String array created from input argument.
	 */
	//==========================================================================
	public  String[] toStringArray(String objname, DbAttribute[] attr, int mode)
	{
		int			nb_attr = attr.length;

		//	Copy object name and nb attrib to String array
		Vector	vector = new Vector();
		vector.add(objname);
		vector.add(new String("" + nb_attr));
		for (int i=0 ; i<nb_attr ; i++)
		{
			//	Copy Attrib name and nb prop to String array
			vector.add(attr[i].name);
			vector.add(new String("" + attr[i].size()));
			for (int j=0 ; j<attr[i].size() ; j++)
			{
				//	Copy data to String array
				vector.add(attr[i].get_property_name(j));
				String[]	values = attr[i].get_value(j);
				if (mode!=1)
					vector.add(new String(""+values.length));
				for (int v=0 ; v<values.length ; v++)
					vector.add(values[v]);
			}
		}
		//	alloacte a String array
		String[]	array = new String[vector.size()];
		for (int i=0 ; i<vector.size() ; i++)
			array[i] = (String) vector.elementAt(i);
		return array;
	}
	//==========================================================================
	/**
	 *	Convert a StringArray to a DbAttribute class array
	 *	@param	array	String array to be converted
	 *	@param	mode	decode argout params mode (mode=2 added 26/10/04)
	 *	@return	the DbAtribute class array created from input argument.
	 */
	//==========================================================================
	public DbAttribute[] toDbAttributeArray(String[] array, int mode)
				throws DevFailed
	{
		if (mode<1 && mode>2)
			Except.throw_non_supported_exception("API_NotSupportedMode",
				"Mode " + mode + " to decode attribute properties is not supported",
				"ApiUtil.toDbAttributeArray()");

		int	idx = 1;
		int	nb_attr = Integer.parseInt(array[idx++]);
		DbAttribute[]	attr = new DbAttribute[nb_attr];
		for (int i=0 ; i<nb_attr ; i++)
		{
			//	Create DbAttribute with name and nb properties
			//------------------------------------------------------
			attr[i] = new DbAttribute(array[idx++]);

			//	Get nb properties
			//------------------------------------------------------
			int	nb_prop = Integer.parseInt(array[idx++]);

			for (int j=0 ; j<nb_prop ; j++)
			{
				//	And copy property name and value in
				//	DbAttribute's DbDatum array
				//------------------------------------------
				String	p_name   = array[idx++];
				switch (mode)
				{
				case 1:
					//	Value is just one element
					attr[i].add(p_name, array[idx++]);
					break;
				case 2:
					//	value is an array
					int		p_length = Integer.parseInt(array[idx++]);
					String[]	val = new String[p_length];
					for (int p=0 ; p<p_length ; p++)
						val[p] = array[idx++];
					attr[i].add(p_name, val);
					break;
				}
			}
		}
		return attr;
	}
	//==========================================================================
	//==========================================================================
	public String stateName(DevState state)
	{
		return TangoConst.Tango_DevStateName[ state.value() ];
	}
	//==========================================================================
	//==========================================================================
	public String stateName(short state_val)
	{
		return TangoConst.Tango_DevStateName[ state_val ];
	}
	//==========================================================================
	//==========================================================================
	public String qualityName(AttrQuality att_q)
	{
		return TangoConst.Tango_QualityName[ att_q.value() ];
	}
	//==========================================================================
	//==========================================================================
	public String qualityName(short att_q_val)
	{
		return TangoConst.Tango_QualityName[ att_q_val ];
	}
	//===================================================================
	/**
	 *	Parse Tango host (check multi Tango_host)
	 */
	//===================================================================
	public String[] parseTangoHost(String tgh)	throws DevFailed
	{
		String	host    = null;
		String	strport = null;
		try
		{
			//	Check if there is more than one Tango Host
			StringTokenizer stk;
			if (tgh.indexOf(",")>0)
				stk = new StringTokenizer(tgh, ",");
			else
				stk = new StringTokenizer(tgh);

			Vector	vector = new Vector();
			while(stk.hasMoreTokens())
			{
				//	Get each Tango_host
				String	th = stk.nextToken();
				StringTokenizer stk2 = new StringTokenizer(th, ":");
				vector.add(stk2.nextToken());	//	Host Name
				vector.add(stk2.nextToken());	//	Port Number
			}

			//	Get the default one (first)
			int	i = 0;
			host    = (String)vector.elementAt(i++);
			strport = (String)vector.elementAt(i++);
			Integer.parseInt(strport);

			//	Put second one if exists in a singleton map object
			String			def_tango_host = host + ":" + strport;
			DbRedundancy	dbr = DbRedundancy.get_instance();
			if (vector.size()>3)
			{
				String	redun = (String)vector.elementAt(i++) + ":" +
								(String)vector.elementAt(i++);
				dbr.put(def_tango_host, redun);
			}
		}
		catch(Exception e)
		{
			Except.throw_exception("TangoApi_TANGO_HOST_NOT_SET",
									e.toString() + " occured when parsing " +
									"\"TANGO_HOST\" property " + tgh,
									"TangoApi.ApiUtil.parseTangoHost()");
		}
		String[]	array = { host, strport };
		return array;
	}
	//===================================================================
	//===================================================================




	//===================================================================
	/**
	 *	Create the event consumer. This will automatically start
	 *      a new thread which is waiting indefinitely for events.
	 *
	 *	@return	none
	 */
	//===================================================================
	public void create_event_consumer() throws DevFailed
	{
		event_consumer = EventConsumer.create();
	}
	//===================================================================
	/**
	 *	Get the event consumer singleton object.
	 *
	 *	@return	EventConsumer
	 */
	//===================================================================
	public EventConsumer get_event_consumer()
	{
		return event_consumer;
	}
}
