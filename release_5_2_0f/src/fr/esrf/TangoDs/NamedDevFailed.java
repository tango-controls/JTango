//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.2  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2004/09/17 08:02:33  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
//
//
// Copyright 1995 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoDs;
 

import fr.esrf.Tango.DevError;

/** 
 *	Class Description:
 *
 * @author  verdier
 * @version  $Revision$
 */


public class NamedDevFailed
{
	public String		name;
	public long		idx_in_call;
	public DevError[]	err_stack;
	//======================================================================
	NamedDevFailed(DevError[] err, String name, long idx)
	{
		err_stack = err;
		this.name = name;
		idx_in_call = idx;
	}	
}
