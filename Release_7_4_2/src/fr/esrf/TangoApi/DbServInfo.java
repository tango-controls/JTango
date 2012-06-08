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
// Revision 1.7  2009/01/16 12:50:02  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2003/09/08 11:02:34  pascal_verdier
// *** empty log message ***
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
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
//-======================================================================

package fr.esrf.TangoApi;

/**
 *	Class Description:
 *	This class describe server information.
 *
 * @author  verdier
 * @version  $Revision$
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
