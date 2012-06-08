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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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
