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
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoDs;



/**
 * User class to create attribute default properties.
 *
 * This class is used to set attribute default properties. Three levels of 
 * attributes properties setting are implemented within Tango. The highest
 * property setting priority is the database. Then the user default (set using
 * this UserDefaultAttrProp class) and finally a Tango library default
 * value
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */
 
public class UserDefaultAttrProp implements TangoConst
{
	String		       label;
	String		       description;
	String		       unit;
	String		       standard_unit;
	String		       display_unit;
	String		       format;
	String		       min_value;
	String		       max_value;
	String		       min_alarm;
	String		       max_alarm;
		
/**
 * Constructs a newly allocated UserDefaultAttrProp object.
 *
 */
	public UserDefaultAttrProp()
	{
	}

/**
 * Set default label property
 *
 * @param	def_label	The user default label property
 */
	public void set_label(String def_label)
	{
		label = def_label;
	}
	
/**
 * Set default description property
 *
 * @param	def_desc	The user default description property
 */	
	public void set_description(String def_desc)
	{
		description = def_desc;
	}
	
/**
 * Set default unit property
 *
 * @param	def_unit	The user default unit property
 */
	public void set_unit(String def_unit)
	{
		unit = def_unit;
	}
	
/**
 * Set default standard unit property
 *
 * @param	def_std_unit	The user default standard unit property
 */
	public void set_standard_unit(String def_std_unit)
	{
		standard_unit = def_std_unit;
	}
	
/**
 * Set default display unit property
 *
 * @param	def_disp_unit	The user default display unit property
 */
	public void set_display_unit(String def_disp_unit)
	{
		display_unit = def_disp_unit;
	}
	
/**
 * Set default format property
 *
 * @param	def_format	The user default format property
 */
	public void set_format(String def_format)
	{
		format = def_format;
	}
	
/**
 * Set default min_value property
 *
 * @param	def_min_value	The user default min_value property
 */
	public void set_min_value(String def_min_value)
	{
		min_value = def_min_value;
	}
	
/**
 * Set default max_value property
 *
 * @param	def_max_value	The user default max_value property
 */
	public void set_max_value(String def_max_value)
	{
		max_value = def_max_value;
	}
	
/**
 * Set default min_alarm property
 *
 * @param	def_min_alarm	The user default min_alarm property
 */
	public void set_min_alarm(String def_min_alarm)
	{
		min_alarm = def_min_alarm;
	}
	
/**
 * Set default max_alarm property
 *
 * @param	def_max_alarm	The user default max_alarm property
 */
	public void set_max_alarm(String def_max_alarm)
	{
		max_alarm = def_max_alarm;
	}
}
