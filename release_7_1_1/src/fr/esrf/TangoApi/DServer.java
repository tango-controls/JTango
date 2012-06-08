//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008
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
// Revision 1.1  2007/08/23 08:33:26  ounsy
// updated change from api/java
//
// Revision 1.2  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 1.1  2005/08/10 08:09:39  pascal_verdier
// Initial Revision.
//
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
