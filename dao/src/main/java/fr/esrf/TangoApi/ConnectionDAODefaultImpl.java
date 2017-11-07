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
// $Revision: 29387 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CORBA.SystemException;

import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 * 
 * @author verdier
 * @version $Revision: 29387 $
 */

public class ConnectionDAODefaultImpl implements ApiDefs, IConnectionDAO {

    private boolean firstTime = true;

    // ===================================================================
    // ===================================================================
    public Device get_device(final Connection connection) {
		return connection.device;
    }

    // ===================================================================
    // ===================================================================
    private String buildUrlName(final String host, final String port) {
        try {
            return "tango://" + TangoUrl.getCanonicalName(host) + ":" + port;
        }
        catch (DevFailed e) {
    		return "tango://" + host + ":" + port;
        }
    }

    // ===================================================================
    // ===================================================================
    private String buildUrlName(final String host, final String port, final String devname) {
        try {
    		return "tango://" + TangoUrl.getCanonicalName(host) + ":" + port + "/" + devname;
        }
        catch (DevFailed e) {
    		return "tango://" + host + ":" + port + "/" + devname;
        }
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
		//connection.url.trace();

		ApiUtil.get_orb();
		connect_to_dbase(connection);
		connection.devname = connection.device.name();
		connection.setAlready_connected(true);
    }

