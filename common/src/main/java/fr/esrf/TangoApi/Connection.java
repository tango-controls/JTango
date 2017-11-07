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
// $Revision: 26328 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoDs.TangoConst;

/**
 * Class Description: This class manage device connection for Tango objects.
 * It is an api between user and IDL Device object.
 * 
 * @author verdier
 * @version $Revision: 26328 $
 */

public class Connection implements ApiDefs {

	private IConnectionDAO iConnection = null;
	
	/**
	 *	Device IDL version number
	 */
	protected int	idl_version = 1;
	/**
	 *	Device IDL object used for TANGO device access
	 */
	protected Device	device = null;
	/**
	 *	Device IDL_2 object used for TANGO device access
	 */
	protected Device_2	device_2 = null;
	/**
	 *	Device IDL_3 object used for TANGO device access
	 */
	protected Device_3	device_3 = null;
	/**
	 *	Device IDL_4 object used for TANGO device access
	 */
	protected Device_4	device_4 = null;
	/**
	 *	Device IDL_5 object used for TANGO device access
	 */
	protected Device_5	device_5 = null;
	/**
	 *	TACO Device object used for TANGO interface.
	 */
	protected TacoTangoDevice	taco_device = null;
	/**
	 *	Device IDL object used for efective connection.
	 */
	private org.omg.CORBA.Object obj = null;
	/**
	 *	Device timeout value in ms.
	 */
	private int			dev_timeout = 0;
	/**
	 *	Device name;
	 */
	protected String	devname = null;
	/**
	 *	Used to know if it is a connection or a Re-connection.
	 */
	private boolean		already_connected = false;
	/**
	 *	Device connected is a database or a device proxy
	 */
	private	boolean		device_is_dbase;
	/**
	 *	The connection class (Database, or device class)
	 */
	protected String	classname = null;
	/**
	 *	IOR String only used if the device import is done from ior.
	 */
	protected String	ior = null;
	/**
	 *	URL like for connection:
	 */
	protected TangoUrl	url;
	/**
	 *	Data source (CACHE_DEV, CACHE or DEV)
	 */
	protected DevSource	dev_src = DevSource.CACHE_DEV;
	/**
	 *	if true -> try to reconnect the device before exception
	 */
	protected boolean transparent_reconnection = true;

	/**
	 *	Previous reconnection failed excpetionm.
	 */
	private DevFailed	prev_failed = null;
	/**
	 *	Previous reconnection failed time.
	 */
	private long		prev_failed_t0 = 0;
	
	/**
	 *	Tango access control must be checked if true
	 */
	protected boolean	check_access = true;
	/**
	 *	Tango access control must be checked if true
	 */
	protected boolean	check_access_done = false;
	/**
	 *	Tango access control for this connection.
	 */
	protected int	access = TangoConst.ACCESS_READ;
	
	// ===================================================================
	// ===================================================================
	public Device get_device() {
		return iConnection.get_device(this);
	}

