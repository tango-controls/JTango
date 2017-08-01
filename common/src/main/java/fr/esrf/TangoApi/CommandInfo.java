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
 * @version  $Revision: 25296 $
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
