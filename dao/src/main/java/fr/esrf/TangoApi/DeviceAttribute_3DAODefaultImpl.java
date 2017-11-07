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
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoApi;

import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevStateHelper;
import fr.esrf.Tango.DevVarBooleanArrayHelper;
import fr.esrf.Tango.DevVarCharArrayHelper;
import fr.esrf.Tango.DevVarDoubleArrayHelper;
import fr.esrf.Tango.DevVarFloatArrayHelper;
import fr.esrf.Tango.DevVarLong64ArrayHelper;
import fr.esrf.Tango.DevVarLongArrayHelper;
import fr.esrf.Tango.DevVarShortArrayHelper;
import fr.esrf.Tango.DevVarStateArrayHelper;
import fr.esrf.Tango.DevVarStringArrayHelper;
import fr.esrf.Tango.DevVarULong64ArrayHelper;
import fr.esrf.Tango.DevVarULongArrayHelper;
import fr.esrf.Tango.DevVarUShortArrayHelper;
import fr.esrf.Tango.TimeVal;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

/**
 * Class Description: This class manage data object for Tango device attribute
 * access. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> DeviceAttribute devattr = dev.read_attribute("Current"); <Br>
 * if (devattr.hasFailed())<Br>
 * {
 * <ul>
 * Except.print_exception(devattr.getErrStack());
 * </ul>
 * else <Br>
 * {
 * <ul>
 * double current = devattr.extractDouble(); <Br>
 * System.out.println("Current : " + current);
 * </ul>
 * </ul></i>
 * 
 * @author verdier
 * @version $Revision: 25297 $
 */

public class DeviceAttribute_3DAODefaultImpl implements IDeviceAttribute_3DAO {
    AttributeValue_3 attrval = new AttributeValue_3();

    // ===========================================
    /**
     * Build a DeviceAttribute IDL object
     * 
     * @param name
     *            Attribute name.
     */
    // ===========================================
    private void buildAttributeValueObject(final String name) {
	attrval.name = name;
	attrval.quality = AttrQuality.ATTR_VALID;
	attrval.time = new TimeVal();
	attrval.r_dim = new AttributeDim();
	attrval.w_dim = new AttributeDim();
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	attrval.w_dim.dim_x = 0;
	attrval.w_dim.dim_y = 0;
	try {
	    attrval.value = ApiUtil.get_orb().create_any();
	} catch (final DevFailed e) {
	}

	final long now = System.currentTimeMillis();
	attrval.time.tv_sec = (int) (now / 1000);
	attrval.time.tv_usec = (int) (now - attrval.time.tv_sec * 1000) * 1000;
	attrval.time.tv_nsec = 0;
	attrval.err_list = null;
    }

