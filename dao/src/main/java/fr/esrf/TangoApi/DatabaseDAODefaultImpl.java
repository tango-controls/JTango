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
// $Revision: 30265 $
//
//-======================================================================


package fr.esrf.TangoApi;


import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoApi.events.DbEventImportInfo;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.util.*;

/**
 * Class Description:
 * This class is the main class for TANGO database API.
 * The TANGO database is implemented as a TANGO device server.
 * To access it, the user has the CORBA interface command_inout().
 * This expects and returns all parameters as ascii strings thereby making
 * the database laborious to use for retreing device properties and information.
 * In order to simplify this access, a high-level API has been implemented
 * which hides the low-level formatting necessary to convert the
 * command_inout() return values into binary values and all CORBA aspects
 * of the TANGO.
 * All data types are native java types e.g. simple types an arrays.
 *
 * @author verdier
 * @version $Revision: 30265 $
 */

public class DatabaseDAODefaultImpl extends ConnectionDAODefaultImpl implements IDatabaseDAO {

    //===================================================================
    /**
     * Database access constructor.
     */
    //===================================================================
    public DatabaseDAODefaultImpl() {
        super();
    }


    //===================================================================

    /**
     * Database access init method.
     *
     * @throws DevFailed in case of environment not corectly set.
     */
    //===================================================================
    public void init(Database database) throws DevFailed {
        super.init(database);
    }

    //===================================================================
    /**
     * Database access constructor.
     *
     * @throws DevFailed in case of host or port not available
     * @param    host    host where database is running.
     * @param    port    port for database connection.
     */
    //===================================================================
    public void init(Database database, String host, String port) throws DevFailed {
        super.init(database, host, port);
    }

