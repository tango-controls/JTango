//+============================================================================
//
// file :               AttrProperty.java
//
// description :        Java source code for the AttrProperty class.
//			This class is used to manage couple attribute
//			property name and attribute property value.
//			Both are stored as strings
//
// project :            TANGO
//
// $Author: :          E.Taurel
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
// Revision 1.4  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.3  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.2  2008/09/19 07:08:13  pascal_verdier
// Serialization BY_DEV, BY_CLASS, NO_SYNC (by Attribute).
// Check poa.pool_thread_max value.
// Database.(get/put)_device_attribute_property() replace command_inout("Db(Get/Put)DeviceAttributeProperty") for new db command.
//
// Revision 1.1  2007/08/23 08:33:24  ounsy
// updated change from api/java
//
// Revision 3.6  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:23  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:36  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:20  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:22:59  taurel
// Added attribute support
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

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
