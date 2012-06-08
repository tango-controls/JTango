//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AccessProxy class definition .
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
// Revision 1.7  2008/11/12 10:14:55  pascal_verdier
// Access control checked.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2008/10/10 08:34:34  pascal_verdier
// Security test have been done.
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.2  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.1  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
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
 * @author  verdier
 */

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;


class  AccessProxy extends DeviceProxy
{
	protected static String		user  = null;
	protected static String		host  = null;
	protected boolean			forced = false;
	/**
	 *	Device rights table
	 */
	protected Hashtable 		dev_right_table = null;
	/**
	 *	Allowed Commands foe a class tacle.
	 */
	protected Hashtable 		allowed_cmd_table = null;
	//===============================================================
	/**
	 *	Constructor for Access device proxy
	 *
	 *	@param devname	access device name
	 */
	//===============================================================
	AccessProxy(String devname) throws DevFailed
	{
		//	Build device proxy and check if present.
		super(devname, false);
		ping();

		//	Check if forced mode
		String	forced_str = System.getProperty("SUPER_TANGO");
		if (forced_str!=null)
			forced = (forced_str.equals("true"));
		dev_right_table = new Hashtable();
		allowed_cmd_table = new Hashtable();
	}
	//===============================================================
	/**
	 *	Check access for specified device
	 *
	 *	@param devname	device name to check access
	 */
	//===============================================================
	int checkAccessControl(String devname) throws DevFailed
	{
		if (forced) return TangoConst.ACCESS_WRITE;

		//	Check if alread tested.
		/*
		String	str = (String) dev_right_table.get(devname);
		if (str!=null)
			if (str.equals("write"))
				return TangoConst.ACCESS_WRITE;
			else
				return TangoConst.ACCESS_READ;
		*/

		try
		{
			//	If not already done check user and host names.
			if (user==null)
				user = System.getProperty("user.name").toLowerCase();
			if (host==null)
				host = InetAddress.getLocalHost().getHostAddress();
			DeviceData	argin = new DeviceData();
			argin.insert(new String[] { user, host, devname });

			//	Check for user and host rights on specified device
			String	rights = command_inout("GetAccess", argin).extractString();
			dev_right_table.put(devname, rights);
			if (rights.equals("write"))
				return TangoConst.ACCESS_WRITE;
			else
				return TangoConst.ACCESS_READ;
		}
		catch (DevFailed e)
		{
			if (e.errors[0].reason.equals("TangoApi_DEVICE_NOT_EXPORTED"))
				Except.re_throw_exception(e, 
					"TangoApi_CANNOT_CHECK_ACCESS_CONTROL",
					"Cannot import Access Control device !",
					"AccessProxy.checkAccessControl()");
			else
				throw e;
		}
		catch (UnknownHostException e)
		{
			//	In case of failure, returns always TangoConst.ACCESS_READ.
			System.out.println(e);
			return TangoConst.ACCESS_READ;
		}
		return TangoConst.ACCESS_READ;
	}
	//===============================================================
	/**
	 *	Check for specified device, the specified command is allowed.
	 *
	 *	@param	classname Specified class name.
	 *	@param	cmd Specified command name.
	 */
	//===============================================================
	boolean isCommandAllowed(String classname, String cmd)
	{
		String[]	allowed = (String[]) allowed_cmd_table.get(classname);

		//	Check if allowed commands already read
		if (allowed==null)
			allowed = getAllowedCommands(classname);

		//	If no cmd allowed returns false
		if (allowed.length==0)
			return false;

		//	Else, check is specified one is allowed
		for (String anAllowed : allowed)
			if (anAllowed.toLowerCase().equals(cmd.toLowerCase()))
				return true;
		return false;
	}
	//===============================================================
	/**
	 *	query access device to know allowed commands for the device
	 */
	//===============================================================
	protected String[] getAllowedCommands(String classname)
	{
		try
		{
			DeviceData	argin = new DeviceData();
			argin.insert(classname);
			DeviceData	argout = command_inout("GetAllowedCommands", argin);
			String[] allowed = argout.extractStringArray();
			allowed_cmd_table.put(classname, allowed);
			return allowed;
		}
		catch (DevFailed e)
		{
			String[]	dummy = new String[0];
			allowed_cmd_table.put(classname, dummy);
			return dummy;
		}
	}
	//===============================================================
	//===============================================================
}
