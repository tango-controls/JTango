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
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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
