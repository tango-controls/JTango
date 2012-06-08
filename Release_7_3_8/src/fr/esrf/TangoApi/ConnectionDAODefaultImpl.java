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
// Revision 1.21  2010/10/20 06:10:17  pascal_verdier
// RE-indent methods !
//
// Revision 1.20  2010/09/10 09:38:11  abeilleg
// remove printstacktrace
//
// Revision 1.19  2010/08/11 10:08:12  abeilleg
// throw dev failed: reset all devices to null
//
// Revision 1.18  2010/08/06 13:54:45  abeilleg
// memory leak fix for jacorb objects(ReplyReceiver) when server is in timeout.
//
// Revision 1.17  2010/08/05 13:08:37  abeilleg
// bug fix: build_connection was always called for devices that are IDL2,3,4.
//
// Revision 1.16  2010/07/27 15:24:02  abeilleg
// remove debug traces
//
// Revision 1.15  2010/07/27 14:40:26  abeilleg
// replace check on string org.omg.CORBA.CORBA by org.omg.CORBA
//
// Revision 1.14  2010/07/27 13:38:36  abeilleg
// allow reconnection for ping command
//
// Revision 1.13  2009/09/11 12:10:30  pascal_verdier
// tangorc environment file is managed.
//
// Revision 1.12  2009/04/27 14:37:12  pascal_verdier
// Pb on Access control for astor fixed.
//
// Revision 1.11  2009/04/08 08:35:39  pascal_verdier
// remove trace
//
// Revision 1.10  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.9  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.8  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/10/10 08:31:57  pascal_verdier
// Security check has been done.
//
// Revision 1.6  2008/09/19 08:38:02  pascal_verdier
// get(host/server/class)_name() methods added.
//
// Revision 1.5  2008/09/12 11:32:16  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.4  2008/04/16 13:00:36  pascal_verdier
// Event subscribtion stateless added.
//
// Revision 1.3  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.2  2007/09/26 06:16:45  pascal_verdier
// Spelling correction in exception description.
//
// Revision 1.1  2007/08/23 09:41:20  ounsy
// Add default impl for tangorb
//
// Revision 3.30  2007/04/27 09:05:17  pascal_verdier
// Taco device multinathost supported.
//
// $Log$
// Revision 1.21  2010/10/20 06:10:17  pascal_verdier
// RE-indent methods !
//
// Revision 1.20  2010/09/10 09:38:11  abeilleg
// remove printstacktrace
//
// Revision 1.19  2010/08/11 10:08:12  abeilleg
// throw dev failed: reset all devices to null
//
// Revision 1.18  2010/08/06 13:54:45  abeilleg
// memory leak fix for jacorb objects(ReplyReceiver) when server is in timeout.
//
// Revision 1.17  2010/08/05 13:08:37  abeilleg
// bug fix: build_connection was always called for devices that are IDL2,3,4.
//
// Revision 1.16  2010/07/27 15:24:02  abeilleg
// remove debug traces
//
// Revision 1.15  2010/07/27 14:40:26  abeilleg
// replace check on string org.omg.CORBA.CORBA by org.omg.CORBA
//
// Revision 1.14  2010/07/27 13:38:36  abeilleg
// allow reconnection for ping command
//
// Revision 1.13  2009/09/11 12:10:30  pascal_verdier
// tangorc environment file is managed.
//
// Revision 1.12  2009/04/27 14:37:12  pascal_verdier
// Pb on Access control for astor fixed.
//
// Revision 1.11  2009/04/08 08:35:39  pascal_verdier
// remove trace
//
// Revision 1.10  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.9  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.8  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/10/10 08:31:57  pascal_verdier
// Security check has been done.
//
// Revision 1.6  2008/09/19 08:38:02  pascal_verdier
// get(host/server/class)_name() methods added.
//
// Revision 1.5  2008/09/12 11:32:16  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.4  2008/04/16 13:00:36  pascal_verdier
// Event subscribtion stateless added.
//
// Revision 1.3  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.2  2007/09/26 06:16:45  pascal_verdier
// Spelling correction in exception description.
//
// Revision 1.1  2007/08/23 09:41:20  ounsy
// Add default impl for tangorb
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

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CORBA.SystemException;

import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevCmdInfo_2;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevInfo;
import fr.esrf.Tango.DevInfo_3;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.Device;
import fr.esrf.Tango.DeviceHelper;
import fr.esrf.Tango.Device_2Helper;
import fr.esrf.Tango.Device_3Helper;
import fr.esrf.Tango.Device_4Helper;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 * 
 * @author verdier
 * @version $Revision$
 */

public class ConnectionDAODefaultImpl implements ApiDefs, IConnectionDAO {

    // ===================================================================
    // ===================================================================
    public Device get_device(final Connection connection) {
		return connection.device;
    }

    // ===================================================================
    // ===================================================================
    private String buildUrlName(final String host, final String port) {
		return "tango://" + host + ":" + port;
    }

