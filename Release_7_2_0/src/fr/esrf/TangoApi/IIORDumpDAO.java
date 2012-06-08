//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	source code 
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
// Revision 1.3  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;

public interface IIORDumpDAO {

	public void init(IORdump iORdump, String devname, String iorString) throws DevFailed;

	// ===============================================================
	// ===============================================================
	public void init(IORdump iORdump, String devname) throws DevFailed;
	// ===============================================================
	// ===============================================================
	public void init(IORdump iORdump, DeviceProxy dev) throws DevFailed;	
	
	//===============================================================
	/**
	 *	Return a string with ID type, IIOP version, host name, and port number.
	 */
	public String toString(IORdump iORdump);

	//===============================================================
	/**
	 *	Return the ID type
	 */
	public String get_type_id();

	//===============================================================
	/**
	 *	Return the host where the process is running.
	 */
	public String get_host();

	//===============================================================
	/**
	 *	Return the host name where the process is running.
	 */
	public String get_hostname();

	//===============================================================
	/**
	 *	Return the connection port.
	 */
	public int get_port();

	//===============================================================
	/**
	 *	Return the connection TACO prg_number.
	 */
	public int get_prg_number();

	//===============================================================
	/**
	 *	Return the IIOP version number.
	 */
	public String get_iiop_version();

}
