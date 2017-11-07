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
// $Revision: 28442 $
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

public class DeviceAttributeDAODefaultImpl implements IDeviceAttributeDAO {
    private AttributeValue_5 attributeValue_5 = new AttributeValue_5();
    private DeviceAttribute_3 deviceAttribute_3 = null;
    private boolean use_union = true; // since IDL 4

    // ===========================================
    /**
     * Build a DeviceAttribute IDL object
     * 
     * @param name Attribute name.
     */
    // ===========================================
    private void buildAttributeValueObject(final String name) {
        attributeValue_5.name = name;
        attributeValue_5.quality = AttrQuality.ATTR_VALID;
        attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
        attributeValue_5.time = new TimeVal();
        attributeValue_5.r_dim = new AttributeDim();
        attributeValue_5.w_dim = new AttributeDim();
        attributeValue_5.r_dim.dim_x = 1;
        attributeValue_5.r_dim.dim_y = 0;
        attributeValue_5.w_dim.dim_x = 0;
        attributeValue_5.w_dim.dim_y = 0;

        attributeValue_5.value = new AttrValUnion();

        final long now = System.currentTimeMillis();
        attributeValue_5.time.tv_sec = (int) (now / 1000);
        attributeValue_5.time.tv_usec = (int) (now - attributeValue_5.time.tv_sec * 1000) * 1000;
        attributeValue_5.time.tv_nsec = 0;
        attributeValue_5.err_list = new DevError[0];
    }

    public DeviceAttributeDAODefaultImpl() {
    }


