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

import java.util.Vector;

/**
 * Class Description:
 * This class manage a vector of DbDatum for attribute properties read/write
 * and the attribute name associated.
 *
 * @author verdier
 * @version $Revision: 26454 $
 */


public class DbAttribute extends Vector<DbDatum> implements java.io.Serializable {
    public String name;

    //===========================================================
    /**
     * Default constructor for the DbAttribute Object.
     *
     * @param name Attribute name.
     */
    //===========================================================
    public DbAttribute(String name) {
        super();
        this.name = name;
    }


    //===========================================================
    /**
     * get the DbDatum object by index.
     *
     * @param idx index of the DbDatum expected.
     */
    //===========================================================
    public DbDatum datum(int idx) {
        return elementAt(idx);
    }

    //===========================================================
    /**
     * get the DbDatum object by DbDatum.name.
     *
     * @param name index of the DbDatum expected.
     */
    //===========================================================
    public DbDatum datum(String name) {
        DbDatum datum;
        for (int i = 0; i < size(); i++) {
            datum = elementAt(i);
            if (name.equalsIgnoreCase(datum.name))
                return datum;
        }
        return null;
    }

    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name property name
     */
    //===========================================================
    public void add(String name) {
        addElement(new DbDatum(name, ""));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name  property name
     * @param value property value
     */
    //===========================================================
    public void add(String name, String value) {
        addElement(new DbDatum(name, value));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name  property name
     * @param value property value
     */
    //===========================================================
    public void add(String name, short value) {
        addElement(new DbDatum(name, value));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name  property name
     * @param value property value
     */
    //===========================================================
    public void add(String name, int value) {
        addElement(new DbDatum(name, value));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name  property name
     * @param value property value
     */
    //===========================================================
    public void add(String name, double value) {
        addElement(new DbDatum(name, value));
    }


    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name   property name
     * @param values property value
     */
    //===========================================================
    public void add(String name, String[] values) {
        addElement(new DbDatum(name, values));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name   property name
     * @param values property value
     */
    //===========================================================
    public void add(String name, short[] values) {
        addElement(new DbDatum(name, values));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name   property name
     * @param values property value
     */
    //===========================================================
    public void add(String name, int[] values) {
        addElement(new DbDatum(name, values));
    }
    //===========================================================
    /**
     * Add a new DbDatum in Vector
     *
     * @param name   property name
     * @param values property value
     */
    //===========================================================
    public void add(String name, double[] values) {
        addElement(new DbDatum(name, values));
    }
    //===========================================================
    /**
     * Return the property name
     *
     * @param idx index of property
     * @return property name
     */
    //===========================================================
    public String get_property_name(int idx) {
        return datum(idx).name;
    }

    //===========================================================
    /**
     * Return the property value
     *
     * @param idx index of property
     * @return property values in an array of Strings
     */
    //===========================================================
    public String[] get_value(int idx) {
        return datum(idx).extractStringArray();
    }
    //===========================================================
    /**
     * Return the property value as a String object
     *
     * @param idx index of property
     * @return property value in a String object.
     */
    //===========================================================
    public String get_string_value(int idx) {
        String[] array = datum(idx).extractStringArray();
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str += array[i];
            if (i < array.length - 1)
                str += "\n";
        }
        return str;
    }
    //===========================================================
    /**
     * Return the property value
     *
     * @param name property name
     * @return property value in an array of Strings
     */
    //===========================================================
    public String[] get_value(String name) {
        return datum(name).extractStringArray();
    }
    //===========================================================
    /**
     * Return the property value in aString object
     *
     * @param name property name
     * @return property value in aString object if exists, null otherwise
     */
    //===========================================================
    public String get_string_value(String name) {
        //  Check if datum exists for name
        DbDatum datum = datum(name);
        if (datum == null)
            return null;

        //  Else get string value
        String[] array = datum.extractStringArray();
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str += array[i];
            if (i < array.length - 1)
                str += "\n";
        }
        return str;
    }
    //===========================================================
    /**
     * Return true if property not found;
     *
     * @param name property name
     * @return true if property not found;
     */
    //===========================================================
    public boolean is_empty(String name) {
        DbDatum datum = datum(name);
        if (datum == null)
            return true;
        else
            return datum.is_empty();
    }
    //===========================================================
    /**
     * Return a list of properties found;
     *
     * @return a list of properties found;
     */
    //===========================================================
    public String[] get_property_list() {
        String[] array = new String[size()];
        for (int i = 0; i < size(); i++)
            array[i] = datum(i).name;
        return array;
    }
}
