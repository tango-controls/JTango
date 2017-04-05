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
// $Revision: 26454 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class Description:
 * This class manage database connection for Tango Class.
 *
 * @author verdier
 * @version $Revision: 26454 $
 */


public class DbClass implements java.io.Serializable {
    /**
     * Database object used for TANGO database access.
     */
    private Database database;

    /**
     * Device name used to access database if device not exported.
     */
    private String className;

    //===================================================================
    /**
     * DbClass constructor.
     * It will make a connection to the TANGO database.
     *
     * @param    className        Name of the class oject.
     */
    //===================================================================
    public DbClass(String className) throws DevFailed {
        //	Access the database and get device server info
        database = ApiUtil.get_db_obj();
        this.className = className;
    }

    //===================================================================
    /**
     * DbClass constructor.
     * It will make a connection to the TANGO database.
     *
     * @param    className        Name of the class oject.
     * @param    host    host where database is running.
     * @param    port    port for database connection.
     */
    //===================================================================
    public DbClass(String className, String host, String port) throws DevFailed {
        //	Access the database and get device server info
        database = ApiUtil.get_db_obj(host, port);
        this.className = className;
    }


    //==========================================================================
    /**
     * Query the database for a list of class
     * properties for the specified object.
     *
     * @param wildcard propertie's wildcard (* matches any charactere).
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public String[] get_property_list(String wildcard)
            throws DevFailed {
        return database.get_class_property_list(className, wildcard);
    }
    //==========================================================================
    /**
     * Query the database for a list of properties for this class.
     *
     * @param propertyNames list of property names.
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public DbDatum[] get_property(String[] propertyNames) throws DevFailed {
        return database.get_class_property(className, propertyNames);
    }
    //==========================================================================
    /**
     * Query the database for a property for this class.
     *
     * @param propertyName property name.
     * @return properties in DbDatum object.
     */
    //==========================================================================
    public DbDatum get_property(String propertyName) throws DevFailed {
        return database.get_class_property(className, propertyName);
    }
    //==========================================================================
    /**
     * Query the database for a list of properties for this class.
     * The property names are specified by the DbDatum array objects.
     *
     * @param properties list of property DbDatum objects.
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public DbDatum[] get_property(DbDatum[] properties) throws DevFailed {
        return database.get_class_property(className, properties);
    }

    //==========================================================================
    /**
     * Insert or update a list of properties for this class
     * The property names and their values are specified by the DbDatum array.
     *
     * @param properties Properties names and values array.
     */
    //==========================================================================
    public void put_property(DbDatum[] properties) throws DevFailed {
        database.put_class_property(className, properties);
    }
    //==========================================================================
    /**
     * Delete a list of properties for this class.
     *
     * @param propertyNames Property names.
     */
    //==========================================================================
    public void delete_property(String[] propertyNames) throws DevFailed {
        database.delete_class_property(className, propertyNames);
    }
    //==========================================================================
    /**
     * Delete a property for this class.
     *
     * @param propertyName Property name.
     */
    //==========================================================================
    public void delete_property(String propertyName) throws DevFailed {
        database.delete_class_property(className, propertyName);
    }
    //==========================================================================
    /**
     * Delete a list of properties for this class.
     *
     * @param properties Property DbDatum objects.
     */
    //==========================================================================
    public void delete_property(DbDatum[] properties) throws DevFailed {
        database.delete_class_property(className, properties);
    }


    //================================================================
    //	ATTRIBUTES
    //================================================================
    //==========================================================================
    /**
     * Query the database for a attributes defined for a class.
     * All attributes for a class attribute are returned.
     *
     * @param wildcard Wildcard char is '*' and matches wildvcard characters.
     * @return attributes list for specified class
     */
    //==========================================================================
    public String[] get_attribute_list(String wildcard) throws DevFailed {
        return database.get_class_attribute_list(className, wildcard);
    }
    //==========================================================================
    /**
     * Insert or update a list of attribute properties for this class.
     * The property names and their values are specified by the DbAttribute array.
     *
     * @param attr attribute names and properties names and values.
     */
    //==========================================================================
    public void put_attribute_property(DbAttribute[] attr) throws DevFailed {
        database.put_class_attribute_property(className, attr);
    }
    //==========================================================================
    /**
     * Insert or update a list of attribute properties for this class.
     * The property names and their values are specified by the DbAttribue.
     *
     * @param attr attribute name and properties names and values.
     */
    //==========================================================================
    public void put_attribute_property(DbAttribute attr) throws DevFailed {
        database.put_class_attribute_property(className, attr);
    }
    //==========================================================================
    /**
     * Delete a list of properties for this object.
     *
     * @param propertyNames Property names.
     */
    //==========================================================================
    public void delete_attribute_property(String attributeName, String[] propertyNames) throws DevFailed {
        database.delete_class_attribute_property(className, attributeName, propertyNames);
    }
    //==========================================================================
    /**
     * Delete a property for this object.
     *
     * @param propertyName Property name.
     */
    //==========================================================================
    public void delete_attribute_property(String attributeName, String propertyName) throws DevFailed {
        database.delete_class_attribute_property(className, attributeName, propertyName);
    }
    //==========================================================================
    /**
     * Query the database for a list of class attribute
     * properties for this device.
     *
     * @param attributeNames attribute names.
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public DbAttribute[] get_attribute_property(String[] attributeNames)  throws DevFailed {
        return database.get_class_attribute_property(className, attributeNames);
    }
    //==========================================================================
    /**
     * Query the database for of class attribute
     * property for this device.
     *
     * @param attributeName attribute name.
     * @return properties in DbDatum objects.
     */
    //==========================================================================
    public DbAttribute get_attribute_property(String attributeName)  throws DevFailed {
        return database.get_class_attribute_property(className, attributeName);
    }

