//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the CmdDoneEvent class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;
 
import fr.esrf.Tango.DevError;


/**
 *	Object defining result of an asynchronous call.
 */


public class  CmdDoneEvent
{
	public DeviceProxy 	device;
	public String		cmdname;
	public DeviceData	argout;
	public DevError[]	errors;
	public boolean		err;

	//===============================================================
	/**
	 *	Object constructor
	 */
	//===============================================================
	public CmdDoneEvent(DeviceProxy dev, String cmd, 
						DeviceData arg, DevError[] err_in)
	{
		device  = dev;
		cmdname = cmd;
		argout  = arg;
		errors  = err_in;
		if (errors==null)
			err = false;
		else
			err = (errors.length!=0) ;
	}
	//===============================================================
	//===============================================================
}
