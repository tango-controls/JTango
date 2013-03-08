//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/srcServer API.
//
// $Author: pascal_verdier $
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
// $Revision:  $
//
//-======================================================================

package fr.esrf.TangoApi;


/** 
 *	This class is able to define a Tango server structure,
 *	loaded from database (classes, devices, properties,...)
 *  It is able to copy it in another database
 *  and/or delete this structure in the database.
 *
 * @author  verdier
 */

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.util.ArrayList;


public class DbServerStructure {
    private String   serverName;
    private ArrayList<TangoClass>  classes = new ArrayList<TangoClass>();
	//===============================================================
    /**
     * Constructor for DbServerStructure.
     * It will read the server structure from database.
     *
     * @param serverName the specified server (ServerName/InstanceName)
     * @throws DevFailed  in case of read database fails.
     */
	//===============================================================
	public DbServerStructure(String serverName) throws DevFailed {
		DbServer    server = new DbServer(serverName);
        this.serverName = serverName;

        //  Check if server defined in database.
        new DeviceProxy("dserver/"+serverName.toLowerCase());

        //  Build Server classes
        String[]    classNames = server.get_class_list();
        classes.add(new TangoClass("DServer"));
        for (String className : classNames) {
             classes.add(new TangoClass(className));
        }
	}
	//===============================================================
    /**
     *
     * @return the server name
     */
	//===============================================================
    public String getName() {
        return serverName;
    }
	//===============================================================
    /**
     *
     * @return list of Tango classes for server
     */
	//===============================================================
    public ArrayList<TangoClass> getClasses() {
        return classes;
    }
	//===============================================================
    /**
     * Put the DbServerStructure object in the database specified by tango host.
     *
     * @param tangoHost the specified tango host (Host:Port)
     * @throws DevFailed in case of write database fails
     */
	//===============================================================
    public void putInDatabase(String tangoHost) throws  DevFailed {
        Database    database = ApiUtil.get_db_obj(tangoHost);
        createTheServer(database);
        putProperties(database);
    }
	//===============================================================
    /**
     *  Check if the server or one of its devices already exists in database specified by tango host
     *
     * @param tangoHost the specified tango host (Host:Port)
     * @return true if serer already defined
     * @throws DevFailed if a database read fails.
     */
	//===============================================================
    public boolean alreadyExists(String tangoHost) throws DevFailed {
        String  header = "tango://"+tangoHost+"/";

        //  Check if server defined in database.
        try {
            new DeviceProxy(header + "dserver/"+serverName.toLowerCase());
            return true;
        }
        catch (DevFailed e){ /* Does not exist. --> Continue */ }

        //  Check if at least one device already defined
        for (TangoClass clazz : classes) {
            for (TangoDevice device : clazz) {
                try {
                    new DeviceProxy(header+device.name);
                    return true;
                }
                catch (DevFailed e) {
                    if (!e.errors[0].desc.contains("not defined in the database"))
                        Except.re_throw_exception(e, "FailedToCheck",
                                "Failed to check " + device.name +  "in " + tangoHost,
                                "DbServerStructure.alreadyExists()");
                }
            }
        }
        return false;
    }
	//===============================================================
    /**
     * Create the server in specified database
     *
     * @param database  the specified database.
     * @throws DevFailed if a write in database fails
     */
	//===============================================================
    private void createTheServer(Database database) throws DevFailed {
        ArrayList<DbDevInfo>    deviceInfoList =new ArrayList<DbDevInfo>();
        for (TangoClass clazz : classes) {
            for (TangoDevice device : clazz) {
                deviceInfoList.add(new DbDevInfo(device.name, clazz.name, serverName));
            }
        }
        DbDevInfo[] deviceInfoArray = new DbDevInfo[deviceInfoList.size()];
        for (int i=0 ; i<deviceInfoList.size() ; i++) {
            deviceInfoArray[i] = deviceInfoList.get(i);
        }
        database.add_server(serverName, deviceInfoArray);
    }
	//===============================================================
    /**
     * put class/device/attribute properties in specified database.
     *
     * @param database the specified database.
     * @throws DevFailed if write database fails.
     */
	//===============================================================
    private void putProperties(Database database) throws DevFailed {
        for (TangoClass clazz : classes) {
            clazz.putProperties(database);
            clazz.putAttributeProperties(database);
            for (TangoDevice device : clazz) {
                device.putProperties(database);
                device.putAttributeProperties(database);
            }
        }
    }
	//===============================================================
    /**
     * Remove the server in default database
     * @throws DevFailed if write database fails.
     */
	//===============================================================
    public void remove() throws DevFailed {
        remove(ApiUtil.get_db_obj().get_tango_host());
    }
	//===============================================================
    /**
     * Remove the server in database specified by tango host
     *
     * @param tangoHost the specified tango host (Host:Port)
     * @throws DevFailed if write database fails.
     */
	//===============================================================
    public void remove(String tangoHost) throws DevFailed {

        //  Remove device and class in reverse order,
		//	to remove the DServer as last one.
        //  If a failure append during this method,
        //	the server will be able to reloaded.
        Database    database = ApiUtil.get_db_obj(tangoHost);
        for (int i=classes.size()-1 ; i>=0 ; i--) {
            for (TangoDevice device : classes.get(i)) {
                database.delete_device(device.name);
            }
        }

        //  Check if there is other instance(s) of classes
        for (TangoClass clazz : classes) {
            if (!clazz.name.toLowerCase().equals("dserver")) { //   NOT for DServer class
                String[]    deviceNames = database.get_device_name("*", clazz.name);
                if (deviceNames.length==0) {
                    //  There is no device any more --> remove class properties
                    clazz.removeProperties(database);
                    //System.out.println("Class properties removed for " + clazz.name);
                }
                else {
                    //System.out.println(clazz.name + " class is used. do not delete Class properties");
                }
            }
        }
    }
    //===============================================================
    public String toString() {
        return serverName;
    }
	//===============================================================
	//===============================================================