    // ===================================================================
    /**
     * Connection constructor. It makes a connection on database server.
     * 
     * @param host host where database is running.
     * @param port port for database connection.
     */
    // ===================================================================
    public void init(final Connection connection, final String host, final String port)
	    	throws DevFailed {
		connection.url = new TangoUrl(buildUrlName(TangoUrl.getCanonicalName(host), port));
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
     * @param host host where database is running.
     * @param port port for database connection.
     * @param auto_reconnect do not reconnect if false.
     */
    // ===================================================================
    public void init(final Connection connection, final String host, final String port,
	    	final boolean auto_reconnect) throws DevFailed {
		connection.url = new TangoUrl(buildUrlName(TangoUrl.getCanonicalName(host), port));
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
     * @param devname name of the device to be imported.
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
     * @param info exported info of the device to be imported.
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
		
		try {
			initCtrlAccess(connection);
		}
		catch(DevFailed e) {
			System.err.println(e.errors[0].desc);
		}
    }

    // ===================================================================
    /**
     * Connection constructor. It imports the device. And set check_access.
     * 
     * @param devname name of the device to be imported.
     * @param check_access set check_access value
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
     * @param devname name of the device to be imported.
     * @param param String parameter to import device.
     * @param src Source to import device (ior, dbase...)
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
	    	Except.throw_wrong_syntax_exception(
                    "TangoApi_INVALID_ARGS", "Invalid argument", "Connection.Connection()");
		}
    }

    // ===================================================================
    /**
     * Connection constructor. It imports the device.
     * 
     * @param devname name of the device to be imported.
     * @param host host where database is running.
     * @param port port for database connection.
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
		if (!connection.deviceCreated()) {
            if (connection.devname != null) {
				final long t = System.currentTimeMillis();
				final long delay = t - connection.getPrev_failed_t0();
				boolean try_reconnection = true;

				// Check if connection on database or on a device
				if (connection.isDevice_is_dbase()) {
		    		connect_to_dbase(connection);
				}
                else {
		    		try {
                        // url.trace();
                        // Check if Tango or taco device
                        if (connection.url.protocol == TANGO) {
                            if (connection instanceof DeviceProxy) {
                                //  Set admin device to null to force reconnection.
                                ((DeviceProxy) connection).setAdm_dev(null);
                            }

                            // Do not reconnect if to soon
                            if (connection.isPrev_failed()
                                && delay < ApiUtil.getReconnectionDelay()) {
                                try_reconnection = false;
                                throw connection.getPrev_failed();
                            }
                            //if (!connection.devname.startsWith("tango"))
                            //	System.out.println(" Try reconnection on " +
                            //			connection.devname +":	" + delay + " ms");
                            connection.setPrev_failed_t0(t);

                            // Check if connection with database or without
                            if (connection.url.use_db) {
                                dev_import(connection);
                            } else {
                                dev_import_without_dbase(connection);
                            }
                            connection.setPrev_failed(null);
                        }
                        else
                        if (connection.url.protocol == TACO) {
                            if (connection.taco_device == null) {
                                connection.taco_device =
                                    new TacoTangoDevice(connection.devname, connection.url.host);
                            }
                        }
		    		}
                    catch (final DevFailed e) {
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
     * Check the IDL revision and create the associated device.
     * 
     * @param corba_str  IOR or corbaloc string.
     * @param connection the connection object
     * @throws fr.esrf.Tango.DevFailed if connection failed
     */
    // ===================================================================
    private void createDevice(final Connection connection, final String corba_str) throws DevFailed {
		try {
	    	// Build corba object
	    	final ORB orb = ApiUtil.get_orb();
	    	connection.setObj(orb.string_to_object(corba_str));
		}
        catch (final RuntimeException e) {
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
		}
        catch (final Exception e) {
	    	ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
	    	e.printStackTrace();
		}
		set_obj_timeout(connection, connection.getDev_timeout());

		// Construct the Object
		if (connection.getObj()._is_a("IDL:Tango/Device_5:1.0")) {
	    	//System.out.println(connection.devname + " Device is a Tango/Device_5:1.0 !!!!!!!!!");
	    	connection.device_5 = Device_5Helper.narrow(connection.getObj());
	    	connection.device_4 = connection.device_5;
	    	connection.device_3 = connection.device_5;
	    	connection.device_2 = connection.device_5;
	    	connection.device = connection.device_5;
	    	connection.idl_version = 5;
        }
        else if (connection.getObj()._is_a("IDL:Tango/Device_4:1.0")) {
	    	// System.out.println("Device is a Tango/Device_4:1.0 !!!!!!!!!");
            connection.device_5 = null;
	    	connection.device_4 = Device_4Helper.narrow(connection.getObj());
	    	connection.device_3 = connection.device_4;
	    	connection.device_2 = connection.device_4;
	    	connection.device = connection.device_4;
	    	connection.idl_version = 4;
		}
        else if (connection.getObj()._is_a("IDL:Tango/Device_3:1.0")) {
	    	// System.out.println("Device is a Tango/Device_3:1.0 !!!!!!!!!");
            connection.device_5 = null;
	    	connection.device_4 = null;
	    	connection.device_3 = Device_3Helper.narrow(connection.getObj());
	    	connection.device_2 = connection.device_3;
	    	connection.device = connection.device_3;
	    	connection.idl_version = 3;
		}
        else if (connection.getObj()._is_a("IDL:Tango/Device_2:1.0")) {
	    	System.err.println("Device " + connection.get_name() + " is a Tango/Device_2:1.0 !!!!!!!!!");
            connection.device_5 = null;
	    	connection.device_4 = null;
	    	connection.device_3 = null;
	    	connection.device_2 = Device_2Helper.narrow(connection.getObj());
	    	connection.device = connection.device_2;
	    	connection.idl_version = 2;
		}
        else if (connection.getObj()._is_a("IDL:Tango/Device:1.0")) {
	    	System.err.println("Device " + connection.get_name() + " is a Tango/Device:1.0 !!!!!!!!!");
            connection.device_5 = null;
	    	connection.device_4 = null;
	    	connection.device_3 = null;
	    	connection.device_2 = null;
	    	connection.device = DeviceHelper.narrow(connection.getObj());
	    	connection.idl_version = 1;
		}
        else {
	    	System.err.println("TangoApi_DEVICE_IDL_UNKNOWN!");
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
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
		}

		final DbDevImportInfo info = db.import_device(connection.devname);
		return info.ior;
    }

    // ===================================================================
    // ===================================================================
    public String get_host_name(final Connection connection) throws DevFailed {
        if (connection.url.use_db) {
            Database db;
            if (connection.url.host == null) {
                db = ApiUtil.get_db_obj();
            } else {
                db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
            }
            final DbDevImportInfo info = db.import_device(connection.devname);
            return info.hostname;
        }
        else {
            return connection.url.host;
        }
    }

    // ===================================================================
    // ===================================================================
    public String get_server_name(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
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
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
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
		if (connection.url.use_db && connection.check_access && !connection.check_access_done) {

	    	connection.access = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort)
		    	.checkAccessControl(connection.devname, connection.url);

	    	//System.out.println(connection.devname + " -> " +
	    	// ((connection.access==TangoConst.ACCESS_READ)? "Read" : "Write"));

		} else {
	    	connection.access = TangoConst.ACCESS_WRITE;
		}
		connection.check_access_done = true;
    }

