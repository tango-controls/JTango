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
// Revision 1.7  2008/10/10 11:34:14  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.6  2008/04/11 08:08:44  pascal_verdier
// *** empty log message ***
//
// Revision 1.5  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.4  2006/06/08 08:04:40  pascal_verdier
// Bug on events if DS change host fixed.
//
// Revision 1.3  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//-======================================================================
/*
 * DbEventImportInfo.java
 *
 * Created on May 23, 2003, 11:52 AM
 */

package fr.esrf.TangoApi.events;
 

import fr.esrf.Tango.DevVarLongStringArray;

/**
 *
 * @author  pascal_verdier
 */
public class DbEventImportInfo implements java.io.Serializable {

	/**
	 *	channel_name
	 */
	public String	name;
	/**
	 *	ior connection as String.
	 */
	public String	channel_ior = null;    
	/**
	 *	host where notifd running
	 */
	public String	host = null;    
	/**
	 *	true if device is exported.
	 */
	public boolean	channel_exported;
        
      /** Creates a new instance of DbEventImportInfo */
    public DbEventImportInfo() 
    {
    }
	//===============================================
	/**
	 *	Complete constructor.
	 */
	//===============================================
	public DbEventImportInfo(DevVarLongStringArray info)
	{
		channel_ior      = new String(info.svalue[1]);
		channel_exported = (info.lvalue[0]==1);
		host             = info.svalue[3];
	}
    
	//===============================================
	/**
	 *	Complete constructor.
	 */
	//===============================================
	public DbEventImportInfo(String name, String host, boolean exported, String ior)
	{
		this.name        = name;
		this.host        = host;
		channel_exported = exported;
		channel_ior      = ior;
	}
}
