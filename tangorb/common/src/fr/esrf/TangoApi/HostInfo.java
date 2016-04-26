//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
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
// $Revision$
//
//-======================================================================


package fr.esrf.TangoApi;


/**
 * This class manage the host information
 * - name
 * - address
 *
 * @author verdier
 */

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;


class HostInfo {
    private static String name = null;
    private static String address = null;
    private static boolean trace;
    private static Vector<String> addresses = new Vector<String>();

    //===============================================================
    //===============================================================
    private HostInfo() throws DevFailed {
        String env = System.getenv("TraceAddresses");
        trace = (env != null && env.equals("true"));
        if (trace) System.out.println("HostInfo.HostInfo()");
        try {

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                //  To do not have 127.x.x.x
                if (networkInterface.isLoopback())
                    continue;

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                if (trace)
                    System.out.println("----------------- " + networkInterface.getName() + " --------------------");
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (trace) {
                        System.out.println("getCanonicalHostName(): " + inetAddress.getCanonicalHostName());
                        System.out.println("getHostName():          " + inetAddress.getHostName());
                        System.out.println("getHostAddress():       " + inetAddress.getHostAddress());
                    }
                    if (!networkInterface.getName().startsWith("docker"))
                        checkInetAddress(inetAddress);
                }
            }
        } catch (SocketException e) {
            System.err.println(e.toString());
            Except.throw_exception("TangoApi_SocketException",
                    e.toString(), "HostInfo.HostInfo()");
        }
        if (name == null || address == null) {
            System.err.println("Host name/address cannot be determined !");
            Except.throw_exception("TangoApi_NetworkSystemException",
                    "Host name/address cannot be determined !",
                    "HostInfo.HostInfo()");
        }
    }

    //===============================================================
    //===============================================================
    private boolean checkInetAddress(InetAddress inetAddress) {

        //  Check if not local host
        if (! inetAddress.getCanonicalHostName().startsWith("local")) {
            //  Check if name is not the address (???)
            if (!inetAddress.getCanonicalHostName().equalsIgnoreCase(inetAddress.getHostAddress())) {

                addresses.add(inetAddress.getHostAddress());

                //  Check if IPV 4 address
                if (isIPV4address(inetAddress.getHostAddress())) {
                    name = inetAddress.getCanonicalHostName();
                    address = inetAddress.getHostAddress();
                    //System.out.println(name+":	" + address);
                    return true;
                }
            } else if (trace)
                System.err.println(
                        "Warning: at least one getCanonicalHostName() returns " + inetAddress.getCanonicalHostName() +
                        "\n  Check files /etc/resolv.conf and /etc/nsswitch.conf ");
        }
        return false;
    }

    //===============================================================
    //===============================================================
    private boolean isIPV4address(String address) {
        StringTokenizer st = new StringTokenizer(address, ".");
        List<String> nodes = new ArrayList<String>();
        while (st.hasMoreTokens())
            nodes.add(st.nextToken());
        return (nodes.size() == 4);
    }

    //===============================================================
    //===============================================================
    static String getName() throws DevFailed {
        if (name == null)
            new HostInfo();
        return name;
    }

    //===============================================================
    //===============================================================
    static String getAddress() throws DevFailed {
        if (address == null)
            new HostInfo();
        return address;
    }

    //===============================================================
    //===============================================================
    static Vector<String> getAddresses() throws DevFailed {
        if (address == null)
            new HostInfo();
        return addresses;
    }

    //===============================================================
    //===============================================================
    private static String toStaticString() {
        String str = "";
        try {
            if (name == null)
                new HostInfo();
            str += "name:          " + name + "\n";
            str += "address:       " + address + "\n";
        } catch (DevFailed e) {
            str = e.errors[0].desc;
        }
        return str;
    }

    //===============================================================
    //===============================================================
    public static void main(String[] args) {
        System.out.println(toStaticString());
    }
    //===============================================================
    //===============================================================
}