    //===========================================================
    /**
     * return the class name.
     */
    //===========================================================
    public String name() {
        return className;
    }





    // ===================================================================
    /*
     * Pipe related methods
     */
    // ===================================================================

    // ===================================================================
    /**
     * Query the database for a list of class pipe properties
     * for the specified pipe.
     * @param pipeName specified pipe.
     * @return a list of class pipe properties.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbPipe getPipeProperties(String pipeName) throws DevFailed {
        return database.getClassPipeProperties(className, pipeName);
    }
    // ===================================================================
    /**
     * Query the database for a class pipe property
     * for the specified pipe.
     * @param pipeName specified pipe.
     * @param propertyName specified property.
     * @return class pipe property.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public DbDatum getPipeProperty(String pipeName, String propertyName) throws DevFailed {
        DbPipe dbPipe = database.getClassPipeProperties(className, pipeName);
        DbDatum datum = dbPipe.getDatum(propertyName);
        if (datum==null)
            Except.throw_exception("TangoApi_PropertyNotFound",
                    "Property " + propertyName + " not found for pipe " + pipeName);
        return datum;
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified class.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param dbPipe pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putPipeProperty(DbPipe dbPipe) throws DevFailed {
        database.putClassPipeProperty(className, dbPipe);
    }
    // ==========================================================================
    /**
     * Insert or update a list of pipe properties for the specified class.
     * The property names and their values are specified by the DbAPipe.
     *
     * @param dbPipes list of pipe name, and properties (names and values).
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void putPipeProperty(ArrayList<DbPipe> dbPipes) throws DevFailed {
        for (DbPipe dbPipe : dbPipes)
            database.putClassPipeProperty(className, dbPipe);
    }
    // ===================================================================
    /**
     * Query database for a list of pipes for specified class.
     * @return a list of pipes defined in database for specified class.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<String> getPipeList() throws DevFailed {
        return getPipeList("*");
    }
    // ===================================================================
    /**
     * Query database for a list of pipes for specified class and specified wildcard.
     * @param wildcard specified wildcard.
     * @return a list of pipes defined in database for specified class and specified wildcard.
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public List<String> getPipeList(String wildcard) throws DevFailed {
        return database.getClassPipeList(className, wildcard);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     * @param pipeName pipe name
     * @param propertyName property name
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deletePipeProperty(String pipeName, String propertyName) throws DevFailed {
        ArrayList<String> list = new ArrayList<String>(1);
        list.add(propertyName);
        deletePipeProperties(pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deletePipeProperties(String pipeName, String[] propertyNames) throws DevFailed {
        ArrayList<String>   list = new ArrayList<String>(propertyNames.length);
        Collections.addAll(list, propertyNames);
        deletePipeProperties(pipeName, list);
    }
    // ==========================================================================
    /**
     * Delete a pipe property for the specified class.
     *
     * @param pipeName pipe name
     * @param propertyNames property names
     * @throws DevFailed in case of database access failed
     */
    // ==========================================================================
    public void deletePipeProperties(String pipeName, List<String> propertyNames) throws DevFailed {
        database.deleteClassPipeProperties(className, pipeName, propertyNames);
    }
    // ===================================================================
    /**
     * Delete specified pipe for specified class.
     * @param pipeName      pipe name
     * @throws DevFailed in case of database access failed
     */
    // ===================================================================
    public void deletePipe(String pipeName) throws DevFailed {
        database.deleteClassPipe(className, pipeName);
    }
    /**
     * Returns the property history for specified pipe.
     * @param pipeName      pipe name
     * @param propertyName  property Name
     * @return the property history for specified pipe.
     * @throws DevFailed  in case of database access failed
     */
    // ===================================================================
    public List<DbHistory> getPipePropertyHistory(String pipeName,
                                                       String propertyName) throws DevFailed {
        return database.getClassPipePropertyHistory(className, pipeName, propertyName);
    }
}
