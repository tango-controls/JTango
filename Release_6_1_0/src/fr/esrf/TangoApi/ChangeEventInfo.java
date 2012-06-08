//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the ChangeEventInfo class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.1  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.ChangeEventProp;


/**
 *	Class Description: This class is the same class as ChangeEventProp, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision$
 */
public class ChangeEventInfo implements java.io.Serializable
{
	public String	rel_change = "";
	public String	abs_change = "";
	public String[]	extensions;
	//-======================================================================
	//-======================================================================
	public ChangeEventInfo(String rel_change, String abs_change, String[] extensions)
	{
		this.rel_change = rel_change;
		this.abs_change = abs_change;
		this.extensions = extensions;
	}
	//-======================================================================
	//-======================================================================
	public ChangeEventInfo(ChangeEventProp ev_prop)
	{
		this.rel_change = ev_prop.rel_change;
		this.abs_change = ev_prop.abs_change;
		this.extensions = ev_prop.extensions;
	}
	//-======================================================================
	//-======================================================================
	public ChangeEventProp getTangoObj()
	{
		return new ChangeEventProp(rel_change, abs_change, extensions);
	}
	//-======================================================================
	//-======================================================================
}
