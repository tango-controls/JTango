//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013
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
