package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.events.DbEventImportInfo;

public interface IDatabaseDAO extends IConnectionDAO{

	
	//===================================================================
	/**
	 *	Database access init method.
	 *
	 *	@throws DevFailed in case of environment not corectly set.
	 */
	//===================================================================
	public abstract void init(Database database) throws DevFailed;	
	
	//===================================================================
	/**
	 *	Database access constructor.
	 *
	 *	@param	host	host where database is running.
	 *	@param	port	port for database connection.
	 *	@throws DevFailed in case of host or port not available
	 */
	//===================================================================
	public abstract void init(Database database, String host, String port) throws DevFailed;	
	
	//==========================================================================
	//==========================================================================
	public abstract String toString(Database database);

	//==========================================================================
	/**
	 *	Query the database for general info about the table in the database.
	 *	@return	the result of the query as String.
	 */
	//==========================================================================
	public abstract String get_info(Database database) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of host registred.
	 *	@return	the list of all hosts registred in TANGO database.
	 */
	//==========================================================================
	public abstract String[] get_host_list(Database database) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of host registred.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return	the list of the hosts registred in TANGO database 
	 *			with the specified wildcard.
	 */
	//==========================================================================
	public abstract String[] get_host_list(Database database, String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of classes instancied for a server.
	 *
	 *	@param	servname server name and instance name (ie.: Serial/i1).
	 *	@return	the list of all classes registred in TANGO database for 
	 *			servname except the DServer class
	 *			(existing on all Tango device server).
	 */
	//==========================================================================
	public abstract String[] get_server_class_list(Database database, String servname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of server names registred in the database.
	 *	@return	the list of all server names registred in TANGO database.
	 */
	//==========================================================================
	public abstract String[] get_server_name_list(Database database) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of instance names
	 *		registred for specified server name.
	 *
	 *	@param	servname	server name.
	 *	@return	the list of all instance names for specified server name.
	 */
	//==========================================================================
	public abstract String[] get_instance_name_list(Database database, String servname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of servers registred in the database.
	 *	@return	the list of all servers registred in TANGO database.
	 */
	//==========================================================================
	public abstract String[] get_server_list(Database database) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of servers registred in the database.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return	the list of all servers registred in TANGO database.
	 */
	//==========================================================================
	public abstract String[] get_server_list(Database database, String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of servers registred on the specified host.
	 *	@param hostname	the specified host name.
	 *	@return	the list of the servers registred in TANGO database 
	 *			for the specified host.
	 */
	//==========================================================================
	public abstract String[] get_host_server_list(Database database, String hostname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for server information.
	 *	@param servname	The specified server name.
	 *	@return	The information found for the specified server
	 *				in a DBServInfo object.
	 */
	//==========================================================================
	public abstract DbServInfo get_server_info(Database database, String servname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Add/update server information in databse.
	 *	@param info	Server information for the specified server
	 *					in a DbServinfo object.
	 */
	//==========================================================================
	public abstract void put_server_info(Database database, DbServInfo info) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete server information in databse.
	 *	@param servname	Server name.
	 */
	//==========================================================================
	public abstract void delete_server_info(Database database, String servname) throws DevFailed;

	//==========================================================================
	/**
	 *	Add/update a device to the database
	 *	@param devinfo The device name, class and server  specified in object.
	 */
	//==========================================================================
	public abstract void add_device(Database database, DbDevInfo devinfo) throws DevFailed;

	//==========================================================================
	/**
	 *	Add/update a device to the database
	 *	@param devname		The device name
	 *	@param classname	The class.
	 *	@param servname		The server name.
	 */
	//==========================================================================
	public abstract void add_device(Database database, String devname, String classname,
			String servname) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete the device of the specified name from the database
	 *	@param devname The device name.
	 */
	//==========================================================================
	public abstract void delete_device(Database database, String devname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for the export and more info of the specified device.
	 *	@param devname The device name.
	 *	@return the information in a DbGetDeviceInfo.
	 */
	//==========================================================================
	public abstract DeviceInfo get_device_info(Database database, String devname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for the export info of the specified device.
	 *	@param devname The device name.
	 *	@return the information in a DbDevImportInfo.
	 */
	//==========================================================================
	public abstract DbDevImportInfo import_device(Database database, String devname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Mark the specified server as unexported in the database.
	 *	@param devname The device name.
	 */
	//==========================================================================
	public abstract void unexport_device(Database database, String devname) throws DevFailed;

	//==========================================================================
	/**
	 *	Update the export info fort this device in the database.
	 *	@param devinfo	Device information to export.
	 */
	//==========================================================================
	public abstract void export_device(Database database, DbDevExportInfo devinfo)
			throws DevFailed;

	//**************************************
	//       Devices list MANAGEMENT
	//**************************************	
	//==========================================================================
	/**
	 *	Query the database for server devices and classes.
	 *	@param servname	The specified server name.
	 *	@return	The devices and classes (e.g. "id11/motor/1", "StepperMotor",
	 *			"id11/motor/2", "StepperMotor",....)
	 */
	//==========================================================================
	public abstract String[] get_device_class_list(Database database, String servname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of devices served by the specified server
	 *	and of the specified class.
	 *	@param servname		The server name.
	 *	@param classname	The class name
	 *	@return the device names are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String[] get_device_name(Database database, String servname, String classname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device domain names witch match
	 *	the wildcard provided.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return the device domain are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String[] get_device_domain(Database database, String wildcard)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device family names witch match
	 *	the wildcard provided.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return the device family are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String[] get_device_family(Database database, String wildcard)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device member names witch match
	 *	the wildcard provided.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return the device member are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String[] get_device_member(Database database, String wildcard)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Add a group of devices to the database.
	 *	@param servname	Server name for these devices.
	 *	@param devinfo	Devices and server information.
	 */
	//==========================================================================
	public abstract void add_server(Database database, String servname, DbDevInfo[] devinfo)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete the device server and its associated devices from the database.
	 *	@param devname the device name.
	 */
	//==========================================================================
	public abstract void delete_server(Database database, String devname) throws DevFailed;

	//==========================================================================
	/**
	 *	Add a group of devices to the database.
	 *	@param devinfo	Devices and server information.
	 */
	//==========================================================================
	public abstract void export_server(Database database, DbDevExportInfo[] devinfo)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Mark all devices exported for this device server as unexported.
	 *	@param devname the device name.
	 */
	//==========================================================================
	public abstract void unexport_server(Database database, String devname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of object (ie non-device)
	 *	for which properties are defiend.
	 *	@param wildcard	wildcard (* matches any charactere).
	 *	@return objects for which properties are defiened list.
	 */
	//==========================================================================
	public abstract String[] get_object_list(Database database, String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of object (ie non-device)
	 *	for which properties are defiend.
	 *	@param objname	object name.
	 *	@param wildcard wildcard (* matches any charactere).
	 *	@return Property names..
	 */
	//==========================================================================
	public abstract String[] get_object_property_list(Database database, String objname,
			String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of object (ie non-device)
	 *	properties for the pecified object.
	 *	@param name Object name.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_property(Database database, String name, String[] propnames)
			throws DevFailed;

	
	// ==========================================================================
	/**
	 * Query the database for an object (ie non-device) property for the
	 * pecified object without access check (initilizing phase).
	 * 
	 * @param name
	 *            Object name.
	 * @param propname
	 *            list of property names.
	 * @return property in DbDatum object.
	 */
	// ==========================================================================	
	public DbDatum get_property(Database database, String name, String propname, boolean forced) throws DevFailed;
	
	//==========================================================================
	/**
	 *	Query the database for an object (ie non-device)
	 *	property for the pecified object.
	 *	@param name Object name.
	 *	@param propname list of property names.
	 *	@return property in DbDatum object.
	 */
	//==========================================================================
	public abstract DbDatum get_property(Database database, String name, String propname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of object (ie non-device)
	 *	properties for thr dpecified object.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param name Object name.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_property(Database database, String name, DbDatum[] properties)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of properties for the specified object
	 *	The property names and their values are specified by the DbDatum array.
	 *	
	 *	@param name Object name.
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public abstract void put_property(Database database, String name, DbDatum[] properties)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Object name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_property(Database database, String name, String[] propnames)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a  property for the specified object.
	 *	@param name Object name.
	 *	@param propname Property names.
	 */
	//==========================================================================
	public abstract void delete_property(Database database, String name, String propname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Object name.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public abstract void delete_property(Database database, String name, DbDatum[] properties)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of class
	 *	properties for the pecified object.
	 *	@param classname	device name.
	 *	@param wildcard	propertie's wildcard (* matches any charactere).
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract String[] get_class_property_list(Database database, String classname,
			String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device
	 *	properties for the pecified object.
	 *	@param devname	device name.
	 *	@param wildcard	propertie's wildcard (* matches any charactere).
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract String[] get_device_property_list(Database database, String devname,
			String wildcard) throws DevFailed;

	//==========================================================================
	//==========================================================================
	public abstract String get_class_for_device(Database database, String devname)
			throws DevFailed;

	//==========================================================================
	//==========================================================================
	public abstract String[] get_class_inheritance_for_device(Database database, String devname)
			throws DevFailed;

	//**************************************
	//       DEVICE PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/**
	 *	Query the database for a list of device
	 *	properties for the pecified object.
	 *	@param name device name.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_device_property(Database database, String name,
			String[] propnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a  device
	 *	property for the pecified object.
	 *	@param name device name.
	 *	@param propname property name.
	 *	@return property in DbDatum object.
	 */
	//==========================================================================
	public abstract DbDatum get_device_property(Database database, String name, String propname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device
	 *	properties for the pecified object.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param name device name.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_device_property(Database database, String name,
			DbDatum[] properties) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of properties for the specified device
	 *	The property names and their values are specified by the DbDatum array.
	 *	
	 *	@param name device name.
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public abstract void put_device_property(Database database, String name, DbDatum[] properties)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Device name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_device_property(Database database, String name, String[] propnames)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a property for the specified object.
	 *	@param name Device name.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public abstract void delete_device_property(Database database, String name, String propname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Device name.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public abstract void delete_device_property(Database database, String name,
			DbDatum[] properties) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device attributes
	 *	@param devname device name.
	 *	@return attribute names.
	 */
	//==========================================================================
	public abstract String[] get_device_attribute_list(Database database, String devname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of device attributes
	 *	properties for the pecified object.
	 *	@param devname device name.
	 *	@param attnames attribute names.
	 *	@return properties in DbAttribute objects array.
	 */
	//==========================================================================
	public abstract DbAttribute[] get_device_attribute_property(Database database, String devname,
			String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for device attribute
	 *	property for the pecified object.
	 *	@param devname device name.
	 *	@param attname attribute name.
	 *	@return property in DbAttribute object.
	 */
	//==========================================================================
	public abstract DbAttribute get_device_attribute_property(Database database, String devname,
			String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for the specified device.
	 *	The property names and their values are specified by the DbAttribute array.
	 *	
	 *	@param devname	device name.
	 *	@param attr		attribute names, and properties (names and values).
	 */
	//==========================================================================
	public abstract void put_device_attribute_property(Database database, String devname,
			DbAttribute[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of attribute properties for the specified device.
	 *	The property names and their values are specified by the DbAttribute.
	 *	
	 *	@param devname	device name.
	 *	@param attr	attribute name, and properties (names and values).
	 */
	//==========================================================================
	public abstract void put_device_attribute_property(Database database, String devname,
			DbAttribute attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete an list of attributes properties for the specified object.
	 *	@param devname Device name.
	 *	@param attr	attribute name, and properties (names).
	 */
	//==========================================================================
	public abstract void delete_device_attribute_property(Database database, String devname,
			DbAttribute attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of attributes properties for the specified object.
	 *	@param devname Device name.
	 *	@param attr	attribute names, and properties (names) in array.
	 */
	//==========================================================================
	public abstract void delete_device_attribute_property(Database database, String devname,
			DbAttribute[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param devname	Device name.
	 *	@param attname	Attribute name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_device_attribute_property(Database database, String devname,
			String attname, String[] propnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a property for the specified object.
	 *	@param devname	Device name.
	 *	@param attname	Attribute name.
	 *	@param propname	Property name.
	 */
	//==========================================================================
	public abstract void delete_device_attribute_property(Database database, String devname,
			String attname, String propname) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete an attribute for the specified object.
	 *	@param devname	Device name.
	 *	@param attname	Attribute name.
	 */
	//==========================================================================
	public abstract void delete_device_attribute(Database database, String devname, String attname)
			throws DevFailed;

	//**************************************
	//      CLASS PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/**
	 *	Query the database for a list of classes registred in the database.
	 *	@param servname	server name
	 *	@return	the list of all servers registred in TANGO database.
	 */
	//==========================================================================
	public abstract String[] get_class_list(Database database, String servname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of class
	 *	properties for the pecified object.
	 *	@param name Class name.
	 *	@param propnames list of property names.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_class_property(Database database, String name, String[] propnames)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a class
	 *	property for the pecified object.
	 *	@param name Class name.
	 *	@param propname list of property names.
	 *	@return property in DbDatum object.
	 */
	//==========================================================================
	public abstract DbDatum get_class_property(Database database, String name, String propname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of class
	 *	properties for the pecified object.
	 *	The property names are specified by the DbDatum array objects.
	 *	@param name Class name.
	 *	@param properties list of property DbDatum objects.
	 *	@return properties in DbDatum objects.
	 */
	//==========================================================================
	public abstract DbDatum[] get_class_property(Database database, String name,
			DbDatum[] properties) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of properties for the specified class.
	 *	The property names and their values are specified by the DbDatum array.
	 *	
	 *	@param name Class name.
	 *	@param properties Properties names and values array.
	 */
	//==========================================================================
	public abstract void put_class_property(Database database, String name, DbDatum[] properties)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Class name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_class_property(Database database, String name, String[] propnames)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a property for the specified object.
	 *	@param name Class name.
	 *	@param propname Property name.
	 */
	//==========================================================================
	public abstract void delete_class_property(Database database, String name, String propname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Class name.
	 *	@param properties Property DbDatum objects.
	 */
	//==========================================================================
	public abstract void delete_class_property(Database database, String name, DbDatum[] properties)
			throws DevFailed;

	//**************************************
	//      CLASS Attribute PROPERTIES MANAGEMENT
	//**************************************	
	//==========================================================================
	/**
	 *	Query the database for a attributes defined for a class.
	 *	All attributes for a class attribute are returned.
	 *
	 *	@param classname	class name.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return attributes list for specified class
	 */
	//==========================================================================
	public abstract String[] get_class_attribute_list(Database database, String classname,
			String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a attribute properties for trhe specified class.
	 *
	 *	@param classname	class name.
	 *	@param attname		attribute name
	 *	@return attribute properties for specified class and attribute.
	 */
	//==========================================================================
	public abstract DbAttribute get_class_attribute_property(Database database, String classname,
			String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of class attributes
	 *	properties for the pecified object.
	 *	@param classname Class name.
	 *	@param attnames list of attribute names.
	 *	@return attribute properties for specified class and attributes.
	 */
	//==========================================================================
	public abstract DbAttribute[] get_class_attribute_property(
			Database database, String classname, String[] attnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of properties for the specified class attribute.
	 *	The attribute name, the property names and their values
	 *	are specified by the DbAttribute.
	 *	
	 *	@param classname Class name.
	 *	@param attr		DbAttribute objects containing attribute names,
	 *					property names and property values.
	 */
	//==========================================================================
	public abstract void put_class_attribute_property(Database database, String classname,
			DbAttribute[] attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Insert or update a list of properties for the specified class attribute.
	 *	The attribute name, the property names and their values
	 *	are specified by the DbAttribute.
	 *	
	 *	@param classname Class name.
	 *	@param attr		DbAttribute object containing attribute name,
	 *					property names and property values.
	 */
	//==========================================================================
	public abstract void put_class_attribute_property(Database database, String classname,
			DbAttribute attr) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a property for the specified object.
	 *	@param name Class name.
	 *	@param propname Property names.
	 */
	//==========================================================================
	public abstract void delete_class_attribute_property(Database database, String name,
			String attname, String propname) throws DevFailed;

	//==========================================================================
	/**
	 *	Delete a list of properties for the specified object.
	 *	@param name Class name.
	 *	@param attname attribute name.
	 *	@param propnames Property names.
	 */
	//==========================================================================
	public abstract void delete_class_attribute_property(Database database, String name,
			String attname, String[] propnames) throws DevFailed;

	//==========================================================================
	/**
	 *	Query database for list of exported devices.
	 *
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return	The list of exported devices
	 */
	//==========================================================================
	public abstract String[] get_device_exported(Database database, String wildcard)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query database for list of exported devices for the specified class name.
	 *
	 *	@param classname	class name to query the exported devices.
	 *	@return	The list of exported devices
	 */
	//==========================================================================
	public abstract String[] get_device_exported_for_class(Database database, String classname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of aliases for the specified wildcard.
	 *	@param wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return the device aliases are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String[] get_device_alias_list(Database database, String wildcard)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for an alias for the specified device.
	 *	@param devname	device's name.
	 *	@return the device alias found.
	 */
	//==========================================================================
	public abstract String get_device_alias(Database database, String devname) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database a device for the specified alias.
	 *	@param alias	The device name.alias
	 *	@return the device aliases are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String get_alias_device(Database database, String alias) throws DevFailed;

	//==========================================================================
	/**
	 *	Set an alias for a device name
	 *	@param devname device name.
	 *	@param aliasname alias name.
	 */
	//==========================================================================
	public abstract void put_device_alias(Database database, String devname, String aliasname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database to delete alias for the specified device alias.
	 *	@param alias	device alias name.
	 */
	//==========================================================================
	public abstract void delete_device_alias(Database database, String alias) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of aliases for the specified wildcard.
	 *	@param 	wildcard	Wildcard char is '*' and matches wildvcard characters.
	 *	@return the device aliases are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String[] get_attribute_alias_list(Database database, String wildcard)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for a list of aliases for the specified attribute.
	 *	@param	attname	The attribute name.
	 *	@return the device aliases are stored in an array of strings.
	 */
	//==========================================================================
	public abstract String get_attribute_alias(Database database, String attname) throws DevFailed;

	//==========================================================================
	/**
	 *	Set an alias for a attribute name
	 *	@param attname attribute name.
	 *	@param aliasname alias name.
	 */
	//==========================================================================
	public abstract void put_attribute_alias(Database database, String attname, String aliasname)
			throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database to delete alias for the specified attribute alias.
	 *	@param alias	device alias name.
	 */
	//==========================================================================
	public abstract void delete_attribute_alias(Database database, String alias) throws DevFailed;

	//==========================================================================
	//==========================================================================
	public abstract String[] getDevices(Database database, String wildcard) throws DevFailed;

	//==========================================================================
	/**
	 *	Query the database for the export info of the specified event.
	 *	@param  channel_name	The event name.
	 *	@return the information in a DbEventImportInfo.
	 */
	//==========================================================================
	public abstract DbEventImportInfo import_event(Database database, String channel_name)
			throws DevFailed;

	//==========================================================================
	/**
	 * Returns the history of the specified device property.
	 * @param devname Device name
	 * @param propname Property name (can be wildcarded)
	 * @throws DevFailed in case of failure
	 */
	//==========================================================================
	public abstract DbHistory[] get_device_property_history(Database database, String devname,
			String propname) throws DevFailed;

	//==========================================================================
	/**
	 * Returns the history of the specified device attribute property.
	 * @param devname Device name
	 * @param attname Attribute name (can be wildcarded)
	 * @param propname Property name (can be wildcarded)
	 * @throws DevFailed in case of failure
	 */
	//==========================================================================
	public abstract DbHistory[] get_device_attribute_property_history(
			Database database, String devname, String attname, String propname) throws DevFailed;

	//==========================================================================
	/**
	 * Returns the history of the specified class property.
	 * @param classname Class name
	 * @param propname Property name (can be wildcarded)
	 * @throws DevFailed in case of failure
	 */
	//==========================================================================
	public abstract DbHistory[] get_class_property_history(Database database, String classname,
			String propname) throws DevFailed;

	//==========================================================================
	/**
	 * Returns the history of the specified class attribute property.
	 * @param classname Class name
	 * @param attname Attribute name (can be wildcarded)
	 * @param propname Property name (can be wildcarded)
	 * @throws DevFailed in case of failure
	 */
	//==========================================================================
	public abstract DbHistory[] get_class_attribute_property_history(
			Database database, String classname, String attname, String propname) throws DevFailed;

	//==========================================================================
	/**
	 * Returns the history of the specified object property.
	 * @param objname Object name
	 * @param propname Property name (can be wildcarded)
	 * @throws DevFailed in case of failure
	 */
	//==========================================================================
	public abstract DbHistory[] get_property_history(Database database, String objname,
			String propname) throws DevFailed;

	//===================================================================
	/**
	 *	Query database for specified services.
	 *
	 *	@param	servicename		The service name.
	 *	@param	instname		The instance name (could be * for all instances).
	 *	@return The device names found for specified service and instance.
	 *	@throws DevFailed in case of failure
	 */
	//===================================================================
	public abstract String[] getServices(Database database, String servicename, String instname)
			throws DevFailed;

	//===============================================================
	/**
	 *	Register a device as a Tango service :
	 *	<b>ServiceName/InstanceName:DeviceName</b>
	 *
	 *	@param serviceName	Service's name
	 *	@param instanceName	Instance service's name
	 *	@param devname		Device's name
	 */
	//===============================================================
	public abstract void registerService(Database database, String serviceName,
			String instanceName, String devname) throws DevFailed;

	//===============================================================
	/**
	 *	Unregister a device as a Tango service :
	 *	<b>ServiceName/InstanceName:DeviceName</b>
	 *
	 *	@param serviceName	Service's name
	 *	@param instanceName	Instance service's name
	 *	@param devname		Device's name
	 */
	//===============================================================
	public abstract void unregisterService(Database database, String serviceName,
			String instanceName, String devname) throws DevFailed;
	//===================================================================
	//===================================================================

	//===================================================================
	/**
	 *	Check Tango Access.
	 *	 - Check if control access is requested.
	 *	 - Check who is the user and the host.
	 *	 - Check access for this user, this host and the specified device.
	 *
	 *	@param devname Specified device name.
	 *	@returns The Tango access controle found.
	 */
	//===================================================================	
	public int checkAccessControl(Database database, String devname);
	
	/**
	 *	Check for specified device, the specified command is allowed.
	 *
	 *	@param	devname Specified device name.
	 *	@param	cmd Specified command name.
	 */	
	public boolean isCommandAllowed(Database database, String devname, String cmd) throws DevFailed;
	
}