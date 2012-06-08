//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Log$
// Revision 1.11  2009/06/11 12:22:04  pascal_verdier
// Bug in writing uchar attribute fixed.
//
// Revision 1.10  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.9  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.8  2008/12/19 13:36:39  pascal_verdier
// Attribute data from union for Device_4Impl.
//
// Revision 1.7  2008/12/03 15:44:26  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.6  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2008/09/12 11:32:14  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.4  2008/07/30 11:26:03  pascal_verdier
// insert/extract UChar added
//
// Revision 1.3  2008/04/10 08:27:33  pascal_verdier
// Catch org.omg.CORBA.BAD_PARAM in extractDevStateArray() added
//
// Revision 1.2  2007/11/22 07:53:46  pascal_verdier
// Chenge extractDevStateArray management.
//
// Revision 1.1  2007/08/23 09:41:20  ounsy
// Add default impl for tangorb
//
// Revision 3.21  2007/06/07 09:23:56  pascal_verdier
// extractBoolean() added (was removed unfornutatly).
//
// Revision 3.20  2007/05/29 08:11:15  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.19  2007/01/10 10:11:07  pascal_verdier
// DevFailed message syntax fixed.
//
// Revision 3.18  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.17  2006/03/20 13:05:53  pascal_verdier
// extractCharArray() method added.
//
// Revision 3.16  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.15  2005/11/29 05:32:02  pascal_verdier
// Bug in getType() method for UCHAR fixed.
//
// Revision 3.14  2005/05/18 12:46:22  pascal_verdier
// getType() method added.
//
// Revision 3.13  2004/12/16 10:16:44  pascal_verdier
// Missing TANGO 5 features added.
//
// Revision 3.12  2004/12/09 13:41:14  pascal_verdier
// New Attribute types added (insert and extract methods).
//
// Revision 3.11  2004/12/09 12:13:30  pascal_verdier
// If read attribute failed WrongData exception is thrown for all get methods.
//
// Revision 3.10  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.9  2004/11/05 12:38:56  pascal_verdier
// Bug on DeviceAttribute.extract fixed.
//
// Revision 3.8  2004/10/11 12:23:27  pascal_verdier
// Example in header modified.
//
// Revision 3.7  2004/09/17 07:57:03  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.6  2004/08/17 08:36:39  pascal_verdier
// An exception is now thrown if quality factor is invalid.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttrValUnion;
import fr.esrf.Tango.AttributeDataType;
import fr.esrf.Tango.AttributeDim;
import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.AttributeValue_4;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
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
 * @version $Revision$
 */

public class DeviceAttributeDAODefaultImpl implements IDeviceAttributeDAO {
    private AttributeValue_4 attrval = new AttributeValue_4();
    private DeviceAttribute_3 devAtt_3 = null;
    private boolean use_union = true; // since IDL 4

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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.time = new TimeVal();
	attrval.r_dim = new AttributeDim();
	attrval.w_dim = new AttributeDim();
	attrval.r_dim.dim_x = 1;
	attrval.r_dim.dim_y = 0;
	attrval.w_dim.dim_x = 0;
	attrval.w_dim.dim_y = 0;

	attrval.value = new AttrValUnion();

