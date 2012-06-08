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
// Revision 1.12  2009/09/11 12:10:30  pascal_verdier
// tangorc environment file is managed.
//
// Revision 1.11  2009/05/07 08:47:14  pascal_verdier
// Pb with name() CORBAT attribute in checkAccessControl() fixed.
//
// Revision 1.10  2009/04/27 14:37:12  pascal_verdier
// Pb on Access control for astor fixed.
//
// Revision 1.9  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.8  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.7  2008/12/03 15:44:26  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.6  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2008/10/10 08:31:57  pascal_verdier
// Security check has been done.
//
// Revision 1.4  2008/06/03 13:31:11  pascal_verdier
// Check class number in get_server_class_list method added.
//
// Revision 1.3  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.2  2007/10/08 06:40:50  pascal_verdier
// Class Attribue properties are put as String array.
//
// Revision 1.1  2007/08/23 09:41:20  ounsy
// Add default impl for tangorb
//
// Revision 3.26  2007/04/04 14:12:06  pascal_verdier
// Method get_device_attribute_list() added.
//
// Revision 3.25  2007/03/13 12:16:32  pascal_verdier
// Another bug in getServices fixed.
//
// Revision 3.24  2007/03/12 14:25:57  pascal_verdier
// Bug in getDevices fixed.
//
// Revision 3.23  2007/03/12 13:43:05  pascal_verdier
// Bug in getServices fixed.
//
// Revision 3.22  2006/11/27 16:12:14  pascal_verdier
// Methods register_service and unregister_service added.
//
// Revision 3.21  2006/11/13 08:21:47  pascal_verdier
// Constant added.
//
// Revision 3.20  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.19  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.18  2006/04/10 12:10:31  jlpons
// Added database history commands
//
// Revision 3.17  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.16  2005/08/30 07:33:44  pascal_verdier
// Redundancy database connection between 2 TANGO_HOST added.
//
// Revision 3.15  2005/06/13 09:05:18  pascal_verdier
// Minor bugs fixed.
//
// Revision 3.14  2005/06/02 14:08:28  pascal_verdier
// *** empty log message ***
//
// Revision 3.13  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.12  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.11  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.10  2004/10/11 12:25:42  pascal_verdier
// Multi TANGO_HOST bug fixed.
//
// Revision 3.9  2004/09/23 14:00:15  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.8  2004/06/29 04:02:57  pascal_verdier
// Comments used by javadoc added.
//
// Revision 3.7  2004/06/21 12:23:35  pascal_verdier
// get_device_exported_for_class(String classname) method added
// and get_alias(String devname) bug fixed.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.2  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.1  2003/07/22 14:15:35  pascal_verdier
// DeviceData are now in-methods objects.
// Minor change for TACO-TANGO common database.
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
 

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.events.DbEventImportInfo;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.TangoDs.Except;

import java.util.StringTokenizer;
import java.util.Vector;

/** 
 *	Class Description:
 *	This class is the main class for TANGO database API.
 *	The TANGO database is implemented as a TANGO device server.
 *	To access it, the user has the CORBA interface command_inout().
 *	This expects and returns all parameters as ascii strings thereby making
 *	the database laborious to use for retreing device properties and information.
 *	In order to simplify this access, a high-level API has been implemented
 *	which hides the low-level formatting necessary to convert the
 *	command_inout() return values into binary values and all CORBA aspects
 *	of the TANGO.
 *	All data types are native java types e.g. simple types an arrays.
 *
 * @author  verdier
 * @version  $Revision$
 */

public class DatabaseDAODefaultImpl extends ConnectionDAODefaultImpl implements IDatabaseDAO
{

	//===================================================================
	/**
	 *	Database access constructor.
	 */
	//===================================================================
	public DatabaseDAODefaultImpl()
	{
		super();
	}

	
	//===================================================================
	/**
	 *	Database access init method.
	 *
	 *	@throws DevFailed in case of environment not corectly set.
	 */
	//===================================================================
	public void init(Database database) throws DevFailed
	{
		super.init(database);
	}
	
	//===================================================================
	/**
	 *	Database access constructor.
	 *
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 *	@throws DevFailed in case of host or port not available
	 */
	//===================================================================
	public void init(Database database, String host, String port) throws DevFailed
	{
		super.init(database, host, port);
	}
	//==========================================================================
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#toString()
	 */
	public String toString(Database database)
	{
		return database.url.host + ":" + database.url.port;
	}
	//==========================================================================
	/**
	 *	Convert a String array to a sting.
	 */
	//==========================================================================
	private String stringArray2String(String[] array)
	{
		StringBuffer	sb = new StringBuffer("");
		for(int i=0 ; i<array.length ; i++)
		{
			sb.append(array[i]);
			if (i<array.length-1)
				sb.append("\n");
		}
		return sb.toString();
	}

	//==========================================================================
	//==========================================================================
	private void checkAccess(Database database)
	{
		//
		//	Manage Access control
		//
		if (database.check_access && database.isAccess_checked()==false)
		{
			database.access = checkAccessControl(database, database.devname);
			database.setAccess_checked(true);
			//	Initialize value.
			ApiUtil.getReconnectionDelay();
			//System.out.println(this + "." + database.devname + " -> " +
			//		((database.access==TangoConst.ACCESS_READ)? "Read" : "Write"));
		}
	}





	//**************************************
	//       MISCELANEOUS MANAGEMENT
	//**************************************	


	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_info()
	 */
	//==========================================================================
	public String get_info(Database database) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argout = command_inout(database, "DbInfo");
		String[] info = argout.extractStringArray();

