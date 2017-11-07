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

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;

/**
 * Class Description: This class manage data object for Tango device attribute access. <br>
 * <br>
 * <br>
 * <b> Usage example: </b> <br>
 * <ul><i>
 *   <FONT COLOR="#3b648b">   <!--- DeepSkyBlue4 --->
 *   // Read "Current" attribute</font><br>
 *   DeviceAttribute deviceAttribute = deviceProxy.read_attribute("Current"); <br>
 *   if (deviceAttribute.hasFailed()) { <br>
 *   <ul>
 *      Except.print_exception(deviceAttribute.getErrStack());
 *   </ul>
 *   else { <br>
 *   <ul>
 *       int type = attribute.getType();<br>
 *       if (type==TangoConst.Tango_DEV_DOUBLE) {<br>
 *       <ul>
 *          <FONT COLOR="#3b648b">   <!--- DeepSkyBlue4 --->
 *          // If attribute read is double</font><br>
 *          double current = deviceAttribute.extractDouble(); <br>
 *          System.out.println("Current : " + current);<br>
 *       </ul>
 *       }<br>
 *       else if (type==TangoConst.Tango_DEV_ENUM) {
 *       <ul>
 *       <FONT COLOR="#3b648b">   <!--- DeepSkyBlue4 --->
 *          // If attribute read is an enum</font><br>
 *          AttributeInfoEx   info = deviceProxy.get_attribute_info_ex(attributeName);<br>
 *          short index = deviceAttribute.extractShort();<br>
 *          System.out.println(info.getEnumLabel(index));<br>
 *       </ul>
 *       }<br>
 *   </ul>
 *   }
 *   <br><br><br>
 *
 *   <FONT COLOR="#3b648b">   <!--- DeepSkyBlue4 --->
 *   // To write an enum value, use a short value</font><br>
 *   short  index = 2;<br>
 *   deviceAttribute = new DeviceAttribute("EnumAttr");<br>
 *   deviceAttribute.insert(index);<br>
 *   deviceProxy.write_attribute(deviceAttribute);<br>
 *   <br><br>
 *   <FONT COLOR="#3b648b">   <!--- DeepSkyBlue4 --->
 *   // Or declare an enum like:</font><br>
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
 *   <FONT COLOR="#3b648b">   <!--- DeepSkyBlue4 --->
 *   // And insert enum value </font><br>
 *   deviceAttribute.insert(Numbers.TWO.value);<br>
 * </ul></i>
 *
 * @author verdier
 * @version $Revision: 26454 $
 */

public class DeviceAttribute {
    private IDeviceAttributeDAO deviceAttributeDAO = null;