    //==========================================================================
    //==========================================================================
    /* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#toString()
	 */
    public String toString(Database database) {
        return database.url.host + ":" + database.url.port;
    }
    //==========================================================================
    /**
     * Convert a String array to a sting.
     *
     * @param array String array to be converted
     * @return string after conversion.
     */
    //==========================================================================
    private String stringArray2String(String[] array) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0 ; i<array.length ; i++) {
            sb.append(array[i]);
            if (i<array.length - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    //==========================================================================
    //==========================================================================
    private void checkAccess(Database database) {
        //
        //	Manage Access control
        //
        if (database.check_access && !database.isAccess_checked()) {
            database.access = checkAccessControl(database, database.devname, database.url);
            database.setAccess_checked(true);
            //	Initialize value.
            ApiUtil.getReconnectionDelay();
            //System.out.println(this + "." + database.deviceName + " -> " +
            //		((database.access==TangoConst.ACCESS_READ)? "Read" : "Write"));
        }
    }


    //**************************************
    //       MISCELLANEOUS MANAGEMENT
    //**************************************


    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_info()
	 */
    //==========================================================================
    public String get_info(Database database) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argOut = command_inout(database, "DbInfo");
        String[] info = argOut.extractStringArray();

        //	format result as string
        return stringArray2String(info);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_host_list()
	 */
    //==========================================================================
    public String[] get_host_list(Database database) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert("*");
        DeviceData argOut = command_inout(database, "DbGetHostList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_host_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_host_list(Database database, String wildcard) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetHostList", argIn);
        return argOut.extractStringArray();
    }


    //**************************************
    //       SERVERS MANAGEMENT
    //**************************************

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_class_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_server_class_list(Database database, String serverName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(serverName);
        DeviceData argOut = command_inout(database, "DbGetDeviceServerClassList", argIn);
        String[] list = argOut.extractStringArray();
        //	Extract DServer class
        int nb_classes;
        if (list.length==0)
            nb_classes = 0;
        else
            nb_classes = list.length - 1;
        String[] classes = new String[nb_classes];
        for (int i = 0, j = 0 ; i<list.length && j<nb_classes ; i++)
            if (!list[i].equals("DServer"))
                classes[j++] = list[i];
        return classes;
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_name_list()
	 */
    //==========================================================================
    public String[] get_server_name_list(Database database) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert("*");
        DeviceData argOut = command_inout(database, "DbGetServerNameList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_instance_name_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_instance_name_list(Database database, String serverName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(serverName);
        DeviceData argOut = command_inout(database, "DbGetInstanceNameList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_list()
	 */
    //==========================================================================
    public String[] get_server_list(Database database) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert("*");
        DeviceData argOut = command_inout(database, "DbGetServerList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_server_list(Database database, String wildcard) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetServerList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_host_server_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_host_server_list(Database database, String hostname) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert(hostname);
        DeviceData argOut = command_inout(database, "DbGetHostServerList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_server_info(java.lang.String)
	 */
    //==========================================================================
    public DbServInfo get_server_info(Database database, String serverName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert(serverName);
        DeviceData argOut = command_inout(database, "DbGetServerInfo", argIn);
        String[] info = argOut.extractStringArray();
        return new DbServInfo(info);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_server_info(fr.esrf.TangoApi.DbServInfo)
	 */
    //==========================================================================
    public void put_server_info(Database database, DbServInfo info) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

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
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbPutServerInfo", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_server_info(java.lang.String)
	 */
    //==========================================================================
    public void delete_server_info(Database database, String serverName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(serverName);
        command_inout(database, "DbDeleteServerInfo", argIn);
    }

    // ==========================================================================
    /* (non-Javadoc)
     * Rename server name/instance in databse.
     *
     * @param srcServerName existing server name.
     * @param newServerName new server name.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void rename_server(Database database, String srcServerName, String newServerName) throws DevFailed {

        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{srcServerName, newServerName});
        command_inout(database, "DbRenameServer", argIn);
    }

    //**************************************
    //       DEVICES MANAGEMENT
    //**************************************

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#add_device(fr.esrf.TangoApi.DbDevInfo)
	 */
    //==========================================================================
    public void add_device(Database database, DbDevInfo devinfo) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(devinfo.toStringArray());
        command_inout(database, "DbAddDevice", argIn);
        //System.out.println(devinfo.name + " created");
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#add_device(java.lang.String, java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void add_device(Database database, String deviceName, String classname, String serverName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DbDevInfo devinfo = new DbDevInfo(deviceName, classname, serverName);
        DeviceData argIn = new DeviceData();
        argIn.insert(devinfo.toStringArray());
        command_inout(database, "DbAddDevice", argIn);
        //System.out.println(devinfo.name + " created");
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device(java.lang.String)
	 */
    //==========================================================================
    public void delete_device(Database database, String deviceName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        boolean delete = true;
        try {
            //	Check if device alive before delete
            //------------------------------------------
            String fullDeviceName = "tango://" + database.get_tango_host() + "/" + deviceName;
            DeviceProxy d = new DeviceProxy(fullDeviceName);
            d.ping();
            //	If device alive do not delete
            //------------------------------------------
            delete = false;
        } catch (DevFailed e) { /* */ }

        if (delete) {
            DeviceData argIn = new DeviceData();
            argIn.insert(deviceName);
            command_inout(database, "DbDeleteDevice", argIn);
            //System.out.println(deviceName + " deleted");
        } else
            Except.throw_connection_failed("TangoApi_DEVICE_ALIVE",
                    "Cannot delete a device which is ALIVE.", "delete_device()");
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_info(java.lang.String)
	 */
    //==========================================================================
    public DeviceInfo get_device_info(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        DeviceData argOut = command_inout(database, "DbGetDeviceInfo", argIn);
        DevVarLongStringArray info = argOut.extractLongStringArray();
        return new DeviceInfo(info);
    }

    // ==========================================================================
	/*
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_info(java.lang.String)
	 */
    // ==========================================================================
    public String[] get_device_list(Database database, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetDeviceWideList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#import_device(java.lang.String)
	 */
    //==========================================================================
    public DbDevImportInfo import_device(Database database, String deviceName)
            throws DevFailed {
        DevVarLongStringArray info;

        //	ALWAYS Authorized (e.g. import TAC itself)
        int tmp_access = database.access;
        database.access = TangoConst.ACCESS_WRITE;
        try {
            DeviceData argIn = new DeviceData();
            argIn.insert(deviceName);
            //System.out.println("DbImportDevice " + deviceName);
            DeviceData argOut = command_inout(database, "DbImportDevice", argIn);
            info = argOut.extractLongStringArray();
            database.access = tmp_access;
        } catch (DevFailed e) {
            database.access = tmp_access;
            throw e;
        }
        return new DbDevImportInfo(info);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#unexport_device(java.lang.String)
	 */
    //==========================================================================
    public void unexport_device(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        command_inout(database, "DbUnExportDevice", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#export_device(fr.esrf.TangoApi.DbDevExportInfo)
	 */
    //==========================================================================
    public void export_device(Database database, DbDevExportInfo devExportInfo)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //  Check for MySql 5.6 and higher compatibility
        if (devExportInfo.host.isEmpty())    devExportInfo.host = "null";
        if (devExportInfo.ior.isEmpty())     devExportInfo.ior = "null";
        if (devExportInfo.version.isEmpty()) devExportInfo.version = "null";

        String[] array = devExportInfo.toStringArray();
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbExportDevice", argIn);
    }


    //**************************************
    //       Devices list MANAGEMENT
    //**************************************
    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_class_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_class_list(Database database, String serverName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert(serverName);
        DeviceData argOut = command_inout(database, "DbGetDeviceClassList", argIn);
        return argOut.extractStringArray();
    }


    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_name(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_name(Database database, String serverName, String classname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array;
        array = new String[2];
        array[0] = serverName;
        array[1] = classname;
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        DeviceData argOut = command_inout(database, "DbGetDeviceList", argIn);

        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_domain(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_domain(Database database, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetDeviceDomainList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_family(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_family(Database database, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetDeviceFamilyList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_member(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_member(Database database, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetDeviceMemberList", argIn);
        return argOut.extractStringArray();
    }


    //**************************************
    //       SERVERS MANAGEMENT
    //**************************************

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#add_server(java.lang.String, fr.esrf.TangoApi.DbDevInfo[])
	 */
    //==========================================================================
    public void add_server(Database database, String serverName, DbDevInfo[] devinfo)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Convert data from DbDevInfos to a string array
        //----------------------------------------------
        //System.out.println("creating " + serverName);
        String[] array;
        array = new String[1 + 2 * devinfo.length];

        array[0] = serverName;
        for (int i = 0 ; i<devinfo.length ; i++) {
            array[2 * i + 1] = devinfo[i].name;
            array[2 * i + 2] = devinfo[i]._class;
        }

        //	Send command
        //-----------------------
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbAddServer", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_server(java.lang.String)
	 */
    //==========================================================================
    public void delete_server(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        command_inout(database, "DbDeleteServer", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#export_server(fr.esrf.TangoApi.DbDevExportInfo[])
	 */
    //==========================================================================
    public void export_server(Database database, DbDevExportInfo[] devExportInfos)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Convert data from DbDevInfos to a string array
        //----------------------------------------------
        String[] array;
        array = new String[6 * devExportInfos.length];
        for (int i = 0 ; i<devExportInfos.length ; i++) {
            String[] one = devExportInfos[i].toStringArray();
            System.arraycopy(one, 0, array, 6 * i, 6);
        }

        //	Send command
        //-----------------------
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbExportServer", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#unexport_server(java.lang.String)
	 */
    //==========================================================================
    public void unexport_server(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        command_inout(database, "DbUnExportServer", argIn);
    }


    //**************************************
    //       PROPERTIES MANAGEMENT
    //**************************************
    //==========================================================================

    /**
     * Convert Properties in DbDatnum array to a String array.
     *
     * @param name       Object name.
     * @param properties Properties names and values array.
     * @return Specifying the properties found.
     */
    //==========================================================================
    private String[] dbdatum2StringArray(String name, DbDatum[] properties) {
        //	At first, search the array size
        //---------------------------------------
        int size = 2;    //	Object Name and nb properties
        for (DbDatum property : properties) {
            size += 2;    //	Property Name and nb properties
            size += property.size();
        }

        //	Format input parameters as strin array
        //--------------------------------------------
        String[] result;
        result = new String[size];
        result[0] = name;
        result[1] = String.valueOf(properties.length);
        for (int i = 0, pnum = 2 ; i<properties.length ; i++) {
            String[] prop = properties[i].toStringArray();
            for (String propname : prop)
                result[pnum++] = propname;
        }
        return result;
    }
    //==========================================================================

    /**
     * Convert a String array to a DbDatnum array for properties.
     *
     * @param strprop Properties names and values array.
     * @return a DbDatum array specifying the properties found in string array.
     */
    //==========================================================================
    private DbDatum[] stringArray2DbDatum(String[] strprop) {
        //	And format result as DbDatum array
        //---------------------------------------
        DbDatum[] properties;
        int nb_prop = Integer.parseInt(strprop[1]);
        properties = new DbDatum[nb_prop];

        //	Skip obj name, nb prop found and name of first property.
        //-----------------------------------------------------------
        for (int i = 2, pnum = 0 ; i<strprop.length - 1 ; ) {
            int nb = Integer.parseInt(strprop[i + 1]);

            //	if property exist, create Datnum object.
            //---------------------------------------------------
            int start_val = i + 2;
            int end_val = i + 2 + nb;
            if (nb>0)
                properties[pnum++] = new DbDatum(strprop[i],
                        strprop, start_val, end_val);
            else {
                //	no property  --> fields do not exist
                properties[pnum++] = new DbDatum(strprop[i]);

                //	If nb property is zero there is a property
                //	set to space char (!!!)
                //	if Object is device it is true but false for a class !!!
                //----------------------------------------------------------
                if (start_val + 1<strprop.length) {
                    String s = strprop[start_val];
                    if (s.length()==0 || s.equals(" "))
                        end_val = start_val + 1;
                }
            }
            i = end_val;
        }
        return properties;
    }

    //==========================================================================

    /**
     * Query the database for a list of object (ie. non-device, class or device)
     * properties for the pecified object.
     * The property names are specified by the DbDatum array objects.
     *
     * @param database   database object
     * @param name       Object name.
     * @param type       Object type (nothing, class, device..)
     * @param properties list of property DbDatum objects.
     * @return properties in DbDatum objects.
     * @throws fr.esrf.Tango.DevFailed in case of connection failed
     */
    //==========================================================================
    private DbDatum[] get_obj_property(Database database, String name, String type, DbDatum[] properties)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Format input parameters as string array
        //--------------------------------------------
        String[] array;
        array = new String[properties.length];
        for (int i = 0 ; i<properties.length ; i++)
            array[i] = properties[i].name;
        return get_obj_property(database, name, type, array);
    }
    //==========================================================================

    /**
     * Query the database for an object (ie. non-device, class or device)
     * property for the pecified object.
     *
     * @param database database object
     * @param name     Object name.
     * @param type     Object type (nothing, class, device..)
     * @param propname lproperty name.
     * @return property in DbDatum objects.
     * @throws fr.esrf.Tango.DevFailed in case of connection failed
     */
    //==========================================================================
    private DbDatum get_obj_property(Database database, String name, String type, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Format input parameters as string array
        //--------------------------------------------
        String[] array;
        array = new String[1];
        array[0] = propname;
        DbDatum[] data = get_obj_property(database, name, type, array);
        return data[0];
    }
    //==========================================================================

    /**
     * Query the database for a list of object (ie. non-device, class or device)
     * properties for thr dpecified object.
     * The property names are specified by the DbDatum array objects.
     *
     * @param database  database object
     * @param name      Object name.
     * @param type      Object type (nothing, class, device..)
     * @param propnames list of property names.
     * @return properties in DbDatum objects.
     * @throws fr.esrf.Tango.DevFailed in case of connection failed
     */
    //==========================================================================
    private DbDatum[] get_obj_property(Database database, String name, String type, String[] propnames)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Format input parameters as string array
        //--------------------------------------------
        String[] array;
        array = new String[1 + propnames.length];
        array[0] = name;
        System.arraycopy(propnames, 0, array, 1, propnames.length);

        //	Buid command name (depends on object type)
        //---------------------------------------------------
        String cmd = "DbGet" + type + "Property";

        //	Read Database
        //---------------------
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        DeviceData argOut = command_inout(database, cmd, argIn);
        String[] result = argOut.extractStringArray();

        //	And convert to DbDatum array before returning
        //-------------------------------------------------
        return stringArray2DbDatum(result);
    }
    //==========================================================================

    /**
     * Delete a list of properties for the specified object.
     *
     * @param database   database object
     * @param name       Object name.
     * @param type       Object type (nothing, class, device..)
     * @param properties list of property DbDatum objects.
     * @throws fr.esrf.Tango.DevFailed in case of connection failed
     */
    //==========================================================================
    private void delete_obj_property(Database database, String name, String type, DbDatum[] properties)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Format input parameters as strin array
        //--------------------------------------------
        String[] array;
        array = new String[properties.length];
        for (int i = 0 ; i<properties.length ; i++)
            array[i] = properties[i].name;

        delete_obj_property(database, name, type, array);
    }
    //==========================================================================

    /**
     * Delete a property for the specified object.
     *
     * @param database database object
     * @param name     Object name.
     * @param type     Object type (nothing, class, device..)
     * @param propname Property name.
     * @throws fr.esrf.Tango.DevFailed in case of connection failed
     */
    //==========================================================================
    private void delete_obj_property(Database database, String name, String type, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Format input parameters as strin array
        //--------------------------------------------
        String[] array;
        array = new String[1];
        array[0] = propname;

        delete_obj_property(database, name, type, array);
    }
    //==========================================================================

    /**
     * Delete a list of properties for the specified object.
     *
     * @param database  database object
     * @param name      Object name.
     * @param type      Object type (nothing, class, device..)
     * @param propnames Property names.
     * @throws fr.esrf.Tango.DevFailed in case of connection failed
     */
    //==========================================================================
    private void delete_obj_property(Database database, String name, String type, String[] propnames)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Format input parameters as strin array
        //--------------------------------------------
        String[] array;
        array = new String[propnames.length + 1];
        array[0] = name;
        System.arraycopy(propnames, 0, array, 1, propnames.length);

        //	Buid command name (depends on object type)
        //---------------------------------------------------
        String cmd = "DbDelete" + type + "Property";

        //	Send it to  Database
        //------------------------------
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, cmd, argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_object_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_object_list(Database database, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetObjectList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_object_property_list(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public String[] get_object_property_list(Database database, String objname, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = new String[2];
        array[0] = objname;
        array[1] = wildcard;
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        DeviceData argOut = command_inout(database, "DbGetPropertyList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public DbDatum[] get_property(Database database, String name, String[] propnames)
            throws DevFailed {
        String type = "";    //	No object type
        return get_obj_property(database, name, type, propnames);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbDatum get_property(Database database, String name, String propname)
            throws DevFailed {
        String type = "";    //	No object type
        return get_obj_property(database, name, type, propname);
    }
    //==========================================================================

    /**
     * Query the database for an object (ie non-device)
     * property for the pecified object without access check (initilizing phase).
     *
     * @param name     Object name.
     * @param propname list of property names.
     * @return property in DbDatum object.
     */
    //==========================================================================
    public DbDatum get_property(Database database, String name, String propname, boolean forced)
            throws DevFailed {
        int tmp_access = database.access;
        if (forced)
            database.access = TangoConst.ACCESS_WRITE;
        DbDatum datum;
        try {
            //	Format input parameters as string array
            //--------------------------------------------
            String[] array;
            array = new String[2];
            array[0] = name;
            array[1] = propname;

            //	Read Database
            //---------------------
            DeviceData argIn = new DeviceData();
            argIn.insert(array);
            DeviceData argOut = command_inout(database, "DbGetProperty", argIn);
            String[] result = argOut.extractStringArray();

            //	And convert to DbDatum array before returning
            datum = stringArray2DbDatum(result)[0];
            database.access = tmp_access;
        } catch (DevFailed e) {
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
            throws DevFailed {
        String type = "";    //	No object type
        return get_obj_property(database, name, type, properties);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public void put_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = dbdatum2StringArray(name, properties);
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbPutProperty", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public void delete_property(Database database, String name, String[] propnames)
            throws DevFailed {
        String type = "";
        delete_obj_property(database, name, type, propnames);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void delete_property(Database database, String name, String propname)
            throws DevFailed {
        String type = "";
        delete_obj_property(database, name, type, propname);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public void delete_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
        String type = "";
        delete_obj_property(database, name, type, properties);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property_list(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public String[] get_class_property_list(Database database, String classname, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(classname);
        DeviceData argOut = command_inout(database, "DbGetClassPropertyList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property_list(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_property_list(Database database, String deviceName, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = new String[2];
        array[0] = deviceName;
        array[1] = wildcard;
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        DeviceData argOut = command_inout(database, "DbGetDevicePropertyList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_for_device(java.lang.String)
	 */
    public String get_class_for_device(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        DeviceData argOut = command_inout(database, "DbGetClassForDevice", argIn);
        return argOut.extractString();
    }

    //==========================================================================
    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_inheritance_for_device(java.lang.String)
	 */
    public String[] get_class_inheritance_for_device(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] result = {"Device_3Impl"};
        try {
            DeviceData argIn = new DeviceData();
            argIn.insert(deviceName);
            DeviceData argOut = command_inout(database, "DbGetClassInheritanceForDevice", argIn);
            result = argOut.extractStringArray();
        } catch (DevFailed e) {
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
            throws DevFailed {
        String type = "Device";    //	Device object type
        return get_obj_property(database, name, type, propnames);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbDatum get_device_property(Database database, String name, String propname)
            throws DevFailed {
        String type = "Device";    //	Device object type
        return get_obj_property(database, name, type, propname);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public DbDatum[] get_device_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
        String type = "Device";    //	Device object type
        return get_obj_property(database, name, type, properties);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public void put_device_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = dbdatum2StringArray(name, properties);
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbPutDeviceProperty", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public void delete_device_property(Database database, String name, String[] propnames)
            throws DevFailed {
        String type = "Device";
        delete_obj_property(database, name, type, propnames);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void delete_device_property(Database database, String name, String propname)
            throws DevFailed {
        String type = "Device";
        delete_obj_property(database, name, type, propname);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public void delete_device_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
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
    public String[] get_device_attribute_list(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{deviceName, "*"});
        DeviceData argOut = command_inout(database, "DbGetDeviceAttributeList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public DbAttribute[] get_device_attribute_property(Database database, String deviceName, String[] attnames)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        DeviceData argOut;
        int mode = 2;

        try {
            //	value is an array
            argIn.insert(ApiUtil.toStringArray(deviceName, attnames));
            argOut = command_inout(database, "DbGetDeviceAttributeProperty2", argIn);
        } catch (DevFailed e) {
            if (e.errors[0].reason.equals("API_CommandNotFound")) {
                //	Value is just one element
                argOut = command_inout(database, "DbGetDeviceAttributeProperty", argIn);
                mode = 1;
            } else
                throw e;
        }
        return ApiUtil.toDbAttributeArray(argOut.extractStringArray(), mode);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbAttribute get_device_attribute_property(Database database, String deviceName, String attname)
            throws DevFailed {
        String[] attnames = new String[1];
        attnames[0] = attname;
        return get_device_attribute_property(database, deviceName, attnames)[0];
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute[])
	 */
    //==========================================================================
    public void put_device_attribute_property(Database database, String deviceName, DbAttribute[] attr)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        try {
            //	value is an array
            argIn.insert(ApiUtil.toStringArray(deviceName, attr, 2));
            command_inout(database, "DbPutDeviceAttributeProperty2", argIn);
        } catch (DevFailed e) {
            if (e.errors[0].reason.equals("API_CommandNotFound")) {
                //	Value is just one element
                argIn.insert(ApiUtil.toStringArray(deviceName, attr, 1));
                command_inout(database, "DbPutDeviceAttributeProperty", argIn);
            } else
                throw e;
        }
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute)
	 */
    //==========================================================================
    public void put_device_attribute_property(Database database, String deviceName, DbAttribute attr)
            throws DevFailed {
        DbAttribute[] da = new DbAttribute[1];
        da[0] = attr;
        put_device_attribute_property(database, deviceName, da);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute)
	 */
    //==========================================================================
    public void delete_device_attribute_property(Database database, String deviceName, DbAttribute attr)
            throws DevFailed {
        delete_device_attribute_property(database, deviceName,
                attr.name, attr.get_property_list());
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute[])
	 */
    //==========================================================================
    public void delete_device_attribute_property(Database database, String deviceName, DbAttribute[] attribute)
            throws DevFailed {
        for (DbAttribute att : attribute)
            delete_device_attribute_property(database, deviceName,
                    att.name, att.get_property_list());
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public void delete_device_attribute_property(Database database, String deviceName, String attname, String[] propnames)
            throws DevFailed {
        if (propnames.length==0)
            return;

        if (!database.isAccess_checked()) checkAccess(database);

        //	Build a String array before command
        String[] array = new String[2 + propnames.length];
        array[0] = deviceName;
        array[1] = attname;
        System.arraycopy(propnames, 0, array, 2, propnames.length);

        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbDeleteDeviceAttributeProperty", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute_property(java.lang.String, java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void delete_device_attribute_property(Database database, String deviceName, String attname, String propname)
            throws DevFailed {
        String[] array = new String[1];
        array[0] = propname;
        delete_device_attribute_property(database, deviceName, attname, array);
    }


    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_attribute(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void delete_device_attribute(Database database, String deviceName, String attname) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = new String[2];
        array[0] = deviceName;
        array[1] = attname;
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbDeleteDeviceAttribute", argIn);
    }


    //**************************************
    //      CLASS PROPERTIES MANAGEMENT
    //**************************************
    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_class_list(Database database, String serverName) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //	Query info from database
        DeviceData argIn = new DeviceData();
        argIn.insert(serverName);
        DeviceData argOut = command_inout(database, "DbGetClassList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public DbDatum[] get_class_property(Database database, String name, String[] propnames)
            throws DevFailed {
        String type = "Class";    //	class object type
        return get_obj_property(database, name, type, propnames);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbDatum get_class_property(Database database, String name, String propname)
            throws DevFailed {
        String type = "Class";    //	class object type
        return get_obj_property(database, name, type, propname);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public DbDatum[] get_class_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
        String type = "Class";    //	Device object type
        return get_obj_property(database, name, type, properties);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_class_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public void put_class_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = dbdatum2StringArray(name, properties);
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbPutClassProperty", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public void delete_class_property(Database database, String name, String[] propnames)
            throws DevFailed {
        String type = "Class";
        delete_obj_property(database, name, type, propnames);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void delete_class_property(Database database, String name, String propname)
            throws DevFailed {
        String type = "Class";
        delete_obj_property(database, name, type, propname);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_property(java.lang.String, fr.esrf.TangoApi.DbDatum[])
	 */
    //==========================================================================
    public void delete_class_property(Database database, String name, DbDatum[] properties)
            throws DevFailed {
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
    public String[] get_class_attribute_list(Database database, String classname, String wildcard) throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(ApiUtil.toStringArray(classname, wildcard));
        DeviceData argOut = command_inout(database, "DbGetClassAttributeList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_property(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbAttribute get_class_attribute_property(Database database, String classname, String attname) throws DevFailed {
        String[] attnames = new String[1];
        attnames[0] = attname;
        return get_class_attribute_property(database, classname, attnames)[0];
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_property(java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public DbAttribute[] get_class_attribute_property(Database database, String classname, String[] attnames)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        DeviceData argOut;
        int mode = 2;

        try {
            //	value is an array
            argIn.insert(ApiUtil.toStringArray(classname, attnames));
            argOut = command_inout(database, "DbGetClassAttributeProperty2", argIn);
        } catch (DevFailed e) {
            if (e.errors[0].reason.equals("API_CommandNotFound")) {
                //	Value is just one element
                argOut = command_inout(database, "DbGetClassAttributeProperty", argIn);
                mode = 1;
            } else
                throw e;
        }
        return ApiUtil.toDbAttributeArray(argOut.extractStringArray(), mode);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_class_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute[])
	 */
    //==========================================================================
    public void put_class_attribute_property(Database database, String classname, DbAttribute[] attr)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(ApiUtil.toStringArray(classname, attr, 2));
        command_inout(database, "DbPutClassAttributeProperty2", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_class_attribute_property(java.lang.String, fr.esrf.TangoApi.DbAttribute)
	 */
    //==========================================================================
    public void put_class_attribute_property(Database database, String classname, DbAttribute attr)
            throws DevFailed {
        DbAttribute[] da = new DbAttribute[1];
        da[0] = attr;
        put_class_attribute_property(database, classname, da);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_attribute_property(java.lang.String, java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void delete_class_attribute_property(Database database, String name, String attname, String propname)
            throws DevFailed {
        String[] array = new String[1];
        array[0] = propname;
        delete_class_attribute_property(database, name, attname, array);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_class_attribute_property(java.lang.String, java.lang.String, java.lang.String[])
	 */
    //==========================================================================
    public void delete_class_attribute_property(Database database, String name, String attname, String[] propnames)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        String[] array = new String[2 + propnames.length];
        array[0] = name;
        array[1] = attname;
        System.arraycopy(propnames, 0, array, 2, propnames.length);

        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbDeleteClassAttributeProperty", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_exported(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_exported(Database database, String wildcard)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetDeviceExportedList", argIn);

        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_exported_for_class(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_exported_for_class(Database database, String classname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(classname);
        DeviceData argOut = command_inout(database, "DbGetExportdDeviceListForClass", argIn);

        return argOut.extractStringArray();
    }

    //==========================================================================
    //		Aliases management
    //==========================================================================

    // ==========================================================================

    /**
     * Query the database for an alias for the specified device.
     *
     * @param database   specified database object.
     * @param deviceName device's name.
     * @return the device alias found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String getAliasFromDevice(Database database, String deviceName) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        DeviceData argOut = command_inout(database, "DbGetDeviceAlias", argIn);
        return argOut.extractString();
    }

    // ==========================================================================

    /**
     * Query the database for a device name for the specified alias.
     *
     * @param database specified database object.
     * @param alias    alias name.
     * @return the device name found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String getDeviceFromAlias(Database database, String alias) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(alias);
        DeviceData argOut = command_inout(database, "DbGetAliasDevice", argIn);
        return argOut.extractString();
    }

    // ==========================================================================

    /**
     * Query the database for an alias for the specified attribute.
     *
     * @param database specified database object.
     * @param attName  attribute name.
     * @return the alias found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String getAliasFromAttribute(Database database, String attName) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(attName);
        DeviceData argOut = command_inout(database, "DbGetAttributeAlias2", argIn);
        return argOut.extractString();
    }

    // ==========================================================================

    /**
     * Query the database for an attribute name for the specified alias.
     *
     * @param database specified database object.
     * @param alias    alias name.
     * @return the attribute found.
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public String getAttributeFromAlias(Database database, String alias) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(alias);
        DeviceData argOut = command_inout(database, "DbGetAliasAttribute", argIn);
        return argOut.extractString();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_alias_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_device_alias_list(Database database, String wildcard)
            throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetDeviceAliasList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_alias(java.lang.String)
	 */
    //==========================================================================
    public String get_device_alias(Database database, String deviceName)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        DeviceData argOut = command_inout(database, "DbGetDeviceAlias", argIn);
        return argOut.extractString();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_alias_device(java.lang.String)
	 */
    //==========================================================================
    public String get_alias_device(Database database, String alias)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(alias);
        DeviceData argOut = command_inout(database, "DbGetAliasDevice", argIn);
        return argOut.extractString();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_device_alias(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void put_device_alias(Database database, String deviceName, String aliasname)
            throws DevFailed {
        String[] array = new String[2];
        array[0] = deviceName;
        array[1] = aliasname;
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbPutDeviceAlias", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_device_alias(java.lang.String)
	 */
    //==========================================================================
    public void delete_device_alias(Database database, String alias)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(alias);
        command_inout(database, "DbDeleteDeviceAlias", argIn);
    }


    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_attribute_alias_list(java.lang.String)
	 */
    //==========================================================================
    public String[] get_attribute_alias_list(Database database, String wildcard)
            throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(wildcard);
        DeviceData argOut = command_inout(database, "DbGetAttributeAliasList", argIn);
        return argOut.extractStringArray();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_attribute_alias(java.lang.String)
	 */
    //==========================================================================
    public String get_attribute_alias(Database database, String attname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(attname);
        DeviceData argOut = command_inout(database, "DbGetAttributeAlias", argIn);
        return argOut.extractString();
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#put_attribute_alias(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public void put_attribute_alias(Database database, String attname, String aliasname)
            throws DevFailed {
        String[] array = new String[2];
        array[0] = attname;
        array[1] = aliasname;
        DeviceData argIn = new DeviceData();
        argIn.insert(array);
        command_inout(database, "DbPutAttributeAlias", argIn);
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#delete_attribute_alias(java.lang.String)
	 */
    //==========================================================================
    public void delete_attribute_alias(Database database, String alias)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(alias);
        command_inout(database, "DbDeleteAttributeAlias", argIn);
    }

    //==========================================================================
    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#getDevices(java.lang.String)
	 */
    public String[] getDevices(Database database, String wildcard) throws DevFailed {
        //	Get each field of device name
        StringTokenizer stk = new StringTokenizer(wildcard, "/");
        Vector<String> vector = new Vector<String>();
        while (stk.hasMoreTokens())
            vector.add(stk.nextToken());
        if (vector.size()<3)
            Except.throw_exception("TangoApi_DeviceNameNotValid",
                    "Device name not valid", "ATangoApi.Database.getDevices()");

        String domain = vector.elementAt(0);
        String family = vector.elementAt(1);
        String member = vector.elementAt(2);
        vector.clear();

        //	Check for specifieddomain
        String[] domains = get_device_domain(database, domain);
        if (domains.length==0)
            domains = new String[]{domain};

        //	Check for all domains found
        for (String domain_1 : domains) {
            String domain_header = domain_1 + "/";
            //	Get families
            String[] families = get_device_family(database, domain_header + family);
            if (families.length==0)
                families = new String[]{family};

            //	Check for all falilies found
            for (String family_1 : families) {
                String family_header = domain_header + family_1 + "/";
                String[] members = get_device_member(database, family_header + member);

                //	Add all members found
                for (String member_1 : members)
                    vector.add(family_header + member_1);
            }
        }
        //	Copy all from vector to String array
        String[] devices = new String[vector.size()];
        for (int i = 0 ; i<vector.size() ; i++)
            devices[i] = vector.elementAt(i);
        return devices;
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#import_event(java.lang.String)
	 */
    //==========================================================================
    public DbEventImportInfo import_event(Database database, String channel_name)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        //  Check if channel name i a full url
        channel_name = new TangoUrl(channel_name).devname;
        DeviceData argIn = new DeviceData();
        argIn.insert(channel_name);
        DeviceData argOut = command_inout(database, "DbImportEvent", argIn);
        DevVarLongStringArray info = argOut.extractLongStringArray();
        return new DbEventImportInfo(info);
    }

    //==========================================================================
    /**
     * Convert the result of a DbGet...PropertyHist command.
     *
     * @param ret         return value of DebGet...
     * @param isAttribute true if attribute or pipe property
     * @return database history list
     * @throws fr.esrf.Tango.DevFailed incase of invalid format.
     */
    //==========================================================================
    private List<DbHistory> convertPropertyHistory(String[] ret, boolean isAttribute)
            throws DevFailed {
        ArrayList<DbHistory> list = new ArrayList<DbHistory>();
        int i = 0;
        int count = 0;
        int offset;
        String aName = "";
        String pName;
        String pDate;
        String pCount;

        while (i<ret.length) {
            if (isAttribute) {
                aName = ret[i];
                pName = ret[i + 1];
                pDate = ret[i + 2];
                pCount = ret[i + 3];
                offset = 4;
            } else {
                pName = ret[i];
                pDate = ret[i + 1];
                pCount = ret[i + 2];
                offset = 3;
            }

            try {
                count = Integer.parseInt(pCount);
            } catch (NumberFormatException e) {
                Except.throw_exception("TangoApi_HistoryInvalid", "History format is invalid");
            }
            String[] value = new String[count];
            System.arraycopy(ret, i + offset, value, 0, count);

            if (isAttribute)
                list.add(new DbHistory(aName, pName, pDate, value));
            else
                list.add(new DbHistory(pName, pDate, value));
            i += (count + offset);
        }
        return list;
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_property_history(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbHistory[] get_device_property_history(Database database, String deviceName, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{deviceName, propname});
        DeviceData argOut = command_inout(database, "DbGetDevicePropertyHist", argIn);
        List<DbHistory> dbHistories = convertPropertyHistory(argOut.extractStringArray(), false);
        DbHistory[] array = new DbHistory[dbHistories.size()];
        for (int i=0 ; i<array.length ; i++)
            array[i] = dbHistories.get(i);
        return array;
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_device_attribute_property_history(java.lang.String, java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbHistory[] get_device_attribute_property_history(Database database, String deviceName, String attname, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{deviceName, attname, propname});
        DeviceData argOut = command_inout(database, "DbGetDeviceAttributePropertyHist", argIn);
        List<DbHistory> dbHistories =  convertPropertyHistory(argOut.extractStringArray(), true);
        DbHistory[] array = new DbHistory[dbHistories.size()];
        for (int i=0 ; i<array.length ; i++)
            array[i] = dbHistories.get(i);
        return array;
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_property_history(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbHistory[] get_class_property_history(Database database, String classname, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{classname, propname});
        DeviceData argOut = command_inout(database, "DbGetClassPropertyHist", argIn);
        List<DbHistory> dbHistories =  convertPropertyHistory(argOut.extractStringArray(), false);
        DbHistory[] array = new DbHistory[dbHistories.size()];
        for (int i=0 ; i<array.length ; i++)
            array[i] = dbHistories.get(i);
        return array;
    }

    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_class_attribute_property_history(java.lang.String, java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbHistory[] get_class_attribute_property_history(Database database, String classname, String attname, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{classname, attname, propname});
        DeviceData argOut = command_inout(database, "DbGetClassAttributePropertyHist", argIn);
        List<DbHistory> dbHistories =  convertPropertyHistory(argOut.extractStringArray(), true);
        DbHistory[] array = new DbHistory[dbHistories.size()];
        for (int i=0 ; i<array.length ; i++)
            array[i] = dbHistories.get(i);
        return array;
    }


    //==========================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#get_property_history(java.lang.String, java.lang.String)
	 */
    //==========================================================================
    public DbHistory[] get_property_history(Database database, String objname, String propname)
            throws DevFailed {
        if (!database.isAccess_checked()) checkAccess(database);

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{objname, propname});
        DeviceData argOut = command_inout(database, "DbGetPropertyHist", argIn);
        List<DbHistory> dbHistories =  convertPropertyHistory(argOut.extractStringArray(), false);
        DbHistory[] array = new DbHistory[dbHistories.size()];
        for (int i=0 ; i<array.length ; i++)
            array[i] = dbHistories.get(i);
        return array;
    }

    //===================================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#getServices(java.lang.String, java.lang.String)
	 */
    //===================================================================
    public String[] getServices(Database database, String servicename, String instname)
            throws DevFailed {
        Vector<String> v = new Vector<String>();
        char separ;

        //	Read Service property
        DbDatum datum = get_property(database, TangoConst.CONTROL_SYSTEM,
                TangoConst.SERVICE_PROP_NAME, true);
        if (!datum.is_empty()) {
            String[] services = datum.extractStringArray();

            //	Build filter
            String target = servicename.toLowerCase();
            if (!instname.equals("*")) {
                target += "/" + instname.toLowerCase();
                separ = ':';
            } else
                separ = '/';

            //	Search with filter
            int start;
            for (String service : services) {
                start = service.indexOf(separ);
                if (start>0) {
                    String startLine =
                            service.substring(0, start).toLowerCase();
                    if (startLine.equals(target))
                        v.add(service.substring(
                                service.indexOf(':') + 1));
                }
            }
        }
        String[] result = new String[v.size()];
        for (int i = 0 ; i<v.size() ; i++)
            result[i] = v.get(i);
        return result;
    }

    //===============================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#registerService(java.lang.String, java.lang.String, java.lang.String)
	 */
    //===============================================================
    public void registerService(Database database, String serviceName, String instanceName, String deviceName)
            throws DevFailed {
        String[] services = new String[0];

        //	Get service property
        DbDatum data = get_property(database, TangoConst.CONTROL_SYSTEM,
                TangoConst.SERVICE_PROP_NAME);
        if (!data.is_empty())
            services = data.extractStringArray();

        //	Build what to be inserted and searched
        String new_line = serviceName + "/" + instanceName;
        String target = new_line.toLowerCase();
        new_line += ":" + deviceName;

        //	Search if already exists
        boolean exists = false;
        Vector<String> v = new Vector<String>();
        for (String service : services) {
            String line = service.toLowerCase();
            int idx = line.indexOf(':');
            if (idx>0)
                line = line.substring(0, idx);
            if (line.equals(target)) {
                // Found  -> replace existing by new one
                exists = true;
                v.add(new_line);
            } else
                v.add(service);
        }
        if (!exists)
            v.add(new_line);

        //	Copy vector to String array
        services = new String[v.size()];
        for (int i = 0 ; i<v.size() ; i++)
            services[i] = v.get(i);

        //	And finaly put property
        data = new DbDatum(TangoConst.SERVICE_PROP_NAME);
        data.insert(services);
        put_property(database, TangoConst.CONTROL_SYSTEM, new DbDatum[]{data});
    }

    //===============================================================
	/* (non-Javadoc)
	 * @see fr.esrf.TangoApi.IDatabaseDAO#unregisterService(java.lang.String, java.lang.String, java.lang.String)
	 */
    //===============================================================
    public void unregisterService(Database database, String serviceName, String instanceName, String deviceName)
            throws DevFailed {
        String[] services = new String[0];

        //	Get service property
        DbDatum data = get_property(database, TangoConst.CONTROL_SYSTEM,
                TangoConst.SERVICE_PROP_NAME);
        if (!data.is_empty())
            services = data.extractStringArray();

        //	Build what to be remove and searched
        String target = serviceName + "/" + instanceName;
        target = target.toLowerCase();

        //	Search if already exists
        boolean exists = false;
        Vector<String> v = new Vector<String>();
        for (String service : services) {
            String line = service.toLowerCase();
            int idx = line.indexOf(':');
            if (idx>0)
                line = line.substring(0, idx);

            if (line.equals(target))    // Found
                exists = true;
            else
                v.add(service);
        }
        if (exists) {
            //	Copy vector to String array
            services = new String[v.size()];
            for (int i = 0 ; i<v.size() ; i++)
                services[i] = v.get(i);

            //	And finally put property
            data = new DbDatum(TangoConst.SERVICE_PROP_NAME);
            data.insert(services);
            put_property(database, TangoConst.CONTROL_SYSTEM, new DbDatum[]{data});
        }
    }
    //===================================================================
    //===================================================================


    //===================================================================
    private boolean access_service_read = false;
    private static final Object monitor = new Object();
    //===================================================================
    /**
     * Check Tango Access.
     * - Check if control access is requested.
     * - Check who is the user and the host.
     * - Check access for this user, this host and the specified device.
     *
     * @param database used database object
     * @param deviceName  Specified device name.
     * @param devUrl   Specified device url
     * @return The Tango access control found.
     */
    //===================================================================
    public int checkAccessControl(Database database, String deviceName, TangoUrl devUrl) {
        int access = TangoConst.ACCESS_WRITE;
        synchronized (monitor) {
            if (database.devname==null)
                database.devname = database.device.name();
            if (deviceName.equals(database.devname) && database.isAccess_checked())
                return database.access;
            try {
                //	Else create proxy
                //	Check if AccessProxy object already exists
                if (/*!database.isAccess_checked() &&*/ database.getAccess_proxy()==null) {
                    //	Check if access deviceName is from env (for tests)
                    String access_deviceName = ApiUtil.getAccessDevname();
                    if (access_deviceName==null || access_deviceName.length()==0) {
                        if (access_service_read)
                            if (!database.check_access)
                                return TangoConst.ACCESS_WRITE;

                        //	Get Access service
                        String[] services =
                                getServices(database, TangoConst.ACCESS_SERVICE, "*");
                        if (services.length>0)
                            access_deviceName = services[0];
                        else {
                            //	if not set --> No check
                            //System.out.println("No Access Service Found !");
                            database.check_access = false;
                            access_service_read = true;
                            return TangoConst.ACCESS_WRITE;
                        }
                    }
                    // If tango_host not from env -> add tango_host as header for access device name
                    //  Check if header not already in access_deviceName :-)
                    if (!devUrl.fromEnv && !access_deviceName.startsWith("tango://"))
                        access_deviceName = "tango://" + devUrl.host + ":" + devUrl.port + "/" + access_deviceName;

                    //	Then build Tango Access Control Proxy
                    database.setAccess_proxy(new AccessProxy(access_deviceName));
                }
                if (database.getAccess_proxy()!=null) {
                    access = database.getAccess_proxy().checkAccessControl(deviceName);
                }

                //	if database access not already checked, and not first import -> do it now
                if (!database.isAccess_checked())
                    if (!deviceName.equals(database.device.name()))
                        checkAccess(database);

            } catch (DevFailed e) {
                //	In case of failure, returns always TangoConst.ACCESS_READ
                access = TangoConst.ACCESS_READ;
                //	if cannot import AccessProxy
                //	-> change description to be more explicit
                if (e.errors.length>1)
                    if (e.errors[1].reason.equals("TangoApi_CANNOT_IMPORT_DEVICE"))
                        e.errors[0].desc +=
                                "\nControlled access service defined in Db but unreachable --> Read Only access given to all devices...";

                database.setAccess_devfailed(e);
                //Except.print_exception(e);
            }
        }
        return access;
    }
    //===================================================================

    /**
     * Check for specified device, the specified command is allowed.
     *
     * @param    classname Specified class name.
     * @param    cmd Specified command name.
     */
    //===================================================================
    public boolean isCommandAllowed(Database database, String classname, String cmd)
            throws DevFailed {
        if (database.getAccess_proxy()==null) {
            if (!database.isAccess_checked())
                checkAccess(database);
            return !database.check_access;
        } else
            return database.getAccess_proxy().isCommandAllowed(classname, cmd);
    }

    //===================================================================
    //===================================================================
    public String[] getPossibleTangoHosts(Database database) {
        String[] tangoHosts = null;
        try {
            DeviceData deviceData = database.command_inout("DbGetCSDbServerList");
            tangoHosts = deviceData.extractStringArray();
        } catch (DevFailed e) {
            String desc = e.errors[0].desc.toLowerCase();
            try {
                if (desc.startsWith("command ") && desc.endsWith("not found")) {
                    tangoHosts = new String[]{database.getFullTangoHost()};
                } else
                    System.err.println(e.errors[0].desc);
            } catch (DevFailed e2) {
                System.err.println(e2.errors[0].desc);
            }
        }
        return tangoHosts;
    }
    //===================================================================
    //===================================================================


    //===================================================================
    /**
     * Pipe related methods
     */
    //===================================================================

    // ===================================================================
    /**
     * Query the database for a list of device pipe properties
     * for the specified pipe.
     *	@param database Database object.
     * @param deviceName specified device.
     * @return a list of device pipe properties.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbPipe getDevicePipeProperties(Database database, String deviceName, String pipeName) throws DevFailed {

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{deviceName, pipeName});
        DeviceData argOut = database.command_inout("DbGetDevicePipeProperty", argIn);
        return ApiUtil.toDbPipe(pipeName, argOut.extractStringArray());
    }
    // ===================================================================
    /**
     * Query the database for a list of class pipe properties
     * for the specified pipe.
     *	@param database Database object.
     * @param className specified class.
     * @param pipeName specified pipe.
     * @return a list of class pipe properties.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbPipe getClassPipeProperties(Database database, String className, String pipeName) throws DevFailed {

        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{className, pipeName});
        DeviceData argOut = database.command_inout("DbGetClassPipeProperty", argIn);
        return ApiUtil.toDbPipe(pipeName, argOut.extractStringArray());
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified device.
     * The property names and their values are specified by the DbAPipe.
     *
     *	@param database Database object.
     * @param deviceName device name.
     * @param dbPipe pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putDevicePipeProperty(Database database, String deviceName, DbPipe dbPipe) throws DevFailed {
        DeviceData argIn = new DeviceData();
        String[]    array = ApiUtil.toStringArray(deviceName, dbPipe);
        argIn.insert(array);
        database.command_inout("DbPutDevicePipeProperty", argIn);
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified class.
     * The property names and their values are specified by the DbAPipe.
     *
     *	@param database Database object.
     * @param className class name.
     * @param dbPipe pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putClassPipeProperty(Database database, String className, DbPipe dbPipe) throws DevFailed {
        DeviceData argIn = new DeviceData();
        String[]    array = ApiUtil.toStringArray(className, dbPipe);
        argIn.insert(array);
        database.command_inout("DbPutClassPipeProperty", argIn);
    }
    // ===================================================================
    /**
     * Query database for a list of pipes for specified device and specified wildcard.
     *
     * @param deviceName specified device name.
     * @param wildcard   specified wildcard.
     * @return a list of pipes defined in database for specified device and specified wildcard.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<String> getDevicePipeList(Database database, String deviceName, String wildcard) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{deviceName, wildcard});
        DeviceData argOut = database.command_inout("DbGetDevicePipeList", argIn);
        //System.out.println("database.command_inout(\"DbGetDevicePipeList\", argIn)");

        String[] array = argOut.extractStringArray();
        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, array);
        return list;
    }
    // ===================================================================
    /**
     * Query database for a list of pipes for specified class and specified wildcard.
     *
     * @param className specified class name.
     * @param wildcard  specified wildcard.
     * @return a list of pipes defined in database for specified class and specified wildcard.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<String> getClassPipeList(Database database, String className, String wildcard) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(new String[]{className, wildcard});
        DeviceData argOut = database.command_inout("DbGetDevicePipeList", argIn);
        String[] array = argOut.extractStringArray();
        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, array);
        return list;
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified device.
     *
     *	@param database Database object.
     * @param deviceName Device name.
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteDevicePipeProperties(Database database, String deviceName,
                                         String pipeName, List<String> propertyNames) throws DevFailed {

        String[]    array = new String[propertyNames.size()+2];
        int i=0;
        array[i++] = deviceName;
        array[i++] = pipeName;
        for (String propertyName : propertyNames)
            array[i++] = propertyName;
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        database.command_inout("DbDeleteDevicePipeProperty", argIn);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     *	@param database Database object.
     * @param className class name.
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deleteClassPipeProperties(Database database, String className,
                                         String pipeName, List<String> propertyNames) throws DevFailed {

        String[]    array = new String[propertyNames.size()+2];
        int i=0;
        array[i++] = className;
        array[i++] = pipeName;
        for (String propertyName : propertyNames)
            array[i++] = propertyName;
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        database.command_inout("DbDeleteClassPipeProperty", argIn);
    }
    // ===================================================================
    /**
     * Delete specified pipe for specified device.
     * @param database Database object.
     * @param deviceName    device name
     * @param pipeName      pipe name
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deleteDevicePipe(Database database, String deviceName, String pipeName) throws DevFailed {
        String[]    array = new String[] { deviceName, pipeName };
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        database.command_inout("DbDeleteDevicePipe", argIn);
    }
    // ===================================================================
    /**
     * Delete specified pipe for specified class.
     * @param database Database object.
     * @param className    class name
     * @param pipeName      pipe name
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deleteClassPipe(Database database, String className, String pipeName) throws DevFailed {
        String[]    array = new String[] { className, pipeName };
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        database.command_inout("DbDeleteClassPipe", argIn);
    }
    // ===================================================================
    /**
     * Delete all properties for specified pipes
     * @param database Database object.
     * @param deviceName    device name
     * @param pipeNames     pipe names
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public void deleteAllDevicePipeProperty(Database database, String deviceName,
                                            List<String> pipeNames) throws DevFailed {
        String[]    array = new String[1+pipeNames.size()];
        int i=0;
        array[i++] = deviceName;
        for (String pipeName : pipeNames)
            array[i++] = pipeName;
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        database.command_inout("DbDeleteAllDevicePipeProperty", argIn);
    }
    // ===================================================================
    /**
     * Returns the property history for specified pipe.
     * @param database Database object.
     * @param deviceName    device name
     * @param pipeName      pipe name
     * @param propertyName  property Name
     * @return the property history for specified pipe.
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public List<DbHistory> getDevicePipePropertyHistory(Database database, String deviceName,
                                                        String pipeName, String propertyName) throws DevFailed {
        String[]    array = new String[] { deviceName, pipeName, propertyName };
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        DeviceData  argOut = database.command_inout("DbGetDevicePipePropertyHist", argIn);
        return convertPropertyHistory(argOut.extractStringArray(), true);
    }
    // ===================================================================
    /**
     * Returns the property history for specified pipe.
     * @param database Database object.
     * @param className    class name
     * @param pipeName      pipe name
     * @param propertyName  property Name
     * @return the property history for specified pipe.
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public List<DbHistory> getClassPipePropertyHistory(Database database, String className,
                                                       String pipeName, String propertyName) throws DevFailed {
        String[]    array = new String[] { className, pipeName, propertyName };
        DeviceData  argIn = new DeviceData();
        argIn.insert(array);
        DeviceData  argOut = database.command_inout("DbGetClassPipePropertyHist", argIn);
        return convertPropertyHistory(argOut.extractStringArray(), true);
    }
    // ===================================================================
    /**
     * Query database to get a list of device using the specified device as
     * 		as root for forwarded attributes
     * @param deviceName the specified device
     * @return a list of device using the specified device as as root for forwarded attributes
     * @throws DevFailed
     */
    // ===================================================================
    public  List<String[]> getForwardedAttributeDataForDevice(Database database, String deviceName) throws DevFailed {
        DeviceData argIn = new DeviceData();
        argIn.insert(deviceName);
        DeviceData argOut = database.command_inout("DbGetForwardedAttributeListForDevice", argIn);
        String[] array = argOut.extractStringArray();
        ArrayList<String[]> list = new ArrayList<String[]>();
        for (int i=0 ; i<array.length/3 ; i++) {
            list.add(new String[] { array[3*i], array[3*i+1], array[3*i+2] });
        }
        return list;
    }
}
