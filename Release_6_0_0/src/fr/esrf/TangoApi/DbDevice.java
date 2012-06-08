//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 3.13  2007/04/04 14:11:33  pascal_verdier
// Method get_attribute_list() added.
//
// Revision 3.12  2006/09/27 14:14:57  pascal_verdier
// Access Control optimized.
// get_class  and get_class_inheritance added for a device.
//
// Revision 3.11  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.10  2005/02/11 12:50:46  pascal_verdier
// DeviceInfo Object added (Start/Stop device dates).
//
// Revision 3.9  2004/12/07 14:29:30  pascal_verdier
// NonDbDevice exception management added.
//
// Revision 3.8  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.7  2004/06/21 12:24:07  pascal_verdier
// get_alias(String devname) bug fixed.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2003/09/08 11:02:34  pascal_verdier
// *** empty log message ***
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
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;


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
		this.devname = new String(devname);
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
		this.devname = new String(devname);
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
		String	alias = dbase.get_device_alias(devname);
		return alias;
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
	 *	@return properties in DbAttribute objects.
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
	 *	@return properties in DbAttribute objects array.
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
