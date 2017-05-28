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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;


import fr.esrf.Tango.AttributeConfig;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevSource;
import fr.esrf.Tango.factory.TangoFactory;


/**
 * <b>Class Description:</b><Br>
 * This class is a wrapper for TACO device.
 * It is an interface between TANGO DeviceProxy and TACO device.
 * It replace the fr.esrf.TacoApi.TacoDevice class using JNI library abd use true
 * java classes found in Taco.jar (ESRF specific).
 *
 * @author verdier
 * @version $Revision: 25296 $
 */


public class TacoTangoDevice {
    private ITacoTangoDeviceDAO tacoDevice = null;

    /**
     * the device name
     */
    private String deviceName;

    /**
     * the taco device proxy
     */
    private Object taco_proxy;    //	this is a fr.esrf.TacoApi.TacoDevice object
    //	but  fr.esrf.TacoApi objects are unknown.

    /**
     * Attribute config object
     */
    private AttributeConfig[] att_config = null;

    //======================================================
    /**
     * Constructor for a Taco Device Object.
     *
     * @param    deviceName    the device name
     * @param    netHost    the $NETHOST (where Taco database is)
     */
    //======================================================
    TacoTangoDevice(String deviceName, String netHost) throws DevFailed {
        tacoDevice = TangoFactory.getSingleton().getTacoTangoDeviceDAO();
        tacoDevice.init(this, deviceName, netHost);
    }
    //======================================================
    /**
     * Excute a command on a TACO device
     *
     * @param    command    Command name to be executed on the device
     * @param    argin    Input command argument (Tango data)
     * @return Output command argument (Tango data)
     */
    //======================================================
    DeviceData command_inout(String command, DeviceData argin) throws DevFailed {
        return tacoDevice.command_inout(this, command, argin);
    }

    //======================================================
    //======================================================
    CommandInfo[] commandListQuery() throws DevFailed {
        return tacoDevice.commandListQuery(this);
    }

    //======================================================
    //======================================================
    CommandInfo commandQuery(String cmdname) throws DevFailed {
        return tacoDevice.commandQuery(this, cmdname);
    }
    //======================================================
    /**
     * Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
     *
     * @param    mode RPC protocol mode to be seted
     * (TangoApi.ApiDefs.<b>D_TCP</b> or ApiDefs.TacoTangoDevice.<b>D_TCP</b>).
     */
    //======================================================
    void set_rpc_protocol(int mode) throws DevFailed {
        tacoDevice.set_rpc_protocol(this, mode);
    }
    //======================================================
    /**
     * Return the dev_rpc_protocol use by TACO device
     *
     * @return RPC protocol used
     * (TangoApi.ApiDefs.<b>D_TCP</b> or ApiDefs.TacoTangoDevice.<b>D_TCP</b>).
     */
    //======================================================
    int get_rpc_protocol() throws DevFailed {
        return tacoDevice.get_rpc_protocol(this);
    }
    //======================================================
    /**
     * Execute the dev_rpc_timeout TACO command to get RPC timeout.
     */
    //======================================================
    int get_rpc_timeout() throws DevFailed {
        return tacoDevice.get_rpc_timeout(this);
    }
    //======================================================
    /**
     * Execute the dev_rpc_timeout TACO command to set RPC timeout.
     */
    //======================================================
    void set_rpc_timeout(int millis) throws DevFailed {
        tacoDevice.set_rpc_timeout(this, millis);
    }
    //==========================================================================
    /**
     * Returns  TACO device information.
     *
     * @return TACO device information as String array.
     *         <li> Device name.
     *         <li> Class name
     *         <li> Device type
     *         <li> Device server name
     *         <li> Host name
     *         <li> Nethost name
     */
    //==========================================================================
    String[] dev_inform() throws DevFailed {
        return tacoDevice.dev_inform(this);
    }

    //==========================================================================
    //==========================================================================
    public DbDatum[] get_property(String[] propnames) throws DevFailed {
        return tacoDevice.get_property(this, propnames);
    }
    //==========================================================================
    /**
     * Set the device data source
     *
     * @param src new data source (CACHE_DEV, CACHE or DEV).
     */
    //==========================================================================
    void set_source(DevSource src) throws DevFailed {
        tacoDevice.set_source(this, src);
    }
    //==========================================================================
    /**
     * returns the device data source
     *
     * @return data source (CACHE_DEV, CACHE or DEV).
     */
    //==========================================================================
    DevSource get_source() {
        return tacoDevice.get_source(this);
    }
    //==========================================================================
    //==========================================================================


    //==========================================================================
    /*
	 *	Signal / Attribute management
	 */
    //==========================================================================

    //==========================================================================
    //==========================================================================
    String[] get_attribute_list() throws DevFailed {
        return tacoDevice.get_attribute_list(this);
    }

    //======================================================
    //======================================================
    AttributeConfig[] get_attribute_config(String[] attrnames) throws DevFailed {
        return tacoDevice.get_attribute_config(this, attrnames);
    }

    //======================================================
    //======================================================
    DeviceAttribute[] read_attribute(String[] attrnames) throws DevFailed {
        return tacoDevice.read_attribute(this, attrnames);
    }

    //======================================================
	/*
	 *	Taco  <--> Tango  data conversion methods
	 */
    //======================================================

    //======================================================
    /**
     * Taco  --> Tango  data convertion method
     *
     * @return the TANGO converted data
     * @param    taco_type Taco data
     */
    //======================================================
    int tangoType(int taco_type) {
        return tacoDevice.tangoType(taco_type);
    }

    //======================================================
    /**
     * Taco  --> Tango  data conversion method
     *
     * @return the TANGO converted data
     *         <ul>
     *         <li> Device name.
     *         <li> Class name
     *         <li> Device type
     *         <li> Device server name
     *         <li> Host name
     *         </ul>
     * @param    taco_info Taco data
     */
    //======================================================
    String[] infoToTango(String taco_info) {
        return tacoDevice.infoToTango(this, taco_info);
    }
    //======================================================
    //======================================================


    //======================================================
    //======================================================
    public AttributeConfig[] getAttrConfig() {
        return att_config;
    }

    //======================================================
    //======================================================
    public Object getTacoProxy() {
        return taco_proxy;
    }

    //======================================================
    //======================================================
    public String getDevName() {
        return deviceName;
    }

    //======================================================
    //======================================================
    public void setAttrConfig(AttributeConfig[] att_config) {
        this.att_config = att_config;
    }

    //======================================================
    //======================================================
    public void setTacoProxy(Object taco_proxy) {
        this.taco_proxy = taco_proxy;
    }

    //======================================================
    //======================================================
    public void setDevName(String deviceName) {
        this.deviceName = deviceName;
    }
}
