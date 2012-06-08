//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevCmdInfo;
import fr.esrf.Tango.DevCmdInfo_2;
import fr.esrf.Tango.DispLevel;

/**
 *	Class Description:
 *	This class manage data object for Tango device command information.
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *		CommandInfo	info = dev.command_query("ReadCurrent);<Br>
 *		System.out.print(info.cmd_name + "(" + info.in_type + ", " + 
 *										info.out_type + ")");	<Br></ul>
 *	</ul></i>
 *
 * @author  verdier
 * @version  $Revision$
 */

public class CommandInfo implements java.io.Serializable
{
	/**
	 *	Command name
	 */
	public String		cmd_name;
	/**
	 *	Diplay level DispLevel.OPERATORb or DispLevel.EXPERT
	 */
	public DispLevel	level = DispLevel.OPERATOR;
	/**
	 *	For future usage.
	 */
	public int			cmd_tag;
	/**
	 *	Input argument type
	 */
	public int			in_type;
	/**
	 *	Output argument type
	 */
	public int			out_type;
	/**
	 *	Input argument description
	 */
	public String		in_type_desc;
	/**
	 *	Output argument description
	 */
	public String		out_type_desc;

	//==============================================================
	/**
	 *	Constructor as an IDL DevCmdInfo object
	 */
	//==============================================================
    public CommandInfo(String cmd_name,
						int cmd_tag,
						int in_type,
						int out_type,
						String in_type_desc,
						String out_type_desc)
	{
		this.cmd_name = cmd_name;
		this.cmd_tag  = cmd_tag;
		this.in_type  = in_type;
		this.out_type = out_type;
		this.in_type_desc  = in_type_desc;
		this.out_type_desc = out_type_desc;
	}
	//==============================================================
	/**
	 *	Constructor as an IDL DevCmdInfo_2 object
	 */
	//==============================================================
    public CommandInfo(String cmd_name,
						DispLevel level,
						int cmd_tag,
						int in_type,
						int out_type,
						String in_type_desc,
						String out_type_desc)
	{
		this.cmd_name = cmd_name;
		this.level    = level;
		this.cmd_tag  = cmd_tag;
		this.in_type  = in_type;
		this.out_type = out_type;
		this.in_type_desc  = in_type_desc;
		this.out_type_desc = out_type_desc;
	}

	//==============================================================
	/**
	 *	Constructor from IDL DevCmdInfo object
	 */
	//==============================================================
    public CommandInfo(DevCmdInfo info)
	{
		this.cmd_name = info.cmd_name;
		this.cmd_tag  = info.cmd_tag;
		this.in_type  = info.in_type;
		this.out_type = info.out_type;
		this.in_type_desc  = info.in_type_desc;
		this.out_type_desc = info.out_type_desc;
	}
	//==============================================================
	/**
	 *	Constructor from IDL DevCmdInfo_2 object
	 */
	//==============================================================
    public CommandInfo(DevCmdInfo_2 info)
	{
		this.cmd_name = info.cmd_name;
		this.level    = info.level;
		this.cmd_tag  = info.cmd_tag;
		this.in_type  = info.in_type;
		this.out_type = info.out_type;
		this.in_type_desc  = info.in_type_desc;
		this.out_type_desc = info.out_type_desc;
	}



	//==============================================================
	//==============================================================
	public static String[]	TangoTypesArray = {
			"Tango::DEV_VOID",
			"Tango::DEV_BOOLEAN",
			"Tango::DEV_SHORT",
			"Tango::DEV_LONG",
			"Tango::DEV_FLOAT",
			"Tango::DEV_DOUBLE",
			"Tango::DEV_USHORT",
			"Tango::DEV_ULONG",
			"Tango::CONST_DEV_STRING",
			"Tango::DEV_STRING",

			"Tango::DEVVAR_CHARARRAY",
			"Tango::DEVVAR_SHORTARRAY",
			"Tango::DEVVAR_LONGARRAY",
			"Tango::DEVVAR_FLOATARRAY",
			"Tango::DEVVAR_DOUBLEARRAY",
			"Tango::DEVVAR_USHORTARRAY",
			"Tango::DEVVAR_ULONGARRAY",
			"Tango::DEVVAR_STRINGARRAY",
			"Tango::DEVVAR_LONGSTRINGARRAY",
			"Tango::DEVVAR_DOUBLESTRINGARRAY",
			"Tango::DEV_STATE"
		};
	//==============================================================
	//==============================================================
	public String toString()
	{
		return new String(cmd_name + "(" + TangoTypesArray[in_type] +
									", " +TangoTypesArray[out_type] + ")");
	}
}