    // ===================================================================
    // ===================================================================
    private String buildUrlName(final String host, final String port, final String devname) {
		return "tango://" + host + ":" + port + "/" + devname;
    }

    public ConnectionDAODefaultImpl() {
    }

    // ===================================================================
    /**
     * Connection constructor. It makes a connection on database server.
     * 
     */
    // ===================================================================
    public void init(final Connection connection) throws DevFailed {
		// Check Tango Host Properties (host name, port number)
		// ------------------------------------------------------------
		connection.url = new TangoUrl();
		connection.setDevice_is_dbase(true);
		connection.transparent_reconnection = true; // Always true for Database
		// url.trace();

		ApiUtil.get_orb();
		connect_to_dbase(connection);
		connection.devname = connection.device.name();
		connection.setAlready_connected(true);
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
    public void init(final Connection connection, final String host, final String port)
	    	throws DevFailed {
		connection.url = new TangoUrl(buildUrlName(host, port));
		connection.setDevice_is_dbase(true);
		connection.transparent_reconnection = true; // Always true for Database

		ApiUtil.get_orb();
		connect_to_dbase(connection);
		connection.devname = connection.device.name();
		connection.setAlready_connected(true);
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
    public void init(final Connection connection, final String host, final String port,
	    	final boolean auto_reconnect) throws DevFailed {
		connection.url = new TangoUrl(buildUrlName(host, port));
		connection.setDevice_is_dbase(true);
		connection.transparent_reconnection = auto_reconnect;

		ApiUtil.get_orb();
		connect_to_dbase(connection);
		connection.devname = connection.device.name();
		connection.setAlready_connected(true);
    }

    // ===================================================================
    /**
     * Connection constructor. It imports the device.
     * 
     * @param devname
     *            name of the device to be imported.
     */
    // ===================================================================
    public void init(final Connection connection, final String devname) throws DevFailed {
		 connection.url = new TangoUrl(devname);
		 connection.setDevice_is_dbase(false);
		 connection.devname = connection.url.devname;

		 // Check if connection is possible
		 if (connection.url.protocol == TANGO && connection.url.use_db) {
	    	 connection.ior = get_exported_ior(connection);
		 }
    }

    // ===================================================================
    /**
     * Connection constructor. It imports the device.
     * 
     * @param info
     *            exported info of the device to be imported.
     */
    // ===================================================================
    public void init(final Connection connection, final DbDevImportInfo info) throws DevFailed {
		connection.url = new TangoUrl(info.name);
		connection.setDevice_is_dbase(false);
		connection.devname = connection.url.devname;

		// Check if connection is possible
		if (connection.url.protocol == TANGO && connection.url.use_db) {
	    	connection.ior = info.ior;
		}
		connection.classname = info.classname;
		initCtrlAccess(connection);
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
    public void init(final Connection connection, final String devname, final boolean check_access)
	    	throws DevFailed {
		connection.url = new TangoUrl(devname);
		connection.setDevice_is_dbase(false);
		connection.devname = connection.url.devname;
		connection.check_access = check_access;

		// Check if connection is possible
		if (connection.url.protocol == TANGO && connection.url.use_db) {
	    	connection.ior = get_exported_ior(connection);
		}
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
    public void init(final Connection connection, final String devname, final String param,
	    	final int src) throws DevFailed {
		connection.devname = devname;
		connection.setDevice_is_dbase(false);
		if (src == FROM_IOR) {
	    	connection.ior = param;
	    	connection.url = new TangoUrl();
	    	connection.url.protocol = TANGO;
		} else {
	    	Except.throw_wrong_syntax_exception("TangoApi_INVALID_ARGS", "Invalid argument",
		    	"Connection.Connemction()");
		}
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
    public void init(final Connection connection, final String devname, final String host,
	    	final String port) throws DevFailed {
		connection.url = new TangoUrl(buildUrlName(host, port, devname));
		connection.devname = connection.url.devname;
		connection.setDevice_is_dbase(false);

		// Check if connection is possible
		if (connection.url.protocol == TANGO) {
	    	connection.ior = get_exported_ior(connection);
		}
   	}

    // ===================================================================
    // ===================================================================
    public synchronized void build_connection(final Connection connection) throws DevFailed {
		if (connection.device == null && connection.device_2 == null && connection.device_3 == null
			&& connection.device_4 == null) {
	    	if (connection.devname != null) {

			final long t = System.currentTimeMillis();
			final long delay = t - connection.getPrev_failed_t0();
			boolean try_reconnection = true;

			// Check if connection on database or on a device
			if (connection.isDevice_is_dbase()) {
		    	connect_to_dbase(connection);
			} else {
		    	try {
				// url.trace();
				// Check if Tango or taco device
				if (connection.url.protocol == TANGO) {
			    	// Do not reconnect if to soon
			    	if (connection.isPrev_failed()
				    	&& delay < ApiUtil.getReconnectionDelay()) {
					try_reconnection = false;
					throw connection.getPrev_failed();
			    	}
			    	// System.out.println(" Try reconnection on " +
			    	// connection.devname + ":	" +
			    	// delay
			    	// + " ms");
			    	connection.setPrev_failed_t0(t);

			    	// Check if connection with databse or without
			    	if (connection.url.use_db) {
					dev_import(connection);
			    	} else {
					dev_import_without_dbase(connection);
			    	}
			    	connection.setPrev_failed(null);
				} else if (connection.url.protocol == TACO) {
			    	if (connection.taco_device == null) {
					connection.taco_device = new TacoTangoDevice(connection.devname,
						connection.url.host);
			    	}
				}
		    	} catch (final DevFailed e) {
				if (try_reconnection) {
			    	connection.setPrev_failed_t0(t);
			    	connection.setPrev_failed(e);
				}
				connection.ior = null;
				Except.throw_communication_failed(e, "TangoApi_CANNOT_IMPORT_DEVICE",
					"Cannot import " + connection.devname,
					"Connection.build_connection(" + connection.devname + ")");
		    	}
			}
	    	}
		}
    }

    // ===================================================================
    /**
     * Ckeck the IDL revision and create the associated device.
     * 
     * @param corba_str
     *            IOR or corbaloc string.
     * @param connection the connection object
     * @throws fr.esrf.Tango.DevFailed if connection failed
     */
    // ===================================================================
    private void createDevice(final Connection connection, final String corba_str) throws DevFailed {
		try {
	    	// Build corba object

	    	final ORB orb = ApiUtil.get_orb();
	    	connection.setObj(orb.string_to_object(corba_str));

		} catch (final RuntimeException e) {
	    	Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_VALID", e.toString().substring(
		    	e.toString().indexOf(":") + 2), "Connection.createDevice()");
		}
		// Get default timeout from env or fix it if not set
		String strTimeout;
		if ((strTimeout = ApiUtil.getStrDefaultTimeout()) == null) {
	    	strTimeout = "3000";
		}
		try {
	    	connection.setDev_timeout(Integer.parseInt(strTimeout));
		} catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	e.printStackTrace();
		}
		set_obj_timeout(connection, connection.getDev_timeout());

		// Construct the Object
		if (connection.getObj()._is_a("IDL:Tango/Device_4:1.0")) {
	    	// System.out.println("Device is a Tango/Device_4:1.0 !!!!!!!!!");
	    	connection.device_4 = Device_4Helper.narrow(connection.getObj());
	    	connection.device_3 = connection.device_4;
	    	connection.device_2 = connection.device_4;
	    	connection.device = connection.device_4;
	    	connection.idl_version = 4;
		} else if (connection.getObj()._is_a("IDL:Tango/Device_3:1.0")) {
	    	// System.out.println("Device is a Tango/Device_3:1.0 !!!!!!!!!");
	    	connection.device_4 = null;
	    	connection.device_3 = Device_3Helper.narrow(connection.getObj());
	    	connection.device_2 = connection.device_3;
	    	connection.device = connection.device_3;
	    	connection.idl_version = 3;
		} else if (connection.getObj()._is_a("IDL:Tango/Device_2:1.0")) {
	    	// System.out.println("Device is a Tango/Device_2:1.0 !!!!!!!!!");
	    	connection.device_4 = null;
	    	connection.device_3 = null;
	    	connection.device_2 = Device_2Helper.narrow(connection.getObj());
	    	connection.device = connection.device_2;
	    	connection.idl_version = 2;
		} else if (connection.getObj()._is_a("IDL:Tango/Device:1.0")) {
	    	// System.out.println("Device is a Tango/Device:1.0 !!!!!!!!!");
	    	connection.device_4 = null;
	    	connection.device_3 = null;
	    	connection.device_2 = null;
	    	connection.device = DeviceHelper.narrow(connection.getObj());
	    	connection.idl_version = 1;
		} else {
	    	System.out.println("TangoApi_DEVICE_IDL_UNKNOWN!");
	    	Except.throw_non_supported_exception("TangoApi_DEVICE_IDL_UNKNOWN", connection.devname
		    	+ " has an IDL revision not supported !", "Connection.createDevice("
		    	+ connection.devname + ")");
		}

		// Force a network is_a() here for error
		// management of build_connection()
		// We do not use ping() as it can be serialized
		// at the server level

		connection.getObj()._is_a("Dummy");
    }

    // ===================================================================
    // ===================================================================
    public String get_ior(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
		}

		final DbDevImportInfo info = db.import_device(connection.devname);
		return info.ior;
    }

    // ===================================================================
    // ===================================================================
    public String get_host_name(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
		}
		final DbDevImportInfo info = db.import_device(connection.devname);
		return info.hostname;
    }

