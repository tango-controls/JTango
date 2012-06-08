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
//
// $Log$
// Revision 1.1  2009/09/11 12:09:43  pascal_verdier
// tangorc environment file is managed.
//
//
//-======================================================================

package fr.esrf.TangoApi;


import fr.esrf.TangoDs.Except;
import fr.esrf.Tango.DevFailed;

import java.util.StringTokenizer;
import java.io.*;


/**
 *	This class is able to manage environment variables. 
 *
 * 
 * @author verdier
 * @version $Revision$
 */

class TangoEnv
{
	private static String	tango_host  = null;
	private static String	super_tango = null;
	private static String	access_devname   = null;
	private static String	default_timeout  = null;
	private static String	orb_max_msg_size = null;
	//===================================================================
	/**
	 *	Returns the TANGO_HOST found in  JVM, environment, tangorc file,....
	 */
	//===================================================================
	static String getTangoHost() throws DevFailed
	{
		//	Check if already done
		if (tango_host==null)
			if ((tango_host=System.getProperty("TANGO_HOST"))==null)	//	From JVM property
				if ((tango_host=System.getenv("TANGO_HOST"))==null)		//	From Environment property
					if ((tango_host=getFromEnvFile("TANGO_HOST"))==null)		//	From Environment file
						Except.throw_connection_failed("TangoApi_TANGO_HOST_NOT_SET",
									"Property \"TANGO_HOST\" not exported",
									"TangoEnv.getTangoHost()");
		//	else
		return tango_host;
	}
	//===================================================================
	/**
	 *	Returns the SUPER_TANGO found in  JVM, environment, tangorc file,....
	 */
	//===================================================================
	static boolean isSuperTango()
	{
		//	Check if already done
		if (super_tango==null)
			if ((super_tango=System.getProperty("SUPER_TANGO"))==null)	//	From JVM property
				if ((super_tango=System.getenv("SUPER_TANGO"))==null)		//	From Environment property
					if ((super_tango=getFromEnvFile("SUPER_TANGO"))==null)		//	From Environment file
						super_tango = "false";
		return (super_tango.trim().toLowerCase().equals("true"));
	}
	//===================================================================
	/**
	 *	Returns the ACCESS_DEVNAME found in  JVM, environment, tangorc file,....
	 */
	//===================================================================
	static String getAccessDevname()
	{
		//	Check if already done
		if (access_devname==null)
			if ((access_devname=System.getProperty("ACCESS_DEVNAME"))==null)	//	From JVM property
				if ((access_devname=System.getenv("ACCESS_DEVNAME"))==null)		//	From Environment property
					access_devname = getFromEnvFile("ACCESS_DEVNAME");		//	From Environment file
		//	else
		return access_devname;
	}
	//===================================================================
	/**
	 *	Returns the TANGO_TIMEOUT found in  JVM, environment, tangorc file,....
	 */
	//===================================================================
	static String getStrDefaultTimeout()
	{
		//	Check if already done
		if (default_timeout==null)
			if ((default_timeout=System.getProperty("TANGO_TIMEOUT"))==null)	//	From JVM property
				if ((default_timeout=System.getenv("TANGO_TIMEOUT"))==null)		//	From Environment property
					default_timeout = getFromEnvFile("TANGO_TIMEOUT");		//	From Environment file
		//	else
		return default_timeout;
	}
	//===================================================================
	/**
	 *	Returns the getORBgiopMaxMsgSize found in  JVM, environment, tangorc file,....
	 */
	//===================================================================
	static String getORBgiopMaxMsgSize()
	{
		//	Check if already done
		if (orb_max_msg_size==null)
			if ((orb_max_msg_size=System.getProperty("ORBgiopMaxMsgSize"))==null)	//	From JVM property
				if ((orb_max_msg_size=System.getenv("ORBgiopMaxMsgSize"))==null)		//	From Environment property
					orb_max_msg_size = getFromEnvFile("ORBgiopMaxMsgSize");		//	From Environment file
		//	else
		return orb_max_msg_size;
	}
	//===================================================================
	//===================================================================
	private static String getFromEnvFile(String varname)
	{
		if (osIsUnix())
		{
			//	Check if file exists at home
			String	home = System.getenv("HOME");
			String	varval = getFromEnvFile(varname, home+"/.tangorc");
			if (varval!=null)
				return varval;

			//	Check default tangorc
			String	tangorc = System.getProperty("TANGO_RC");
			if (tangorc==null)
				tangorc = "/etc/tangorc";
			return getFromEnvFile(varname, tangorc);
		}
		else	//	WIN 32
		{
			
			//	Check default tangorc
			String	tangorc = System.getProperty("TANGO_RC");
			if (tangorc==null)
			{
				String	tango_root = System.getenv("TANGO_ROOT");
				if (tango_root==null)
					return null;
				tangorc = tango_root+"/tangorc";
			}
			return getFromEnvFile(varname, tangorc);
		}
	}
	//===============================================================
	//===============================================================
	private static String getFromEnvFile(String varname, String filename)
	{
		try
		{
			String	code = readFile(filename);
        	StringTokenizer stk = new StringTokenizer(code, "\n");
	        while (stk.hasMoreTokens())
        	{
            	String line = stk.nextToken().trim();
				if (line.startsWith("#")==false)
				{
					int	pos = line.indexOf(varname+"=");
					if (pos>=0)	//	Var found
						return line.substring(pos+varname.length()+1);
				}
			}
		}
		catch(Exception e) {
			//System.err.println(e);
		}
		return null;
	}
	//===============================================================
	//===============================================================
	static private boolean	_osIsUnix = true;
	static private boolean	_osIsUnixTested = false;
	static public boolean osIsUnix()
	{
		if (!_osIsUnixTested)
		{
			try
			{
				String	os = System.getProperty("os.name");
				_osIsUnix = ! os.toLowerCase().startsWith("windows");
			}
			catch(Exception e)
			{
				_osIsUnix = false;
			}
		}
		return _osIsUnix;
	}
	//===============================================================
	//===============================================================
    private static String readFile(String filename)
                    throws	FileNotFoundException,
                            SecurityException,
                            IOException
    {
        FileInputStream	fid = new FileInputStream(filename);
        int nb = fid.available();
        byte[]	inStr  = new byte[nb];
        nb = fid.read(inStr);
        fid.close();
        return new String(inStr);
    }
	//===============================================================
	//===============================================================
}
