//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
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
// Revision 1.8  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.7  2008/12/03 15:39:50  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:31  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.9  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.8  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.7  2004/11/22 16:01:09  pascal_verdier
// get_string_value method added.
//
// Revision 3.6  2004/11/05 11:59:20  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2004/03/08 11:35:40  pascal_verdier
// AttributeProxy and aliases management added.
// First revision for event management classes.
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
// Revision 2.0  2003/01/09 14:00:37  verdier
// jacORB is now the ORB used.
//
// Revision 1.8  2002/06/26 09:02:17  verdier
// tested with atkpanel on a TACO device
//
// Revision 1.7  2002/04/09 12:21:51  verdier
// IDL 2 implemented.
//
// Revision 1.6  2002/01/09 12:18:15  verdier
// TACO signals can be read as TANGO attribute.
//
// Revision 1.5  2001/12/10 14:19:42  verdier
// TACO JNI Interface added.
// URL syntax used for connection.
// Connection on device without database added.
//
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
//
//-======================================================================

package fr.esrf.TangoApi;

import java.util.Vector;
 
/** 
 *	Class Description:
 *	This class manage a vector of DbDatum for attribute properties read/write
 *	and the attribute's name associated.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbAttribute extends Vector implements java.io.Serializable
{
	public String		name;

	//===========================================================
	/**
	 *	Default constructor for the DbAttribute Object.
	 *
	 *	@param name	Attribute name.
	 */
	//===========================================================
	public DbAttribute(String name)
	{
		super();
		this.name = name;
	}



	//===========================================================
	/**
	 *	get the DbDatum object by index.
	 *
	 *	@param idx index of the DbDatum expected.
	 */
	//===========================================================
	public DbDatum datum(int idx)
	{
		return (DbDatum) elementAt(idx);
	}

	//===========================================================
	/**
	 *	get the DbDatum object by DbDatum.name.
	 *
	 *	@param name index of the DbDatum expected.
	 */
	//===========================================================
	public DbDatum datum(String name)
	{
		DbDatum	datum;
		for (int i=0 ; i<size() ; i++)
		{
			datum = (DbDatum)elementAt(i);
			if (name.equalsIgnoreCase(datum.name))
				return datum;
		}
		return null;
	}

	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 */
	//===========================================================
	public void add(String name)
	{
		addElement(new DbDatum(name, ""));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param datum	property name and value
	 */
	//===========================================================
	public void add(DbDatum datum)
	{
		addElement(datum);
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param value	property value
	 */
	//===========================================================
	public void add(String name, String value)
	{
		addElement(new DbDatum(name, value));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param value	property value
	 */
	//===========================================================
	public void add(String name, short value)
	{
		addElement(new DbDatum(name, value));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param value	property value
	 */
	//===========================================================
	public void add(String name, int value)
	{
		addElement(new DbDatum(name, value));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param value	property value
	 */
	//===========================================================
	public void add(String name, double value)
	{
		addElement(new DbDatum(name, value));
	}





	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param values	property value
	 */
	//===========================================================
	public void add(String name, String[] values)
	{
		addElement(new DbDatum(name, values));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param values	property value
	 */
	//===========================================================
	public void add(String name, short[] values)
	{
		addElement(new DbDatum(name, values));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param values	property value
	 */
	//===========================================================
	public void add(String name, int[] values)
	{
		addElement(new DbDatum(name, values));
	}
	//===========================================================
	/**
	 *	Add a new DbDatum in Vector
	 *
	 *	@param name		property name
	 *	@param values	property value
	 */
	//===========================================================
	public void add(String name, double[] values)
	{
		addElement(new DbDatum(name, values));
	}
	//===========================================================
	/**
	 *	Return the property name
	 *
	 *	@param idx		index of property
	 *	@return property name
	 */
	//===========================================================
	public String get_property_name(int idx)
	{
		return datum(idx).name;
	}

	//===========================================================
	/**
	 *	Return the property value
	 *
	 *	@param idx		index of property
	 *	@return property values in an array of Strings
	 */
	//===========================================================
	public String[] get_value(int idx)
	{
		return datum(idx).extractStringArray();
	}
	//===========================================================
	/**
	 *	Return the property value as a String object
	 *
	 *	@param idx		index of property
	 *	@return property value in a String object.
	 */
	//===========================================================
	public String get_string_value(int idx)
	{
		String[]	array = datum(idx).extractStringArray();
		String		str = "";
		for (int i=0 ; i<array.length ; i++)
		{
			str += array[i];
			if (i<array.length-1)
				str += "\n";
		}
		return str;
	}
	//===========================================================
	/**
	 *	Return the property value
	 *
	 *	@param name		property name
	 *	@return property value in an array of Strings
	 */
	//===========================================================
	public String[] get_value(String name)
	{
		return datum(name).extractStringArray();
	}
	//===========================================================
	/**
	 *	Return the property value in aString object
	 *
	 *	@param name		property name
	 *	@return property value in aString object
	 */
	//===========================================================
	public String get_string_value(String name)
	{
		String[]	array = datum(name).extractStringArray();
		String		str = "";
		for (int i=0 ; i<array.length ; i++)
		{
			str += array[i];
			if (i<array.length-1)
				str += "\n";
		}
		return str;
	}
	//===========================================================
	/**
	 *	Return true if property not found;
	 *
	 *	@param name		property name
	 *	@return true if property not found;
	 */
	//===========================================================
	public boolean is_empty(String name)
	{
		DbDatum datum = datum(name);
		if (datum==null)
			return true;
		else
			return datum.is_empty();
	}
	//===========================================================
	/**
	 *	Return a list of properties found;
	 *
	 *	@return a list of properties found;
	 */
	//===========================================================
	public String[] get_property_list()
	{
		String[]	array = new String[size()];
		for (int i=0 ; i<size() ; i++)
			array[i] = datum(i).name;
		return array;
	}
}
