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

/**
 * Class Description: This class manage data object for Tango device access.
 * 
 * @author verdier
 * @version $Revision: 25296 $
 */

public class DbDatum implements java.io.Serializable {
    public String name;
    private boolean is_empty_val = true;
    private String[] values = new String[] { "" };

    // ===========================================================
    /**
     * Default constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     */
    // ===========================================================
    public DbDatum(final String name) {
	this.name = name;
    }

    // ********** Constructors with value added ***************//

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final boolean argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final short argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final int argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final float argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final double argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final String argin) {
	this.name = name;
	insert(argin);
    }

    // ********** Constructors with value added ***************//

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final byte[] argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final short[] argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final int[] argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final float[] argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final double[] argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param argin
     *            Values.
     */
    // ===========================================================
    public DbDatum(final String name, final String[] argin) {
	this.name = name;
	insert(argin);
    }

    // ===========================================================
    /**
     * Constructor for the dDbDatum Object.
     * 
     * @param name
     *            object name.
     * @param strval
     *            Values as string array.
     * @param start
     *            Index to start in array
     * @param end
     *            Index to stop array.
     */
    // ===========================================================
    public DbDatum(final String name, final String[] strval, final int start, final int end) {
	this.name = name;
	String[] tmp;
	tmp = new String[end - start];
	for (int i = 0; i < end - start; i++) {
	    tmp[i] = strval[start + i];
	}
	insert(tmp);
    }

    // =======================================
    /**
     * Give the number of values 1 or more if array.
     */
    // =======================================
    public int size() {
	return values.length;
    }

    // ********** Insert Methods for basic types *********************

    // ===========================================
    /**
     * Insert method for argin is boolean.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final boolean argin) {
	values = new String[1];
	values[0] = String.valueOf(argin);
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is short.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final short argin) {
	values = new String[1];
	values[0] = String.valueOf(argin);
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is long.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final long argin) {
	values = new String[1];
	values[0] = String.valueOf(argin);
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is int.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final int argin) {
	values = new String[1];
	values[0] = String.valueOf(argin);
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is float.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final float argin) {
	values = new String[1];
	values[0] = String.valueOf(argin);
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is double.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final double argin) {
	values = new String[1];
	values[0] = String.valueOf(argin);
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is String.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final String argin) {
	values = new String[1];
	values[0] = argin;
	is_empty_val = false;
    }

    // ********** Insert Methods for sequence types *********************

    // ===========================================
    /**
     * Insert method for argin is DevVarCharArray.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final byte[] argin) {
	values = new String[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = String.valueOf(argin[i]);
	}
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is DevVarShortArray.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final short[] argin) {
	values = new String[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = String.valueOf(argin[i]);
	}
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is DevVarLongArray.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final int[] argin) {
	values = new String[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = String.valueOf(argin[i]);
	}
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is DevVarFloatArray.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final float[] argin) {
	values = new String[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = String.valueOf(argin[i]);
	}
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is DevVarDoubleArray.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final double[] argin) {
	values = new String[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = String.valueOf(argin[i]);
	}
	is_empty_val = false;
    }

    // ===========================================
    /**
     * Insert method for argin is DevVarStringArray.
     * 
     * @param argin
     *            argin value for next command.
     */
    // ===========================================
    public void insert(final String[] argin) {
	values = new String[argin.length];
	System.arraycopy(argin, 0, values, 0, argin.length);
	is_empty_val = false;
    }

    // ********** Extract Methods for basic types *********************

    // ===========================================
    /**
     * extract method for a boolean.
     */
    // ===========================================
    public boolean extractBoolean() {
	return !(values[0].toLowerCase().equals("false") || values[0].equals("0"));
    }

    // ===========================================
    /**
     * extract method for a short.
     */
    // ===========================================
    public short extractShort() {
	return Short.parseShort(values[0]);
    }

    // ===========================================
    /**
     * extract method for an int.
     */
    // ===========================================
    public int extractLong() {
	return Integer.parseInt(values[0]);
    }

    // ===========================================
    /**
     * extract method for a long.
     */
    // ===========================================
    /***
     * public long extractLong() { return Long.parseLong(values[0]); }
     ***/
    // ===========================================
    /**
     * extract method for a float.
     */
    // ===========================================
    public float extractFloat() {
	return new Float(values[0]);
	// Exist only in JDK 1.2
	// return Float.parseFloat(values[0]);
    }

    // ===========================================
    /**
     * extract method for a double.
     */
    // ===========================================
    public double extractDouble() {
	return new Double(values[0]);
	// Exist only in JDK 1.2
	// return Double.parseDouble(values[0]);
    }

    // ===========================================
    /**
     * extract method for a String.
     */
    // ===========================================
    public String extractString() {
	return values[0];
    }

    // ********** Extract Methods for sequence types *********************

    // ===========================================
    /**
     * extract method for a byte Array.
     */
    // ===========================================
    public byte[] extractByteArray() {
	return values[0].getBytes();
    }

    // ===========================================
    /**
     * extract method for a short Array.
     */
    // ===========================================
    public short[] extractShortArray() {
	short[] argout;
	argout = new short[values.length];
	for (int i = 0; i < values.length; i++) {
	    argout[i] = Short.parseShort(values[i]);
	}
	return argout;
    }

    // ===========================================
    /**
     * extract method for a long Array.
     */
    // ===========================================
    public int[] extractLongArray() {
	int[] argout;
	argout = new int[values.length];
	for (int i = 0; i < values.length; i++) {
	    argout[i] = Integer.parseInt(values[i]);
	}
	return argout;
    }

    // ===========================================
    /**
     * extract method for a float Array.
     */
    // ===========================================
    public float[] extractFloatArray() {
	float[] argout;
	argout = new float[values.length];
	for (int i = 0; i < values.length; i++) {
	    argout[i] = new Float(values[i]);
	}
	return argout;
    }

    // ===========================================
    /**
     * extract method for a double Array.
     */
    // ===========================================
    public double[] extractDoubleArray() {
	double[] argout;
	argout = new double[values.length];
	for (int i = 0; i < values.length; i++) {
	    argout[i] = new Double(values[i]);
	}
	return argout;
    }

    // ===========================================
    /**
     * Return the true if the value is empty.
     */
    // ===========================================
    public boolean is_empty() {
	return is_empty_val;
    }

    // ===========================================
    /**
     * extract method for a String Array.
     */
    // ===========================================
    public String[] extractStringArray() {
	return values;
    }

    // ===========================================
    /**
     * Format values as String array.
     */
    // ===========================================
    public String[] toStringArray() {
	String[] result;
	result = new String[size() + 2];

	result[0] = name;
	result[1] = String.valueOf(size());
	System.arraycopy(values, 0, result, 2, size());

	return result;
    }
}
