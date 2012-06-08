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
// Revision 1.2  2007/08/23 09:47:52  ounsy
// add last modif : Methods use_db() and get_db_obj() added.
//
// Revision 1.1  2007/07/04 12:55:33  ounsy
// creation of 3 sub modules :
// 	- client for the webtangorb classes
// 	- common for the classes used by webtangorb and the tangowebserver
// 	- server for the generic classes of tangowebserver
//
// Revision 1.2  2007/07/02 12:03:46  ounsy
// Correction for tango web access
//
// Revision 3.31  2007/05/29 08:11:15  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.30  2007/02/15 16:12:42  pascal_verdier
// Catch for runtime exception added in read_attribute_history
// and read_command_history  methods.
//
// Revision 3.29  2006/11/27 16:11:12  pascal_verdier
// Bug in reconnection fixed for org.omg.CORBA.OBJECT_NOT_EXIST exception.
// Bug in reconnection fixed for write_attribute() and status() call.
//
// Revision 3.28  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.27  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.26  2006/03/10 08:23:16  pascal_verdier
// Exception catched and converted to DevFailed in
// DeviceProxy.subscribe_event() andDeviceProxy.unsubscribe_event()
//
// Revision 3.25  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.24  2005/11/29 05:34:55  pascal_verdier
// TACO info added.
//
// Revision 3.23  2005/10/10 14:09:57  pascal_verdier
// set_source and get_source methods added for Taco device mapping.
// Compatibility with Taco-1.5.jar.
//
// Revision 3.22  2005/08/30 07:33:44  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.21  2005/08/10 08:13:30  pascal_verdier
// The object returned by get_adm_dev() is the same object
// for all devices of a server (do not re-create).
//
// Revision 3.20  2005/04/26 13:27:09  pascal_verdier
// unexport added for command line.
//
// Revision 3.19  2005/03/01 08:21:45  pascal_verdier
// Attribute name set to lower case in subscribe event.
//
// Revision 3.18  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.17  2005/02/10 14:07:33  pascal_verdier
// Build connection before get_attribute_list() if not already done.
//
// Revision 3.16  2005/01/19 10:17:00  pascal_verdier
// Convert NamedDevFailedList to DevFailed for single write_attribute().
//
// Revision 3.15  2005/01/06 10:16:39  pascal_verdier
// device_2 create a AttributeIfoExt on get_attribute_info_ex().
//
// Revision 3.14  2004/12/16 10:16:44  pascal_verdier
// Missing TANGO 5 features added.
//
// Revision 3.13  2004/12/07 14:29:30  pascal_verdier
// NonDbDevice exception management added.
//
// Revision 3.12  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.11  2004/11/30 13:06:19  pascal_verdier
// get_adm_dev() method added..
//
// Revision 3.10  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.9  2004/09/23 14:00:15  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.8  2004/09/17 07:57:03  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.7  2004/06/29 04:02:57  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.3  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.2  2003/09/08 11:02:34  pascal_verdier
// *** empty log message ***
//
// Revision 3.1  2003/05/19 13:35:14  pascal_verdier
// Bug on transparency reconnection fixed.
// input argument modified for add_logging_target method.
//
// Revision 3.0  2003/04/29 08:03:26  pascal_verdier
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
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import org.omg.CORBA.Any;
import org.omg.CORBA.Request;

import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

/**
 * Class Description: This class manage device connection for Tango objects. It
 * is an api between user and IDL Device object.
 * 
 * <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> String status; <Br>
 * DeviceProxy dev = new DeviceProxy("sys/steppermotor/1"); <Br>
 * try { <Br>
 * <ul>
 * DeviceData data = dev.command_inout("DevStatus"); <Br>
 * status = data.extractString(); <Br>
 * </ul> } <Br>
 * catch (DevFailed e) { <Br>
 * <ul>
 * status = "Unknown status"; <Br>
 * Except.print_exception(e); <Br>
 * </ul> } <Br>
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision$
 */

public class DeviceProxyDAOWebImpl extends ConnectionDAOWebImpl implements IDeviceProxyDAO, ApiDefs, IWebImpl {

	private Object[] classParam = null;

