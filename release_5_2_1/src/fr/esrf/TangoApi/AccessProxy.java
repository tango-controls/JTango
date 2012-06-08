//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AccessProxy class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.2  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.1  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
//
// Copyleft 2006 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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
 * @Revision 
 */
 
import fr.esrf.Tango.*;
import fr.esrf.TangoDs.*;
import fr.esrf.TangoApi.*;

import java.io.*;
import java.util.*;

import java.net.InetAddress;
import java.net.UnknownHostException;


class  AccessProxy extends DeviceProxy
{
	protected static String		user  = null;
	protected static String		host  = null;
	protected boolean			forced = false;
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
		String	forced_str = System.getProperty("SUPER_USER");
		if (forced_str!=null)
			forced = (forced_str.equals("true"));
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
	 *	@param	devname Specified device name.
	 *	@param	cmd Specified command name.
	 */
	//===============================================================
	boolean isCommandAllowed(String devname, String cmd)
	{
		String[]	allowed = (String[]) allowed_cmd_table.get(devname);

		//	Check if allowed commands already read
		if (allowed==null)
			allowed = getAllowedCommands(devname);

		//	If no cmd allowed returns false
		if (allowed.length==0)
			return false;

		//	Else, check is specified one is allowed
		for (int i=0 ; i<allowed.length ; i++)
			if (allowed[i].toLowerCase().equals(cmd.toLowerCase()))
				return true;
		return false;
	}
	//===============================================================
	/**
	 *	query access device to know allowed commands for the device
	 */
	//===============================================================
	protected String[] getAllowedCommands(String devname)
	{
		try
		{
			DeviceData	argin = new DeviceData();
			argin = new DeviceData();
			argin.insert(devname);
			DeviceData	argout = command_inout("GetAllowedCommands", argin);
			String[] allowed = argout.extractStringArray();
			allowed_cmd_table.put(devname, allowed);
			return allowed;
		}
		catch (DevFailed e)
		{
			String[]	dummy = new String[0];
			allowed_cmd_table.put(devname, dummy);
			return dummy;
		}
	}
	//===============================================================
	//===============================================================
}
