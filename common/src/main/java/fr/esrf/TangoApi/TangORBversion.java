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
// $Revision: 25896 $
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
     *  package version used.
     */
    public String slf4j = null;

    public String guava;
    public String javatuples;
    public String transmorph;
    public String cal10n;



    public String jarfile;
    private static final String[] packages = {
            "API",
            "Tango",
            "JacORB",
            "ZMQ",
            "slf4j",
            "guava",
            "javatuples",
            "transmorph",
            "cal10n",
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
        String target = "JTango";
        int start, end;
        if ((start=classpath.indexOf(target)) < 0) {
            target = "TangORB";
            if ((start=classpath.indexOf(target)) < 0)
                Except.throw_exception("TangORB_NotFound",
                        "TangORB jar file not found in CLASSPATH",
                        "TangORBversion.TangORBversion()");
        }

        if ((start = classpath.lastIndexOf(separator, start)) < 0)
            start = 0;
        else
            start++;

        //	Search end
        if ((end=classpath.indexOf(separator, start)) < 0)
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
                    jarfile + " No such file or directory");

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
            String version = attr.getValue(target);
            if (version != null) {
                switch (i) {
                    case 0:
                        api = version.trim();
                        break;
                    case 1:
                        Tango = version.trim();
                        break;
                    case 2:
                        JacORB = version.trim();
                        break;
                    case 3:
                        ZMQ = version.trim();
                        break;
                    case 4:
                        slf4j = version.trim();
                        break;
                    case 5:
                        guava = version.trim();
                        break;
                    case 6:
                        javatuples = version.trim();
                        break;
                    case 7:
                        transmorph = version.trim();
                        break;
                    case 8:
                        cal10n = version.trim();
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

        //	Display package versions
        StringBuilder sb = new StringBuilder();
        for (int i=0 ; i<packages.length ; i++) {
            switch (i) {
                case 0:
                    sb.append(buildVersion(packages[i], api, max_length));
                    break;
                case 1:
                    sb.append(buildVersion(packages[i], Tango, max_length));
                    break;
                case 2:
                    sb.append(buildVersion(packages[i],JacORB, max_length));
                    break;
                case 3:
                    sb.append(buildVersion(packages[i], ZMQ, max_length));
                    break;
                case 4:
                    sb.append(buildVersion(packages[i], slf4j, max_length));
                    break;
                case 5:
                    sb.append(buildVersion(packages[i], guava, max_length));
                    break;
                case 6:
                    sb.append(buildVersion(packages[i], javatuples, max_length));
                    break;
                case 7:
                    sb.append(buildVersion(packages[i], transmorph, max_length));
                    break;
                case 8:
                    sb.append(buildVersion(packages[i], cal10n, max_length));
                    break;
            }
        }
        return sb.toString().trim();
    }

    //========================================================================
    //========================================================================
    private String buildVersion(String packageName, String release, int maxLength) {
        if (release==null || release.isEmpty())
            return "";

        int length = maxLength - packageName.length()+3;

        StringBuilder   sb = new StringBuilder(packageName + " version");
        for (int j=0 ; j<length ; j++)
            sb.append(".");
        sb.append(release).append('\n');
        return sb.toString();
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
