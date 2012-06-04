//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012
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
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;


/**
 *	Class Description:
 *	This class manage database connection for Tango device.
 *	It is an api between user and IDL Device object.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbDevice implements java.io.Serializable
{
	/**
	 *	Database object used for TANGO databse access.
	 */
	private Database	dbase;

	/**
	 *	Device name used to access database if device not exported.
	 */
	private String devname;

	//===================================================================
	/**
	 *	DbDevice constructor.
	 *	It will make a connection to the TANGO database.
	 *
	 *	@param	devname		Name of the device to be imported.
	 */
	//===================================================================
	public DbDevice(String devname) throws DevFailed
	{
		//	Check if database is  used.
		TangoUrl	url = new TangoUrl(devname);
		if (url.use_db==false)
			Except.throw_non_db_exception("Api_NonDatabaseDevice",
					"Device " + devname + " do not use database",
					"DbDevice.DbDevice()");

		//	Access the database
		//------------------------
		dbase = ApiUtil.get_db_obj();
		this.devname = devname;
	}
	//===================================================================
	/**
	 *	DbDevice constructor.
	 *	It will make a connection to the TANGO database.
	 *
	 *	@param	devname		Name of the device to be imported.
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 */
	//===================================================================
	public DbDevice(String devname, String host, String port) throws DevFailed
	{
		//	Access the database
		//------------------------
		if (host==null || port==null)
			dbase = ApiUtil.get_db_obj();
		else
			dbase = ApiUtil.get_db_obj(host, port);
		this.devname = devname;
	}
	//==========================================================================
	/**
	 *	Query the database for the info of this device.
	 *	@return the information in a DeviceInfo.
	 */
	//==========================================================================
	public DeviceInfo get_info()
				throws DevFailed
	{
		return dbase.get_device_info(devname);
	}
	//==========================================================================
	/**
	 *	Query the database for the export info of this device.
	 *	@return the information in a DbDevImportInfo.
	 */
	//==========================================================================
	public DbDevImportInfo import_device()
				throws DevFailed
	{
		return dbase.import_device(devname);
	}
	//==========================================================================
	/**
	 *	Update the export info for this device in the database.
	 *	@param devinfo	Device information to export.
	 */
	//==========================================================================
	public void export_device(DbDevExportInfo devinfo)
				throws DevFailed
	{
		dbase.export_device(devinfo);
	}
	//==========================================================================
	/**
	 *	Unexport the divice in database.
	 */
	//==========================================================================
	public void unexport_device() throws DevFailed
	{
		dbase.unexport_device(devname);
	}
	//==========================================================================
	/**
	 *	Add/update this device to the database
	 *	@param devinfo The device name, class and server  specified in object.
	 */
	//==========================================================================
	public void add_device(DbDevInfo devinfo) throws DevFailed
	{
		dbase.add_device(devinfo);
	}
	//==========================================================================
	/**
	 *	Delete this device from the database
	 */
	//==========================================================================
	public void delete_device() throws DevFailed
	{
		dbase.delete_device(devname);
	}
	//==========================================================================
	//==========================================================================
	public String get_class() throws DevFailed
	{
		return dbase.get_class_for_device(devname);
	}
	//==========================================================================
	//==========================================================================
	public String[] get_class_inheritance() throws DevFailed
	{
		return dbase.get_class_inheritance_for_device(devname);
	}
	//==========================================================================
	/**
	 *	Set an alias for a device name
	 *	@param aliasname alias name.
	 */
	//==========================================================================
	public void put_alias(String aliasname)
				throws DevFailed
	{
		dbase.put_device_alias(devname, aliasname);
	}
	//==========================================================================
	/**
	 *	Get an alias for a device name
	 */
	//==========================================================================
	public String get_alias()
				throws DevFailed
	{
		//	check if devname is already an alias.
		if (devname.indexOf("/")<0)
		{
			//	if alias -> replace alias by real device name
			devname = ApiUtil.get_db_obj().get_alias_device(devname);
		}

		//	Then query database for an alias for devname.
		return dbase.get_device_alias(devname);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device
	 *	properties for the pecified object.
	 *	@param wildcard	propertie's wildcard (* matches any charactere).
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public String[] get_property_list(String wildcard)
				throws DevFailed
	{
		return dbase.get_device_property_list(devname, wildcard);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device properties for this device.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum[] get_property(String[] propnames)
				throws DevFailed
	{
		return dbase.get_device_property(devname, propnames);
	}
	//==========================================================================
	/**
	 *	Query the database for a device property for this device.
	 *	@param propname property name.
	 *	@return property in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum get_property(String propname)
				throws DevFailed
	{
		return dbase.get_device_property(devname, propname);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device properties for this device.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public DbDatum[] get_property(DbDatum[] properties)
				throws DevFailed
	{
		return dbase.get_device_property(devname, properties);
	}

	//==========================================================================
	/**
	 *	Insert or update a list of properties for this device
	 *	The property names and their values are specified by the DbDatum array.
	 *	
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public void put_property(DbDatum[] properties)
				throws DevFailed
	{
		dbase.put_device_property(devname, properties);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this device.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_property(String[] propnames)
				throws DevFailed
	{
		dbase.delete_device_property(devname, propnames);
	}
	//==========================================================================
	/**
	 *	Delete a property for this device.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_property(String propname)
				throws DevFailed
	{
		dbase.delete_device_property(devname, propname);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this device.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public void delete_property(DbDatum[] properties)
				throws DevFailed
	{
		dbase.delete_device_property(devname, properties);
	}

	//============================================
	//	ATTRIBUTE PROPERTY MANAGEMENT
	//============================================

	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param attr attribute names and properties (names and values) array.
	 */
	//==========================================================================
	public void put_attribute_property(DbAttribute[] attr)
				throws DevFailed
	{
		dbase.put_device_attribute_property(devname, attr);
	}
	//==========================================================================
	/**
	 *	Insert or update an attribute properties for this device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param attr attribute name and properties (names and values).
	 */
	//==========================================================================
	public void put_attribute_property(DbAttribute attr)
				throws DevFailed
	{
		dbase.put_device_attribute_property(devname, attr);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param attname attribute name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public void delete_attribute_property(String attname, String[] propnames)
				throws DevFailed
	{
		dbase.delete_device_attribute_property(devname, attname, propnames);
	}
	//==========================================================================
	/**
	 *	Delete a property for this object.
	 *	@param attname attribute name.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public void delete_attribute_property(String attname, String propname)
				throws DevFailed
	{
		dbase.delete_device_attribute_property(devname, attname, propname);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param	attr	specified attribute
	 */
	//==========================================================================
	public void delete_attribute_property(DbAttribute attr)
				throws DevFailed
	{
		dbase.delete_device_attribute_property(devname, attr);
	}
	//==========================================================================
	/**
	 *	Delete a list of properties for this object.
	 *	@param	attr	specified attributes
	 */
	//==========================================================================
	public void delete_attribute_property(DbAttribute[] attr)
				throws DevFailed
	{
		dbase.delete_device_attribute_property(devname, attr);
	}

	//==========================================================================
	/**
	 *	Query the database for a list of device attributes
	 *	@return attribute names.
	 */
	//==========================================================================
	public String[] get_attribute_list()
				throws DevFailed
	{
		return dbase.get_device_attribute_list(devname);
	}
	//==========================================================================
	/**
	 *	Query the database for a list of device attribute
	 *	properties for this device.
	 *	@param attnames attribute names.
	 *	@return properties in DbAttribute objects array.
	 */
	//==========================================================================
	public DbAttribute[] get_attribute_property(String[] attnames)
				throws DevFailed
	{
		return dbase.get_device_attribute_property(devname, attnames);
	}
	//==========================================================================
	/**
	 *	Query the database for a device attribute
	 *	property for this device.
	 *	@param attname attribute name.
	 *	@return properties in DbAttribute objects.
	 */
	//==========================================================================
	public DbAttribute get_attribute_property(String attname)
				throws DevFailed
	{
		return dbase.get_device_attribute_property(devname, attname);
	}
	//==========================================================================
	/**
	 *	Delete an attribute for this object.
	 *	@param attname attribute name.
	 */
	//==========================================================================
	public void delete_attribute(String attname)
				throws DevFailed
	{
		dbase.delete_device_attribute(devname, attname);
	}
	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified attribute.
	 *
	 *	 @param attname	specified attribute name.
	 */
	//==========================================================================
	public int get_attribute_polling_period(String attname) throws DevFailed
	{
		return get_polling_period(attname, TangoConst.ATTRIBUTE);
	}
	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified command.
	 *
	 *	 @param cmdname	specified attribute name.
	 */
	//==========================================================================
	public int get_command_polling_period(String cmdname) throws DevFailed
	{
		return get_polling_period(cmdname, TangoConst.COMMAND);
	}
	//==========================================================================
	/**
	 *	Returns the polling period (in ms) for specified attribute.
	 *
	 *	@param name	specified attribute or command name.
	 *	@param src	TangoConst.COMMAND or TangoConst.ATTRIBUTE
	 */
	//==========================================================================
	private int get_polling_period(String name, int src) throws DevFailed
	{
		//	Get the polling property
		String		propname = (src==TangoConst.ATTRIBUTE)? "polled_attr" : "polled_cmd";
		DbDatum		datum = get_property(propname);
		if (datum.is_empty())
			Except.throw_exception("NOT_POLLED",
				((src==TangoConst.ATTRIBUTE)? "Attribute " : "Command ") +
				 name + " not polled",
				"DbDevice.get_polling_period()");


		String[]	str = datum.extractStringArray();
		String	_name = name.toLowerCase();
		for (int i=0 ; i<str.length ; i+=2)
		{
			//	Check for name
			if (str[i].toLowerCase().equals(_name))
			{
				//	not last index.
				if (i<str.length-1)
				{
					try
					{
						return Integer.parseInt(str[i+1]);
					}
					catch(NumberFormatException e)
					{
						Except.throw_exception("BAD_PARAM",
							"Period value is not coherent",
							"DbDevice.get_polling_period()");
					}
				}
				else
					Except.throw_exception("BAD_PARAM",
							"Period value is not coherent",
							"DbDevice.get_polling_period()");
					
			}
		}
		Except.throw_exception("NOT_POLLED",
				((src==TangoConst.ATTRIBUTE)? "Attribute " : "Command ") +
				 name + " not polled",
				"DbDevice.get_polling_period()");
		
		return -1;
	}



	//===========================================================
	/**
	 *	return the device name.
	 */
	//===========================================================
	public String name()
	{
		return devname;
	}
}
