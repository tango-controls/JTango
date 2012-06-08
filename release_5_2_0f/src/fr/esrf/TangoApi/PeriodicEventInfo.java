//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the PeriodicEventInfo class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.1  2004/11/05 11:59:21  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.PeriodicEventProp;


/**
 *	Class Description: This class is the same class as PeriodicEventProp, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision$
 */
public class PeriodicEventInfo implements java.io.Serializable
{
	public String period = "";
	public String[] extensions;
	//-======================================================================
	//-======================================================================
	public PeriodicEventInfo(String period, String[] extensions)
	{
		this.period     = period;
		this.extensions = extensions;
	}
	//-======================================================================
	//-======================================================================
	public PeriodicEventInfo(PeriodicEventProp ev_prop)
	{
		this.period     = ev_prop.period;
		this.extensions = ev_prop.extensions;
	}
	//-======================================================================
	//-======================================================================
	public PeriodicEventProp getTangoObj()
	{
		return new PeriodicEventProp(period, extensions);
	}
}
