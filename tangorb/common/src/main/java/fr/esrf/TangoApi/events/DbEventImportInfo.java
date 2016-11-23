//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi.events;


import fr.esrf.Tango.DevVarLongStringArray;

/**
 * @author pascal_verdier
 */
public class DbEventImportInfo implements java.io.Serializable {

    /**
     * channel_name
     */
    public String name;
    /**
     * ior connection as String.
     */
    public String channel_ior = null;
    /**
     * host where notifd running
     */
    public String host = null;
    /**
     * true if device is exported.
     */
    public boolean channel_exported;

    /**
     * Creates a new instance of DbEventImportInfo
     */
    public DbEventImportInfo() {
    }
    //===============================================
    /**
     * Complete constructor.
     * @param info object containing information from database
     */
    //===============================================
    public DbEventImportInfo(DevVarLongStringArray info) {
        channel_ior = info.svalue[1];
        channel_exported = (info.lvalue[0] == 1);
        host = info.svalue[3];
    }

    //===============================================
    /**
     * Complete constructor.
     */
    //===============================================
    public DbEventImportInfo(String name, String host, boolean exported, String ior) {
        this.name = name;
        this.host = host;
        channel_exported = exported;
        channel_ior = ior;
    }
}