	// ===================================================================
	/**
	 * Connection constructor. It makes a connection on database server.
	 * 
	 */
	// ===================================================================
	public Connection() throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		// Check Tango Host Properties (host name, port number)
		iConnection.init(this);
	}

	// ===================================================================
	/**
	 * Connection constructor. It makes a connection on database server.
	 * 
	 * @param host host where database is running.
	 * @param port port for database connection.
	 */
	// ===================================================================
	public Connection(String host, String port) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, host, port);
	}

	// ===================================================================
	/**
	 * Connection constructor. It makes a connection on database server.
	 * 
	 * @param host  host where database is running.
	 * @param port  port for database connection.
	 * @param auto_reconnect do not reconnect if false.
	 */
	// ===================================================================
	public Connection(String host, String port, boolean auto_reconnect) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, host, port, auto_reconnect);
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device.
	 * 
	 * @param devname  name of the device to be imported.
	 */
	// ===================================================================
	public Connection(String devname) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, devname);
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device.
	 * 
	 *	@param	info exported info of the device to be imported.
	 */
	// ===================================================================
	public Connection(DbDevImportInfo info) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, info);
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device. And set check_access.
	 * 
	 * @param devname name of the device to be imported.
	 * @param check_access  set check_access value
	 */
	// ===================================================================
	public Connection(String devname, boolean check_access) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, devname, check_access);
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device.
	 * 
	 * @param devname name of the device to be imported.
	 * @param param String parameter to import device.
	 * @param src   Source to import device (ior, dbase...)
	 */
	// ===================================================================
	public Connection(String devname, String param, int src) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, devname, param, src);
	}

	// ===================================================================
	/**
	 * Connection constructor. It imports the device.
	 * 
	 * @param devname name of the device to be imported.
	 * @param host    host where database is running.
	 * @param port    port for database connection.
	 */
	// ===================================================================
	public Connection(String devname, String host, String port) throws DevFailed {
		iConnection = TangoFactory.getSingleton().getConnectionDAO();
		iConnection.init(this, devname, host, port);
	}

	// ===================================================================
	/**
	 *	Returns true if TANGO_HOST and device name of this obejct
	 *		is identical the specified connection.
	 *	@param connection	The specified connection to compare.
	 */
	// ===================================================================
	public boolean equals(Connection connection)
	{
		//GA: add test if not null
		if (connection != null) {
			if (!devname.toLowerCase().equals(connection.devname.toLowerCase()))
				return false;
			if (url.protocol!=connection.url.protocol)
				return false;
			if (!url.host.equals(connection.url.host))
				return false;
			if (url.port!=connection.url.port)
				return false;
		}
		return true;
	}
	// ===================================================================
	// ===================================================================
    public TangoUrl getUrl() {
        return url;
    }
	// ===================================================================
	// ===================================================================
	public synchronized void build_connection() throws DevFailed {
		iConnection.build_connection(this);
	}

	// ===================================================================
	// ===================================================================
	public String get_ior() throws DevFailed {
		return iConnection.get_ior(this);
	}

	// ===================================================================
	/**
	 * Really build the device connection.
	 */
	// ===================================================================
	public void dev_import() throws DevFailed {
		iConnection.dev_import(this);
	}

	// ===================================================================
	// ===================================================================
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean deviceCreated() {
        if (device_5!=null) return true;
        if (device_4!=null) return true;
        if (device_3!=null) return true;
        if (device_2!=null) return true;
        return device!=null;
    }
	// ===================================================================
	/**
	 * Change the timeout value for a device call.
	 * 
	 * @param millis  New value of the timeout in milliseconds.
	 * @throws DevFailed  if orb.create_policy throws an
     *              org.omg.CORBA.PolicyError.
	 */
	// ===================================================================
	public void set_timeout_millis(int millis) throws DevFailed {
		iConnection.set_timeout_millis(this, millis);
	}

	// ===================================================================
	/**
	 * return the timeout value for a device call.
	 * 
	 * @return the value of the timeout in milliseconds.
	 * @deprecated use get_timeout_millis() instead
	 */
	// ===================================================================
	public int get_timeout() {
		try {
			return iConnection.get_timeout_millis(this);
		}
		catch(DevFailed e)
		{
			return 3000;
		}
	}

	// ===================================================================
	/**
	 * return the timeout value for a device call.
	 * 
	 * @return the value of the timeout in milliseconds.
	 */
	// ===================================================================
	public int get_timeout_millis() throws DevFailed {
		return iConnection.get_timeout_millis(this);
	}

	// ===========================================================
	/**
	 * Build reason and origin of the exception And throw it into a DevFailed
	 * exception
	 */
	// ===========================================================
	public void throw_dev_failed(Exception e, String command, boolean from_inout_cmd) throws DevFailed {
		iConnection.throw_dev_failed(this, e, command, from_inout_cmd);
	}

	// ===========================================================
	/**
	 * Send a command to a device server.
	 * 
	 * @param command  Command name to send to the device.
	 * @param argin input command argument.
	 * @return the output argument of the command.
	 * @throws DevFailed
	 */
	// ===========================================================
	public DeviceData command_inout(String command, DeviceData argin) throws DevFailed {
		return iConnection.command_inout(this, command, argin);
	}

	// ===========================================================
	/**
	 * Send a command to a device server.
	 * 
	 * @param command Command name.
	 * @return the output argument of the command.
	 * @throws DevFailed
	 */
	// ===========================================================
	public DeviceData command_inout(String command) throws DevFailed {
		return iConnection.command_inout(this, command);
	}

	// ===========================================================
	/**
	 * Execute a ping command to a device server.
	 * 
	 * @return the elapsed time for ping command in microseconds.
	 */
	// ===========================================================
	public long ping() throws DevFailed {
		return iConnection.ping(this);
	}

	// ===========================================================
	/**
	 * Execute a info command to a device server.
	 */
	// ===========================================================
	public String[] black_box(int length) throws DevFailed {
		return iConnection.black_box(this, length);
	}

	// ===========================================================
	/**
	 * Execute a info command to a device server.
	 */
	// ===========================================================
	public DevInfo_3 info_3() throws DevFailed {
		return iConnection.info_3(this);
	}

	// ===========================================================
	/**
	 * Execute a info command to a device server.
	 */
	// ===========================================================
	public DevInfo info() throws DevFailed {
		return iConnection.info(this);
	}

	// ===========================================================
	/**
	 * Execute a command_list_query command to a device server.
	 */
	// ===========================================================
	public CommandInfo[] command_list_query() throws DevFailed {
		return iConnection.command_list_query(this);
	}

	// ==========================================================================
	/**
	 * Returns the IDL version supported
	 * 
	 * @return the IDL version supported .
	 */
	// ==========================================================================
	public int get_idl_version() throws DevFailed {
		return iConnection.get_idl_version(this);
	}

	// ==========================================================================
	/**
	 * returns the device data source
	 * 
	 * @return data source (CACHE_DEV, CACHE or DEV).
	 */
	// ==========================================================================
	public DevSource get_source() throws DevFailed {
		return iConnection.get_source(this);
	}

	// ==========================================================================
	/**
	 * Set the device data source
	 * 
	 * @param new_src  new data source (CACHE_DEV, CACHE or DEV).
	 */
	// ==========================================================================
	public void set_source(DevSource new_src) throws DevFailed {
		iConnection.set_source(this, new_src);
	}

	// ==========================================================================
	/**
	 * return the device connected name.
	 */
	// ==========================================================================
	public String get_name() {
		return iConnection.get_name(this);
	}
	// ==========================================================================
	/**
	 * return the device connected host name.
	 */
	// ==========================================================================
	public String get_host_name() throws DevFailed {
		return iConnection.get_host_name(this);
	}

	// ==========================================================================
	/**
	 * return the device connected class name.
	 */
	// ==========================================================================
	public String get_class_name() throws DevFailed {
		return iConnection.get_class_name(this);
	}

	// ==========================================================================
	/**
	 * return the device connected server name.
	 */
	// ==========================================================================
	public String get_server_name() throws DevFailed {
		return iConnection.get_server_name(this);
	}

	// ==========================================================================
	/**
	 * return the device connected dexcription.
	 */
	// ==========================================================================
	public String description() throws DevFailed {
		return iConnection.description(this);
	}

	// ==========================================================================
	/**
	 * return the administartion device name.
	 */
	// ==========================================================================
	public String adm_name() throws DevFailed {
		return iConnection.adm_name(this);
	}

	// ==========================================================================
	/**
	 * return the name of connection (host:port)
	 */
	// ==========================================================================
	public String get_tango_host() throws DevFailed {
		return iConnection.get_tango_host(this);
	}
	// ==========================================================================
	/**
	 * Returns the name of connection (host:port),
	 * @return the name of connection (host:port),
     * 	host name will be returned with its full qualified domain name
	 */
	// ==========================================================================
	public String getFullTangoHost() throws DevFailed {
        //  Get the tango_host
        String  tangoHost = get_tango_host();
        String  host = tangoHost.substring(0, tangoHost.indexOf(':'));
        String  port = tangoHost.substring(tangoHost.indexOf(':'));
        host = TangoUrl.getCanonicalName(host);
        return host+port;
	}

	// ==========================================================================
	/**
	 * return true if device is a taco device
	 */
	// ==========================================================================
	public boolean is_taco() {
		return iConnection.is_taco(this);
	}

	// ==========================================================================
	/**
	 * if not a TACO command then throw a DevFailed Exception.
	 * 
	 * @param cmdname command name to be put inside reason and origin fields.
	 */
	// ==========================================================================
	public void checkIfTaco(String cmdname) throws DevFailed {
		iConnection.checkIfTaco(this, cmdname);
	}

	// ==========================================================================
	/**
	 * if not a TACO command then throw a DevFailed Exception.
	 * 
	 * @param cmdname  command name to be put inside reason and origin fields.
	 */
	// ==========================================================================
	public void checkIfTango(String cmdname) throws DevFailed {
		iConnection.checkIfTango(this, cmdname);
	}

	// ==========================================================================
	/**
	 * return the value of transparent_reconnection
	 */
	// ==========================================================================
	public boolean get_transparency_reconnection() {
		return iConnection.get_transparency_reconnection(this);
	}

	// ==========================================================================
	/**
	 * set the value of transparent_reconnection
	 */
	// ==========================================================================
	public void set_transparency_reconnection(boolean val) {
		iConnection.set_transparency_reconnection(this, val);
	}

	// ==========================================================================
	// ==========================================================================
	public int getAccessControl() {
		return iConnection.getAccessControl(this);
	}

	// ==========================================================================
	// ==========================================================================
	public void setAccessControl(int access) {
		iConnection.setAccessControl(this, access);
	}

	// ==========================================================================
	// ==========================================================================
	public boolean isAllowedCommand(String cmd) throws DevFailed {
		return iConnection.isAllowedCommand(this, cmd);
	}
    // ==========================================================================
    /**
     *
     * @return the device_5 object if connected, null otherwise.
     */
    // ==========================================================================
    public Device_5 getDevice_5() {
        return device_5;
    }

	public IConnectionDAO getIConnection() {
		return iConnection;
	}

	public void setIConnection(IConnectionDAO connection) {
		iConnection = connection;
	}

	/*
	 * Getter AND Setter generated
	 */
	public boolean isAlready_connected() {
		return already_connected;
	}

	public void setAlready_connected(boolean already_connected) {
		this.already_connected = already_connected;
	}

	public int getDev_timeout() {
		return dev_timeout;
	}

	public void setDev_timeout(int dev_timeout) {
		this.dev_timeout = dev_timeout;
	}

	public boolean isDevice_is_dbase() {
		return device_is_dbase;
	}

	public void setDevice_is_dbase(boolean device_is_dbase) {
		this.device_is_dbase = device_is_dbase;
	}

	public org.omg.CORBA.Object getObj() {
		return obj;
	}

	public void setObj(org.omg.CORBA.Object obj) {
		this.obj = obj;
	}

	/**
	 * Return true if prev_failed is not equal to true
	 * @return true if previous call has failed
	 */
	public boolean isPrev_failed() {
		return (prev_failed!=null);
	}
	
	
	public DevFailed getPrev_failed() {
		return prev_failed;
	}

	public void setPrev_failed(DevFailed prev_failed) {
		this.prev_failed = prev_failed;
	}

	public long getPrev_failed_t0() {
		return prev_failed_t0;
	}

	public void setPrev_failed_t0(long prev_failed_t0) {
		this.prev_failed_t0 = prev_failed_t0;
	}
}

