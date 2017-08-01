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
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoApi;




import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;


public class DevVarCmdArray
{
	/**
	 *	manage an array of CommandInfo objects.
	 */
	protected CommandInfo[]	cmd_info;
	/**
	 *	The device name
	 */
	protected String	devname;
	
	//======================================================
	//======================================================
	public DevVarCmdArray(String devname, DevCmdInfo[] info)
	{
		this.devname = devname;
		cmd_info = new CommandInfo[info.length];
		for (int i=0 ; i<info.length ; i++)
			cmd_info[i] = new CommandInfo(info[i]);
	}
	//======================================================
	//======================================================
	public DevVarCmdArray(String devname, CommandInfo[] info)
	{
		this.devname  = devname;
		this.cmd_info = info;
	}
	
	//======================================================
	//======================================================
	public int size()
	{
		return cmd_info.length;
	}
	//======================================================
	//======================================================
	public CommandInfo elementAt(int i)
	{
		return cmd_info[i];
	}
	
	//======================================================
	//======================================================
	public CommandInfo[] getInfoArray()
	{
		return cmd_info;
	}
	//======================================================
	//======================================================
	public int argoutType(String cmdname) throws DevFailed
	{
		for (CommandInfo info : cmd_info)
			if (cmdname.equals(info.cmd_name))
				return info.out_type;

		Except.throw_non_supported_exception("TACO_CMD_UNAVAILABLE",
				cmdname + " command unknown for device " + devname,
				"DevVarCmdArray.argoutType()");
		return -1;
	}
	//======================================================
	//======================================================
	public int arginType(String cmdname) throws DevFailed
	{
		for (CommandInfo info : cmd_info)
			if (cmdname.equals(info.cmd_name))
				return info.in_type;
		Except.throw_non_supported_exception("TACO_CMD_UNAVAILABLE",
				cmdname + " command unknown for device " + devname,
				"DevVarCmdArray.arginType()");
		return -1;
	}
	//======================================================
	//======================================================
	public String toString()
	{
		StringBuffer	sb = new StringBuffer();
		for (CommandInfo info : cmd_info)
		{
			sb.append(info.cmd_name);
			sb.append("(").append(info.in_type);
			sb.append(", ").append(info.out_type).append(")\n");
		}
		return sb.toString();
	}
}
