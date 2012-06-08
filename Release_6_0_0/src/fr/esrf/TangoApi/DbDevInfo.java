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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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
		this.name = new String(name);
		this._class = new String(_class);
		this.server = new String(server);
	}
	//===============================================
	/**
	 *	Complete information constructor.
	 */
	//===============================================
	public DbDevInfo(String name, String _class, String server, int type)
	{
		this.name = new String(name);
		this._class = new String(_class);
		this.server = new String(server);
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
