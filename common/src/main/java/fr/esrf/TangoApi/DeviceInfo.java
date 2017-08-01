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


import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;

/**
 * Class Description:
 * This class is an object containing the device information.
 * It extends DeviceInfo object with more info.
 *
 * @author verdier
 * @version $Revision: 25296 $
 */


public class DeviceInfo extends DbDevImportInfo implements java.io.Serializable {
    /**
     * Date when the device has been exported last time;
     */
    public String last_exported;
    /**
     * Date when the device has been unexported last time;
     */
    public String last_unexported;
    //===============================================
    /**
     * Complete constructor.
     */
    //===============================================
    public DeviceInfo(DevVarLongStringArray info) {
        super();
        name = info.svalue[0];
        ior = info.svalue[1];
        version = info.svalue[2];
        exported = (info.lvalue[0] == 1);
        if (info.lvalue.length > 1) pid = info.lvalue[1];

        //	Server has been added later
        if (info.svalue.length > 3)
            server = info.svalue[3];
        //	Host has been added later
        if (info.svalue.length > 4)
            hostname = info.svalue[4];

        if (info.svalue.length > 5) {
            last_exported = info.svalue[5];
            last_unexported = info.svalue[6];
        }
    }

    //===============================================
    //===============================================
    public String toString() {
        String result = super.toString();
        result += "\nlast_exported:   " + last_exported;
        result += "\nlast_unexported: " + last_unexported;
        return result;
    }

    //===============================================================
    //===============================================================
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Device name ?");
            System.exit(0);
        }
        try {
            String devname = args[0];
            Database db = ApiUtil.get_db_obj();
            DeviceInfo info = db.get_device_info(devname);
            System.out.println(info);
        } catch (DevFailed e) {
            if (args.length < 2 || args[1].equals("-no_exception") == false)
                fr.esrf.TangoDs.Except.print_exception(e);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
