//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the java api class definition .
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
// Revision 1.5  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.7  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
//-======================================================================

package fr.esrf.TangoApi;
 


/** 
 *	Constant definition interface for Java API package.
 *
 * @author  verdier
 * @version $Revision$
 */



public interface ApiDefs {
	final static int		FROM_DBASE = 0;
	final static int		FROM_IOR   = 1;

	public static final int	TANGO = 0;
	public static final int	TACO  = 1;
	public static final String[]	protocol_name = { "tango", "taco" };

	/**
	 *	Constant value to set RPC protocol to TCP mode.
	 */
	public static final int		D_TCP = 888;
	/**
	 *	Constant value to set RPC protocol to UDP mode.
	 */
	public static final int		D_UDP = 999;
	
	public static final boolean	FROM_CMD  = false;
	public static final boolean	FROM_ATTR = true;

	public static final int		ALL_ASYNCH = 0;
	public static final int		POLLING    = 1;
	public static final int		CALLBACK   = 2;

	public static final int		PUSH_CALLBACK = 0;
	public static final int		PULL_CALLBACK = 1;

	static final short	CMD   = 0;
	static final short	ATT_R = 1;
	static final short	ATT_W = 2;
	static final short	MISC  = 3;
	static final int 	NO_TIMEOUT = -1;
  
	public final int	LOGGING_OFF     = 0;
	public final int	LOGGING_FATAL   = 1;
	public final int	LOGGING_ERROR   = 2;
	public final int	LOGGING_WARN    = 3;
	public final int	LOGGING_INFO    = 4;
	public final int	LOGGING_DEBUG   = 5;
}