    public DeviceAttribute() {
	deviceAttributeDAO = TangoFactory.getSingleton()
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(attributeValue_5);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     *
     * @param attributeValue_4 AttributeValue_4 IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue_4 attributeValue_4) {
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(attributeValue_4);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue_3 AttributeValue_3 IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue_3 attributeValue_3) {
    	deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(attributeValue_3);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param attributeValue AttributeValue IDL object.
     */
    // ===========================================
    public DeviceAttribute(final AttributeValue attributeValue) {
	    deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
    	deviceAttributeDAO.init(attributeValue);
    }

    // ===========================================
    /**
     * DeviceAttribute class constructor.
     * 
     * @param name Attribute name.
     */
    // ===========================================
    public DeviceAttribute(final String name) {
	    deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
    	deviceAttributeDAO.init(name);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, values, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, values, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton() .getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, values, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, values, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, values, dim_x, dim_y);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, value);
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
        deviceAttributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
        deviceAttributeDAO.init(name, values, dim_x, dim_y);
    }

    // ===========================================
    // ===========================================
    public boolean hasFailed() {
        return deviceAttributeDAO.hasFailed();
    }

    // ===========================================
    /**
     * Returns the attribute errors list
     */
    // ===========================================
    public DevError[] getErrStack() {
        return deviceAttributeDAO.getErrStack();
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue3 AttributeValue_3 input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue_3 attributeValue3) {
        deviceAttributeDAO.setAttributeValue(attributeValue3);
    }

    // ===========================================
    /**
     * Set the AttributeValue internal object with input one.
     * 
     * @param attributeValue AttributeValue input object
     */
    // ===========================================
    public void setAttributeValue(final AttributeValue attributeValue) {
        deviceAttributeDAO.setAttributeValue(attributeValue);
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
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevState[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final boolean argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final boolean[] argIn) {
	deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte argIn) {
        deviceAttributeDAO.insert_uc(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argIn) {
        deviceAttributeDAO.insert_uc(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final short argIn) {
        deviceAttributeDAO.insert_uc(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final short[] argIn) {
        deviceAttributeDAO.insert_uc(argIn);
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
        deviceAttributeDAO.insert_uc(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_uc(final byte[] argIn, final int dim_x, final int dim_y) {
        deviceAttributeDAO.insert_uc(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final short argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final short[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short argIn) {
        deviceAttributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int argIn) {
        deviceAttributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argIn) {
        deviceAttributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argIn) {
        deviceAttributeDAO.insert_us(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final short[] argIn, final int dim_x, final int dim_y) {
        deviceAttributeDAO.insert_us(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_us(final int[] argIn, final int dim_x, final int dim_y) {
        deviceAttributeDAO.insert_us(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final int argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final int[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final long argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final long[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
	deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int argIn) {
        deviceAttributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values as unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long argIn) {
        deviceAttributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argIn) {
        deviceAttributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argIn) {
        deviceAttributeDAO.insert_ul(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final int[] argIn, final int dim_x, final int dim_y) {
        deviceAttributeDAO.insert_ul(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute valuesas unsigned.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_ul(final long[] argIn, final int dim_x, final int dim_y) {
        deviceAttributeDAO.insert_ul(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_u64(final long argIn) {
        deviceAttributeDAO.insert_u64(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert_u64(final long[] argIn) {
        deviceAttributeDAO.insert_u64(argIn);
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
        deviceAttributeDAO.insert_u64(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final float argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final float[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final double argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final double[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final String argIn) {
        deviceAttributeDAO.insert(argIn);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final String[] argIn) {
        deviceAttributeDAO.insert(argIn);
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
        deviceAttributeDAO.insert(argIn, dim_x, dim_y);
    }

    // ===========================================
    /**
     * Insert method for attribute values.
     * 
     * @param argIn Attribute values.
     */
    // ===========================================
    public void insert(final DevEncoded argIn) {
        deviceAttributeDAO.insert(argIn);
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
        return deviceAttributeDAO.extractDevStateArray();
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
        return deviceAttributeDAO.extractDevState();
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
        return deviceAttributeDAO.extractBoolean();
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
        return deviceAttributeDAO.extractBooleanArray();
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
        return deviceAttributeDAO.extractUChar();
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
        return deviceAttributeDAO.extractUCharArray();
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
        return deviceAttributeDAO.extractCharArray();
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
        return deviceAttributeDAO.extractShort();
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
        return deviceAttributeDAO.extractShortArray();
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
        return deviceAttributeDAO.extractUShort();
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
        return deviceAttributeDAO.extractUShortArray();
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
        return deviceAttributeDAO.extractLong();
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
        return deviceAttributeDAO.extractLongArray();
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
        return deviceAttributeDAO.extractULong();
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
        return deviceAttributeDAO.extractULongArray();
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
        return deviceAttributeDAO.extractLong64();
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
        return deviceAttributeDAO.extractLong64Array();
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
        return deviceAttributeDAO.extractULong64();
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
        return deviceAttributeDAO.extractULong64Array();
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
	return deviceAttributeDAO.extractFloat();
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
        return deviceAttributeDAO.extractFloatArray();
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
        return deviceAttributeDAO.extractDouble();
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
        return deviceAttributeDAO.extractDoubleArray();
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
        return deviceAttributeDAO.extractState();
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
        return deviceAttributeDAO.extractString();
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
        return deviceAttributeDAO.extractStringArray();
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
        return deviceAttributeDAO.getQuality();
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
        return deviceAttributeDAO.extractDevEncoded();
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
        return deviceAttributeDAO.extractDevEncodedArray();
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
        return deviceAttributeDAO.getDataFormat();
    }

    // ===========================================
    /**
     * Return attribute time value.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public TimeVal getTimeVal() throws DevFailed {
        return deviceAttributeDAO.getTimeVal();
    }

    // ===========================================
    /**
     * Return attribute time value in seconds since EPOCH.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValSec() throws DevFailed {
        return deviceAttributeDAO.getTimeValSec();
    }

    // ===========================================
    /**
     * Return attribute time value in milli seconds since EPOCH.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public long getTimeValMillisSec() throws DevFailed {
        return deviceAttributeDAO.getTimeValMillisSec();
    }

    // ===========================================
    /**
     * Return attribute name.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public String getName() throws DevFailed {
        return deviceAttributeDAO.getName();
    }

    // ===========================================
    /**
     * Return number of data read.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getNbRead() throws DevFailed {
        return deviceAttributeDAO.getNbRead();
    }

    // ===========================================
    /**
     * Return number of data read.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    public AttributeDim getReadAttributeDim() throws DevFailed {
        return deviceAttributeDAO.getReadAttributeDim();
    }

    // ===========================================
    /**
     * Return number of data write.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    public AttributeDim getWriteAttributeDim() throws DevFailed {
        return deviceAttributeDAO.getWriteAttributeDim();
    }

    // ===========================================
    /**
     * Return number of data written.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getNbWritten() throws DevFailed {
        return deviceAttributeDAO.getNbWritten();
    }

    // ===========================================
    /**
     * Return attribute dim_x.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getDimX() throws DevFailed {
        return deviceAttributeDAO.getDimX();
    }

    // ===========================================
    /**
     * Return attribute dim_y.
     * 
     * @throws DevFailed in case of read_attribute failed
     */
    // ===========================================
    public int getDimY() throws DevFailed {
        return deviceAttributeDAO.getDimY();
    }

    // ===========================================
    /**
     * Return attribute written dim_x.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimX() throws DevFailed {
        return deviceAttributeDAO.getWrittenDimX();
    }

    // ===========================================
    /**
     * Return attribute written dim_y.
     * 
     * @throws DevFailed  in case of read_attribute failed
     */
    // ===========================================
    public int getWrittenDimY() throws DevFailed {
        return deviceAttributeDAO.getWrittenDimY();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue getAttributeValueObject_2() throws DevFailed {
        return deviceAttributeDAO.getAttributeValueObject_2();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     * 
     * @throws DevFailed
     */
    // ===========================================
    public AttributeValue_3 getAttributeValueObject_3() throws DevFailed {
        return deviceAttributeDAO.getAttributeValueObject_3();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_4 getAttributeValueObject_4() {
        return deviceAttributeDAO.getAttributeValueObject_4();
    }

    // ===========================================
    /**
     * Return AttributeValue IDL object.
     */
    // ===========================================
    public AttributeValue_5 getAttributeValueObject_5() {
        return deviceAttributeDAO.getAttributeValueObject_5();
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
        return deviceAttributeDAO.getTime();
    }

    // ===========================================
    // ===========================================
    public int getType() throws DevFailed {
        return deviceAttributeDAO.getType();
    }

    // ===========================================
    // ===========================================
    public IDeviceAttributeDAO getDeviceAttributeDAO() {
        return deviceAttributeDAO;
    }
}
