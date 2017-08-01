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
// $Revision: 28158 $
//
//-======================================================================
package fr.esrf.TangoApi;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.esrf.Tango.DevFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class manage the host information obtained from {@link NetworkInterface#getInetAddresses()}
 * - name -- last found NI name
 * - address -- last found NI address
 * - addresses -- list of NI addresses
 *
 * @author verdier
 */
public class HostInfo {
    /**
     * A jacorb system property for IP address on multi-homed host
     */
    public static final String OAI_ADDR = System.getProperty("OAIAddr");
    private static final Logger LOGGER = LoggerFactory.getLogger(HostInfo.class);

    //===============================================================
    //===============================================================
    private HostInfo() {
    }

    /**
     * @return all Ip (4&6) available on this host
     * @throws DevFailed
     */
    public static Iterable<String> getIpAddresses() throws DevFailed {
        List<String> result;
        if (OAI_ADDR != null && !OAI_ADDR.isEmpty()) {
            result = new ArrayList<>(1);
            result.add(OAI_ADDR);
        } else {
            try {
                Iterable<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

                result = new ArrayList<>();

                for (NetworkInterface nic : Iterables.filter(networkInterfaces, new Predicate<NetworkInterface>() {
                    @Override
                    public boolean apply(NetworkInterface networkInterface) {
                        try {
                            return !networkInterface.isLoopback();
                        } catch (SocketException e) {
                            LOGGER.warn("Ignoring NetworkInterface({}) due to an exception: {}", networkInterface.getName(), e);
                            return false;
                        }
                    }
                })) {
                    result.addAll(
                            Lists.transform(nic.getInterfaceAddresses(), new Function<InterfaceAddress, String>() {
                                @Override
                                public String apply(InterfaceAddress interfaceAddress) {
                                    return interfaceAddress.getAddress().getHostAddress();
                                }
                            }));
                }
            } catch (SocketException e) {
                throw DevFailedUtils.newDevFailed(e);
            }
        }
        return result;
    }

    //===============================================================
    //===============================================================
    public static String getName() throws DevFailed {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
    }

    //===============================================================
    //===============================================================
    public static String getAddress() throws DevFailed {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw DevFailedUtils.newDevFailed(e);
        }
    }

    //===============================================================
    //===============================================================
    public static Iterable<String> getAddresses() throws DevFailed {
        return getIpAddresses();
    }
}
