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

/**
 *	Class Description:
 *	This class describe server information.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
 */


public class DbServInfo implements java.io.Serializable
{
	/**
	 *	Server name.
	 */
	public String	name;
	/**
	 *	Host where registred.
	 */
	public String	host;
	/**
	 *	True if server is controlled by the TANGO manager (Astor).
	 */
	public boolean	controlled;
	/**
	 *	Level used for automatic startup.
	 *	No automatic startup if level is null or negative.
	 */
	public int	startup_level;

	//=========================================================
	/**
	 *	Default constructor.
	 *	@param	name	servername (i.e. "Starter/corvus").
	 */
	//=========================================================
	public DbServInfo(String name)
	{
		this.name          = name;
		this.host          = "";
		this.controlled     = false;
		this.startup_level = 0;
	}
	//=========================================================
	/**
	 *	Complete constructor.
	 *	@param	name		Server name (i.e. "Starter/corvus").
	 *	@param	host		Host name to register the server.
	 *	@param	controlled	True if server must controlled by
	 *							the TANGO manager (Astor).
	 *	@param	level		Level used for automatic startup.
	 *			No automatic startup if level is null or negative.
	 */
	//=========================================================
	public DbServInfo(String name, String host, boolean controlled, int level)
	{
		this.name          = name;
		this.host          = host;
		this.controlled    = controlled;
		this.startup_level = level;
	}
	//=========================================================
	/**
	 *	Default constructor.
	 *	@param	info	String array containing object field as String.
	 */
	//=========================================================
	public DbServInfo(String[] info)
	{
		//	Fixe default values
		//---------------------------
		this.name          = info[0];
		this.host          = "";
		this.controlled     = false;
		this.startup_level = 0;

		//	Set fields to array items if exist
		//-------------------------------------------
		if (info.length>1)
			this.host      = info[1];
		
		if (info.length>2)
		{
			try
			{
				Integer	i = new Integer(info[2]);
				this.controlled = (i==1);
			}
			catch(NumberFormatException e) {}
		}
		
		
		if (info.length>3)
		{
			try
			{
				this.startup_level= new Integer(info[3]);
			}
			catch(NumberFormatException e) {}
		}
	}
	//=========================================================
	/**
	 *	Set host name
	 */
	//=========================================================
	public void set_host_name(String hostname)
	{
		this.host = hostname;
	}
	//=========================================================
	/**
	 *	Dinsplay server info on a single line.
	 */
	//=========================================================
	public String toString()
	{
		StringBuffer	sb = new StringBuffer(name + "  (on " + host + ")  ");
		
		if (controlled)
			sb.append("controlled");
		else
			sb.append("not controlled");
		sb.append("   startup level: ").append(startup_level);
		return sb.toString();
	}
}
