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
//-======================================================================

package fr.esrf.TangoApi;

import java.util.Hashtable;



/**
 *	Class Description:
 *	@author  verdier
 *	@version  $Revision$
 */

public class DbRedundancy implements java.io.Serializable
{
	static private DbRedundancy	instance = null;
	static private Hashtable		map;
	
	private DbRedundancy()
	{
		map = new Hashtable();
	}
	
	static public DbRedundancy get_instance()
	{
		if (instance==null)
			instance = new DbRedundancy();
		return instance;
	}
	public void put(String th1, String th2)
	{
		map.put(th1, th2);
	}
	public String	get(String key)
	{
		return (String)map.get(key);
	}
}
