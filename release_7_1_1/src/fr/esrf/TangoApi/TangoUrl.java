//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Log$
// Revision 1.9  2009/03/25 13:27:56  pascal_verdier
// ...
//
// Revision 1.8  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.7  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.6  2008/10/10 08:35:31  pascal_verdier
// TANGO_HOST available from environment variable added.
//
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.net.MalformedURLException;
import java.net.URL;


/**
 *	Class Description:<Br>
 *	Tango URL management.
 *
 * @author  verdier
 * @version  $Revision$
 */

public class TangoUrl implements ApiDefs, java.io.Serializable
{
	int		protocol = TANGO;
	String	host     = null;
	String	strport  = null;
	int		port     = -1;
	String	devname  = null;
	boolean	use_db   = true;
	static private boolean	envRead = false;

	//===================================================================
	/**
	 *	Object Constructor.
	 *
	 *	@param	urlstr	url string to connect.
	 */
	//===================================================================
	public TangoUrl(String urlstr)	throws DevFailed
	{
		//	Get TACO/TANGO protocol
		int	idx;
		if ((idx=urlstr.indexOf(protocol_name[TANGO] + ":"))>=0)
			protocol = TANGO;
		else
		if ((idx=urlstr.indexOf(protocol_name[TACO] + ":"))>=0)
		{
			protocol = TACO;
			use_db = false;
		}
		//	replace by standard one
		int len;
		if (idx<0)
			idx = len = 0;
		else
			len = protocol_name[protocol].length() + 1;
		String new_urlstr =
			"http:" + urlstr.substring(idx+len);

		//	Build URL object
		URL	url = null;
		try
		{
			url = new URL(new_urlstr);
		}
		catch(MalformedURLException e)
		{
			//	Check if malformed due to multi TANGO_HOST
			boolean	ok = false;
			int	comma = new_urlstr.indexOf(",");
			if (comma>0)
			{
				//	parse TANGO_HOST part
				int	start = new_urlstr.indexOf("//");
				if (start>=0)
				{
					start += 2;
					int	end = new_urlstr.indexOf("/", start);

					if (end<0)	//	no device name
						end = new_urlstr.length();

					if (end>start)
					{
						String[]	array =
							ApiUtil.parseTangoHost(new_urlstr.substring(start, end));
						host    = array[0];
						strport = array[1];
						port    = Integer.parseInt(strport);
						//	If OK remove multi tango host before retrying URL constructor
						String	tmp = new_urlstr.substring(0, comma) +
										new_urlstr.substring(end);
						try
						{
							url = new URL(tmp);
							ok = true;
						}
						catch(MalformedURLException ex){}
					}
				}
			}
			if (ok==false)
			{
				//e.printStackTrace();
				Except.throw_wrong_syntax_exception("TangoApi_BAD_URL",
										"Bad url parameter",
										"TangoUrl.TangoUrl()");
			}
		}

		//	Fill object fields from URL class
		assert url != null;
		host = url.getHost();
		port = url.getPort();
		strport = Integer.toString(port);
		devname = url.getFile();

        //	Check if tango host and port OK
        //	Else retreive from environment
		if (protocol==TANGO && (host==null || host.length()==0))
        {
            //  Check if database already connected
            Database	db = null;
            try
            {
                if (host!=null && host.length()>0)
                    db = ApiUtil.get_db_obj(host, strport);
                else
                    db = ApiUtil.get_db_obj();
            }
            catch(DevFailed e){}
            if (db!=null)
                envRead = true;

            //  TANGO_HOST must be read from environment
			if (envRead==false)
			{
				setFromEnv();
				if (protocol==TANGO && port < 0)
					Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
											"Cannot parse port number",
											"TangoUrl.TangoUrl()");
			}
			else
			{
				//	Get the DB used host and port
				host = ApiUtil.get_db_obj().url.host;
				port = ApiUtil.get_db_obj().url.port;
				strport = Integer.toString(port);
			}
        }

		//	Check id device name is OK
		while (devname.startsWith("/"))		//	if slash is first char, remove
			devname = devname.substring(1);
		//	check if alias or device name
		if (devname.length()>0)
		{
			int	nb_slash=0;
			for (int i=0 ; i<devname.length() ; i++)
				if (devname.charAt(i)=='/')
					nb_slash++;

			if (nb_slash!=0 && nb_slash!=2)		//	NOT alias or device name
				Except.throw_wrong_syntax_exception("TangoApi_BAD_DEVICE_NAME",
							"Device name ("+ devname + ") wrong definition.",
							"TangoUrl.TangoUrl()");
		}

		//	Check char used
		if (devname.indexOf('#') >=0 &&devname.indexOf("->")>=0)
					Except.throw_wrong_syntax_exception("TangoApi_BAD_DEVICE_NAME",
							"Device name ("+ devname + ") wrong definition.",
							"TangoUrl.TangoUrl()");

			//	check for dbase usage
		if (protocol==TANGO)
			if (url.getRef()!=null)
				if (url.getRef().indexOf("dbase=no")==0)
					use_db = false;

		//trace();
	}
	//===================================================================
	/**
	 *
	 */
	//===================================================================
	public TangoUrl()	throws DevFailed
	{
		if (protocol==TANGO)
            setFromEnv();
	}
	//===================================================================
	//===================================================================
	private void setFromEnv()	throws DevFailed
	{
		String	env = TangoEnv.getTangoHost();
		assert	env != null;
		if (env.indexOf(":")<0)
			Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
									"Unknown \"TANGO_HOST\" property " + env,
									"TangoUrl.TangoUrl()");
		String[]	array = ApiUtil.parseTangoHost(env);
		host    = array[0];
		strport = array[1];
		port    = Integer.parseInt(strport);
		envRead= true;
	}
	//===================================================================
	//===================================================================
	public String toString()
	{
		StringBuffer	sb =
			new StringBuffer((protocol==TANGO)?"tango":"taco");
		sb.append("://").append(host).append(":").append(strport);
		sb.append("/").append(devname);
		if (!use_db)
			sb.append("#dbase=no");
		return sb.toString();
	}
	//===================================================================
	/**
	 *
	 */
	//===================================================================
	public  void trace()
	{
			System.out.println("	protocol = " + protocol_name[protocol]);

		if (host!=null)
			System.out.println("	host name= " + host);

		//if (port>0)
			System.out.println("	port num = " + strport);
			System.out.println("	device   = " + devname);

		if (use_db)
			System.out.println("	use database");
		else
			System.out.println("	Do NOT use database");
		System.out.println();
	}
}
