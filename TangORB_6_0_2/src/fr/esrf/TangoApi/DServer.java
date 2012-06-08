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
// Revision 1.2  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 1.1  2005/08/10 08:09:39  pascal_verdier
// Initial Revision.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;

import java.util.Hashtable;


/**
 *	Class Description:
 *	This class manage a static HashTable of admin device objects used for DeviceProxy.
 *
 * @author  verdier
 * @version  $Revision$
 */

class DServer
{
	static private DServer		instance = null;
	static private Hashtable 	adm_dev_table = null;


	//===================================================================
	//===================================================================
	private DServer()
	{
		adm_dev_table = new Hashtable();
	}
	//===================================================================
	//===================================================================
	static DServer get_instance()
	{
		if (instance==null)
			instance = new DServer();
		return instance;
	}
	//===================================================================
	//===================================================================
	DeviceProxy get_adm_dev(String devname) throws DevFailed
	{
		//	Check if already exists
		if (adm_dev_table.containsKey(devname))
			return (DeviceProxy) adm_dev_table.get(devname);
		else
		{
			//	Create a new one and store it before return
			DeviceProxy	dev = new DeviceProxy(devname);
			adm_dev_table.put(devname, dev);
			return dev;
		}
	}
	//===================================================================
	//===================================================================
}
