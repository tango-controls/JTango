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
// Revision 1.5  2007/08/23 09:42:20  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
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

import java.io.FileNotFoundException;
import java.io.IOException;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.factory.TangoFactory;

/**
 * Class descriptio: This class analyze a IOR string.
 */

public class IORdump {
	private IIORDumpDAO iordumpDAO = null;

	public boolean is_taco = false;

	// ===============================================================
	// ===============================================================
	public IORdump(String devname, String iorString) throws DevFailed {
		iordumpDAO = TangoFactory.getSingleton().getIORDumpDAO();
		iordumpDAO.init(this, devname, iorString);

	}

	// ===============================================================
	// ===============================================================
	public IORdump(String devname) throws DevFailed {
		iordumpDAO = TangoFactory.getSingleton().getIORDumpDAO();
		iordumpDAO.init(this, devname);

	}

	// ===============================================================
	// ===============================================================
	public IORdump(DeviceProxy dev) throws DevFailed {
		iordumpDAO = TangoFactory.getSingleton().getIORDumpDAO();
		iordumpDAO.init(this, dev);

	}

	// ===============================================================
	/**
	 * Return a string with ID type, IIOP version, host name, and port number.
	 */
	// ===============================================================
	public String toString() {
		return iordumpDAO.toString(this);
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
		return iordumpDAO.get_type_id();

	}

	// ===============================================================
	/**
	 * Return the host where the process is running.
	 */
	// ===============================================================
	public String get_host() {
		return iordumpDAO.get_host();

	}

	// ===============================================================
	/**
	 * Return the host name where the process is running.
	 */
	// ===============================================================
	public String get_hostname() {
		return iordumpDAO.get_hostname();

	}

	// ===============================================================
	/**
	 * Return the connection port.
	 */
	// ===============================================================
	public int get_port() {
		return iordumpDAO.get_port();

	}

	// ===============================================================
	/**
	 * Return the connection TACO prg_number.
	 */
	// ===============================================================
	public int get_prg_number() {
		return iordumpDAO.get_prg_number();

	}

	// ===============================================================
	/**
	 * Return the IIOP version number.
	 */
	// ===============================================================
	public String get_iiop_version() {
		return iordumpDAO.get_iiop_version();

	}

	// ===============================================================
	// ===============================================================
	public static void printSyntax() {
		// for static call we use an utility class
		IORDumpUtil.printSyntax();
	}

	// ===============================================================
	// ===============================================================
	public static String getIor(String filename) throws FileNotFoundException, SecurityException, IOException {
		// for static call we use an utility class
		return IORDumpUtil.getIor(filename);
	}

	// ===============================================================
	// ===============================================================
	public static void main(String[] args) {
		// for static call we use an utility class
		IORDumpUtil.main(args);
	}


	public IIORDumpDAO getIordumpDAO() {
		return iordumpDAO;
	}

	public void setIordumpDAO(IIORDumpDAO iordumpDAO) {
		this.iordumpDAO = iordumpDAO;
	}

}