	// ===================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#init()
	 */
	// ===================================================================
	public void init(DeviceProxy deviceProxy) {
		//super.init();
		classParam = new Object[] {};
	}

	// ===================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#init(java.lang.String)
	 */
	// ===================================================================
	public void init(DeviceProxy deviceProxy, String devname) throws DevFailed {
		//super.init(devname);
		classParam = new Object[] { devname };
	}

	// ===================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#init(java.lang.String, boolean)
	 */
	// ===================================================================
	public void init(DeviceProxy deviceProxy, String devname, boolean check_access) throws DevFailed {
		//super.init(devname, check_access);
		classParam = new Object[] { devname, check_access };
	}

	// ===================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#init(java.lang.String,
	 *      java.lang.String)
	 */
	// ===================================================================
	public void init(DeviceProxy deviceProxy, String devname, String ior) { // throws DevFailed {
		classParam = new Object[] { devname, ior };
	}

	// ===================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#init(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	// ===================================================================
	public void init(DeviceProxy deviceProxy, String devname, String host, String port) throws DevFailed {
		//super.init(devname, host, port);
		classParam = new Object[] { devname, host, port };
	}

	//	========================================================================== 
	//========================================================================== 
	public boolean use_db(DeviceProxy deviceProxy) 
	{
		try {
			return (Boolean)WebServerClientUtil.getResponse(this, classParam, "use_db", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return false;
		}			
	} 
	
	//========================================================================== 
	//========================================================================== 
	public Database get_db_obj(DeviceProxy deviceProxy) throws DevFailed 
	{ 
		return (Database)WebServerClientUtil.getResponse(this, classParam, "get_db_obj", new Object[] {}, new Class[] {});		
	}
	
	// ===================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#import_admin_device(java.lang.String)
	 */
	// ===================================================================
	public void import_admin_device(DeviceProxy deviceProxy, String origin) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "import_admin_device", new Object[] { origin }, new Class[] { String.class });

	}

	// ===========================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#name()
	 */
	// ===========================================================
	public String name(DeviceProxy deviceProxy) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "name", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
			return null;
		}

	}

	// ===========================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#status()
	 */
	// ===========================================================
	public String status(DeviceProxy deviceProxy) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "status", new Object[] {}, new Class[] {});

	}

	// ===========================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#status(boolean)
	 */
	// ===========================================================
	public String status(DeviceProxy deviceProxy, boolean src) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "status", new Object[] { src }, new Class[] { boolean.class });

	}

	// ===========================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#state()
	 */
	// ===========================================================
	public DevState state(DeviceProxy deviceProxy ) throws DevFailed {
		return (DevState) WebServerClientUtil.getResponse(this, classParam, "state", new Object[] {}, new Class[] {});

	}

	// ===========================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#state(boolean)
	 */
	// ===========================================================
	public DevState state(DeviceProxy deviceProxy, boolean src) throws DevFailed {
		return (DevState) WebServerClientUtil.getResponse(this, classParam, "state", new Object[] { src }, new Class[] { boolean.class });

	}

	// ===========================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_query(java.lang.String)
	 */
	// ===========================================================
	public CommandInfo command_query(DeviceProxy deviceProxy, String cmdname) throws DevFailed {
		return (CommandInfo) WebServerClientUtil.getResponse(this, classParam, "command_query", new Object[] { cmdname }, new Class[] { String.class });

	}

	// ===========================================================
	// The following methods are an agrega of DbDevice
	// ===========================================================
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_class()
	 */
	// ==========================================================================
	public String get_class(DeviceProxy deviceProxy) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_class", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_class_inheritance()
	 */
	// ==========================================================================
	public String[] get_class_inheritance(DeviceProxy deviceProxy) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_class_inheritance", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#put_alias(java.lang.String)
	 */
	// ==========================================================================
	public void put_alias(DeviceProxy deviceProxy, String aliasname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_alias", new Object[] { aliasname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_alias()
	 */
	// ==========================================================================
	public String get_alias(DeviceProxy deviceProxy) throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "get_alias", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_info()
	 */
	// ==========================================================================
	public DeviceInfo get_info(DeviceProxy deviceProxy) throws DevFailed {
		return (DeviceInfo) WebServerClientUtil.getResponse(this, classParam, "get_info", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#import_device()
	 */
	// ==========================================================================
	public DbDevImportInfo import_device(DeviceProxy deviceProxy) throws DevFailed {
		return (DbDevImportInfo) WebServerClientUtil.getResponse(this, classParam, "import_device", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#export_device(fr.esrf.TangoApi.DbDevExportInfo)
	 */
	// ==========================================================================
	public void export_device(DeviceProxy deviceProxy, DbDevExportInfo devinfo) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "export_device", new Object[] { devinfo }, new Class[] { DbDevExportInfo.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#unexport_device()
	 */
	// ==========================================================================
	public void unexport_device(DeviceProxy deviceProxy) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "unexport_device", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#add_device(fr.esrf.TangoApi.DbDevInfo)
	 */
	// ==========================================================================
	public void add_device(DeviceProxy deviceProxy, DbDevInfo devinfo) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "add_device", new Object[] { devinfo }, new Class[] { DbDevInfo.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_device()
	 */
	// ==========================================================================
	public void delete_device(DeviceProxy deviceProxy) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_device", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_property_list(java.lang.String)
	 */
	// ==========================================================================
	public String[] get_property_list(DeviceProxy deviceProxy, String wildcard) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_property_list", new Object[] { wildcard }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_property(java.lang.String[])
	 */
	// ==========================================================================
	public DbDatum[] get_property(DeviceProxy deviceProxy, String[] propnames) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { propnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_property(java.lang.String)
	 */
	// ==========================================================================
	public DbDatum get_property(DeviceProxy deviceProxy, String propname) throws DevFailed {
		return (DbDatum) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { propname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_property(fr.esrf.TangoApi.DbDatum[])
	 */
	// ==========================================================================
	public DbDatum[] get_property(DeviceProxy deviceProxy, DbDatum[] properties) throws DevFailed {
		return (DbDatum[]) WebServerClientUtil.getResponse(this, classParam, "get_property", new Object[] { properties }, new Class[] { DbDatum[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#put_property(fr.esrf.TangoApi.DbDatum)
	 */
	// ==========================================================================
	public void put_property(DeviceProxy deviceProxy, DbDatum prop) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_property", new Object[] { prop }, new Class[] { DbDatum.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#put_property(fr.esrf.TangoApi.DbDatum[])
	 */
	// ==========================================================================
	public void put_property(DeviceProxy deviceProxy, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_property", new Object[] { properties }, new Class[] { DbDatum[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_property(java.lang.String[])
	 */
	// ==========================================================================
	public void delete_property(DeviceProxy deviceProxy, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_property", new Object[] { propnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_property(java.lang.String)
	 */
	// ==========================================================================
	public void delete_property(DeviceProxy deviceProxy, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_property", new Object[] { propname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_property(fr.esrf.TangoApi.DbDatum[])
	 */
	// ==========================================================================
	public void delete_property(DeviceProxy deviceProxy, DbDatum[] properties) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_property", new Object[] { properties }, new Class[] { DbDatum[].class });

	}

	// ============================================
	// ATTRIBUTE PROPERTY MANAGEMENT
	// ============================================

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_list()
	 */
	// ==========================================================================
	public String[] get_attribute_list(DeviceProxy deviceProxy) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_list", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#put_attribute_property(fr.esrf.TangoApi.DbAttribute[])
	 */
	// ==========================================================================
	public void put_attribute_property(DeviceProxy deviceProxy, DbAttribute[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_attribute_property", new Object[] { attr }, new Class[] { DbAttribute[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#put_attribute_property(fr.esrf.TangoApi.DbAttribute)
	 */
	// ==========================================================================
	public void put_attribute_property(DeviceProxy deviceProxy, DbAttribute attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "put_attribute_property", new Object[] { attr }, new Class[] { DbAttribute.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_attribute_property(java.lang.String,
	 *      java.lang.String[])
	 */
	// ==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, String attname, String[] propnames) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_attribute_property", new Object[] { attname, propnames }, new Class[] { String.class, String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_attribute_property(java.lang.String,
	 *      java.lang.String)
	 */
	// ==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, String attname, String propname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_attribute_property", new Object[] { attname, propname }, new Class[] { String.class, String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_attribute_property(fr.esrf.TangoApi.DbAttribute)
	 */
	// ==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, DbAttribute attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_attribute_property", new Object[] { attr }, new Class[] { DbAttribute.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_attribute_property(fr.esrf.TangoApi.DbAttribute[])
	 */
	// ==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, DbAttribute[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_attribute_property", new Object[] { attr }, new Class[] { DbAttribute[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_property(java.lang.String[])
	 */
	// ==========================================================================
	public DbAttribute[] get_attribute_property(DeviceProxy deviceProxy, String[] attnames) throws DevFailed {
		return (DbAttribute[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_property", new Object[] { attnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_property(java.lang.String)
	 */
	// ==========================================================================
	public DbAttribute get_attribute_property(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (DbAttribute) WebServerClientUtil.getResponse(this, classParam, "get_attribute_property", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#delete_attribute(java.lang.String)
	 */
	// ==========================================================================
	public void delete_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "delete_attribute", new Object[] { attname }, new Class[] { String.class });

	}

	// ===========================================================
	// Attribute Methods
	// ===========================================================
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_info(java.lang.String[])
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_info(DeviceProxy deviceProxy, String[] attnames) throws DevFailed {
		return (AttributeInfo[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_info", new Object[] { attnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_info_ex(java.lang.String[])
	 */
	// ==========================================================================
	public AttributeInfoEx[] get_attribute_info_ex(DeviceProxy deviceProxy, String[] attnames) throws DevFailed {
		return (AttributeInfoEx[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_info_ex", new Object[] { attnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_config(java.lang.String[])
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_config(DeviceProxy deviceProxy, String[] attnames) throws DevFailed {
		return (AttributeInfo[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_config", new Object[] { attnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_info(java.lang.String)
	 */
	// ==========================================================================
	public AttributeInfo get_attribute_info(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (AttributeInfo) WebServerClientUtil.getResponse(this, classParam, "get_attribute_info", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_info_ex(java.lang.String)
	 */
	// ==========================================================================
	public AttributeInfoEx get_attribute_info_ex(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (AttributeInfoEx) WebServerClientUtil.getResponse(this, classParam, "get_attribute_info_ex", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_config(java.lang.String)
	 */
	// ==========================================================================
	public AttributeInfo get_attribute_config(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (AttributeInfo) WebServerClientUtil.getResponse(this, classParam, "get_attribute_config", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_info()
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_info(DeviceProxy deviceProxy) throws DevFailed {
		return (AttributeInfo[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_info", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_info_ex()
	 */
	// ==========================================================================
	public AttributeInfoEx[] get_attribute_info_ex(DeviceProxy deviceProxy) throws DevFailed {
		return (AttributeInfoEx[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_info_ex", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_attribute_config()
	 */
	// ==========================================================================
	public AttributeInfo[] get_attribute_config(DeviceProxy deviceProxy) throws DevFailed {
		return (AttributeInfo[]) WebServerClientUtil.getResponse(this, classParam, "get_attribute_config", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#set_attribute_info(fr.esrf.TangoApi.AttributeInfo[])
	 */
	// ==========================================================================
	public void set_attribute_info(DeviceProxy deviceProxy, AttributeInfo[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_attribute_info", new Object[] { attr }, new Class[] { AttributeInfo[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#set_attribute_info(fr.esrf.TangoApi.AttributeInfoEx[])
	 */
	// ==========================================================================
	public void set_attribute_info(DeviceProxy deviceProxy, AttributeInfoEx[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_attribute_info", new Object[] { attr }, new Class[] { AttributeInfoEx[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#set_attribute_config(fr.esrf.TangoApi.AttributeInfo[])
	 */
	// ==========================================================================
	public void set_attribute_config(DeviceProxy deviceProxy, AttributeInfo[] attr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_attribute_config", new Object[] { attr }, new Class[] { AttributeInfo[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute(java.lang.String)
	 */
	// ==========================================================================
	public DeviceAttribute read_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (DeviceAttribute) WebServerClientUtil.getResponse(this, classParam, "read_attribute", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * return directly AttributeValue object without creation of DeviceAttribute
	 * object
	 */
	// ==========================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_value(java.lang.String)
	 */
	public AttributeValue read_attribute_value(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (AttributeValue) WebServerClientUtil.getResponse(this, classParam, "read_attribute_value", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute(java.lang.String[])
	 */
	// ==========================================================================
	public DeviceAttribute[] read_attribute(DeviceProxy deviceProxy, String[] attnames) throws DevFailed {
		return (DeviceAttribute[]) WebServerClientUtil.getResponse(this, classParam, "read_attribute", new Object[] { attnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute(fr.esrf.TangoApi.DeviceAttribute)
	 */
	// ==========================================================================
	public void write_attribute(DeviceProxy deviceProxy, DeviceAttribute devattr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "write_attribute", new Object[] { devattr }, new Class[] { DeviceAttribute.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute(fr.esrf.TangoApi.DeviceAttribute[])
	 */
	// ==========================================================================
	public void write_attribute(DeviceProxy deviceProxy, DeviceAttribute[] devattr) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "write_attribute", new Object[] { devattr }, new Class[] { DeviceAttribute[].class });

	}

	// ==========================================================================
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_adm_dev()
	 */
	public DeviceProxy get_adm_dev(DeviceProxy deviceProxy) throws DevFailed {
		return (DeviceProxy) WebServerClientUtil.getResponse(this, classParam, "get_adm_dev", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/**
	 * Polling commands.
	 */
	// ==========================================================================
	// ==========================================================================
	/**
	 * Add a command to be polled for the device. If already polled, update its
	 * polling period.
	 * 
	 * @param objname
	 *            command/attribute name to be polled.
	 * @param objtype
	 *            command or attribute.
	 * @param period
	 *            polling period.
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#poll_command(java.lang.String, int)
	 */
	// ==========================================================================
	public void poll_command(DeviceProxy deviceProxy, String cmdname, int period) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "poll_command", new Object[] { cmdname, period }, new Class[] { String.class, int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#poll_attribute(java.lang.String,
	 *      int)
	 */
	// ==========================================================================
	public void poll_attribute(DeviceProxy deviceProxy, String attname, int period) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "poll_attribute", new Object[] { attname, period }, new Class[] { String.class, int.class });

	}

	// ==========================================================================
	/**
	 * Remove object of polled object list
	 * 
	 * @param objname
	 *            object name to be removed of polled object list.
	 * @param objtype
	 *            object type (command or attribute).
	 */
	// ==========================================================================
	// TODO remove javadoc
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#stop_poll_command(java.lang.String)
	 */
	// ==========================================================================
	public void stop_poll_command(DeviceProxy deviceProxy, String cmdname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "stop_poll_command", new Object[] { cmdname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#stop_poll_attribute(java.lang.String)
	 */
	// ==========================================================================
	public void stop_poll_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "stop_poll_attribute", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#polling_status()
	 */
	// ==========================================================================
	public String[] polling_status(DeviceProxy deviceProxy) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "polling_status", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_history(java.lang.String,
	 *      int)
	 */
	// ==========================================================================
	public DeviceDataHistory[] command_history(DeviceProxy deviceProxy, String cmdname, int nb) throws DevFailed {
		return (DeviceDataHistory[]) WebServerClientUtil.getResponse(this, classParam, "command_history", new Object[] { cmdname, nb }, new Class[] { String.class, int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#attribute_history(java.lang.String,
	 *      int)
	 */
	// ==========================================================================
	public DeviceDataHistory[] attribute_history(DeviceProxy deviceProxy, String attname, int nb) throws DevFailed {
		return (DeviceDataHistory[]) WebServerClientUtil.getResponse(this, classParam, "attribute_history", new Object[] { attname, nb }, new Class[] { String.class, int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_history(java.lang.String)
	 */
	// ==========================================================================
	public DeviceDataHistory[] command_history(DeviceProxy deviceProxy, String cmdname) throws DevFailed {
		return (DeviceDataHistory[]) WebServerClientUtil.getResponse(this, classParam, "command_history", new Object[] { cmdname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#attribute_history(java.lang.String)
	 */
	// ==========================================================================
	public DeviceDataHistory[] attribute_history(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (DeviceDataHistory[]) WebServerClientUtil.getResponse(this, classParam, "attribute_history", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/**
	 * Asynchronous calls
	 */
	// ==========================================================================
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_asynch(java.lang.String,
	 *      fr.esrf.TangoApi.DeviceData)
	 */
	// ==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData data_in) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "command_inout_asynch", new Object[] { cmdname, data_in }, new Class[] { String.class, DeviceData.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_asynch(java.lang.String)
	 */
	// ==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "command_inout_asynch", new Object[] { cmdname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_asynch(java.lang.String,
	 *      boolean)
	 */
	// ==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, boolean forget) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "command_inout_asynch", new Object[] { cmdname, forget }, new Class[] { String.class, boolean.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_asynch(java.lang.String,
	 *      fr.esrf.TangoApi.DeviceData, boolean)
	 */
	// ==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData data_in, boolean forget) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "command_inout_asynch", new Object[] { cmdname, data_in, forget }, new Class[] { String.class, DeviceData.class,
				boolean.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_asynch(java.lang.String,
	 *      fr.esrf.TangoApi.DeviceData, fr.esrf.TangoApi.CallBack)
	 */
	// ==========================================================================
	public void command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData argin, CallBack cb) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "command_inout_asynch", new Object[] { cmdname, argin, cb }, new Class[] { String.class, DeviceData.class, CallBack.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_asynch(java.lang.String,
	 *      fr.esrf.TangoApi.CallBack)
	 */
	// ==========================================================================
	public void command_inout_asynch(DeviceProxy deviceProxy, String cmdname, CallBack cb) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "command_inout_asynch", new Object[] { cmdname, cb }, new Class[] { String.class, CallBack.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_reply(int, int)
	 */
	// ==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, int id, int timeout) throws DevFailed, AsynReplyNotArrived {
		return (DeviceData) WebServerClientUtil.getResponse(this, classParam, "command_inout_reply", new Object[] { id, timeout }, new Class[] { int.class, int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_reply(fr.esrf.TangoApi.AsyncCallObject,
	 *      int)
	 */
	// ==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, AsyncCallObject aco, int timeout) throws DevFailed, AsynReplyNotArrived {
		DeviceData argout = null;
		int ms_to_sleep = 50;
		AsynReplyNotArrived except = null;
		long t0 = System.currentTimeMillis();
		long t1 = t0;

		// System.out.println("command_inout_reply to= " + timeout + " ms");
		while (((t1 - t0) < timeout || timeout == 0) && argout == null) {
			try {
				argout = command_inout_reply(deviceProxy, aco);
			} catch (AsynReplyNotArrived na) {
				except = na;
				// Wait a bit before retry
				this.sleep(ms_to_sleep);
				t1 = System.currentTimeMillis();
				// System.out.println(" " + (t1-t0) + " ms");
			} catch (DevFailed e) {
				throw e;
			}
		}
		// If reply not arrived throw last exception
		if (argout == null && except != null)
			throw except;

		return argout;
	}

	private synchronized void sleep(long ms) {
		try {
			wait(ms);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_reply(int)
	 */
	// ==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, int id) throws DevFailed, AsynReplyNotArrived {
		return (DeviceData) WebServerClientUtil.getResponse(this, classParam, "command_inout_reply", new Object[] { id }, new Class[] { int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#command_inout_reply(fr.esrf.TangoApi.AsyncCallObject)
	 */
	// ==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, AsyncCallObject aco) throws DevFailed, AsynReplyNotArrived {
		DeviceData data = null;

		check_asynch_reply(deviceProxy, aco.request, aco.id, "command_inout");

		// If no exception, extract the any from return value,
		Any any = aco.request.return_value().extract_any();

		// And put it in a DeviceData object
		data = new DeviceData();
		data.any = any;
		ApiUtil.remove_async_request(aco.id);

		return data;
	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_asynch(java.lang.String)
	 */
	// ==========================================================================
	public int read_attribute_asynch(DeviceProxy deviceProxy, String attname) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "read_attribute_asynch", new Object[] { attname }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_asynch(java.lang.String[])
	 */
	// ==========================================================================
	public int read_attribute_asynch(DeviceProxy deviceProxy, String[] attnames) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "read_attribute_asynch", new Object[] { attnames }, new Class[] { String[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_asynch_idl_cmd(org.omg.CORBA.Request,
	 *      java.lang.String)
	 */
	// ==========================================================================
	public String get_asynch_idl_cmd(DeviceProxy deviceProxy, Request request, String idl_cmd) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "get_asynch_idl_cmd", new Object[] { request, idl_cmd }, new Class[] { Request.class, String.class });
		} catch (DevFailed e) {
			e.printStackTrace();
			return null;
		}

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#check_asynch_reply(org.omg.CORBA.Request,
	 *      int, java.lang.String)
	 */
	// ==========================================================================
	public void check_asynch_reply(DeviceProxy deviceProxy, Request request, int id, String idl_cmd) throws DevFailed, AsynReplyNotArrived {
		WebServerClientUtil.getResponse(this, classParam, "check_asynch_reply", new Object[] { request, id, idl_cmd }, new Class[] { Request.class, int.class, String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_reply(int, int)
	 */
	// ==========================================================================
	public DeviceAttribute[] read_attribute_reply(DeviceProxy deviceProxy, int id, int timeout) throws DevFailed, AsynReplyNotArrived {
		return (DeviceAttribute[]) WebServerClientUtil.getResponse(this, classParam, "read_attribute_reply", new Object[] { id, timeout }, new Class[] { int.class, int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_reply(int)
	 */
	// ==========================================================================
	public DeviceAttribute[] read_attribute_reply(DeviceProxy deviceProxy, int id) throws DevFailed, AsynReplyNotArrived {
		return (DeviceAttribute[]) WebServerClientUtil.getResponse(this, classParam, "read_attribute_reply", new Object[] { id }, new Class[] { int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_asynch(java.lang.String,
	 *      fr.esrf.TangoApi.CallBack)
	 */
	// ==========================================================================
	public void read_attribute_asynch(DeviceProxy deviceProxy, String attname, CallBack cb) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "read_attribute_asynch", new Object[] { attname, cb }, new Class[] { String.class, CallBack.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#read_attribute_asynch(java.lang.String[],
	 *      fr.esrf.TangoApi.CallBack)
	 */
	// ==========================================================================
	public void read_attribute_asynch(DeviceProxy deviceProxy, String[] attnames, CallBack cb) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "read_attribute_asynch", new Object[] { attnames, cb }, new Class[] { String[].class, CallBack.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_asynch(fr.esrf.TangoApi.DeviceAttribute)
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "write_attribute_asynch", new Object[] { attr }, new Class[] { DeviceAttribute.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_asynch(fr.esrf.TangoApi.DeviceAttribute,
	 *      boolean)
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr, boolean forget) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "write_attribute_asynch", new Object[] { attr, forget }, new Class[] { DeviceAttribute.class, boolean.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_asynch(fr.esrf.TangoApi.DeviceAttribute[])
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "write_attribute_asynch", new Object[] { attribs }, new Class[] { DeviceAttribute[].class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_asynch(fr.esrf.TangoApi.DeviceAttribute[],
	 *      boolean)
	 */
	// ==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs, boolean forget) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "write_attribute_asynch", new Object[] { attribs, forget }, new Class[] { DeviceAttribute[].class, boolean.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_reply(int)
	 */
	// ==========================================================================
	public void write_attribute_reply(DeviceProxy deviceProxy, int id) throws DevFailed, AsynReplyNotArrived {
		WebServerClientUtil.getResponse(this, classParam, "write_attribute_reply", new Object[] { id }, new Class[] { int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_reply(int, int)
	 */
	// ==========================================================================
	public void write_attribute_reply(DeviceProxy deviceProxy, int id, int timeout) throws DevFailed, AsynReplyNotArrived {
		WebServerClientUtil.getResponse(this, classParam, "write_attribute_reply", new Object[] { id, timeout }, new Class[] { int.class, int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_asynch(fr.esrf.TangoApi.DeviceAttribute,
	 *      fr.esrf.TangoApi.CallBack)
	 */
	// ==========================================================================
	public void write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr, CallBack cb) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "write_attribute_asynch", new Object[] { attr, cb }, new Class[] { DeviceAttribute.class, CallBack.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#write_attribute_asynch(fr.esrf.TangoApi.DeviceAttribute[],
	 *      fr.esrf.TangoApi.CallBack)
	 */
	// ==========================================================================
	public void write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs, CallBack cb) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "write_attribute_asynch", new Object[] { attribs, cb }, new Class[] { DeviceAttribute[].class, CallBack.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#pending_asynch_call(int)
	 */
	// ==========================================================================
	public int pending_asynch_call(DeviceProxy deviceProxy, int reply_model) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "pending_asynch_call", new Object[] { reply_model }, new Class[] { int.class });
		} catch (DevFailed e) {
			e.printStackTrace();
			return 0;
		}

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_asynch_replies()
	 */
	// ==========================================================================
	public void get_asynch_replies(DeviceProxy deviceProxy) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "get_asynch_replies", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			e.printStackTrace();
		}

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_asynch_replies(int)
	 */
	// ==========================================================================
	public void get_asynch_replies(DeviceProxy deviceProxy, int timeout) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "get_asynch_replies", new Object[] { timeout }, new Class[] { int.class });
		} catch (DevFailed e) {
			e.printStackTrace();
		}

	}

	// ==========================================================================
	/**
	 * Logging related methods
	 */
	// ==========================================================================
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#add_logging_target(java.lang.String,
	 *      java.lang.String)
	 */
	// ==========================================================================
	public void add_logging_target(DeviceProxy deviceProxy, String target_type, String target_name) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "add_logging_target", new Object[] { target_type, target_name }, new Class[] { String.class, String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#add_logging_target(java.lang.String)
	 */
	// ==========================================================================
	public void add_logging_target(DeviceProxy deviceProxy, String target) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "add_logging_target", new Object[] { target }, new Class[] { String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#remove_logging_target(java.lang.String,
	 *      java.lang.String)
	 */
	// ==========================================================================
	public void remove_logging_target(DeviceProxy deviceProxy, String target_type, String target_name) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "remove_logging_target", new Object[] { target_type, target_name }, new Class[] { String.class, String.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_logging_target()
	 */
	// ==========================================================================
	public String[] get_logging_target(DeviceProxy deviceProxy) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "get_logging_target", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_logging_level()
	 */
	// ==========================================================================
	public int get_logging_level(DeviceProxy deviceProxy) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_logging_level", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#set_logging_level(int)
	 */
	// ==========================================================================
	public void set_logging_level(DeviceProxy deviceProxy, int level) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_logging_level", new Object[] { level }, new Class[] { int.class });

	}

	// ==========================================================================
	/**
	 * TACO commands
	 */
	// ==========================================================================
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#dev_inform()
	 */
	// ==========================================================================
	public String[] dev_inform(DeviceProxy deviceProxy) throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "dev_inform", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#set_rpc_protocol(int)
	 */
	// ==========================================================================
	public void set_rpc_protocol(DeviceProxy deviceProxy, int mode) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "set_rpc_protocol", new Object[] { mode }, new Class[] { int.class });

	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#get_rpc_protocol()
	 */
	// ==========================================================================
	public int get_rpc_protocol(DeviceProxy deviceProxy) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_rpc_protocol", new Object[] {}, new Class[] {});

	}

	// ==========================================================================
	/**
	 * Just a main method to check API methods.
	 */
	// ==========================================================================
	public void main(String args[]) {

	}

	// ===============================================================
	// ===============================================================
	// TODO remove javadoc
	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#subscribe_event(java.lang.String,
	 *      int, fr.esrf.TangoApi.CallBack, java.lang.String[])
	 */
	// ==========================================================================
	public int subscribe_event(DeviceProxy deviceProxy, String attr_name, int event, CallBack callback, String[] filters) throws DevFailed {
		System.out.println("First version of TANGO WEB No event manage");
		DevError error = new DevError();
		throw new DevFailed(new DevError[]{error});
	}

	// ==========================================================================
	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esrf.TangoApi.IDeviceProxyDAO#unsubscribe_event(int)
	 */
	// ==========================================================================
	public void unsubscribe_event(DeviceProxy deviceProxy, int event_id) throws DevFailed {
		//WebServerClientUtil.getResponse(this, classParam, "unsubscribe_event", new Object[] { event_id }, new Class[] { int.class });

	}

	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}
}