    // ===================================================================
    // ===================================================================
    private String get_exported_ior(final Connection connection) throws DevFailed {
		Database db;
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
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
	    	try {
				initCtrlAccess(connection);
			}
			catch(DevFailed e) {
				System.err.println(e.errors[0].desc);
			}
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
		if (connection.url.host == null) {
	    	db = ApiUtil.get_db_obj();
		} else {
	    	db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
		}
		// Check if device must be imported directly from IOR
        String  local_ior = null;
        boolean ior_read  = false;
		if (connection.ior == null) {
	    	final DbDevImportInfo info = db.import_device(connection.devname);
	    	if (info.exported) {
                 local_ior = info.ior;
                 ior_read = true;
	    	} else {
                Except.throw_connection_failed("TangoApi_DEVICE_NOT_EXPORTED",
                        connection.devname + " Not Exported !",
                        "Connection(" + connection.devname + ")");
             }
		} else {
	    	local_ior = connection.ior;
		}
		try {
	    	// Import the TANGO device
            try {
    	    	createDevice(connection, local_ior);
            }
            catch(final Exception e0) {
                if (ior_read || connection.isAlready_connected())
                    throw e0;   //  Has already been connected
				if (e0.toString().startsWith("org.omg.CORBA.TRANSIENT")) {
    				throw e0;   //  Connection failed (Hard killed)
				}
                // Else it is the first time, retry (ior could have changed)
                connection.ior = null;
                dev_import(connection);
            }

	    	connection.setAlready_connected(true);
	    	connection.ior = local_ior;

		} catch (final DevFailed e) {
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.device_5 = null;
	    	connection.ior = null;
	    	throw e;
		} catch (final Exception e) {
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.device_5 = null;
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
                // Prepare the connection string
                final String db_corbaloc = "corbaloc:iiop:" + connection.url.host + ":"
                    + connection.url.strPort + "/database";
                // And connect to database.
                createDevice(connection, db_corbaloc);
                connection.classname = "Database";
 	    	} catch (final SystemException ex) {
				if (connection.transparent_reconnection) {
		    		try {
                        final DbRedundancy dbr = DbRedundancy.get_instance();
                        final String th2 = dbr.get(connection.url.host + ":" + connection.url.port);

                        // Prepare the connection string
                        final String db_corbaloc = "corbaloc:iiop:" + th2 + "/database";

                        // And connect to database.
                        createDevice(connection, db_corbaloc);
                        // System.out.println("Connected to " + db_corbaloc);
                    } catch (final SystemException e) {
                        // e.printStackTrace();
                        connection.device = null;
                        connection.ior = null;
                        Except.throw_connection_failed("TangoApi_DATABASE_CONNECTION_FAILED",
                            "Connection to database failed  !\n" + e, "connect_to_dbase("
                                + connection.url.host + "," + connection.url.strPort + ")");
 		    		}
				} else {
		    		// e.printStackTrace();
		    		connection.device = null;
		    		connection.ior = null;
    		    	Except.throw_connection_failed("TangoApi_DATABASE_CONNECTION_FAILED",
	    		    	"Connection to database failed  !\n" + ex, "connect_to_dbase("
		    		    	+ connection.url.host + "," + connection.url.strPort + ")");
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
                 // Prepare the connection string
                 final String db_corbaloc = "corbaloc:iiop:" + connection.url.host + ":"
                     + connection.url.strPort + "/" + connection.devname.toLowerCase();
                 // System.out.println("db_corbaloc=" + db_corbaloc);

                 // And connect to device.
                 createDevice(connection, db_corbaloc);
                 connection.access = TangoConst.ACCESS_WRITE;
	    	 } catch (final SystemException e) {
                 connection.device = null;
                 connection.ior = null;
                 // e.printStackTrace();
                 Except.throw_connection_failed("TangoApi_DEVICE_CONNECTION_FAILED",
                     "Connection to device without database failed  !\n" + e,
                     "Connection.dev_import_without_dbase(" + connection.url.host + ","
                         + connection.url.strPort + ")");
	    	 }
		 }
    }

