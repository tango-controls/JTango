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

class AttrProperty implements TangoConst
{

	private String		attr_name;
	private String[]	attr_value;

//+-------------------------------------------------------------------------
//
// method : 		AttrProperty 
// 
// description : 	constructors for AttrProperty class
//
//--------------------------------------------------------------------------
	AttrProperty(String name,String value)
	{
		this(name, new String[] { value });
	}
	AttrProperty(String name,String[] value)
	{
		// Property name in lower case letters
		attr_name = name.toLowerCase();
		attr_value = value;

		// For data_type or data_format properties,
		//	also change property value to lowercase letters
		if ((attr_name.equals("data_type") == true) ||
			(attr_name.equals("data_format") == true))
				attr_value[0] = attr_value[0].toLowerCase();
	}
//+-------------------------------------------------------------------------
//
// method : 		AttrProperty.toString
// 
// description : 	Redefinition of the Object.toString method to
//			print attribute name and value in one go
//
//--------------------------------------------------------------------------
	public String toString()
	{
		StringBuffer s = new StringBuffer("Property name = ");
		s.append(attr_name);
		s.append(", Property value = ");
		for (String value : attr_value)
			s.append("\n\t").append(value);

		return s.toString();
	}

//+-------------------------------------------------------------------------
//
// Methods to retrieve/set some data members from outside the class and all
// its inherited classes
//
//--------------------------------------------------------------------------
	String[] get_value()
	{
		return attr_value;
	}

	String get_name()
	{
		return attr_name;
	}
}
