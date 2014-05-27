//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
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
// $Revision$
//
//-======================================================================


package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;

/**
 * Class Description: This class manage data object for Tango device attribute
 * access. <br>
 * <br>
 * <br>
 * <b> Usage example: </b> <br>
 * <ul><i>
 *   // Read "Current" attribute<br>
 *   DeviceAttribute deviceAttribute = deviceProxy.read_attribute("Current"); <br>
 *   if (deviceAttribute.hasFailed()) { <br>
 *   <ul>
 *      Except.print_exception(deviceAttribute.getErrStack());
 *   </ul>
 *   else { <br>
 *   <ul>
 *       // If attribute read is double<br>
 *       int type = attribute.getType();<br>
 *       if (type==TangoConst.Tango_DEV_DOUBLE) {<br>
 *       <ul>
 *          double current = deviceAttribute.extractDouble(); <br>
 *          System.out.println("Current : " + current);<br>
 *       </ul>
 *       }<br>
 *       else if (type==TangoConst.Tango_DEV_ENUM) {
 *       <ul>
 *          // If attribute read is an enum<br>
 *          AttributeInfoEx   info = deviceProxy.get_attribute_info_ex(attributeName);<br>
 *          short index = deviceAttribute.extractShort();<br>
 *          System.out.println(info.getEnumLabel(index));<br>
 *       </ul>
 *       }<br>
 *   </ul>
 *   }
 *   <br><br><br>
 *
 *   // To write an enum value, use a short value<br>
 *   short  v = 2;<br>
 *   deviceAttribute = new DeviceAttribute("EnumAttr");<br>
 *   deviceAttribute.insert(v);<br>
 *   deviceProxy.write_attribute(deviceAttribute);<br>
 *   <br><br>
 *   // Or declare an enum like:<br>
 *   enum Numbers { ZERO(0), ONE(1), TWO(2}, THREE(3);<br>
 *   <ul>
 *       public short value;<br>
 *       private Numbers(int value) {
 *       <ul>
 *           this.value = (short)value;
 *       </ul>
 *       }
 *   </ul>
 *   }<br>
 *   - - - - - <br>
 *   // And insert enum value <br>
 *   deviceAttribute.insert(Numbers.TWO.value);<br>
 * </ul></i>
 *
 * @author verdier
 * @version $Revision$
 */

public class DeviceAttribute {
    private IDeviceAttributeDAO deviceattributeDAO = null;