    // ===================================================================
    // ===================================================================
    public String get_server_name(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
		}
		final DbDevImportInfo info = db.import_device(connection.devname);
		return info.server;
   	}

    // ===================================================================
    // ===================================================================
    public String get_class_name(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
		}
		if (connection.classname == null) {
	    	final DbDevImportInfo info = db.import_device(connection.devname);
	    	connection.classname = info.classname;
		}
		return connection.classname;
    }

    // ===================================================================
    // ===================================================================
    private void initCtrlAccess(final Connection connection) throws DevFailed {
		if (connection.url.use_db && connection.check_access) {
	    	connection.access = ApiUtil.get_db_obj(connection.url.host, connection.url.strport)
		    	.checkAccessControl(connection.devname);
	    	/*
	    	 * System.out.println(connection.devname + " -> " +
	    	 * ((connection.access==TangoConst.ACCESS_READ)? "Read" : "Write"));
	    	 */
		} else {
	    	connection.access = TangoConst.ACCESS_WRITE;
		}
    }

    // ===================================================================
    // ===================================================================
    private String get_exported_ior(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
		}
		final DbDevImportInfo info = db.import_device(connection.devname);
		String result = null;
		if (info.exported) {
	    	result = info.ior;
		}

		// check if TACO
		if (info.is_taco) {
	    	connection.url.protocol = TACO;
		} else {
	    	// Manage Access control
	    	initCtrlAccess(connection);
		}
		return result;
    }

    // ===================================================================
    /**
     * Really build the device connection.
     */
    // ===================================================================
    public void dev_import(final Connection connection) throws DevFailed {
		Database db;
		String local_ior;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
		}

		// Check if device must be imported directly from IOR
		// ---------------------------------------------------------
		if (connection.ior == null) {
	    	final DbDevImportInfo info = db.import_device(connection.devname);
	    	if (!info.exported) {
			    Except.throw_connection_failed("TangoApi_DEVICE_NOT_EXPORTED", connection.devname
				    + " Not Exported !", "Connection(" + connection.devname + ")");
	    	}
	    	local_ior = info.ior;
		} else {
	    	local_ior = connection.ior;
		}
		try {
	    	// Import the TANGO device
	    	// --------------------------------
	    	createDevice(connection, local_ior);
	    	// device.ping();
	    	connection.setAlready_connected(true);
	    	connection.ior = local_ior;

		} catch (final DevFailed e) {
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.ior = null;
	    	throw e;
		} catch (final Exception e) {
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.ior = null;
	    	final String reason = "TangoApi_CANNOT_IMPORT_DEVICE";
	    	final String s = connection.isAlready_connected() ? "Re-" : "";
	    	final String desc = "Cannot " + s + "import " + connection.devname + " :\n\t"
		    	+ e.toString();
	    	final String origin = "Connection.dev_import(" + connection.devname + ")";
	    	// e.printStackTrace();
	    	Except.throw_connection_failed(reason, desc, origin);
		}
    }

    // ===================================================================
    /**
     * Import the tango database.
     *
     * @param connection the connection object
     * @throws fr.esrf.Tango.DevFailed if connection failed
     */
    // ===================================================================
    private void connect_to_dbase(final Connection connection) throws DevFailed {
		if (connection.device == null) {
	    	try {
			// Prepeare the connection string
			// ----------------------------------
			final String db_corbaloc = "corbaloc:iiop:" + connection.url.host + ":"
				+ connection.url.strport + "/database";
			// And connect to database.
			// ----------------------------
			createDevice(connection, db_corbaloc);
			connection.classname = "Database";
			// System.out.println("Connected to " + db_corbaloc +
			// "\ndevice: " +
			// connection.device.name());
	    	} catch (final SystemException ex) {
			if (connection.transparent_reconnection) {
		    	try {
				final DbRedundancy dbr = DbRedundancy.get_instance();
				final String th2 = dbr.get(connection.url.host + ":" + connection.url.port);

				// Prepeare the connection string
				// ----------------------------------
				final String db_corbaloc = "corbaloc:iiop:" + th2 + "/database";
				// And connect to database.
				// ----------------------------
				createDevice(connection, db_corbaloc);
				// System.out.println("Connected to " + db_corbaloc);
		    	} catch (final SystemException e) {
				// e.printStackTrace();
				connection.device = null;
				connection.ior = null;
				Except.throw_connection_failed("TangoApi_DATABASE_CONNECTION_FAILED",
					"Connection to database failed  !\n" + e, "connect_to_dbase("
						+ connection.url.host + "," + connection.url.strport + ")");
		    	}
			} else {
		    	// e.printStackTrace();
		    	connection.device = null;
		    	connection.ior = null;
		    	Except.throw_connection_failed("TangoApi_DATABASE_CONNECTION_FAILED",
			    	"Connection to database failed  !\n" + ex, "connect_to_dbase("
				    	+ connection.url.host + "," + connection.url.strport + ")");
			}
	    	}
		}
    }

    // ===================================================================
    /**
     * Import the tango database.
     * 
     * @param connection the connection object
     * @throws fr.esrf.Tango.DevFailed if connection failed
     */
    // ===================================================================
    private void dev_import_without_dbase(final Connection connection) throws DevFailed {
		 if (connection.device == null) {
	    	 try {
			 // Prepeare the connection string
			 // ----------------------------------
			 final String db_corbaloc = "corbaloc:iiop:" + connection.url.host + ":"
				 + connection.url.strport + "/" + connection.devname.toLowerCase();
			 // System.out.println("db_corbaloc=" + db_corbaloc);
			 // And connect to device.
			 // ----------------------------
			 createDevice(connection, db_corbaloc);
			 connection.access = TangoConst.ACCESS_WRITE;
	    	 } catch (final SystemException e) {
			 connection.device = null;
			 connection.ior = null;
			 // e.printStackTrace();
			 Except.throw_connection_failed("TangoApi_DEVICE_CONNECTION_FAILED",
				 "Connection to device without database failed  !\n" + e,
				 "Connection.dev_import_without_dbase(" + connection.url.host + ","
					 + connection.url.strport + ")");
	    	 }
		 }
    }

    // ===================================================================
    // ===================================================================
    private void set_obj_timeout(final Connection connection, final int millis) {
		// Change Jacorb policy for timeout
		final org.omg.CORBA.Policy p = new org.jacorb.orb.policies.RelativeRoundtripTimeoutPolicy(
			10000 * millis);
		connection.getObj()._set_policy_override(new Policy[] { p },
			org.omg.CORBA.SetOverrideType.ADD_OVERRIDE);

		// Save new timeout value
		// ---------------------------------
		connection.setDev_timeout(millis);
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
    public void set_timeout_millis(final Connection connection, final int millis) throws DevFailed {

		build_connection(connection);

		// Check if TACO device
		if (connection.url.protocol == TACO) {
	    	connection.taco_device.set_rpc_timeout(millis);
	    	return;
		}

		// Else TANGO device
		set_obj_timeout(connection, millis);
    }

    // ===================================================================
    /**
     * return the timeout value for a device call.
     * 
     * @return the value of the timeout in milliseconds.
     * @deprecated use get_timeout_millis() instead
     */
    // ===================================================================
    @Deprecated
    public int get_timeout(final Connection connection) {
		return connection.getDev_timeout();
    }

    // ===================================================================
    /**
     * return the timeout value for a device call.
     * 
     * @return the value of the timeout in milliseconds.
     */
    // ===================================================================
    public int get_timeout_millis(final Connection connection) throws DevFailed {

		build_connection(connection);

		// Check if TACO device
		if (connection.url.protocol == TACO) {
	    	return connection.taco_device.get_rpc_timeout();
		} else {
	    	// Else TANGO device
	    	return connection.getDev_timeout();
		}
    }

    // ===========================================================
    /**
     * Build reason and origin of the exception And throw it into a DevFailed
     * exception
     */
    // ===========================================================
    public void throw_dev_failed(final Connection connection, final Exception e,
	    	final String command, final boolean from_inout_cmd) throws DevFailed {
		if (e instanceof DevFailed) {
	    	throw (DevFailed) e;
		}

		String desc;
		final String origin = from_inout_cmd ? connection.devname + ".command_inout(" + command
			+ ")" : connection.devname + "." + command + ")";
		// e.printStackTrace();
		if (e.toString().indexOf("org.omg.CORBA.NO_RESPONSE") >= 0
			|| e.toString().indexOf("org.omg.CORBA.TIMEOUT") >= 0
			|| e.toString().indexOf("org.omg.CORBA.IMP_LIMIT") >= 0) {
	    	desc = "Device (" + connection.devname + ") timed out (>" + connection.getDev_timeout()
		    	+ " ms)!";
	    	Except.throw_communication_timeout(e.toString(), desc, origin);
		} else if (e.toString().indexOf("org.omg.CORBA.BAD_INV_ORDER") >= 0) {
	    	desc = "Lost Connection during command : " + command;
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.ior = null;
	    	Except.throw_connection_failed(e.toString(), desc, origin);
		} else if (e.toString().indexOf("org.omg.CORBA.TRANSIENT") >= 0
			|| e.toString().indexOf("org.omg.CORBA.UNKNOWN") >= 0
			|| e.toString().indexOf("org.omg.CORBA.COMM_FAILURE") >= 0
			|| e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST") >= 0) {
	    	desc = "Lost Connection during command : " + command;
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.ior = null;
	    	Except.throw_connection_failed(e.toString(), desc, origin);
		} else if (e.toString().startsWith("java.lang.RuntimeException")) {
	    	desc = "API has catched a RuntimeException" + command;
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.ior = null;
	    	Except.throw_connection_failed(e.toString(), desc, origin);
		} else {
	    	System.out.println("API has catched an exception for " + origin + " : \n" + e);
	    	desc = e.getMessage();
	    	Except.throw_communication_failed(e.toString(), desc, origin);
		}
    }

    // ===========================================================
    // ===========================================================
    protected void throwNotAuthorizedException(String desc, String origin) throws DevFailed
    {
        Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
                desc + "   is not authorized for:\n" +
                        ApiUtilDAODefaultImpl.getUser() + "  on   " +
                        ApiUtil.getHostName() + "  (" + ApiUtil.getHostAddress() + ")",
                origin);
    }
    // ===========================================================
    /**
     * Send a command to a device server.
     * 
     * @param connection the connection object
     * @param command
     *            Command name to send to the device.
     * @param argin
     *            input command argument.
     * @return the output argument of the command.
     * @throws DevFailed
     */
    // ===========================================================
    public DeviceData command_inout(final Connection connection, final String command,
	    	final DeviceData argin) throws DevFailed {
		Any received = null;

		build_connection(connection);

		// Check if taco device
		if (connection.url.protocol == TACO) {
	    	return connection.taco_device.command_inout(command, argin);
		}

		//
		// Manage Access control
		//
		if (connection.access == TangoConst.ACCESS_READ) {
	    	final Database db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
	    	if (!db.isCommandAllowed(connection.get_class_name(), command)) {
			// Check if not allowed or PB with access device
			if (db.access_devfailed != null) {
		    	throw db.access_devfailed;
			}
			// ping the device to throw execption
			// if failed (for reconnection)
			ping(connection);

			System.out.println(connection.devname + "." + command
				+ "  -> TangoApi_READ_ONLY_MODE");
            throwNotAuthorizedException(connection.devname + ".command_inout(" + command + ")",
                    "Connection.command_inout()");
	    	}
		}
		// Else tango call
		boolean done = false;
		// try 2 times for reconnection if requested
		final int retries = connection.transparent_reconnection ? 2 : 1;
		for (int i=0 ; i<retries && !done ; i++) {
	    	try {
			if (connection.device_4 != null) {
		    	received = connection.device_4.command_inout_4(command, argin.extractAny(),
			    	connection.dev_src, DevLockManager.getInstance().getClntIdent());
			} else if (connection.device_2 != null) {
		    	received = connection.device_2.command_inout_2(command, argin.extractAny(),
			    	connection.dev_src);
			} else {
		    	received = connection.device.command_inout(command, argin.extractAny());
			}
			done = true;
	    	} catch (final DevFailed e) {
			final String reason = "TangoApi_CANNOT_EXECUTE_COMMAND";
			final String desc = "Cannot execute command " + command + " on "
				+ connection.devname;
			final String origin = "Connection.command_inout()";

			// Check if (from database) DeviceNotDefined exception
			// then add witch database in the existing DevFailed.
			if (e.errors[0].reason.equals("DB_DeviceNotDefined")) {
		    	String d = e.errors[0].desc;
		    	final int idx = d.lastIndexOf("!");
		    	if (idx > 0) {
				d = d.substring(0, idx);
		    	}
		    	d += "  " + connection.url.host + ":" + connection.url.port + " !";
		    	e.errors[0].desc = d;
			}
			Except.throw_connection_failed(e, reason, desc, origin);
	    	} catch (final Exception e) {
			manageExceptionReconnection(connection, retries, i, e, this.getClass()
				+ ".command_inout");
	    	}
		}
		return new DeviceData(received);
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
    public DeviceData command_inout(final Connection connection, final String command)
	    	throws DevFailed {
		final DeviceData argin = new DeviceData();
		return command_inout(connection, command, argin);
    }

    // ===========================================================
    /**
     * Execute a ping command to a device server.
     * 
     * @return the elapsed time for ping command in microseconds.
     */
    // ===========================================================
    public long ping(final Connection connection) throws DevFailed {

		long result = 0;
		final int maxRetries = connection.transparent_reconnection ? 2 : 1;
		int nbRetries = 0;
		boolean retry = false;
		do {
	    	try {
			result = doPing(connection);
	    	} catch (final DevFailed e) {
			if (nbRetries < maxRetries) {
		    	retry = true;
			} else {
		    	throw e;
			}
			nbRetries++;
	    	}
		} while (retry);
		return result;
    }

	// ===========================================================
	// ===========================================================
	private long doPing(final Connection connection) throws DevFailed {
		checkIfTango(connection, "ping");

		build_connection(connection);

		final long t0 = System.currentTimeMillis();
		try {
	    	connection.device.ping();
		} catch (final DevFailed e) {
	    	final String reason = "TangoApi_CANNOT_PING_DEVICE";
	    	final String desc = "Cannot ping " + connection.devname;
	    	final String origin = "Connection.ping()";
	    	Except.throw_connection_failed(e, reason, desc, origin);
		} catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	throw_dev_failed(connection, e, "ping", false);
		}

		final long t1 = System.currentTimeMillis();
		return (int) (t1 - t0) * 1000; // Set as micro seconds
    }

    // ===========================================================
    /**
     * Execute a info command to a device server.
     */
    // ===========================================================
    public String[] black_box(final Connection connection, final int length) throws DevFailed {
		checkIfTango(connection, "black_box");

		build_connection(connection);

		String[] result = new String[0];
		try {
	    	result = connection.device.black_box(length);
		} catch (final DevFailed e) {
	    	final String reason = "TangoApi_CANNOT_READ_BLACK BOX";
	    	final String desc = "Cannot read black box on " + connection.devname;
	    	final String origin = "Connection.black_box()";
	    	Except.throw_connection_failed(e, reason, desc, origin);
		} catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	throw_dev_failed(connection, e, "black_box", false);
		}
		return result;
    }

    // ===========================================================
    /**
     * Execute a info command to a device server.
     */
    // ===========================================================
    public DevInfo_3 info_3(final Connection connection) throws DevFailed {
		checkIfTango(connection, "info");

		build_connection(connection);

		try {
	    	DevInfo_3 info_3;
	    	if (connection.device_3 != null) {
			info_3 = connection.device_3.info_3();
	    	} else {
			final DevInfo info = connection.device.info();
			info_3 = new DevInfo_3(info.dev_class, info.server_id, info.server_host,
				info.server_version, info.doc_url, "");
	    	}
	    	return info_3;
		} catch (final DevFailed e) {
	    	final String reason = "TangoApi_CANNOT_READ _DEVICE_INFO";
	    	final String desc = "Cannot read device info on " + connection.devname;
	    	final String origin = "Connection.info()";
	    	Except.throw_connection_failed(e, reason, desc, origin);
		} catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	throw_dev_failed(connection, e, "info", false);
		}
		return null;
    }

    // ===========================================================
    /**
     * Execute a info command to a device server.
     */
    // ===========================================================
    public DevInfo info(final Connection connection) throws DevFailed {
	checkIfTango(connection, "info");

		build_connection(connection);

		DevInfo info = null;
		try {
	    	info = connection.device.info();
		} catch (final DevFailed e) {
	    	final String reason = "TangoApi_CANNOT_READ _DEVICE_INFO";
	    	final String desc = "Cannot read device info on " + connection.devname;
	    	final String origin = "Connection.info()";
	    	Except.throw_connection_failed(e, reason, desc, origin);
		} catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	throw_dev_failed(connection, e, "info", false);
		}
		return info;
    }

    // ===========================================================
    /**
     * Execute a command_list_query command to a device server.
     */
    // ===========================================================
    public CommandInfo[] command_list_query(final Connection connection) throws DevFailed {

		build_connection(connection);

		try {
	    	CommandInfo[] result;
	    	if (connection.url.protocol == TACO) {
			result = connection.taco_device.commandListQuery();
	    	} else {
			// Check IDL revision
			if (connection.device_2 != null) {
		    	final DevCmdInfo_2[] tmp = connection.device_2.command_list_query_2();
		    	result = new CommandInfo[tmp.length];
		    	for (int i = 0; i < tmp.length; i++) {
				result[i] = new CommandInfo(tmp[i]);
		    	}
			} else {
		    	final DevCmdInfo[] tmp = connection.device.command_list_query();
		    	result = new CommandInfo[tmp.length];
		    	for (int i = 0; i < tmp.length; i++) {
				result[i] = new CommandInfo(tmp[i]);
		    	}
			}
	    	}
	    	return result;
		} catch (final DevFailed e) {
	    	final String reason = "TangoApi_CANNOT__READ_CMD_LIST";
	    	final String desc = "Cannot read command list for " + connection.devname;
	    	final String origin = "Connection.command_list_query()";
	    	Except.throw_connection_failed(e, reason, desc, origin);
		} catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	throw_dev_failed(connection, e, "command_list_query", false);
		}
		return null;
    }

    // ==========================================================================
    /**
     * Returns the IDL version supported
     * 
     * @return the IDL version supported .
     */
    // ==========================================================================
    public int get_idl_version(final Connection connection) throws DevFailed {

		build_connection(connection);

		return connection.idl_version;
    }

    // ==========================================================================
    /**
     * returns the device data source
     * 
     * @return data source (CACHE_DEV, CACHE or DEV).
     */
    // ==========================================================================
    public DevSource get_source(final Connection connection) throws DevFailed {
		if (is_taco(connection)) {
	    	if (connection.taco_device == null) {
			connection.taco_device = new TacoTangoDevice(connection.devname,
				connection.url.host);
	    	}
	    	return connection.taco_device.get_source();
		} else {
	    	return connection.dev_src;
		}
    }

    // ==========================================================================
    /**
     * Set the device data source
     * 
     * @param new_src
     *            new data source (CACHE_DEV, CACHE or DEV).
     */
    // ==========================================================================
    public void set_source(final Connection connection, final DevSource new_src) throws DevFailed {
		if (is_taco(connection)) {
	    	if (connection.taco_device == null) {
			connection.taco_device = new TacoTangoDevice(connection.devname,
				connection.url.host);
	    	}
	    	connection.taco_device.set_source(new_src);
		} else {
	    	connection.dev_src = new_src;
		}
    }

    // ==========================================================================
    /**
     * return the device connected name.
     */
    // ==========================================================================
    public String get_name(final Connection connection) {
		return connection.devname;
    }

    // ==========================================================================
    /**
     * return the device connected dexcription.
     */
    // ==========================================================================
    public String description(final Connection connection) throws DevFailed {

	build_connection(connection);

	return connection.device.description();
    }

    // ==========================================================================
    /**
     * return the administartion device name.
     */
    // ==========================================================================
    public String adm_name(final Connection connection) throws DevFailed {
	checkIfTango(connection, "adm_name");

	build_connection(connection);

	String name = null;
	try {
	    name = connection.device.adm_name();
	} catch (final Exception e) {
	    ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    throw_dev_failed(connection, e, "adm_name", false);
	}

		// If no database, add syntax to be used in DeviceProxy constructor.
		if (!connection.url.use_db) {
	  	  name = "tango://" + connection.url.host + ":" + connection.url.port + "/" + name
		 	   + "#dbase=no";
		}
		return name;
    }

    // ==========================================================================
    /**
     * return the name of connection (host:port)
     */
    // ==========================================================================
    public String get_tango_host(final Connection connection) throws DevFailed {
	    checkIfTango(connection, "get_tango_host");
		    return connection.url.host + ":" + connection.url.strport;
    }

    // ==========================================================================
    /**
     * return true if device is a taco device
     */
    // ==========================================================================
    public boolean is_taco(final Connection connection) {
		return connection.url.protocol == TACO;
    }

    // ==========================================================================
    /**
     * if not a TACO command then throw a DevFailed Exception.
     * 
     * @param cmdname
     *            command name to be put inside reason and origin fields.
     */
    // ==========================================================================
    public void checkIfTaco(final Connection connection, final String cmdname) throws DevFailed {
	if (!is_taco(connection)) {
	    Except.throw_non_supported_exception("TangoApi_NOT_TANGO_CMD", cmdname
		    + " is NOT a TANGO command.", cmdname + "()");
		}
    }

    // ==========================================================================
    /**
     * if not a TACO command then throw a DevFailed Exception.
     * 
     * @param cmdname
     *            command name to be put inside reason and origin fields.
     */
    // ==========================================================================
    public void checkIfTango(final Connection connection, final String cmdname) throws DevFailed {
	if (is_taco(connection)) {
	    Except.throw_non_supported_exception("TangoApi_NOT_TACO_CMD", cmdname
		    + " is NOT a TACO command.", cmdname + "()");
		}
    }

    // ==========================================================================
    /**
     * return the value of transparent_reconnection
     */
    // ==========================================================================
    public boolean get_transparency_reconnection(final Connection connection) {
	return connection.transparent_reconnection;
    }

    // ==========================================================================
    /**
     * set the value of transparent_reconnection
     */
    // ==========================================================================
    public void set_transparency_reconnection(final Connection connection, final boolean val) {
	connection.transparent_reconnection = val;
    }

    // ==========================================================================
    // ==========================================================================
    public int getAccessControl(final Connection connection) {
	return connection.access;
    }

    // ==========================================================================
    // ==========================================================================
    public void setAccessControl(final Connection connection, final int access) {
	connection.access = access;
    }

    // ==========================================================================
    // ==========================================================================
    public boolean isAllowedCommand(final Connection connection, final String cmd) throws DevFailed {
	final Database db = ApiUtil.get_db_obj(connection.url.host, connection.url.strport);
	return db.isCommandAllowed(connection.get_class_name(), cmd);
    }

    protected void manageExceptionReconnection(final Connection deviceProxy, final int retries,
	    final int i, final Exception e, final String origin) throws DevFailed {
		ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(deviceProxy);
		if (i == 0
			&& (e.toString().indexOf("org.omg.CORBA.TRANSIENT") >= 0
				|| e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST") >= 0 || e
				.toString().indexOf("org.omg.CORBA.COMM_FAILURE") >= 0)) {
	    	deviceProxy.device = null;
	    	deviceProxy.device_2 = null;
	    	deviceProxy.device_3 = null;
	    	deviceProxy.device_4 = null;
	    	deviceProxy.ior = null;
	    	build_connection(deviceProxy);

	    	if (i == retries - 1) {
			throw_dev_failed(deviceProxy, e, origin, false);
	    	}
		} else {
	    	throw_dev_failed(deviceProxy, e, origin, false);
		}
    }
}