	final long now = System.currentTimeMillis();
	attrval.time.tv_sec = (int) (now / 1000);
	attrval.time.tv_usec = (int) (now - attrval.time.tv_sec * 1000) * 1000;
	attrval.time.tv_nsec = 0;
	attrval.err_list = new DevError[0];
    }

    public DeviceAttributeDAODefaultImpl() {
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attrval
     *            AttributeValue_4 IDL object.
     */
    // ===========================================
    public void init(final AttributeValue_4 attrval) {
	this.attrval = attrval;
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attrval_3
     *            AttributeValue_3 IDL object.
     */
    // ===========================================
    public void init(final AttributeValue_3 attrval_3) {
	setAttributeValue(attrval_3);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attrval_2
     *            AttributeValue IDL object.
     */
    // ===========================================
    public void init(final AttributeValue attrval_2) {
	setAttributeValue(attrval_2);
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
     *            AttributeValue_4 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_4 attrval) {
	this.attrval = attrval;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attrval_3
     *            AttributeValue_3 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_3 attrval_3) {
	devAtt_3 = new DeviceAttribute_3(attrval_3);
	use_union = false;

	attrval.name = attrval_3.name;
	attrval.quality = attrval_3.quality;
	attrval.time = attrval_3.time;
	attrval.r_dim = attrval_3.r_dim;
	attrval.w_dim = attrval_3.w_dim;
	attrval.err_list = attrval_3.err_list;
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attrval_2
     *            AttributeValue input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue attrval_2) {
	devAtt_3 = new DeviceAttribute_3(attrval_2);
	use_union = false;
	attrval.name = attrval_2.name;
	attrval.quality = attrval_2.quality;
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.time = attrval_2.time;

	attrval.r_dim = new AttributeDim();
	attrval.w_dim = new AttributeDim();
	attrval.r_dim.dim_x = attrval_2.dim_x;
	attrval.r_dim.dim_y = attrval_2.dim_y;
	attrval.w_dim.dim_x = 0;
	attrval.w_dim.dim_y = 0;
	attrval.err_list = null;
    }

    // ===========================================
    // ===========================================
    public AttributeDim getReadAttributeDim() {
	return attrval.r_dim;
    }

    // ===========================================
    // ===========================================
    public AttributeDim getWriteAttributeDim() {
	return attrval.w_dim;
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final DevState[] values = new DevState[1];
	values[0] = argin;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.state_att_value(values);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.state_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.state_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final boolean[] values = new boolean[1];
	values[0] = argin;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.bool_att_value(values);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.bool_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.bool_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final byte[] values = new byte[] { argin };
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.uchar_att_value(values);
	} else {
	    devAtt_3.insert_uc(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.uchar_att_value(argin);
	} else {
	    devAtt_3.insert_uc(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final byte[] values = new byte[] { (byte) (argin & 0xFF) };
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.uchar_att_value(values);
	} else {
	    devAtt_3.insert_uc(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final byte[] values = new byte[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (byte) (argin[i] & 0xFF);
	}
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.uchar_att_value(values);
	} else {
	    devAtt_3.insert_uc(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final byte[] values = new byte[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (byte) (argin[i] & 0xFF);
	}
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.uchar_att_value(values);
	} else {
	    devAtt_3.insert_uc(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.uchar_att_value(argin);
	} else {
	    devAtt_3.insert_uc(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	insert(new short[] { argin });
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.short_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.short_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final short[] values = new short[] { argin };
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ushort_att_value(values);
	} else {
	    devAtt_3.insert_us(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final short[] values = new short[] { (short) (argin & 0xFFFF) };
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ushort_att_value(values);
	} else {
	    devAtt_3.insert_us(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ushort_att_value(argin);
	} else {
	    devAtt_3.insert_us(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final short[] values = new short[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (short) (argin[i] & 0xFFFF);
	}
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ushort_att_value(values);
	} else {
	    devAtt_3.insert_us(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.ushort_att_value(argin);
	} else {
	    devAtt_3.insert_us(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final short[] values = new short[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (short) (argin[i] & 0xFFFF);
	}
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.ushort_att_value(values);
	} else {
	    devAtt_3.insert_us(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	insert(new int[] { argin });
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.long_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.long_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	insert(new long[] { argin });
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.long64_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.long64_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final int[] values = new int[1];
	values[0] = argin;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ulong_att_value(values);
	} else {
	    devAtt_3.insert_ul(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final int[] values = new int[1];
	values[0] = (int) argin;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ulong_att_value(values);
	} else {
	    devAtt_3.insert_ul(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ulong_att_value(argin);
	} else {
	    devAtt_3.insert_ul(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final int[] values = new int[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (int) argin[i];
	}
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ulong_att_value(values);
	} else {
	    devAtt_3.insert_ul(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.ulong_att_value(argin);
	} else {
	    devAtt_3.insert_ul(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final int[] values = new int[argin.length];
	for (int i = 0; i < argin.length; i++) {
	    values[i] = (int) argin[i];
	}
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.ulong_att_value(values);
	} else {
	    devAtt_3.insert_ul(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	final long[] values = new long[1];
	values[0] = argin;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ulong64_att_value(values);
	} else {
	    devAtt_3.insert_u64(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.ulong64_att_value(argin);
	} else {
	    devAtt_3.insert_u64(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.ulong64_att_value(argin);
	} else {
	    devAtt_3.insert_u64(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.float_att_value(new float[] { argin });
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.float_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.float_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	insert(new double[] { argin });
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.double_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.double_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	insert(new String[] { argin });
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = argin.length;
	attrval.w_dim.dim_y = 0;
	if (use_union) {
	    attrval.value.string_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
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
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.w_dim.dim_x = dim_x;
	attrval.w_dim.dim_y = dim_y;
	if (use_union) {
	    attrval.value.string_att_value(argin);
	} else {
	    devAtt_3.insert(argin);
	}
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argin
     *            Attribute values.
     */
    // ===========================================
    public void insert(final DevEncoded argin) {
	attrval.data_format = AttrDataFormat.FMT_UNKNOWN;
	attrval.err_list = new DevError[0]; // ?? Only for DevEncoded, else
	// (NullPointerException)
	attrval.w_dim.dim_x = 1;
	attrval.w_dim.dim_y = 0;
	attrval.value.encoded_att_value(new DevEncoded[] { argin });
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
	    if (use_union) {
		// Check if saclar (State attribute itself !)
		if (attrval.value.discriminator().value() == AttributeDataType._DEVICE_STATE) {
		    return new DevState[] { attrval.value.dev_state_att() };
		} else {
		    return attrval.value.state_att_value();
		}
	    } else {
		return devAtt_3.extractDevStateArray();
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
	if (use_union) {
	    // Check if saclar (State attribute itself !)
	    if (attrval.value.discriminator().value() == AttributeDataType._DEVICE_STATE) {
		return attrval.value.dev_state_att();
	    } else {
		return attrval.value.state_att_value()[0];
	    }
	} else {
	    return devAtt_3.extractDevState();
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
	if (use_union) {
	    return attrval.value.bool_att_value()[0];
	} else {
	    return devAtt_3.extractBoolean();
	}
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
	if (use_union) {
	    return attrval.value.bool_att_value();
	} else {
	    return devAtt_3.extractBooleanArray();
	}
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
	if (use_union) {
	    return attrval.value.uchar_att_value()[0];
	} else {
	    return devAtt_3.extractUChar();
	}
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

	if (use_union) {
	    final byte[] argout = attrval.value.uchar_att_value();
	    final short[] val = new short[argout.length];
	    final short mask = 0xFF;
	    for (int i = 0; i < argout.length; i++) {
		val[i] = (short) (mask & argout[i]);
	    }
	    return val;
	} else {
	    return devAtt_3.extractUCharArray();
	}
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

	if (use_union) {
	    return attrval.value.uchar_att_value();
	} else {
	    return devAtt_3.extractCharArray();
	}
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
	manageExceptions("extractShort()");
	if (use_union) {
	    return attrval.value.short_att_value()[0];
	} else {
	    return devAtt_3.extractShort();
	}
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
	if (use_union) {
	    return attrval.value.short_att_value();
	} else {
	    return devAtt_3.extractShortArray();
	}
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
	if (use_union) {
	    return attrval.value.ushort_att_value()[0];
	} else {
	    return devAtt_3.extractUShort();
	}
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
	if (use_union) {
	    final short[] argout = attrval.value.ushort_att_value();
	    final int[] val = new int[argout.length];
	    for (int i = 0; i < argout.length; i++) {
		val[i] = 0xFFFF & argout[i];
	    }
	    return val;
	} else {
	    return devAtt_3.extractUShortArray();
	}
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
	if (use_union) {
	    return attrval.value.long_att_value()[0];
	} else {
	    return devAtt_3.extractLong();
	}
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
	if (use_union) {
	    return attrval.value.long_att_value();
	} else {
	    return devAtt_3.extractLongArray();
	}
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
	if (use_union) {
	    final int[] argout = attrval.value.ulong_att_value();
	    long mask = 0x7fffffff;
	    mask += (long) 1 << 31;
	    return mask & argout[0];
	} else {
	    return devAtt_3.extractULong();
	}
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
	if (use_union) {
	    final int[] array = attrval.value.ulong_att_value();
	    long mask = 0x7fffffff;
	    mask += (long) 1 << 31;
	    final long[] result = new long[array.length];
	    for (int i = 0; i < array.length; i++) {
		result[i] = mask & array[i];
	    }
	    return result;
	} else {
	    return devAtt_3.extractULongArray();
	}
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
	if (use_union) {
	    return extractLong64Array()[0];
	} else {
	    return devAtt_3.extractLong64();
	}
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
	if (use_union) {
	    return attrval.value.long64_att_value();
	} else {
	    return devAtt_3.extractLong64Array();
	}
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
	if (use_union) {
	    return attrval.value.ulong64_att_value();
	} else {
	    return devAtt_3.extractULong64Array();
	}
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
	return extractFloatArray()[0];
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
	if (use_union) {
	    return attrval.value.float_att_value();
	} else {
	    return devAtt_3.extractFloatArray();
	}
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
	return extractDoubleArray()[0];
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
	if (use_union) {
	    return attrval.value.double_att_value();
	} else {
	    return devAtt_3.extractDoubleArray();
	}
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
	manageExceptions("extractState");
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
	return extractStringArray()[0];
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
	if (use_union) {
	    return attrval.value.string_att_value();
	} else {
	    return devAtt_3.extractStringArray();
	}
    }

    // ===========================================
    /**
     * extract method for a DevEncoded[]
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public DevEncoded extractDevEncoded() throws DevFailed {
	manageExceptions("extractDevEncoded");
	if (attrval.value.encoded_att_value() == null) {
	    Except.throw_exception("BAD_PARAM", "DevEcoded object is null",
		    "DeviceAttribute.extractDevEncoded()");
	}
	return attrval.value.encoded_att_value()[0];
    }

    // ===========================================
    /**
     * extract method for a DevEncoded[]
     * 
     * @return the extracted value.
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public DevEncoded[] extractDevEncodedArray() throws DevFailed {
	manageExceptions("extractDevEncoded");
	if (attrval.value.encoded_att_value() == null) {
	    Except.throw_exception("BAD_PARAM", "DevEcoded object is null",
		    "DeviceAttribute.extractDevEncoded()");
	}
	return attrval.value.encoded_att_value();
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
     * Return attribute data format (SCALR, . * SPECTRUM, IMAGE or FMT_UNKNOWN)
     * If device is older than Device_4Impl, FMT_UNKNOWN is returned.
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public AttrDataFormat getDataFormat() throws DevFailed {
	return attrval.data_format;
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
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public String getName() throws DevFailed {
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
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue getAttributeValueObject_2() throws DevFailed {
	// Build a DeviceAttribute_3 from this
	final DeviceAttribute_3DAODefaultImpl att = new DeviceAttribute_3DAODefaultImpl();
	att.setAttributeValue(this);
	final AttributeValue attrval = att.getAttributeValueObject_2();
	// And return the AttributeValue object
	return attrval;
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
	// Set the write value as read ones
	// attrval.w_dim.dim_x = attrval.r_dim.dim_x;
	// attrval.w_dim.dim_y = attrval.r_dim.dim_y;

	// return attribute value 4
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
	if (!use_union) {
	    return devAtt_3.getType();
	}

	// Else check for union data type
	if (attrval.value == null || attrval.value.discriminator() == null) {
	    Except.throw_exception("AttributeTypeNotSet", "Attribute "
		    + attrval.name + " Value Has Not Been Set",
		    "DeviceAttribute.getType()");
	}

	int type = -1;
	try {
	    switch (attrval.value.discriminator().value()) {
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
	    case AttributeDataType._NO_DATA:
		type = TangoConst.Tango_DEV_VOID;
		break;

	    default:
		Except.throw_exception("AttributeTypeNotSupported",
			"Attribute Type ("
				+ attrval.value.discriminator().value()
				+ ") Not Supported",
			"DeviceAttribute.getType()");
	    }
	} catch (final org.omg.CORBA.BAD_PARAM e) {
	    Except.throw_exception("Api_TypeCodePackage.BadKind",
		    "Bad or unknown type ", "DeviceAttribute.getType()");
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
