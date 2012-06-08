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
//
// $Revision$
//
// $Log$
// Revision 1.16  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.15  2008/12/19 13:36:39  pascal_verdier
// Attribute data from union for Device_4Impl.
//
// Revision 1.14  2008/12/16 10:02:04  pascal_verdier
// Update asynchronous calls for device_3/4Impl
//
// Revision 1.13  2008/12/03 13:09:22  pascal_verdier
// EventQueue management added.
//
// Revision 1.12  2008/11/12 10:12:35  pascal_verdier
// Check if Database used in one method.
//
// Revision 1.11  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.10  2008/10/10 08:31:57  pascal_verdier
// Security check has been done.
//
// Revision 1.9  2008/09/12 11:32:14  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.8  2008/07/11 08:33:48  pascal_verdier
// Make transparency reconnection on attribute_config
//
// Revision 1.7  2008/04/16 13:00:36  pascal_verdier
// Event subscribtion stateless added.
//
// Revision 1.6  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.5  2007/12/13 09:49:02  pascal_verdier
// check device before get_attribute_info_ex() call added.
//
// Revision 1.4  2007/11/22 07:53:04  pascal_verdier
// Chenge the exception thrown in read_history
//
// Revision 1.3  2007/11/15 12:17:44  ounsy
// add try catch when call device proxy
//
// Revision 1.2  2007/10/26 12:49:41  ounsy
// Change access to any objet in command_inout_asynch et command_inout_reply. Use insert(any) and extractAny() insteadof direct access to any object.
//
// Revision 1.1  2007/08/23 09:41:20  ounsy
// Add default impl for tangorb
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
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoApi.events.EventConsumer;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.NamedDevFailed;
import fr.esrf.TangoDs.NamedDevFailedList;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.Any;
import org.omg.CORBA.NVList;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Request;
import org.omg.CORBA.TypeCode;

/**
 *	Class Description:
 *	This class manage device connection for Tango objects.
 *	It is an api between user and IDL Device object.
 *
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *		String	status; <Br>
 *		DeviceProxy	dev = DeviceProxyFactory.get("sys/steppermotor/1"); <Br>
 *		try { <Br>	<ul>
 *			DeviceData	data = dev.command_inout("DevStatus"); <Br>
 *			status = data.extractString(); <Br>	</ul>
 *		} <Br>
 *		catch (DevFailed e) { <Br>	<ul>
 *			status = "Unknown status"; <Br>
 *			Except.print_exception(e); <Br>	</ul>
 *		} <Br>
 *	</ul></i>
 *
 * @author  verdier
 * @version  $Revision$
 */


@SuppressWarnings({"ThrowInsideCatchBlockWhichIgnoresCaughtException", "NestedTryStatement"})
public class DeviceProxyDAODefaultImpl extends ConnectionDAODefaultImpl implements ApiDefs, IDeviceProxyDAO
{

	public DeviceProxyDAODefaultImpl()
	{
		//super();
	}	
	
	//===================================================================
	/**
	 *	Default DeviceProxy constructor. It will do nothing
	 */
	//===================================================================
	public void init(DeviceProxy deviceProxy)	throws DevFailed
	{
		//super.init(deviceProxy);
	}
	//===================================================================
	/**
	 *	DeviceProxy constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 */
	//===================================================================
	public void init(DeviceProxy deviceProxy, String devname)	throws DevFailed
	{
		//super.init(deviceProxy, devname);
		deviceProxy.setFull_class_name("DeviceProxy("+name(deviceProxy)+")");
	}
	//===================================================================
	/**
	 *	DeviceProxy constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	check_access	set check_access value
	 */
	public void init(DeviceProxy deviceProxy, String devname, boolean check_access)	throws DevFailed
	{
		//super.init(deviceProxy, devname, check_access);
		deviceProxy.setFull_class_name("DeviceProxy("+name(deviceProxy)+")");
	}
	//===================================================================
	/**
	 *	TangoDevice constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	ior		ior string used to import device
	 */
	//===================================================================
	public void init(DeviceProxy deviceProxy, String devname, String ior)	throws DevFailed
	{
		//super.init(deviceProxy, devname, ior, FROM_IOR);
		deviceProxy.setFull_class_name("DeviceProxy("+name(deviceProxy)+")");
	}
	//===================================================================
	/**
	 *	TangoDevice constructor. It will import the device.
	 *
	 *	@param	devname	name of the device to be imported.
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public void init(DeviceProxy deviceProxy, String devname, String host, String port)	throws DevFailed
	{
		//super.init(deviceProxy, devname, host, port);
		deviceProxy.setFull_class_name("DeviceProxy("+name(deviceProxy)+")");
	}
	
	//	========================================================================== 
	//========================================================================== 
	public boolean use_db(DeviceProxy deviceProxy) 
	{ 
	        return deviceProxy.url.use_db; 
	} 
	//========================================================================== 
	//==========================================================================
	private void checkIfUseDb(DeviceProxy deviceProxy, String origin)
		throws DevFailed
	{
		if  (!deviceProxy.url.use_db)
	        Except.throw_non_db_exception(
					"Api_NonDatabaseDevice",
	               	"Device " + name(deviceProxy) + " do not use database",
					"DeviceProxy(" + name(deviceProxy) + ")." + origin);
	}
	//==========================================================================
	//==========================================================================
	public Database get_db_obj(DeviceProxy deviceProxy) throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_db_obj()");
	    return ApiUtil.get_db_obj(deviceProxy.url.host, deviceProxy.url.strport);
	}



	//===================================================================
	/**
	 *	Get connection on administration device.
	 */
	//===================================================================
	public void import_admin_device(DeviceProxy deviceProxy, DbDevImportInfo info)
				throws DevFailed
	{
		checkIfTango(deviceProxy, "import_admin_device()");
/*
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
*/
		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			if (DeviceProxyFactory.exists(info.name))
				deviceProxy.setAdm_dev(DeviceProxyFactory.get(info.name));
			else
			{
				//	If not exists, create it with info
				deviceProxy.setAdm_dev(new DeviceProxy(info));
			}
	}
	//===================================================================
	/**
	 *	Get connection on administration device.
	 */
	//===================================================================
	public void import_admin_device(DeviceProxy deviceProxy, String origin)
				throws DevFailed
	{
		checkIfTango(deviceProxy, origin);
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			deviceProxy.setAdm_dev(DeviceProxyFactory.get(adm_name(deviceProxy)));
	}
	//===========================================================
	/**
	 *	return the device name.
	 */
	//===========================================================
	public String name(DeviceProxy deviceProxy)
	{
		return get_name(deviceProxy);
	}