    //===============================================================
    /**
     * A class defining a Tango class
     */
    //===============================================================
    public class TangoClass extends ArrayList<TangoDevice> {
        String  name;
        ArrayList<TangoProperty>   properties = new ArrayList<TangoProperty>();
        ArrayList<TangoAttribute>   attributes = new ArrayList<TangoAttribute>();
        //===========================================================
        private TangoClass(String name) throws DevFailed {
            this.name = name;
            Database    database = ApiUtil.get_db_obj();

            //  Read properties
            String[]    propertyNames = database.get_class_property_list(name, "*");
            if (propertyNames.length>0) {
                DbDatum[]   propertyValues = database.get_class_property(name, propertyNames);
                for (int i=0 ; i<propertyNames.length ; i++) {
                    if (!propertyValues[i].is_empty()) {
                        properties.add(new TangoProperty(propertyNames[i], propertyValues[i].extractStringArray()));
                    }
                }
            }

            //  Get attribute list
            String[] attributeNames  = database.get_class_attribute_list(name, "*");
            for (String attributeName : attributeNames) {
                //  Read attribute properties
                DbAttribute dbAttribute = database.get_class_attribute_property(name, attributeName);
                for (Object object : dbAttribute) {
                    DbDatum datum = (DbDatum) object;
                    if (!datum.is_empty()) {
                        TangoAttribute attribute = new TangoAttribute(attributeName);
                        attribute.add(new TangoProperty(datum.name, datum.extractStringArray()));
                        attributes.add(attribute);
                    }
                }
            }

            //  Build devices
            String[]    deviceNames = new DbServer(serverName).get_device_name(name);
            for (String deviceName : deviceNames)
                add(new TangoDevice(deviceName));
        }
        //===========================================================
        public String getName() {
            return name;
        }
        //===========================================================
        public ArrayList<TangoProperty> getProperties() {
            return properties;
        }
        //===========================================================
        public ArrayList<TangoAttribute> getAttributes() {
            return attributes;
        }
        //===========================================================
        private void putProperties(Database database) throws DevFailed {
            if (properties.size()>0) {
                DbDatum[]   data = new DbDatum[properties.size()];
                int i=0;
                for (TangoProperty property : properties) {
                    data[i++] = new DbDatum(property.name, property.values);
                }
                database.put_class_property(name, data);
            }
        }
        //===========================================================
        private void putAttributeProperties(Database database) throws DevFailed {
            ArrayList<DbAttribute>  dbAttributeList = new ArrayList<DbAttribute>();
            if (attributes.size()>0) {
                //  Build the DbDAttribute list for the class
                for (TangoAttribute attribute : attributes) {
                    if (attribute.size()>0) {
                        DbAttribute    dbAttribute = new DbAttribute(name);
                        for (TangoProperty property : attribute) {
                            dbAttribute.add(new DbDatum(property.name, property.values));
                        }
                        dbAttributeList.add(dbAttribute);
                    }
                }

                //  Copy in array before put in database
                DbAttribute[]   dbAttributes = new DbAttribute[dbAttributeList.size()];
                for (int i=0 ; i<dbAttributeList.size() ; i++) {
                    dbAttributes[i] =dbAttributeList.get(i);
                }
                database.put_class_attribute_property(name, dbAttributes);
            }
        }
        //===========================================================
        private void removeProperties(Database database) throws DevFailed {

            // delete class properties if any
            if (properties.size()>0) {
                String[]    propertyNames = new String[properties.size()];
                for (int i=0 ; i<properties.size() ; i++) {
                    propertyNames[i] = properties.get(i).name;
                }
                database.delete_class_property(name, propertyNames);
            }

            // delete class attribute properties if any
            if (attributes.size()>0) {
                for (TangoAttribute attribute : attributes) {
                    if (attribute.size()>0) {
                        String[]    propertyNames = new String[attribute.size()];
                        for (int i=0 ; i<attribute.size() ; i++) {
                            propertyNames[i] = attribute.get(i).name;
                        }
                        database.delete_class_attribute_property(name, attribute.name, propertyNames);
                    }
                }
            }
        }
        //===========================================================
        public String toString() {
            return name;
        }
        //===========================================================
    }
    //===============================================================
    //===============================================================




