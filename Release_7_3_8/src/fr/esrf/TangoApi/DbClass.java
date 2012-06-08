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
// Revision 1.7  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
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
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:22  pascal_verdier
// Using JacORB-2.1
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

import fr.esrf.Tango.DevFailed;


/**
 *	Class Description:
 *	This class manage database connection for Tango Class.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbClass implements java.io.Serializable
{
	/**
	 *	Database object used for TANGO databse access.
	 */
	private Database	dbase;

	/**
	 *	Device name used to access database if device not exported.
	 */
	private String classname;

	//===================================================================
	/**
	 *	DbClass constructor.
	 *	It will make a connection to the TANGO database.
	 *
	 *	@param	classname		Name of the class oject.
	 */
	//===================================================================
	public DbClass(String classname) throws DevFailed
	{
		//	Access the database and get device server info
		//--------------------------------------------------
		dbase = ApiUtil.get_db_obj();
		this.classname = classname;
	}

	//===================================================================
	/**
	 *	DbClass constructor.
	 *	It will make a connection to the TANGO database.
	 *
	 *	@param	classname		Name of the class oject.
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public DbClass(String classname, String host, String port) throws DevFailed
	{
		//	Access the database and get device server info
		//--------------------------------------------------
		dbase = ApiUtil.get_db_obj(host, port);
		this.classname = classname;
	}


	//==========================================================================
	/**
	 *	Query the database for a list of class
	 *	properties for the pecified object.
	 *	@param wildcard	propertie's wildcard (* matches any charactere).
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public String[] get_property_list(String wildcard)
				throws DevFailed
	{
		return dbase.get_class_property_list(classname, wildcard);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of properties for this class.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum[] get_property(String[] propnames)
				throws DevFailed
	{
		return dbase.get_class_property(classname, propnames);
	}
	//==========================================================================
	/**
	 *	Query the database for a property for this class.
	 *	@param propname		property name.
	 *	@return properties in DbDatum object.
	 */
	//==========================================================================
	public DbDatum get_property(String propname)
				throws DevFailed
	{
		return dbase.get_class_property(classname, propname);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of properties for this class.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum[] get_property(DbDatum[] properties)
				throws DevFailed
	{
		return dbase.get_class_property(classname, properties);
	}

	//==========================================================================
	/**
	 *	Insert or update a list of properties for this class
	 *	The property names and their values are specified by the DbDatum array.
	 *	
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public void put_property(DbDatum[] properties)
				throws DevFailed
	{
		dbase.put_class_property(classname, properties);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this class.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_property(String[] propnames)
				throws DevFailed
	{
		dbase.delete_class_property(classname, propnames);
	}
	//==========================================================================
	/**
	 *	Delete a property for this class.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_property(String propname)
				throws DevFailed
	{
		dbase.delete_class_property(classname, propname);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this class.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public void delete_property(DbDatum[] properties)
				throws DevFailed
	{
		dbase.delete_class_property(classname, properties);
	}


	//================================================================
	//	ATTRIBUTES
	//================================================================
	//==========================================================================
	/**
	 *	Query the database for a attributes defined for a class.
	 *	All attributes for a class attribute are returned.
	 *
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return attributes list for specified class
	 */
	//==========================================================================
	public String[] get_attribute_list(String wildcard) throws DevFailed
	{
		return dbase.get_class_attribute_list(classname, wildcard);
	}
	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for this class.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param attr attribute names and properties names and values.
	 */
	//==========================================================================
	public void put_attribute_property(DbAttribute[] attr)
				throws DevFailed
	{
		dbase.put_class_attribute_property(classname, attr);
	}
	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for this class.
	 *	The property names and their values are specified by the DbAttribue.
	 *	
	 *	@param attr attribute name and properties names and values.
	 */
	//==========================================================================
	public void put_attribute_property(DbAttribute attr)
				throws DevFailed
	{
		dbase.put_class_attribute_property(classname, attr);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_attribute_property(String attname, String[] propnames)
				throws DevFailed
	{
		dbase.delete_class_attribute_property(classname, attname, propnames);
	}
	//==========================================================================
	/**
	 *	Delete a property for this object.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_attribute_property(String attname, String propname)
				throws DevFailed
	{
		dbase.delete_class_attribute_property(classname, attname, propname);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of class attribute
	 *	properties for this device.
	 *	@param attnames attribute names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbAttribute[] get_attribute_property(String[] attnames)
				throws DevFailed
	{
		return dbase.get_class_attribute_property(classname, attnames);
	}
	//==========================================================================
	/**
	 *	Query the database for of class attribute
	 *	property for this device.
	 *	@param attname attribute name.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbAttribute get_attribute_property(String attname)
				throws DevFailed
	{
		return dbase.get_class_attribute_property(classname, attname);
	}


	//===========================================================
	/**
	 *	return the class name.
	 */
	//===========================================================
	public String name()
	{
		return classname;
	}
}
