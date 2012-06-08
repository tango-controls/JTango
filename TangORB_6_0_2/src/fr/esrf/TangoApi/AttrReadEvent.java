//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the AttrReadEvent class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
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
 *	Class description: This class is an object used by asynchronous calls
 *		to give data to the callbacks.
 */

public class  AttrReadEvent implements java.io.Serializable
{
	public DeviceProxy 			device;
	public String[]				attr_names;
	public DeviceAttribute[]	argout;
	public DevError[]			errors;
	public boolean				err;

	//===============================================================
	//===============================================================
	public AttrReadEvent(DeviceProxy dev, String[] att, 
						DeviceAttribute[] arg, DevError[] err_in)
	{
		device   = dev;
		attr_names = att;
		argout   = arg;
		errors   = err_in;
		if (errors==null)
			err = false;
		else
			err = (errors.length!=0) ;
	}
}