    //===============================================================
    /**
     * A class defining a Tango device
     */
    //===============================================================
    public class TangoDevice extends DeviceProxy {
        String name;
        ArrayList<TangoProperty>   properties = new ArrayList<TangoProperty>();
        ArrayList<TangoAttribute>  attributes = new ArrayList<TangoAttribute>();
        //===========================================================
        TangoDevice(String name) throws DevFailed{
            super(name);
            this.name = name;

            //  Read properties
            String[]    propertyNames = this.get_property_list("*");
            if (propertyNames.length>0) {
                DbDatum[]   propertyValues = get_property(propertyNames);
                for (int i=0 ; i<propertyNames.length ; i++) {
                    if (!propertyValues[i].is_empty()) {
                        properties.add(new TangoProperty(propertyNames[i], propertyValues[i].extractStringArray()));
                    }
                }
            }
            //  get attribute list from Db
            Database    database = ApiUtil.get_db_obj();
            String[] attributeNames  = database.get_device_attribute_list(name);
            for (String attributeName : attributeNames) {
                //  Read attribute properties
                DbAttribute dbAttribute = database.get_device_attribute_property(name, attributeName);
                for (Object object : dbAttribute) {
                    DbDatum datum = (DbDatum) object;
                    if (!datum.is_empty()) {
                        TangoAttribute attribute = new TangoAttribute(attributeName);
                        attribute.add(new TangoProperty(datum.name, datum.extractStringArray()));
                        attributes.add(attribute);
                    }
                }
            }
        }
        //===========================================================
        public String getName() {
            return name;
        }
        //===========================================================
        public ArrayList<TangoProperty> getProperties() {
            return properties;
        }
        //===========================================================
        public ArrayList<TangoAttribute> getAttributes() {
            return attributes;
        }
        //===========================================================
        private void putProperties(Database database) throws DevFailed {
            if (properties.size()>0) {
                DbDatum[]   data = new DbDatum[properties.size()];
                int i=0;
                for (TangoProperty property : properties) {
                    data[i++] = new DbDatum(property.name, property.values);
                }
                database.put_device_property(name, data);
            }
        }
        //===========================================================
        private void putAttributeProperties(Database database) throws DevFailed {
            ArrayList<DbAttribute>  dbAttributeList = new ArrayList<DbAttribute>();
            if (attributes.size()>0) {
                //  Build the DbDAttribute list for the class
                for (TangoAttribute attribute : attributes) {
                    if (attribute.size()>0) {
                        DbAttribute    dbAttribute = new DbAttribute(name);
                        for (TangoProperty property : attribute) {
                            dbAttribute.add(new DbDatum(property.name, property.values));
                        }
                        dbAttributeList.add(dbAttribute);
                    }
                }

                //  Copy in array before put in database
                DbAttribute[]   dbAttributes = new DbAttribute[dbAttributeList.size()];
                for (int i=0 ; i<dbAttributeList.size() ; i++) {
                    dbAttributes[i] =dbAttributeList.get(i);
                }
                database.put_device_attribute_property(name, dbAttributes);
            }
        }
        //===========================================================
        public String toString() {
            return name;
        }
        //===========================================================
    }
    //===============================================================
    //===============================================================




    //===============================================================
    /**
     * A class defining a Tango attribute
     */
    //===============================================================
    public class TangoAttribute extends ArrayList<TangoProperty> {
        String   name;
        //===========================================================
        TangoAttribute(String name) {
            this.name = name;
        }
        //===========================================================
        public String toString() {
            return name;
        }
        //===========================================================
    }
    //===============================================================
    //===============================================================



    //===============================================================
    /**
     * A class defining a Tango property
     */
    //===============================================================
    public class TangoProperty {
        String   name;
        String[] values;
        //===========================================================
        TangoProperty(String name, String[] values) {
            this.name   = name;
            this.values = values;
        }
        //===========================================================
        public String toString() {
            String  str = name;
            if (values.length>0) {
                str += ": " + values[0];
                if (values.length>1)
                    str += "...";
            }
            return str;
        }
        //===========================================================
    }
    //===============================================================
    //===============================================================
}
