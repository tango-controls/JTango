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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.factory.TangoFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	/*
	 * Make the IOR analyse
	 */
	// ===============================================================
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

