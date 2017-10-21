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

import fr.esrf.Tango.DevFailed;

import java.util.Hashtable;

/**
 * Class Description: This class manage a static vector of DeviceProxy object
 * to have a single connection on each device. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> DeviceProxy	dev = DeviceProxyFactory.get_instance(deviceName); <Br>
 * </ul>
 * </i>
 *
 * @author verdier
 * @version $Revision: 25483 $
 */

public class DeviceProxyFactory {

    private static Hashtable<String, DeviceProxy> proxy_table =
            new Hashtable<String, DeviceProxy>();

    //===================================================================
    /**
     * DeviceProxy single connection management.
     * If it does not already exist, create it.
     * Else get it from table and return it.
     *
     * @param deviceName Device name to be created or get.
     * @return the DeviceProxy object
     * @throws DevFailed if DeviceProxy creation failed
     */
    //===================================================================
    public static DeviceProxy get(String deviceName) throws DevFailed {
        //	Get full device name (with tango host) to manage multi tango_host
        String fullDeviceName;
        if (deviceName.startsWith("tango:"))
            fullDeviceName = deviceName;
        else
            fullDeviceName = new TangoUrl(deviceName).toString().toLowerCase();

        String tangoHost;
        if (deviceName.contains("dbase=no"))
            tangoHost = "";
        else
            tangoHost = ApiUtil.get_default_db_obj().getUrl().getTangoHost();
        return get(fullDeviceName, tangoHost);
    }
    //===================================================================
    /**
     * DeviceProxy single connection management.
     * If it does not already exist, create it.
     * Else get it from table and return it.
     *
     * @param deviceName Device name to be created or get.
     * @param tangoHost to build the url (full device name).
     * @return the DeviceProxy object
     * @throws DevFailed if DeviceProxy creation failed
     */
    //===================================================================
    public static DeviceProxy get(String deviceName, String tangoHost) throws DevFailed {
        //	Get full device name (with tango host) to manage multi tango_host
        String fullDeviceName;
        if (deviceName.startsWith("tango://") || deviceName.startsWith("//"))
            fullDeviceName = deviceName;
        else
            fullDeviceName = "tango://"+tangoHost + "/"+deviceName;

        //	Get it if already exists
        DeviceProxy dev =  proxy_table.get(fullDeviceName);
        if (dev == null) {
            try {
                //	Else create it.
                dev = new DeviceProxy(deviceName);
                proxy_table.put(fullDeviceName, dev);
            }
            catch(DevFailed e) {
                e.printStackTrace();
                throw e;
            }
        }
        return dev;
    }
    //===================================================================
    /**
     * DeviceProxy single connection management.
     * returns true it does already exist
     *
     * @param deviceName Device name to check if exists.
     * @return true it does already exist
     * @throws DevFailed if url is not correct
     */
    //===================================================================
    public static boolean exists(String deviceName) throws DevFailed {
        //	Get full device name (with tango host) to manage multi tango_host
        String fullDeviceName = new TangoUrl(deviceName).toString();

        //	Get it if already exists
        DeviceProxy dev = proxy_table.get(fullDeviceName);
        return (dev != null);
    }
    //===================================================================
    /**
     * Remove the specified DeviceProxy in table.
     *
     * @param deviceName Device name to be removed.
     */
    //===================================================================
    public static void remove(String deviceName) {
        try {
            //	Get full device name (with tango host)
            String fullDeviceName = new TangoUrl(deviceName).toString();

            //	Check if already exists before remove.
            if (proxy_table.containsKey(fullDeviceName))
                proxy_table.remove(fullDeviceName);
        } catch (DevFailed e) {
            //	Not serious. Display only warning
            System.err.println(e.errors[0].desc);
        }
    }
    //===================================================================
    /**
     * Remove the specified DeviceProxy in table.
     *
     * @param dev Device to be removed.
     */
    //===================================================================
    public static void remove(DeviceProxy dev) {
        //	Get full device name (with tango host)
        String fullDeviceName = dev.url.toString();

        //	Check if already exists before remove.
        if (proxy_table.containsKey(fullDeviceName))
            proxy_table.remove(fullDeviceName);
    }

    //===================================================================
    /**
     * Add a new device proxy in table
     * @param dev the DeviceProxy to store in table. 
     */
    //===================================================================
    static void add(DeviceProxy dev) {
        //	Get full device name (with tango host) to manage multi tango_host
        String fullDeviceName = dev.url.toString();

        //	Get it if already exists
        DeviceProxy tmp_dev = proxy_table.get(fullDeviceName);
        if (tmp_dev == null) {
            //System.out.println("Create " + fullDeviceName);
            proxy_table.put(fullDeviceName, dev);    //	else add it
        }
    }
    //===================================================================
    //===================================================================
}
