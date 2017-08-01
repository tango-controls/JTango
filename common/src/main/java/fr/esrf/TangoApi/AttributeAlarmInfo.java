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

import fr.esrf.Tango.AttributeAlarm;


/**
 *	Class Description: This class is the same class as AttributeAlarm, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision: 25296 $
 */
public class AttributeAlarmInfo implements java.io.Serializable
{
	public String min_alarm = "";
	public String max_alarm = "";
	public String min_warning = "";
	public String max_warning = "";
	public String delta_t = "";
	public String delta_val = "";
	public String[] extensions;
	//-======================================================================
	//-======================================================================
	public AttributeAlarmInfo(String min_alarm, String max_alarm, String min_warning, String max_warning, String delta_t, String delta_val, String[] extensions)
	{
		this.min_alarm   = min_alarm;
		this.max_alarm   = max_alarm;
		this.min_warning = min_warning;
		this.max_warning = max_warning;
		this.delta_t     = delta_t;
		this.delta_val   = delta_val;
		this.extensions  = extensions;
	}
	//-======================================================================
	//-======================================================================
	public AttributeAlarmInfo(AttributeAlarm al)
	{
		this.min_alarm   = al.min_alarm;
		this.max_alarm   = al.max_alarm;
		this.min_warning = al.min_warning;
		this.max_warning = al.max_warning;
		this.delta_t     = al.delta_t;
		this.delta_val   = al.delta_val;
		this.extensions  = al.extensions;
	}
	//-======================================================================
	//-======================================================================
	public AttributeAlarm getTangoObj()
	{
		return new AttributeAlarm(min_alarm, max_alarm,
								min_warning, max_warning,
								delta_t, delta_val, extensions);
	}
}
