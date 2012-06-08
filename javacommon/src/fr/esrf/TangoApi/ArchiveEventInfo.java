//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the ArchiveEventInfo class definition .
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
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:31  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.1  2004/11/05 11:59:19  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.ArchiveEventProp;


/**
 *	Class Description: This class is the same class as ArchiveEventProp, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision$
 */
public class ArchiveEventInfo implements java.io.Serializable
{
	public String rel_change = "";
	public String abs_change = "";
	public String period = "";
	public String[] extensions;
	//-======================================================================
	//-======================================================================
	public ArchiveEventInfo(String rel_change, String abs_change, String period, String[] extensions)
	{
		this.rel_change = rel_change;
		this.abs_change = abs_change;
		this.period     = period;
		this.extensions = extensions;
	}
	//-======================================================================
	//-======================================================================
	public ArchiveEventInfo(ArchiveEventProp ev_prop)
	{
		this.rel_change = ev_prop.rel_change;
		this.abs_change = ev_prop.abs_change;
		this.period     = ev_prop.period;
		this.extensions = ev_prop.extensions;
	}
	//-======================================================================
	//-======================================================================
	public ArchiveEventProp getTangoObj()
	{
		return new ArchiveEventProp(rel_change, abs_change, period, extensions);
	}
}
