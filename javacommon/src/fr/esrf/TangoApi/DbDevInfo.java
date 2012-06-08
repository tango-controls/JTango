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
// Revision 1.7  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:31  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:27  pascal_verdier
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
 *	Device information object.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbDevInfo implements java.io.Serializable
{
	/**
	 *	The device name.
	 */
	public String	name;
	/**
	 *	The class name.
	 */
	public String	_class;
	/**
	 *	The server name.
	 */
	public String	server;
	/**
	 *	The device type.
	 */
	public int		type = 0;

	//===============================================
	/**
	 *	Default constructor.
	 */
	//===============================================
	public DbDevInfo()
	{
	}
	//===============================================
	/**
	 *	Complete information constructor.
	 */
	//===============================================
	public DbDevInfo(String name, String _class, String server)
	{
		this.name   = name;
		this._class = _class;
		this.server = server;
	}
	//===============================================
	/**
	 *	Complete information constructor.
	 */
	//===============================================
	public DbDevInfo(String name, String _class, String server, int type)
	{
		this.name   = name;
		this._class = _class;
		this.server = server;
		this.type   = type;
	}
	//===============================================
	/**
	 *	Serialize object filed as string array.
	 */
	//===============================================
	public String[] toStringArray()
	{
		String[]	argout;
		argout = new String[3];
		argout[0] = server;
		argout[1] = name;
		argout[2] = _class;
		//argout[3] = "Undefined";
		return argout;
	}
}
