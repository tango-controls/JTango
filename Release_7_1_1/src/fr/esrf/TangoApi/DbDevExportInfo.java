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
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 3.8  2006/11/13 08:24:37  pascal_verdier
// Warnings removed.
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
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
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
//
//-======================================================================

package fr.esrf.TangoApi;




/**
 *	Class Description:
 *	This class is an object containing the exported device information.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbDevExportInfo implements java.io.Serializable
{
	/**
	 *	The devivce name.
	 */
	public String	name;
	/**
	 *	ior connection as String.
	 */
	public String	ior;
	/**
	 *	Host name where device will be exported.
	 */
	public String	host;
	/**
	 *	TANGO protocol version number.
	 */
	public String	version;

	//===============================================
	/**
	 *	Default constructor.
	 */
	//===============================================
	public DbDevExportInfo()
	{
	}
	//===============================================
	/**
	 *	Complete constructor (pid does not exit in java).
	 */
	//===============================================
	public DbDevExportInfo(String name, String ior,
										String host, String version)
	{
		this.name     = name;
		this.ior      = ior;
		this.host     = host;
		this.version  = version;
	}
	//===============================================
	/**
	 *	Serialise object data to a string array data.
	 */
	//===============================================
	public String[] toStringArray()
	{
		String[]	argout;
		argout = new String[5];
		int	i=0;
		argout[i++] = name;
		argout[i++] = ior;
		argout[i++] = host;
		argout[i++] = version;
		argout[i] = "0";
		return argout;
	}
}
