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
// Revision 1.9  2009/03/25 13:27:56  pascal_verdier
// ...
//
// Revision 1.8  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.7  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.6  2008/09/19 08:40:05  pascal_verdier
// hostname and classname fields added
//
// Revision 1.5  2007/09/13 09:22:31  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.9  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.8  2005/11/29 05:34:55  pascal_verdier
// TACO info added.
//
// Revision 3.7  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:22  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.2  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.1  2003/07/22 14:15:35  pascal_verdier
// DeviceData are now in-methods objects.
// Minor change for TACO-TANGO common database.
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
 

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;

/** 
 *	Class Description:
 *	This class is an object containing the imported device information.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbDevImportInfo implements java.io.Serializable
{
	/**
	 *	The devivce name.
	 */
	public String	name = null;
	/**
	 *	ior connection as String.
	 */
	public String	ior = null;
	/**
	 *	TANGO protocol version number.
	 */
	public String	version = null;
	/**
	 *	true if device is exported.
	 */
	public boolean	exported;
	/**
	 *	Server PID (if not a java program).
	 */
	public int	pid = 0;
	/**
	 *	Server name and intance name
	 */
	public String	server = "unknown";
	/**
	 *	Host name where server is running
	 */
	public String	hostname = "unknown";
	/**
	 *	Host name where server is running
	 */
	public String	classname = "unknown";
	/**
	 *	is a TACO device (rpc and not ior)
	 */
	public boolean	is_taco = false;
	
	public String	taco_info;

	//===============================================
	/**
	 *	Default constructor.
	 */
	//===============================================
	public DbDevImportInfo()
	{
	}
	//===============================================

    /**
     *	Complete constructor.
     *
     * @param name  device name
     * @param exported  true if exported
     * @param version   IDL version
     * @param ior       IOR string
     * @param server    server name for specified device
     * @param hostname  Host name where server is (or have been) running
     * @param classname class name of tspecified device.
     */
	//===============================================
	public DbDevImportInfo(String name, boolean exported,
			String version, String ior, String server, String hostname, String classname)
	{
		this.name = name;
		this.ior  = ior;
		this.exported  = exported;
		this.version   = version;
		this.server    = server;
		this.hostname  = hostname;
		this.classname = classname;
	}
	//===============================================
	/**
	 *	Complete constructor.
     * @param info  info returned by Database server
     */
	//===============================================
	public DbDevImportInfo(DevVarLongStringArray info)
	{
		name     = info.svalue[0];
		ior      = info.svalue[1];
		version  = info.svalue[2];
		exported = (info.lvalue[0]==1);
		if (info.lvalue.length>1)	pid = info.lvalue[1];

		//	Server has been added later
		if (info.svalue.length>3)
			server = info.svalue[3];
		//	Host has been added later
		if (info.svalue.length>4)
			hostname = info.svalue[4];
		//	Class has been added later
		if (info.svalue.length>5)
			classname = info.svalue[5];


		is_taco = (ior.startsWith("rpc:"));
	}
	//===============================================
	/**
	 *	Complete constructor.
     * @param taco_info info from taco database
     */
	//===============================================
	public DbDevImportInfo(String[] taco_info)
	{
		is_taco = true;
		if (taco_info.length<6)
		{
			//	Not full -> is an error
			this.taco_info = "";
			for (String info : taco_info)
				this.taco_info += info + "\n";
		}
		else
		{
			this.taco_info = 
			"Device:        " + taco_info[0] + "\n" +
			"type_id:       " + "taco:" + taco_info[2] + "\n" +
			"host:          " + taco_info[4] + "\n" +
			"Class:         " + taco_info[1] + "\n" +
			"Server:        " + taco_info[3] + "\n" +
			"NETHOST:       " + taco_info[5];
		}
	}
	//===============================================
	//===============================================
	public String toString()
	{
		String	result;
		if (is_taco)
		{
			result = taco_info;
		}
		else
		try
		{
			//	Return info in ior
			IORdump	id = new IORdump(name, ior);
			result = id.toString();
			result += "\nServer:          " + server;
            if (classname!=null)
            result += "\nClass:           " + classname;
			if (pid!=0)
				result += "\nServer PID:      " + pid;
			result += "\nExported:        " + exported;
			
		}
		catch (DevFailed e)
		{
			//	return full exception string
			//-----------------------------------
			StringBuffer	sb = new StringBuffer(e.toString() + ":\n");
			for (int i=0 ; i<e.errors.length ; i++)
			{
				sb.append(e.errors[i].reason).append(" from ");
				sb.append(e.errors[i].origin).append("\n");
				sb.append(e.errors[i].desc).append("\n");
				if (i<e.errors.length-1)
					sb.append("-------------------------------------------------------------\n");
			}
			result = sb.toString();
		}
		return result;
	}
//===============================================================
//===============================================================
//===============================================================
//===============================================================
    public static void  main(String[] args)
    {
		//noinspection ProhibitedExceptionCaught
		try
		{
			String		devname = args[0];
			Database	db = ApiUtil.get_db_obj();
			DbDevImportInfo	info =  db.import_device(devname);
			System.out.println(info);
		}
		catch(DevFailed e)
		{
			if (args.length<2 || !args[1].equals("-no_exception"))
				fr.esrf.TangoDs.Except.print_exception(e);
			System.exit(1);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Device name ?");
			System.exit(0);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
    }
}
