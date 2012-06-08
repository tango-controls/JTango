//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the java api class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.7  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
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

