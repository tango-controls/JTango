//+===========================================================================
// $Source$
//
// Project:   Tango API
//
// Description:  Java source for conversion between Tango/TACO library
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.1  2007/07/04 12:55:34  ounsy
// creation of 3 sub modules :
// 	- client for the webtangorb classes
// 	- common for the classes used by webtangorb and the tangowebserver
// 	- server for the generic classes of tangowebserver
//
// Revision 1.3  2007/07/02 12:03:46  ounsy
// Correction for tango web access
//
// Revision 3.12  2006/06/08 08:05:44  pascal_verdier
// Constructor with DeviceProxy instance added.
//
// Revision 3.11  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.10  2005/05/18 12:50:04  pascal_verdier
// Remove unused data.
//
// Revision 3.9  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.8  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.7  2004/11/05 12:05:34  pascal_verdier
// Use now JacORB_2_2_1.
//
// Revision 3.6  2004/05/14 14:21:34  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2003/07/22 14:15:35  pascal_verdier
// DeviceData are now in-methods objects.
// Minor change for TACO-TANGO common database.
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
// Revision 2.0  2003/01/09 14:00:37  verdier
// jacORB is now the ORB used.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-===========================================================================
//         (c) - Software Engineering Group - ESRF
//============================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

/**
 * Class descriptio: This class analyze a IOR string.
 */

public class IORDumpDAOWebImpl implements IIORDumpDAO, IWebImpl{

	private Object[] classParam = null;

	public IORDumpDAOWebImpl()
	{
	}
	
	
	// ===============================================================	
	// ===============================================================
	public void init(IORdump iORdump, String devname, String iorString) throws DevFailed {
		classParam = new Object[] { devname, iorString };
	}

	// ===============================================================
	// ===============================================================
	public void init(IORdump iORdump, String devname) throws DevFailed {
		classParam = new Object[] { devname };
	}

	// ===============================================================
	// ===============================================================
	public void init(IORdump iORdump, DeviceProxy dev) throws DevFailed {
		classParam = new Object[] { dev };
	}

	// ===============================================================
	/**
	 * Return a string with ID type, IIOP version, host name, and port number.
	 */
	// ===============================================================
	public String toString(IORdump iORdump) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "toString", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// ===============================================================
	/**
	 * Make the IOR analyse
	 */
	// ===============================================================
	// TODO remove javadoc
	// ===============================================================
	/**
	 * Return the ID type
	 */
	// ===============================================================
	public String get_type_id() {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "get_type_id", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// ===============================================================
	/**
	 * Return the host where the process is running.
	 */
	// ===============================================================
	public String get_host() {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "get_host", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// ===============================================================
	/**
	 * Return the host name where the process is running.
	 */
	// ===============================================================
	public String get_hostname() {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "get_hostname", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// ===============================================================
	/**
	 * Return the connection port.
	 */
	// ===============================================================
	public int get_port() {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_port", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	// ===============================================================
	/**
	 * Return the connection TACO prg_number.
	 */
	// ===============================================================
	public int get_prg_number() {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "get_prg_number", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	// ===============================================================
	/**
	 * Return the IIOP version number.
	 */
	// ===============================================================
	public String get_iiop_version() {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "get_iiop_version", new Object[] {}, new Class[] {});
		} catch (DevFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	

	public Object[] getClassParam() {
		return classParam;
	}


	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}
}
