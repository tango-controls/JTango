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
// $Revision: 25723 $
//
//-======================================================================


package fr.esrf.TangoApi;


/**
 *	Class Description:
 *	This class manage data object for Tango forwarded attribute information.
 *
 * @author  verdier
 * @version  $Revision: 25723 $
 */

public class ForwardedAttributeDatum implements java.io.Serializable
{
	/**
	 *	The device name.
	 */
	private String deviceName;
	/**
	 *	The forwarded attribute name.
	 */
	private String forwardedAttributeName;
	/**
	 *	root attribute name
	 */
	private String rootAttributeName;

	ForwardedAttributeDatum(String deviceName, String forwardedAttributeName, String rootAttributeName) {
		this.deviceName = deviceName;
		this.forwardedAttributeName = forwardedAttributeName;
		this.rootAttributeName = rootAttributeName;
	}

	/**
	 *
	 * @return the device name which owns the forwarded attribute
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 *
	 * @return the forwarded attribute name
	 */
	public String getForwardedAttributeName() {
		return forwardedAttributeName;
	}

	/**
	 *
	 * @return the root attribute (__root_att) .
	 */
	public String getRootAttributeName() {
		return rootAttributeName;
	}

	public String toString() {
		return deviceName + "/" + forwardedAttributeName + " (__root_att) is " + rootAttributeName;
	}
}
