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


import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;



/**
 *	<b>Class Description:</b><Br>
 *	This class is a wrapper for TACO device.
 *	It is an interface between TANGO DeviceProxy and TACO device.
 *	It replace the fr.esrf.TacoApi.TacoDevice class using JNI library abd use true
 *	java classes found in Taco.jar (ESRF specific).
 *	
 * @author  verdier
 * @version	$Revision: 25296 $
 */


public interface ITacoTangoDeviceDAO
{

	//======================================================
	/**
	 *	Constructor for a Taco Device Object.
	 *
	 *	@param	devname	the device name
	 *	@param	nethost	the $NETHOST (where Taco database is)
	 */
	//======================================================
	public abstract void init(TacoTangoDevice tacoDevice, String devname, String nethost) throws DevFailed;
	//======================================================
	/**
	 *	Excute a command on a TACO device
	 *
	 *	@param	command	Command name to be executed on the device
	 *	@param	argin	Input command argument (Tango data)
	 *
	 *	@return	Output command argument (Tango data)
	 */
	//======================================================
	public abstract DeviceData command_inout(TacoTangoDevice tacoDevice, String command, DeviceData argin) throws DevFailed;
	//======================================================
	//======================================================
	public abstract CommandInfo[] commandListQuery(TacoTangoDevice tacoDevice) throws DevFailed;
	//======================================================
	//======================================================
	public abstract CommandInfo commandQuery(TacoTangoDevice tacoDevice, String cmdname) throws DevFailed;
	//======================================================
	/**
	 *	Execute the dev_rpc_protocol TACO command to change RPC protocol mode.
	 *
	 *	@param	mode RPC protocol mode to be seted 
	 *		(TangoApi.ApiDefs.<b>D_TCP</b> or ApiDefs.TacoTangoDevice.<b>D_TCP</b>).
	 */
	//======================================================
	public abstract void set_rpc_protocol(TacoTangoDevice tacoDevice, int mode)	throws DevFailed;
	//======================================================
	/**
	 *	Return the dev_rpc_protocol use by TACO device
	 *
	 *	@return	 RPC protocol used
	 *		(TangoApi.ApiDefs.<b>D_TCP</b> or ApiDefs.TacoTangoDevice.<b>D_TCP</b>).
	 */
	//======================================================
	public abstract int get_rpc_protocol(TacoTangoDevice tacoDevice)	throws DevFailed;
	//======================================================
	/**
	 *	Execute the dev_rpc_timeout TACO command to get RPC timeout.
	 */
	//======================================================
	public abstract int get_rpc_timeout(TacoTangoDevice tacoDevice)	throws DevFailed;
	//======================================================
	/**
	 *	Execute the dev_rpc_timeout TACO command to set RPC timeout.
	 */
	//======================================================
	public abstract void set_rpc_timeout(TacoTangoDevice tacoDevice, int millis)	throws DevFailed;
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
	 *	<li> Nethost name
	 */
	//==========================================================================
	public abstract String[] dev_inform(TacoTangoDevice tacoDevice) throws DevFailed;
	//==========================================================================
	//==========================================================================
	public abstract DbDatum[] get_property(TacoTangoDevice tacoDevice, String[] propnames) throws DevFailed;
	//==========================================================================
	/**
	 *	Set the device data source
	 *
	 *	@param src	new data source (CACHE_DEV, CACHE or DEV).
	 */
	//==========================================================================
	public abstract void set_source(TacoTangoDevice tacoDevice, DevSource src) throws DevFailed;
	//==========================================================================
	/**
	 *	returns the device data source
	 *
	 *	@return  data source (CACHE_DEV, CACHE or DEV).
	 */
	//==========================================================================
	public abstract DevSource get_source(TacoTangoDevice tacoDevice);
	//==========================================================================
	//==========================================================================







	//==========================================================================
	/*
	 *	Signal / Attribute management
	 */
	//==========================================================================

	//==========================================================================
	//==========================================================================
	public abstract String[] get_attribute_list(TacoTangoDevice tacoDevice) throws DevFailed;
	//======================================================
	//======================================================
	public abstract AttributeConfig[] get_attribute_config(TacoTangoDevice tacoDevice, String[] attrnames) throws DevFailed;
	//======================================================
	//======================================================
	public abstract DeviceAttribute[] read_attribute(TacoTangoDevice tacoDevice, String[] attrnames) throws DevFailed;

	//======================================================
	/*
	 *	Taco  <--> Tango  data convertion methods
	 */
	//======================================================

	//======================================================
	/**
	 *	Taco  --> Tango  data convertion method
	 *
	 *	@param	taco_type Taco data
	 *	@return the TANGO converted data
	 */
	//======================================================
	public abstract int tangoType(int taco_type);

	//======================================================
	/**
	 *	Taco  --> Tango  data convertion method
	 *
	 *	@param	taco_info Taco data
	 *	@return the TANGO converted data
     *  <ul>
	 *	    <li> Device name.
	 *	    <li> Class name
	 *	    <li> Device type
	 *	    <li> Device server name
	 *	    <li> Host name
     *  </ul>
	 */
	//======================================================
	public abstract String[] infoToTango(TacoTangoDevice tacoDevice, String taco_info);
	//======================================================
	//======================================================
}