    // ===================================================================
    // ===================================================================
    private void set_obj_timeout(final Connection connection, final int millis) {

		// Change Jacorb policy for timeout
		final org.omg.CORBA.Policy p =
			new org.jacorb.orb.policies.RelativeRoundtripTimeoutPolicy(10000 * millis);
		org.omg.CORBA.Object    obj = connection.getObj()._set_policy_override(
                new Policy[] { p }, org.omg.CORBA.SetOverrideType.ADD_OVERRIDE);

        //  Set new instances to connection object
        connection.setObj(obj);
        switch (connection.idl_version) {
            case 5:
                connection.device_5 = Device_5Helper.narrow(obj);
                break;
            case 4:
                connection.device_4 = Device_4Helper.narrow(obj);
                break;
            case 3:
                connection.device_3 = Device_3Helper.narrow(obj);
                break;
            case 2:
                connection.device_2 = Device_2Helper.narrow(obj);
                break;
            default:
                connection.device = DeviceHelper.narrow(obj);
                break;
        }

		// Save new timeout value
		connection.setDev_timeout(millis);
    }

    // ===================================================================
    /**
     * Change the timeout value for a device call.
     * 
     * @param millis  New value of the timeout in milliseconds.
     * @throws DevFailed  if orb.create_policy throws an org.omg.CORBA.PolicyError
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
		final String origin = from_inout_cmd ? connection.devname + ".command_inout(" + command + ")"
                : connection.devname + "." + command + ")";
		// e.printStackTrace();
		if (e.toString().contains("org.omg.CORBA.NO_RESPONSE")
			|| e.toString().contains("org.omg.CORBA.TIMEOUT")
			|| e.toString().contains("org.omg.CORBA.IMP_LIMIT")) {
	    	desc = "Device (" + connection.devname + ") timed out (>" + connection.getDev_timeout()
		    	+ " ms)!";
	    	Except.throw_communication_timeout(e.toString(), desc, origin);
		} else if (e.toString().contains("org.omg.CORBA.BAD_INV_ORDER")) {
	    	desc = "Lost Connection during command : " + command;
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.device_5 = null;
	    	connection.ior = null;
	    	Except.throw_connection_failed(e.toString(), desc, origin);
		} else if (e.toString().contains("org.omg.CORBA.TRANSIENT")
			|| e.toString().contains("org.omg.CORBA.UNKNOWN")
			|| e.toString().contains("org.omg.CORBA.COMM_FAILURE")
			|| e.toString().contains("org.omg.CORBA.OBJECT_NOT_EXIST")) {
	    	desc = "Lost Connection during command : " + command;
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.device_5 = null;
	    	connection.ior = null;
	    	Except.throw_connection_failed(e.toString(), desc, origin);
		} else if (e.toString().startsWith("java.lang.RuntimeException")) {
	    	desc = "API has catched a RuntimeException" + command;
	    	connection.device = null;
	    	connection.device_2 = null;
	    	connection.device_3 = null;
	    	connection.device_4 = null;
	    	connection.device_5 = null;
	    	connection.ior = null;
	    	Except.throw_connection_failed(e.toString(), desc, origin);
		} else {
	    	System.out.println("API has caught an exception for " + origin + " : \n" + e);
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
                        ApiUtil.getHostName(),// + "  (" + ApiUtil.getHostAddress() + ")",
                origin);
    }
    // ===========================================================
    /**
     * Send a command to a device server.
     * 
     * @param connection the connection object
     * @param command Command name to send to the device.
     * @param argin input command argument.
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
		//System.out.println(connection.devname + ".command_inout("+command + ") -----------> " +
		//		((connection.access==TangoConst.ACCESS_READ)? "Read" : "Write"));

		//
		// Manage Access control
		//
		if (connection.access == TangoConst.ACCESS_READ) {
	    	final Database db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
	    	if (!db.isCommandAllowed(connection.get_class_name(), command)) {
				// Check if not allowed or PB with access device
				if (db.access_devfailed != null) {
		    		throw db.access_devfailed;
				}
                //  Special case for first connection on database
                if (firstTime && connection instanceof Database) {
                    firstTime = false;
                    return command_inout(connection, command, argin);
                }
				// ping the device to throw exception
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
				if (connection.device_5 != null) {
		    		received = connection.device_5.command_inout_4(command, argin.extractAny(),
			    		connection.dev_src, DevLockManager.getInstance().getClntIdent());
                } else if (connection.device_4 != null) {
		    		received = connection.device_4.command_inout_4(command, argin.extractAny(),
			    		connection.dev_src, DevLockManager.getInstance().getClntIdent());
                } else if (connection.device_2 != null) {
                    received = connection.device_2.command_inout_2(command, argin.extractAny(),
                        connection.dev_src);
                } else {
                    received = connection.device.command_inout(command, argin.extractAny());
                }
                done = true;
            }
			catch (final DevFailed e) {
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
					manageExceptionReconnection(connection, retries, i, e,
						 this.getClass() + ".command_inout");
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
		final int maxRetries = connection.transparent_reconnection ? 1 : 0;
		int nbRetries = 0;
		boolean retry;
		do {
	    	try {
				result = doPing(connection);
                retry = false;
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

        final int retries = connection.transparent_reconnection ? 2 : 1;
        for (int oneTry=1 ; oneTry<=retries ; oneTry++) {
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
                if (oneTry>=retries) {
                    final String reason = "TangoApi_CANNOT__READ_CMD_LIST";
                    final String desc = "Cannot read command list for " + connection.devname;
                    final String origin = "Connection.command_list_query()";
                    Except.throw_connection_failed(e, reason, desc, origin);
                }
            } catch (final Exception e) {
                if (oneTry>=retries) {
                    ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(connection);
                    throw_dev_failed(connection, e, "command_list_query", false);
                }
            }
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
     * return the administration device name.
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

        if (name !=null &&  !name.startsWith("tango://") && !name.startsWith("//"))
            name = "tango://" + connection.url.host + ":" + connection.url.port + "/" + name;
		// If no database, add syntax to be used in DeviceProxy constructor.
		if (!connection.url.use_db) {
            name += "#dbase=no";
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
		    return connection.url.host + ":" + connection.url.strPort;
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
	final Database db = ApiUtil.get_db_obj(connection.url.host, connection.url.strPort);
	    return db.isCommandAllowed(connection.get_class_name(), cmd);
    }

    // ==========================================================================
    // ==========================================================================
    protected void manageExceptionReconnection(final Connection deviceProxy, final int retries,
	    final int i, final Exception e, final String origin) throws DevFailed {
		ApiUtilDAODefaultImpl.removePendingRepliesOfDevice(deviceProxy);
		if (i==0
			&& (e.toString().contains("org.omg.CORBA.TRANSIENT") ||
				e.toString().contains("org.omg.CORBA.OBJECT_NOT_EXIST") ||
                e.toString().contains("org.omg.CORBA.COMM_FAILURE"))) {
	    	deviceProxy.device = null;
	    	deviceProxy.device_2 = null;
	    	deviceProxy.device_3 = null;
	    	deviceProxy.device_4 = null;
	    	deviceProxy.device_5 = null;
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
