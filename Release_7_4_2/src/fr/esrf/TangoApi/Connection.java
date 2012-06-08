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
// Revision 1.16  2009/10/08 11:38:52  pascal_verdier
// Test if connection is null added by Gwenaelle Abeille in equals() method.
//
// Revision 1.15  2009/04/27 14:36:26  pascal_verdier
// equals() method added.
//
// Revision 1.14  2009/03/25 13:27:56  pascal_verdier
// ...
//
// Revision 1.13  2008/11/12 10:14:55  pascal_verdier
// Access control checked.
//
// Revision 1.12  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.11  2008/10/10 08:34:34  pascal_verdier
// Security test have been done.
//
// Revision 1.10  2008/09/19 08:39:13  pascal_verdier
// get(host/server/class)_name() methods added
//
// Revision 1.9  2008/09/12 11:20:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.8  2008/01/10 15:40:23  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.7  2007/09/14 07:32:30  ounsy
// change protected modifier in constructor to public. It need by Web TangORB.
//
// Revision 1.6  2007/08/23 11:44:49  ounsy
// correct a bug with missing TangoUrl field in connection :
// used by ApiUtil : ApiUtil.get_db_obj().url.host
//
// Revision 1.5  2007/08/23 09:42:21  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
//
// Revision 3.29  2007/03/16 07:07:33  pascal_verdier
// remove trace
//
// Revision 3.28  2007/03/16 06:59:59  pascal_verdier
// Bug between AccessControl and -nodb option fixed.
//
// Revision 3.27  2006/11/27 16:11:12  pascal_verdier
// Bug in reconnection fixed for org.omg.CORBA.OBJECT_NOT_EXIST exception.
// Bug in reconnection fixed for write_attribute() and status() call.
//
// Revision 3.26  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.25  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.24  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.23  2005/11/29 05:29:55  pascal_verdier
// Default transparency reconnection is now true.
//
// Revision 3.22  2005/10/20 12:09:23  pascal_verdier
// Bug in error management in command_inout() method fixed.
//
// Revision 3.21  2005/10/18 15:30:36  jlpons
// New connection machanism needed by Jacorb 2.2.2
// Improved timeout management
//
// Revision 3.20  2005/10/10 14:09:57  pascal_verdier
// set_source and get_source methods added for Taco device mapping.
// Compatibility with Taco-1.5.jar.
//
// Revision 3.19  2005/08/30 07:33:44  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.18  2005/08/11 07:17:15  pascal_verdier
// Bug fixed on Database reconnection.
//
// Revision 3.17  2005/08/10 08:09:05  pascal_verdier
// If connection failed, do not retry if less than one second.
//
// Revision 3.16  2005/04/26 13:29:03  pascal_verdier
// On DB_DeviceNotDefined exception, add database name on description.
//
// Revision 3.15  2005/03/03 09:10:10  pascal_verdier
// Method  public DevInfo_3  info_3() added.
// info() return DevInfo.
//
// Revision 3.14  2005/03/01 08:20:54  pascal_verdier
// dev_info() modied for Device_3Impl.
//
// Revision 3.13  2005/01/14 10:32:33  pascal_verdier
// jacorb.connection.client.connect_timeout property added and set to 5000.
//
// Revision 3.12  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.11  2004/12/02 13:59:44  pascal_verdier
// Throws a DevFailed exception if TANGO_HOST host name is not valid.
//
// Revision 3.10  2004/11/30 13:05:37  pascal_verdier
// Add no database syntax on adm_name.
//
// Revision 3.9  2004/11/05 12:05:34  pascal_verdier
// Use now JacORB_2_2_1.
//
// Revision 3.8  2004/09/17 07:57:02  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.7  2004/08/17 08:37:45  pascal_verdier
// minor change.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:22  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.4  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.3  2003/09/08 11:02:34  pascal_verdier
// *** empty log message ***
//
// Revision 3.2  2003/07/22 14:15:35  pascal_verdier
// DeviceData are now in-methods objects.
// Minor change for TACO-TANGO common database.
//
// Revision 3.1  2003/05/19 13:35:14  pascal_verdier
// Bug on transparency reconnection fixed.
// input argument modified for add_logging_target method.
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

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;
import fr.esrf.TangoDs.TangoConst;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 * 
 * @author verdier
 * @version $Revision$
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
		// ------------------------------------------------------------
		iConnection.init(this);
	}

	// ===================================================================
	/**
	 * Connection constructor. It makes a connection on database server.
	 * 
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
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
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
	 * @param auto_reconnect
	 *            do not reconnect if false.
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
	 * @param devname
	 *            name of the device to be imported.
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
	 * @param devname
	 *            name of the device to be imported.
	 * @param check_access
	 *            set check_access value
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
	 * @param devname
	 *            name of the device to be imported.
	 * @param param
	 *            String parameter to import device.
	 * @param src
	 *            Source to import device (ior, dbase...)
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
	 * @param devname
	 *            name of the device to be imported.
	 * @param host
	 *            host where database is running.
	 * @param port
	 *            port for database connection.
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
			if (devname.toLowerCase().equals(connection.devname.toLowerCase())==false)
				return false;
			if (url.protocol!=connection.url.protocol)
				return false;
			if (url.host.equals(connection.url.host)==false)
				return false;
			if (url.port!=connection.url.port)
				return false;
		}
		return true;
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
	/**
	 * Change the timeout value for a device call.
	 * 
	 * @param millis
	 *            New value of the timeout in milliseconds.
	 * @throws DevFailed
	 *             if orb.create_policy throws an org.omg.CORBA.PolicyError
	 *             exception.
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
		try
		{
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
	 * @param command
	 *            Command name to send to the device.
	 * @param argin
	 *            input command argument.
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
	 * @param command
	 *            Command name.
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
	 * @param new_src
	 *            new data source (CACHE_DEV, CACHE or DEV).
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
	 * @param cmdname
	 *            command name to be put inside reason and origin fields.
	 */
	// ==========================================================================
	public void checkIfTaco(String cmdname) throws DevFailed {
		iConnection.checkIfTaco(this, cmdname);
	}

	// ==========================================================================
	/**
	 * if not a TACO command then throw a DevFailed Exception.
	 * 
	 * @param cmdname
	 *            command name to be put inside reason and origin fields.
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

