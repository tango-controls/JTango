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
// $Revision: 30274 $
//
//-======================================================================


package fr.esrf.TangoApi;


import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarLongStringArray;

/**
 * Class Description:
 * This class is an object containing the imported device information.
 *
 * @author verdier
 * @version $Revision: 30274 $
 */


public class DbDevImportInfo implements java.io.Serializable {
    /**
     * The device name.
     */
    public String name = null;
    /**
     * ior connection as String.
     */
    public String ior = null;
    /**
     * TANGO protocol version number.
     */
    public String version = null;
    /**
     * true if device is exported.
     */
    public boolean exported;
    /**
     * Server PID (if not a java program).
     */
    public int pid = 0;
    /**
     * Server name and intance name
     */
    public String server = "unknown";
    /**
     * Host name where server is running
     */
    public String hostname = "unknown";
    /**
     * Host name where server is running
     */
    public String classname = "unknown";
    /**
     * is a TACO device (rpc and not ior)
     */
    public boolean is_taco = false;

    public String taco_info;

    //===============================================

    /**
     * Default constructor.
     */
    //===============================================
    public DbDevImportInfo() {
    }
    //===============================================

    /**
     * Complete constructor.
     *
     * @param name      device name
     * @param exported  true if exported
     * @param version   IDL version
     * @param ior       IOR string
     * @param server    server name for specified device
     * @param hostname  Host name where server is (or have been) running
     * @param classname class name of specified device.
     */
    //===============================================
    public DbDevImportInfo(String name, boolean exported,
                           String version, String ior, String server, String hostname, String classname) {
        this.name = name;
        this.ior = ior;
        this.exported = exported;
        this.version = version;
        this.server = server;
        this.hostname = hostname;
        this.classname = classname;
    }
    //===============================================

    /**
     * Complete constructor.
     *
     * @param info info returned by Database server
     */
    //===============================================
    public DbDevImportInfo(DevVarLongStringArray info) {
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
        //	Class has been added later
        if (info.svalue.length > 5)
            classname = info.svalue[5];
        is_taco = (ior.startsWith("rpc:"));
    }
    //===============================================

    /**
     * Complete constructor.
     *
     * @param taco_info info from taco database
     */
    //===============================================
    public DbDevImportInfo(String[] taco_info) {
        is_taco = true;
        if (taco_info.length < 6) {
            //	Not full -> is an error
            this.taco_info = "";
            for (String info : taco_info)
                this.taco_info += info + "\n";
        } else {
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
    public String toString() {
        String result;
        if (is_taco) {
            result = taco_info;
        } else
            try {
                //	Return info in ior
                IORdump id = new IORdump(name, ior);
                result = id.toString();
                result += "\nServer:          " + server;
                if (classname != null && !classname.equals("unknown"))
                    result += "\nClass:           " + classname;
                if (pid != 0)
                    result += "\nServer PID:      " + pid;
                result += "\nExported:        " + exported;

            } catch (DevFailed e) {
                //	return full exception string
                StringBuilder sb = new StringBuilder(e.toString() + ":\n");
                for (int i = 0; i < e.errors.length; i++) {
                    sb.append(e.errors[i].reason).append(" from ");
                    sb.append(e.errors[i].origin).append("\n");
                    sb.append(e.errors[i].desc).append("\n");
                    if (i < e.errors.length - 1)
                        sb.append("-------------------------------------------------------------\n");
                }
                result = sb.toString();
            }
        return result;
    }

    //===============================================================
//===============================================================
    public static void main(String[] args) {
        //noinspection ProhibitedExceptionCaught
        try {
            String devname = args[0];
            Database db = ApiUtil.get_db_obj();
            DbDevImportInfo info = db.import_device(devname);
            System.out.println(info);
        } catch (DevFailed e) {
            if (args.length < 2 || !args[1].equals("-no_exception"))
                fr.esrf.TangoDs.Except.print_exception(e);
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Device name ?");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