    public DeviceAttribute() {
	deviceattributeDAO = TangoFactory.getSingleton()
		.getDeviceAttributeDAO();
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue_5 AttributeValue_5 IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue_5 attributeValue_5) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(attributeValue_5);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     *
     * @param attributeValue_4 AttributeValue_4 IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue_4 attributeValue_4) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(attributeValue_4);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue_3 AttributeValue_3 IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue_3 attributeValue_3) {
    	deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(attributeValue_3);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue AttributeValue IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue attributeValue) {
	    deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
    	deviceattributeDAO.init(attributeValue);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     */
    // ===========================================
    public DeviceAttribute(final String name) {
	    deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
    	deviceattributeDAO.init(name);
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
    public DeviceAttribute(final String name, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final boolean value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final DevState value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name,
                           final boolean[] value, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final byte value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name,
                           final byte[] value, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final short value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param values Attribute values.
     * @param dim_x  array dimention in X
     * @param dim_y  array dimention in Y
     */
    // ===========================================
    public DeviceAttribute(final String name,
                           final short[] values, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final int value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
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
    public DeviceAttribute(final String name,
                           final int[] values, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name  Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final long value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
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
    public DeviceAttribute(final String name, final long[] values, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton() .getDeviceAttributeDAO();
        deviceattributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final float value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
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
    public DeviceAttribute(final String name,
                           final float[] values, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final double value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
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
    public DeviceAttribute(final String name,
                           final double[] values, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     * @param value Attribute value.
     */
    // ===========================================
    public DeviceAttribute(final String name, final String value) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, value);
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
    public DeviceAttribute(final String name,
                           final String[] values, final int dim_x, final int dim_y) {
        deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceattributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    // ===========================================
    public boolean hasFailed() {
        return deviceattributeDAO.hasFailed();
    }

    // ===========================================
    /**
     * Returns the attribute errors list
     */
    // ===========================================
    public DevError[] getErrStack() {
        return deviceattributeDAO.getErrStack();
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue3 AttributeValue_3 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_3 attributeValue3) {
        deviceattributeDAO.setAttributeValue(attributeValue3);
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue AttributeValue input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue attributeValue) {
        deviceattributeDAO.setAttributeValue(attributeValue);
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
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevState[] argIn) {
        deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final boolean argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final boolean[] argIn) {
	deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte argIn) {
        deviceattributeDAO.insert_uc(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argIn) {
        deviceattributeDAO.insert_uc(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final short argIn) {
        deviceattributeDAO.insert_uc(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final short[] argIn) {
        deviceattributeDAO.insert_uc(argIn);
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
        deviceattributeDAO.insert_uc(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argIn, final int dim_x, final int dim_y) {
        deviceattributeDAO.insert_uc(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final short argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final short[] argIn) {
        deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short argIn) {
        deviceattributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int argIn) {
        deviceattributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argIn) {
        deviceattributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argIn) {
        deviceattributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argIn, final int dim_x, final int dim_y) {
        deviceattributeDAO.insert_us(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argIn, final int dim_x, final int dim_y) {
        deviceattributeDAO.insert_us(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final int argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final int[] argIn) {
        deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final long argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final long[] argIn) {
        deviceattributeDAO.insert(argIn);
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
	deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int argIn) {
        deviceattributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long argIn) {
        deviceattributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argIn) {
        deviceattributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argIn) {
        deviceattributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argIn, final int dim_x, final int dim_y) {
        deviceattributeDAO.insert_ul(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argIn, final int dim_x, final int dim_y) {
        deviceattributeDAO.insert_ul(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_u64(final long argIn) {
        deviceattributeDAO.insert_u64(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_u64(final long[] argIn) {
        deviceattributeDAO.insert_u64(argIn);
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
        deviceattributeDAO.insert_u64(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final float argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final float[] argIn) {
        deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final double argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final double[] argIn) {
        deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final String argIn) {
        deviceattributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final String[] argIn) {
        deviceattributeDAO.insert(argIn);
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
        deviceattributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevEncoded argIn) {
        deviceattributeDAO.insert(argIn);
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
        return deviceattributeDAO.extractDevStateArray();
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
        return deviceattributeDAO.extractDevState();
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
        return deviceattributeDAO.extractBoolean();
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
        return deviceattributeDAO.extractBooleanArray();
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
        return deviceattributeDAO.extractUChar();
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
        return deviceattributeDAO.extractUCharArray();
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
        return deviceattributeDAO.extractCharArray();
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
        return deviceattributeDAO.extractShort();
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
        return deviceattributeDAO.extractShortArray();
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
        return deviceattributeDAO.extractUShort();
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
        return deviceattributeDAO.extractUShortArray();
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
        return deviceattributeDAO.extractLong();
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
        return deviceattributeDAO.extractLongArray();
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
        return deviceattributeDAO.extractULong();
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
        return deviceattributeDAO.extractULongArray();
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
        return deviceattributeDAO.extractLong64();
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
        return deviceattributeDAO.extractLong64Array();
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
        return deviceattributeDAO.extractULong64();
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
        return deviceattributeDAO.extractULong64Array();
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
	return deviceattributeDAO.extractFloat();
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
        return deviceattributeDAO.extractFloatArray();
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
        return deviceattributeDAO.extractDouble();
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
        return deviceattributeDAO.extractDoubleArray();
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
        return deviceattributeDAO.extractState();
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
        return deviceattributeDAO.extractString();
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
        return deviceattributeDAO.extractStringArray();
    }

    // ===========================================
    /**
     * Return attribute quality
     * 
     * @throws DevFailed
     *             in case of read_attribute failed
     */
    // ===========================================
    public AttrQuality getQuality() throws DevFailed {
        return deviceattributeDAO.getQuality();
    }

    // ===========================================
    /**
     * extract method for a DevEncoded
     * 
     * @return the extracted value.
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public DevEncoded extractDevEncoded() throws DevFailed {
        return deviceattributeDAO.extractDevEncoded();
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
        return deviceattributeDAO.extractDevEncodedArray();
    }

    // ===========================================
    /**
     * Return attribute data format (SCALAR, . * SPECTRUM, IMAGE or FMT_UNKNOWN)
     * If device is older than Device_4Impl, FMT_UNKNOWN is returned.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public AttrDataFormat getDataFormat() throws DevFailed {
        return deviceattributeDAO.getDataFormat();
    }

    // ===========================================
    /**
     * Return attribute time value.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public TimeVal getTimeVal() throws DevFailed {
        return deviceattributeDAO.getTimeVal();
    }

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValSec() throws DevFailed {
        return deviceattributeDAO.getTimeValSec();
    }

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValMillisSec() throws DevFailed {
        return deviceattributeDAO.getTimeValMillisSec();
    }

    // ===========================================
    /**
     * Return attribute name.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public String getName() throws DevFailed {
        return deviceattributeDAO.getName();
    }

    // ===========================================
    /**
     * Return number of data read.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getNbRead() throws DevFailed {
        return deviceattributeDAO.getNbRead();
    }

    // ===========================================
    /**
     * Return number of data read.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    public AttributeDim getReadAttributeDim() throws DevFailed {
        return deviceattributeDAO.getReadAttributeDim();
    }

    // ===========================================
    /**
     * Return number of data write.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    public AttributeDim getWriteAttributeDim() throws DevFailed {
        return deviceattributeDAO.getWriteAttributeDim();
    }

    // ===========================================
    /**
     * Return number of data written.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getNbWritten() throws DevFailed {
        return deviceattributeDAO.getNbWritten();
    }

    // ===========================================
    /**
     * Return attribute dim_x.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getDimX() throws DevFailed {
        return deviceattributeDAO.getDimX();
    }

    // ===========================================
    /**
     * Return attribute dim_y.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getDimY() throws DevFailed {
        return deviceattributeDAO.getDimY();
    }

    // ===========================================
    /**
     * Return attribute written dim_x.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimX() throws DevFailed {
        return deviceattributeDAO.getWrittenDimX();
    }

    // ===========================================
    /**
     * Return attribute written dim_y.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimY() throws DevFailed {
        return deviceattributeDAO.getWrittenDimY();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue getAttributeValueObject_2() throws DevFailed {
        return deviceattributeDAO.getAttributeValueObject_2();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue_3 getAttributeValueObject_3() throws DevFailed {
        return deviceattributeDAO.getAttributeValueObject_3();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_4 getAttributeValueObject_4() {
        return deviceattributeDAO.getAttributeValueObject_4();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_5 getAttributeValueObject_5() {
        return deviceattributeDAO.getAttributeValueObject_5();
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
        return deviceattributeDAO.getTime();
    }

    // ===========================================
    // ===========================================
    public int getType() throws DevFailed {
        return deviceattributeDAO.getType();
    }

    // ===========================================
    // ===========================================
    public IDeviceAttributeDAO getDeviceattributeDAO() {
        return deviceattributeDAO;
    }
}
