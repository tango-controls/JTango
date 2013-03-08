//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013
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
import org.jacorb.orb.ParsedIOR;
import org.jacorb.orb.iiop.IIOPAddress;
import org.jacorb.orb.iiop.IIOPProfile;

import java.net.InetAddress;
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
	private String	devname     = null;
	public IORDumpDAODefaultImpl()
	{
	}
	
//===============================================================
//===============================================================
	public void init(IORdump iORdump, String devname, String iorString) throws  DevFailed
	{
		if (devname==null)
			this.devname   = "unknown";
		else
			this.devname   = devname;

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
		this.devname   = devname;

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
		this.devname   = dev.name();

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
			return "No IOR found in database for " + devname + "\n" +
					"(Maybe, the device has never been exported...)";
		else
		{
			String str = "Device:          " + devname + "\n" +
						 "type_id:         " + get_type_id()		+ "\n";
				if (!iORdump.is_taco)
					str +=	"iiop_version:    " + get_iiop_version()	+ "\n";

				str +=		"host:            " + get_host()			+ "\n";
				if (!iORdump.is_taco)
					str +=	"port:            " + get_port();
				else
					str +=	"prg number:      " + get_prg_number();
			return str;
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
		//   org.jacorb.orb.util.PrintIOR.printIOR(pior, ApiUtil.get_orb());
		//-------------------------------------------------------------------
		//	Constructor signature modified in JacORB-2.3.0
		// ParsedIOR pior = new ParsedIOR( iorString, ApiUtil.get_orb(), null);
		ParsedIOR pior = new ParsedIOR(
				(org.jacorb.orb.ORB)ApiUtil.get_orb(), iorString);
        org.omg.IOP.IOR ior = pior.getIOR();
		type_id = ior.type_id;
        List profiles = pior.getProfiles();
        for (Object profile : profiles) {
            IIOPProfile p = (IIOPProfile) profile;
            iiopVersion = "" + (int) p.version().major + "." +
                    (int) p.version().minor;

            //	Modified in JacORB-2.3.0
            //	String	name =  p.getAddress().getHostname();
            String name = ((IIOPAddress) p.getAddress()).getOriginalHost();
            try {
                InetAddress iadd = InetAddress.getByName(name);
                String      add = iadd.getHostAddress();
                hostname = iadd.getHostName();
                host = hostname + " (" + add + ")";
				/*
				List	list = p.getAlternateAddresses();
				System.out.println("List size="+list.size());
				for (Object o : list)
					System.out.println("--> "+o);
				*/
            }
            catch (Exception e) {
                host = name + " (" + e + ")";
            }
            //	Modified in JacORB-2.3.0
            //	port = p.getAddress().getPort();
            port = ((IIOPAddress) p.getAddress()).getPort();
            if (port < 0) port += 65536;
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
}