		//	format result as string
		return stringArray2String(info);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_host_list()
	 */
	//==========================================================================
	public String[] get_host_list(Database database) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert("*");
		DeviceData	argout = command_inout(database, "DbGetHostList", argin);
		return argout.extractStringArray();
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_host_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_host_list(Database database, String wildcard) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetHostList", argin);
		return argout.extractStringArray();
	}






	//**************************************
	//       SERVERS MANAGEMENT
	//**************************************	

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_class_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_server_class_list(Database database, String servname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(servname);
		DeviceData	argout = command_inout(database, "DbGetDeviceServerClassList", argin);
		String[]	list = argout.extractStringArray();
		//	Extract DServer class
		int 		nb_classes;
		if (list.length==0)
			nb_classes = 0 ;
		else
			nb_classes = list.length-1;
		String[]	classes = new String[nb_classes];
		for (int i=0, j=0 ; i<list.length && j<nb_classes ; i++)
			if (list[i].equals("DServer")==false)
				classes[j++] = list[i];
		return classes;
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_name_list()
	 */
	//==========================================================================
	public String[] get_server_name_list(Database database) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert("*");
		DeviceData	argout = command_inout(database, "DbGetServerNameList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_instance_name_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_instance_name_list(Database database, String servname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(servname);
		DeviceData	argout = command_inout(database, "DbGetInstanceNameList", argin);		
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_list()
	 */
	//==========================================================================
	public String[] get_server_list(Database database) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert("*");
		DeviceData	argout = command_inout(database, "DbGetServerList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_server_list(Database database, String wildcard) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetServerList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_host_server_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_host_server_list(Database database, String hostname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(hostname);
		DeviceData	argout = command_inout(database, "DbGetHostServerList", argin);
		return argout.extractStringArray();
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_info(java.lang.String)
	 */
	//==========================================================================
	public DbServInfo get_server_info(Database database, String servname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(servname);
		DeviceData	argout = command_inout(database, "DbGetServerInfo", argin);
		String[]	info = argout.extractStringArray();
		return new DbServInfo(info);
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_server_info(fr.esrf.TangoApi.DbServInfo)
	 */
	//==========================================================================
	public void put_server_info(Database database, DbServInfo info) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[] array;
		array = new String[4];
		array[0] = info.name;
		array[1] = info.host;
		array[2] = (info.controlled) ? "1" : "0";
		array[3] = Integer.toString(info.startup_level);

		/*System.out.println("DbPutServerInfo:");
		for (int i=0 ; i<array.length ; i++)
			System.out.println("	"+array[i]);
		*/

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbPutServerInfo", argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_server_info(java.lang.String)
	 */
	//==========================================================================
	public void delete_server_info(Database database, String servname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(servname);
		command_inout(database, "DbDeleteServerInfo", argin);
	}

	//**************************************
	//       DEVICES MANAGEMENT
	//**************************************	

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#add_device(fr.esrf.TangoApi.DbDevInfo)
	 */
	//==========================================================================
	public void add_device(Database database, DbDevInfo devinfo) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devinfo.toStringArray());
		command_inout(database, "DbAddDevice", argin);
		//System.out.println(devinfo.name + " created");
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#add_device(java.lang.String, java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void add_device(Database database, String devname, String classname, String servname)
					throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DbDevInfo devinfo = new DbDevInfo(devname, classname, servname);
		DeviceData	argin = new DeviceData();
		argin.insert(devinfo.toStringArray());
		command_inout(database, "DbAddDevice", argin);
		//System.out.println(devinfo.name + " created");
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device(java.lang.String)
	 */
	//==========================================================================
	public void delete_device(Database database, String devname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		boolean	delete = true;
		try {
			//	Check if device alive before delete
			//------------------------------------------
			DeviceProxy	d = new DeviceProxy(devname);
			d.ping();
			//	If device alive do not delete
			//------------------------------------------
			delete = false;
		}
		catch(DevFailed e) {}

		if (delete)
		{
			DeviceData	argin = new DeviceData();
			argin.insert(devname);
			command_inout(database, "DbDeleteDevice", argin);
			//System.out.println(devname + " deleted");
		}
		else
			Except.throw_connection_failed("TangoApi_DEVICE_ALIVE",
				"Cannot delete a device which is ALIVE.", "delete_device()");
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_info(java.lang.String)
	 */
	//==========================================================================
	public DeviceInfo get_device_info(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		DeviceData	argout = command_inout(database, "DbGetDeviceInfo", argin);
		DevVarLongStringArray	info = argout.extractLongStringArray();
		return new DeviceInfo(info);
	}
	// ==========================================================================
	/*
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_info(java.lang.String)
	 */
	// ==========================================================================
	public String[] get_device_list(Database database, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetDeviceWideList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#import_device(java.lang.String)
	 */
	//==========================================================================
	public DbDevImportInfo import_device(Database database, String devname)
				throws DevFailed
	{
		//	ALWAYS Authorized
		int	tmp_access = database.access;
		database.access = TangoConst.ACCESS_WRITE;

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		DeviceData	argout = command_inout(database, "DbImportDevice", argin);
		DevVarLongStringArray	info = argout.extractLongStringArray();

		database.access = tmp_access;
		return new DbDevImportInfo(info);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#unexport_device(java.lang.String)
	 */
	//==========================================================================
	public void unexport_device(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		command_inout(database, "DbUnExportDevice", argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#export_device(fr.esrf.TangoApi.DbDevExportInfo)
	 */
	//==========================================================================
	public void export_device(Database database, DbDevExportInfo devinfo)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = devinfo.toStringArray();
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbExportDevice", argin);
	}



	//**************************************
	//       Devices list MANAGEMENT
	//**************************************	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_class_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_class_list(Database database, String servname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(servname);
		DeviceData	argout = command_inout(database, "DbGetDeviceClassList", argin);
		return argout.extractStringArray();
	}


	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_name(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_name(Database database, String servname, String classname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array;
		array = new String[2];
		array[0] = servname;
		array[1] = classname;
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		DeviceData	argout = command_inout(database, "DbGetDeviceList", argin);

		return argout.extractStringArray();
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_domain(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_domain(Database database, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetDeviceDomainList", argin);
		return argout.extractStringArray();
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_family(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_family(Database database, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetDeviceFamilyList", argin);
		return argout.extractStringArray();
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_member(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_member(Database database, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetDeviceMemberList", argin);
		return argout.extractStringArray();
	}


	//**************************************
	//       SERVERS MANAGEMENT
	//**************************************	

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#add_server(java.lang.String, fr.esrf.TangoApi.DbDevInfo[])
	 */
	//==========================================================================
	public void add_server(Database database, String servname, DbDevInfo[] devinfo)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Convert data from DbDevInfos to a string array
		//----------------------------------------------
		//System.out.println("creating " + servname);
		String[]	array;
		array = new String[1 + 2*devinfo.length];
		
		array[0]   = servname;
		for (int i=0 ; i<devinfo.length ; i++)
		{
			array[2*i+1] = devinfo[i].name;
			array[2*i+2] = devinfo[i]._class;
		}

		//	Send command
		//-----------------------
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbAddServer", argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_server(java.lang.String)
	 */
	//==========================================================================
	public void delete_server(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		command_inout(database, "DbDeleteServer", argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#export_server(fr.esrf.TangoApi.DbDevExportInfo[])
	 */
	//==========================================================================
	public void export_server(Database database, DbDevExportInfo[] devinfo)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Convert data from DbDevInfos to a string array
		//----------------------------------------------
		String[]	array;
		array = new String[6*devinfo.length];
		for (int i=0 ; i<devinfo.length ; i++)
		{
			String[]	one = devinfo[i].toStringArray();
			for (int j=0 ; j<6 ; j++)
				array[6*i+j] = one[j];
		}

		//	Send command
		//-----------------------
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbExportServer", argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#unexport_server(java.lang.String)
	 */
	//==========================================================================
	public void unexport_server(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		command_inout(database, "DbUnExportServer", argin);
	}



	
	//**************************************
	//       PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/**
	 * Convert Properties in DbDatnum array to a String array.
	 *	@param name	Object name.
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	private String[] dbdatum2StringArray(String name, DbDatum[] properties)
	{
		//	At first, search the array size
		//---------------------------------------
		int	size = 2;	//	Object Name and nb properties
		for (DbDatum property : properties)
		{
			size += 2;	//	Property Name and nb properties
			size += property.size();
		}

		//	Format input parameters as strin array
		//--------------------------------------------
		String[] result;
		result = new String[size];
		result[0] = name;
		result[1] = String.valueOf(properties.length);
		for (int i=0, pnum=2 ; i<properties.length ; i++)
		{
			String[] prop = properties[i].toStringArray();
			for (String propname : prop)
				result[pnum++] = propname;
		}
		return result;
	}
	//==========================================================================
	/**
	 * Convert a String array to a DbDatnum array for properties.
	 *	@param strprop Properties names and values array.
	 *	@return a DbDatum array specifying the properties fonud in string array.
	 */
	//==========================================================================
	private DbDatum[] 	stringArray2DbDatum(String[] strprop)
	{
		//	And format result as DbDatum array
		//---------------------------------------
		DbDatum[]	properties;
		int 		nb_prop = Integer.parseInt(strprop[1]);
		properties = new DbDatum[nb_prop];

		//	Skip obj name, nb prop found and name of first property.
		//-----------------------------------------------------------
		for (int i=2, pnum=0 ; i<strprop.length-1 ; )
		{
			int nb = Integer.parseInt(strprop[i+1]);

			//	if property exist, create Datnum object.
			//---------------------------------------------------
			int start_val = i+2;
			int	end_val   = i+2+nb;
			if (nb>0)
				properties[pnum++] = new DbDatum(strprop[i],
										strprop, start_val, end_val);
			else
			{
				//	no property  --> fields do not exist
				properties[pnum++] = new DbDatum(strprop[i]);

				//	If nb property is zero there is a property
				//	set to space char (!!!)
				//	if Object is device it is true but false for a class !!!
				//----------------------------------------------------------
				if (start_val+1 < strprop.length)
				{
					String s = strprop[start_val];
					if (s.length()==0 || s.equals(" "))
						end_val = start_val+1;
				}
			}
			i = end_val;
		}
		return properties;
	}

	//==========================================================================
	/**
	 *	Query the database for a list of object (ie. non-device, class or device)
	 *	properties for the pecified object.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param name Object name.
	 *	@param type	Object type (nothing, class, device..)
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	private DbDatum[] get_obj_property(Database database, String name, String type, DbDatum[] properties)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Format input parameters as string array
		//--------------------------------------------
		String[]	array;
		array = new String[properties.length];
		for (int i=0 ; i<properties.length ; i++)
			array[i] = properties[i].name;
		return get_obj_property(database, name, type, array);
	}
	//==========================================================================
	/**
	 *	Query the database for an object (ie. non-device, class or device)
	 *	property for the pecified object.
	 *	@param name Object name.
	 *	@param type	Object type (nothing, class, device..)
	 *	@param propname lproperty name.
	 *	@return property in DbDatum objects.
	 */
	//==========================================================================
	private DbDatum get_obj_property(Database database, String name, String type, String propname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Format input parameters as string array
		//--------------------------------------------
		String[]	array;
		array = new String[1];
		array[0] = propname;
		DbDatum[]	data = get_obj_property(database, name, type, array);
		return data[0];
	}
	//==========================================================================
	/**
	 *	Query the database for a list of object (ie. non-device, class or device)
	 *	properties for thr dpecified object.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param name Object name.
	 *	@param type	Object type (nothing, class, device..)
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	private DbDatum[] get_obj_property(Database database, String name, String type, String[] propnames)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Format input parameters as string array
		//--------------------------------------------
		String[]	array;
		array = new String[1 + propnames.length];
		array[0] = name;
		for (int i=1 ; i<propnames.length+1 ; i++)
			array[i] = propnames[i-1];

		//	Buid command name (depends on object type)
		//---------------------------------------------------
		String	cmd = "DbGet"+type+"Property";

		//	Read Database
		//---------------------
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		DeviceData	argout = command_inout(database, cmd, argin);
		String[]	result = argout.extractStringArray();

		//	And convert to DbDatum array before returning
		//-------------------------------------------------
		return stringArray2DbDatum(result);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Object name.
	 *	@param type	Object type (nothing, class, device..)
	 *	@param properties list of property DbDatum objects.
	 */
	//==========================================================================
	private void delete_obj_property(Database database, String name, String type, DbDatum[] properties)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Format input parameters as strin array
		//--------------------------------------------
		String[]	array;
		array = new String[properties.length];
		for (int i=0 ; i<properties.length ; i++)
			array[i] = properties[i].name;

		delete_obj_property(database, name, type, array);
	}
	//==========================================================================
	/**
	 *	Delete a property for the specified object.
	 *	@param name Object name.
	 *	@param type	Object type (nothing, class, device..)
	 *	@param propname Property name.
	 */
	//==========================================================================
	private void delete_obj_property(Database database, String name, String type, String propname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Format input parameters as strin array
		//--------------------------------------------
		String[]	array;
		array = new String[1];
		array[0] = propname;

		delete_obj_property(database, name, type, array);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Object name.
	 *	@param type	Object type (nothing, class, device..)
	 *	@param propnames Property names.
	 */
	//==========================================================================
	private void delete_obj_property(Database database, String name, String type, String[] propnames)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Format input parameters as strin array
		//--------------------------------------------
		String[]	array;
		array = new String[propnames.length+1];
		array[0] = name;
		System.arraycopy(propnames, 0, array, 1, propnames.length);

		//	Buid command name (depends on object type)
		//---------------------------------------------------
		String	cmd = "DbDelete"+type+"Property";
		
		/****
		for (int i=0 ; i<array.length ; i++)
			System.out.println("array -> " + array[i]);
		System.out.println("cmd -> " + cmd);
		*********/

		//	Send it to  Database
		//------------------------------
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, cmd, argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_object_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_object_list(Database database, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetObjectList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_object_property_list(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public String[] get_object_property_list(Database database, String objname, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = new String[2];
		array[0] = objname;
		array[1] = wildcard;
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		DeviceData	argout = command_inout(database, "DbGetPropertyList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public DbDatum[] get_property(Database database, String name, String[] propnames)
				throws DevFailed
	{
		String	type = "";	//	No object type
		return get_obj_property(database, name, type, propnames);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbDatum get_property(Database database, String name, String propname)
				throws DevFailed
	{
		String	type = "";	//	No object type
		return get_obj_property(database, name, type, propname);
	}
	//==========================================================================
	/**
	 *	Query the database for an object (ie non-device)
	 *	property for the pecified object without access check (initilizing phase).
	 *	@param name Object name.
	 *	@param propname list of property names.
	 *	@return property in DbDatum object.
	 */
	//==========================================================================
	public DbDatum get_property(Database database, String name, String propname, boolean forced)
				throws DevFailed
	{
		int	tmp_access = database.access;
		if (forced)
			database.access = TangoConst.ACCESS_WRITE;
		DbDatum	datum = null;
		try
		{
			//	Format input parameters as string array
			//--------------------------------------------
			String[]	array;
			array = new String[2];
			array[0] = name;
			array[1] = propname;

			//	Read Database
			//---------------------
			DeviceData	argin = new DeviceData();
			argin.insert(array);
			DeviceData	argout = command_inout(database, "DbGetProperty", argin);
			String[]	result = argout.extractStringArray();

			//	And convert to DbDatum array before returning
			datum = stringArray2DbDatum(result)[0];
			database.access = tmp_access;
		}
		catch (DevFailed e)
		{
			database.access = tmp_access;
			throw e;
		}
		return datum;
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public DbDatum[] get_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		String	type = "";	//	No object type
		return get_obj_property(database, name, type, properties);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public void put_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = dbdatum2StringArray(name, properties);
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbPutProperty", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public void delete_property(Database database, String name, String[] propnames)
				throws DevFailed
	{
		String type = "";
		delete_obj_property(database, name, type, propnames);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void delete_property(Database database, String name, String propname)
				throws DevFailed
	{
		String type = "";
		delete_obj_property(database, name, type, propname);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public void delete_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		String type = "";
		delete_obj_property(database, name, type, properties);
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property_list(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public String[] get_class_property_list(Database database, String classname, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(classname);
		DeviceData	argout = command_inout(database, "DbGetClassPropertyList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property_list(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_property_list(Database database, String devname, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = new String[2];
		array[0] = devname;
		array[1] = wildcard;		
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		DeviceData	argout = command_inout(database, "DbGetDevicePropertyList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_for_device(java.lang.String)
	 */
	public String get_class_for_device(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		DeviceData	argout = command_inout(database, "DbGetClassForDevice", argin);
		return argout.extractString();
	}
	//==========================================================================
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_inheritance_for_device(java.lang.String)
	 */
	public String[] get_class_inheritance_for_device(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	result = { "Device_3Impl" };
		try
		{
			DeviceData	argin = new DeviceData();
			argin.insert(devname);
			DeviceData	argout = command_inout(database, "DbGetClassInheritanceForDevice", argin);
			result = argout.extractStringArray();
		}
		catch(DevFailed e)
		{
			//	Check if an old API else re-throw
			if (!e.errors[0].reason.equals("API_CommandNotFound"))
				throw e;
		}

		return result;
	}






	
	//**************************************
	//       DEVICE PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public DbDatum[] get_device_property(Database database, String name, String[] propnames)
				throws DevFailed
	{
		String	type = "Device";	//	Device object type
		return get_obj_property(database, name, type, propnames);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbDatum get_device_property(Database database, String name, String propname)
				throws DevFailed
	{
		String	type = "Device";	//	Device object type
		return get_obj_property(database, name, type, propname);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public DbDatum[] get_device_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		String		type = "Device";	//	Device object type
		return get_obj_property(database, name, type, properties);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public void put_device_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = dbdatum2StringArray(name, properties);
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbPutDeviceProperty", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public void delete_device_property(Database database, String name, String[] propnames)
				throws DevFailed
	{
		String type = "Device";
		delete_obj_property(database, name, type, propnames);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void delete_device_property(Database database, String name, String propname)
				throws DevFailed
	{
		String type = "Device";
		delete_obj_property(database, name, type, propname);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public void delete_device_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		String type = "Device";
		delete_obj_property(database, name, type, properties);
	}






	
	//**************************************
	//      ATTRIBUTE PROPERTIES MANAGEMENT
	//**************************************
	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_attribute_list(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(new String[] { devname, "*"});
		DeviceData	argout = command_inout(database, "DbGetDeviceAttributeList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public DbAttribute[] get_device_attribute_property(Database database, String devname, String[] attnames)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		DeviceData	argout;
		int			mode = 2;

		try
		{
					//	value is an array
			argin.insert(ApiUtil.toStringArray(devname, attnames));
			argout = command_inout(database, "DbGetDeviceAttributeProperty2", argin);
		}
		catch(DevFailed e)
		{
			if (e.errors[0].reason.equals("API_CommandNotFound"))
			{
					//	Value is just one element
				argout = command_inout(database, "DbGetDeviceAttributeProperty", argin);
				mode = 1;
			}
			else
				throw e;
		}
		return ApiUtil.toDbAttributeArray(argout.extractStringArray(), mode);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbAttribute get_device_attribute_property(Database database, String devname, String attname)
				throws DevFailed
	{
		String[]	attnames = new String[1];
		attnames[0] = attname;
		return get_device_attribute_property(database, devname, attnames)[0];
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute[])
	 */
	//==========================================================================
	public void put_device_attribute_property(Database database, String devname, DbAttribute[] attr)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		try
		{
					//	value is an array
			argin.insert(ApiUtil.toStringArray(devname, attr, 2));
			command_inout(database, "DbPutDeviceAttributeProperty2", argin);
		}
		catch(DevFailed e)
		{
			if (e.errors[0].reason.equals("API_CommandNotFound"))
			{
					//	Value is just one element
				argin.insert(ApiUtil.toStringArray(devname, attr, 1));
				command_inout(database, "DbPutDeviceAttributeProperty", argin);
			}
			else
				throw e;
		}
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute)
	 */
	//==========================================================================
	public void put_device_attribute_property(Database database, String devname, DbAttribute attr)
				throws DevFailed
	{
		DbAttribute[]	da = new DbAttribute[1];
		da[0] = attr;
		put_device_attribute_property(database, devname, da);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute)
	 */
	//==========================================================================
	public void delete_device_attribute_property(Database database, String devname, DbAttribute attr)
				throws DevFailed
	{
		delete_device_attribute_property(database, devname, 
						attr.name, attr.get_property_list());
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute[])
	 */
	//==========================================================================
	public void delete_device_attribute_property(Database database, String devname, DbAttribute[] attribute)
				throws DevFailed
	{
		for (DbAttribute att : attribute)
			delete_device_attribute_property(database, devname,
					att.name, att.get_property_list());
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public void delete_device_attribute_property(Database database, String devname, String attname, String[] propnames)
				throws DevFailed
	{
		if (propnames.length==0)
			return;

		if (database.isAccess_checked()==false) checkAccess(database);

		//	Build a String array before command
		String[]	array = new String[2+propnames.length];
		array[0] = devname;
		array[1] = attname;
		System.arraycopy(propnames, 0, array, 2, propnames.length);

		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbDeleteDeviceAttributeProperty", argin);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void delete_device_attribute_property(Database database, String devname, String attname, String propname)
				throws DevFailed
	{
		String[]	array = new String[1];
		array[0] = propname;
		delete_device_attribute_property(database, devname, attname, array);
	}


	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void delete_device_attribute(Database database, String devname, String attname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[] array = new String[2];
		array[0] = devname;
		array[1] = attname;
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbDeleteDeviceAttribute", argin);
	}


	//**************************************
	//      CLASS PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_class_list(Database database, String servname) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		//	Query info from database
		DeviceData	argin = new DeviceData();
		argin.insert(servname);
		DeviceData	argout = command_inout(database, "DbGetClassList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public DbDatum[] get_class_property(Database database, String name, String[] propnames)
				throws DevFailed
	{
		String	type = "Class";	//	class object type
		return get_obj_property(database, name, type, propnames);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbDatum get_class_property(Database database, String name, String propname)
				throws DevFailed
	{
		String	type = "Class";	//	class object type
		return get_obj_property(database, name, type, propname);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public DbDatum[] get_class_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		String		type = "Class";	//	Device object type
		return get_obj_property(database, name, type, properties);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_class_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public void put_class_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = dbdatum2StringArray(name, properties);
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbPutClassProperty", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public void delete_class_property(Database database, String name, String[] propnames)
				throws DevFailed
	{
		String type = "Class";
		delete_obj_property(database, name, type, propnames);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void delete_class_property(Database database, String name, String propname)
				throws DevFailed
	{
		String type = "Class";
		delete_obj_property(database, name, type, propname);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
	//==========================================================================
	public void delete_class_property(Database database, String name, DbDatum[] properties)
				throws DevFailed
	{
		String type = "Class";
		delete_obj_property(database, name, type, properties);
	}



	
	//**************************************
	//      CLASS Attribute PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_list(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public String[] get_class_attribute_list(Database database, String classname, String wildcard) throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(ApiUtil.toStringArray(classname, wildcard));
		DeviceData	argout = command_inout(database, "DbGetClassAttributeList", argin);
		return argout.extractStringArray();
	}
	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_property(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbAttribute get_class_attribute_property(Database database, String classname, String attname) throws DevFailed
	{
		String[]	attnames = new String[1];
		attnames[0] = attname;
		return get_class_attribute_property(database, classname, attnames)[0];
	}
	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_property(java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public DbAttribute[] get_class_attribute_property(Database database, String classname, String[] attnames)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		DeviceData	argout;
		int			mode = 2;

		try
		{
					//	value is an array
			argin.insert(ApiUtil.toStringArray(classname, attnames));
			argout = command_inout(database, "DbGetClassAttributeProperty2", argin);
		}
		catch(DevFailed e)
		{
			if (e.errors[0].reason.equals("API_CommandNotFound"))
			{
					//	Value is just one element
				argout = command_inout(database, "DbGetClassAttributeProperty", argin);
				mode = 1;
			}
			else
				throw e;
		}
		return ApiUtil.toDbAttributeArray(argout.extractStringArray(), mode);
	}
	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_class_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute[])
	 */
	//==========================================================================
	public void put_class_attribute_property(Database database, String classname, DbAttribute[] attr)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(ApiUtil.toStringArray(classname, attr, 2));
		command_inout(database, "DbPutClassAttributeProperty2", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_class_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute)
	 */
	//==========================================================================
	public void put_class_attribute_property(Database database, String classname, DbAttribute attr)
				throws DevFailed
	{
		DbAttribute[]	da = new DbAttribute[1];
		da[0] = attr;
		put_class_attribute_property(database, classname, da);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_attribute_property(java.lang.String, java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void delete_class_attribute_property(Database database, String name, String attname, String propname)
				throws DevFailed
	{
		String[]	array = new String[1];
		array[0] = propname;
		delete_class_attribute_property(database, name, attname, array);
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_attribute_property(java.lang.String, java.lang.String, java.lang.String[])
	 */
	//==========================================================================
	public void delete_class_attribute_property(Database database, String name, String attname, String[] propnames)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		String[]	array = new String[2+propnames.length];
		array[0] = name;
		array[1] = attname;
		System.arraycopy(propnames, 0, array, 2, propnames.length);

		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbDeleteClassAttributeProperty", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_exported(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_exported(Database database, String wildcard)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetDeviceExportedList", argin);		

		return argout.extractStringArray();
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_exported_for_class(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_exported_for_class(Database database, String classname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(classname);
		DeviceData	argout = command_inout(database, "DbGetExportdDeviceListForClass", argin);		

		return argout.extractStringArray();
	}

	//==========================================================================
	//		Aliases management
	//==========================================================================

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_alias_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_device_alias_list(Database database, String wildcard)
				throws DevFailed
	{
		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetDeviceAliasList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_alias(java.lang.String)
	 */
	//==========================================================================
	public String get_device_alias(Database database, String devname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(devname);
		DeviceData	argout = command_inout(database, "DbGetDeviceAlias", argin);
		return argout.extractString();
	}
	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_alias_device(java.lang.String)
	 */
	//==========================================================================
	public String get_alias_device(Database database, String alias)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(alias);
		DeviceData	argout = command_inout(database, "DbGetAliasDevice", argin);
		return argout.extractString();
	}
	
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_alias(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void put_device_alias(Database database, String devname, String aliasname)
				throws DevFailed
	{
		String[]	array = new String[2];
		array[0] = devname;
		array[1] = aliasname;
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbPutDeviceAlias", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_alias(java.lang.String)
	 */
	//==========================================================================
	public void delete_device_alias(Database database, String alias)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(alias);
		command_inout(database, "DbDeleteDeviceAlias", argin);
	}



	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_attribute_alias_list(java.lang.String)
	 */
	//==========================================================================
	public String[] get_attribute_alias_list(Database database, String wildcard)
				throws DevFailed
	{
		DeviceData	argin = new DeviceData();
		argin.insert(wildcard);
		DeviceData	argout = command_inout(database, "DbGetAttributeAliasList", argin);
		return argout.extractStringArray();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_attribute_alias(java.lang.String)
	 */
	//==========================================================================
	public String get_attribute_alias(Database database, String attname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(attname);
		DeviceData	argout = command_inout(database, "DbGetAttributeAlias", argin);
		return argout.extractString();
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_attribute_alias(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public void put_attribute_alias(Database database, String attname, String aliasname)
				throws DevFailed
	{
		String[]	array = new String[2];
		array[0] = attname;
		array[1] = aliasname;
		DeviceData	argin = new DeviceData();
		argin.insert(array);
		command_inout(database, "DbPutAttributeAlias", argin);		
	}
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_attribute_alias(java.lang.String)
	 */
	//==========================================================================
	public void delete_attribute_alias(Database database, String alias)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(alias);
		command_inout(database, "DbDeleteAttributeAlias", argin);
	}
	//==========================================================================
	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#getDevices(java.lang.String)
	 */
	public String[] getDevices(Database database, String wildcard) throws DevFailed
	{
		//	Get each field of device name
		StringTokenizer stk = new StringTokenizer(wildcard, "/");
		Vector<String>	vector = new Vector<String>();
		while(stk.hasMoreTokens())
			vector.add(stk.nextToken());
		if (vector.size()<3)
			Except.throw_exception( "TangoApi_DeviceNameNotValid",
				"Device name not valid", "ATangoApi.Database.getDevices()");
		
		String	domain = vector.elementAt(0);
		String	family = vector.elementAt(1);
		String	member = vector.elementAt(2);
		vector.clear();
		
		//	Check for specifieddomain
		String[]	domains = get_device_domain(database, domain);
		if (domains.length==0)
			domains = new String[] { domain };

		//	Check for all domains found
		for (String domain_1 : domains)
		{
			String domain_header = domain_1 + "/";
			//	Get families
			String[]	families = get_device_family(database, domain_header + family);
			if (families.length == 0)
				families = new String[]{family};

			//	Check for all falilies found
			for (String family_1 : families)
			{
				String family_header = domain_header + family_1 + "/";
				String[]	members = get_device_member(database, family_header + member);

				//	Add all members found
				for (String member_1 : members)
					vector.add(family_header + member_1);
			}
		}
		//	Copy all from vector to String array
		String[]	devices = new String[vector.size()];
		for (int i=0 ; i<vector.size() ; i++)
			devices[i] = vector.elementAt(i);
		return devices;
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#import_event(java.lang.String)
	 */
	//==========================================================================
	public DbEventImportInfo import_event(Database database, String channel_name)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(channel_name);
		DeviceData	argout = command_inout(database, "DbImportEvent", argin);
		DevVarLongStringArray	info = argout.extractLongStringArray();
		return new DbEventImportInfo(info);
	}

	//==========================================================================
	/**
	 * Convert the result of a DbGet...PropertyHist command.
	 */
	//==========================================================================
	private DbHistory[] convertPropertyHistory(String[] ret, boolean isAttribute)
          throws DevFailed
	{
		Vector<DbHistory>	v = new Vector<DbHistory>();
		int		i=0;
		int		count=0;
		int		offset;
		String	aName = "";
		String	pName;
		String	pDate;
		String	pCount;

		while(i<ret.length)
		{
			if(isAttribute)
			{
				aName = ret[i];
				pName = ret[i+1];
				pDate = ret[i+2];
				pCount = ret[i+3];
				offset = 4;
			}
			else
			{
				pName = ret[i];
				pDate = ret[i+1];
				pCount = ret[i+2];
				offset = 3;
			}

			try {
				count = Integer.parseInt(pCount);
			} catch(NumberFormatException e) {
				Except.throw_exception( "TangoApi_HisotryInvalid",
					"History format is invalid", "ATangoApi.Database.convertPropertyHistory()");
			}
			String[] value = new String[count];
			for(int j=0;j<count;j++)
			value[j] = ret[i+offset+j];

			if (isAttribute)
				v.add(new DbHistory(aName,pName,pDate,value));
			else
				v.add(new DbHistory(pName,pDate,value));

			i += (count+offset);
		}

		DbHistory[] result = new DbHistory[v.size()];
		for(i=0;i<result.length;i++)
		    result[i] = v.get(i);
		return result;

	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property_history(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbHistory[] get_device_property_history(Database database, String devname,String propname)
					throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(new String[]{devname,propname});
		DeviceData	argout = command_inout(database, "DbGetDevicePropertyHist", argin);
		return convertPropertyHistory(argout.extractStringArray(),false);
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_property_history(java.lang.String, java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbHistory[] get_device_attribute_property_history(Database database, String devname,String attname,String propname)
			throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(new String[]{devname,attname,propname});
		DeviceData	argout = command_inout(database, "DbGetDeviceAttributePropertyHist", argin);
		return convertPropertyHistory(argout.extractStringArray(),true);
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property_history(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbHistory[] get_class_property_history(Database database, String classname,String propname)
			throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(new String[]{classname,propname});
		DeviceData	argout = command_inout(database, "DbGetClassPropertyHist", argin);
		return convertPropertyHistory(argout.extractStringArray(),false);
	}

	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_property_history(java.lang.String, java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbHistory[] get_class_attribute_property_history(Database database, String classname,String attname,String propname)
	 throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(new String[]{classname,attname,propname});
		DeviceData	argout = command_inout(database, "DbGetClassAttributePropertyHist", argin);
		return convertPropertyHistory(argout.extractStringArray(),true);
	}


	//==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property_history(java.lang.String, java.lang.String)
	 */
	//==========================================================================
	public DbHistory[] get_property_history(Database database, String objname,String propname)
				throws DevFailed
	{
		if (database.isAccess_checked()==false) checkAccess(database);

		DeviceData	argin = new DeviceData();
		argin.insert(new String[]{objname,propname});
		DeviceData	argout = command_inout(database, "DbGetPropertyHist", argin);
		return convertPropertyHistory(argout.extractStringArray(),false);
	}
	//===================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#getServices(java.lang.String, java.lang.String)
	 */
	//===================================================================
	public String[] getServices(Database database, String servicename, String instname)
					throws DevFailed
	{
		Vector<String>	v = new Vector<String>();
		char	separ;

		//	Read Service property
		DbDatum	datum = get_property(database, TangoConst.CONTROL_SYSTEM,
									TangoConst.SERVICE_PROP_NAME, true);
		if (datum.is_empty()==false)
		{
			String[]	services = datum.extractStringArray();

			//	Build filter
			String	target = servicename.toLowerCase();
			if (instname.equals("*")==false)
			{
				target +=  "/" + instname.toLowerCase();
				separ  = ':';
			}
			else
				separ = '/';

			//	Search with filter
			int	start;
			for (String service : services)
			{
				start = service.indexOf(separ);
				if (start > 0)
				{
					String startLine =
							service.substring(0, start).toLowerCase();
					if (startLine.equals(target))
						v.add(service.substring(
								service.indexOf(':') + 1));
				}
			}
		}
		String[]	result = new String[v.size()];
		for (int i=0 ; i<v.size() ; i++)
			result[i] = v.get(i);
		return result;
	}
	//===============================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#registerService(java.lang.String, java.lang.String, java.lang.String)
	 */
	//===============================================================
	public void registerService(Database database, String serviceName, String instanceName, String devname)
			throws DevFailed
	{
		String[]	services = new String[0];

		//	Get service property
		DbDatum		data = get_property(database, TangoConst.CONTROL_SYSTEM,
										TangoConst.SERVICE_PROP_NAME);
		if (data.is_empty()==false)
			services = data.extractStringArray();

		//	Build what to be inserted and searched
		String	new_line = serviceName + "/" + instanceName;
		String	target   = new_line.toLowerCase();
		new_line        += ":" + devname;

		//	Search if already exists
		boolean	exists = false;
		Vector<String>	v = new Vector<String>();
		for (String service : services)
		{
			String line = service.toLowerCase();
			int idx = line.indexOf(':');
			if (idx > 0)
				line = line.substring(0, idx);
			if (line.equals(target))
			{
				// Found  -> replace existing by new one
				exists = true;
				v.add(new_line);
			}
			else
				v.add(service);
		}
		if (!exists)
			v.add(new_line);

		//	Copy vector to String array
		services = new String[v.size()];
		for (int i=0 ; i<v.size() ; i++)
			services[i] = v.get(i);

		//	And finaly put property
		data = new DbDatum(TangoConst.SERVICE_PROP_NAME);
		data.insert(services);
		put_property(database, TangoConst.CONTROL_SYSTEM, new DbDatum[]{ data });
	}
	//===============================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#unregisterService(java.lang.String, java.lang.String, java.lang.String)
	 */
	//===============================================================
	public void unregisterService(Database database, String serviceName, String instanceName, String devname)
			throws DevFailed
	{
		String[]	services = new String[0];

		//	Get service property
		DbDatum		data = get_property(database, TangoConst.CONTROL_SYSTEM,
										TangoConst.SERVICE_PROP_NAME);
		if (data.is_empty()==false)
			services = data.extractStringArray();

		//	Build what to be remove and searched
		String	target = serviceName + "/" + instanceName;
		target = target.toLowerCase();

		//	Search if already exists
		boolean	exists = false;
		Vector<String>	v = new Vector<String>();
		for (String service : services)
		{
			String line = service.toLowerCase();
			int idx = line.indexOf(':');
			if (idx > 0)
				line = line.substring(0, idx);

			if (line.equals(target))	// Found
				exists = true;
			else
				v.add(service);
		}
		if (exists)
		{
			//	Copy vector to String array
			services = new String[v.size()];
			for (int i=0 ; i<v.size() ; i++)
				services[i] = v.get(i);

			//	And finaly put property
			data = new DbDatum(TangoConst.SERVICE_PROP_NAME);
			data.insert(services);
			put_property(database, TangoConst.CONTROL_SYSTEM, new DbDatum[]{ data });
		}
	}
	//===================================================================
	//===================================================================







	//===================================================================
	private boolean	access_service_read = false;
	/**
	 *	Check Tango Access.
	 *	 - Check if control access is requested.
	 *	 - Check who is the user and the host.
	 *	 - Check access for this user, this host and the specified device.
	 *
	 *	@param database used database object
	 *	@param devname Specified device name.
	 *	@return The Tango access controle found.
	 */
	//===================================================================
	public int checkAccessControl(Database database, String devname)
	{
		if (database.devname==null)
			database.devname = database.device.name();
		if (devname.equals(database.devname) && database.isAccess_checked())
			return database.access;

		int	access = TangoConst.ACCESS_WRITE;
		try
		{
			//	Else create proxy
			//	Check if AccessProxy object already exists 
			if (!database.isAccess_checked() && database.getAccess_proxy()==null)
			{
				//	Check if access devname is from env (for tests)
				String	access_devname = ApiUtil.getAccessDevname();
				if (access_devname==null || access_devname.length()==0)
				{
					 if (access_service_read)
						if (database.check_access == false)
							return  TangoConst.ACCESS_WRITE;

					//	Get Access service
					String[]	services =
						getServices(database, TangoConst.ACCESS_SERVICE, "*");
					if (services.length>0)
						access_devname = services[0];
					else
					{
						//	if not set --> No check
						//System.out.println("No Access Service Found !");
						database.check_access = false;
						access_service_read   = true;
						return  TangoConst.ACCESS_WRITE;
					}
				}
				database.setAccess_proxy(new AccessProxy(access_devname));
			}
			if (database.getAccess_proxy()!=null)
				access = database.getAccess_proxy().checkAccessControl(devname);

			//	if database access not already checked, and not first import -> do it now
			if (database.isAccess_checked()==false)
				if (devname.equals(database.device.name())==false)
					checkAccess(database);
			
		}
		catch (DevFailed e)
		{
			//	In case of failure, returns always TangoConst.ACCESS_READ
			access = TangoConst.ACCESS_READ;
			//	if cannot import AccessProxy
			//	-> change description to be more explicit
			if (e.errors.length>1)
				if (e.errors[1].reason.equals("TangoApi_CANNOT_IMPORT_DEVICE"))
					e.errors[0].desc +=
						"\nControlled access service defined in Db but unreachable --> Read Only access given to all devices...";

			database.setAccess_devfailed(e);
			Except.print_exception(e);
		}
		return access;
	}
	//===================================================================
	/**
	 *	Check for specified device, the specified command is allowed.
	 *
	 *	@param	classname Specified class name.
	 *	@param	cmd Specified command name.
	 */
	//===================================================================
	public boolean isCommandAllowed(Database database, String classname, String cmd)
			throws DevFailed
	{
		if (database.getAccess_proxy()==null)
		{
			if (database.isAccess_checked()==false)
				checkAccess(database);
			return !database.check_access;
		}
		else
			return database.getAccess_proxy().isCommandAllowed(classname, cmd);
	}
	//===================================================================
	//===================================================================
}
