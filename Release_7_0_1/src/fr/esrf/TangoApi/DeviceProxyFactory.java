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
// Revision 1.1  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
//
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;

import java.util.Hashtable;

/**
 * Class Description: This class manage a static vector of DeviceProxy object
 *		to have a single connection on each device. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> DeviceProxy	dev = DeviceProxyFactory.get_instance(devname); <Br>
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision$
 */

public class DeviceProxyFactory
{

    private static Hashtable proxy_table = new Hashtable();
	private static DeviceProxyFactory	instance = null;
	//===================================================================
	//===================================================================
	private DeviceProxyFactory()
	{
	}
	//===================================================================
	/**
	 *	Returns an instance of this singleton
	 */
	//===================================================================
	public static DeviceProxyFactory get_instance()
	{
		if (instance==null)
			instance = new DeviceProxyFactory();
		return instance;
	}


	//===================================================================
	/**
	 *	DeviceProxy single connection management.
	 *	If it does not already exist, create it.
	 *	Elese get it from table and return it.
	 *	@param devname Device name to be created or get.
	 */
	//===================================================================
	public static DeviceProxy get(String devname) throws DevFailed
	{
		//	Get full device name (with tango host) to manage multi tango_host
		String	full_devname = new TangoUrl(devname).toString().toLowerCase();

		//	Get it if already exists
		DeviceProxy	dev = (DeviceProxy)proxy_table.get(full_devname);
		if (dev==null)
		{
			//	Eles create it.
			dev = new DeviceProxy(devname);
			proxy_table.put(full_devname, dev);
		}
		return dev;
	}
	//===================================================================
	/**
	 *	DeviceProxy single connection management.
	 *	returns true it does already exist
	 *	@param devname Device name to check if exists.
	 */
	//===================================================================
	public static boolean exists(String devname) throws DevFailed
	{
		//	Get full device name (with tango host) to manage multi tango_host
		String	full_devname = new TangoUrl(devname).toString();

		//	Get it if already exists
		DeviceProxy	dev = (DeviceProxy)proxy_table.get(full_devname);
		return (dev!=null);
	}
	//===================================================================
	/**
	 *	Remove the specified DeviceProxy in table.
	 *	@param devname Device name to be removed.
	 */
	//===================================================================
	public static void remove(String devname)
	{
		try
		{
			//	Get full device name (with tango host)
			String	full_devname = new TangoUrl(devname).toString();

			//	Check if already exists before remove.
			if (proxy_table.containsKey(full_devname))
				proxy_table.remove(full_devname);
		}
		catch(DevFailed e)
		{
			//	Not serious. Display only warning
			System.err.println(e.errors[0].desc);
		}
	}
	//===================================================================
	/**
	 *	Remove the specified DeviceProxy in table.
	 *	@param dev Device to be removed.
	 */
	//===================================================================
	public static void remove(DeviceProxy dev)
	{
		//	Get full device name (with tango host)
		String	full_devname = dev.url.toString();

		//	Check if already exists before remove.
		if (proxy_table.containsKey(full_devname))
			proxy_table.remove(full_devname);
	}
	//===================================================================
	//===================================================================
	static void add(DeviceProxy dev)
	{
		//	Get full device name (with tango host) to manage multi tango_host
		String	full_devname = dev.url.toString();

		//	Get it if already exists
		DeviceProxy	tmp_dev = (DeviceProxy)proxy_table.get(full_devname);
		if (tmp_dev==null)
			proxy_table.put(full_devname, dev);	//	else add it
	}
	//===================================================================
	//===================================================================
}
