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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.PeriodicEventProp;


/**
 *	Class Description: This class is the same class as PeriodicEventProp, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
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
