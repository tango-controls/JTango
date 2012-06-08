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
// Revision 3.2  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
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
 *	This class is an object containing the device information.
 *	It extends DeviceInfo object with more info.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DeviceInfo extends DbDevImportInfo implements java.io.Serializable
{
	/**
	 *	Date when the device has been exported last time;
	 */
	public String	last_exported;
	/**
	 *	Date when the device has been unexported last time;
	 */
	public String	last_unexported;
	//===============================================
	/**
	 *	Complete constructor.
	 */
	//===============================================
	public DeviceInfo(DevVarLongStringArray info)
	{
		super(info);
		if (info.svalue.length>5)
		{
			last_exported   = new String(info.svalue[5]);
			last_unexported = new String(info.svalue[6]);
		}
	}
	
	//===============================================
	//===============================================
	public String toString()
	{
		String	result = super.toString();
		result += "\nlast_exported:   " + last_exported;
		result += "\nlast_unexported: " + last_unexported;
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
			DeviceInfo	info =  db.get_device_info(devname);
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