	//===========================================================
	/**
	 *	return the device status read from CORBA attribute.
	 */
	//===========================================================
	public String status(DeviceProxy deviceProxy)	throws DevFailed
	{
		return status(deviceProxy, ApiDefs.FROM_ATTR);
	}
	//===========================================================
	/**
	 *	return the device status.
	 *	@param	src	Source to read status.
	 *		It could be ApiDefs.FROM_CMD to read it from a command_inout or
	 *		ApiDefs.FROM_ATTR to read it from CORBA attribute.
	 */
	//===========================================================
	public String status(DeviceProxy deviceProxy, boolean src)	throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		if (deviceProxy.url.protocol==TANGO)
		{
			String	status = "Unknown";
			int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
			boolean done = false;
			for (int i=0 ; i<retries && !done ; i++)
			{
				try
				{
					if (src==ApiDefs.FROM_ATTR)
						status = deviceProxy.device.status();
					else
					{
						DeviceData	argout = deviceProxy.command_inout("Status");
						status = argout.extractString();
					}
					done = true;
				}
				catch(Exception e)
				{
					//	Do reconnection ?
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
					{
						//System.out.println("Retrying...");
						deviceProxy.device = null;
						//ior    = null;
						build_connection(deviceProxy);

						if (i==(retries-1))
						{
							status = "Unknown";
							throw_dev_failed(deviceProxy, e, "DeviceProxy.status()", false);
						}
					}
					else
					{
						status = "Unknown";
						throw_dev_failed(deviceProxy, e, "DeviceProxy.status()", false);
					}
				}
			}
			return status;
		}
		else
			return command_inout(deviceProxy, "DevStatus").extractString();
	}

	//===========================================================
	/**
	 *	return the device state read from CORBA attribute.
	 */
	//===========================================================
	public DevState state(DeviceProxy deviceProxy)	throws DevFailed
	{
		return state(deviceProxy, ApiDefs.FROM_ATTR);
	}
	//===========================================================
	/**
	 *	return the device state.
	 *
	 *	@param	src	Source to read state.
	 *		It could be ApiDefs.FROM_CMD to read it from a command_inout or
	 *		ApiDefs.FROM_ATTR to read it from CORBA attribute.
	 */
	//===========================================================
	public DevState state(DeviceProxy deviceProxy, boolean src)	throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		if (deviceProxy.url.protocol==TANGO)
		{
			DevState	state = DevState.UNKNOWN;
			int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
			boolean done = false;
			for (int i=0 ; i<retries && !done ; i++)
			{
				try
				{
					if (src==ApiDefs.FROM_ATTR)
						state = deviceProxy.device.state();
					else
					{
						DeviceData	argout = deviceProxy.command_inout("State");
						state  = argout.extractDevState();
					}
					done = true;
				}
				catch(Exception e)
				{
					//	Do reconnection ?
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
					{
						//System.out.println("Retrying...");
						deviceProxy.device = null;
						//ior    = null;
						build_connection(deviceProxy);

						if (i==(retries-1))
						{
							state = DevState.UNKNOWN;
							throw_dev_failed(deviceProxy, e, "DeviceProxy.state()", false);
						}
					}
					else
					{
						state = DevState.UNKNOWN;
						throw_dev_failed(deviceProxy, e, "DeviceProxy.state()", false);
					}
				}
			}
			return state;
		}
		else
		{
			DeviceData	argout = command_inout(deviceProxy, "DevState");
			short		state  = argout.extractShort();
			return T2Ttypes.tangoState(state);
		}
	}

	//===========================================================
	/**
	 *	return the IDL device command_query for the specified command.
	 *
	 *	@param cmdname command name to be used for the command_query
	 *	@return the command information..
	 */
	//===========================================================
	public CommandInfo command_query(DeviceProxy deviceProxy, String cmdname)	throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		CommandInfo	info = null;
		if (deviceProxy.url.protocol==TANGO)
		{
			//	try 2 times for reconnection if requested
			boolean done = false;
			int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
			for (int i=0 ; i<retries && !done ; i++)
			{
				try
				{
					//	Check IDL version
					if (deviceProxy.device_2!=null)
					{
						DevCmdInfo_2	tmp = deviceProxy.device_2.command_query_2(cmdname);
						info = new CommandInfo(tmp);
					}
					else
					{
						DevCmdInfo	tmp = deviceProxy.device.command_query(cmdname);
						info = new CommandInfo(tmp);
					}
				}
				catch (Exception e)
				{
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
					{
						deviceProxy.device = null;
						deviceProxy.ior    = null;
						build_connection(deviceProxy);

						if (i==(retries-1))
							throw_dev_failed(deviceProxy, e, "device.command_query()", false);
					}
					else
					{
						throw_dev_failed(deviceProxy, e, "device.command_query()", false);
					}
				}
			}
		}
		else
		{
			//	TACO device
			info = deviceProxy.taco_device.commandQuery(cmdname);
		}
		return info;
	}










	//===========================================================
	//	The following methods are an agrega of DbDevice
	//===========================================================
	//==========================================================================
	/**
	 *	Returns the class for the device
	 */
	//==========================================================================
	public String get_class(DeviceProxy deviceProxy) throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_class()");
		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_class");

		String	classname = super.get_class_name(deviceProxy);
		if (classname!=null)
			return classname;
		else
			return deviceProxy.getDb_dev().get_class();
	}
	//==========================================================================
	/**
	 *	Returns the class inheritance for the device
	 *	<ul>
	 *		<li> [0] Device Class
	 *		<li> [1] Class from the device class is inherited.
	 *		<li> .....And so on
	 *	</ul>
	 */
	//==========================================================================
	public String[] get_class_inheritance(DeviceProxy deviceProxy) throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_class_inheritance()");
		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_class_inheritance");
		return deviceProxy.getDb_dev().get_class_inheritance();
	}


	//==========================================================================
	/**
	 *	Set an alias for a device name
	 *	@param aliasname alias name.
	 */
	//==========================================================================
	public void put_alias(DeviceProxy deviceProxy, String aliasname)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "put_alias()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "put_alias");
		deviceProxy.getDb_dev().put_alias(aliasname);
	}
	//==========================================================================
	/**
	 *	Get alias for the device
	 *	@return the alias name if found.
	 */
	//==========================================================================
	public String get_alias(DeviceProxy deviceProxy)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_alias()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_alias");
		return deviceProxy.getDb_dev().get_alias();
	}

	//==========================================================================
	/**
	 *	Query the database for the info of this device.
	 *	@return the information in a DeviceInfo.
	 */
	//==========================================================================
	public DeviceInfo get_info(DeviceProxy deviceProxy)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_info()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		//checkIfTango("import_device");
		if (deviceProxy.url.protocol==TANGO)
			return deviceProxy.getDb_dev().get_info();
		else
			return null;
	}

	//==========================================================================
	/**
	 *	Query the database for the export info of this device.
	 *	@return the information in a DbDevImportInfo.
	 */
	//==========================================================================
	public DbDevImportInfo import_device(DeviceProxy deviceProxy)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "import_device()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		//checkIfTango("import_device");
		if (deviceProxy.url.protocol==TANGO)
			return deviceProxy.getDb_dev().import_device();
		else
			return new DbDevImportInfo(dev_inform(deviceProxy));
	}
	//==========================================================================
	/**
	 *	Update the export info for this device in the database.
	 *	@param devinfo	Device information to export.
	 */
	//==========================================================================
	public void export_device(DeviceProxy deviceProxy, DbDevExportInfo devinfo)
				throws DevFailed
	{
		checkIfTango(deviceProxy, "export_device");
		checkIfUseDb(deviceProxy, "export_device()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		deviceProxy.getDb_dev().export_device(devinfo);
	}
	//==========================================================================
	/**
	 *	Unexport the device in database.
	 */
	//==========================================================================
	public void unexport_device(DeviceProxy deviceProxy) throws DevFailed
	{
		checkIfTango(deviceProxy, "unexport_device");
		checkIfUseDb(deviceProxy, "unexport_device()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		deviceProxy.getDb_dev().unexport_device();
	}
	//==========================================================================
	/**
	 *	Add/update this device to the database
	 *	@param devinfo The device name, class and server  specified in object.
	 */
	//==========================================================================
	public void add_device(DeviceProxy deviceProxy, DbDevInfo devinfo) throws DevFailed
	{
		checkIfTango(deviceProxy, "add_device");
		checkIfUseDb(deviceProxy, "add_device()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		deviceProxy.getDb_dev().add_device(devinfo);
	}
	//==========================================================================
	/**
	 *	Delete this device from the database
	 */
	//==========================================================================
	public void delete_device(DeviceProxy deviceProxy) throws DevFailed
	{
		checkIfTango(deviceProxy, "delete_device");
		checkIfUseDb(deviceProxy, "delete_device()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		deviceProxy.getDb_dev().delete_device();
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device
	 *	properties for the pecified object.
	 *	@param wildcard	propertie's wildcard (* matches any charactere).
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public String[] get_property_list(DeviceProxy deviceProxy, String wildcard)
				throws DevFailed
	{
		checkIfTango(deviceProxy, "get_property_list");
		checkIfUseDb(deviceProxy, "get_property_list()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		return deviceProxy.getDb_dev().get_property_list(wildcard);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device properties for this device.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum[] get_property(DeviceProxy deviceProxy, String[] propnames)
				throws DevFailed
	{
		if (deviceProxy.url.protocol==TANGO)
		{
			checkIfUseDb(deviceProxy, "get_property()");

			if (deviceProxy.getDb_dev()==null)
				deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
			return deviceProxy.getDb_dev().get_property(propnames);
		}
		else
			return deviceProxy.taco_device.get_property(propnames);
	}
	//==========================================================================
	/**
	 *	Query the database for a device property for this device.
	 *	@param propname property name.
	 *	@return property in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum get_property(DeviceProxy deviceProxy, String propname)
				throws DevFailed
	{
		if (deviceProxy.url.protocol==TANGO)
		{
			checkIfUseDb(deviceProxy, "get_property()");

			if (deviceProxy.getDb_dev()==null)
				deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
			return deviceProxy.getDb_dev().get_property(propname);
		}
		else
		{
			String[]	propnames = { propname };
			return deviceProxy.taco_device.get_property(propnames)[0];
		}
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device properties for this device.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum[] get_property(DeviceProxy deviceProxy, DbDatum[] properties)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_property");
		return deviceProxy.getDb_dev().get_property(properties);
	}

	//==========================================================================
	/**
	 *	Insert or update a  property for this device
	 *	The property name and its value are specified by the DbDatum
	 *
	 *	@param prop Property name and value
	 */
	//==========================================================================
	public void put_property(DeviceProxy deviceProxy, DbDatum prop)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "put_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "put_property");

		DbDatum[] properties = new DbDatum[1];
		properties[0] = prop;
		put_property(deviceProxy, properties);
	}
	//==========================================================================
	/**
	 *	Insert or update a list of properties for this device
	 *	The property names and their values are specified by the DbDatum array.
	 *
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public void put_property(DeviceProxy deviceProxy, DbDatum[] properties)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "put_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "put_property");
		deviceProxy.getDb_dev().put_property(properties);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this device.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_property(DeviceProxy deviceProxy, String[] propnames)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_property");
		deviceProxy.getDb_dev().delete_property(propnames);
	}
	//==========================================================================
	/**
	 *	Delete a property for this device.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_property(DeviceProxy deviceProxy, String propname)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev( new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_property");
		deviceProxy.getDb_dev().delete_property(propname);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this device.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public void delete_property(DeviceProxy deviceProxy, DbDatum[] properties)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_property");
		deviceProxy.getDb_dev().delete_property(properties);
	}






	//============================================
	//	ATTRIBUTE PROPERTY MANAGEMENT
	//============================================

	//==========================================================================
	/**
	 *	Query the device for attribute config and returns names only.
	 *
	 *	@return attributes list for specified device
	 */
	//==========================================================================
	public String[] get_attribute_list(DeviceProxy deviceProxy) throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		if (deviceProxy.url.protocol==TANGO)
		{
			//	Read All attribute config
			String[]		wildcard = new String[1];

			if (deviceProxy.device_3!=null)
			{
				wildcard[0] = TangoConst.Tango_AllAttr_3;
			}
			else
				wildcard[0] = TangoConst.Tango_AllAttr;
			AttributeInfo[]	ac = get_attribute_info(deviceProxy, wildcard);
			String[]		result = new String[ac.length];
			for (int i=0 ; i<ac.length ; i++)
				result[i] = ac[i].name;
			return result;
		}
		else
		{
			return deviceProxy.taco_device.get_attribute_list();
		}
	}
	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *
	 *	@param attr attribute names and properties (names and values) array.
	 */
	//==========================================================================
	public void put_attribute_property(DeviceProxy deviceProxy, DbAttribute[] attr)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "put_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "put_attribute_property");
		deviceProxy.getDb_dev().put_attribute_property(attr);
	}
	//==========================================================================
	/**
	 *	Insert or update an attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *
	 *	@param attr attribute name and properties (names and values).
	 */
	//==========================================================================
	public void put_attribute_property(DeviceProxy deviceProxy, DbAttribute attr)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "put_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "put_attribute_property");
		deviceProxy.getDb_dev().put_attribute_property(attr);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param attname attribute name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, String attname, String[] propnames)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_attribute_property");
		deviceProxy.getDb_dev().delete_attribute_property(attname, propnames);
	}
	//==========================================================================
	/**
	 *	Delete a property for this object.
	 *	@param attname attribute name.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, String attname, String propname)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_attribute_property");
		deviceProxy.getDb_dev().delete_attribute_property(attname, propname);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param attr DbAttribute objects specifying attributes.
	 */
	//==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, DbAttribute attr)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_attribute_property");
		deviceProxy.getDb_dev().delete_attribute_property(attr);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param attr  DbAttribute objects array specifying attributes.
	 */
	//==========================================================================
	public void delete_attribute_property(DeviceProxy deviceProxy, DbAttribute[] attr)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_attribute_property");
		deviceProxy.getDb_dev().delete_attribute_property(attr);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device attribute
	 *	properties for this device.
	 *	@param attnames list of attribute names.
	 *	@return properties in DbAttribute objects array.
	 */
	//==========================================================================
	public DbAttribute[] get_attribute_property(DeviceProxy deviceProxy, String[] attnames)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_attribute_property");
		return deviceProxy.getDb_dev().get_attribute_property(attnames);
	}
	//==========================================================================
	/**
	 *	Query the database for a device attribute
	 *	property for this device.
	 *	@param attname attribute name.
	 *	@return property in DbAttribute object.
	 */
	//==========================================================================
	public DbAttribute get_attribute_property(DeviceProxy deviceProxy, String attname)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "get_attribute_property()");

		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_attribute_property");
		return deviceProxy.getDb_dev().get_attribute_property(attname);
	}

	//==========================================================================
	/**
	 *	Delete an attribute for this object.
	 *	@param attname attribute name.
	 */
	//==========================================================================
	public void delete_attribute(DeviceProxy deviceProxy, String attname)
				throws DevFailed
	{
		checkIfUseDb(deviceProxy, "delete_attribute()");
		
		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "delete_attribute");
		deviceProxy.getDb_dev().delete_attribute(attname);
	}





	//===========================================================
	//	Attribute Methods
	//===========================================================
	//==========================================================================
	/**
	 *	Get the attributes configuration for the specified device.
	 *
	 *	@param attnames	attribute names to request config.
	 *	@return the attributes configuration.
	 */
	//==========================================================================
	public AttributeInfo[] get_attribute_info(DeviceProxy deviceProxy, String[] attnames) throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		try
		{
			AttributeInfo[]		result;
			AttributeConfig[]	ac   = new AttributeConfig[0];
			AttributeConfig_2[]	ac_2 = null;
			if (deviceProxy.url.protocol==TANGO)
			{
				//	Check IDL version
				if (deviceProxy.device_2!=null)
				{
					ac_2 = deviceProxy.device_2.get_attribute_config_2(attnames);
				}
				else
					ac = deviceProxy.device.get_attribute_config(attnames);
			}
			else
				ac = deviceProxy.taco_device.get_attribute_config(attnames);

			//	Convert AttributeConfig(_2)  object to AttributeInfo object
			int	size = (ac_2!=null)? ac_2.length : ac.length;
			result = new AttributeInfo[size];
			for (int i=0 ; i<size ; i++)
			{
				if (ac_2!=null)
					result[i] =  new AttributeInfo(ac_2[i]);
				else
					result[i] =  new AttributeInfo(ac[i]);
			}
			return result;
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw_dev_failed(deviceProxy, e, "get_attribute_config", true);
			return null;
		}
	}
	//==========================================================================
	/**
	 *	Get the extended attributes configuration for the specified device.
	 *
	 *	@param attnames	attribute names to request config.
	 *	@return the attributes configuration.
	 */
	//==========================================================================
	public AttributeInfoEx[] get_attribute_info_ex(DeviceProxy deviceProxy, String[] attnames) throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		//	try 2 times for reconnection if requested
		AttributeInfoEx[]	result = null;
		boolean done = false;
		int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
		for (int i=0 ; i<retries && !done ; i++)
		{
			try
			{
				AttributeConfig_3[]	ac_3   = null;
				AttributeConfig_2[]	ac_2   = null;
				AttributeConfig[]	ac     = null;
				if (deviceProxy.url.protocol==TANGO)
				{
					//	Check IDL version
					if (deviceProxy.device_3!=null)
					{
						ac_3 = deviceProxy.device_3.get_attribute_config_3(attnames);
					}
					else
					if (deviceProxy.device_2!=null)
					{
						ac_2 = deviceProxy.device_2.get_attribute_config_2(attnames);
					}
					else
						Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
								"Not supported by the IDL version used by device",
								deviceProxy.getFull_class_name()+".get_attribute_info_ex()");
				}
				else
					ac = deviceProxy.taco_device.get_attribute_config(attnames);

				//	Convert AttributeConfig(_3)  object to AttributeInfo object
				int	size;
				if (ac_3!=null)
					size = ac_3.length;
				else
				if (ac_2!=null)
					size = ac_2.length;
				else
					size = ac.length;
				result = new AttributeInfoEx[size];
				for (int n=0 ; n<size ; n++)
					if (ac_3!=null)
						result[n] =  new AttributeInfoEx(ac_3[n]);
					else
					if (ac_2!=null)
						result[n] =  new AttributeInfoEx(ac_2[n]);
					else
					if (ac!=null)	//	Taco
						result[n] =  new AttributeInfoEx(ac[n]);
				done = true;
			}
			catch(DevFailed e)
			{
				throw e;
			}
			catch(Exception e)
			{
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
				{
					deviceProxy.device = null;
					deviceProxy.ior    = null;
					build_connection(deviceProxy);

					if (i==(retries-1))
						throw_dev_failed(deviceProxy, e, "device.get_attribute_config_ex()", true);
				}
				else
					throw_dev_failed(deviceProxy, e, "device.get_attribute_config_ex()", true);
			}
		}
		return result;
	}
	//==========================================================================
	/**
	 *	Get the attributes configuration for the specified device.
	 *
	 *	@param attnames	attribute names to request config.
	 *	@return the attributes configuration.
	 *	@deprecated use get_attribute_info(String[] attnames)
	 */
	//==========================================================================
	public AttributeInfo[] get_attribute_config(DeviceProxy deviceProxy, String[] attnames) throws DevFailed
	{
		return get_attribute_info(deviceProxy, attnames);
	}
	//==========================================================================
	/**
	 *	Get the attribute info for the specified device.
	 *
	 *	@param attname	attribute name to request config.
	 *	@return the attribute info.
	 */
	//==========================================================================
	public AttributeInfo get_attribute_info(DeviceProxy deviceProxy, String attname) throws DevFailed
	{
		String[]		attnames = ApiUtil.toStringArray(attname);
		AttributeInfo[]	ac = get_attribute_info(deviceProxy, attnames);
		return ac[0];
	}
	//==========================================================================
	/**
	 *	Get the attribute info for the specified device.
	 *
	 *	@param attname	attribute name to request config.
	 *	@return the attribute info.
	 */
	//==========================================================================
	public AttributeInfoEx get_attribute_info_ex(DeviceProxy deviceProxy, String attname) throws DevFailed
	{
		String[]		attnames = ApiUtil.toStringArray(attname);
		AttributeInfoEx[]	ac = get_attribute_info_ex(deviceProxy, attnames);
		return ac[0];
	}
	//==========================================================================
	/**
	 *	Get the attribute configuration for the specified device.
	 *
	 *	@param attname	attribute name to request config.
	 *	@return the attribute configuration.
	 *	@deprecated use get_attribute_info(String attname)
	 */
	//==========================================================================
	public AttributeInfo get_attribute_config(DeviceProxy deviceProxy, String attname) throws DevFailed
	{
		return get_attribute_info(deviceProxy, attname);
	}
	//==========================================================================
	/**
	 *	Get all attributes info for the specified device.
	 *
	 *	@return all attributes info.
	 */
	//==========================================================================
	public AttributeInfo[] get_attribute_info(DeviceProxy deviceProxy) throws DevFailed
	{
		
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		
		String[]		attnames = new String[1];
		if (deviceProxy.device_3!=null)
			attnames[0] = TangoConst.Tango_AllAttr_3;
		else
			attnames[0] = TangoConst.Tango_AllAttr;

		return get_attribute_info(deviceProxy,attnames);
	}
	//==========================================================================
	/**
	 *	Get all attributes info for the specified device.
	 *
	 *	@return all attributes info.
	 */
	//==========================================================================
	public AttributeInfoEx[] get_attribute_info_ex(DeviceProxy deviceProxy) throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);		
		
		String[]		attnames = new String[1];
		if (deviceProxy.device_3!=null)
			attnames[0] = TangoConst.Tango_AllAttr_3;
		else
			attnames[0] = TangoConst.Tango_AllAttr;

		return get_attribute_info_ex(deviceProxy,attnames);
	}
	//==========================================================================
	/**
	 *	Get all attributes configuration for the specified device.
	 *
	 *	@return all attributes configuration.
	 *	@deprecated use get_attribute_info()
	 */
	//==========================================================================
	public AttributeInfo[] get_attribute_config(DeviceProxy deviceProxy) throws DevFailed
	{
		return get_attribute_info(deviceProxy);
	}
	//==========================================================================
	/**
	 *	Update the attributes info for the specified device.
	 *
	 *	@param attr the attributes info.
	 */
	//==========================================================================
	public void set_attribute_info(DeviceProxy deviceProxy, AttributeInfo[] attr) throws DevFailed
	{
		checkIfTango(deviceProxy,"set_attribute_config");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		try
		{
			AttributeConfig[]	config = new AttributeConfig[attr.length];
			for (int i=0 ; i<attr.length ; i++)
				config[i] = attr[i].get_attribute_config_obj();
			deviceProxy.device.set_attribute_config(config);
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw_dev_failed(deviceProxy, e, "set_attribute_info", true);
		}
	}
	//==========================================================================
	/**
	 *	Update the attributes extended info for the specified device.
	 *
	 *	@param attr the attributes info.
	 */
	//==========================================================================
	public void set_attribute_info(DeviceProxy deviceProxy, AttributeInfoEx[] attr) throws DevFailed
	{
		checkIfTango(deviceProxy,"set_attribute_config");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		if (deviceProxy.access==TangoConst.ACCESS_READ)
		{
			//	pind the device to throw execption
			//	if failed (for reconnection)
//			ping(deviceProxy);

			Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
					deviceProxy.devname + ".set_attribute_info()  is not authorized !",
						"DeviceProxy.set_attribute_info()");
		}
		try
		{
			if (deviceProxy.device_4!=null)
			{
				AttributeConfig_3[]	config = new AttributeConfig_3[attr.length];
				for (int i=0 ; i<attr.length ; i++)
					config[i] = attr[i].get_attribute_config_obj_3();
				deviceProxy.device_4.set_attribute_config_4(config,
							DevLockManager.getInstance().getClntIdent());
			}
			else
			if (deviceProxy.device_3!=null)
			{
				AttributeConfig_3[]	config = new AttributeConfig_3[attr.length];
				for (int i=0 ; i<attr.length ; i++)
					config[i] = attr[i].get_attribute_config_obj_3();
				deviceProxy.device_3.set_attribute_config_3(config);
			}
			else
			{
				AttributeConfig[]	config = new AttributeConfig[attr.length];
				for (int i=0 ; i<attr.length ; i++)
					config[i] = attr[i].get_attribute_config_obj();
				deviceProxy.device.set_attribute_config(config);
			}
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw_dev_failed(deviceProxy, e, "set_attribute_info", true);
		}
	}
	//==========================================================================
	/**
	 *	Update the attributes configuration for the specified device.
	 *
	 *	@param attr the attributes configuration.
	 *	@deprecated use set_attribute_info(AttributeInfo[] attr)
	 */
	//==========================================================================
	public void set_attribute_config(DeviceProxy deviceProxy, AttributeInfo[] attr) throws DevFailed
	{
		set_attribute_info(deviceProxy, attr);
	}
	//==========================================================================
	/**
	 *	Read the attribute value for the specified device.
	 *
	 *	@param attname	attribute name to request value.
	 *	@return the attribute value.
	 */
	//==========================================================================
	public DeviceAttribute read_attribute(DeviceProxy deviceProxy, String attname)
					throws DevFailed
	{
		String[] names = ApiUtil.toStringArray(attname);
		DeviceAttribute[] attval = read_attribute(deviceProxy, names);
		return attval[0];
	}
	//==========================================================================
	/**
	 *	return directly AttributeValue object without creation of 
	 *	DeviceAttribute object
	 */
	//==========================================================================
	
	public AttributeValue read_attribute_value(DeviceProxy deviceProxy, String attname)
					throws DevFailed
	{
		checkIfTango(deviceProxy, "read_attribute_value");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		AttributeValue[]	attrval;
		if (deviceProxy.getAttnames_array()==null)
			deviceProxy.setAttnames_array(new String[1]);
		deviceProxy.getAttnames_array()[0] = attname;
		try {
 			if (deviceProxy.device_2!=null)
				attrval = deviceProxy.device_2.read_attributes_2(deviceProxy.getAttnames_array(), deviceProxy.dev_src);
			else
				attrval = deviceProxy.device.read_attributes(deviceProxy.getAttnames_array());
			return attrval[0];
		}
		catch (DevFailed e) {
			Except.throw_connection_failed(e, "TangoApi_CANNOT_READ_ATTRIBUTE",
							"Cannot read attribute:   " + attname,
							deviceProxy.getFull_class_name()+".read_attribute()");
			return null;
		}
		catch (Exception e) {
			throw_dev_failed(deviceProxy, e, "device.read_attributes()", false);
			return null;
		}
	}
	//==========================================================================
	/**
	 *	Read the attribute values for the specified device.
	 *
	 *	@param attnames	attribute names to request values.
	 *	@return the attribute values.
	 */
	//==========================================================================
	public DeviceAttribute[] read_attribute(DeviceProxy deviceProxy, String[] attnames)
					throws DevFailed
	{
		DeviceAttribute[]	attr;

		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		//	Read attributes on device server
		AttributeValue[]	attrval = new AttributeValue[0];
		AttributeValue_3[]	attrval_3 = new AttributeValue_3[0];
		AttributeValue_4[]	attrval_4 = new AttributeValue_4[0];
		if (deviceProxy.url.protocol==TANGO)
		{
			//	try 2 times for reconnection if requested
			boolean done = false;
			int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
			for (int i=0 ; i<retries && !done ; i++)
			{
				try {
					if (deviceProxy.device_4!=null)
						attrval_4 = deviceProxy.device_4.read_attributes_4(
								attnames, deviceProxy.dev_src,
								DevLockManager.getInstance().getClntIdent());
					else
					if (deviceProxy.device_3!=null)
						attrval_3 = deviceProxy.device_3.read_attributes_3(
								attnames, deviceProxy.dev_src);
					else
					if (deviceProxy.device_2!=null)
						attrval = deviceProxy.device_2.read_attributes_2(
								attnames, deviceProxy.dev_src);
					else
						attrval = deviceProxy.device.read_attributes(attnames);
					done = true;
				}
				catch (DevFailed e) {
					//Except.print_exception(e);
					StringBuffer	sb = new StringBuffer(attnames[0]);
					for (int j=1 ; j<attnames.length ; j++)
						sb.append(", ").append(attnames[j]);
					Except.throw_connection_failed(e, "TangoApi_CANNOT_READ_ATTRIBUTE",
									"Cannot read attribute(s):   " + sb.toString(),
									deviceProxy.getFull_class_name()+".read_attribute()");
				}
				catch (Exception e)
				{
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
					{
						deviceProxy.device = null;
						deviceProxy.ior    = null;
						build_connection(deviceProxy);

						if (i==(retries-1))
							throw_dev_failed(deviceProxy, e, "device.read_attributes()", false);
					}
					else
					{
						throw_dev_failed(deviceProxy, e, "device.read_attributes()", false);
					}
				}
			}
			//	Build a Device Attribute Object
			//	Depends on Device_impl version
			if (deviceProxy.device_4!=null)
			{
				attr = new DeviceAttribute[attrval_4.length];
				for (int i=0 ; i<attrval_4.length ; i++)
					attr[i] = new DeviceAttribute(attrval_4[i]);
			}
			else
			if (deviceProxy.device_3!=null)
			{
				attr = new DeviceAttribute[attrval_3.length];
				for (int i=0 ; i<attrval_3.length ; i++)
					attr[i] = new DeviceAttribute(attrval_3[i]);
			}
			else
			{
				attr = new DeviceAttribute[attrval.length];
				for (int i=0 ; i<attrval.length ; i++)
					attr[i] = new DeviceAttribute(attrval[i]);
			}
		}
		else
			attr = deviceProxy.taco_device.read_attribute(attnames);
		return attr;
	}


	//==========================================================================
	/**
	 *	Write the attribute value for the specified device.
	 *
	 *	@param	devattr	attribute name and value.
	 */
	//==========================================================================
	public void write_attribute(DeviceProxy deviceProxy, DeviceAttribute devattr)
					throws DevFailed
	{
		checkIfTango(deviceProxy, "write_attribute");
		try
		{
			DeviceAttribute[] array = { devattr };
			write_attribute(deviceProxy, array);
		}
		catch (NamedDevFailedList e) {
			NamedDevFailed	namedDF = e.elementAt(0);
			throw new DevFailed(namedDF.err_stack);
		}
		catch (Exception e)
		{
			 e.printStackTrace();
			throw_dev_failed(deviceProxy, e, "device.write_attributes()", false);
		}
	}
	//==========================================================================
	/**
	 *	Write the attribute values for the specified device.
	 *
	 *	@param	devattr	attribute names and values.
	 */
	//==========================================================================
	public void write_attribute(DeviceProxy deviceProxy, DeviceAttribute[] devattr)
					throws DevFailed
	{
		checkIfTango(deviceProxy, "write_attribute");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		//
		//	Manage Access control
		//
		if (deviceProxy.access==TangoConst.ACCESS_READ)
		{
			//	pind the device to throw execption
			//	if failed (for reconnection)
			ping(deviceProxy);

			Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
					deviceProxy.devname + ".write_attribute()  is not authorized !",
						"DeviceProxy.write_attribute()");
		}
		//	Build an AttributeValue IDL object array
		AttributeValue_4[]	attrval_4 = new AttributeValue_4[0];
		AttributeValue[]	attrval   = new AttributeValue[0];
		if (deviceProxy.device_4!=null)
		{
			attrval_4 = new AttributeValue_4[devattr.length];
			for (int i=0 ; i<devattr.length ; i++)
				attrval_4[i] = devattr[i].getAttributeValueObject_4();
		}
		else
		{
			attrval = new AttributeValue[devattr.length];
			for (int i=0 ; i<devattr.length ; i++)
				attrval[i] = devattr[i].getAttributeValueObject_2();
		}
		boolean done = false;
		int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
		for (int i=0 ; i<retries && !done ; i++)
		{
			//	write attributes on device server
			try {
				if (deviceProxy.device_4!=null)
					deviceProxy.device_4.write_attributes_4(attrval_4,
						DevLockManager.getInstance().getClntIdent());
				else
				if (deviceProxy.device_3!=null)
					deviceProxy.device_3.write_attributes_3(attrval);
				else
					deviceProxy.device.write_attributes(attrval);
				done = true;
			}
			catch (DevFailed e) {
				//Except.print_exception(e);
				throw e;
			}
			catch (MultiDevFailed e) {
				throw new NamedDevFailedList(e,
						name(deviceProxy), "DeviceProxy.write_attribute", "MultiDevFailed");
			}
			catch (Exception e)
			{
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
				{
					deviceProxy.device = null;
					deviceProxy.ior    = null;
					build_connection(deviceProxy);

					if (i==(retries-1))
						throw_dev_failed(deviceProxy, e, "device.write_attributes()", false);
				}
				else
				{
					throw_dev_failed(deviceProxy, e, "device.write_attributes()", false);
				}
			}
		}	//	End of for ( ; ; )
	}


	//==========================================================================
	/**
	 *	Write and then read the attribute values, for the specified device.
	 *
	 *	@param	devattr	attribute names and values.
	 */
	//==========================================================================
	public DeviceAttribute[] write_read_attribute(DeviceProxy deviceProxy, DeviceAttribute[] devattr)
					throws DevFailed
	{
		checkIfTango(deviceProxy, "write_read_attribute");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		//
		//	Manage Access control
		//
		if (deviceProxy.access==TangoConst.ACCESS_READ)
		{
			//	pind the device to throw execption
			//	if failed (for reconnection)
			ping(deviceProxy);

			Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
					deviceProxy.devname + ".write_read_attribute()  is not authorized !",
						"DeviceProxy.write_read_attribute()");
		}
		//	Build an AttributeValue IDL object array
		AttributeValue_4[]	in_attrval_4  = new AttributeValue_4[0];
		AttributeValue_4[]	out_attrval_4 = new AttributeValue_4[0];
		if (deviceProxy.device_4!=null)
		{
			in_attrval_4 = new AttributeValue_4[devattr.length];
			for (int i=0 ; i<devattr.length ; i++)
				in_attrval_4[i] = devattr[i].getAttributeValueObject_4();
		}
		else
		{
			Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
					"Cannot execute write_read_attribute(), " +
					deviceProxy.devname + " is not a device_4Impl or above",
						"DeviceProxy.write_read_attribute()");
		}

		boolean done = false;
		int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
		for (int i=0 ; i<retries && !done ; i++)
		{
			//	write attributes on device server
			try {
				if (deviceProxy.device_4!=null)
					out_attrval_4 = deviceProxy.device_4.write_read_attributes_4(
							in_attrval_4,DevLockManager.getInstance().getClntIdent());
				done = true;
			}
			catch (DevFailed e) {
				//Except.print_exception(e);
				throw e;
			}
			catch (MultiDevFailed e) {
				throw new NamedDevFailedList(e,
						name(deviceProxy), "DeviceProxy.write_read_attribute", "MultiDevFailed");
			}
			catch (Exception e)
			{
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
				{
					deviceProxy.device = null;
					deviceProxy.ior    = null;
					build_connection(deviceProxy);

					if (i==(retries-1))
						throw_dev_failed(deviceProxy, e, "device.write_read_attributes()", false);
				}
				else
				{
					//e.printStackTrace();
					throw_dev_failed(deviceProxy, e, "device.write_read_attributes()", false);
				}
			}
		}	//	End of for ( ; ; )

		//	Build a Device Attribute Object
		//	Depends on Device_impl version
		DeviceAttribute[]	attr = new DeviceAttribute[out_attrval_4.length];
		if (deviceProxy.device_4!=null)
		{
			for (int i=0 ; i<out_attrval_4.length ; i++)
				attr[i] = new DeviceAttribute(out_attrval_4[i]);
		}
		return attr;
	}
	//==========================================================================
	//==========================================================================
	public DeviceProxy get_adm_dev(DeviceProxy deviceProxy) throws DevFailed
	{
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "get_adm_dev");
		return deviceProxy.getAdm_dev();
	}
	//==========================================================================
	/**
	 *	Polling commands.
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Add a command to be polled for the device.
	 *	If already polled, update its polling period.
	 *
	 *	@param	objname	command/attribute name to be polled.
	 *	@param	objtype	command or attribute.
	 *	@param	period	polling period.
	 */
	//==========================================================================
	private void poll_object(DeviceProxy deviceProxy, String objname, String objtype, int period) throws DevFailed
	{
		DevVarLongStringArray	lsa = new DevVarLongStringArray();
		lsa.lvalue = new int[1];
		lsa.svalue = new String[3];
		lsa.svalue[0] = deviceProxy.devname;
		lsa.svalue[1] = objtype;
		lsa.svalue[2] = objname;
		lsa.lvalue[0] = period;

		//	Send command on administration device.
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "poll_object");
		if (DeviceProxy.isCheck_idl() && deviceProxy.getAdm_dev().get_idl_version()<2)	//	Check if IDL support command
			Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
							"Not supported by the IDL version used by device",
							deviceProxy.getFull_class_name()+".poll_object()");

		DeviceData	argin = new DeviceData();
		argin.insert(lsa);
		
		//	Try to add polling period.
		try {
			deviceProxy.getAdm_dev().command_inout("AddObjPolling", argin);
		}
		catch (DevFailed e)
		{
			//	check : if already polled, just update period polling
			for (DevError error : e.errors)
			{
				if (error.reason.equals("API_AlreadyPolled"))
				{
					deviceProxy.getAdm_dev().command_inout("UpdObjPollingPeriod", argin);
					return;
				}
			}
			//	Not this exception then  re-throw it
			Except.throw_communication_failed(e, "TangoApi_CANNOT_POLL_OBJECT",
										"Cannot poll object " + objname,
										deviceProxy.getFull_class_name()+".poll_object()");
		}
	}
	//==========================================================================
	/**
	 *	Add a command to be polled for the device.
	 *	If already polled, update its polling period.
	 *
	 *	@param	cmdname	command name to be polled.
	 *	@param	period	polling period.
	 */
	//==========================================================================
	public void poll_command(DeviceProxy deviceProxy, String cmdname, int period) throws DevFailed
	{
		poll_object(deviceProxy, cmdname, "command", period);
	}
	//==========================================================================
	/**
	 *	Add a attribute to be polled for the device.
	 *	If already polled, update its polling period.
	 *
	 *	@param	attname	attribute name to be polled.
	 *	@param	period	polling period.
	 */
	//==========================================================================
	public void poll_attribute(DeviceProxy deviceProxy, String attname, int period) throws DevFailed
	{
		poll_object(deviceProxy, attname, "attribute", period);
	}
	//==========================================================================
	/**
	 *	Remove object of polled object list
	 *
	 *	@param	objname	object name to be removed of polled object list.
	 *	@param	objtype	object type (command or attribute).
	 */
	//==========================================================================
	private void remove_poll_object(DeviceProxy deviceProxy, String objname, String objtype) throws DevFailed
	{
		//	Send command on administration device.
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "remove_poll_object");
		if (DeviceProxy.isCheck_idl() && deviceProxy.getAdm_dev().get_idl_version()<2)	//	Check if IDL support command
			Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
							"Not supported by the IDL version used by device",
							deviceProxy.getFull_class_name()+".remove_poll_object()");

		DeviceData	argin = new DeviceData();
		String[]	params = new String[3];
		params[0] = deviceProxy.devname;
		params[1] = objtype;
		params[2] = objname;
		argin.insert(params);
		deviceProxy.getAdm_dev().command_inout("RemObjPolling", argin);
	}
	//==========================================================================
	/**
	 *	Remove command of polled object list
	 *
	 *	@param	cmdname	command name to be removed of polled object list.
	 */
	//==========================================================================
	public void stop_poll_command(DeviceProxy deviceProxy, String cmdname) throws DevFailed
	{
		remove_poll_object(deviceProxy, cmdname, "command");
	}
	//==========================================================================
	/**
	 *	Remove attribute of polled object list
	 *
	 *	@param	attname	attribute name to be removed of polled object list.
	 */
	//==========================================================================
	public void stop_poll_attribute(DeviceProxy deviceProxy, String attname) throws DevFailed
	{
		remove_poll_object(deviceProxy, attname, "attribute");
	}
	//==========================================================================
	/**
	 *	Returns the polling status for the device.
	 */
	//==========================================================================
	public String[] polling_status(DeviceProxy deviceProxy) throws DevFailed
	{
		//	Send command on administration device.
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy , "polling_status");
		if (DeviceProxy.isCheck_idl() && deviceProxy.getAdm_dev().get_idl_version()<2)	//	Check if IDL support command
			Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
							"Not supported by the IDL version used by device",
							deviceProxy.getFull_class_name()+".polling_status()");
		DeviceData	argin = new DeviceData();
		argin.insert(deviceProxy.devname);
		DeviceData	argout = deviceProxy.getAdm_dev().command_inout("DevPollStatus", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/**
	 *	Return the history for command polled.
	 *
	 *	@param	cmdname	command name to read polled history.
	 *	@param	nb		nb data to read.
	 */
	//==========================================================================
	public DeviceDataHistory[] command_history(DeviceProxy deviceProxy, String cmdname, int nb) throws DevFailed
	{
		checkIfTango(deviceProxy, "command_history");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		
		if (DeviceProxy.isCheck_idl() && get_idl_version(deviceProxy)<2)	//	Check if IDL support command
			Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
							"Not supported by the IDL version used by device",
							deviceProxy.getFull_class_name()+".command_history()");

		DeviceDataHistory[]	histo = new DeviceDataHistory[0];
		try
		{
			//	Check IDL revision to know kind of history.
			if (deviceProxy.device_4!=null)
			{
				long	t0 = System.currentTimeMillis();
				DevCmdHistory_4		cmd_histo =
					deviceProxy.device_4.command_inout_history_4(cmdname, nb);
				long	t1 = System.currentTimeMillis();
				System.out.println("	transfert --> " + (t1-t0) + " ms");
				histo = ConversionUtil.histo4ToDeviceDataHistoryArray(cmdname, cmd_histo);
			}
			else
			{
				DevCmdHistory[]		cmd_histo =
					deviceProxy.device_2.command_inout_history_2(cmdname, nb);
				histo = new DeviceDataHistory[cmd_histo.length];
				for(int i=0 ; i<cmd_histo.length ; i++)
				histo[i] =  new DeviceDataHistory(cmdname, cmd_histo[i]);
			}
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw_dev_failed(deviceProxy, e,  "command_inout_history()", false);
		}
		return histo;
	}
	//==========================================================================
	/**
	 *	Return the history for attribute polled.
	 *
	 *	@param	attname	attribute name to read polled history.
	 *	@param	nb		nb data to read.
	 */
	//==========================================================================
	public DeviceDataHistory[] attribute_history(DeviceProxy deviceProxy, String attname, int nb) throws DevFailed
	{
		checkIfTango(deviceProxy, "attribute_history");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		
		if (DeviceProxy.isCheck_idl() && get_idl_version(deviceProxy)<2)	//	Check if IDL support command
			Except.throw_non_supported_exception("TangoApi_IDL_NOT_SUPPORTED",
							"Not supported by the IDL version used by device",
							deviceProxy.getFull_class_name()+".attribute_history()");

		DeviceDataHistory[]	histo = new DeviceDataHistory[0];
		try
		{
			//	Check IDL revision to know kind of history.
			if (deviceProxy.device_4!=null)
			{
				long	t0 = System.currentTimeMillis();
				DevAttrHistory_4	att_histo =
					deviceProxy.device_4.read_attribute_history_4(attname, nb);
				long	t1 = System.currentTimeMillis();
				System.out.println("	transfert --> " + (t1-t0) + " ms");
				histo = ConversionUtil.histo4ToDeviceDataHistoryArray(att_histo);
			}
			else
			if (deviceProxy.device_3!=null)
			{
				DevAttrHistory_3[]	att_histo =
					deviceProxy.device_3.read_attribute_history_3(attname, nb);

				histo = new DeviceDataHistory[att_histo.length];
				for(int i=0 ; i<att_histo.length ; i++)
					histo[i] =  new DeviceDataHistory(att_histo[i]);
			}
			else
			if (deviceProxy.device_2!=null)
			{
				DevAttrHistory[]	att_histo =
					deviceProxy.device_2.read_attribute_history_2(attname, nb);
				histo = new DeviceDataHistory[att_histo.length];
				for(int i=0 ; i<att_histo.length ; i++)
					histo[i] =  new DeviceDataHistory(att_histo[i]);
			}
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw_dev_failed(deviceProxy, e,
						"read_attribute_history()", false);
		}
		return histo;
	}

	//==========================================================================
	/**
	 *	Return the full history for command polled.
	 *
	 *	@param	cmdname	command name to read polled history.
	 */
	//==========================================================================
	public DeviceDataHistory[] command_history(DeviceProxy deviceProxy, String cmdname) throws DevFailed
	{
		int		hist_depth = 10;
		DbDatum	data = get_property(deviceProxy, "poll_ring_depth");
		if (!data.is_empty())
			hist_depth = data.extractLong();
		return command_history(deviceProxy, cmdname, hist_depth);
	}
	//==========================================================================
	/**
	 *	Return the full history for attribute polled.
	 *
	 *	@param	attname	attribute name to read polled history.
	 */
	//==========================================================================
	public DeviceDataHistory[] attribute_history(DeviceProxy deviceProxy, String attname) throws DevFailed
	{
		int		hist_depth = 10;
		DbDatum	data = get_property(deviceProxy, "poll_ring_depth");
		if (!data.is_empty())
			hist_depth = data.extractLong();
		return attribute_history(deviceProxy, attname, hist_depth);
	}

	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified attribute.
	 *
	 *	 @param attname	specified attribute name.
	 */
	//==========================================================================
	public int get_attribute_polling_period(DeviceProxy deviceProxy, String attname) throws DevFailed
	{
		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_attribute_polling_period");
		return deviceProxy.getDb_dev().get_attribute_polling_period(attname);
	}
	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified command.
	 *
	 *	 @param cmdname	specified attribute name.
	 */
	//==========================================================================
	public int get_command_polling_period(DeviceProxy deviceProxy, String cmdname) throws DevFailed
	{
		if (deviceProxy.getDb_dev()==null)
			deviceProxy.setDb_dev(new DbDevice(deviceProxy.devname, deviceProxy.url.host, deviceProxy.url.strport));
		checkIfTango(deviceProxy, "get_attribute_polling_period");
		return deviceProxy.getDb_dev().get_attribute_polling_period(cmdname);
	}



	//==========================================================================
	/**
	 * 	Asynchronous calls
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 *	@param	data_in	input argument command.
	 */
	//==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData data_in)
				throws DevFailed
	{
		return command_inout_asynch(deviceProxy, cmdname, data_in, false);
	}
	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 */
	//==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname)
				throws DevFailed
	{
		return command_inout_asynch(deviceProxy, cmdname, new DeviceData(), false);
	}
	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, boolean forget)
				throws DevFailed
	{
		return command_inout_asynch(deviceProxy, cmdname, new DeviceData(), forget);
	}
	//==========================================================================
	/**
	 *	Add and set arguments to request for asynchronous command.
	 */
	//==========================================================================
	private void setRequestArgsForCmd(Request request, 
										String name,
										DeviceData data_in,
										DevSource src, 
										ClntIdent ident)	throws DevFailed
	{
		ORB	orb = ApiUtil.get_orb();

		request.add_in_arg().insert_string(name);
		request.add_in_arg().insert_any(data_in.extractAny());
		//	Add source if any
		if (src!=null)
		{
			Any	any = request.add_in_arg();
			DevSourceHelper.insert(any, src);
		}
		//	Add client ident if any
		if (ident!=null)
		{
			Any	any = request.add_in_arg();
			ClntIdentHelper.insert(any, ident);
		}
		request.set_return_type(orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_any));
		request.exceptions().add(DevFailedHelper.type());

	}
	//==========================================================================
	/**
	 *	Asynchronous command_inout.
	 *
	 *	@param	cmdname	command name.
	 *	@param	data_in	input argument command.
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public int command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData data_in, boolean forget)
				throws DevFailed
	{
		checkIfTango(deviceProxy, "command_inout_asynch");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		//	Manage Access control
		//----------------------------------
		if (deviceProxy.access==TangoConst.ACCESS_READ)
		{
			Database	db = ApiUtil.get_db_obj(deviceProxy.url.host, deviceProxy.url.strport);
			if (!db.isCommandAllowed(deviceProxy.get_class_name(), cmdname))
			{
				//	Check if not allowed or PB with access device
				if (db.access_devfailed!=null)
					throw db.access_devfailed;
				//	pind the device to throw execption
				//	if failed (for reconnection)
				ping(deviceProxy);

				System.out.println(deviceProxy.devname + "." + cmdname + "  -> TangoApi_READ_ONLY_MODE");
				Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
						deviceProxy.devname + ".command_inout_asynch("+
						cmdname+")  is not authorized !",
						"Connection.command_inout_asynch()");
			}
		}
		//	Create the request object
		//----------------------------------
		Request request;
		if (deviceProxy.device_4!=null)
		{
			request = deviceProxy.device_4._request("command_inout_4");
			setRequestArgsForCmd(request, cmdname, data_in,
					get_source(deviceProxy),
					DevLockManager.getInstance().getClntIdent());
		}
		else
		if (deviceProxy.device_2!=null)
		{
			request = deviceProxy.device_2._request("command_inout_2");
			setRequestArgsForCmd(request, cmdname, data_in,
					get_source(deviceProxy), null);
		}
		else
		{
			request = deviceProxy.device._request("command_inout");
			setRequestArgsForCmd(request, cmdname, data_in, null, null);
		}

		//	send it (defered or just one way)
		int	id = 0;
		//	Else tango call
		boolean done = false;
		//	try 2 times for reconnection if requested
		int	retries = (deviceProxy.transparent_reconnection)? 2 : 1;
		for (int i=0 ; i<retries && !done ; i++)
		{
			try
			{
				if (forget)
					request.send_oneway();
				else
				{
					request.send_deferred();
					//	store request reference to read reply later
					String[]	names = new String[1];
					names[0] = cmdname;
					id = ApiUtil.put_async_request(new AsyncCallObject(request, deviceProxy, CMD, names));
				}
				done = true;
			}
			catch(Exception e)
			{
				if (i==0 &&
					(e.toString().indexOf("org.omg.CORBA.TRANSIENT")         >=0 ||
					 e.toString().indexOf("org.omg.CORBA.OBJECT_NOT_EXIST")  >=0 ||
					 e.toString().indexOf("org.omg.CORBA.CORBA.COMM_FAILURE")>=0) )
				{
					deviceProxy.device = null;
					build_connection(deviceProxy);
					if (i==(retries-1))
						throw_dev_failed(deviceProxy, e, cmdname, true);
				}
				else
					throw_dev_failed(deviceProxy, e, cmdname, true);
			}
		}
		return id;
	}
	//==========================================================================
	/**
	 *	Asynchronous command_inout using callback for reply.
	 *
	 *	@param	cmdname	Command name.
	 *	@param	argin	Input argument command.
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void command_inout_asynch(DeviceProxy deviceProxy, String cmdname, DeviceData argin, CallBack cb)
				throws DevFailed
	{
		int id = command_inout_asynch(deviceProxy, cmdname, argin, false);
		ApiUtil.set_async_reply_model(id, CALLBACK);
		ApiUtil.set_async_reply_cb(id, cb);

		//	if push callback, start a thread to do it
		if (ApiUtil.get_asynch_cb_sub_model()==PUSH_CALLBACK)
		{
			AsyncCallObject aco = ApiUtil.get_async_object(id);
			new CallbackThread(aco).start();
		}
	}
	//==========================================================================
	/**
	 *	Asynchronous command_inout using callback for reply.
	 *
	 *	@param	cmdname	Command name.
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void command_inout_asynch(DeviceProxy deviceProxy, String cmdname, CallBack cb)
				throws DevFailed
	{
		command_inout_asynch(deviceProxy, cmdname, new DeviceData(), cb);
	}



	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	id	asynchronous call id (returned by command_inout_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, int id, int timeout)
			throws DevFailed, AsynReplyNotArrived
	{
		return command_inout_reply(deviceProxy ,ApiUtil.get_async_object(id), timeout);
	}
	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	aco	asynchronous call Request instance
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, AsyncCallObject aco, int timeout)
			throws DevFailed, AsynReplyNotArrived
	{
		DeviceData			argout = null;
		int					ms_to_sleep = 50;
		AsynReplyNotArrived	except = null;
		long	t0 = System.currentTimeMillis();
		long	t1 = t0;
		
		//System.out.println("command_inout_reply to= " + timeout + " ms");
		while (((t1-t0)<timeout || timeout==0 ) && argout==null)
		{
			try
			{
				argout = command_inout_reply(deviceProxy ,aco);
			}
			catch (AsynReplyNotArrived na)
			{
				except = na;
				//	Wait a bit before retry
				this.sleep(ms_to_sleep);
				t1 = System.currentTimeMillis();
				//System.out.println(" " + (t1-t0) + " ms");
			}
			catch (DevFailed e)
			{
				throw e;
			}
		}
		//	If reply not arrived throw last exception
		if (argout==null)
			throw except;

		return argout;
	}
	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	id	asynchronous call id (returned by command_inout_asynch).
	 */
	//==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, int id)
			throws DevFailed, AsynReplyNotArrived
	{
		return command_inout_reply(deviceProxy ,ApiUtil.get_async_object(id));
	}
	//==========================================================================
	/**
	 *	Read Asynchronous command_inout reply.
	 *
	 *	@param	aco	asynchronous call Request instance
	 */
	//==========================================================================
	public DeviceData command_inout_reply(DeviceProxy deviceProxy, AsyncCallObject aco)
			throws DevFailed, AsynReplyNotArrived
	{
		DeviceData	data;

		if (deviceProxy.device_4!=null)
			check_asynch_reply(deviceProxy ,aco.request, aco.id, "command_inout_4");
		else
		if (deviceProxy.device_2!=null)
			check_asynch_reply(deviceProxy ,aco.request, aco.id, "command_inout_2");
		else
			check_asynch_reply(deviceProxy ,aco.request, aco.id, "command_inout");

		//	If no exception, extract the any from return value,
		Any	any = aco.request.return_value().extract_any();	

		//	And put it in a DeviceData object
		data = new DeviceData();
		data.insert(any);
		ApiUtil.remove_async_request(aco.id);

		return data;
	}
	//==========================================================================
	/**
	 *	add and set arguments to request for asynchronous attributes.
	 */
	//==========================================================================
	private void setRequestArgsForReadAttr(Request request, 
										String[] names,
										DevSource src, 
										TypeCode return_type)
	{
		Any		any;
		any = request.add_in_arg();
		DevVarStringArrayHelper.insert(any, names);
		
		//	Add source if any
		if (src!=null)
		{
			any = request.add_in_arg();
			DevSourceHelper.insert(any, src);
		}
		request.set_return_type(return_type);
		request.exceptions().add(DevFailedHelper.type());
	}
	//==========================================================================
	/**
	 *	Asynchronous read_attribute.
	 *
	 *	@param	attname	Attribute name.
	 */
	//==========================================================================
	public int read_attribute_asynch(DeviceProxy deviceProxy, String attname)
				throws DevFailed
	{
		String[]	attnames = new String[1];
		attnames[0] = attname;
		return read_attribute_asynch(deviceProxy ,attnames);
	}
	//==========================================================================
	/**
	 *	Asynchronous read_attribute.
	 *
	 *	@param	attnames	Attribute names.
	 */
	//==========================================================================
	public int read_attribute_asynch(DeviceProxy deviceProxy, String[] attnames)
				throws DevFailed
	{
		checkIfTango(deviceProxy ,"read_attributes_asynch");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		
		//	Create the request object
		//----------------------------------
		Request request;
		if (deviceProxy.device_4!=null)
		{
			request = deviceProxy.device_4._request("read_attributes_4");
			setRequestArgsForReadAttr(request, attnames, 
				get_source(deviceProxy), AttributeValueList_4Helper.type());
		}
		else
		if (deviceProxy.device_3!=null)
		{
			request = deviceProxy.device_3._request("read_attributes_3");
			setRequestArgsForReadAttr(request, attnames, 
				get_source(deviceProxy), AttributeValueList_3Helper.type());
		}
		else
		if (deviceProxy.device_2!=null)
		{
			request = deviceProxy.device_2._request("read_attributes_2");
			setRequestArgsForReadAttr(request, attnames,
				get_source(deviceProxy), AttributeValueListHelper.type());
		}
		else
		{
			request = deviceProxy.device._request("read_attributes");
			setRequestArgsForReadAttr(request, attnames, null, AttributeValueListHelper.type());
		}

		//	send it (defered or just one way)
		request.send_deferred();
		//	store request reference to read reply later
		return ApiUtil.put_async_request(
			new AsyncCallObject(request, deviceProxy, ATT_R, attnames));
	}
	//==========================================================================
	/**
	 *	Retrieve the command/attribute arguments to build exception description.
	 */
	//==========================================================================
	public String get_asynch_idl_cmd(DeviceProxy deviceProxy, Request request, String idl_cmd)
	{
		NVList	args = request.arguments();
		StringBuffer	sb = new StringBuffer();
		try
		{
			if (idl_cmd.equals("command_inout"))
				return args.item(0).value().extract_string();	//	get command name
			else
			{
				//	read_attribute, get attribute names
				String[]	s_array = DevVarStringArrayHelper.extract(args.item(0).value());
				for (int i=0 ; i<s_array.length ; i++)
				{
					sb.append(s_array[i]);
					if (i<s_array.length-1)
						sb.append(", ");
				}
			}
		}
		catch(org.omg.CORBA.Bounds e) {}
		return sb.toString();
	}
	//==========================================================================
	/**
	 *	Check Asynchronous call reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public void check_asynch_reply(DeviceProxy deviceProxy, Request request, int id, String idl_cmd)
		throws DevFailed, AsynReplyNotArrived
	{
		//	Check if request object has been found
		if (request==null)
			Except.throw_connection_failed(	"TangoApi_CommandFailed",
									"Asynchronous call id not found",
									deviceProxy.getFull_class_name()+"."+idl_cmd + "_reply()");
		else
		{
			if (!request.operation().equals(idl_cmd))
				Except.throw_connection_failed(	"TangoApi_CommandFailed",
										"Asynchronous call id not for " + idl_cmd,
										deviceProxy.getFull_class_name()+"."+idl_cmd + "_reply()");

			//	Reply arrived ? Throw exception if not yet arrived
			if (!request.poll_response())
			{
				//System.out.println("Response not yet arrived");
				Except.throw_asyn_reply_not_arrived("API_AsynReplyNotArrived",
						"Device " + deviceProxy.devname +
						": reply for asynchronous call (id = "+
						id + ") is not yet arrived",
						deviceProxy.getFull_class_name()+"."+ idl_cmd + "_reply()");
			}
			else
			{
				//	Check if an exception has been thrown
				Exception	except = request.env().exception();
				if (except!=null)
				{
					//	Check if user exception (DevFailed).
					if (except instanceof org.omg.CORBA.UnknownUserException)
					{
						Any	any = ((org.omg.CORBA.UnknownUserException)except).except;
						DevFailed	e = DevFailedHelper.extract(any);
						Except.throw_connection_failed(e,
											"TangoApi_CommandFailed",
											"Asynchronous command failed",
											deviceProxy.getFull_class_name()+"."+idl_cmd + "_reply("+
											get_asynch_idl_cmd(deviceProxy ,request, idl_cmd)+")");
					}
					else
					{
						ApiUtil.remove_async_request(id);
						//	Another exception -> re-throw it as a DevFailed
						throw_dev_failed(deviceProxy, except,
								deviceProxy.getFull_class_name()+"."+idl_cmd + "_reply("+
								get_asynch_idl_cmd(deviceProxy ,request, idl_cmd)+")", false);
					}
				}
				//System.out.println("Reply is arrived !!!");
			}
		}
	}
	//==========================================================================
	/**
	 *	Read Asynchronous read_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public DeviceAttribute[] read_attribute_reply(DeviceProxy deviceProxy, int id, int timeout)
				throws DevFailed, AsynReplyNotArrived
	{
		DeviceAttribute[]	argout = null;
		int					ms_to_sleep = 50;
		AsynReplyNotArrived	except = null;
		long	t0 = System.currentTimeMillis();
		long	t1 = t0;

		while (((t1-t0)<timeout || timeout==0 ) && argout==null)
		{
			try
			{
				argout = read_attribute_reply(deviceProxy ,id);
			}
			catch(AsynReplyNotArrived na)
			{
				except = na;
				//	Wait a bit before retry
				this.sleep(ms_to_sleep);
				t1 = System.currentTimeMillis();
				//System.out.println(" " + (t1-t0) + " ms");
			}
			catch (DevFailed e)
			{
				throw e;
			}
		}
		//	If reply not arrived throw last exception
		if (argout==null)
			throw except;

		return argout;
	}
	//==========================================================================
	/**
	 *	Read Asynchronous read_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public DeviceAttribute[] read_attribute_reply(DeviceProxy deviceProxy, int id)
			throws DevFailed, AsynReplyNotArrived
	{
		DeviceAttribute[]	data;
		AttributeValue[]	attrval;
		AttributeValue_3[]	attrval_3;
		AttributeValue_4[]	attrval_4;
		Request				request = ApiUtil.get_async_request(id);

		//	If no exception, extract the any from return value,
		Any	any = request.return_value();
		if (deviceProxy.device_4!=null)
		{
			check_asynch_reply(deviceProxy, request, id, "read_attributes_4");
			attrval_4 = AttributeValueList_4Helper.extract(any);
			data = new DeviceAttribute[attrval_4.length];
			for (int i=0 ; i<attrval_4.length ; i++)
				data[i] = new DeviceAttribute(attrval_4[i]);
		}
		else
		if (deviceProxy.device_3!=null)
		{
			check_asynch_reply(deviceProxy, request, id, "read_attributes_3");
			attrval_3 = AttributeValueList_3Helper.extract(any);
			data = new DeviceAttribute[attrval_3.length];
			for (int i=0 ; i<attrval_3.length ; i++)
				data[i] = new DeviceAttribute(attrval_3[i]);
		}
		else
		if (deviceProxy.device_2!=null)
		{
			check_asynch_reply(deviceProxy, request, id, "read_attributes_2");
			attrval = AttributeValueListHelper.extract(any);
			data = new DeviceAttribute[attrval.length];
			for (int i=0 ; i<attrval.length ; i++)
				data[i] = new DeviceAttribute(attrval[i]);
		}
		else
		{
			check_asynch_reply(deviceProxy, request, id, "read_attributes");
			attrval = AttributeValueListHelper.extract(any);
			data = new DeviceAttribute[attrval.length];
			for (int i=0 ; i<attrval.length ; i++)
				data[i] = new DeviceAttribute(attrval[i]);
		}
		ApiUtil.remove_async_request(id);

		return data;
	}
	//==========================================================================
	/**
	 *	Asynchronous read_attribute using callback for reply.
	 *
	 *	@param	attname	attribute name.
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void read_attribute_asynch(DeviceProxy deviceProxy, String attname, CallBack cb)
				throws DevFailed
	{
		String[]	attnames = new String[1];
		attnames[0] = attname;
		read_attribute_asynch(deviceProxy ,attnames, cb);
	}
	//==========================================================================
	/**
	 *	Asynchronous read_attribute using callback for reply.
	 *
	 *	@param	attnames	attribute names.
	 *	@param	cb			a CallBack object instance.
	 */
	//==========================================================================
	public void read_attribute_asynch(DeviceProxy deviceProxy, String[] attnames, CallBack cb)
				throws DevFailed
	{
		int id = read_attribute_asynch(deviceProxy, attnames);
		ApiUtil.set_async_reply_model(id, CALLBACK);
		ApiUtil.set_async_reply_cb(id, cb);

		//	if push callback, start a thread to do it
		if (ApiUtil.get_asynch_cb_sub_model()==PUSH_CALLBACK)
		{
			AsyncCallObject aco = ApiUtil.get_async_object(id);
			new CallbackThread(aco).start();
		}
	}

	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attr	Attribute value (name, writing value...)
	 */
	//==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr)
				throws DevFailed
	{
		return write_attribute_asynch(deviceProxy ,attr, false);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attr	Attribute value (name, writing value...)
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr, boolean forget)
				throws DevFailed
	{
		DeviceAttribute[]	attribs = new DeviceAttribute[1];
		attribs[0] = attr;
		return write_attribute_asynch(deviceProxy ,attribs);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attribs	Attribute values (name, writing value...)
	 */
	//==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs)
				throws DevFailed
	{
		return write_attribute_asynch(deviceProxy ,attribs, false);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute.
	 *
	 *	@param	attribs	Attribute values (name, writing value...)
	 *	@param	forget	forget the response if true
	 */
	//==========================================================================
	public int write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs, boolean forget)
				throws DevFailed
	{
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);

		//
		//	Manage Access control
		//
		if (deviceProxy.access==TangoConst.ACCESS_READ)
		{
			//	pind the device to throw execption
			//	if failed (for reconnection)
			ping(deviceProxy);

			Except.throw_connection_failed("TangoApi_READ_ONLY_MODE",
					deviceProxy.devname + ".write_attribute_asynch()  is not authorized !",
						"DeviceProxy.write_attribute_asynch()");
		}

		//	Build idl argin object
		Request 	request;
		String[]	attnames = new String[attribs.length];

		Any		any;
		if (deviceProxy.device_4!=null)
		{
			AttributeValue_4[]	attrval_4 = new AttributeValue_4[attribs.length];
			for (int i=0 ; i<attribs.length ; i++)
			{
				attrval_4[i] = attribs[i].getAttributeValueObject_4();
				attrval_4[i].err_list = new DevError[0];
				attnames[i] = attrval_4[i].name;
			}
			request = deviceProxy.device_4._request("write_attributes_4");

			any = request.add_in_arg();
			AttributeValueList_4Helper.insert(any, attrval_4);
			any = request.add_in_arg();
			ClntIdentHelper.insert(any, DevLockManager.getInstance().getClntIdent());
		}
		else
		{
			AttributeValue[] attrval  = new AttributeValue[attribs.length];
			for (int i=0 ; i<attribs.length ; i++)
			{
				attrval[i] = attribs[i].getAttributeValueObject_2();
				attnames[i] = attrval[i].name;
			}

			//	Create the request object
			if (deviceProxy.device_3!=null)
				request = deviceProxy.device_3._request("write_attributes_3");
			else
			if (deviceProxy.device_2!=null)
				request = deviceProxy.device_2._request("write_attributes");
			else
				request = deviceProxy.device._request("write_attributes");

			any = request.add_in_arg();
			AttributeValueListHelper.insert(any, attrval);
		}
		request.exceptions().add(DevFailedHelper.type());

		//	send it (defered or just one way)
		int	id = 0;
		if (forget)
			request.send_oneway();
		else
		{
			request.send_deferred();
			//	store request reference to read reply later
			id = ApiUtil.put_async_request(new AsyncCallObject(request, deviceProxy, ATT_W, attnames));
		}

		return id;
	}
	//==========================================================================
	/**
	 *	check for Asynchronous write_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by read_attribute_asynch).
	 */
	//==========================================================================
	public void write_attribute_reply(DeviceProxy deviceProxy, int id)
			throws DevFailed, AsynReplyNotArrived
	{
		Request				request = ApiUtil.get_async_request(id);
		if (deviceProxy.device_4!=null)
			check_asynch_reply(deviceProxy, request, id, "write_attributes_4");
		else
		if (deviceProxy.device_3!=null)
			check_asynch_reply(deviceProxy, request, id, "write_attributes_3");
		else
		if (deviceProxy.device_2!=null)
			check_asynch_reply(deviceProxy, request, id, "write_attributes_2");
		else
			check_asynch_reply(deviceProxy, request, id, "write_attributes");
	}
	//==========================================================================
	/**
	 *	check for Asynchronous write_attribute reply.
	 *
	 *	@param	id	asynchronous call id (returned by write_attribute_asynch).
	 *	@param	timeout	number of millisonds to wait reply before throw an excption.
	 */
	//==========================================================================
	public void write_attribute_reply(DeviceProxy deviceProxy, int id, int timeout)
			throws DevFailed, AsynReplyNotArrived
	{
		int					ms_to_sleep = 50;
		AsynReplyNotArrived	except = null;
		long	t0 = System.currentTimeMillis();
		long	t1 = t0;
		boolean	done = false;
		while (((t1-t0)<timeout || timeout==0 ) && !done)
		{
			try
			{
				write_attribute_reply(deviceProxy ,id);
				done = true;
			}
			catch(AsynReplyNotArrived na)
			{
				except = na;
				//	Wait a bit before retry
				this.sleep(ms_to_sleep);
				t1 = System.currentTimeMillis();
				//System.out.println(" " + (t1-t0) + " ms");
			}
			catch(DevFailed e)
			{
				throw e;
			}
		}
		//	If reply not arrived throw last exception
		if (!done)
			throw except;
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute using callback for reply.
	 *
	 *	@param	attr	Attribute values (name, writing value...)
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute attr, CallBack cb)
				throws DevFailed
	{
		DeviceAttribute[]	attribs = new DeviceAttribute[1];
		attribs[0] = attr;
		write_attribute_asynch(deviceProxy ,attribs, cb);
	}
	//==========================================================================
	/**
	 *	Asynchronous write_attribute using callback for reply.
	 *
	 *	@param	attribs	Attribute values (name, writing value...)
	 *	@param	cb		a CallBack object instance.
	 */
	//==========================================================================
	public void write_attribute_asynch(DeviceProxy deviceProxy, DeviceAttribute[] attribs, CallBack cb)
				throws DevFailed
	{
		int id = write_attribute_asynch(deviceProxy ,attribs);
		ApiUtil.set_async_reply_model(id, CALLBACK);
		ApiUtil.set_async_reply_cb(id, cb);

		//	if push callback, start a thread to do it
		if (ApiUtil.get_asynch_cb_sub_model()==PUSH_CALLBACK)
		{
			AsyncCallObject aco = ApiUtil.get_async_object(id);
			new CallbackThread(aco).start();
		}
	}
	//==========================================================================
	/**
	 *	return the still pending asynchronous call for a reply model.
	 *
	 *	@param	reply_model	ApiDefs.ALL_ASYNCH, POLLING or CALLBACK.
	 */
	//==========================================================================
	public int pending_asynch_call(DeviceProxy deviceProxy, int reply_model)
	{
			return ApiUtil.pending_asynch_call(deviceProxy, reply_model);
	}
	//==========================================================================
	/**
	 *	Fire callback methods for all 
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public void get_asynch_replies(DeviceProxy deviceProxy)
	{	
			ApiUtil.get_asynch_replies(deviceProxy);
		
	}
	//==========================================================================
	/**
	 *	Fire callback methods for all 
	 *	asynchronous requests(cmd and attr) with already arrived replies.
	 */
	//==========================================================================
	public void get_asynch_replies(DeviceProxy deviceProxy, int timeout)
	{	
			ApiUtil.get_asynch_replies(deviceProxy, timeout);
	}



	//==========================================================================
	/**
	 * 	Logging related methods
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Adds a new logging target to the device.
	 *
	 *	@param target The target for logging (e.g. file::/tmp/logging_device).
	 */
	//==========================================================================
	public void add_logging_target(DeviceProxy deviceProxy, String target)
				throws DevFailed
	{
		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy ,"add_logging_target");

		//	Prepeare data
		
		String[]		str = new String[2];
		str[0] = get_name(deviceProxy);
		str[1] = target;
		DeviceData	argin = new DeviceData();
		argin.insert(str);
		//	And send command
		deviceProxy.getAdm_dev().command_inout("AddLoggingTarget", argin);
	}
	//==========================================================================
	/**
	 *	Removes a new logging target to the device.
	 */
	//==========================================================================
	public void remove_logging_target(DeviceProxy deviceProxy, String target_type, String target_name)
				throws DevFailed
	{
		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "remove_logging_target");

		//	Prepeare data
		String[]		target = new String[2];
		target[0] = get_name(deviceProxy);
		target[1] = target_type + "::" + target_name;
		DeviceData	argin = new DeviceData();
		argin.insert(target);
		//	And send command
		deviceProxy.getAdm_dev().command_inout("RemoveLoggingTarget", argin);
	}
	//==========================================================================
	/**
	 *	get logging target from the device.
	 */
	//==========================================================================
	public String[] get_logging_target(DeviceProxy deviceProxy)
				throws DevFailed
	{
		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "get_logging_target");

		//	Prepeare data
		DeviceData	argin = new DeviceData();
		argin.insert(get_name(deviceProxy));
		//	And send command
		DeviceData	argout = deviceProxy.getAdm_dev().command_inout("GetLoggingTarget", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/**
	 *	get logging level from the device.
	 *	@return device's logging level:
	 *		(ApiDefs.LOGGING_OFF, ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
	 *		ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
	 */
	//==========================================================================
	public int get_logging_level(DeviceProxy deviceProxy)
				throws DevFailed
	{
		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "get_logging_level");
		
		//	Prepeare data
		String[]	target = new String[1];
		target[0] = get_name(deviceProxy);
		DeviceData	argin = new DeviceData();
		argin.insert(target);
		//	And send command
		DeviceData	argout = deviceProxy.getAdm_dev().command_inout("GetLoggingLevel", argin);
		DevVarLongStringArray	lsa = argout.extractLongStringArray();		
		return lsa.lvalue[0];
	}
	//==========================================================================
	/**
	 *	Set logging level from the device.
	 *	@param level device's logging level:
	 *		(ApiDefs.LOGGING_OFF, ApiDefs.LOGGING_FATAL, ApiDefs.LOGGING_ERROR,
	 *		ApiDefs.LOGGING_INFO, ApiDefs.LOGGING_DEBUG)
	 */
	//==========================================================================
	public void set_logging_level(DeviceProxy deviceProxy, int level)
				throws DevFailed
	{
		//	Get connection on administration device
		if (deviceProxy.getAdm_dev()==null)
			import_admin_device(deviceProxy, "set_logging_level");
		
		//	Prepeare data
		DevVarLongStringArray	lsa = new DevVarLongStringArray();
		lsa.lvalue = new int[1];
		lsa.svalue = new String[1];
		lsa.lvalue[0] = level;
		lsa.svalue[0] = get_name(deviceProxy);
		DeviceData	argin = new DeviceData();
		argin.insert(lsa);
		//	And send command
		deviceProxy.getAdm_dev().command_inout("SetLoggingLevel", argin);
	}


	// ==========================================================================
	//	Locking Device 4 commands
	// ==========================================================================
	
	// ==========================================================================
	/**
	 *	Lock the device
	 *
	 *	@param	validity	Lock validity (in seconds)
	 */
	// ==========================================================================
	public void lock(DeviceProxy deviceProxy, int validity)
				throws DevFailed
	{
		DevLockManager.getInstance().lock(deviceProxy, validity);
	}
	// ==========================================================================
	/**
	 *	Unlock the device
	 *
	 *	@return the device lock counter
	 */
	// ==========================================================================
	public int unlock(DeviceProxy deviceProxy)
				throws DevFailed
	{
		return DevLockManager.getInstance().unlock(deviceProxy);
	}
	// ==========================================================================
	/**
	 *	Returns true if the device is locked
	 */
	// ==========================================================================
	public boolean isLocked(DeviceProxy deviceProxy)
			throws DevFailed
	{
		return DevLockManager.getInstance().isLocked(deviceProxy);
	}
	// ==========================================================================
	/**
	 *	Returns true if the device is locked by this process
	 */
	// ==========================================================================
	public boolean isLockedByMe(DeviceProxy deviceProxy)
			throws DevFailed
	{
		return DevLockManager.getInstance().isLockedByMe(deviceProxy);
	}
	// ==========================================================================
	/**
	 *	Returns the device lock status
	 */
	// ==========================================================================
	public String getLockerStatus(DeviceProxy deviceProxy)
				throws DevFailed
	{
		return DevLockManager.getInstance().getLockerStatus(deviceProxy);
	}
	// ==========================================================================
	/**
	 *	Returns the device lock info
	 */
	// ==========================================================================
	public LockerInfo getLockerInfo(DeviceProxy deviceProxy) throws DevFailed
	{
		return DevLockManager.getInstance().getLockerInfo(deviceProxy);
	}
	// ==========================================================================
	// ==========================================================================


	//==========================================================================
	/**
	 * 	TACO commands
	 */
	//==========================================================================
	//==========================================================================
	/**
	 *	Returns  TACO device information.
	 *
	 *	@return  TACO device information as String array.
	 *	<li> Device name.
	 *	<li> Class name
	 *	<li> Device type
	 *	<li> Device server name
	 *	<li> Host name
	 */
	//==========================================================================
	public String[] dev_inform(DeviceProxy deviceProxy) throws DevFailed
	{
		checkIfTaco(deviceProxy, "dev_inform");
		if (deviceProxy.taco_device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		return deviceProxy.taco_device.dev_inform();
	}

	//==========================================================================
	/**
	 *	Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
	 *	@param	mode RPC protocol mode to be seted 
	 *		(TangoApi.TacoDevice.<b>D_TCP</b> or TangoApi.TacoDevice.<b>D_UDP</b>).
	 */
	//==========================================================================
	public void set_rpc_protocol(DeviceProxy deviceProxy, int mode)	throws DevFailed
	{
		checkIfTaco(deviceProxy, "dev_rpc_protocol");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		deviceProxy.taco_device.set_rpc_protocol(mode);
	}

	//==========================================================================
	/**
	 *	@return	mode RPC protocol mode  used
	 *	@return	mode RPC protocol mode  used
	 *		(TangoApi.TacoDevice.<b>D_TCP</b> or TangoApi.TacoDevice.<b>D_UDP</b>).
	 */
	//==========================================================================
	public int get_rpc_protocol(DeviceProxy deviceProxy)	throws DevFailed
	{
		checkIfTaco(deviceProxy, "get_rpc_protocol");
		if (deviceProxy.device==null && deviceProxy.devname!=null)
			build_connection(deviceProxy);
		return deviceProxy.taco_device.get_rpc_protocol();
	}
	//===============================================================
	//===============================================================
	private synchronized void sleep(long ms)
	{
		try
		{
			wait(ms);
		}
		catch(InterruptedException e) { System.out.println(e);}
	}
	//==========================================================================
	/**
	 *	Subscribe to an event.
	 *
	 *	@param	attr_name	attribute name.
	 *	@param	event		event name.
	 *	@param  callback	event callback.
	 *	@param  stateless	If true, do not throw exception if connection failed.
	 */
	//==========================================================================
	public int subscribe_event(DeviceProxy deviceProxy, String attr_name, int event, CallBack callback, String[] filters, boolean stateless)
                   throws DevFailed
	{
		int	id=0;
        if (ApiUtil.get_event_consumer() == null)
            ApiUtil.create_event_consumer();
		
		try
		{
	        EventConsumer event_consumer = ApiUtil.get_event_consumer();
			id = event_consumer.subscribe_event(deviceProxy, attr_name.toLowerCase(), event, callback, filters, stateless);
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
		e.printStackTrace();
			Except.throw_communication_failed(e.toString(),
								"Subsrcibe event on " + name(deviceProxy) + "/" + attr_name + " Failed !",
								"DeviceProxy.subscribe_event()");
		}
		return id;
	}
	//==========================================================================
	/**
	 *	Subscribe to event to be stored in an event queue.
	 *
	 *	@param	attr_name	attribute name.
	 *	@param	event		event name.
	 *	@param  max_size	event queue maximum size.
	 *	@param  stateless	If true, do not throw exception if connection failed.
	 */
	//==========================================================================
	public int subscribe_event(DeviceProxy deviceProxy, String attr_name, int event, int max_size, String[] filters, boolean stateless)
                   throws DevFailed
	{
		int	id=0;
        if (ApiUtil.get_event_consumer() == null)
            ApiUtil.create_event_consumer();
		
		try
		{
	        EventConsumer event_consumer = ApiUtil.get_event_consumer();
			id = event_consumer.subscribe_event(deviceProxy, attr_name.toLowerCase(), event, max_size, filters, stateless);
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			Except.throw_communication_failed(e.toString(),
								"Subsrcibe event on " + name(deviceProxy) + "/" + attr_name + " Failed !",
								"DeviceProxy.subscribe_event()");
		}
		return id;
	}
	//==========================================================================
	/**
	 *	Unsubscribe to an event.
	 *
	 *	@param	event_id	event identifier.
	 */
	//==========================================================================
	public void unsubscribe_event(DeviceProxy deviceProxy, int event_id)
                   throws DevFailed
    {
        if (ApiUtil.get_event_consumer() == null)
            ApiUtil.create_event_consumer();
			
		try
		{
	        EventConsumer event_consumer = ApiUtil.get_event_consumer();
			event_consumer.unsubscribe_event(event_id);
		}
		catch(DevFailed e)
		{
			throw e;
		}
		catch(Exception e)
		{
			Except.throw_communication_failed(e.toString(),
								"Unsubsrcibe event on event ID " + event_id + " Failed !",
								"DeviceProxy.unsubscribe_event()");
		}
    }
	//==========================================================================
	//==========================================================================




	
	//==========================================================================
	/**
	 *	Just a main method to check API methods.
	 */
	//==========================================================================
	public void main (String args[])
	{
		String	devname = null;
		String	cmdname = null;
		try
		{
			cmdname = args[0];
			devname = args[1];
		}
		catch(Exception e)
		{
			if (cmdname==null)
			{
				System.out.println("Usage :");
				System.out.println("fr.esrf.TangoApi.DeviceProxy  cmdname devname");
				System.out.println("	- cmdname : command name (ping, state, status, unexport...)");
				System.out.println("	- devname : device name to send command.");
			}
			else
				System.out.println("Device name ?");
			System.exit(0);
		}
		try
		{
			//	Check if wildcard
			String[]		devnames;
			DeviceProxy[]	dev;
			if (devname.indexOf("*")<0)
			{
				devnames = new String[1];
				devnames[0] = devname;
			}
			else
				devnames = ApiUtil.get_db_obj().getDevices(devname);

			//	Create DeviceProxy Objects
			dev = new DeviceProxy[devnames.length];
			for (int i=0 ; i<devnames.length ; i++)
				dev[i] = new DeviceProxy(devnames[i]);
			
			if (cmdname.equals("ping"))
			{
				//noinspection InfiniteLoopStatement
				while (true)
				{
					for (int i=0 ; i<dev.length ; i++)
					{
						try {
							long t = dev[i].ping();
							System.out.println(devnames[i] + " is alive  (" + t/1000 + " ms)");
						}
						catch(DevFailed e) {
							System.out.println(devnames[i] +
											"  " + e.errors[0].desc);
						}
					}
					if (dev.length>1)
						System.out.println();
					try { Thread.sleep(1000); }
					catch(InterruptedException e) {}
				}
			}
			else
			if (cmdname.equals("status"))
				for (int i=0 ; i<dev.length ; i++)
				{
					try {
						System.out.println(devnames[i] + " - " +
								dev[i].status());
					}
					catch(DevFailed e) {
						System.out.println(devnames[i] +
										"  " + e.errors[0].desc);
					}
				}
			else
			if (cmdname.equals("state"))
			{
				for (int i=0 ; i<dev.length ; i++)
				{
					try {
						System.out.println(devnames[i] + " is " +
									ApiUtil.stateName(dev[i].state()));
						/*
						DeviceAttribute	da = dev[i].read_attribute("State");
						DevState	st = da.extractDevStateArray()[0];
						System.out.println(devnames[i] + " is " +
									ApiUtil.stateName(st));
						*/
					}
					catch(DevFailed e) {
						System.out.println(devnames[i] +
										"  " + e.errors[0].desc);
					}
				}
			}
			else
			if (cmdname.equals("unexport"))
			{
				for (int i=0 ; i<dev.length ; i++)
				{
					try {
						dev[i].unexport_device();
						System.out.println(devnames[i] + " unexported !");
					}
					catch(DevFailed e) {
						System.out.println(devnames[i] +
										"  " + e.errors[0].desc);
					}
				}
			}
			else
				System.out.println(cmdname + " ?   Unknow command !");
		}
		catch(DevFailed e)
		{
			Except.print_exception(e);
			//e.printStackTrace();
		}
	}
}
