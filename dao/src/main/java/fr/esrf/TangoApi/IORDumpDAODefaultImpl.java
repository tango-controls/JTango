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
// $Revision: 30281 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;
import org.jacorb.orb.ParsedIOR;
import org.jacorb.orb.iiop.IIOPAddress;
import org.jacorb.orb.iiop.IIOPProfile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *	Class descriptio: This class analyze a IOR string.
 */

public class IORDumpDAODefaultImpl implements IIORDumpDAO
{
	private String	iorString   = null;
	private String	type_id     = null;
	private String	iiopVersion = null;
	private String	host        = null;
	private String	hostname    = "";
	private int		port        = -1;
	private int		prg_number  = -1;		//	used by TACO
	private String  deviceName = null;
    List<NetworkConnection>  connections = new ArrayList<NetworkConnection>();


	public IORDumpDAODefaultImpl()
	{
	}
	
    //===============================================================
    //===============================================================
	public void init(IORdump iORdump, String devname, String iorString) throws  DevFailed
	{
		if (devname==null)
			this.deviceName = "unknown";
		else
			this.deviceName = devname;

		//	If ior null get it from database.
		if (iorString==null)
		{
			DeviceProxy		dev = new DeviceProxy(devname);
			iorString = dev.get_ior();
		}
		else
			this.iorString = iorString;

		if (iorString !=null)
			if (iorString.equals("nada"))
				this.iorString = null;
			else
				iorAnalysis(iORdump, iorString);
	}
    //===============================================================
    //===============================================================
	public void init(IORdump iORdump, String devname) throws  DevFailed
	{
		this.deviceName = devname;

		//	Create device to know its IOR.
		DeviceProxy		dev = new DeviceProxy(devname);
		iorString = dev.get_ior();
		if (iorString !=null)
			if (iorString.equals("nada"))
				iorString = null;
			else
				iorAnalysis(iORdump, iorString);
	}
    //===============================================================
    //===============================================================
	public void init(IORdump iORdump, DeviceProxy dev) throws  DevFailed
	{
		this.deviceName = dev.name();

		//	get IOR.
		iorString = dev.get_ior();
		if (iorString !=null)
			if (iorString.equals("nada"))
				iorString = null;
			else
				iorAnalysis(iORdump, iorString);
	}
    //===============================================================
    /**
     *	Return a string with ID type, IIOP version, host name, and port number.
     */
    //===============================================================
	public String toString(IORdump iORdump)
	{
		if (iorString == null)
			return "No IOR found in database for " + deviceName + "\n" +
					"(Maybe, the device has never been exported...)";
		else
		{
            StringBuilder   sb = new StringBuilder();
			sb.append("Device:          ").append(deviceName).append("\n");
			sb.append("type_id:         ").append(get_type_id()).append("\n");
            if (!iORdump.is_taco)
                sb.append("iiop_version:    ").append(get_iiop_version()).append("\n");

            sb.append("host:            ").append(get_host()).append("\n");
            List<String> alternates = getAlternateAddresses();
            if (alternates.size()>0) {
                for (String add : alternates )
                    sb.append("alternate addr.: ").append(add).append("\n");
            }

            if (!iORdump.is_taco)
                sb.append("port:            ").append(get_port());
            else
                sb.append("prg number:      ").append(get_prg_number());
			return sb.toString();
		}
	}
    //===============================================================
    /**
     *	Make the IOR analyse
     *
     * @param iORdump   IORdump instance
     * @param iorString IOR String
     * @throws DevFailed on CORBA error
     */
    //===============================================================
	private void iorAnalysis(IORdump iORdump, String iorString) throws  DevFailed
	{
		if(iorString==null)
			return;

		//	Check if ior string start with "rpc:" --> TACO protocole
		//	ior = rpc:hostname:prgnumber
		if (iorString.startsWith("rpc:"))
		{	
			iORdump.is_taco = true;
			type_id = "Taco";
			host    = iorString.substring("rpc:".length(), 
						iorString.indexOf(":", "rpc:".length()+1));
			String	s = iorString.substring(iorString.indexOf(":", "rpc:".length()+1)+1);
			prg_number = Integer.parseInt(s);
			return;
		}
		else
		if (!iorString.startsWith("IOR:"))
			return;
	
		//	Start Analysis  --  Code based on :
		//   org.jacorb.orb.util.PrintIOR.printIOR(orb, ior);
		ParsedIOR pior = new ParsedIOR(
				(org.jacorb.orb.ORB)ApiUtil.get_orb(), iorString);
        org.omg.IOP.IOR ior = pior.getIOR();
		type_id = ior.type_id;
        List profiles = pior.getProfiles();
        for (Object profile : profiles) {
            IIOPProfile p = (IIOPProfile) profile;
            iiopVersion = "" + (int) p.version().major + "." +
                    (int) p.version().minor;

            try {
				//  check for multiple connections
                connections.add(new NetworkConnection((IIOPAddress) p.getAddress()));
                List	alternates = p.getAlternateAddresses();
				for (Object alternate : alternates)
					connections.add(new NetworkConnection((IIOPAddress) alternate));
            }
            catch (Exception e) {
                host = " (" + e + ")";
            }


            if (connections.isEmpty())
                Except.throw_exception("NO_NETWORK_FOUND",
                        "Failed to found network connection", "IORdump.iorAnalysis()");
            NetworkConnection   connection = connections.get(0);
            hostname = connection.name;
            host = hostname + " (" + connection.address + ")";
            port = connection.port;
        }
	}
    //===============================================================
    //===============================================================
    private class NetworkConnection {
        private String name;
        private String address;
        private int port;

