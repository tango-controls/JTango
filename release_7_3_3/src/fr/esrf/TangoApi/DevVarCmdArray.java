//+===========================================================================
// $Source$
//
// Project:   Tango API
//
// Description:  Java source for conversion between Tango/TACO library
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
// Revision 1.3  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.2  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2007/08/23 08:33:26  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:22  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
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
// Revision 1.2  2001/11/08 10:47:27  verdier
// Package added.
//
// Revision 1.1  2001/11/07 14:45:02  verdier
// Initial revision
//
//
//============================================================================

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
