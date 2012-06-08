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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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
	 */
	//===============================================
	public DbDevImportInfo(DevVarLongStringArray info)
	{
		name     = new String(info.svalue[0]);
		ior      = new String(info.svalue[1]);
		version  = new String(info.svalue[2]);
		exported = (info.lvalue[0]==1);
		if (info.lvalue.length>1)	pid = info.lvalue[1];

		//	Server has been added later
		if (info.svalue.length>3)
			server = new String(info.svalue[3]);
		is_taco = (ior.startsWith("rpc:"));
	}
	//===============================================
	/**
	 *	Complete constructor.
	 */
	//===============================================
	public DbDevImportInfo(String[] taco_info)
	{
		is_taco = true;
		if (taco_info.length<6)
		{
			//	Not full -> is an error
			this.taco_info = "";
			for (int i=0 ; i<taco_info.length ; i++)
				this.taco_info += taco_info[i] + "\n";
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
				sb.append(e.errors[i].reason + " from " + e.errors[i].origin + "\n");
				sb.append(e.errors[i].desc + "\n");
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
        try
		{
			String		devname = args[0];
			Database	db = ApiUtil.get_db_obj();
			DbDevImportInfo	info =  db.import_device(devname);
			System.out.println(info);
		}
		catch(DevFailed e)
		{
			if (args.length<2 || args[1].equals("-no_exception")==false)
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