        private NetworkConnection(IIOPAddress iiopAddress) throws UnknownHostException {
            String originalHost = iiopAddress.getOriginalHost();
            InetAddress inetAddress = InetAddress.getByName(originalHost);
            address = inetAddress.getHostAddress();

            //  Start a thread to get host name with timeout
            GetHostNameThread thread = new GetHostNameThread(inetAddress);
            thread.start();
            try {
                thread.join(100);
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
            //  If host name is not, set it as address
            if (thread.hostName != null)
                name = thread.hostName;
            else
                name = address;
            port = iiopAddress.getPort();
            if (port < 0) port += 65536;
        }
     }
    //===============================================================
    /**
     * A little thread to get host name with timeout.
     * Sometimes getHostName() method could be quiet slow (5 sec.)
     */
    //===============================================================
    private class GetHostNameThread extends Thread {
        private InetAddress inetAddress;
        private String hostName = null;
        private GetHostNameThread(InetAddress inetAddress) { this.inetAddress = inetAddress; }
        public void run() {
            hostName = inetAddress.getHostName();
        }
    }
    //===============================================================
    /**
     *	Return the ID type
     */
    //===============================================================
	public String get_type_id()
	{
		return type_id;
	}
    //===============================================================
    /**
     *	Return the host where the process is running.
     */
    //===============================================================
	public String get_host()
	{
		return host;
	}
    //===============================================================
    /**
     *	Return the host name where the process is running.
     */
    //===============================================================
	public String get_hostname()
	{
		return hostname;
	}
    //===============================================================
    /**
     *	Return the connection port.
     */
    //===============================================================
	public int get_port()
	{
		return port;
	}
    //===============================================================
    /**
     *	Return the connection TACO prg_number.
     */
    //===============================================================
	public int get_prg_number()
	{
		return prg_number;
	}
    //===============================================================
    /**
     *	Return the IIOP version number.
     */
    //===============================================================
	public String get_iiop_version()
	{
		return iiopVersion;
	}
    //===============================================================
    /**
     *	Return alternate address  list .
     */
    //===============================================================
	public List<String> getAlternateAddresses() {
        List<String>   list = new ArrayList<String>();
        for (int i=1 ; i<connections.size() ; i++) {
            NetworkConnection connection = connections.get(i);
            String  address = connection.address;
            if (!connection.name.equals(connection.address))
                address += " (" + connection.name +")";
            list.add(address);
        }
		return list;
	}
}