    // ===========================================
    /**
     * DeviceAttribute class constructor.
     *
     * @param attributeValue_5 AttributeValue_5 IDL object.
     */
    // ===========================================
    public void init(AttributeValue_5 attributeValue_5) {
        this.attributeValue_5 = attributeValue_5;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue_4 AttributeValue_4 IDL object.
     */
    // ===========================================
    public void init(final AttributeValue_4 attributeValue_4) {
		setAttributeValue(attributeValue_4);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue_3 AttributeValue_3 IDL object.
     */
    // ===========================================
    public void init(final AttributeValue_3 attributeValue_3) {
		setAttributeValue(attributeValue_3);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue AttributeValue IDL object.
     */
    // ===========================================
    public void init(final AttributeValue attributeValue) {
		setAttributeValue(attributeValue);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     */
    // ===========================================
    public void init(final String name) {
		buildAttributeValueObject(name);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void init(final String name, final int dim_x, final int dim_y) {
		buildAttributeValueObject(name);
		attributeValue_5.r_dim.dim_x = dim_x;
		attributeValue_5.r_dim.dim_y = dim_y;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public void init(final String name,
			final boolean[] value, final int dim_x, final int dim_y) {
		buildAttributeValueObject(name);
		insert(value, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public void init(final String name,
			final byte[] value, final int dim_x, final int dim_y) {
		buildAttributeValueObject(name);
		insert_uc(value, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
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
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
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
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
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
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
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
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
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
     * @param name Attribute name.
     * @param value Attribute value.
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
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void init(final String name,
				final String[] values, final int dim_x, final int dim_y) {
		buildAttributeValueObject(name);
		insert(values, dim_x, dim_y);
    }

    // ===========================================
    // ===========================================
    public boolean hasFailed() {
		return attributeValue_5.err_list != null && attributeValue_5.err_list.length > 0;
    }

    // ===========================================
    /**
     * Returns the attribute errors list
     */
    // ===========================================
    public DevError[] getErrStack() {
		return attributeValue_5.err_list;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue_4 AttributeValue_4 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_4 attributeValue_4) {
        use_union = true;
        attributeValue_5.name = attributeValue_4.name;
        attributeValue_5.value = attributeValue_4.value;
        attributeValue_5.quality = attributeValue_4.quality;
        attributeValue_5.time = attributeValue_4.time;
        attributeValue_5.r_dim = attributeValue_4.r_dim;
        attributeValue_5.w_dim = attributeValue_4.w_dim;
        attributeValue_5.err_list = attributeValue_4.err_list;
        attributeValue_5.data_format = attributeValue_4.data_format;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue_3 AttributeValue_3 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_3 attributeValue_3) {
		deviceAttribute_3 = new DeviceAttribute_3(attributeValue_3);
		use_union = false;

		attributeValue_5.name = attributeValue_3.name;
		attributeValue_5.quality = attributeValue_3.quality;
		attributeValue_5.time = attributeValue_3.time;
		attributeValue_5.r_dim = attributeValue_3.r_dim;
		attributeValue_5.w_dim = attributeValue_3.w_dim;
		attributeValue_5.err_list = attributeValue_3.err_list;
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue AttributeValue input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue attributeValue) {
		deviceAttribute_3 = new DeviceAttribute_3(attributeValue);
		use_union = false;
		attributeValue_5.name = attributeValue.name;
		attributeValue_5.quality = attributeValue.quality;
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.time = attributeValue.time;

		attributeValue_5.r_dim = new AttributeDim();
		attributeValue_5.w_dim = new AttributeDim();
		attributeValue_5.r_dim.dim_x = attributeValue.dim_x;
		attributeValue_5.r_dim.dim_y = attributeValue.dim_y;
		attributeValue_5.w_dim.dim_x = 0;
		attributeValue_5.w_dim.dim_y = 0;
		attributeValue_5.err_list = null;
    }

    // ===========================================
    // ===========================================
    public AttributeDim getReadAttributeDim() {
		return attributeValue_5.r_dim;
    }

    // ===========================================
    // ===========================================
    public AttributeDim getWriteAttributeDim() {
		return attributeValue_5.w_dim;
    }

    // ===========================================
    // Insert methods
    // ===========================================

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevState argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final DevState[] values = new DevState[1];
		values[0] = argIn;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.state_att_value(values);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevState[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.state_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final DevState[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.state_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
   	}

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final boolean argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final boolean[] values = new boolean[1];
		values[0] = argIn;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.bool_att_value(values);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final boolean[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.bool_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final boolean[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.bool_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final byte[] values = new byte[] { argIn };
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.uchar_att_value(values);
		} else {
	    	deviceAttribute_3.insert_uc(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.uchar_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_uc(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final short argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final byte[] values = new byte[] { (byte) (argIn & 0xFF) };
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.uchar_att_value(values);
		} else {
	    	deviceAttribute_3.insert_uc(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final short[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final byte[] values = new byte[argIn.length];
		for (int i = 0; i < argIn.length; i++) {
	    	values[i] = (byte) (argIn[i] & 0xFF);
		}
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.uchar_att_value(values);
		} else {
	    	deviceAttribute_3.insert_uc(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     * @param dim_x nb data.in x direction
     * @param dim_y nb data.in y direction
     */
    // ===========================================
    public void insert_uc(final short[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final byte[] values = new byte[argIn.length];
		for (int i = 0; i < argIn.length; i++) {
	    	values[i] = (byte) (argIn[i] & 0xFF);
		}
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.uchar_att_value(values);
		} else {
	    	deviceAttribute_3.insert_uc(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.uchar_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_uc(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final short argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		insert(new short[] { argIn });
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final short[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.short_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final short[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.short_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final short[] values = new short[] { argIn };
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ushort_att_value(values);
		} else {
	    	deviceAttribute_3.insert_us(argIn);
		}
   	}

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final short[] values = new short[] { (short) (argIn & 0xFFFF) };
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ushort_att_value(values);
		} else {
	    	deviceAttribute_3.insert_us(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ushort_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_us(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final short[] values = new short[argIn.length];
		for (int i = 0; i < argIn.length; i++) {
	    	values[i] = (short) (argIn[i] & 0xFFFF);
		}
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ushort_att_value(values);
		} else {
	    	deviceAttribute_3.insert_us(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.ushort_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_us(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final short[] values = new short[argIn.length];
		for (int i = 0; i < argIn.length; i++) {
	    	values[i] = (short) (argIn[i] & 0xFFFF);
		}
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.ushort_att_value(values);
		} else {
	    	deviceAttribute_3.insert_us(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final int argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		insert(new int[] { argIn });
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final int[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.long_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final int[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.long_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final long argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		insert(new long[] { argIn });
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final long[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.long64_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final long[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.long64_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final int[] values = new int[1];
		values[0] = argIn;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ulong_att_value(values);
		} else {
	    	deviceAttribute_3.insert_ul(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final int[] values = new int[1];
		values[0] = (int) argIn;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ulong_att_value(values);
		} else {
	    	deviceAttribute_3.insert_ul(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ulong_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_ul(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final int[] values = new int[argIn.length];
		for (int i = 0; i < argIn.length; i++) {
	    	values[i] = (int) argIn[i];
		}
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ulong_att_value(values);
		} else {
	    	deviceAttribute_3.insert_ul(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.ulong_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_ul(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final int[] values = new int[argIn.length];
		for (int i = 0; i < argIn.length; i++) {
	    	values[i] = (int) argIn[i];
		}
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.ulong_att_value(values);
		} else {
	    	deviceAttribute_3.insert_ul(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_u64(final long argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		final long[] values = new long[1];
		values[0] = argIn;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ulong64_att_value(values);
		} else {
	    	deviceAttribute_3.insert_u64(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_u64(final long[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.ulong64_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_u64(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert_u64(final long[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.ulong64_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert_u64(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final float argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.float_att_value(new float[] { argIn });
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final float[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.float_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final float[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.float_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final double argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		insert(new double[] { argIn });
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final double[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.double_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final double[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.double_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
   	}

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final String argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		insert(new String[] { argIn });
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final String[] argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = argIn.length;
		attributeValue_5.w_dim.dim_y = 0;
		if (use_union) {
	    	attributeValue_5.value.string_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     * @param dim_x array dimention in X
     * @param dim_y array dimention in Y
     */
    // ===========================================
    public void insert(final String[] argIn, final int dim_x, final int dim_y) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.w_dim.dim_x = dim_x;
		attributeValue_5.w_dim.dim_y = dim_y;
		if (use_union) {
	    	attributeValue_5.value.string_att_value(argIn);
		} else {
	    	deviceAttribute_3.insert(argIn);
		}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevEncoded argIn) {
		attributeValue_5.data_format = AttrDataFormat.FMT_UNKNOWN;
		attributeValue_5.err_list = new DevError[0]; // ?? Only for DevEncoded, else
		// (NullPointerException)
		attributeValue_5.w_dim.dim_x = 1;
		attributeValue_5.w_dim.dim_y = 0;
		attributeValue_5.value.encoded_att_value(new DevEncoded[] { argIn });
    }

    // ===========================================
    /**
     * Throws exception if err_list not null.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    private void manageExceptions(final String method_name, boolean onQuality) throws DevFailed {
		if (attributeValue_5.err_list != null) {
	    	if (attributeValue_5.err_list.length > 0) {
				throw new WrongData(attributeValue_5.err_list);
	    	}
		}
		if (onQuality) {
			if (attributeValue_5.quality == AttrQuality.ATTR_INVALID) {
	    		Except.throw_wrong_data_exception("AttrQuality_ATTR_INVALID",
		    		"Attribute quality factor is INVALID", "DeviceAttribute."
			    		+ method_name + "()");
			}
		}
    }
    // ===========================================
    // ===========================================
    private void manageExceptions(final String method_name) throws DevFailed {
		manageExceptions(method_name, true);
	}

    // ===========================================
    /**
     * extract method for an DevState Array.
     * 
     * @return the extracted value.
     * @throws DevFailed  in case of read_attribute failed or if AttrQuality is
     *             ATTR_INVALID.
     */
    // ===========================================
    public DevState[] extractDevStateArray() throws DevFailed {
		manageExceptions("extractDevStateArray()");
		try {
	    	if (use_union) {
                // Check if scalar (State attribute itself !)
                if (attributeValue_5.value.discriminator().value() == AttributeDataType._DEVICE_STATE) {
                    return new DevState[] { attributeValue_5.value.dev_state_att() };
                } else {
                    return attributeValue_5.value.state_att_value();
                }
	    	} else {
				return deviceAttribute_3.extractDevStateArray();
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
     * @throws DevFailed in case of read_attribute failed or
	 *		 	if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public DevState extractDevState() throws DevFailed {
		manageExceptions("extractDevState");
		if (use_union) {
	    	// Check if saclar (State attribute itself !)
	    	if (attributeValue_5.value.discriminator().value() == AttributeDataType._DEVICE_STATE) {
			    return attributeValue_5.value.dev_state_att();
	    	} else {
			    return attributeValue_5.value.state_att_value()[0];
	    	}
		} else {
	    	return deviceAttribute_3.extractDevState();
		}
    }

    // ===========================================
    /**
     * extract method for an boolean.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *		 	if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public boolean extractBoolean() throws DevFailed {
		manageExceptions("extractBoolean()");
		if (use_union) {
	    	return attributeValue_5.value.bool_att_value()[0];
		} else {
	    	return deviceAttribute_3.extractBoolean();
		}
    }

    // ===========================================
    /**
     * extract method for an boolean Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public boolean[] extractBooleanArray() throws DevFailed {
		manageExceptions("extractBooleanArray()");
		if (use_union) {
	    	return attributeValue_5.value.bool_att_value();
		} else {
	    	return deviceAttribute_3.extractBooleanArray();
		}
    }

    // ===========================================
    /**
     * extract method for an unsigned char.
     * 
     * @return the extracted value.
     * @throws DevFailed  in case of read_attribute failed or
	 *			if AttrQuality is  ATTR_INVALID.
     */
    // ===========================================
    public short extractUChar() throws DevFailed {
		manageExceptions("extractUChar");
		if (use_union) {
	    	return attributeValue_5.value.uchar_att_value()[0];
		} else {
	    	return deviceAttribute_3.extractUChar();
		}
    }

    // ===========================================
    /**
     * extract method for an unsigned char Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public short[] extractUCharArray() throws DevFailed {
		manageExceptions("extractUCharArray()");

		if (use_union) {
	    	final byte[] argOut = attributeValue_5.value.uchar_att_value();
	    	final short[] val = new short[argOut.length];
	    	final short mask = 0xFF;
	    	for (int i = 0; i < argOut.length; i++) {
			val[i] = (short) (mask & argOut[i]);
	    	}
	    	return val;
		} else {
	    	return deviceAttribute_3.extractUCharArray();
		}
    }

    // ===========================================
    /**
     * extract method for an unsigned char Array as a char array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public byte[] extractCharArray() throws DevFailed {
		manageExceptions("extractCharArray()");

		if (use_union) {
	    	return attributeValue_5.value.uchar_att_value();
		} else {
	    	return deviceAttribute_3.extractCharArray();
		}
    }

    // ===========================================
    /**
     * extract method for a short.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
      */
    // ===========================================
    public short extractShort() throws DevFailed {
		manageExceptions("extractShort()");
		if (use_union) {
	    	return attributeValue_5.value.short_att_value()[0];
		} else {
	    	return deviceAttribute_3.extractShort();
		}
    }

    // ===========================================
    /**
     * extract method for a short Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public short[] extractShortArray() throws DevFailed {
		manageExceptions("extractShortArray");
		if (use_union) {
	    	return attributeValue_5.value.short_att_value();
		} else {
	    	return deviceAttribute_3.extractShortArray();
		}
    }

    // ===========================================
    /**
     * extract method for an unsigned short.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
      */
    // ===========================================
    public int extractUShort() throws DevFailed {
		manageExceptions("extractUShort");
		if (use_union) {
	    	return attributeValue_5.value.ushort_att_value()[0];
		} else {
	    	return deviceAttribute_3.extractUShort();
		}
    }

    // ===========================================
    /**
     * extract method for an unsigned short Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
      */
    // ===========================================
    public int[] extractUShortArray() throws DevFailed {
		manageExceptions("extractUShortArray");
		if (use_union) {
	    	final short[] argOut = attributeValue_5.value.ushort_att_value();
	    	final int[] val = new int[argOut.length];
	    	for (int i = 0; i < argOut.length; i++) {
			val[i] = 0xFFFF & argOut[i];
	    	}
	    	return val;
		} else {
	    	return deviceAttribute_3.extractUShortArray();
		}
    }

    // ===========================================
    /**
     * extract method for a long.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
      */
    // ===========================================
    public int extractLong() throws DevFailed {
		manageExceptions("extractLong");
		if (use_union) {
	    	return attributeValue_5.value.long_att_value()[0];
		} else {
	    	return deviceAttribute_3.extractLong();
		}
    }

    // ===========================================
    /**
     * extract method for a long Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public int[] extractLongArray() throws DevFailed {
		manageExceptions("extractLongArray");
		if (use_union) {
	    	return attributeValue_5.value.long_att_value();
		} else {
	    	return deviceAttribute_3.extractLongArray();
		}
    }

    // ===========================================
    /**
     * extract method for a unsigned long.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public long extractULong() throws DevFailed {
		manageExceptions("extractULong");
		if (use_union) {
	    	final int[] argOut = attributeValue_5.value.ulong_att_value();
	    	long mask = 0x7fffffff;
	    	mask += (long) 1 << 31;
	    	return mask & argOut[0];
		} else {
	    	return deviceAttribute_3.extractULong();
		}
    }

    // ===========================================
    /**
     * extract method for a unsigned long.array
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public long[] extractULongArray() throws DevFailed {
		manageExceptions("extractULong");
		if (use_union) {
	    	final int[] array = attributeValue_5.value.ulong_att_value();
	    	long mask = 0x7fffffff;
	    	mask += (long) 1 << 31;
	    	final long[] result = new long[array.length];
	    	for (int i = 0; i < array.length; i++) {
			result[i] = mask & array[i];
	    	}
	    	return result;
		} else {
	    	return deviceAttribute_3.extractULongArray();
		}
    }

    // ===========================================
    /**
     * extract method for a long.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public long extractLong64() throws DevFailed {
		manageExceptions("extractLong64");
		if (use_union) {
	    	return extractLong64Array()[0];
		} else {
	    	return deviceAttribute_3.extractLong64();
		}
    }

    // ===========================================
    /**
     * extract method for a long Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public long[] extractLong64Array() throws DevFailed {
		manageExceptions("extractLong64Array");
		if (use_union) {
	    	return attributeValue_5.value.long64_att_value();
		} else {
	    	return deviceAttribute_3.extractLong64Array();
		}
    }

    // ===========================================
    /**
     * extract method for a long.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
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
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public long[] extractULong64Array() throws DevFailed {
		manageExceptions("extractULong64Array");
		if (use_union) {
	    	return attributeValue_5.value.ulong64_att_value();
		} else {
	    	return deviceAttribute_3.extractULong64Array();
		}
    }

    // ===========================================
    /**
     * extract method for a float.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public float extractFloat() throws DevFailed {
		manageExceptions("extractFloat");
		return extractFloatArray()[0];
    }

    // ===========================================
    /**
     * extract method for a float Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public float[] extractFloatArray() throws DevFailed {
		manageExceptions("extractFloatArray");
		if (use_union) {
	    	return attributeValue_5.value.float_att_value();
		} else {
	    	return deviceAttribute_3.extractFloatArray();
		}
    }

    // ===========================================
    /**
     * extract method for a double.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public double extractDouble() throws DevFailed {
		manageExceptions("extractDouble");
		return extractDoubleArray()[0];
    }

    // ===========================================
    /**
     * extract method for a double Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public double[] extractDoubleArray() throws DevFailed {
		manageExceptions("extractDoubleArray");
		if (use_union) {
	    	return attributeValue_5.value.double_att_value();
		} else {
	    	return deviceAttribute_3.extractDoubleArray();
		}
    }

    // ===========================================
    /**
     * extract method for a DevState (state attribute).
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public DevState extractState() throws DevFailed {
		// It is used for state attribute
		// and kept for backward compatibility
		manageExceptions("extractState");
		return extractDevState();
    }

    // ===========================================
    /**
     * extract method for a String.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public String extractString() throws DevFailed {
		manageExceptions("extractString");
		return extractStringArray()[0];
    }

    // ===========================================
    /**
     * extract method for a double Array.
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed or
	 *			if AttrQuality is ATTR_INVALID.
     */
    // ===========================================
    public String[] extractStringArray() throws DevFailed {
		manageExceptions("extractStringArray");
		if (use_union) {
	    	return attributeValue_5.value.string_att_value();
		} else {
	    	return deviceAttribute_3.extractStringArray();
		}
    }

    // ===========================================
    /**
     * extract method for a DevEncoded[]
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public DevEncoded extractDevEncoded() throws DevFailed {
		manageExceptions("extractDevEncoded");
		if (attributeValue_5.value.encoded_att_value() == null) {
	    	Except.throw_exception("BAD_PARAM", "DevEncoded object is null");
		}
		return attributeValue_5.value.encoded_att_value()[0];
    }

    // ===========================================
    /**
     * extract method for a DevEncoded[]
     * 
     * @return the extracted value.
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public DevEncoded[] extractDevEncodedArray() throws DevFailed {
		manageExceptions("extractDevEncoded");
		if (attributeValue_5.value.encoded_att_value() == null) {
	    	Except.throw_exception("BAD_PARAM", "DevEncoded object is null");
		}
		return attributeValue_5.value.encoded_att_value();
    }

    // ===========================================
    // ===========================================

    // ===========================================
    /**
     * Return attribute quality
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public AttrQuality getQuality() throws DevFailed {
		manageExceptions("getQuality", false);
		return attributeValue_5.quality;
    }

    // ===========================================
    /**
     * Return attribute data format (SCALR, . * SPECTRUM, IMAGE or FMT_UNKNOWN)
     * If device is older than Device_4Impl, FMT_UNKNOWN is returned.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public AttrDataFormat getDataFormat() throws DevFailed {
		return attributeValue_5.data_format;
    }

    // ===========================================
    /**
     * Return attribute time value.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public TimeVal getTimeVal() throws DevFailed {
		manageExceptions("getTimeVal", false);
		return attributeValue_5.time;
   	}

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValSec() throws DevFailed {
		manageExceptions("getTimeValSec", false);
		return attributeValue_5.time.tv_sec;
    }

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValMillisSec() throws DevFailed {
		manageExceptions("getTimeValMillisSec", false);
		return attributeValue_5.time.tv_sec * 1000L + attributeValue_5.time.tv_usec / 1000L;
    }

    // ===========================================
    /**
     * Return attribute name.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public String getName() throws DevFailed {
		return attributeValue_5.name;
    }

    // ===========================================
    // ===========================================
    private static int DIM_MINI(final int y) {
		return y == 0 ? 1 : y;
    }

    // ===========================================
    /**
     * Return number of data read.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getNbRead() throws DevFailed {
		manageExceptions("getNbRead");
        int ySize = attributeValue_5.r_dim.dim_y;
		return attributeValue_5.r_dim.dim_x * DIM_MINI(ySize);
    }

    // ===========================================
    /**
     * Return number of data written.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getNbWritten() throws DevFailed {
		manageExceptions("getNbWritten");
        int ySize = attributeValue_5.w_dim.dim_y;
		return attributeValue_5.w_dim.dim_x * DIM_MINI(ySize);
    }

    // ===========================================
    /**
     * Return attribute dim_x.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getDimX() throws DevFailed {
		manageExceptions("getDimX");
		return attributeValue_5.r_dim.dim_x;
    }

    // ===========================================
    /**
     * Return attribute dim_y.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getDimY() throws DevFailed {
		manageExceptions("getDimY");
		return attributeValue_5.r_dim.dim_y;
    }

    // ===========================================
    /**
     * Return attribute written dim_x.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimX() throws DevFailed {
		manageExceptions("getWrittenDimX");
		return attributeValue_5.w_dim.dim_x;
    }

    // ===========================================
    /**
     * Return attribute written dim_y.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimY() throws DevFailed {
		manageExceptions("getWrittenDimY");
		return attributeValue_5.w_dim.dim_y;
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue getAttributeValueObject_2() throws DevFailed {
		// Build a DeviceAttribute_3 from this
		final DeviceAttribute_3DAODefaultImpl att = new DeviceAttribute_3DAODefaultImpl();
		att.setAttributeValue(this);
		return att.getAttributeValueObject_2();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue_3 getAttributeValueObject_3() throws DevFailed {
		// Build a DeviceAttribute_3 from this
		final DeviceAttribute_3 att = new DeviceAttribute_3();
		att.setAttributeValue(this);
		// And return the AttributeValue_3 object
		return att.getAttributeValueObject_3();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_4 getAttributeValueObject_4() {
		// return attribute value 4
		return new AttributeValue_4(
                attributeValue_5.value,
                attributeValue_5.quality,
                attributeValue_5.data_format,
                attributeValue_5.time,
                attributeValue_5.name,
                attributeValue_5.r_dim,
                attributeValue_5.w_dim,
                attributeValue_5.err_list
        );
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_5 getAttributeValueObject_5() {
        return attributeValue_5;
    }

    // ===========================================
    /**
     * return time in milliseconds since 1/1/70
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTime() throws DevFailed {
		manageExceptions("getTime", false);
		return (long) attributeValue_5.time.tv_sec * 1000 + attributeValue_5.time.tv_usec / 1000;
    }

    // ===========================================
    // ===========================================
    public int getType() throws DevFailed {
		if (!use_union) {
		    return deviceAttribute_3.getType();
		}

        if (attributeValue_5.data_type!=TangoConst.Tango_DEV_VOID)
            return attributeValue_5.data_type;

		// Else check for union data type
		if (attributeValue_5.value == null || attributeValue_5.value.discriminator() == null) {
	    	Except.throw_exception("AttributeTypeNotSet",
                    "Attribute " + attributeValue_5.name + " Value Has Not Been Set");
		}

		int type = -1;
		try {
	    	switch (attributeValue_5.value.discriminator().value()) {
    			case AttributeDataType._ATT_BOOL:
					type = TangoConst.Tango_DEV_BOOLEAN;
					break;
    			case AttributeDataType._ATT_SHORT:
					type = TangoConst.Tango_DEV_SHORT;
					break;
    			case AttributeDataType._ATT_LONG:
					type = TangoConst.Tango_DEV_LONG;
					break;
    			case AttributeDataType._ATT_LONG64:
					type = TangoConst.Tango_DEV_LONG64;
					break;
    			case AttributeDataType._ATT_FLOAT:
					type = TangoConst.Tango_DEV_FLOAT;
					break;
    			case AttributeDataType._ATT_DOUBLE:
					type = TangoConst.Tango_DEV_DOUBLE;
					break;
    			case AttributeDataType._ATT_UCHAR:
					type = TangoConst.Tango_DEV_UCHAR;
					break;
	   			case AttributeDataType._ATT_USHORT:
					type = TangoConst.Tango_DEV_USHORT;
					break;
    			case AttributeDataType._ATT_ULONG:
					type = TangoConst.Tango_DEV_ULONG;
					break;
    			case AttributeDataType._ATT_ULONG64:
					type = TangoConst.Tango_DEV_ULONG64;
					break;
    			case AttributeDataType._ATT_STRING:
					type = TangoConst.Tango_DEV_STRING;
					break;
    			case AttributeDataType._DEVICE_STATE:
	    		case AttributeDataType._ATT_STATE:
					type = TangoConst.Tango_DEV_STATE;
					break;
    			case AttributeDataType._ATT_ENCODED:
					type = TangoConst.Tango_DEV_ENCODED;
					break;
    			case AttributeDataType._ATT_NO_DATA:
					type = TangoConst.Tango_DEV_VOID;
					break;

    			default:
					Except.throw_exception("AttributeTypeNotSupported",
							"Attribute Type (" +
						    attributeValue_5.value.discriminator().value() + ") Not Supported");
	    	}
		} catch (final org.omg.CORBA.BAD_PARAM e) {
	    	Except.throw_exception("Api_TypeCodePackage.BadKind", "Bad or unknown type ");
		}
		return type;
    }

    // ===========================================================================
    /**
     * Set the Union value as Any to the DeviceAttribute_3 object
     */
    // ===========================================================================
    void setValueAsAny(final DeviceAttribute_3DAODefaultImpl att_3) 
	 throws DevFailed {
		switch (getType()) {
			case TangoConst.Tango_DEV_BOOLEAN:
	    		att_3.insert(extractBooleanArray());
	    		break;
			case TangoConst.Tango_DEV_SHORT:
	    		att_3.insert(extractShortArray());
	    		break;
			case TangoConst.Tango_DEV_LONG:
	    		att_3.insert(extractLongArray());
	    		break;
			case TangoConst.Tango_DEV_LONG64:
	    		att_3.insert(extractLong64Array());
	    		break;
			case TangoConst.Tango_DEV_FLOAT:
	    		att_3.insert(extractFloatArray());
	    		break;
			case TangoConst.Tango_DEV_DOUBLE:
	    		att_3.insert(extractDoubleArray());
	    		break;
			case TangoConst.Tango_DEV_UCHAR:
	    		att_3.insert_uc(extractUCharArray());
	    		break;
			case TangoConst.Tango_DEV_USHORT:
	    		att_3.insert_us(extractUShortArray());
	    		break;
			case TangoConst.Tango_DEV_ULONG:
	    		att_3.insert_ul(extractULongArray());
	    		break;
			case TangoConst.Tango_DEV_ULONG64:
	    		att_3.insert_u64(extractULong64Array());
	    		break;
			case TangoConst.Tango_DEV_STRING:
	    		att_3.insert(extractStringArray());
	    		break;
			case TangoConst.Tango_DEV_STATE:
	    		att_3.insert(extractDevStateArray());
	    		break;
		}
   	}
}
