//+===========================================================================
// $Source$
//
// Project:   Tango API
//
// Description:  Java source for conversion between Tango/TACO library
//
// $Author$
//
// $Revision$
//
// $Log$
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
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-===========================================================================
//         (c) - Software Engineering Group - ESRF
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
		for (int i=0 ; i<cmd_info.length ; i++)
			if (cmdname.equals(cmd_info[i].cmd_name))
				return cmd_info[i].out_type;

		Except.throw_non_supported_exception("TACO_CMD_UNAVAILABLE",
				new String (cmdname + " command unknown for device " + devname),
				"DevVarCmdArray.argoutType()");
		return -1;
	}
	//======================================================
	//======================================================
	public int arginType(String cmdname) throws DevFailed
	{
		for (int i=0 ; i<cmd_info.length ; i++)
			if (cmdname.equals(cmd_info[i].cmd_name))
				return cmd_info[i].in_type;
		Except.throw_non_supported_exception("TACO_CMD_UNAVAILABLE",
				new String (cmdname + " command unknown for device " + devname),
				"DevVarCmdArray.arginType()");
		return -1;
	}
	//======================================================
	//======================================================
	public String toString()
	{
		StringBuffer	sb = new StringBuffer();
		for (int i=0 ; i<cmd_info.length ; i++)
		{
			sb.append(cmd_info[i].cmd_name);
			sb.append("(" + cmd_info[i].in_type);
			sb.append(", "+ cmd_info[i].out_type + ")\n");
		}
		return sb.toString();
	}
}
