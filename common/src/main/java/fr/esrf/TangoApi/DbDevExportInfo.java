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


package fr.esrf.TangoApi;


import org.tango.client.database.DeviceExportInfo;

/**
 * Class Description:
 * This class is an object containing the exported device information.
 *
 * @author verdier
 * @version $Revision: 25296 $
 * @deprecated use {@link org.tango.client.database.DeviceExportInfo}
 */

@Deprecated
public class DbDevExportInfo {
    private final DeviceExportInfo value;
    //===============================================

    /**
     * Complete constructor (pid does not exit in java).
     *
     * @param name    device name
     * @param ior     IOR found in database
     * @param host    host wher running (or have been running)
     * @param version IDL revision
     */
    //===============================================
    public DbDevExportInfo(String name, String ior,
                           String host, String version) {
        this.value = new DeviceExportInfo(name, ior, host, version, "null", "null");
    }
    //===============================================

    /**
     * Serialise object data to a string array data.
     *
     * @return a description as a String array.
     */
    //===============================================
    public String[] toStringArray() {
        return value.toStringArray();
    }

    public DeviceExportInfo asDeviceExportInfo() {
        return value;
    }
}
