//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012
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
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class TangORBversion implements java.io.Serializable {
    /**
     * Tango version compatibility.
     */
    public String Tango = null;
    /**
     * Java Api version (TangORB version).
     */
    public String api = null;
    /**
     * JacORB package version used.
     */
    public String JacORB = null;
    /**
     * ZMQ package version used.
     */
    public String ZMQ = null;
    /**
     * logkit package version used.
     */
    public String logkit = null;
    /**
     * avalon package version used.
     */
    public String avalon = null;

    public String jarfile;
    private static final String[] packages = {
            "API",
            "Tango",
            "JacORB",
            "ZMQ",
            "logkit",
            "avalon_framework",
    };

    //========================================================================
    /*
      *	Constructor analysing classpath to find TangORB jar file.
      */
    //========================================================================
    public TangORBversion() throws DevFailed, IOException {
        //	Get classpath from environment
        String classpath = System.getProperty("java.class.path");
        String separator = System.getProperty("path.separator");

        //	Parse for TangORB jar file path
        String target = "TangORB";
        int start, end;
        if ((start = classpath.indexOf(target)) < 0)
            Except.throw_exception("TangORB_NotFound",
                    "TangORB jar file not found in CLASSPATH",
                    "TangORBversion.TangORBversion()");
        if ((start = classpath.lastIndexOf(separator, start)) < 0)
            start = 0;
        else
            start++;

        //	Search end
        if ((end = classpath.indexOf(separator, start)) < 0)
            jarfile = classpath.substring(start);
        else
            jarfile = classpath.substring(start, end);
        //System.out.println(jarfile);

        //	And init Object from jar file
        initObject();
    }

    //========================================================================
    /*
      *	Constructor initialising object with jar file passed.
      *	@param filename jar file to initialise object.
      */
    //========================================================================
    public TangORBversion(String filename) throws DevFailed, IOException {
        jarfile = filename;
        initObject();
    }

    //========================================================================
    /*
      *	Read jar file manifest and fill fields.
      */
    //========================================================================
    private void initObject() throws DevFailed, IOException {
        //	Check if file exists
        if (!new File(jarfile).exists())
            Except.throw_exception("FileNotFoundException",
                    jarfile + " No such file or directory",
                    "TangORBversion.initObject()");

        // Retrieve the manifest file from the jar file
        JarFile jf = new JarFile(jarfile);
        Manifest manif = jf.getManifest();

        //	Retrieve the Tango-Version attribute in the Manifest file
        //	and print the version number (if defined)
        Attributes attr = manif.getMainAttributes();

        //	Get max length
        int max_length = 0;
        for (String pack : packages)
            if (pack.length() > max_length)
                max_length = pack.length();

        //	Display package versions
        for (int i = 0; i < packages.length; i++) {
            String target = packages[i] + "-Version";
            String vers = attr.getValue(target);
            /*
               if (vers == null)
                   Except.throw_exception("TangORB_ManifestError",
                               target + " version not found in manifest.",
                               "TangORBversion.initObject()");
               else
               */
            {
                switch (i) {
                    case 0:
                        api = vers.trim();
                        break;
                    case 1:
                        Tango = vers.trim();
                        break;
                    case 2:
                        JacORB = vers.trim();
                        break;
                    case 3:
                        ZMQ = vers.trim();
                        break;
                    case 4:
                        logkit = vers.trim();
                        break;
                    case 5:
                        avalon = vers.trim();
                        break;
                }
            }
        }
    }

    //========================================================================
    //========================================================================
    public String toString() {
        //	Get max length
        int max_length = 0;
        for (String pack : packages)
            if (pack.length() > max_length)
                max_length = pack.length();
        String versStr = " version";
        max_length += versStr.length() + 3;

        //	Display package versions
        String str = "";
        for (int i = 0; i < packages.length; i++) {
            str += packages[i] + versStr;
            for (int j = packages[i].length() + versStr.length();
                 j < max_length; j++)
                str += ".";
            switch (i) {
                case 0:
                    str += api;
                    break;
                case 1:
                    str += Tango;
                    break;
                case 2:
                    str += JacORB;
                    break;
                case 3:
                    str += ZMQ;
                    break;
                case 4:
                    str += logkit;
                    break;
                case 5:
                    str += avalon;
                    break;
            }
            if (i < packages.length - 1)
                str += "\n";
        }
        return str;
    }

    //========================================================================
    //========================================================================
    public static void main(String[] args) {
        try {
            TangORBversion tangORB;
            if (args.length > 0)
                tangORB = new TangORBversion(args[0]);
            else
                tangORB = new TangORBversion();
            System.out.println(tangORB);
        } catch (DevFailed e) {
            Except.print_exception(e);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        }
        System.exit(0);
    }
}
