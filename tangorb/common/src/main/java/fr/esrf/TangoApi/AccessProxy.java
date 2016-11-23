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
// $Revision: 25483 $
//
//-======================================================================


package fr.esrf.TangoApi;


/**
 *	This class is extends TangoApi.DeviceProxy 
 *	to manage Tango access device.
 *	 - Check if control access is requested.
 *	 - Check who is the user and the host.
 *	 - Check access for this user, this host and the specified device.
 *
 * @author verdier
 */

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.util.Hashtable;
import java.util.Vector;


class AccessProxy extends DeviceProxy {
    protected static String user = null;
    protected static String hostAddr = null;
    protected boolean forced = false;
    private static boolean muliIP = true;
    private static final Object monitor = new Object();

    /**
     * Device rights table
     */
    protected Hashtable<String, String> dev_right_table = null;
    /**
     * Allowed Commands for a class table.
     */
    protected Hashtable<String, String[]> allowed_cmd_table = null;
    //===============================================================
    /**
     * Constructor for Access device proxy
     *
     * @param devname access device name
     * @throws fr.esrf.Tango.DevFailed in case of connection failed on access device
     */
    //===============================================================
    AccessProxy(String devname) throws DevFailed {
        //	Build device proxy and check if present.
        super(devname, false);

        //	Check if forced mode
        forced = TangoEnv.isSuperTango();
        dev_right_table = new Hashtable<String, String>();
        allowed_cmd_table = new Hashtable<String, String[]>();

        if (!forced) {
            set_transparency_reconnection(false);
            ping();
            set_transparency_reconnection(true);
        }
    }
    //===============================================================
    /**
     * Check access for specified device
     *
     * @param devName device name to check access
     * @return the access mode found (TangoConst.ACCESS_WRITE or TangoConst.ACCESS_RAED)
     * @throws fr.esrf.Tango.DevFailed in case of connection failed on access device
     */
    //===============================================================
    int checkAccessControl(String devName) throws DevFailed {
        if (forced)
            return TangoConst.ACCESS_WRITE;

        synchronized (monitor) {
            //	Check if already tested.
            String str = dev_right_table.get(devName);
            if (str != null) {
                //System.out.println(devname + " AccessControl already checked.");
                if (str.equals("write"))
                    return TangoConst.ACCESS_WRITE;
                else
                    return TangoConst.ACCESS_READ;
            }
            //else
            //	System.out.println("Check AccessControl for " + devname);

            try {
                //	If not already done check user name
                if (user == null)
                    user = System.getProperty("user.name").toLowerCase();
                hostAddr = ApiUtil.getHostAddress();

                DeviceData argin = new DeviceData();
                String rights;
                if (muliIP) {
                    Vector<String> addresses = ApiUtil.getHostAddresses();
                    String[] array = new String[addresses.size() + 2];
                    int i = 0;
                    array[i++] = user;
                    array[i++] = devName;
                    for (String address : addresses) {
                        //System.out.println(" Checking for : " + address);
                        array[i++] = address;
                    }

                    argin.insert(array);
                    rights = command_inout("GetAccessForMultiIP", argin).extractString();
                } else {
                    argin.insert(new String[]{user, hostAddr, devName});
                    rights = command_inout("GetAccess", argin).extractString();
                }
                //	Check for user and host rights on specified device
                dev_right_table.put(devName, rights);
                if (rights.equals("write")) {
                    return TangoConst.ACCESS_WRITE;
                } else {
                    return TangoConst.ACCESS_READ;
                }
            } catch (DevFailed e) {
                if (e.errors[0].reason.equals("TangoApi_DEVICE_NOT_EXPORTED"))
                    Except.re_throw_exception(e,
                            "TangoApi_CANNOT_CHECK_ACCESS_CONTROL",
                            "Cannot import Access Control device !",
                            "AccessProxy.checkAccessControl()");
                else if (muliIP && e.errors[0].reason.equals("API_CommandNotFound")) {
                    System.err.println(e.errors[0].desc + "  -  TAC server is an old version");
                    muliIP = false;
                    return checkAccessControl(devName);
                } else
                    throw e;
            }
        }
        return TangoConst.ACCESS_READ;
    }

    //===============================================================
    /**
     * Check for specified device, the specified command is allowed.
     *
     * @return true if command is allowed.
     * @param    classname Specified class name.
     * @param    cmd Specified command name.
     */
    //===============================================================
    boolean isCommandAllowed(String classname, String cmd) {
        String[] allowed = allowed_cmd_table.get(classname);

        //	Check if allowed commands already read
        if (allowed == null)
            allowed = getAllowedCommands(classname);

        //	If no cmd allowed returns false
        if (allowed.length == 0)
            return false;

        //	Else, check is specified one is allowed
        for (String anAllowed : allowed)
            if (anAllowed.toLowerCase().equals(cmd.toLowerCase()))
                return true;
        return false;
    }
    //===============================================================
    /**
     * query access device to know allowed commands for the device and for a specified class
     *
     * @param classname specified class name
     * @return allowed command list fod specifid class.
     */
    //===============================================================
    protected String[] getAllowedCommands(String classname) {
        try {
            DeviceData argin = new DeviceData();
            argin.insert(classname);
            DeviceData argout = command_inout("GetAllowedCommands", argin);
            String[] allowed = argout.extractStringArray();
            allowed_cmd_table.put(classname, allowed);
            return allowed;
        } catch (DevFailed e) {
            String[] dummy = new String[0];
            allowed_cmd_table.put(classname, dummy);
            return dummy;
        }
    }
    //===============================================================
    //===============================================================
}
