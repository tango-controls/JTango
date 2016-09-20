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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoApi;

import java.util.ArrayList;

/**
 * Class Description:
 * This class manage a list of DbDatum for pipe properties read/write
 *
 * @author verdier
 * @version $Revision: 25296 $
 */


public class DbPipe extends ArrayList<DbDatum> implements java.io.Serializable {

    private String name;

    //===========================================================
    /**
     * Default constructor for the DbPipe Object.
     *
     * @param name Attribute name.
     */
    //===========================================================
    public DbPipe(String name) {
        super();
        this.name = name;
    }
    //===========================================================
    /**
     * return pipe name.
     *
     * @return the pipe name.
     */
    //===========================================================
    public String getName() {
        return name;
    }

    //===========================================================
    /**
     * get the DbDatum object by DbDatum.name.
     *
     * @param name index of the DbDatum expected.
     */
    //===========================================================
    public DbDatum getDatum(String name) {
        for (DbDatum datum : this) {
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
        add(new DbDatum(name, ""));
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
        add(new DbDatum(name, value));
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
        add(new DbDatum(name, value));
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
        add(new DbDatum(name, value));
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
        add(new DbDatum(name, value));
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
        add(new DbDatum(name, values));
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
        add(new DbDatum(name, values));
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
        add(new DbDatum(name, values));
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
        add(new DbDatum(name, values));
    }
    //===========================================================
    /**
     * Return the property name
     *
     * @param index property index
     * @return property name
     */
    //===========================================================
    public String getPropertyName(int index) {
        return get(index).name;
    }

    //===========================================================
    /**
     * Return the property value
     *
     * @param idx index of property
     * @return property values in an array of Strings
     */
    //===========================================================
    public String[] getValue(int idx) {
        return get(idx).extractStringArray();
    }
    //===========================================================
    /**
     * Return the property value as a String object
     *
     * @param index index of property
     * @return property value in a String object.
     */
    //===========================================================
    public String getStringValue(int index) {
        String[] array = get(index).extractStringArray();
        String str = "";
        for (int i=0 ; i<array.length ; i++) {
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
    public String[] getValue(String name) {
        return getDatum(name).extractStringArray();
    }
    //===========================================================
    /**
     * Return the property value in aString object
     *
     * @param name property name
     * @return property value in aString object if exists, null otherwise
     */
    //===========================================================
    public String getStringValue(String name) {
        //  Check if datum exists for name
        DbDatum datum = getDatum(name);
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
        DbDatum datum = getDatum(name);
        return datum==null || datum.is_empty();
    }
    //===========================================================
    /**
     * Return a list of properties found;
     *
     * @return a list of properties found;
     */
    //===========================================================
    public String[] getPropertyList() {
        String[] array = new String[size()];
        for (int i=0 ; i<size(); i++)
            array[i] = get(i).name;
        return array;
    }
}