    public DeviceAttribute_3DAODefaultImpl() {
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attrval
     *            AttributeValue_3 IDL object.
     */
    // ===========================================
    public void init(final AttributeValue_3 attrval) {
	this.attrval = attrval;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attrval
     *            AttributeValue_4 IDL object.
     */
    // ===========================================
    public void init(final AttributeValue attrval) {
	this.attrval.value = attrval.value;
	this.attrval.name = attrval.name;
	this.attrval.quality = attrval.quality;
	this.attrval.time = attrval.time;
	this.attrval.r_dim = new AttributeDim();
	this.attrval.w_dim = new AttributeDim();
	this.attrval.r_dim.dim_x = attrval.dim_x;
	this.attrval.r_dim.dim_y = attrval.dim_y;
	this.attrval.w_dim.dim_x = 0;
	this.attrval.w_dim.dim_y = 0;
	this.attrval.err_list = null;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     */
    // ===========================================
    public void init(final String name) {
	buildAttributeValueObject(name);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final int dim_x, final int dim_y) {
	buildAttributeValueObject(name);
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final boolean value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final DevState value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final boolean[] value, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(value, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final byte value) {
	buildAttributeValueObject(name);
	insert_uc(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final byte[] value, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert_uc(value, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final short value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param values
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final short[] values, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final int value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param values
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final int[] values, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final long value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param values
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final long[] values, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final float value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param values
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final float[] values, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final double value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param values
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final double[] values, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    // ===========================================
    public void init(final String name, final String value) {
	buildAttributeValueObject(name);
	insert(value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name
     *            Attribute name.
     * @param values
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void init(final String name, final String[] values, final int dim_x,
	    final int dim_y) {
	buildAttributeValueObject(name);
	insert(values, dim_x, dim_y);
    }

    // ===========================================
    // ===========================================
    public boolean hasFailed() {
	return attrval.err_list != null && attrval.err_list.length > 0;
    }

    // ===========================================
    /**
     * Returns the attribute errors list
     */
    // ===========================================
    public DevError[] getErrStack() {
	return attrval.err_list;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attrval
     *            AttributeValue_3 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_3 attrval) {
	this.attrval = attrval;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attrval
     *            AttributeValue input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue attrval) {
	this.attrval.value = attrval.value;
	this.attrval.name = attrval.name;
	this.attrval.quality = attrval.quality;
	this.attrval.time = attrval.time;
	this.attrval.r_dim.dim_x = attrval.dim_x;
	this.attrval.r_dim.dim_y = attrval.dim_y;
	this.attrval.w_dim.dim_x = 0;
	this.attrval.w_dim.dim_y = 0;
	this.attrval.err_list = null;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param devatt
     *            AttributeValue IDL object.
     */
    // ===========================================
    // GA: cf. the inteface implemented by this class
    public void setAttributeValue(final IDeviceAttributeDAO devatt)
	    throws DevFailed {

	attrval.name = devatt.getName();
	attrval.quality = devatt.getQuality();
	attrval.time = devatt.getTimeVal();
	attrval.r_dim = devatt.getReadAttributeDim();
	attrval.w_dim = devatt.getWriteAttributeDim();
	attrval.r_dim.dim_x = devatt.getReadAttributeDim().dim_x;
	attrval.r_dim.dim_y = devatt.getReadAttributeDim().dim_y;

	if (devatt.getWriteAttributeDim() != null) {
	    attrval.w_dim.dim_x = devatt.getWriteAttributeDim().dim_x;
	    attrval.w_dim.dim_y = devatt.getWriteAttributeDim().dim_y;
	} else {
	    attrval.w_dim = devatt.getReadAttributeDim();// write as read
	}

	attrval.err_list = null;
	// Get the value from Union object
	attrval.value = ApiUtil.get_orb().create_any();
	// GA
	// devatt.setValueAsAny(this);
	((DeviceAttributeDAODefaultImpl) devatt).setValueAsAny(this);
    }

    // ===========================================
    // Insert methods
    // ===========================================

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final DevState argin) {
	final DevState[] values = new DevState[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarStateArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final DevState[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarStateArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final DevState[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarStateArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final boolean argin) {
	final boolean[] values = new boolean[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarBooleanArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final boolean[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarBooleanArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final boolean[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarBooleanArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte argin) {
	final byte[] values = new byte[1];
	attrval.r_dim.dim_x = 1;
	values[0] = argin;
	attrval.r_dim.dim_y = 0;
	DevVarCharArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarCharArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_uc(final short argin) {
	final byte[] values = new byte[1];
	values[0] = (byte) (argin & 0xFF);
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarCharArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_uc(final short[] argin) {
	final byte[] values = new byte[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (byte) (argin[i] & 0xFF);
	}
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarCharArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            nb data.in x direction
     * @param dim_y
     *            nb data.in y direction
     */
    // ===========================================
    public void insert_uc(final short[] argin, final int dim_x, final int dim_y) {
	final byte[] values = new byte[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (byte) (argin[i] & 0xFF);
	}
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarCharArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarCharArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final short argin) {
	final short[] values = new short[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarShortArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final short[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarShortArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final short[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarShortArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_us(final short argin) {
	final short[] values = new short[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarUShortArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_us(final int argin) {
	final short[] values = new short[1];
	values[0] = (short) (argin & 0xFFFF);
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarUShortArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarUShortArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argin) {
	final short[] values = new short[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (short) (argin[i] & 0xFFFF);
	}
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarUShortArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarUShortArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argin, final int dim_x, final int dim_y) {
	final short[] values = new short[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (short) (argin[i] & 0xFFFF);
	}
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarUShortArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final int argin) {
	final int[] values = new int[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarLongArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final int[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarLongArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final int[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarLongArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final long argin) {
	final long[] values = new long[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarLong64ArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final long[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarLong64ArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final long[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarLong64ArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_ul(final int argin) {
	final int[] values = new int[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarULongArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_ul(final long argin) {
	final int[] values = new int[1];
	values[0] = (int) argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarULongArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarULongArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argin) {
	final int[] values = new int[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (int) argin[i];
	}
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarULongArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarULongArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argin, final int dim_x, final int dim_y) {
	final int[] values = new int[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (int) argin[i];
	}
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarULongArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_u64(final long argin) {
	final long[] values = new long[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarULong64ArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert_u64(final long[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarULong64ArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert_u64(final long[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarULong64ArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final float argin) {
	final float[] values = new float[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarFloatArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final float[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarFloatArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final float[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarFloatArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final double argin) {
	final double[] values = new double[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarDoubleArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final double[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarDoubleArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final double[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarDoubleArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final String argin) {
	final String[] values = new String[1];
	values[0] = argin;
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	DevVarStringArrayHelper.insert(attrval.value, values);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final String[] argin) {
	attrval.r_dim.dim_x = argin.length;
	attrval.r_dim.dim_y = 0;
	DevVarStringArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     * @param dim_x
     *            array dimention in X
     * @param dim_y
     *            array dimention in Y
     */
    // ===========================================
    public void insert(final String[] argin, final int dim_x, final int dim_y) {
	attrval.r_dim.dim_x = dim_x;
	attrval.r_dim.dim_y = dim_y;
	DevVarStringArrayHelper.insert(attrval.value, argin);
    }

    // ===========================================
    /**
     * Throws exception if err_list not null.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    private void manageExceptions(final String method_name) throws DevFailed {
	if (attrval.err_list != null) {
	    if (attrval.err_list.length > 0) {
		throw new WrongData(attrval.err_list);
	    }
	}
	if (attrval.quality == AttrQuality.ATTR_INVALID) {
	    Except.throw_wrong_data_exception("AttrQuality_ATTR_INVALID",
		    "Attribute quality factor is INVALID", "DeviceAttribute."
			    + method_name + "()");
	}
    }

    // ===========================================
    /**
     * extract method for an DevState Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public DevState[] extractDevStateArray() throws DevFailed {
	manageExceptions("extractDevStateArray()");
	try {
	    if (isArray()) {
		return DevVarStateArrayHelper.extract(attrval.value);
	    } else {
		// It is used for state attribute
		return new DevState[] { DevStateHelper.extract(attrval.value) };
	    }
	} catch (final org.omg.CORBA.BAD_PARAM e) {
	    Except
		    .throw_wrong_data_exception(
			    e.toString(),
			    "Exception catched : "
				    + e.toString()
				    + "\n"
				    + "Maybe the attribute value has not been initialized",
			    "DeviceAttribute.extractDevStateArray()");
	}
	return new DevState[0];// never used
    }

    // ===========================================
    /**
     * extract method for an DevState.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public DevState extractDevState() throws DevFailed {
	manageExceptions("extractDevState");
	if (isArray()) {
	    // It is used for an attribute of DevState enum.
	    final DevState[] array = extractDevStateArray();
	    return array[0];
	} else {
	    // It is used for state attribute
	    return DevStateHelper.extract(attrval.value);
	}
    }

    // ===========================================
    /**
     * extract method for an boolean.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public boolean extractBoolean() throws DevFailed {
	manageExceptions("extractBoolean()");
	return DevVarBooleanArrayHelper.extract(attrval.value)[0];
    }

    // ===========================================
    /**
     * extract method for an boolean Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public boolean[] extractBooleanArray() throws DevFailed {
	manageExceptions("extractBooleanArray()");
	return DevVarBooleanArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for an unsigned char.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public short extractUChar() throws DevFailed {
	manageExceptions("extractUChar");
	final short[] array = extractUCharArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for an unsigned char Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public short[] extractUCharArray() throws DevFailed {
	manageExceptions("extractUCharArray()");

	final byte[] argout = DevVarCharArrayHelper.extract(attrval.value);
	final short[] val = new short[argout.length];
	final short mask = 0xFF;
	for (int i = 0; i < argout.length; i++) {
	    val[i] = (short) (mask & argout[i]);
	}
	return val;
    }

    // ===========================================
    /**
     * extract method for an unsigned char Array as a char array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public byte[] extractCharArray() throws DevFailed {
	manageExceptions("extractCharArray()");

	return DevVarCharArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for a short.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public short extractShort() throws DevFailed {
	manageExceptions("xtractShort()");
	final short[] array = extractShortArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for a short Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public short[] extractShortArray() throws DevFailed {
	manageExceptions("extractShortArray");
	return DevVarShortArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for an unsigned short.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public int extractUShort() throws DevFailed {
	manageExceptions("extractUShort");
	final int[] array = extractUShortArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for an unsigned short Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public int[] extractUShortArray() throws DevFailed {
	manageExceptions("extractUShortArray");
	final short[] argout = DevVarUShortArrayHelper.extract(attrval.value);
	final int[] val = new int[argout.length];
	for (int i = 0; i < argout.length; i++) {
	    val[i] = 0xFFFF & argout[i];
	}
	return val;
    }

    // ===========================================
    /**
     * extract method for a long.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public int extractLong() throws DevFailed {
	manageExceptions("extractLong");
	final int[] array = extractLongArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for a long Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public int[] extractLongArray() throws DevFailed {
	manageExceptions("extractLongArray");
	return DevVarLongArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for a unsigned long.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public long extractULong() throws DevFailed {
	manageExceptions("extractULong");
	final int[] array = DevVarULongArrayHelper.extract(attrval.value);
	long mask = 0x7fffffff;
	mask += (long) 1 << 31;
	return mask & array[0];
    }

    // ===========================================
    /**
     * extract method for a unsigned long.array
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public long[] extractULongArray() throws DevFailed {
	manageExceptions("extractULong");
	final int[] array = DevVarULongArrayHelper.extract(attrval.value);
	long mask = 0x7fffffff;
	mask += (long) 1 << 31;
	final long[] result = new long[array.length];
	for (int i = 0; i < array.length; i++) {
	    result[i] = mask & array[i];
	}
	return result;
    }

    // ===========================================
    /**
     * extract method for a long.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public long extractLong64() throws DevFailed {
	manageExceptions("extractLong64");
	return extractLong64Array()[0];
    }

    // ===========================================
    /**
     * extract method for a long Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public long[] extractLong64Array() throws DevFailed {
	manageExceptions("extractLong64Array");
	return DevVarLong64ArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for a long.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public long extractULong64() throws DevFailed {
	manageExceptions("extractULong64");
	return extractULong64Array()[0];
    }

    // ===========================================
    /**
     * extract method for a long Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public long[] extractULong64Array() throws DevFailed {
	manageExceptions("extractULong64Array");
	return DevVarULong64ArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for a float.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public float extractFloat() throws DevFailed {
	manageExceptions("extractFloat");
	final float[] array = extractFloatArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for a float Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public float[] extractFloatArray() throws DevFailed {
	manageExceptions("extractFloatArray");
	return DevVarFloatArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for a double.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public double extractDouble() throws DevFailed {
	manageExceptions("extractDouble");
	final double[] array = extractDoubleArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for a double Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public double[] extractDoubleArray() throws DevFailed {
	manageExceptions("extractDoubleArray");
	return DevVarDoubleArrayHelper.extract(attrval.value);
    }

    // ===========================================
    /**
     * extract method for a DevState (state attribute).
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public DevState extractState() throws DevFailed {
	// It is used for state attribute
	// and kept for backward compatibility
	return extractDevState();
    }

    // ===========================================
    /**
     * extract method for a String.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public String extractString() throws DevFailed {
	manageExceptions("extractString");
	final String[] array = extractStringArray();
	return array[0];
    }

    // ===========================================
    /**
     * extract method for a double Array.
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public String[] extractStringArray() throws DevFailed {
	manageExceptions("extractStringArray");
	return DevVarStringArrayHelper.extract(attrval.value);
    }

    // ===========================================
    // ===========================================
    public boolean isArray() throws DevFailed {
	boolean retval = true;

	try {
	    final TypeCode tc = attrval.value.type();
	    final TypeCode tc_alias = tc.content_type();
	    /* TypeCode tc_seq = */
	    tc_alias.content_type();
	} catch (final org.omg.CORBA.TypeCodePackage.BadKind e) {
	    // System.out.println(e);
	    retval = false;
	}
	return retval;
    }

    // ===========================================
    // ===========================================

    // ===========================================
    /**
     * Return attribute quality
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public AttrQuality getQuality() throws DevFailed {
	manageExceptions("getQuality");
	return attrval.quality;
    }

    // ===========================================
    /**
     * Return attribute time value.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public TimeVal getTimeVal() throws DevFailed {
	manageExceptions("getTimeVal");
	return attrval.time;
    }

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValSec() throws DevFailed {
	manageExceptions("getTimeValSec");
	return attrval.time.tv_sec;
    }

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValMillisSec() throws DevFailed {
	manageExceptions("getTimeValMillisSec");
	return attrval.time.tv_sec * 1000L + attrval.time.tv_usec / 1000L;
    }

    // ===========================================
    /**
     * Return attribute name.
     */
    // ===========================================
    public String getName() {
	return attrval.name;
    }

    // ===========================================
    // ===========================================
    private int DIM_MINI(final int x) {
	return x == 0 ? 1 : x;
    }

    // ===========================================
    /**
     * Return number of data read.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public int getNbRead() throws DevFailed {
	manageExceptions("getNbRead");
	return attrval.r_dim.dim_x * DIM_MINI(attrval.r_dim.dim_y);
    }

    // ===========================================
    /**
     * Return number of data read object.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public AttributeDim getReadAttributeDim() throws DevFailed {
	manageExceptions("getReadAttributeDim");
	return attrval.r_dim;
    }

    // ===========================================
    /**
     * Return number of data Write object.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public AttributeDim getWriteAttributeDim() throws DevFailed {
	manageExceptions("getWriteAttributeDim");
	return attrval.w_dim;
    }

    // ===========================================
    /**
     * Return number of data written.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public int getNbWritten() throws DevFailed {
	manageExceptions("getNbWritten");
	return attrval.w_dim.dim_x * DIM_MINI(attrval.w_dim.dim_y);
    }

    // ===========================================
    /**
     * Return attribute dim_x.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public int getDimX() throws DevFailed {
	manageExceptions("getDimX");
	return attrval.r_dim.dim_x;
    }

    // ===========================================
    /**
     * Return attribute dim_y.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public int getDimY() throws DevFailed {
	manageExceptions("getDimY");
	return attrval.r_dim.dim_y;
    }

    // ===========================================
    /**
     * Return attribute written dim_x.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimX() throws DevFailed {
	manageExceptions("getWrittenDimX");
	return attrval.w_dim.dim_x;
    }

    // ===========================================
    /**
     * Return attribute written dim_y.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimY() throws DevFailed {
	manageExceptions("getWrittenDimY");
	return attrval.w_dim.dim_y;
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue getAttributeValueObject_2() {
	final AttributeValue attrval_2 = new AttributeValue();
	attrval_2.value = attrval.value;
	attrval_2.name = attrval.name;
	attrval_2.quality = attrval.quality;
	attrval_2.time = attrval.time;
	attrval_2.dim_x = attrval.w_dim.dim_x;
	attrval_2.dim_y = attrval.w_dim.dim_y;

	return attrval_2;
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_3 getAttributeValueObject_3() {
	return attrval;
    }

    // ===========================================
    /**
     * return time in milliseconds since 1/1/70
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public long getTime() throws DevFailed {
	manageExceptions("getTime");
	return (long) attrval.time.tv_sec * 1000 + attrval.time.tv_usec / 1000;
    }

    // ===========================================
    // ===========================================
    public int getType() throws DevFailed {
	int type = -1;
	try {
	    final TypeCode tc = attrval.value.type();
	    // Special case for test
	    if (tc.kind().value() == TCKind._tk_enum) {
		return TangoConst.Tango_DEV_STATE;
	    }

	    final TypeCode tc_alias = tc.content_type();
	    final TypeCode tc_seq = tc_alias.content_type();
	    final TCKind kind = tc_seq.kind();
	    switch (kind.value()) {
	    case TCKind._tk_void:
		type = TangoConst.Tango_DEV_VOID;
		break;
	    case TCKind._tk_boolean:
		type = TangoConst.Tango_DEV_BOOLEAN;
		break;
	    case TCKind._tk_char:
		type = TangoConst.Tango_DEV_CHAR;
		break;
	    case TCKind._tk_octet:
		type = TangoConst.Tango_DEV_UCHAR;
		break;
	    case TCKind._tk_short:
		type = TangoConst.Tango_DEV_SHORT;
		break;
	    case TCKind._tk_ushort:
		type = TangoConst.Tango_DEV_USHORT;
		break;
	    case TCKind._tk_long:
		type = TangoConst.Tango_DEV_LONG;
		break;
	    case TCKind._tk_ulong:
		type = TangoConst.Tango_DEV_ULONG;
		break;
	    case TCKind._tk_longlong:
		type = TangoConst.Tango_DEV_LONG64;
		break;
	    case TCKind._tk_ulonglong:
		type = TangoConst.Tango_DEV_ULONG64;
		break;
	    case TCKind._tk_float:
		type = TangoConst.Tango_DEV_FLOAT;
		break;
	    case TCKind._tk_double:
		type = TangoConst.Tango_DEV_DOUBLE;
		break;
	    case TCKind._tk_string:
		type = TangoConst.Tango_DEV_STRING;
		break;
	    case TCKind._tk_enum:
		type = TangoConst.Tango_DEV_STATE;
		break;
	    default:
		Except.throw_exception("AttributeTypeNotSupported",
			"Attribute Type (" + kind.value() + ") Not Supported",
			"DeviceAttribute.getType()");
	    }
	} catch (final org.omg.CORBA.TypeCodePackage.BadKind e) {
	    Except.throw_exception("Api_TypeCodePackage.BadKind",
		    "Bad or unknown type ", "DeviceAttribute.getType()");
	}
	return type;
    }
}
